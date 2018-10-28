package functionalj.environments;

import functionalj.functions.FuncUnit0;

public class TimeKeeperFuncs {
    
    public static FuncUnit0 Sleep(long millisecond) {
        return ()-> {
            Env.timerKeeper.value().currentMilliSecond();
            Thread.sleep(millisecond);
        };
    }
    
}
