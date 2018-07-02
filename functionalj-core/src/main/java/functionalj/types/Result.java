package functionalj.types;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.functions.Func3;
import functionalj.kinds.Comonad;
import functionalj.kinds.Filterable;
import functionalj.kinds.Functor;
import functionalj.kinds.Monad;
import funtionalj.failable.FailableBiFunction;
import funtionalj.failable.FailableFunc0;
import funtionalj.failable.FailableFunction;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;


public abstract class Result<DATA>
                    implements 
                        Functor<Result<?>, DATA>, 
                        Monad<Result<?>, DATA>, 
                        Comonad<Result<?>, DATA>,
                        Filterable<Result<?>, DATA>,
                        Tuple2<DATA, Exception>,
                        Nullable<DATA> {
    
    protected abstract Object getData();
    
    protected <T> T processData(Func3<Boolean, DATA, Exception, T> processor) {
        val data      = getData();
        val isValue   = ((data == null) || !(data instanceof ExceptionHolder));
        val value     = isValue ? (DATA)data : null;
        val exception = isValue ? null         : ((ExceptionHolder)data).getException();
        return processor.apply(isValue, value, exception);
    }
    
    public DATA getValue() {
        val data = getData();
        return (data instanceof ExceptionHolder) ? null : (DATA)data;
    }
    
    public Exception getException() {
        val data = getData();
        return (data instanceof ExceptionHolder) ? ((ExceptionHolder)data).getException() : null;
    }
    
    @Override
    public DATA get() {
        return getValue();
    }
    
    @Override
    public DATA _extract() {
        return getValue();
    }
    
    @Override
    public DATA _1() {
        return getValue();
    }
    
    @Override
    public Exception _2() {
        return getException();
    }
    
    public Tuple2<DATA, Exception> asTuple() {
        return new Tuple2<DATA, Exception>() {
            @Override public DATA _1()      { return getValue();     }
            @Override public Exception _2() { return getException(); }
        };
    }
    
    public ImmutableResult<DATA> toImmutable() {
        return ImmutableResult.of(this);
    }
    
    @Override
    public <TARGET> Result<TARGET> _map(Function<? super DATA, TARGET> mapper) {
        return new ResultDerived<>(this, (s, e) -> mapper.apply(s));
    }
    
    @Override
    public <TARGET> Result<TARGET> map(Function<? super DATA, TARGET> mapper) {
        return _map(mapper);
    }
    
    public <TARGET> Result<TARGET> map(BiFunction<DATA, Exception, TARGET> mapper) {
        return new ResultDerived<>(this, (s, e) -> mapper.apply(s, e));
    }
    
    public <TARGET> Result<TARGET> failableMap(FailableFunction<DATA, TARGET> mapper) {
        return new ResultDerived<>(this, (s, e) -> mapper.apply(s));
    }
    
    public <TARGET> Result<TARGET> failableMap(FailableBiFunction<DATA, Exception, TARGET> mapper) {
        return new ResultDerived<>(this, (s, e) -> mapper.apply(s, e));
    }

    @Override
    public <TARGET> Result<TARGET> _flatMap(Function<? super DATA, Monad<Result<?>, TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            val monad = (Result<TARGET>)mapper.apply(s);
            return monad.orThrow();
        });
    }
    
    public <TARGET> Result<TARGET> flatMap(BiFunction<DATA, Exception, Monad<Result<?>, TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            val monad = (Result<TARGET>)mapper.apply(s, e);
            return monad.orThrow();
        });
    }
    
    public <TARGET> Result<TARGET> failableFlatMap(FailableFunction<DATA, Monad<Result<?>, TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            val monad = (Result<TARGET>)mapper.apply(s);
            return monad.orThrow();
        });
    }
    
    @Override
    public <TARGET> Result<TARGET> flatMap(Function<? super DATA, ? extends Nullable<TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            val monad = (Nullable<TARGET>)mapper.apply(s);
            return monad.orElse(null);
        });
    }
    
    public <TARGET> Result<TARGET> failableFlatMap(FailableBiFunction<DATA, Exception, Monad<Result<?>, TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            val monad = (Nullable<TARGET>)mapper.apply(s, e);
            return monad.orElse(null);
        });
    }
    
    @Override
    public Result<DATA> peek(Consumer<? super DATA> theConsumer) {
        Nullable.super.peek(theConsumer);
        return this;
    }
    
    public boolean isValue() {
        val exception = getException();
        return exception == null;
    }
    
    public boolean isNotNull() {
        return processData((isValue, value, exception) -> {
            if (exception != null)
                return false;
            
            return (value != null);
        });
    }
    
    public boolean isNull() {
        return processData((isValue, value, exception) -> {
            if (exception != null)
                return false;
            
            return (value == null);
        });
    }
    
    public boolean isException() {
        val exception = getException();
        return exception != null;
    }
    
    public boolean isAvailable() {
        val exception = getException();
        return !(exception instanceof ResultNotAvailableException);
    }
    
    public boolean isNotAvailable() {
        val exception = getException();
        return (exception instanceof ResultNotAvailableException);
    }
    
    public boolean isNotReady() {
        val exception = getException();
        return (exception instanceof ResultNotReadyException);
    }
    
    public boolean isCancelled() {
        val exception = getException();
        return (exception instanceof ResultCancelledException);
    }
    
    public boolean isImmutable() {
        return this instanceof ImmutableResult;
    }
    
    public boolean isNotImmutable() {
        return !(this instanceof ImmutableResult);
    }
    
    public Result<DATA> ensureNotNull() {
        DATA value = getValue();
        if (value != null)
            return this;
        
        return ImmutableResult.of(null, new NullPointerException());
    }
    
    public Result<DATA> makeNullable() {
        val exception = getException();
        if (exception instanceof NullPointerException)
            return ImmutableResult.ofNull();
        
        return this;
    }
    
    public Result<DATA> filter(Predicate<? super DATA> theCondition) {
        DATA value = get();
        if (value == null)
            return this;
        
        val isPass = theCondition.test(value);
        if (!isPass)
            return ImmutableResult.ofNull();
        
        return this;
    }
    
    @Override
    public Result<DATA> _filter(Function<? super DATA, Boolean> predicate) {
        return filter(predicate::apply);
    }
    
    public Result<DATA> otherwise(DATA otherwiseValue) {
        DATA value = getValue();
        if (value == null)
            return ImmutableResult.of(otherwiseValue, null);
        return this;
    }
    
    public Result<DATA> otherwiseGet(Supplier<DATA> otherwiseSupplier) {
        DATA value = getValue();
        if (value == null)
            return this;
        
        return ImmutableResult.from((Supplier<DATA>)()->{
            return otherwiseSupplier.get();
        });
    }
    
    public Result<DATA> otherwiseGet(FailableFunc0<DATA> otherwiseSupplier) {
        DATA value = getValue();
        if (value != null)
            return this;
        
        return ImmutableResult.from((FailableFunc0<DATA>)()->{
            return otherwiseSupplier.get();
        });
    }
    
    public DATA orElse(DATA elseValue) {
        DATA value = getValue();
        if (value == null)
            return elseValue;
        
        return value;
    }
    
    public DATA orElseGet(Supplier<? extends DATA> elseSupplier) {
        DATA value = getValue();
        if (value == null)
            return elseSupplier.get();
        
        return value;
    }
    
    public MayBe<MayBe<DATA>> asMayBe() {
        return processData((isValue, value, exception) -> {
            if (exception != null)
                return MayBe.nothing();
            
            return MayBe.of(MayBe.of(value));
        });
    }
    
    public MayBe<DATA> getMayBeValue() {
        DATA value = getValue();
        return MayBe.of(value);
    }
    
    public MayBe<Exception> getMayBeException() {
        val exception = getException();
        return MayBe.of(exception);
    }
    
    public DATA orThrow() throws Exception {
        val data = processData((isValue, value, exception) -> {
            if (exception != null)
                return new ExceptionHolder(exception);
            
            return value;
        });
        
        if (data instanceof ExceptionHolder)
            throw ((ExceptionHolder)data).getException();
        
        return (DATA)data;
    }
    public DATA orThrowRuntimeException() {
        val data = processData((isValue, value, exception) -> {
            if (exception == null) {
                return value;
            }
            
            if (exception instanceof RuntimeException)
                return new ExceptionHolder((RuntimeException)exception);
            
            return new ExceptionHolder(new RuntimeException(exception));
        });
        
        if (data instanceof ExceptionHolder)
            throw (RuntimeException)((ExceptionHolder)data).getException();
        
        return (DATA)data;
    }
    public <EXCEPTION extends Exception> DATA orThrow(Function<Exception, EXCEPTION> toException) 
            throws EXCEPTION {
        val data = processData((isValue, value, exception) -> {
            if (exception == null) {
                return value;
            }
            return new ExceptionHolder(toException.apply(exception));
        });
        
        if (data instanceof ExceptionHolder)
            throw (EXCEPTION)((ExceptionHolder)data).getException();
        
        return (DATA)data;
    }
    public <RUNTIMEEXCEPTION extends RuntimeException> 
            DATA orThrowRuntimeException(Function<Exception, RUNTIMEEXCEPTION> toRuntimeException) {
        val data = processData((isValue, value, exception) -> {
            if (exception == null)
                return value;
            
            throw toRuntimeException.apply(exception);
        });
        
        if (data instanceof ExceptionHolder)
            throw (RUNTIMEEXCEPTION)((ExceptionHolder)data).getException();
        
        return (DATA)data;
    }

    @Override
    public <TARGET> Monad<Result<?>, TARGET> _of(TARGET target) {
        return ImmutableResult.of(target);
    }
    
    public Optional<DATA> toOptional() {
        return Optional.ofNullable(this.get());
    }
    
    public String toString() {
        return processData((isValue, value, exception) -> {
            if (exception == null)
                return "Result:{ Value: "     + value      + " }";
           else return "Result:{ Exception: " + exception  + " }";
        });
    }
    
    
    public static class ExceptionHolder {
        private final Exception exception;
        ExceptionHolder(Exception exception) {
            this.exception = requireNonNull(exception);
        }
        public Exception getException() {
            return exception;
        }
    }
    
}
