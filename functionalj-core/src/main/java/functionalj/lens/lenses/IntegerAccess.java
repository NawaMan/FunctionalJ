package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import tuple.Tuple;
import tuple.Tuple2;

/**
 * @author manusitn
 *
 * @param <HOST>
 */
@FunctionalInterface
public interface IntegerAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Integer, IntegerAccess<HOST>>,
                        ToIntFunction<HOST>,
                        ConcreteAccess<HOST, Integer, IntegerAccess<HOST>> {
    
    @Override
    public default IntegerAccess<HOST> newAccess(Function<HOST, Integer> accessToValue) {
        return accessToValue::apply;
    }
    
    public default int applyAsInt(HOST host) {
        return apply(host).intValue();
    }
    
    public default MathOperators<Integer> __mathOperators() {
        return __IntMathOperators;
    }
    
    public static MathOperators<Integer> __IntMathOperators = new MathOperators<Integer>() {

        @Override
        public Integer zero() {
            return 0;
        }
        @Override
        public Integer toInt(Integer number) {
            return (number == null) ? 0 : number.intValue();
        }
        @Override
        public Long toLong(Integer number) {
            return (long)toInt(number);
        }
        @Override
        public Double toDouble(Integer number) {
            return (double)toInt(number);
        }
        @Override
        public BigInteger toBigInteger(Integer number) {
            return BigInteger.valueOf(toInt(number));
        }
        @Override
        public BigDecimal toBigDecimal(Integer number) {
            return new BigDecimal(toInt(number));
        }
        
        @Override
        public Integer add(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return v1 + v2;
        }
        @Override
        public Integer subtract(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return v1 - v2;
        }
        @Override
        public Integer multiply(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return v1 * v2;
        }
        @Override
        public Integer divide(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return v1 / v2;
        }
        @Override
        public Integer remainder(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return v1 % v2;
        }

        @Override
        public Tuple2<Integer, Integer> divideAndRemainder(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return Tuple.of(v1 / v2, v1 % v2);
        }

        @Override
        public Integer pow(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return (int)Math.pow(v1, v2);
        }

        @Override
        public Integer abs(Integer number) {
            int v = (number == null) ? 0 : number.intValue();
            return Math.abs(v);
        }
        @Override
        public Integer negate(Integer number) {
            int v = (number == null) ? 0 : number.intValue();
            return Math.negateExact(v);
        }
        @Override
        public Integer signum(Integer number) {
            int v = (number == null) ? 0 : number.intValue();
            return (int)Math.signum(v);
        }

        @Override
        public Integer min(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return Math.min(v1, v2);
        }
        @Override
        public Integer max(Integer number1, Integer number2) {
            int v1 = (number1 == null) ? 0 : number1.intValue();
            int v2 = (number2 == null) ? 0 : number2.intValue();
            return Math.max(v1, v2);
        }

    };
    
}
