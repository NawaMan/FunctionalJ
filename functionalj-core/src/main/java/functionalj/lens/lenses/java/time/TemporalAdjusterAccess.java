package functionalj.lens.lenses.java.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import lombok.val;

public interface TemporalAdjusterAccess<HOST, TEMPORAL_ADJUSTER extends TemporalAdjuster> extends AnyAccess<HOST, TEMPORAL_ADJUSTER> {

    public static <H, T extends TemporalAdjuster> TemporalAdjusterAccess<H, T> of(Function<H, T> func) {
        return func::apply;
    }

    public default TemporalAccess<HOST, Temporal> adjustInto(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.adjustInto(temporal);
        };
    }
}
