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
package functionalj.list.intlist;

import static functionalj.list.intlist.IntFuncList.deriveToInt;
import static functionalj.list.intlist.IntFuncList.deriveToObj;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;


public interface IntFuncListWithCombine extends AsIntFuncList {
    
    /** Concatenate the given tail stream to this stream. */
    public default IntFuncList concatWith(IntFuncList tail) {
        return deriveToInt(this, stream -> stream.concatWith(tail.intStream()));
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
    public default IntFuncList mergeWith(IntFuncList anotherStreamable) {
        return deriveToInt(this, stream -> stream.mergeWith(anotherStreamable.intStream()));
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
    public default <ANOTHER> FuncList<IntTuple2<ANOTHER>> zipWith(
            FuncList<ANOTHER> anotherStreamable) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.stream()));
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
    public default <ANOTHER> FuncList<IntTuple2<ANOTHER>> zipWith(
            int               defaultValue,
            FuncList<ANOTHER> anotherStreamable) {
        return deriveToObj(this, stream -> stream.zipWith(defaultValue, anotherStreamable.stream()));
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
    public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(
            FuncList<ANOTHER>                 anotherStreamable,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.stream(), merger));
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
    public default FuncList<IntIntTuple> zipWith(IntFuncList anotherStreamable) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.intStream()));
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
    public default FuncList<IntIntTuple> zipWith(IntFuncList anotherStreamable, int defaultValue) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.intStream(), defaultValue));
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
    public default FuncList<IntIntTuple> zipWith(IntFuncList anotherStreamable, int defaultValue1,
            int defaultValue2) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.intStream(), defaultValue1, defaultValue2));
    }
    
    public default IntFuncList zipWith(IntFuncList anotherStreamable, IntBiFunctionPrimitive merger) {
        return deriveToInt(this, stream -> stream.zipWith(anotherStreamable.intStream(), merger));
    }
    
    public default IntFuncList zipWith(IntFuncList anotherStreamable, int defaultValue,
            IntBiFunctionPrimitive merger) {
        return deriveToInt(this, stream -> stream.zipWith(anotherStreamable.intStream(), defaultValue, merger));
    }
    
    public default IntFuncList zipWith(IntFuncList anotherStreamable, int defaultValue1, int defaultValue2,
            IntBiFunctionPrimitive merger) {
        return deriveToInt(this, stream -> stream.zipWith(anotherStreamable.intStream(), defaultValue1, defaultValue2, merger));
    }
    
    public default <T> FuncList<T> zipToObjWith(IntFuncList anotherStreamable, IntIntBiFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherStreamable.intStream(), merger));
    }
    
    public default <T> FuncList<T> zipToObjWith(IntFuncList anotherStreamable, int defaultValue,
            IntIntBiFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherStreamable.intStream(), defaultValue, merger));
    }
    
    public default <T> FuncList<T> zipToObjWith(IntFuncList anotherStreamable, int defaultValue1, int defaultValue2,
            IntIntBiFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherStreamable.intStream(), defaultValue1, defaultValue2, merger));
    }
    
    public default <ANOTHER, TARGET> FuncList<TARGET> zipToObjWith(
            int               defaultValue,
            FuncList<ANOTHER> anotherStreamable,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return deriveToObj(this, stream -> stream.zipWith(defaultValue, anotherStreamable.stream(), merger));
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
    public default IntFuncList choose(IntFuncList anotherStreamable, IntBiPredicatePrimitive selectThisNotAnother) {
        return deriveToInt(this, stream -> stream.choose(anotherStreamable.intStreamPlus(), selectThisNotAnother));
    }
    
}
