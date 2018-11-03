package functionalj.promise;

import functionalj.ref.Ref;

public abstract class StartableAction<DATA> {
    
    // TODO - Experimental!!!
    // Basically, if a promise has more than one listener (subscriber+eavesdropper) 
    static Ref<Boolean> runSynchromously = Ref.ofValue(false);
    
    public abstract PendingAction<DATA> start();
    
}
