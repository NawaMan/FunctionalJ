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
import static functionalj.stream.StreamPlusHelper.useIterator;
import static functionalj.stream.ZipWithOption.AllowUnpaired;

import java.util.stream.Stream;

import functionalj.function.Func2;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusWithCombine<DATA> extends AsStreamPlus<DATA> {
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> concatWith(
            Stream<DATA> tail) {
        return StreamPlus.concat(
                StreamPlus.of(this), 
                StreamPlus.of(tail)
               )
               .flatMap(s -> (StreamPlus<DATA>)s);
    }
    
    public default StreamPlus<DATA> merge(Stream<DATA> anotherStream) {
        // TODO - Check to see if rawIterator is still needed.
        val thisStream = stream();
        val iteratorA  = StreamPlusHelper.rawIterator(thisStream);
        val iteratorB  = StreamPlusHelper.rawIterator(anotherStream);
        
        val resultStream 
                = StreamPlusHelper
                .doMerge(iteratorA, iteratorB);
        
        resultStream
                .onClose(()->{
                    funcUnit0(()->thisStream   .close()).runCarelessly();
                    funcUnit0(()->anotherStream.close()).runCarelessly();
                });
        return resultStream;
    }
    
    //-- Zip --
    
    public default <B, TARGET> StreamPlus<TARGET> combineWith(Stream<B> anotherStream, Func2<DATA, B, TARGET> combinator) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth)
                .map(combinator::applyTo);
    }
    public default <B, TARGET> StreamPlus<TARGET> combineWith(Stream<B> anotherStream, ZipWithOption option, Func2<DATA, B, TARGET> combinator) {
        return zipWith(anotherStream, option)
                .map(combinator::applyTo);
    }
    
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, Tuple2::of);
    }
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream, ZipWithOption option) {
        return zipWith(anotherStream, option, Tuple2::of);
    }
    
    public default <B, C> StreamPlus<C> zipWith(Stream<B> anotherStream, Func2<DATA, B, C> merger) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, merger);
    }
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <B, C> StreamPlus<C> zipWith(Stream<B> anotherStream, ZipWithOption option, Func2<DATA, B, C> merger) {
        return useIterator(this, iteratorA -> {
            return useIterator(StreamPlus.from(anotherStream), iteratorB -> {
                return StreamPlusHelper.doZipWith(option, merger, iteratorA, iteratorB);
            });
        });
    }
    
    public default StreamPlus<DATA> choose(Stream<DATA> anotherStream, Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return zipWith(anotherStream, AllowUnpaired)
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
