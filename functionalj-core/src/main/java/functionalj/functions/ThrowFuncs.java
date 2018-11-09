package functionalj.functions;

import java.util.function.Supplier;

import functionalj.environments.Log;
import functionalj.function.Func1;
import functionalj.function.FunctionInvocationException;
import functionalj.ref.Ref;
import lombok.val;

public interface ThrowFuncs {
    
    public static final Ref<Func1<Exception, RuntimeException>> exceptionTranformer = Ref.ofValue(e -> {
        val throwable = new FunctionInvocationException(e);
        Log.logErr(throwable);
        return throwable;
    });
    
    public static <T extends Throwable> T doThrow(T throwable) throws T {
        throw throwable;
    }
    
    public static <T extends Throwable> T doThrowFrom(Supplier<T> supplier) throws T {
        throw supplier.get();
    }
    
}
