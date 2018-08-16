package functionalj.types.promise;

import java.util.function.BiConsumer;

@SuppressWarnings("javadoc")
public class WaitForever implements Wait {
    
    public static WaitForever instance = new WaitForever();
    
    private WaitForever() {}
    
    @Override
    public WaitSession newSession() {
        return new WaitSession() {
            @Override
            public void onExpired(BiConsumer<String, Throwable> onDone) {
            }
        };
    }
}
