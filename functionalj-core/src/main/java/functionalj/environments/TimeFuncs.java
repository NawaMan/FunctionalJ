package functionalj.environments;

import functionalj.functions.FuncUnit0;

public class TimeFuncs {
    
    public static FuncUnit0 Sleep(long millisecond) {
        return ()-> {
            Env.time.value().sleep(millisecond);
        };
    }
    
}
