// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.doublestream;

import static functionalj.function.FuncUnit0.funcUnit0;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import java.util.PrimitiveIterator;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import functionalj.function.DoubleDoubleFunction;
import functionalj.function.DoubleDoublePredicatePrimitive;
import functionalj.function.DoubleObjBiFunction;
import functionalj.result.NoMoreResultException;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.DoubleTuple2;
import lombok.val;

public interface DoubleStreamPlusWithCombine {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /**
     * Concatenate the given head stream in front of this stream.
     */
    public default DoubleStreamPlus prependWith(DoubleStream head) {
        return DoubleStreamPlus.concat(DoubleStreamPlus.from(head), DoubleStreamPlus.from(doubleStreamPlus()));
    }
    
    /**
     * Concatenate the given tail stream to this stream.
     */
    public default DoubleStreamPlus appendWith(DoubleStream tail) {
        return DoubleStreamPlus.concat(DoubleStreamPlus.from(doubleStreamPlus()), DoubleStreamPlus.from(tail));
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
    public default DoubleStreamPlus mergeWith(DoubleStream anotherStream) {
        val thisStream = doubleStreamPlus();
        val iteratorA = DoubleIteratorPlus.from(thisStream.iterator());
        val iteratorB = DoubleIteratorPlus.from(anotherStream.iterator());
        val resultStream = DoubleStreamPlusHelper.doMergeInt(iteratorA, iteratorB);
        resultStream.onClose(() -> {
            funcUnit0(() -> thisStream.close()).runCarelessly();
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
    public default <ANOTHER> StreamPlus<DoubleTuple2<ANOTHER>> zipWith(Stream<ANOTHER> anotherStream) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleWith((value, another) -> DoubleTuple2.of(value, another), iteratorA, iteratorB);
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
    public default <ANOTHER> StreamPlus<DoubleTuple2<ANOTHER>> zipWith(double defaultValue, Stream<ANOTHER> anotherStream) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleWith(defaultValue, (value, another) -> DoubleTuple2.of(value, another), iteratorA, iteratorB);
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
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(Stream<ANOTHER> anotherStream, DoubleObjBiFunction<ANOTHER, TARGET> merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleWith(merger, iteratorA, iteratorB);
    }
    
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(double defaultValue, Stream<ANOTHER> anotherStream, DoubleObjBiFunction<ANOTHER, TARGET> merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleWith(defaultValue, merger, iteratorA, iteratorB);
    }
    
    public default StreamPlus<DoubleDoubleTuple> zipWith(DoubleStream anotherStream) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleObjWith(DoubleDoubleTuple::new, iteratorA, iteratorB);
    }
    
    public default StreamPlus<DoubleDoubleTuple> zipWith(DoubleStream anotherStream, double defaultValue) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleObjWith(DoubleDoubleTuple::new, iteratorA, iteratorB, defaultValue);
    }
    
    public default StreamPlus<DoubleDoubleTuple> zipWith(DoubleStream anotherStream, double defaultValue1, double defaultValue2) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleObjWith(DoubleDoubleTuple::new, iteratorA, iteratorB, defaultValue1, defaultValue2);
    }
    
    public default DoubleStreamPlus zipWith(DoubleStream anotherStream, DoubleBinaryOperator merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleWith(merger, iteratorA, iteratorB);
    }
    
    public default DoubleStreamPlus zipWith(DoubleStream anotherStream, double defaultValue, DoubleBinaryOperator merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleWith(merger, iteratorA, iteratorB, defaultValue);
    }
    
    public default DoubleStreamPlus zipWith(DoubleStream anotherStream, double defaultValue1, double defaultValue2, DoubleBinaryOperator merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
    }
    
    public default <T> StreamPlus<T> zipToObjWith(DoubleStream anotherStream, DoubleDoubleFunction<T> merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleObjWith(merger, iteratorA, iteratorB);
    }
    
    public default <T> StreamPlus<T> zipToObjWith(DoubleStream anotherStream, double defaultValue, DoubleDoubleFunction<T> merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleObjWith(merger, iteratorA, iteratorB, defaultValue);
    }
    
    public default <T> StreamPlus<T> zipToObjWith(DoubleStream anotherStream, double defaultValue1, double defaultValue2, DoubleDoubleFunction<T> merger) {
        DoubleIteratorPlus iteratorA = doubleStreamPlus().iterator();
        DoubleIteratorPlus iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        return DoubleStreamPlusHelper.doZipDoubleDoubleObjWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
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
    public default DoubleStreamPlus choose(DoubleStream anotherStream, DoubleDoublePredicatePrimitive selectThisNotAnother) {
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
    public default DoubleStreamPlus choose(DoubleStream anotherStream, ZipWithOption option, DoubleDoublePredicatePrimitive selectThisNotAnother) {
        val iteratorA = this.doubleStreamPlus().iterator();
        val iteratorB = DoubleStreamPlus.from(anotherStream).iterator();
        val iterator = new PrimitiveIterator.OfDouble() {
        
            private boolean hasNextA;
        
            private boolean hasNextB;
        
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth) ? (hasNextA && hasNextB) : (hasNextA || hasNextB);
            }
        
            public double nextDouble() {
                val nextA = hasNextA ? iteratorA.nextDouble() : Double.NaN;
                val nextB = hasNextB ? iteratorB.nextDouble() : Double.NaN;
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
        val iterable = new DoubleIterable() {
        
            @Override
            public DoubleIteratorPlus iterator() {
                return DoubleIteratorPlus.from(iterator);
            }
        };
        return DoubleStreamPlus.from(StreamSupport.doubleStream(iterable.spliterator(), false));
    }
}
