package functionalj.list;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.map.FuncMap;
import functionalj.stream.AsStreamable;
import functionalj.stream.StreamProcessor;
import functionalj.streamable.Streamable;

public interface FuncListWithGroupingBy<DATA>
    extends AsStreamable<DATA> {
    
    /** Group the elements by determining the grouping keys */
    public default <KEY> FuncMap<KEY, FuncList<? super DATA>> groupingBy(Function<? super DATA, KEY> keyMapper) {
        return streamable()
                .groupingBy(keyMapper)
                .mapValue(Streamable::toFuncList);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, KEY>                       keyMapper,
            Function<? super FuncList<? super DATA>, VALUE> aggregate) {
        return groupingBy(keyMapper)
                .mapValue(aggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, KEY>          keyMapper,
            StreamProcessor<? super DATA, VALUE> processor) {
        return groupingBy(keyMapper, processor);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, ACCUMULATED, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY>                 keyMapper,
            Supplier<Collector<? super DATA, ACCUMULATED, VALUE>> collectorSupplier) {
        return groupingBy(keyMapper, collectorSupplier);
    }
    
}
