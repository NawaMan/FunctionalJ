// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.stream;

import static functionalj.function.Func.alwaysTrue;
import static functionalj.stream.StreamPlusHelper.dummy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToBoolean;
import functionalj.result.Result;
import functionalj.stream.collect.CollectorPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface AsStreamPlusWithStatistic<DATA> {

    public StreamPlus<DATA> streamPlus();

    /**
     * @return  the size of the stream
     */
    @Eager
    @Terminal
    public default int size() {
        val streamPlus = streamPlus();
        return (int) streamPlus.count();
    }

    @Eager
    @Terminal
    public default long count() {
        val streamPlus = streamPlus();
        return streamPlus.count();
    }

    public default Optional<DATA> min(Comparator<? super DATA> comparator) {
        val streamPlus = streamPlus();
        return streamPlus.min(comparator);
    }

    public default Optional<DATA> max(Comparator<? super DATA> comparator) {
        val streamPlus = streamPlus();
        return streamPlus.max(comparator);
    }

    /**
     * Return the value whose mapped value is the smallest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DATA> minBy(Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus.min((a, b) -> {
            val mappedA = mapper.apply(a);
            val mappedB = mapper.apply(b);
            return mappedA.compareTo(mappedB);
        });
    }

    /**
     * Return the value whose mapped value is the smallest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DATA> minBy(Aggregation<DATA, D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minBy(mapper);
    }

    /**
     * Return the value whose mapped value is the biggest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DATA> maxBy(Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus.max((a, b) -> {
            val mappedA = mapper.apply(a);
            val mappedB = mapper.apply(b);
            return mappedA.compareTo(mappedB);
        });
    }

    /**
     * Return the value whose mapped value is the biggest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<DATA> maxBy(Aggregation<DATA, D> aggregation) {
        val mapper = aggregation.newAggregator();
        return maxBy(mapper);
    }

    /**
     * Return the value whose mapped value is the smallest using the comparator.
     */
    @Eager
    @Terminal
    public default <D> Optional<DATA> minBy(Function<DATA, D> mapper, Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        return streamPlus.min((a, b) -> {
            val mappedA = mapper.apply(a);
            val mappedB = mapper.apply(b);
            return comparator.compare(mappedA, mappedB);
        });
    }

    /**
     * Return the value whose mapped value is the smallest using the comparator.
     */
    @Eager
    @Terminal
    public default <D> Optional<DATA> minBy(Aggregation<DATA, D> aggregation, Comparator<? super D> comparator) {
        val mapper = aggregation.newAggregator();
        return minBy(mapper, comparator);
    }

    /**
     * Return the value whose mapped value is the biggest using the comparator.
     */
    @Eager
    @Terminal
    public default <D> Optional<DATA> maxBy(Function<DATA, D> mapper, Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        return streamPlus.max((a, b) -> {
            val mappedA = mapper.apply(a);
            val mappedB = mapper.apply(b);
            return comparator.compare(mappedA, mappedB);
        });
    }

    /**
     * Return the value whose mapped value is the biggest using the comparator.
     */
    @Eager
    @Terminal
    public default <D> Optional<DATA> maxBy(Aggregation<DATA, D> aggregation, Comparator<? super D> comparator) {
        val mapper = aggregation.newAggregator();
        return maxBy(mapper, comparator);
    }

    /**
     * Return the value is the smallest and the biggest using the comparator.
     */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default Optional<Tuple2<DATA, DATA>> minMax(Comparator<? super DATA> comparator) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(dummy);
        val maxRef = new AtomicReference<Object>(dummy);
        streamPlus.sorted(comparator).forEach(each -> {
            minRef.compareAndSet(StreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(Tuple2.of((DATA) min, (DATA) max));
    }

    /**
     * Return the value whose mapped value is the smallest and the biggest.
     */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default <D extends Comparable<D>> Optional<Tuple2<DATA, DATA>> minMaxBy(Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(dummy);
        val maxRef = new AtomicReference<Object>(dummy);
        streamPlus.sortedBy(mapper).forEach(each -> {
            minRef.compareAndSet(dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(Tuple2.of((DATA) min, (DATA) max));
    }

    /**
     * Return the value whose mapped value is the smallest and the biggest.
     */
    @Eager
    @Terminal
    public default <D extends Comparable<D>> Optional<Tuple2<DATA, DATA>> minMaxBy(Aggregation<DATA, D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minMaxBy(mapper);
    }

    /**
     * Return the value whose mapped value is the smallest and the biggest using the comparator.
     */
    @Eager
    @Terminal
    @SuppressWarnings("unchecked")
    public default <D> Optional<Tuple2<DATA, DATA>> minMaxBy(Function<DATA, D> mapper, Comparator<? super D> comparator) {
        val streamPlus = streamPlus();
        val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
        streamPlus.sortedBy(mapper, (i1, i2) -> comparator.compare(i1, i2)).forEach(each -> {
            minRef.compareAndSet(StreamPlusHelper.dummy, each);
            maxRef.set(each);
        });
        val min = minRef.get();
        val max = maxRef.get();
        return (dummy.equals(min) || dummy.equals(max)) ? Optional.empty() : Optional.of(Tuple2.of((DATA) min, (DATA) max));
    }
    
    /**
     * Return the value whose mapped value is the smallest and the biggest using the comparator.
     */
    @Eager
    @Terminal
    public default <D> Optional<Tuple2<DATA, DATA>> minMaxBy(Aggregation<DATA, D> aggregation, Comparator<? super D> comparator) {
        val mapper = aggregation.newAggregator();
        return minMaxBy(mapper, comparator);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Function<DATA, D> mapper) {
        return minIndexBy(alwaysTrue(), mapper);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Aggregation<DATA, D> aggregation) {
        val mapper = aggregation.newAggregator();
        return minIndexBy(alwaysTrue(), mapper);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Function<DATA, D> mapper) {
        return maxIndexBy(alwaysTrue(), mapper);
    }
    
    /**
     * Map each value using the mapper to a comparable value and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Aggregation<DATA, D> aggregation) {
        val mapper = aggregation.newAggregator();
        return maxIndexBy(alwaysTrue(), mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Predicate<DATA> filter, Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus.mapWithIndex(Tuple::of).filter(t -> filter.test(t._2)).minBy(t -> mapper.apply(t._2)).map(t -> t._1);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(AggregationToBoolean<DATA> aggregationFilter, Function<DATA, D> mapper) {
        val filter = aggregationFilter.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Predicate<DATA> filter, Aggregation<DATA, D> aggregationMapper) {
        val mapper = aggregationMapper.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(AggregationToBoolean<DATA> aggregationFilter, Aggregation<DATA, D> aggregationMapper) {
        val filter = aggregationFilter.newAggregator();
        val mapper = aggregationMapper.newAggregator();
        return minIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a maximum value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Predicate<DATA> filter, Function<DATA, D> mapper) {
        val streamPlus = streamPlus();
        return streamPlus.mapWithIndex(Tuple::of).filter(t -> filter.test(t._2)).maxBy(t -> mapper.apply(t._2)).map(t -> t._1);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(AggregationToBoolean<DATA> aggregationFilter, Function<DATA, D> mapper) {
        val filter = aggregationFilter.newAggregator();
        return maxIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Predicate<DATA> filter, Aggregation<DATA, D> aggregationMapper) {
        val mapper = aggregationMapper.newAggregator();
        return maxIndexBy(filter, mapper);
    }
    
    /**
     * Using the mapper to map each value that passes the filter to a comparable and use it to find a minimal value then return the index
     */
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(AggregationToBoolean<DATA> aggregationFilter, Aggregation<DATA, D> aggregationMapper) {
        val filter = aggregationFilter.newAggregator();
        val mapper = aggregationMapper.newAggregator();
        return maxIndexBy(filter, mapper);
    }
    
    // == Sum ==
    /**
     * Map each element to int and return the sum.
     *
     * @param mapperToInt  the mapper to int.
     * @return             the sum of all the int value.
     */
    public default int sumToInt(ToIntFunction<DATA> mapperToInt) {
        return streamPlus().mapToInt(mapperToInt).sum();
    }
    
    /**
     * Map each element to long and return the sum.
     *
     * @param mapperToLong  the mapper to long.
     * @return              the sum of all the long value.
     */
    public default long sumToLong(ToLongFunction<DATA> mapperToLong) {
        return streamPlus().mapToLong(mapperToLong).sum();
    }
    
    /**
     * Map each element to double and return the sum.
     *
     * @param mapperToDouble  the mapper to double.
     * @return                the sum of all the double value.
     */
    public default double sumToDouble(ToDoubleFunction<DATA> mapperToDouble) {
        return streamPlus().mapToDouble(mapperToDouble).sum();
    }
    
    /**
     * Map each element to BigInteger and return the sum.
     *
     * @param mapperToBigInteger  the mapper to BigInteger.
     * @return                    the sum of all the BigInteger value.
     */
    public default BigInteger sumToBigInteger(Function<DATA, BigInteger> mapperToBigInteger) {
        return streamPlus().map(mapperToBigInteger).reduce(BigInteger::add).orElse(BigInteger.ZERO);
    }
    
    /**
     * Map each element to BigDecimal and return the sum.
     *
     * @param mapperToBigDecimal  the mapper to BigDecimal.
     * @return                    the sum of all the BigDecimal value.
     */
    public default BigDecimal sumToBigDecimal(Function<DATA, BigDecimal> mapperToBigDecimal) {
        return streamPlus().map(mapperToBigDecimal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
    
    // TODO - Add the primitive ones
    /**
     * Map each element to BigDecimal and return the sum.
     *
     * @param mapperToBigDecimal  the mapper to BigDecimal.
     * @return                    the sum of all the BigDecimal value.
     */
    public default Result<BigDecimal> sum(Function<DATA, BigDecimal> mapperToBigDecimal) {
        val sum = streamPlus().map(mapperToBigDecimal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return Result.valueOf(sum);
    }
    
    // == Average ==
    /**
     * Map each element to int and return the average.
     *
     * @param mapperToInt  the mapper to int.
     * @return             the sum of all the int value.
     */
    public default OptionalDouble average(ToIntFunction<DATA> mapperToInt) {
        return streamPlus().mapToInt(mapperToInt).average();
    }
    
    /**
     * Map each element to long and return the average.
     *
     * @param mapperToLong  the mapper to long.
     * @return              the sum of all the long value.
     */
    public default OptionalDouble average(ToLongFunction<DATA> mapperToLong) {
        return streamPlus().mapToLong(mapperToLong).average();
    }
    
    /**
     * Map each element to double and return the average.
     *
     * @param mapperToDouble  the mapper to double.
     * @return                the sum of all the double value.
     */
    public default OptionalDouble average(ToDoubleFunction<DATA> mapperToDouble) {
        return streamPlus().mapToDouble(mapperToDouble).average();
    }
    
    /**
     * Map each element to BigDecimal and return the average.
     *
     * @param mapperToBigDecimal  the mapper to BigDecimal.
     * @return                    the sum of all the BigDecimal value.
     */
    public default Result<BigDecimal> average(Function<DATA, BigDecimal> mapperToBigDecimal) {
        class RunningData {
            
            BigDecimal sum = BigDecimal.ZERO;
            
            int count = 0;
        }
        val characteristics = EnumSet.of(Characteristics.CONCURRENT, Characteristics.UNORDERED);
        val collector = new Collector<DATA, RunningData, Result<BigDecimal>>() {
            
            @Override
            public Supplier<RunningData> supplier() {
                return () -> new RunningData();
            }
            
            @Override
            public BiConsumer<RunningData, DATA> accumulator() {
                return (running, data) -> {
                    val value = mapperToBigDecimal.apply(data);
                    running.sum = running.sum.add(value);
                    running.count++;
                };
            }
            
            @Override
            public BinaryOperator<RunningData> combiner() {
                return (running1, running2) -> {
                    val running = new RunningData();
                    running.sum = running1.sum.add(running1.sum);
                    running.count = 1;
                    return running;
                };
            }

            @Override
            public Function<RunningData, Result<BigDecimal>> finisher() {
                return running -> {
                    int count = running.count;
                    return (count == 0) ? Result.ofNull() : Result.valueOf(running.sum.divide(BigDecimal.valueOf(count)));
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return characteristics;
            }
        };
        val aggregation = Aggregation.from(CollectorPlus.from(collector));
        return streamPlus().aggregate(aggregation);
    }
}
