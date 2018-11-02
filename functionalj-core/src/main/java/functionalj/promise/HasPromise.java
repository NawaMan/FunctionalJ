package functionalj.promise;

import functionalj.function.NamedExpression;
import functionalj.result.AsResult;
import functionalj.result.Result;

@FunctionalInterface
public interface HasPromise<DATA> extends NamedExpression<HasPromise<DATA>> {
    
    public Promise<DATA> getPromise();
    
    public default HasPromise<DATA> apply(Object dummy) {
        return this;
    }
    
    public default AsResult<DATA> asResult() {
        return getPromise().getResult();
    }
    
    public default Result<DATA> getResult() {
        return getPromise().getResult();
    }
    
}
