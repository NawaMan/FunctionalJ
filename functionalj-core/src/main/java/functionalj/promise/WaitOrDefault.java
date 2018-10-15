package functionalj.promise;

import java.util.function.Supplier;

import functionalj.result.Result;

@SuppressWarnings("javadoc")
public class WaitOrDefault<DATA> extends Wait {
    
    private Wait                   wait;
    private Supplier<Result<DATA>> supplier;
    
    public WaitOrDefault(Wait wait, Supplier<Result<DATA>> supplier) {
        this.wait     = wait;
        this.supplier = supplier;
    }
    
    public Supplier<Result<DATA>> getDefaultSupplier() {
        return supplier;
    }
    
    @Override
    public WaitSession newSession() {
        return this.wait.newSession();
    }
    
}
