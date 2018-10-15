package functionalj.promise;

import java.util.function.BiConsumer;

@SuppressWarnings("javadoc")
public class WaitForever extends Wait {
    
    public static WaitForever instance = new WaitForever();
    
    private WaitForever() {}
    
    @Override
    public WaitSession newSession() {
        return new WaitSession() {
            @Override
            public void onExpired(BiConsumer<String, Exception> onDone) {
            }
        };
    }
}
