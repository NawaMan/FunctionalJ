package functionalj.stream;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import lombok.val;

public interface StreamPlusWithGroupingBy<DATA> extends AsStreamPlus<DATA> {
    
    // Eager
    public default <KEY> FuncMap<KEY, StreamPlus<DATA>> groupingBy(
            Function<? super DATA, ? extends KEY> keyMapper) {
        Supplier  <Map<KEY, ArrayList<DATA>>>                            supplier;
        BiConsumer<Map<KEY, ArrayList<DATA>>, ? super DATA>              accumulator;
        BiConsumer<Map<KEY, ArrayList<DATA>>, Map<KEY, ArrayList<DATA>>> combiner;
        
        Supplier<ArrayList<DATA>>                   collectorSupplier = ArrayList::new;
        Function<ArrayList<DATA>, StreamPlus<DATA>> toStreamPlus      = array -> StreamPlus.from(array.stream());
        
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
        
        val streamPlus = streamPlus();
        val theMap = streamPlus.collect(supplier, accumulator, combiner);
        return ImmutableMap
                .from    (theMap)
                .mapValue(toStreamPlus);
    }
    
    // Eager
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY> keyMapper,
            Function<StreamPlus<DATA>, VALUE>     aggregate) {
        val groupingBy = groupingBy(keyMapper);
        val mapValue = groupingBy.mapValue(aggregate);
        return (FuncMap<KEY, VALUE>)mapValue;
    }
    
    // Eager
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY> keyMapper,
            StreamProcessor<? super DATA, VALUE>  processor) {
        Function<StreamPlus<DATA>, VALUE> aggregate = (StreamPlus<DATA> stream) -> {
            return (VALUE)processor.process((StreamPlus)stream);
        };
        
        return groupingBy(keyMapper, aggregate);
    }
    
}
