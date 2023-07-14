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
package functionalj.list.longlist;

import static functionalj.list.longlist.LongFuncList.deriveToLong;
import static functionalj.list.longlist.LongFuncList.deriveToObj;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import functionalj.function.IntLongBiFunction;
import functionalj.function.IntLongToLongFunctionPrimitive;
import functionalj.function.LongObjBiFunction;
import functionalj.list.FuncList;
import functionalj.tuple.IntLongTuple;

public interface LongFuncListWithMapWithIndex extends AsLongFuncList {

    // TODO - to int, long, double
    /**
     * @return  the stream of each value and index.
     */
    public default FuncList<IntLongTuple> mapWithIndex() {
        return deriveToObj(this, stream -> stream.mapWithIndex());
    }

    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default LongFuncList mapWithIndex(IntLongToLongFunctionPrimitive combinator) {
        return deriveToLong(this, stream -> stream.mapWithIndex(combinator));
    }

    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default <T> FuncList<T> mapToObjWithIndex(IntLongBiFunction<T> combinator) {
        return deriveToObj(this, stream -> stream.mapToObjWithIndex(combinator));
    }

    /**
     * Create a stream whose value is the combination between the mapped value of this stream and its index.
     */
    public default <T1, T> FuncList<T> mapWithIndex(LongUnaryOperator valueMapper, IntLongBiFunction<T> combinator) {
        return deriveToObj(this, stream -> stream.mapWithIndex(valueMapper, combinator));
    }

    /**
     * Create a stream whose value is the combination between the mapped value of this stream and its index.
     */
    public default <T1, T> FuncList<T> mapToObjWithIndex(LongFunction<? extends T1> valueMapper, LongObjBiFunction<? super T1, T> combinator) {
        return deriveToObj(this, stream -> stream.mapToObjWithIndex(valueMapper, combinator));
    }
}
