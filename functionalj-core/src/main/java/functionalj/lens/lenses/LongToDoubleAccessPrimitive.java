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
import static functionalj.function.Apply.applyPrimitive;
import static functionalj.function.Apply.getPrimitive;
import static functionalj.function.Compare.comparePrimitive;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToDoubleFunction;
import functionalj.function.LongComparator;
import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;

/**
 * Classes implementing this interface know how to access from a long to a double value.
 */
@FunctionalInterface
public interface LongToDoubleAccessPrimitive extends DoubleAccessPrimitive<Long>, LongToDoubleFunction, LongFunction<Double> {
    
    // == Constructor ==
    @Override
    public default LongToDoubleAccessPrimitive newAccess(Function<Long, Double> accessToValue) {
        return accessToValue::apply;
    }
    
    // == abstract functionalities ==
    public double applyLongToDouble(long host);
    
    public default double applyAsDouble(long operand) {
        return applyLongToDouble(operand);
    }
    
    public default double applyAsDouble(Long host) {
        return applyLongToDouble(host);
    }
    
    public default Double apply(long host) {
        return applyLongToDouble(host);
    }
    
    // -- conversion --
    public default DoubleAccessBoxed<Long> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    @Override
    public default LongToLongAccessPrimitive asLong() {
        return asLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    @Override
    public default LongToDoubleAccessPrimitive asDouble() {
        return host -> accessPrimitive(this, host);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive asInteger(int overflowValue) {
        return asInteger(overflowValue, overflowValue);
    }
    
    @Override
    public default LongToLongAccessPrimitive asLong(long overflowValue) {
        return asLong(overflowValue, overflowValue);
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
    public default LongToLongAccessPrimitive asLong(long negativeOverflowValue, long positiveOverflowValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (value < Long.MIN_VALUE)
                return negativeOverflowValue;
            if (value > Long.MAX_VALUE)
                return positiveOverflowValue;
            return (long) Math.round(value);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive round() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.round(value);
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive roundToInt() {
        return round().asInteger();
    }
    
    @Override
    public default LongToLongAccessPrimitive roundToLong() {
        return round().asLong();
    }
    
    @Override
    public default LongToDoubleAccessPrimitive ceil() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.ceil(value);
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive ceilToInt() {
        return round().asInteger();
    }
    
    @Override
    public default LongToLongAccessPrimitive ceilToLong() {
        return round().asLong();
    }
    
    @Override
    public default LongToDoubleAccessPrimitive floor() {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.floor(value);
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive floorToInt() {
        return floor().asInteger();
    }
    
    @Override
    public default LongToLongAccessPrimitive floorToLong() {
        return floor().asLong();
    }
    
    @Override
    public default LongToDoubleAccessPrimitive roundBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            return Math.round(value / precision) * precision;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive roundBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.round(value);
            }
            return Math.round(value / precision) * precision;
        };
    }
    
    public default LongToDoubleAccessPrimitive roundBy(LongToDoubleAccessPrimitive precisionFunction) {
        return host -> {
            val value = accessPrimitive(this, host);
            val precision = accessPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            return Math.round(value / precision) * precision;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive ceilBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            return Math.ceil(value / precision) * precision;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive ceilBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            return Math.ceil(value / precision) * precision;
        };
    }
    
    public default LongToDoubleAccessPrimitive ceilBy(LongToDoubleAccessPrimitive precisionFunction) {
        return host -> {
            val value = accessPrimitive(this, host);
            val precision = accessPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            return Math.ceil(value / precision) * precision;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive floorBy(double precision) {
        return host -> {
            val value = accessPrimitive(this, host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            return Math.floor(value / precision) * precision;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive floorBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            return Math.floor(value / precision) * precision;
        };
    }
    
    public default LongToDoubleAccessPrimitive floorBy(LongToDoubleAccessPrimitive precisionFunction) {
        return host -> {
            val value = accessPrimitive(this, host);
            val precision = accessPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            return Math.floor(value / precision) * precision;
        };
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
    
    // TODO - Find a better way to format this that allow a fix width disregards of the magnitude of the value.
    // or just redirect the format to another function that can be substituted.
    // -- Equality --
    @Override
    public default LongToBooleanAccessPrimitive that(DoublePredicate checker) {
        return host -> {
            val value = accessPrimitive(this, host);
            return checker.test(value);
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIs(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIs(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNot(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIsNot(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsAnyOf(double... otherValues) {
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
    public default LongToBooleanAccessPrimitive thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatIsNoneOf(double... otherValues) {
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
    public default LongToBooleanAccessPrimitive thatIsNoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = accessPrimitive(this, host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
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
    public default LongToDoubleAccessEqualPrimitive thatEquals(double anotherValue) {
        return new LongToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherValue);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatEquals(DoubleSupplier anotherSupplier) {
        return new LongToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    
    public default LongToDoubleAccessEqualPrimitive thatEquals(LongToDoubleAccessPrimitive anotherAccess) {
        return new LongToDoubleAccessEqualPrimitive(false, this, (host, value) -> anotherAccess.applyAsDouble(host));
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    
    public default LongToDoubleAccessEqualPrimitive eq(LongToDoubleAccessPrimitive anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatNotEquals(double anotherValue) {
        return new LongToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherValue);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatNotEquals(DoubleSupplier anotherSupplier) {
        return new LongToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    
    public default LongToDoubleAccessEqualPrimitive thatNotEquals(DoubleUnaryOperator anotherAccess) {
        return new LongToDoubleAccessEqualPrimitive(true, this, (host, value) -> anotherAccess.applyAsDouble(value));
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default LongToDoubleAccessEqualPrimitive neq(DoubleUnaryOperator anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    @Override
    public default LongToDoubleAccessEqualPrimitive thatNotEqualsMinusOne() {
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
    public default LongToIntegerAccessPrimitive compareTo(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val compare = comparePrimitive(value, anotherValue);
            return compare;
        };
    }
    
    @Override
    public default LongToIntegerAccessPrimitive compareTo(DoubleSupplier anotherSupplier) {
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
    public default LongToIntegerAccessPrimitive cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    
    @Override
    public default LongToIntegerAccessPrimitive cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    
    public default LongToIntegerAccessPrimitive cmp(LongToIntegerAccessPrimitive anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThan(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThan(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive gt(LongToDoubleAccessPrimitive anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThan(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatLessThan(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive lt(LongToDoubleAccessPrimitive anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive gteq(LongToDoubleAccessPrimitive anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    
    @Override
    public default LongToBooleanAccessPrimitive lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    
    public default LongToBooleanAccessPrimitive lteq(LongToDoubleAccessPrimitive anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    // -- Min+Max --
    @Override
    public default LongToDoubleAccessPrimitive min(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive min(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    
    public default LongToDoubleAccessPrimitive min(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive max(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.max(value, anotherValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive max(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    
    public default LongToDoubleAccessPrimitive max(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    
    // -- Math --
    @Override
    public default LongToBooleanAccessPrimitive thatIsRound() {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * Math.round(value) == value;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive abs() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive negate() {
        return host -> {
            val value = accessPrimitive(this, host);
            return -value;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive signum() {
        return host -> {
            val value = accessPrimitive(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive plus(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive plus(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive plus(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value + anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive minus(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive minus(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive minus(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value - anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive time(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive time(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive time(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = accessPrimitive(anotherAccess, host);
            return value * anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive dividedBy(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive dividedBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive dividedBy(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive remainderBy(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    public default LongToDoubleAccessPrimitive remainderBy(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            val division = Math.floor(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    public default LongToDoubleAccessPrimitive inverse() {
        return host -> {
            val value = access(this, host);
            return 1 / (value * 1.0);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive square() {
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
    public default LongToDoubleAccessPrimitive pow(double anotherValue) {
        return host -> {
            val value = accessPrimitive(this, host);
            return Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive pow(DoubleSupplier valueSupplier) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.pow(value, anotherValue);
        };
    }
    
    public default LongToDoubleAccessPrimitive pow(LongToDoubleAccessPrimitive anotherAccess) {
        return host -> {
            val value = accessPrimitive(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive exp() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.exp(doubleValue);
        };
    }
    
    /**
     * Returns &lt;i&gt;e&lt;/i&gt;&lt;sup&gt;x&lt;/sup&gt;&nbsp;-1.  Note that for values of
     * &lt;i&gt;x&lt;/i&gt; near 0, the exact sum of
     * {@code expm1(x)}&nbsp;+&nbsp;1 is much closer to the true
     * result of &lt;i&gt;e&lt;/i&gt;&lt;sup&gt;x&lt;/sup&gt; than {@code exp(x)}.
     */
    @Override
    public default LongToDoubleAccessPrimitive expm1() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.expm1(doubleValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive log() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.log(doubleValue);
        };
    }
    
    @Override
    public default LongToDoubleAccessPrimitive log10() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.log10(doubleValue);
        };
    }
    
    /**
     * Returns the base 10 logarithm of a {@code double} value.
     * Special cases:
     *
     * &lt;ul&gt;&lt;li&gt;If the argument is NaN or less than zero, then the result
     * is NaN.
     * &lt;li&gt;If the argument is positive infinity, then the result is
     * positive infinity.
     * &lt;li&gt;If the argument is positive zero or negative zero, then the
     * result is negative infinity.
     * &lt;li&gt; If the argument is equal to 10&lt;sup&gt;&lt;i&gt;n&lt;/i&gt;&lt;/sup&gt; for
     * integer &lt;i&gt;n&lt;/i&gt;, then the result is &lt;i&gt;n&lt;/i&gt;.
     * &lt;/ul&gt;
     *
     * &lt;p&gt;The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     */
    @Override
    public default LongToDoubleAccessPrimitive log1p() {
        return host -> {
            double doubleValue = accessPrimitive(this, host);
            return Math.log1p(doubleValue);
        };
    }
    // // TODO - Add more
    // //    Math.addExact((int)0, (int)0)
    // //    Math.addExact((long)0, (long)0)
    // //    Math.decrementExact((int)0)
    // //    Math.decrementExact((long)0)
    // //    Math.incrementExact((int)0)
    // //    Math.incrementExact((long)0)
    // //    Math.multiplyExact(int, int)
    // //    Math.multiplyExact(long, long)
    // //    Math.negateExact(int)
    // //    Math.negateExact(long)
    // //    Math.subtractExact(int, int)
    // //    Math.subtractExact(long, long)
    // //    Math.toIntExact(0)
    // 
    // // TODO - Add more.
    // 
    // //    Math.acos(doubleValue)
    // //    Math.asin(doubleValue)
    // //    Math.tan(doubleValue)
    // //    Math.tan2(doubleValue)
    // //    Math.cos(doubleValue)
    // //    Math.cosh(doubleValue)
    // //    Math.sin(doubleValue)
    // //    Math.sinh(doubleValue)
    // //    Math.tan(doubleValue)
    // //    Math.tanh(doubleValue)
    // //
    // //    Math.toDegrees(doubleValue)
    // //    Math.toRadians(doubleValue)
    // 
}
