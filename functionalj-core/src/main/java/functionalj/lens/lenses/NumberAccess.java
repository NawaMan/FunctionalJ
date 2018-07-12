package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.lens.core.AccessCreator;
import lombok.val;

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
    public default BooleanAccess<HOST> thatEqualsZero(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) == 0);
    }
    
    public NUMACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
    public MathOperators<TYPE> __mathOperators();
    
    
    public static interface MathOperators<NUMBER> {
        
        public NUMBER zero();
        
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
        
    }
    
    
    public default IntegerAccess toInt() {
        return intAccess(0, __mathOperators()::toInt);
    }
    public default LongAccess toLong() {
        return longAccess(0L, __mathOperators()::toLong);
    }
    public default DoubleAccess toDouble() {
        return doubleAccess(0.0, __mathOperators()::toDouble);
    }
    public default BigIntegerAccess toBigInteger() {
        return bigIntegerAccess(BigInteger.ZERO, __mathOperators()::toBigInteger);
    }
    public default BigDecimalAccess toBigDecimal() {
        return bigDecimalAccess(BigDecimal.ZERO, __mathOperators()::toBigDecimal);
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
                return Func0.getOrElse(valueSupplier, fallbackValue);
            
            val v     = orgAccess.apply(host); 
            val value = Func0.getOrElse(valueSupplier, fallbackValue);
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
                return Func1.applyOrElse(valueFunction, fallbackValue, fallbackValue);
            
            val v     = orgAccess.apply(host); 
            val value = Func1.applyOrElse(valueFunction, v, fallbackValue);
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
                return Func2.applyOrElse(valueFunction, host, fallbackValue, fallbackValue);
            
            val v     = orgAccess.apply(host); 
            val value = Func2.applyOrElse(valueFunction, host, v, fallbackValue);
            return operator.apply(v, value);
        });
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
    
    
    public default NUMACCESS divide(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    public default NUMACCESS divide(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    public default NUMACCESS divide(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    public default NUMACCESS divide(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::divide);
    }
    
    
    public default NUMACCESS remainder(TYPE value) {
        return __operate(value, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    public default NUMACCESS remainder(Supplier<TYPE> valueSupplier) {
        return __operate(__mathOperators().zero(), valueSupplier, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    public default NUMACCESS remainder(Function<TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    public default NUMACCESS remainder(BiFunction<HOST, TYPE, TYPE> valueFunction) {
        return __operate(__mathOperators().zero(), valueFunction, (NUMACCESS)this, this::newAccess, __mathOperators()::remainder);
    }
    
    
//  public BigDecimal[] divideAndRemainder(BigDecimal divisor); --  Tuple
//  public BigDecimal pow(BigDecimal n);
//  public BigDecimal abs();
//  public BigDecimal negate();
//  public BigDecimal plus();
//  public BigDecimal signum();
//  public BigDecimal min(BigDecimal val);
//  public BigDecimal max(BigDecimal val);
//  public BigDecimal toBigDecimal();
    
}
