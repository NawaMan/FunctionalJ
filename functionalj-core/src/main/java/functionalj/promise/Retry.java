package functionalj.promise;

import functionalj.ref.Ref;
import lombok.val;

public class Retry<DATA> {
    
    static final int  NO_RETRY      =  0;
    static final int  RETRY_FOREVER = -1;
    static final long NO_WAIT       =  0L;
    
    public static final Ref<Integer> defaultRetryCount = Ref.ofValue(NO_RETRY);
    public static final Ref<Long>    defaultRetryWait  = Ref.ofValue(NO_WAIT);
    
    @SuppressWarnings("rawtypes")
    static final Retry noRetry = new Retry(NO_RETRY, NO_WAIT);
    
    public static final <D> Retry<D> defaultRetry() {
        return new Retry<D>(
                defaultRetryCount.orElse(NO_RETRY),
                defaultRetryWait .orElse(NO_WAIT));
    }
    
    private final int  times;
    private final long waitTimeMilliSecond;
    
    public Retry(int times, long waitTimeMilliSecond) {
        this.times = times;
        this.waitTimeMilliSecond = waitTimeMilliSecond;
    }
    
    public int times() {
        return times;
    }
    public long waitTimeMilliSecond() {
        return waitTimeMilliSecond;
    }
    
    public Retry<DATA> times(int times) {
        return new Retry<DATA>(times, waitTimeMilliSecond);
    }
    
    public Retry<DATA> waitTimeMilliSecond(long waitTimeMilliSecond) {
        return new Retry<DATA>(times, waitTimeMilliSecond);
    }
    
    public WaitRetry<DATA> waitFor(long period) {
        return new WaitRetry<DATA>(this, period);
    }
    
    public static class WaitRetry<DATA> {
        
        private final Retry<DATA> retry;
        private final long  period;
        
        WaitRetry(Retry<DATA> retry, long period) {
            this.retry  = retry;
            this.period = period;
        }
        
        public Retry<DATA> milliseconds() {
            return new Retry<DATA>(retry.times, period);
        }
        
        public Retry<DATA> seconds() {
            return new Retry<DATA>(retry.times, period * 1000);
        }
        
        public Retry<DATA> minutes() {
            return new Retry<DATA>(retry.times, period * 1000 * 60);
        }
        
        public Retry<DATA> hours() {
            return new Retry<DATA>(retry.times, period * 1000 * 60 * 60);
        }
        
        public Retry<DATA> days() {
            return new Retry<DATA>(retry.times, period * 1000 * 60 * 60 * 24);
        }
        
        public Retry<DATA> weeks() {
            return new Retry<DATA>(retry.times, period * 1000 * 60 * 60 * 24 * 7);
        }
        
    }
    
    DeferAction<DATA> create(DeferActionBuilder<DATA> builder) {
        val interruptOnCancel = builder.interruptOnCancel();
        val supplier          = builder.supplier();
        val onStart           = builder.onStart();
        val runner            = builder.runner();
        if (times == Retry.NO_RETRY)
            return DeferAction.create(interruptOnCancel, supplier, onStart, runner);
        
        return RetryableDeferActionCreator.current.value()
                .createRetryDeferAction(interruptOnCancel, onStart, runner, this, supplier);
    }
    
}
