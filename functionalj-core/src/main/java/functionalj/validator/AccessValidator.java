package functionalj.types.result.validator;

import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func4;
import functionalj.types.result.Result;
import functionalj.types.result.ValidationException;
import lombok.val;

@SuppressWarnings("javadoc")
public interface AccessValidator<DATA, TARGET> extends Validator<DATA> {

    public Function<DATA, TARGET>    access();
    public Predicate<? super TARGET> checker();
    public ValidationException       createException(DATA data, TARGET target);
    
    public default Result<DATA> validate(DATA data) {
        return Result.from(()->{
            val mapper  = access();
            val target  = mapper.apply(data);
            val checker = checker();
            if (checker.test(target))
                return data;
                
            val exception = createException(data, target);
            throw exception;
        });
    }
    
    public static class Impl<D, T> implements AccessValidator<D, T> {
        private Function<D, T>       access;
        private Predicate<? super T> checker;
        private Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> exceptionCreator;
        
        public Impl(
                Function<D, T> access,
                Predicate<? super T> checker,
                Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> exceptionCreator) {
            this.access = access;
            this.checker = checker;
            this.exceptionCreator = exceptionCreator;
        }
        
        @Override
        public Function<D, T> access() {
            return access;
        }

        @Override
        public Predicate<? super T> checker() {
            return checker;
        }
        
        @Override
        public ValidationException createException(D data, T target) {
            return exceptionCreator.apply(data, target, access, checker);
        }
        
    }
}
