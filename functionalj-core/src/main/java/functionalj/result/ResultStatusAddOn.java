package functionalj.result;

import java.util.function.Consumer;
import java.util.function.Supplier;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import functionalj.functions.FuncUnit2;
import lombok.val;

public interface ResultStatusAddOn<DATA> {
    
    public static <T> Func1<Exception, Boolean> returnFalse() {
        return e -> false;
    }
    public static <T> Func1<Exception, Result<T>> returnValueException() {
        return e -> Result.ofException(e);
    }
    public static <T> Func1<Exception, Boolean> returnTrue() {
        return e -> false;
    }
    public static <T> Func1<Exception, T> throwException() {
        return e -> { throw e; };
    }
    
    public <T> T mapData(Func1<Exception, T> exceptionGet, Func2<DATA, Exception, T> processor);
    
    public <T> Result<T> mapValue(Func2<DATA, Exception, Result<T>> processor);
    
    public Result<DATA> asResult();
    
    
    public default Result<DATA> useData(FuncUnit2<DATA, Exception> processor) {
        mapData(throwException(), (value, exception)->{
            processor.accept(value, exception);
            return null;
        });
        return asResult();
    }
    
    //== Present ==
    
    public default boolean isPresent() {
        return mapData(
                returnFalse(),
                (value, exception) -> {
                    return (value != null);
                }
        );
    }
    
