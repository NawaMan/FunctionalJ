package functionalj.lens.lenses.java.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.LongAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ChronoZonedDateTimeAccess<
                    HOST, 
                    CHRONO_LOCAL_DATE      extends ChronoLocalDate, 
                    CHRONO_ZONED_DATE_TIME extends ChronoZonedDateTime<CHRONO_LOCAL_DATE>>
                        extends
                            AnyAccess             <HOST, CHRONO_ZONED_DATE_TIME>,
                            TemporalAccess        <HOST, CHRONO_ZONED_DATE_TIME> {
    
    public default LocalDateAccess<HOST> toLocalDate() {
        return host -> {
            val value = apply(host);
            return (LocalDate)value.toLocalDate();
        };
    }
    public default LocalTimeAccess<HOST> toLocalTime() {
        return host -> {
            val value = apply(host);
            return (LocalTime)value.toLocalTime();
        };
    }
    @SuppressWarnings("unchecked")
    public default <TIME extends ChronoLocalDateTime<CHRONO_LOCAL_DATE>> ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE, TIME> toLocalDateTime() {
        return host -> {
            val value = apply(host);
            return (TIME) value.toLocalDateTime();
        };
    }
    
    @SuppressWarnings("unchecked")
    public default <CHRONOLOGY extends Chronology> ChronologyAccess<HOST, CHRONOLOGY> getChronology() {
        return host -> {
            val value = apply(host);
            return (CHRONOLOGY)value.getChronology();
        };
    }
    
    public default ZoneOffsetAccess<HOST> getOffset() {
        return host -> {
            val value = apply(host);
            return value.getOffset();
        };
    }
    public default ZoneIdAccess<HOST, ZoneId> getZone() {
        return host -> {
            val value = apply(host);
            return value.getZone();
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> withEarlierOffsetAtOverlap() {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withEarlierOffsetAtOverlap();
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> withLaterOffsetAtOverlap() {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withLaterOffsetAtOverlap();
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> withZoneSameLocal(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withZoneSameLocal(zone);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> withZoneSameInstant(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withZoneSameInstant(zone);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.with(adjuster);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.with(field, newValue);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> plus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.plus(amount);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.plus(amountToAdd, unit);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> minus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.minus(amount);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_LOCAL_DATE, CHRONO_ZONED_DATE_TIME> minus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.minus(amountToAdd, unit);
        };
    }
    
    public default StringAccess<HOST> format(DateTimeFormatter formatter) {
        return host -> {
            val value = apply(host);
            return value.format(formatter);
        };
    }
    
    public default InstantAccess<HOST> toInstant() {
        return host -> {
            val value = apply(host);
            return value.toInstant();
        };
    }
    
    public default LongAccess<HOST> toEpochSecond() {
        return host -> {
            val value = apply(host);
            return value.toEpochSecond();
        };
    }
    
    // NOTE: Ok has to admit, with no time to test, these generic is likely wrong :-(
    
    public default IntegerAccess<HOST> compareTo(CHRONO_ZONED_DATE_TIME other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    // Duplicate the lt,lte,gt,gte as I am fail to make this extends ComparableAccess
    public default BooleanAccess<HOST> thatGreaterThan(CHRONO_ZONED_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(CHRONO_ZONED_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(CHRONO_ZONED_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(CHRONO_ZONED_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccess<HOST> thatIsAfter(CHRONO_ZONED_DATE_TIME other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    public default BooleanAccess<HOST> thatIsBefore(CHRONO_ZONED_DATE_TIME other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
    public default BooleanAccess<HOST> thatIsEqual(CHRONO_ZONED_DATE_TIME other) {
        return host -> {
            val value = apply(host);
            return value.isEqual(other);
        };
    }
}
