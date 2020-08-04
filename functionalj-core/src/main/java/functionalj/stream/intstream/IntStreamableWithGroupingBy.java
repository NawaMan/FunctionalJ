package functionalj.stream.intstream;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.IntCollectorPlus;
import functionalj.stream.IntStreamProcessor;
import lombok.val;

public interface IntStreamableWithGroupingBy
    extends IntStreamableWithMapToTuple {
    
    /** Group the elements by determining the grouping keys */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <KEY> FuncMap<KEY, IntStreamable> groupingBy(IntFunction<KEY> keyMapper) {
        // TODO - Avoid using boxed.
        Supplier  <Map<KEY, ArrayList<Integer>>>                               supplier;
        BiConsumer<Map<KEY, ArrayList<Integer>>, Integer>                      accumulator;
        BiConsumer<Map<KEY, ArrayList<Integer>>, Map<KEY, ArrayList<Integer>>> combiner;
        
        Supplier<ArrayList<Integer>> collectorSupplier = ArrayList::new;
        Function<ArrayList<Integer>, IntStreamable> toStreamable 
                = array -> (IntStreamable)(()->IntStreamPlus.from(array.stream().mapToInt(Integer::intValue)));
        
        supplier = LinkedHashMap::new;
        accumulator = (map, each) -> {
            val key = keyMapper.apply(each);
            map.compute(key, (k, a)->{
                if (a == null) {
                    a = collectorSupplier.get();
                }
                a.add(each);
                return a;
            });
        };
        combiner = (map1, map2) -> map1.putAll(map2);
        val theMap = intStream().boxed().collect(supplier, accumulator, combiner);
        return ImmutableMap
                    .from    (theMap)
                    .mapValue(toStreamable);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<KEY>                       keyMapper,
            Function<? super IntStreamable, VALUE> aggregate) {
        return groupingBy(keyMapper)
                .mapValue(aggregate);
    }

    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            IntFunction<KEY>          keyMapper,
            IntStreamProcessor<VALUE> processor) {
        FuncMap<KEY, IntStreamable> groupingBy = groupingBy(keyMapper);
//        return (FuncMap<KEY, VALUE>) groupingBy
//                .mapValue(stream -> stream.calculate((IntStreamProcessor) processor));
        return null;
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            IntFunction<? extends KEY>                     keyMapper,
            Supplier<IntCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
//        return groupingBy(keyMapper)
//                .mapValue(stream -> (VALUE)stream.collect((IntCollectorPlus)collectorSupplier.get()));
        return null;
    }
    
}