package functionalj.types.result;

import functionalj.functions.Func2;
import functionalj.types.result.Result.ExceptionHolder;
import lombok.val;

public class Value<DATA> extends ImmutableResult<DATA> {
    
    public Value(DATA data) {
        super(data);
    }
    Value(DATA data, Exception exception) {
        super(data, exception);
    }
    
    public final <T> Result<T> mapValue(Func2<DATA, Exception, Result<T>> processor) {
        try {
            val data      = __valueData();
            val isValue   = ((data == null) || !(data instanceof ExceptionHolder));
            val value     = isValue ? (DATA)data : null;
            val exception = isValue ? null       : ((ExceptionHolder)data).getException();
            assert !((value != null) && (exception != null));
            
            val newValue = processor.apply(value, exception);
            if (newValue instanceof Value)
                return newValue;
            
            Object __valueData = newValue.__valueData();
            if (__valueData instanceof ExceptionHolder)
                return new Value<T>((T)null, ((ExceptionHolder) __valueData).getException());
            return new Value<T>((T)__valueData);
        } catch (Exception cause) {
            throw cause;
        }
    }
    
}
