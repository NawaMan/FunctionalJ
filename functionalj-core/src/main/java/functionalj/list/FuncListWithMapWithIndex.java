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

import functionalj.function.IntObjBiFunction;
import functionalj.function.IntObjToDoubleBiFunction;
import functionalj.function.IntObjToIntBiFunction;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.markers.Sequential;
import functionalj.streamable.AsStreamable;
import functionalj.tuple.IntTuple2;

public interface FuncListWithMapWithIndex<DATA> extends AsStreamable<DATA> {
    
    /** @return  the stream of each value and index. */
    public default FuncList<IntTuple2<DATA>> mapWithIndex() {
        return FuncList.deriveFrom(this, stream -> stream.mapWithIndex());
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <TARGET> FuncList<TARGET> mapWithIndex(IntObjBiFunction<? super DATA, TARGET> combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    public default <TARGET> FuncList<TARGET> mapToObjWithIndex(IntObjBiFunction<? super DATA, TARGET> combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapToObjWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    @Sequential
    public default IntFuncList mapToIntWithIndex(IntObjToIntBiFunction<? super DATA> combinator) {
        return IntFuncList.deriveFrom(this, stream -> stream.mapToIntWithIndex(combinator));
    }
    
    /** Create a stream whose value is the combination between value of this stream and its index. */
    @Sequential
    public default DoubleFuncList mapToDoubleWithIndex(IntObjToDoubleBiFunction<? super DATA> combinator) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDoubleWithIndex(combinator));
    }
    
}
