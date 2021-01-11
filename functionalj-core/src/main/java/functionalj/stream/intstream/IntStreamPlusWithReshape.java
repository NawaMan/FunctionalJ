// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.stream.intstream;

import static functionalj.function.Func.f;
import static functionalj.stream.StreamPlus.streamOf;
import static functionalj.stream.intstream.IntStreamPlus.generateWith;
import static functionalj.stream.intstream.IntStreamPlusHelper.sequentialToObj;
import static java.lang.Math.max;

import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import functionalj.result.NoMoreResultException;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;
import functionalj.stream.markers.Sequential;
import lombok.val;


public interface IntStreamPlusWithReshape extends AsIntStreamPlus {
    
    public IntStreamPlus intStreamPlus();
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * The last portion may be shorter.
     **/
    public default StreamPlus<IntStreamPlus> segmentSize(int count) {
        return segmentSize(count, true);
    }
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * Depending on the includeTail flag, the last sub stream may not be included if its length is not `count`.
     *
     * @param count        the element count of the sub stream.
     * @param includeTail  the flag indicating if the last sub stream that does not have count element is to be included
     *                       as opposed to thrown away.
     * @return             the stream of sub stream.
     */
    public default StreamPlus<IntStreamPlus> segmentSize(int count, boolean includeTail) {
        val index = new AtomicInteger(0);
        return segment(data -> {
                    val currentIndex = index.getAndIncrement();
                    return (currentIndex % count) == 0;
                },
                includeTail);
    }
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * Depending on the includeTail flag, the last sub stream may not be included if its length is not `count`.
     *
     * @param count               the element count of the sub stream.
     * @param incompletedSegment  the option indicating if the last sub stream that does not have count element is to be included
     *                              as opposed to thrown away.
     * @return                    the stream of sub stream.
     */
    public default StreamPlus<IntStreamPlus> segmentSize(int count, IncompletedSegment incompletedSegment) {
        return segmentSize(count, (incompletedSegment == IncompletedSegment.included));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition) {
        return segment(startCondition, true);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition,
            IncompletedSegment incompletedSegment) {
        return segment(startCondition, (incompletedSegment == IncompletedSegment.included));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition,
            boolean      includeIncompletedSegment) {
        // TODO - Find a way to make it fully lazy. Try tryAdvance.
        val newStorage = (Supplier<GrowOnlyIntArray>)GrowOnlyIntArray::new;
        val toStreamPlus = (Function<GrowOnlyIntArray, IntStreamPlus>)GrowOnlyIntArray::stream;
        
        val streamPlus = intStreamPlus();
        return sequentialToObj(streamPlus, stream -> {
            val list = new AtomicReference<>(newStorage.get());
            val adding = new AtomicBoolean(false);
            
            val streamOrNull = (IntFunction<IntStreamPlus>)((int data) ->{
                if (startCondition.test(data)) {
                    adding.set(true);
                    val resultList = list.getAndUpdate(l -> newStorage.get());
                    list.get().add(data);
                    return resultList.isEmpty() ? null : toStreamPlus.apply(resultList);
                }
                if (adding.get()) {
                    list.get().add(data);
                }
                return null;
            });
            val mainStream = StreamPlus.from(stream.mapToObj(streamOrNull)).filterNonNull();
            if (!includeIncompletedSegment)
                return mainStream;
            
            val mainSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->mainStream;
            val tailSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->StreamPlus.of(toStreamPlus.apply(list.get()));
            val resultStream
                    = StreamPlus.of(mainSupplier, tailSupplier)
                    .flatMap(Supplier::get);
            
            resultStream
            .onClose(()->stream.close());
            
            return resultStream;
        });
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true.
     * The tail sub stream will always be included.
     */
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition,
            IntPredicate endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate       startCondition,
            IntPredicate       endCondition,
            IncompletedSegment incompletedSegment) {
        val includeIncompletedSegment = incompletedSegment == IncompletedSegment.included;
        return segment(startCondition, endCondition, includeIncompletedSegment);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition,
            IntPredicate endCondition,
            boolean      includeIncompletedSegment) {
        val newStorage   = (Supplier<GrowOnlyIntArray>)GrowOnlyIntArray::new;
        val toStreamPlus = (Function<GrowOnlyIntArray, IntStreamPlus>)GrowOnlyIntArray::stream;
        val list         = new AtomicReference<GrowOnlyIntArray>(null);
        
        val streamPlus = intStreamPlus();
        val head = (Supplier<StreamPlus<IntStreamPlus>>)() -> {
            return sequentialToObj(streamPlus, stream -> {
                return stream.mapToObj(i -> {
                    boolean canStart = startCondition.test(i);
                    boolean canEnd   = endCondition.test(i);
                    val theList = list.get();
                    
                    // Add if the list is already there
                    if (!canStart && theList != null) {
                        theList.add(i);
                    }
                    // If end, remove list.
                    if (canEnd && (theList != null)) {
                        list.set(null);
                    }
                    // If start added list.
                    if (canStart && (theList == null)) {
                        list.set(newStorage.get());
                    }
                    // If start, make sure to add this one.
                    if (canStart) {
                        list.get().add(i);
                    }
                    
                    if (!canEnd || (theList == null))
                        return null;
                    
                    return toStreamPlus.apply(theList);
                })
                .filterNonNull();
            });
        };
        val tail = (Supplier<StreamPlus<IntStreamPlus>>)() -> {
            val theList = list.get();
            return (includeIncompletedSegment && (theList != null) && !theList.isEmpty())
                    ? streamOf(toStreamPlus.apply(theList))
                    : null;
        };
        
        return streamOf(head, tail)
                .flatMap(supplier -> supplier.get())
                .filterNonNull();
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     */
    public default StreamPlus<IntStreamPlus> segmentSize(IntFunction<Integer> segmentSize) {
        val newStorage   = (Supplier<GrowOnlyIntArray>)GrowOnlyIntArray::new;
        val toStreamPlus = (Function<GrowOnlyIntArray, IntStreamPlus>)GrowOnlyIntArray::stream;
        val emptyStream  = (Supplier<IntStreamPlus>)IntStreamPlus::empty;
        val singleStream = (IntFunction<IntStreamPlus>)(data -> IntStreamPlus.of(data));
        
        // TODO - Find a way to make it fully lazy. Try tryAdvance.
        val streamPlus = intStreamPlus();
        return sequentialToObj(streamPlus, stream -> {
            val listRef = new AtomicReference<>(newStorage.get());
            val leftRef = new AtomicInteger(-1);
            
            val head
                = stream
                .mapToObj(each -> {
                    int left = leftRef.get();
                    if (left == -1) {
                        val newSize = segmentSize.apply(each);
                        if (newSize == null) {
                            return null;
                        } else if (newSize == 0) {
                            return emptyStream.get();
                        } else if (newSize == 1) {
                            return singleStream.apply(each);
                        } else {
                            val list = listRef.get();
                            list.add(each);
                            leftRef.set(newSize - 1);
                        }
                    } else if (left == 1) {
                        val list = listRef.getAndSet(newStorage.get());
                        list.add(each);
                        
                        leftRef.set(-1);
                        return toStreamPlus.apply(list);
                        
                    } else {
                        val list = listRef.get();
                        list.add(each);
                        leftRef.decrementAndGet();
                    }
                    return null;
                })
                .filterNonNull()
                ;
            
            val resultStream
                = StreamPlus.of(
                    f(()-> head),
                    f(()-> StreamPlus.of(toStreamPlus.apply(listRef.get())))
                )
                .flatMap(s -> s.get());
            
            resultStream
            .onClose(()->stream.close());
            
            return resultStream;
        });
    }
    
    /**
     * Combine the current value with the one before it using
     *   then combinator every time the condition to collapse is true.
     **/
    @Sequential
    public default IntStreamPlus collapseWhen(
            IntPredicate      conditionToCollapse,
            IntBinaryOperator combinator) {
        val splitr      = intStreamPlus().spliterator();
        val spliterator = new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
            boolean isFirst      = true;
            int     accumulation = Integer.MIN_VALUE;
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                boolean hasNext;
                do {
                    hasNext = splitr.tryAdvance((int value) -> {
                        val toCollapse = conditionToCollapse.test(value);
                        if (isFirst) {
                            accumulation = value;
                            isFirst = false;
                        } else {
                            val accValue = accumulation;
                            if (!toCollapse) {
                                consumer.accept(accValue);
                                accumulation = value;
                            } else {
                                accumulation = combinator.applyAsInt(accValue, value);
                            }
                        }
                    });
                } while(hasNext);
                consumer.accept(accumulation);
                return false;
            }
        };
        return IntStreamPlus.from(StreamSupport.intStream(spliterator, false));
    }
    
    /**
     * Collapse the value of this stream together.
     * Each sub stream size is determined by the segmentSize function.
     *
     * If the segmentSize function return null or less than 1,
     *     the value will be used as is (no collapse).
     */
    @Sequential
    public default IntStreamPlus collapseSize(
            IntFunction<Integer> segmentSize,
            IntBinaryOperator    combinator) {
        val splitr       = intStreamPlus().spliterator();
        val isParallel   = false;
        val isSequence   = !isParallel;
        val unsetCounter = Integer.MIN_VALUE;
        val spliterator  = new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
            int counter      = unsetCounter;
            int accumulation = Integer.MIN_VALUE;
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                counter = unsetCounter;
                // Try to find the first element.
                boolean hasNext = true;
                while (hasNext) {
                    hasNext = splitr.tryAdvance((int value) -> {
                        accumulation = value;
                        counter      = Math.max(0, segmentSize.apply(value));
                    });
                    // Do not find the first element
                    if (!hasNext) {
                        return false;
                    }
                    // Collect the rest of the element
                    while ((counter > 1) && hasNext) {
                        hasNext = splitr.tryAdvance((int value) -> {
                            accumulation = combinator.applyAsInt(accumulation, value);
                            counter--;
                        });
                    }
                    // Send out the value if exist.
                    if (counter >= 0) {
                        consumer.accept(accumulation);
                        return true;
                    }
                }
                return false;
            }
        };
        return IntStreamPlus.from(StreamSupport.intStream(spliterator, isSequence));
    }
    
    /**
     * Collapse the value of this stream together.
     * Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or less than 1,
     *     the value will be used as is (no collapse).
     */
    @Sequential
    public default IntStreamPlus collapseSize(
            IntFunction<Integer> segmentSize,
            IntUnaryOperator     mapper,
            IntBinaryOperator    combinator) {
        val splitr       = intStreamPlus().spliterator();
        val isParallel   = false;
        val isSequence   = !isParallel;
        val unsetCounter = Integer.MIN_VALUE;
        val spliterator  = new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
            int counter      = unsetCounter;
            int accumulation = Integer.MIN_VALUE;
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                counter = unsetCounter;
                boolean hasNext = true;
                while (hasNext) {
                    // Try to find the first element.
                    hasNext = splitr.tryAdvance((int value) -> {
                        accumulation = mapper.applyAsInt(value);
                        counter      = Math.max(0, segmentSize.apply(value));
                    });
                    // Do not find the first element
                    if (!hasNext) {
                        return false;
                    }
                    // Collect the rest of the element by the counter
                    while ((counter > 1) && hasNext) {
                        hasNext = splitr.tryAdvance((int value) -> {
                            val mappedValue = mapper.applyAsInt(value);
                            accumulation    = combinator.applyAsInt(accumulation, mappedValue);
                            counter--;
                        });
                    }
                    // Send out the value if exist.
                    if (counter >= 0) {
                        consumer.accept(accumulation);
                        return true;
                    }
                }
                return false;
            }
        };
        return IntStreamPlus.from(StreamSupport.intStream(spliterator, isSequence));
    }
    
    /**
     * Collapse the value of this stream together.
     * Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or less than 1,
     *     the value will be used as is (no collapse).
     */
    @Sequential
    public default <TARGET> StreamPlus<TARGET> collapseSizeToObj(
            IntUnaryOperator                   segmentSize,
            IntFunction<TARGET>                mapper,
            BiFunction<TARGET, TARGET, TARGET> combinator) {
        val splitr       = intStreamPlus().spliterator();
        val isParallel   = false;
        val isSequence   = !isParallel;
        val unsetCounter = Integer.MIN_VALUE;
        val spliterator  = new Spliterators.AbstractSpliterator<TARGET>(splitr.estimateSize(), 0) {
            @Override
            public boolean tryAdvance(Consumer<? super TARGET> consumer) {
                val counter     = new AtomicInteger(unsetCounter);
                val accumulator = new AtomicReference<TARGET>();
                // Try to find the first element.
                boolean hasNext = true;
                while (hasNext) {
                    hasNext = splitr.tryAdvance((int value) -> {
                        val mappedValue = mapper.apply(value);
                        accumulator.set(mappedValue);
                        val count = Math.max(0, segmentSize.applyAsInt(value));
                        counter.set(count);
                    });
                    // Do not find the first element
                    if (!hasNext) {
                        return false;
                    }
                    // Collect the rest of the element
                    while ((counter.get() > 1) && hasNext) {
                        hasNext = splitr.tryAdvance((int value) -> {
                            val accValue    = accumulator.get();
                            val mappedValue = mapper.apply(value);
                            val newValue    = combinator.apply(accValue, mappedValue);
                            accumulator.set(newValue);
                            counter.decrementAndGet();
                        });
                    }
                    // Send out the value if exist.
                    if (counter.get() >= 0) {
                        val accValue = accumulator.get();
                        consumer.accept(accValue);
                        return true;
                    }
                }
                return false;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
    
    public default IntStreamPlus collapseAfter(
            IntPredicate      conditionToCollapseNext,
            IntBinaryOperator concatFunc) {
//        return useIterator(iterator -> {
//            int first;
//            try {
//                first = iterator.next();
//            } catch (NoSuchElementException e) {
//                return IntStreamPlus.empty();
//            }
//
//            val prev = new int[][] { new int[] { first }};
//            val isDone = new boolean[] { false };
//            val collapseNext = new boolean[] { conditionToCollapseNext.test(first) };
//            IntStreamPlus resultStream = IntStreamPlus.generate(()->{
//                if (prev[0] == null) {
//                    isDone[0] = true;
//                    return Integer.MIN_VALUE;
//                }
//
//                while(true) {
//                    int next;
//                    try {
//                        next = iterator.nextInt();
//                    } catch (NoSuchElementException e) {
//                        val yield = prev[0][0];
//                        prev[0] = null;
//                        return yield;
//                    }
//                    boolean collapseNow = collapseNext[0];
//                    collapseNext[0] = conditionToCollapseNext.test(next);
//                    if (collapseNow) {
//                        prev[0][0] = concatFunc.applyAsInt(prev[0][0], next);
//                    } else {
//                        val yield = prev[0][0];
//                        prev[0][0] = next;
//                        return yield;
//                    }
//                }
//            })
//            .takeUntil(i -> isDone[0]);
//
//            return resultStream;
//        });
        return null;
    }

}
