package functionalj.lens.lenses;

import functionalj.functions.ThrowFuncs;


@FunctionalInterface
public interface LongAccessBoxed<HOST> extends LongAccess<HOST> {
    
    public default long applyAsLong(HOST host) {
        try {
            var value = applyUnsafe(host);
            return value.longValue();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
}