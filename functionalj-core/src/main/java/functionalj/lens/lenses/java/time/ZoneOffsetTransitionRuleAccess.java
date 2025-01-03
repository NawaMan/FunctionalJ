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

import java.time.zone.ZoneOffsetTransitionRule;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

public interface ZoneOffsetTransitionRuleAccess<HOST> extends AnyAccess<HOST, ZoneOffsetTransitionRule>, ConcreteAccess<HOST, ZoneOffsetTransitionRule, ZoneOffsetTransitionRuleAccess<HOST>> {
    
    public static <H> ZoneOffsetTransitionRuleAccess<H> of(Function<H, ZoneOffsetTransitionRule> func) {
        return func::apply;
    }
    
    public default ZoneOffsetTransitionRuleAccess<HOST> newAccess(Function<HOST, ZoneOffsetTransitionRule> accessToValue) {
        return ZoneOffsetTransitionRuleAccess.of(accessToValue);
    }
    
    public default MonthAccess<HOST> getMonth() {
        return host -> {
            val value = apply(host);
            return value.getMonth();
        };
    }
    
    public default IntegerAccessPrimitive<HOST> getDayOfMonthIndicator() {
        return host -> {
            val value = apply(host);
            return value.getDayOfMonthIndicator();
        };
    }
    
    public default DayOfWeekAccess<HOST> getDayOfWeek() {
        return host -> {
            val value = apply(host);
            return value.getDayOfWeek();
        };
    }
    
    public default LocalTimeAccess<HOST> getLocalTime() {
        return host -> {
            val value = apply(host);
            return value.getLocalTime();
        };
    }
    
    public default BooleanAccessPrimitive<HOST> isMidnightEndOfDay() {
        return host -> {
            val value = apply(host);
            return value.isMidnightEndOfDay();
        };
    }
    
    public default TimeDefinitionAccess<HOST> getTimeDefinition() {
        return host -> {
            val value = apply(host);
            return value.getTimeDefinition();
        };
    }
    
    public default ZoneOffsetAccess<HOST> getStandardOffset() {
        return host -> {
            val value = apply(host);
            return value.getStandardOffset();
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
    
    public default ZoneOffsetTransitionAccess<HOST> createTransition(int year) {
        return host -> {
            val value = apply(host);
            return value.createTransition(year);
        };
    }
}
