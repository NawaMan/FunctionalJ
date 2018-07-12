package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface BigDecimalAccess<HOST> 
                    extends 
                        NumberAccess<HOST, BigDecimal, BigDecimalAccess<HOST>>, 
                        ConcreteAccess<HOST, BigDecimal, BigDecimalAccess<HOST>> {

    @Override
    public default BigDecimalAccess<HOST> newAccess(Function<HOST, BigDecimal> access) {
        return access::apply;
    }
    
    
    public default MathOperators<BigDecimal> __mathOperators() {
        return __BigDecimalMathOperators;
    }
    
    public static MathOperators<BigDecimal> __BigDecimalMathOperators = new MathOperators<BigDecimal>() {

        @Override
        public BigDecimal zero() {
            return BigDecimal.ZERO;
        }
        @Override
        public Integer toInt(BigDecimal number) {
            return toBigDecimal(number).intValue();
        }
        @Override
        public Long toLong(BigDecimal number) {
            return toBigDecimal(number).longValue();
        }
        @Override
        public Double toDouble(BigDecimal number) {
            return toBigDecimal(number).doubleValue();
        }
        @Override
        public BigInteger toBigInteger(BigDecimal number) {
            return toBigDecimal(number).toBigInteger();
        }
        @Override
        public BigDecimal toBigDecimal(BigDecimal number) {
            return Nullable.of(number).orElse(BigDecimal.ZERO);
        }
        
        @Override
        public BigDecimal add(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.add(v2);
        }
        @Override
        public BigDecimal subtract(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.subtract(v2);
        }
        @Override
        public BigDecimal multiply(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.multiply(v2);
        }
        @Override
        public BigDecimal divide(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.divide(v2);
        }
        @Override
        public BigDecimal remainder(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.remainder(v2);
        }
        
    };
    
    // TODO - add, subscript ...
    
//    public BigDecimal add(BigDecimal augend);
//    public BigDecimal subtract(BigDecimal subtrahend);
//    public BigDecimal multiply(BigDecimal multiplicand);
//    public BigDecimal divide(BigDecimal divisor);
//    public BigDecimal remainder(BigDecimal divisor);
//    public BigDecimal[] divideAndRemainder(BigDecimal divisor); --  Tuple
//    public BigDecimal pow(BigDecimal n);
//    public BigDecimal abs();
//    public BigDecimal negate();
//    public BigDecimal plus();
//    public BigDecimal signum();
//    public BigDecimal min(BigDecimal val);
//    public BigDecimal max(BigDecimal val);
//    public BigDecimal toBigDecimal();
    
}
