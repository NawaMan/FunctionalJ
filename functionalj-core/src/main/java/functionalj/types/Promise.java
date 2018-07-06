package functionalj.types;

import java.util.function.Consumer;

public interface Promise<DATA> {
    
    public interface Key {}
    
    // onAvailable
    // ifAvailable
    // cancel onAvailable
    
    public Key onAvailable(Consumer<Result<DATA>> resultConsumer);
    
    public void cancel(Key key);
    
    public Result<DATA> ifAvailable();
    
}
