// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.lens.lenses.java.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;
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
import lombok.val;

@FunctionalInterface
public interface LocalDateTimeAccess<HOST> extends AnyAccess<HOST, LocalDateTime>, TemporalAccess<HOST, LocalDateTime>, TemporalAdjusterAccess<HOST, LocalDateTime>, ChronoLocalDateTimeAccess<HOST, LocalDateTime>, ConcreteAccess<HOST, LocalDateTime, LocalDateTimeAccess<HOST>> {
    
    public static <H> LocalDateTimeAccess<H> of(Function<H, LocalDateTime> func) {
        return func::apply;
    }
    
    public default LocalDateTimeAccess<HOST> newAccess(Function<HOST, LocalDateTime> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default LocalDateAccess<HOST> toLocalDate() {
        return host -> {
            val value = apply(host);
            return value.toLocalDate();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getYear() {
        return host -> {
            val value = apply(host);
            return value.getYear();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getMonthValue() {
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
    
    public default IntegerAccessPrimitive<HOST> getDayOfMonth() {
        return host -> {
            val value = apply(host);
            return value.getDayOfMonth();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getDayOfYear() {
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
    
    public default LocalDateTimeAccess<HOST> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return value.with(adjuster);
        };
    }
    
    public default LocalDateTimeAccess<HOST> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return value.with(field, newValue);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withYear(int year) {
        return host -> {
            val value = apply(host);
            return value.withYear(year);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withMonth(int month) {
        return host -> {
            val value = apply(host);
            return value.withMonth(month);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withDayOfMonth(int dayOfMonth) {
        return host -> {
            val value = apply(host);
            return value.withDayOfMonth(dayOfMonth);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withDayOfYear(int dayOfYear) {
        return host -> {
            val value = apply(host);
            return value.withDayOfYear(dayOfYear);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withHour(int hour) {
        return host -> {
            val value = apply(host);
            return value.withHour(hour);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withMinute(int minute) {
        return host -> {
            val value = apply(host);
            return value.withMinute(minute);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withSecond(int second) {
        return host -> {
            val value = apply(host);
            return value.withSecond(second);
        };
    }
    
    public default LocalDateTimeAccess<HOST> withNano(int nanoOfSecond) {
        return host -> {
            val value = apply(host);
            return value.withNano(nanoOfSecond);
        };
    }
    
    public default LocalDateTimeAccess<HOST> truncatedTo(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.truncatedTo(unit);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusYears(long years) {
        return host -> {
            val value = apply(host);
            return value.plusYears(years);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusMonths(long months) {
        return host -> {
            val value = apply(host);
            return value.plusMonths(months);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusWeeks(long weeks) {
        return host -> {
            val value = apply(host);
            return value.plusWeeks(weeks);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusDays(long days) {
        return host -> {
            val value = apply(host);
            return value.plusDays(days);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusHours(long hours) {
        return host -> {
            val value = apply(host);
            return value.plusHours(hours);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusMinutes(long minutes) {
        return host -> {
            val value = apply(host);
            return value.plusMinutes(minutes);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusSeconds(long seconds) {
        return host -> {
            val value = apply(host);
            return value.plusSeconds(seconds);
        };
    }
    
    public default LocalDateTimeAccess<HOST> plusNanos(long nanos) {
        return host -> {
            val value = apply(host);
            return value.plusNanos(nanos);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusYears(long years) {
        return host -> {
            val value = apply(host);
            return value.minusYears(years);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusMonths(long months) {
        return host -> {
            val value = apply(host);
            return value.minusMonths(months);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusWeeks(long weeks) {
        return host -> {
            val value = apply(host);
            return value.minusWeeks(weeks);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusDays(long days) {
        return host -> {
            val value = apply(host);
            return value.minusDays(days);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusHours(long hours) {
        return host -> {
            val value = apply(host);
            return value.minusHours(hours);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusMinutes(long minutes) {
        return host -> {
            val value = apply(host);
            return value.minusMinutes(minutes);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusSeconds(long seconds) {
        return host -> {
            val value = apply(host);
            return value.minusSeconds(seconds);
        };
    }
    
    public default LocalDateTimeAccess<HOST> minusNanos(long nanos) {
        return host -> {
            val value = apply(host);
            return value.minusNanos(nanos);
        };
    }
    
    public default OffsetDateTimeAccess<HOST> atOffset(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.atOffset(offset);
        };
    }
    
    public default ChronoZonedDateTimeAccess<HOST, ChronoZonedDateTime<? extends ChronoLocalDate>> atZone(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.atZone(zone);
        };
    }
    
    public default DurationAccess<HOST> durationTo(LocalDateTime endTimeExclusive) {
        return host -> {
            val value = apply(host);
            return Duration.between(value, endTimeExclusive);
        };
    }
    
    public default DurationAccess<HOST> durationFrom(LocalDateTime startTimeInclusive) {
        return host -> {
            val value = apply(host);
            return Duration.between(startTimeInclusive, value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(LocalDateTime other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(LocalDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(LocalDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(LocalDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(LocalDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAfter(LocalDateTime other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsBefore(LocalDateTime other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsEqual(LocalDateTime other) {
        return host -> {
            val value = apply(host);
            return value.isEqual(other);
        };
    }
}
