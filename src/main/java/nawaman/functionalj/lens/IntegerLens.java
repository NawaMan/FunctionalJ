package nawaman.functionalj.lens;

import java.util.function.Predicate;

@FunctionalInterface
public interface IntegerLens<HOST> extends ComparableLens<HOST, Integer>, IntegerAccess<HOST> {
    
    @Override
    default Integer apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
