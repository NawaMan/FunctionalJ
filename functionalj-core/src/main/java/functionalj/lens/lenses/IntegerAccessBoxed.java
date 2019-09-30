package functionalj.lens.lenses;

import functionalj.functions.ThrowFuncs;
import lombok.val;

@FunctionalInterface
public interface IntegerAccessBoxed<HOST> extends IntegerAccess<HOST> {
    
    public default int applyAsInt(HOST host) {
        try {
            val integer = applyUnsafe(host);
            return integer.intValue();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
}