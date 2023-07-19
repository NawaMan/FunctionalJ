// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import functionalj.function.IntComparator;
import functionalj.list.intlist.IntFuncList;
import lombok.val;

/**
 * Classes implementing this interface know how to access from an integer to an integer value.
 */
@FunctionalInterface
public interface IntegerToIntegerAccessPrimitive extends IntUnaryOperator, IntegerAccessPrimitive<Integer>, IntFunction<Integer> {
    
    // == Constructor ==
    public static IntegerToIntegerAccessPrimitive of(IntegerToIntegerAccessPrimitive accessToValue) {
        return accessToValue;
    }
    
    public static IntegerToIntegerAccessPrimitive from(IntUnaryOperator accessToValue) {
        return host -> accessToValue.applyAsInt(host);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive newAccess(Function<Integer, Integer> accessToValue) {
        return accessToValue::apply;
    }
    
    public default IntegerToIntegerAccessPrimitive newAccess(IntUnaryOperator accessToValue) {
        return accessToValue::applyAsInt;
    }
    
    // == abstract functionalities ==
    public int applyIntToInt(int host);
    
    @Override
    public default Integer apply(int host) {
        return applyIntToInt(host);
    }
    
    @Override
    public default int applyAsInt(int host) {
        return applyIntToInt(host);
    }
    
    @Override
    public default int applyAsInt(Integer host) {
        return applyIntToInt(host);
    }
    
    // -- conversion --
    public default IntegerToIntegerAccessBoxed boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive asInteger() {
        return host -> accessPrimitive(this, host);
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
    
    // -- Equality --
    @Override
    public default IntegerToBooleanAccessPrimitive that(IntPredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIs(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIs(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIs(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNot(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNot(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNot(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(int... otherValues) {
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
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(IntFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(int... otherValues) {
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
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(IntFuncList otherValues) {
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
    public default IntegerToBooleanAccessPrimitive thatEquals(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatEquals(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive eq(int anotherValue) {
        return thatEquals(anotherValue);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive eq(IntSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    
    public default IntegerToBooleanAccessPrimitive eq(IntegerToIntegerAccessPrimitive anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatNotEquals(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatNotEquals(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive neq(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive neq(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default IntegerToBooleanAccessPrimitive neq(IntegerToIntegerAccessPrimitive anotherAccess) {
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
    
    // -- Compare --
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
    public default IntegerToIntegerAccessPrimitive compareTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive compareTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive compareTo(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive cmp(int anotherValue) {
        return compareTo(anotherValue);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive cmp(IntSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    
    public default IntegerToIntegerAccessPrimitive cmp(IntegerToIntegerAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gt(int anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gt(IntSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    
    public default IntegerToBooleanAccessPrimitive gt(IntegerToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThan(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatLessThan(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lt(int anotherValue) {
        return thatLessThan(anotherValue);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lt(IntSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    
    public default IntegerToBooleanAccessPrimitive lt(IntegerToIntegerAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gteq(int anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive gteq(IntSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    
    public default IntegerToBooleanAccessPrimitive gteq(IntegerToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lteq(int anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive lteq(IntSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    
    public default IntegerToBooleanAccessPrimitive lteq(IntegerToIntegerAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    // -- Min+Max --
    @Override
    public default IntegerToIntegerAccessPrimitive min(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive min(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive min(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive max(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive max(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive max(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    
    // -- Math --
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
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(IntSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsInt();
            return value % anotherValue == 0;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive plus(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive plus(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive plus(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive minus(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive minus(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive minus(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive time(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive time(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive time(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive dividedBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive dividedBy(IntSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsInt();
            return 1.0 * value / anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive dividedBy(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive remainderBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue;
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive remainderBy(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value % anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive remainderBy(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive inverse() {
        return host -> {
            val value = access(this, host);
            return 1 / (value * 1.0);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive square() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * value;
        };
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive squareRoot() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.sqrt(value);
        };
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive factorial() {
        return host -> {
            val value = accessPrimitive(this, host);
            if (value <= 0) {
                return 1;
            }
            return factorialRef.get().applyAsInt(value);
        };
    }
    
    // TODO - Make this Long once we are ready.
    @Override
    public default IntegerToLongAccessPrimitive pow(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default IntegerToLongAccessPrimitive pow(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    public default IntegerToLongAccessPrimitive pow(IntegerToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
}
