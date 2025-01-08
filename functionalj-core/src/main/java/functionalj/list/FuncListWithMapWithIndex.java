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
import static functionalj.list.FuncList.deriveFrom;

import functionalj.function.IntObjBiFunction;
import functionalj.function.IntObjToDoubleBiFunction;
import functionalj.function.IntObjToIntBiFunction;
import functionalj.function.IntObjToLongBiFunction;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.list.longlist.LongFuncList;
import functionalj.stream.AsStreamPlus;
import functionalj.stream.IndexedItem;
import functionalj.stream.doublestream.AsDoubleStreamPlus;
import functionalj.stream.intstream.AsIntStreamPlus;
import functionalj.stream.longstream.AsLongStreamPlus;
import functionalj.stream.markers.Sequential;
import lombok.val;

public interface FuncListWithMapWithIndex<DATA> extends AsFuncList<DATA> {
    
    /**
     * @return  the stream of each value and index.
     */
    public default FuncList<IndexedItem<DATA>> mapWithIndex() {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapWithIndex());
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default <TARGET> FuncList<TARGET> mapWithIndex(IntObjBiFunction<? super DATA, TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    public default <TARGET> FuncList<TARGET> mapToObjWithIndex(IntObjBiFunction<? super DATA, TARGET> combinator) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapToObjWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default IntFuncList mapToIntWithIndex(IntObjToIntBiFunction<? super DATA> combinator) {
        val funcList = funcListOf(this);
        return IntFuncList.deriveFrom(funcList, stream -> stream.mapToIntWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default LongFuncList mapToLongWithIndex(IntObjToLongBiFunction<? super DATA> combinator) {
        val funcList = funcListOf(this);
        return LongFuncList.deriveFrom(funcList, stream -> stream.mapToLongWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default DoubleFuncList mapToDoubleWithIndex(IntObjToDoubleBiFunction<? super DATA> combinator) {
        val funcList = funcListOf(this);
        return DoubleFuncList.deriveFrom(funcList, stream -> stream.mapToDoubleWithIndex(combinator));
    }
    
    //== FlatMap ==
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default <T> FuncList<T> flatMapWithIndex(IntObjBiFunction<? super DATA, ? extends AsStreamPlus<T>> combinator) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.flatMapWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default <T> FuncList<T> flatMapToObjWithIndex(IntObjBiFunction<? super DATA, ? extends AsStreamPlus<T>> combinator) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.flatMapWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default IntFuncList flatMapToIntWithIndex(IntObjBiFunction<? super DATA, ? extends AsIntStreamPlus> combinator) {
        val funcList = funcListOf(this);
        return IntFuncList.deriveFrom(funcList, stream -> stream.flatMapToIntWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default LongFuncList flatMapToLongWithIndex(IntObjBiFunction<? super DATA, ? extends AsLongStreamPlus> combinator) {
        val funcList = funcListOf(this);
        return LongFuncList.deriveFrom(funcList, stream -> stream.flatMapToLongWithIndex(combinator));
    }
    
    /**
     * Create a stream whose value is the combination between value of this stream and its index.
     */
    @Sequential
    public default DoubleFuncList flatMapToDoubleWithIndex(IntObjBiFunction<? super DATA, ? extends AsDoubleStreamPlus> combinator) {
        val funcList = funcListOf(this);
        return DoubleFuncList.deriveFrom(funcList, stream -> stream.flatMapToDoubleWithIndex(combinator));
    }
}
