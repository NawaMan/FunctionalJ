package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface DoubleAccess<HOST> 
        extends 
            NumberAccess<HOST, Double, DoubleAccess<HOST>>, 
            ToDoubleFunction<HOST>, 
            ConcreteAccess<HOST, Double, DoubleAccess<HOST>> {
    
    @Override
    public default DoubleAccess<HOST> newAccess(Function<HOST, Double> access) {
        return access::apply;
    }
    
    public default double applyAsDouble(HOST host) {
        return apply(host).doubleValue();
    }

    
    public default MathOperators<Double> __mathOperators() {
        return __DoubleMathOperators;
    }
    
    
    public static MathOperators<Double> __DoubleMathOperators = new MathOperators<Double>() {

        @Override
        public Double zero() {
            return 0.0;
        }
        @Override
        public Integer toInt(Double number) {
            return toDouble(number).intValue();
        }
        @Override
        public Long toLong(Double number) {
            return toDouble(number).longValue();
        }
        @Override
        public Double toDouble(Double number) {
            return Nullable.of(number).orElse(0.0);
        }
        @Override
        public BigInteger toBigInteger(Double number) {
            return BigInteger.valueOf(toLong(number));
        }
        @Override
        public BigDecimal toBigDecimal(Double number) {
            return new BigDecimal(toDouble(number));
        }
        
        @Override
        public Double add(Double number1, Double number2) {
            double v1 = (number1 == null) ? 0 : number1.doubleValue();
            double v2 = (number2 == null) ? 0 : number2.doubleValue();
            return v1 + v2;
        }
        @Override
        public Double subtract(Double number1, Double number2) {
            double v1 = (number1 == null) ? 0 : number1.doubleValue();
            double v2 = (number2 == null) ? 0 : number2.doubleValue();
            return v1 - v2;
        }
        @Override
        public Double multiply(Double number1, Double number2) {
            double v1 = (number1 == null) ? 0 : number1.doubleValue();
            double v2 = (number2 == null) ? 0 : number2.doubleValue();
            return v1 * v2;
        }
        @Override
        public Double divide(Double number1, Double number2) {
            double v1 = (number1 == null) ? 0 : number1.doubleValue();
            double v2 = (number2 == null) ? 0 : number2.doubleValue();
            return v1 / v2;
        }
        @Override
        public Double remainder(Double number1, Double number2) {
            double v1 = (number1 == null) ? 0 : number1.intValue();
            double v2 = (number2 == null) ? 0 : number2.intValue();
            return v1 % v2;
        }
        
    };
    
}
