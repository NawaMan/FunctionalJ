package functionalj.lens.lenses.java.time;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import lombok.val;

@FunctionalInterface
public interface ZonedDateTimeAccess<HOST>
                    extends AnyAccess                <HOST, ZonedDateTime>
                    ,       TemporalAccess           <HOST, ZonedDateTime>
                    ,       ChronoZonedDateTimeAccess<HOST, LocalDate, ZonedDateTime>
                    ,       ConcreteAccess           <HOST, ZonedDateTime, ZonedDateTimeAccess<HOST>> {
    
    public static <H> ZonedDateTimeAccess<H> of(Function<H, ZonedDateTime> func) {
        return func::apply;
    }
    
    public default ZonedDateTimeAccess<HOST> newAccess(Function<HOST, ZonedDateTime> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default ZonedDateTimeAccess<HOST> withLaterOffsetAtOverlap() {
        return host -> {
            val value = apply(host);
            return value.withLaterOffsetAtOverlap();
        };
    }
    public default ZoneIdAccess<HOST, ZoneId> getZone() {
        return host -> {
            val value = apply(host);
            return value.getZone();
        };
    }
    public default ZonedDateTimeAccess<HOST> withZoneSameLocal(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.withZoneSameLocal(zone);
        };
    }
    public default ZonedDateTimeAccess<HOST> withZoneSameInstant(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.withZoneSameInstant(zone);
        };
    }
    public default ZonedDateTimeAccess<HOST> withFixedOffsetZone() {
        return host -> {
            val value = apply(host);
            return value.withFixedOffsetZone();
        };
    }
    @SuppressWarnings("unchecked")
    public default LocalDateTimeAccess<HOST> toLocalDateTime() {
        return host -> {
            val value = apply(host);
            return value.toLocalDateTime();
        };
    }
    public default LocalDateAccess<HOST> toLocalDate() {
        return host -> {
            val value = apply(host);
            return value.toLocalDate();
        };
    }
    public default IntegerAccess<HOST> getYear() {
        return host -> {
            val value = apply(host);
            return value.getYear();
        };
    }
    public default IntegerAccess<HOST> getMonthValue() {
        return host -> {
            val value = apply(host);
            return value.getMonthValue();
        };
    }
    public default MonthAccess<HOST> getMonth() {
        return host -> {
            val value = apply(host);
            return value.getMonth();
        };
    }
    public default IntegerAccess<HOST> getDayOfMonth() {
        return host -> {
            val value = apply(host);
            return value.getDayOfMonth();
        };
    }
    public default IntegerAccess<HOST> getDayOfYear() {
        return host -> {
            val value = apply(host);
            return value.getDayOfYear();
        };
    }
    public default DayOfWeekAccess<HOST> getDayOfWeek() {
        return host -> {
            val value = apply(host);
            return value.getDayOfWeek();
        };
    }
    public default LocalTimeAccess<HOST> toLocalTime() {
        return host -> {
            val value = apply(host);
            return value.toLocalTime();
        };
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
    public default ZonedDateTimeAccess<HOST> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return value.with(adjuster);
        };
    }
    public default ZonedDateTimeAccess<HOST> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return value.with(field, newValue);
        };
    }
    public default ZonedDateTimeAccess<HOST> withYear(int year) {
        return host -> {
            val value = apply(host);
            return value.withYear(year);
        };
    }
    public default ZonedDateTimeAccess<HOST> withMonth(int month) {
        return host -> {
            val value = apply(host);
            return value.withMonth(month);
        };
    }
    public default ZonedDateTimeAccess<HOST> withDayOfMonth(int dayOfMonth) {
        return host -> {
            val value = apply(host);
            return value.withDayOfMonth(dayOfMonth);
        };
    }
    public default ZonedDateTimeAccess<HOST> withDayOfYear(int dayOfYear) {
        return host -> {
            val value = apply(host);
            return value.withDayOfYear(dayOfYear);
        };
    }
    public default ZonedDateTimeAccess<HOST> withHour(int hour) {
        return host -> {
            val value = apply(host);
            return value.withHour(hour);
        };
    }
    public default ZonedDateTimeAccess<HOST> withMinute(int minute) {
        return host -> {
            val value = apply(host);
            return value.withMinute(minute);
        };
    }
    public default ZonedDateTimeAccess<HOST> withSecond(int second) {
        return host -> {
            val value = apply(host);
            return value.withSecond(second);
        };
    }
    public default ZonedDateTimeAccess<HOST> withNano(int nanoOfSecond) {
        return host -> {
            val value = apply(host);
            return value.withNano(nanoOfSecond);
        };
    }
    public default ZonedDateTimeAccess<HOST> truncatedTo(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.truncatedTo(unit);
        };
    }
    public default ZonedDateTimeAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    public default ZonedDateTimeAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusYears(long years) {
        return host -> {
            val value = apply(host);
            return value.plusYears(years);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusMonths(long months) {
        return host -> {
            val value = apply(host);
            return value.plusMonths(months);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusWeeks(long weeks) {
        return host -> {
            val value = apply(host);
            return value.plusWeeks(weeks);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusDays(long days) {
        return host -> {
            val value = apply(host);
            return value.plusDays(days);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusHours(long hours) {
        return host -> {
            val value = apply(host);
            return value.plusHours(hours);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusMinutes(long minutes) {
        return host -> {
            val value = apply(host);
            return value.plusMinutes(minutes);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusSeconds(long seconds) {
        return host -> {
            val value = apply(host);
            return value.plusSeconds(seconds);
        };
    }
    public default ZonedDateTimeAccess<HOST> plusNanos(long nanos) {
        return host -> {
            val value = apply(host);
            return value.plusNanos(nanos);
        };
    }
    public default ZonedDateTimeAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    public default ZonedDateTimeAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusYears(long years) {
        return host -> {
            val value = apply(host);
            return value.minusYears(years);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusMonths(long months) {
        return host -> {
            val value = apply(host);
            return value.minusMonths(months);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusWeeks(long weeks) {
        return host -> {
            val value = apply(host);
            return value.minusWeeks(weeks);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusDays(long days) {
        return host -> {
            val value = apply(host);
            return value.minusDays(days);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusHours(long hours) {
        return host -> {
            val value = apply(host);
            return value.minusHours(hours);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusMinutes(long minutes) {
        return host -> {
            val value = apply(host);
            return value.minusMinutes(minutes);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusSeconds(long seconds) {
        return host -> {
            val value = apply(host);
            return value.minusSeconds(seconds);
        };
    }
    public default ZonedDateTimeAccess<HOST> minusNanos(long nanos) {
        return host -> {
            val value = apply(host);
            return value.minusNanos(nanos);
        };
    }
    public default OffsetDateTimeAccess<HOST> toOffsetDateTime() {
        return host -> {
            val value = apply(host);
            return value.toOffsetDateTime();
        };
    }
    
}
