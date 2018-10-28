package functionalj.environments;

import functionalj.InterruptedRuntimeException;
import nawaman.defaultj.annotations.Default;

public interface TimeKeeper {
    
    @Default
    public static final TimeKeeper instance = new TimeKeeperSystem();
    
    public long currentMilliSecond();
    public void sleep(long millisecond);
    
    public static class TimeKeeperSystem implements TimeKeeper {
        
//        private final ScheduledExecutorService scheduler =
//                Executors.newScheduledThreadPool(1);
        
        public long currentMilliSecond() {
            return System.currentTimeMillis();
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
