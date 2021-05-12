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

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.function.Func1;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;
import lombok.val;


public interface CollectorPlus<DATA, ACCUMULATED, TARGET> 
            extends
                CollectorExtensible<DATA, ACCUMULATED, TARGET>,
                StreamProcessor<DATA, TARGET> {
    
    public static <D, A, R> CollectorPlus<D, A, R> from(Collector<D, A, R> collector) {
        return (collector instanceof CollectorPlus)
                ? (CollectorPlus<D,A,R>)collector
                : ()->collector;
    }
    
    // TODO - make it easy to create reducer
    // (DATA, DATA)->DATA
    // or
    // (DATA)->TARGET , (TARGET, TARGET) -> TARGET
    // or
    // (DATA)->ACCUMULATED , (ACCUMULATED, ACCUMULATED) -> ACCUMULATED, (ACCUMULATED) -> TARGET
    
    
    public default TARGET process(StreamPlus<? extends DATA> stream) {
        return stream.calculate(this);
    }
    
    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, TARGET> of(Func1<SOURCE, DATA> mapper) {
        val collector = new DerivedCollectorPlus<>(this, mapper);
        return CollectorPlus.from(collector);
    }
}

class DerivedCollectorPlus<SOURCE, DATA, ACCUMULATED, RESULT>
        implements Collector<SOURCE, ACCUMULATED, RESULT> {
    
    private final CollectorPlus<DATA, ACCUMULATED, RESULT> collector;
    private final Func1<SOURCE, DATA>                      mapper;
    
    public DerivedCollectorPlus(
            CollectorPlus<DATA, ACCUMULATED, RESULT> collector, 
            Func1<SOURCE, DATA>                      mapper) {
        this.collector = collector;
        this.mapper = mapper;
    }
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    @Override
    public BiConsumer<ACCUMULATED, SOURCE> accumulator() {
        val accumulator = collector.accumulator();
        return (a, s)->{
            val d = mapper.apply(s);
            accumulator.accept(a, d);
        };
    }
    @Override
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    @Override
    public Function<ACCUMULATED, RESULT> finisher() {
        return collector.finisher();
    }
    @Override
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
}

