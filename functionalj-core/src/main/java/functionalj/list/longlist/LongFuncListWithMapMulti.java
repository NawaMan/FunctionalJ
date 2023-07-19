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

import static functionalj.list.longlist.AsLongFuncListHelper.funcListOf;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import functionalj.function.LongObjBiConsumer;
import functionalj.list.FuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.intlist.IntFuncList;
import lombok.val;

public interface LongFuncListWithMapMulti extends AsLongFuncList {
    
    public default <T> LongFuncList mapMulti(LongObjBiConsumer<LongConsumer> mapper) {
        val funcList = funcListOf(this);
        return LongFuncList.deriveFrom(funcList, stream -> stream.mapMulti(mapper));
    }
    
    public default IntFuncList mapMultiToInt(LongObjBiConsumer<IntConsumer> mapper) {
        val funcList = funcListOf(this);
        return IntFuncList.deriveFrom(funcList, stream -> stream.mapMultiToInt(mapper));
    }
    
    public default LongFuncList mapMultiToLong(LongObjBiConsumer<LongConsumer> mapper) {
        return mapMulti(mapper);
    }
    
    public default DoubleFuncList mapMultiToDouble(LongObjBiConsumer<DoubleConsumer> mapper) {
        val funcList = funcListOf(this);
        return DoubleFuncList.deriveFrom(funcList, stream -> stream.mapMultiToDouble(mapper));
    }
    
    public default <T> FuncList<T> mapMultiToObj(LongObjBiConsumer<Consumer<? super T>> mapper) {
        val funcList = funcListOf(this);
        return FuncList.deriveFrom(funcList, stream -> stream.mapMultiToObj(mapper));
    }
}
