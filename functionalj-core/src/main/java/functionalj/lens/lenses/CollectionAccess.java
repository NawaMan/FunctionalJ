package functionalj.lens.lenses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import functionalj.lens.core.AccessParameterized;
import lombok.val;

@FunctionalInterface
public interface CollectionAccess<HOST, COLLECTION extends Collection<TYPE>, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
        extends ObjectAccess<HOST, COLLECTION>, AccessParameterized<HOST, COLLECTION, TYPE, AnyAccess<HOST,TYPE>> {
    
    public AccessParameterized<HOST, COLLECTION, TYPE, SUBACCESS> accessParameterized();
    
    @Override
    public default COLLECTION applyUnsafe(HOST host) throws Exception {
        return accessParameterized().applyUnsafe(host);
    }
    
    @Override
    public default SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToSub) {
        return accessParameterized().createSubAccessFromHost(accessToSub);
    }
    
    public default IntegerAccess<HOST> size() {
        return intAccess(0, collection -> collection.size());
    }
    
    public default BooleanAccess<HOST> thatIsEmpty() {
        return booleanAccess(false, collection -> collection.isEmpty());
    }
    
    public default BooleanAccess<HOST> thatIsNotEmpty() {
        return booleanAccess(false, collection -> !collection.isEmpty());
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
            public COLLECTION applyUnsafe(HOST host) throws Exception {
                return (COLLECTION)spec.apply(host).stream().filter(checker).collect(Collectors.toList());
            }
            @Override
            public SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return spec.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    public default ListAccess<HOST, TYPE, SUBACCESS> toList() {
        val spec        = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, List<TYPE>, TYPE, SUBACCESS>() {
            @Override
            public List<TYPE> applyUnsafe(HOST host) throws Exception{
                val collection = spec.apply(host);
                if (collection  instanceof List)
                    return (List<TYPE>)collection;
                
                return new ArrayList<TYPE>(collection);
            }
            @Override
            public SUBACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return spec.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
}
