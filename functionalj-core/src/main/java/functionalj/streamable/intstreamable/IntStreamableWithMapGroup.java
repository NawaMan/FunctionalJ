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
package functionalj.streamable.intstreamable;

import static functionalj.streamable.intstreamable.IntStreamable.deriveToDouble;
import static functionalj.streamable.intstreamable.IntStreamable.deriveToInt;
import static functionalj.streamable.intstreamable.IntStreamable.deriveToObj;

import java.util.function.IntBinaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import functionalj.function.Func1;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntIntToDoubleFunctionPrimitive;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.Streamable;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import functionalj.tuple.IntIntTuple;

public interface IntStreamableWithMapGroup extends AsIntStreamable {
    
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapTwo(IntBinaryOperator combinator) {
        return deriveToInt(this, stream -> stream.mapTwo(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapGroup(int count, ToIntFunction<IntStreamPlus> combinator) {
        return deriveToInt(this, stream -> stream.mapGroup(count, combinator));
    }
    
    // == Object ==
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<IntIntTuple> mapTwoToObj() {
        return deriveToObj(this, stream -> stream.mapTwoToObj());
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> Streamable<TARGET> mapGroupToObj(IntIntBiFunction<TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapGroupToObj(combinator));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<IntStreamPlus> mapGroupToObj(int count) {
        return deriveToObj(this, stream -> stream.mapGroupToObj(count));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> Streamable<TARGET> mapGroupToObj(int count, Func1<IntStreamPlus, ? extends TARGET> combinator) {
        return mapGroupToObj(count)
                .map(combinator);
    }
    
    //== Int ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapTwoToInt(IntBinaryOperator combinator) {
        return deriveToInt(this, stream -> stream.mapTwoToInt(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapGroupToInt(int count, ToIntFunction<IntStreamPlus> combinator) {
        return deriveToInt(this, stream -> stream.mapGroupToInt(count, combinator));
    }
    
    //== Double ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapTwoToDouble(IntIntToDoubleFunctionPrimitive combinator) {
        return deriveToDouble(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapGroupToDouble(int count, ToDoubleFunction<IntStreamPlus> combinator) {
        return deriveToDouble(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
    
}
