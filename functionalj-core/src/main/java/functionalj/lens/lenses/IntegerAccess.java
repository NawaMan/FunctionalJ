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

import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;

import functionalj.function.Func1;
import lombok.val;

@SuppressWarnings("javadoc")
public interface IntegerAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Integer, IntegerAccess<HOST>>,
                        ToIntFunction<HOST>,
                        ConcreteAccess<HOST, Integer, IntegerAccess<HOST>> {
    
    public static <H> IntegerAccess<H> of(Function<H, Integer> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof IntegerAccess) {
            return (IntegerAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToIntFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToIntFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Integer>)accessToValue;
            val access = (IntegerAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }
        
        val func   = (Function<H, Integer>)accessToValue;
        val access = (IntegerAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> IntegerAccess<H> ofPrimitive(ToIntFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (IntegerAccessPrimitive<H>)accessToValue::applyAsInt;
        return access;
    }
    
    
    public int applyAsInt(HOST host);
    
    public Integer applyUnsafe(HOST host) throws Exception;
    
    
    @Override
    public default IntegerAccess<HOST> newAccess(Function<HOST, Integer> accessToValue) {
        return of(accessToValue);
    }
    
    
    public default MathOperators<Integer> __mathOperators() {
        return IntMathOperators.instance;
    }
    
    //-- Compare --
    
    // TODO - Supplier and BiFunction
    // TODO - Move back to NumberAccess
    // TODO - Add Byte/Character
    
    public default BooleanAccessPrimitive<HOST> thatIsZero() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotZero() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsPositive() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue > 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNegative() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue < 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotPositive() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue <= 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotNegative() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue >= 0;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> toInteger() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> toLong() {
        return host -> {
            int intValue = applyAsInt(host);
            return (long)intValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> toDouble() {
        return host -> {
            int intValue = applyAsInt(host);
            return (double)intValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> toZero() {
        return host -> 0;
    }
    
    public default IntegerAccessPrimitive<HOST> toOne() {
        return host -> 1;
    }
    
    public default IntegerAccessPrimitive<HOST> toMinusOne() {
        return host -> -1;
    }
    
    public default IntegerAccessPrimitive<HOST> abs() {
        return host -> {
            int intValue = applyAsInt(host);
            return (intValue < 0) ? -intValue : intValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> negate() {
        return host -> {
            int intValue = applyAsInt(host);
            return -intValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> signum() {
        return host -> {
            int intValue = applyAsInt(host);
            return (intValue == 0) ? 0 : (intValue < 0) ? -1 : 1;
        };
    }
    
    
    public default IntegerAccessPrimitive<HOST> bitAnd(int value) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue & value;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitAnd(IntSupplier anotherSupplier) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherSupplier.getAsInt();
            return intValue & value;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitAnd(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherAccess.applyAsInt(host);
            return intValue & value;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitAnd(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherFunction.applyAsInt(host, intValue);
            return intValue & value;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> bitOr(int value) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue | value;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitOr(IntSupplier anotherSupplier) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherSupplier.getAsInt();
            return intValue | value;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitOr(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherAccess.applyAsInt(host);
            return intValue | value;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitOr(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherFunction.applyAsInt(host, intValue);
            return intValue | value;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> bitAt(int bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return host -> {
            int intValue = applyAsInt(host);
            return (intValue & p) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(IntSupplier anotherSupplier) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherSupplier.getAsInt();
            val p        = (int)Math.pow(2, value);
            return (intValue & p) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherAccess.applyAsInt(host);
            val p        = (int)Math.pow(2, value);
            return (intValue & p) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherFunction.applyAsInt(host, intValue);
            val p        = (int)Math.pow(2, value);
            return (intValue & p) != 0;
        };
    }
    
    
    public default IntegerAccessPrimitive<HOST> compareTo(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            int compare  = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            int compare      = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.applyAsInt(host);
            int compare      = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherFunction.applyAsInt(host, intValue);
            int compare      = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(int anotherValue) {
        return compareTo(anotherValue);
    }
    public default IntegerAccessPrimitive<HOST> cmp(IntSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerAccessPrimitive<HOST> cmp(IntegerAccess<HOST> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default IntegerAccessPrimitive<HOST> cmp(ToIntBiIntFunction<HOST> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEquals(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.applyAsInt(host);
            return intValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherFunction.applyAsInt(host, intValue);
            return intValue == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> eq(int anotherValue) {
        return thatEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> eq(IntSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> eq(IntegerAccess<HOST> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> eq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.applyAsInt(host);
            return intValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherFunction.applyAsInt(host, intValue);
            return intValue != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> neq(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> neq(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> neq(IntegerAccess<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> neq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.applyAsInt(host);
            return intValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherFunction.applyAsInt(host, intValue);
            return intValue > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gt(int anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gt(IntSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gt(IntegerAccess<HOST> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gt(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.applyAsInt(host);
            return intValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherFunction.applyAsInt(host, intValue);
            return intValue < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lt(int anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lt(IntSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lt(IntegerAccess<HOST> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lt(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.applyAsInt(host);
            return intValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherFunction.applyAsInt(host, intValue);
            return intValue >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(int anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gteq(IntSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gteq(IntegerAccess<HOST> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gteq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(IntegerAccess<HOST> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.applyAsInt(host);
            return intValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherFunction.applyAsInt(host, intValue);
            return intValue <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(int anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lteq(IntSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lteq(IntegerAccess<HOST> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lteq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    public default IntegerAccessPrimitive<HOST> plus(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return intValue + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return intValue + anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> minus(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return intValue - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return intValue - anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> times(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> times(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> times(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return intValue * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> times(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return intValue * anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> dividedBy(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue / anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> dividedBy(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue / anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> dividedBy(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return intValue / anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> dividedBy(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return intValue / anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> remainderBy(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return intValue % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return intValue % anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return Math.pow(intValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return Math.pow(intValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return Math.pow(intValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return Math.pow(intValue, anotherValue);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> min(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return Math.min(intValue, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return Math.min(intValue, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return Math.min(intValue, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return Math.min(intValue, anotherValue);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> max(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return Math.max(intValue, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return Math.max(intValue, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(IntegerAccess<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host);
            return Math.max(intValue, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueFunction.applyAsInt(host, intValue);
            return Math.max(intValue, anotherValue);
        };
    }
    
////  public BigDecimal[] divideAndRemainder(BigDecimal divisor); --  Tuple
    
}
