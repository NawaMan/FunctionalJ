package functionalj.kinds;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.val;

public interface Peekable<SELF, DATA> {
    
    public SELF peek(Consumer<? super DATA> consumer);
    
    public default <T extends DATA> SELF peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return peek(value -> {
            if (!clzz.isInstance(value))
            	return;
            
            val target = clzz.cast(value);
            theConsumer.accept(target);
        });
    }
    public default SELF peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return peek(value -> {
            if (!selector.test(value))
            	return;
            
            theConsumer.accept(value);
        });
    }
    public default <T> SELF peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            theConsumer.accept(target);
        });
    }
    
    public default <T> SELF peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
}
