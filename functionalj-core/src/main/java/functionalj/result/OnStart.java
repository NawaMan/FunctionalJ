package functionalj.result;

import functionalj.function.FuncUnit0;

// This interface is used to document the second parameter on DeferAction.run and DeferAction.from.
public interface OnStart extends FuncUnit0 {
    
    public static final OnStart DoNothing = ()->{};
    
    public static OnStart run(FuncUnit0 runnable) {
        if (runnable == null)
            return null;
        
        return runnable::run;
    }
    
}
