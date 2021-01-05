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

import static functionalj.list.doublelist.DoubleFuncList.deriveToDouble;
import static functionalj.list.doublelist.DoubleFuncList.deriveToObj;

import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import functionalj.list.FuncList;
import functionalj.streamable.Streamable;

public interface DoubleFuncListWithFlatMap extends AsDoubleFuncList {
    
    /** FlatMap with the given mapper. */
    public default <T> FuncList<T> flatMapToObj(DoubleFunction<? extends FuncList<? extends T>> mapper) {
        return deriveToObj(this, stream -> {
            return stream.flatMapToObj(value -> {
                @SuppressWarnings("unchecked")
                FuncList<T> apply = (FuncList<T>) mapper.apply(value);
                return apply.stream();
            });
        });
    }
    
    /** FlatMap with the given mapper for only the value that pass the condition. */
    public default DoubleFuncList flatMapOnly(
            DoublePredicate                          checker,
            DoubleFunction<? extends DoubleFuncList> mapper) {
        return deriveToDouble(this, stream -> {
            DoubleFunction<? extends DoubleStream> newMapper = value -> mapper.apply(value).doubleStream();
            return stream.flatMapOnly(checker, newMapper);
        });
    }
    
    /** FlatMap with the mapper if the condition is true, otherwise use another elseMapper. */
    public default DoubleFuncList flatMapIf(
            DoublePredicate                         checker,
            DoubleFunction<? extends DoubleFuncList> trueMapper,
            DoubleFunction<? extends DoubleFuncList> falseMapper) {
        DoubleFunction<? extends DoubleStream> newTrueMapper  = value -> trueMapper .apply(value).doubleStream();
        DoubleFunction<? extends DoubleStream> newFalseMapper = value -> falseMapper.apply(value).doubleStream();
        return deriveToDouble(this, stream -> stream.flatMapIf(checker, newTrueMapper, newFalseMapper));
    }
    
    /** FlatMap with the mapper if the condition is true, otherwise use another elseMapper. */
    public default <T> FuncList<T> flatMapToObjIf(
            DoublePredicate                         checker,
            DoubleFunction<? extends Streamable<T>> trueMapper,
            DoubleFunction<? extends Streamable<T>> falseMapper) {
        DoubleFunction<? extends Stream<T>> newTrueMapper  = value -> trueMapper.apply(value).stream();
        DoubleFunction<? extends Stream<T>> newFalseMapper = value -> falseMapper.apply(value).stream();
        return deriveToObj(this, stream -> stream.flatMapToObjIf(checker, newTrueMapper, newFalseMapper));
    }
    
}
