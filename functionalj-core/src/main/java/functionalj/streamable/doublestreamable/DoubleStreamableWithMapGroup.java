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
package functionalj.streamable.doublestreamable;

import java.util.function.DoubleBinaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import functionalj.function.DoubleDoubleBiFunction;
import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.function.Func1;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.streamable.Streamable;
import functionalj.streamable.intstreamable.IntStreamable;
import functionalj.tuple.DoubleDoubleTuple;

public interface DoubleStreamableWithMapGroup extends AsDoubleStreamable {
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapTwo(DoubleBinaryOperator combinator) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapGroup(int count, ToDoubleFunction<DoubleStreamPlus> combinator) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
    
    //== Object ==
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<DoubleDoubleTuple> mapTwoToObj() {
        return Streamable.deriveFrom(this, stream -> stream.mapTwoToObj());
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> Streamable<TARGET> mapGroupToObj(DoubleDoubleBiFunction<TARGET> combinator) {
        return Streamable.deriveFrom(this, stream -> stream.mapGroupToObj(combinator));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default Streamable<DoubleStreamPlus> mapGroupToObj(int count) {
        return Streamable.deriveFrom(this, stream -> stream.mapGroupToObj(count));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> Streamable<TARGET> mapGroupToObj(int count, Func1<DoubleStreamPlus, ? extends TARGET> combinator) {
        return Streamable.deriveFrom(this, stream -> stream.mapGroupToObj(count, combinator));
    }
    
    //== Int ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapTwoToInt(DoubleDoubleToIntFunctionPrimitive combinator) {
        return IntStreamable.deriveFrom(this, stream -> stream.mapTwoToInt(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntStreamable mapGroupToInt(int count, ToIntFunction<DoubleStreamPlus> combinator) {
        return IntStreamable.deriveFrom(this, stream -> stream.mapGroupToInt(count, combinator));
    }
    
    //== Double ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapTwoToDouble(DoubleBinaryOperator combinator) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleStreamable mapGroupToDouble(int count, ToDoubleFunction<DoubleStreamPlus> combinator) {
        return DoubleStreamable.deriveFrom(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
    
}
