package functionalj.ref;

import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.function.Func0;

class Else {
    
    static <D> Func0<D> ElseUse(D defaultValue) {
        return ()->defaultValue;
    }
    static <D> Func0<D> ElseGet(Supplier<D> defaultSupplier) {
        if (defaultSupplier instanceof Func0)
            return (Func0<D>)defaultSupplier;
        
        return Func.from(defaultSupplier);
    }
    static <D> Func0<D> ElseDefault(Class<D> dataClass) {
        return ()->RefTo.defaultProvider.value().get(dataClass);
    }
    
}
