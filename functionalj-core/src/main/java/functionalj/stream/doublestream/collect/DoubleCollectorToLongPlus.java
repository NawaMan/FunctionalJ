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
package functionalj.stream.doublestream.collect;

import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

import functionalj.stream.collect.CollectorToLongPlus;
import functionalj.stream.intstream.collect.IntCollectorToLongPlus;
import functionalj.stream.longstream.collect.LongCollectorToLongPlus;
import lombok.val;

public interface DoubleCollectorToLongPlus<ACCUMULATED> extends DoubleCollectorPlus<ACCUMULATED, Long> {
    
    public Supplier<ACCUMULATED>          supplier();
    public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator();
    public BinaryOperator<ACCUMULATED>    combiner();
    
    public ToLongFunction<ACCUMULATED> finisherToLong();
    
    public default Function<ACCUMULATED, Long> finisher() {
        val finisherToLong = finisherToLong();
        return accumulated -> {
            return finisherToLong.applyAsLong(accumulated);
        };
    }
    
    //== Derived ==
    
    public default <SOURCE> CollectorToLongPlus<SOURCE, ACCUMULATED> of(ToDoubleFunction<SOURCE> mapper) {
        return new DerivedDoubleCollectorToLongPlus.FromObj<>(this, mapper);
    }
    
    public default IntCollectorToLongPlus<ACCUMULATED> of(IntToDoubleFunction mapper) {
        return new DerivedDoubleCollectorToLongPlus.FromInt<>(this, mapper);
    }
    
    public default LongCollectorToLongPlus<ACCUMULATED> of(LongToDoubleFunction mapper) {
        return new DerivedDoubleCollectorToLongPlus.FromLong<>(this, mapper);
    }
    
    public default DoubleCollectorToLongPlus<ACCUMULATED> of(DoubleUnaryOperator mapper) {
        return new DerivedDoubleCollectorToLongPlus.FromDouble<>(this, mapper);
    }
}
