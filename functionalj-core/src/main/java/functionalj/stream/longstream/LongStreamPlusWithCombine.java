// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.longstream;

import static functionalj.function.FuncUnit0.funcUnit0;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import java.util.PrimitiveIterator;
import java.util.function.LongBinaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import functionalj.function.LongBiPredicatePrimitive;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.result.NoMoreResultException;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.LongLongTuple;
import functionalj.tuple.LongTuple2;
import lombok.val;

public interface LongStreamPlusWithCombine {
    
    public LongStreamPlus longStreamPlus();
    
    /**
     * Concatenate the given head stream in front of this stream.
     */
    public default LongStreamPlus prependWith(LongStream head) {
        return LongStreamPlus.concat(LongStreamPlus.from(head), LongStreamPlus.from(longStreamPlus()));
    }
    
    /**
     * Concatenate the given tail stream to this stream.
     */
    public default LongStreamPlus appendWith(LongStream tail) {
        return LongStreamPlus.concat(LongStreamPlus.from(longStreamPlus()), LongStreamPlus.from(tail));
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
    public default LongStreamPlus mergeWith(LongStream anotherStream) {
        val streamPlus = longStreamPlus();
        val iteratorA = LongIteratorPlus.from(streamPlus.iterator());
        val iteratorB = LongIteratorPlus.from(anotherStream.iterator());
        val resultStream = LongStreamPlusHelper.doMergeLong(iteratorA, iteratorB);
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
    public default <ANOTHER> StreamPlus<LongTuple2<ANOTHER>> zipWith(Stream<ANOTHER> anotherStream) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongWith((value, another) -> LongTuple2.of(value, another), iteratorA, iteratorB);
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
    public default <ANOTHER> StreamPlus<LongTuple2<ANOTHER>> zipWith(long defaultValue, Stream<ANOTHER> anotherStream) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongWith(defaultValue, (value, another) -> LongTuple2.of(value, another), iteratorA, iteratorB);
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
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(Stream<ANOTHER> anotherStream, LongObjBiFunction<ANOTHER, TARGET> merger) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongWith(merger, iteratorA, iteratorB);
    }
    
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(long defaultValue, Stream<ANOTHER> anotherStream, LongObjBiFunction<ANOTHER, TARGET> merger) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongWith(defaultValue, merger, iteratorA, iteratorB);
    }
    
    public default StreamPlus<LongLongTuple> zipWith(LongStream anotherStream) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongObjWith(LongLongTuple::new, iteratorA, iteratorB);
    }
    
    public default StreamPlus<LongLongTuple> zipWith(LongStream anotherStream, long defaultValue) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongObjWith(LongLongTuple::new, iteratorA, iteratorB, defaultValue);
    }
    
    public default StreamPlus<LongLongTuple> zipWith(long defaultValue1, LongStream anotherStream, long defaultValue2) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongObjWith(LongLongTuple::new, iteratorA, iteratorB, defaultValue1, defaultValue2);
    }
    
    public default LongStreamPlus zipWith(LongStream anotherStream, LongBinaryOperator merger) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongWith(merger, iteratorA, iteratorB);
    }
    
    public default LongStreamPlus zipWith(LongStream anotherStream, long defaultValue, LongBinaryOperator merger) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongWith(merger, iteratorA, iteratorB, defaultValue);
    }
    
    public default LongStreamPlus zipWith(LongStream anotherStream, long defaultValue1, long defaultValue2, LongBinaryOperator merger) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
    }
    
    public default <T> StreamPlus<T> zipToObjWith(LongStream anotherStream, LongLongBiFunction<T> merger) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongObjWith(merger, iteratorA, iteratorB);
    }
    
    public default <T> StreamPlus<T> zipToObjWith(LongStream anotherStream, long defaultValue1, long defaultValue2, LongLongBiFunction<T> merger) {
        LongIteratorPlus iteratorA = longStreamPlus().iterator();
        LongIteratorPlus iteratorB = LongStreamPlus.from(anotherStream).iterator();
        return LongStreamPlusHelper.doZipLongLongObjWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
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
    public default LongStreamPlus choose(LongStream anotherStream, LongBiPredicatePrimitive selectThisNotAnother) {
        return choose(anotherStream, AllowUnpaired, selectThisNotAnother);
    }
    
    /**
     * Create a new stream by choosing value from each stream using the selector.
     * The parameter option can be used to select when the stream should end.
     * In the case that the unpair is allow,
     *   the value from the longer stream is automatically used after the shorter stream ended.
     *
     * For an example with ZipWithOption.AllowUnpaired: &lt;br&gt;
     *   This stream:    [10, 1, 9, 2] &lt;br&gt;
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] &lt;br&gt;
     *   Selector:       (v1,v2) -&gt; v1 &gt; v2 &lt;br&gt;
     *   Result stream:  [10, 5, 9, 5, 5, 5, 5]
     */
    public default LongStreamPlus choose(LongStream anotherStream, ZipWithOption option, LongBiPredicatePrimitive selectThisNotAnother) {
        val iteratorA = this.longStreamPlus().iterator();
        val iteratorB = LongStreamPlus.from(anotherStream).iterator();
        val iterator = new PrimitiveIterator.OfLong() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth) ? (hasNextA && hasNextB) : (hasNextA || hasNextB);
            }
        
            public long nextLong() {
                val nextA = hasNextA ? iteratorA.nextLong() : Long.MIN_VALUE;
                val nextB = hasNextB ? iteratorB.nextLong() : Long.MIN_VALUE;
                if (hasNextA && hasNextB) {
                    boolean selectA = selectThisNotAnother.testLongLong(nextA, nextB);
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
        val iterable = new LongIterable() {
        
            @Override
            public LongIteratorPlus iterator() {
                return LongIteratorPlus.from(iterator);
            }
        };
        return LongStreamPlus.from(StreamSupport.longStream(iterable.spliterator(), false));
    }
}
