// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

public interface LocalTimeAccess<HOST> extends AnyAccess<HOST, LocalTime>, TemporalAccess<HOST, LocalTime>, TemporalAdjusterAccess<HOST, LocalTime>, ConcreteAccess<HOST, LocalTime, LocalTimeAccess<HOST>> {
    
    public static <H> LocalTimeAccess<H> of(Function<H, LocalTime> func) {
        return func::apply;
    }
    
    public default LocalTimeAccess<HOST> newAccess(Function<HOST, LocalTime> accessToValue) {
        return host -> accessToValue.apply(host);
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
    
    public default IntegerAccessPrimitive<HOST> toSecondOfDay() {
        return host -> {
            val value = apply(host);
            return value.toSecondOfDay();
        };
    }
    
    public default LongAccessPrimitive<HOST> toNanoOfDay() {
        return host -> {
            val value = apply(host);
            return value.toNanoOfDay();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(LocalTime other) {
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
    
    public default BooleanAccessPrimitive<HOST> thatIsAfter(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsBefore(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
}
