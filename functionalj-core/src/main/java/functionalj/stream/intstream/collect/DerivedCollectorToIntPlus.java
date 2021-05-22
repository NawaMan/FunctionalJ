package functionalj.stream.intstream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import functionalj.stream.collect.CollectorToIntPlus;
import lombok.val;

public class DerivedCollectorToIntPlus<SOURCE, ACCUMULATED> implements CollectorToIntPlus<SOURCE, ACCUMULATED> {
    
    private final CollectorToIntPlus<?, ACCUMULATED> collector;
    private final Function<SOURCE, ?>                mapper;
    
    public <INPUT> DerivedCollectorToIntPlus(CollectorToIntPlus<INPUT, ACCUMULATED> collector, Function<SOURCE, INPUT> mapper) {
        this.collector = collector;
        this.mapper    = mapper;
    }
    
    @Override
    public CollectorToIntPlus<SOURCE, ACCUMULATED> collector() {
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
    public ToIntFunction<ACCUMULATED> finisherToInt() {
        return collector.finisherToInt();
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
}
