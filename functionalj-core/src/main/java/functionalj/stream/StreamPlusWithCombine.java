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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import functionalj.result.NoMoreResultException;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusWithCombine<DATA> {

    public StreamPlus<DATA> streamPlus();

    /**
     * Concatenate the given head stream in front of this stream.
     */
    public default StreamPlus<DATA> prependWith(Stream<DATA> head) {
        return StreamPlus.concat(StreamPlus.from(head), streamPlus());
    }

    /**
     * Concatenate the given tail stream to this stream.
     */
    public default StreamPlus<DATA> appendWith(Stream<DATA> tail) {
        return StreamPlus.concat(streamPlus(), StreamPlus.from(tail));
    }

    /**
     * Merge this with another stream by alternatively picking value from the each stream.
     * If one stream ended before another one, the rest of the value will be appended.
     *
     * For an example: &lt;br&gt;
     *   This stream:    [A, B, C] &lt;br&gt;
     *   Another stream: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Result stream:  [A, 1, B, 2, C, 3, 4, 5] &lt;br&gt;
     */
    public default StreamPlus<DATA> mergeWith(Stream<DATA> anotherStream) {
        val streamPlus = streamPlus();
        val iteratorA = streamPlus.iterator();
        val iteratorB = StreamPlus.from(anotherStream).iterator();
        val resultStream = StreamPlusHelper.doMerge(iteratorA, iteratorB);
        resultStream.onClose(() -> {
            funcUnit0(() -> streamPlus.close()).runCarelessly();
            funcUnit0(() -> anotherStream.close()).runCarelessly();
        });
        return resultStream;
    }

    // -- Zip --
    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * The combination stops when any of the stream ended.
     *
     * For an example: &lt;br&gt;
     *   This stream:    [A, B, C] &lt;br&gt;
     *   Another stream: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Result stream:  [(A, 1), (B, 2), (C, 3)] &lt;br&gt;
     */
    public default <ANOTHER> StreamPlus<Tuple2<DATA, ANOTHER>> zipWith(Stream<ANOTHER> anotherStream) {
        return zipWith(anotherStream, RequireBoth, Tuple2::of);
    }

    /**
     * Combine this stream with another stream into a stream of tuple pair.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     *
     * For an example with ZipWithOption.AllowUnpaired: &lt;br&gt;
     *   This stream:    [A, B, C] &lt;br&gt;
     *   Another stream: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Result stream:  [(A, 1), (B, 2), (C, 3), (null, 4), (null, 5)] &lt;br&gt;
     */
    public default <ANOTHER> StreamPlus<Tuple2<DATA, ANOTHER>> zipWith(Stream<ANOTHER> anotherStream, ZipWithOption option) {
        return zipWith(anotherStream, option, Tuple2::of);
    }

    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * The combination stops when any of the stream ended.
     *
     * For an example: &lt;br&gt;
     *   This stream:    [A, B, C] &lt;br&gt;
     *   Another stream: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Combinator:     (v1,v2) -&gt; v1 + "-" + v2
     *   Result stream:  [A-1, B-2, C-3] &lt;br&gt;
     */
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(Stream<ANOTHER> anotherStream, BiFunction<DATA, ANOTHER, TARGET> combinator) {
        return zipWith(anotherStream, RequireBoth, combinator);
    }

    /**
     * Combine this stream with another stream using the combinator to create the result value one by one.
     * Depending on the given ZipWithOption, the combination may ended when one ended or continue with null as value.
     *
     * For an example with ZipWithOption.AllowUnpaired: &lt;br&gt;
     *   This stream:    [A, B, C] &lt;br&gt;
     *   Another stream: [1, 2, 3, 4, 5] &lt;br&gt;
     *   Combinator:     (v1,v2) -&gt; v1 + "-" + v2
     *   Result stream:  [A-1, B-2, C-3, null-4, null-5] &lt;br&gt;
     */
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(Stream<ANOTHER> anotherStream, ZipWithOption option, BiFunction<DATA, ANOTHER, TARGET> combinator) {
        val iteratorA = streamPlus().iterator();
        val iteratorB = IteratorPlus.from(anotherStream.iterator());
        return StreamPlusHelper.doZipWith(option, combinator, iteratorA, iteratorB);
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
    public default StreamPlus<DATA> choose(Stream<DATA> anotherStream, BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        return choose(anotherStream, AllowUnpaired, selectThisNotAnother);
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
    public default StreamPlus<DATA> choose(Stream<DATA> anotherStream, ZipWithOption option, BiFunction<DATA, DATA, Boolean> selectThisNotAnother) {
        val iteratorA = this.streamPlus().iterator();
        val iteratorB = anotherStream.iterator();
        val iterator = new Iterator<DATA>() {

            private boolean hasNextA;

            private boolean hasNextB;

            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth) ? (hasNextA && hasNextB) : (hasNextA || hasNextB);
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
