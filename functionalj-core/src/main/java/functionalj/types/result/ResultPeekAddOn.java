package functionalj.types.result;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func1;
import functionalj.functions.FuncUnit1;
import lombok.val;

@SuppressWarnings("javadoc")
public interface ResultPeekAddOn<DATA> {
    
    public Result<DATA> peek(Consumer<? super DATA> consumer);
    
    public default <T extends DATA> Result<DATA> peek(Class<T> clzz, FuncUnit1<? super T> theConsumer) {
        return peek(value -> {
            if (!clzz.isInstance(value))
                return;
            
            val target = clzz.cast(value);
            theConsumer.accept(target);
        });
    }
    public default Result<DATA> peek(Predicate<? super DATA> selector, FuncUnit1<? super DATA> theConsumer) {
        return peek(value -> {
            if (!selector.test(value))
                return;
            
            theConsumer.accept(value);
        });
    }
    public default <T> Result<DATA> peek(Func1<? super DATA, T> mapper, FuncUnit1<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            theConsumer.accept(target);
        });
    }
    
    public default <T> Result<DATA> peek(Func1<? super DATA, T> mapper, Predicate<? super T> selector, FuncUnit1<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
}
