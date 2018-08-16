package functionalj.functions;

public interface FuncUnit0 extends Runnable {
    
    public static FuncUnit0 of(FuncUnit0 runnable) {
        return runnable;
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
    
    void runUnsafe() throws Exception;
    
}
