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
package functionalj.stream.intstream;

import static functionalj.function.FuncUnit0.funcUnit0;
import static functionalj.stream.ZipWithOption.AllowUnpaired;

import java.util.PrimitiveIterator;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.result.NoMoreResultException;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;
import lombok.val;


public interface IntStreamPlusWithCombine {
    
    public IntStreamPlus intStreamPlus();
    
    
    /** Concatenate the given head stream in front of this stream. */
    public default IntStreamPlus prependWith(IntStream head) {
        return IntStreamPlus.concat(
                IntStreamPlus.from(head),
                IntStreamPlus.from(intStreamPlus()));
    }
    
    /** Concatenate the given tail stream to this stream. */
    public default IntStreamPlus appendWith(IntStream tail) {
        return IntStreamPlus.concat(
                IntStreamPlus.from(intStreamPlus()),
                IntStreamPlus.from(tail));
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
    public default IntStreamPlus mergeWith(IntStream anotherStream) {
        val streamPlus = intStreamPlus();
        val iteratorA  = IntIteratorPlus.from(streamPlus   .iterator());
        val iteratorB  = IntIteratorPlus.from(anotherStream.iterator());
        
        val resultStream
                = IntStreamPlusHelper
                .doMergeInt(iteratorA, iteratorB);
        
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
    public default <ANOTHER> StreamPlus<IntTuple2<ANOTHER>> zipWith(Stream<ANOTHER> anotherStream) {
        IntIteratorPlus       iteratorA = intStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntWith((value, another) -> IntTuple2.of(value, another), iteratorA, iteratorB);
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
    public default <ANOTHER> StreamPlus<IntTuple2<ANOTHER>> zipWith(
            int             defaultValue,
            Stream<ANOTHER> anotherStream) {
        IntIteratorPlus       iteratorA = intStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntWith(defaultValue, (value, another) -> IntTuple2.of(value, another), iteratorA, iteratorB);
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
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        IntIteratorPlus       iteratorA = intStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntWith(merger, iteratorA, iteratorB);
    }
    
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(
            int                               defaultValue,
            Stream<ANOTHER>                   anotherStream,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        IntIteratorPlus       iteratorA = intStreamPlus().iterator();
        IteratorPlus<ANOTHER> iteratorB = StreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntWith(defaultValue, merger, iteratorA, iteratorB);
    }
    
    public default StreamPlus<IntIntTuple> zipWith(
            IntStream anotherStream) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntObjWith(IntIntTuple::new, iteratorA, iteratorB);
    }
    
    public default StreamPlus<IntIntTuple> zipWith(
            IntStream anotherStream,
            int       defaultValue) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntObjWith(IntIntTuple::new, iteratorA, iteratorB, defaultValue);
    }
    
    public default StreamPlus<IntIntTuple> zipWith(
            int       defaultValue1,
            IntStream anotherStream,
            int       defaultValue2) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntObjWith(IntIntTuple::new, iteratorA, iteratorB, defaultValue1, defaultValue2);
    }
    
    public default IntStreamPlus zipWith(
            IntStream         anotherStream,
            IntBinaryOperator merger) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntWith(merger, iteratorA, iteratorB);
    }
    
    public default IntStreamPlus zipWith(
            IntStream         anotherStream,
            int               defaultValue,
            IntBinaryOperator merger) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntWith(merger, iteratorA, iteratorB, defaultValue);
    }
    
    public default IntStreamPlus zipWith(
            IntStream         anotherStream,
            int               defaultValue1,
            int               defaultValue2,
            IntBinaryOperator merger) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
    }
    
    public default <T> StreamPlus<T> zipToObjWith(
            IntStream           anotherStream,
            IntIntBiFunction<T> merger) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntObjWith(merger, iteratorA, iteratorB);
    }
    
    public default <T> StreamPlus<T> zipToObjWith(
            IntStream           anotherStream,
            int                 defaultValue1,
            int                 defaultValue2,
            IntIntBiFunction<T> merger) {
        IntIteratorPlus iteratorA = intStreamPlus().iterator();
        IntIteratorPlus iteratorB = IntStreamPlus.from(anotherStream).iterator();
        return IntStreamPlusHelper.doZipIntIntObjWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
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
    public default IntStreamPlus choose(
            IntStream               anotherStream, 
            IntBiPredicatePrimitive selectThisNotAnother) {
        return choose(anotherStream, AllowUnpaired, selectThisNotAnother);
    }
    
    /**
     * Create a new stream by choosing value from each stream using the selector.
     * The parameter option can be used to select when the stream should end.
     * In the case that the unpair is allow,
     *   the value from the longer stream is automatically used after the shorter stream ended.
     * 
     * For an example with ZipWithOption.AllowUnpaired: <br>
     *   This stream:    [10, 1, 9, 2] <br>
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] <br>
     *   Selector:       (v1,v2) -> v1 > v2 <br>
     *   Result stream:  [10, 5, 9, 5, 5, 5, 5]
     */
    public default IntStreamPlus choose(
            IntStream               anotherStream, 
            ZipWithOption           option, 
            IntBiPredicatePrimitive selectThisNotAnother) {
        val iteratorA = this.intStreamPlus().iterator();
        val iteratorB = IntStreamPlus.from(anotherStream).iterator();
        val iterator = new PrimitiveIterator.OfInt() {
            private boolean hasNextA;
            private boolean hasNextB;
            
            public boolean hasNext() {
                hasNextA = iteratorA.hasNext();
                hasNextB = iteratorB.hasNext();
                return (option == ZipWithOption.RequireBoth)
                        ? (hasNextA && hasNextB)
                        : (hasNextA || hasNextB);
            }
            public int nextInt() {
                val nextA = hasNextA ? iteratorA.nextInt() : Integer.MIN_VALUE;
                val nextB = hasNextB ? iteratorB.nextInt() : Integer.MIN_VALUE;
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
        val iterable = new IntIterable() {
            @Override
            public IntIteratorPlus iterator() {
                return IntIteratorPlus.from(iterator);
            }
        };
        return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
    }
    
}
