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
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.intstream.CollectorPlusHelper;
import functionalj.stream.longstream.LongStreamProcessor;


public interface LongCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Long, ACCUMULATED, RESULT>,
            LongStreamProcessor<RESULT> {
    
    Supplier<ACCUMULATED>         supplier();
    LongAccumulator<ACCUMULATED>  longAccumulator();
    BinaryOperator<ACCUMULATED>   combiner();
    Function<ACCUMULATED, RESULT> finisher();
    
    public default Set<Characteristics> characteristics() {
        return CollectorPlusHelper.characteristics();
    }
    
    public default Collector<Long, ACCUMULATED, RESULT> collector() {
        return this;
    }
    
    public default BiConsumer<ACCUMULATED, Long> accumulator() {
        return longAccumulator();
    }
//    
//    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToIntFunction<SOURCE> mapper) {
//        val collector = new CollectorFromInt<>(this, mapper);
//        return CollectorPlus.from(collector);
//    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> ofLong(LongUnaryOperator mapper) {
        return new LongCollectorFromLong<>(this, mapper);
    }
}