package functionalj.lens.lenses.java.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

public interface LocalTimeAccess<HOST>
                    extends AnyAccess             <HOST, LocalTime>
                    ,       TemporalAccess        <HOST, LocalTime>
                    ,       TemporalAdjusterAccess<HOST, LocalTime>
                    ,       ConcreteAccess        <HOST, LocalTime, LocalTimeAccess<HOST>> {
    
    public static <H> LocalTimeAccess<H> of(Function<H, LocalTime> func) {
        return func::apply;
    }
    
    public default LocalTimeAccess<HOST> newAccess(Function<HOST, LocalTime> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default IntegerAccess<HOST> getHour() {
        return host -> {
            val value = apply(host);
            return value.getHour();
        };
    }
    public default IntegerAccess<HOST> getMinute() {
        return host -> {
            val value = apply(host);
            return value.getMinute();
        };
    }
    public default IntegerAccess<HOST> getSecond() {
        return host -> {
            val value = apply(host);
            return value.getSecond();
        };
    }
    public default IntegerAccess<HOST> getNano() {
        return host -> {
            val value = apply(host);
            return value.getNano();
        };
    }
    public default LocalTimeAccess<HOST> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return value.with(adjuster);
        };
    }
    public default LocalTimeAccess<HOST> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return value.with(field, newValue);
        };
    }
    public default LocalTimeAccess<HOST> withHour(int hour) {
        return host -> {
            val value = apply(host);
            return value.withHour(hour);
        };
    }
    public default LocalTimeAccess<HOST> withMinute(int minute) {
        return host -> {
            val value = apply(host);
            return value.withMinute(minute);
        };
    }
    public default LocalTimeAccess<HOST> withSecond(int second) {
        return host -> {
            val value = apply(host);
            return value.withSecond(second);
        };
    }
    public default LocalTimeAccess<HOST> withNano(int nanoOfSecond) {
        return host -> {
            val value = apply(host);
            return value.withNano(nanoOfSecond);
        };
    }
    public default LocalTimeAccess<HOST> truncatedTo(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.truncatedTo(unit);
        };
    }
    public default LocalTimeAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    public default LocalTimeAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    public default LocalTimeAccess<HOST> plusHours(long hoursToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusHours(hoursToAdd);
        };
    }
    public default LocalTimeAccess<HOST> plusMinutes(long minutesToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusMinutes(minutesToAdd);
        };
    }
    public default LocalTimeAccess<HOST> plusSeconds(long secondstoAdd) {
        return host -> {
            val value = apply(host);
            return value.plusSeconds(secondstoAdd);
        };
    }
    public default LocalTimeAccess<HOST> plusNanos(long nanosToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusNanos(nanosToAdd);
        };
    }
    public default LocalTimeAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    public default LocalTimeAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    public default LocalTimeAccess<HOST> minusHours(long hoursToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusHours(hoursToSubtract);
        };
    }
    public default LocalTimeAccess<HOST> minusMinutes(long minutesToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusMinutes(minutesToSubtract);
        };
    }
    public default LocalTimeAccess<HOST> minusSeconds(long secondsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusSeconds(secondsToSubtract);
        };
    }
    public default LocalTimeAccess<HOST> minusNanos(long nanosToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusNanos(nanosToSubtract);
        };
    }
    
    public default LocalDateTimeAccess<HOST> atDate(LocalDate date) {
        return host -> {
            val value = apply(host);
            return value.atDate(date);
        };
    }
    public default OffsetTimeAccess<HOST> atOffset(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.atOffset(offset);
        };
    }
    public default IntegerAccess<HOST> toSecondOfDay() {
        return host -> {
            val value = apply(host);
            return value.toSecondOfDay();
        };
    }
    public default LongAccess<HOST> toNanoOfDay() {
        return host -> {
            val value = apply(host);
            return value.toNanoOfDay();
        };
    }
    
    public default IntegerAccess<HOST> compareTo(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    public default BooleanAccess<HOST> thatGreaterThan(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccess<HOST> thatIsAfter(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    public default BooleanAccess<HOST> thatIsBefore(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
}
