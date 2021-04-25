// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.lenses;

import static functionalj.function.Apply.access;
import static functionalj.function.Apply.accessPrimitive;
import static functionalj.function.Apply.getPrimitive;
import static functionalj.function.Compare.comparePrimitive;

import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import functionalj.function.DoubleComparator;
import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;


/**
 * Classes implementing this interface know how to access from a double to an double value.
 **/
@FunctionalInterface
public interface DoubleToDoubleAccessPrimitive extends DoubleUnaryOperator, DoubleAccessPrimitive<Double>, DoubleFunction<Double> {
    
    //== Constructor ==
    
    public static DoubleToDoubleAccessPrimitive of(DoubleToDoubleAccessPrimitive accessToValue) {
        return accessToValue;
    }
    
    public static DoubleToDoubleAccessPrimitive from(DoubleUnaryOperator accessToValue) {
        return host -> accessToValue.applyAsDouble(host);
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive newAccess(Function<Double, Double> accessToValue) {
        return accessToValue::apply;
    }
    
    public default DoubleToDoubleAccessPrimitive newAccess(DoubleUnaryOperator accessToValue) {
        return accessToValue::applyAsDouble;
    }
    
    //== abstract functionalities ==
    
    public double applyToDouble(double host);
    
    
    @Override
    public default double applyAsDouble(double host) {
        return applyToDouble(host);
    }
    
    @Override
    public default double applyAsDouble(Double host) {
        return applyToDouble(host);
    }
    
    @Override
    public default Double apply(double host) {
        return applyToDouble(host);
    }
    
    
    //-- conversion --
    
    @Override
    public default DoubleToDoubleAccessBoxed boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    @Override
    public default DoubleToLongAccessPrimitive asLong() {
        return asLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive asDouble() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive asInteger(int overflowValue) {
        return asInteger(overflowValue, overflowValue);
    }
    
    @Override
    public default DoubleToLongAccessPrimitive asLong(long overflowValue) {
        return asLong(overflowValue, overflowValue);
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive asInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (value < Integer.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Integer.MAX_VALUE)
                return positiveOverflowValue;
            
            return (int)Math.round(value);
        };
    }
    
    @Override
    public default DoubleToLongAccessPrimitive asLong(long negativeOverflowValue, long positiveOverflowValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (value < Long.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Long.MAX_VALUE)
                return positiveOverflowValue;
            
            return (long)Math.round(value);
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive round() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.round(value);
        };
    }
    @Override
    public default DoubleToIntegerAccessPrimitive roundToInt() {
        return round().asInteger();
    }
    
