package functionalj.promise;

import static functionalj.function.Func.getOrElse;

import java.util.function.Supplier;

import functionalj.environments.AsyncRunner;
import functionalj.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public abstract class WaitAwhile extends Wait {
    
    @SuppressWarnings("rawtypes")
    public static final Supplier<Result> CANCELLATION_RESULT = ()->Result.ofCancelled();
    
    
    protected WaitAwhile() {}
    
    
    public <DATA> WaitOrDefault<DATA> orDefaultTo(DATA data) {
        return new WaitOrDefault<>(this, ()->Result.valueOf(data));
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultFrom(Supplier<DATA> supplier) {
        return new WaitOrDefault<>(this, ()->Result.of(supplier));
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
    
    public static class WaitAsync extends WaitAwhile {
        
        private final long        time;
        private final AsyncRunner asyncRunner;
        
        public WaitAsync(long time) {
            this(time, null);
        }
        public WaitAsync(long time, AsyncRunner asyncRunner) {
            this.time = Math.max(0, time);
            this.asyncRunner = asyncRunner;
            
        }
        
        @Override
        public WaitSession newSession() {
            val session = new WaitSession();
            AsyncRunner.run(asyncRunner, ()->{
                // TODO - Once scheduling is available, use it.
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    // Allow interruption.
                }
                session.expire(null, null);
            });
            return session;
        }
        
    }
    
}
