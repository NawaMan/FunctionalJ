package functionalj.lens.lenses;

import java.util.function.Function;

import functionalj.lens.core.AccessParameterized;
import functionalj.stream.IndexedItem;
import lombok.val;

@FunctionalInterface
public interface IndexedItemAccess<HOST, T, TACCESS extends AnyAccess<HOST, T>> 
        extends 
            AccessParameterized<HOST, IndexedItem<T>, T, TACCESS>, 
            ConcreteAccess<HOST, IndexedItem<T>, IndexedItemAccess<HOST, T, TACCESS>> {
    
    public AccessParameterized<HOST, IndexedItem<T>, T, TACCESS> accessParameterized();
    
    @Override
    public default IndexedItem<T> applyUnsafe(HOST host) throws Exception {
        return accessParameterized().apply(host);
    }
    
    @Override
    public default IndexedItemAccess<HOST, T, TACCESS> newAccess(Function<HOST, IndexedItem<T>> access) {
        val accessParam = new AccessParameterized<HOST, IndexedItem<T>, T, TACCESS>() {
            @Override
            public IndexedItem<T> applyUnsafe(HOST host) throws Exception {
                return access.apply(host);
            }
            @Override
            public TACCESS createSubAccessFromHost(Function<HOST, T> accessToParameter) {
                return IndexedItemAccess.this.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> accessParam;
    }
    
    @Override
    public default TACCESS createSubAccessFromHost(Function<HOST, T> accessToParameter) {
        return accessParameterized()
                .createSubAccess(IndexedItem::item);
    }
    
    public default IntegerAccessPrimitive<HOST> index() {
        return (HOST host) -> {
            return accessParameterized()
                    .apply(host)
                    .index();
        };
    }
    
    public default TACCESS item() {
        return accessParameterized()
                .createSubAccess(IndexedItem::item);
    }
    
    public default IntegerAccessPrimitive<HOST> _1() {
        return index();
    }
    
    public default TACCESS _2() {
        return item();
    }
    
    public default IntegerAccessPrimitive<HOST> first() {
        return index();
    }
    
    public default TACCESS second() {
        return item();
    }
}
