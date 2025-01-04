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
package functionalj.stream.intstream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.intstream.CollectorPlusHelper;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;

public interface IntCollectorPlus<ACCUMULATED, RESULT> extends CollectorPlus<Integer, ACCUMULATED, RESULT> {
    
    Supplier<ACCUMULATED> supplier();
    
    ObjIntConsumer<ACCUMULATED> intAccumulator();
    
    BinaryOperator<ACCUMULATED> combiner();
    
    Function<ACCUMULATED, RESULT> finisher();
    
    public default Set<Characteristics> characteristics() {
        return CollectorPlusHelper.unorderedConcurrent();
    }
    
    public default Collector<Integer, ACCUMULATED, RESULT> collector() {
        return this;
    }
    
    public default BiConsumer<ACCUMULATED, Integer> accumulator() {
        return intAccumulator()::accept;
    }
    
    // == Derive ==
    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToIntFunction<SOURCE> mapper) {
        val collector = new DerivedIntCollectorPlus.FromObj<>(this, mapper);
        return CollectorPlus.from(collector);
    }
    
    public default IntCollectorPlus<ACCUMULATED, RESULT> of(IntUnaryOperator mapper) {
        return new DerivedIntCollectorPlus.FromInt<>(this, mapper);
    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> of(LongToIntFunction mapper) {
        return new DerivedIntCollectorPlus.FromLong<>(this, mapper);
    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleToIntFunction mapper) {
        return new DerivedIntCollectorPlus.FromDouble<>(this, mapper);
    }
}
