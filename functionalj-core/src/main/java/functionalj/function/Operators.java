package functionalj.function;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.UnaryOperator;

import functionalj.function.aggregator.DoubleReduceAggregationToDouble;
import functionalj.function.aggregator.IntReduceAggregationToInt;
import functionalj.function.aggregator.LongReduceAggregationToLong;
import functionalj.function.aggregator.ReduceAggregation;

/**
 * This class contain named integer operator.
 */
public final class Operators {
    
    //== Int ==
    
    /** Negation (-a). */
    public static final UnaryOperator<Integer> negateInt = a -> -a;
    
    /** Increment (a + 1). */
    public static final UnaryOperator<Integer> incrementInt = a -> a + 1;
    
    /** Decrement (a - 1). */
    public static final UnaryOperator<Integer> decrementInt = a -> a - 1;
    
    /** Absolute value (|a|). */
    public static final UnaryOperator<Integer> absoluteInt = Math::abs;
    
    /** Square (a^2). */
    public static final UnaryOperator<Integer> squareInt = a -> a * a;
    
    /** Cube (a^3). */
    public static final UnaryOperator<Integer> cubeInt = a -> a * a * a;
    
    /** Bitwise complement (~a). */
    public static final UnaryOperator<Integer> bitwiseComplementInt = a -> ~a;
    
    /** Sum of two numbers (ints). */
    public static final IntReduceAggregationToInt sumInts = new IntReduceAggregationToInt(0, (a, b) -> a + b);
    
    /** Difference of two numbers (a - b). */
    public static final IntBinaryOperator differenceOfInts = (a, b) -> a - b;
    
    /** Product of two numbers (a * b). */
    public static final IntReduceAggregationToInt productInts = new IntReduceAggregationToInt(1, (a, b) -> a * b);
    
    /** Integer division (a / b). */
    public static final IntBinaryOperator divideInts = (a, b) -> a / b;
    
    /** Remainder of two numbers (a % b). */
    public static final IntBinaryOperator divisionRemainderOfInts = (a, b) -> a % b;
    
    /** Maximum of two numbers. */
    public static final IntReduceAggregationToInt maxInts = new IntReduceAggregationToInt(Integer.MIN_VALUE, Integer::max);
    
    /** Minimum of two numbers. */
    public static final IntReduceAggregationToInt minInts = new IntReduceAggregationToInt(Integer.MAX_VALUE, Integer::min);
    
    /** Bitwise AND of two numbers. */
    public static final IntReduceAggregationToInt andInts = new IntReduceAggregationToInt(-1, (a, b) -> a & b);
    
    /** Bitwise OR of two numbers. */
    public static final IntReduceAggregationToInt orInts = new IntReduceAggregationToInt(0, (a, b) -> a | b);
    
    /** Bitwise XOR of two numbers. */
    public static final IntReduceAggregationToInt xorInts = new IntReduceAggregationToInt(0, (a, b) -> a ^ b);
    
    /** Left shift (a << b). */
    public static final IntBinaryOperator leftShiftInts = (a, b) -> a << b;
    
    /** Right shift (a >> b). */
    public static final IntBinaryOperator rightShiftInts = (a, b) -> a >> b;
    
    /** Unsigned right shift (a >>> b). */
    public static final IntBinaryOperator unsignedRightShiftInts = (a, b) -> a >>> b;
    
    /** Absolute difference of two numbers. */
    public static final IntBinaryOperator distanceOfInts = (a, b) -> Math.abs(a - b);
    
    /** GCD of two numbers (non-negative integers). */
    public static final IntBinaryOperator gcdInts = (a, b) -> {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    };
    
    /** Logical comparison: returns 1 if a > b, -1 if a < b, 0 if equal. */
    public static final IntBinaryOperator compareInts = (a, b) -> Integer.compare(a, b);
    
    /** Power operation (a^b). */
    public static final IntBinaryOperator powerInts = (a, b) -> {
        if (b < 0) throw new ArithmeticException("Negative exponent not allowed for integers");
        return (int) Math.pow(a, b);
    };
    
    //== Long ==
    
    /** Negation (-a). */
    public static final UnaryOperator<Long> negateLong = a -> -a;
    
