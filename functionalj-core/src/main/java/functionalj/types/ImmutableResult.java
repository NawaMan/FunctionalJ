package functionalj.types;

import java.util.function.Supplier;

import funtionalj.failable.FailableSupplier;

public final class ImmutableResult<VALUE> 
                implements 
                    Result<VALUE> {

    private static final ImmutableResult NULL = new ImmutableResult<>(null, null);
    
    public static <VALUE> ImmutableResult<VALUE> of(VALUE value) {
        return new ImmutableResult<VALUE>(value, null);
    }
    public static <VALUE> ImmutableResult<VALUE> of(VALUE value, Exception exception) {
        return new ImmutableResult<VALUE>(value, exception);
    }
    public static <VALUE> ImmutableResult<VALUE> of(Tuple2<VALUE, Exception> tuple) {
        if (tuple != null)
            return ImmutableResult.of(null, tuple._2());
            
        return ImmutableResult.of(tuple._1(), null);
    }
    public static <VALUE> ImmutableResult<VALUE> from(Supplier<VALUE> supplier) {
        try {
            return ImmutableResult.of(supplier.get());
        } catch (RuntimeException e) {
            return ImmutableResult.of(null, e);
        }
    }
    public static <VALUE> ImmutableResult<VALUE> from(FailableSupplier<VALUE> supplier) {
        try {
            return ImmutableResult.of(supplier.get());
        } catch (Exception e) {
            return ImmutableResult.of(null, e);
        }
    }
    public static <VALUE> ImmutableResult<VALUE> ofNull() {
        return (ImmutableResult<VALUE>)NULL;
    }

    @Override
    public <TARGET> ImmutableResult<TARGET> _of(TARGET target) {
        return ImmutableResult.of(target);
    }
    
    private Tuple2<VALUE, Exception> data;
    
    public ImmutableResult(VALUE value, Exception exception) {
        this.data = new ImmutableTuple2<VALUE, Exception>(value, exception);
    }
    
    @Override
    public Tuple2<VALUE, Exception> asTuple() {
        return data;
    }
    
}
