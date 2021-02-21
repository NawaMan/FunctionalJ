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

import static functionalj.function.Apply.access;
import static functionalj.function.Apply.applyPrimitive;
import static functionalj.function.Apply.getPrimitive;
import static functionalj.function.Compare.compareOrNull;
import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

import functionalj.function.Func1;
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
    
    @Override
    public default IntegerAccess<HOST> newAccess(Function<HOST, Integer> accessToValue) {
        return of(accessToValue);
    }
    
    //== abstract functionalities ==
    
    public int applyAsInt(HOST host);
    
    
    public Integer applyUnsafe(HOST host) throws Exception;
    
    
    //-- conversion --
    
    public default IntegerAccessBoxed<HOST> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerAccessPrimitive<HOST> asInteger() {
        return host -> access(this, host);
    }
    
    @Override
    public default LongAccessPrimitive<HOST> asLong() {
        return host -> access(this, host);
    }
    
    @Override
    public default DoubleAccessPrimitive<HOST> asDouble() {
        return host -> access(this, host);
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
    
    //-- Equality --
    
    public default BooleanAccessPrimitive<HOST> that(IntPredicate checker) {
        return host -> {
            val value = access(this, host);
            return checker.test(value);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(IntSupplier anotherSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(IntSupplier anotherSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(int ... otherValues) {
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
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(IntFuncList otherValues) {
        return host -> {
            val value = access(this, host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(int ... otherValues) {
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
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(IntFuncList otherValues) {
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
    
    public default BooleanAccessPrimitive<HOST> thatEquals(int anotherValue) {
        return thatIs(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(IntSupplier anotherSupplier) {
        return thatIs(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> thatEquals(ToIntFunction<HOST> anotherAccess) {
        return thatIs(anotherAccess);
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
    
    public default BooleanAccessPrimitive<HOST> thatNotEquals(int anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(IntSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> thatNotEquals(ToIntFunction<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
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
    
    //-- Compare --
    
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
    
    public default IntegerAccessPrimitive<HOST> compareTo(int anotherValue) {
        return host -> {
            val value   = access(this, host);
            val compare = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            val compare      = compareOrNull(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToIntFunction<HOST> anotherFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherFunction, host);
            val compare      = compareOrNull(value, anotherValue);
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
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
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
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(IntSupplier anotherSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
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
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
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
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(IntSupplier anotherSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(anotherSupplier);
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
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
    
    //-- Min+Max --
    
    public default IntegerAccessPrimitive<HOST> min(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.min(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> min(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return Math.min(value, anotherValue);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> max(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return Math.max(value, anotherValue);
        };
    }
    public default IntegerAccessPrimitive<HOST> max(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    public default MathOperators<Integer> __mathOperators() {
        return IntMathOperators.instance;
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
    
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value % anotherValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(IntSupplier anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = anotherAccess.getAsInt();
            return value % anotherValue == 0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsDivisibleBy(ToIntFunction<HOST> anotherAccess) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(anotherAccess, host);
            return value % anotherValue == 0;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> abs() {
        return host -> {
            val value = access(this, host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> negate() {
        return host -> {
            val value = access(this, host);
            return -value;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> signum() {
        return host -> {
            val value = access(this, host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> plus(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value + anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> plus(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value + anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> minus(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value - anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> minus(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value - anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> time(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> time(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value * anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> time(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return 1.0*value / anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> remainderBy(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return value % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return value % anotherValue;
        };
    }
    public default IntegerAccessPrimitive<HOST> remainderBy(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return value % anotherValue;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> square() {
        return host -> {
            val value = access(this, host);
            return value * value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> squareRoot () {
        return host -> {
            val value = access(this, host);
            return Math.sqrt(value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> factorial() {
        return host -> {
            val value = access(this, host);
            if (value <= 0) {
                return 1;
            }
            
            return factorialRef.get().applyAsInt(value);
        };
    }
    
    // TODO - Make this Long once we are ready.
    
    public default LongAccessPrimitive<HOST> pow(int anotherValue) {
        return host -> {
            val value = access(this, host);
            return (long)Math.pow(value, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> pow(IntSupplier valueSupplier) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = getPrimitive(valueSupplier);
            return (long)Math.pow(value, anotherValue);
        };
    }
    public default LongAccessPrimitive<HOST> pow(ToIntFunction<HOST> valueFunction) {
        return host -> {
            val value        = access(this, host);
            val anotherValue = applyPrimitive(valueFunction, host);
            return (long)Math.pow(value, anotherValue);
        };
    }
    
}
