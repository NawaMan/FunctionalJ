// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.list.AsFuncListHelper.funcListOf;
import static functionalj.list.FuncList.deriveToDouble;
import static functionalj.list.FuncList.deriveToInt;
import static functionalj.list.FuncList.deriveToObj;

import java.util.function.BiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import functionalj.function.Func1;
import functionalj.function.Func10;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import functionalj.function.ObjectObjectToDoubleFunctionPrimitive;
import functionalj.function.ObjectObjectToIntFunctionPrimitive;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.StreamPlus;
import functionalj.tuple.Tuple10;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import functionalj.tuple.Tuple7;
import functionalj.tuple.Tuple8;
import functionalj.tuple.Tuple9;
import lombok.val;

public interface FuncListWithMapGroup<DATA> extends AsFuncList<DATA> {
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple2<DATA, DATA>> mapTwo() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapTwo());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple3<DATA, DATA, DATA>> mapThree() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapThree());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple4<DATA, DATA, DATA, DATA>> mapFour() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapFour());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple5<DATA, DATA, DATA, DATA, DATA>> mapFive() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapFive());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple6<DATA, DATA, DATA, DATA, DATA, DATA>> mapSix() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapSix());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple7<DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapSeven() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapSeven());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple8<DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapEight() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapEight());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple9<DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapNine() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapNine());
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<Tuple10<DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA, DATA>> mapTen() {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapTen());
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(BiFunction<? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func3<? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func4<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func5<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func6<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func7<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func8<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func9<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(Func10<? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? super DATA, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(combinator));
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default FuncList<StreamPlus<DATA>> mapGroup(int count) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(count));
    }
    
    /**
     * @return  the stream of  each previous value and each current value.
     */
    public default <TARGET> FuncList<TARGET> mapGroup(int count, Func1<? super StreamPlus<? extends DATA>, ? extends TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveToObj(funcList, stream -> stream.mapGroup(count, combinator));
    }
    
    // == Int ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapTwoToInt(ObjectObjectToIntFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        val funcList = funcListOf(this);
        return deriveToInt(funcList, stream -> stream.mapTwoToInt(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default IntFuncList mapGroupToInt(int count, ToIntFunction<? super StreamPlus<? extends DATA>> combinator) {
        val funcList = funcListOf(this);
        return deriveToInt(funcList, stream -> stream.mapGroupToInt(count, combinator));
    }
    
    // == Double ==
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default DoubleFuncList mapTwoToDouble(ObjectObjectToDoubleFunctionPrimitive<? super DATA, ? super DATA> combinator) {
        val funcList = funcListOf(this);
        return deriveToDouble(funcList, stream -> stream.mapTwoToDouble(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between the previous value and the current value of this stream.
     */
    public default DoubleFuncList mapGroupToDouble(int count, ToDoubleFunction<? super StreamPlus<? extends DATA>> combinator) {
        val funcList = funcListOf(this);
        return deriveToDouble(funcList, stream -> stream.mapGroupToDouble(count, combinator));
    }
}
