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
package functionalj.list;

import static functionalj.list.FuncList.deriveToDouble;
import static functionalj.list.FuncList.deriveToInt;
import static functionalj.list.FuncList.deriveToObj;

import java.util.function.BiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import functionalj.function.Func1;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.ObjectObjectToDoubleFunctionPrimitive;
import functionalj.function.ObjectObjectToIntFunctionPrimitive;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.StreamPlus;
import functionalj.streamable.AsStreamable;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

public interface FuncListWithMapGroup<DATA> extends AsStreamable<DATA> {
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<Tuple2<DATA, DATA>> mapTwo() {
        return deriveToObj(this, stream -> stream.mapTwo());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<Tuple3<DATA, DATA, DATA>> mapThree() {
        return deriveToObj(this, stream -> stream.mapThree());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<Tuple4<DATA, DATA, DATA, DATA>> mapFour() {
        return deriveToObj(this, stream -> stream.mapFour());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<Tuple5<DATA, DATA, DATA, DATA, DATA>> mapFive() {
        return deriveToObj(this, stream -> stream.mapFive());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<Tuple6<DATA, DATA, DATA, DATA, DATA, DATA>> mapSix() {
        return deriveToObj(this, stream -> stream.mapSix());
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> FuncList<TARGET> mapGroup(BiFunction<? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> FuncList<TARGET> mapGroup(Func3<? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> FuncList<TARGET> mapGroup(Func4<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> FuncList<TARGET> mapGroup(Func5<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> FuncList<TARGET> mapGroup(Func6<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<StreamPlus<DATA>> mapGroup(int count) {
        return deriveToObj(this, stream -> stream.mapGroup(count));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> FuncList<TARGET> mapGroup(int count, Func1<? super StreamPlus<? extends DATA>, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(count, combinator));
    }
    
    //== Int ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntFuncList mapTwoToInt(ObjectObjectToIntFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        return deriveToInt(this, stream -> stream.mapTwoToInt(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntFuncList mapGroupToInt(int count, ToIntFunction<? super StreamPlus<? extends DATA>> combinator) {
        return deriveToInt(this, stream -> stream.mapGroupToInt(count, combinator));
    }
    
    //== Double ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleFuncList mapTwoToDouble(ObjectObjectToDoubleFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        return deriveToDouble(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleFuncList mapGroupToDouble(int count, ToDoubleFunction<? super StreamPlus<? extends DATA>> combinator) {
        return deriveToDouble(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
    
}
