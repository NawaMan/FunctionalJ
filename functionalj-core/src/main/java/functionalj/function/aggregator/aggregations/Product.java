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
 * Product aggregation.
 **/
public class Product {
    
    /** Sum of two numbers (ints). */
    public static final IntReduceAggregationToInt ofInts = Operators.productInts;
    
    /** Sum of two numbers (longs). */
    public static final LongReduceAggregationToLong ofLongs = Operators.productLongs;
    
    /** Sum of two numbers (doubles). */
    public static final DoubleReduceAggregationToDouble ofDoubles = Operators.productDoubles;
    
    /**
     * Create a product aggregation for integers.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to an integer.
     * @return        the integer aggregation.
     */
    public static <T> AggregationToInt<T> of(IntegerAccess<T> mapper) {
        return Operators.productInts.of(mapper);
    }
    
    /**
     * Create a product aggregation for longs.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a long.
     * @return        the long aggregation.
     */
    public static <T> AggregationToLong<T> of(LongAccess<T> mapper) {
        return Operators.productLongs.of(mapper);
    }
    
    /**
     * Create a product aggregation for doubles.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a double.
     * @return        the double aggregation.
     */
    public static <T> AggregationToDouble<T> of(DoubleAccess<T> mapper) {
        return Operators.productDoubles.of(mapper);
    }
    
    /**
     * Create a product aggregation for big integers.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a big integer.
     * @return        the big integer aggregation.
     */
    public static <T> Aggregation<T, BigInteger> of(BigIntegerAccess<T> mapper) {
        return Operators.productBigInt.of(mapper);
    }
    
    /**
     * Create a product aggregation for big decimals.
     * 
     * @param <T>     the source type.
     * @param mapper  the mapper from the source type to a big decimal.
     * @return        the big decimal aggregation.
     */
    public static <T> Aggregation<T, BigDecimal> of(BigDecimalAccess<T> mapper) {
        return Operators.productBigDecimal.of(mapper);
    }
    
}
