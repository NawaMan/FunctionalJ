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

import static functionalj.stream.intstream.IntStreamPlusHelper.sequential;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.aggregator.IntAggregationToBoolean;
import functionalj.stream.markers.Sequential;
import lombok.val;

public interface IntStreamPlusWithLimit {

    public IntStreamPlus intStreamPlus();

    /**
     * Limit the size of the stream to the given size.
     */
    public default IntStreamPlus limit(Long maxSize) {
        val streamPlus = intStreamPlus();
        return ((maxSize == null) || (maxSize.longValue() < 0)) ? streamPlus : streamPlus.limit((long) maxSize);
    }

    /**
     * Skip to the given offset position.
     */
    public default IntStreamPlus skip(Long offset) {
        val streamPlus = intStreamPlus();
        return ((offset == null) || (offset.longValue() < 0)) ? streamPlus : streamPlus.skip((long) offset);
    }

    /**
     * Skip any value while the condition is true.
     */
    @Sequential
    public default IntStreamPlus skipWhile(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                if (!condition.test(e))
                    isStillTrue.set(false);
                return !isStillTrue.get();
            });
        });
    }

    /**
     * Skip any value while the condition is true.
     */
    @Sequential
    public default IntStreamPlus skipWhile(IntAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return skipWhile(condition);
    }

    /**
     * Skip any value while the condition is true.
     */
    @Sequential
    public default IntStreamPlus skipWhile(IntBiPredicatePrimitive condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val orgSpliterator = stream.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean isStillSkipping = true;

                boolean isFirst = true;

                int prevValue = Integer.MIN_VALUE;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    IntConsumer action = elem -> {
                        if (isStillSkipping) {
                            if (!isFirst) {
                                if (condition.test(prevValue, elem)) {
                                    isStillSkipping = false;
                                }
                            } else {
                                isFirst = false;
                            }
                            if (!isStillSkipping) {
                                consumer.accept(prevValue);
                            }
                            prevValue = elem;
                        } else {
                            consumer.accept(prevValue);
                            prevValue = elem;
                        }
                    };
                    boolean hadNext = orgSpliterator.tryAdvance(action);
                    if (!isStillSkipping && !hadNext) {
                        consumer.accept(prevValue);
                    }
                    return hadNext;
                }
            };
            IntStream newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }

    /**
     * Skip any value until the condition is true.
     */
    @Sequential
    public default IntStreamPlus skipUntil(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                if (condition.test(e))
                    isStillTrue.set(false);
                return !isStillTrue.get();
            });
        });
    }

    /**
     * Skip any value until the condition is true.
     */
    @Sequential
    public default IntStreamPlus skipUntil(IntAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return skipUntil(condition);
    }

    /**
     * Skip any value until the condition is true.
     */
    @Sequential
    public default IntStreamPlus skipUntil(IntBiPredicatePrimitive condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, stream -> {
            val orgSpliterator = stream.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean isStillSkipping = true;

                boolean isFirst = true;

                int prevValue = Integer.MIN_VALUE;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    IntConsumer action = elem -> {
                        if (isStillSkipping) {
                            if (!isFirst) {
                                if (!condition.test(prevValue, elem)) {
                                    isStillSkipping = false;
                                }
                            } else {
                                isFirst = false;
                            }
                            if (!isStillSkipping) {
                                consumer.accept(prevValue);
                            }
                            prevValue = elem;
                        } else {
                            consumer.accept(prevValue);
                            prevValue = elem;
                        }
                    };
                    boolean hadNext = orgSpliterator.tryAdvance(action);
                    if (!isStillSkipping && !hadNext) {
                        consumer.accept(prevValue);
                    }
                    return hadNext;
                }
            };
            IntStream newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }

    /**
     * Accept any value while the condition is true.
     */
    @Sequential
    public default IntStreamPlus acceptWhile(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean stillGoing = true;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        IntConsumer action = elem -> {
                            if (condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        };
                        boolean hadNext = orgSpliterator.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            };
            IntStream newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }

    /**
     * Accept any value while the condition is true.
     */
    @Sequential
    public default IntStreamPlus acceptWhile(IntAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return acceptWhile(condition);
    }

    /**
     * Accept any value while the condition is true.
     */
    @Sequential
    public default IntStreamPlus acceptWhile(IntBiPredicatePrimitive condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean stillGoing = true;

                boolean isFirst = true;

                int prevValue = -1;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        IntConsumer action = elem -> {
                            if (!isFirst) {
                                if (condition.test(prevValue, elem)) {
                                    consumer.accept(elem);
                                } else {
                                    stillGoing = false;
                                }
                            } else {
                                consumer.accept(elem);
                                isFirst = false;
                            }
                            prevValue = elem;
                        };
                        boolean hadNext = orgSpliterator.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            };
            val newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }

    /**
     * Accept any value until the condition is true.
     */
    @Sequential
    public default IntStreamPlus acceptUntil(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean stillGoing = true;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        IntConsumer action = elem -> {
                            if (!condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        };
                        boolean hadNext = orgSpliterator.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            };
            val newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }

    /**
     * Accept any value until the condition is true.
     */
    @Sequential
    public default IntStreamPlus acceptUntil(IntAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return acceptWhile(condition);
    }

    /**
     * Accept any value until the condition is true.
     */
    @Sequential
    public default IntStreamPlus acceptUntil(IntBiPredicatePrimitive condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean stillGoing = true;

                boolean isFirst = true;

                int prevValue = -1;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        IntConsumer action = elem -> {
                            if (!isFirst) {
                                if (!condition.test(prevValue, elem)) {
                                    consumer.accept(elem);
                                } else {
                                    stillGoing = false;
                                }
                            } else {
                                consumer.accept(elem);
                                isFirst = false;
                            }
                            prevValue = elem;
                        };
                        boolean hadNext = orgSpliterator.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            };
            val newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }

    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    @Sequential
    public default IntStreamPlus dropAfter(IntPredicate condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean stillGoing = true;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        IntConsumer action = elem -> {
                            consumer.accept(elem);
                            if (condition.test(elem)) {
                                stillGoing = false;
                            }
                        };
                        boolean hadNext = orgSpliterator.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            };
            val newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }

    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    @Sequential
    public default IntStreamPlus dropAfter(IntAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return dropAfter(condition);
    }

    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    @Sequential
    public default IntStreamPlus dropAfter(IntBiPredicatePrimitive condition) {
        val streamPlus = intStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {

                boolean stillGoing = true;

                boolean isFirst = true;

                int prevValue = -1;

                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        IntConsumer action = elem -> {
                            if (!isFirst) {
                                consumer.accept(elem);
                                if (condition.test(prevValue, elem)) {
                                    stillGoing = false;
                                }
                            } else {
                                consumer.accept(elem);
                                isFirst = false;
                            }
                            prevValue = elem;
                        };
                        boolean hadNext = orgSpliterator.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            };
            val newStream = StreamSupport.intStream(newSpliterator, false);
            return IntStreamPlus.from(newStream);
        });
    }
}
