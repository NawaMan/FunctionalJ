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
package functionalj.stream.collect;

import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.stream.Collector;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;

@FunctionalInterface
public interface CollectorPlus<DATA, ACCUMULATED, RESULT> extends CollectorExtensible<DATA, ACCUMULATED, RESULT> {

    public static <D, A, R> CollectorPlus<D, A, R> from(Collector<D, A, R> collector) {
        return (collector instanceof CollectorPlus) ? (CollectorPlus<D, A, R>) collector : () -> collector;
    }

    // TODO - make it easy to create reducer
    // (DATA, DATA)->DATA
    // or
    // (DATA)->TARGET , (TARGET, TARGET) -> TARGET
    // or
    // (DATA)->ACCUMULATED , (ACCUMULATED, ACCUMULATED) -> ACCUMULATED, (ACCUMULATED) -> TARGET
    public default CollectorPlus<DATA, ACCUMULATED, RESULT> collectorPlus() {
        return this;
    }

    // == Derive ==
    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(Function<SOURCE, DATA> mapper) {
        val collector = new DerivedCollectorPlus.FromObj<>(this, mapper);
        return CollectorPlus.from(collector);
    }

    public default IntCollectorPlus<ACCUMULATED, RESULT> of(IntFunction<DATA> mapper) {
        return new DerivedCollectorPlus.FromInt<>(this, mapper);
    }

    public default LongCollectorPlus<ACCUMULATED, RESULT> of(LongFunction<DATA> mapper) {
        return new DerivedCollectorPlus.FromLong<>(this, mapper);
    }

    public default DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleFunction<DATA> mapper) {
        return new DerivedCollectorPlus.FromDouble<>(this, mapper);
    }
}
