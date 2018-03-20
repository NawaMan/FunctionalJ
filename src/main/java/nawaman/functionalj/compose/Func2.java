package nawaman.functionalj.compose;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Func2<I1, I2, R> extends BiFunction<I1, I2, R> {
    
    public default Func1<I1, Func1<I2, R>> curry() {
        return i1 -> i2 -> this.apply(i1, i2);
    }
    
    public default Function<I2, R> curry1(I1 i1) {
        return i2 -> this.apply(i1, i2);
    }
    public default Function<I1, R> curry2(I2 i2) {
        return i1 -> this.apply(i1, i2);
    }
    
    public default Func2<I2, I1, R> flip() {
        return (i2, i1) -> this.apply(i1, i2);
    }
    
    public default Function<I1, R> curry(Absent<I1> a1, I2 i2) {
        return i1 -> this.apply(i1, i2);
    }
    
    public default Function<I2, R> curry(I1 i1, Absent<I2> a2) {
        return i2 -> this.apply(i1, i2);
    }
    
}