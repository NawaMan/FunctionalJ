package functionalj.stream.doublestream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
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
    public default <ACCUMULATOR, RESULT> RESULT collect(DoubleCollectorPlus<ACCUMULATOR, RESULT> collector) {
        val supplier = (Supplier)collector.supplier();
        val combiner = Func.f((ACCUMULATOR r1, ACCUMULATOR r2) -> {
            BinaryOperator simpleCombiner = collector.combiner();
            simpleCombiner.apply(r1, r2);
        });
        ObjDoubleConsumer<ACCUMULATOR> accumulator = (ACCUMULATOR r, double v) -> {
            // This is ridiculous but work. Sorry.
            Object      objectR           = (Object)r;
            ACCUMULATOR resultR           = (ACCUMULATOR)objectR;
            BiConsumer  simpleAccumulator = collector.accumulator();
            simpleAccumulator.accept(resultR, v);
        };
        val finisher    = collector.finisher();
        val streamPlus  = doubleStreamPlus();
        val accumulated = streamPlus.collect(supplier, accumulator, combiner);
        val result      = finisher.apply((ACCUMULATOR)accumulated);
        return result;
    }
    
}
