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
package functionalj.list.intlist;

import static functionalj.list.intlist.AsIntFuncListHelper.funcListOf;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import functionalj.function.IntObjBiConsumer;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.longlist.LongFuncList;
import lombok.val;

public interface IntFuncListWithMapMulti extends AsIntFuncList {

    public default <T> IntFuncList mapMulti(IntObjBiConsumer<IntConsumer> mapper) {
        val funcList = funcListOf(this);
        return IntFuncList.deriveFrom(funcList, stream -> stream.mapMulti(mapper));
    }

    public default IntFuncList mapMultiToInt(IntObjBiConsumer<IntConsumer> mapper) {
        return mapMulti(mapper);
    }

    public default LongFuncList mapMultiToLong(IntObjBiConsumer<LongConsumer> mapper) {
        val funcList = funcListOf(this);
        return LongFuncList.deriveFrom(funcList, stream -> stream.mapMultiToLong(mapper));
    }

    public default DoubleFuncList mapMultiToDouble(IntObjBiConsumer<DoubleConsumer> mapper) {
        val funcList = funcListOf(this);
        return DoubleFuncList.deriveFrom(funcList, stream -> stream.mapMultiToDouble(mapper));
    }

    public default <T> FuncList<T> mapMultiToObj(IntObjBiConsumer<Consumer<? super T>> mapper) {
        val funcList = funcListOf(this);
        return FuncList.deriveFrom(funcList, stream -> stream.mapMultiToObj(mapper));
    }
}
