// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.time.ZoneOffset;
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
public interface ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE_TIME extends ChronoLocalDateTime<? extends ChronoLocalDate>> extends AnyAccess<HOST, CHRONO_LOCAL_DATE_TIME>, TemporalAccess<HOST, CHRONO_LOCAL_DATE_TIME>, TemporalAdjusterAccess<HOST, CHRONO_LOCAL_DATE_TIME> {
    
    public static <H, DT extends ChronoLocalDateTime<? extends ChronoLocalDate>> ChronoLocalDateTimeAccess<H, DT> of(Function<H, DT> func) {
        return func::apply;
    }
    
    @SuppressWarnings("unchecked")
    public default <CHRONOLOGY extends Chronology> ChronologyAccess<HOST, CHRONOLOGY> getChronology() {
        return host -> {
            val value = apply(host);
            return (CHRONOLOGY) value.getChronology();
        };
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
    
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE_TIME> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE_TIME) value.with(adjuster);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE_TIME> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE_TIME) value.with(field, newValue);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE_TIME> plus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE_TIME) value.plus(amount);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE_TIME> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE_TIME) value.plus(amountToAdd, unit);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE_TIME> minus(TemporalAmount amount) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE_TIME) value.minus(amount);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoLocalDateTimeAccess<HOST, CHRONO_LOCAL_DATE_TIME> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return (CHRONO_LOCAL_DATE_TIME) value.minus(amountToSubtract, unit);
        };
    }
    
    public default StringAccess<HOST> format(DateTimeFormatter formatter) {
        return host -> {
            val value = apply(host);
            return value.format(formatter);
        };
    }
    
    public default ChronoZonedDateTimeAccess<HOST, ChronoZonedDateTime<? extends ChronoLocalDate>> atZone(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.atZone(zone);
        };
    }
    
    public default InstantAccess<HOST> toInstant(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.toInstant(offset);
        };
    }
    
    public default LongAccessPrimitive<HOST> toEpochSecond(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.toEpochSecond(offset);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(ChronoLocalDateTime<? extends ChronoLocalDate> other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(CHRONO_LOCAL_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(CHRONO_LOCAL_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(CHRONO_LOCAL_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(CHRONO_LOCAL_DATE_TIME anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAfter(CHRONO_LOCAL_DATE_TIME other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsBefore(CHRONO_LOCAL_DATE_TIME other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsEqual(CHRONO_LOCAL_DATE_TIME other) {
        return host -> {
            val value = apply(host);
            return value.isEqual(other);
        };
    }
}
