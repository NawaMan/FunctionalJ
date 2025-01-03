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
package functionalj.stream.doublestream;

import java.util.Objects;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
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
     */
    @Sequential
    public default StreamPlus<DoubleFuncList> segment(int count) {
        if (count <= 0) {
            return StreamPlus.empty();
        }
        if (count <= 1) {
            return doubleStreamPlus().mapToObj(each -> DoubleFuncList.of(each));
        }
        val splitr = doubleStreamPlus().spliterator();
        val isSequence = false;
        val spliterator = new Spliterators.AbstractSpliterator<DoubleFuncList>(splitr.estimateSize(), 0) {
        
            @Override
            public boolean tryAdvance(Consumer<? super DoubleFuncList> consumer) {
                val eachListBuilder = DoubleFuncList.newBuilder();
                boolean hasThis;
                int i = count;
                do {
                    hasThis = splitr.tryAdvance((DoubleConsumer) eachListBuilder::add);
                } while (hasThis && (--i > 0));
                val eachList = eachListBuilder.build();
                val useThis = !eachList.isEmpty();
                if (useThis) {
                    consumer.accept(eachList);
                }
                return useThis;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return 0,
     *   the value will be ignored.
     */
    @Sequential
    public default StreamPlus<DoubleFuncList> segment(DoubleToIntFunction segmentSize) {
        Objects.requireNonNull(segmentSize);
        val splitr = doubleStreamPlus().spliterator();
        val isSequence = false;
        val spliterator = new Spliterators.AbstractSpliterator<DoubleFuncList>(splitr.estimateSize(), 0) {
        
            int count = -1;
        
            @Override
            public boolean tryAdvance(Consumer<? super DoubleFuncList> consumer) {
                val eachListBuilder = DoubleFuncList.newBuilder();
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((DoubleConsumer) (eachValue -> {
                        if (count < 1) {
                            count = segmentSize.applyAsInt(eachValue);
                        }
                        if (count > 0) {
                            eachListBuilder.add(eachValue);
                        }
                    }));
                } while (hasThis && (--count > 0));
                val eachList = eachListBuilder.build();
                val useThis = !eachList.isEmpty();
                if (useThis) {
                    consumer.accept(eachList);
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
    public default StreamPlus<DoubleFuncList> segmentWhen(DoublePredicate startCondition) {
        Objects.requireNonNull(startCondition);
        val splitr = doubleStreamPlus().spliterator();
        val isSequence = false;
        val spliterator = new Spliterators.AbstractSpliterator<DoubleFuncList>(splitr.estimateSize(), 0) {
        
            DoubleFuncListBuilder eachListBuilder = DoubleFuncList.newBuilder();
        
            boolean hasNewList = false;
        
            @Override
            public boolean tryAdvance(Consumer<? super DoubleFuncList> consumer) {
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((DoubleConsumer) (eachValue -> {
                        if (startCondition.test(eachValue)) {
                            val eachList = eachListBuilder.build();
                            eachListBuilder = DoubleFuncList.newBuilder();
                            val hasNewList = !eachList.isEmpty();
                            if (hasNewList) {
                                consumer.accept(eachList);
                            }
                        }
                        eachListBuilder.add(eachValue);
                    }));
                } while (hasThis && !hasNewList);
                if (hasNewList) {
                    hasNewList = false;
                    return true;
                }
                val eachList = eachListBuilder.build();
                eachListBuilder = DoubleFuncList.newBuilder();
                val useThis = !eachList.isEmpty();
                if (useThis) {
                    consumer.accept(eachList);
                }
                return hasThis || useThis;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
    
    /**
     * Segment the stream into sub stream starting the element after the precondition is true.
     */
    @Sequential
    public default StreamPlus<DoubleFuncList> segmentAfter(DoublePredicate endCondition) {
        Objects.requireNonNull(endCondition);
        val splitr = doubleStreamPlus().spliterator();
        val isSequence = false;
        val spliterator = new Spliterators.AbstractSpliterator<DoubleFuncList>(splitr.estimateSize(), 0) {
        
            DoubleFuncListBuilder eachListBuilder = DoubleFuncList.newBuilder();
        
            boolean hasNewList = false;
        
            @Override
            public boolean tryAdvance(Consumer<? super DoubleFuncList> consumer) {
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((DoubleConsumer) (eachValue -> {
                        eachListBuilder.add(eachValue);
                        if (endCondition.test(eachValue)) {
                            val eachList = eachListBuilder.build();
                            val hasNewList = !eachList.isEmpty();
                            if (hasNewList) {
                                consumer.accept(eachList);
                            }
                            eachListBuilder = DoubleFuncList.newBuilder();
                        }
                    }));
                } while (hasThis && !hasNewList);
                if (hasNewList) {
                    hasNewList = false;
                    return true;
                }
                val eachList = eachListBuilder.build();
                eachListBuilder = DoubleFuncList.newBuilder();
                val useThis = !eachList.isEmpty();
                if (useThis) {
                    consumer.accept(eachList);
                }
                return hasThis || useThis;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
    
    /**
     * Segment the stream into sub stream
     *   starting when the start condition is true
     *   and ending when the end condition is true
     *   -- both inclusively.
     *
     * Note: this method will not include the last sub stream
     *   even if the end condition is never been true before the stream ended.
     *
     * @param startCondition  the condition to start the sub stream
     * @param endCondition    the condition to end the sub stream
     */
    public default StreamPlus<DoubleFuncList> segmentBetween(DoublePredicate startCondition, DoublePredicate endCondition) {
        return segmentBetween(startCondition, endCondition, true);
    }
    
    /**
     * Segment the stream into sub stream
     *   starting when the start condition is true
     *   and ending when the end condition is true
     *   -- both inclusively.
     *
     * @param startCondition      the condition to start the sub stream
     * @param endCondition        the condition to end the sub stream
     * @param incompletedSegment  specifying if the incomplete segment at the end should be included.
     */
    public default StreamPlus<DoubleFuncList> segmentBetween(DoublePredicate startCondition, DoublePredicate endCondition, IncompletedSegment incompletedSegment) {
        val includeIncompletedSegment = incompletedSegment == IncompletedSegment.included;
        return segmentBetween(startCondition, endCondition, includeIncompletedSegment);
    }
    
    /**
     * Segment the stream into sub stream
     *   starting when the start condition is true
     *   and ending when the end condition is true
     *   -- both inclusively.
     *
     * @param startCondition             the condition to start the sub stream
     * @param endCondition               the condition to end the sub stream
     * @param includeIncompletedSegment  specifying if the incomplete segment at the end should be included.
     */
    public default StreamPlus<DoubleFuncList> segmentBetween(DoublePredicate startCondition, DoublePredicate endCondition, boolean includeIncompletedSegment) {
        Objects.requireNonNull(endCondition);
        val splitr = doubleStreamPlus().spliterator();
        val isSequence = false;
        val spliterator = new Spliterators.AbstractSpliterator<DoubleFuncList>(splitr.estimateSize(), 0) {
        
            DoubleFuncListBuilder eachListBuilder = null;
        
            boolean hasNewList = false;
        
            @Override
            public boolean tryAdvance(Consumer<? super DoubleFuncList> consumer) {
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((DoubleConsumer) (eachValue -> {
                        if ((eachListBuilder == null) && startCondition.test(eachValue)) {
                            eachListBuilder = DoubleFuncList.newBuilder();
                        }
                        if (eachListBuilder != null) {
                            eachListBuilder.add(eachValue);
                        }
                        if ((eachListBuilder != null) && endCondition.test(eachValue)) {
                            val eachList = eachListBuilder.build();
                            hasNewList = !eachList.isEmpty();
                            if (hasNewList) {
                                consumer.accept(eachList);
                            }
                            eachListBuilder = null;
                        }
                    }));
                } while (hasThis && !hasNewList);
                if (hasNewList) {
                    hasNewList = false;
                    return true;
                }
                if (includeIncompletedSegment && (eachListBuilder != null)) {
                    val eachList = eachListBuilder.build();
                    eachListBuilder = DoubleFuncList.newBuilder();
                    val useThis = !eachList.isEmpty();
                    if (useThis) {
                        consumer.accept(eachList);
                    }
                    return hasThis || useThis;
                }
                return hasThis;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
}
