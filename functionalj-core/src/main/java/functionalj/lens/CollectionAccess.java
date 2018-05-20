package functionalj.lens;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface CollectionAccess<HOST, COLLECTION extends Collection<TYPE>, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
        extends ObjectAccess<HOST, COLLECTION>, AccessParameterized<HOST, COLLECTION, TYPE, AnyAccess<HOST,TYPE>> {
    
    public AccessParameterized<HOST, COLLECTION, TYPE, SUBACCESS> accessParameterized();
    
    @Override
    public default COLLECTION apply(HOST input) {
        return accessParameterized().apply(input);
    }
    
    @Override
    public default SUBACCESS createSubAccess(Function<COLLECTION, TYPE> accessToSub) {
        return accessParameterized().createSubAccess(accessToSub);
    }
    
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
