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
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import functionalj.function.DoubleComparator;
import functionalj.list.intlist.IntFuncList;
import lombok.val;

/**
 * Classes implementing this interface know how to access from a double to an integer value.
 */
@FunctionalInterface
public interface DoubleToIntegerAccessPrimitive extends IntegerAccessPrimitive<Double>, DoubleToIntFunction, DoubleFunction<Integer> {
    
    // == Constructor ==
    public static DoubleToIntegerAccessPrimitive of(DoubleToIntegerAccessPrimitive accessToValue) {
        return accessToValue;
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive newAccess(Function<Double, Integer> accessToValue) {
        return accessToValue::apply;
    }
    
    // == abstract functionalities ==
    public int applyDoubleToInt(double host);
    
    public default int applyAsInt(double operand) {
        return applyDoubleToInt(operand);
    }
    
    public default int applyAsInt(Double host) {
        return applyDoubleToInt(host);
    }
    
    @Override
    public default Integer apply(double host) {
        return applyDoubleToInt(host);
    }
    
    // -- conversion --
    public default IntegerAccessBoxed<Double> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive asInteger() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default DoubleToLongAccessPrimitive asLong() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive asDouble() {
        return host -> accessPrimitive(this, host);
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
    
    // -- Equality --
    @Override
    public default DoubleToBooleanAccessPrimitive that(IntPredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIs(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIs(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIs(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNot(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNot(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNot(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(int... otherValues) {
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
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(IntFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsNoneOf(int... otherValues) {
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
    public default DoubleToBooleanAccessPrimitive thatIsNoneOf(IntFuncList otherValues) {
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
    public default DoubleToBooleanAccessPrimitive thatEquals(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatEquals(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive eq(int anotherValue) {
        return thatEquals(anotherValue);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive eq(IntSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    
    public default DoubleToBooleanAccessPrimitive eq(DoubleToIntegerAccessPrimitive anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatNotEquals(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            int anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatNotEquals(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive neq(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive neq(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default DoubleToBooleanAccessPrimitive neq(DoubleToIntegerAccessPrimitive anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatNotEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    // -- Compare --
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
    public default DoubleToIntegerAccessPrimitive compareTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive compareTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive compareTo(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive cmp(int anotherValue) {
        return compareTo(anotherValue);
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive cmp(IntSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    
    public default DoubleToIntegerAccessPrimitive cmp(DoubleToIntegerAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive gt(int anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive gt(IntSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    
    public default DoubleToBooleanAccessPrimitive gt(DoubleToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThan(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatLessThan(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive lt(int anotherValue) {
        return thatLessThan(anotherValue);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive lt(IntSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    
    public default DoubleToBooleanAccessPrimitive lt(DoubleToIntegerAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive gteq(int anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive gteq(IntSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    
    public default DoubleToBooleanAccessPrimitive gteq(DoubleToIntegerAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive lteq(int anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive lteq(IntSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    
    public default DoubleToBooleanAccessPrimitive lteq(DoubleToIntegerAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    // -- Min+Max --
    @Override
    public default DoubleToIntegerAccessPrimitive min(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive min(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    
    public default DoubleToIntegerAccessPrimitive min(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive max(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive max(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    
    public default DoubleToIntegerAccessPrimitive max(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    
    // -- Math --
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsOdd() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % 2 != 0;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsEven() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % 2 == 0;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsDivisibleBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default DoubleToBooleanAccessPrimitive thatIsDivisibleBy(IntSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsInt();
            return value % anotherValue == 0;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsDivisibleBy(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive plus(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive plus(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive plus(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive minus(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive minus(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive minus(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive time(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive time(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive time(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive dividedBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive dividedBy(IntSupplier anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = anotherAccess.getAsInt();
            return 1.0 * value / anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive dividedBy(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive remainderBy(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value % anotherValue;
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive remainderBy(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value % anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive remainderBy(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value % anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive inverse() {
        return host -> {
            val value = access(this, host);
            return 1 / (value * 1.0);
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive square() {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * value;
        };
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive squareRoot() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.sqrt(value);
        };
    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive factorial() {
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
    public default DoubleToLongAccessPrimitive pow(int anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default DoubleToLongAccessPrimitive pow(IntSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    public default DoubleToLongAccessPrimitive pow(DoubleToIntegerAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
}
