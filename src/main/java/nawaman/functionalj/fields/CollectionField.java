package nawaman.functionalj.fields;

import java.util.Collection;
import java.util.function.Predicate;

public interface CollectionField<HOST, TYPE, COLLECTION extends Collection<TYPE>> extends ObjectField<HOST, COLLECTION> {
    
    public default BooleanField<HOST> contains(TYPE value) {
        return booleanField(false, collection -> collection.contains(value));
    }
    
    public default BooleanField<HOST> contains(Predicate<TYPE> check) {
        return booleanField(false, collection -> collection.stream().anyMatch(check));
    }
//    
//    public default StreamField<HOST, TYPE> stream() {
//        return host -> this.apply(host).stream();
//    }
    
}
