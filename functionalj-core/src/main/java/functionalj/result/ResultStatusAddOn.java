// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.result;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.Func0;
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
    
    public <T> Result<T> mapValue(BiFunction<DATA, Exception, Result<T>> processor);
    
    public Result<DATA> asResult();
    
    
    public default Result<DATA> useData(FuncUnit2<DATA, Exception> processor) {
        mapData(throwException(), (value, exception)->{
            processor.accept(value, exception);
            return null;
        });
        return asResult();
    }
    
    //== Status ==
    
    public default boolean isStatus(ResultStatus status) {
        return mapData(returnFalse(), Helper.processIs(status::equals));
    }
    
    public default Result<DATA> ifStatusRun(ResultStatus status, Runnable runnable) {
        useData(Helper.processIf(status::equals, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifStatusAccept(ResultStatus status, Consumer<? super DATA> consumer) {
        useData(Helper.processIf(status::equals, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenStatusUse(ResultStatus status, DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(status::equals, asResult(), fallbackValue));
    }
    public default Result<DATA> whenStatusGet(ResultStatus status, Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(status::equals, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenStatusApply(ResultStatus status, BiFunction<DATA, ? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(status::equals, asResult(), recoverFunction));
    }
    
    //== Present ==
    
    public default boolean isPresent() {
        return mapData(returnFalse(), Helper.processIs(ResultStatus::isPresent));
    }
    
    public default Result<DATA> ifPresent(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isPresent, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifPresent(Consumer<? super DATA> consumer) {
        useData(Helper.processIf(ResultStatus::isPresent, consumer));
        return asResult();
    }
    
    //== Absent ==
    
    public default boolean isAbsent() {
        return mapData(returnFalse(), Helper.processIs(ResultStatus::isAbsent));
    }
    
    public default Result<DATA> ifAbsent(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isAbsent, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifAbsent(Consumer<? super DATA> consumer) {
        useData(Helper.processIf(ResultStatus::isAbsent, consumer));
        return asResult();
    }
    
    public default Result<DATA> ifAbsent(BiConsumer<? super DATA, ? super Exception> consumer) {
        useData(Helper.processIf(ResultStatus::isAbsent, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenAbsentUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isAbsent, asResult(), fallbackValue));
    }
    public default Result<DATA> whenAbsentGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isAbsent, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenAbsentApply(BiFunction<DATA, ? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isAbsent, asResult(), recoverFunction));
    }
    
    //== Null ==
    
    public default boolean isNull() {
        return mapData(returnTrue(), Helper.processIs(ResultStatus::isNull));
    }
    
    public default Result<DATA> ifNull(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isNull, runnable));
        return asResult();
    }
    
    public default Result<DATA> whenNullUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isNull, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNullGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isNull, asResult(), fallbackSupplier));
    }
    
    //== Value ==
    
    public default boolean isValue() {
        return mapData(returnFalse(), Helper.processIs(ResultStatus::isValue));
    }
    
    public default Result<DATA> ifValue(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isValue, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifValue(Consumer<? super DATA> consumer) {
        useData(Helper.processIf(ResultStatus::isValue, consumer));
        return asResult();
    }
    
    //== NotValue ==
    
    public default boolean isNotValue() {
        return mapData(returnFalse(), Helper.processIs(ResultStatus::isNotValue));
    }
    
    public default Result<DATA> ifNotValue(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isNotValue, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNotValue(Consumer<? super DATA> consumer) {
        useData(Helper.processIf(ResultStatus::isNotValue, consumer));
        return asResult();
    }
    
    public default Result<DATA> ifNotValue(BiConsumer<? super DATA, ? super Exception> consumer) {
        useData(Helper.processIf(ResultStatus::isNotValue, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNotValueUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isNotValue, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNotValueGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isNotValue, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotValueApply(BiFunction<DATA, ? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isNotValue, asResult(), recoverFunction));
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
        return mapData(returnFalse(), Helper.processIs(ResultStatus::isInvalid));
    }
    
    public default Result<DATA> ifInvalid(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isInvalid, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifInvalid(Consumer<? super Exception> consumer) {
        useData(Helper.processIfException(ResultStatus::isInvalid, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenInvalidUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isInvalid, asResult(), fallbackValue));
    }
    public default Result<DATA> whenInvalidGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isInvalid, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenInvalidApply(Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isInvalid, asResult(), recoverFunction));
    }
    
    //== NotExist ==
    
    public default boolean isNotExist() {
        return mapData(returnTrue(), Helper.processIs(ResultStatus::isNotExist));
    }
    
    public default Result<DATA> ifNotExist(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isNotExist, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNotExist(Consumer<? super Exception> consumer) {
        useData(Helper.processIfException(ResultStatus::isNotExist, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNotExistUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isNotExist, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNotExistGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isNotExist, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotExistApply(Function<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isNotExist, asResult(), recoverFunction));
    }
    
    //== Exception ==
    
    public default boolean isException() {
        return mapData(returnFalse(), Helper.processIs(ResultStatus::isException));
    }
    
    public default Result<DATA> ifException(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isException, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifException(Consumer<? super Exception> consumer) {
        useData(Helper.processIfException(ResultStatus::isException, consumer));
        return asResult();
    }
    
    public default Result<DATA> ifExceptionThenPrint() {
        useData(Helper.processIfException(ResultStatus::isException, exception -> exception.printStackTrace()));
        return asResult();
    }
    public default Result<DATA> ifExceptionThenPrint(PrintStream printStream) {
        useData(Helper.processIfException(ResultStatus::isException, exception -> exception.printStackTrace(printStream)));
        return asResult();
    }
    public default Result<DATA> ifExceptionThenPrint(PrintWriter printWriter) {
        useData(Helper.processIfException(ResultStatus::isException, exception -> exception.printStackTrace(printWriter)));
        return asResult();
    }
    
    public default Result<DATA> whenExceptionUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isException, asResult(), fallbackValue));
    }
    public default Result<DATA> whenExceptionGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isException, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenExceptionApply(Function<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isException, asResult(), recoverFunction));
    }
    
    public default Result<DATA> recover(Class<? extends Throwable> problemClass, DATA fallbackValue) {
        return mapValue((data, exception)->{
            if (exception == null)
                return asResult();
            
            if (!problemClass.isInstance(exception))
                return asResult();
            
            return Result.valueOf(fallbackValue);
        });
    }
    public default Result<DATA> recover(Class<? extends Throwable> problemClass, Supplier<? extends DATA> fallbackSupplier) {
        return mapValue((data, exception)->{
            if (exception == null)
                return asResult();
            
            if (!problemClass.isInstance(exception))
                return asResult();
            
            return Result.of(Func0.from(fallbackSupplier));
        });
    }
    public default Result<DATA> recover(Class<? extends Throwable> problemClass, Func1<? super Exception,? extends DATA> recoverFunction) {
        return mapValue((data, exception)->{
            if (exception == null)
                return asResult();
            
            if (!problemClass.isInstance(exception))
                return asResult();
            
            return Result.valueOf(exception).map(recoverFunction);
        });
    }
    
    //== Cancelled ==
    
    public default boolean isCancelled() {
        return mapData(returnTrue(), Helper.processIs(ResultStatus::isCancelled));
    }
    
    public default Result<DATA> ifCancelled(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isCancelled, runnable));
        return asResult();
    }
    
    public default Result<DATA> whenCancelledUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isCancelled, asResult(), fallbackValue));
    }
    public default Result<DATA> whenCancelledGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isCancelled, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenCancelledApply(Function<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isCancelled, asResult(), recoverFunction));
    }
    
    //== Ready ==
    
    public default boolean isReady() {
        return mapData(returnTrue(), Helper.processIs(ResultStatus::isReady));
    }
    
    public default Result<DATA> ifReady(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isReady, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifReady(Consumer<? super DATA> consumer) {
        useData(Helper.processIf(ResultStatus::isReady, consumer));
        return asResult();
    }
    
    public default Result<DATA> ifReady(BiConsumer<? super DATA, ? super Exception> consumer) {
        useData(Helper.processIf(ResultStatus::isReady, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenReadyUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isReady, asResult(), fallbackValue));
    }
    public default Result<DATA> whenReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isReady, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotReadyApply(BiFunction<DATA, ? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isReady, asResult(), recoverFunction));
    }
    
    //== Not Ready ==
    
    public default boolean isNotReady() {
        return mapData(returnTrue(), Helper.processIs(ResultStatus::isNotReady));
    }
    
    public default Result<DATA> ifNotReady(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isNotReady, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNotReady(Consumer<? super Exception> consumer) {
        useData(Helper.processIfException(ResultStatus::isNotReady, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNotReadyUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isNotReady, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNotReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isNotReady, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNotReadyApply(Function<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isNotReady, asResult(), recoverFunction));
    }
    
    //== No More Result ==
    
    public default boolean isNoMore() {
        return mapData(returnTrue(), Helper.processIs(ResultStatus::isNoMore));
    }
    
    public default Result<DATA> ifNoMore(Runnable runnable) {
        useData(Helper.processIf(ResultStatus::isNoMore, runnable));
        return asResult();
    }
    
    public default Result<DATA> ifNoMore(Consumer<? super Exception> consumer) {
        useData(Helper.processIfException(ResultStatus::isNoMore, consumer));
        return asResult();
    }
    
    public default Result<DATA> whenNoMoreUse(DATA fallbackValue) {
        return mapValue(Helper.processWhenUse(ResultStatus::isNoMore, asResult(), fallbackValue));
    }
    public default Result<DATA> whenNoMoreGet(Supplier<? extends DATA> fallbackSupplier) {
        return mapValue(Helper.processWhenGet(ResultStatus::isNoMore, asResult(), fallbackSupplier));
    }
    public default Result<DATA> whenNoMoreApply(Function<? super Exception,? extends DATA> recoverFunction) {
        return mapValue(Helper.processWhenApply(ResultStatus::isNoMore, asResult(), recoverFunction));
    }
    
}
