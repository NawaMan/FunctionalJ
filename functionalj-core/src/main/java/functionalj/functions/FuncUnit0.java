package functionalj.functions;

import static java.util.Objects.requireNonNull;

import functionalj.ref.RunBody;
import lombok.val;

public interface FuncUnit0 extends Runnable, RunBody<RuntimeException> {
    
    public static FuncUnit0 of(FuncUnit0 runnable) {
        return runnable;
    }
    public static FuncUnit0 from(Runnable runnable) {
        return runnable::run;
    }
    public static <EXCEPTION extends Exception> FuncUnit0 from(RunBody<EXCEPTION> runnable) {
        return runnable::run;
    }
    
    public default void run() {
        try {
            runUnsafe();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionInvocationException(e);
        }
    }
    
    public default void runCarelessly() {
        try {
            runUnsafe();
        } catch (Exception e) {
        }
    }
    
    void runUnsafe() throws Exception;
    
    
    public default FuncUnit0 carelessly() {
        return this::runCarelessly;
    }
    
    public default FuncUnit0 then(FuncUnit0 after) {
        requireNonNull(after);
        return () -> {
            runUnsafe();
            after.runUnsafe();
        };
    }
    
    public default <T> Func0<T> thenReturnNull() {
        return thenReturn(null);
    }
    public default <T> Func0<T> thenReturn(T value) {
        return () -> {
            runUnsafe();
            return value;
        };
    }
    
    public default <T> Func0<T> thenGet(Func0<T> supplier) {
        return () -> {
            runUnsafe();
            val value = supplier.applyUnsafe();
            return value;
        };
    }
    
}
