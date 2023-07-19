// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list.intlist;

import static functionalj.list.intlist.IntFuncList.deriveToDouble;
import static functionalj.list.intlist.IntFuncList.deriveToInt;
import static functionalj.list.intlist.IntFuncList.deriveToLong;
import static functionalj.list.intlist.IntFuncList.deriveToObj;
import java.util.function.IntBinaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import functionalj.function.Func1;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntIntToDoubleFunctionPrimitive;
import functionalj.function.IntIntToLongFunctionPrimitive;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.longlist.LongFuncList;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.tuple.IntIntTuple;

public interface IntFuncListWithMapGroup extends AsIntFuncList {
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapTwo(IntBinaryOperator combinator) {
        return deriveToInt(this, stream -> stream.mapTwo(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapGroup(int count, ToIntFunction<IntStreamPlus> combinator) {
        return deriveToInt(this, stream -> stream.mapGroup(count, combinator));
    }
    
    // == Object ==
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<IntIntTuple> mapTwoToObj() {
        return deriveToObj(this, stream -> stream.mapTwoToObj());
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapTwoToObj(IntIntBiFunction<TARGET> combinator) {
        return deriveToObj(this, stream -> stream.mapTwoToObj(combinator));
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<IntStreamPlus> mapGroupToObj(int count) {
        return deriveToObj(this, stream -> stream.mapGroupToObj(count));
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default <TARGET> FuncList<TARGET> mapGroupToObj(int count, Func1<IntStreamPlus, ? extends TARGET> combinator) {
        return mapGroupToObj(count).map(combinator);
    }
    
    // == Int ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapTwoToInt(IntBinaryOperator combinator) {
        return deriveToInt(this, stream -> stream.mapTwoToInt(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapGroupToInt(int count, ToIntFunction<IntStreamPlus> combinator) {
        return deriveToInt(this, stream -> stream.mapGroupToInt(count, combinator));
    }
    
    // == Long ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default LongFuncList mapTwoToLong(IntIntToLongFunctionPrimitive combinator) {
        return deriveToLong(this, stream -> stream.mapTwoToLong(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default LongFuncList mapGroupToLong(int count, ToLongFunction<IntStreamPlus> combinator) {
        return deriveToLong(this, stream -> stream.mapGroupToLong(count, combinator));
    }
    
    // == Double ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default DoubleFuncList mapTwoToDouble(IntIntToDoubleFunctionPrimitive combinator) {
        return deriveToDouble(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default DoubleFuncList mapGroupToDouble(int count, ToDoubleFunction<IntStreamPlus> combinator) {
        return deriveToDouble(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
}
