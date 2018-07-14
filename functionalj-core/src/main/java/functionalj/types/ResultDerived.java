package functionalj.types;

import java.util.Objects;

import functionalj.functions.Func2;
import lombok.val;

public final class ResultDerived<DATA, SOURCE> extends Result<DATA> {
    
    private final Result<SOURCE>                 source;
    private final Func2<SOURCE, Exception, DATA> action;
    
//    public static <S, D> ResultDerived<D, S> of(Tuple2<S, Exception> source, Function<Tuple2<S, Exception>, Tuple2<D, Exception>> action) {
//        return new ResultDerived<D, S>(source, action);
//    }
//    public static <D> ResultDerived<D, D> of(Tuple2<D, Exception> source) {
//        return new ResultDerived<D, D>(source, t -> t);
//    }
//    
    public ResultDerived(Result<SOURCE> source, Func2<SOURCE, Exception, DATA> action) {
        this.source = Objects.requireNonNull(source);
        this.action = Objects.requireNonNull(action);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected Object getData() {
        val data      = source.getData();
        val isValue   = ((data == null) || !(data instanceof ExceptionHolder));
        val value     = isValue ? (SOURCE)data : null;
        val exception = isValue ? null         : ((ExceptionHolder)data).getException();
        
        try {
            val newData = action.apply(value, exception);
            return newData;
        } catch (Exception e) {
            return new ExceptionHolder(e);
        }
    }
    
}
