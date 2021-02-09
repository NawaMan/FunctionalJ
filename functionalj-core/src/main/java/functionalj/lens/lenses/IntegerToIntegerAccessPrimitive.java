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

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.ToIntBiIntFunction;
import functionalj.list.intlist.IntFuncList;
import lombok.val;


/**
 * Classes implementing this interface know how to access from an integer to an integer value.
 **/
@FunctionalInterface
public interface IntegerToIntegerAccessPrimitive extends IntUnaryOperator, IntegerAccessPrimitive<Integer>, IntFunction<Integer> {
    
    public static int apply(IntegerAccess<Integer> access, int value) {
        val resValue 
            = (access instanceof IntegerToIntegerAccessPrimitive)
            ? ((IntegerToIntegerAccessPrimitive)access).applyIntToInt(value)
            : access.applyAsInt(value);
        return resValue;
    }
    
    //== abstract functionalities ==
    
    public int applyIntToInt(int host);
    
    
    @Override
    public default Integer apply(int host) {
        return applyIntToInt(host);
    }
    
    @Override
    public default int applyAsInt(int host) {
        return applyIntToInt(host);
    }
    
    @Override
    public default int applyAsInt(Integer host) {
        return applyIntToInt(host);
    }
    
    //-- create --
    
    @Override
    public default IntegerToIntegerAccessPrimitive newAccess(Function<Integer, Integer> accessToValue) {
        return accessToValue::apply;
    }
    
    public default IntegerToIntegerAccessPrimitive newAccess(IntUnaryOperator accessToValue) {
        return accessToValue::applyAsInt;
    }
    
    //-- conversion --
    
    public default IntegerToIntegerAccessBoxed boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerToIntegerAccessPrimitive asInteger() {
        return host -> applyAsInt(host);
    }
    
    @Override
    public default IntegerToLongAccessPrimitive asLong() {
        return host -> applyAsInt(host);
    }
    
    @Override
    public default IntegerToDoubleAccessPrimitive asDouble() {
        return host -> applyAsInt(host);
    }
    
    public default IntegerToStringAccessPrimitive asString() {
        return host -> "" + applyAsInt(host);
    }
    public default IntegerToStringAccessPrimitive asString(String template) {
        return host -> {
            val value = applyAsInt(host);
            return String.format(template, value);
        };
    }
    
    //-- to value --
    
    public default IntegerToIntegerAccessPrimitive toZero() {
        return host -> 0;
    }
    
    public default IntegerToIntegerAccessPrimitive toOne() {
        return host -> 1;
    }
    
    public default IntegerToIntegerAccessPrimitive toMinusOne() {
        return host -> -1;
    }
    
