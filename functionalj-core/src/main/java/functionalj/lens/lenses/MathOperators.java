package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;

import functionalj.tuple.Tuple2;

public interface MathOperators<NUMBER> {
    
    public NUMBER zero();
    public NUMBER one();
    public NUMBER minusOne();
    
    public Integer    toInteger(NUMBER number);
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