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
package functionalj.list.doublelist;

import static functionalj.list.doublelist.DoubleFuncList.deriveToDouble;
import static functionalj.list.doublelist.DoubleFuncList.deriveToObj;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.function.DoubleBiPredicatePrimitive;
import functionalj.function.DoubleDoubleBiFunction;
import functionalj.function.DoubleObjBiFunction;
import functionalj.list.FuncList;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.DoubleTuple2;


public interface DoubleFuncListWithCombine extends AsDoubleFuncList {
    
    /** Concatenate the given tail stream to this stream. */
    public default DoubleFuncList concatWith(DoubleFuncList tail) {
        return deriveToDouble(this, stream -> stream.concatWith(tail.doubleStream()));
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
    public default DoubleFuncList mergeWith(DoubleFuncList anotherStreamable) {
        return deriveToDouble(this, stream -> stream.mergeWith(anotherStreamable.doubleStream()));
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
    public default <ANOTHER> FuncList<DoubleTuple2<ANOTHER>> zipWith(
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
    public default <ANOTHER> FuncList<DoubleTuple2<ANOTHER>> zipWith(
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
            FuncList<ANOTHER>                    anotherStreamable,
            DoubleObjBiFunction<ANOTHER, TARGET> merger) {
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
    public default FuncList<DoubleDoubleTuple> zipWith(DoubleFuncList anotherStreamable) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.doubleStream()));
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
    public default FuncList<DoubleDoubleTuple> zipWith(DoubleFuncList anotherStreamable, double defaultValue) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.doubleStream(), defaultValue));
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
    public default FuncList<DoubleDoubleTuple> zipWith(DoubleFuncList anotherStreamable, double defaultValue1, double defaultValue2) {
        return deriveToObj(this, stream -> stream.zipWith(anotherStreamable.doubleStream(), defaultValue1, defaultValue2));
    }
    
    public default DoubleFuncList zipWith(DoubleFuncList anotherStreamable, DoubleBiFunctionPrimitive merger) {
        return deriveToDouble(this, stream -> stream.zipWith(anotherStreamable.doubleStream(), merger));
    }
    
    public default DoubleFuncList zipWith(DoubleFuncList anotherStreamable, double defaultValue, DoubleBiFunctionPrimitive merger) {
        return deriveToDouble(this, stream -> stream.zipWith(anotherStreamable.doubleStream(), defaultValue, merger));
    }
    
    public default DoubleFuncList zipWith(
                                    DoubleFuncList            anotherStreamable, 
                                    double                    defaultValue1, 
                                    double                    defaultValue2,
                                    DoubleBiFunctionPrimitive merger) {
        return deriveToDouble(this, stream -> stream.zipWith(anotherStreamable.doubleStream(), defaultValue1, defaultValue2, merger));
    }
    
    public default <T> FuncList<T> zipToObjWith(DoubleFuncList anotherStreamable, DoubleDoubleBiFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherStreamable.doubleStream(), merger));
    }
    
    public default <T> FuncList<T> zipToObjWith(DoubleFuncList anotherStreamable, double defaultValue, DoubleDoubleBiFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherStreamable.doubleStream(), defaultValue, merger));
    }
    
    public default <T> FuncList<T> zipToObjWith(
                                    DoubleFuncList            anotherStreamable, 
                                    double                    defaultValue1, 
                                    double                    defaultValue2, 
                                    DoubleDoubleBiFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherStreamable.doubleStream(), defaultValue1, defaultValue2, merger));
    }
    
    public default <ANOTHER, TARGET> FuncList<TARGET> zipToObjWith(
                                                        double                               defaultValue,
                                                        FuncList<ANOTHER>                    anotherStreamable,
                                                        DoubleObjBiFunction<ANOTHER, TARGET> merger) {
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
    public default DoubleFuncList choose(DoubleFuncList anotherStreamable, DoubleBiPredicatePrimitive selectThisNotAnother) {
        return deriveToDouble(this, stream -> stream.choose(anotherStreamable.doubleStreamPlus(), selectThisNotAnother));
    }
    
}
