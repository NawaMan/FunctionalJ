package functionalj.types.result;

import functionalj.types.promise.HasPromise;
import functionalj.types.promise.Promise;

@FunctionalInterface
public interface AsResult<DATA> extends HasPromise<DATA> {

    public Result<DATA> asResult();
    
    public default Promise<DATA> getPromise() {
    	return Promise.ofResult(this);
    }
    
}
