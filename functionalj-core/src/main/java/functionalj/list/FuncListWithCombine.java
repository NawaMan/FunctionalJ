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

import static functionalj.list.AsFuncListHelper.funcListOf;
import static functionalj.list.FuncList.deriveFrom;
import java.util.List;
import java.util.function.BiFunction;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface FuncListWithCombine<DATA> extends AsFuncList<DATA> {
    
    /**
     * Concatenate the given head stream in front of this stream.
     */
    public default FuncList<DATA> prependWith(List<DATA> head) {
        val funcList = funcListOf(this);
        return FuncList.concat(FuncList.from(head), funcList);
    }
    
    /**
     * Concatenate the given tail stream to this stream.
     */
    public default FuncList<DATA> appendWith(List<DATA> tail) {
        val funcList = funcListOf(this);
        return FuncList.concat(funcList, FuncList.from(tail));
    }
    
    /**
     * Merge this with another stream by alternatively picking value from the each stream.
     * If one stream ended before another one, the rest of the value will be appended.
     *
     * For an example: &lt;br&gt;
     *   This list:    [A, B, C] &lt;br&gt;
     *   Another list: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Result list:  [A, 1, B, 2, C, 3, 4, 5] &lt;br&gt;
     */
    public default FuncList<DATA> mergeWith(List<DATA> anotherList) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mergeWith(anotherList.stream()));
    }
    
    // -- Zip --
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * The combination stops when any of the stream ended.
     *
     * For an example: &lt;br&gt;
     *   This list:    [A, B, C] &lt;br&gt;
     *   Another list: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Result list:  [(A, 1), (B, 2), (C, 3)] &lt;br&gt;
     */
    public default <ANOTHER> FuncList<Tuple2<DATA, ANOTHER>> zipWith(List<ANOTHER> anotherList) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.zipWith(anotherList.stream()));
    }
    
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     *
     * For an example with ZipWithOption.AllowUnpaired: &lt;br&gt;
     *   This list:    [A, B, C] &lt;br&gt;
     *   Another list: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Result list:  [(A, 1), (B, 2), (C, 3), (null, 4), (null, 5)] &lt;br&gt;
     */
    public default <ANOTHER> FuncList<Tuple2<DATA, ANOTHER>> zipWith(List<ANOTHER> anotherList, ZipWithOption option) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.zipWith(anotherList.stream(), option));
    }
    
    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * The combination stops when any of the stream ended.
     *
     * For an example: &lt;br&gt;
     *   This list:    [A, B, C] &lt;br&gt;
     *   Another list: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Combinator:   (v1,v2) -&gt; v1 + "-" + v2
     *   Result list:  [A-1, B-2, C-3] &lt;br&gt;
     */
    public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(List<ANOTHER> anotherList, BiFunction<DATA, ANOTHER, TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.zipWith(anotherList.stream(), combinator));
    }
    
    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     *
     * For an example with ZipWithOption.AllowUnpaired: &lt;br&gt;
     *   This list:    [A, B, C] &lt;br&gt;
     *   Another list: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Combinator:   (v1,v2) -&gt; v1 + "-" + v2
     *   Result list:  [A-1, B-2, C-3, null-4, null-5] &lt;br&gt;
     */
    public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(List<ANOTHER> anotherList, ZipWithOption option, BiFunction<DATA, ANOTHER, TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.zipWith(anotherList.stream(), option, combinator));
    }
    
    /**
     * Create a new stream by choosing value from each stream using the selector.
     * The value from the longer stream is automatically used after the shorter stream ended.
     *
     * For an example: &lt;br&gt;
     *   This stream:    [10, 1, 9, 2] &lt;br&gt;
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] &lt;br&gt;
     *   Selector:       (v1,v2) -&gt; v1 &gt; v2 &lt;br&gt;
     *   Result stream:  [10, 5, 9, 5]
     */
    public default FuncList<DATA> choose(List<DATA> anotherFuncList, BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.choose(anotherFuncList.stream(), selectThisNotAnother));
    }
    
    /**
     * Create a new stream by choosing value from each stream using the selector.
     * The value from the longer stream is automatically used after the shorter stream ended.
     *
     * For an example: &lt;br&gt;
     *   This stream:    [10, 1, 9, 2] &lt;br&gt;
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] &lt;br&gt;
     *   Selector:       (v1,v2) -&gt; v1 &gt; v2 &lt;br&gt;
     *   Result stream:  [10, 5, 9, 5]
     */
    public default FuncList<DATA> choose(List<DATA> anotherFuncList, ZipWithOption option, BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.choose(anotherFuncList.stream(), option, selectThisNotAnother));
    }
}
