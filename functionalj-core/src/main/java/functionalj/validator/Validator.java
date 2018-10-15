package functionalj.validator;

import java.util.function.Predicate;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.result.Result;
import functionalj.result.ValidationException;

@SuppressWarnings("javadoc")
public interface Validator<DATA> extends Predicate<DATA>, Func1<DATA, Boolean> {

    
    public static <D> Validator<D> of(Predicate<D> checker, Func2<? super D, ? super Predicate<? super D>, ? extends ValidationException> exceptionCreator) {
        return new SimpleValidator.Impl<D>(checker, exceptionCreator);
    }
    
    public static <D> Validator<D> of(Predicate<D> checker, String templateMsg) {
        return new SimpleValidator.Impl<D>(checker, SimpleValidator.exceptionFor(templateMsg));
    }
    
    @Override
    public default boolean test(DATA data) {
        return validate(data).isPresent();
    }
    @Override
    public default Boolean applyUnsafe(DATA data) throws Exception {
        return test(data);
    }
    
    public Result<DATA> validate(DATA data);
    
}