    /** Increment (a + 1). */
    public static final UnaryOperator<Long> incrementLong = a -> a + 1L;
    
    /** Decrement (a - 1). */
    public static final UnaryOperator<Long> decrementLong = a -> a - 1L;
    
    /** Absolute value (|a|). */
    public static final UnaryOperator<Long> absoluteLong = Math::abs;
    
    /** Square (a^2). */
    public static final UnaryOperator<Long> squareLong = a -> a * a;
    
    /** Cube (a^3). */
    public static final UnaryOperator<Long> cubeLong = a -> a * a * a;
    
    /** Bitwise complement (~a). */
    public static final UnaryOperator<Long> bitwiseComplementLong = a -> ~a;
    
    /** Sum of two numbers (longs). */
    public static final LongReduceAggregationToLong sumLongs = new LongReduceAggregationToLong(0L, (a, b) -> a + b);
    
    /** Difference of two numbers (a - b). */
    public static final LongBinaryOperator differenceOfLongs = (a, b) -> a - b;
    
    /** Product of two numbers (a * b). */
    public static final LongReduceAggregationToLong productLongs = new LongReduceAggregationToLong(1L, (a, b) -> a * b);
    
    /** Long division (a / b). */
    public static final LongBinaryOperator divideLongs = (a, b) -> a / b;
    
    /** Remainder of two numbers (a % b). */
    public static final LongBinaryOperator divisionRemainderOfLongs = (a, b) -> a % b;
    
    /** Maximum of two numbers. */
    public static final LongReduceAggregationToLong maxLongs = new LongReduceAggregationToLong(Long.MIN_VALUE, Long::max);
    
    /** Minimum of two numbers. */
    public static final LongReduceAggregationToLong minLongs = new LongReduceAggregationToLong(Long.MAX_VALUE, Long::min);
    
    /** Bitwise AND of two numbers. */
    public static final LongReduceAggregationToLong andLongs = new LongReduceAggregationToLong(-1L, (a, b) -> a & b);
    
    /** Bitwise OR of two numbers. */
    public static final LongReduceAggregationToLong orLongs = new LongReduceAggregationToLong(0L, (a, b) -> a | b);
    
    /** Bitwise XOR of two numbers. */
    public static final LongReduceAggregationToLong xorLongs = new LongReduceAggregationToLong(0L, (a, b) -> a ^ b);
    
    /** Left shift (a << b). */
    public static final LongBinaryOperator leftShiftLongs = (a, b) -> a << b;
    
    /** Right shift (a >> b). */
    public static final LongBinaryOperator rightShiftLongs = (a, b) -> a >> b;
    
    /** Unsigned right shift (a >>> b). */
    public static final LongBinaryOperator unsignedRightShiftLongs = (a, b) -> a >>> b;
    
    /** Absolute difference of two numbers. */
    public static final LongBinaryOperator distanceOfLongs = (a, b) -> Math.abs(a - b);
    
    /** GCD of two numbers (non-negative longs). */
    public static final LongBinaryOperator gcdLongs = ((a, b) -> {
        while (b != 0L) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    });
    
    /** Logical comparison: returns 1 if a > b, -1 if a < b, 0 if equal. */
    public static final LongBinaryOperator compareLongs = (a, b) -> Long.compare(a, b);
    
    /** Power operation (a^b). */
    public static final LongBinaryOperator powerLongs = (a, b) -> {
        if (b < 0L) throw new ArithmeticException("Negative exponent not allowed for longs");
        long result = 1L;
        for (long i = 0L; i < b; i++) {
            result *= a;
        }
        return result;
    };
    
    //== Double ==
    
    /** Negation (-a). */
    public static final UnaryOperator<Double> negateDouble = a -> -a;
    
    /** Absolute value (|a|). */
    public static final UnaryOperator<Double> absoluteDouble = Math::abs;
    
    /** Square (a^2). */
    public static final UnaryOperator<Double> squareDouble = a -> a * a;
    
    /** Cube (a^3). */
    public static final UnaryOperator<Double> cubeDouble = a -> a * a * a;
    
    /** Square root (√a). */
    public static final UnaryOperator<Double> squareRoot = Math::sqrt;
    
