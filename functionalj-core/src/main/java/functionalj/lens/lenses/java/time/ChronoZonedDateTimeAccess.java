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
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME extends ChronoZonedDateTime<? extends ChronoLocalDate>> extends AnyAccess<HOST, CHRONO_ZONED_DATE_TIME>, TemporalAccess<HOST, CHRONO_ZONED_DATE_TIME> {
    
    public static <H, DT extends ChronoLocalDateTime<? extends ChronoLocalDate>> ChronoLocalDateTimeAccess<H, DT> of(Function<H, DT> func) {
        return func::apply;
    }
    
    public default LocalDateAccess<HOST> toLocalDate() {
        return host -> {
            val value = apply(host);
            return (LocalDate) value.toLocalDate();
        };
    }
    
    public default LocalTimeAccess<HOST> toLocalTime() {
        return host -> {
            val value = apply(host);
            return (LocalTime) value.toLocalTime();
        };
    }
    
    public default ChronoLocalDateTimeAccess<HOST, ? extends ChronoLocalDateTime<? extends ChronoLocalDate>> toLocalDateTime() {
        return host -> {
            val value = apply(host);
            return value.toLocalDateTime();
        };
    }
    
    @SuppressWarnings("unchecked")
    public default <CHRONOLOGY extends Chronology> ChronologyAccess<HOST, CHRONOLOGY> getChronology() {
        return host -> {
            val value = apply(host);
            return (CHRONOLOGY) value.getChronology();
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
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> withEarlierOffsetAtOverlap() {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withEarlierOffsetAtOverlap();
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> withLaterOffsetAtOverlap() {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withLaterOffsetAtOverlap();
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> withZoneSameLocal(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withZoneSameLocal(zone);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> withZoneSameInstant(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.withZoneSameInstant(zone);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.with(adjuster);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.with(field, newValue);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> plus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.plus(amount);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.plus(amountToAdd, unit);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> minus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_ZONED_DATE_TIME) value.minus(amount);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoZonedDateTimeAccess<HOST, CHRONO_ZONED_DATE_TIME> minus(long amountToAdd, TemporalUnit unit) {
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
    
    public default LongAccessPrimitive<HOST> toEpochSecond() {
        return host -> {
            val value = apply(host);
            return value.toEpochSecond();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(ChronoZonedDateTime<?> other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(ChronoZonedDateTime<?> anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(ChronoZonedDateTime<?> anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(ChronoZonedDateTime<?> anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(ChronoZonedDateTime<?> anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAfter(ChronoZonedDateTime<?> other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsBefore(ChronoZonedDateTime<?> other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsEqual(ChronoZonedDateTime<?> other) {
        return host -> {
            val value = apply(host);
            return value.isEqual(other);
        };
    }
}
