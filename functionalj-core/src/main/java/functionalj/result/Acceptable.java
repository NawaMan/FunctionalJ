package functionalj.result;

import functionalj.list.FuncList;
import functionalj.validator.Validator;

@SuppressWarnings("javadoc")
public abstract class Acceptable<DATA> extends ImmutableResult<DATA> {
    
    protected Acceptable(DATA data, Validation<DATA> validating) {
        this(data, FuncList.of(validating.toValidator()));
    }
    protected Acceptable(DATA data, FuncList<Validator<? super DATA>> validators) {
        super(data, validators);
    }
    protected Acceptable(DATA data, FuncList<Validator<? super DATA>> validators, Validation<DATA> validating) {
        super(data, FuncList.from(validators).append(validating.toValidator()));
    }
    
}
