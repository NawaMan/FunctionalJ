package functionalj.promise;

import functionalj.ref.Ref;

public class Retry {
    
    static final int  NO_RETRY      =  0;
    static final int  RETRY_FOREVER = -1;
    static final long NO_WAIT       =  0L;
    
    public static final Retry noRetry = new Retry(NO_RETRY, NO_WAIT);
    
    public static final Ref<Retry> current = Ref.ofValue(noRetry);
    
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
    
    public Retry times(int times) {
        return new Retry(times, waitTimeMilliSecond);
    }
    
    public Retry waitTimeMilliSecond(long waitTimeMilliSecond) {
        return new Retry(times, waitTimeMilliSecond);
    }
    
    public WaitRetry waitFor(long period) {
        return new WaitRetry(this, period);
    }
    
    public static class WaitRetry {
        
        private final Retry retry;
        private final long  period;
        
        WaitRetry(Retry retry, long period) {
            this.retry  = retry;
            this.period = period;
        }
        
        public Retry milliseconds() {
            return new Retry(retry.times, period);
        }
        
        public Retry seconds() {
            return new Retry(retry.times, period * 1000);
        }
        
        public Retry minutes() {
            return new Retry(retry.times, period * 1000 * 60);
        }
        
        public Retry hours() {
            return new Retry(retry.times, period * 1000 * 60 * 60);
        }
        
        public Retry days() {
            return new Retry(retry.times, period * 1000 * 60 * 60 * 24);
        }
        
        public Retry weeks() {
            return new Retry(retry.times, period * 1000 * 60 * 60 * 24 * 7);
        }
        
    }
    
}
