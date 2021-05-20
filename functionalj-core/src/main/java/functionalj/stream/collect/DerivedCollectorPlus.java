package functionalj.stream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import lombok.val;

public class DerivedCollectorPlus<SOURCE, ACCUMULATED, TARGET> implements CollectorPlus<SOURCE, ACCUMULATED, TARGET> {
    
    private final Collector<?, ACCUMULATED, TARGET> collector;
    private final Function<SOURCE, ?>               mapper;
    
    public <INPUT> DerivedCollectorPlus(Collector<INPUT, ACCUMULATED, TARGET> collector, Function<SOURCE, INPUT> mapper) {
        this.collector = collector;
        this.mapper    = mapper;
    }
    
    @Override
    public Collector<SOURCE, ACCUMULATED, TARGET> collector() {
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
    public Function<ACCUMULATED, TARGET> finisher() {
        return collector.finisher();
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
}
