package functionalj.types.promise;

import static functionalj.functions.Func.carelessly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@SuppressWarnings("javadoc")
public class WaitSession {
    
    private List<BiConsumer<String, Throwable>> listeners = new ArrayList<BiConsumer<String, Throwable>>();
    
    public WaitSession() {}
    
    public void onExpired(BiConsumer<String, Throwable> onDone) {
        listeners.add(onDone);
    }
    
    void expire(String message, Throwable throwable) {
        listeners.forEach(listener -> {
            carelessly(() -> {
                listener.accept(message, throwable);
            });
        });
    }
    
}
