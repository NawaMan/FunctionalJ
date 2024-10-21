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
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import functionalj.stream.collect.CollectorToDoublePlus;
import functionalj.stream.doublestream.collect.DoubleCollectorToDoublePlus;
import functionalj.stream.intstream.collect.IntCollectorToDoublePlus;
import lombok.val;

public interface LongCollectorToDoublePlus<ACCUMULATED> extends LongCollectorPlus<ACCUMULATED, Double> {
    
    public Supplier<ACCUMULATED> supplier();
    
    public ObjLongConsumer<ACCUMULATED> longAccumulator();
    
    public BinaryOperator<ACCUMULATED> combiner();
    
    public ToDoubleFunction<ACCUMULATED> finisherToDouble();
    
    public Set<Characteristics> characteristics();
    
    public default Function<ACCUMULATED, Double> finisher() {
        val finisherToDouble = finisherToDouble();
        return accumulated -> {
            return finisherToDouble.applyAsDouble(accumulated);
        };
    }
    
    // == Derived ==
    public default <SOURCE> CollectorToDoublePlus<SOURCE, ACCUMULATED> of(ToLongFunction<SOURCE> mapper) {
        return new DerivedLongCollectorToDoublePlus.FromObj<>(this, mapper);
    }
    
    public default IntCollectorToDoublePlus<ACCUMULATED> of(IntToLongFunction mapper) {
        return new DerivedLongCollectorToDoublePlus.FromInt<>(this, mapper);
    }
    
    public default LongCollectorToDoublePlus<ACCUMULATED> of(LongUnaryOperator mapper) {
        return new DerivedLongCollectorToDoublePlus.FromLong<>(this, mapper);
    }
    
    public default DoubleCollectorToDoublePlus<ACCUMULATED> of(DoubleToLongFunction mapper) {
        return new DerivedLongCollectorToDoublePlus.FromDouble<>(this, mapper);
    }
}
