// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import functionalj.function.Func2;
import functionalj.tuple.Tuple2;

public interface StreamableWithCombine<DATA> extends AsStreamable<DATA> {
    
    /** Concatenate the given tail stream to this stream. */
    public default Streamable<DATA> concatWith(Streamable<DATA> tail) {
        return deriveFrom(this, stream -> stream.concatWith(tail.stream()));
    }
    
    /**
     * Merge this with another stream by alternatively picking value from the each stream.
     * If one stream ended before another one, the rest of the value will be appended.
     * 
     * For an example: <br>
     *   This stream:    [A, B, C] <br>
     *   Another stream: [1, 2, 3, 4, 5] <br>
     *   Result stream:  [A, 1, B, 2, C, 3, 4, 5] <br>
     */
    public default Streamable<DATA> merge(Streamable<DATA> anotherStreamable) {
        return deriveFrom(this, stream -> stream.merge(anotherStreamable.stream()));
    }
    
    //-- Zip --
    
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * The combination stops when any of the stream ended.
     * 
     * For an example: <br>
     *   This stream:    [A, B, C] <br>
     *   Another stream: [1, 2, 3, 4, 5] <br>
     *   Result stream:  [(A, 1), (B, 2), (C, 3)] <br>
     */
    public default <B> Streamable<Tuple2<DATA,B>> zipWith(Streamable<B> anotherStreamable) {
        return deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream()));
    }
    
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This stream:    [A, B, C] <br>
     *   Another stream: [1, 2, 3, 4, 5] <br>
     *   Result stream:  [(A, 1), (B, 2), (C, 3), (null, 4), (null, 5)] <br>
     */
    public default <B> Streamable<Tuple2<DATA,B>> zipWith(
            Streamable<B> anotherStreamable,
            ZipWithOption option) {
        return deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream(), option));
    }
    
    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * The combination stops when any of the stream ended.
     * 
     * For an example: <br>
     *   This stream:    [A, B, C] <br>
     *   Another stream: [1, 2, 3, 4, 5] <br>
     *   Combinator:     (v1,v2) -> v1 + "-" + v2
     *   Result stream:  [A-1, B-2, C-3] <br>
     */
    public default <B, C> Streamable<C> zipWith(
            Streamable<B>     anotherStreamable, 
            Func2<DATA, B, C> combinator) {
        return deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream(), combinator));
    }
    
    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This stream:    [A, B, C] <br>
     *   Another stream: [1, 2, 3, 4, 5] <br>
     *   Combinator:     (v1,v2) -> v1 + "-" + v2
     *   Result stream:  [A-1, B-2, C-3, null-4, null-5] <br>
     */
    public default <B, C> Streamable<C> zipWith(
            Streamable<B>     anotherStreamable, 
            ZipWithOption     option,
            Func2<DATA, B, C> combinator) {
        return deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream(), option, combinator));
    }
    
    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when any of the stream ended.
     * 
     * For an example: <br>
     *   This stream:    [10, 1, 9, 2] <br>
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] <br>
     *   Selector:       (v1,v2) -> v1 > v2 <br>
     *   Result stream:  [10, 5, 9, 5]
     */
    public default Streamable<DATA> choose(
            Streamable<DATA>           anotherStreamable,
            Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return deriveFrom(this, stream -> stream.choose(anotherStreamable.stream(), selectThisNotAnother));
    }
    
    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when both stream ended.
     * The value from the longer stream is automatically used after the shorter stream ended.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This stream:    [10, 1, 9, 2] <br>
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] <br>
     *   Selector:       (v1,v2) -> v1 > v2 <br>
     *   Result stream:  [10, 5, 9, 5, 5, 5, 5]
     */
    public default Streamable<DATA> choose(
            Streamable<DATA>           anotherStreamable,
            ZipWithOption              option,
            Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return deriveFrom(this, stream -> stream.choose(anotherStreamable.stream(), option, selectThisNotAnother));
    }
    
}
