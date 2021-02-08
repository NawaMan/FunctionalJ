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

import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

import functionalj.function.DoubleIntegerToIntegerFunction;
import functionalj.list.doublelist.DoubleFuncList;
import lombok.val;


/**
 * Classes implementing this interface know how to access from a double to an integer value.
 **/
@FunctionalInterface
public interface DoubleToIntegerAccessPrimitive extends IntegerAccessPrimitive<Double>, DoubleToIntFunction, DoubleFunction<Integer> {
    
    public static int apply(IntegerAccess<Double> access, double value) {
        val resValue 
            = (access instanceof DoubleToIntegerAccessPrimitive)
            ? ((DoubleToIntegerAccessPrimitive)access).applyDoubleToInt(value)
            : access.applyAsInt(value);
        return resValue;
    }
    
    //== abstract functionalities ==
    
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
    
    //-- create --
    
    @Override
    public default DoubleToIntegerAccessPrimitive newAccess(Function<Double, Integer> accessToValue) {
        return accessToValue::apply;
    }
    
    //-- conversion --
    
    // TODO - Figure this out.
//    public default DoubleToIntegerAccessBoxed boxed() {
//        return host -> apply(host);
//    }
    
    @Override
    public default DoubleToIntegerAccessPrimitive asInteger() {
        return host -> applyAsInt(host);
    }
    
    @Override
    public default DoubleToLongAccessPrimitive asLong() {
        return host -> applyAsInt(host);
    }
    
    @Override
    public default DoubleToDoubleAccessPrimitive asDouble() {
        return host -> applyAsInt(host);
    }
    
    public default DoubleToStringAccessPrimitive asString() {
        return host -> "" + applyAsInt(host);
    }
    public default DoubleToStringAccessPrimitive asString(String template) {
        return host -> {
            val value = applyAsInt(host);
            return String.format(template, value);
        };
    }
    
    //-- to value --
    
    public default DoubleToIntegerAccessPrimitive toZero() {
        return host -> 0;
    }
    
    public default DoubleToIntegerAccessPrimitive toOne() {
        return host -> 1;
    }
    
    public default DoubleToIntegerAccessPrimitive toMinusOne() {
        return host -> -1;
    }
    
