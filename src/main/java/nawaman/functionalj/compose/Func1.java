package nawaman.functionalj.compose;

import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface Func1<I, R> extends Function<I, R> {

    public static <I,R> Func1<I, R> of(Func1<I, R> f3) {
        return f3;
    }
    
    public default Supplier<R> curry(I i) {
        return ()->this.apply(i);
    }
}
