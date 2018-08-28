package functionalj.types.result;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.functions.FuncUnit2;
import functionalj.types.result.Result.ExceptionHolder;
import lombok.val;

public class ImmutableResult<DATA> extends Result<DATA> {
    
    private final Object data;
    
    public ImmutableResult(DATA data) {
        this(data, null);
    }
    ImmutableResult(DATA data, Exception exception) {
        this.data = (exception != null)
                ? new ExceptionHolder(exception)
                : data ;
    }
    
    @Override
    Object __valueData() {
        return data;
    }
    
}
