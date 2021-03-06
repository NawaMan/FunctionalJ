package functionalj.lens.lenses.java.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

@FunctionalInterface
public interface TemporalUnitAccess<HOST, TEMPORAL_UNIT extends TemporalUnit>
                    extends AnyAccess<HOST, TEMPORAL_UNIT> {
    
    public static <H, T extends TemporalUnit> TemporalUnitAccess<H, T> of(Function<H, T> func) {
        return func::apply;
    }
    
    public default DurationAccess<HOST> getDuration() {
        return host -> {
            val value = apply(host);
            return value.getDuration();
        };
    }
    public default BooleanAccess<HOST> isDurationEstimated() {
        return host -> {
            val value = apply(host);
            return value.isDurationEstimated();
        };
    }
    public default BooleanAccess<HOST> isDateBased() {
        return host -> {
            val value = apply(host);
            return value.isDateBased();
        };
    }
    public default BooleanAccess<HOST> isTimeBased() {
        return host -> {
            val value = apply(host);
            return value.isTimeBased();
        };
    }
    public default BooleanAccess<HOST> isSupportedBy(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.isSupportedBy(temporal);
        };
    }
    public default <R extends Temporal> TemporalAccess<HOST, R> addTo(R temporal, long amount) {
        return host -> {
            val value = apply(host);
            return value.addTo(temporal, amount);
        };
    }
    public default LongAccess<HOST> between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return host -> {
            val value = apply(host);
            return value.between(temporal1Inclusive, temporal2Exclusive);
        };
    }
    
}
