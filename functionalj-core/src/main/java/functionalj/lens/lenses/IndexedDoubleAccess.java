package functionalj.lens.lenses;

import java.util.function.Function;

import functionalj.stream.doublestream.IndexedDouble;


@FunctionalInterface
public interface IndexedDoubleAccess<HOST> extends ConcreteAccess<HOST, IndexedDouble, IndexedDoubleAccess<HOST>> {
    
    @Override
    public default IndexedDoubleAccess<HOST> newAccess(Function<HOST, IndexedDouble> accessToValue) {
        return accessToValue::apply;
    }
    
    public default IntegerAccessPrimitive<HOST> index() {
        return (HOST host) -> apply(host).index();
    }
    
    public default DoubleAccessPrimitive<HOST> item() {
        return (HOST host) -> apply(host).item();
    }
    
    public default IntegerAccessPrimitive<HOST> _1() {
        return index();
    }
    
    public default DoubleAccessPrimitive<HOST> _2() {
        return item();
    }
    
    public default IntegerAccessPrimitive<HOST> first() {
        return index();
    }
    
    public default DoubleAccessPrimitive<HOST> second() {
        return item();
    }
    
}
