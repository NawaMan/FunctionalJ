package functionalj.ref;

import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.function.Func0;

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
        return ()->RefTo.defaultProvider.value().get(dataClass);
    }
    
}
