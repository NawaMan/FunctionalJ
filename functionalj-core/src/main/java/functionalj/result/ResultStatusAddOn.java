package functionalj.result;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit2;

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
        return mapData(returnFalse(), helper.processIs(ResultStatus::isPresent));
    }
    
    public default Result<DATA> ifPresent(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isPresent, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifPresent(Consumer<? super DATA> consumer) {
        useData(helper.processIf(ResultStatus::isPresent, consumer));
        return asResult();
    }
    
    //== Absent ==
    
    public default boolean isAbsent() {
        return mapData(returnFalse(), helper.processIs(ResultStatus::isAbsent));
    }
    
    public default Result<DATA> ifAbsent(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isAbsent, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifAbsent(Consumer<? super DATA> consumer) {
        useData(helper.processIf(ResultStatus::isAbsent, consumer));
        return asResult();
    }
    
    public default Result<DATA> ifAbsent(BiConsumer<? super DATA, ? super Exception> consumer) {
        useData(helper.processIf(ResultStatus::isAbsent, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenAbsentUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isAbsent, asResult(), fallbackValue));
    }
    public default Result<DATA> whenAbsentGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isAbsent, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenAbsentApply(Func2<DATA, ? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isAbsent, asResult(), recoverFunction));
    }
    
    //== Null ==
    
    public default boolean isNull() {
        return mapData(returnTrue(), helper.processIs(ResultStatus::isNull));
    }
    
    public default Result<DATA> ifNull(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isNull, runnable));
        return asResult();
    }
    
    public default Result<DATA> whenNullUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isNull, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNullGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isNull, asResult(), fallbackSupplier));
    }
    
    //== Value ==
    
    public default boolean isValue() {
        return mapData(returnFalse(), helper.processIs(ResultStatus::isValue));
    }
    
    public default Result<DATA> ifValue(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isValue, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifValue(Consumer<? super DATA> consumer) {
        useData(helper.processIf(ResultStatus::isValue, consumer));
        return asResult();
    }
    
    //== NotValue ==
    
    public default boolean isNotValue() {
        return mapData(returnFalse(), helper.processIs(ResultStatus::isNotValue));
    }
    
    public default Result<DATA> ifNotValue(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isNotValue, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNotValue(Consumer<? super DATA> consumer) {
        useData(helper.processIf(ResultStatus::isNotValue, consumer));
        return asResult();
    }
    
    public default Result<DATA> ifNotValue(BiConsumer<? super DATA, ? super Exception> consumer) {
        useData(helper.processIf(ResultStatus::isNotValue, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNotValueUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isNotValue, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNotValueGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isNotValue, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotValueApply(Func2<DATA, ? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isNotValue, asResult(), recoverFunction));
    }
    
    //== Valid ==
    
    public default boolean isValid() {
        return isValue();
    }
    public default Result<DATA> ifValid(Consumer<? super DATA> consumer) {
        return ifValue(consumer);
    }
    
    //== Invalid ==
    
    public default boolean isInvalid() {
        return mapData(returnFalse(), helper.processIs(ResultStatus::isInvalid));
    }
    
    public default Result<DATA> ifInvalid(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isInvalid, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifInvalid(Consumer<? super Exception> consumer) {
        useData(helper.processIfException(ResultStatus::isInvalid, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenInvalidUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isInvalid, asResult(), fallbackValue));
    }
    public default Result<DATA> whenInvalidGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isInvalid, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenInvalidApply(Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isInvalid, asResult(), recoverFunction));
    }
    
    //== NotExist ==
    
    public default boolean isNotExist() {
        return mapData(returnTrue(), helper.processIs(ResultStatus::isNotExist));
    }
    
    public default Result<DATA> ifNotExist(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isNotExist, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNotExist(Consumer<? super Exception> consumer) {
        useData(helper.processIfException(ResultStatus::isNotExist, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNotExistUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isNotExist, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNotExistGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isNotExist, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotExistApply(Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isNotExist, asResult(), recoverFunction));
    }
    
    //== Exception ==
    
    public default boolean isException() {
        return mapData(returnFalse(), helper.processIs(ResultStatus::isException));
    }
    
    public default Result<DATA> ifException(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isException, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifException(Consumer<? super Exception> consumer) {
        useData(helper.processIfException(ResultStatus::isException, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenExceptionUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isException, asResult(), fallbackValue));
    }
    public default Result<DATA> whenExceptionGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isException, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenExceptionApply(Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isException, asResult(), recoverFunction));
    }
    
    public default Result<DATA> recover(Class<? extends Throwable> problemClass, DATA fallbackValue) {
        return mapValue((data, exception)->{
            if (exception == null)
                return asResult();
            
            if (!problemClass.isInstance(exception))
                return asResult();
            
            return Result.of(fallbackValue);
        });
    }
    public default Result<DATA> recover(Class<? extends Throwable> problemClass, Supplier<? extends DATA> fallbackSupplier) {
        return mapValue((data, exception)->{
            if (exception == null)
                return asResult();
            
            if (!problemClass.isInstance(exception))
                return asResult();
            
            return Result.from(fallbackSupplier);
        });
    }
    public default Result<DATA> recover(Class<? extends Throwable> problemClass, Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue((data, exception)->{
            if (exception == null)
                return asResult();
            
            if (!problemClass.isInstance(exception))
                return asResult();
            
            return Result.of(exception).map(recoverFunction);
        });
    }
    
    //== Cancelled ==
    
    public default boolean isCancelled() {
        return mapData(returnTrue(), helper.processIs(ResultStatus::isCancelled));
    }
    
    public default Result<DATA> ifCancelled(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isCancelled, runnable));
        return asResult();
    }
    
    public default Result<DATA> ResultStatus(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isCancelled, runnable));
        return asResult();
    }
    
    
    public default Result<DATA> ResultStatus(Consumer<? super Exception> consumer) {
        useData(helper.processIfException(ResultStatus::isCancelled, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenCancelledUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isCancelled, asResult(), fallbackValue));
    }
    public default Result<DATA> whenCancelledGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isCancelled, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenCancelledApply(Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isCancelled, asResult(), recoverFunction));
    }
    
    //== Ready ==
    
    public default boolean isReady() {
        return mapData(returnTrue(), helper.processIs(ResultStatus::isReady));
    }
    
    public default Result<DATA> ifReady(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isReady, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifReady(Consumer<? super DATA> consumer) {
        useData(helper.processIf(ResultStatus::isReady, consumer));
        return asResult();
    }
    
    public default Result<DATA> ifReady(BiConsumer<? super DATA, ? super Exception> consumer) {
        useData(helper.processIf(ResultStatus::isReady, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenReadyUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isReady, asResult(), fallbackValue));
    }
    public default Result<DATA> whenReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isReady, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotReadyApply(Func2<DATA, ? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isReady, asResult(), recoverFunction));
    }
    
    //== Not Ready ==
    
    public default boolean isNotReady() {
        return mapData(returnTrue(), helper.processIs(ResultStatus::isNotReady));
    }
    
    public default Result<DATA> ifNotReady(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isNotReady, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNotReady(Consumer<? super Exception> consumer) {
        useData(helper.processIfException(ResultStatus::isNotReady, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNotReadyUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isNotReady, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNotReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isNotReady, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotReadyApply(Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isNotReady, asResult(), recoverFunction));
    }
    
    //== No More Result ==
    
    public default boolean isNoMore() {
        return mapData(returnTrue(), helper.processIs(ResultStatus::isNoMore));
    }
    
    public default Result<DATA> ifNoMore(Runnable runnable) {
        useData(helper.processIf(ResultStatus::isNoMore, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNoMore(Consumer<? super Exception> consumer) {
        useData(helper.processIfException(ResultStatus::isNoMore, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNoMoreUse(DATA fallbackValue) {
        return mapValue(helper.processWhenUse(ResultStatus::isNoMore, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNoMoreGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(helper.processWhenGet(ResultStatus::isNoMore, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNoMoreApply(Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(helper.processWhenApply(ResultStatus::isNoMore, asResult(), recoverFunction));
    }
    
}
