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

import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;

import functionalj.function.LongBiFunctionPrimitive;
import functionalj.function.ToLongBiLongFunction;
import lombok.val;

@FunctionalInterface
public interface LongToLongAccessPrimitive extends LongUnaryOperator, LongAccessPrimitive<Long> {
    
    public long applyLongToLong(long host);
    
    public default long applyAsLong(long host) {
        return applyLongToLong(host);
    }
    
    public default long applyAsLong(Long host) {
        return applyLongToLong(host);
    }
    
    public static long apply(LongAccess<Long> access, long value) {
        val resValue 
            = (access instanceof LongToLongAccessPrimitive)
            ? ((LongToLongAccessPrimitive)access).applyLongToLong(value)
            : access.applyAsLong(value);
        return resValue;
    }
    
    public default LongToLongAccessBoxed boxed() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue;
        };
    }
    
    
    //-- Compare --
    
    public default LongToIntegerAccessPrimitive toInteger() {
        return host -> {
            long longValue = applyAsLong(host);
            return (int)longValue;
        };
    }
    
    public default LongToLongAccessPrimitive toLong() {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive toDouble() {
        return host -> {
            long longValue = applyAsLong(host);
            return (double)longValue;
        };
    }
    
    public default LongToLongAccessPrimitive toZero() {
        return host -> 0;
    }
    
    public default LongToLongAccessPrimitive toOne() {
        return host -> 1;
    }
    
    public default LongToLongAccessPrimitive toMinusOne() {
        return host -> -1;
    }
    
    public default LongToLongAccessPrimitive abs() {
        return host -> {
            long longValue = applyAsLong(host);
            return (longValue < 0) ? -longValue : longValue;
        };
    }
    
    public default LongToLongAccessPrimitive negate() {
        return host -> {
            long longValue = applyAsLong(host);
            return -longValue;
        };
    }
    
    public default LongToLongAccessPrimitive signum() {
        return host -> {
            long longValue = applyAsLong(host);
            return (longValue == 0) ? 0 : (longValue < 0) ? -1 : 1;
        };
    }
    
    
    public default LongToLongAccessPrimitive bitAnd(long value) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue & value;
        };
    }
    public default LongToLongAccessPrimitive bitAnd(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue & anotherValue;
        };
    }
    public default LongToLongAccessPrimitive bitAnd(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue & anotherValue;
        };
    }
    public default LongToLongAccessPrimitive bitAnd(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue & anotherValue;
        };
    }
    
    public default LongToLongAccessPrimitive bitOr(long value) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue | value;
        };
    }
    public default LongToLongAccessPrimitive bitOr(LongSupplier anotherSupplier) {
        return host -> {
            long longValue = applyAsLong(host);
            long value    = anotherSupplier.getAsLong();
            return longValue | value;
        };
    }
    public default LongToLongAccessPrimitive bitOr(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue | anotherValue;
        };
    }
    public default LongToLongAccessPrimitive bitOr(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue | anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive bitAt(long bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return host -> {
            long longValue = applyAsLong(host);
            return (longValue & p) != 0;
        };
    }
    public default LongToBooleanAccessPrimitive bitAt(LongSupplier anotherSupplier) {
        return host -> {
            long longValue = applyAsLong(host);
            long value    = anotherSupplier.getAsLong();
            val p        = (int)Math.pow(2, value);
            return (longValue & p) != 0;
        };
    }
    public default LongToBooleanAccessPrimitive bitAt(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            val p            = (int)Math.pow(2, anotherValue);
            return (longValue & p) != 0;
        };
    }
    public default LongToBooleanAccessPrimitive bitAt(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            val p        = (int)Math.pow(2, anotherValue);
            return (longValue & p) != 0;
        };
    }
    
    
    public default LongToIntegerAccessPrimitive compareTo(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            int  compare  = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    public default LongToIntegerAccessPrimitive compareTo(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            int  compare      = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    public default LongToIntegerAccessPrimitive compareTo(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            int  compare      = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    public default LongToIntegerAccessPrimitive compareTo(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            int  compare      = Long.compare(longValue, anotherValue);
            return compare;
        };
    }
    
    public default LongToIntegerAccessPrimitive cmp(long anotherValue) {
        return compareTo(anotherValue);
    }
    public default LongToIntegerAccessPrimitive cmp(LongSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default LongToIntegerAccessPrimitive cmp(LongAccess<Long> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default LongToIntegerAccessPrimitive cmp(ToLongBiLongFunction<Long> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default LongToBooleanAccessPrimitive thatIsOdd() {
        return host -> {
            long theValue = applyAsLong(host);
            return theValue % 2 == 1;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIsEven() {
        return host -> {
            long theValue = applyAsLong(host);
            return theValue % 2 == 0;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatEquals(long anotherValue) {
        return host -> {
            long theValue = applyAsLong(host);
            return theValue == anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatEquals(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue == anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatEquals(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue == anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatEquals(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue == anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive eq(long anotherValue) {
        return thatEquals(anotherValue);
    }
    public default LongToBooleanAccessPrimitive eq(LongSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default LongToBooleanAccessPrimitive eq(LongAccess<Long> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default LongToBooleanAccessPrimitive eq(ToLongBiLongFunction<Long> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default LongToBooleanAccessPrimitive thatNotEquals(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue != anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatNotEquals(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue != anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatNotEquals(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue != anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatNotEquals(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue != anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive neq(long anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default LongToBooleanAccessPrimitive neq(LongSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default LongToBooleanAccessPrimitive neq(LongAccess<Long> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default LongToBooleanAccessPrimitive neq(ToLongBiLongFunction<Long> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThan(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue > anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatGreaterThan(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue > anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatGreaterThan(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue > anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatGreaterThan(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue > anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive gt(long anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default LongToBooleanAccessPrimitive gt(LongSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default LongToBooleanAccessPrimitive gt(LongAccess<Long> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default LongToBooleanAccessPrimitive gt(ToLongBiLongFunction<Long> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default LongToBooleanAccessPrimitive thatLessThan(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue < anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatLessThan(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue < anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatLessThan(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue < anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatLessThan(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue < anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive lt(long anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default LongToBooleanAccessPrimitive lt(LongSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default LongToBooleanAccessPrimitive lt(LongAccess<Long> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default LongToBooleanAccessPrimitive lt(ToLongBiLongFunction<Long> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue >= anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue >= anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue >= anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue >= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive gteq(long anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default LongToBooleanAccessPrimitive gteq(LongSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default LongToBooleanAccessPrimitive gteq(LongAccess<Long> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default LongToBooleanAccessPrimitive gteq(ToLongBiLongFunction<Long> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(long anotherValue) {
        return host -> {
            long longValue = applyAsLong(host);
            return longValue <= anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongSupplier anotherSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherSupplier.getAsLong();
            return longValue <= anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue <= anotherValue;
        };
    }
    public default LongToBooleanAccessPrimitive thatLessThanOrEqualsTo(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue <= anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive lteq(long anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default LongToBooleanAccessPrimitive lteq(LongSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default LongToBooleanAccessPrimitive lteq(LongAccess<Long> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default LongToBooleanAccessPrimitive lteq(ToLongBiLongFunction<Long> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    public default LongToLongAccessPrimitive plus(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return longValue + anotherValue;
        };
    }
    public default LongToLongAccessPrimitive plus(LongSupplier valueSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue + anotherValue;
        };
    }
    public default LongToLongAccessPrimitive plus(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue + anotherValue;
        };
    }
    public default LongToLongAccessPrimitive plus(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue + anotherValue;
        };
    }
    
    public default LongToLongAccessPrimitive minus(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return longValue - anotherValue;
        };
    }
    public default LongToLongAccessPrimitive minus(LongSupplier valueSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue - anotherValue;
        };
    }
    public default LongToLongAccessPrimitive minus(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue - anotherValue;
        };
    }
    public default LongToLongAccessPrimitive minus(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue - anotherValue;
        };
    }
    
    public default LongToLongAccessPrimitive time(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return longValue * anotherValue;
        };
    }
    public default LongToLongAccessPrimitive time(LongSupplier valueSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue * anotherValue;
        };
    }
    public default LongToLongAccessPrimitive time(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue * anotherValue;
        };
    }
    public default LongToLongAccessPrimitive time(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue * anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive dividedBy(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return 1.0 * longValue / anotherValue;
        };
    }
    public default LongToDoubleAccessPrimitive dividedBy(LongSupplier anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = anotherAccess.getAsLong();
            return 1.0*longValue / anotherValue;
        };
    }
    public default LongToDoubleAccessPrimitive dividedBy(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return 1.0*longValue / anotherValue;
        };
    }
    public default LongToDoubleAccessPrimitive dividedBy(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return 1.0*longValue / anotherValue;
        };
    }
    
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(long value) {
        return host -> {
            long intValue     = applyAsLong(host);
            long anotherValue = value;
            return intValue % anotherValue == 0;
        };
    }
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(LongSupplier anotherAccess) {
        return host -> {
            long intValue     = applyAsLong(host);
            long anotherValue = anotherAccess.getAsLong();
            return intValue % anotherValue == 0;
        };
    }
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(LongAccess<Long> anotherAccess) {
        return host -> {
            long intValue     = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return intValue % anotherValue == 0;
        };
    }
    public default LongToBooleanAccessPrimitive thatIsDivisibleBy(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long intValue     = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue % anotherValue == 0;
        };
    }
    
    public default LongToLongAccessPrimitive remainderBy(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return longValue % anotherValue;
        };
    }
    public default LongToLongAccessPrimitive remainderBy(LongSupplier valueSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return longValue % anotherValue;
        };
    }
    public default LongToLongAccessPrimitive remainderBy(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return longValue % anotherValue;
        };
    }
    public default LongToLongAccessPrimitive remainderBy(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return longValue % anotherValue;
        };
    }
    
    public default LongToDoubleAccessPrimitive pow(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return Math.pow(longValue, anotherValue);
        };
    }
    public default LongToDoubleAccessPrimitive pow(LongSupplier valueSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return Math.pow(longValue, anotherValue);
        };
    }
    public default LongToDoubleAccessPrimitive pow(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return Math.pow(longValue, anotherValue);
        };
    }
    public default LongToDoubleAccessPrimitive pow(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return Math.pow(longValue, anotherValue);
        };
    }
    
    public default LongToLongAccessPrimitive min(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return Math.min(longValue, anotherValue);
        };
    }
    public default LongToLongAccessPrimitive min(LongSupplier valueSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return Math.min(longValue, anotherValue);
        };
    }
    public default LongToLongAccessPrimitive min(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return Math.min(longValue, anotherValue);
        };
    }
    public default LongToLongAccessPrimitive min(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return Math.min(longValue, anotherValue);
        };
    }
    
    public default LongToLongAccessPrimitive max(long value) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = value;
            return Math.max(longValue, anotherValue);
        };
    }
    public default LongToLongAccessPrimitive max(LongSupplier valueSupplier) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = valueSupplier.getAsLong();
            return Math.max(longValue, anotherValue);
        };
    }
    public default LongToLongAccessPrimitive max(LongAccess<Long> anotherAccess) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongToLongAccessPrimitive.apply(anotherAccess, host);
            return Math.max(longValue, anotherValue);
        };
    }
    public default LongToLongAccessPrimitive max(ToLongBiLongFunction<Long> anotherFunction) {
        return host -> {
            long longValue    = applyAsLong(host);
            long anotherValue = LongBiFunctionPrimitive.apply(anotherFunction, host, longValue);
            return Math.max(longValue, anotherValue);
        };
    }
    
}
