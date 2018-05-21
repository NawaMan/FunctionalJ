package functionalj.lens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.val;

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
    
    public default CollectionAccess<HOST, COLLECTION, TYPE, SUBACCESS> filter(Predicate<TYPE> checker) {
        val spec        = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, COLLECTION, TYPE, SUBACCESS>() {
            @SuppressWarnings("unchecked")
            @Override
            public COLLECTION apply(HOST host) {
                return (COLLECTION)spec.apply(host).stream().filter(checker).collect(Collectors.toList());
            }
            @Override
            public SUBACCESS createSubAccess(Function<COLLECTION, TYPE> accessToSub) {
                return spec.createSubAccess(accessToSub);
            }
        };
        return () -> specWithSub;
    }
    
    public default ListAccess<HOST, List<TYPE>, TYPE, SUBACCESS> toList() {
        val spec        = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, List<TYPE>, TYPE, SUBACCESS>() {
            @Override
            public List<TYPE> apply(HOST host) {
                val collection = spec.apply(host);
                if (collection  instanceof List)
                    return (List<TYPE>)collection;
                
                return new ArrayList<TYPE>(collection);
            }
            @Override
            public SUBACCESS createSubAccess(Function<List<TYPE>, TYPE> accessToSub) {
                return spec.createSubAccess(collection -> {
                    val list = (collection  instanceof List) ? (List<TYPE>)collection : new ArrayList<TYPE>(collection);
                    return accessToSub.apply(list);
                });
            }
        };
        return () -> specWithSub;
    }
    
    
//    
//    public default StreamField<HOST, TYPE> stream() {
//        return host -> this.apply(host).stream();
//    }
    
}
