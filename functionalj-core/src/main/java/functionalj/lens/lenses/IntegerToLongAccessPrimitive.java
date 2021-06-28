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

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;

import functionalj.function.IntComparator;
import lombok.val;


/**
 * Classes implementing this interface know how to access from an int to a long value.
 **/
@FunctionalInterface
public interface IntegerToLongAccessPrimitive 
                    extends 
                        LongAccessPrimitive<Integer>, 
                        IntToLongFunction,
                        IntFunction<Long>{
    
    //== Constructor ==
    
    public static IntegerToLongAccessPrimitive of(IntegerToLongAccessPrimitive accessToValue) {
        return accessToValue;
    }
    
    @Override
    public default IntegerToLongAccessPrimitive newAccess(Function<Integer, Long> accessToValue) {
        return accessToValue::apply;
    }
    
    //== abstract functionalities ==
    
    public long applyIntToLong(int host);
    
    public default long applyAsLong(int operand) {
        return applyIntToLong(operand);
    }
    
    public default long applyAsLong(Integer host) {
        return applyIntToLong(host);
    }
    
    @Override
    public default Long apply(int host) {
        return applyIntToLong(host);
    }
    
    @Override
    public default Long apply(Integer input) {
        return LongAccessPrimitive.super.apply(input);
    }
    
    
    //-- conversion --
    
    public default LongAccessBoxed<Integer> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public default IntegerToIntegerAccessPrimitive asInteger(int overflowValue) {
        return asInteger(overflowValue);
    }
    
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
    public default IntegerToLongAccessPrimitive asLong() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive asDouble() {
        return host -> accessPrimitive(this, host);
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
    
    //-- Equality --
    
    @Override
    public default IntegerToBooleanAccessPrimitive that(LongPredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIs(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatIs(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNot(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNot(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(long ... otherValues) {
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
//    @Override
//    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(LongFuncList otherValues) {
//        return host -> {
//            val value = accessPrimitive(this, host);
//            return otherValues.anyMatch(anotherValue -> value == anotherValue);
//        };
//    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(long ... otherValues) {
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
//    @Override
//    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(LongFuncList otherValues) {
//        return host -> {
//            val value = accessPrimitive(this, host);
//            return otherValues.noneMatch(anotherValue -> value == anotherValue);
//        };
//    }
    
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
    public default IntegerToBooleanAccessPrimitive thatEquals(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatEquals(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatEquals(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive eq(long anotherValue) {
        return thatEquals(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive eq(LongSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive eq(LongToIntegerAccessPrimitive anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatNotEquals(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatNotEquals(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatNotEquals(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive neq(long anotherValue) {
        return thatNotEquals(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive neq(LongSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive neq(LongToIntegerAccessPrimitive anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatNotEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    //-- Compare --
    
    @Override
    public default IntComparator inOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(aValue, bValue);
        };
    }
    
    @Override
    public default IntComparator inReverseOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(bValue, aValue);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive compareTo(long anotherValue) {
        return host -> {
            val value   = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    @Override
    public default IntegerToIntegerAccessPrimitive compareTo(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare      = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val compare      = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive cmp(long anotherValue) {
        return compareTo(anotherValue);
    }
    @Override
    public default IntegerToIntegerAccessPrimitive cmp(LongSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerToIntegerAccessPrimitive cmp(LongToIntegerAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gt(long anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive gt(LongSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gt(LongToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThan(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThan(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lt(long anotherValue) {
        return thatLessThan(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive lt(LongSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lt(LongToIntegerAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gteq(long anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive gteq(LongSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gteq(LongToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lteq(long anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    @Override
    public default IntegerToBooleanAccessPrimitive lteq(LongSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lteq(LongToIntegerAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    //-- Min+Max --
    
    @Override
    public default IntegerToLongAccessPrimitive min(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    @Override
    public default IntegerToLongAccessPrimitive min(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToLongAccessPrimitive min(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive max(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    @Override
    public default IntegerToLongAccessPrimitive max(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToLongAccessPrimitive max(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsOdd() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % 2 != 0;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsEven() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % 2 == 0;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue == 0;
        };
    }
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(LongSupplier anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherAccess);
            return value % anotherValue == 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive plus(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    @Override
    public default IntegerToLongAccessPrimitive plus(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    public default IntegerToLongAccessPrimitive plus(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive minus(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    @Override
    public default IntegerToLongAccessPrimitive minus(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    public default IntegerToLongAccessPrimitive minus(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive time(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    @Override
    public default IntegerToLongAccessPrimitive time(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    public default IntegerToLongAccessPrimitive time(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive dividedBy(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    @Override
    public default IntegerToDoubleAccessPrimitive dividedBy(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return 1.0*value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return 1.0*value / anotherValue;
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive remainderBy(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue;
        };
    }
    @Override
    public default IntegerToLongAccessPrimitive remainderBy(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value % anotherValue;
        };
    }
    public default IntegerToLongAccessPrimitive remainderBy(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive inverse() {
        return host -> {
            val value = access(this, host);
            return 1/(value * 1.0);
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive square() {
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
    public default IntegerToLongAccessPrimitive factorial() {
        return host -> {
            val value = accessPrimitive(this, host);
            if (value <= 0) {
                return 1;
            }
            
            return factorialRef.get().applyAsLong(value);
        };
    }
    
    // TODO - Make this Long once we are ready.
    
    @Override
    public default IntegerToLongAccessPrimitive pow(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return (long)Math.pow(value, anotherValue);
        };
    }
    @Override
    public default IntegerToLongAccessPrimitive pow(LongSupplier valueSupplier) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return (long)Math.pow(value, anotherValue);
        };
    }
    public default IntegerToLongAccessPrimitive pow(IntegerToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value        = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return (long)Math.pow(value, anotherValue);
        };
    }
    
}
