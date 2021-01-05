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
package functionalj.list.doublelist;

import java.util.function.DoubleBinaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import functionalj.function.DoubleDoubleBiFunction;
import functionalj.function.DoubleDoubleToIntFunctionPrimitive;
import functionalj.function.Func1;
import functionalj.list.FuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.tuple.DoubleDoubleTuple;

public interface DoubleFuncListWithMapGroup extends AsDoubleFuncList {
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleFuncList mapTwo(DoubleBinaryOperator combinator) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleFuncList mapGroup(int count, ToDoubleFunction<DoubleStreamPlus> combinator) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
    
    //== Object ==
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<DoubleDoubleTuple> mapTwoToObj() {
        return FuncList.deriveFrom(this, stream -> stream.mapTwoToObj());
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default <TARGET> FuncList<TARGET> mapGroupToObj(DoubleDoubleBiFunction<TARGET> combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapGroupToObj(combinator));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default FuncList<DoubleStreamPlus> mapGroupToObj(int count) {
        return FuncList.deriveFrom(this, stream -> stream.mapGroupToObj(count));
    }
    
    /** @return  the stream of  each previous value and each current value. */
    public default <TARGET> FuncList<TARGET> mapGroupToObj(int count, Func1<DoubleStreamPlus, ? extends TARGET> combinator) {
        return FuncList.deriveFrom(this, stream -> stream.mapGroupToObj(count, combinator));
    }
    
    //== Int ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntFuncList mapTwoToInt(DoubleDoubleToIntFunctionPrimitive combinator) {
        return IntFuncList.deriveFrom(this, stream -> stream.mapTwoToInt(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default IntFuncList mapGroupToInt(int count, ToIntFunction<DoubleStreamPlus> combinator) {
        return IntFuncList.deriveFrom(this, stream -> stream.mapGroupToInt(count, combinator));
    }
    
    //== Double ==
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleFuncList mapTwoToDouble(DoubleBinaryOperator combinator) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
    public default DoubleFuncList mapGroupToDouble(int count, ToDoubleFunction<DoubleStreamPlus> combinator) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapGroupToDouble(count, combinator));
    }
    
}
