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

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.function.ToDoubleBiDoubleFunction;
import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;


/**
 * Classes implementing this interface know how to access from a double to an double value.
 **/
@FunctionalInterface
public interface DoubleToDoubleAccessPrimitive extends DoubleUnaryOperator, DoubleAccessPrimitive<Double>, DoubleFunction<Double> {
    
    public static double apply(DoubleAccess<Double> access, double value) {
        val resValue 
            = (access instanceof DoubleToDoubleAccessPrimitive)
            ? ((DoubleToDoubleAccessPrimitive)access).applyDoubleToDouble(value)
            : access.applyAsDouble(value);
        return resValue;
    }
    
    //== abstract functionalities ==
    
    public double applyDoubleToDouble(double host);
    
    
    public default double applyAsDouble(double host) {
        return applyDoubleToDouble(host);
    }
    
    public default double applyAsDouble(Double host) {
        return applyDoubleToDouble(host);
    }
    
    @Override
    public default Double apply(double host) {
        return applyDoubleToDouble(host);
    }
    
    //-- create --
    
    @Override
    public default DoubleToDoubleAccessPrimitive newAccess(Function<Double, Double> accessToValue) {
        return accessToValue::apply;
    }
    
    public default DoubleToDoubleAccessPrimitive newAccess(DoubleUnaryOperator accessToValue) {
        return accessToValue::applyAsDouble;
    }
    
    //-- conversion --
    
    public default DoubleAccessBoxed<Double> boxed() {
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
        return host -> applyAsDouble(host);
    }
    
    public default DoubleToIntegerAccessPrimitive asInteger(int overflowValue) {
        return asInteger(overflowValue, overflowValue);
    }
    
    public default DoubleToLongAccessPrimitive asLong(long overflowValue) {
        return asLong(overflowValue, overflowValue);
    }
    
