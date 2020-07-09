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

import static functionalj.function.FuncUnit0.funcUnit0;
import static functionalj.stream.ZipWithOption.RequireBoth;

import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusWithCombine<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    public <TARGET> StreamPlus<TARGET> derive(Func1<StreamPlus<DATA>, StreamPlus<TARGET>> action);
    
    public IntStreamPlus deriveToInt(Func1<StreamPlus<DATA>, IntStreamPlus> action);
    
    public <TARGET> StreamPlus<TARGET> deriveToObj(Func1<StreamPlus<DATA>, StreamPlus<TARGET>> action);
    
    
    /**
     * Concatenate the given tail stream to this stream.
     * 
     * @param tail  the tail stream.
     * @return      the combined stream.
     */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> concatWith(
            Stream<DATA> tail) {
        return StreamPlus.concat(
                StreamPlus.of(this), 
                StreamPlus.of(tail)
               )
               .flatMap(s -> (StreamPlus<DATA>)s);
    }
    
    /**
     * Merge this with another stream by alternatively picking value from the each stream.
     * If one stream ended before another one, the rest of the value will be appended.
     * 
     * For an example: <br>
     *   This stream:    [A, B, C] <br>
     *   Another stream: [1, 2, 3, 4, 5] <br>
     *   Result stream:  [A, 1, B, 2, C, 3, 4, 5] <br>
     * 
     * @param anotherStream  another stream.
     * @return               the merged stream.
     */
    public default StreamPlus<DATA> merge(Stream<DATA> anotherStream) {
        val streamPlus = streamPlus();
        val iteratorA  = streamPlus.iterator();
        val iteratorB  = StreamPlus.from(anotherStream).iterator();
        
        val resultStream 
                = StreamPlusHelper
                .doMerge(iteratorA, iteratorB);
        
        resultStream
                .onClose(()->{
                    funcUnit0(()->streamPlus   .close()).runCarelessly();
                    funcUnit0(()->anotherStream.close()).runCarelessly();
                });
        return resultStream;
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
     * 
     * @param anotherStream  another stream.
     * @return               the merged stream.
     */
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream) {
        return zipWith(anotherStream, RequireBoth, Tuple2::of);
    }
    
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This stream:    [A, B, C] <br>
     *   Another stream: [1, 2, 3, 4, 5] <br>
     *   Result stream:  [(A, 1), (B, 2), (C, 3), (null, 4), (null, 5)] <br>
     * 
     * @param anotherStream  another stream.
     * @param option         the zip option.
     * @return               the merged stream.
     */
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(
            Stream<B>     anotherStream,
            ZipWithOption option) {
        return zipWith(anotherStream, option, Tuple2::of);
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
     * 
     * @param anotherStream  another stream.
     * @param combinator     the combinator.
     * @return               the merged stream.
     */
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(
            Stream<ANOTHER> anotherStream,
            Func2<DATA, ANOTHER, TARGET> combinator) {
        return zipWith(anotherStream, RequireBoth, combinator);
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
     * 
     * @param anotherStream  another stream.
     * @param option         the zip option.
     * @param combinator     the combinator.
     * @return               the merged stream.
     */
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <B, C> StreamPlus<C> zipWith(
            Stream<B>         anotherStream, 
            ZipWithOption     option,
            Func2<DATA, B, C> combinator) {
        val iteratorA = streamPlus().iterator();
        val iteratorB = StreamPlus.from(anotherStream).iterator();
        return StreamPlusHelper.doZipWith(option, combinator, iteratorA, iteratorB);
    }
    
    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when any of the stream ended.
     * 
     * For an example: <br>
     *   This stream:    [10, 1, 9, 2] <br>
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] <br>
     *   Selector:       (v1,v2) -> v1 > v2
     *   Result stream:  [10, 5, 9, 5] <br>
     * 
     * @param anotherStream         another stream.
     * @param selectThisNotAnother  the selector.
     * @return                      the merged stream.
     */
    public default StreamPlus<DATA> choose(
            Stream<DATA>               anotherStream,
            Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return choose(anotherStream, RequireBoth, selectThisNotAnother);
    }
    
    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when both stream ended.
     * The value from the longer stream is automatically used after the shorter stream ended.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This stream:    [10, 1, 9, 2] <br>
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] <br>
     *   Selector:       (v1,v2) -> v1 > v2
     *   Result stream:  [10, 5, 9, 5, 5, 5, 5] <br>
     * 
     * @param anotherStream         another stream.
     * @param option                the zip option.
     * @param selectThisNotAnother  the selector.
     * @return                      the merged stream.
     */
    public default StreamPlus<DATA> choose(
            Stream<DATA>               anotherStream,
            ZipWithOption              option,
            Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return zipWith(anotherStream, option)
                .map(t -> {
                    val _1 = t._1();
                    val _2 = t._2();
                    if ((_1 != null) && _2 == null)
                        return _1;
                    if ((_1 == null) && _2 != null)
                        return _2;
                    if ((_1 == null) && _2 == null)
                        return null;
                    val which = selectThisNotAnother.applyTo(t);
                    return which ? _1 : _2;
                })
                .filterNonNull();
    }
    
}
