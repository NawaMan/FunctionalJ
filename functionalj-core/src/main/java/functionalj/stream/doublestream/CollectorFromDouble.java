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
package functionalj.stream.doublestream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;

import lombok.val;

public class CollectorFromDouble<SOURCE, ACCUMULATED, RESULT>
        implements Collector<SOURCE, ACCUMULATED, RESULT> {
    private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
    private final ToDoubleFunction<SOURCE>                 mapper;
    
    public CollectorFromDouble(
            DoubleCollectorPlus<ACCUMULATED, RESULT> collector,
            ToDoubleFunction<SOURCE>                 mapper) {
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
            val d = mapper.applyAsDouble(s);
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
