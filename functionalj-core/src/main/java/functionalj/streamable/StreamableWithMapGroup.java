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
package functionalj.streamable;

import static functionalj.streamable.Streamable.deriveFrom;
import static functionalj.streamable.Streamable.deriveToInt;
import static functionalj.streamable.Streamable.deriveToObj;

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
import functionalj.stream.StreamPlus;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import functionalj.streamable.intstreamable.IntStreamable;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

public interface StreamableWithMapGroup<DATA> extends AsStreamable<DATA> {
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<Tuple2<? super DATA, ? super DATA>> mapTwo() {
        return deriveToObj(this, stream -> stream.mapTwo());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<Tuple3<? super DATA, ? super DATA, ? super DATA>> mapThree() {
        return deriveToObj(this, stream -> stream.mapThree());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<Tuple4<? super DATA, ? super DATA, ? super DATA, ? super DATA>> mapFour() {
        return deriveToObj(this, stream -> stream.mapFour());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<Tuple5<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA>> mapFive() {
        return deriveToObj(this, stream -> stream.mapFive());
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<Tuple6<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA>> mapSix() {
        return deriveToObj(this, stream -> stream.mapSix());
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> Streamable<TARGET> mapGroup(BiFunction<? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> Streamable<TARGET> mapGroup(Func3<? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> Streamable<TARGET> mapGroup(Func4<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveFrom(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> Streamable<TARGET> mapGroup(Func5<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> Streamable<TARGET> mapGroup(Func6<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(combinator));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<StreamPlus<? super DATA>> mapGroup(int count) {
        return deriveToObj(this, stream -> stream.mapGroup(count));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> Streamable<TARGET> mapGroup(int count, Func1<? super StreamPlus<? super DATA>, ? extends TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroup(count, combinator));
    }
    
    //== Int ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapTwoToInt(ObjectObjectToIntFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        return deriveToInt(this, stream -> stream.mapTwoToInt(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapGroupToInt(int count, ToIntFunction<? super StreamPlus<? super DATA>> combinator) {
        return deriveToInt(this, stream -> stream.mapGroupToInt(count, combinator));
    }
    
    //== Double ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapTwoToDouble(ObjectObjectToDoubleFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        return Streamable.deriveToDouble(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapGroupToDouble(int count, ToDoubleFunction<? super StreamPlus<? super DATA>> combinator) {
        return Streamable.deriveToDouble(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
    
}
