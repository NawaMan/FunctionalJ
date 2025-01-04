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
import static functionalj.function.Apply.applyPrimitive;
import static functionalj.function.Apply.getPrimitive;
import static functionalj.function.Compare.compareOrNull;
import static java.util.Objects.requireNonNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;
import functionalj.function.Func1;
import functionalj.functions.LongFuncs;
import functionalj.lens.lenses.java.time.InstantAccess;
import functionalj.lens.lenses.java.time.LocalDateTimeAccess;
import functionalj.ref.Ref;
import lombok.val;

public interface LongAccess<HOST> extends NumberAccess<HOST, Long, LongAccess<HOST>>, ToLongFunction<HOST>, ConcreteAccess<HOST, Long, LongAccess<HOST>> {
    
    /**
     * The reference to a function to calculate factorial for integer. *
     */
    public static final Ref<LongUnaryOperator> factorialRef = Ref.ofValue(value -> LongFuncs.factorial(value));
    
    public static <H> LongAccess<H> of(Function<H, Long> accessToValue) {
        requireNonNull(accessToValue);
        if (accessToValue instanceof LongAccess) {
            return (LongAccess<H>) accessToValue;
        }
        if (accessToValue instanceof ToLongFunction) {
            @SuppressWarnings("unchecked")
            val func1 = (ToLongFunction<H>) accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        if (accessToValue instanceof Func1) {
            val func1 = (Func1<H, Long>) accessToValue;
            val access = (LongAccessBoxed<H>) func1::applyUnsafe;
            return access;
        }
        val func = (Function<H, Long>) accessToValue;
        val access = (LongAccessBoxed<H>) (host -> func.apply(host));
        return access;
    }
    
    public static <H> LongAccess<H> ofPrimitive(ToLongFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (LongAccessPrimitive<H>) accessToValue::applyAsLong;
        return access;
    }
    
    @Override
    public default LongAccess<HOST> newAccess(Function<HOST, Long> accessToValue) {
        return of(accessToValue);
    }
    
    // == abstract functionalities ==
    public long applyAsLong(HOST host);
    
    public Long applyUnsafe(HOST host) throws Exception;
    
    // -- conversion --
    public default LongAccessBoxed<HOST> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerAccess<HOST> asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public default IntegerAccess<HOST> asInteger(int overflowValue) {
        return asInteger(overflowValue);
    }
    
    public default IntegerAccess<HOST> asInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return IntegerAccess.of(host -> {
            val value = access(this, host);
            if (value < Integer.MIN_VALUE)
                return negativeOverflowValue;
            if (value > Integer.MAX_VALUE)
                return positiveOverflowValue;
            return (int) Math.round(value);
        });
    }
    
    @Override
    public default LongAccessPrimitive<HOST> asLong() {
        return host -> {
            long longValue = applyAsLong(host);
            return (long) longValue;
        };
    }
    
    @Override
    public default DoubleAccessPrimitive<HOST> asDouble() {
        return host -> {
            long longValue = applyAsLong(host);
            return (double) longValue;
        };
    }
    
    @Override
    public default StringAccess<HOST> asString() {
        return host -> "" + access(this, host);
    }
    
    @Override
    public default StringAccess<HOST> asString(String template) {
        return host -> {
            val value = access(this, host);
            return String.format(template, value);
        };
    }
    
    public default BigIntegerAccess<HOST> asBitInteger() {
        return host -> {
            val value = access(this, host);
            return BigInteger.valueOf(value);
        };
    }
    
    public default BigDecimalAccess<HOST> asBitDecimal() {
        return host -> {
            val value = access(this, host);
            return BigDecimal.valueOf(value);
        };
    }
    
    public default InstantAccess<HOST> asInstant() {
        return host -> {
            long timestampMilliSecond = apply(host);
            return Instant.ofEpochMilli(timestampMilliSecond);
        };
    }
    
    public default LocalDateTimeAccess<HOST> asLocalDateTime() {
        return asLocalDateTime(ZoneId.systemDefault());
    }
    
    public default LocalDateTimeAccess<HOST> asLocalDateTime(ZoneId zone) {
        return host -> {
            val timestampMilliSecond = apply(host);
            val instant = Instant.ofEpochMilli(timestampMilliSecond);
            return LocalDateTime.ofInstant(instant, zone);
        };
    }
    
