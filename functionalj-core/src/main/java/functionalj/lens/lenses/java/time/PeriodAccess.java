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

import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.ListAccess;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface PeriodAccess<HOST> extends AnyAccess<HOST, Period>, ChronoPeriodAccess<HOST, Period>, ConcreteAccess<HOST, Period, PeriodAccess<HOST>> {
    
    public static <H> PeriodAccess<H> of(Function<H, Period> func) {
        return func::apply;
    }
    
    public default PeriodAccess<HOST> newAccess(Function<HOST, Period> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default LongAccessPrimitive<HOST> get(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.get(unit);
        };
    }
    
    public default ListAccess<HOST, TemporalUnit, TemporalUnitAccess<HOST, TemporalUnit>> getUnits() {
        return ListAccess.of(this.then(TemporalAmount::getUnits), TemporalUnitAccess::of);
    }
    
    public default IsoChronologyAccess<HOST> getChronology() {
        return host -> {
            val value = apply(host);
            return value.getChronology();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isZero() {
        return host -> {
            val value = apply(host);
            return value.isZero();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isNegative() {
        return host -> {
            val value = apply(host);
            return value.isNegative();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getYears() {
        return host -> {
            val value = apply(host);
            return value.getYears();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getMonths() {
        return host -> {
            val value = apply(host);
            return value.getMonths();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getDays() {
        return host -> {
            val value = apply(host);
            return value.getDays();
        };
    }
    
    public default PeriodAccess<HOST> withYears(int years) {
        return host -> {
            val value = apply(host);
            return value.withYears(years);
        };
    }
    
    public default PeriodAccess<HOST> withMonths(int months) {
        return host -> {
            val value = apply(host);
            return value.withMonths(months);
        };
    }
    
    public default PeriodAccess<HOST> withDays(int days) {
        return host -> {
            val value = apply(host);
            return value.withDays(days);
        };
    }
    
    public default PeriodAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    
    public default PeriodAccess<HOST> plusYears(long yearsToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusYears(yearsToAdd);
        };
    }
    
    public default PeriodAccess<HOST> plusMonths(long monthsToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusMonths(monthsToAdd);
        };
    }
    
    public default PeriodAccess<HOST> plusDays(long daysToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusDays(daysToAdd);
        };
    }
    
    public default PeriodAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    
    public default PeriodAccess<HOST> minusYears(long yearsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusYears(yearsToSubtract);
        };
    }
    
    public default PeriodAccess<HOST> minusMonths(long monthsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusMonths(monthsToSubtract);
        };
    }
    
    public default PeriodAccess<HOST> minusDays(long daysToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusDays(daysToSubtract);
        };
    }
    
    public default PeriodAccess<HOST> multipliedBy(int scalar) {
        return host -> {
            val value = apply(host);
            return value.multipliedBy(scalar);
        };
    }
    
    public default PeriodAccess<HOST> negated() {
        return host -> {
            val value = apply(host);
            return value.negated();
        };
    }
    
    public default PeriodAccess<HOST> normalized() {
        return host -> {
            val value = apply(host);
            return value.normalized();
        };
    }
    
    public default LongAccessPrimitive<HOST> toTotalMonths() {
        return host -> {
            val value = apply(host);
            return value.toTotalMonths();
        };
    }
}
