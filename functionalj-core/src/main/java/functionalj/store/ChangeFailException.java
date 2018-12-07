package functionalj.store;

public class ChangeFailException extends RuntimeException {

    private static final long serialVersionUID = -1774773688354042392L;
    
    public ChangeFailException(Throwable cause) {
        super(cause);
    }

}
