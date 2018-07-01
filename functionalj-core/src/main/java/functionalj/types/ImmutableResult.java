package functionalj.types;

import java.util.function.Supplier;

import funtionalj.failable.FailableSupplier;

public final class ImmutableResult<DATA> 
                implements 
                    Result<DATA> {

    private static final ImmutableResult NULL = new ImmutableResult<>(null, null);
    
    public static <D> ImmutableResult<D> of(D value) {
        return new ImmutableResult<D>(value, null);
    }
    public static <D> ImmutableResult<D> of(D value, Exception exception) {
        return new ImmutableResult<D>(value, exception);
    }
    public static <D> ImmutableResult<D> of(Tuple2<D, Exception> tuple) {
        if (tuple instanceof ImmutableResult)
            return (ImmutableResult<D>)tuple;
        
        if (tuple == null)
            return ImmutableResult.ofNull();
            
        return ImmutableResult.of(tuple._1(), null);
    }
    public static <D> ImmutableResult<D> from(Supplier<D> supplier) {
        try {
            return ImmutableResult.of(supplier.get());
        } catch (RuntimeException e) {
            return ImmutableResult.of(null, e);
        }
    }
    public static <D> ImmutableResult<D> from(FailableSupplier<D> supplier) {
        try {
            return ImmutableResult.of(supplier.get());
        } catch (Exception e) {
            return ImmutableResult.of(null, e);
        }
    }
    public static <D> ImmutableResult<D> ofNull() {
        return (ImmutableResult<D>)NULL;
    }

    @Override
    public <TARGET> ImmutableResult<TARGET> _of(TARGET target) {
        return ImmutableResult.of(target);
    }
    
    private Tuple2<DATA, Exception> data;
    
    public ImmutableResult(DATA value, Exception exception) {
        this.data = new ImmutableTuple2<DATA, Exception>(value, (value != null) ? null : exception);
    }
    
    @Override
    public Tuple2<DATA, Exception> asTuple() {
        return data;
    }
    
    public ImmutableResult<DATA> toImmutable() {
        return this;
    }
    
    public String toString() {
        return Result.resultToString(this);
    }
    
}
