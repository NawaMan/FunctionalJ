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
package functionalj.stream.intstream.collect;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import functionalj.stream.collect.CollectorToBooleanPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToBooleanPlus;
import functionalj.stream.longstream.collect.LongCollectorToBooleanPlus;
import lombok.val;

public interface IntCollectorToBooleanPlus<ACCUMULATED> extends IntCollectorPlus<ACCUMULATED, Boolean> {
    
    public Supplier<ACCUMULATED> supplier();
    
    public ObjIntConsumer<ACCUMULATED> intAccumulator();
    
    public BinaryOperator<ACCUMULATED> combiner();
    
    public Predicate<ACCUMULATED> finisherToBoolean();
    
    public Set<Characteristics> characteristics();
    
    public default Function<ACCUMULATED, Boolean> finisher() {
        val finisherToBoolean = finisherToBoolean();
        return accumulated -> {
            return finisherToBoolean.test(accumulated);
        };
    }
    
    // == Derived ==
    public default <SOURCE> CollectorToBooleanPlus<SOURCE, ACCUMULATED> of(ToIntFunction<SOURCE> mapper) {
        return new DerivedIntCollectorToBooleanPlus.FromObj<>(this, mapper);
    }
    
    public default IntCollectorToBooleanPlus<ACCUMULATED> of(IntUnaryOperator mapper) {
        return new DerivedIntCollectorToBooleanPlus.FromInt<>(this, mapper);
    }
    
    public default LongCollectorToBooleanPlus<ACCUMULATED> of(LongToIntFunction mapper) {
        return new DerivedIntCollectorToBooleanPlus.FromLong<>(this, mapper);
    }
    
    public default DoubleCollectorToBooleanPlus<ACCUMULATED> of(DoubleToIntFunction mapper) {
        return new DerivedIntCollectorToBooleanPlus.FromDouble<>(this, mapper);
    }
}
