package nawaman.functionalj.lens;

import java.util.List;

@FunctionalInterface
public interface ListLens<HOST, TYPE, LIST extends List<TYPE>>
        extends ObjectLens<HOST, LIST>, ListAccess<HOST, TYPE, LIST> {
    
    @Override
    default LIST apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
