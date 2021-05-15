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

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.doublestream.DoubleStreamProcessor;
import functionalj.stream.intstream.CollectorPlusHelper;


public interface DoubleCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Double, ACCUMULATED, RESULT>,
            DoubleStreamProcessor<RESULT> {
    
    Supplier<ACCUMULATED>          supplier();
    ObjDoubleConsumer<ACCUMULATED> doubleAccumulator();
    BinaryOperator<ACCUMULATED>    combiner();
    Function<ACCUMULATED, RESULT>  finisher();
    
    public default Set<Characteristics> characteristics() {
        return CollectorPlusHelper.unorderedConcurrent();
    }
    
    public default Collector<Double, ACCUMULATED, RESULT> collector() {
        return this;
    }
    
    public default BiConsumer<ACCUMULATED, Double> accumulator() {
        return doubleAccumulator()::accept;
    }
//    
//    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(Dou<SOURCE> mapper) {
//        val collector = new CollectorFromInt<>(this, mapper);
//        return CollectorPlus.from(collector);
//    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> ofDouble(DoubleUnaryOperator mapper) {
        return new DoubleCollectorFromDouble<>(this, mapper);
    }
}