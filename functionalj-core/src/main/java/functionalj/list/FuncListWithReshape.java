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
package functionalj.list;

import static functionalj.list.FuncList.deriveFrom;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;
import functionalj.streamable.AsStreamable;

public interface FuncListWithReshape<DATA> extends AsStreamable<DATA> {
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * The last portion may be shorter.
     **/
    public default FuncList<StreamPlus<DATA>> segmentSize(int count) {
        return deriveFrom(this, stream -> stream.segmentSize(count));
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
    public default FuncList<StreamPlus<DATA>> segmentSize(
            int     count, 
            boolean includeTail) {
        return deriveFrom(this, stream -> stream.segmentSize(count, includeTail));
    }
    
    /**
     * Segment the stream into sub stream with the fix length of count.
     * Depending on the includeTail flag, the last sub stream may not be included if its length is not `count`.
     *
     * @param count        the element count of the sub stream.
     * @param includeTail  the option indicating if the last sub stream that does not have count element is to be included
     *                       as opposed to thrown away.
     * @return             the stream of sub stream.
     */
    public default FuncList<StreamPlus<DATA>> segmentSize(int count, IncompletedSegment incompletedSegment) {
        return segmentSize(count, (incompletedSegment == IncompletedSegment.included));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true.
     * The tail sub stream will always be included.
     */
    public default FuncList<StreamPlus<DATA>> segment(Predicate<DATA> startCondition) {
        return deriveFrom(this, stream -> stream.segment(startCondition));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    public default FuncList<StreamPlus<DATA>> segment(
            Predicate<DATA> startCondition, 
            boolean         includeIncompletedSegment) {
        return deriveFrom(this, stream -> stream.segment(startCondition, includeIncompletedSegment));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true. */
    public default FuncList<StreamPlus<DATA>> segment(Predicate<? super DATA> startCondition, IncompletedSegment incompletedSegment) {
        return deriveFrom(this, stream -> stream.segment(startCondition, incompletedSegment));
    }
    
    /**
     * Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true.
     * The tail sub stream will always be included.
     */
    public default FuncList<StreamPlus<DATA>> segment(
            Predicate<DATA> startCondition, 
            Predicate<DATA> endCondition) {
        return deriveFrom(this, stream -> stream.segment(startCondition, endCondition));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default FuncList<StreamPlus<DATA>> segment(
            Predicate<DATA> startCondition, 
            Predicate<DATA> endCondition, 
            boolean         includeIncompletedSegment) {
        return deriveFrom(this, stream -> stream.segment(startCondition, endCondition, includeIncompletedSegment));
    }
    
    /** Segment the stream into sub stream whenever the start condition is true and ended when the end condition is true. */
    public default FuncList<StreamPlus<DATA>> segment(
            Predicate<DATA> startCondition,
            Predicate<DATA> endCondition,
            IncompletedSegment incompletedSegment) {
        return deriveFrom(this, stream -> stream.segment(startCondition, endCondition, incompletedSegment));
    }
    
    /**
     * Create a stream of sub-stream which size is derived from the value.
     * 
     * If the segmentSize function return null, the value will be ignored.
     * If the segmentSize function return 0, an empty stream is returned.
     */
    public default FuncList<StreamPlus<DATA>> segmentSize(Func1<DATA, Integer> segmentSize) {
        return deriveFrom(this, stream -> stream.segmentSize(segmentSize));
    }
    
    /** Combine the current value with the one before it using then combinator everytime the condition to collapse is true. */
    public default FuncList<DATA> collapseWhen(
            Predicate<DATA>         conditionToCollapse, 
            Func2<DATA, DATA, DATA> concatFunc) {
        return deriveFrom(this, stream -> stream.collapseWhen(conditionToCollapse, concatFunc));
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * 
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default FuncList<DATA> collapseSize(
            Func1<DATA, Integer>    segmentSize, 
            Func2<DATA, DATA, DATA> combinator) {
        return deriveFrom(this, stream -> stream.collapseSize(segmentSize, combinator));
    }
    
    /**
     * Collapse the value of this stream together. Each sub stream size is determined by the segmentSize function.
     * The value is mapped using the mapper function before combined.
     * 
     * If the segmentSize function return null or 0, the value will be used as is (no collapse).
     */
    public default <TARGET> FuncList<TARGET> collapseSize(
            Func1<DATA, Integer>          segmentSize, 
            Func1<DATA, TARGET>           mapper, 
            Func2<TARGET, TARGET, TARGET> combinator) {
        return deriveFrom(this, stream -> stream.collapseSize(segmentSize, mapper, combinator));
    }
    
    //-- More - then StreamPlus --
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(int ... percentiles) {
        return FuncList.from(streamable().segmentByPercentiles(percentiles));
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(double ... percentiles) {
        return FuncList.from(streamable().segmentByPercentiles(percentiles));
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(IntFuncList percentiles) {
        return streamable().segmentByPercentiles(percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(DoubleFuncList percentiles) {
        return streamable().segmentByPercentiles(percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            int ...                   percentiles) {
        return streamable().segmentByPercentiles(mapper, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            int ...                   percentiles) {
        return streamable().segmentByPercentiles(mapper, comparator, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            double ...                percentiles) {
        return streamable().segmentByPercentiles(mapper, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            double ...                percentiles) {
        return streamable().segmentByPercentiles(mapper, comparator, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            IntFuncList               percentiles) {
        return streamable().segmentByPercentiles(mapper, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            IntFuncList               percentiles) {
        return streamable().segmentByPercentiles(mapper, comparator, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            DoubleFuncList            percentiles) {
        return streamable().segmentByPercentiles(mapper, percentiles);
    }
    
    /** Split the stream into segment based on the given percentiles. **/
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(
            Function<? super DATA, T> mapper,
            Comparator<T>             comparator,
            DoubleFuncList            percentiles) {
        return streamable().segmentByPercentiles(mapper, comparator, percentiles);
    }
    
}
