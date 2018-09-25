package functionalj.types.result;

import functionalj.functions.FuncUnit0;

// This interface is used to document the second parameter on DeferAction.run and DeferAction.from.
public interface OnStart extends FuncUnit0 {
    
    public static Runnable run(FuncUnit0 runnable) {
        return runnable;
    }
    
}
