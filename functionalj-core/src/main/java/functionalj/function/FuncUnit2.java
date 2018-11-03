package functionalj.function;

import static java.util.Objects.requireNonNull;

import java.util.function.BiConsumer;

import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import lombok.val;

@FunctionalInterface
public interface FuncUnit2<INPUT1, INPUT2> extends BiConsumer<INPUT1, INPUT2> {
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> of(FuncUnit2<INPUT1, INPUT2> consumer) {
        return (value1, value2) -> consumer.accept(value1, value2);
    }
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> from(BiConsumer<INPUT1, INPUT2> consumer) {
        return consumer::accept;
    }
    
    public default void accept(INPUT1 input1, INPUT2 input2) {
        try {
            acceptUnsafe(input1, input2);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionInvocationException(e);
        }
    }
    
    public default void acceptCarelessly(INPUT1 input1, INPUT2 input2) {
        try {
            acceptUnsafe(input1, input2);
        } catch (Exception e) {
        }
    }
    
    public void acceptUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;
    
    public default FuncUnit2<INPUT1, INPUT2> carelessly() {
        return this::acceptCarelessly;
    }
    
    
    public default FuncUnit2<INPUT1, INPUT2> then(FuncUnit0 after) {
        requireNonNull(after);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            after.runUnsafe();
        };
    }
    public default FuncUnit2<INPUT1, INPUT2> then(FuncUnit2<? super INPUT1, ? super INPUT2> after) {
        requireNonNull(after);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            after.acceptUnsafe(input1, input2);
        };
    }
    
    public default <T> Func2<INPUT1, INPUT2, T> thenReturnNull() {
        return thenReturn(null);
    }
    public default <T> Func2<INPUT1, INPUT2, T> thenReturn(T value) {
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            return value;
        };
    }
    
    public default <T> Func2<INPUT1, INPUT2, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            val value = supplier.applyUnsafe();
            return value;
        };
    }
    
    public default Func2<INPUT1, INPUT2, HasPromise<Object>> async() {
        return this.thenReturnNull().async();
    }
    public default Func2<HasPromise<INPUT1>, HasPromise<INPUT2>, Promise<Object>> defer() {
        return (promise1, promise2) -> {
            val func0 = this.thenReturnNull();
            return Promise.from(
                    input1 -> promise1,
                    input2 -> promise2,
                    func0);
        };
    }
    
}
