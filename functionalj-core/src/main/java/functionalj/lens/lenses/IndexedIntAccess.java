package functionalj.lens.lenses;

import java.util.function.Function;

import functionalj.stream.intstream.IndexedInt;


@FunctionalInterface
public interface IndexedIntAccess<HOST> extends ConcreteAccess<HOST, IndexedInt, IndexedIntAccess<HOST>> {
    
    @Override
    public default IndexedIntAccess<HOST> newAccess(Function<HOST, IndexedInt> accessToValue) {
        return accessToValue::apply;
    }
    
    public default IntegerAccessPrimitive<HOST> index() {
        return (HOST host) -> apply(host).index();
    }
    
    public default IntegerAccessPrimitive<HOST> item() {
        return (HOST host) -> apply(host).item();
    }
    
    public default IntegerAccessPrimitive<HOST> _1() {
        return index();
    }
    
    public default IntegerAccessPrimitive<HOST> _2() {
        return item();
    }
    
    public default IntegerAccessPrimitive<HOST> first() {
        return index();
    }
    
    public default IntegerAccessPrimitive<HOST> second() {
        return item();
    }
    
}
