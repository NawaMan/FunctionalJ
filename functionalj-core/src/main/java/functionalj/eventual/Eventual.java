package functionalj.eventual;

import functionalj.pipeable.Pipeable;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;

public interface Eventual<DATA> extends HasPromise<DATA>, Pipeable<HasPromise<DATA>> {
    
    DATA get();
    
    @Override
    public default Promise<DATA> getPromise() {
        return Promise.ofValue(null);
    }
    
    @Override
    public default HasPromise<DATA> __data() throws Exception {
        return this;
    }
    
}
