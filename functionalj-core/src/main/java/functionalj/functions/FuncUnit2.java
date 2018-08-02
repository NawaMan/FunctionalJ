package functionalj.functions;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface FuncUnit2<INPUT1, INPUT2> extends BiConsumer<INPUT1, INPUT2> {
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> accept(FuncUnit2<INPUT1, INPUT2> consumer) {
        return (value1, value2) -> consumer.accept(value1, value2);
    }
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> of(FuncUnit2<INPUT1, INPUT2> consumer) {
        return (value1, value2) -> consumer.accept(value1, value2);
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
    
    void acceptUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;
    
}
