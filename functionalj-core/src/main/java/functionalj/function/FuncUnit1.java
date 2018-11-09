package functionalj.function;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import functionalj.functions.ThrowFuncs;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import lombok.val;

public interface FuncUnit1<INPUT> extends Consumer<INPUT> {
    
    public static <INPUT> FuncUnit1<INPUT> of(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    public static <INPUT> FuncUnit1<INPUT> from(Consumer<INPUT> consumer) {
        return consumer::accept;
    }
    
    public void acceptUnsafe(INPUT input) throws Exception;
    
    public default void accept(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTranformer.value().apply(e);
        }
    }
    
    public default void acceptCarelessly(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (Exception e) {
        }
    }
    
    public default FuncUnit1<INPUT> then(FuncUnit0 after) {
        requireNonNull(after);
        return input -> {
            acceptUnsafe(input);
            after.runUnsafe();
        };
    }
    public default FuncUnit1<INPUT> then(FuncUnit1<? super INPUT> after) {
        requireNonNull(after);
        return input -> {
            acceptUnsafe(input);
            after.acceptUnsafe(input);
        };
    }
    
    public default <T> Func1<INPUT, T> thenReturnNull() {
        return thenReturn(null);
    }
    public default <T> Func1<INPUT, T> thenReturn(T value) {
        return input -> {
            acceptUnsafe(input);
            return value;
        };
    }
    
    public default <T> Func1<INPUT, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return input -> {
            acceptUnsafe(input);
            val value = supplier.applyUnsafe();
            return value;
        };
    }
    
    public default FuncUnit1<INPUT> carelessly() {
        return this::acceptCarelessly;
    }
    
    public default Func1<INPUT, Promise<Object>> async() {
        return this.thenReturnNull().async();
    }
    public default Func1<HasPromise<INPUT>, Promise<Object>> defer() {
        return input -> {
            val func0 = this.thenReturnNull();
            return input.getPromise()
                    .map(func0);
        };
    }
    
    @SuppressWarnings("javadoc")
    public default FuncUnit0 bind(INPUT i) {
        return () -> this.acceptUnsafe(i);
    }
}
