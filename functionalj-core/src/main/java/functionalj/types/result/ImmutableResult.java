package functionalj.types.result;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.functions.FuncUnit2;
import functionalj.types.result.Result.ExceptionHolder;
import lombok.val;

public class ImmutableResult<DATA> extends Result<DATA> {
    
    private final Object data;
    
    public ImmutableResult(DATA data) {
        this.data = data;
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
    
//    public final <T> Value<T> mapValue(Func2<DATA, Exception, Value<T>> processor) {
//        try {
//            val data      = __valueData();
//            val isValue   = ((data == null) || !(data instanceof ExceptionHolder));
//            val value     = isValue ? (DATA)data : null;
//            val exception = isValue ? null       : ((ExceptionHolder)data).getException();
//            assert !((value != null) && (exception != null));
//            
//            val newValue = processor.apply(value, exception);
//            return newValue;
//        } catch (Exception cause) {
//            throw cause;
//        }
//    }
    
}
