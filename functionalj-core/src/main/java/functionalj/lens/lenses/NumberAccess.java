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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import functionalj.function.Func;
import functionalj.lens.core.AccessCreator;
import lombok.val;

public interface NumberAccess<HOST, TYPE extends Comparable<TYPE>, NUMACCESS extends NumberAccess<HOST, TYPE, ?>> extends ComparableAccess<HOST, TYPE> {
    
    public default IntegerAccess<HOST> compareTo(TYPE anotherValue) {
        return intPrimitiveAccess(Integer.MIN_VALUE, any -> any.compareTo(anotherValue));
    }
    
    public default BooleanAccess<HOST> thatGreaterThan(TYPE anotherValue) {
        return booleanPrimitiveAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    
    public default BooleanAccess<HOST> thatLessThan(TYPE anotherValue) {
        return booleanPrimitiveAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(TYPE anotherValue) {
        return booleanPrimitiveAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(TYPE anotherValue) {
        return booleanPrimitiveAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsZero() {
        return (BooleanAccessPrimitive<HOST>) (host -> {
            val value = apply(host);
            val zero = toZero().apply(host);
            return value.compareTo(zero) == 0;
        });
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotZero() {
        return (BooleanAccessPrimitive<HOST>) (host -> {
            val value = apply(host);
            val zero = toZero().apply(host);
            return value.compareTo(zero) != 0;
        });
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsPositive() {
        return (BooleanAccessPrimitive<HOST>) (host -> {
            val value = apply(host);
            val zero = toZero().apply(host);
            return value.compareTo(zero) > 0;
        });
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNegative() {
        return (BooleanAccessPrimitive<HOST>) (host -> {
            val value = apply(host);
            val zero = toZero().apply(host);
            return value.compareTo(zero) < 0;
        });
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotPositive() {
        return (BooleanAccessPrimitive<HOST>) (host -> {
            val value = apply(host);
            val zero = toZero().apply(host);
            return value.compareTo(zero) <= 0;
        });
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotNegative() {
        return (BooleanAccessPrimitive<HOST>) (host -> {
            val value = apply(host);
            val zero = toZero().apply(host);
            return value.compareTo(zero) >= 0;
        });
    }
    
    public NUMACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
    public MathOperators<TYPE> __mathOperators();
    
    public default IntegerAccess<HOST> asInteger() {
        return intPrimitiveAccess(0, __mathOperators()::asInteger);
    }
    
    public default LongAccess<HOST> asLong() {
        return longPrimitiveAccess(0L, __mathOperators()::asLong);
    }
    
    public default DoubleAccess<HOST> asDouble() {
        return doublePrimitiveAccess(0.0, __mathOperators()::asDouble);
    }
    
    public default BigIntegerAccess<HOST> asBigInteger() {
        return bigIntegerAccess(BigInteger.ZERO, __mathOperators()::asBigInteger);
    }
    
    public default BigDecimalAccess<HOST> asBigDecimal() {
        return bigDecimalAccess(BigDecimal.ZERO, __mathOperators()::asBigDecimal);
    }
    
    // TODO -- These __operate should be moved to a util class.
    public default NUMACCESS __operate(AccessCreator<HOST, TYPE, NUMACCESS> accessCreator, Supplier<TYPE> operator) {
        return accessCreator.newAccess(host -> {
            return operator.get();
        });
    }
    
    public default NUMACCESS __operate(TYPE value, NUMACCESS orgAccess, AccessCreator<HOST, TYPE, NUMACCESS> accessCreator, BiFunction<TYPE, TYPE, TYPE> operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return value;
            val v = orgAccess.apply(host);
            return operator.apply(v, value);
        });
    }
    
    public default NUMACCESS __operate(TYPE fallbackValue, Supplier<TYPE> valueSupplier, NUMACCESS orgAccess, AccessCreator<HOST, TYPE, NUMACCESS> accessCreator, BiFunction<TYPE, TYPE, TYPE> operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return Func.getOrElse(valueSupplier, fallbackValue);
            val v = orgAccess.apply(host);
            val value = Func.getOrElse(valueSupplier, fallbackValue);
            return operator.apply(v, value);
        });
    }
    
    public default NUMACCESS __operate(TYPE fallbackValue, Function<TYPE, TYPE> valueFunction, NUMACCESS orgAccess, AccessCreator<HOST, TYPE, NUMACCESS> accessCreator, BiFunction<TYPE, TYPE, TYPE> operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return Func.applyOrElse(valueFunction, fallbackValue, fallbackValue);
            val v = orgAccess.apply(host);
            val value = Func.applyOrElse(valueFunction, v, fallbackValue);
            return operator.apply(v, value);
        });
    }
    
    public default NUMACCESS __operate(TYPE fallbackValue, BiFunction<HOST, TYPE, TYPE> valueFunction, NUMACCESS orgAccess, AccessCreator<HOST, TYPE, NUMACCESS> accessCreator, BiFunction<TYPE, TYPE, TYPE> operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return Func.applyOrElse(valueFunction, host, fallbackValue, fallbackValue);
            val v = orgAccess.apply(host);
            val value = Func.applyOrElse(valueFunction, host, v, fallbackValue);
            return operator.apply(v, value);
        });
    }
    
    public default NUMACCESS toZero() {
        return __operate(this::newAccess, __mathOperators()::zero);
    }
    
    public default NUMACCESS toOne() {
        return __operate(this::newAccess, __mathOperators()::one);
    }
    
    public default NUMACCESS toMinusOne() {
        return __operate(this::newAccess, __mathOperators()::minusOne);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS plus(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::add);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS plus(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::add);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS plus(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::add);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS plus(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::add);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS less(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::subtract);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS less(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::subtract);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS less(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::subtract);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS less(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::subtract);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS time(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::multiply);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS time(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::multiply);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS time(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::multiply);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS time(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::multiply);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS dividedBy(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::divide);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS dividedBy(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::divide);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS dividedBy(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::divide);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS dividedBy(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::divide);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainderBy(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::remainder);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainderBy(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::remainder);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainderBy(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::remainder);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainderBy(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::remainder);
    }
    
    // public BigDecimal[] divideAndRemainder(BigDecimal divisor); --  Tuple
    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::pow);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::pow);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::pow);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::pow);
    }
    
    public default NUMACCESS abs() {
        return newAccess(host -> {
            if (host == null)
                return __mathOperators().zero();
            val v = apply(host);
            return __mathOperators().abs(v);
        });
    }
    
    public default NUMACCESS negate() {
        return newAccess(host -> {
            if (host == null)
                return __mathOperators().zero();
            val v = apply(host);
            return __mathOperators().negate(v);
        });
    }
    
    public default NUMACCESS signum() {
        return newAccess(host -> {
            if (host == null)
                return __mathOperators().zero();
            val v = apply(host);
            return __mathOperators().signum(v);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS min(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::min);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS min(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::min);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS min(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::min);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS min(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::min);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS max(TYPE value) {
        return __operate(value, (NUMACCESS) this, this::newAccess, __mathOperators()::max);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS max(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS) this, this::newAccess, __mathOperators()::max);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS max(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::max);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS max(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS) this, this::newAccess, __mathOperators()::max);
    }
}
