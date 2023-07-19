package functionalj.lens.lenses.java.time;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface OffsetTimeAccess<HOST> extends AnyAccess<HOST, OffsetTime>, TemporalAccess<HOST, OffsetTime>, TemporalAdjusterAccess<HOST, OffsetTime>, ConcreteAccess<HOST, OffsetTime, OffsetTimeAccess<HOST>> {
    
    public static <H> OffsetTimeAccess<H> of(Function<H, OffsetTime> func) {
        return func::apply;
    }
    
    public default OffsetTimeAccess<HOST> newAccess(Function<HOST, OffsetTime> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default ZoneOffsetAccess<HOST> getOffset() {
        return host -> {
            val value = apply(host);
            return value.getOffset();
        };
    }
    
    public default OffsetTimeAccess<HOST> withOffsetSameLocal(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.withOffsetSameLocal(offset);
        };
    }
    
    public default OffsetTimeAccess<HOST> withOffsetSameInstant(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.withOffsetSameInstant(offset);
        };
    }
    
    public default LocalTimeAccess<HOST> toLocalTime() {
        return host -> {
            val value = apply(host);
            return value.toLocalTime();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getHour() {
        return host -> {
            val value = apply(host);
            return value.getHour();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getMinute() {
        return host -> {
            val value = apply(host);
            return value.getMinute();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getSecond() {
        return host -> {
            val value = apply(host);
            return value.getSecond();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getNano() {
        return host -> {
            val value = apply(host);
            return value.getNano();
        };
    }
    
    @Override
    public default OffsetTimeAccess<HOST> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return value.with(adjuster);
        };
    }
    
    @Override
    public default OffsetTimeAccess<HOST> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return value.with(field, newValue);
        };
    }
    
    public default OffsetTimeAccess<HOST> withHour(int hour) {
        return host -> {
            val value = apply(host);
            return value.withHour(hour);
        };
    }
    
    public default OffsetTimeAccess<HOST> withMinute(int minute) {
        return host -> {
            val value = apply(host);
            return value.withMinute(minute);
        };
    }
    
    public default OffsetTimeAccess<HOST> withSecond(int second) {
        return host -> {
            val value = apply(host);
            return value.withSecond(second);
        };
    }
    
    public default OffsetTimeAccess<HOST> withNano(int nanoOfSecond) {
        return host -> {
            val value = apply(host);
            return value.withNano(nanoOfSecond);
        };
    }
    
    public default OffsetTimeAccess<HOST> truncatedTo(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.truncatedTo(unit);
        };
    }
    
    @Override
    public default OffsetTimeAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    
    @Override
    public default OffsetTimeAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    
    public default OffsetTimeAccess<HOST> plusHours(long hours) {
        return host -> {
            val value = apply(host);
            return value.plusHours(hours);
        };
    }
    
    public default OffsetTimeAccess<HOST> plusMinutes(long minutes) {
        return host -> {
            val value = apply(host);
            return value.plusMinutes(minutes);
        };
    }
    
    public default OffsetTimeAccess<HOST> plusSeconds(long seconds) {
        return host -> {
            val value = apply(host);
            return value.plusSeconds(seconds);
        };
    }
    
    public default OffsetTimeAccess<HOST> plusNanos(long nanos) {
        return host -> {
            val value = apply(host);
            return value.plusNanos(nanos);
        };
    }
    
    @Override
    public default OffsetTimeAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    
    @Override
    public default OffsetTimeAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    
    public default OffsetTimeAccess<HOST> minusHours(long hours) {
        return host -> {
            val value = apply(host);
            return value.minusHours(hours);
        };
    }
    
    public default OffsetTimeAccess<HOST> minusMinutes(long minutes) {
        return host -> {
            val value = apply(host);
            return value.minusMinutes(minutes);
        };
    }
    
    public default OffsetTimeAccess<HOST> minusSeconds(long seconds) {
        return host -> {
            val value = apply(host);
            return value.minusSeconds(seconds);
        };
    }
    
    public default OffsetTimeAccess<HOST> minusNanos(long nanos) {
        return host -> {
            val value = apply(host);
            return value.minusNanos(nanos);
        };
    }
    
    public default StringAccess<HOST> format(DateTimeFormatter formatter) {
        return host -> {
            val value = apply(host);
            return value.format(formatter);
        };
    }
    
    public default OffsetDateTimeAccess<HOST> atDate(LocalDate date) {
        return host -> {
            val value = apply(host);
            return value.atDate(date);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(OffsetTime other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(OffsetTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(OffsetTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(OffsetTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(OffsetTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAfter(OffsetTime other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsBefore(OffsetTime other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsEqual(OffsetTime other) {
        return host -> {
            val value = apply(host);
            return value.isEqual(other);
        };
    }
}
