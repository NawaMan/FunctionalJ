package functionalj.lens.lenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import nullablej.nullable.Nullable;

public class LongMathOperators implements MathOperators<Long> {

    public static MathOperators<Long> instance = new LongMathOperators();

    @Override
    public Long zero() {
        return 0L;
    }

    @Override
    public Long one() {
        return 1L;
    }

    @Override
    public Long minusOne() {
        return -1L;
    }

    @Override
    public Integer asInteger(Long number) {
        return asLong(number).intValue();
    }

    @Override
    public Long asLong(Long number) {
        return Nullable.of(number).orElse(0L);
    }

    @Override
    public Double asDouble(Long number) {
        return (double) asLong(number);
    }

    @Override
    public BigInteger asBigInteger(Long number) {
        return BigInteger.valueOf(asLong(number));
    }

    @Override
    public BigDecimal asBigDecimal(Long number) {
        return new BigDecimal(asLong(number));
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
        return (long) Math.pow(v1, v2);
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
        return (long) Math.signum(v);
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
}
