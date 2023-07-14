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

import static functionalj.list.longlist.LongFuncList.deriveToDouble;
import static functionalj.list.longlist.LongFuncList.deriveToInt;
import static functionalj.list.longlist.LongFuncList.deriveToLong;
import static functionalj.list.longlist.LongFuncList.deriveToObj;
import java.util.function.LongBinaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import functionalj.function.Func1;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongLongToDoubleFunctionPrimitive;
import functionalj.function.LongLongToIntFunctionPrimitive;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.tuple.LongLongTuple;

public interface LongFuncListWithMapGroup extends AsLongFuncList {

    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default LongFuncList mapTwo(LongBinaryOperator combinator) {
        return deriveToLong(this, stream -> stream.mapTwo(combinator));
    }

    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default LongFuncList mapGroup(int count, ToLongFunction<LongStreamPlus> combinator) {
        return deriveToLong(this, stream -> stream.mapGroup(count, combinator));
    }

    // == Object ==
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<LongLongTuple> mapTwoToObj() {
        return deriveToObj(this, stream -> stream.mapTwoToObj());
    }

    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapTwoToObj(LongLongBiFunction<TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapTwoToObj(combinator));
    }

    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<LongStreamPlus> mapGroupToObj(int count) {
        return deriveToObj(this, stream -> stream.mapGroupToObj(count));
    }

    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default <TARGET> FuncList<TARGET> mapGroupToObj(int count, Func1<LongStreamPlus, ? extends TARGET> combinator) {
        return mapGroupToObj(count).map(combinator);
    }

    // == Int ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapTwoToInt(LongLongToIntFunctionPrimitive combinator) {
        return deriveToInt(this, stream -> stream.mapTwoToInt(combinator));
    }

    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapGroupToInt(int count, ToIntFunction<LongStreamPlus> combinator) {
        return deriveToInt(this, stream -> stream.mapGroupToInt(count, combinator));
    }

    // == Long ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default LongFuncList mapTwoToLong(LongBinaryOperator combinator) {
        return deriveToLong(this, stream -> stream.mapTwoToLong(combinator));
    }

    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default LongFuncList mapGroupToLong(int count, ToLongFunction<LongStreamPlus> combinator) {
        return deriveToLong(this, stream -> stream.mapGroupToLong(count, combinator));
    }

    // == Double ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default DoubleFuncList mapTwoToDouble(LongLongToDoubleFunctionPrimitive combinator) {
        return deriveToDouble(this, stream -> stream.mapTwoToDouble(combinator));
    }

    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default DoubleFuncList mapGroupToDouble(int count, ToDoubleFunction<LongStreamPlus> combinator) {
        return deriveToDouble(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
}
