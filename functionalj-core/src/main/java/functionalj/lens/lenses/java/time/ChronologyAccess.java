package functionalj.lens.lenses.java.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.Map;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ChronologyAccess<HOST, CHRONOLOGY extends Chronology>
                    extends
                        AnyAccess<HOST, CHRONOLOGY> {
    
    public default StringAccess<HOST> getId() {
        return host -> {
            val value = apply(host);
            return value.getId();
        };
    }
    public default StringAccess<HOST> getCalendarType() {
        return host -> {
            val value = apply(host);
            return value.getCalendarType();
        };
    }
    
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> date(Era era, int yearOfEra, int month, int dayOfMonth) {
        return host -> {
            val value = apply(host);
            return value.date(era, yearOfEra, month, dayOfMonth);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> date(int prolepticYear, int month, int dayOfMonth) {
        return host -> {
            val value = apply(host);
            return value.date(prolepticYear, month, dayOfMonth);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> dateYearDay(Era era, int yearOfEra, int dayOfYear) {
        return host -> {
            val value = apply(host);
            return value.dateYearDay(era, yearOfEra, dayOfYear);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> dateYearDay(int prolepticYear, int dayOfYear) {
        return host -> {
            val value = apply(host);
            return value.dateYearDay(prolepticYear, dayOfYear);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> dateEpochDay(long epochDay) {
        return host -> {
            val value = apply(host);
            return value.dateEpochDay(epochDay);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> dateNow() {
        return host -> {
            val value = apply(host);
            return value.dateNow();
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> dateNow(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.dateNow(zone);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> dateNow(Clock clock) {
        return host -> {
            val value = apply(host);
            return value.dateNow(clock);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> date(TemporalAccessor temporal) {
        return host -> {
            val value = apply(host);
            return value.date(temporal);
        };
    }
    @SuppressWarnings("unchecked")
    public default <DATE extends ChronoLocalDate> ChronoLocalDateTimeAccess<HOST, DATE, ChronoLocalDateTime<DATE>> 
                    localDateTime(TemporalAccessor temporal) {
        return host -> {
            val value = apply(host);
            return (ChronoLocalDateTime<DATE>) value.localDateTime(temporal);
        };
    }
    @SuppressWarnings("unchecked")
    public default <DATE extends ChronoLocalDate> ChronoZonedDateTimeAccess<HOST, DATE, ChronoZonedDateTime<DATE>> 
            zonedDateTime(TemporalAccessor temporal) {
        return host -> {
            val value = apply(host);
            return (ChronoZonedDateTime<DATE>) value.zonedDateTime(temporal);
        };
    }
    @SuppressWarnings("unchecked")
    public default <DATE extends ChronoLocalDate> ChronoZonedDateTimeAccess<HOST, DATE, ChronoZonedDateTime<DATE>> 
            zonedDateTime(Instant instant, ZoneId zone) {
        return host -> {
            val value = apply(host);
            return (ChronoZonedDateTime<DATE>) value.zonedDateTime(instant, zone);
        };
    }
    
    public default BooleanAccess<HOST> isLeapYear(long prolepticYear) {
        return host -> {
            val value = apply(host);
            return value.isLeapYear(prolepticYear);
        };
    }
    public default IntegerAccess<HOST> prolepticYear(Era era, int yearOfEra) {
        return host -> {
            val value = apply(host);
            return value.prolepticYear(era, yearOfEra);
        };
    }
    public default EraAccess<HOST> eraOf(int eraValue) {
        return host -> {
            val value = apply(host);
            return value.eraOf(eraValue);
        };
    }
//    public default List<Era> eras();
    public default ValueRangeAccess<HOST> range(ChronoField field) {
        return host -> {
            val value = apply(host);
            return value.range(field);
        };
    }
    public default StringAccess<HOST> getDisplayName(TextStyle style, Locale locale) {
        return host -> {
            val value = apply(host);
            return value.getDisplayName(style, locale);
        };
    }
    public default ChronoLocalDateAccess<HOST, ? extends ChronoLocalDate> resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        return host -> {
            val value = apply(host);
            return value.resolveDate(fieldValues, resolverStyle);
        };
    }
    public default ChronoPeriodAccess<HOST, ? extends ChronoPeriod> period(int years, int months, int days) {
        return host -> {
            val value = apply(host);
            return value.period(years, months, days);
        };
    }
    
    public default IntegerAccess<HOST> compareTo(Chronology other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    public default BooleanAccess<HOST> thatGreaterThan(Chronology anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(Chronology anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(Chronology anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(Chronology anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
}
