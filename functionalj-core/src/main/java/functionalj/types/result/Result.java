package functionalj.types.result;

import static functionalj.FunctionalJ.it;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.FunctionalJ;
import functionalj.functions.Func0;
import functionalj.functions.Func3;
import functionalj.kinds.Comonad;
import functionalj.kinds.Filterable;
import functionalj.kinds.Functor;
import functionalj.kinds.Monad;
import functionalj.types.MayBe;
import functionalj.types.list.FunctionalList;
import functionalj.types.result.validator.Validator;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;
import tuple.ImmutableTuple2;
import tuple.ImmutableTuple3;
import tuple.ImmutableTuple4;
import tuple.ImmutableTuple5;
import tuple.ImmutableTuple6;
import tuple.Tuple;
import tuple.Tuple2;
import tuple.Tuple3;
import tuple.Tuple4;
import tuple.Tuple5;
import tuple.Tuple6;


public class Result<DATA>
                    implements
                        Functor<Result<?>, DATA>, 
                        Monad<Result<?>, DATA>, 
                        Comonad<Result<?>, DATA>,
                        Filterable<Result<?>, DATA>,
                        Nullable<DATA>{

    private static final Result NULL         = new Result<>(null, null);
    private static final Result NOTAVAILABLE = new Result<>(null, new ResultNotAvailableException());
    private static final Result NOTREADY     = new Result<>(null, new ResultNotReadyException());
    private static final Result CANCELLED    = new Result<>(null, new ResultCancelledException());
    
    public static <D> Result<D> of(D value) {
        return new Result<D>(value, null);
    }
    public static <D> Result<D> ofException(Exception exception) {
        return new Result<D>(null, exception);
    }
    public static <D> Result<D> ofResult(Result<D> result) {
        if (result instanceof Result)
            return (Result<D>)result;
        
        if (result == null)
            return Result.ofNull();
        
        val data      = result.getData();
        val value     = (data instanceof ExceptionHolder) ? null                                   : (D)data;
        val exception = (data instanceof ExceptionHolder) ? ((ExceptionHolder)data).getException() : null;
        if (exception != null)
            return Result.ofException(exception);
            
        return Result.of(value);
    }
    public static <D> Result<D> from(Supplier<D> supplier) {
        try {
            return Result.of(supplier.get());
        } catch (RuntimeException e) {
            return Result.ofException(e);
        }
    }
    public static <D> Result<D> from(Func0<D> supplier) {
        try {
            return Result.of(supplier.get());
        } catch (Exception e) {
            return Result.ofException(e);
        }
    }
    public static <D> Result<D> Try(Supplier<D> supplier) {
        try {
            return Result.of(supplier.get());
        } catch (RuntimeException e) {
            return Result.ofException(e);
        }
    }
    public static <D> Result<D> Try(Func0<D> supplier) {
        try {
            return Result.of(supplier.get());
        } catch (Exception e) {
            return Result.ofException(e);
        }
    }
    
    public static <D> Result<D> ofNull() {
        return (Result<D>)NULL;
    }
    public static <D> Result<D> ofNotAvailable() {
        return (Result<D>)NOTAVAILABLE;
    }
    public static <D> Result<D> ofNotReady() {
        return (Result<D>)NOTREADY;
    }
    public static <D> Result<D> ofNotReady(String message) {
        return Result.ofException(new ResultNotReadyException(message, null));
    }
    public static <D> Result<D> ofNotReady(String message, Throwable exception) {
        return Result.ofException(new ResultNotReadyException(message, exception));
    }
    public static <D> Result<D> ofCancelled() {
        return (Result<D>)CANCELLED;
    }
    public static <D> Result<D> ofCancelled(String message) {
        return Result.ofException(new ResultCancelledException(message, null));
    }
    public static <D> Result<D> ofCancelled(String message, Throwable exception) {
        return Result.ofException(new ResultCancelledException(message, exception));
    }
    

    
    private final Object data;
    
    Result(DATA value, Exception exception) {
        this.data = ((value == null) && (exception != null))
                ? new ExceptionHolder(exception)
                : value;
    }
    
    protected Object getData() {
        return data;
    }
    
    
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
    
    public final <T extends Result<DATA>> T castTo(Class<T> clzz) {
        return clzz.cast(this);
    }
    
    public final Tuple2<DATA, Exception> toTuple() {
        return new Tuple2<DATA, Exception>() {
            @Override public final DATA _1()      { return getValue();     }
            @Override public final Exception _2() { return getException(); }
        };
    }

    @Override
    public <TARGET> Monad<Result<?>, TARGET> _of(TARGET target) {
        return Result.of(target);
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
    
    @SuppressWarnings("unchecked")
    @Override
    public final <TARGET> Result<TARGET> _map(Function<? super DATA, TARGET> mapper) {
        return processData(
                e -> Result.ofNull(),
                (isValue, value, exception) -> {
                    if (value == null)
                        return (Result<TARGET>)this;
                    
                    val newValue = mapper.apply(value);
                    return new Result<TARGET>(newValue, null);
                });
    }
    
    @Override
    public final <TARGET> Result<TARGET> map(Function<? super DATA, TARGET> mapper) {
        return _map(mapper);
    }
    
    public final Result<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return _map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    
    public final <TARGET> Result<TARGET> map(BiFunction<DATA, Exception, TARGET> mapper) {
        return processData(
                e -> Result.ofNull(),
                (isValue, value, exception) -> {
                    val newValue = mapper.apply(value, exception);
                    return Result.of(newValue);
                });
    }

    public final Result<DATA> mapException(Function<? super Exception, ? extends Exception> mapper) {
        return processData(
                e -> Result.ofNull(),
                (isValue, value, exception) -> {
                    if (isValue)
                        return this;
                    
                    val newException = mapper.apply(exception);
                    return Result.ofException(newException);
                });
    }
    

    public final <T1, T2> 
        Result<Tuple2<T1, T2>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return map(each -> new ImmutableTuple2<T1, T2>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each)));
    }
    
    public final <T1, T2, T3> 
        Result<Tuple3<T1, T2, T3>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return map(each -> new ImmutableTuple3<T1, T2, T3>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each)));
    }
    
    public final <T1, T2, T3, T4> 
        Result<Tuple4<T1, T2, T3, T4>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return map(each -> new ImmutableTuple4<T1, T2, T3, T4>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each), 
                                    mapper4.apply(each)));
    }
    
    public final <T1, T2, T3, T4, T5> 
        Result<Tuple5<T1, T2, T3, T4, T5>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return map(each -> new ImmutableTuple5<T1, T2, T3, T4, T5>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each), 
                                    mapper4.apply(each), 
                                    mapper5.apply(each)));
    }
    
    public final <T1, T2, T3, T4, T5, T6> 
        Result<Tuple6<T1, T2, T3, T4, T5, T6>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return map(each -> new ImmutableTuple6<T1, T2, T3, T4, T5, T6>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each), 
                                    mapper4.apply(each), 
                                    mapper5.apply(each), 
                                    mapper6.apply(each)));
    }
    
    @Override
    public final <TARGET> Result<TARGET> _flatMap(Function<? super DATA, Monad<Result<?>, TARGET>> mapper) {
        return processData(
                e -> Result.ofNull(),
                (isValue, value, exception) -> {
                    if (!isValue)
                        return (Result<TARGET>)this;

                    val monad = mapper.apply(value);
                    return (Result<TARGET>)monad;
                });
    }
    
    public final <TARGET> Result<TARGET> flatMap(BiFunction<DATA, Exception, Monad<Result<?>, TARGET>> mapper) {
        return processData(
                e -> Result.ofNull(),
                (isValue, value, exception) -> {
                    val monad = mapper.apply(value, exception);
                    return (Result<TARGET>)monad;
                });
    }
    
    @Override
    public final <TARGET> Result<TARGET> flatMap(Function<? super DATA, ? extends Nullable<TARGET>> mapper) {
        return processData(
                e -> Result.ofNull(),
                (isValue, value, exception) -> {
                    if (value == null)
                        return (Result<TARGET>)this;
                    
                    val monad = (Nullable<TARGET>)mapper.apply(value);
                    return Result.of(monad.orElse(null));
                });
    }
    
    public final Result<DATA> filter(Predicate<? super DATA> theCondition) {
        DATA value = get();
        if (value == null)
            return this;
        
        val isPass = theCondition.test(value);
        if (!isPass)
            return Result.ofNull();
        
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
            return Result.ofNull();
        
        return this;
    }
    public final <T> Result<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        DATA value = get();
        if (value == null)
            return this;

        val target = mapper.apply(value);
        val isPass = theCondition.test(target);
        if (!isPass)
            return Result.ofNull();
        
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
                        return Result.ofException(new ValidationException(new NullPointerException()));
                    
                    return this;
                });
    }
    public final Result<DATA> validateNotNull(String message) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (value == null) 
                        return Result.ofException(new ValidationException(message));
                    
                    return this;
                });
    }
    public final Result<DATA> validateUnavailable() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                if (exception instanceof ResultNotAvailableException)
                    return Result.ofException(new ValidationException(exception));
                
                return this;
            });
    }
    public final Result<DATA> validateNotReady() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ResultNotAvailableException)
                        return Result.ofException(new ValidationException(exception));
                    
                    return this;
                });
    }
    public final Result<DATA> validateResultCancelled() {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (exception instanceof ResultCancelledException)
                        return Result.ofException(new ValidationException(exception));
                    
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
                        
                        return Result.ofException(new ValidationException(message));
                    } catch (Exception e) {
                        return Result.ofException(new ValidationException(message, e));
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
                        
                        return Result.ofException(new ValidationException(message));
                        
                    } catch (Exception e) {
                        return Result.ofException(new ValidationException(message, e));
                    }
                });
    }
    public final Result<DATA> validate(Validator<DATA> validator) {
        return processData(
                e -> this,
                (isValue, value, exception)->{
                    if (value == null) 
                        return this;
                    
                    return validator.validate(value);
                });
    }
    
    // TODO - Validate all -> go through and collect them all.
    
    public final <D extends Validatable<D, ?>> Valid<D> toValidValue(Function<DATA, D> mapper) {
        return processData(
                e -> (Valid<D>)new Valid<D>((D)null, e),
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
    
    @SafeVarargs
    public final Result<Tuple2<DATA, FunctionalList<ValidationException>>> validate(Validator<? super DATA> ... validators) {
        return validate(asList(validators));
    }
    
    @SuppressWarnings("unchecked")
    public final Result<Tuple2<DATA, FunctionalList<ValidationException>>> validate(List<Validator<? super DATA>> validators) {
        return (Result<Tuple2<DATA, FunctionalList<ValidationException>>>) processData(
                e -> Result.ofException(new ResultNotAvailableException()),
                (isValue, value, exception)->{
                    if (!isValue)
                        return (Result<Tuple2<DATA, FunctionalList<ValidationException>>>)this;
                    
                    val exceptions = FunctionalList.of(validators)
                        .map   (validator -> validator.validate(value))
                        .filter(result    -> result.isException())
                        .map   (result    -> result.getException())
                        .map   (error     -> ValidationException.of(error));
                    return Result.of(Tuple.of(value, exceptions));
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
                    
                    return Result.ofException(new NullPointerException());
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
        
        return Result.of(otherwiseValue);
    }
    
    public final Result<DATA> otherwiseGet(Supplier<DATA> otherwiseSupplier) {
        val exception = getException();
        if (exception == null)
            return this;
        
        return Result.from((Supplier<DATA>)()->{
            return otherwiseSupplier.get();
        });
    }
    
    public final Result<DATA> otherwiseGet(Func0<DATA> otherwiseSupplier) {
        val exception = getException();
        if (exception == null)
            return this;
        
        return Result.from((Func0<DATA>)()->{
            return otherwiseSupplier.getUnsafe();
        });
    }
    
    public final Result<DATA> otherwiseInvalid() {
        val exception = getException();
        if (exception == null)
            return this;
        if (exception instanceof ValidationException)
            return this;
        
        return Result.ofException(new ValidationException(new NullPointerException()));
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
