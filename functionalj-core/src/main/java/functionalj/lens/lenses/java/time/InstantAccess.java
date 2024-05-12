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

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface InstantAccess<HOST> extends TemporalAccess<HOST, Instant>, TemporalAdjusterAccess<HOST, Instant>, ConcreteAccess<HOST, Instant, InstantAccess<HOST>> {
    
    public static <H> InstantAccess<H> of(Function<H, Instant> func) {
        return func::apply;
    }
    
    public default InstantAccess<HOST> newAccess(Function<HOST, Instant> accessToValue) {
        return accessToValue::apply;
    }
    
    public default LongAccessPrimitive<HOST> getEpochSecond() {
        return host -> {
            val value = apply(host);
            return value.getEpochSecond();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getNano() {
        return host -> {
            val value = apply(host);
            return value.getNano();
        };
    }
    
    public default InstantAccess<HOST> with(TemporalAdjuster adjuster) {
        return host -> {
            val value = apply(host);
            return value.with(adjuster);
        };
    }
    
    public default InstantAccess<HOST> with(TemporalField field, long newValue) {
        return host -> {
            val value = apply(host);
            return value.with(field, newValue);
        };
    }
    
    public default InstantAccess<HOST> truncatedTo(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.truncatedTo(unit);
        };
    }
    
    @Override
    public default InstantAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    
    @Override
    public default InstantAccess<HOST> plus(long amountToAdd, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd, unit);
        };
    }
    
    public default InstantAccess<HOST> plusSeconds(long secondsToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusSeconds(secondsToAdd);
        };
    }
    
    public default InstantAccess<HOST> plusMillis(long millisToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusMillis(millisToAdd);
        };
    }
    
    public default InstantAccess<HOST> plusNanos(long nanosToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusNanos(nanosToAdd);
        };
    }
    
    public default InstantAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    
    public default InstantAccess<HOST> minus(long amountToSubtract, TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract, unit);
        };
    }
    
    public default InstantAccess<HOST> minusSeconds(long secondsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusSeconds(secondsToSubtract);
        };
    }
    
    public default InstantAccess<HOST> minusMillis(long millisToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusMillis(millisToSubtract);
        };
    }
    
    public default InstantAccess<HOST> minusNanos(long nanosToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusNanos(nanosToSubtract);
        };
    }
    
    public default OffsetDateTimeAccess<HOST> atOffset(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.atOffset(offset);
        };
    }
    
    public default ZonedDateTimeAccess<HOST> atZone(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.atZone(zone);
        };
    }
    
    public default LongAccessPrimitive<HOST> toEpochMilli() {
        return host -> {
            val value = apply(host);
            return value.toEpochMilli();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(Instant other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(Instant anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAfter(Instant other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsBefore(Instant other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
}
