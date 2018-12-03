package functionalj.result;

import functionalj.list.FuncList;
import functionalj.validator.Validator;
import lombok.val;

public class ImmutableResult<DATA> extends Result<DATA> {
    
    private final Object data;
    
    public ImmutableResult(DATA data) {
        this(data, (Exception)null);
    }
    ImmutableResult(DATA data, Exception exception) {
        if (exception != null) {
            this.data = new ExceptionHolder(exception);
        } else {
            this.data = data;
        }
    }
    ImmutableResult(DATA data, FuncList<Validator<? super DATA>> validators) {
        Object theData = data;
        if (validators != null) {
            for (val validator : validators) {
                try {
                    val result    = validator.validate(data);
                    val exception = result.getException();
                    if (exception != null) {
                        if (exception instanceof ValidationException) {
                            theData = new ExceptionHolder(exception);
                        } else {
                            theData = new ExceptionHolder(new ValidationException(exception));
                        }
                        break;
                    }
                } catch (ValidationException exception) {
                    theData = new ExceptionHolder(exception);
                    break;
                } catch (Exception exception) {
                    theData = new ExceptionHolder(new ValidationException(exception));
                    break;
                }
            }
        }
        this.data = theData;
    }
    
    @Override
    Object __valueData() {
        return data;
    }
    
}
