package functionalj.lens.lenses;

import functionalj.functions.ThrowFuncs;
import lombok.val;

@FunctionalInterface
public interface DoubleAccessBoxed<HOST> extends DoubleAccess<HOST> {
    
    public default double applyAsDouble(HOST host) {
        try {
            val value = applyUnsafe(host);
            return value.doubleValue();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
}