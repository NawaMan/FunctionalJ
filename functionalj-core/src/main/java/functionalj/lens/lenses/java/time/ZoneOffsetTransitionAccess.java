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

import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface ZoneOffsetTransitionAccess<HOST> extends AnyAccess<HOST, ZoneOffsetTransition>, ConcreteAccess<HOST, ZoneOffsetTransition, ZoneOffsetTransitionAccess<HOST>> {
    
    public static <H> ZoneOffsetTransitionAccess<H> of(Function<H, ZoneOffsetTransition> func) {
        return func::apply;
    }
    
    public default ZoneOffsetTransitionAccess<HOST> newAccess(Function<HOST, ZoneOffsetTransition> accessToValue) {
        return ZoneOffsetTransitionAccess.of(accessToValue);
    }
    
    public default InstantAccess<HOST> getInstant() {
        return host -> {
            val value = apply(host);
            return value.getInstant();
        };
    }
    
    public default LongAccessPrimitive<HOST> toEpochSecond() {
        return host -> {
            val value = apply(host);
            return value.toEpochSecond();
        };
    }
    
    public default LocalDateTimeAccess<HOST> getDateTimeBefore() {
        return host -> {
            val value = apply(host);
            return value.getDateTimeBefore();
        };
    }
    
    public default LocalDateTimeAccess<HOST> getDateTimeAfter() {
        return host -> {
            val value = apply(host);
            return value.getDateTimeAfter();
        };
    }
    
    public default ZoneOffsetAccess<HOST> getOffsetBefore() {
        return host -> {
            val value = apply(host);
            return value.getOffsetBefore();
        };
    }
    
    public default ZoneOffsetAccess<HOST> getOffsetAfter() {
        return host -> {
            val value = apply(host);
            return value.getOffsetAfter();
        };
    }
    
    public default DurationAccess<HOST> getDuration() {
        return host -> {
            val value = apply(host);
            return value.getDuration();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isGap() {
        return host -> {
            val value = apply(host);
            return value.isGap();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isOverlap() {
        return host -> {
            val value = apply(host);
            return value.isOverlap();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isValidOffset(ZoneOffset offset) {
        return host -> {
            val value = apply(host);
            return value.isValidOffset(offset);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(ZoneOffsetTransition other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
}
