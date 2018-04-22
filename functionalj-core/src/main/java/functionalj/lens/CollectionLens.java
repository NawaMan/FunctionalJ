package functionalj.lens;

import java.util.Collection;

@FunctionalInterface
public interface CollectionLens<HOST, TYPE, COLLECTION extends Collection<TYPE>> 
        extends ObjectLens<HOST, COLLECTION>,
        CollectionAccess<HOST, TYPE, COLLECTION> {
    
    @Override
    default COLLECTION apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
}
