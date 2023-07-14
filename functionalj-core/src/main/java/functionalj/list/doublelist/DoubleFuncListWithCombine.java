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
import functionalj.function.DoubleDoubleToDoubleFunctionPrimitive;
import functionalj.function.DoubleDoublePredicatePrimitive;
import functionalj.function.DoubleDoubleFunction;
import functionalj.function.DoubleObjBiFunction;
import functionalj.list.FuncList;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.DoubleDoubleTuple;
import functionalj.tuple.DoubleTuple2;

public interface DoubleFuncListWithCombine extends AsDoubleFuncList {

    /**
     * Concatenate the given head stream in front of this stream.
     */
    public default DoubleFuncList prependWith(DoubleFuncList head) {
        return deriveToDouble(this, stream -> stream.prependWith(head.doubleStream()));
    }

    /**
     * Concatenate the given tail stream to this stream.
     */
    public default DoubleFuncList appendWith(DoubleFuncList tail) {
        return deriveToDouble(this, stream -> stream.appendWith(tail.doubleStream()));
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
    public default DoubleFuncList mergeWith(DoubleFuncList anotherFuncList) {
        return deriveToDouble(this, stream -> stream.mergeWith(anotherFuncList.doubleStream()));
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
    public default <ANOTHER> FuncList<DoubleTuple2<ANOTHER>> zipWith(FuncList<ANOTHER> anotherFuncList) {
        return deriveToObj(this, stream -> stream.zipWith(anotherFuncList.stream()));
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
    public default <ANOTHER> FuncList<DoubleTuple2<ANOTHER>> zipWith(int defaultValue, FuncList<ANOTHER> anotherFuncList) {
        return deriveToObj(this, stream -> stream.zipWith(defaultValue, anotherFuncList.stream()));
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
    public default <ANOTHER, TARGET> FuncList<TARGET> zipWith(FuncList<ANOTHER> anotherFuncList, DoubleObjBiFunction<ANOTHER, TARGET> merger) {
        return deriveToObj(this, stream -> stream.zipWith(anotherFuncList.stream(), merger));
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
    public default FuncList<DoubleDoubleTuple> zipWith(DoubleFuncList anotherFuncList) {
        return deriveToObj(this, stream -> stream.zipWith(anotherFuncList.doubleStream()));
    }

    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when any of the stream ended.
     *
     * For an example: &lt;br&gt;
     *   This stream:    [10, 1, 9, 2] &lt;br&gt;
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] &lt;br&gt;
     *   Selector:       (v1,v2) -&gt; v1 &gt; v2 &lt;br&gt;
     *   Result stream:  [10, 5, 9, 5]
     */
    public default FuncList<DoubleDoubleTuple> zipWith(DoubleFuncList anotherFuncList, double defaultValue) {
        return deriveToObj(this, stream -> stream.zipWith(anotherFuncList.doubleStream(), defaultValue));
    }

    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when both stream ended.
     * The value from the longer stream is automatically used after the shorter stream ended.
     *
     * For an example with ZipWithOption.AllowUnpaired: &lt;br&gt;
     *   This stream:    [10, 1, 9, 2] &lt;br&gt;
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] &lt;br&gt;
     *   Selector:       (v1,v2) -&gt; v1 &gt; v2 &lt;br&gt;
     *   Result stream:  [10, 5, 9, 5, 5, 5, 5]
     */
    public default FuncList<DoubleDoubleTuple> zipWith(double defaultValue1, DoubleFuncList anotherFuncList, double defaultValue2) {
        return deriveToObj(this, stream -> stream.zipWith(anotherFuncList.doubleStream(), defaultValue1, defaultValue2));
    }

    public default DoubleFuncList zipWith(DoubleFuncList anotherFuncList, DoubleDoubleToDoubleFunctionPrimitive merger) {
        return deriveToDouble(this, stream -> stream.zipWith(anotherFuncList.doubleStream(), merger));
    }

    public default DoubleFuncList zipWith(DoubleFuncList anotherFuncList, double defaultValue, DoubleDoubleToDoubleFunctionPrimitive merger) {
        return deriveToDouble(this, stream -> stream.zipWith(anotherFuncList.doubleStream(), defaultValue, merger));
    }

    public default DoubleFuncList zipWith(double defaultValue1, DoubleFuncList anotherFuncList, double defaultValue2, DoubleDoubleToDoubleFunctionPrimitive merger) {
        return deriveToDouble(this, stream -> stream.zipWith(anotherFuncList.doubleStream(), defaultValue1, defaultValue2, merger));
    }

    public default <T> FuncList<T> zipToObjWith(DoubleFuncList anotherFuncList, DoubleDoubleFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherFuncList.doubleStream(), merger));
    }

    public default <T> FuncList<T> zipToObjWith(double defaultValue1, DoubleFuncList anotherFuncList, double defaultValue2, DoubleDoubleFunction<T> merger) {
        return deriveToObj(this, stream -> stream.zipToObjWith(anotherFuncList.doubleStream(), defaultValue1, defaultValue2, merger));
    }

    public default <ANOTHER, TARGET> FuncList<TARGET> zipToObjWith(double defaultValue, FuncList<ANOTHER> anotherFuncList, DoubleObjBiFunction<ANOTHER, TARGET> merger) {
        return deriveToObj(this, stream -> stream.zipWith(defaultValue, anotherFuncList.stream(), merger));
    }

    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when both stream ended.
     * The value from the longer stream is automatically used after the shorter stream ended.
     */
    public default DoubleFuncList choose(DoubleFuncList anotherFuncList, DoubleDoublePredicatePrimitive selectThisNotAnother) {
        return deriveToDouble(this, stream -> stream.choose(anotherFuncList.doubleStreamPlus(), selectThisNotAnother));
    }

    /**
     * Create a new stream by choosing value from each stream suing the selector.
     * The combine stream ended when both stream ended.
     *
     * For AllowUnpaired, the values from the longer stream
     *
     * For an example with ZipWithOption.AllowUnpaired: &lt;br&gt;
     *   This stream:    [10, 1, 9, 2] &lt;br&gt;
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] &lt;br&gt;
     *   Selector:       (v1,v2) -&gt; v1 &gt; v2 &lt;br&gt;
     *   Result stream:  [10, 5, 9, 5, 5, 5, 5]
     *
     * For an example with ZipWithOption.RequireBoth: &lt;br&gt;
     *   This stream:    [10, 1, 9, 2] &lt;br&gt;
     *   Another stream: [ 5, 5, 5, 5, 5, 5, 5] &lt;br&gt;
     *   Selector:       (v1,v2) -&gt; v1 &gt; v2 &lt;br&gt;
     *   Result stream:  [10, 5, 9, 5]
     */
    public default DoubleFuncList choose(DoubleFuncList anotherFuncList, ZipWithOption option, DoubleDoublePredicatePrimitive selectThisNotAnother) {
        return deriveToDouble(this, stream -> stream.choose(anotherFuncList.doubleStreamPlus(), option, selectThisNotAnother));
    }
}
