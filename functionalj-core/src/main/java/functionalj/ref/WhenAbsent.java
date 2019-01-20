package functionalj.ref;

import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.function.Func0;
import lombok.val;

class WhenAbsent {
    
    static <D> Func0<D> Use(D defaultValue) {
        return ()->defaultValue;
    }
    static <D> Func0<D> Get(Supplier<D> defaultSupplier) {
        if (defaultSupplier instanceof Func0)
            return (Func0<D>)defaultSupplier;
        
        return Func.from(defaultSupplier);
    }
    static <D> Func0<D> UseDefault(Class<D> dataClass) {
        return ()->{
            val provider = RefTo.defaultProvider.value();
            val value    = provider.get(dataClass);
            return value;
        };
    }
    
}
