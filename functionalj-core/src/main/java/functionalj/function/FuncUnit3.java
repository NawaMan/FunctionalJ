package functionalj.function;

import static java.util.Objects.requireNonNull;

import functionalj.functions.ThrowFuncs;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.tuple.Tuple3;
import lombok.val;

@FunctionalInterface
public interface FuncUnit3<INPUT1, INPUT2, INPUT3> {
    
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> of(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer;
    }
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> from(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer::accept;
    }
    
    public void acceptUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3) throws Exception;
    
    public default void accept(INPUT1 input1, INPUT2 input2, INPUT3 input3) {
        try {
            acceptUnsafe(input1, input2, input3);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionHandler.value().apply(e);
        }
    }
    
    public default void acceptCarelessly(INPUT1 input1, INPUT2 input2, INPUT3 input3) {
        try {
            acceptUnsafe(input1, input2, input3);
        } catch (Exception e) {
        }
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
    
    public default FuncUnit1<Tuple3<INPUT1, INPUT2, INPUT3>> wholly() {
        return tuple -> {
            val _1 = tuple._1();
            val _2 = tuple._2();
            val _3 = tuple._3();
            acceptUnsafe(_1, _2, _3);
        };
    }
    
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> carelessly() {
        return this::acceptCarelessly;
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
    
    //== Partially apply functions ==
    
    @SuppressWarnings("javadoc")
    public default FuncUnit0 bind(INPUT1 i1, INPUT2 i2, INPUT3 i3) {
        return () -> this.acceptUnsafe(i1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public default FuncUnit2<INPUT2, INPUT3> bind1(INPUT1 i1) {
        return (i2,i3) -> this.acceptUnsafe(i1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public default FuncUnit2<INPUT1, INPUT3> bind2(INPUT2 i2) {
        return (i1,i3) -> this.acceptUnsafe(i1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public default FuncUnit2<INPUT1, INPUT2> bind3(INPUT3 i3) {
        return (i1,i2) -> this.acceptUnsafe(i1, i2, i3);
    }
    
    @SuppressWarnings("javadoc")
    public default FuncUnit1<INPUT1> bind(Absent a1, INPUT2 i2, INPUT3 i3) {
        return i1 -> this.acceptUnsafe(i1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public default FuncUnit1<INPUT2> bind(INPUT1 i1, Absent a2, INPUT3 i3) {
        return i2 -> this.acceptUnsafe(i1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public default FuncUnit1<INPUT3> bind(INPUT1 i1, INPUT2 i2, Absent a3) {
        return i3 -> this.acceptUnsafe(i1, i2, i3);
    }
    
    @SuppressWarnings("javadoc")
    public default FuncUnit2<INPUT1, INPUT2> bind(Absent a1, Absent a2, INPUT3 i3) {
        return (i1, i2) -> this.acceptUnsafe(i1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public default FuncUnit2<INPUT1, INPUT3> bind(Absent a1, INPUT2 i2, Absent a3) {
        return (i1, i3) -> this.acceptUnsafe(i1, i2, i3);
    }
    @SuppressWarnings("javadoc")
    public default FuncUnit2<INPUT2, INPUT3> bind(INPUT1 i1, Absent a2, Absent a3) {
        return (i2, i3) -> this.acceptUnsafe(i1, i2, i3);
    }
    
}
