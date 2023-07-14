package functionalj.lens.lenses.java.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface TemporalAccess<HOST, TEMPORAL extends Temporal> extends AnyAccess<HOST, TEMPORAL>, TemporalAccessorAccess<HOST, TEMPORAL> {

    public static <H, T extends Temporal> TemporalAccess<H, T> of(Function<H, T> func) {
        return func::apply;
    }

    public default BooleanAccessPrimitive<HOST> thatIsSupported(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.isSupported(unit);
        };
    }

    public default TemporalAccess<HOST, ? extends Temporal> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return value.with(adjuster);
        };
    }

    public default TemporalAccess<HOST, ? extends Temporal> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return value.with(field, newValue);
        };
    }

    public default TemporalAccess<HOST, ? extends Temporal> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }

    public default TemporalAccess<HOST, ? extends Temporal> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }

    public default TemporalAccess<HOST, ? extends Temporal> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }

    public default TemporalAccess<HOST, ? extends Temporal> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }

    public default LongAccessPrimitive<HOST> until(Temporal endExclusive, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.until(endExclusive, unit);
        };
    }
}
