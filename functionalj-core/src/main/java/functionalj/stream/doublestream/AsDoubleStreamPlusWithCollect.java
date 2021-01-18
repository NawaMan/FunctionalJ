package functionalj.stream.doublestream;

import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsDoubleStreamPlusWithCollect {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    
    /**
     * Performs a mutable reduction operation on the elements of this stream. A mutable reduction is one in which the reduced value is
     * a mutable result container, such as an {@code ArrayList}, and elements are incorporated by updating the state of the result rather
     * than by replacing the result.
     **/
    @Eager
    @Terminal
    public default <RESULT> RESULT collect(
            Supplier<RESULT>           supplier,
            ObjDoubleConsumer<RESULT>  accumulator,
            BiConsumer<RESULT, RESULT> combiner) {
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .collect(supplier, accumulator, combiner);
    }
    
    @Eager
    @Terminal
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <RESULT> RESULT collect(DoubleCollectorPlus<?, RESULT> collector) {
        Supplier<RESULT>           supplier    = (Supplier)         collector.supplier();
        ObjDoubleConsumer<RESULT>  accumulator = (ObjDoubleConsumer)collector.accumulator();
        BiConsumer<RESULT, RESULT> combiner    = (BiConsumer)       collector.combiner();
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .collect(supplier, accumulator, combiner);
    }
    
}
