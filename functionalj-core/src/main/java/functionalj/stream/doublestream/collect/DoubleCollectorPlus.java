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
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.lens.lenses.DoubleToDoubleAccessPrimitive;
import functionalj.lens.lenses.IntegerToDoubleAccessPrimitive;
import functionalj.lens.lenses.LongToDoubleAccessPrimitive;
import functionalj.stream.StreamPlus;
import functionalj.stream.Aggregator;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleAggregator;
import functionalj.stream.intstream.CollectorPlusHelper;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;


public interface DoubleCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Double, ACCUMULATED, RESULT>,
            DoubleAggregator<RESULT> {
    
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
    
    // == of - to other collector plus ==
    
    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToDoubleFunction<SOURCE> mapper) {
        return new DoubleCollectorPlusFrom<>(this, mapper);
    }
    
    public default IntCollectorPlus<ACCUMULATED, RESULT> of(IntToDoubleFunction mapper) {
        return new DoubleCollectorPlusFromInt<>(this, mapper);
    }
    
    public default <SOURCE> IntCollectorPlus<ACCUMULATED, RESULT> of(IntegerToDoubleAccessPrimitive mapper) {
        return new DoubleCollectorPlusFromInt<>(this, mapper);
    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> of(LongToDoubleFunction mapper) {
        return new DoubleCollectorPlusFromLong<>(this, mapper);
    }
    
    public default <SOURCE> LongCollectorPlus<ACCUMULATED, RESULT> of(LongToDoubleAccessPrimitive mapper) {
        return new DoubleCollectorPlusFromLong<>(this, mapper);
    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleUnaryOperator mapper) {
        return new DoubleCollectorPlusFromDouble<>(this, mapper);
    }
    
    public default <SOURCE> DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleToDoubleAccessPrimitive mapper) {
        return new DoubleCollectorPlusFromDouble<>(this, mapper);
    }
    
    //-- Suitable for Lambda -- use explicit name --
    
    public default IntCollectorPlus<ACCUMULATED, RESULT> ofInt(IntToDoubleFunction mapper) {
        return new DoubleCollectorPlusFromInt<>(this, mapper);
    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> ofLong(LongToDoubleFunction mapper) {
        return new DoubleCollectorPlusFromLong<>(this, mapper);
    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> ofDouble(DoubleUnaryOperator mapper) {
        return new DoubleCollectorPlusFromDouble<>(this, mapper);
    }
    
}

// == Implementation ==

class DoubleCollectorPlusBacked<ACCUMULATED, RESULT> {
    
    protected final DoubleCollectorPlus<ACCUMULATED, RESULT> doubleCollector;
    
    public DoubleCollectorPlusBacked(
            DoubleCollectorPlus<ACCUMULATED, RESULT> doubleCollector) {
        this.doubleCollector = doubleCollector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return doubleCollector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return doubleCollector.combiner();
    }
    
    public Function<ACCUMULATED, RESULT> finisher() {
        return doubleCollector.finisher();
    }
    
    public Set<Characteristics> characteristics() {
        return doubleCollector.characteristics();
    }
    
}

class DoubleCollectorPlusFrom<SOURCE, ACCUMULATED, RESULT> 
        extends DoubleCollectorPlusBacked<ACCUMULATED, RESULT>
        implements
            CollectorPlus<SOURCE, ACCUMULATED, RESULT>,
            Aggregator<SOURCE, RESULT> {
    
    private final ToDoubleFunction<SOURCE> mapper;
    
    public DoubleCollectorPlusFrom(
            DoubleCollectorPlus<ACCUMULATED, RESULT> collector,
            ToDoubleFunction<SOURCE>                 mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public BiConsumer<ACCUMULATED, SOURCE> accumulator() {
        val accumulator = doubleCollector.accumulator();
        return (a, s) -> {
            val i = mapper.applyAsDouble(s);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public RESULT process(StreamPlus<? extends SOURCE> stream) {
        return doubleCollector.process(stream.mapToDouble(mapper));
    }
    
    @Override
    public Collector<SOURCE, ACCUMULATED, RESULT> collector() {
        return this;
    }
    
}

class DoubleCollectorPlusFromDouble<ACCUMULATED, RESULT> 
        extends DoubleCollectorPlusBacked<ACCUMULATED, RESULT>
        implements DoubleCollectorPlus<ACCUMULATED, RESULT> {
    
    private final DoubleUnaryOperator mapper;
    
    public DoubleCollectorPlusFromDouble(
            DoubleCollectorPlus<ACCUMULATED, RESULT> collector,
            DoubleUnaryOperator                      mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
        val accumulator = doubleCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsDouble(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Double> accumulator() {
        val accumulator = doubleCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsDouble(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(DoubleStreamPlus stream) {
        return doubleCollector.process(stream.map(mapper));
    }
}

class DoubleCollectorPlusFromLong<ACCUMULATED, RESULT>
        extends DoubleCollectorPlusBacked<ACCUMULATED, RESULT>
        implements LongCollectorPlus<ACCUMULATED, RESULT> {
    
    private final LongToDoubleFunction mapper;
    
    public DoubleCollectorPlusFromLong(
            DoubleCollectorPlus<ACCUMULATED, RESULT> doubleCollector, 
            LongToDoubleFunction                     mapper) {
        super(doubleCollector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjLongConsumer<ACCUMULATED> longAccumulator() {
        val accumulator = doubleCollector.accumulator();
        return (a, l) -> {
            val i = mapper.applyAsDouble(l);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Long> accumulator() {
        val accumulator = doubleCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsDouble(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(LongStreamPlus stream) {
        return doubleCollector.process(stream.mapToDouble(mapper));
    }
}

class DoubleCollectorPlusFromInt<ACCUMULATED, RESULT> 
     extends DoubleCollectorPlusBacked<ACCUMULATED, RESULT>
     implements IntCollectorPlus<ACCUMULATED, RESULT> {
    
    private final IntToDoubleFunction mapper;
    
    public DoubleCollectorPlusFromInt(
            DoubleCollectorPlus<ACCUMULATED, RESULT> collector,
            IntToDoubleFunction                      mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjIntConsumer<ACCUMULATED> intAccumulator() {
        val accumulator = doubleCollector.accumulator();
        return (a, l) -> {
            val i = mapper.applyAsDouble(l);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Integer> accumulator() {
        val accumulator = doubleCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsDouble(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(IntStreamPlus stream) {
        return doubleCollector.process(stream.mapToDouble(mapper));
    }
    
}
