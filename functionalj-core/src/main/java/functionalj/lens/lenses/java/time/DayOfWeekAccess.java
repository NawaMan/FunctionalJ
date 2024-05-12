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

import java.time.DayOfWeek;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface DayOfWeekAccess<HOST> extends AnyAccess<HOST, DayOfWeek>, TemporalAccessorAccess<HOST, DayOfWeek>, TemporalAdjusterAccess<HOST, DayOfWeek>, ConcreteAccess<HOST, DayOfWeek, DayOfWeekAccess<HOST>> {
    
    public static <H> DayOfWeekAccess<H> of(Function<H, DayOfWeek> func) {
        return func::apply;
    }
    
    public default DayOfWeekAccess<HOST> newAccess(Function<HOST, DayOfWeek> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccessPrimitive<HOST> isMonday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.MONDAY;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isTuesday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.TUESDAY;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isWednesday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.WEDNESDAY;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isThursday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.THURSDAY;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isFriday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.FRIDAY;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isSaturday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.SATURDAY;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isSunday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.SUNDAY;
        };
    }
    
    public default DayOfWeekAccess<HOST> plus(long days) {
        return host -> {
            val value = apply(host);
            return value.plus(days);
        };
    }
    
    public default DayOfWeekAccess<HOST> minus(long days) {
        return host -> {
            val value = apply(host);
            return value.minus(days);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(DayOfWeek other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
}
