package functionalj.types;

import java.util.function.Consumer;

import functionalj.types.result.Result;
import lombok.val;

@SuppressWarnings("javadoc")
public interface Promise<DATA> {
    
    public static interface Control {
        
        public void cancel();
        
    }
    
    public static interface Wait {
        
        public static interface Session {
            public void onDone(Runnable onDone);
        }
        
        public Session newSession();
        
    }
    
    public void abort();
    
    public Control onAvailable(Consumer<Result<DATA>> resultConsumer);
    
    public default Control onAvailable(Wait wait, Consumer<Result<DATA>> resultConsumer) {
        val control = onAvailable(result -> {
            resultConsumer.accept(result);
        });
        val session = wait.newSession();
        session.onDone(() -> {
            
        });
        return control;
    }
    
    public Result<DATA> getResult();
    
    public Result<DATA> getResult(Wait wait);
    
}
