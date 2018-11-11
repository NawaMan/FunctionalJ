package functionalj.functions;

import java.util.function.Supplier;

import functionalj.environments.Log;
import functionalj.function.Func1;
import functionalj.function.FunctionInvocationException;
import functionalj.ref.Ref;
import lombok.val;

public interface ThrowFuncs {
    
    public static final Ref<Func1<Exception, RuntimeException>> exceptionTranformer = Ref.ofValue(e -> {
        val throwable = (e instanceof RuntimeException) 
                ? (RuntimeException)e 
                : new FunctionInvocationException(e);
        return throwable;
    });
    
    public static <T extends Throwable> T doThrow(T throwable) throws T {
        throw throwable;
    }
    
    public static <T extends Throwable> T doThrowFrom(Supplier<T> supplier) throws T {
        throw supplier.get();
    }
    
    public static void handleNoThrow(Exception exception) {
        // TODO - Make a ref.
        Log.logErr(exception);
    }
    
    public static void handleThrowRuntime(Exception exception) {
        val throwable = exceptionTranformer.value().apply(exception);
        handleNoThrow(throwable);
        throw throwable;
    }
    
    // TODO - Add wrap around for some type
    //        Umm ... should rename this class or add in Func.carelessly
    
}
