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
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntToLongFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import functionalj.lens.lenses.DoubleToLongAccessPrimitive;
import functionalj.lens.lenses.IntegerToLongAccessPrimitive;
import functionalj.lens.lenses.LongToLongAccessPrimitive;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamProcessor;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.intstream.CollectorPlusHelper;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import lombok.val;


public interface LongCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Long, ACCUMULATED, RESULT>,
            LongStreamProcessor<RESULT> {
    
    Supplier<ACCUMULATED>         supplier();
    ObjLongConsumer<ACCUMULATED>  longAccumulator();
    BinaryOperator<ACCUMULATED>   combiner();
    Function<ACCUMULATED, RESULT> finisher();
    
    public default Set<Characteristics> characteristics() {
        return CollectorPlusHelper.unorderedConcurrent();
    }
    
    public default Collector<Long, ACCUMULATED, RESULT> collector() {
        return this;
    }
    
    public default BiConsumer<ACCUMULATED, Long> accumulator() {
        return longAccumulator()::accept;
    }
    
    // == of - to other collector plus ==
    
    public default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToLongFunction<SOURCE> mapper) {
        return new LongCollectorPlusFrom<>(this, mapper);
    }
    
    public default IntCollectorPlus<ACCUMULATED, RESULT> of(IntToLongFunction mapper) {
        return new LongCollectorPlusFromInt<>(this, mapper);
    }
    
    public default <SOURCE> IntCollectorPlus<ACCUMULATED, RESULT> of(IntegerToLongAccessPrimitive mapper) {
        return new LongCollectorPlusFromInt<>(this, mapper);
    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> of(LongUnaryOperator mapper) {
        return new LongCollectorPlusFromLong<>(this, mapper);
    }
    
    public default <SOURCE> LongCollectorPlus<ACCUMULATED, RESULT> of(LongToLongAccessPrimitive mapper) {
        return new LongCollectorPlusFromLong<>(this, mapper);
    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleToLongFunction mapper) {
        return new LongCollectorPlusFromDouble<>(this, mapper);
    }
    
    public default <SOURCE> DoubleCollectorPlus<ACCUMULATED, RESULT> of(DoubleToLongAccessPrimitive mapper) {
        return new LongCollectorPlusFromDouble<>(this, mapper);
    }
    
    //-- Suitable for Lambda -- use explicit name --
    
    public default IntCollectorPlus<ACCUMULATED, RESULT> ofInt(IntToLongFunction mapper) {
        return new LongCollectorPlusFromInt<>(this, mapper);
    }
    
    public default LongCollectorPlus<ACCUMULATED, RESULT> ofLong(LongUnaryOperator mapper) {
        return new LongCollectorPlusFromLong<>(this, mapper);
    }
    
    public default DoubleCollectorPlus<ACCUMULATED, RESULT> ofLong(DoubleToLongFunction mapper) {
        return new LongCollectorPlusFromDouble<>(this, mapper);
    }
    
}

//== Implementation ==

class LongCollectorPlusBacked<ACCUMULATED, RESULT> {
    
    protected final LongCollectorPlus<ACCUMULATED, RESULT> longCollector;
    
    public LongCollectorPlusBacked(
            LongCollectorPlus<ACCUMULATED, RESULT> intCollector) {
        this.longCollector = intCollector;
    }
    
    public Supplier<ACCUMULATED> supplier() {
        return longCollector.supplier();
    }
    
    public BinaryOperator<ACCUMULATED> combiner() {
        return longCollector.combiner();
    }
    
    public Function<ACCUMULATED, RESULT> finisher() {
        return longCollector.finisher();
    }
    
    public Set<Characteristics> characteristics() {
        return longCollector.characteristics();
    }
    
}

class LongCollectorPlusFrom<SOURCE, ACCUMULATED, RESULT> 
        extends LongCollectorPlusBacked<ACCUMULATED, RESULT>
        implements
            CollectorPlus<SOURCE, ACCUMULATED, RESULT>,
            StreamProcessor<SOURCE, RESULT> {
    
    private final ToLongFunction<SOURCE> mapper;
    
    public LongCollectorPlusFrom(
            LongCollectorPlus<ACCUMULATED, RESULT> collector,
            ToLongFunction<SOURCE>                 mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public BiConsumer<ACCUMULATED, SOURCE> accumulator() {
        val accumulator = longCollector.accumulator();
        return (a, s) -> {
            val i = mapper.applyAsLong(s);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public RESULT process(StreamPlus<? extends SOURCE> stream) {
        return longCollector.process(stream.mapToLong(mapper));
    }
    
    @Override
    public Collector<SOURCE, ACCUMULATED, RESULT> collector() {
        return this;
    }
    
}

class LongCollectorPlusFromLong<ACCUMULATED, RESULT>
        extends LongCollectorPlusBacked<ACCUMULATED, RESULT>
        implements LongCollectorPlus<ACCUMULATED, RESULT> {
    
    private final LongUnaryOperator mapper;
    
    public LongCollectorPlusFromLong(
            LongCollectorPlus<ACCUMULATED, RESULT> intCollector,
            LongUnaryOperator                      mapper) {
        super(intCollector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjLongConsumer<ACCUMULATED> longAccumulator() {
        val accumulator = longCollector.accumulator();
        return (a, l) -> {
            val i = mapper.applyAsLong(l);
            accumulator.accept(a, i);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Long> accumulator() {
        val accumulator = longCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsLong(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(LongStreamPlus stream) {
        return longCollector.process(stream.map(mapper));
    }
}

class LongCollectorPlusFromInt<ACCUMULATED, RESULT> 
        extends LongCollectorPlusBacked<ACCUMULATED, RESULT>
        implements IntCollectorPlus<ACCUMULATED, RESULT> {
    
    private final IntToLongFunction mapper;
    
    public LongCollectorPlusFromInt(
            LongCollectorPlus<ACCUMULATED, RESULT> longCollector,
            IntToLongFunction                      mapper) {
        super(longCollector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjIntConsumer<ACCUMULATED> intAccumulator() {
        val accumulator = longCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsLong(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Integer> accumulator() {
        val accumulator = longCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsLong(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(IntStreamPlus stream) {
        return longCollector.process(stream.mapToLong(mapper));
    }
}

class LongCollectorPlusFromDouble<ACCUMULATED, RESULT>
        extends LongCollectorPlusBacked<ACCUMULATED, RESULT>
        implements DoubleCollectorPlus<ACCUMULATED, RESULT> {
    
    private final DoubleToLongFunction mapper;
    
    public LongCollectorPlusFromDouble(
            LongCollectorPlus<ACCUMULATED, RESULT> collector,
            DoubleToLongFunction                   mapper) {
        super(collector);
        this.mapper = mapper;
    }
    
    @Override
    public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
        return (a, l) -> {
            val i = mapper.applyAsLong(l);
            longCollector.longAccumulator().accept(a, i);
        };
    }
    
    @Override
    public BiConsumer<ACCUMULATED, Double> accumulator() {
        val accumulator = longCollector.accumulator();
        return (a, s) -> {
            val d = mapper.applyAsLong(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public RESULT process(DoubleStreamPlus stream) {
        return longCollector.process(stream.mapToLong(mapper));
    }
    
}
