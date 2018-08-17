package functionalj.types.promise;

import static functionalj.functions.Func.getOrElse;

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.types.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public abstract class WaitAwhile extends Wait {
    
    @SuppressWarnings("rawtypes")
    public static final Supplier<Result> CANCELLATION_RESULT = ()->Result.ofCancelled();
    
    
    protected WaitAwhile() {}
    
    
    public <DATA> WaitOrDefault<DATA> orDefaultTo(DATA data) {
        return new WaitOrDefault<>(this, ()->Result.of(data));
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultFrom(Supplier<DATA> supplier) {
        return new WaitOrDefault<>(this, ()->Result.from(supplier));
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultGet(Supplier<Result<DATA>> supplier) {
        return new WaitOrDefault<>(this, supplier);
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultResult(Result<DATA> result) {
        return new WaitOrDefault<>(this, ()->result);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <DATA> WaitOrDefault<DATA> orCancel() {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)(Supplier)CANCELLATION_RESULT);
    }
    
    // TODO - from Exception class.
    public <DATA> WaitOrDefault<DATA> orException(Exception exception) {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)()->Result.ofException(exception));
    }
    public <DATA> WaitOrDefault<DATA> orException(Supplier<Exception> exceptionSupplier) {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)()->Result.ofException(getOrElse(exceptionSupplier, null)));
    }
    
    //==  Basic sub class ==
    
    public static class WaitThread extends WaitAwhile {
        
        private final long time;
        private final Function<Runnable, Thread> threadFacory;
        
        public WaitThread(long time) {
            this(time, null);
        }
        public WaitThread(long time, Function<Runnable, Thread> threadFacory) {
            this.time         = Math.max(0, time);
            this.threadFacory = (threadFacory != null) ? threadFacory : runnable -> new Thread(runnable);
        }
        
        @Override
        public WaitSession newSession() {
            val session = new WaitSession();
            threadFacory.apply(()->{
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    // Allow interruption.
                }
                session.expire(null, null);
            })
            .start();
            return session;
        }
        
    }
    
}
