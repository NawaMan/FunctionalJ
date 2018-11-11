package functionalj.promise;

public abstract class StartableAction<DATA> {
    
    public abstract PendingAction<DATA> start();
    
}
