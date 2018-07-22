package functionalj.types.result;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.functions.Func0;
import functionalj.functions.Func3;
import functionalj.kinds.Comonad;
import functionalj.kinds.Filterable;
import functionalj.kinds.Functor;
import functionalj.kinds.Monad;
import functionalj.types.MayBe;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;
import tuple.Tuple2;


public abstract class Result<DATA>
                    implements
                        Functor<Result<?>, DATA>, 
                        Monad<Result<?>, DATA>, 
                        Comonad<Result<?>, DATA>,
                        Filterable<Result<?>, DATA>,
                        Nullable<DATA>{

    private static final ImmutableResult NULL = new ImmutableResult<>(null, null);
    
    public static <D> ImmutableResult<D> of(D value) {
        return new ImmutableResult<D>(value, null);
    }
    public static <D> ImmutableResult<D> of(D value, Exception exception) {
        return new ImmutableResult<D>(value, exception);
    }
    public static <D> ImmutableResult<D> ofResult(Result<D> result) {
        if (result instanceof ImmutableResult)
            return (ImmutableResult<D>)result;
        
        if (result == null)
            return ImmutableResult.ofNull();
        
        val data      = result.getData();
        val value     = (data instanceof ExceptionHolder) ? null                                   : (D)data;
        val exception = (data instanceof ExceptionHolder) ? ((ExceptionHolder)data).getException() : null;
        return ImmutableResult.of(value, exception);
    }
    public static <D> ImmutableResult<D> ofTuple(Tuple2<D, Exception> tuple) {
        if (tuple == null)
            return ImmutableResult.ofNull();
            
        return ImmutableResult.of(tuple._1(), tuple._2());
    }
    public static <D> ImmutableResult<D> from(Supplier<D> supplier) {
        try {
            return ImmutableResult.of(supplier.get());
        } catch (RuntimeException e) {
            return ImmutableResult.of(null, e);
        }
    }
    public static <D> ImmutableResult<D> from(Func0<D> supplier) {
        try {
            return ImmutableResult.of(supplier.get());
        } catch (Exception e) {
            return ImmutableResult.of(null, e);
        }
    }
    public static <D> ImmutableResult<D> ofNull() {
        return (ImmutableResult<D>)NULL;
    }
    
    
    Result() {}
    
    protected abstract Object getData();
    
    
    protected final <T> T processData(Function<Exception, T> defaultGet, Func3<Boolean, DATA, Exception, T> processor) {
        try {
            val data      = getData();
            val isValue   = ((data == null) || !(data instanceof ExceptionHolder));
            val value     = isValue ? (DATA)data : null;
            val exception = isValue ? null         : ((ExceptionHolder)data).getException();
            
            return processor.apply(isValue, value, exception);
        } catch (Exception cause) {
            return defaultGet.apply(cause);
        }
    }
    
    public final DATA getValue() {
        val data = getData();
        return (data instanceof ExceptionHolder) ? null : (DATA)data;
    }
    
    public final Exception getException() {
        val data = getData();
        return (data instanceof ExceptionHolder) ? ((ExceptionHolder)data).getException() : null;
    }
    
    @Override
    public final DATA get() {
        return getValue();
    }
    
    @Override
    public final DATA _extract() {
        return getValue();
    }
    
//    @Override
//    public final DATA _1() {
//        return getValue();
//    }
//    
//    @Override
//    public final Exception _2() {
//        return getException();
//    }
    
    public final Tuple2<DATA, Exception> asTuple() {
        return new Tuple2<DATA, Exception>() {
            @Override public final DATA _1()      { return getValue();     }
            @Override public final Exception _2() { return getException(); }
        };
    }

    @Override
    public <TARGET> Monad<Result<?>, TARGET> _of(TARGET target) {
        return ImmutableResult.of(target);
    }
    
    public final boolean isImmutable() {
        return this instanceof ImmutableResult;
    }
    
    public final boolean isNotImmutable() {
        return !(this instanceof ImmutableResult);
    }
    
    public final ImmutableResult<DATA> toImmutable() {
        return ImmutableResult.ofResult(this);
    }
    
    public final Optional<DATA> toOptional() {
        return Optional.ofNullable(this.get());
    }
    
    public final MayBe<MayBe<DATA>> asMayBe() {
        return processData(
                e -> MayBe.nothing(),
                (isValue, value, exception) -> {
                    if (exception != null)
                        return MayBe.nothing();
                    
                    return MayBe.of(MayBe.of(value));
                });
    }
    
    public final MayBe<DATA> getMayBeValue() {
        return processData(
                e -> MayBe.nothing(),
                (isValue, value, exception) -> {
                    return MayBe.of(value);
                });
    }
    
    public final MayBe<Exception> getMayBeException() {
        return processData(
                e -> MayBe.nothing(),
                (isValue, value, exception) -> {
                    return MayBe.of(exception);
                });
    }
    
    @Override
    public final <TARGET> Result<TARGET> _map(Function<? super DATA, TARGET> mapper) {
        return new ResultDerived<>(this, (s, e) -> (s == null) ? null : mapper.apply(s));
    }
    
    @Override
    public final <TARGET> Result<TARGET> map(Function<? super DATA, TARGET> mapper) {
        return _map(mapper);
    }
    
    public final Result<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return _map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    
    public final <TARGET> Result<TARGET> map(BiFunction<DATA, Exception, TARGET> mapper) {
        return new ResultDerived<>(this, (s, e) -> mapper.apply(s, e));
    }

    public final <TARGET> Result<TARGET> mapException(Function<? super Exception, ? extends Exception> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            throw mapper.apply(e);
        });
    }
    
    @Override
    public final <TARGET> Result<TARGET> _flatMap(Function<? super DATA, Monad<Result<?>, TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            if (s == null)
                return null;
            
            val monad = (Result<TARGET>)mapper.apply(s);
            return monad.orThrow();
        });
    }
    
    public final <TARGET> Result<TARGET> flatMap(BiFunction<DATA, Exception, Monad<Result<?>, TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            if (s == null)
                return null;
            
            val monad = (Result<TARGET>)mapper.apply(s, e);
            return monad.orThrow();
        });
    }
    
    @Override
    public final <TARGET> Result<TARGET> flatMap(Function<? super DATA, ? extends Nullable<TARGET>> mapper) {
        return new ResultDerived<>(this, (s, e) -> {
            if (s == null)
                return null;
            
            val monad = (Nullable<TARGET>)mapper.apply(s);
            return monad.orElse(null);
        });
    }
    
    // TODO - filterIn, peekIn
    
    // asPeek, checkPeek
    // asMap -- all with a way to show error
    // case
    
    public final Result<DATA> filter(Predicate<? super DATA> theCondition) {
        DATA value = get();
        if (value == null)
            return this;
        
        val isPass = theCondition.test(value);
        if (!isPass)
            return ImmutableResult.ofNull();
        
        return this;
    }
    
    public final <T extends DATA> Result<DATA> filter(Class<T> clzz) {
        return filter(clzz::isInstance);
    }
    public final <T extends DATA> Result<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        DATA value = get();
        if (value == null)
            return this;
        
        if (clzz.isInstance(value))
            return this;

        val target = clzz.cast(value);
        val isPass = theCondition.test(target);
        if (!isPass)
            return ImmutableResult.ofNull();
        
        return this;
    }
    public final <T> Result<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        DATA value = get();
        if (value == null)
            return this;

        val target = mapper.apply(value);
        val isPass = theCondition.test(target);
        if (!isPass)
            return ImmutableResult.ofNull();
        
        return this;
    }
    
    @Override
    public final Result<DATA> _filter(Function<? super DATA, Boolean> predicate) {
        return filter(predicate::apply);
    }
    
    @Override
    public final Result<DATA> peek(Consumer<? super DATA> theConsumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (value != null)
                        theConsumer.accept(value);
                    return this;
                });
    }
    
    public final <T extends DATA> Result<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if ((value != null) && clzz.isInstance(value)) {
                        val target = clzz.cast(value);
                        theConsumer.accept(target);
                    }
                    return this;
                });
    }
    public final Result<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if ((value != null) && selector.test(value)) {
                        theConsumer.accept(value);
                    }
                    return this;
                });
    }
    public final <T> Result<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (value != null) {
                        val target = mapper.apply(value);
                        theConsumer.accept(target);
                    }
                    return this;
                });
    }
    public final <T> Result<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (value == null) 
                        return this;
                    
                    val target = mapper.apply(value);
                    if (selector.test(target))
                        theConsumer.accept(target);
                    
                    return this;
                });
    }
    
    public final Result<DATA> peek(BiConsumer<? super DATA, ? super Exception> theConsumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (value != null)
                        theConsumer.accept(value, exception);
                    return this;
                });
    }
    
    public final boolean isValue() {
        return processData(
                e -> false,
                (isValue, value, exception) -> {
                    return isValue;
                });
    }
    
    // No exception -> can be null
    public final Result<DATA> ifValue(Consumer<? super DATA> consumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (isValue)
                        consumer.accept(value);
                    
                    return this;
                });
    }
    
    public final boolean isNotNull() {
        return processData(
                e -> true,
                (isValue, value, exception) -> {
                    return (value != null);
                });
    }
    
    // No exception -> non-null value (aka ifPresent)
    public final Result<DATA> ifNotNull(Consumer<? super DATA> consumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (value != null)
                        consumer.accept(value);
                    
                    return this;
                });
    }
    
    public final boolean isNull() {
        return processData(
                e -> true,
                (isValue, value, exception) -> {
                    return (isValue && (value == null));
                });
    }
    
    public final Result<DATA> ifNull(Runnable action) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (isValue && (value == null))
                        action.run();
                    
                    return this;
                });
    }
    
    public final boolean isException() {
        val exception = getException();
        return exception != null;
    }
    
    public final Result<DATA> ifException(Consumer<? super Exception> consumer) {
        return processData(
                e -> this,
                (isValue, value, exception) -> {
                    if (exception != null)
                        consumer.accept(exception);
                    
                    return this;
                });
    }
    
    public final boolean isAvailable() {
        val exception = getException();
        return !(exception instanceof ResultNotAvailableException);
    }
    
    public final Result<DATA> ifAvailable(BiConsumer<? super DATA, ? super Exception> consumer) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (!(exception instanceof ResultNotAvailableException))
                        consumer.accept(value, exception);
                    
                    return this;
                });
    }
    
    public final boolean isNotAvailable() {
        return processData(
                e -> true,
                (isValue, value, exception)->{
                    return (exception instanceof ResultNotAvailableException);
                });
    }
    
    public final Result<DATA> ifNotAvailable(Runnable action) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ResultNotAvailableException)
                        action.run();
                    
                    return this;
                });
    }
    
    public final boolean isNotReady() {
        return processData(
                e -> true,
                (isValue, value, exception)->{
                    return (exception instanceof ResultNotReadyException);
                });
    }
    
    public final Result<DATA> ifNotReady(Runnable action) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ResultNotReadyException)
                        action.run();
                    
                    return this;
                });
    }
    
    public final boolean isCancelled() {
        return processData(
                e -> true,
                (isValue, value, exception)->{
                    return (exception instanceof ResultCancelledException);
                });
    }
    
    public final Result<DATA> ifCancelled(Runnable action) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ResultCancelledException)
                        action.run();
                    
                    return this;
                });
    }
    
    public final boolean isValid() {
        return processData(
                e -> false,
                (isValue, value, exception)->{
                    return !(exception instanceof ValidationException);
                });
    }
    public final Result<DATA> ifValid(Runnable action) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ValidationException)
                        action.run();
                    
                    return this;
                });
    }
    public final Result<DATA> ifValid(Consumer<DATA> consumer) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (!isValue)
                        return this;
                    
                    if (value != null)
                        consumer.accept(value);
                    
                    return this;
                });
    }
    public final boolean isInvalid() {
        return processData(
                e -> true,
                (isValue, value, exception)->{
                    return (exception instanceof ValidationException);
                });
    }
    public final Result<DATA> ifInvalid(Runnable runnable) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ValidationException) 
                        runnable.run();
                    
                    return this;
                });
    }
    public final Result<DATA> ifInvalid(Consumer<ValidationException> consumer) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ValidationException) 
                        consumer.accept((ValidationException)exception);
                    
                    return this;
                });
    }
    
    public final Result<DATA> validateNotNull() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (isValue && (value == null))
                        return Result.of((DATA)null, new ValidationException(new NullPointerException()));
                    
                    return this;
                });
    }
    public final Result<DATA> validateNotNull(String message) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (value == null) 
                        return Result.of((DATA)null, new ValidationException(message));
                    
                    return this;
                });
    }
    public final Result<DATA> validateUnavailable() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                if (exception instanceof ResultNotAvailableException)
                    return Result.of((DATA)null, new ValidationException(exception));
                
                return this;
            });
    }
    public final Result<DATA> validateNotReady() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ResultNotAvailableException)
                        return Result.of((DATA)null, new ValidationException(exception));
                    
                    return this;
                });
    }
    public final Result<DATA> validateResultCancelled() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ResultCancelledException)
                        return Result.of((DATA)null, new ValidationException(exception));
                    
                    return this;
                });
    }
    public final Result<DATA> validate(Predicate<? super DATA> checker, String message) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (value == null) 
                        return this;
                    try {
                        if (checker.test(value))
                            return this;
                        
                        return Result.of((DATA)null, new ValidationException(message));
                    } catch (Exception e) {
                        return Result.of((DATA)null, new ValidationException(message, e));
                    }
                });
    }
    public final <T> Result<DATA> validate(Function<? super DATA, T> mapper, Predicate<? super T> checker, String message) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (value == null) 
                        return this;
                    try {
                        val target = mapper.apply(value);
                        if (checker.test(target))
                            return this;
                        
                        return Result.of((DATA)null, new ValidationException(message));
                        
                    } catch (Exception e) {
                        return Result.of((DATA)null, new ValidationException(message, e));
                    }
                });
    }
    public final <T> Result<DATA> validate(Validator<DATA, T> validator) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (value == null) 
                        return this;
                    try {
                        val mapper  = validator.mapper();
                        val target  = mapper.apply(value);
                        val checker = validator.checker();
                        if (checker.test(target))
                            return this;
                        
                        val newError         = validator.newError();
                        val genException     = newError.apply(value, target, mapper, checker);
                        val notNullException = requireNonNull(genException);
                        return Result.of((DATA)null, notNullException);
                        
                    } catch (Exception e) {
                        return Result.of((DATA)null, new ValidationException(e));
                    }
                });
    }
    
    public final <T extends Result<DATA>> T castTo(Class<T> clzz) {
        return clzz.cast(this);
    }
    
    public final <D extends Validatable<D, ?>> Valid<D> toValidValue(Function<DATA, D> mapper) {
        return processData(
                e -> (Valid<D>)new Valid<D>((D)null, (e instanceof ValidationException) ? (ValidationException)e : e),
                (isValue, value, exception)->{
                    if (isValue) {
                        val target = mapper.apply(value);
                        Valid<D> valueOf = Valid.valueOf((D)target);
                        return valueOf;
                    }
                    
                    Valid<D> valueOf = new Valid<D>((D)null, exception);
                    return valueOf;
                });
    }
    
    public final Result<DATA> ensureNotNull() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (value != null)
                        return this;
                    if (!isValue)
                        return this;
                    
                    return ImmutableResult.of(null, new NullPointerException());
                });
    }
    
    
    public final Result<DATA> defaultTo(DATA defaultValue) {
       return processData(
               e -> this,
               (isValue, value, exception)->{
                   if (!isValue)
                       return this;
                   if (value != null)
                       return this;
                   
                   return Result.of(defaultValue);
               });
    }
    
    public final Result<DATA> defaultFrom(Supplier<DATA> defaultSupplier) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (!isValue)
                        return this;
                    if (value != null)
                        return this;
                    try {
                        return Result.of(defaultSupplier.get());
                    } catch (Exception e) {
                        return Result.ofNull();
                    }
                });
    }

    public final Result<DATA> otherwiseNull() {
        val exception = getException();
        if (exception == null)
            return this;
        
        return Result.ofNull();
    }
    
    public final Result<DATA> otherwise(DATA otherwiseValue) {
        val exception = getException();
        if (exception == null)
            return this;
        
        return ImmutableResult.of(otherwiseValue, null);
    }
    
    public final Result<DATA> otherwiseGet(Supplier<DATA> otherwiseSupplier) {
        val exception = getException();
        if (exception == null)
            return this;
        
        return ImmutableResult.from((Supplier<DATA>)()->{
            return otherwiseSupplier.get();
        });
    }
    
    public final Result<DATA> otherwiseGet(Func0<DATA> otherwiseSupplier) {
        val exception = getException();
        if (exception == null)
            return this;
        
        return ImmutableResult.from((Func0<DATA>)()->{
            return otherwiseSupplier.getUnsafe();
        });
    }
    
    public final Result<DATA> otherwiseInvalid() {
        val exception = getException();
        if (exception == null)
            return this;
        if (exception instanceof ValidationException)
            return this;
        
        return Result.of(null, new ValidationException(new NullPointerException()));
    }
    
    public final DATA orElse(DATA elseValue) {
        val value = getValue();
        if (value == null)
            return elseValue;
        
        return value;
    }
    
    public final DATA orElseGet(Supplier<? extends DATA> elseSupplier) {
        val value = getValue();
        if (value == null)
            return elseSupplier.get();
        
        return value;
    }
    
    public final DATA orThrow() throws Exception {
        val data = processData(
                e -> new ExceptionHolder(e),
                (isValue, value, exception) -> {
                    if (exception != null)
                        return new ExceptionHolder(exception);
                    
                    return value;
                });
        
        if (data instanceof ExceptionHolder)
            throw ((ExceptionHolder)data).getException();
        
        return (DATA)data;
    }
    public final DATA orThrowRuntimeException() {
        val data = processData(
                e -> new ExceptionHolder(e),
                (isValue, value, exception) -> {
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
    public final <EXCEPTION extends Exception> DATA orThrow(Function<Exception, EXCEPTION> toException) 
            throws EXCEPTION {
        val data = processData(
                e -> new ExceptionHolder(e),
                (isValue, value, exception) -> {
                    if (exception == null) {
                        return value;
                    }
                    return new ExceptionHolder(toException.apply(exception));
                });
        
        if (data instanceof ExceptionHolder)
            throw (EXCEPTION)((ExceptionHolder)data).getException();
        
        return (DATA)data;
    }
    public final <RUNTIMEEXCEPTION extends RuntimeException> 
            DATA orThrowRuntimeException(Function<Exception, RUNTIMEEXCEPTION> toRuntimeException) {
        val data = processData(
                e -> new ExceptionHolder(e),
                (isValue, value, exception) -> {
                    if (exception == null)
                        return value;
                    
                    throw toRuntimeException.apply(exception);
                });
        
        if (data instanceof ExceptionHolder)
            throw (RUNTIMEEXCEPTION)((ExceptionHolder)data).getException();
        
        return (DATA)data;
    }
    
    public String toString() {
        return processData(
                e -> "Result:{ Exception: " + e  + " }",
                (isValue, value, exception) -> {
                    if (exception == null)
                        return "Result:{ Value: "     + value      + " }";
                   else return "Result:{ Exception: " + exception  + " }";
                });
    }
    
    
    public final static class ExceptionHolder {
        private final Exception exception;
        ExceptionHolder(Exception exception) {
            this.exception = requireNonNull(exception);
        }
        public final Exception getException() {
            return exception;
        }
    }
    
}