    public default IntegerToIntegerAccessPrimitive to(int anotherValue) {
        return host -> {
            val value   = applyAsInt(host);
            val compare = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive to(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive to(IntUnaryOperator anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive to(IntBiFunctionPrimitive anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    
    //-- Equality --
    
    public default IntegerToBooleanAccessPrimitive thatIs(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIs(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value == anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNot(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNot(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value != anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(int ... otherValues) {
        return host -> {
            val value = applyAsInt(host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return true;
                }
            }
            return false;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsAnyOf(IntFuncList otherValues) {
        return host -> {
            val value = applyAsInt(host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNoneOf(int ... otherValues) {
        return host -> {
            val value = applyAsInt(host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return false;
                }
            }
            return true;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsoneOf(IntFuncList otherValues) {
        return host -> {
            val value = applyAsInt(host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsOne() {
        return thatIs(1);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsZero() {
        return thatIs(0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsMinusOne() {
        return thatIs(-1);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsFourtyTwo() {
        return thatIs(42);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNotOne() {
        return thatIsNot(1);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNotZero() {
        return thatIsNot(0);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsNotMinusOne() {
        return thatIsNot(-1);
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsPositive() {
        return host -> {
            val value = applyAsInt(host);
            return value > 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNegative() {
        return host -> {
            val value = applyAsInt(host);
            return value < 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNotPositive() {
        return host -> {
            val value = applyAsInt(host);
            return value <= 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsNotNegative() {
        return host -> {
            val value = applyAsInt(host);
            return value >= 0;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatEquals(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatEquals(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value == anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatEquals(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value == anotherValue;
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
            val value = applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            int anotherValue = anotherSupplier.getAsInt();
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatNotEquals(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value != anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatNotEquals(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value != anotherValue;
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
    
    //-- Compare --
    
    public default IntegerToIntegerAccessPrimitive compareTo(int anotherValue) {
        return host -> {
            val value   = applyAsInt(host);
            val compare = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerToIntegerAccessPrimitive compareTo(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            val compare      = Integer.compare(value, anotherValue);
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
    
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value > anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThan(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value > anotherValue;
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
            val value = applyAsInt(host);
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value < anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThan(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value < anotherValue;
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
            val value = applyAsInt(host);
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value >= anotherValue;
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
            val value = applyAsInt(host);
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatLessThanOrEqualsTo(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value <= anotherValue;
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
    
    //-- digitAt
    
    public default IntegerToIntegerAccessPrimitive digitAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive digitAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive digitAt(ToIntFunction<Integer> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive digitAt(ToIntBiIntFunction<Integer> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- digitValueAt
    
    public default IntegerToIntegerAccessPrimitive digitValueAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive digitValueAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive digitValueAt(ToIntFunction<Integer> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive digitValueAt(ToIntBiIntFunction<Integer> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- factorValueAt
    
    public default IntegerToIntegerAccessPrimitive factorValueAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive factorValueAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive factorValueAt(ToIntFunction<Integer> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerToIntegerAccessPrimitive factorValueAt(ToIntBiIntFunction<Integer> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- factorValueAt
    
    public default IntegerToIntegerAccessPrimitive largestFactor() {
        return host -> {
            val value = applyAsInt(host);
            return largestFactorOfRef.get().applyAsInt(value);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive largestFactorIndex() {
        return host -> {
            val value = applyAsInt(host);
            return largestFactorIndexOfRef.get().applyAsInt(value);
        };
    }
    
    //-- Min+Max --
    
    public default IntegerToIntegerAccessPrimitive min(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive min(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive min(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive min(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return Math.min(value, anotherValue);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive max(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive max(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive max(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerToIntegerAccessPrimitive max(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    public default IntegerToBooleanAccessPrimitive thatIsOdd() {
        return host -> {
            val value = applyAsInt(host);
            return value % 2 != 0;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsEven() {
        return host -> {
            val value = applyAsInt(host);
            return value % 2 == 0;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive abs() {
        return host -> {
            val value = applyAsInt(host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive negate() {
        return host -> {
            val value = applyAsInt(host);
            return -value;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive signum() {
        return host -> {
            val value = applyAsInt(host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive plus(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value + anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive plus(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value + anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive plus(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value + anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive plus(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value + anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive minus(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value - anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive minus(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value - anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive minus(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value - anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive minus(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value - anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive time(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value * anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive time(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value * anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive time(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value * anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive time(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value * anotherValue;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive dividedBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return 1.0 * value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(IntSupplier anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.getAsInt();
            return 1.0*value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return 1.0*value / anotherValue;
        };
    }
    public default IntegerToDoubleAccessPrimitive dividedBy(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return 1.0*value / anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive remainderBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value % anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive remainderBy(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value % anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive remainderBy(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value % anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive remainderBy(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value % anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value % anotherValue == 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(IntSupplier anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.getAsInt();
            return value % anotherValue == 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive thatIsDivisibleBy(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value % anotherValue == 0;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive square() {
        return host -> {
            val value = applyAsInt(host);
            return value * value;
        };
    }
    
    public default IntegerToDoubleAccessPrimitive squareRoot () {
        return host -> {
            val value = applyAsInt(host);
            return Math.sqrt(value);
        };
    }
    
    public default IntegerToIntegerAccessPrimitive factorial() {
        return host -> {
            val value = applyAsInt(host);
            if (value <= 0) {
                return 1;
            }
            
            return factorialRef.get().applyAsInt(value);
        };
    }
    
    public default IntegerToDoubleAccessPrimitive pow(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.pow(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    public default IntegerToDoubleAccessPrimitive pow(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return Math.pow(value, anotherValue);
        };
    }
    
    //-- Bit wise --
    
    public default IntegerToIntegerAccessPrimitive bitAnd(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value & anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitAnd(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value & anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitAnd(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value & anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitAnd(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value & anotherValue;
        };
    }
    
    public default IntegerToIntegerAccessPrimitive bitOr(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value | anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitOr(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value | anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitOr(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value | anotherValue;
        };
    }
    public default IntegerToIntegerAccessPrimitive bitOr(ToIntBiIntFunction<Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = IntBiFunctionPrimitive.apply(anotherFunction, host, value);
            return value | anotherValue;
        };
    }
    
    public default IntegerToBooleanAccessPrimitive bitAt(int bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return host -> {
            val value = applyAsInt(host);
            return (value & p) != 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive bitAt(IntSupplier bitIndexSupplier) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = bitIndexSupplier.getAsInt();
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive bitAt(IntegerAccess<Integer> anotherAccess) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = IntegerToIntegerAccessPrimitive.apply(anotherAccess, host);
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    public default IntegerToBooleanAccessPrimitive bitAt(ToIntBiIntFunction<Integer> bitIndexFunction) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = IntBiFunctionPrimitive.apply(bitIndexFunction, host, value);
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    
}
