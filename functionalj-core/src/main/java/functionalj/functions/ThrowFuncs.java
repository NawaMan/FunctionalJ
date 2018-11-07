package functionalj.functions;

import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.FunctionInvocationException;
import functionalj.ref.Ref;

public interface ThrowFuncs {
    
    public static final Ref<Func1<Exception, RuntimeException>> exceptionHandler = Ref.ofValue(e -> new FunctionInvocationException(e));
    
    public static <T extends Throwable> T doThrow(T throwable) throws T {
        throw throwable;
    }
    
    public static <T extends Throwable> T doThrowFrom(Supplier<T> supplier) throws T {
        throw supplier.get();
    }
    
}
