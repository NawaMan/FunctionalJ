package functionalj.stream.doublestream.collect;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

import functionalj.stream.collect.CollectorToDoublePlus;
import lombok.val;

public class DerivedCollectorToDoublePlus<SOURCE, ACCUMULATED> implements CollectorToDoublePlus<SOURCE, ACCUMULATED> {
    
    private final CollectorToDoublePlus<?, ACCUMULATED> collector;
    private final Function<SOURCE, ?>                   mapper;
    
    public <INPUT> DerivedCollectorToDoublePlus(CollectorToDoublePlus<INPUT, ACCUMULATED> collector, Function<SOURCE, INPUT> mapper) {
        this.collector = collector;
        this.mapper    = mapper;
    }
    
    @Override
    public CollectorToDoublePlus<SOURCE, ACCUMULATED> collector() {
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
    public ToDoubleFunction<ACCUMULATED> finisherToDouble() {
        return collector.finisherToDouble();
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return collector.characteristics();
    }
    
}
