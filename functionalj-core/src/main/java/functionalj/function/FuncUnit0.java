package functionalj.function;

import static java.util.Objects.requireNonNull;

import functionalj.promise.Promise;
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
    
    void runUnsafe() throws Exception;
    
    public default void run() {
        try {
            runUnsafe();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception exception) {
            throw Func.exceptionHandler.value().apply(exception);
        }
    }
    
    public default void runCarelessly() {
        try {
            runUnsafe();
        } catch (Exception e) {
        }
    }
    
    public default FuncUnit0 then(FuncUnit0 after) {
        requireNonNull(after);
        return () -> {
            runUnsafe();
            after.runUnsafe();
        };
    }
    
    public default <I, T> Func1<I, T> then(Func1<I, T> function) {
        return input -> {
            runUnsafe();
            val value = function.applyUnsafe(input);
            return value;
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
    
    
    public default FuncUnit0 carelessly() {
        return this::runCarelessly;
    }
    
    public default Promise<Object> async() {
        return this.thenReturnNull().defer();
    }
    
    public default Promise<Object> defer() {
        return this.thenReturnNull().defer();
    }
    
}
