package functionalj.tuple;

import functionalj.function.Func1;
import functionalj.function.Func2;

@FunctionalInterface
public interface ToTuple2Func<D, T1, T2> extends Func1<D, Tuple2<T1, T2>> {
    
    public default <T> Func1<D, T> thenReduce(Func2<T1, T2, T> reducer) {
        return this.then(reducer::applyTo);
    }
    
    public default Func1<D, T1> thenDrop() {
        return this.then(Tuple2::drop);
    }
    
}
