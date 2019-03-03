package functionalj.lens.lenses.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

import functionalj.lens.lenses.AnyAccess;
import lombok.val;

public interface TemporalAdjusterAccess<HOST, TEMPORAL_ADJUSTER extends TemporalAdjuster>
                    extends
                        AnyAccess<HOST, TEMPORAL_ADJUSTER> {
    
    public default TemporalAccess<HOST, Temporal> adjustInto(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.adjustInto(temporal);
        };
    }
    
}
