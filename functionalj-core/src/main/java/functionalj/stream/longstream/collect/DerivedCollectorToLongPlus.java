package functionalj.stream.longstream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

import functionalj.stream.collect.CollectorToLongPlus;
import lombok.val;

public class DerivedCollectorToLongPlus<SOURCE, ACCUMULATED> implements CollectorToLongPlus<SOURCE, ACCUMULATED> {
    
    private final CollectorToLongPlus<?, ACCUMULATED> collector;
    private final Function<SOURCE, ?>                   mapper;
    
    public <INPUT> DerivedCollectorToLongPlus(CollectorToLongPlus<INPUT, ACCUMULATED> collector, Function<SOURCE, INPUT> mapper) {
        this.collector = collector;
        this.mapper    = mapper;
    }
    
    @Override
    public CollectorToLongPlus<SOURCE, ACCUMULATED> collector() {
        return this;
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        return collector.supplier();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public BiConsumer<ACCUMULATED, SOURCE> accumulator() {
        val accumulator = (BiConsumer)collector.accumulator();
        return (a, s) -> {
            val d = mapper.apply(s);
            accumulator.accept(a, d);
        };
    }
    
    @Override
    public BinaryOperator<ACCUMULATED> combiner() {
        return collector.combiner();
    }
    
    @Override
    public ToLongFunction<ACCUMULATED> finisherToLong() {
        return collector.finisherToLong();
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
}
