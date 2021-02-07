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

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

import functionalj.function.Func1;
import functionalj.function.ToIntBiIntFunction;
import functionalj.functions.IntFuncs;
import functionalj.list.intlist.IntFuncList;
import functionalj.ref.Ref;
import lombok.val;


/**
 * Classes implementing this interface know how to access to an integer value.
 **/
public interface IntegerAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Integer, IntegerAccess<HOST>>,
                        ToIntFunction<HOST>,
                        ConcreteAccess<HOST, Integer, IntegerAccess<HOST>> {
    
    /** The reference to a function to calculate factorial for integer. **/
    public static final Ref<IntUnaryOperator> factorialRef = Ref.ofValue(value -> IntFuncs.factorial(value));
    
    /**
     * The reference to a function to calculate value of the integer at the digit.
     * 
     * For example: Digit of 512 at 2 is 5.
     ***/
    public static final Ref<IntBinaryOperator> digitAtRef = Ref.ofValue((value, digit) -> IntFuncs.digitAt(value, digit));
    
    /**
     * The reference to a function to calculate value of the integer at the digit.
     * 
     * For example: Digit value of 512 at 2 is 500.
     ***/
    public static final Ref<IntBinaryOperator> digitValueAtRef = Ref.ofValue((value, digit) -> IntFuncs.digitValueAt(value, digit));
    
    /**
     * The reference to a function to calculate factor value of the integer at the digit.
     * 
     * For example: Factor value of 512 at 2 is 100.
     ***/
    public static final Ref<IntBinaryOperator> factorValueAtRef = Ref.ofValue((value, digit) -> IntFuncs.factorValueAt(value, digit));
    
    /**
     * The reference to a function to calculate largest factor of an integer.
     * 
     * For example: Largest factor of 512 is 100.
     * For example: Largest factor of 4096 is 1000.
     ***/
    public static final Ref<IntUnaryOperator> largestFactorOfRef = Ref.ofValue((value) -> IntFuncs.largestFactorOf(value));
    
    /**
     * The reference to a function to calculate largest factor of an integer.
     * 
     * For example: Largest factor of 512 is 2.
     * For example: Largest factor of 4096 is 3.
     ***/
    public static final Ref<IntUnaryOperator> largestFactorIndexOfRef = Ref.ofValue((value) -> IntFuncs.largestFactorIndexOf(value));
    
    
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
    
    public static <H> IntegerAccessPrimitive<H> ofPrimitive(ToIntFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (IntegerAccessPrimitive<H>)accessToValue::applyAsInt;
        return access;
    }
    
    //== abstract functionalities ==
    
    public int applyAsInt(HOST host);
    
    
    public Integer applyUnsafe(HOST host) throws Exception;
    
    
    //-- create --
    
    @Override
    public default IntegerAccess<HOST> newAccess(Function<HOST, Integer> accessToValue) {
        return of(accessToValue);
    }
    
    //-- conversion --
    
    public default IntegerAccessBoxed<HOST> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerAccessPrimitive<HOST> asInteger() {
        return host -> applyAsInt(host);
    }
    
    @Override
    public default LongAccessPrimitive<HOST> asLong() {
        return host -> applyAsInt(host);
    }
    
    @Override
    public default DoubleAccessPrimitive<HOST> asDouble() {
        return host -> applyAsInt(host);
    }
    
    public default StringAccess<HOST> asString() {
        return host -> "" + applyAsInt(host);
    }
    public default StringAccess<HOST> asString(String template) {
        return host -> {
            val value = applyAsInt(host);
            return String.format(template, value);
        };
    }
    
    //-- to value --
    
    public default IntegerAccessPrimitive<HOST> toZero() {
        return host -> 0;
    }
    
    public default IntegerAccessPrimitive<HOST> toOne() {
        return host -> 1;
    }
    
    public default IntegerAccessPrimitive<HOST> toMinusOne() {
        return host -> -1;
    }
    
    public default IntegerAccessPrimitive<HOST> to(int anotherValue) {
        return host -> {
            val value   = applyAsInt(host);
            val compare = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> to(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> to(ToIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> to(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    
    //-- Equality --
    
    public default BooleanAccessPrimitive<HOST> thatIs(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAnyOF(int ... otherValues) {
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
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(IntFuncList otherValues) {
        return host -> {
            val value = applyAsInt(host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(int ... otherValues) {
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
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(IntFuncList otherValues) {
        return host -> {
            val value = applyAsInt(host);
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
        return thatIsNot(0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotZero() {
        return thatIsNot(0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotMinusOne() {
        return thatIsNot(-1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsPositive() {
        return host -> {
            val value = applyAsInt(host);
            return value > 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNegative() {
        return host -> {
            val value = applyAsInt(host);
            return value < 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotPositive() {
        return host -> {
            val value = applyAsInt(host);
            return value <= 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotNegative() {
        return host -> {
            val value = applyAsInt(host);
            return value >= 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatEquals(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> eq(int anotherValue) {
        return thatEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> eq(IntSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> eq(ToIntFunction<HOST> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> eq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> neq(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> neq(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> neq(ToIntFunction<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> neq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    //-- Compare --
    
    public default IntComparator ascendingOrder() {
        return (a, b) -> Integer.compare(a, b);
    }
    
    public default IntComparator descendingOrder() {
        return (a, b) -> Integer.compare(b, a);
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(int anotherValue) {
        return host -> {
            val value   = applyAsInt(host);
            val compare = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            val compare      = Integer.compare(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(int anotherValue) {
        return compareTo(anotherValue);
    }
    public default IntegerAccessPrimitive<HOST> cmp(IntSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerAccessPrimitive<HOST> cmp(ToIntFunction<HOST> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default IntegerAccessPrimitive<HOST> cmp(ToIntBiIntFunction<HOST> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gt(int anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gt(IntSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gt(ToIntFunction<HOST> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gt(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lt(int anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lt(IntSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lt(ToIntFunction<HOST> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lt(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(int anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gteq(IntSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gteq(ToIntFunction<HOST> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gteq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(int anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lteq(IntSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lteq(ToIntFunction<HOST> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lteq(ToIntBiIntFunction<HOST> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    //-- digitAt
    
    public default IntegerAccessPrimitive<HOST> digitAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> digitAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> digitAt(ToIntFunction<HOST> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> digitAt(ToIntBiIntFunction<HOST> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return digitAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- digitValueAt
    
    public default IntegerAccessPrimitive<HOST> digitValueAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> digitValueAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> digitValueAt(ToIntFunction<HOST> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> digitValueAt(ToIntBiIntFunction<HOST> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return digitValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- factorValueAt
    
    public default IntegerAccessPrimitive<HOST> factorValueAt(int digitIndex) {
        return host -> {
            val value = applyAsInt(host);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> factorValueAt(IntSupplier digitIndexSupplier) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexSupplier.getAsInt();
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> factorValueAt(ToIntFunction<HOST> digitIndexAccess) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexAccess.applyAsInt(host);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    public default IntegerAccessPrimitive<HOST> factorValueAt(ToIntBiIntFunction<HOST> digitIndexFunction) {
        return host -> {
            val value      = applyAsInt(host);
            val digitIndex = digitIndexFunction.applyAsInt(host, value);
            return factorValueAtRef.get().applyAsInt(value, digitIndex);
        };
    }
    
    //-- factorValueAt
    
    public default IntegerAccessPrimitive<HOST> largestFactor() {
        return host -> {
            val value = applyAsInt(host);
            return largestFactorOfRef.get().applyAsInt(value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> largestFactorIndex() {
        return host -> {
            val value = applyAsInt(host);
            return largestFactorIndexOfRef.get().applyAsInt(value);
        };
    }
    
    //-- Min+Max --
    
    public default IntegerAccessPrimitive<HOST> min(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return Math.min(value, anotherValue);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> max(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    public default MathOperators<Integer> __mathOperators() {
        return IntMathOperators.instance;
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsOdd() {
        return host -> {
            val value = applyAsInt(host);
            return value % 2 != 0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsEven() {
        return host -> {
            val value = applyAsInt(host);
            return value % 2 == 0;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> abs() {
        return host -> {
            val value = applyAsInt(host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> negate() {
        return host -> {
            val value = applyAsInt(host);
            return -value;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> signum() {
        return host -> {
            val value = applyAsInt(host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> plus(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return value + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return value + anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> minus(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return value - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return value - anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> time(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> time(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> time(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return value * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> time(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return value * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return 1.0*value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> remainderBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return value % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return value % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return value % anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value % anotherValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(IntSupplier anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.getAsInt();
            return value % anotherValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value % anotherValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value % anotherValue == 0;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> square() {
        return host -> {
            val value = applyAsInt(host);
            return value * value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> squareRoot () {
        return host -> {
            val value = applyAsInt(host);
            return Math.sqrt(value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> factorial() {
        return host -> {
            val value = applyAsInt(host);
            if (value <= 0) {
                return 1;
            }
            
            return factorialRef.get().applyAsInt(value);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(IntSupplier valueSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueSupplier.getAsInt();
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(ToIntBiIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = valueFunction.applyAsInt(host, value);
            return Math.pow(value, anotherValue);
        };
    }
    
    //-- Bit wise --
    
    public default IntegerAccessPrimitive<HOST> bitAnd(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value & anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitAnd(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value & anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitAnd(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value & anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitAnd(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value & anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> bitOr(int anotherValue) {
        return host -> {
            val value = applyAsInt(host);
            return value | anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitOr(IntSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherSupplier.getAsInt();
            return value | anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitOr(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherAccess.applyAsInt(host);
            return value | anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> bitOr(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsInt(host);
            val anotherValue = anotherFunction.applyAsInt(host, value);
            return value | anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> bitAt(int bitIndex) {
        val p = (int)Math.pow(2, bitIndex);
        return host -> {
            val value = applyAsInt(host);
            return (value & p) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(IntSupplier bitIndexSupplier) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = bitIndexSupplier.getAsInt();
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = anotherAccess.applyAsInt(host);
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> bitAt(ToIntBiIntFunction<HOST> anotherFunction) {
        return host -> {
            val value    = applyAsInt(host);
            val bitIndex = anotherFunction.applyAsInt(host, value);
            val bitValue = (int)Math.pow(2, bitIndex);
            return (value & bitValue) != 0;
        };
    }
    
}
