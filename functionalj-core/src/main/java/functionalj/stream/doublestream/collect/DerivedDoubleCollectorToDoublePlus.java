package functionalj.stream.doublestream.collect;

import java.util.function.BinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

import functionalj.stream.collect.CollectorToDoublePlus;
import functionalj.stream.doublestream.DoubleStreamPlus;

public class DerivedDoubleCollectorToDoublePlus<ACCUMULATED> implements DoubleCollectorToDoublePlus<ACCUMULATED> {
    
    private final CollectorToDoublePlus<?, ACCUMULATED> collector;
    private final DoubleFunction<?>                     mapper;
    
    public <INPUT> DerivedDoubleCollectorToDoublePlus(CollectorToDoublePlus<INPUT, ACCUMULATED> collector, DoubleFunction<INPUT> mapper) {
        this.collector = collector;
        this.mapper    = mapper;
    }
    
    @Override
    public CollectorToDoublePlus<SOURCE, ACCUMULATED> collector() {
        return this;
    }
    
    @Override
    public Supplier<ACCUMULATED> supplier() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public ObjDoubleConsumer<ACCUMULATED> doubleAccumulator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public BinaryOperator<ACCUMULATED> combiner() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public ToDoubleFunction<ACCUMULATED> finisherAsDouble() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
