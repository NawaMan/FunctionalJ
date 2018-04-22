package functionalj.lens;

import java.util.Collection;
import java.util.function.Predicate;

@FunctionalInterface
public interface CollectionAccess<HOST, TYPE, COLLECTION extends Collection<TYPE>> 
        extends ObjectAccess<HOST, COLLECTION> {
    
    public default BooleanAccess<HOST> thatContains(TYPE value) {
        return booleanAccess(false, collection -> collection.contains(value));
    }
    
    public default BooleanAccess<HOST> thatContains(Predicate<TYPE> check) {
        return booleanAccess(false, collection -> collection.stream().anyMatch(check));
    }
//    
//    public default StreamField<HOST, TYPE> stream() {
//        return host -> this.apply(host).stream();
//    }
    
}
