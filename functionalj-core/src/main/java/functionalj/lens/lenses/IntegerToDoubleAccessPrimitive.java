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

import static functionalj.function.Apply.accessPrimitive;
import static functionalj.function.Apply.applyPrimitive;
import static functionalj.function.Apply.getPrimitive;
import static functionalj.function.Compare.comparePrimitive;

import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;

import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;


/**
 * Classes implementing this interface know how to access from an integer to a double value.
 **/
@FunctionalInterface
public interface IntegerToDoubleAccessPrimitive extends DoubleAccessPrimitive<Integer>, IntToDoubleFunction {
    
    //== Constructor ==
    
    @Override
    public default IntegerToDoubleAccessPrimitive newAccess(Function<Integer, Double> accessToValue) {
        return accessToValue::apply;
    }
    
    //== abstract functionalities ==
    
    public double applyIntToDouble(int host);
    
    public default double applyAsDouble(int operand) {
        return applyIntToDouble(operand);
    }
    
    public default double applyAsDouble(Integer host) {
        return applyIntToDouble(host);
    }
    
    //-- conversion --
    
    public default DoubleAccessBoxed<Integer> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    @Override
    public default IntegerToLongAccessPrimitive asLong() {
        return asLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive asDouble() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive asInteger(int overflowValue) {
        return asInteger(overflowValue, overflowValue);
    }
    
    @Override
    public default IntegerToLongAccessPrimitive asLong(long overflowValue) {
        return asLong(overflowValue, overflowValue);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive asInteger(int negativeOverflowValue, int positiveOverflowValue) {
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
    public default IntegerToLongAccessPrimitive asLong(long negativeOverflowValue, long positiveOverflowValue) {
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
    public default IntegerToDoubleAccessPrimitive round() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.round(value);
        };
    }
    @Override
    public default IntegerToIntegerAccessPrimitive roundToInt() {
        return round().asInteger();
    }
    
    @Override
    public default IntegerToLongAccessPrimitive roundToLong() {
        return round().asLong();
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive ceil() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.ceil(value);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive ceilToInt() {
        return round().asInteger();
    }
    
    @Override
    public default IntegerToLongAccessPrimitive ceilToLong() {
        return round().asLong();
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive floor() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.floor(value);
        };
    }
    @Override
    public default IntegerToIntegerAccessPrimitive floorToInt() {
        return floor().asInteger();
    }
    @Override
    public default IntegerToLongAccessPrimitive floorToLong() {
        return floor().asLong();
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive roundBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive roundBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive roundBy(IntegerToDoubleAccessPrimitive precisionFunction) {
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
    public default IntegerToDoubleAccessPrimitive ceilBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive ceilBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive ceilBy(IntegerToDoubleAccessPrimitive precisionFunction) {
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
    public default IntegerToDoubleAccessPrimitive floorBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive floorBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive floorBy(IntegerToDoubleAccessPrimitive precisionFunction) {
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
    public default IntegerToStringAccessPrimitive asString() {
        return host -> "" + accessPrimitive(this, host);
    }
    @Override
    public default IntegerToStringAccessPrimitive asString(String template) {
        return host -> {
            val value = accessPrimitive(this, host);
            return String.format(template, value);
        };
    }
    
    // TODO - Find a better way to format this that allow a fix width disregards of the magnitude of the value.
    //          or just redirect the format to another function that can be substituted.
    
    //-- Equality --
    
    @Override
    public default IntegerToBooleanAccessPrimitive that(DoublePredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIs(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNot(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(double ... otherValues) {
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
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(double ... otherValues) {
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
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsOne() {
        return thatIs(1);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsZero() {
        return thatIs(0);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsMinusOne() {
        return thatIs(-1);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsFourtyTwo() {
        return thatIs(42);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNotOne() {
        return thatIsNot(1);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNotZero() {
        return thatIsNot(0);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNotMinusOne() {
        return thatIsNot(-1);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsPositive() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > 0;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNegative() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < 0;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNotPositive() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= 0;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNotNegative() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= 0;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatEquals(double anotherValue) {
        return new IntegerToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherValue);
    }
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatEquals(DoubleSupplier anotherSupplier) {
        return new IntegerToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default IntegerToDoubleAccessEqualPrimitive thatEquals(IntegerToDoubleAccessPrimitive anotherAccess) {
        return new IntegerToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherAccess.applyAsDouble(host));
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    @Override
    public default IntegerToDoubleAccessEqualPrimitive eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default IntegerToDoubleAccessEqualPrimitive eq(IntegerToDoubleAccessPrimitive anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatNotEquals(double anotherValue) {
        return new IntegerToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherValue);
    }
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatNotEquals(DoubleSupplier anotherSupplier) {
        return new IntegerToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default IntegerToDoubleAccessEqualPrimitive thatNotEquals(DoubleUnaryOperator anotherAccess) {
        return new IntegerToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherAccess.applyAsDouble(value));
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    @Override
    public default IntegerToDoubleAccessEqualPrimitive neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default IntegerToDoubleAccessEqualPrimitive neq(DoubleUnaryOperator anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default IntegerToDoubleAccessEqualPrimitive thatNotEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    //-- Compare --
    
    @Override
    public default IntComparator ascendingOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(aValue, bValue);
        };
    }
    
    @Override
    public default IntComparator descendingOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(bValue, aValue);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive compareTo(double anotherValue) {
        return host -> {
            val value   = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    @Override
    public default IntegerToIntegerAccessPrimitive compareTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare      = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val compare      = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    @Override
    public default IntegerToIntegerAccessPrimitive cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerToIntegerAccessPrimitive cmp(IntegerToDoubleAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gt(IntegerToDoubleAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThan(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lt(IntegerToDoubleAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gteq(IntegerToDoubleAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lteq(IntegerToDoubleAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    //-- Min+Max --
    
    @Override
    public default IntegerToDoubleAccessPrimitive min(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive min(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive min(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive max(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive max(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive max(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsRound() {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0*Math.round(value) == value;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive plus(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive plus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive plus(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive minus(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive minus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive minus(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive time(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive time(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive time(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive dividedBy(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive dividedBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return 1.0*value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return 1.0*value / anotherValue;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive remainderBy(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive remainderBy(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive square() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * value;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive squareRoot () {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.sqrt(value);
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive pow(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.pow(value, anotherValue);
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive pow(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.pow(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(IntegerToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive exp() {
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
    public default IntegerToDoubleAccessPrimitive expm1() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.expm1(doubleValue);
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive log() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.log(doubleValue);
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive log10() {
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
    public default IntegerToDoubleAccessPrimitive log1p() {
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
