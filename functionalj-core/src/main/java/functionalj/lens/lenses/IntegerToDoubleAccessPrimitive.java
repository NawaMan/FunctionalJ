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

import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;

import functionalj.function.DoubleIntegerToDoubleFunction;
import functionalj.function.IntegerDoubleToIntegerFunction;
import functionalj.function.ToDoubleBiDoubleFunction;
import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;


@FunctionalInterface
public interface IntegerToDoubleAccessPrimitive extends DoubleAccessPrimitive<Integer>, IntToDoubleFunction {
    
    public static double apply(DoubleAccess<Integer> access, int value) {
        val resValue 
            = (access instanceof IntegerToDoubleAccessPrimitive)
            ? ((IntegerToDoubleAccessPrimitive)access).applyAsDouble(value)
            : access.applyAsDouble(value);
        return resValue;
    }
    
    //== abstract functionalities ==
    
    public double applyIntToDouble(int host);
    
    public default double applyAsDouble(int operand) {
        return applyIntToDouble(operand);
    }
    
    public default double applyAsDouble(Integer host) {
        return applyIntToDouble(host);
    }
    
    //-- create --
    
    @Override
    public default IntegerToDoubleAccessPrimitive newAccess(Function<Integer, Double> accessToValue) {
        return accessToValue::apply;
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
        return host -> applyAsDouble(host);
    }
    
    public default IntegerToIntegerAccessPrimitive asInteger(int overflowValue) {
        return asInteger(overflowValue, overflowValue);
    }
    
    public default IntegerToLongAccessPrimitive asLong(long overflowValue) {
        return asLong(overflowValue, overflowValue);
    }
    
