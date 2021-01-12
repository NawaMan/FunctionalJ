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
package functionalj.stream;

import static functionalj.function.FuncUnit0.funcUnit0;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static functionalj.stream.ZipWithOption.RequireBoth;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.result.NoMoreResultException;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.Tuple2;
import lombok.val;


public interface StreamPlusWithCombine<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    public <TARGET> StreamPlus<TARGET> derive(Func1<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public IntStreamPlus deriveToInt(Func1<StreamPlus<DATA>, IntStream> action);
    
    public <TARGET> StreamPlus<TARGET> deriveToObj(Func1<StreamPlus<DATA>, Stream<TARGET>> action);
    
    
    /** Concatenate the given head stream in front of this stream. */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> prependWith(Stream<DATA> head) {
        return StreamPlus.concat(
                StreamPlus.of(head),
                StreamPlus.of(this)
               )
               .flatMap(s -> (StreamPlus<DATA>)s);
    }
    
    /** Concatenate the given tail stream to this stream. */
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> appendWith(Stream<DATA> tail) {
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
     */
    public default StreamPlus<DATA> mergeWith(Stream<DATA> anotherStream) {
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
     */
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(
            Stream<ANOTHER>                   anotherStream,
            BiFunction<DATA, ANOTHER, TARGET> combinator) {
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
     */
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <B, C> StreamPlus<C> zipWith(
            Stream<B>              anotherStream,
            ZipWithOption          option,
            BiFunction<DATA, B, C> combinator) {
        val iteratorA = streamPlus().iterator();
        val iteratorB = StreamPlus.from(anotherStream).iterator();
        return StreamPlusHelper.doZipWith(option, combinator, iteratorA, iteratorB);
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
    public default StreamPlus<DATA> choose(
            Stream<DATA>                    anotherStream,
            BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        return choose(anotherStream, AllowUnpaired, selectThisNotAnother);
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
    public default StreamPlus<DATA> choose(
            Stream<DATA>                    anotherStream,
            ZipWithOption                   option,
            BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        val iteratorA = this.streamPlus().iterator();
        val iteratorB = StreamPlus.from(anotherStream).iterator();
        val iterator = new Iterator<DATA>() {
            private boolean hasNextA;
            private boolean hasNextB;
            
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth)
                        ? (hasNextA && hasNextB)
                        : (hasNextA || hasNextB);
            }
            public DATA next() {
                val nextA = hasNextA ? iteratorA.next() : null;
                val nextB = hasNextB ? iteratorB.next() : null;
                if (hasNextA && hasNextB) {
                    boolean selectA = selectThisNotAnother.apply(nextA, nextB);
                    return selectA ? nextA : nextB;
                }
                if (hasNextA) {
                    return nextA;
                }
                if (hasNextB) {
                    return nextB;
                }
                throw new NoMoreResultException();
            }
        };
        val iterable = new Iterable<DATA>() {
            @Override
            public Iterator<DATA> iterator() {
                return iterator;
            }
          
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
}
