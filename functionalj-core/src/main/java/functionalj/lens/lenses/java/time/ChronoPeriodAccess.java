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

import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.TemporalAmount;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface ChronoPeriodAccess<HOST, CHRONO_PERIOD extends ChronoPeriod> extends AnyAccess<HOST, CHRONO_PERIOD>, TemporalAmountAccess<HOST, CHRONO_PERIOD> {
    
    public static <H, C extends ChronoPeriod> ChronoPeriodAccess<H, C> of(Function<H, C> func) {
        return func::apply;
    }
    
    public default ChronologyAccess<HOST, ? extends Chronology> getChronology() {
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
    
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return (CHRONO_PERIOD) value.plus(amountToAdd);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> minus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return (CHRONO_PERIOD) value.minus(amountToAdd);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> multipliedBy(int scalar) {
        return host -> {
            val value = apply(host);
            return (CHRONO_PERIOD) value.multipliedBy(scalar);
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> negated() {
        return host -> {
            val value = apply(host);
            return (CHRONO_PERIOD) value.negated();
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> normalized() {
        return host -> {
            val value = apply(host);
            return (CHRONO_PERIOD) value.normalized();
        };
    }
}
