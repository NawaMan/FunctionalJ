package functionalj.functions;

@FunctionalInterface
public interface FuncUnit3<INPUT1, INPUT2, INPUT3> {
    
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> of(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return (value1, value2, value3) -> consumer.accept(value1, value2, value3);
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
    
    void acceptUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3) throws Exception;
    
}
