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
package functionalj.streamable.doublestreamable;

import static functionalj.streamable.doublestreamable.DoubleStreamable.deriveFrom;
import static functionalj.streamable.doublestreamable.DoubleStreamable.deriveToDouble;
import static functionalj.streamable.doublestreamable.DoubleStreamable.deriveToObj;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleUnaryOperator;

import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.Streamable;
import lombok.val;


public interface DoubleStreamableWithReshape extends AsDoubleStreamable {
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * The last portion may be shorter.
     **/
    public default Streamable<DoubleStreamPlus> segmentSize(int count) {
        return deriveToObj(this, stream -> stream.segmentSize(count));
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
    public default Streamable<DoubleStreamPlus> segmentSize(
            int     count,
            boolean includeTail) {
        return deriveToObj(this, stream -> stream.segmentSize(count, includeTail));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default Streamable<DoubleStreamPlus> segment(DoublePredicate startCondition) {
        return deriveToObj(this, stream -> stream.segment(startCondition));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    public default Streamable<DoubleStreamPlus> segment(
            DoublePredicate startCondition,
            boolean         includeIncompletedSegment) {
        return deriveToObj(this, stream -> stream.segment(startCondition, includeIncompletedSegment));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true.
     * The tail sub stream will always be included.
     */
    public default Streamable<DoubleStreamPlus> segment(
            DoublePredicate startCondition,
            DoublePredicate endCondition) {
        return deriveToObj(this, stream -> stream.segment(startCondition, endCondition));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default Streamable<DoubleStreamPlus> segment(
            DoublePredicate startCondition,
            DoublePredicate endCondition,
            boolean         includeIncompletedSegment) {
        return deriveToObj(this, stream -> stream.segment(startCondition, endCondition, includeIncompletedSegment));
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     * 
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     */
    public default Streamable<DoubleStreamPlus> segmentSize(DoubleFunction<Integer> segmentSize) {
        return deriveToObj(this, stream -> stream.segmentSize(segmentSize));
    }
    
    /** Combine the current value with the one before it using then combinator everytime the condition to collapse is true. */
    public default DoubleStreamable collapseWhen(
            DoublePredicate      conditionToCollapse,
            DoubleBinaryOperator concatFunc) {
        return deriveFrom(this, stream -> stream.collapseWhen(conditionToCollapse, concatFunc));
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default DoubleStreamable collapseSize(
            DoubleToIntFunction segmentSize,
            DoubleBinaryOperator    combinator) {
//        return deriveFrom(this, stream -> stream.ccollapseSize(segmentSize, combinator));
        return null;
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     * 
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default DoubleStreamable collapseSize(
            DoubleFunction<Integer> segmentSize,
            DoubleUnaryOperator     mapper,
            DoubleBinaryOperator    combinator) {
        return deriveToDouble(this, stream -> stream.collapseSize(segmentSize, mapper, combinator));
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     *
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default <TARGET> Streamable<TARGET> collapseSize(
            DoubleFunction<Integer>               segmentSize,
            DoubleFunction<TARGET>                mapper,
            BiFunction<TARGET, TARGET, TARGET> combinator) {
        return deriveToObj(this, stream -> stream.collapseSize(segmentSize, mapper, combinator));
    }
    
    //-- More - then StreamPlus --
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> Streamable<DoubleFuncList> segmentByPercentiles(int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble(i -> i).toImmutableList();
        return segmentByPercentiles(percentileList).streamable();
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> Streamable<DoubleFuncList> segmentByPercentiles(double ... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(percentileList).streamable();
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(DoubleFuncList percentiles) {
        val list = doubleStreamPlus().sorted().toImmutableList();
        return DoubleStreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<DoubleFuncList> segmentByPercentiles(
            DoubleFunction<T> mapper,
            int ...           percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble(i -> i).toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
            DoubleFunction<T> mapper,
            Comparator<T>     comparator,
            int ...           percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble(i -> i).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<DoubleFuncList> segmentByPercentiles(
            DoubleFunction<T> mapper,
            double ...        percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
            DoubleFunction<T> mapper,
            Comparator<T>     comparator,
            double ...        percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<DoubleFuncList> segmentByPercentiles(
            DoubleFunction<T> mapper,
            DoubleFuncList    percentiles) {
        val list = doubleStreamPlus().sortedBy(mapper).toImmutableList();
        return DoubleStreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<DoubleFuncList> segmentByPercentiles(
            DoubleFunction<T> mapper,
            Comparator<T>     comparator,
            DoubleFuncList    percentiles) {
        val list = doubleStreamPlus().sortedBy(mapper, comparator).toImmutableList();
        return DoubleStreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
}