    /** Reciprocal (1/a). */
    public static final UnaryOperator<Double> reciprocal = a -> {
        if (a == 0.0) throw new ArithmeticException("Division by zero");
        return 1.0 / a;
    };
    
    /** Natural logarithm (ln(a)). */
    public static final UnaryOperator<Double> naturalLog = Math::log;
    
    /** Exponential (e^a). */
    public static final UnaryOperator<Double> exponential = Math::exp;
    
    /** Rounding to nearest integer. */
    public static final UnaryOperator<Double> round = Math::rint;
    
    /** Sum of two numbers (doubles). */
    public static final DoubleReduceAggregationToDouble sumDoubles = new DoubleReduceAggregationToDouble(0, (a, b) -> a + b);
    
    /** Difference of two numbers (a - b). */
    public static final DoubleBinaryOperator differenceOfDoubles = (a, b) -> a - b;
    
    /** Product of two numbers (a * b). */
    public static final DoubleReduceAggregationToDouble productDoubles = new DoubleReduceAggregationToDouble(1.0, (a, b) -> a * b);
    
    /** Division of two numbers (a / b). */
    public static final DoubleBinaryOperator divideDoubles = (a, b) -> a / b;
    
    /** Remainder of two numbers (a % b). */
    public static final DoubleBinaryOperator divisionRemainderOfDoubles = (a, b) -> a % b;
    
    /** Maximum of two numbers. */
    public static final DoubleReduceAggregationToDouble maxDoubles = new DoubleReduceAggregationToDouble(Double.NEGATIVE_INFINITY, Double::max);
    
    /** Minimum of two numbers. */
    public static final DoubleReduceAggregationToDouble minDoubles = new DoubleReduceAggregationToDouble(Double.POSITIVE_INFINITY, Double::min);
    
    /** Absolute difference of two numbers. */
    public static final DoubleBinaryOperator distanceOfDoubles = (a, b) -> Math.abs(a - b);
    
    /** Power operation (a^b). */
    public static final DoubleBinaryOperator powerDoubles = Math::pow;
    
    /** Logical comparison: returns 1.0 if a > b, -1.0 if a < b, 0.0 if equal. */
    public static final DoubleBinaryOperator compareDoubles = (a, b) -> Double.compare(a, b);
    
    //== BigInteger ==  
    
    /** Negation (-a). */
    public static final UnaryOperator<BigInteger> negateBigInt = BigInteger::negate;
    
    /** Absolute value (|a|). */
    public static final UnaryOperator<BigInteger> absoluteBigInt = BigInteger::abs;
    
    /** Increment (a + 1). */
    public static final UnaryOperator<BigInteger> incrementBigInt = a -> a.add(BigInteger.ONE);
    
    /** Decrement (a - 1). */
    public static final UnaryOperator<BigInteger> decrementBigInt = a -> a.subtract(BigInteger.ONE);
    
    /** Square (a^2). */
    public static final UnaryOperator<BigInteger> squareBigInt = a -> a.multiply(a);
    
    /** Cube (a^3). */
    public static final UnaryOperator<BigInteger> cubeBigInt = a -> a.multiply(a).multiply(a);
    
    /** Sum of two numbers (a + b). */
    public static final ReduceAggregation<BigInteger> sumBigInt = new ReduceAggregation<>(BigInteger.ZERO, BigInteger::add);
    
    /** Difference of two numbers (a - b). */
    public static final BinaryOperator<BigInteger> differenceBigInt = BigInteger::subtract;
    
    /** Product of two numbers (a * b). */
    public static final ReduceAggregation<BigInteger> productBigInt = new ReduceAggregation<>(BigInteger.ONE, BigInteger::multiply);
    
    /** Division of two numbers (a / b). */
    public static final BinaryOperator<BigInteger> divisionBigInt = (a, b) -> a.divide(b);
    
    /** Modulus (a % b). */
    public static final BinaryOperator<BigInteger> modulusBigInt = (a, b) -> a.mod(b);
    
    /** GCD (greatest common divisor). */
    public static final BinaryOperator<BigInteger> gcdBigInt = BigInteger::gcd;
    
    /** Power operation (a^b). */
    public static final BinaryOperator<BigInteger> powerBigInt = (a, b) -> a.pow(b.intValue());
    
    //== BigDecimal ==
    