    // -- Equality --
    public default BooleanAccessPrimitive<HOST> that(LongPredicate checker) {
        return host -> {
            val value = access(this, host);
            return checker.test(value);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(LongSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(ToLongFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(LongSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(ToLongFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(long... otherValues) {
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
    
    // public default BooleanAccessPrimitive<HOST> thatIsAnyOf(LongFuncList otherValues) {
    // return host -> {
    // val value = access(this, host);
    // return otherValues.anyMatch(anotherValue -> value == anotherValue);
    // };
    // }
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(long... otherValues) {
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
    
    // public default BooleanAccessPrimitive<HOST> thatIsNoneOf(LongFuncList otherValues) {
    // return host -> {
    // val value = access(this, host);
    // return otherValues.noneMatch(anotherValue -> value == anotherValue);
    // };
    // }
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
    
    public default BooleanAccessPrimitive<HOST> thatEquals(long anotherValue) {
        return thatIs(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEquals(LongSupplier anotherSupplier) {
        return thatIs(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEquals(ToLongFunction<HOST> anotherAccess) {
        return thatIs(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> eq(long anotherValue) {
        return thatEquals(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> eq(LongSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> eq(ToLongFunction<HOST> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(long anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(LongSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToLongFunction<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> neq(long anotherValue) {
        return thatNotEquals(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> neq(LongSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> neq(ToLongFunction<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEqualsOne() {
        return thatEquals(1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEqualsZero() {
        return thatEquals(0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEqualsMinusOne() {
        return thatEquals(-1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEqualsFourtyTwo() {
        return thatEquals(42);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEqualsOne() {
        return thatEquals(1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEqualsZero() {
        return thatEquals(0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEqualsMinusOne() {
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
    
    public default IntegerAccessPrimitive<HOST> compareTo(long anotherValue) {
        return host -> {
            val value = access(this, host);
            val compare = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(LongSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(ToLongFunction<HOST> anotherFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherFunction, host);
            val compare = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(long anotherValue) {
        return compareTo(anotherValue);
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(LongSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(ToLongFunction<HOST> anotherAccess) {
        return compareTo(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(LongSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToLongFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gt(long anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> gt(LongSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> gt(ToLongFunction<HOST> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(LongSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToLongFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lt(long anotherValue) {
        return thatLessThan(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> lt(LongSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> lt(ToLongFunction<HOST> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToLongFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(long anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(LongSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(ToLongFunction<HOST> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToLongFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(long anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(LongSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(ToLongFunction<HOST> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    
    // -- Min+Max --
    public default LongAccessPrimitive<HOST> min(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return Math.min(value, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> min(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> min(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return Math.min(value, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> max(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return Math.max(value, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> max(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> max(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return Math.max(value, anotherValue);
        };
    }
    
    // -- Math --
    public default MathOperators<Long> __mathOperators() {
        return LongMathOperators.instance;
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsOdd() {
        return host -> {
            val value = access(this, host);
            return value % 2 != 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsEven() {
        return host -> {
            val value = access(this, host);
            return value % 2 == 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value % anotherValue == 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(LongSupplier anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = anotherAccess.getAsLong();
            return value % anotherValue == 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(ToLongFunction<HOST> anotherAccess) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    
    public default LongAccessPrimitive<HOST> abs() {
        return host -> {
            val value = access(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default LongAccessPrimitive<HOST> negate() {
        return host -> {
            val value = access(this, host);
            return -value;
        };
    }
    
    public default LongAccessPrimitive<HOST> signum() {
        return host -> {
            val value = access(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default LongAccessPrimitive<HOST> plus(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value + anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> plus(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> plus(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value + anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> minus(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value - anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> minus(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> minus(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value - anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> time(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value * anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> time(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> time(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> remainderBy(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return value % anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> remainderBy(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value % anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> remainderBy(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value % anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> inverse() {
        return host -> {
            val value = access(this, host);
            return 1 / (value * 1.0);
        };
    }
    
    public default LongAccessPrimitive<HOST> square() {
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
    
    public default LongAccessPrimitive<HOST> factorial() {
        return host -> {
            val value = access(this, host);
            if (value <= 0) {
                return 1;
            }
            return factorialRef.get().applyAsLong(value);
        };
    }
    
    // TODO - Make this Long once we are ready.
    public default LongAccessPrimitive<HOST> pow(long anotherValue) {
        return host -> {
            val value = access(this, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> pow(LongSupplier valueSupplier) {
        return host -> {
            val value = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return (long) Math.pow(value, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> pow(ToLongFunction<HOST> valueFunction) {
        return host -> {
            val value = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return (long) Math.pow(value, anotherValue);
        };
    }
}
