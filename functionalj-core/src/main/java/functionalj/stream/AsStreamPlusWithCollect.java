package functionalj.stream;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsStreamPlusWithCollect<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    
    @Eager
    @Terminal
    public default <RESULT> RESULT collect(
            Supplier<RESULT>                 supplier,
            BiConsumer<RESULT, ? super DATA> accumulator,
            BiConsumer<RESULT, RESULT>       combiner) {
        val streamPlus = streamPlus();
        return streamPlus
                .collect(supplier, accumulator, combiner);
    }
    
    @Eager
    @Terminal
    public default <RESULT, ACCUMULATOR> RESULT collect(Collector<? super DATA, ACCUMULATOR, RESULT> collector) {
        val streamPlus = streamPlus();
        return streamPlus
                .collect(collector);
    }
}
