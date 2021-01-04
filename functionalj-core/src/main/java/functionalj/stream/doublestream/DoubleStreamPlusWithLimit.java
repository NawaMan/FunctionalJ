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
package functionalj.stream.doublestream;

import static functionalj.stream.doublestream.DoubleStreamPlusHelper.sequential;

import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.stream.StreamSupport;

import functionalj.function.DoubleBiPredicatePrimitive;
import functionalj.stream.markers.Sequential;
import lombok.val;


public interface DoubleStreamPlusWithLimit {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /** Limit the size of the stream to the given size. */
    public default DoubleStreamPlus limit(Long maxSize) {
        val streamPlus = doubleStreamPlus();
        return ((maxSize == null) || (maxSize.longValue() < 0))
                ? streamPlus
                : DoubleStreamPlus.from(
                        streamPlus
                        .limit((long)maxSize));
    }
    
    /** Skip to the given offset position. */
    public default DoubleStreamPlus skip(Long offset) {
        val streamPlus = doubleStreamPlus();
        return ((offset == null) || (offset.longValue() < 0))
                ? streamPlus
                : DoubleStreamPlus.from(
                        streamPlus
                        .skip((long)offset));
    }
    
    /** Skip any value while the condition is true. */
    @Sequential
    public default DoubleStreamPlus skipWhile(DoublePredicate condition) {
        val streamPlus = doubleStreamPlus();
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
    
    /** Skip any value until the condition is true. */
    @Sequential
    public default DoubleStreamPlus skipUntil(DoublePredicate condition) {
        val streamPlus = doubleStreamPlus();
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
    
    /** Accept any value while the condition is true. */
    @Sequential
    public default DoubleStreamPlus takeWhile(DoublePredicate condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        val streamPlus = doubleStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            return DoubleStreamPlus.from(
                    StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        @Override
                        public boolean tryAdvance(DoubleConsumer consumer) {
                            if (stillGoing) {
                                DoubleConsumer action = elem -> {
                                    if (condition.test(elem)) {
                                        consumer.accept(elem);
                                    } else {
                                        stillGoing = false;
                                    }
                                };
                                boolean hadNext = splitr.tryAdvance(action);
                                return hadNext && stillGoing;
                            }
                            return false;
                        }
                    }, false)
                );
        });
    }
    
    /** Accept any value while the condition is true. */
    @Sequential
    public default DoubleStreamPlus takeWhile(DoubleBiPredicatePrimitive condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        val streamPlus = doubleStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            return DoubleStreamPlus.from(
                    StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        boolean isFirst    = true;
                        double  prevValue  = Double.NaN;
                        @Override
                        public boolean tryAdvance(DoubleConsumer consumer) {
                            if (stillGoing) {
                                DoubleConsumer action = elem -> {
                                    if (!isFirst) {
                                        if (condition.test(prevValue, elem)) {
                                            consumer.accept(elem);
                                        } else {
                                            stillGoing = false;
                                        }
                                    } else {
                                        isFirst = false;
                                    }
                                    prevValue = elem;
                                };
                                boolean hadNext = splitr.tryAdvance(action);
                                return hadNext && stillGoing;
                            }
                            return false;
                        }
                    }, false)
                );
        });
    }
    
    /** Accept any value until the condition is true. */
    @Sequential
    public default DoubleStreamPlus takeUntil(DoublePredicate condition) {
        val streamPlus = doubleStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                @Override
                public boolean tryAdvance(DoubleConsumer consumer) {
                    if (stillGoing) {
                        DoubleConsumer action = elem -> {
                            if (!condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        };
                        boolean hadNext = splitr.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            }, false);
            return DoubleStreamPlus.from(resultStream);
        });
    }
    
    /** Accept any value while the condition is true. */
    @Sequential
    public default DoubleStreamPlus dropAfter(DoublePredicate condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        val streamPlus = doubleStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            return DoubleStreamPlus.from(
                    StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        @Override
                        public boolean tryAdvance(DoubleConsumer consumer) {
                            if (stillGoing) {
                                DoubleConsumer action = elem -> {
                                    consumer.accept(elem);
                                    if (condition.test(elem)) {
                                        stillGoing = false;
                                    }
                                };
                                boolean hadNext = splitr.tryAdvance(action);
                                return hadNext && stillGoing;
                            }
                            return false;
                        }
                    }, false)
                );
        });
    }
    
    /** Accept any value until the condition is true. */
    @Sequential
    public default DoubleStreamPlus takeUntil(DoubleBiPredicatePrimitive condition) {
        val streamPlus = doubleStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                boolean isFirst    = true;
                double  prevValue  = -1;
                @Override
                public boolean tryAdvance(DoubleConsumer consumer) {
                    if (stillGoing) {
                        DoubleConsumer action = elem -> {
                            if (!isFirst) {
                                if (!condition.test(prevValue, elem)) {
                                    consumer.accept(elem);
                                } else {
                                    stillGoing = false;
                                }
                            } else {
                                isFirst = false;
                            }
                            prevValue = elem;
                        };
                        boolean hadNext = splitr.tryAdvance(action);
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            }, false);
            return DoubleStreamPlus.from(resultStream);
        });
    }
    
    /** Accept any value while the condition is true. */
    @Sequential
    public default DoubleStreamPlus dropAfter(DoubleBiPredicatePrimitive condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        val streamPlus = doubleStreamPlus();
        return sequential(streamPlus, stream -> {
            val splitr = stream.spliterator();
            return DoubleStreamPlus.from(
                    StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        boolean isFirst    = true;
                        double  prevValue  = -1;
                        @Override
                        public boolean tryAdvance(DoubleConsumer consumer) {
                            if (stillGoing) {
                                DoubleConsumer action = elem -> {
                                    if (!isFirst) {
                                        consumer.accept(elem);
                                        if (condition.test(prevValue, elem)) {
                                            stillGoing = false;
                                        }
                                    } else {
                                        isFirst = false;
                                    }
                                    prevValue = elem;
                                };
                                boolean hadNext = splitr.tryAdvance(action);
                                return hadNext && stillGoing;
                            }
                            return false;
                        }
                    }, false)
                );
        });
    }
    
}