    // No exception -> can be null
    public default Result<DATA> ifPresent(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            if (value != null)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenPresentGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (value != null)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenPresentUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (value != null)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                }
        );
    }
    
    //== NotPresent ==
    
    public default boolean isNotPresent() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return (value == null);
                }
        );
    }
    
    public default Result<DATA> ifNotPresent(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            if (value == null)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenNotPresentGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (value == null)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenNotPresentUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (value == null)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                }
        );
    }
    
    //== Null ==
    
    public default boolean isNull() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return (value == null) && (exception == null);
                }
        );
    }
    
    public default Result<DATA> ifNull(Runnable runnable) {
        useData((value, exception) -> {
            if ((value == null) && (exception == null))
                runnable.run();
        });
        return asResult();
    }
    public default Result<DATA> ifNull(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            if ((value == null) && (exception == null))
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenNullGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if ((value == null) && (exception == null))
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenNullUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if ((value == null) && (exception == null))
                        return Result.of(fallbackValue);
                    
                    return asResult();
                }
        );
    }
    
    //== NotNull ==
    
    public default boolean isNotNull() {
        return isPresent();
    }
    
    public default Result<DATA> ifNotNull(Consumer<? super DATA> consumer) {
        return ifNotPresent(consumer);
    }
    
    public default Result<DATA> whenNotNullGet(Supplier<? extends DATA> fallbackSupplier) {
        return whenNotNullGet(fallbackSupplier);
    }
    
    public default Result<DATA> whenNotNullUse(DATA fallbackValue) {
        return whenNotNullUse(fallbackValue);
    }
    
    //== Value ==
    
    public default boolean isValue() {
        return mapData(
                returnFalse(),
                (value, exception) -> {
                    return (exception == null);
                }
        );
    }
    
    public default Result<DATA> ifValue(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            if (exception == null)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenValueGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (exception == null)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenValueUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (exception == null)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                }
        );
    }
    
    //== NotValue ==
    
    public default boolean isNotValue() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return (exception != null);
                }
        );
    }
    
    public default Result<DATA> ifNotValue(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            if (exception != null)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenNotValueGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (exception != null)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenNotValueUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (exception != null)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                });
    }
    
    //== Valid ==
    
    public default boolean isValid() {
        return isValue();
    }
    
    public default Result<DATA> ifValid(Consumer<? super DATA> consumer) {
        return ifValue(consumer);
    }
    
    public default Result<DATA> whenValidGet(Supplier<? extends DATA> fallbackSupplier) {
        return whenValueGet(fallbackSupplier);
    }
    
    public default Result<DATA> whenValidUse(DATA fallbackValue) {
        return whenValueUse(fallbackValue);
    }
    
    //== Invalid ==
    
    public default boolean isInvalid() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return (exception instanceof ValidationException);
                }
        );
    }
    
    public default Result<DATA> ifInvalid(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            if (exception instanceof ValidationException)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenInvalidGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (exception instanceof ValidationException)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenInvalidUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (exception instanceof ValidationException)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                });
    }
    
    //== Exist ==
    
    public default boolean isExist() {
        return isValue();
    }
    
    public default Result<DATA> ifExist(Consumer<? super DATA> consumer) {
        return ifValue(consumer);
    }
    
    public default Result<DATA> whenExistGet(Supplier<? extends DATA> fallbackSupplier) {
        return whenValueGet(fallbackSupplier);
    }
    
    public default Result<DATA> whenExistUse(DATA fallbackValue) {
        return whenValueUse(fallbackValue);
    }
    
    //== NotExist ==
    
    public default boolean isNotExist() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return (exception instanceof ResultNotExistException);
                }
        );
    }
    
    public default Result<DATA> ifNotExist(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            if (exception instanceof ResultNotExistException)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenNotExistGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (exception instanceof ResultNotExistException)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenNotExistUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (exception instanceof ResultNotExistException)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                });
    }
    
    //== Exception ==
    
    public default boolean isException() {
        return mapData(
                returnFalse(),
                (value, exception) -> {
                    return (exception != null);
                }
        );
    }
    
    public default Result<DATA> ifException(Consumer<? super Exception> consumer) {
        useData((value, exception) -> {
            if (exception != null)
                consumer.accept(exception);
        });
        return asResult();
    }
    
    public default Result<DATA> whenExceptionGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (exception != null) {
                        Result<DATA> newValue = Result.from(fallbackSupplier);
                        return newValue;
                    }
                    
                    return asResult();
                }
        );
    }
    public default Result<DATA> whenExceptionApply(Func1<Exception,? extends DATA> fallbackMapper) {
        return mapValue(
                (value, exception) -> {
                    if (exception != null)
                        return Result.from(()->{
                            val newValue = fallbackMapper.apply(exception);
                            return newValue;
                        });
                    
                    return asResult();
                }
                );
    }
    
    public default Result<DATA> whenExceptionUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (exception != null)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                }
        );
    }
    
    //== Problem ==
    
    public default boolean isProblem() {
        return mapData(
                returnFalse(),
                (value, exception) -> {
                    return (exception != null)
                       && !(exception instanceof ResultNotReadyException)
                       && !(exception instanceof ResultCancelledException)
                       && !(exception instanceof ResultNotExistException);
                }
        );
    }
    
    public default Result<DATA> ifProblem(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            val isProblem = (exception != null)
                    && !(exception instanceof ResultNotReadyException)
                    && !(exception instanceof ResultCancelledException)
                    && !(exception instanceof ResultNotExistException);
            if (isProblem)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenProblemGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    val isProblem = (exception != null)
                            && !(exception instanceof ResultNotReadyException)
                            && !(exception instanceof ResultCancelledException)
                            && !(exception instanceof ResultNotExistException);
                    if (isProblem)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenProblemUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    val isProblem = (exception != null)
                            && !(exception instanceof ResultNotReadyException)
                            && !(exception instanceof ResultCancelledException)
                            && !(exception instanceof ResultNotExistException);
                    if (isProblem)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                }
        );
    }
    
    //== Cancelled ==
    
    public default boolean isCancelled() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return (exception instanceof ResultCancelledException);
                }
        );
    }
    
    public default Result<DATA> ifCancelled(Runnable runnable) {
        useData((value, exception) -> {
            if (exception instanceof ResultCancelledException)
                runnable.run();
        });
        return asResult();
    }
    
    public default Result<DATA> whenCancelledGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    if (exception instanceof ResultCancelledException)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenCancelledUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    if (exception instanceof ResultCancelledException)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                });
    }
    
    //== Ready ==
    
    public default boolean isReady() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return !(exception instanceof ResultNotReadyException)
                        && !(exception instanceof ResultNotAvailableException);
                }
        );
    }
    
    public default Result<DATA> ifReady(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            val isReady = !(exception instanceof ResultNotReadyException)
                       && !(exception instanceof ResultNotAvailableException);
            if (isReady)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    val isReady = !(exception instanceof ResultNotReadyException)
                               && !(exception instanceof ResultNotAvailableException);
                    if (isReady)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenReadyUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    val isReady = !(exception instanceof ResultNotReadyException)
                               && !(exception instanceof ResultNotAvailableException);
                    if (isReady)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                });
    }
    
    //== Not Ready ==
    
    public default boolean isNotReady() {
        return mapData(
                returnTrue(),
                (value, exception) -> {
                    return (exception instanceof ResultNotReadyException)
                        || (exception instanceof ResultNotAvailableException);
                }
        );
    }
    
    public default Result<DATA> ifNotReady(Consumer<? super DATA> consumer) {
        useData((value, exception) -> {
            val isNotReady = (exception instanceof ResultNotReadyException)
                          || (exception instanceof ResultNotAvailableException);
            if (isNotReady)
                consumer.accept(value);
        });
        return asResult();
    }
    
    public default Result<DATA> whenNotReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(
                (value, exception) -> {
                    val isNotReady = (exception instanceof ResultNotReadyException)
                                  || (exception instanceof ResultNotAvailableException);
                    if (isNotReady)
                        return Result.from(fallbackSupplier);
                    
                    return asResult();
                }
        );
    }
    
    public default Result<DATA> whenNotReadyUse(DATA fallbackValue) {
        return mapValue(
                (value, exception) -> {
                    val isNotReady = (exception instanceof ResultNotReadyException)
                                  || (exception instanceof ResultNotAvailableException);
                    if (isNotReady)
                        return Result.of(fallbackValue);
                    
                    return asResult();
                });
    }
    
}
