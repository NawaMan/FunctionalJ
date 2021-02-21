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
package functionalj.stream.longstream;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;

import functionalj.stream.CollectorPlus;
import lombok.val;


public interface LongCollectorPlus<ACCUMULATED, RESULT>
        extends
            CollectorPlus<Long, ACCUMULATED, RESULT>,
            LongStreamProcessor<RESULT> {
    
    Supplier<ACCUMULATED>         supplier();
    LongAccumulator<ACCUMULATED>  longAccumulator();
    BinaryOperator<ACCUMULATED>   combiner();
    Function<ACCUMULATED, RESULT> finisher();
    Set<Characteristics>          characteristics();
    
    
    default BiConsumer<ACCUMULATED, Long> accumulator() {
        return longAccumulator();
    }
    
    
    default <SOURCE> CollectorPlus<SOURCE, ACCUMULATED, RESULT> of(ToLongFunction<SOURCE> mapper) {
        val collector = new CollectorFromLong<>(this, mapper);
        return CollectorPlus.from(collector);
    }
    
    default LongCollectorPlus<ACCUMULATED, RESULT> ofLong(LongUnaryOperator mapper) {
        return new LongCollectorFromLong<>(this, mapper);
    }
}

class LongCollector<ACCUMULATED, RESULT>
        implements Collector<Long, ACCUMULATED, RESULT> {
    
    private final LongCollectorPlus<ACCUMULATED, RESULT> collector;
    
    public LongCollector(LongCollectorPlus<ACCUMULATED, RESULT> collector) {
        this.collector = collector;
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    @Override
    public BiConsumer<ACCUMULATED, Long> accumulator() {
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

class CollectorFromLong<SOURCE, ACCUMULATED, RESULT>
        implements Collector<SOURCE, ACCUMULATED, RESULT> {
    private final LongCollectorPlus<ACCUMULATED, RESULT> collector;
    private final ToLongFunction<SOURCE>                 mapper;
    
    public CollectorFromLong(
            LongCollectorPlus<ACCUMULATED, RESULT> collector,
            ToLongFunction<SOURCE>                 mapper) {
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
            val d = mapper.applyAsLong(s);
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

class LongCollectorFromLong<ACCUMULATED, RESULT>
        implements LongCollectorPlus<ACCUMULATED, RESULT> {
    
    private final LongCollectorPlus<ACCUMULATED, RESULT> collector;
    private final LongUnaryOperator              mapper;
    
    public LongCollectorFromLong(
            LongCollectorPlus<ACCUMULATED, RESULT> collector,
            LongUnaryOperator                      mapper) {
        this.collector = requireNonNull(collector);
        this.mapper    = requireNonNull(mapper);
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    @Override
    public LongAccumulator<ACCUMULATED> longAccumulator() {
        val accumulator = collector.accumulator();
        return (a, s)->{
            val d = mapper.applyAsLong(s);
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
    public Collector<Long, ACCUMULATED, RESULT> collector() {
        ToLongFunction<Long> newMapper = (Long l) -> mapper.applyAsLong(l);
        return new CollectorFromLong<Long, ACCUMULATED, RESULT>(collector, newMapper);
    }
    
    @Override
    public RESULT process(LongStreamPlus stream) {
        return stream.map(mapper).collect(collector);
    }
    
}
//
//TODO - Implement this.
//
//class IntCollectorFromDouble<ACCUMULATED, RESULT>
//       implements DoubleCollectorPlus<ACCUMULATED, RESULT> {
//   
//   private final DoubleCollectorPlus<ACCUMULATED, RESULT> collector;
//   private final DoubleToIntegerAccessPrimitive        mapper;
//   
//   public IntCollectorFromDouble(
//           DoubleCollectorPlus<ACCUMULATED, RESULT> collector,
//           DoubleToIntegerAccessPrimitive           mapper) {
//       this.collector = requireNonNull(collector);
//       this.mapper    = requireNonNull(mapper);
//   }
//   
//   @Override
//   public Supplier<ACCUMULATED> supplier() {
//       return collector.supplier();
//   }
//   
//   @Override
//   public DoubleAccumulator<ACCUMULATED> doubleAccumulator() {
//       val accumulator = collector.accumulator();
//       return (a, s)->{
//           val d = mapper.applyAsInt(s);
//           accumulator.accept(a, d);
//       };
//   }
//   
//   @Override
//   public BinaryOperator<ACCUMULATED> combiner() {
//       return collector.combiner();
//   }
//   
//   @Override
//   public Function<ACCUMULATED, RESULT> finisher() {
//       return collector.finisher();
//   }
//   
//   @Override
//   public Set<Characteristics> characteristics() {
//       return collector.characteristics();
//   }
//   
//   @Override
//   public Collector<Double, ACCUMULATED, RESULT> collector() {
//       ToDoubleFunction<Double> newMapper;
//       DoubleCollectorPlus<ACCUMULATED, RESULT> myC;
//       //        ToIntFunction<Integer> newMapper = (Integer i) -> mapper.applyAsInt(i);
////       return new CollectorFromDouble<Integer, ACCUMULATED, RESULT>(collector, newMapper);
//       CollectorFromDouble<Double, ACCUMULATED, RESULT> collectorFromDouble = new CollectorFromDouble<Double, ACCUMULATED, RESULT>(myC, newMapper);
//       return collectorFromDouble;
//   }
//   
//   @Override
//   public RESULT process(DoubleStreamPlus stream) {
//       return stream.mapToInt(mapper).collect(collector);
//   }
//   
//}
