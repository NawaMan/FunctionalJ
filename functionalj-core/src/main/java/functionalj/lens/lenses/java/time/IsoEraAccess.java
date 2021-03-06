package functionalj.lens.lenses.java.time;

import java.time.chrono.IsoEra;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import lombok.val;

@FunctionalInterface
public interface IsoEraAccess<HOST>
                    extends AnyAccess     <HOST, IsoEra>
                    ,       EraAccess     <HOST, IsoEra>
                    ,       ConcreteAccess<HOST, IsoEra, IsoEraAccess<HOST>> {
    
    public static <H> IsoEraAccess<H> of(Function<H, IsoEra> func) {
        return func::apply;
    }
    
    public default IsoEraAccess<HOST> newAccess(Function<HOST, IsoEra> accessToValue) {
        return accessToValue::apply;
    }
    
    public default IntegerAccess<HOST> getValue() {
        return host -> {
            val value = apply(host);
            return value.getValue();
        };
    }
    
    public default BooleanAccess<HOST> isBce() {
        return host -> {
            val value = apply(host);
            return value == IsoEra.BCE;
        };
    }
    public default BooleanAccess<HOST> isCe() {
        return host -> {
            val value = apply(host);
            return value == IsoEra.CE;
        };
    }
    
}
