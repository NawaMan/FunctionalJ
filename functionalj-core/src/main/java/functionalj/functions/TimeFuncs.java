package functionalj.functions;

import functionalj.environments.Env;
import functionalj.function.FuncUnit0;

public class TimeFuncs {
    
    public static FuncUnit0 Sleep(long millisecond) {
        return ()-> {
            Env.time().sleep(millisecond);
        };
    }
    
}
