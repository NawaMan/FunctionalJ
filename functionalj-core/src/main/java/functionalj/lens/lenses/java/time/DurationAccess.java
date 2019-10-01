package functionalj.lens.lenses.java.time;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface DurationAccess<HOST>
                    extends AnyAccess           <HOST, Duration>
                    ,       TemporalAmountAccess<HOST, Duration>
                    ,       ConcreteAccess      <HOST, Duration, DurationAccess<HOST>> {
    
    public static <H> DurationAccess<H> of(Function<H, Duration> func) {
        return func::apply;
    }
    
    public default DurationAccess<HOST> newAccess(Function<HOST, Duration> accessToValue) {
        return accessToValue::apply;
    }
    
    public default BooleanAccessPrimitive<HOST> isZero() {
        return host -> {
            val value = apply(host);
            return value.isZero();
        };
    }
    public default BooleanAccessPrimitive<HOST> isNegative() {
        return host -> {
            val value = apply(host);
            return value.isNegative();
        };
    }
    public default LongAccessPrimitive<HOST> getSeconds() {
        return host -> {
            val value = apply(host);
            return value.getSeconds();
        };
    }
    public default IntegerAccessPrimitive<HOST> getNano() {
        return host -> {
            val value = apply(host);
            return value.getNano();
        };
    }
    public default DurationAccess<HOST> withSeconds(long seconds) {
        return host -> {
            val value = apply(host);
            return value.withSeconds(seconds);
        };
    }
    public default DurationAccess<HOST> withNanos(int nanoOfSecond) {
        return host -> {
            val value = apply(host);
            return value.withNanos(nanoOfSecond);
        };
    }
    public default DurationAccess<HOST> plus(Duration duration) {
        return host -> {
            val value = apply(host);
            return value.plus(duration);
        };
     }
    public default DurationAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    public default DurationAccess<HOST> plusDays(long daysToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusDays(daysToAdd);
        };
    }
    public default DurationAccess<HOST> plusHours(long hoursToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusHours(hoursToAdd);
        };
    }
    public default DurationAccess<HOST> plusMinutes(long minutesToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusMinutes(minutesToAdd);
        };
    }
    public default DurationAccess<HOST> plusSeconds(long secondsToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusSeconds(secondsToAdd);
        };
    }
    public default DurationAccess<HOST> plusMillis(long millisToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusMillis(millisToAdd);
        };
    }
    public default DurationAccess<HOST> plusNanos(long nanosToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusNanos(nanosToAdd);
        };
    }
    public default DurationAccess<HOST> minus(Duration duration) {
        return host -> {
            val value = apply(host);
            return value.minus(duration);
        };
     }
    public default DurationAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    public default DurationAccess<HOST> minusDays(long daysToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusDays(daysToSubtract);
        };
    }
    public default DurationAccess<HOST> minusHours(long hoursToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusHours(hoursToSubtract);
        };
    }
    public default DurationAccess<HOST> minusMinutes(long minutesToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusMinutes(minutesToSubtract);
        };
    }
    public default DurationAccess<HOST> minusSeconds(long secondsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusSeconds(secondsToSubtract);
        };
    }
    public default DurationAccess<HOST> minusMillis(long millisToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusMillis(millisToSubtract);
        };
    }
    public default DurationAccess<HOST> minusNanos(long nanosToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusNanos(nanosToSubtract);
        };
    }
    public default DurationAccess<HOST> multipliedBy(long multiplicand) {
        return host -> {
            val value = apply(host);
            return value.multipliedBy(multiplicand);
        };
     }
    public default DurationAccess<HOST> dividedBy(long divisor) {
        return host -> {
            val value = apply(host);
            return value.dividedBy(divisor);
        };
     }
    public default DurationAccess<HOST> negated() {
        return host -> {
            val value = apply(host);
            return value.negated();
        };
    }
    public default DurationAccess<HOST> abs() {
        return host -> {
            val value = apply(host);
            return value.abs();
        };
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
    public default LongAccessPrimitive<HOST> toDays() {
        return host -> {
            val value = apply(host);
            return value.toDays();
        };
    }
    public default LongAccessPrimitive<HOST> toHours() {
        return host -> {
            val value = apply(host);
            return value.toHours();
        };
    }
    public default LongAccessPrimitive<HOST> toMinutes() {
        return host -> {
            val value = apply(host);
            return value.toMinutes();
        };
    }
    public default LongAccessPrimitive<HOST> toMillis() {
        return host -> {
            val value = apply(host);
            return value.toMillis();
        };
    }
    public default LongAccessPrimitive<HOST> toNanos() {
        return host -> {
            val value = apply(host);
            return value.toNanos();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(Duration other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    public default BooleanAccess<HOST> thatGreaterThan(Duration anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(Duration anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(Duration anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(Duration anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
}
