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
package functionalj.list.doublelist;

import java.util.Objects;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.stream.StreamSupport;

import functionalj.list.FuncList;
import functionalj.stream.StreamPlus;
import lombok.val;

public interface DoubleFuncListWithSegment extends AsDoubleFuncList {
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * 
     * Note:
     * <ul>
     * <li>The last portion may be shorter.</li>
     * <li>If the count is less than or equal to 0, an empty stream is return.</li>
     * </ul>
     * 
     * @param count  the element count of the sub stream.
     **/
    public default FuncList<DoubleFuncList> segment(int count) {
        if (count <= 0) {
            return FuncList.empty();
        }
        if (count <= 1) {
            return asDoubleFuncList().mapToObj(each -> DoubleFuncList.of(each));
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
    public default FuncList<DoubleFuncList> segment(DoubleToIntFunction segmentSize) {
        Objects.requireNonNull(segmentSize);
        
        return FuncList.deriveFrom(this, streamPlus -> {
            val splitr      = streamPlus.spliterator();
            val isSequence  = false;
            val spliterator = new Spliterators.AbstractSpliterator<DoubleFuncList>(splitr.estimateSize(), 0) {
                int count = -1;
                @Override
                public boolean tryAdvance(Consumer<? super DoubleFuncList> consumer) {
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
                        consumer.accept(eachList);
                    }
                    return hasThis;
                }
            };
            return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
        });
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default FuncList<DoubleFuncList> segmentWhen(DoublePredicate startCondition) {
        Objects.requireNonNull(startCondition);
        
        return FuncList.deriveFrom(this, streamPlus -> {
            val splitr       = streamPlus.spliterator();
            val isSequence   = false;
            val spliterator  = new Spliterators.AbstractSpliterator<DoubleFuncList>(splitr.estimateSize(), 0) {
                DoubleFuncListBuilder eachListBuilder = DoubleFuncList.newBuilder();
                boolean               hasNewList      = false;
                @Override
                public boolean tryAdvance(Consumer<? super DoubleFuncList> consumer) {
                    boolean hasThis;
                    do {
                        hasThis = splitr.tryAdvance((DoubleConsumer)(eachValue -> {
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
                    } while(hasThis && !hasNewList);
                    if (hasNewList) {
                        hasNewList = false;
                        return true;
                    }
                    
                    val eachList = eachListBuilder.build();
                    eachListBuilder = DoubleFuncList.newBuilder();
                    val useThis  = !eachList.isEmpty();
                    if (useThis) {
                        consumer.accept(eachList);
                    }
                    return hasThis || useThis;
                }
            };
            return StreamPlus.from(StreamSupport.stream(spliterator, isSequence));
        });
    }
    
//    //-- More - then StreamPlus --
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
//            int ... percentiles) {
//        return DoubleFuncListHelper.segmentByPercentiles(, IntFuncList.of(percentiles).mapToDouble());
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
//            double ... percentiles) {
//        return DoubleFuncListHelper.segmentByPercentiles(this, DoubleFuncList.of(percentiles));
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
//            DoubleFuncList percentiles) {
//        return DoubleFuncListHelper.segmentByPercentiles(this, percentiles);
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T extends Comparable<? super T>> FuncList<DoubleFuncList> segmentByPercentiles(
//            DoubleFunction<T> mapper,
//            double ...        percentiles) {
//        val percentileList = DoubleFuncList.of(percentiles);
//        return segmentByPercentiles(mapper, percentileList);
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T extends Comparable<? super T>> FuncList<DoubleFuncList> segmentByPercentiles(
//            DoubleFunction<T> mapper,
//            int ...           percentiles) {
//        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
//        return segmentByPercentiles(mapper, percentileList);
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
//            DoubleFunction<T> mapper,
//            Comparator<T>     comparator,
//            int ...           percentiles) {
//        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
//        return segmentByPercentiles(mapper, comparator, percentileList);
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
//            DoubleFunction<T> mapper,
//            Comparator<T>     comparator,
//            double ...        percentiles) {
//        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
//        return segmentByPercentiles(mapper, comparator, percentileList);
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T extends Comparable<? super T>> FuncList<DoubleFuncList> segmentByPercentiles(
//            DoubleFunction<T> mapper,
//            DoubleFuncList    percentiles) {
//        val list = doubleStreamPlus().sortedBy(mapper).toImmutableList();
//        return DoubleFuncListHelper.segmentByPercentiles(list, percentiles);
//    }
//    
//    /** Split the stream into segment based on the given percentiles. **/
//    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
//            DoubleFunction<T>  mapper,
//            Comparator<T>      comparator,
//            DoubleFuncList     percentiles) {
//        val list = doubleStreamPlus().sortedBy(mapper, comparator).toImmutableList();
//        return DoubleFuncListHelper.segmentByPercentiles(list, percentiles);
//    }
    
}
