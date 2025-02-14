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

import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface TemporalUnitAccess<HOST, TEMPORAL_UNIT extends TemporalUnit> extends AnyAccess<HOST, TEMPORAL_UNIT> {
    
    public static <H, T extends TemporalUnit> TemporalUnitAccess<H, T> of(Function<H, T> func) {
        return func::apply;
    }
    
    public default DurationAccess<HOST> getDuration() {
        return host -> {
            val value = apply(host);
            return value.getDuration();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isDurationEstimated() {
        return host -> {
            val value = apply(host);
            return value.isDurationEstimated();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isDateBased() {
        return host -> {
            val value = apply(host);
            return value.isDateBased();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isTimeBased() {
        return host -> {
            val value = apply(host);
            return value.isTimeBased();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isSupportedBy(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.isSupportedBy(temporal);
        };
    }
    
    public default <R extends Temporal> TemporalAccess<HOST, R> addTo(R temporal, long amount) {
        return host -> {
            val value = apply(host);
            return value.addTo(temporal, amount);
        };
    }
    
    public default LongAccessPrimitive<HOST> between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return host -> {
            val value = apply(host);
            return value.between(temporal1Inclusive, temporal2Exclusive);
        };
    }
}
