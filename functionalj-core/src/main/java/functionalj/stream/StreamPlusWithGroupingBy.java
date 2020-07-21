package functionalj.stream;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.map.FuncMap;
import functionalj.stream.makers.Eager;
import functionalj.stream.makers.Terminal;

public interface StreamPlusWithGroupingBy<DATA> extends AsStreamPlus<DATA> {
    
    /** Group the elements by determining the grouping keys */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, StreamPlus<? super DATA>> groupingBy(Function<? super DATA, KEY> keyMapper) {
        Streamable<DATA> streamable = () -> streamPlus();
        return streamable
                .groupingBy(keyMapper)
                .mapValue(Streamable::streamPlus);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, KEY>                       keyMapper,
            Function<? super StreamPlus<? super DATA>, VALUE> aggregate) {
        Streamable<DATA>                                  streamable     = () -> streamPlus();
        Function<? super Streamable<? super DATA>, VALUE> valueAggregate = valueStreamable -> aggregate.apply(valueStreamable.stream());
        return streamable
                .groupingBy(keyMapper, valueAggregate);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, KEY> keyMapper,
            StreamProcessor<? super DATA, VALUE>  processor) {
        Streamable<DATA> streamable = () -> streamPlus();
        return streamable
                .groupingBy(keyMapper, processor::process);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    public default <KEY, ACCUMULATED, VALUE> FuncMap<? extends KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY>                 keyMapper,
            Supplier<Collector<? super DATA, ACCUMULATED, VALUE>> collectorSupplier) {
        Streamable<DATA> streamable = () -> streamPlus();
        return streamable
                .groupingBy(keyMapper, collectorSupplier);
    }
    
}
