package functionalj.function.aggregator.aggregations;

import java.math.BigDecimal;
import java.math.BigInteger;

import functionalj.function.Operators;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToDouble;
import functionalj.function.aggregator.AggregationToInt;
import functionalj.function.aggregator.AggregationToLong;
import functionalj.function.aggregator.DoubleReduceAggregationToDouble;
import functionalj.function.aggregator.IntReduceAggregationToInt;
import functionalj.function.aggregator.LongReduceAggregationToLong;
import functionalj.lens.lenses.BigDecimalAccess;
import functionalj.lens.lenses.BigIntegerAccess;
import functionalj.lens.lenses.DoubleAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.LongAccess;

/**
 * Sum aggregation.
 **/
public class Sum {
    
    /** Sum of two numbers (ints). */
    public static final IntReduceAggregationToInt ofInts = Operators.sumInts;
    
    /** Sum of two numbers (longs). */
    public static final LongReduceAggregationToLong ofLongs = Operators.sumLongs;
    
    /** Sum of two numbers (doubles). */
    public static final DoubleReduceAggregationToDouble ofDoubles = Operators.sumDoubles;
    
    /**
     * Create a sum aggregation for integers.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to an integer to be sum.
     * @return        the integer aggregation.
     */
    public static <T> AggregationToInt<T> of(IntegerAccess<T> mapper) {
        return Operators.sumInts.of(mapper);
    }
    
    /**
     * Create a sum aggregation for longs.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a long to be sum.
     * @return        the long aggregation.
     */
    public static <T> AggregationToLong<T> of(LongAccess<T> mapper) {
        return Operators.sumLongs.of(mapper);
    }
    
    /**
     * Create a sum aggregation for doubles.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a double to be sum.
     * @return        the double aggregation.
     */
    public static <T> AggregationToDouble<T> of(DoubleAccess<T> mapper) {
        return Operators.sumDoubles.of(mapper);
    }
    
    /**
     * Create a sum aggregation for big integers.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a big integer to be sum.
     * @return        the big integer aggregation.
     */
    public static <T> Aggregation<T, BigInteger> of(BigIntegerAccess<T> mapper) {
        return Operators.sumBigInt.of(mapper);
    }
    
    /**
     * Create a sum aggregation for big decimals.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a big decimal to be sum.
     * @return        the big decimal aggregation.
     */
    public static <T> Aggregation<T, BigDecimal> of(BigDecimalAccess<T> mapper) {
        return Operators.sumBigDecimal.of(mapper);
    }
    
}
