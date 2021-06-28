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
package functionalj.stream.longstream;

import java.util.Objects;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongToIntFunction;
import java.util.stream.StreamSupport;

import functionalj.list.longlist.LongFuncList;
import functionalj.list.longlist.LongFuncListBuilder;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;
import functionalj.stream.markers.Sequential;
import lombok.val;


public interface LongStreamPlusWithSegment {
    
    public LongStreamPlus longStreamPlus();
    
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * The last portion may be shorter.
     **/
    @Sequential
    public default StreamPlus<LongFuncList> segment(int count) {
        if (count <= 0) {
            return StreamPlus.empty();
        }
        if (count <= 1) {
            return longStreamPlus().mapToObj(each -> LongFuncList.of(each));
        }
        
        val splitr      = longStreamPlus().spliterator();
        val isSequence  = false;
        val spliterator = new Spliterators.AbstractSpliterator<LongFuncList>(splitr.estimateSize(), 0) {
            @Override
            public boolean tryAdvance(Consumer<? super LongFuncList> consumer) {
                val eachListBuilder = LongFuncList.newBuilder();
                boolean hasThis;
                int i = count;
                do { hasThis = splitr.tryAdvance((LongConsumer)eachListBuilder::add); }
                while(hasThis && (--i > 0));
                
                val eachList = eachListBuilder.build();
                val useThis  = !eachList.isEmpty();
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
    public default StreamPlus<LongFuncList> segment(LongToIntFunction segmentSize) {
        Objects.requireNonNull(segmentSize);
        
        val splitr      = longStreamPlus().spliterator();
        val isSequence  = false;
        val spliterator = new Spliterators.AbstractSpliterator<LongFuncList>(splitr.estimateSize(), 0) {
            int count = -1;
            @Override
            public boolean tryAdvance(Consumer<? super LongFuncList> consumer) {
                val eachListBuilder = LongFuncList.newBuilder();
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((LongConsumer)(eachValue -> {
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
    public default StreamPlus<LongFuncList> segmentWhen(LongPredicate startCondition) {
        Objects.requireNonNull(startCondition);
        
        val splitr       = longStreamPlus().spliterator();
        val isSequence   = false;
        val spliterator  = new Spliterators.AbstractSpliterator<LongFuncList>(splitr.estimateSize(), 0) {
            LongFuncListBuilder eachListBuilder = LongFuncList.newBuilder();
            boolean             hasNewList      = false;
            @Override
            public boolean tryAdvance(Consumer<? super LongFuncList> consumer) {
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((LongConsumer)(eachValue -> {
                        if (startCondition.test(eachValue)) {
                            val eachList = eachListBuilder.build();
                            eachListBuilder = LongFuncList.newBuilder();
                            
                            val hasNewList = !eachList.isEmpty();
                            if (hasNewList) {
                                consumer.accept(eachList);
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
                eachListBuilder = LongFuncList.newBuilder();
                val useThis  = !eachList.isEmpty();
                if (useThis) {
                    consumer.accept(eachList);
                }
                return hasThis || useThis;
            }
        };
        return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
    }
    
    /** Segment the stream into sub stream starting the element after the precondition is true. */
    @Sequential
    public default StreamPlus<LongFuncList> segmentAfter(LongPredicate endCondition) {
        Objects.requireNonNull(endCondition);
        
        val splitr       = longStreamPlus().spliterator();
        val isSequence   = false;
        val spliterator  = new Spliterators.AbstractSpliterator<LongFuncList>(splitr.estimateSize(), 0) {
            LongFuncListBuilder eachListBuilder = LongFuncList.newBuilder();
            boolean             hasNewList      = false;
            @Override
            public boolean tryAdvance(Consumer<? super LongFuncList> consumer) {
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((LongConsumer)(eachValue -> {
                        eachListBuilder.add(eachValue);
                        if (endCondition.test(eachValue)) {
                            val eachList = eachListBuilder.build();
                            val hasNewList = !eachList.isEmpty();
                            if (hasNewList) {
                                consumer.accept(eachList);
                            }
                            eachListBuilder = LongFuncList.newBuilder();
                        }
                    }));
                } while(hasThis && !hasNewList);
                if (hasNewList) {
                    hasNewList = false;
                    return true;
                }
                
                val eachList = eachListBuilder.build();
                eachListBuilder = LongFuncList.newBuilder();
                val useThis  = !eachList.isEmpty();
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
    public default StreamPlus<LongFuncList> segmentBetween(
            LongPredicate startCondition,
            LongPredicate endCondition) {
        return segmentBetween(startCondition, endCondition, true);
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
     **/
    public default StreamPlus<LongFuncList> segmentBetween(
            LongPredicate      startCondition,
            LongPredicate      endCondition,
            IncompletedSegment incompletedSegment) {
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
     **/
    public default StreamPlus<LongFuncList> segmentBetween(
            LongPredicate startCondition,
            LongPredicate endCondition,
            boolean       includeIncompletedSegment) {
        Objects.requireNonNull(endCondition);
        
        val splitr       = longStreamPlus().spliterator();
        val isSequence   = false;
        val spliterator  = new Spliterators.AbstractSpliterator<LongFuncList>(splitr.estimateSize(), 0) {
            LongFuncListBuilder eachListBuilder = null;
            boolean             hasNewList      = false;
            @Override
            public boolean tryAdvance(Consumer<? super LongFuncList> consumer) {
                boolean hasThis;
                do {
                    hasThis = splitr.tryAdvance((LongConsumer)(eachValue -> {
                        if ((eachListBuilder == null) && startCondition.test(eachValue)) {
                            eachListBuilder = LongFuncList.newBuilder();
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
                } while(hasThis && !hasNewList);
                
                if (hasNewList) {
                    hasNewList = false;
                    return true;
                }
                
                if (includeIncompletedSegment && (eachListBuilder != null)) {
                    val eachList = eachListBuilder.build();
                    eachListBuilder = LongFuncList.newBuilder();
                    val useThis  = !eachList.isEmpty();
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
