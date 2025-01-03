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
package functionalj.list.intlist;

import static functionalj.list.FuncList.deriveFrom;
import static functionalj.list.intlist.AsIntFuncListHelper.funcListOf;
import java.util.Comparator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Sequential;
import lombok.val;

public interface IntFuncListWithSegment extends AsIntFuncList {
    
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
    public default FuncList<IntFuncList> segment(int count) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.segment(count));
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return 0,
     *   the value will be ignored.
     */
    public default FuncList<IntFuncList> segment(IntUnaryOperator segmentSize) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.segment(segmentSize));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default FuncList<IntFuncList> segmentWhen(IntPredicate startCondition) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.segmentWhen(startCondition));
    }
    
    /**
     * Segment the stream into sub stream starting the element after the precondition is true.
     */
    @Sequential
    public default FuncList<IntFuncList> segmentAfter(IntPredicate endCondition) {
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
    public default FuncList<IntFuncList> segmentBetween(IntPredicate startCondition, IntPredicate endCondition) {
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
    public default FuncList<IntFuncList> segmentBetween(IntPredicate startCondition, IntPredicate endCondition, boolean includeIncompletedSegment) {
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
    public default FuncList<IntFuncList> segmentBetween(IntPredicate startCondition, IntPredicate endCondition, IncompletedSegment incompletedSegment) {
        val includeTail = (incompletedSegment == IncompletedSegment.included);
        return deriveFrom(this, stream -> stream.segmentBetween(startCondition, endCondition, includeTail));
    }
    
    // -- segmentByPercentiles --
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(int... percentiles) {
        return IntFuncListHelper.segmentByPercentiles(this, IntFuncList.of(percentiles).mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(IntFuncList percentiles) {
        return IntFuncListHelper.segmentByPercentiles(this, percentiles.mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(double... percentiles) {
        return IntFuncListHelper.segmentByPercentiles(this, DoubleFuncList.of(percentiles));
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(DoubleFuncList percentiles) {
        return IntFuncListHelper.segmentByPercentiles(this, percentiles);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, double... percentiles) {
        val percentileList = DoubleFuncList.of(percentiles);
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, int... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, Comparator<T> comparator, int... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, Comparator<T> comparator, double... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, IntFuncList percentiles) {
        val list = intStreamPlus().sortedBy(mapper).toImmutableList();
        return IntFuncListHelper.segmentByPercentiles(list, percentiles.mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T extends Comparable<? super T>> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, DoubleFuncList percentiles) {
        val list = intStreamPlus().sortedBy(mapper).toImmutableList();
        return IntFuncListHelper.segmentByPercentiles(list, percentiles);
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, Comparator<T> comparator, IntFuncList percentiles) {
        val list = intStreamPlus().sortedBy(mapper, comparator).toImmutableList();
        return IntFuncListHelper.segmentByPercentiles(list, percentiles.mapToDouble());
    }
    
    /**
     * Split the stream into segment based on the given percentiles. *
     */
    public default <T> FuncList<IntFuncList> segmentByPercentiles(IntFunction<T> mapper, Comparator<T> comparator, DoubleFuncList percentiles) {
        val list = intStreamPlus().sortedBy(mapper, comparator).toImmutableList();
        return IntFuncListHelper.segmentByPercentiles(list, percentiles);
    }
}
