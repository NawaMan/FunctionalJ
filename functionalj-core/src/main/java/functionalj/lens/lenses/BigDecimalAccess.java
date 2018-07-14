package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

import functionalj.types.ImmutableTuple;
import functionalj.types.Tuple2;
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

        @Override
        public Tuple2<BigDecimal, BigDecimal> divideAndRemainder(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            BigDecimal[] rs = v1.divideAndRemainder(v2);
            return ImmutableTuple.of(rs[0], rs[1]);
        }

        @Override
        public BigDecimal pow(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.pow(v2.intValue());
        }

        @Override
        public BigDecimal abs(BigDecimal number) {
            BigDecimal v = (number == null) ? BigDecimal.ZERO : number;
            return v.abs();
        }
        @Override
        public BigDecimal negate(BigDecimal number) {
            BigDecimal v = (number == null) ? BigDecimal.ZERO : number;
            return v.negate();
        }
        @Override
        public BigDecimal signum(BigDecimal number) {
            BigDecimal v = (number == null) ? BigDecimal.ZERO : number;
            return BigDecimal.valueOf(v.signum());
        }

        @Override
        public BigDecimal min(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.min(v2);
        }
        @Override
        public BigDecimal max(BigDecimal number1, BigDecimal number2) {
            BigDecimal v1 = (number1 == null) ? BigDecimal.ZERO : number1;
            BigDecimal v2 = (number2 == null) ? BigDecimal.ZERO : number2;
            return v1.max(v2);
        }
        
    };
    
}
