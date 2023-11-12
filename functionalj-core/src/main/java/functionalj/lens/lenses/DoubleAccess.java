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
import static functionalj.function.Apply.applyPrimitive;
import static functionalj.function.Apply.getPrimitive;
import static functionalj.function.Compare.compareOrNull;
import static java.util.Objects.requireNonNull;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import functionalj.function.Func1;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.ref.Ref;
import lombok.val;

/**
 * Classes implementing this interface know how to access to a double value.
 * 
 * @param <HOST> the host of the access.
 */
public interface DoubleAccess<HOST> extends NumberAccess<HOST, Double, DoubleAccess<HOST>>, ToDoubleFunction<HOST>, ConcreteAccess<HOST, Double, DoubleAccess<HOST>> {
    
    /**
     * The reference to a function to calculate factorial for integer. *
     */
    public static final Ref<Double> equalPrecision = Ref.ofValue(0.0).whenAbsentUse(0.0);
    
    public static final Ref<DoubleSupplier> equalPrecisionToUse = Ref.<DoubleSupplier>dictactedTo(() -> Math.abs(DoubleAccess.equalPrecision.get()));
    
    // == Constructor ==
    public static <H> DoubleAccess<H> of(Function<H, Double> accessToValue) {
        requireNonNull(accessToValue);
        if (accessToValue instanceof DoubleAccess) {
            return (DoubleAccess<H>) accessToValue;
        }
        if (accessToValue instanceof ToDoubleFunction) {
            @SuppressWarnings("unchecked")
            val func1 = (ToDoubleFunction<H>) accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        if (accessToValue instanceof Func1) {
            val func1 = (Func1<H, Double>) accessToValue;
            val access = (DoubleAccessBoxed<H>) func1::applyUnsafe;
            return access;
        }
        val func = (Function<H, Double>) accessToValue;
        val access = (DoubleAccessBoxed<H>) (host -> func.apply(host));
        return access;
    }
    
    public static <H> DoubleAccess<H> ofPrimitive(ToDoubleFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (DoubleAccessPrimitive<H>) accessToValue::applyAsDouble;
        return access;
    }
    
    @Override
    public default DoubleAccess<HOST> newAccess(Function<HOST, Double> accessToValue) {
        return of(accessToValue);
    }
    
    // == abstract functionalities ==
    public double applyAsDouble(HOST host);
    
    public Double applyUnsafe(HOST host) throws Exception;
    
    // -- conversion --
    public default DoubleAccessBoxed<HOST> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerAccessPrimitive<HOST> asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    @Override
    public default LongAccessPrimitive<HOST> asLong() {
        return asLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    @Override
    public default DoubleAccessPrimitive<HOST> asDouble() {
        return host -> access(this, host);
    }
    
    public default IntegerAccessPrimitive<HOST> asInteger(int overflowValue) {
        return asInteger(overflowValue, overflowValue);
    }
    
    public default LongAccessPrimitive<HOST> asLong(long overflowValue) {
        return asLong(overflowValue, overflowValue);
    }
    
    public default IntegerAccessPrimitive<HOST> asInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return host -> {
            val value = access(this, host);
            if (value < Integer.MIN_VALUE)
                return negativeOverflowValue;
            if (value > Integer.MAX_VALUE)
                return positiveOverflowValue;
            return (int) Math.round(value);
        };
    }
    
    public default LongAccessPrimitive<HOST> asLong(long negativeOverflowValue, long positiveOverflowValue) {
        return host -> {
            val value = access(this, host);
            if (value < Long.MIN_VALUE)
                return negativeOverflowValue;
            if (value > Long.MAX_VALUE)
                return positiveOverflowValue;
            return (long) Math.round(value);
        };
    }
    
    public default IntegerAccessBoxed<HOST> asIntegerOrNull(Integer negativeOverflowValue, Integer positiveOverflowValue) {
        return host -> {
            double doubleValue = access(this, host);
            if (doubleValue < Integer.MIN_VALUE)
                return negativeOverflowValue;
            if (doubleValue > Integer.MIN_VALUE)
                return positiveOverflowValue;
            return (int) doubleValue;
        };
    }
    
    public default LongAccessBoxed<HOST> asLongOrNull(Long negativeOverflowValue, Long positiveOverflowValue) {
        return host -> {
            double doubleValue = access(this, host);
            if (doubleValue < Long.MIN_VALUE)
                return negativeOverflowValue;
            if (doubleValue > Long.MIN_VALUE)
                return positiveOverflowValue;
            return (long) doubleValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> round() {
        return host -> {
            val value = access(this, host);
            return Math.round(value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> roundToInt() {
        return round().asInteger();
    }
    
    public default LongAccessPrimitive<HOST> roundToLong() {
        return round().asLong();
    }
    
    public default DoubleAccessPrimitive<HOST> ceil() {
        return host -> {
            val value = access(this, host);
            return Math.ceil(value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> ceilToInt() {
        return round().asInteger();
    }
    
    public default LongAccessPrimitive<HOST> ceilToLong() {
        return round().asLong();
    }
    
    public default DoubleAccessPrimitive<HOST> floor() {
        return host -> {
            val value = access(this, host);
            return Math.floor(value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> floorToInt() {
        return floor().asInteger();
    }
    
    public default LongAccessPrimitive<HOST> floorToLong() {
        return floor().asLong();
    }
    
    public default DoubleAccessPrimitive<HOST> roundBy(double precision) {
        return host -> {
            val value = access(this, host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            return Math.round(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> roundBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value = access(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.round(value);
            }
            return Math.round(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> roundBy(ToDoubleFunction<HOST> precisionFunction) {
        return host -> {
            val value = access(this, host);
            val precision = applyPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            return Math.round(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> ceilBy(double precision) {
        return host -> {
            val value = access(this, host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            return Math.ceil(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> ceilBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value = access(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            return Math.ceil(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> ceilBy(ToDoubleFunction<HOST> precisionFunction) {
        return host -> {
            val value = access(this, host);
            val precision = applyPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            return Math.ceil(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> floorBy(double precision) {
        return host -> {
            val value = access(this, host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            return Math.floor(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> floorBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value = access(this, host);
            val precision = getPrimitive(precisionSupplier);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            return Math.floor(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> floorBy(ToDoubleFunction<HOST> precisionFunction) {
        return host -> {
            val value = access(this, host);
            val precision = applyPrimitive(precisionFunction, host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            return Math.floor(value / precision) * precision;
        };
    }
    
    public default StringAccess<HOST> asString() {
        return host -> "" + access(this, host);
    }
    
    public default StringAccess<HOST> asString(String template) {
        return host -> {
            val value = access(this, host);
            return String.format(template, value);
        };
    }
    
    public default BigIntegerAccess<HOST> asBitInteger() {
        return host -> {
            val value = access(this, host);
            return BigDecimal.valueOf(value).toBigInteger();
        };
    }
    
    public default BigDecimalAccess<HOST> asBitDecimal() {
        return host -> {
            val value = access(this, host);
            return BigDecimal.valueOf(value);
        };
    }
    
    // TODO - Find a better way to format this that allow a fix width disregards of the magnitude of the value.
    // or just redirect the format to another function that can be substituted.
    // -- Equality --
    public default BooleanAccessPrimitive<HOST> that(DoublePredicate checker) {
        return host -> {
            val value = access(this, host);
            return checker.test(value);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(double... otherValues) {
        return host -> {
            val value = access(this, host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return true;
                }
            }
            return false;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = access(this, host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(double... otherValues) {
        return host -> {
            val value = access(this, host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return false;
                }
            }
            return true;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = access(this, host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsOne() {
        return thatIs(1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsZero() {
        return thatIs(0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsMinusOne() {
        return thatIs(-1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsFourtyTwo() {
        return thatIs(42);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotOne() {
        return thatIsNot(1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotZero() {
        return thatIsNot(0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotMinusOne() {
        return thatIsNot(-1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsPositive() {
        return host -> {
            val value = access(this, host);
            return value > 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNegative() {
        return host -> {
            val value = access(this, host);
            return value < 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotPositive() {
        return host -> {
            val value = access(this, host);
            return value <= 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotNegative() {
        return host -> {
            val value = access(this, host);
            return value >= 0;
        };
    }
    
    public default DoubleAccessEqual<HOST> thatEquals(double anotherValue) {
        return new DoubleAccessEqual<>(false, this, (host, value) -> anotherValue);
    }
    
    public default DoubleAccessEqual<HOST> thatEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqual<>(false, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    
    public default DoubleAccessEqual<HOST> thatEquals(ToDoubleFunction<HOST> anotherAccess) {
        return new DoubleAccessEqual<>(false, this, (host, value) -> anotherAccess.applyAsDouble(host));
    }
    
    public default DoubleAccessEqual<HOST> eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    
    public default DoubleAccessEqual<HOST> eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    
    public default DoubleAccessEqual<HOST> eq(ToDoubleFunction<HOST> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    public default DoubleAccessEqual<HOST> thatNotEquals(double anotherValue) {
        return new DoubleAccessEqual<>(true, this, (host, value) -> anotherValue);
    }
    
    public default DoubleAccessEqual<HOST> thatNotEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqual<>(true, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    
    public default DoubleAccessEqual<HOST> thatNotEquals(ToDoubleFunction<HOST> anotherAccess) {
        return new DoubleAccessEqual<>(true, this, (host, value) -> anotherAccess.applyAsDouble(host));
    }
    
    public default DoubleAccessEqual<HOST> neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    public default DoubleAccessEqual<HOST> neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default DoubleAccessEqual<HOST> neq(ToDoubleFunction<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    public default DoubleAccessEqual<HOST> thatEqualsOne() {
        return thatEquals(1);
    }
    
    public default DoubleAccessEqual<HOST> thatEqualsZero() {
        return thatEquals(0);
    }
    
    public default DoubleAccessEqual<HOST> thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    public default DoubleAccessEqual<HOST> thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    public default DoubleAccessEqual<HOST> thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    public default DoubleAccessEqual<HOST> thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    public default DoubleAccessEqual<HOST> thatNotEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    // -- Compare --
    public default Comparator<HOST> inOrder() {
        return (a, b) -> {
            val aValue = this.apply(a);
            val bValue = this.apply(b);
            return compareOrNull(aValue, bValue);
        };
    }
    
    public default Comparator<HOST> inReverseOrder() {
        return (a, b) -> {
            val aValue = this.apply(a);
            val bValue = this.apply(b);
            return compareOrNull(bValue, aValue);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(double anotherValue) {
        return host -> {
            val value = access(this, host);
            val compare = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(ToDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherFunction, host);
            val compare = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(ToDoubleFunction<HOST> anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> gt(ToDoubleFunction<HOST> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> lt(ToDoubleFunction<HOST> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(ToDoubleFunction<HOST> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(ToDoubleFunction<HOST> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    // -- Min+Max --
    public default DoubleAccessPrimitive<HOST> min(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return Math.min(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> min(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> min(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return Math.min(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> max(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return Math.max(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> max(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> max(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return Math.max(value, anotherValue);
        };
    }
    
    // -- Math --
    public default MathOperators<Double> __mathOperators() {
        return DoubleMathOperators.instance;
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsRound() {
        return host -> {
            val value = access(this, host);
            return 1.0 * Math.round(value) == value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> abs() {
        return host -> {
            val value = access(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> negate() {
        return host -> {
            val value = access(this, host);
            return -value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> signum() {
        return host -> {
            val value = access(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> plus(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value + anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> plus(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> plus(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value + anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> minus(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value - anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> minus(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> minus(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value - anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> time(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return value * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> time(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> time(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> remainderBy(double anotherValue) {
        return host -> {
            val value = access(this, host);
            val division = Math.round(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            val division = Math.round(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> remainderBy(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            val division = Math.round(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> inverse() {
        return host -> {
            val value = access(this, host);
            return 1 / (value * 1.0);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> square() {
        return host -> {
            val value = access(this, host);
            return value * value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> squareRoot() {
        return host -> {
            val value = access(this, host);
            return Math.sqrt(value);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(double anotherValue) {
        return host -> {
            val value = access(this, host);
            return Math.pow(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(DoubleSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.pow(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> exp() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.exp(doubleValue);
        };
    }
    
    /**
     * Returns &lt;i&gt;e&lt;/i&gt;&lt;sup&gt;x&lt;/sup&gt;&nbsp;-1.  Note that for values of
     * &lt;i&gt;x&lt;/i&gt; near 0, the exact sum of
     * {@code expm1(x)}&nbsp;+&nbsp;1 is much closer to the true
     * result of &lt;i&gt;e&lt;/i&gt;&lt;sup&gt;x&lt;/sup&gt; than {@code exp(x)}.
     * 
     * @return the value <i>e</i><sup>{@code x}</sup>&nbsp;-&nbsp;1.
     */
    public default DoubleAccessPrimitive<HOST> expm1() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.expm1(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> log() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.log(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> log10() {
        return host -> {
            double doubleValue = access(this, host);
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
     * 
     * @return the value ln({@code x}&nbsp;+&nbsp;1), the natural
     */
    public default DoubleAccessPrimitive<HOST> log1p() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.log1p(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> acos() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.acos(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> asin() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.asin(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> atan() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.atan(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> cos() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.cos(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> cosh() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.cosh(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> sin() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.sin(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> sinh() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.sinh(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> tan() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.tan(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> tanh() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.tanh(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> toDegrees() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.toDegrees(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> toRadians() {
        return host -> {
            double doubleValue = access(this, host);
            return Math.toRadians(doubleValue);
        };
    }
    
}
