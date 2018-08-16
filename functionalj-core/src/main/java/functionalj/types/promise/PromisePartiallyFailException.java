package functionalj.types.promise;

public class PromisePartiallyFailException extends RuntimeException {
    
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
