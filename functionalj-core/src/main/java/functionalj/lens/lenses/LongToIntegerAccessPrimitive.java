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
package functionalj.lens.lenses;

import static functionalj.function.Apply.access;
import static functionalj.function.Apply.accessPrimitive;
import static functionalj.function.Apply.getPrimitive;
import static functionalj.function.Compare.comparePrimitive;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;
import functionalj.function.LongComparator;
import lombok.val;

/**
 * Classes implementing this interface know how to access from a long to an integer value.
 */
@FunctionalInterface
public interface LongToIntegerAccessPrimitive extends IntegerAccessPrimitive<Long>, LongToIntFunction, LongFunction<Integer>, LongToIntegerAccessBoxed {
    
    // == Constructor ==
    public static LongToIntegerAccessPrimitive of(LongToIntegerAccessPrimitive accessToValue) {
        return accessToValue;
    }
    
    @Override
    public default LongToIntegerAccessPrimitive newAccess(Function<Long, Integer> accessToValue) {
        return accessToValue::apply;
    }
    
    // == abstract functionalities ==
    public int applyLongToInt(long host);
    
    @Override
    public default Integer applyUnsafe(Long host) throws Exception {
        return LongToIntegerAccessBoxed.super.applyUnsafe(host);
    }
    
    public default int applyAsInt(long operand) {
        return applyLongToInt(operand);
    }
    
    public default int applyAsInt(Long host) {
        return applyLongToInt(host);
    }
    
    @Override
    public default Integer apply(long host) {
        return applyLongToInt(host);
    }
    
    @Override
    public default Integer apply(Long input) {
        return IntegerAccessPrimitive.super.apply(input);
    }
    
    // -- conversion --
    public default IntegerAccessBoxed<Long> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive asInteger() {
        return host -> accessPrimitive(this, host);
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
    public default LongToBooleanAccessPrimitive that(IntPredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIs(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIs(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIs(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNot(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNot(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIsNot(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsAnyOf(int... otherValues) {
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
    public default LongToBooleanAccessPrimitive thatIsNoneOf(int... otherValues) {
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
    public default LongToBooleanAccessPrimitive thatEquals(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatEquals(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive eq(int anotherValue) {
        return thatEquals(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive eq(IntSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive eq(LongToIntegerAccessPrimitive anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatNotEquals(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            int anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatNotEquals(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive neq(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive neq(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive neq(LongToIntegerAccessPrimitive anotherAccess) {
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
    
    @Override
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
    public default LongToIntegerAccessPrimitive compareTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive compareTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    public default LongToIntegerAccessPrimitive compareTo(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive cmp(int anotherValue) {
        return compareTo(anotherValue);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive cmp(IntSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    
    public default LongToIntegerAccessPrimitive cmp(LongToIntegerAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThan(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThan(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gt(int anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gt(IntSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive gt(LongToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThan(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatLessThan(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lt(int anotherValue) {
        return thatLessThan(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lt(IntSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive lt(LongToIntegerAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gteq(int anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gteq(IntSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive gteq(LongToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lteq(int anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lteq(IntSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive lteq(LongToIntegerAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    // -- Min+Max --
    @Override
    public default LongToIntegerAccessPrimitive min(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive min(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    
    public default LongToIntegerAccessPrimitive min(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive max(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive max(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    
    public default LongToIntegerAccessPrimitive max(LongToIntegerAccessPrimitive anotherAccess) {
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
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(IntSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsInt();
            return value % anotherValue == 0;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive plus(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive plus(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    
    public default LongToIntegerAccessPrimitive plus(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive minus(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive minus(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    
    public default LongToIntegerAccessPrimitive minus(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive time(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive time(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    
    public default LongToIntegerAccessPrimitive time(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive dividedBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive dividedBy(IntSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsInt();
            return 1.0 * value / anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive dividedBy(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive remainderBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive remainderBy(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value % anotherValue;
        };
    }
    
    public default LongToIntegerAccessPrimitive remainderBy(LongToIntegerAccessPrimitive anotherAccess) {
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
    public default LongToIntegerAccessPrimitive square() {
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
    public default LongToIntegerAccessPrimitive factorial() {
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
    public default LongToLongAccessPrimitive pow(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default LongToLongAccessPrimitive pow(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    public default LongToLongAccessPrimitive pow(LongToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
}
