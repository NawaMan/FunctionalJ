package functionalj.result;

import functionalj.promise.HasPromise;
import functionalj.promise.Promise;

public interface HasResult<DATA> extends HasPromise<DATA> {

    public Result<DATA> getResult();
    
    public default Promise<DATA> getPromise() {
        return Promise.ofResult(this);
    }

}
