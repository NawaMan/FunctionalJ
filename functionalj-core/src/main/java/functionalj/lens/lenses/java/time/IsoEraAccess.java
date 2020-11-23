package functionalj.lens.lenses.java.time;

import java.time.chrono.IsoEra;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
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
    
    public default IntegerAccessPrimitive<HOST> getValue() {
        return host -> {
            val value = apply(host);
            return value.getValue();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isBce() {
        return host -> {
            val value = apply(host);
            return value == IsoEra.BCE;
        };
    }
    public default BooleanAccessPrimitive<HOST> isCe() {
        return host -> {
            val value = apply(host);
            return value == IsoEra.CE;
        };
    }
    
}
