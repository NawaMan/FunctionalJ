package functionalj.functions;

import java.util.function.Consumer;

public interface FuncUnit1<INPUT> extends Consumer<INPUT> {
    
    public static <INPUT> FuncUnit1<INPUT> of(FuncUnit1<INPUT> consumer) {
        return (value) -> consumer.accept(value);
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
    
    void acceptUnsafe(INPUT input) throws Exception;
    
}
