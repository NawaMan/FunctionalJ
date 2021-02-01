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

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;

import functionalj.lens.lenses.DoubleToDoubleAccessPrimitive;
import functionalj.stream.CollectorPlus;
import lombok.val;


public interface DoubleCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Double, ACCUMULATED, RESULT>,
            DoubleStreamProcessor<RESULT> {
    
    Supplier<ACCUMULATED>          supplier();
    DoubleAccumulator<ACCUMULATED> doubleAccumulator();
    BinaryOperator<ACCUMULATED>    combiner();
    Function<ACCUMULATED, RESULT>  finisher();
    Set<Characteristics>           characteristics();
    
    
    default BiConsumer<ACCUMULATED, Double> accumulator() {
        return doubleAccumulator();
    }
    
    
    default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToDoubleFunction<SOURCE> mapper) {
        val collector = new CollectorFromDouble<>(this, mapper);
        return CollectorPlus.from(collector);
    }
    
    default DoubleCollectorPlus<ACCUMULATED, RESULT> ofDouble(DoubleToDoubleAccessPrimitive mapper) {
        return new DoubleCollectorFromDouble<>(this, mapper);
    }
}

class DoubleCollector<ACCUMULATED, RESULT>
        implements Collector<Double, ACCUMULATED, RESULT> {
    
    private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
    
    public DoubleCollector(DoubleCollectorPlus<ACCUMULATED, RESULT> collector) {
        this.collector = collector;
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    @Override
    public BiConsumer<ACCUMULATED, Double> accumulator() {
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

class DoubleCollectorFromDouble<ACCUMULATED, RESULT>
        implements DoubleCollectorPlus<ACCUMULATED, RESULT> {
    
    private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
    private final DoubleToDoubleAccessPrimitive            mapper;
    
    public DoubleCollectorFromDouble(
            DoubleCollectorPlus<ACCUMULATED, RESULT> collector,
            DoubleToDoubleAccessPrimitive            mapper) {
        this.collector = requireNonNull(collector);
        this.mapper    = requireNonNull(mapper);
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    @Override
    public DoubleAccumulator<ACCUMULATED> doubleAccumulator() {
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
    
    @Override
    public Collector<Double, ACCUMULATED, RESULT> collector() {
        ToDoubleFunction<Double> newMapper = (Double i) -> mapper.applyAsDouble(i);
        return new CollectorFromDouble<Double, ACCUMULATED, RESULT>(collector, newMapper);
    }
    
    @Override
    public RESULT process(DoubleStreamPlus stream) {
        return stream.map(mapper).collect(collector);
    }
    
}

// TODO - Implement this.
//
//class IntCollectorFromDouble<ACCUMULATED, RESULT>
//        implements DoubleCollectorPlus<ACCUMULATED, RESULT> {
//    
//    private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
//    private final DoubleToIntegerAccessPrimitive        mapper;
//    
//    public IntCollectorFromDouble(
//            DoubleCollectorPlus<ACCUMULATED, RESULT> collector,
//            DoubleToIntegerAccessPrimitive           mapper) {
//        this.collector = requireNonNull(collector);
//        this.mapper    = requireNonNull(mapper);
//    }
//    
//    @Override
//    public Supplier<ACCUMULATED> supplier() {
//        return collector.supplier();
//    }
//    
//    @Override
//    public DoubleAccumulator<ACCUMULATED> doubleAccumulator() {
//        val accumulator = collector.accumulator();
//        return (a, s)->{
//            val d = mapper.applyAsInt(s);
//            accumulator.accept(a, d);
//        };
//    }
//    
//    @Override
//    public BinaryOperator<ACCUMULATED> combiner() {
//        return collector.combiner();
//    }
//    
//    @Override
//    public Function<ACCUMULATED, RESULT> finisher() {
//        return collector.finisher();
//    }
//    
//    @Override
//    public Set<Characteristics> characteristics() {
//        return collector.characteristics();
//    }
//    
//    @Override
//    public Collector<Double, ACCUMULATED, RESULT> collector() {
//        ToDoubleFunction<Double> newMapper;
//        DoubleCollectorPlus<ACCUMULATED, RESULT> myC;
//        //        ToIntFunction<Integer> newMapper = (Integer i) -> mapper.applyAsInt(i);
////        return new CollectorFromDouble<Integer, ACCUMULATED, RESULT>(collector, newMapper);
//        CollectorFromDouble<Double, ACCUMULATED, RESULT> collectorFromDouble = new CollectorFromDouble<Double, ACCUMULATED, RESULT>(myC, newMapper);
//        return collectorFromDouble;
//    }
//    
//    @Override
//    public RESULT process(DoubleStreamPlus stream) {
//        return stream.mapToInt(mapper).collect(collector);
//    }
//    
//}

