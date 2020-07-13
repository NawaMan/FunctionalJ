package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.function.Consumer;

public interface StreamableWithPeek<DATA> extends AsStreamable<DATA> {
    
    public default <T extends DATA> Streamable<DATA> peek(
            Class<T>            clzz, 
            Consumer<? super T> theConsumer) {
        return deriveFrom(this, stream -> stream.peek(clzz, theConsumer));
    }
}
