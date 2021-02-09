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
import static functionalj.list.doublelist.DoubleFuncList.deriveToInt;
import static functionalj.list.doublelist.DoubleFuncList.deriveToObj;

import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.DoubleObjBiFunction;
import functionalj.function.IntDoubleBiFunction;
import functionalj.function.IntegerDoubleToDoubleFunction;
import functionalj.function.IntegerDoubleToIntegerFunction;
import functionalj.list.FuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.tuple.IntDoubleTuple;

public interface DoubleFuncListWithMapWithIndex extends AsDoubleFuncList {
    
    // TODO - to int, long, double
    
    /** @return  the stream of each value and index. */
    public default FuncList<IntDoubleTuple> mapWithIndex() {
        return deriveToObj(this, stream -> stream.mapWithIndex());
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default DoubleFuncList mapWithIndex(IntegerDoubleToDoubleFunction combinator) {
        return deriveToDouble(this, stream -> stream.mapWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T> FuncList<T> mapToObjWithIndex(IntDoubleBiFunction<T> combinator) {
        return deriveToObj(this, stream -> stream.mapToObjWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default IntFuncList mapToIntWithIndex(IntegerDoubleToIntegerFunction combinator) {
        return deriveToInt(this, stream -> stream.mapToIntWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> FuncList<T> mapWithIndex(
            DoubleUnaryOperator    valueMapper,
            IntDoubleBiFunction<T> combinator) {
        return deriveToObj(this, stream -> stream.mapWithIndex(valueMapper, combinator));
    }
    
    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> FuncList<T> mapToObjWithIndex(
            DoubleFunction<? extends T1>       valueMapper,
            DoubleObjBiFunction<? super T1, T> combinator) {
        return deriveToObj(this, stream -> stream.mapToObjWithIndex(valueMapper, combinator));
    }
    
}
