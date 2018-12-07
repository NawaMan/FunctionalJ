package functionalj.store;

public class ChangeRejectedException extends RuntimeException {
    
    private static final long serialVersionUID = 430630206410100106L;
    
    public ChangeRejectedException(String reason) {
        super(reason);
    }
    public ChangeRejectedException(String reason, Throwable cause) {
        super(reason, cause);
    }
    public ChangeRejectedException(Throwable cause) {
        super(cause);
    }
    
}