    /** Negation (-a). */
    public static final UnaryOperator<BigDecimal> negateBigDecimal = BigDecimal::negate;
    
    /** Absolute value (|a|). */
    public static final UnaryOperator<BigDecimal> absoluteBigDecimal = BigDecimal::abs;
    
    /** Increment (a + 1). */
    public static final UnaryOperator<BigDecimal> incrementBigDecimal = a -> a.add(BigDecimal.ONE);
    
    /** Decrement (a - 1). */
    public static final UnaryOperator<BigDecimal> decrementBigDecimal = a -> a.subtract(BigDecimal.ONE);
    
    /** Square (a^2). */
    public static final UnaryOperator<BigDecimal> squareBigDecimal = a -> a.multiply(a);
    
    /** Square root (√a) using Newton's method for precision. */
    public static final UnaryOperator<BigDecimal> squareRootBigDecimal = a -> {
        if (a.compareTo(BigDecimal.ZERO) < 0) throw new ArithmeticException("Square root of negative number");
        BigDecimal x = a.divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_EVEN);
        BigDecimal lastX;
        do {
            lastX = x;
            x = x.add(a.divide(x, BigDecimal.ROUND_HALF_EVEN)).divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_EVEN);
        } while (!x.equals(lastX));
        return x;
    };
    
    /** Sum of two numbers (a + b). */
    public static final ReduceAggregation<BigDecimal> sumBigDecimal = new ReduceAggregation<>(BigDecimal.ZERO, BigDecimal::add);
    
    /** Difference of two numbers (a - b). */
    public static final BinaryOperator<BigDecimal> differenceBigDecimal = BigDecimal::subtract;
    
    /** Product of two numbers (a * b). */
    public static final ReduceAggregation<BigDecimal> productBigDecimal = new ReduceAggregation<>(BigDecimal.ONE, BigDecimal::multiply);
    
    /** Division of two numbers (a / b). */
    public static final BinaryOperator<BigDecimal> divisionBigDecimal = (a, b) -> {
        if (b.equals(BigDecimal.ZERO)) throw new ArithmeticException("Division by zero");
        return a.divide(b, BigDecimal.ROUND_HALF_EVEN);
    };
    
    /** Modulus (a % b). */
    public static final BinaryOperator<BigDecimal> modulusBigDecimal = (a, b) -> {
        if (b.equals(BigDecimal.ZERO)) throw new ArithmeticException("Division by zero");
        return a.remainder(b);
    };
    
    //== String ==
    
    /** Convert the string to uppercase. */
    public static final UnaryOperator<String> toUpperCase = String::toUpperCase;
    
    /** Convert the string to lowercase. */
    public static final UnaryOperator<String> toLowerCase = String::toLowerCase;
    
    /** Trim leading and trailing spaces. */
    public static final UnaryOperator<String> trim = String::trim;
    
    /** Concatenate two strings. */
    public static final ReduceAggregation<String> concat = new ReduceAggregation<>("", (a, b) -> a + b);
    
    /** Join two strings with a delimiter. */
    public static final ReduceAggregation<String> joinWithComma = new ReduceAggregation<>((String)null, (a, b) -> (a == null) ? b : (a + "," + b));
    
    /** Join two strings with a delimiter. */
    public static final ReduceAggregation<String> joinWithSpace = new ReduceAggregation<>((String)null, (a, b) -> (a == null) ? b : (a + " " + b));
    
    /** Join two strings with a delimiter. */
    public static final ReduceAggregation<String> joinWithTab = new ReduceAggregation<>((String)null, (a, b) -> (a == null) ? b : (a + "\t" + b));
    
    /** Join two strings with a delimiter. */
    public static final ReduceAggregation<String> joinWithNewLine = new ReduceAggregation<>((String)null, (a, b) -> (a == null) ? b : (a + System.lineSeparator() + b));
    
    /** Check if two strings are equal (case-sensitive). */
    public static final BinaryOperator<String> equals = (a, b) -> String.valueOf(a.equals(b));
    
    /** Check if two strings are equal (case-insensitive). */
    public static final BinaryOperator<String> equalsIgnoreCase = (a, b) -> String.valueOf(a.equalsIgnoreCase(b));
    
}
