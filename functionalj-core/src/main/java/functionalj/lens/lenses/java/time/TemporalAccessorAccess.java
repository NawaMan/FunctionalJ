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

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

public interface TemporalAccessorAccess<HOST, TEMPORAL_ACCESSOR extends TemporalAccessor> extends AnyAccess<HOST, TEMPORAL_ACCESSOR> {
    
    public static <H, T extends TemporalAccessor> TemporalAccessorAccess<H, T> of(Function<H, T> func) {
        return func::apply;
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsSupported(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.isSupported(field);
        };
    }
    
    public default ValueRangeAccess<HOST> range(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.range(field);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> get(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.get(field);
        };
    }
    
    public default LongAccessPrimitive<HOST> getLong(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.getLong(field);
        };
    }
    
    public default <R> AnyAccess<HOST, R> query(TemporalQuery<R> query) {
        return host -> {
            val value = apply(host);
            return value.query(query);
        };
    }
}
