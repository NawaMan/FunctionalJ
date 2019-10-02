// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.ToLongFunction;

import functionalj.function.Func1;
import functionalj.lens.lenses.java.time.InstantAccess;
import functionalj.lens.lenses.java.time.LocalDateTimeAccess;
import lombok.val;

@SuppressWarnings("javadoc")
public interface LongAccess<HOST> 
        extends 
            NumberAccess<HOST, Long, LongAccess<HOST>>, 
            ToLongFunction<HOST>,
            ConcreteAccess<HOST, Long, LongAccess<HOST>> {
    
    
    public static <H> LongAccess<H> of(Function<H, Long> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof LongAccess) {
            return (LongAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToLongFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToLongFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Long>)accessToValue;
            val access = (LongAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }
        
        val func   = (Function<H, Long>)accessToValue;
        val access = (LongAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> LongAccess<H> ofPrimitive(ToLongFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (LongAccessPrimitive<H>)accessToValue::applyAsLong;
        return access;
    }
    
    
    public long applyAsLong(HOST host);
    
    public Long applyUnsafe(HOST host) throws Exception;
    
    
    @Override
    public default LongAccess<HOST> newAccess(Function<HOST, Long> accessToValue) {
        return of(accessToValue);
    }
    
    public default InstantAccess<HOST> toInstant() {
        return host -> {
            long timestampMilliSecond = apply(host);
            return Instant.ofEpochMilli(timestampMilliSecond);
        };
    }
    
    public default LocalDateTimeAccess<HOST> toLocalDateTime() {
        return toLocalDateTime(ZoneId.systemDefault());
    }
    
    public default LocalDateTimeAccess<HOST> toLocalDateTime(ZoneId zone) {
        return host -> {
            val timestampMilliSecond = apply(host);
            val instant = Instant.ofEpochMilli(timestampMilliSecond);
            return LocalDateTime.ofInstant(instant, zone);
        };
    }
    
    public default MathOperators<Long> __mathOperators() {
        return LongMathOperators.instance;
    }
    
    //-- Compare --
    
    // TODO - Supplier and BiFunction
    // TODO - Move back to NumberAccess
    // TODO - Add Byte/Character
    
    public default BooleanAccessPrimitive<HOST> thatIsZero() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotZero() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsPositive() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue > 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNegative() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue < 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotPositive() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue <= 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotNegative() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue >= 0;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> toInteger() {
        return host -> {
            long longValue = applyAsLong(host);
            return (int)longValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> toLong() {
        return host -> {
            long longValue = applyAsLong(host);
            return (long)longValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> toDouble() {
        return host -> {
            long longValue = applyAsLong(host);
            return (double)longValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> toZero() {
        return host -> 0;
    }
    
    public default LongAccessPrimitive<HOST> toOne() {
        return host -> 1;
    }
    
    public default LongAccessPrimitive<HOST> toMinusOne() {
        return host -> -1;
    }
    
    public default LongAccessPrimitive<HOST> abs() {
        return host -> {
            long longValue = applyAsLong(host);
            return (longValue < 0) ? -longValue : longValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> negate() {
        return host -> {
            long longValue = applyAsLong(host);
            return -longValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> signum() {
        return host -> {
            long longValue = applyAsLong(host);
            return (longValue == 0) ? 0 : (longValue < 0) ? -1 : 1;
        };
    }
    
    
    public default LongAccessPrimitive<HOST> bitOr(long value) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue | value;
        };
    }
    public default LongAccessPrimitive<HOST> bitOr(LongSupplier anotherSupplier) {
        return host -> {
            long longValue = applyAsLong(host);
            long value     = anotherSupplier.getAsLong();
            return longValue | value;
        };
    }
    public default LongAccessPrimitive<HOST> bitOr(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue = applyAsLong(host);
            long value     = anotherAccess.applyAsLong(host);
            return longValue | value;
        };
    }
    public default LongAccessPrimitive<HOST> bitOr(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue = applyAsLong(host);
            long value     = anotherFunction.applyAsLong(host, longValue);
            return longValue | value;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> bitAt(long bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return host -> {
            long longValue = applyAsLong(host);
            return (longValue & p) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(LongSupplier anotherSupplier) {
        return host -> {
            long longValue = applyAsLong(host);
            long value     = anotherSupplier.getAsLong();
            val p          = (int)Math.pow(2, value);
            return (longValue & p) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue = applyAsLong(host);
            long value     = anotherAccess.applyAsLong(host);
            val p          = (int)Math.pow(2, value);
            return (longValue & p) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue = applyAsLong(host);
            long value     = anotherFunction.applyAsLong(host, longValue);
            val p          = (int)Math.pow(2, value);
            return (longValue & p) != 0;
        };
    }
    
    
    public default LongAccessPrimitive<HOST> compareTo(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            long compare   = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    public default LongAccessPrimitive<HOST> compareTo(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            long compare      = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    public default LongAccessPrimitive<HOST> compareTo(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherAccess.applyAsLong(host);
            long compare      = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    public default LongAccessPrimitive<HOST> compareTo(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherFunction.applyAsLong(host, longValue);
            long compare      = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    
    public default LongAccessPrimitive<HOST> cmp(long anotherValue) {
        return compareTo(anotherValue);
    }
    public default LongAccessPrimitive<HOST> cmp(LongSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default LongAccessPrimitive<HOST> cmp(LongAccess<HOST> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default LongAccessPrimitive<HOST> cmp(ToLongBiLongFunction<HOST> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEquals(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(LongSupplier anotherSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherAccess.applyAsLong(host);
            return longValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherFunction.applyAsLong(host, longValue);
            return longValue == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> eq(long anotherValue) {
        return thatEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> eq(LongSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> eq(LongAccess<HOST> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> eq(ToLongBiLongFunction<HOST> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(LongSupplier anotherSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherAccess.applyAsLong(host);
            return longValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherFunction.applyAsLong(host, longValue);
            return longValue != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> neq(long anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> neq(LongSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> neq(LongAccess<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> neq(ToLongBiLongFunction<HOST> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(LongSupplier anotherSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherAccess.applyAsLong(host);
            return longValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherFunction.applyAsLong(host, longValue);
            return longValue > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gt(long anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gt(LongSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gt(LongAccess<HOST> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gt(ToLongBiLongFunction<HOST> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(LongSupplier anotherSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherAccess.applyAsLong(host);
            return longValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherFunction.applyAsLong(host, longValue);
            return longValue < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lt(long anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lt(LongSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lt(LongAccess<HOST> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lt(ToLongBiLongFunction<HOST> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherAccess.applyAsLong(host);
            return longValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherFunction.applyAsLong(host, longValue);
            return longValue >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(long anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gteq(LongSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gteq(LongAccess<HOST> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gteq(ToLongBiLongFunction<HOST> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(LongAccess<HOST> anotherAccess) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherAccess.applyAsLong(host);
            return longValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToLongBiLongFunction<HOST> anotherFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = anotherFunction.applyAsLong(host, longValue);
            return longValue <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(long anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lteq(LongSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lteq(LongAccess<HOST> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lteq(ToLongBiLongFunction<HOST> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    public default LongAccessPrimitive<HOST> plus(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return longValue + anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> plus(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue + anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> plus(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return longValue + anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> plus(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return longValue + anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> minus(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return longValue - anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> minus(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue - anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> minus(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return longValue - anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> minus(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return longValue - anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> times(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return longValue * anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> times(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue * anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> times(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return longValue * anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> times(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return longValue * anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> dividedBy(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return longValue / anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> dividedBy(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue / anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> dividedBy(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return longValue / anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> dividedBy(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return longValue / anotherValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> remainderBy(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return longValue % anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> remainderBy(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue % anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> remainderBy(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return longValue % anotherValue;
        };
    }
    public default LongAccessPrimitive<HOST> remainderBy(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return longValue % anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return Math.pow(longValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return Math.pow(longValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return Math.pow(longValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return Math.pow(longValue, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> min(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return Math.min(longValue, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> min(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return Math.min(longValue, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> min(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return Math.min(longValue, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> min(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return Math.min(longValue, anotherValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> max(long value) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = value;
            return Math.max(longValue, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> max(LongSupplier valueSupplier) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return Math.max(longValue, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> max(LongAccess<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host);
            return Math.max(longValue, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> max(ToLongBiLongFunction<HOST> valueFunction) {
        return host -> {
            long longValue     = applyAsLong(host);
            long anotherValue = valueFunction.applyAsLong(host, longValue);
            return Math.max(longValue, anotherValue);
        };
    }
}
