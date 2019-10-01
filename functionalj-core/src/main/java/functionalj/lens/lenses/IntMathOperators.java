package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;

import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;


public class IntMathOperators implements MathOperators<Integer> {
    
    public static MathOperators<Integer> instance = new IntMathOperators();
    
    @Override
    public Integer zero() {
        return 0;
    }
    @Override
    public Integer one() {
        return 1;
    }
    @Override
    public Integer minusOne() {
        return -1;
    }
    @Override
    public Integer toInteger(Integer number) {
        return (number == null) ? 0 : number.intValue();
    }
    @Override
    public Long toLong(Integer number) {
        return (long)toInteger(number);
    }
    @Override
    public Double toDouble(Integer number) {
        return (double)toInteger(number);
    }
    @Override
    public BigInteger toBigInteger(Integer number) {
        return BigInteger.valueOf(toInteger(number));
    }
    @Override
    public BigDecimal toBigDecimal(Integer number) {
        return new BigDecimal(toInteger(number));
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
    
}