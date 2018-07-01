package functionalj.types;

import java.util.Objects;
import java.util.function.Function;

public final class ResultDerived<DATA, SOURCE> implements Result<DATA> {
    
    private final Tuple2<SOURCE, Exception>                                    source;
    private final Function<Tuple2<SOURCE, Exception>, Tuple2<DATA, Exception>> action;
    private volatile Tuple2<DATA, Exception> target = null;
    
    public static <S, D> ResultDerived<D, S> of(Tuple2<S, Exception> source, Function<Tuple2<S, Exception>, Tuple2<D, Exception>> action) {
        return new ResultDerived<D, S>(source, action);
    }
    public static <D> ResultDerived<D, D> of(Tuple2<D, Exception> source) {
        return new ResultDerived<D, D>(source, t -> t);
    }
    
    public ResultDerived(Tuple2<SOURCE, Exception> source, Function<Tuple2<SOURCE, Exception>, Tuple2<DATA, Exception>> action) {
        this.source = Objects.requireNonNull(source);
        this.action = Objects.requireNonNull(action);
    }
    
    public final synchronized void materialize() {
        if (target != null)
            return;
        
        synchronized (this) {
            if (target != null)
                return;
            
            target = action.apply(source);
        }
    }
    
    @Override
    public Tuple2<DATA, Exception> asTuple() {
        if (target != null)
            return target;
        
        return action.apply(source);
    }
    
    public String toString() {
        return Result.resultToString(this);
    }
    
}
