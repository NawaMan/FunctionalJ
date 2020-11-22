package functionalj.lens.lenses.java.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface LocalDateAccess<HOST>
                    extends AnyAccess             <HOST, LocalDate>
                    ,       TemporalAccess        <HOST, LocalDate>
                    ,       TemporalAdjusterAccess<HOST, LocalDate>
                    ,       ChronoLocalDateAccess <HOST, LocalDate>
                    ,       ConcreteAccess        <HOST, LocalDate, LocalDateAccess<HOST>> {
    
    public static <H> LocalDateAccess<H> of(Function<H, LocalDate> func) {
        return func::apply;
    }
    
    public default LocalDateAccess<HOST> newAccess(Function<HOST, LocalDate> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default PeriodAccess<HOST> periodTo(LocalDate endDateExclusive) {
        return host -> {
            var value = apply(host);
            return Period.between(value, endDateExclusive);
        };
    }
    
    public default PeriodAccess<HOST> periodFrom(LocalDate startDateInclusive) {
        return host -> {
            var value = apply(host);
            return Period.between(startDateInclusive, value);
        };
    }
    
    public default IsoChronologyAccess<HOST> getIsoChronology() {
        return host -> {
            var value = apply(host);
            return value.getChronology();
        };
    }
    public default IntegerAccessPrimitive<HOST> getYear() {
        return host -> {
            var value = apply(host);
            return value.getYear();
        };
    }
    public default IntegerAccessPrimitive<HOST> getMonthValue() {
        return host -> {
            var value = apply(host);
            return value.getMonthValue();
        };
    }
    public default MonthAccess<HOST> getMonth() {
        return host -> {
            var value = apply(host);
            return value.getMonth();
        };
    }
    public default IntegerAccessPrimitive<HOST> getDayOfMonth() {
        return host -> {
            var value = apply(host);
            return value.getDayOfMonth();
        };
    }
    public default IntegerAccessPrimitive<HOST> getDayOfYear() {
        return host -> {
            var value = apply(host);
            return value.getDayOfYear();
        };
    }
    public default DayOfWeekAccess<HOST> getDayOfWeek() {
        return host -> {
            var value = apply(host);
            return value.getDayOfWeek();
        };
    }
    public default LocalDateAccess<HOST> with(TemporalAdjuster adjuster) {
        return host -> {
            var value = apply(host);
            return value.with(adjuster);
        };
    }
    public default LocalDateAccess<HOST> with(TemporalField field, long newValue) {
        return host -> {
            var value = apply(host);
            return value.with(field, newValue);
        };
    }
    public default LocalDateAccess<HOST> withYear(int year) {
        return host -> {
            var value = apply(host);
            return value.withYear(year);
        };
    }
    public default LocalDateAccess<HOST> withMonth(int month) {
        return host -> {
            var value = apply(host);
            return value.withMonth(month);
        };
    }
    public default LocalDateAccess<HOST> withDayOfMonth(int dayOfMonth) {
        return host -> {
            var value = apply(host);
            return value.withMonth(dayOfMonth);
        };
    }
    public default LocalDateAccess<HOST> withDayOfYear(int dayOfYear) {
        return host -> {
            var value = apply(host);
            return value.withMonth(dayOfYear);
        };
    }
    public default LocalDateAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            var value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    public default LocalDateAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            var value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    public default LocalDateAccess<HOST> plusYears(long yearsToAdd) {
        return host -> {
            var value = apply(host);
            return value.plusYears(yearsToAdd);
        };
    }
    public default LocalDateAccess<HOST> plusMonths(long monthsToAdd) {
        return host -> {
            var value = apply(host);
            return value.plusMonths(monthsToAdd);
        };
    }
    public default LocalDateAccess<HOST> plusWeeks(long weeksToAdd) {
        return host -> {
            var value = apply(host);
            return value.plusWeeks(weeksToAdd);
        };
    }
    public default LocalDateAccess<HOST> plusDays(long daysToAdd) {
        return host -> {
            var value = apply(host);
            return value.plusDays(daysToAdd);
        };
    }
    public default LocalDateAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            var value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    public default LocalDateAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            var value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    public default LocalDateAccess<HOST> minusYears(long yearsToSubtract) {
        return host -> {
            var value = apply(host);
            return value.minusYears(yearsToSubtract);
        };
    }
    public default LocalDateAccess<HOST> minusMonths(long monthsToSubtract) {
        return host -> {
            var value = apply(host);
            return value.minusMonths(monthsToSubtract);
        };
    }
    public default LocalDateAccess<HOST> minusWeeks(long weeksToSubtract) {
        return host -> {
            var value = apply(host);
            return value.minusWeeks(weeksToSubtract);
        };
    }
    public default LocalDateAccess<HOST> minusDays(long daysToSubtract) {
        return host -> {
            var value = apply(host);
            return value.minusDays(daysToSubtract);
        };
    }
    public default PeriodAccess<HOST> until(ChronoLocalDate endDateExclusive) {
        return host -> {
            var value = apply(host);
            return value.until(endDateExclusive);
        };
    }
    public default LocalDateTimeAccess<HOST> atTime(LocalTime time) {
        return host -> {
            var value = apply(host);
            return value.atTime(time);
        };
    }
    public default LocalDateTimeAccess<HOST> atTime(int hour, int minute) {
        return host -> {
            var value = apply(host);
            return value.atTime(hour, minute);
        };
    }
    public default LocalDateTimeAccess<HOST> atTime(int hour, int minute, int second) {
        return host -> {
            var value = apply(host);
            return value.atTime(hour, minute, second);
        };
    }
    public default LocalDateTimeAccess<HOST> atTime(int hour, int minute, int second, int nanoOfSecond) {
        return host -> {
            var value = apply(host);
            return value.atTime(hour, minute, second, nanoOfSecond);
        };
    }
    public default OffsetDateTimeAccess<HOST> atTime(OffsetTime time) {
        return host -> {
            var value = apply(host);
            return value.atTime(time);
        };
    }
    public default LocalDateTimeAccess<HOST> atStartOfDay() {
        return host -> {
            var value = apply(host);
            return value.atStartOfDay();
        };
    }
    public default ZonedDateTimeAccess<HOST> atStartOfDay(ZoneId zone) {
        return host -> {
            var value = apply(host);
            return value.atStartOfDay(zone);
        };
    }
    
}
