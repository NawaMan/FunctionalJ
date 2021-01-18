package functionalj.stream.intstream;

import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsIntStreamPlusWithCollect {
    
    public IntStreamPlus intStreamPlus();
    
    
    /**
     * Performs a mutable reduction operation on the elements of this stream. A mutable reduction is one in which the reduced value is
     * a mutable result container, such as an {@code ArrayList}, and elements are incorporated by updating the state of the result rather
     * than by replacing the result.
     **/
    @Eager
    @Terminal
    public default <RESULT> RESULT collect(
            Supplier<RESULT>           supplier,
            ObjIntConsumer<RESULT>     accumulator,
            BiConsumer<RESULT, RESULT> combiner) {
        val streamPlus = intStreamPlus();
        return streamPlus
                .collect(supplier, accumulator, combiner);
    }
    
    @Eager
    @Terminal
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <RESULT> RESULT collect(IntCollectorPlus<?, RESULT> collector) {
        Supplier<RESULT>       supplier    = (Supplier)      collector.supplier();
        ObjIntConsumer<RESULT> accumulator = (ObjIntConsumer)collector.accumulator();
        BiConsumer<RESULT, RESULT>  combiner    = (BiConsumer)    collector.combiner();
        val streamPlus = intStreamPlus();
        return streamPlus
                .collect(supplier, accumulator, combiner);
    }
    
}
