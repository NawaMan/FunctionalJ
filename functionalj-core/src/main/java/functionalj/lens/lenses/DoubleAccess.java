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

import static java.util.Objects.requireNonNull;

import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import functionalj.function.Func1;
import functionalj.function.ToDoubleBiDoubleFunction;
import lombok.val;


public interface DoubleAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Double, DoubleAccess<HOST>>, 
                        ToDoubleFunction<HOST>, 
                        ConcreteAccess<HOST, Double, DoubleAccess<HOST>> {
    
    public static <H> DoubleAccess<H> of(Function<H, Double> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof DoubleAccess) {
            return (DoubleAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToDoubleFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToDoubleFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Double>)accessToValue;
            val access = (DoubleAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }
        
        val func   = (Function<H, Double>)accessToValue;
        val access = (DoubleAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> DoubleAccess<H> ofPrimitive(ToDoubleFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (DoubleAccessPrimitive<H>)accessToValue::applyAsDouble;
        return access;
    }
    
    
    public double applyAsDouble(HOST host);
    
    public Double applyUnsafe(HOST host) throws Exception;
    
    
    @Override
    public default DoubleAccess<HOST> newAccess(Function<HOST, Double> accessToValue) {
        return of(accessToValue);
    }
    
    public default MathOperators<Double> __mathOperators() {
        return DoubleMathOperators.instance;
    }
    
    //-- Compare --
    
    public default DoubleComparator ascendingOrder() {
        return (a, b) -> Double.compare(a, b);
    }
    
    public default DoubleComparator descendingOrder() {
        return (a, b) -> Double.compare(b, a);
    }
    
    // TODO - Supplier and BiFunction
    // TODO - Move back to NumberAccess
    // TODO - Add Byte/Character
    
    public default BooleanAccessPrimitive<HOST> thatIsZero() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotZero() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsPositive() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue > 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNegative() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue < 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotPositive() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue <= 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotNegative() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue >= 0;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> toInteger() {
        return toInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public default LongAccessPrimitive<HOST> toLong() {
        return toLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public default IntegerAccessPrimitive<HOST> toInteger(int overflowValue) {
        return toInteger(overflowValue, overflowValue);
    }
    
    public default LongAccessPrimitive<HOST> toLong(long overflowValue) {
        return toLong(overflowValue, overflowValue);
    }
    
    public default IntegerAccessPrimitive<HOST> toInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            if (doubleValue < Integer.MIN_VALUE)
                return negativeOverflowValue;
            if (doubleValue > Integer.MIN_VALUE)
                return positiveOverflowValue;
            return (int)doubleValue;
        };
    }
    
    public default LongAccessPrimitive<HOST> toLong(long negativeOverflowValue, long positiveOverflowValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            if (doubleValue < Long.MIN_VALUE)
                return negativeOverflowValue;
            if (doubleValue > Long.MIN_VALUE)
                return positiveOverflowValue;
            return (long)doubleValue;
        };
    }
    
    public default IntegerAccessBoxed<HOST> toIntegerOrNull(Integer negativeOverflowValue, Integer positiveOverflowValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            if (doubleValue < Integer.MIN_VALUE)
                return negativeOverflowValue;
            if (doubleValue > Integer.MIN_VALUE)
                return positiveOverflowValue;
            return (int)doubleValue;
        };
    }
    
    public default LongAccessBoxed<HOST> toLong(Long negativeOverflowValue, Long positiveOverflowValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            if (doubleValue < Long.MIN_VALUE)
                return negativeOverflowValue;
            if (doubleValue > Long.MIN_VALUE)
                return positiveOverflowValue;
            return (long)doubleValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> roundToInt() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return (int)Math.round(doubleValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> roundToLong() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.round(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> round() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return (double)Math.round(doubleValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> ceil() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return (long)Math.ceil(doubleValue);
        };
    }
    
    public default LongAccessPrimitive<HOST> floor() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return (long)Math.floor(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> toDouble() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return (double)doubleValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> toZero() {
        return host -> 0;
    }
    
    public default DoubleAccessPrimitive<HOST> toOne() {
        return host -> 1;
    }
    
    public default DoubleAccessPrimitive<HOST> toMinusOne() {
        return host -> -1;
    }
    
    public default DoubleAccessPrimitive<HOST> abs() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return (doubleValue < 0) ? -doubleValue : doubleValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> negate() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return -doubleValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> signum() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return (doubleValue == 0) ? 0 : (doubleValue < 0) ? -1 : 1;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(double anotherValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            int    compare     = Double.compare(doubleValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(DoubleSupplier anotherSupplier) {
        return host -> {
            double doubleValue  = applyAsDouble(host);
            double anotherValue = anotherSupplier.getAsDouble();
            int    compare      = Double.compare(doubleValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(DoubleAccess<HOST> anotherAccess) {
        return host -> {
            double doubleValue  = applyAsDouble(host);
            double anotherValue = anotherAccess.applyAsDouble(host);
            int    compare      = Double.compare(doubleValue, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            double doubleValue  = applyAsDouble(host);
            double anotherValue = anotherFunction.applyAsDouble(host, doubleValue);
            int    compare      = Double.compare(doubleValue, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    public default IntegerAccessPrimitive<HOST> cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerAccessPrimitive<HOST> cmp(DoubleAccess<HOST> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default IntegerAccessPrimitive<HOST> cmp(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatEquals(double anotherValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(DoubleSupplier anotherSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherSupplier.getAsDouble();
            return doubleValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(DoubleAccess<HOST> anotherAccess) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherAccess.applyAsDouble(host);
            return doubleValue == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            double doubleValue    = applyAsDouble(host);
            double anotherValue = anotherFunction.applyAsDouble(host, doubleValue);
            return doubleValue == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> eq(DoubleAccess<HOST> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> eq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(double anotherValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(DoubleSupplier anotherSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherSupplier.getAsDouble();
            return doubleValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(DoubleAccess<HOST> anotherAccess) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherAccess.applyAsDouble(host);
            return doubleValue != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherFunction.applyAsDouble(host, doubleValue);
            return doubleValue != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> neq(DoubleAccess<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> neq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(double anotherValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherSupplier.getAsDouble();
            return doubleValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(DoubleAccess<HOST> anotherAccess) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherAccess.applyAsDouble(host);
            return doubleValue > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherFunction.applyAsDouble(host, doubleValue);
            return doubleValue > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gt(DoubleAccess<HOST> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gt(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(double anotherValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherSupplier.getAsDouble();
            return doubleValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(DoubleAccess<HOST> anotherAccess) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherAccess.applyAsDouble(host);
            return doubleValue < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherFunction.applyAsDouble(host, doubleValue);
            return doubleValue < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lt(DoubleAccess<HOST> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lt(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherSupplier.getAsDouble();
            return doubleValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(DoubleAccess<HOST> anotherAccess) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherAccess.applyAsDouble(host);
            return doubleValue >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherFunction.applyAsDouble(host, doubleValue);
            return doubleValue >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gteq(DoubleAccess<HOST> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gteq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherSupplier.getAsDouble();
            return doubleValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(DoubleAccess<HOST> anotherAccess) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherAccess.applyAsDouble(host);
            return doubleValue <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = anotherFunction.applyAsDouble(host, doubleValue);
            return doubleValue <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lteq(DoubleAccess<HOST> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lteq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    public default DoubleAccessPrimitive<HOST> plus(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return doubleValue + anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> plus(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return doubleValue + anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> plus(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return doubleValue + anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> plus(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return doubleValue + anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> minus(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return doubleValue - anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> minus(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return doubleValue - anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> minus(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return doubleValue - anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> minus(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return doubleValue - anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> time(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return doubleValue * anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> time(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return doubleValue * anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> time(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return doubleValue * anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> time(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return doubleValue * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return doubleValue / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return doubleValue / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return doubleValue / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return doubleValue / anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> remainderBy(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return doubleValue % anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return doubleValue % anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> remainderBy(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return doubleValue % anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> remainderBy(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue  = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return doubleValue % anotherValue;
        };
    }
    
    
    // TODO - Add more
//    Math.addExact((int)0, (int)0)
//    Math.addExact((long)0, (long)0)
//    Math.decrementExact((int)0)
//    Math.decrementExact((long)0)
//    Math.incrementExact((int)0)
//    Math.incrementExact((long)0)
//    Math.multiplyExact(int, int)
//    Math.multiplyExact(long, long)
//    Math.negateExact(int)
//    Math.negateExact(long)
//    Math.subtractExact(int, int)
//    Math.subtractExact(long, long)
//    Math.toIntExact(0)
    
    public default DoubleAccessPrimitive<HOST> square() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return doubleValue * doubleValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> squareRoot () {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.sqrt(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return Math.pow(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return Math.pow(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return Math.pow(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return Math.pow(doubleValue, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> exp() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.exp(doubleValue);
        };
    }
    
    /**
     * Returns <i>e</i><sup>x</sup>&nbsp;-1.  Note that for values of
     * <i>x</i> near 0, the exact sum of
     * {@code expm1(x)}&nbsp;+&nbsp;1 is much closer to the true
     * result of <i>e</i><sup>x</sup> than {@code exp(x)}.
     **/
    public default DoubleAccessPrimitive<HOST> expm1() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.expm1(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> log() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> log10() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log10(doubleValue);
        };
    }
    
    /**
     * Returns the base 10 logarithm of a {@code double} value.
     * Special cases:
     *
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is negative infinity.
     * <li> If the argument is equal to 10<sup><i>n</i></sup> for
     * integer <i>n</i>, then the result is <i>n</i>.
     * </ul>
     *
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     */
    public default DoubleAccessPrimitive<HOST> log1p() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log1p(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> min(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return Math.min(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> min(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue  = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return Math.min(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> min(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return Math.min(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> min(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue  = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return Math.min(doubleValue, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> max(double value) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = value;
            return Math.max(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> max(DoubleSupplier valueSupplier) {
        return host -> {
            double doubleValue  = applyAsDouble(host);
            double anotherValue = valueSupplier.getAsDouble();
            return Math.max(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> max(DoubleAccess<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host);
            return Math.max(doubleValue, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> max(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            double doubleValue     = applyAsDouble(host);
            double anotherValue = valueFunction.applyAsDouble(host, doubleValue);
            return Math.max(doubleValue, anotherValue);
        };
    }
    
    // TODO - Add more.
    
//    Math.acos(doubleValue)
//    Math.asin(doubleValue)
//    Math.tan(doubleValue)
//    Math.tan2(doubleValue)
//    Math.cos(doubleValue)
//    Math.cosh(doubleValue)
//    Math.sin(doubleValue)
//    Math.sinh(doubleValue)
//    Math.tan(doubleValue)
//    Math.tanh(doubleValue)
//    
//    Math.toDegrees(doubleValue)
//    Math.toRadians(doubleValue)
    
}