    public default IntegerToIntegerAccessPrimitive asInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return host -> {
            val value = applyAsDouble(host);
            if (value < Integer.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Integer.MAX_VALUE)
                return positiveOverflowValue;
            
            return (int)Math.round(value);
        };
    }
    
    public default IntegerToLongAccessPrimitive asLong(long negativeOverflowValue, long positiveOverflowValue) {
        return host -> {
            val value = applyAsDouble(host);
            if (value < Long.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Long.MAX_VALUE)
                return positiveOverflowValue;
            
            return (long)Math.round(value);
        };
    }
    
    public default IntegerToDoubleAccessPrimitive round() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.round(value);
        };
    }
    public default IntegerToIntegerAccessPrimitive roundToInt() {
        return round().asInteger();
    }
    
    public default IntegerToLongAccessPrimitive roundToLong() {
        return round().asLong();
    }
    
    public default IntegerToDoubleAccessPrimitive ceil() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.ceil(value);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive ceilToInt() {
        return round().asInteger();
    }
    
    public default IntegerToLongAccessPrimitive ceilToLong() {
        return round().asLong();
    }
    
    public default IntegerToDoubleAccessPrimitive floor() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.floor(value);
        };
    }
    public default IntegerToIntegerAccessPrimitive floorToInt() {
        return floor().asInteger();
    }
    public default IntegerToLongAccessPrimitive floorToLong() {
        return floor().asLong();
    }
    
    public default IntegerToDoubleAccessPrimitive roundBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive roundBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive roundBy(ToDoubleBiDoubleFunction<Integer> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive ceilBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive ceilBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive ceilBy(ToDoubleBiDoubleFunction<Integer> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive floorBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive floorBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default IntegerToDoubleAccessPrimitive floorBy(ToDoubleBiDoubleFunction<Integer> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    
    public default IntegerToStringAccessPrimitive asString() {
        return host -> "" + applyAsDouble(host);
    }
    public default IntegerToStringAccessPrimitive asString(String template) {
        return host -> {
            val value = applyAsDouble(host);
            return String.format(template, value);
        };
    }
    
    // TODO - Find a better way to format this that allow a fix width disregards of the magnitude of the value.
    //          or just redirect the format to another function that can be substituted.
    
    //-- to value --
    
    public default IntegerToDoubleAccessPrimitive toZero() {
        return host -> 0.0;
    }
    
    public default IntegerToDoubleAccessPrimitive toOne() {
        return host -> 1.0;
    }
    
    public default IntegerToDoubleAccessPrimitive toMinusOne() {
        return host -> -1.0;
    }
    
    public default IntegerToDoubleAccessPrimitive to(double anotherValue) {
        return host -> {
            val value   = applyAsDouble(host);
            val compare = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToDoubleAccessPrimitive to(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToDoubleAccessPrimitive to(ToDoubleFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToDoubleAccessPrimitive to(ToDoubleBiDoubleFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    
    //-- Equality --
    
    public default IntegerToBooleanAccessPrimitive that(DoublePredicate checker) {
        return host -> {
            val value = applyAsDouble(host);
            return checker.test(value);
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIs(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(ToDoubleFunction<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(ToDoubleBiDoubleFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value == anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNot(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(ToDoubleFunction<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(ToDoubleBiDoubleFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value != anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(double ... otherValues) {
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
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(double ... otherValues) {
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
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsOne() {
        return thatIs(1);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsZero() {
        return thatIs(0.0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsMinusOne() {
        return thatIs(-1.0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsFourtyTwo() {
        return thatIs(42.0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNotOne() {
        return thatIsNot(1.0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNotZero() {
        return thatIsNot(0.0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNotMinusOne() {
        return thatIsNot(-1.0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsPositive() {
        return host -> {
            val value = applyAsDouble(host);
            return value > 0.0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNegative() {
        return host -> {
            val value = applyAsDouble(host);
            return value < 0.0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNotPositive() {
        return host -> {
            val value = applyAsDouble(host);
            return value <= 0.0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNotNegative() {
        return host -> {
            val value = applyAsDouble(host);
            return value >= 0.0;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsRound() {
        return host -> {
            val value = applyAsDouble(host);
            return 1.0*Math.round(value) == value;
        };
    }
    
    public default IntegerToDoubleAccessEqualPrimitive thatEquals(double anotherValue) {
        return new IntegerToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherValue);
    }
    public default IntegerToDoubleAccessEqualPrimitive thatEquals(DoubleSupplier anotherSupplier) {
        return new IntegerToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default IntegerToDoubleAccessEqualPrimitive thatEquals(DoubleUnaryOperator anotherAccess) {
        return new IntegerToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherAccess.applyAsDouble(value));
    }
    public default IntegerToDoubleAccessEqualPrimitive thatEquals(DoubleIntegerToDoubleFunction anotherFunction) {
        return new IntegerToDoubleAccessEqualPrimitive(false, this, anotherFunction);
    }
    
    public default IntegerToDoubleAccessEqualPrimitive eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    public default IntegerToDoubleAccessEqualPrimitive eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default IntegerToDoubleAccessEqualPrimitive eq(DoubleUnaryOperator anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default IntegerToDoubleAccessEqualPrimitive eq(DoubleIntegerToDoubleFunction anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default IntegerToDoubleAccessEqualPrimitive thatNotEquals(double anotherValue) {
        return new IntegerToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherValue);
    }
    public default IntegerToDoubleAccessEqualPrimitive thatNotEquals(DoubleSupplier anotherSupplier) {
        return new IntegerToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default IntegerToDoubleAccessEqualPrimitive thatNotEquals(DoubleUnaryOperator anotherAccess) {
        return new IntegerToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherAccess.applyAsDouble(value));
    }
    public default IntegerToDoubleAccessEqualPrimitive thatNotEquals(DoubleIntegerToDoubleFunction anotherFunction) {
        return new IntegerToDoubleAccessEqualPrimitive(true, this, anotherFunction);
    }
    
    public default IntegerToDoubleAccessEqualPrimitive neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default IntegerToDoubleAccessEqualPrimitive neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default IntegerToDoubleAccessEqualPrimitive neq(DoubleUnaryOperator anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default IntegerToDoubleAccessEqualPrimitive neq(DoubleIntegerToDoubleFunction anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    //-- Compare --
    
    public default DoubleComparator ascendingOrder() {
        return (a, b) -> Double.compare(a, b);
    }
    
    public default DoubleComparator descendingOrder() {
        return (a, b) -> Double.compare(b, a);
    }
    
    public default IntegerToIntegerAccessPrimitive compareTo(double anotherValue) {
        return host -> {
            val value   = applyAsDouble(host);
            val compare = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    public default IntegerToIntegerAccessPrimitive cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerToIntegerAccessPrimitive cmp(DoubleAccess<Integer> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default IntegerToIntegerAccessPrimitive cmp(ToIntBiFunction<Integer, Double> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value > anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gt(DoubleAccess<Integer> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive gt(ToIntBiFunction<Integer, Double> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatLessThan(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value < anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lt(DoubleAccess<Integer> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive lt(ToIntBiFunction<Integer, Double> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value >= anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gteq(DoubleAccess<Integer> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive gteq(ToIntBiFunction<Integer, Double> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value <= anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lteq(DoubleAccess<Integer> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive lteq(ToIntBiFunction<Integer, Double> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    // TODO - See if the following is valid
    //-- digitAt
    //-- digitValueAt
    //-- factorValueAt
    //-- factorValueAt
    
    //-- Min+Max --
    
    public default IntegerToDoubleAccessPrimitive min(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive min(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive min(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive min(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return Math.min(value, anotherValue);
        };
    }
    
    public default IntegerToDoubleAccessPrimitive max(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive max(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive max(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive max(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    public default IntegerToDoubleAccessPrimitive abs() {
        return host -> {
            val value = applyAsDouble(host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive negate() {
        return host -> {
            val value = applyAsDouble(host);
            return -value;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive signum() {
        return host -> {
            val value = applyAsDouble(host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive plus(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value + anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive plus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value + anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive plus(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value + anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive plus(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value + anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive minus(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value - anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive minus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value - anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive minus(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value - anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive minus(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value - anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive time(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value * anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive time(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value * anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive time(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value * anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive time(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value * anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive dividedBy(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return 1.0 * value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(DoubleSupplier anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.getAsDouble();
            return 1.0*value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return 1.0*value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return 1.0*value / anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive remainderBy(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value % anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value % anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive remainderBy(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return value % anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive remainderBy(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return value % anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive square() {
        return host -> {
            val value = applyAsDouble(host);
            return value * value;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive squareRoot () {
        return host -> {
            val value = applyAsDouble(host);
            return Math.sqrt(value);
        };
    }
    
    public default IntegerToDoubleAccessPrimitive pow(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.pow(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(DoubleAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerToDoubleAccessPrimitive.apply(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(ToIntBiFunction<Integer, Double> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = IntegerDoubleToIntegerFunction.apply(anotherFunction, host, value);
            return Math.pow(value, anotherValue);
        };
    }
    
    public default IntegerToDoubleAccessPrimitive exp() {
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
    public default IntegerToDoubleAccessPrimitive expm1() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.expm1(doubleValue);
        };
    }
    
    public default IntegerToDoubleAccessPrimitive log() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log(doubleValue);
        };
    }
    
    public default IntegerToDoubleAccessPrimitive log10() {
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
    public default IntegerToDoubleAccessPrimitive log1p() {
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
