package functionalj.environments;

import functionalj.InterruptedRuntimeException;

public final class Time {
    
    private Time() {
    }
    
    public static long currentMilliSecond() {
        return Env.time().currentMilliSecond();
    }
    
    public static void sleep(long millisecond) {
        Env.time().sleep(millisecond);
    }
    
    
    public static interface Instance {
        
        public long currentMilliSecond();
        public void sleep(long millisecond);
        
    }
    
    public static class System implements Instance {
        
        public static final Time.Instance instance = new Time.System();
        
//        private final ScheduledExecutorService scheduler =
//                Executors.newScheduledThreadPool(1);
        
        public long currentMilliSecond() {
            return java.lang.System.currentTimeMillis();
        }
        
        public void sleep(long millisecond) {
            try {
                Thread.sleep(millisecond);
            } catch (InterruptedException e) {
                throw new InterruptedRuntimeException(e);
            }
        }
        
//        public <EXECUTION extends Exception> 
//                Promise<Object> schedule(RunBody<EXECUTION> callable, long delay, TimeUnit unit) {
//        }
//        public <DATA, EXECUTION extends Exception> 
//                Promise<Object> schedule(ComputeBody<DATA, EXECUTION> callable, long delay, TimeUnit unit) {
//        }
        // Schedule that start with absulote time
        
        // Schedule periodic tasks.
    }
    
    // TODO - Implement that allow fake
    
}
