package functionalj.result;

import functionalj.promise.HasPromise;
import functionalj.promise.Promise;

@FunctionalInterface
public interface AsResult<DATA> extends HasPromise<DATA> {

    public Result<DATA> asResult();
    
    public default Promise<DATA> getPromise() {
    	return Promise.ofResult(this);
    }
    
}
