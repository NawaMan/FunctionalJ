package functionalj.functions;

import static java.util.Objects.requireNonNull;

import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import lombok.val;

@FunctionalInterface
public interface FuncUnit3<INPUT1, INPUT2, INPUT3> {
    
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> of(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return (value1, value2, value3) -> consumer.accept(value1, value2, value3);
    }
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> from(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer::accept;
    }
    
    public default void accept(INPUT1 input1, INPUT2 input2, INPUT3 input3) {
        try {
            acceptUnsafe(input1, input2, input3);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionInvocationException(e);
        }
    }
    
    public default void acceptCarelessly(INPUT1 input1, INPUT2 input2, INPUT3 input3) {
        try {
            acceptUnsafe(input1, input2, input3);
        } catch (Exception e) {
        }
    }
    
    public void acceptUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3) throws Exception;
    
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> carelessly() {
        return this::acceptCarelessly;
    }
    
    
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> then(FuncUnit0 after) {
        requireNonNull(after);
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
            after.runUnsafe();
        };
    }
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> then(FuncUnit3<? super INPUT1, ? super INPUT2, ? super INPUT3> after) {
        requireNonNull(after);
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
            after.acceptUnsafe(input1, input2, input3);
        };
    }
    
    public default <T> Func3<INPUT1, INPUT2, INPUT3, T> thenReturnNull() {
        return thenReturn(null);
    }
    public default <T> Func3<INPUT1, INPUT2, INPUT3, T> thenReturn(T value) {
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
            return value;
        };
    }
    
    public default <T> Func3<INPUT1, INPUT2, INPUT3, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
            val value = supplier.applyUnsafe();
            return value;
        };
    }
    
    public default Func3<INPUT1, INPUT2, INPUT3, Promise<Object>> async() {
        return this.thenReturnNull().async();
    }
    public default Func3<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, Promise<Object>> defer() {
        return (promise1, promise2, promise3) -> {
            val func0 = this.thenReturnNull();
            return Promise.from(
                    input1 -> promise1,
                    input2 -> promise2,
                    input3 -> promise3,
                    func0);
        };
    }
}
