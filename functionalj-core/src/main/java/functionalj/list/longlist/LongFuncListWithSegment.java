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
package functionalj.list.longlist;

import static functionalj.list.FuncList.deriveFrom;
import static functionalj.list.longlist.AsLongFuncListHelper.funcListOf;
import java.util.Comparator;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToIntFunction;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Sequential;
import lombok.val;

public interface LongFuncListWithSegment extends AsLongFuncList {
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     *
     * Note:
     * &lt;ul&gt;
     * &lt;li&gt;The last portion may be shorter.&lt;/li&gt;
     * &lt;li&gt;If the count is less than or equal to 0, an empty stream is return.&lt;/li&gt;
     * &lt;/ul&gt;
     *
     * @param count  the element count of the sub stream.
     */
    public default FuncList<LongFuncList> segment(int count) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.segment(count));
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return 0,
     *   the value will be ignored.
     */
    public default FuncList<LongFuncList> segment(LongToIntFunction segmentSize) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.segment(segmentSize));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default FuncList<LongFuncList> segmentWhen(LongPredicate startCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.segmentWhen(startCondition));
    }
    
    /**
     * Segment the stream into sub stream starting the element after the precondition is true.
     */
    @Sequential
    public default FuncList<LongFuncList> segmentAfter(LongPredicate endCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.segmentAfter(endCondition));
    }
    
    /**
     * Segment the stream into sub stream
     *   starting when the start condition is true
     *   and ending when the end condition is true
     *   -- both inclusively.
     *
     * Note: this method will include the last sub stream
     *   even if the end condition is never been true before the stream ended.
     *
     * @param startCondition  the condition to start the sub stream
     * @param endCondition    the condition to end the sub stream
     */
    public default FuncList<LongFuncList> segmentBetween(LongPredicate startCondition, LongPredicate endCondition) {
        return deriveFrom(this, stream -> stream.segmentBetween(startCondition, endCondition));
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
    public default FuncList<LongFuncList> segmentBetween(LongPredicate startCondition, LongPredicate endCondition, boolean includeIncompletedSegment) {
        return deriveFrom(this, stream -> stream.segmentBetween(startCondition, endCondition, includeIncompletedSegment));
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
    public default FuncList<LongFuncList> segmentBetween(LongPredicate startCondition, LongPredicate endCondition, IncompletedSegment incompletedSegment) {
        val includeTail = (incompletedSegment == IncompletedSegment.included);
        return deriveFrom(this, stream -> stream.segmentBetween(startCondition, endCondition, includeTail));
    }
    
    // -- segmentByPercentiles --
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(int... percentiles) {
        return LongFuncListHelper.segmentByPercentiles(this, IntFuncList.of(percentiles).mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(IntFuncList percentiles) {
        return LongFuncListHelper.segmentByPercentiles(this, percentiles.mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(double... percentiles) {
        return LongFuncListHelper.segmentByPercentiles(this, DoubleFuncList.of(percentiles));
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(DoubleFuncList percentiles) {
        return LongFuncListHelper.segmentByPercentiles(this, percentiles);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, double... percentiles) {
        val percentileList = DoubleFuncList.of(percentiles);
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, int... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, Comparator<T> comparator, int... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, Comparator<T> comparator, double... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, LongFuncList percentiles) {
        val list = longStreamPlus().sortedBy(mapper).toImmutableList();
        return LongFuncListHelper.segmentByPercentiles(list, percentiles.mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, DoubleFuncList percentiles) {
        val list = longStreamPlus().sortedBy(mapper).toImmutableList();
        return LongFuncListHelper.segmentByPercentiles(list, percentiles);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, Comparator<T> comparator, LongFuncList percentiles) {
        val list = longStreamPlus().sortedBy(mapper, comparator).toImmutableList();
        return LongFuncListHelper.segmentByPercentiles(list, percentiles.mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<LongFuncList> segmentByPercentiles(LongFunction<T> mapper, Comparator<T> comparator, DoubleFuncList percentiles) {
        val list = longStreamPlus().sortedBy(mapper, comparator).toImmutableList();
        return LongFuncListHelper.segmentByPercentiles(list, percentiles);
    }
}
