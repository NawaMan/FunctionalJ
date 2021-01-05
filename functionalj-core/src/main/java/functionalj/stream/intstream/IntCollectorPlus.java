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
package functionalj.stream.intstream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

import functionalj.stream.CollectorPlus;
import lombok.val;


public interface IntCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Integer, ACCUMULATED, RESULT>,
            IntStreamProcessor<RESULT> {
    
    Supplier<ACCUMULATED>         supplier();
    IntAccumulator<ACCUMULATED>   intAccumulator();
    BinaryOperator<ACCUMULATED>   combiner();
    Function<ACCUMULATED, RESULT> finisher();
    Set<Characteristics>          characteristics();
    
    
    default BiConsumer<ACCUMULATED, Integer> accumulator() {
        return intAccumulator();
    }
    
    
    default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToIntFunction<SOURCE> mapper) {
        val collector = new CollectorFromInt<>(this, mapper);
        return CollectorPlus.from(collector);
    }
}

class IntegerCollector<ACCUMULATED, RESULT>
        implements Collector<Integer, ACCUMULATED, RESULT> {
    
    private final IntCollectorPlus<ACCUMULATED, RESULT> collector;
    
    public IntegerCollector(IntCollectorPlus<ACCUMULATED, RESULT> collector) {
        this.collector = collector;
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    @Override
    public BiConsumer<ACCUMULATED, Integer> accumulator() {
        return collector.accumulator();
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

class CollectorFromInt<SOURCE, ACCUMULATED, RESULT>
        implements Collector<SOURCE, ACCUMULATED, RESULT> {
    private final IntCollectorPlus<ACCUMULATED, RESULT> collector;
    private final ToIntFunction<SOURCE>                 mapper;
    
    public CollectorFromInt(
            IntCollectorPlus<ACCUMULATED, RESULT> collector,
            ToIntFunction<SOURCE>                 mapper) {
        this.collector = collector;
        this.mapper    = mapper;
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    @Override
    public BiConsumer<ACCUMULATED, SOURCE> accumulator() {
        val accumulator = collector.accumulator();
        return (a, s)->{
            val d = mapper.applyAsInt(s);
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
