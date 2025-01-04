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
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.val;

public interface FuncListWithFlatMap<DATA> extends AsFuncList<DATA> {
    
    /**
     * FlatMap with the given mapper for only the value that pass the condition.
     */
    public default FuncList<DATA> flatMapOnly(Predicate<? super DATA> checker, Function<? super DATA, ? extends FuncList<DATA>> mapper) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.flatMapOnly(checker, value -> mapper.apply(value).stream()));
    }
    
    /**
     * FlatMap with the mapper if the condition is true, otherwise use another elseMapper.
     */
    public default <T> FuncList<T> flatMapIf(Predicate<? super DATA> checker, Function<? super DATA, ? extends FuncList<T>> mapper, Function<? super DATA, ? extends FuncList<T>> elseMapper) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> {
            return stream.flatMapIf(checker, value -> mapper.apply(value).stream(), value -> elseMapper.apply(value).stream());
        });
    }
}
