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
package functionalj.stream.intstream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.lens.lenses.DoubleToIntegerAccessPrimitive;
import functionalj.lens.lenses.IntegerToIntegerAccessPrimitive;
import functionalj.lens.lenses.LongToIntegerAccessPrimitive;
import functionalj.stream.StreamPlus;
import functionalj.stream.Aggregator;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.intstream.CollectorPlusHelper;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntAggregator;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.collect.LongCollectorPlus;
import lombok.val;


public interface IntCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Integer, ACCUMULATED, RESULT>,
            IntAggregator<RESULT> {
    
    Supplier<ACCUMULATED>         supplier();
    ObjIntConsumer<ACCUMULATED>   intAccumulator();
    BinaryOperator<ACCUMULATED>   combiner();
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
    
    // == of - to other collector plus ==
    
    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToIntFunction<SOURCE> mapper) {
        return new IntCollectorPlusFrom<>(this, mapper);
    }
    
    public default IntCollectorPlus<ACCUMULATED, RESULT> of(IntUnaryOperator mapper) {
        return new IntCollectorPlusFromInt<>(this, mapper);
    }
    
    public default <SOURCE> IntCollectorPlus<ACCUMULATED, RESULT> of(IntegerToIntegerAccessPrimitive mapper) {
        return new IntCollectorPlusFromInt<>(this, mapper);
    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> of(LongToIntFunction mapper) {
        return new IntCollectorPlusFromLong<>(this, mapper);
    }
    
    public default <SOURCE> LongCollectorPlus<ACCUMULATED, RESULT> of(LongToIntegerAccessPrimitive mapper) {
        return new IntCollectorPlusFromLong<>(this, mapper);
    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleToIntFunction mapper) {
        return new IntCollectorPlusFromDouble<>(this, mapper);
    }
    
    public default <SOURCE> DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleToIntegerAccessPrimitive mapper) {
        return new IntCollectorPlusFromDouble<>(this, mapper);
    }
    
    //-- Suitable for Lambda -- use explicit name --
    
    public default IntCollectorPlus<ACCUMULATED, RESULT> ofInt(IntUnaryOperator mapper) {
        return new IntCollectorPlusFromInt<>(this, mapper);
    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> ofLong(LongToIntFunction mapper) {
        return new IntCollectorPlusFromLong<>(this, mapper);
    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> ofLong(DoubleToIntFunction mapper) {
        return new IntCollectorPlusFromDouble<>(this, mapper);
    }
    
}

// == Implementation ==

class IntCollectorPlusBacked<ACCUMULATED, RESULT> {
    
    protected final IntCollectorPlus<ACCUMULATED, RESULT> intCollector;
    
    public IntCollectorPlusBacked(
            IntCollectorPlus<ACCUMULATED, RESULT> intCollector) {
        this.intCollector = intCollector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return intCollector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return intCollector.combiner();
    }
    
    public Function<ACCUMULATED, RESULT> finisher() {
        return intCollector.finisher();
    }
    
    public Set<Characteristics> characteristics() {
        return intCollector.characteristics();
    }
    
}

class IntCollectorPlusFrom<SOURCE, ACCUMULATED, RESULT> 
        extends IntCollectorPlusBacked<ACCUMULATED, RESULT>
        implements
            CollectorPlus<SOURCE, ACCUMULATED, RESULT>,
            Aggregator<SOURCE, RESULT> {
    
    private final ToIntFunction<SOURCE> mapper;
    
    public IntCollectorPlusFrom(
            IntCollectorPlus<ACCUMULATED, RESULT> collector,
            ToIntFunction<SOURCE>                 mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public BiConsumer<ACCUMULATED, SOURCE> accumulator() {
        val accumulator = intCollector.accumulator();
        return (a, s) -> {
            val i = mapper.applyAsInt(s);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public RESULT process(StreamPlus<? extends SOURCE> stream) {
        return intCollector.process(stream.mapToInt(mapper));
    }
    
    @Override
    public Collector<SOURCE, ACCUMULATED, RESULT> collector() {
        return this;
    }
    
}

class IntCollectorPlusFromInt<ACCUMULATED, RESULT> 
        extends IntCollectorPlusBacked<ACCUMULATED, RESULT>
        implements IntCollectorPlus<ACCUMULATED, RESULT> {
    
    private final IntUnaryOperator mapper;
    
    public IntCollectorPlusFromInt(
            IntCollectorPlus<ACCUMULATED, RESULT> collector,
            IntUnaryOperator                      mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjIntConsumer<ACCUMULATED> intAccumulator() {
        val accumulator = intCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsInt(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Integer> accumulator() {
        val accumulator = intCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsInt(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(IntStreamPlus stream) {
        return intCollector.process(stream.map(mapper));
    }
}

class IntCollectorPlusFromLong<ACCUMULATED, RESULT>
        extends IntCollectorPlusBacked<ACCUMULATED, RESULT>
        implements LongCollectorPlus<ACCUMULATED, RESULT> {
    
    private final LongToIntFunction mapper;
    
    public IntCollectorPlusFromLong(
            IntCollectorPlus<ACCUMULATED, RESULT> intCollector, 
            LongToIntFunction                     mapper) {
        super(intCollector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjLongConsumer<ACCUMULATED> longAccumulator() {
        val accumulator = intCollector.accumulator();
        return (a, l) -> {
            val i = mapper.applyAsInt(l);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Long> accumulator() {
        val accumulator = intCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsInt(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(LongStreamPlus stream) {
        return intCollector.process(stream.mapToInt(mapper));
    }
}

class IntCollectorPlusFromDouble<ACCUMULATED, RESULT> 
        extends IntCollectorPlusBacked<ACCUMULATED, RESULT>
        implements DoubleCollectorPlus<ACCUMULATED, RESULT> {
    
    private final DoubleToIntFunction mapper;
    
    public IntCollectorPlusFromDouble(
            IntCollectorPlus<ACCUMULATED, RESULT> collector, 
            DoubleToIntFunction                   mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
        val accumulator = intCollector.accumulator();
        return (a, l) -> {
            val i = mapper.applyAsInt(l);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Double> accumulator() {
        val accumulator = intCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsInt(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(DoubleStreamPlus stream) {
        return intCollector.process(stream.mapToInt(mapper));
    }
    
}
