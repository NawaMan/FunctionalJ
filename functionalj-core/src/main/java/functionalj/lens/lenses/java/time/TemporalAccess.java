package functionalj.lens.lenses.java.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

@FunctionalInterface
public interface TemporalAccess<HOST, TEMPORAL extends Temporal>
                    extends
                        AnyAccess             <HOST, TEMPORAL>,
                        TemporalAccessorAccess<HOST, TEMPORAL> {
    
    public default BooleanAccess<HOST> thatIsSupported(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.isSupported(unit);
        };
    }
    @SuppressWarnings("unchecked")
    public default TemporalAccess<HOST, TEMPORAL> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return (TEMPORAL)value.with(adjuster);
        };
    }
    @SuppressWarnings("unchecked")
    public default TemporalAccess<HOST, TEMPORAL> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return (TEMPORAL)value.with(field, newValue);
        };
    }
    @SuppressWarnings("unchecked")
    public default TemporalAccess<HOST, TEMPORAL> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return (TEMPORAL)value.plus(amountToAdd);
        };
    }
    @SuppressWarnings("unchecked")
    public default TemporalAccess<HOST, TEMPORAL> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (TEMPORAL)value.plus(amountToAdd, unit);
        };
    }
    @SuppressWarnings("unchecked")
    public default TemporalAccess<HOST, TEMPORAL> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return (TEMPORAL)value.minus(amountToSubtract);
        };
    }
    @SuppressWarnings("unchecked")
    public default TemporalAccess<HOST, TEMPORAL> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (TEMPORAL)value.minus(amountToSubtract, unit);
        };
    }
    
    public default LongAccess<HOST> until(Temporal endExclusive, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.until(endExclusive, unit);
        };
    }

}
