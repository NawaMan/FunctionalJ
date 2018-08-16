package functionalj.types.promise;

import java.util.function.BiConsumer;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface WaitSession {
    
    public void onExpired(BiConsumer<String, Throwable> onDone);
    
}
