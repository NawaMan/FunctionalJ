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

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ListAccess;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

public interface TemporalAmountAccess<HOST, TEMPORAL_AMOUNT extends TemporalAmount> extends AnyAccess<HOST, TEMPORAL_AMOUNT> {
    
    public static <H, T extends TemporalAmount> TemporalAmountAccess<H, T> of(Function<H, T> func) {
        return func::apply;
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
    
    public default TemporalAccess<HOST, Temporal> addTo(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.addTo(temporal);
        };
    }
    
    public default TemporalAccess<HOST, Temporal> subtractFrom(Temporal temporal) {
        return host -> {
            val value = apply(host);
            return value.subtractFrom(temporal);
        };
    }
}
