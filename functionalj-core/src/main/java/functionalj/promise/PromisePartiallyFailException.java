package functionalj.promise;

public class PromisePartiallyFailException extends RuntimeException {
    
	private static final long serialVersionUID = 1715068836323475893L;
	
	private final int index;
    private final int count;
    
    public PromisePartiallyFailException(int index, int count, Throwable cause) {
        super("Promise #" + index + " out of " + count + " fail.", cause);
        this.index = index;
        this.count = count;
    }
    
    public int getIndex() {
        return index;
    }
    
    public int getCount() {
        return count;
    }
    
}
