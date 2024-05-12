// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.promise;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.result.Result;

@SuppressWarnings({ "unchecked", "rawtypes" })
public interface PromiseStatusAddOn<DATA> {
    
    public <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper);
    
    public default Promise<DATA> useData(FuncUnit2<DATA, Exception> processor) {
        return (Promise) mapResult(result -> result.useData((FuncUnit2) processor));
    }
    
    public default Promise<DATA> whenComplete(FuncUnit2<DATA, Exception> processor) {
        return (Promise) mapResult(result -> result.useData((FuncUnit2) processor));
    }
    
    public default Promise<DATA> whenComplete(FuncUnit1<Result<DATA>> processor) {
        return (Promise) mapResult(result -> {
            processor.accept((Result) result);
            return null;
        });
    }
    
    // == Present ==
    public default Promise<DATA> ifPresent(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifPresent(runnable));
    }
    
    public default Promise<DATA> ifPresent(Consumer<? super DATA> consumer) {
        return (Promise) mapResult(result -> result.ifPresent((Consumer) consumer));
    }
    
    // == Absent ==
    public default Promise<DATA> ifAbsent(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifAbsent(runnable));
    }
    
    public default Promise<DATA> ifAbsent(Consumer<? super DATA> consumer) {
        return (Promise) mapResult(result -> result.ifAbsent((Consumer) consumer));
    }
    
    public default Promise<DATA> ifAbsent(BiConsumer<? super DATA, ? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifAbsent((BiConsumer) consumer));
    }
    
    public default Promise<DATA> whenAbsentUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenAbsentUse(fallbackValue));
    }
    
    public default Promise<DATA> whenAbsentGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenAbsentGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenAbsentApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenAbsentApply((Func2) recoverFunction));
    }
    
    // == Null ==
    public default Promise<DATA> ifNull(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifNull(runnable));
    }
    
    public default Promise<DATA> whenNullUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenNullUse(fallbackValue));
    }
    
    public default Promise<DATA> whenNullGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenNullGet(fallbackSupplier));
    }
    
    // == Value ==
    public default Promise<DATA> ifValue(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifValue(runnable));
    }
    
    public default Promise<DATA> ifValue(Consumer<? super DATA> consumer) {
        return (Promise) mapResult(result -> result.ifValue((Consumer) consumer));
    }
    
    // == NotValue ==
    public default Promise<DATA> ifNotValue(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifNotValue(runnable));
    }
    
    public default Promise<DATA> ifNotValue(Consumer<? super DATA> consumer) {
        return (Promise) mapResult(result -> result.ifNotValue((Consumer) consumer));
    }
    
    public default Promise<DATA> ifNotValue(BiConsumer<? super DATA, ? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifNotValue((BiConsumer) consumer));
    }
    
    public default Promise<DATA> whenNotValueUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenNotValueUse(fallbackValue));
    }
    
    public default Promise<DATA> whenNotValueGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenNotValueGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenNotValueApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenNotValueApply((Func2) recoverFunction));
    }
    
    // == Valid ==
    public default Promise<DATA> ifValid(Consumer<? super DATA> consumer) {
        return (Promise) mapResult(result -> result.ifValid((Consumer) consumer));
    }
    
    // == Invalid ==
    public default Promise<DATA> ifInvalid(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifInvalid(runnable));
    }
    
    public default Promise<DATA> ifInvalid(Consumer<? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifInvalid((Consumer) consumer));
    }
    
    public default Promise<DATA> whenInvalidUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenInvalidUse(fallbackValue));
    }
    
    public default Promise<DATA> whenInvalidGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenInvalidGet((Supplier) fallbackSupplier));
    }
    
    public default Promise<DATA> whenInvalidApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenInvalidApply((Func1) recoverFunction));
    }
    
    // == NotExist ==
    public default Promise<DATA> ifNotExist(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifNotExist(runnable));
    }
    
    public default Promise<DATA> ifNotExist(Consumer<? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifNotExist((Consumer) consumer));
    }
    
    public default Promise<DATA> whenNotExistUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenNotExistUse(fallbackValue));
    }
    
    public default Promise<DATA> whenNotExistGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenNotExistGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenNotExistApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenNotExistApply(recoverFunction));
    }
    
    // == Exception ==
    public default Promise<DATA> ifException(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifException(runnable));
    }
    
    public default Promise<DATA> ifException(Consumer<? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifException(consumer));
    }
    
    public default Promise<DATA> ifExceptionThenPrint() {
        return (Promise) mapResult(result -> result.ifExceptionThenPrint());
    }
    
    public default Promise<DATA> ifExceptionThenPrint(PrintStream printStream) {
        return (Promise) mapResult(result -> result.ifExceptionThenPrint(printStream));
    }
    
    public default Promise<DATA> ifExceptionThenPrint(PrintWriter printWriter) {
        return (Promise) mapResult(result -> result.ifExceptionThenPrint(printWriter));
    }
    
    public default Promise<DATA> whenExceptionUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenExceptionUse(fallbackValue));
    }
    
    public default Promise<DATA> whenExceptionGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenExceptionGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenExceptionApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenExceptionApply(recoverFunction));
    }
    
    public default Promise<DATA> recover(Class<? extends Throwable> problemClass, DATA fallbackValue) {
        return (Promise) mapResult(result -> result.recover(problemClass, fallbackValue));
    }
    
    public default Promise<DATA> recover(Class<? extends Throwable> problemClass, Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.recover(problemClass, fallbackSupplier));
    }
    
    public default Promise<DATA> recover(Class<? extends Throwable> problemClass, Func1<? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.recover(problemClass, recoverFunction));
    }
    
    // == Cancelled ==
    public default Promise<DATA> ifCancelled(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifCancelled(runnable));
    }
    
    public default Promise<DATA> whenCancelledUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenCancelledUse(fallbackValue));
    }
    
    public default Promise<DATA> whenCancelledGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenCancelledGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenCancelledApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenCancelledApply(recoverFunction));
    }
    
    // == Ready ==
    public default Promise<DATA> ifReady(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifReady(runnable));
    }
    
    public default Promise<DATA> ifReady(Consumer<? super DATA> consumer) {
        return (Promise) mapResult(result -> result.ifReady((Consumer) consumer));
    }
    
    public default Promise<DATA> ifReady(BiConsumer<? super DATA, ? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifReady((BiConsumer) consumer));
    }
    
    public default Promise<DATA> whenReadyUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenReadyUse(fallbackValue));
    }
    
    public default Promise<DATA> whenReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenReadyGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenNotReadyApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenNotReadyApply((Func2) recoverFunction));
    }
    
    // == Not Ready ==
    public default Promise<DATA> ifNotReady(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifNotReady(runnable));
    }
    
    public default Promise<DATA> ifNotReady(Consumer<? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifNotReady((Consumer) consumer));
    }
    
    public default Promise<DATA> whenNotReadyUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenNotReadyUse(fallbackValue));
    }
    
    public default Promise<DATA> whenNotReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenNotReadyGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenNotReadyApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenNotReadyApply(recoverFunction));
    }
    
    // == No More Result ==
    public default Promise<DATA> ifNoMore(Runnable runnable) {
        return (Promise) mapResult(result -> result.ifNoMore(runnable));
    }
    
    public default Promise<DATA> ifNoMore(Consumer<? super Exception> consumer) {
        return (Promise) mapResult(result -> result.ifNoMore(consumer));
    }
    
    public default Promise<DATA> whenNoMoreUse(DATA fallbackValue) {
        return (Promise) mapResult(result -> result.whenNoMoreUse(fallbackValue));
    }
    
    public default Promise<DATA> whenNoMoreGet(Supplier<? extends DATA> fallbackSupplier) {
        return (Promise) mapResult(result -> result.whenNoMoreGet(fallbackSupplier));
    }
    
    public default Promise<DATA> whenNoMoreApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return (Promise) mapResult(result -> result.whenNoMoreApply(recoverFunction));
    }
}
