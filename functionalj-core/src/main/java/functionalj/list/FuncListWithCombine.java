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

import java.util.function.BiFunction;

import functionalj.stream.ZipWithOption;
import functionalj.streamable.AsStreamable;
import functionalj.tuple.Tuple2;

public interface FuncListWithCombine<DATA> extends AsStreamable<DATA> {
    
    
    /** Concatenate the given head stream in front of this stream. */
    public default FuncList<DATA> prependWith(FuncList<DATA> head) {
        return deriveFrom(this, stream -> stream.prependWith(head.stream()));
    }

    /** Concatenate the given tail stream to this stream. */
    public default FuncList<DATA> appendWith(FuncList<DATA> tail) {
        return deriveFrom(this, stream -> stream.appendWith(tail.streamPlus()));
    }
    
    /**
     * Merge this with another stream by alternatively picking value from the each stream.
     * If one stream ended before another one, the rest of the value will be appended.
     * 
     * For an example: <br>
     *   This list:    [A, B, C] <br>
     *   Another list: [1, 2, 3, 4, 5] <br>
     *   Result list:  [A, 1, B, 2, C, 3, 4, 5] <br>
     */
    public default FuncList<DATA> mergeWith(FuncList<DATA> anotherList) {
        return deriveFrom(this, stream -> stream.mergeWith(anotherList.stream()));
    }
    
    //-- Zip --
    
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * The combination stops when any of the stream ended.
     * 
     * For an example: <br>
     *   This list:    [A, B, C] <br>
     *   Another list: [1, 2, 3, 4, 5] <br>
     *   Result list:  [(A, 1), (B, 2), (C, 3)] <br>
     */
    public default <B> FuncList<Tuple2<DATA,B>> zipWith(FuncList<B> anotherList) {
        return deriveFrom(this, stream -> stream.zipWith(anotherList.stream()));
    }
    
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This list:    [A, B, C] <br>
     *   Another list: [1, 2, 3, 4, 5] <br>
     *   Result list:  [(A, 1), (B, 2), (C, 3), (null, 4), (null, 5)] <br>
     */
    public default <B> FuncList<Tuple2<DATA,B>> zipWith(
            FuncList<B>   anotherList,
            ZipWithOption option) {
        return deriveFrom(this, stream -> stream.zipWith(anotherList.stream(), option));
    }
    
    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * The combination stops when any of the stream ended.
     * 
     * For an example: <br>
     *   This list:    [A, B, C] <br>
     *   Another list: [1, 2, 3, 4, 5] <br>
     *   Combinator:   (v1,v2) -> v1 + "-" + v2
     *   Result list:  [A-1, B-2, C-3] <br>
     */
    public default <B, C> FuncList<C> zipWith(
            FuncList<B>            anotherList, 
            BiFunction<DATA, B, C> combinator) {
        return deriveFrom(this, stream -> stream.zipWith(anotherList.stream(), combinator));
    }
    
    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This list:    [A, B, C] <br>
     *   Another list: [1, 2, 3, 4, 5] <br>
     *   Combinator:   (v1,v2) -> v1 + "-" + v2
     *   Result list:  [A-1, B-2, C-3, null-4, null-5] <br>
     */
    public default <B, C> FuncList<C> zipWith(
            FuncList<B>            anotherList, 
            ZipWithOption          option,
            BiFunction<DATA, B, C> combinator) {
        return deriveFrom(this, stream -> stream.zipWith(anotherList.stream(), option, combinator));
    }
    
    /**
     * Create a new stream by choosing value from each stream using the selector.
     * The value from the longer stream is automatically used after the shorter stream ended.
     *
     * For an example: <br>
     *   This stream:    [10, 1, 9, 2] <br>
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] <br>
     *   Selector:       (v1,v2) -> v1 > v2 <br>
     *   Result stream:  [10, 5, 9, 5]
     */
    public default FuncList<DATA> choose(
            FuncList<DATA>                  anotherStreamable,
            BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        return deriveFrom(this, stream -> stream.choose(anotherStreamable.stream(), selectThisNotAnother));
    }
    
    /**
     * Create a new stream by choosing value from each stream using the selector.
     * The value from the longer stream is automatically used after the shorter stream ended.
     *
     * For an example: <br>
     *   This stream:    [10, 1, 9, 2] <br>
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] <br>
     *   Selector:       (v1,v2) -> v1 > v2 <br>
     *   Result stream:  [10, 5, 9, 5]
     */
    public default FuncList<DATA> choose(
            FuncList<DATA>                  anotherStreamable,
            ZipWithOption                   option,
            BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        return deriveFrom(this, stream -> stream.choose(anotherStreamable.stream(), option, selectThisNotAnother));
    }
    
}
