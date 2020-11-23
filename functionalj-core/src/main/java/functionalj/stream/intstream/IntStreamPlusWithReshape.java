// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import static functionalj.stream.intstream.IntStreamPlus.empty;
import static functionalj.stream.intstream.IntStreamPlus.generateWith;
import static functionalj.stream.intstream.IntStreamPlusHelper.sequentialToObj;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import functionalj.result.NoMoreResultException;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;


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
        var index = new AtomicInteger(0);
        return segment(data -> {
                    var currentIndex = index.getAndIncrement();
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
        var newStorage = (Supplier<GrowOnlyIntArray>)GrowOnlyIntArray::new;
        var toStreamPlus = (Function<GrowOnlyIntArray, IntStreamPlus>)GrowOnlyIntArray::stream;

        var streamPlus = intStreamPlus();
        return sequentialToObj(streamPlus, stream -> {
            var list = new AtomicReference<>(newStorage.get());
            var adding = new AtomicBoolean(false);

            var streamOrNull = (IntFunction<IntStreamPlus>)((int data) ->{
                if (startCondition.test(data)) {
                    adding.set(true);
                    var resultList = list.getAndUpdate(l -> newStorage.get());
                    list.get().add(data);
                    return resultList.isEmpty() ? null : toStreamPlus.apply(resultList);
                }
                if (adding.get()) {
                    list.get().add(data);
                }
                return null;
            });
            var mainStream = StreamPlus.from(stream.mapToObj(streamOrNull)).filterNonNull();
            if (!includeIncompletedSegment)
                return mainStream;

            var mainSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->mainStream;
            var tailSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->StreamPlus.of(toStreamPlus.apply(list.get()));
            var resultStream
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
        var includeIncompletedSegment = incompletedSegment == IncompletedSegment.included;
        return segment(startCondition, endCondition, includeIncompletedSegment);
    }

    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default StreamPlus<IntStreamPlus> segment(
            IntPredicate startCondition,
            IntPredicate endCondition,
            boolean      includeIncompletedSegment) {
        var newStorage   = (Supplier<GrowOnlyIntArray>)GrowOnlyIntArray::new;
        var toStreamPlus = (Function<GrowOnlyIntArray, IntStreamPlus>)GrowOnlyIntArray::stream;

        // TODO - Find a way to make it fully lazy. Try tryAdvance.
        var streamPlus = intStreamPlus();
        StreamPlus<IntStreamPlus> returnStream = sequentialToObj(streamPlus, stream -> {
            var list         = new AtomicReference<>(newStorage.get());
            var adding       = new AtomicBoolean(false);

            StreamPlus<IntStreamPlus> resultStream
                = StreamPlus.from(
                    stream
                    .mapToObj(i -> {
                        var shouldStart = startCondition.test(i);
                        if (shouldStart) {
                            adding.set(true);
                        }
                        if (includeIncompletedSegment && adding.get()) {
                            list.get().add(i);
                        }

                        if (endCondition.test(i)) {
                            adding.set(shouldStart);
                            var resultList = list.getAndUpdate(l -> newStorage.get());
                            return toStreamPlus.apply(resultList);
                        }

                        if (!includeIncompletedSegment && adding.get()) {
                            list.get().add(i);
                        }
                        return null;
                    }))
                    .filterNonNull();

            resultStream
            .onClose(()->stream.close());

            return resultStream;
        });
        return returnStream;
    }

    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     */
    public default StreamPlus<IntStreamPlus> segmentSize(IntFunction<Integer> segmentSize) {
        var newStorage   = (Supplier<GrowOnlyIntArray>)GrowOnlyIntArray::new;
        var toStreamPlus = (Function<GrowOnlyIntArray, IntStreamPlus>)GrowOnlyIntArray::stream;
        var emptyStream  = (Supplier<IntStreamPlus>)IntStreamPlus::empty;
        var singleStream = (IntFunction<IntStreamPlus>)(data -> IntStreamPlus.of(data));

        // TODO - Find a way to make it fully lazy. Try tryAdvance.
        var streamPlus = intStreamPlus();
        return sequentialToObj(streamPlus, stream -> {
            var listRef = new AtomicReference<>(newStorage.get());
            var leftRef = new AtomicInteger(-1);

            var head
                = stream
                .mapToObj(each -> {
                    int left = leftRef.get();
                    if (left == -1) {
                        var newSize = segmentSize.apply(each);
                        if (newSize == null) {
                            return null;
                        } else if (newSize == 0) {
                            return emptyStream.get();
                        } else if (newSize == 1) {
                            return singleStream.apply(each);
                        } else {
                            var list = listRef.get();
                            list.add(each);
                            leftRef.set(newSize - 1);
                        }
                    } else if (left == 1) {
                        var list = listRef.getAndSet(newStorage.get());
                        list.add(each);

                        leftRef.set(-1);
                        return toStreamPlus.apply(list);

                    } else {
                        var list = listRef.get();
                        list.add(each);
                        leftRef.decrementAndGet();
                    }
                    return null;
                })
                .filterNonNull()
                ;

            var resultStream
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

    /** Combine the current value with the one before it using then combinator everytime the condition to collapse is true. */
    public default IntStreamPlus collapseWhen(
            IntPredicate      conditionToCollapse,
            IntBinaryOperator combinator) {
        Object dummy = IntStreamPlusHelper.dummy;
        var intArray = new int[1];
        int first;

        var iterator = intStreamPlus().iterator();

        if (!iterator.hasNext()) {
            return empty();
        }
        try {
            first = iterator.next();
        } catch (NoSuchElementException e) {
            return empty();
        }

        var prev = new AtomicReference<Object>(new int[] { first });
        IntStreamPlus resultStream = generateWith(()->{
            if (prev.get() == dummy)
                throw new NoMoreResultException();

            while(true) {
                int next;
                var prevValue = intArray[0];
                if (!iterator.hasNext()) {
                    var yield = prevValue;
                    prev.set(dummy);
                    return yield;
                }

                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    var yield = prevValue;
                    prev.set(dummy);
                    return yield;
                }
                if (conditionToCollapse.test(next)) {
                    var newValue = combinator.applyAsInt(prevValue, next);
                    prev.set(newValue);
                } else {
                    var yield = prevValue;
                    intArray[0] = next;
                    prev.set(intArray);
                    return yield;
                }
            }
        });

        return resultStream;
    }

    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default IntStreamPlus collapseSize(
            IntFunction<Integer> segmentSize,
            IntBinaryOperator    combinator) {
        Object dummy = IntStreamPlusHelper.dummy;
        var intArray = new int[1];

        var firstObj = new Object();
        var iterator = intStreamPlus().iterator();
        var prev = new AtomicReference<Object>(firstObj);
        IntStreamPlus resultStream = generateWith(()->{
            if (prev.get() == dummy)
                throw new NoMoreResultException();

            while(true) {
                int next;
                Object prevValue = prev.get();
                try {
                    next = iterator.next();
                } catch (NoSuchElementException e) {
                    if (prevValue == firstObj)
                        throw new NoMoreResultException();

                    var yield = intArray[0];
                    prev.set(dummy);
                    return yield;
                }

                Integer newSize = segmentSize.apply(next);
                if ((newSize == null) || (newSize == 0)) {
                    continue;
                }

                if (newSize == 1) {
                    return next;
                }

                intArray[0] = next;
                prev.set(intArray);
                for (int i = 0; i < (newSize - 1); i++) {
                    try {
                        next = iterator.next();
                        int newValue = combinator.applyAsInt(intArray[0], next);
                        intArray[0] = newValue;
                        prev.set(intArray);
                    } catch (NoSuchElementException e) {
                        var yield = intArray[0];
                        prev.set(dummy);
                        return yield;
                    }
                }

                var yield = intArray[0];
                prev.set(firstObj);
                return yield;
            }
        });

        return resultStream;
    }

    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default IntStreamPlus collapseSize(
            IntUnaryOperator  segmentSize,
            IntUnaryOperator  mapper,
            IntBinaryOperator combinator) {
        // var dummy = IntStreamPlusHelper.dummy;
        // var intArray = new int[1];

        // var firstObj = new Object();
        // var iterator = streamPlus().iterator();
        // var prev = new AtomicReference<Object>(firstObj);
        // var resultStream = generateWith(()->{
        //     if (prev.get() == dummy)
        //         throw new NoMoreResultException();

        //     while(true) {
        //         int next;
        //         try {
        //             next = iterator.next();
        //         } catch (NoSuchElementException e) {
        //             if (prev.get() == firstObj)
        //                 throw new NoMoreResultException();

        //             var yield = prev.get();
        //             prev.set(StreamPlusHelper.dummy);
        //             return (TARGET)yield;
        //         }

        //         Integer newSize = segmentSize.apply(next);
        //         if ((newSize == null) || (newSize == 0)) {
        //             continue;
        //         }

        //         if (newSize == 1) {
        //             var target = (TARGET)mapper.apply((DATA)next);
        //             return target;
        //         }

        //         TARGET target = (TARGET)mapper.apply((DATA)next);
        //         prev.set(target);
        //         for (int i = 0; i < (newSize - 1); i++) {
        //             try {
        //                 next   = iterator.next();
        //                 target = (TARGET)mapper.apply((DATA)next);
        //                 var prevValue = (TARGET)prev.get();
        //                 var newValue  = combinator.apply(prevValue, target);
        //                 prev.set(newValue);
        //             } catch (NoSuchElementException e) {
        //                 var yield = prev.get();
        //                 prev.set(StreamPlusHelper.dummy);
        //                 return (TARGET)yield;
        //             }
        //         }

        //         var yield = prev.get();
        //         prev.set(firstObj);
        //         return (TARGET)yield;
        //     }
        // });

        // return resultStream;

        return null;
//        return sequential(stream -> {
//            var splitr = stream.spliterator();
//            var value = new AtomicReference<AtomicInteger>(null);
//            IntStreamPlus head = IntStreamPlus.from(StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
//                @Override
//                public boolean tryAdvance(IntConsumer consumer) {
//                    var count = new AtomicInteger(0);
//                    var hasNext = new AtomicBoolean();
//                    do {
//                        hasNext.set(splitr.tryAdvance((int next) -> {
//                            if (count.get() == 0) {
//                                int newSize = segmentSize.applyAsInt(next);
//                                if (newSize <= 0) {
//                                    count.set(1);
//                                    value.set(null);
//                                } else {
//                                    count.set(newSize);
//                                    value.set(new AtomicInteger(next));
//                                }
//                            } else {
//                                int newValue = concatFunc.applyAsInt(value.get().get(), next);
//                                value.get().set(newValue);
//                            }
//                        }));
//                    } while(count.decrementAndGet() > 0);
//
//                    if ((value.get() != null) && (hasNext.get() || includeTail)) {
//                        consumer.accept(value.get().get());
//                        count.set(0);
//                        value.set(null);
//                    }
//
//                    return hasNext.get();
//                }
//            }, false));
//
//            IntStreamPlus tail = (includeTail && (value.get() != null))
//                     ? IntStreamPlus.of(value.get().get())
//                     : IntStreamPlus.empty();
//            var resultStream
//                = StreamPlus.of(
//                    f(()-> head),
//                    f(()-> tail)
//                )
//                .map(each -> each.get())
//                .filterNonNull()
//                .reduce(IntStreamPlus::concat)
//                .get();
//
//            resultStream
//            .onClose(()->{
//                f(()->head.close()).runCarelessly();
//                f(()->tail.close()).runCarelessly();
//                IntStreamPlusWithSegment.this.close();
//            });
//
//            return resultStream;
//        });
    }

    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default <TARGET> StreamPlus<TARGET> collapseSize(
            IntFunction<Integer>               segmentSize,
            IntFunction<TARGET>                mapper,
            BiFunction<TARGET, TARGET, TARGET> combinator) {
//        return deriveFrom(this, stream -> stream.collapseSize(segmentSize, mapper, combinator));
        return null;
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
//            var prev = new int[][] { new int[] { first }};
//            var isDone = new boolean[] { false };
//            var collapseNext = new boolean[] { conditionToCollapseNext.test(first) };
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
//                        var yield = prev[0][0];
//                        prev[0] = null;
//                        return yield;
//                    }
//                    boolean collapseNow = collapseNext[0];
//                    collapseNext[0] = conditionToCollapseNext.test(next);
//                    if (collapseNow) {
//                        prev[0][0] = concatFunc.applyAsInt(prev[0][0], next);
//                    } else {
//                        var yield = prev[0][0];
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
