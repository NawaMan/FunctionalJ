// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.longstream;

import static functionalj.stream.longstream.LongStreamPlusHelper.sequential;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.stream.StreamSupport;
import functionalj.function.LongBiPredicatePrimitive;
import functionalj.function.aggregator.LongAggregationToBoolean;
import functionalj.stream.markers.Sequential;
import lombok.val;

public interface LongStreamPlusWithLimit {
    
    public LongStreamPlus longStreamPlus();
    
    /**
     * Limit the size of the stream to the given size.
     */
    public default LongStreamPlus limit(Long maxSize) {
        val streamPlus = longStreamPlus();
        return ((maxSize == null) || (maxSize.longValue() < 0)) ? streamPlus : streamPlus.limit((long) maxSize);
    }
    
    /**
     * Skip to the given offset position.
     */
    public default LongStreamPlus skip(Long offset) {
        val streamPlus = longStreamPlus();
        return ((offset == null) || (offset.longValue() < 0)) ? streamPlus : streamPlus.skip((long) offset);
    }
    
    /**
     * Skip any value while the condition is true.
     */
    @Sequential
    public default LongStreamPlus skipWhile(LongPredicate condition) {
        val streamPlus = longStreamPlus();
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
    public default LongStreamPlus skipWhile(LongAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return skipWhile(condition);
    }
    
    /**
     * Skip any value while the condition is true.
     */
    @Sequential
    public default LongStreamPlus skipWhile(LongBiPredicatePrimitive condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, stream -> {
            val orgSpliterator = stream.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean isStillSkipping = true;
        
                boolean isFirst = true;
        
                long prevValue = Long.MIN_VALUE;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    LongConsumer action = elem -> {
                        if (isStillSkipping) {
                            if (!isFirst) {
                                if (condition.testLongLong(prevValue, elem)) {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /**
     * Skip any value until the condition is true.
     */
    @Sequential
    public default LongStreamPlus skipUntil(LongPredicate condition) {
        val streamPlus = longStreamPlus();
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
    public default LongStreamPlus skipUntil(LongAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return skipUntil(condition);
    }
    
    /**
     * Skip any value until the condition is true.
     */
    @Sequential
    public default LongStreamPlus skipUntil(LongBiPredicatePrimitive condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, stream -> {
            val orgSpliterator = stream.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean isStillSkipping = true;
        
                boolean isFirst = true;
        
                long prevValue = Long.MIN_VALUE;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    LongConsumer action = elem -> {
                        if (isStillSkipping) {
                            if (!isFirst) {
                                if (!condition.testLongLong(prevValue, elem)) {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /**
     * Accept any value while the condition is true.
     */
    @Sequential
    public default LongStreamPlus acceptWhile(LongPredicate condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean stillGoing = true;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    if (stillGoing) {
                        LongConsumer action = elem -> {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /**
     * Accept any value while the condition is true.
     */
    @Sequential
    public default LongStreamPlus acceptWhile(LongAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return acceptWhile(condition);
    }
    
    /**
     * Accept any value while the condition is true.
     */
    @Sequential
    public default LongStreamPlus acceptWhile(LongBiPredicatePrimitive condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean stillGoing = true;
        
                boolean isFirst = true;
        
                long prevValue = -1;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    if (stillGoing) {
                        LongConsumer action = elem -> {
                            if (!isFirst) {
                                if (condition.testLongLong(prevValue, elem)) {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /**
     * Accept any value until the condition is true.
     */
    @Sequential
    public default LongStreamPlus acceptUntil(LongPredicate condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean stillGoing = true;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    if (stillGoing) {
                        LongConsumer action = elem -> {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /**
     * Accept any value until the condition is true.
     */
    @Sequential
    public default LongStreamPlus acceptUntil(LongAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return acceptWhile(condition);
    }
    
    /**
     * Accept any value until the condition is true.
     */
    @Sequential
    public default LongStreamPlus acceptUntil(LongBiPredicatePrimitive condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean stillGoing = true;
        
                boolean isFirst = true;
        
                long prevValue = -1;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    if (stillGoing) {
                        LongConsumer action = elem -> {
                            if (!isFirst) {
                                if (!condition.testLongLong(prevValue, elem)) {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    @Sequential
    public default LongStreamPlus dropAfter(LongPredicate condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean stillGoing = true;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    if (stillGoing) {
                        LongConsumer action = elem -> {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
    
    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    @Sequential
    public default LongStreamPlus dropAfter(LongAggregationToBoolean aggregationCondition) {
        val condition = aggregationCondition.newAggregator();
        return dropAfter(condition);
    }
    
    /**
     * Accept any value until the condition is false - include the item that the condition is false.
     */
    @Sequential
    public default LongStreamPlus dropAfter(LongBiPredicatePrimitive condition) {
        val streamPlus = longStreamPlus();
        return sequential(streamPlus, orgStreamPlus -> {
            val orgSpliterator = orgStreamPlus.spliterator();
            val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
                boolean stillGoing = true;
        
                boolean isFirst = true;
        
                long prevValue = -1;
        
                @Override
                public boolean tryAdvance(LongConsumer consumer) {
                    if (stillGoing) {
                        LongConsumer action = elem -> {
                            if (!isFirst) {
                                consumer.accept(elem);
                                if (condition.testLongLong(prevValue, elem)) {
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
            val newStream = StreamSupport.longStream(newSpliterator, false);
            return LongStreamPlus.from(newStream);
        });
    }
}
