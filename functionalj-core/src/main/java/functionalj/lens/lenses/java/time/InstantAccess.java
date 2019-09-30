package functionalj.lens.lenses.java.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

@FunctionalInterface
public interface InstantAccess<HOST>
                    extends TemporalAccess        <HOST, Instant>
                    ,       TemporalAdjusterAccess<HOST, Instant>
                    ,       ConcreteAccess        <HOST, Instant, InstantAccess<HOST>>{
    
    public static <H> InstantAccess<H> of(Function<H, Instant> func) {
        return func::apply;
    }
    
    public default InstantAccess<HOST> newAccess(Function<HOST, Instant> accessToValue) {
        return accessToValue::apply;
    }
    
    public default LongAccess<HOST> getEpochSecond() {
        return host -> {
            val value = apply(host);
            return value.getEpochSecond();
        };
    }
    public default IntegerAccessPrimitive<HOST> getNano() {
        return host -> {
            val value = apply(host);
            return value.getNano();
        };
    }
    
    public default InstantAccess<HOST> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return value.with(adjuster);
        };
    }
    public default InstantAccess<HOST> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return value.with(field, newValue);
        };
    }
    public default InstantAccess<HOST> truncatedTo(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.truncatedTo(unit);
        };
    }
    @Override
    public default InstantAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }

    @Override
    public default InstantAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    public default InstantAccess<HOST> plusSeconds(long secondsToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusSeconds(secondsToAdd);
        };
    }
    public default InstantAccess<HOST> plusMillis(long millisToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusMillis(millisToAdd);
        };
    }
    public default InstantAccess<HOST> plusNanos(long nanosToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusNanos(nanosToAdd);
        };
    }
    public default InstantAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    public default InstantAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    public default InstantAccess<HOST> minusSeconds(long secondsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusSeconds(secondsToSubtract);
        };
    }
    public default InstantAccess<HOST> minusMillis(long millisToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusMillis(millisToSubtract);
        };
    }
    public default InstantAccess<HOST> minusNanos(long nanosToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusNanos(nanosToSubtract);
        };
    }
    
    public default OffsetDateTimeAccess<HOST> atOffset(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.atOffset(offset);
        };
    }
    public default ZonedDateTimeAccess<HOST> atZone(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.atZone(zone);
        };
    }
    
    public default LongAccess<HOST> toEpochMilli() {
        return host -> {
            val value = apply(host);
            return value.toEpochMilli();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(Instant other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    public default BooleanAccess<HOST> thatGreaterThan(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccess<HOST> thatIsAfter(Instant other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    public default BooleanAccess<HOST> thatIsBefore(Instant other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
}