    @Override
    public default DoubleToLongAccessPrimitive roundToLong() {
        return round().asLong();
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive ceil() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.ceil(value);
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive ceilToInt() {
        return round().asInteger();
    }
    
    @Override
    public default DoubleToLongAccessPrimitive ceilToLong() {
        return round().asLong();
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive floor() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.floor(value);
        };
    }
    @Override
    public default DoubleToIntegerAccessPrimitive floorToInt() {
        return floor().asInteger();
    }
    @Override
    public default DoubleToLongAccessPrimitive floorToLong() {
        return floor().asLong();
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive roundBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive roundBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive roundBy(DoubleToDoubleAccessPrimitive precisionFunction) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = accessPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive ceilBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive ceilBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive ceilBy(DoubleToDoubleAccessPrimitive precisionFunction) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = accessPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive floorBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive floorBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive floorBy(DoubleToDoubleAccessPrimitive precisionFunction) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = accessPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    
    @Override
    public default DoubleToStringAccessPrimitive asString() {
        return host -> "" + accessPrimitive(this, host);
    }
    @Override
    public default DoubleToStringAccessPrimitive asString(String template) {
        return host -> {
            val value = accessPrimitive(this, host);
            return String.format(template, value);
        };
    }
    
    // TODO - Find a better way to format this that allow a fix width disregards of the magnitude of the value.
    //          or just redirect the format to another function that can be substituted.
    
    //-- Equality --
    
    @Override
    public default DoubleToBooleanAccessPrimitive that(DoublePredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIs(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIs(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNot(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNot(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(double ... otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return true;
                }
            }
            return false;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNoneOf(double ... otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return false;
                }
            }
            return true;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsOne() {
        return thatIs(1);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsZero() {
        return thatIs(0);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsMinusOne() {
        return thatIs(-1);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsFourtyTwo() {
        return thatIs(42);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNotOne() {
        return thatIsNot(1);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNotZero() {
        return thatIsNot(0);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNotMinusOne() {
        return thatIsNot(-1);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsPositive() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > 0;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNegative() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < 0;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNotPositive() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= 0;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNotNegative() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= 0;
        };
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatEquals(double anotherValue) {
        return new DoubleAccessEqualPrimitive(false, this, (host, value) -> anotherValue);
    }
    @Override
    public default DoubleAccessEqualPrimitive thatEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqualPrimitive(false, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default DoubleAccessEqualPrimitive thatEquals(DoubleToDoubleAccessPrimitive anotherAccess) {
        return new DoubleAccessEqualPrimitive(false, this, (host, value) -> anotherAccess.applyAsDouble(host));
    }
    
    @Override
    public default DoubleAccessEqualPrimitive eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    @Override
    public default DoubleAccessEqualPrimitive eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default DoubleAccessEqualPrimitive eq(DoubleToDoubleAccessPrimitive anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatNotEquals(double anotherValue) {
        return new DoubleAccessEqualPrimitive(true, this, (host, value) -> anotherValue);
    }
    @Override
    public default DoubleAccessEqualPrimitive thatNotEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqualPrimitive(true, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default DoubleAccessEqualPrimitive thatNotEquals(DoubleToDoubleAccessPrimitive anotherAccess) {
        return new DoubleAccessEqualPrimitive(true, this, (host, value) -> anotherAccess.applyAsDouble(value));
    }
    
    @Override
    public default DoubleAccessEqualPrimitive neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    @Override
    public default DoubleAccessEqualPrimitive neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default DoubleAccessEqualPrimitive neq(DoubleToDoubleAccessPrimitive anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default DoubleAccessEqualPrimitive thatNotEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    //-- Compare --
    
    @Override
    public default DoubleComparator inOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(aValue, bValue);
        };
    }
    
    @Override
    public default DoubleComparator inReverseOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(bValue, aValue);
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive compareTo(double anotherValue) {
        return host -> {
            val value   = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    @Override
    public default DoubleToIntegerAccessPrimitive compareTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare      = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive compareTo(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val compare      = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    @Override
    public default DoubleToIntegerAccessPrimitive cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default DoubleToIntegerAccessPrimitive cmp(DoubleToDoubleAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    @Override
    public default DoubleToBooleanAccessPrimitive gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive gt(DoubleToDoubleAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThan(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThan(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    @Override
    public default DoubleToBooleanAccessPrimitive lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive lt(DoubleToDoubleAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    @Override
    public default DoubleToBooleanAccessPrimitive gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive gteq(DoubleToDoubleAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    @Override
    public default DoubleToBooleanAccessPrimitive lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive lteq(DoubleToDoubleAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    //-- Min+Max --
    
    @Override
    public default DoubleToDoubleAccessPrimitive min(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive min(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive min(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive max(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive max(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive max(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsRound() {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0*Math.round(value) == value;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive plus(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive plus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive plus(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive minus(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive minus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive minus(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive time(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive time(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive time(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive dividedBy(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive dividedBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive dividedBy(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return 1.0*value / anotherValue;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive remainderBy(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive remainderBy(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val division     = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive inverse() {
        return host -> {
            val value = access(this, host);
            return 1/(value * 1.0);
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive square() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * value;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive squareRoot () {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.sqrt(value);
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive pow(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.pow(value, anotherValue);
        };
    }
    @Override
    public default DoubleToDoubleAccessPrimitive pow(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive pow(DoubleToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive exp() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.exp(doubleValue);
        };
    }
    
    /**
     * Returns <i>e</i><sup>x</sup>&nbsp;-1.  Note that for values of
     * <i>x</i> near 0, the exact sum of
     * {@code expm1(x)}&nbsp;+&nbsp;1 is much closer to the true
     * result of <i>e</i><sup>x</sup> than {@code exp(x)}.
     **/
    @Override
    public default DoubleToDoubleAccessPrimitive expm1() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.expm1(doubleValue);
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive log() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.log(doubleValue);
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive log10() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.log10(doubleValue);
        };
    }
    
    /**
     * Returns the base 10 logarithm of a {@code double} value.
     * Special cases:
     *
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is negative infinity.
     * <li> If the argument is equal to 10<sup><i>n</i></sup> for
     * integer <i>n</i>, then the result is <i>n</i>.
     * </ul>
     *
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     */
    @Override
    public default DoubleToDoubleAccessPrimitive log1p() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.log1p(doubleValue);
        };
    }
    
    
    // TODO - Add more
//    Math.addExact((int)0, (int)0)
//    Math.addExact((long)0, (long)0)
//    Math.decrementExact((int)0)
//    Math.decrementExact((long)0)
//    Math.incrementExact((int)0)
//    Math.incrementExact((long)0)
//    Math.multiplyExact(int, int)
//    Math.multiplyExact(long, long)
//    Math.negateExact(int)
//    Math.negateExact(long)
//    Math.subtractExact(int, int)
//    Math.subtractExact(long, long)
//    Math.toIntExact(0)
    
    // TODO - Add more.
    
//    Math.acos(doubleValue)
//    Math.asin(doubleValue)
//    Math.tan(doubleValue)
//    Math.tan2(doubleValue)
//    Math.cos(doubleValue)
//    Math.cosh(doubleValue)
//    Math.sin(doubleValue)
//    Math.sinh(doubleValue)
//    Math.tan(doubleValue)
//    Math.tanh(doubleValue)
//    
//    Math.toDegrees(doubleValue)
//    Math.toRadians(doubleValue)
    
}
