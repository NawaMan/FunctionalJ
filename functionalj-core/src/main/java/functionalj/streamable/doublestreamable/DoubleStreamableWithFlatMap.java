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
package functionalj.streamable.doublestreamable;

import static functionalj.streamable.doublestreamable.DoubleStreamable.deriveFrom;
import static functionalj.streamable.doublestreamable.DoubleStreamable.deriveToDouble;
import static functionalj.streamable.doublestreamable.DoubleStreamable.deriveToObj;

import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import functionalj.stream.StreamPlus;
import functionalj.streamable.Streamable;

public interface DoubleStreamableWithFlatMap extends AsDoubleStreamable {
    
    /** FlatMap with the given mapper. */
    public default <T> Streamable<T> flatMapToObj(DoubleFunction<? extends Streamable<? extends T>> mapper) {
        return deriveToObj(this, stream -> {
            return stream.flatMapToObj(value -> {
                @SuppressWarnings("unchecked")
                Streamable<T> apply = (Streamable<T>)mapper.apply(value);
                StreamPlus<T> stream2 = apply.stream();
                return stream2;
            });
        });
    }
    
    /** FlatMap with the given mapper for only the value that pass the condition. */
    public default DoubleStreamable flatMapOnly(
                    DoublePredicate                            checker,
                    DoubleFunction<? extends DoubleStreamable> mapper) {
        return deriveToDouble(this, stream -> {
            DoubleFunction<? extends DoubleStream> newMapper = value -> mapper.apply(value).doubleStream();
            return stream.flatMapOnly(checker, newMapper);
        });
    }
    
    /** FlatMap with the mapper if the condition is true, otherwise use another elseMapper. */
    public default DoubleStreamable flatMapIf(
            DoublePredicate                            checker,
            DoubleFunction<? extends DoubleStreamable> trueMapper,
            DoubleFunction<? extends DoubleStreamable> falseMapper) {
        DoubleFunction<? extends DoubleStream> newTrueMapper  = value -> trueMapper .apply(value).doubleStream();
        DoubleFunction<? extends DoubleStream> newFalseMapper = value -> falseMapper.apply(value).doubleStream();
        return deriveFrom(this, stream -> {
            return stream.flatMapIf(checker, newTrueMapper, newFalseMapper);
        });
    }
    
    /** FlatMap with the mapper if the condition is true, otherwise use another elseMapper. */
    public default <T> Streamable<T> flatMapToObjIf(
            DoublePredicate                         checker,
            DoubleFunction<? extends Streamable<T>> trueMapper,
            DoubleFunction<? extends Streamable<T>> falseMapper) {
        DoubleFunction<? extends Stream<T>> newTrueMapper  = value -> trueMapper.apply(value).stream();
        DoubleFunction<? extends Stream<T>> newFalseMapper = value -> falseMapper.apply(value).stream();
        return deriveToObj(this, stream -> stream.flatMapToObjIf(checker, newTrueMapper, newFalseMapper));
    }
    
}
