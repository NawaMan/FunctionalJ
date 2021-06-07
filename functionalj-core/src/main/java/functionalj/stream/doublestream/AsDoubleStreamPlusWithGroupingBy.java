package functionalj.stream.doublestream;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.aggregator.DoubleAggregation;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableFuncMap;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import lombok.val;

public interface AsDoubleStreamPlusWithGroupingBy {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /** Group the elements by determining the grouping keys */
    public default <KEY> FuncMap<KEY, DoubleFuncList> groupingBy(DoubleFunction<KEY> keyMapper) {
        Supplier  <Map<KEY, GrowOnlyDoubleArray>>                                 supplier;
        BiConsumer<Map<KEY, GrowOnlyDoubleArray>, Double>                         accumulator;
        BiConsumer<Map<KEY, GrowOnlyDoubleArray>, Map<KEY, GrowOnlyDoubleArray>>  combiner;
        
        Supplier<GrowOnlyDoubleArray>                 collectorSupplier = GrowOnlyDoubleArray::new;
        Function<GrowOnlyDoubleArray, DoubleFuncList> toFuncList         = array -> array.toFuncList();
        
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
        val theMap = doubleStreamPlus().boxed().collect(supplier, accumulator, combiner);
        return ImmutableFuncMap
                    .from    (theMap)
                    .mapValue(toFuncList);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            DoubleFunction<KEY>             keyMapper,
            Function<DoubleFuncList, VALUE> aggregate) {
        FuncMap<KEY, DoubleFuncList> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy.mapValue((Function)aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, ACCUMULATED, VALUE> FuncMap<KEY, VALUE> groupingBy(
            DoubleFunction<KEY>                               keyMapper,
            Supplier<DoubleCollectorPlus<ACCUMULATED, VALUE>> collectorSupplier) {
        FuncMap<KEY, DoubleFuncList>    groupingBy = groupingBy(keyMapper);
        Function<DoubleFuncList, VALUE> aggregate  = stream -> stream.collect(collectorSupplier.get());
        FuncMap<KEY, VALUE> mapValue = groupingBy.mapValue((Function)aggregate);
        return (FuncMap<KEY, VALUE>) mapValue;
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            DoubleFunction<KEY>      keyMapper,
            DoubleAggregation<VALUE> aggregation) {
        FuncMap<KEY, DoubleFuncList> groupingBy = groupingBy(keyMapper);
        return (FuncMap<KEY, VALUE>) groupingBy.mapValue(stream -> stream.calculate(aggregation));
    }
    
}
