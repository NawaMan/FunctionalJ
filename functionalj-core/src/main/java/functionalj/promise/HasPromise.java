package functionalj.promise;

import functionalj.result.AsResult;

@FunctionalInterface
public interface HasPromise<DATA> {
    
    public Promise<DATA> getPromise();
    
    public default AsResult<DATA> asResult() {
    	return getPromise().getResult();
    }
    
}
