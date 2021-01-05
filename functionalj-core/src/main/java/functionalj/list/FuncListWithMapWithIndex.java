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

import java.util.function.Function;

import functionalj.function.IntObjBiFunction;
import functionalj.streamable.AsStreamable;
import functionalj.tuple.IntTuple2;

public interface FuncListWithMapWithIndex<DATA> extends AsStreamable<DATA> {
    
    // TODO - to int, long, double
    
    /** @return  the stream of each value and index. */
    public default FuncList<IntTuple2<DATA>> mapWithIndex() {
        return FuncList.deriveFrom(this, stream -> stream.mapWithIndex());
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T> FuncList<T> mapWithIndex(IntObjBiFunction<? super DATA, T> combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <T> FuncList<T> mapToObjWithIndex(IntObjBiFunction<? super DATA, T> combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapToObjWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> FuncList<T> mapWithIndex(
                Function<? super DATA, ? extends T1> valueMapper,
                IntObjBiFunction<? super T1, T>      combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapWithIndex(valueMapper, combinator));
    }
    
    /** Create a stream whose value is the combination between the mapped value of this stream and its index. */
    public default <T1, T> FuncList<T> mapToObjWithIndex(
                Function<? super DATA, ? extends T1> valueMapper,
                IntObjBiFunction<? super T1, T>      combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapToObjWithIndex(valueMapper, combinator));
    }
    
}
