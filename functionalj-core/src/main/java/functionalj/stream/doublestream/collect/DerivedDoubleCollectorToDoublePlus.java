package functionalj.stream.doublestream.collect;

import java.util.function.BinaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

import functionalj.stream.collect.CollectorToDoublePlus;
import functionalj.stream.doublestream.DoubleStreamPlus;

public class DerivedDoubleCollectorToDoublePlus<ACCUMULATED> implements DoubleCollectorToDoublePlus<ACCUMULATED> {
    
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
