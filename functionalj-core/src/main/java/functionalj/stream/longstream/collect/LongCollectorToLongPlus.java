// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.longstream.collect;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntToLongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

import functionalj.lens.lenses.LongAccess;
import functionalj.stream.collect.CollectorToLongPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToLongPlus;
import functionalj.stream.intstream.collect.IntCollectorToLongPlus;
import lombok.val;

public interface LongCollectorToLongPlus<ACCUMULATED> extends LongCollectorPlus<ACCUMULATED, Long> {
    
    public Supplier<ACCUMULATED> supplier();
    
    public ObjLongConsumer<ACCUMULATED> longAccumulator();
    
    public BinaryOperator<ACCUMULATED> combiner();
    
    public ToLongFunction<ACCUMULATED> finisherToLong();
    
    public Set<Characteristics> characteristics();
    
    public default Function<ACCUMULATED, Long> finisher() {
        val finisher = finisherToLong();
        return accumulated -> {
            return finisher.applyAsLong(accumulated);
        };
    }
    
    // == Derived ==
    
    public default <SOURCE> CollectorToLongPlus<SOURCE, ACCUMULATED> of(LongAccess<SOURCE> mapper) {
        return new DerivedLongCollectorToLongPlus.FromObj<>(this, mapper);
    }
    
    public default <SOURCE> CollectorToLongPlus<SOURCE, ACCUMULATED> of(ToLongFunction<SOURCE> mapper) {
        return new DerivedLongCollectorToLongPlus.FromObj<>(this, mapper);
    }
    
    public default IntCollectorToLongPlus<ACCUMULATED> of(IntToLongFunction mapper) {
        return new DerivedLongCollectorToLongPlus.FromInt<>(this, mapper);
    }
    
    public default LongCollectorToLongPlus<ACCUMULATED> of(LongUnaryOperator mapper) {
        return new DerivedLongCollectorToLongPlus.FromLong<>(this, mapper);
    }
    
    public default DoubleCollectorToLongPlus<ACCUMULATED> of(DoubleToLongFunction mapper) {
        return new DerivedLongCollectorToLongPlus.FromDouble<>(this, mapper);
    }
}
