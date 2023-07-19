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
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import functionalj.function.LongComparator;
import lombok.val;

/**
 * Classes implementing this interface know how to access from a long to a long value.
 */
@FunctionalInterface
public interface LongToLongAccessPrimitive extends LongUnaryOperator, LongAccessPrimitive<Long>, LongFunction<Long> {
    
    // == Constructor ==
    public static LongToLongAccessPrimitive of(LongToLongAccessPrimitive accessToValue) {
        return accessToValue;
    }
    
    public static LongToLongAccessPrimitive from(LongUnaryOperator accessToValue) {
        return host -> accessToValue.applyAsLong(host);
    }
    
    @Override
    public default LongToLongAccessPrimitive newAccess(Function<Long, Long> accessToValue) {
        return accessToValue::apply;
    }
    
    public default LongToLongAccessPrimitive newAccess(LongUnaryOperator accessToValue) {
        return accessToValue::applyAsLong;
    }
    
    // == abstract functionalities ==
    public long applyLongToLong(long host);
    
    @Override
    public default Long apply(long host) {
        return applyLongToLong(host);
    }
    
    @Override
    public default long applyAsLong(long host) {
        return applyLongToLong(host);
    }
    
    @Override
    public default long applyAsLong(Long host) {
        return applyLongToLong(host);
    }
    
    // -- conversion --
    @Override
    public default LongToLongAccessBoxed boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive asInteger(int overflowValue) {
        return asInteger(overflowValue);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive asInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (value < Integer.MIN_VALUE)
                return negativeOverflowValue;
            if (value > Integer.MAX_VALUE)
                return positiveOverflowValue;
            return (int) Math.round(value);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive asLong() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default LongToDoubleAccessPrimitive asDouble() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default LongToStringAccessPrimitive asString() {
        return host -> "" + accessPrimitive(this, host);
    }
    
    @Override
    public default LongToStringAccessPrimitive asString(String template) {
        return host -> {
            val value = accessPrimitive(this, host);
            return String.format(template, value);
        };
    }
    
    // -- Equality --
    @Override
    public default LongToBooleanAccessPrimitive that(LongPredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIs(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIs(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIs(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNot(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNot(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIsNot(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsAnyOf(long... otherValues) {
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
    
    // @Override
    // public default LongToBooleanAccessPrimitive thatIsAnyOf(LongFuncList otherValues) {
    // return host -> {
    // val value = accessPrimitive(this, host);
    // return otherValues.anyMatch(anotherValue -> value == anotherValue);
    // };
    // }
    @Override
    public default LongToBooleanAccessPrimitive thatIsNoneOf(long... otherValues) {
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
    
    // @Override
    // public default LongToBooleanAccessPrimitive thatIsNoneOf(LongFuncList otherValues) {
    // return host -> {
    // val value = accessPrimitive(this, host);
    // return otherValues.noneMatch(anotherValue -> value == anotherValue);
    // };
    // }
    @Override
    public default LongToBooleanAccessPrimitive thatIsOne() {
        return thatIs(1);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsZero() {
        return thatIs(0);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsMinusOne() {
        return thatIs(-1);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsFourtyTwo() {
        return thatIs(42);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNotOne() {
        return thatIsNot(1);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNotZero() {
        return thatIsNot(0);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNotMinusOne() {
        return thatIsNot(-1);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsPositive() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNegative() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNotPositive() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNotNegative() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatEquals(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatEquals(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatEquals(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive eq(long anotherValue) {
        return thatEquals(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive eq(LongSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive eq(LongToLongAccessPrimitive anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatNotEquals(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatNotEquals(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatNotEquals(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive neq(long anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive neq(LongSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive neq(LongToLongAccessPrimitive anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatEqualsZero() {
        return thatEquals(0);
    }
    
    public default LongToBooleanAccessPrimitive thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatNotEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    // -- Compare --
    @Override
    public default LongComparator inOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(aValue, bValue);
        };
    }
    
    @Override
    public default LongComparator inReverseOrder() {
        return (a, b) -> {
            val aValue = accessPrimitive(this, a);
            val bValue = accessPrimitive(this, b);
            return comparePrimitive(bValue, aValue);
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive compareTo(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive compareTo(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    public default LongToIntegerAccessPrimitive compareTo(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive cmp(long anotherValue) {
        return compareTo(anotherValue);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive cmp(LongSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    
    public default LongToIntegerAccessPrimitive cmp(LongToLongAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThan(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThan(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThan(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gt(long anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gt(LongSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive gt(LongToLongAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThan(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThan(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatLessThan(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lt(long anotherValue) {
        return thatLessThan(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lt(LongSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive lt(LongToLongAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gteq(long anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gteq(LongSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive gteq(LongToLongAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lteq(long anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lteq(LongSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive lteq(LongToLongAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    // -- Min+Max --
    @Override
    public default LongToLongAccessPrimitive min(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive min(LongSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    
    public default LongToLongAccessPrimitive min(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive max(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive max(LongSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    
    public default LongToLongAccessPrimitive max(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    
    // -- Math --
    @Override
    public default LongToBooleanAccessPrimitive thatIsOdd() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % 2 != 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsEven() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % 2 == 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(LongSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsLong();
            return value % anotherValue == 0;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive plus(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive plus(LongSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    
    public default LongToLongAccessPrimitive plus(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive minus(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive minus(LongSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    
    public default LongToLongAccessPrimitive minus(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive time(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive time(LongSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    
    public default LongToLongAccessPrimitive time(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive dividedBy(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive dividedBy(LongSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsLong();
            return 1.0 * value / anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive dividedBy(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive remainderBy(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue;
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive remainderBy(LongSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value % anotherValue;
        };
    }
    
    public default LongToLongAccessPrimitive remainderBy(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive inverse() {
        return host -> {
            val value = access(this, host);
            return 1 / (value * 1.0);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive square() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * value;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive squareRoot() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.sqrt(value);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive factorial() {
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
    public default LongToLongAccessPrimitive pow(long anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive pow(LongSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    public default LongToLongAccessPrimitive pow(LongToLongAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
}
