package functionalj.environments;

public interface TimeKeeper {
    
    public static final TimeKeeper instance = new TimeKeeperImpl();
    
    public default long currentMilliSecond() {
        return System.currentTimeMillis();
    }
    
    public static class TimeKeeperImpl implements TimeKeeper {
        
        public long currentMilliSecond() {
            return System.currentTimeMillis();
        }
        
        // Sleep
        // Schedule
    }
    
}
