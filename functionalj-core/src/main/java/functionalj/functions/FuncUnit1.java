package functionalj.functions;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import lombok.val;

public interface FuncUnit1<INPUT> extends Consumer<INPUT> {
    
    public static <INPUT> FuncUnit1<INPUT> of(FuncUnit1<INPUT> consumer) {
        return (value) -> consumer.accept(value);
    }
    public static <INPUT> FuncUnit1<INPUT> from(Consumer<INPUT> consumer) {
        return consumer::accept;
    }
    
    public default void accept(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionInvocationException(e);
        }
    }
    
    public default void acceptCarelessly(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (Exception e) {
        }
    }
    
    public void acceptUnsafe(INPUT input) throws Exception;
    
    public default FuncUnit1<INPUT> carelessly() {
        return this::acceptCarelessly;
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
    
}
