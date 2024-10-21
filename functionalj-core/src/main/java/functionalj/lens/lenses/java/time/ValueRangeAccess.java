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

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

public interface ValueRangeAccess<HOST> extends AnyAccess<HOST, ValueRange>, ConcreteAccess<HOST, ValueRange, ValueRangeAccess<HOST>> {
    
    public static <H> ValueRangeAccess<H> of(Function<H, ValueRange> func) {
        return func::apply;
    }
    
    public default ValueRangeAccess<HOST> newAccess(Function<HOST, ValueRange> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccessPrimitive<HOST> isFixed() {
        return host -> {
            val value = apply(host);
            return value.isFixed();
        };
    }
    
    public default LongAccessPrimitive<HOST> getMinimum() {
        return host -> {
            val value = apply(host);
            return value.getMinimum();
        };
    }
    
    public default LongAccessPrimitive<HOST> getLargestMinimum() {
        return host -> {
            val value = apply(host);
            return value.getLargestMinimum();
        };
    }
    
    public default LongAccessPrimitive<HOST> getSmallestMaximum() {
        return host -> {
            val value = apply(host);
            return value.getSmallestMaximum();
        };
    }
    
    public default LongAccessPrimitive<HOST> getMaximum() {
        return host -> {
            val value = apply(host);
            return value.getMaximum();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isIntValue() {
        return host -> {
            val value = apply(host);
            return value.isIntValue();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isValidValue(long value) {
        return host -> {
            return apply(host).isValidValue(value);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isValidIntValue(long value) {
        return host -> {
            return apply(host).isValidIntValue(value);
        };
    }
    
    public default LongAccessPrimitive<HOST> checkValidValue(long value, TemporalField field) {
        return host -> {
            return apply(host).checkValidValue(value, field);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> checkValidIntValue(long value, TemporalField field) {
        return host -> {
            return apply(host).checkValidIntValue(value, field);
        };
    }
}
