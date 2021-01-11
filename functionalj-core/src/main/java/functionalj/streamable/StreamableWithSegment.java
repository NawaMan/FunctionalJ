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
package functionalj.streamable;

import static functionalj.streamable.Streamable.deriveFrom;
import static functionalj.streamable.Streamable.deriveToObj;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Sequential;
import lombok.val;


public interface StreamableWithSegment<DATA> extends AsStreamable<DATA> {
    
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
    @Sequential
    public default Streamable<FuncList<DATA>> segment(int count) {
        return deriveToObj(this, stream -> stream.segment(count));
    }
    /**
     * Create a stream of sub-stream which size is derived from the value.
     *
     * If the segmentSize function return 0,
     *   the value will be ignored.
     */
    public default Streamable<FuncList<DATA>> segment(ToIntFunction<DATA> segmentSize) {
        return deriveToObj(this, stream -> stream.segment(segmentSize));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    @Sequential
    public default Streamable<FuncList<DATA>> segmentWhen(Predicate<DATA> startCondition) {
        return deriveFrom(this, stream -> stream.segmentWhen(startCondition));
    }
    
    /** Segment the stream into sub stream starting the element after the precondition is true. */
    @Sequential
    public default Streamable<FuncList<DATA>> segmentAfter(Predicate<? super DATA> endCondition) {
        return deriveFrom(this, stream -> stream.segmentAfter(endCondition));
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
    public default Streamable<FuncList<DATA>> segmentBetween(
            Predicate<DATA> startCondition,
            Predicate<DATA> endCondition) {
        return deriveToObj(this, stream -> stream.segmentBetween(startCondition, endCondition));
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
    public default Streamable<FuncList<DATA>> segmentBetween(
            Predicate<DATA> startCondition,
            Predicate<DATA> endCondition,
            boolean         includeIncompletedSegment) {
        return deriveToObj(this, stream -> stream.segmentBetween(startCondition, endCondition, includeIncompletedSegment));
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
    public default Streamable<FuncList<DATA>> segmentBetween(
            Predicate<DATA> startCondition,
            Predicate<DATA> endCondition,
            IncompletedSegment incompletedSegment) {
        return deriveToObj(this, stream -> stream.segmentBetween(startCondition, endCondition, incompletedSegment));
    }
    
    //-- More - then StreamPlus --
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> Streamable<FuncList<DATA>> segmentByPercentiles(int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
        return segmentByPercentiles(percentileList).streamable();
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> Streamable<FuncList<DATA>> segmentByPercentiles(double ... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(percentileList).streamable();
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(IntFuncList percentiles) {
        val list = streamPlus().sorted().toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles.mapToDouble());
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(DoubleFuncList percentiles) {
        val list = streamPlus().sorted().toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            int ...                   percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            int ...                   percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToDouble().toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            double ...                percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            double ...                percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            IntFuncList               percentiles) {
        val list = streamPlus().sortedBy(mapper).toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles.mapToDouble());
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            IntFuncList               percentiles) {
        val list = streamPlus().sortedBy(mapper, comparator).toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles.mapToDouble());
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            DoubleFuncList            percentiles) {
        val list = streamPlus().sortedBy(mapper).toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            DoubleFuncList            percentiles) {
        val list = streamPlus().sortedBy(mapper, comparator).toImmutableList();
        return StreamableHelper.segmentByPercentiles(list, percentiles);
    }
    
}
