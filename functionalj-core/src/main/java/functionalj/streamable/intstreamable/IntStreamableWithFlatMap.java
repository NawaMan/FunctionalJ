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
package functionalj.streamable.intstreamable;

import static functionalj.streamable.intstreamable.IntStreamable.deriveFrom;
import static functionalj.streamable.intstreamable.IntStreamable.deriveToInt;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public interface IntStreamableWithFlatMap extends AsIntStreamable {
    
    /** FlatMap with the given mapper for only the value that pass the condition. */
    public default IntStreamable flatMapOnly(
                    IntPredicate                         checker,
                    IntFunction<? extends IntStreamable> mapper) {
        return deriveToInt(this, stream -> {
            IntFunction<? extends IntStream> newMapper = value -> mapper.apply(value).intStream();
            return stream.flatMapOnly(checker, newMapper);
        });
    }
    
    /** FlatMap with the mapper if the condition is true, otherwise use another elseMapper. */
    public default IntStreamable flatMapIf(
            IntPredicate                         checker,
            IntFunction<? extends IntStreamable> trueMapper,
            IntFunction<? extends IntStreamable> falseMapper) {
        IntFunction<? extends IntStream> newTrueMapper  = value -> trueMapper .apply(value).intStream();
        IntFunction<? extends IntStream> newFalseMapper = value -> falseMapper.apply(value).intStream();
        return deriveFrom(this, stream -> {
            return stream.flatMapIf(checker, newTrueMapper, newFalseMapper);
        });
    }
    
}
