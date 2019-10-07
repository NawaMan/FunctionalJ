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

import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import lombok.val;

@FunctionalInterface
public interface IntegerToIntegerAccessPrimitive extends IntUnaryOperator, IntegerAccessPrimitive<Integer> {
    
    public int applyIntToInt(int host);
    
    public default int applyAsInt(int operand) {
        return applyIntToInt(operand);
    }
    
    public default int applyAsInt(Integer host) {
        return applyIntToInt(host);
    }
    
    
    public static int apply(IntegerAccess<Integer> access, int value) {
        val resValue 
            = (access instanceof IntegerToIntegerAccessPrimitive)
            ? ((IntegerToIntegerAccessPrimitive)access).applyIntToInt(value)
            : access.applyAsInt(value);
        return resValue;
    }
    
    public default IntegerToIntegerAccessBoxed boxed() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue;
        };
    }
    
    //-- Compare --
    
    public default IntegerToIntegerAccessPrimitive toInteger() {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue;
        };
    }
    
    public default IntegerToLongAccessPrimitive toLong() {
        return host -> {
            int intValue = applyAsInt(host);
            return (long)intValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive toDouble() {
        return host -> {
            int intValue = applyAsInt(host);
            return (double)intValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive toZero() {
        return host -> 0;
    }
    
    public default IntegerToIntegerAccessPrimitive toOne() {
        return host -> 1;
    }
    
    public default IntegerToIntegerAccessPrimitive toMinusOne() {
        return host -> -1;
    }
    
    public default IntegerToIntegerAccessPrimitive abs() {
        return host -> {
            int intValue = applyAsInt(host);
            return (intValue < 0) ? -intValue : intValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive negate() {
        return host -> {
            int intValue = applyAsInt(host);
            return -intValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive signum() {
        return host -> {
            int intValue = applyAsInt(host);
            return (intValue == 0) ? 0 : (intValue < 0) ? -1 : 1;
        };
    }
    
    
    public default IntegerToIntegerAccessPrimitive bitAnd(int value) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue & value;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitAnd(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue & anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitAnd(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue & anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitAnd(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue & anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive bitOr(int value) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue | value;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitOr(IntSupplier anotherSupplier) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherSupplier.getAsInt();
            return intValue | value;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitOr(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue | anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitOr(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue | anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive bitAt(int bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return host -> {
            int intValue = applyAsInt(host);
            return (intValue & p) != 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive bitAt(IntSupplier anotherSupplier) {
        return host -> {
            int intValue = applyAsInt(host);
            int value    = anotherSupplier.getAsInt();
            val p        = (int)Math.pow(2, value);
            return (intValue & p) != 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive bitAt(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            val p            = (int)Math.pow(2, anotherValue);
            return (intValue & p) != 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive bitAt(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            val p        = (int)Math.pow(2, anotherValue);
            return (intValue & p) != 0;
        };
    }
    
    
    public default IntegerToIntegerAccessPrimitive compareTo(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            int compare  = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            int compare      = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            int compare      = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            int compare      = Integer.compare(intValue, anotherValue);
            return compare;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive cmp(int anotherValue) {
        return compareTo(anotherValue);
    }
    public default IntegerToIntegerAccessPrimitive cmp(IntSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerToIntegerAccessPrimitive cmp(IntegerAccess<Integer> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default IntegerToIntegerAccessPrimitive cmp(ToIntBiIntFunction<Integer> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatEquals(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatEquals(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatEquals(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue == anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive eq(int anotherValue) {
        return thatEquals(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive eq(IntSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive eq(IntegerAccess<Integer> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive eq(ToIntBiIntFunction<Integer> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatNotEquals(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatNotEquals(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatNotEquals(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue != anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive neq(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive neq(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive neq(IntegerAccess<Integer> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive neq(ToIntBiIntFunction<Integer> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue > anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive gt(int anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive gt(IntSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gt(IntegerAccess<Integer> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive gt(ToIntBiIntFunction<Integer> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatLessThan(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue < anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive lt(int anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive lt(IntSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lt(IntegerAccess<Integer> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive lt(ToIntBiIntFunction<Integer> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue >= anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive gteq(int anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive gteq(IntSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive gteq(IntegerAccess<Integer> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive gteq(ToIntBiIntFunction<Integer> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            int intValue = applyAsInt(host);
            return intValue <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return intValue <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue <= anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive lteq(int anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default IntegerToBooleanAccessPrimitive lteq(IntSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default IntegerToBooleanAccessPrimitive lteq(IntegerAccess<Integer> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default IntegerToBooleanAccessPrimitive lteq(ToIntBiIntFunction<Integer> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    public default IntegerToIntegerAccessPrimitive plus(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue + anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive plus(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue + anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive plus(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue + anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive plus(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue + anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive minus(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue - anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive minus(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue - anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive minus(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue - anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive minus(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue - anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive time(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue * anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive time(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue * anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive time(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue * anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive time(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue * anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive dividedBy(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return 1.0 * intValue / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(IntSupplier anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = anotherAccess.getAsInt();
            return 1.0*intValue / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return 1.0*intValue / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return 1.0*intValue / anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive remainderBy(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return intValue % anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive remainderBy(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return intValue % anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive remainderBy(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return intValue % anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive remainderBy(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return intValue % anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive pow(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return Math.pow(intValue, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return Math.pow(intValue, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.pow(intValue, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return Math.pow(intValue, anotherValue);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive min(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return Math.min(intValue, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive min(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return Math.min(intValue, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive min(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.min(intValue, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive min(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return Math.min(intValue, anotherValue);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive max(int value) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = value;
            return Math.max(intValue, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive max(IntSupplier valueSupplier) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = valueSupplier.getAsInt();
            return Math.max(intValue, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive max(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.max(intValue, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive max(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            int intValue     = applyAsInt(host);
            int anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, intValue);
            return Math.max(intValue, anotherValue);
        };
    }
    
}
