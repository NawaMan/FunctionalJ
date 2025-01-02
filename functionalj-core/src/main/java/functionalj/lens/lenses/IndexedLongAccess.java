package functionalj.lens.lenses;

import java.util.function.Function;

import functionalj.stream.longstream.IndexedLong;


@FunctionalInterface
public interface IndexedLongAccess<HOST> extends ConcreteAccess<HOST, IndexedLong, IndexedLongAccess<HOST>> {
    
    @Override
    public default IndexedLongAccess<HOST> newAccess(Function<HOST, IndexedLong> accessToValue) {
        return accessToValue::apply;
    }
    
    public default IntegerAccessPrimitive<HOST> index() {
        return (HOST host) -> apply(host).index();
    }
    
    public default LongAccessPrimitive<HOST> item() {
        return (HOST host) -> apply(host).item();
    }
    
    public default IntegerAccessPrimitive<HOST> _1() {
        return index();
    }
    
    public default LongAccessPrimitive<HOST> _2() {
        return item();
    }
    
    public default IntegerAccessPrimitive<HOST> first() {
        return index();
    }
    
    public default LongAccessPrimitive<HOST> second() {
        return item();
    }
    
}
