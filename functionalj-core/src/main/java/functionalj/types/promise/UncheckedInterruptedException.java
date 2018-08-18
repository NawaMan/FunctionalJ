package functionalj.types.promise;

public class UncheckedInterruptedException extends RuntimeException {

    private static final long serialVersionUID = 4126941862149908606L;

    public UncheckedInterruptedException(InterruptedException cause) {
        super(cause);
    }
    
    
    
}
