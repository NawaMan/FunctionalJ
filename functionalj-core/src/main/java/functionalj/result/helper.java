package functionalj.result;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit2;
import lombok.val;

class helper {
    
    static <DATA> Func2<DATA, Exception, Boolean> processIs(Predicate<ResultStatus> statusCheck) {
        return (DATA value, Exception exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            return statusCheck.test(status);
        };
    }
    
    static <DATA> FuncUnit2<DATA, Exception> processIf(Predicate<ResultStatus> statusCheck, Consumer<? super DATA> consumer) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                consumer.accept(value);
        };
    }
    static <DATA> FuncUnit2<DATA, Exception> processIf(Predicate<ResultStatus> statusCheck, BiConsumer<? super DATA, ? super Exception> consumer) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                consumer.accept(value, exception);
        };
    }
    static <DATA> FuncUnit2<DATA, Exception> processIf(Predicate<ResultStatus> statusCheck, Runnable runnable) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                runnable.run();
        };
    }
    static <DATA> FuncUnit2<DATA, Exception> processIfException(Predicate<ResultStatus> statusCheck, Consumer<? super Exception> consumer) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                consumer.accept(exception);
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenUse(
            Predicate<ResultStatus> statusCheck,
            Result<DATA>            result,
            DATA                    fallbackValue) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.valueOf(fallbackValue);
            
            return result;
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenGet(
            Predicate<ResultStatus>  statusCheck,
            Result<DATA>             result,
            Supplier<? extends DATA> fallbackSupplier) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.of(fallbackSupplier);
            
            return result;
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenApply(
            Predicate<ResultStatus>                  statusCheck,
            Result<DATA>                             result,
            Func1<? super Exception, ? extends DATA> recoverFunction) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.of(()->recoverFunction.apply(exception));
            
            return result;
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenApply(
            Predicate<ResultStatus>                        statusCheck,
            Result<DATA>                                   result,
            Func2<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return (value, exception) -> {
            val status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.of(()->recoverFunction.apply(value, exception));
            
            return result;
        };
    }
    
}
