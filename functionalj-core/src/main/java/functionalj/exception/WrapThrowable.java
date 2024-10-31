package functionalj.exception;

public class WrapThrowable extends Exception {
    
    private static final long serialVersionUID = 1879586611000403483L;
    
    public static WrapThrowable of(Throwable throwable) {
        if (throwable instanceof WrapThrowable)
            return (WrapThrowable) throwable;
        if (throwable == null) {
            return null;
        }
        return new WrapThrowable(throwable);
    }
    
    public WrapThrowable(Throwable throwable) {
        super(throwable);
    }
    
    public Throwable getThrowable() {
        return this.getCause();
    }
    
}
