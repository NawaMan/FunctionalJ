package functionalj.types.promise;

import functionalj.types.result.AsResult;

@FunctionalInterface
public interface HasPromise<DATA> {
    
    public Promise<DATA> getPromise();
    
    public default AsResult<DATA> asResult() {
    	return getPromise().getResult();
    }
    
}