    public default DoubleToIntegerAccessPrimitive asInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return host -> {
            val value = applyAsDouble(host);
            if (value < Integer.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Integer.MAX_VALUE)
                return positiveOverflowValue;
            
            return (int)value;
        };
    }
    
    public default DoubleToLongAccessPrimitive asLong(long negativeOverflowValue, long positiveOverflowValue) {
        return host -> {
            val value = applyAsDouble(host);
            if (value < Long.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Long.MAX_VALUE)
                return positiveOverflowValue;
            
            return (long)value;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive round() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.round(value);
        };
    }
    public default DoubleToIntegerAccessPrimitive roundToInt() {
        return round().asInteger();
    }
    
    public default DoubleToLongAccessPrimitive roundToLong() {
        return round().asLong();
    }
    
    public default DoubleToDoubleAccessPrimitive ceil() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.ceil(value);
        };
    }
    
    public default DoubleToIntegerAccessPrimitive ceilToInt() {
        return round().asInteger();
    }
    
    public default DoubleToLongAccessPrimitive ceilToLong() {
        return round().asLong();
    }
    
    public default DoubleToDoubleAccessPrimitive floor() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.floor(value);
        };
    }
    public default DoubleToIntegerAccessPrimitive floorToInt() {
        return floor().asInteger();
    }
    public default DoubleToLongAccessPrimitive floorToLong() {
        return floor().asLong();
    }
    
    public default DoubleToDoubleAccessPrimitive roundBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive roundBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive roundBy(ToDoubleBiDoubleFunction<Double> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive ceilBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive ceilBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive ceilBy(ToDoubleBiDoubleFunction<Double> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive floorBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive floorBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default DoubleToDoubleAccessPrimitive floorBy(ToDoubleBiDoubleFunction<Double> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    
    public default DoubleToStringAccessPrimitive asString() {
        return host -> "" + applyAsDouble(host);
    }
    public default DoubleToStringAccessPrimitive asString(String template) {
        return host -> {
            val value = applyAsDouble(host);
            return String.format(template, value);
        };
    }
    
    // TODO - Find a better way to format this that allow a fix width disregards of the magnitude of the value.
    //          or just redirect the format to another function that can be substituted.
    
    //-- to value --
    
    public default DoubleToDoubleAccessPrimitive toZero() {
        return host -> 0.0;
    }
    
    public default DoubleToDoubleAccessPrimitive toOne() {
        return host -> 1.0;
    }
    
    public default DoubleToDoubleAccessPrimitive toMinusOne() {
        return host -> -1.0;
    }
    
    public default DoubleToDoubleAccessPrimitive to(double anotherValue) {
        return host -> {
            val value   = applyAsDouble(host);
            val compare = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToDoubleAccessPrimitive to(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToDoubleAccessPrimitive to(ToDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToDoubleAccessPrimitive to(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    
    //-- Equality --
    
    public default DoubleToBooleanAccessPrimitive that(DoublePredicate checker) {
        return host -> {
            val value = applyAsDouble(host);
            return checker.test(value);
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIs(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIs(ToDoubleFunction<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIs(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value == anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNot(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNot(ToDoubleFunction<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNot(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value != anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(double ... otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return true;
                }
            }
            return false;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNoneOf(double ... otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return false;
                }
            }
            return true;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsOne() {
        return thatIs(1);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsZero() {
        return thatIs(0.0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsMinusOne() {
        return thatIs(-1.0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsFourtyTwo() {
        return thatIs(42.0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNotOne() {
        return thatIsNot(1.0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNotZero() {
        return thatIsNot(0.0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNotMinusOne() {
        return thatIsNot(-1.0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsPositive() {
        return host -> {
            val value = applyAsDouble(host);
            return value > 0.0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNegative() {
        return host -> {
            val value = applyAsDouble(host);
            return value < 0.0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNotPositive() {
        return host -> {
            val value = applyAsDouble(host);
            return value <= 0.0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNotNegative() {
        return host -> {
            val value = applyAsDouble(host);
            return value >= 0.0;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsRound() {
        return host -> {
            val value = applyAsDouble(host);
            return 1.0*Math.round(value) == value;
        };
    }
    
    public default DoubleAccessEqualPrimitive thatEquals(double anotherValue) {
        return new DoubleAccessEqualPrimitive(false, this, (host, value) -> anotherValue);
    }
    public default DoubleAccessEqualPrimitive thatEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqualPrimitive(false, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default DoubleAccessEqualPrimitive thatEquals(DoubleUnaryOperator anotherAccess) {
        return new DoubleAccessEqualPrimitive(false, this, (host, value) -> anotherAccess.applyAsDouble(value));
    }
    public default DoubleAccessEqualPrimitive thatEquals(DoubleBinaryOperator anotherFunction) {
        return new DoubleAccessEqualPrimitive(false, this, anotherFunction);
    }
    
    public default DoubleAccessEqualPrimitive eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    public default DoubleAccessEqualPrimitive eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default DoubleAccessEqualPrimitive eq(DoubleUnaryOperator anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default DoubleAccessEqualPrimitive eq(DoubleBinaryOperator anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default DoubleAccessEqualPrimitive thatNotEquals(double anotherValue) {
        return new DoubleAccessEqualPrimitive(true, this, (host, value) -> anotherValue);
    }
    public default DoubleAccessEqualPrimitive thatNotEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqualPrimitive(true, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default DoubleAccessEqualPrimitive thatNotEquals(DoubleUnaryOperator anotherAccess) {
        return new DoubleAccessEqualPrimitive(true, this, (host, value) -> anotherAccess.applyAsDouble(value));
    }
    public default DoubleAccessEqualPrimitive thatNotEquals(DoubleBinaryOperator anotherFunction) {
        return new DoubleAccessEqualPrimitive(true, this, anotherFunction);
    }
    
    public default DoubleAccessEqualPrimitive neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default DoubleAccessEqualPrimitive neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default DoubleAccessEqualPrimitive neq(DoubleUnaryOperator anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default DoubleAccessEqualPrimitive neq(DoubleBinaryOperator anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    //-- Compare --
    
    public default DoubleComparator ascendingOrder() {
        return (a, b) -> Double.compare(a, b);
    }
    
    public default DoubleComparator descendingOrder() {
        return (a, b) -> Double.compare(b, a);
    }
    
    public default DoubleToIntegerAccessPrimitive compareTo(double anotherValue) {
        return host -> {
            val value   = applyAsDouble(host);
            val compare = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive compareTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive compareTo(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive compareTo(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    public default DoubleToIntegerAccessPrimitive cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default DoubleToIntegerAccessPrimitive cmp(DoubleAccess<Double> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default DoubleToIntegerAccessPrimitive cmp(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value > anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value > anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value > anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value > anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive gt(DoubleAccess<Double> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive gt(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatLessThan(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value < anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value < anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThan(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value < anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThan(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value < anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive lt(DoubleAccess<Double> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive lt(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value >= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value >= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value >= anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive gteq(DoubleAccess<Double> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive gteq(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value <= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value <= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value <= anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive lteq(DoubleAccess<Double> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive lteq(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    // TODO - See if the following is valid
    //-- digitAt
    //-- digitValueAt
    //-- factorValueAt
    //-- factorValueAt
    
    //-- Min+Max --
    
    public default DoubleToDoubleAccessPrimitive min(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive min(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive min(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive min(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return Math.min(value, anotherValue);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive max(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive max(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive max(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive max(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    public default DoubleToDoubleAccessPrimitive abs() {
        return host -> {
            val value = applyAsDouble(host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive negate() {
        return host -> {
            val value = applyAsDouble(host);
            return -value;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive signum() {
        return host -> {
            val value = applyAsDouble(host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive plus(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value + anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive plus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value + anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive plus(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value + anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive plus(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value + anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive minus(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value - anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive minus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value - anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive minus(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value - anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive minus(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value - anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive time(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value * anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive time(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value * anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive time(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value * anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive time(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value * anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive dividedBy(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive dividedBy(DoubleSupplier anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.getAsDouble();
            return 1.0*value / anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive dividedBy(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return 1.0*value / anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive dividedBy(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return 1.0*value / anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive remainderBy(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value % anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value % anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive remainderBy(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value % anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive remainderBy(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value % anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive square() {
        return host -> {
            val value = applyAsDouble(host);
            return value * value;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive squareRoot () {
        return host -> {
            val value = applyAsDouble(host);
            return Math.sqrt(value);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive pow(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive pow(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive pow(DoubleAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleToDoubleAccessPrimitive.apply(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive pow(ToDoubleBiDoubleFunction<Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, value);
            return Math.pow(value, anotherValue);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive exp() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.exp(doubleValue);
        };
    }
    
    /**
     * Returns <i>e</i><sup>x</sup>&nbsp;-1.  Note that for values of
     * <i>x</i> near 0, the exact sum of
     * {@code expm1(x)}&nbsp;+&nbsp;1 is much closer to the true
     * result of <i>e</i><sup>x</sup> than {@code exp(x)}.
     **/
    public default DoubleToDoubleAccessPrimitive expm1() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.expm1(doubleValue);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive log() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log(doubleValue);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive log10() {
        return host -> {
            double doubleValue = applyAsDouble(host);
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
    public default DoubleToDoubleAccessPrimitive log1p() {
        return host -> {
            double doubleValue = applyAsDouble(host);
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
