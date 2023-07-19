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
package functionalj.stream.longstream.collect;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntToLongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import functionalj.stream.collect.CollectorToBooleanPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToBooleanPlus;
import functionalj.stream.intstream.collect.IntCollectorToBooleanPlus;
import lombok.val;

public interface LongCollectorToBooleanPlus<ACCUMULATED> extends LongCollectorPlus<ACCUMULATED, Boolean> {
    
    public Supplier<ACCUMULATED> supplier();
    
    public ObjLongConsumer<ACCUMULATED> longAccumulator();
    
    public BinaryOperator<ACCUMULATED> combiner();
    
    public Predicate<ACCUMULATED> finisherToBoolean();
    
    public Set<Characteristics> characteristics();
    
    public default Function<ACCUMULATED, Boolean> finisher() {
        val finisherToDouble = finisherToBoolean();
        return accumulated -> {
            return finisherToDouble.test(accumulated);
        };
    }
    
    // == Derived ==
    public default <SOURCE> CollectorToBooleanPlus<SOURCE, ACCUMULATED> of(ToLongFunction<SOURCE> mapper) {
        return new DerivedLongCollectorToBooleanPlus.FromObj<>(this, mapper);
    }
    
    public default IntCollectorToBooleanPlus<ACCUMULATED> of(IntToLongFunction mapper) {
        return new DerivedLongCollectorToBooleanPlus.FromInt<>(this, mapper);
    }
    
    public default LongCollectorToBooleanPlus<ACCUMULATED> of(LongUnaryOperator mapper) {
        return new DerivedLongCollectorToBooleanPlus.FromLong<>(this, mapper);
    }
    
    public default DoubleCollectorToBooleanPlus<ACCUMULATED> of(DoubleToLongFunction mapper) {
        return new DerivedLongCollectorToBooleanPlus.FromDouble<>(this, mapper);
    }
}
