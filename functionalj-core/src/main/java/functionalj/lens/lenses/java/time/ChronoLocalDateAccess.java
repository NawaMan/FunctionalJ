package functionalj.lens.lenses.java.time;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ChronoLocalDateAccess<HOST, CHRONO_LOCAL_DATE extends ChronoLocalDate>
                    extends AnyAccess             <HOST, CHRONO_LOCAL_DATE>
                    ,       TemporalAccess        <HOST, CHRONO_LOCAL_DATE>
                    ,       TemporalAdjusterAccess<HOST, CHRONO_LOCAL_DATE> {
    
    public static <H, C extends ChronoLocalDate> ChronoLocalDateAccess<H, C> of(Function<H, C> func) {
        return func::apply;
    }
    
    @SuppressWarnings("unchecked")
    public default <CHRONOLOGY extends Chronology> ChronologyAccess<HOST, CHRONOLOGY> getChronology() {
        return host -> {
            val value = apply(host);
            return (CHRONOLOGY)value.getChronology();
        };
    }
    
    public default EraAccess<HOST, Era> getEra() {
        return host -> {
            val value = apply(host);
            return value.getEra();
        };
    }
    public default BooleanAccess<HOST> thatIsLeapYear() {
        return host -> {
            val value = apply(host);
            return value.isLeapYear();
        };
    }
    public default IntegerAccessPrimitive<HOST> lengthOfMonth() {
        return host -> {
            val value = apply(host);
            return value.lengthOfMonth();
        };
    }
    public default IntegerAccessPrimitive<HOST> lengthOfYear() {
        return host -> {
            val value = apply(host);
            return value.lengthOfYear();
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateAccess<HOST, CHRONO_LOCAL_DATE> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE) value.with(adjuster);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateAccess<HOST, CHRONO_LOCAL_DATE> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE) value.with(field, newValue);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateAccess<HOST, CHRONO_LOCAL_DATE> plus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE) value.plus(amount);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateAccess<HOST, CHRONO_LOCAL_DATE> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE) value.plus(amountToAdd, unit);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateAccess<HOST, CHRONO_LOCAL_DATE> minus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE) value.minus(amount);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateAccess<HOST, CHRONO_LOCAL_DATE> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE) value.minus(amountToSubtract, unit);
        };
    }
    
    public default ChronoPeriodAccess<HOST, ? extends ChronoPeriod> until(ChronoLocalDate endDateExclusive) {
        return host -> {
            val value = apply(host);
            return value.until(endDateExclusive);
        };
    }
    
    public default StringAccess<HOST> format(DateTimeFormatter formatter) {
        return host -> {
            val value = apply(host);
            return value.format(formatter);
        };
    }
    
    public default ChronoLocalDateTimeAccess<HOST, ? extends ChronoLocalDateTime<? extends ChronoLocalDate>>
                    atTime(LocalTime localTime) {
        return host -> {
            val value = apply(host);
            return value.atTime(localTime);
        };
    }
    
    public default LongAccessPrimitive<HOST> toEpochDay() {
        return host -> {
            val value = apply(host);
            return value.toEpochDay();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(ChronoLocalDate other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    public default BooleanAccess<HOST> thatGreaterThan(ChronoLocalDate anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(ChronoLocalDate anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(ChronoLocalDate anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(ChronoLocalDate anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccess<HOST> thatIsAfter(ChronoLocalDate other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    public default BooleanAccess<HOST> thatIsBefore(ChronoLocalDate other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
    public default BooleanAccess<HOST> thatIsEqual(ChronoLocalDate other) {
        return host -> {
            val value = apply(host);
            return value.isEqual(other);
        };
    }
    
}
