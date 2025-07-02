// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Func.itself;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.list.FuncList;
import functionalj.result.Result;
import functionalj.result.ResultStatus;
import functionalj.result.ValidationException;
import functionalj.tuple.Tuple2;
import functionalj.validator.Validator;
import lombok.NonNull;
import lombok.val;

/**
 * This special class of promise can have its value assigned later.
 * Once assigned, the value is set.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DeferValue<DATA> extends Promise<DATA> {
    
    public static <DATA> DeferValue<DATA> of(Class<DATA> clazz) {
        return new DeferValue<DATA>();
    }
    
    public static <DATA> DeferValue<DATA> ofValue(DATA data) {
        val deferValue = new DeferValue<DATA>();
        deferValue.assign(data);
        return deferValue;
    }
    
    public static <DATA> DeferValue<DATA> ofFailure(@NonNull Exception cause) {
        val deferValue = new DeferValue<DATA>();
        deferValue.fail(cause);
        return deferValue;
    }
    
    public static <DATA> DeferValue<DATA> ofResult(Result<DATA> result) {
        val deferValue = new DeferValue<DATA>();
        deferValue.complete(result);
        return deferValue;
    }
    
    public static <DATA> DeferValue<DATA> deferValueOf(Class<DATA> clazz) {
        return new DeferValue<DATA>();
    }
    
    public static <DATA> DeferValue<DATA> deferValue() {
        return new DeferValue<DATA>();
    }
    
    public static DeferValue<String> deferString() {
        return new DeferValue<String>();
    }
    
    public static DeferValue<Integer> deferInt() {
        return new DeferValue<Integer>();
    }
    
    public static DeferValue<Long> deferLong() {
        return new DeferValue<Long>();
    }
    
    public static DeferValue<Double> deferDouble() {
        return new DeferValue<Double>();
    }
    
    public static <DATA> DeferValue<DATA> of(String name, Class<DATA> clazz) {
        return new DeferValue<DATA>().named(name);
    }
    
    public static <DATA> DeferValue<DATA> deferValue(String name, Class<DATA> clazz) {
        return new DeferValue<DATA>().named(name);
    }
    
    public static <DATA> DeferValue<DATA> deferValue(String name) {
        return new DeferValue<DATA>().named(name);
    }
    
    public static DeferValue<String> deferString(String name) {
        return new DeferValue<String>().named(name);
    }
    
    public static DeferValue<Integer> deferInt(String name) {
        return new DeferValue<Integer>().named(name);
    }
    
    public static DeferValue<Long> deferLong(String name) {
        return new DeferValue<Long>().named(name);
    }
    
    public static DeferValue<Double> deferDouble(String name) {
        return new DeferValue<Double>().named(name);
    }
    
    public DeferValue() {
        super((OnStart) () -> {
        });
        start();
    }
    
    DeferValue(Promise parent) {
        super(parent);
    }
    
    DeferValue<DATA> parent() {
        return (DeferValue<DATA>) super.parent();
    }
    
    public NamedDeferValue<DATA> named(String name) {
        return new NamedDeferValue<DATA>(this, name);
    }
    
    @Override
    public Promise<DATA> getPromise() {
        return map(itself());
    }
    
    public Func0<DATA> asSupplier() {
        return () -> get();
    }
    
    public String toString() {
        return "Later#" + name;
    }
    
    private boolean complete(boolean shouldThrowException, Predicate<DeferValue<DATA>> parentAction, BooleanSupplier superAction) {
        val parent = parent();
        if (parent != null) {
            return parentAction.test(parent);
        } else {
            if (!isComplete()) {
                synchronized (this) {
                    if (!isComplete()) {
                        return superAction.getAsBoolean();
                    }
                }
            }
            return handleAlreadyCompleted(shouldThrowException);
        }
    }
    
    private boolean handleAlreadyCompleted(boolean shouldThrowException) {
        if (shouldThrowException) {
            throw new RuntimeException();
        } else {
            return false;
        }
    }
    
    boolean cancel(boolean shouldThrowException) {
        return complete(shouldThrowException, parent -> parent.cancel(), () -> super.cancel());
    }
    
    boolean cancel(String message, boolean shouldThrowException) {
        return complete(shouldThrowException, parent -> parent.cancel(message), () -> super.cancel(message));
    }
    
    boolean cancel(Exception cause, boolean shouldThrowException) {
        return complete(shouldThrowException, parent -> parent.cancel(cause), () -> super.cancel(cause));
    }
    
    boolean cancel(String message, Exception cause, boolean shouldThrowException) {
        return complete(shouldThrowException, parent -> parent.cancel(message, cause), () -> super.cancel(message, cause));
    }
    
    boolean assign(DATA data, boolean shouldThrowException) {
        return complete(shouldThrowException, parent -> parent.assign(data), () -> super.makeComplete(data));
    }
    
    boolean fail(Exception exception, boolean shouldThrowException) {
        return complete(shouldThrowException, parent -> parent.fail(exception), () -> super.makeFail(exception));
    }
    
    boolean complete(Result<DATA> result, boolean shouldThrowException) {
        return complete(shouldThrowException, parent -> makeDone(parent, result), () -> makeDone(this, result));
    }
    
    // == Complete ==
    public boolean cancel() {
        return cancel(false);
    }
    
    public boolean cancel(String message) {
        return cancel(message, false);
    }
    
    public boolean cancel(Exception cause) {
        return cancel(cause, false);
    }
    
    public boolean cancel(String message, Exception cause) {
        return cancel(message, cause, false);
    }
    
    public boolean assign(DATA data) {
        return assign(data, false);
    }
    
    public boolean fail(Exception exception) {
        return fail(exception, false);
    }
    
    public boolean complete(Result<DATA> result) {
        return complete(result, false);
    }
    
    // == When not complete ==
    public boolean cancelOrThrow() {
        return cancel(true);
    }
    
    public boolean cancelOrThrow(String message) {
        return cancel(message, true);
    }
    
    public boolean cancelOrThrow(Exception cause) {
        return cancel(cause, true);
    }
    
    public boolean cancelOrThrow(String message, Exception cause) {
        return cancel(message, cause, true);
    }
    
    public boolean assignOrThrow(DATA data) {
        return assign(data, true);
    }
    
    public boolean failOrThrow(Exception exception) {
        return fail(exception, true);
    }
    
    public boolean completeOrThrow(Result<DATA> result) {
        return complete(result, true);
    }
    
    // TODO - Do the same here to Ref
    // == Functional ==
    public final DeferValue<DATA> filter(Predicate<? super DATA> predicate) {
        return DeferValueHelper.mapResult(this, result -> result.filter((Predicate) predicate));
    }
    
    // == Validation ==
    public DeferValue<DATA> validateNotNull() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validateNotNull());
    }
    
    public DeferValue<DATA> validateNotNull(String message) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validateNotNull(message));
    }
    
    public DeferValue<DATA> validateUnavailable() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validateUnavailable());
    }
    
    public DeferValue<DATA> validateNotReady() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validateNotReady());
    }
    
    public DeferValue<DATA> validateResultCancelled() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validateResultCancelled());
    }
    
    public DeferValue<DATA> validateResultNotExist() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validateResultNotExist());
    }
    
    public DeferValue<DATA> validateNoMoreResult() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validateNoMoreResult());
    }
    
    public DeferValue<DATA> validate(String stringFormat, Predicate<? super DATA> validChecker) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validate(stringFormat, (Predicate) validChecker));
    }
    
    public <T> DeferValue<DATA> validate(String stringFormat, Func1<? super DATA, T> mapper, Predicate<? super T> validChecker) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validate(stringFormat, (Func1) mapper, (Predicate) validChecker));
    }
    
    public DeferValue<DATA> validate(Validator<DATA> validator) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validate((Validator) validator));
    }
    
    public DeferValue<Tuple2<DATA, FuncList<ValidationException>>> validate(Validator<? super DATA>... validators) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validate((Validator[]) validators));
    }
    
    public DeferValue<Tuple2<DATA, FuncList<ValidationException>>> validate(List<Validator<? super DATA>> validators) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.validate((List) validators));
    }
    
    public DeferValue<DATA> ensureNotNull() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ensureNotNull());
    }
    
    // Alias of whenNotPresentUse
    public DeferValue<DATA> otherwise(DATA elseValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.otherwise(elseValue));
    }
    
    // Alias of whenNotPresentGet
    public DeferValue<DATA> otherwiseGet(Supplier<? extends DATA> elseSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.otherwiseGet(elseSupplier));
    }
    
    public DeferValue<DATA> printException() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.printException());
    }
    
    public DeferValue<DATA> printException(PrintStream printStream) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.printException(printStream));
    }
    
    public DeferValue<DATA> printException(PrintWriter printWriter) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.printException(printWriter));
    }
    
    // == Peek ==
    public <T extends DATA> DeferValue<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return DeferValueHelper.mapResult(this, result -> result.peek(clzz, (Consumer) theConsumer));
    }
    
    public DeferValue<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return DeferValueHelper.mapResult(this, result -> result.peek((Predicate) selector, (Consumer) theConsumer));
    }
    
    public <T> DeferValue<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return DeferValueHelper.mapResult(this, result -> result.peek((Function) mapper, (Consumer) theConsumer));
    }
    
    public <T> DeferValue<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return DeferValueHelper.mapResult(this, result -> result.peek((Function) mapper, (Predicate) selector, (Consumer) theConsumer));
    }
    
    // == If+When ==
    public DeferValue<DATA> useData(FuncUnit2<DATA, Exception> processor) {
        return DeferValueHelper.mapResult(this, result -> result.useData((FuncUnit2) processor));
    }
    
    public DeferValue<DATA> whenComplete(FuncUnit2<DATA, Exception> processor) {
        return DeferValueHelper.mapResult(this, result -> result.useData((FuncUnit2) processor));
    }
    
    public DeferValue<DATA> whenComplete(FuncUnit1<Result<DATA>> processor) {
        return DeferValueHelper.mapResult(this, result -> {
            processor.accept((Result) result);
            return null;
        });
    }
    
    // == Status ==
    public DeferValue<DATA> ifStatusRun(ResultStatus status, Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifStatusRun(status, runnable));
    }
    
    public DeferValue<DATA> ifStatusAccept(ResultStatus status, Consumer<? super DATA> consumer) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifStatusAccept(status, (Consumer) consumer));
    }
    
    public DeferValue<DATA> whenStatusUse(ResultStatus status, DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenStatusUse(status, fallbackValue));
    }
    
    public DeferValue<DATA> whenStatusGet(ResultStatus status, Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenStatusGet(status, fallbackSupplier));
    }
    
    public DeferValue<DATA> whenStatusApply(ResultStatus status, BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenStatusApply(status, (BiFunction) recoverFunction));
    }
    
    // == Present ==
    public DeferValue<DATA> ifPresent(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifPresent(runnable));
    }
    
    public DeferValue<DATA> ifPresent(Consumer<? super DATA> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifPresent((Consumer) consumer));
    }
    
    // == Absent ==
    public DeferValue<DATA> ifAbsent(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifAbsent(runnable));
    }
    
    public DeferValue<DATA> ifAbsent(Consumer<? super DATA> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifAbsent((Consumer) consumer));
    }
    
    public DeferValue<DATA> ifAbsent(BiConsumer<? super DATA, ? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifAbsent((BiConsumer) consumer));
    }
    
    public DeferValue<DATA> whenAbsentUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenAbsentUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenAbsentGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenAbsentGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenAbsentApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> result.whenAbsentApply((Func2) recoverFunction));
    }
    
    // == Null ==
    public DeferValue<DATA> ifNull(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifNull(runnable));
    }
    
    public DeferValue<DATA> whenNullUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNullUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenNullGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNullGet(fallbackSupplier));
    }
    
    // == Value ==
    public DeferValue<DATA> ifValue(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifValue(runnable));
    }
    
    public DeferValue<DATA> ifValue(Consumer<? super DATA> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifValue((Consumer) consumer));
    }
    
    // == NotValue ==
    public DeferValue<DATA> ifNotValue(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifNotValue(runnable));
    }
    
    public DeferValue<DATA> ifNotValue(Consumer<? super DATA> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifNotValue((Consumer) consumer));
    }
    
    public DeferValue<DATA> ifNotValue(BiConsumer<? super DATA, ? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifNotValue((BiConsumer) consumer));
    }
    
    public DeferValue<DATA> whenNotValueUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotValueUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenNotValueGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotValueGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenNotValueApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> result.whenNotValueApply((Func2) recoverFunction));
    }
    
    // == Valid ==
    public DeferValue<DATA> ifValid(Consumer<? super DATA> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifValid((Consumer) consumer));
    }
    
    // == Invalid ==
    public DeferValue<DATA> ifInvalid(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifInvalid(runnable));
    }
    
    public DeferValue<DATA> ifInvalid(Consumer<? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifInvalid((Consumer) consumer));
    }
    
    public DeferValue<DATA> whenInvalidUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenInvalidUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenInvalidGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> result.whenInvalidGet((Supplier) fallbackSupplier));
    }
    
    public DeferValue<DATA> whenInvalidApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> result.whenInvalidApply((Func1) recoverFunction));
    }
    
    // == NotExist ==
    public DeferValue<DATA> ifNotExist(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifNotExist(runnable));
    }
    
    public DeferValue<DATA> ifNotExist(Consumer<? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifNotExist((Consumer) consumer));
    }
    
    public DeferValue<DATA> whenNotExistUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotExistUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenNotExistGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotExistGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenNotExistApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotExistApply(recoverFunction));
    }
    
    // == Exception ==
    public DeferValue<DATA> ifException(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifException(runnable));
    }
    
    public DeferValue<DATA> ifException(Consumer<? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifException(consumer));
    }
    
    public DeferValue<DATA> ifExceptionThenPrint() {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifExceptionThenPrint());
    }
    
    public DeferValue<DATA> ifExceptionThenPrint(PrintStream printStream) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifExceptionThenPrint(printStream));
    }
    
    public DeferValue<DATA> ifExceptionThenPrint(PrintWriter printWriter) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifExceptionThenPrint(printWriter));
    }
    
    public DeferValue<DATA> whenExceptionUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenExceptionUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenExceptionGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenExceptionGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenExceptionApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenExceptionApply(recoverFunction));
    }
    
    public DeferValue<DATA> recover(Class<? extends Throwable> problemClass, DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.recover(problemClass, fallbackValue));
    }
    
    public DeferValue<DATA> recover(Class<? extends Throwable> problemClass, Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.recover(problemClass, fallbackSupplier));
    }
    
    public DeferValue<DATA> recover(Class<? extends Throwable> problemClass, Func1<? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.recover(problemClass, recoverFunction));
    }
    
    // == Cancelled ==
    public DeferValue<DATA> ifCancelled(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifCancelled(runnable));
    }
    
    public DeferValue<DATA> whenCancelledUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenCancelledUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenCancelledGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenCancelledGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenCancelledApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenCancelledApply(recoverFunction));
    }
    
    // == Ready ==
    public DeferValue<DATA> ifReady(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifReady(runnable));
    }
    
    public DeferValue<DATA> ifReady(Consumer<? super DATA> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifReady((Consumer) consumer));
    }
    
    public DeferValue<DATA> ifReady(BiConsumer<? super DATA, ? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifReady((BiConsumer) consumer));
    }
    
    public DeferValue<DATA> whenReadyUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenReadyUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenReadyGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenNotReadyApply(BiFunction<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> result.whenNotReadyApply((Func2) recoverFunction));
    }
    
    // == Not Ready ==
    public DeferValue<DATA> ifNotReady(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifNotReady(runnable));
    }
    
    public DeferValue<DATA> ifNotReady(Consumer<? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> result.ifNotReady((Consumer) consumer));
    }
    
    public DeferValue<DATA> whenNotReadyUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotReadyUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenNotReadyGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotReadyGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenNotReadyApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNotReadyApply(recoverFunction));
    }
    
    // == No More Result ==
    public DeferValue<DATA> ifNoMore(Runnable runnable) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifNoMore(runnable));
    }
    
    public DeferValue<DATA> ifNoMore(Consumer<? super Exception> consumer) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.ifNoMore(consumer));
    }
    
    public DeferValue<DATA> whenNoMoreUse(DATA fallbackValue) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNoMoreUse(fallbackValue));
    }
    
    public DeferValue<DATA> whenNoMoreGet(Supplier<? extends DATA> fallbackSupplier) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNoMoreGet(fallbackSupplier));
    }
    
    public DeferValue<DATA> whenNoMoreApply(Function<? super Exception, ? extends DATA> recoverFunction) {
        return DeferValueHelper.mapResult(this, result -> (Result) result.whenNoMoreApply(recoverFunction));
    }
}