    public default DoubleToIntegerAccessPrimitive to(int anotherValue) {
        return host -> {
            val value   = applyAsInt(host);
            val compare = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive to(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsDouble();
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive to(DoubleToIntFunction anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive to(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    
    //-- Equality --
    
    public default DoubleToBooleanAccessPrimitive thatIs(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIs(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIs(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value == anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNot(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNot(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNot(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value != anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(double ... otherValues) {
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
    public default DoubleToBooleanAccessPrimitive thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsInt(host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNoneOf(double ... otherValues) {
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
    public default DoubleToBooleanAccessPrimitive thatIsoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsInt(host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsOne() {
        return thatIs(1);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsZero() {
        return thatIs(0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsMinusOne() {
        return thatIs(-1);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsFourtyTwo() {
        return thatIs(42);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNotOne() {
        return thatIsNot(1);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNotZero() {
        return thatIsNot(0);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsNotMinusOne() {
        return thatIsNot(-1);
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsPositive() {
        return host -> {
            val value = applyAsInt(host);
            return value > 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNegative() {
        return host -> {
            val value = applyAsInt(host);
            return value < 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNotPositive() {
        return host -> {
            val value = applyAsInt(host);
            return value <= 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsNotNegative() {
        return host -> {
            val value = applyAsInt(host);
            return value >= 0;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatEquals(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatEquals(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value == anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatEquals(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value == anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive eq(int anotherValue) {
        return thatEquals(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive eq(IntSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive eq(IntegerAccess<Double> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive eq(ToIntBiFunction<Double, Integer> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatNotEquals(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatNotEquals(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value != anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatNotEquals(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value != anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive neq(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive neq(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive neq(IntegerAccess<Double> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive neq(ToIntBiFunction<Double, Integer> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    //-- Compare --
    
    public default DoubleToIntegerAccessPrimitive compareTo(int anotherValue) {
        return host -> {
            val value   = applyAsInt(host);
            val compare = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive compareTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive compareTo(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleToIntegerAccessPrimitive compareTo(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive cmp(int anotherValue) {
        return compareTo(anotherValue);
    }
    public default DoubleToIntegerAccessPrimitive cmp(IntSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default DoubleToIntegerAccessPrimitive cmp(IntegerAccess<Double> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default DoubleToIntegerAccessPrimitive cmp(ToIntBiFunction<Double, Integer> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value > anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value > anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value > anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThan(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value > anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive gt(int anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive gt(IntSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive gt(IntegerAccess<Double> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive gt(ToIntBiFunction<Double, Integer> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatLessThan(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value < anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value < anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThan(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value < anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThan(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value < anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive lt(int anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive lt(IntSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive lt(IntegerAccess<Double> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive lt(ToIntBiFunction<Double, Integer> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value >= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value >= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value >= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value >= anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive gteq(int anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive gteq(IntSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive gteq(IntegerAccess<Double> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive gteq(ToIntBiFunction<Double, Integer> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value <= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value <= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value <= anotherValue;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value <= anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive lteq(int anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default DoubleToBooleanAccessPrimitive lteq(IntSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default DoubleToBooleanAccessPrimitive lteq(IntegerAccess<Double> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default DoubleToBooleanAccessPrimitive lteq(ToIntBiFunction<Double, Integer> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    //-- digitAt
    
    public default DoubleToIntegerAccessPrimitive digitAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive digitAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive digitAt(ToIntFunction<Double> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive digitAt(ToIntBiFunction<Double, Integer> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- digitValueAt
    
    public default DoubleToIntegerAccessPrimitive digitValueAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive digitValueAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive digitValueAt(ToIntFunction<Double> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive digitValueAt(ToIntBiFunction<Double, Integer> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- factorValueAt
    
    public default DoubleToIntegerAccessPrimitive factorValueAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive factorValueAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive factorValueAt(ToIntFunction<Double> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default DoubleToIntegerAccessPrimitive factorValueAt(ToIntBiFunction<Double, Integer> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- factorValueAt
    
    public default DoubleToIntegerAccessPrimitive largestFactor() {
        return host -> {
            val value = applyAsInt(host);
            return largestFactorOfRef.get().applyAsInt(value);
        };
    }
    
    public default DoubleToIntegerAccessPrimitive largestFactorIndex() {
        return host -> {
            val value = applyAsInt(host);
            return largestFactorIndexOfRef.get().applyAsInt(value);
        };
    }
    
    //-- Min+Max --
    
    public default DoubleToIntegerAccessPrimitive min(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleToIntegerAccessPrimitive min(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleToIntegerAccessPrimitive min(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleToIntegerAccessPrimitive min(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return Math.min(value, anotherValue);
        };
    }
    
    public default DoubleToIntegerAccessPrimitive max(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleToIntegerAccessPrimitive max(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleToIntegerAccessPrimitive max(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleToIntegerAccessPrimitive max(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    public default DoubleToBooleanAccessPrimitive thatIsOdd() {
        return host -> {
            val value = applyAsInt(host);
            return value % 2 != 0;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsEven() {
        return host -> {
            val value = applyAsInt(host);
            return value % 2 == 0;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive abs() {
        return host -> {
            val value = applyAsInt(host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive negate() {
        return host -> {
            val value = applyAsInt(host);
            return -value;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive signum() {
        return host -> {
            val value = applyAsInt(host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive plus(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value + anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive plus(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value + anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive plus(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value + anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive plus(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value + anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive minus(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value - anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive minus(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value - anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive minus(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value - anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive minus(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value - anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive time(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value * anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive time(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value * anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive time(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value * anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive time(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value * anotherValue;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive dividedBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive dividedBy(IntSupplier anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.getAsInt();
            return 1.0*value / anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive dividedBy(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return 1.0*value / anotherValue;
        };
    }
    public default DoubleToDoubleAccessPrimitive dividedBy(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return 1.0*value / anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive remainderBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value % anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive remainderBy(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value % anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive remainderBy(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value % anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive remainderBy(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value % anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive thatIsDivisibleBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value % anotherValue == 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsDivisibleBy(IntSupplier anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.getAsInt();
            return value % anotherValue == 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsDivisibleBy(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive thatIsDivisibleBy(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value % anotherValue == 0;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive square() {
        return host -> {
            val value = applyAsInt(host);
            return value * value;
        };
    }
    
    public default DoubleToDoubleAccessPrimitive squareRoot () {
        return host -> {
            val value = applyAsInt(host);
            return Math.sqrt(value);
        };
    }
    
    public default DoubleToIntegerAccessPrimitive factorial() {
        return host -> {
            val value = applyAsInt(host);
            if (value <= 0) {
                return 1;
            }
            
            return factorialRef.get().applyAsInt(value);
        };
    }
    
    public default DoubleToDoubleAccessPrimitive pow(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive pow(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive pow(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleToDoubleAccessPrimitive pow(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return Math.pow(value, anotherValue);
        };
    }
    
    //-- Bit wise --
    
    public default DoubleToIntegerAccessPrimitive bitAnd(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value & anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive bitAnd(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value & anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive bitAnd(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value & anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive bitAnd(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value & anotherValue;
        };
    }
    
    public default DoubleToIntegerAccessPrimitive bitOr(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value | anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive bitOr(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value | anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive bitOr(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            return value | anotherValue;
        };
    }
    public default DoubleToIntegerAccessPrimitive bitOr(ToIntBiFunction<Double, Integer> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = DoubleIntegerToIntegerFunction.apply(anotherFunction, host, value);
            return value | anotherValue;
        };
    }
    
    public default DoubleToBooleanAccessPrimitive bitAt(int bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return host -> {
            val value = applyAsInt(host);
            return (value & p) != 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive bitAt(IntSupplier bitIndexSupplier) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = bitIndexSupplier.getAsInt();
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive bitAt(IntegerAccess<Double> anotherAccess) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = DoubleToIntegerAccessPrimitive.apply(anotherAccess, host);
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    public default DoubleToBooleanAccessPrimitive bitAt(ToIntBiFunction<Double, Integer> bitIndexFunction) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = DoubleIntegerToIntegerFunction.apply(bitIndexFunction, host, value);
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    
}
