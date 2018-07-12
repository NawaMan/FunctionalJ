package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface BigIntegerAccess<HOST> 
        extends 
            NumberAccess<HOST, BigInteger, BigIntegerAccess<HOST>>, 
            ConcreteAccess<HOST, BigInteger, BigIntegerAccess<HOST>> {
    
    @Override
    public default BigIntegerAccess<HOST> newAccess(Function<HOST, BigInteger> access) {
        return access::apply;
    }
    
    
    public default MathOperators<BigInteger> __mathOperators() {
        return __BigIntegerMathOperators;
    }
    
    public static MathOperators<BigInteger> __BigIntegerMathOperators = new MathOperators<BigInteger>() {

        @Override
        public BigInteger zero() {
            return BigInteger.ZERO;
        }
        @Override
        public Integer toInt(BigInteger number) {
            return toBigInteger(number).intValue();
        }
        @Override
        public Long toLong(BigInteger number) {
            return toBigInteger(number).longValue();
        }
        @Override
        public Double toDouble(BigInteger number) {
            return toBigInteger(number).doubleValue();
        }
        @Override
        public BigInteger toBigInteger(BigInteger number) {
            return Nullable.of(number).orElse(BigInteger.ZERO);
        }
        @Override
        public BigDecimal toBigDecimal(BigInteger number) {
            return new BigDecimal(toBigInteger(number));
        }
        
        @Override
        public BigInteger add(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.add(v2);
        }
        @Override
        public BigInteger subtract(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.subtract(v2);
        }
        @Override
        public BigInteger multiply(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.multiply(v2);
        }
        @Override
        public BigInteger divide(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.divide(v2);
        }
        @Override
        public BigInteger remainder(BigInteger number1, BigInteger number2) {
            BigInteger v1 = (number1 == null) ? BigInteger.ZERO : number1;
            BigInteger v2 = (number2 == null) ? BigInteger.ZERO : number2;
            return v1.remainder(v2);
        }
        
    };
    
}
