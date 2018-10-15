package functionalj.promise;

@SuppressWarnings("javadoc")
public enum PromiseStatus {
    
    NOT_STARTED, PENDING, ABORTED, COMPLETED;
    
    public boolean isStarted() {
        return !PromiseStatus.NOT_STARTED.equals(this);
    }
    public boolean isPending() {
        return PromiseStatus.PENDING.equals(this);
    }
    public boolean isAborted() {
        return PromiseStatus.ABORTED.equals(this);
    }
    public boolean isComplete() {
        return PromiseStatus.COMPLETED.equals(this);
    }
    public boolean isDone() {
        return ABORTED.equals(this) || COMPLETED.equals(this);
    }
    public boolean isNotDone() {
        return !this.isDone();
    }
    
}