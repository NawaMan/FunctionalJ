package functionalj.validator;

import java.util.function.Predicate;

import functionalj.function.Func2;
import functionalj.result.Result;
import functionalj.result.ValidationException;
import lombok.val;

@SuppressWarnings("javadoc")
public interface SimpleValidator<DATA> extends Validator<DATA> {
    
    public static <D> 
            Func2<
                ? super D, 
                ? super Predicate<? super D>, 
                ? extends ValidationException> exceptionFor(String template) {
        return (d, p) -> new ValidationException(String.format(template, d, p));
    }
    
    
    public Predicate<? super DATA> checker();
    public ValidationException     createException(DATA data);

    public default Result<DATA> validate(DATA data) {
        return Result.from(()->{
            val checker = checker();
            if (checker.test(data))
                return data;
                
            val exception = createException(data);
            throw exception;
        });
    }
    
    
    public static class Impl<D> implements SimpleValidator<D> {
        
        private final Predicate<? super D> checker;
        private final Func2<? super D, ? super Predicate<? super D>, ? extends ValidationException> exceptionCreator;
        
        public Impl(
                Predicate<? super D> checker,
                Func2<? super D, ? super Predicate<? super D>, ? extends ValidationException> exceptionCreator) {
            this.checker = checker;
            this.exceptionCreator = exceptionCreator;
        }
        
        @Override
        public Predicate<? super D> checker() {
            return checker;
        }
        
        @Override
        public ValidationException createException(D data) {
            return exceptionCreator.apply(data, checker);
        }
        
    }
    
}
