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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.lens.core.AccessCreator;
import functionalj.tuple.Tuple2;
import lombok.val;

@SuppressWarnings("javadoc")
public interface NumberAccess<HOST, TYPE extends Comparable<TYPE>, NUMACCESS extends NumberAccess<HOST, TYPE, ?>> 
        extends ComparableAccess<HOST, TYPE> {
    
    public default IntegerAccess<HOST> compareTo(TYPE anotherValue) {
        return intAccess(Integer.MIN_VALUE, any -> any.compareTo(anotherValue));
    }
    public default BooleanAccess<HOST> thatGreaterThan(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    public default BooleanAccess<HOST> thatIsZero() {
        return (BooleanAccess<HOST>)(host -> {
            val value = apply(host);
            val zero  = toZero().apply(host);
            return value.compareTo(zero) == 0;
        });
    }
    public default BooleanAccess<HOST> thatIsNotZero() {
        return (BooleanAccess<HOST>)(host -> {
            val value = apply(host);
            val zero  = toZero().apply(host);
            return value.compareTo(zero) != 0;
        });
    }
    public default BooleanAccess<HOST> thatIsPositive() {
        return (BooleanAccess<HOST>)(host -> {
            val value = apply(host);
            val zero  = toZero().apply(host);
            return value.compareTo(zero) > 0;
        });
    }
    public default BooleanAccess<HOST> thatIsNegative() {
        return (BooleanAccess<HOST>)(host -> {
            val value = apply(host);
            val zero  = toZero().apply(host);
            return value.compareTo(zero) < 0;
        });
    }
    public default BooleanAccess<HOST> thatIsNotPositive() {
        return (BooleanAccess<HOST>)(host -> {
            val value = apply(host);
            val zero  = toZero().apply(host);
            return value.compareTo(zero) <= 0;
        });
    }
    public default BooleanAccess<HOST> thatIsNotNegative() {
        return (BooleanAccess<HOST>)(host -> {
            val value = apply(host);
            val zero  = toZero().apply(host);
            return value.compareTo(zero) >= 0;
        });
    }
    
    public NUMACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
    public MathOperators<TYPE> __mathOperators();
    
    
    public static interface MathOperators<NUMBER> {
        
        public NUMBER zero();
        public NUMBER one();
        public NUMBER minusOne();
        
        public Integer    toInt(NUMBER number);
        public Long       toLong(NUMBER number);
        public Double     toDouble(NUMBER number);
        public BigInteger toBigInteger(NUMBER number);
        public BigDecimal toBigDecimal(NUMBER number);
        
        public NUMBER add(NUMBER number1, NUMBER number2);
        public NUMBER subtract(NUMBER number1, NUMBER number2);
        public NUMBER multiply(NUMBER number1, NUMBER number2);
        public NUMBER divide(NUMBER number1, NUMBER number2);
        public NUMBER remainder(NUMBER number1, NUMBER number2);
        public Tuple2<NUMBER, NUMBER> divideAndRemainder(NUMBER number, NUMBER divisor);
        
        public NUMBER pow(NUMBER number, NUMBER n);
        public NUMBER abs(NUMBER number);
        public NUMBER negate(NUMBER number);
        public NUMBER signum(NUMBER number);
        public NUMBER min(NUMBER number1, NUMBER number2);
        public NUMBER max(NUMBER number1, NUMBER number2);
        
    }
    
    public default IntegerAccess<HOST> toInt() {
        return intAccess(0, __mathOperators()::toInt);
    }
    public default LongAccess<HOST> toLong() {
        return longAccess(0L, __mathOperators()::toLong);
    }
    public default DoubleAccess<HOST> toDouble() {
        return doubleAccess(0.0, __mathOperators()::toDouble);
    }
    public default BigIntegerAccess<HOST> toBigInteger() {
        return bigIntegerAccess(BigInteger.ZERO, __mathOperators()::toBigInteger);
    }
    public default BigDecimalAccess<HOST> toBigDecimal() {
        return bigDecimalAccess(BigDecimal.ZERO, __mathOperators()::toBigDecimal);
    }
    
    public default NUMACCESS __operate(
            AccessCreator<HOST, TYPE, NUMACCESS> accessCreator,  
            Supplier<TYPE>                       operator) {
        return accessCreator.newAccess(host -> {
            return operator.get();
        });
    }
    public default NUMACCESS __operate(
            TYPE                                 value, 
            NUMACCESS                            orgAccess,
            AccessCreator<HOST, TYPE, NUMACCESS> accessCreator,  
            BiFunction<TYPE, TYPE, TYPE>         operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return value;
            val v = orgAccess.apply(host); 
            return operator.apply(v, value);
        });
    }
    public default NUMACCESS __operate(
            TYPE                                 fallbackValue,
            Supplier<TYPE>                       valueSupplier, 
            NUMACCESS                            orgAccess,
            AccessCreator<HOST, TYPE, NUMACCESS> accessCreator,  
            BiFunction<TYPE, TYPE, TYPE>         operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return Func.getOrElse(valueSupplier, fallbackValue);
            
            val v     = orgAccess.apply(host); 
            val value = Func.getOrElse(valueSupplier, fallbackValue);
            return operator.apply(v, value);
        });
    }
    public default NUMACCESS __operate(
            TYPE                                 fallbackValue,
            Function<TYPE, TYPE>                 valueFunction, 
            NUMACCESS                            orgAccess,
            AccessCreator<HOST, TYPE, NUMACCESS> accessCreator,  
            BiFunction<TYPE, TYPE, TYPE>         operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return Func.applyOrElse(valueFunction, fallbackValue, fallbackValue);
            
            val v     = orgAccess.apply(host); 
            val value = Func.applyOrElse(valueFunction, v, fallbackValue);
            return operator.apply(v, value);
        });
    }
    public default NUMACCESS __operate(
            TYPE                                 fallbackValue,
            BiFunction<HOST, TYPE, TYPE>         valueFunction, 
            NUMACCESS                            orgAccess,
            AccessCreator<HOST, TYPE, NUMACCESS> accessCreator,  
            BiFunction<TYPE, TYPE, TYPE>         operator) {
        return accessCreator.newAccess(host -> {
            if (host == null)
                return Func.applyOrElse(valueFunction, host, fallbackValue, fallbackValue);
            
            val v     = orgAccess.apply(host); 
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
    public default NUMACCESS add(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::add);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS add(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::add);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS add(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::add);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS add(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::add);
    }
    

    @SuppressWarnings("unchecked")
    public default NUMACCESS subtract(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::subtract);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS subtract(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::subtract);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS subtract(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::subtract);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS subtract(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::subtract);
    }
    
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS multiply(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::multiply);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS multiply(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::multiply);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS multiply(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::multiply);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS multiply(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::multiply);
    }
    

    @SuppressWarnings("unchecked")
    public default NUMACCESS divide(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS divide(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS divide(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS divide(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainder(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainder(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainder(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS remainder(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    
    @SuppressWarnings("unchecked")
    public default NUMACCESS mod(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS mod(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS mod(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS mod(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    
    
//  public BigDecimal[] divideAndRemainder(BigDecimal divisor); --  Tuple

    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::pow);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::pow);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::pow);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS pow(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::pow);
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
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::min);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS min(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::min);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS min(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::min);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS min(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::min);
    }

    @SuppressWarnings("unchecked")
    public default NUMACCESS max(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::max);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS max(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::max);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS max(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::max);
    }
    @SuppressWarnings("unchecked")
    public default NUMACCESS max(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::max);
    }
        
}
