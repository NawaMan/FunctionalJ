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
package functionalj.stream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import functionalj.stream.doublestream.collect.DoubleCollectorToIntPlus;
import functionalj.stream.intstream.collect.IntCollectorToIntPlus;
import functionalj.stream.longstream.collect.LongCollectorToIntPlus;
import lombok.val;

public interface CollectorToIntPlus<DATA, ACCUMULATED> extends CollectorPlus<DATA, ACCUMULATED, Integer> {
    
    public Supplier<ACCUMULATED> supplier();
    
    public BiConsumer<ACCUMULATED, DATA> accumulator();
    
    public BinaryOperator<ACCUMULATED> combiner();
    
    public ToIntFunction<ACCUMULATED> finisherToInt();
    
    public Set<Characteristics> characteristics();
    
    public default Function<ACCUMULATED, Integer> finisher() {
        val finisher = finisherToInt();
        return accumulated -> {
            return finisher.applyAsInt(accumulated);
        };
    }
    
    // == Derived ==
    public default <SOURCE> CollectorToIntPlus<SOURCE, ACCUMULATED> of(Function<SOURCE, DATA> mapper) {
        return new DerivedCollectorToIntPlus.FromObj<>(this, mapper);
    }
    
    public default IntCollectorToIntPlus<ACCUMULATED> of(IntFunction<DATA> mapper) {
        return new DerivedCollectorToIntPlus.FromInt<>(this, mapper);
    }
    
    public default LongCollectorToIntPlus<ACCUMULATED> of(LongFunction<DATA> mapper) {
        return new DerivedCollectorToIntPlus.FromLong<>(this, mapper);
    }
    
    public default DoubleCollectorToIntPlus<ACCUMULATED> of(DoubleFunction<DATA> mapper) {
        return new DerivedCollectorToIntPlus.FromDouble<>(this, mapper);
    }
}
