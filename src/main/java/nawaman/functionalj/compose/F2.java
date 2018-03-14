package nawaman.functionalj.compose;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface F2<I1, I2, R> extends BiFunction<I1, I2, R> {
    
    public default BiFunction<I1, I2, R> curry() {
        return this;
    }
    public default Function<I2, R> curry(I1 i1) {
        return i2 -> this.apply(i1, i2);
    }
    public default Function<I1, R> curry2(I2 i2) {
        return i1 -> this.apply(i1, i2);
    }
    public default F2<I2, I1, R> flip() {
        return (i2, i1) -> this.apply(i1, i2);
    }
    
}