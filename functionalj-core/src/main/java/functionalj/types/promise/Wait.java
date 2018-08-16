package functionalj.types.promise;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("javadoc")
public abstract class Wait {
    
    public static WaitForever forever() {
        return WaitForever.instance ;
    }
    
    
    Wait() {}
    
    public abstract WaitSession newSession();
    
}
