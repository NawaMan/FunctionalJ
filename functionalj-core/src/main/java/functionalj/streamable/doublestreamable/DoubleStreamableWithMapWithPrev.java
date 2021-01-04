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

import static functionalj.streamable.Streamable.deriveFrom;

import java.util.OptionalDouble;

import functionalj.function.ObjDoubleBiFunction;
import functionalj.streamable.Streamable;
import functionalj.tuple.ObjDoubleTuple;

public interface DoubleStreamableWithMapWithPrev extends AsDoubleStreamable {
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> Streamable<TARGET> mapWithPrev(
            ObjDoubleBiFunction<OptionalDouble, ? extends TARGET> mapper) {
        return deriveFrom(this, stream -> stream.mapWithPrev(mapper));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default Streamable<ObjDoubleTuple<OptionalDouble>> mapWithPrev() {
        return deriveFrom(this, stream -> stream.mapWithPrev());
    }
    
}
