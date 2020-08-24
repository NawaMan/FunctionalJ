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
package functionalj.stream.intstream;

import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReference;

import functionalj.function.ObjIntBiFunction;
import functionalj.stream.StreamPlus;
import functionalj.tuple.ObjIntTuple;
import lombok.val;


public interface IntStreamPlusWithMapWithPrev {
    
    public IntStreamPlus intStreamPlus();
    
    /** @return  the stream of  each previous value and each current value. */
    public default StreamPlus<ObjIntTuple<OptionalInt>> mapWithPrev() {
        val prev = new AtomicReference<OptionalInt>(OptionalInt.empty());
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(element -> {
                    val prevValue = prev.get();
                    prev.set(OptionalInt.of(element));
                    val result = ObjIntTuple.of(prevValue, element);
                    return result;
                });
    }
    
    public default <TARGET> StreamPlus<TARGET> mapWithPrev(
            ObjIntBiFunction<OptionalInt, ? extends TARGET> mapper) {
        val prev = new AtomicReference<OptionalInt>(OptionalInt.empty());
        val streamPlus = intStreamPlus();
        return streamPlus
                .mapToObj(element -> {
                    val prevValue = prev.get();
                    val newValue  = mapper.apply(prevValue, element);
                    prev.set(OptionalInt.of(element));
                    return newValue;
                });
    }
    
}
