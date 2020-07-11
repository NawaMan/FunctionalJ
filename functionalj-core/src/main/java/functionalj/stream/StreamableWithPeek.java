package functionalj.stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface StreamableWithPeek<DATA> {

    
    public <TARGET> Streamable<TARGET> deriveWith(
            Function<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public default <T extends DATA> Streamable<DATA> peek(
            Class<T>            clzz, 
            Consumer<? super T> theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peek(clzz, theConsumer);
        });
    }
    public default Streamable<DATA> peek(
            Predicate<? super DATA> selector, 
            Consumer<? super DATA>  theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peekBy(selector, theConsumer);
        });
    }
    public default <T> Streamable<DATA> peek(
            Function<? super DATA, T> mapper, 
            Consumer<? super T>       theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peekAs(mapper, theConsumer);
        });
    }
    
    public default <T> Streamable<DATA> peek(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      selector, 
            Consumer<? super T>       theConsumer) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .peekAs(mapper, selector, theConsumer);
        });
    }
}
