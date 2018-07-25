package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import nawaman.nullablej.nullable.Nullable;
import tuple.Tuple;
import tuple.Tuple2;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface LongAccess<HOST> 
        extends 
            NumberAccess<HOST, Long, LongAccess<HOST>>, 
            ToLongFunction<HOST>,
            ConcreteAccess<HOST, Long, LongAccess<HOST>> {

    @Override
    public default LongAccess<HOST> newAccess(Function<HOST, Long> accessToValue) {
        return accessToValue::apply;
    }
    
    public default long applyAsLong(HOST host) {
        return apply(host).longValue();
    }
    
    
    public default MathOperators<Long> __mathOperators() {
        return __LongMathOperators;
    }
    
    public static MathOperators<Long> __LongMathOperators = new MathOperators<Long>() {

        @Override
        public Long zero() {
            return 0L;
        }
        @Override
        public Integer toInt(Long number) {
            return toLong(number).intValue();
        }
        @Override
        public Long toLong(Long number) {
            return Nullable.of(number).orElse(0L);
        }
        @Override
        public Double toDouble(Long number) {
            return (double)toLong(number);
        }
        @Override
        public BigInteger toBigInteger(Long number) {
            return BigInteger.valueOf(toLong(number));
        }
        @Override
        public BigDecimal toBigDecimal(Long number) {
            return new BigDecimal(toLong(number));
        }
        
        @Override
        public Long add(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return v1 + v2;
        }
        @Override
        public Long subtract(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return v1 - v2;
        }
        @Override
        public Long multiply(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return v1 * v2;
        }
        @Override
        public Long divide(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return v1 / v2;
        }
        @Override
        public Long remainder(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return v1 % v2;
        }

        @Override
        public Tuple2<Long, Long> divideAndRemainder(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return Tuple.of(v1 / v2, v1 % v2);
        }

        @Override
        public Long pow(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return (long)Math.pow(v1, v2);
        }

        @Override
        public Long abs(Long number) {
            long v = (number == null) ? 0 : number.longValue();
            return Math.abs(v);
        }
        @Override
        public Long negate(Long number) {
            long v = (number == null) ? 0 : number.longValue();
            return -1 * v;
        }
        @Override
        public Long signum(Long number) {
            long v = (number == null) ? 0 : number.longValue();
            return (long)Math.signum(v);
        }

        @Override
        public Long min(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return Math.min(v1, v2);
        }
        @Override
        public Long max(Long number1, Long number2) {
            long v1 = (number1 == null) ? 0 : number1.longValue();
            long v2 = (number2 == null) ? 0 : number2.longValue();
            return Math.max(v1, v2);
        }
        
    };
}
