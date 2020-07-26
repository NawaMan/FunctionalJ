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
package functionalj.list;

import static functionalj.list.FuncList.deriveFrom;

import java.util.function.BiFunction;

import functionalj.result.Result;
import functionalj.stream.AsStreamable;
import functionalj.tuple.Tuple2;

public interface FuncListWithMapWithPrev<DATA> extends AsStreamable<DATA> {
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> FuncList<TARGET> mapWithPrev(
            BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        return deriveFrom(this, stream -> stream.mapWithPrev(mapper));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default FuncList<Tuple2<? super Result<DATA>, ? super DATA>> mapWithPrev() {
        return deriveFrom(this, stream -> stream.mapWithPrev());
    }
    
}
