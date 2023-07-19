package functionalj.lens.lenses.java.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ListAccess;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

public interface TemporalAmountAccess<HOST, TEMPORAL_AMOUNT extends TemporalAmount> extends AnyAccess<HOST, TEMPORAL_AMOUNT> {
    
    public static <H, T extends TemporalAmount> TemporalAmountAccess<H, T> of(Function<H, T> func) {
        return func::apply;
    }
    
    public default LongAccessPrimitive<HOST> get(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.get(unit);
        };
    }
    
    public default ListAccess<HOST, TemporalUnit, TemporalUnitAccess<HOST, TemporalUnit>> getUnits() {
        return ListAccess.of(this.then(TemporalAmount::getUnits), TemporalUnitAccess::of);
    }
    
    public default TemporalAccess<HOST, Temporal> addTo(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.addTo(temporal);
        };
    }
    
    public default TemporalAccess<HOST, Temporal> subtractFrom(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.subtractFrom(temporal);
        };
    }
}
