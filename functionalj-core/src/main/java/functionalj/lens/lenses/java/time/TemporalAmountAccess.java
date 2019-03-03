package functionalj.lens.lenses.java.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

public interface TemporalAmountAccess<HOST, TEMPORAL_AMOUNT extends TemporalAmount>
                    extends AnyAccess<HOST, TEMPORAL_AMOUNT> {
                        
    public default LongAccess<HOST> get(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.get(unit);
        };
    }
// TODO
//    List<TemporalUnit> getUnits();
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
