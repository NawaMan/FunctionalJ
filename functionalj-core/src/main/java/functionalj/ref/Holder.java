package functionalj.ref;

import java.util.concurrent.atomic.AtomicReference;

class Holder<STATE> {
    
    private Object data;
    
    Holder(boolean isLocal) {
        this.data = isLocal ? new ThreadLocal<STATE>() : new AtomicReference<STATE>();
    }
    
    public boolean isLocal() {
        return this.data instanceof ThreadLocal;
    }
    
    @SuppressWarnings("unchecked")
    public STATE get() {
        if (isLocal()) 
            return ((ThreadLocal<STATE>)data).get();
       else return ((AtomicReference<STATE>)data).get();
    }
    
    @SuppressWarnings("unchecked")
    public void set(STATE oldState, STATE newState) {
        if (isLocal()) 
             ((ThreadLocal<STATE>)data).set(newState);
        else ((AtomicReference<STATE>)data).compareAndSet(oldState, newState);
    }
    
}