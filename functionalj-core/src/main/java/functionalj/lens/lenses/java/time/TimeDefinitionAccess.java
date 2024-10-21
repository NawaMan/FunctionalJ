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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface TimeDefinitionAccess<HOST> extends AnyAccess<HOST, TimeDefinition>, ConcreteAccess<HOST, TimeDefinition, TimeDefinitionAccess<HOST>> {
    
    public static <H> TimeDefinitionAccess<H> of(Function<H, TimeDefinition> func) {
        return func::apply;
    }
    
    public default TimeDefinitionAccess<HOST> newAccess(Function<HOST, TimeDefinition> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccessPrimitive<HOST> isUtc() {
        return host -> {
            val value = apply(host);
            return value == TimeDefinition.UTC;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isWall() {
        return host -> {
            val value = apply(host);
            return value == TimeDefinition.WALL;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isStandard() {
        return host -> {
            val value = apply(host);
            return value == TimeDefinition.STANDARD;
        };
    }
    
    public default LocalDateTimeAccess<HOST> createDateTime(LocalDateTime dateTime, ZoneOffset standardOffset, ZoneOffset wallOffset) {
        return host -> {
            val value = apply(host);
            return value.createDateTime(dateTime, standardOffset, wallOffset);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(TimeDefinition other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(TimeDefinition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
}
