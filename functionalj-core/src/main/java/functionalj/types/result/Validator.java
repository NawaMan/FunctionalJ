package functionalj.types.result;

import static functionalj.FunctionalJ.it;

import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func1;
import functionalj.functions.Func4;
import lombok.val;

public interface Validator<DATA, TARGET> extends Predicate<DATA>, Func1<DATA, Boolean> {
    
    // This act like a predicate but the method validate will add exception.
    
    public Function<DATA, TARGET>    mapper();
    public Predicate<? super TARGET> checker();
    public Func4<? super DATA, ? super TARGET, ? super Function<? super DATA, TARGET>, ? super Predicate<? super TARGET>, ? extends ValidationException> newError();

    @Override
    public default boolean test(DATA data) {
        val mapper  = mapper();
        val target  = mapper.apply(data);
        val checker = checker();
        return checker.test(target);
    }
    @Override
    public default Boolean applyUnsafe(DATA data) throws Exception {
        return test(data);
    }
    
    public default Result<DATA> validate(DATA data) {
        return Result.from(()->{
            val mapper  = mapper();
            val target  = mapper.apply(data);
            val checker = checker();
            if (checker.test(target))
                return data;
                
            val exception = newError().apply(data, target, mapper, checker);
            throw exception;
        });
    }
    
    
    public static class Impl<D, T> implements Validator<D, T> {
        private Function<D, T>       mapper;
        private Predicate<? super T> checker;
        private Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> newError;
        
        public Impl(Function<D, T> mapper, Predicate<? super T> checker,
                Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> newError) {
            this.mapper = mapper;
            this.checker = checker;
            this.newError = newError;
        }
        
        @Override
        public Function<D, T> mapper() {
            return mapper;
        }

        @Override
        public Predicate<? super T> checker() {
            return checker;
        }

        @Override
        public Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> newError() {
            return newError;
        }
        
    }
    
    public static <D, T> Validator<D, T> of(Function<D, T> mapper, Predicate<T> checker, Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> newError) {
        return new Validator.Impl<D, T>(mapper, checker, newError);
    }
    
    public static <D> Validator<D, D> of(Predicate<D> checker, String templateMsg) {
        return new Validator.Impl<D, D>(it(), checker, ValidationException.forString(templateMsg));
    }
    
}
