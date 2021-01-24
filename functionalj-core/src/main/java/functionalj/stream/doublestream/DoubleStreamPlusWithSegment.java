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
package functionalj.stream.doublestream;

import java.util.Objects;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.StreamSupport;

import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.doublelist.DoubleFuncListBuilder;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;
import functionalj.stream.markers.Sequential;
import lombok.val;


public interface DoubleStreamPlusWithSegment extends AsDoubleStreamPlus {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * The last portion may be shorter.
     **/
    @Sequential
    public default StreamPlus<DoubleStreamPlus> segment(int count) {
        if (count <= 0) {
            return StreamPlus.empty();
        }
        if (count <= 1) {
            return doubleStreamPlus().mapToObj(each -> DoubleStreamPlus.of(each));
        }
        
        val index = new AtomicInteger(0);
        return segmentWhen(data -> {
                    val currentIndex = index.getAndIncrement();
                    return (currentIndex % count) == 0;
                });
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return 0,
     *   the value will be ignored.
     */
    @Sequential
    public default StreamPlus<DoubleStreamPlus> segment(DoubleToIntFunction segmentSize) {
        Objects.requireNonNull(segmentSize);
        
        val splitr      = doubleStreamPlus().spliterator();
        val isSequence  = false;
        val spliterator = new Spliterators.AbstractSpliterator<DoubleStreamPlus>(splitr.estimateSize(), 0) {
            int count = -1;
            @Override
            public boolean tryAdvance(Consumer<? super DoubleStreamPlus> consumer) {
                val eachListBuilder = DoubleFuncList.newBuilder();
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((DoubleConsumer)(eachValue -> {
                        if (count < 1) {
                            count = segmentSize.applyAsInt(eachValue);
                        }
                        if (count > 0) {
                            eachListBuilder.add(eachValue);
                        }
                    }));
                }
                while(hasThis && (--count > 0));
                
                val eachList = eachListBuilder.build();
                val useThis  = !eachList.isEmpty();
                if (useThis) {
                    consumer.accept(eachList.doubleStreamPlus());
                }
                return hasThis;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default StreamPlus<DoubleStreamPlus> segmentWhen(DoublePredicate startCondition) {
        Objects.requireNonNull(startCondition);
        
        val splitr       = doubleStreamPlus().spliterator();
        val isSequence   = false;
        val spliterator  = new Spliterators.AbstractSpliterator<DoubleStreamPlus>(splitr.estimateSize(), 0) {
            DoubleFuncListBuilder eachListBuilder = DoubleFuncList.newBuilder();
            boolean               hasNewList      = false;
            @Override
            public boolean tryAdvance(Consumer<? super DoubleStreamPlus> consumer) {
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((DoubleConsumer)(eachValue -> {
                        if (startCondition.test(eachValue)) {
                            val eachList = eachListBuilder.build();
                            eachListBuilder = DoubleFuncList.newBuilder();
                            
                            val hasNewList = !eachList.isEmpty();
                            if (hasNewList) {
                                consumer.accept(eachList.doubleStreamPlus());
                            }
                        }
                        eachListBuilder.add(eachValue);
                    }));
                } while(hasThis && !hasNewList);
                if (hasNewList) {
                    hasNewList = false;
                    return true;
                }
                
                val eachList = eachListBuilder.build();
                eachListBuilder = DoubleFuncList.newBuilder();
                val useThis  = !eachList.isEmpty();
                if (useThis) {
                    consumer.accept(eachList.doubleStreamPlus());
                }
                return hasThis || useThis;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true.
     * The tail sub stream will always be included.
     */
    public default StreamPlus<DoubleStreamPlus> segment(
            DoublePredicate startCondition,
            DoublePredicate endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<DoubleStreamPlus> segment(
            DoublePredicate    startCondition,
            DoublePredicate    endCondition,
            IncompletedSegment incompletedSegment) {
        val includeIncompletedSegment = incompletedSegment == IncompletedSegment.included;
        return segment(startCondition, endCondition, includeIncompletedSegment);
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<DoubleStreamPlus> segment(
            DoublePredicate startCondition,
            DoublePredicate endCondition,
            boolean         includeIncompletedSegment) {
//        val newStorage   = (Supplier<GrowOnlyDoubleArray>)GrowOnlyDoubleArray::new;
//        val toStreamPlus = (Function<GrowOnlyDoubleArray, DoubleStreamPlus>)GrowOnlyDoubleArray::stream;
//        val list         = new AtomicReference<GrowOnlyDoubleArray>(null);
//
//        val streamPlus = doubleStreamPlus();
//        val head = (Supplier<StreamPlus<DoubleStreamPlus>>)() -> {
//            return sequentialToObj(streamPlus, stream -> {
//                return stream.mapToObj(i -> {
//                    boolean canStart = startCondition.test(i);
//                    boolean canEnd   = endCondition.test(i);
//                    val theList = list.get();
//
//                    // Add if the list is already there
//                    if (!canStart && theList != null) {
//                        theList.add(i);
//                    }
//                    // If end, remove list.
//                    if (canEnd && (theList != null)) {
//                        list.set(null);
//                    }
//                    // If start added list.
//                    if (canStart && (theList == null)) {
//                        list.set(newStorage.get());
//                    }
//                    // If start, make sure to add this one.
//                    if (canStart) {
//                        list.get().add(i);
//                    }
//
//                    if (!canEnd || (theList == null))
//                        return null;
//
//                    return toStreamPlus.apply(theList);
//                })
//                .filterNonNull();
//            });
//        };
//        val tail = (Supplier<StreamPlus<DoubleStreamPlus>>)() -> {
//            val theList = list.get();
//            return (includeIncompletedSegment && (theList != null) && !theList.isEmpty())
//                    ? streamOf(toStreamPlus.apply(theList))
//                    : null;
//        };
//
//        return streamOf(head, tail)
//                .flatMap(supplier -> supplier.get())
//                .filterNonNull();
        return null;
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     */
    public default StreamPlus<DoubleStreamPlus> segmentSize(DoubleFunction<Integer> segmentSize) {
//        val newStorage   = (Supplier<GrowOnlyDoubleArray>)GrowOnlyDoubleArray::new;
//        val toStreamPlus = (Function<GrowOnlyDoubleArray, DoubleStreamPlus>)GrowOnlyDoubleArray::stream;
//        val emptyStream  = (Supplier<DoubleStreamPlus>)DoubleStreamPlus::empty;
//        val singleStream = (IntFunction<DoubleStreamPlus>)(data -> DoubleStreamPlus.of(data));
//
//        // TODO - Find a way to make it fully lazy. Try tryAdvance.
//        val streamPlus = doubleStreamPlus();
//        return sequentialToObj(streamPlus, stream -> {
//            val listRef = new AtomicReference<>(newStorage.get());
//            val leftRef = new AtomicInteger(-1);
//
//            val head
//                = stream
//                .mapToObj(each -> {
//                    int left = leftRef.get();
//                    if (left == -1) {
//                        val newSize = segmentSize.apply(each);
//                        if (newSize == null) {
//                            return null;
//                        } else if (newSize == 0) {
//                            return emptyStream.get();
//                        } else if (newSize == 1) {
//                            return singleStream.apply(each);
//                        } else {
//                            val list = listRef.get();
//                            list.add(each);
//                            leftRef.set(newSize - 1);
//                        }
//                    } else if (left == 1) {
//                        val list = listRef.getAndSet(newStorage.get());
//                        list.add(each);
//
//                        leftRef.set(-1);
//                        return toStreamPlus.apply(list);
//
//                    } else {
//                        val list = listRef.get();
//                        list.add(each);
//                        leftRef.decrementAndGet();
//                    }
//                    return null;
//                })
//                .filterNonNull()
//                ;
//
//            val resultStream
//                = StreamPlus.of(
//                    f(()-> head),
//                    f(()-> StreamPlus.of(toStreamPlus.apply(listRef.get())))
//                )
//                .flatMap(s -> s.get());
//
//            resultStream
//            .onClose(()->stream.close());
//
//            return resultStream;
//        });
        return null;
    }
    
    /** Combine the current value with the one before it using then combinator everytime the condition to collapse is true. */
    public default DoubleStreamPlus collapseWhen(
            DoublePredicate      conditionToCollapse,
            DoubleBinaryOperator combinator) {
        val splitr      = doubleStreamPlus().spliterator();
        val spliterator = new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
            boolean isFirst      = true;
            double  accumulation = Double.NaN;
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                boolean hasNext;
                do {
                    hasNext = splitr.tryAdvance((double value) -> {
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
                                accumulation = combinator.applyAsDouble(accValue, value);
                            }
                        }
                    });
                } while(hasNext);
                consumer.accept(accumulation);
                return false;
            }
        };
        return DoubleStreamPlus.from(StreamSupport.doubleStream(spliterator, false));
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default DoubleStreamPlus collapseSize(
            DoubleFunction<Integer> segmentSize,
            DoubleBinaryOperator    combinator) {
//        Object dummy = DoubleStreamPlusHelper.dummy;
//        val array = new double[1];
//
//        val firstObj = new Object();
//        val iterator = doubleStreamPlus().iterator();
//        val prev = new AtomicReference<Object>(firstObj);
//        DoubleStreamPlus resultStream = generateWith(()->{
//            if (prev.get() == dummy)
//                throw new NoMoreResultException();
//
//            while(true) {
//                int next;
//                Object prevValue = prev.get();
//                try {
//                    next = iterator.next();
//                } catch (NoSuchElementException e) {
//                    if (prevValue == firstObj)
//                        throw new NoMoreResultException();
//
//                    val yield = array[0];
//                    prev.set(dummy);
//                    return yield;
//                }
//
//                Integer newSize = segmentSize.apply(next);
//                if ((newSize == null) || (newSize == 0)) {
//                    continue;
//                }
//
//                if (newSize == 1) {
//                    return next;
//                }
//
//                array[0] = next;
//                prev.set(array);
//                for (int i = 0; i < (newSize - 1); i++) {
//                    try {
//                        next = iterator.next();
//                        int newValue = combinator.applyAsDouble(array[0], next);
//                        array[0] = newValue;
//                        prev.set(array);
//                    } catch (NoSuchElementException e) {
//                        val yield = array[0];
//                        prev.set(dummy);
//                        return yield;
//                    }
//                }
//
//                val yield = array[0];
//                prev.set(firstObj);
//                return yield;
//            }
//        });
//
//        return resultStream;
        return null;
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default DoubleStreamPlus collapseSize(
            DoubleFunction<Integer> segmentSize,
            DoubleUnaryOperator     mapper,
            DoubleBinaryOperator    combinator) {
////         val dummy = IntStreamPlusHelper.dummy;
////         val intArray = new int[1];
////
////         val firstObj = new Object();
////         val iterator = streamPlus().iterator();
////         val prev = new AtomicReference<Object>(firstObj);
////         val resultStream = generateWith(()->{
////             if (prev.get() == dummy)
////                 throw new NoMoreResultException();
////
////             while(true) {
////                 int next;
////                 try {
////                     next = iterator.next();
////                 } catch (NoSuchElementException e) {
////                     if (prev.get() == firstObj)
////                         throw new NoMoreResultException();
////
////                     val yield = prev.get();
////                     prev.set(StreamPlusHelper.dummy);
////                     return (TARGET)yield;
////                 }
////
////                 Integer newSize = segmentSize.apply(next);
////                 if ((newSize == null) || (newSize == 0)) {
////                     continue;
////                 }
////
////                 if (newSize == 1) {
////                     val target = (TARGET)mapper.apply((DATA)next);
////                     return target;
////                 }
////
////                 TARGET target = (TARGET)mapper.apply((DATA)next);
////                 prev.set(target);
////                 for (int i = 0; i < (newSize - 1); i++) {
////                     try {
////                         next   = iterator.next();
////                         target = (TARGET)mapper.apply((DATA)next);
////                         val prevValue = (TARGET)prev.get();
////                         val newValue  = combinator.apply(prevValue, target);
////                         prev.set(newValue);
////                     } catch (NoSuchElementException e) {
////                         val yield = prev.get();
////                         prev.set(StreamPlusHelper.dummy);
////                         return (TARGET)yield;
////                     }
////                 }
////
////                 val yield = prev.get();
////                 prev.set(firstObj);
////                 return (TARGET)yield;
////             }
////         });
////
////         return resultStream;
////
////        return IntStreamPlusHelper.sequential(this, stream -> {
////            val splitr = stream.spliterator();
////            val value = new AtomicReference<AtomicInteger>(null);
////            IntStreamPlus head = IntStreamPlus.from(StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
////                @Override
////                public boolean tryAdvance(IntConsumer consumer) {
////                    val count = new AtomicInteger(0);
////                    val hasNext = new AtomicBoolean();
////                    do {
////                        hasNext.set(splitr.tryAdvance((int next) -> {
////                            if (count.get() == 0) {
////                                int newSize = segmentSize.applyAsInt(next);
////                                if (newSize <= 0) {
////                                    count.set(1);
////                                    value.set(null);
////                                } else {
////                                    count.set(newSize);
////                                    value.set(new AtomicInteger(next));
////                                }
////                            } else {
////                                int newValue = concatFunc.applyAsInt(value.get().get(), next);
////                                value.get().set(newValue);
////                            }
////                        }));
////                    } while(count.decrementAndGet() > 0);
////
////                    if ((value.get() != null) && (hasNext.get() || includeTail)) {
////                        consumer.accept(value.get().get());
////                        count.set(0);
////                        value.set(null);
////                    }
////
////                    return hasNext.get();
////                }
////            }, false));
////
////            IntStreamPlus tail = (includeTail && (value.get() != null))
////                     ? IntStreamPlus.of(value.get().get())
////                     : IntStreamPlus.empty();
////            val resultStream
////                = StreamPlus.of(
////                    f(()-> head),
////                    f(()-> tail)
////                )
////                .map(each -> each.get())
////                .filterNonNull()
////                .reduce(IntStreamPlus::concat)
////                .get();
////
////            resultStream
////            .onClose(()->{
////                f(()->head.close()).runCarelessly();
////                f(()->tail.close()).runCarelessly();
////                IntStreamPlusWithSegment.this.close();
////            });
////
////            return resultStream;
////        });
        return null;
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default <TARGET> StreamPlus<TARGET> collapseSize(
            DoubleFunction<Integer>            segmentSize,
            DoubleFunction<TARGET>             mapper,
            BiFunction<TARGET, TARGET, TARGET> combinator) {
////        return deriveFrom(this, stream -> stream.collapseSize(segmentSize, mapper, combinator));
        return null;
    }
    
    public default DoubleStreamPlus collapseAfter(
            DoublePredicate      conditionToCollapseNext,
            DoubleBinaryOperator concatFunc) {
////        return useIterator(iterator -> {
////            int first;
////            try {
////                first = iterator.next();
////            } catch (NoSuchElementException e) {
////                return IntStreamPlus.empty();
////            }
////
////            val prev = new int[][] { new int[] { first }};
////            val isDone = new boolean[] { false };
////            val collapseNext = new boolean[] { conditionToCollapseNext.test(first) };
////            IntStreamPlus resultStream = IntStreamPlus.generate(()->{
////                if (prev[0] == null) {
////                    isDone[0] = true;
////                    return Integer.MIN_VALUE;
////                }
////
////                while(true) {
////                    int next;
////                    try {
////                        next = iterator.nextInt();
////                    } catch (NoSuchElementException e) {
////                        val yield = prev[0][0];
////                        prev[0] = null;
////                        return yield;
////                    }
////                    boolean collapseNow = collapseNext[0];
////                    collapseNext[0] = conditionToCollapseNext.test(next);
////                    if (collapseNow) {
////                        prev[0][0] = concatFunc.applyAsInt(prev[0][0], next);
////                    } else {
////                        val yield = prev[0][0];
////                        prev[0][0] = next;
////                        return yield;
////                    }
////                }
////            })
////            .takeUntil(i -> isDone[0]);
////
////            return resultStream;
////        });
        return null;
    }
    
}
