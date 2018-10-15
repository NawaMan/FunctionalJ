package functionalj.promise;

import static functionalj.functions.Func.carelessly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@SuppressWarnings("javadoc")
public class WaitSession {
    
    private List<BiConsumer<String, Exception>> listeners = new ArrayList<BiConsumer<String, Exception>>();
    
    public WaitSession() {}
    
    public void onExpired(BiConsumer<String, Exception> onDone) {
        listeners.add(onDone);
    }
    
    void expire(String message, Exception throwable) {
        listeners.forEach(listener -> {
            carelessly(() -> {
                listener.accept(message, throwable);
            });
        });
    }
    
}
