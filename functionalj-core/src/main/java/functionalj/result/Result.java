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
package functionalj.result;

import static functionalj.function.Func.f;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func10;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.Func7;
import functionalj.function.Func8;
import functionalj.function.Func9;
import functionalj.function.FunctionInvocationException;
import functionalj.list.FuncList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.validator.SimpleValidator;
import functionalj.validator.Validator;
import lombok.val;
import nullablej.nullable.Nullable;

// TODO - Make Result lazy.
public abstract class Result<DATA> implements AsResult<DATA>, Pipeable<Result<DATA>>, HasPromise<DATA>, ResultMapAddOn<DATA>, ResultChainAddOn<DATA>, ResultFilterAddOn<DATA>, ResultPeekAddOn<DATA>, ResultStatusAddOn<DATA> {
    
    protected static <T> Func1<Exception, T> throwException() {
        return e -> {
            throw e;
        };
    }
    
    protected static <T> Func1<Exception, T> throwRuntimeException() {
        return e -> {
            if (e instanceof RuntimeException)
                throw e;
            throw new RuntimeException(e);
        };
    }
    
    protected static <T> Func1<Exception, Exception> returnException() {
        return e -> e;
    }
    
    protected <T> Func1<Exception, Result<T>> returnValueException() {
        return e -> newException(e);
    }
    
    protected static <T> Func1<Exception, Result<T>> returnValueNull() {
        return e -> Result.ofNull();
    }
    
    protected static <T> Func1<Exception, T> returnNull() {
        return e -> null;
    }
    
    protected static <T> Func1<Exception, Boolean> returnFalse() {
        return e -> false;
    }
    
    protected static <T> Func1<Exception, Boolean> returnTrue() {
        return e -> false;
    }
    
    @SuppressWarnings("rawtypes")
    private static final Result NULL = new Value<>(null);
    
    /**
     * Returns the Null result.
     *
     * @param  <D>  the data type of the result.
     * @return the Result containing null value.
     */
    public static <D> Result<D> ofNull() {
        @SuppressWarnings("unchecked")
        val nullResult = (Result<D>) NULL;
        return nullResult;
    }
    
    /**
     * Returns the NotExist result.
     *
     * @param  <D>  the data type of the result.
     * @return the Result that is the result does not exist.
     */
    public static <D> Result<D> ofNotExist() {
        @SuppressWarnings("unchecked")
        val notAvailableResult = (Result<D>) Result.ofException(new ResultNotExistException());
        return notAvailableResult;
    }
    
    /**
     * Returns the NotExist result with message.
     *
     * @param  message  the exception message.
     * @param  <D>      the data type of the result.
     * @return the Result that is the result is not available.
     */
    public static <D> Result<D> ofNotExist(String message) {
        @SuppressWarnings("unchecked")
        val exceptionResult = (Result<D>) Result.ofException(new ResultNotExistException(message, null));
        return exceptionResult;
    }
    
    /**
     * Returns the NotExist result with message and cause.
     *
     * @param  message  the exception message.
     * @param  cause    the exception cause.
     * @param  <D>      the data type of the result.
     * @return the Result that is the result is not available.
     */
    public static <D> Result<D> ofNotExist(String message, Exception cause) {
        @SuppressWarnings("unchecked")
        val exceptionResult = (Result<D>) Result.ofException(new ResultNotExistException(message, cause));
        return exceptionResult;
    }
    
    /**
     * Returns the NoMore result.
     *
     * @param  <D>  the data type of the result.
     * @return the Result that is the result does not exist.
     */
    public static <D> Result<D> ofNoMore() {
        @SuppressWarnings("unchecked")
        val notAvailableResult = (Result<D>) Result.ofException(new NoMoreResultException());
        return notAvailableResult;
    }
    
    /**
     * Returns the NoMore result with message.
     *
     * @param  message  the exception message.
     * @param  <D>      the data type of the result.
     * @return the Result that is the result is not available.
     */
    public static <D> Result<D> ofNoMore(String message) {
        @SuppressWarnings("unchecked")
        val exceptionResult = (Result<D>) Result.ofException(new NoMoreResultException(message, null));
        return exceptionResult;
    }
    
    /**
     * Returns the NoMore result with message and cause.
     *
     * @param  message  the exception message.
     * @param  cause    the exception cause.
     * @param  <D>      the data type of the result.
     * @return the Result that is the result is not available.
     */
    public static <D> Result<D> ofNoMore(String message, Exception cause) {
        @SuppressWarnings("unchecked")
        val exceptionResult = (Result<D>) Result.ofException(new NoMoreResultException(message, cause));
        return exceptionResult;
    }
    
    /**
     * Returns the NotReady result.
     *
     * @param  <D>  the data type of the result.
     * @return the Result that is the result is not ready.
     */
    public static <D> Result<D> ofNotReady() {
        @SuppressWarnings("unchecked")
        val notReady = (Result<D>) Result.ofException(new ResultNotReadyException());
        return notReady;
    }
    
    /**
     * Returns the NotReady result with message.
     *
     * @param  message  the exception message.
     * @param  <D>      the data type of the result.
     * @return the Result that is the result is not ready.
     */
    public static <D> Result<D> ofNotReady(String message) {
        @SuppressWarnings("unchecked")
        val exceptionResult = (Result<D>) Result.ofException(new ResultNotReadyException(message, null));
        return exceptionResult;
    }
    
    /**
     * Returns the NotReady result with message.
     *
     * @param  message  the exception message.
     * @param  cause    the exception cause.
     * @param  <D>      the data type of the result.
     * @return the Result that is the result is not ready.
     */
    public static <D> Result<D> ofNotReady(String message, Exception cause) {
        @SuppressWarnings("unchecked")
        val exceptionResult = (Result<D>) Result.ofException(new ResultNotReadyException(message, cause));
        return exceptionResult;
    }
    
    /**
     * Returns the Cancelled result.
     *
     * @param  <D>  the data type of the result.
     * @return the Result that is the result is cancelled.
     */
    public static <D> Result<D> ofCancelled() {
        @SuppressWarnings("unchecked")
        val cancelledResult = (Result<D>) Result.ofException(new ResultCancelledException());
        return cancelledResult;
    }
    
    /**
     * Returns the Cancelled result with message.
     *
     * @param  message  the exception message.
     * @param  <D>       the data type of the result.
     * @return the Result that is the result is cancelled.
     */
    public static <D> Result<D> ofCancelled(String message) {
        @SuppressWarnings("unchecked")
        val cancelledResult = (Result<D>) Result.ofException(new ResultCancelledException(message, null));
        return cancelledResult;
    }
    
    /**
     * Returns the Cancelled result with message.
     *
     * @param  message  the exception message.
     * @param  cause    the exception cause.
     * @param  <D>       the data type of the result.
     * @return the Result that is the result is cancelled.
     */
    public static <D> Result<D> ofCancelled(String message, Exception cause) {
        @SuppressWarnings("unchecked")
        val cancelledResult = (Result<D>) Result.ofException(new ResultCancelledException(message, cause));
        return cancelledResult;
    }
    
    /**
     * Returns the Invalid result with message.
     *
     * @param  message  the exception message.
     * @param  <D>       the data type of the result.
     * @return the Result that is the result is invalid.
     */
    public static <D> Result<D> ofInvalid(String message) {
        @SuppressWarnings("unchecked")
        val invalidResult = (Result<D>) Result.ofException(new ValidationException(message, null));
        return invalidResult;
    }
    
    /**
     * Returns the Invalid result with message.
     *
     * @param  message  the exception message.
     * @param  cause    the exception cause.
     * @param  <D>       the data type of the result.
     * @return the Result that is the result is invalid.
     */
    public static <D> Result<D> ofInvalid(String message, Exception cause) {
        @SuppressWarnings("unchecked")
        val invalidResult = (Result<D>) Result.ofException(new ValidationException(message, cause));
        return invalidResult;
    }
    
    public static <D> Value<D> Value(D data) {
        return new Value<D>(data);
    }
    
    public static <D> Result<D> ofValue(D value) {
        return valueOf(value);
    }
    
    public static <D> Result<D> valueOf(D value) {
        if (value == null)
            return Result.ofNull();
        return new Value<D>(value, (Exception) null);
    }
    
    public static <D> Result<D> from(Supplier<? extends D> supplier) {
        return of(Func0.of(supplier::get));
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D> Result<D> of(Func0<? extends D> supplier) {
        try {
            return Result.valueOf((D) ((Func0) supplier).applyUnsafe());
        } catch (FunctionInvocationException e) {
            val cause = e.getCause();
            if (cause instanceof Exception)
                return Result.ofException((Exception) cause);
            else
                return Result.ofException(e);
        } catch (Exception e) {
            return Result.ofException(e);
        }
    }
    
    public static <D> Result<D> Try(Func0<? extends D> supplier) {
        return of(supplier);
    }
    
    public static <D> Result<D> ofException(String exceptionMessage) {
        return new Value<D>((D) null, new FunctionInvocationException(exceptionMessage));
    }
    
    public static <D> Result<D> ofException(Exception exception) {
        return new Value<D>(null, (exception != null) ? exception : new FunctionInvocationException("Unknown reason."));
    }
    
    public static <D> Result<D> ofThrowable(Throwable throwable) {
        Exception exception
            = (throwable != null)
            ? new FunctionInvocationException(throwable)
            : new FunctionInvocationException("Unknown reason.");
        return new Value<D>(null, 
                exception);
    }
    
    public static <D> Result<D> ofResult(Result<D> result) {
        if (result == null)
            return Result.ofNotExist();
        if (result instanceof Result)
            return (Result<D>) result;
        val data = result.__valueData();
        @SuppressWarnings("unchecked")
        val value = (data instanceof ExceptionHolder) ? null : (D) data;
        val exception = (data instanceof ExceptionHolder) ? ((ExceptionHolder) data).getException() : null;
        if (exception != null)
            return Result.ofException(exception);
        return Result.valueOf(value);
    }
    
    public static <D, T1, T2> Result<D> of(T1 value1, T2 value2, Func2<T1, T2, D> merger) {
        return Result.of(Func0.of(() -> {
            val value = Func.applyUnsafe(merger, value1, value2);
            return value;
        }));
    }
    
    public static <D, T1, T2, T3> Result<D> of(T1 value1, T2 value2, T3 value3, Func3<T1, T2, T3, D> merger) {
        return Result.of(Func0.of(() -> {
            val value = merger.applyUnsafe(value1, value2, value3);
            return value;
        }));
    }
    
    public static <D, T1, T2, T3, T4> Result<D> of(T1 value1, T2 value2, T3 value3, T4 value4, Func4<T1, T2, T3, T4, D> merger) {
        return Result.of(Func0.of(() -> {
            val value = merger.applyUnsafe(value1, value2, value3, value4);
            return value;
        }));
    }
    
    public static <D, T1, T2, T3, T4, T5> Result<D> of(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, Func5<T1, T2, T3, T4, T5, D> merger) {
        return Result.of(Func0.of(() -> {
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5);
            return value;
        }));
    }
    
    public static <D, T1, T2, T3, T4, T5, T6> Result<D> of(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5, T6 value6, Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        return Result.of(Func0.of(() -> {
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5, value6);
            return value;
        }));
    }
    
    public static <D> Result<D> Do(Func0<? extends D> supplier) {
        try {
            return Result.valueOf((D) supplier.applyUnsafe());
        } catch (Exception e) {
            return Result.ofException(e);
        }
    }
    
    public static <T1, T2, D> Result<D> Do(Func0<T1> supplier1, Func0<T2> supplier2, Func2<T1, T2, D> merger) {
        return Result.ofResults(Try(supplier1), Try(supplier2), merger);
    }
    
    public static <T1, T2, T3, D> Result<D> Do(Func0<T1> supplier1, Func0<T2> supplier2, Func0<T3> supplier3, Func3<T1, T2, T3, D> merger) {
        return Result.ofResults(Try(supplier1), Try(supplier2), Try(supplier3), merger);
    }
    
    public static <T1, T2, T3, T4, D> Result<D> Do(Func0<T1> supplier1, Func0<T2> supplier2, Func0<T3> supplier3, Func0<T4> supplier4, Func4<T1, T2, T3, T4, D> merger) {
        return Result.ofResults(Try(supplier1), Try(supplier2), Try(supplier3), Try(supplier4), merger);
    }
    
    public static <T1, T2, T3, T4, T5, D> Result<D> Do(Func0<T1> supplier1, Func0<T2> supplier2, Func0<T3> supplier3, Func0<T4> supplier4, Func0<T5> supplier5, Func5<T1, T2, T3, T4, T5, D> merger) {
        return Result.ofResults(Try(supplier1), Try(supplier2), Try(supplier3), Try(supplier4), Try(supplier5), merger);
    }
    
    public static <T1, T2, T3, T4, T5, T6, D> Result<D> Do(Func0<T1> supplier1, Func0<T2> supplier2, Func0<T3> supplier3, Func0<T4> supplier4, Func0<T5> supplier5, Func0<T6> supplier6, Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        return Result.ofResults(Try(supplier1), Try(supplier2), Try(supplier3), Try(supplier4), Try(supplier5), Try(supplier6), merger);
    }
    
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func2} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param merger   the {@link Func2} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, BiFunction<T1, T2, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1 = result1.getResult().orThrow();
            val value2 = result2.getResult().orThrow();
            val value = Func.applyUnsafe(merger, value1, value2);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func3} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param merger   the {@link Func3} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, Func3<T1, T2, T3, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1 = result1.getResult().orThrow();
            val value2 = result2.getResult().orThrow();
            val value3 = result3.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func4} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * @param <T4>     the type of the result in the fourth {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param result4  the fourth {@link HasResult} instance
     * @param merger   the {@link Func4} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3, T4> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, HasResult<T4> result4, Func4<T1, T2, T3, T4, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1 = result1.getResult().orThrow();
            val value2 = result2.getResult().orThrow();
            val value3 = result3.getResult().orThrow();
            val value4 = result4.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3, value4);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func5} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * @param <T4>     the type of the result in the fourth {@link HasResult} instance
     * @param <T5>     the type of the result in the fifth {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param result4  the fourth {@link HasResult} instance
     * @param result5  the fifth {@link HasResult} instance
     * @param merger   the {@link Func5} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3, T4, T5> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, HasResult<T4> result4, HasResult<T5> result5, Func5<T1, T2, T3, T4, T5, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1 = result1.getResult().orThrow();
            val value2 = result2.getResult().orThrow();
            val value3 = result3.getResult().orThrow();
            val value4 = result4.getResult().orThrow();
            val value5 = result5.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func6} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * @param <T4>     the type of the result in the fourth {@link HasResult} instance
     * @param <T5>     the type of the result in the fifth {@link HasResult} instance
     * @param <T6>     the type of the result in the sixth {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param result4  the fourth {@link HasResult} instance
     * @param result5  the fifth {@link HasResult} instance
     * @param result6  the sixth {@link HasResult} instance
     * @param merger   the {@link Func6} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3, T4, T5, T6> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, HasResult<T4> result4, HasResult<T5> result5, HasResult<T6> result6, Func6<T1, T2, T3, T4, T5, T6, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1 = result1.getResult().orThrow();
            val value2 = result2.getResult().orThrow();
            val value3 = result3.getResult().orThrow();
            val value4 = result4.getResult().orThrow();
            val value5 = result5.getResult().orThrow();
            val value6 = result6.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5, value6);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func7} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * @param <T4>     the type of the result in the fourth {@link HasResult} instance
     * @param <T5>     the type of the result in the fifth {@link HasResult} instance
     * @param <T6>     the type of the result in the sixth {@link HasResult} instance
     * @param <T7>     the type of the result in the seventh {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param result4  the fourth {@link HasResult} instance
     * @param result5  the fifth {@link HasResult} instance
     * @param result6  the sixth {@link HasResult} instance
     * @param result7  the seventh {@link HasResult} instance
     * @param merger   the {@link Func7} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, HasResult<T4> result4, HasResult<T5> result5, HasResult<T6> result6, HasResult<T7> result7, Func7<T1, T2, T3, T4, T5, T6, T7, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1  = result1.getResult().orThrow();
            val value2  = result2.getResult().orThrow();
            val value3  = result3.getResult().orThrow();
            val value4  = result4.getResult().orThrow();
            val value5  = result5.getResult().orThrow();
            val value6  = result6.getResult().orThrow();
            val value7  = result7.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5, value6, value7);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func8} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * @param <T4>     the type of the result in the fourth {@link HasResult} instance
     * @param <T5>     the type of the result in the fifth {@link HasResult} instance
     * @param <T6>     the type of the result in the sixth {@link HasResult} instance
     * @param <T7>     the type of the result in the seventh {@link HasResult} instance
     * @param <T8>     the type of the result in the eighth {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param result4  the fourth {@link HasResult} instance
     * @param result5  the fifth {@link HasResult} instance
     * @param result6  the sixth {@link HasResult} instance
     * @param result7  the seventh {@link HasResult} instance
     * @param result8  the eighth {@link HasResult} instance
     * @param merger   the {@link Func8} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, HasResult<T4> result4, HasResult<T5> result5, HasResult<T6> result6, HasResult<T7> result7, HasResult<T8> result8, Func8<T1, T2, T3, T4, T5, T6, T7, T8, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1  = result1.getResult().orThrow();
            val value2  = result2.getResult().orThrow();
            val value3  = result3.getResult().orThrow();
            val value4  = result4.getResult().orThrow();
            val value5  = result5.getResult().orThrow();
            val value6  = result6.getResult().orThrow();
            val value7  = result7.getResult().orThrow();
            val value8  = result8.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5, value6, value7, value8);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func9} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * @param <T4>     the type of the result in the fourth {@link HasResult} instance
     * @param <T5>     the type of the result in the fifth {@link HasResult} instance
     * @param <T6>     the type of the result in the sixth {@link HasResult} instance
     * @param <T7>     the type of the result in the seventh {@link HasResult} instance
     * @param <T8>     the type of the result in the eighth {@link HasResult} instance
     * @param <T9>     the type of the result in the ninth {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param result4  the fourth {@link HasResult} instance
     * @param result5  the fifth {@link HasResult} instance
     * @param result6  the sixth {@link HasResult} instance
     * @param result7  the seventh {@link HasResult} instance
     * @param result8  the eighth {@link HasResult} instance
     * @param result9  the ninth {@link HasResult} instance
     * @param merger   the {@link Func9} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, HasResult<T4> result4, HasResult<T5> result5, HasResult<T6> result6, HasResult<T7> result7, HasResult<T8> result8, HasResult<T9> result9, Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1  = result1.getResult().orThrow();
            val value2  = result2.getResult().orThrow();
            val value3  = result3.getResult().orThrow();
            val value4  = result4.getResult().orThrow();
            val value5  = result5.getResult().orThrow();
            val value6  = result6.getResult().orThrow();
            val value7  = result7.getResult().orThrow();
            val value8  = result8.getResult().orThrow();
            val value9  = result9.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5, value6, value7, value8, value9);
            return value;
        }));
    }
    
    /**
     * Combines ten {@link HasResult} instances using a provided {@link Func10} merger function.
     * If any {@link HasResult} instance does not contain a result, this method throws the contained exception.
     * 
     * @param <D>      the type of the output result
     * @param <T1>     the type of the result in the first {@link HasResult} instance
     * @param <T2>     the type of the result in the second {@link HasResult} instance
     * @param <T3>     the type of the result in the third {@link HasResult} instance
     * @param <T4>     the type of the result in the fourth {@link HasResult} instance
     * @param <T5>     the type of the result in the fifth {@link HasResult} instance
     * @param <T6>     the type of the result in the sixth {@link HasResult} instance
     * @param <T7>     the type of the result in the seventh {@link HasResult} instance
     * @param <T8>     the type of the result in the eighth {@link HasResult} instance
     * @param <T9>     the type of the result in the ninth {@link HasResult} instance
     * @param <T10>    the type of the result in the tenth {@link HasResult} instance
     * 
     * @param result1  the first {@link HasResult} instance
     * @param result2  the second {@link HasResult} instance
     * @param result3  the third {@link HasResult} instance
     * @param result4  the fourth {@link HasResult} instance
     * @param result5  the fifth {@link HasResult} instance
     * @param result6  the sixth {@link HasResult} instance
     * @param result7  the seventh {@link HasResult} instance
     * @param result8  the eighth {@link HasResult} instance
     * @param result9  the ninth {@link HasResult} instance
     * @param result10 the tenth {@link HasResult} instance
     * @param merger   the {@link Func10} function to merge the results
     * @return a {@link Result} instance containing the merged result or throwing an exception if any result is absent
     */
    public static <D, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<D> ofResults(HasResult<T1> result1, HasResult<T2> result2, HasResult<T3> result3, HasResult<T4> result4, HasResult<T5> result5, HasResult<T6> result6, HasResult<T7> result7, HasResult<T8> result8, HasResult<T9> result9, HasResult<T10> result10, Func10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, D> merger) {
        return Result.of(Func0.of(() -> {
            val value1  = result1.getResult().orThrow();
            val value2  = result2.getResult().orThrow();
            val value3  = result3.getResult().orThrow();
            val value4  = result4.getResult().orThrow();
            val value5  = result5.getResult().orThrow();
            val value6  = result6.getResult().orThrow();
            val value7  = result7.getResult().orThrow();
            val value8  = result8.getResult().orThrow();
            val value9  = result9.getResult().orThrow();
            val value10 = result10.getResult().orThrow();
            val value = merger.applyUnsafe(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
            return (D)value;
        }));
    }
    
    //== Function ==
    
    /**
     * Returns the value hold by this {@link Result}.
     * 
     * @return  the value data.
     */
    abstract Object __valueData();
    
    
    public Result<DATA> or(Result<DATA> anotherResult) {
        if (this.isPresent())
            return this;
        if (anotherResult.isPresent())
            return anotherResult;
        return this;
    }
    
    @SuppressWarnings("unchecked")
    protected <T> Result<T> newException(Exception exception) {
        return (Result<T>) Result.ofException(exception);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T mapData(Func1<Exception, T> exceptionGet, Func2<DATA, Exception, T> processor) {
        try {
            val data = __valueData();
            val isValue = ((data == null) || !(data instanceof ExceptionHolder));
            val value = isValue ? (DATA) data : null;
            val exception = isValue ? null : ((ExceptionHolder) data).getException();
            assert !((value != null) && (exception != null));
            return processor.applyUnsafe(value, exception);
        } catch (Exception cause) {
            return exceptionGet.apply(cause);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> Result<T> mapValue(BiFunction<DATA, Exception, Result<T>> processor) {
        return new DerivedResult<T>(this, org -> {
            try {
                val data = org.__valueData();
                val isValue = ((data == null) || !(data instanceof ExceptionHolder));
                val value = isValue ? (DATA) data : null;
                val exception = isValue ? null : ((ExceptionHolder) data).getException();
                assert !((value != null) && (exception != null));
                val newValue = Func.from(processor).applyUnsafe(value, exception);
                return newValue;
            } catch (Exception cause) {
                return newException(cause);
            }
        });
    }
    
    public final DATA get() {
        return value();
    }
    
    public final DATA value() {
        return mapData(throwRuntimeException(), (value, exception) -> {
            if (exception != null)
                throw exception;
            return value;
        });
    }
    
    public final DATA getValue() {
        return value();
    }
    
    public final Exception exception() {
        return mapData(returnNull(), (value, exception) -> {
            if (exception != null)
                return exception;
            return null;
        });
    }
    
    public final Exception getException() {
        return exception();
    }
    
    public final <T extends Result<DATA>> T castTo(Class<T> clzz) {
        return clzz.cast(this);
    }
    
    @Override
    public Result<DATA> asResult() {
        return this;
    }
    
    public final Tuple2<DATA, Exception> toTuple() {
        return new Tuple2<DATA, Exception>() {
        
            @Override
            public final DATA _1() {
                return getValue();
            }
        
            @Override
            public final Exception _2() {
                return getException();
            }
        };
    }
    
    public final Optional<DATA> toOptional() {
        return Optional.ofNullable(this.get());
    }
    
    public final Nullable<DATA> toNullable() {
        return Nullable.of(this.get());
    }
    
    public final FuncList<DATA> toList() {
        return FuncList.of(this.get());
    }
    
    public final Func0<DATA> toSupplier() {
        return () -> get();
    }
    
    @Override
    public Promise<DATA> getPromise() {
        return mapData(Promise::ofException, (value, exception) -> {
            return (exception == null) ? Promise.ofValue(value) : Promise.ofException(exception);
        });
    }
    
    @Override
    public final Result<DATA> __data() throws Exception {
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Result<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return mapValue(f((value, exception) -> {
            if (value == null)
                return (Result<TARGET>) this;
            val newValue = Func.from(mapper).applyUnsafe(value);
            return Result.valueOf(newValue);
        }));
    }
    
    public final <T extends DATA> Result<T> as(Class<T> onlyClass) {
        return filter(onlyClass::isInstance).map(onlyClass::cast);
    }
    
    public final Result<DATA> mapException(Function<? super Exception, ? extends Exception> mapper) {
        return mapValue(f((value, exception) -> {
            if (exception == null)
                return this;
            val newException = Func.from(mapper).applyUnsafe(exception);
            return newException(newException);
        }));
    }
    
    public final <OPERANT, TARGET> Result<TARGET> mapWith(Func2<? super DATA, ? super OPERANT, ? extends TARGET> func, Result<OPERANT> operantResult) {
        return flatMap(data -> {
            return operantResult.map(operant -> {
                return func.apply(data, operant);
            });
        });
    }
    
    @SuppressWarnings("unchecked")
    public final <TARGET> Result<TARGET> flatMap(Function<? super DATA, ? extends Result<TARGET>> mapper) {
        return mapValue(f((value, exception) -> {
            if (value == null)
                return (Result<TARGET>) this;
            val monad = (Result<TARGET>) Func.from(mapper).applyUnsafe(value);
            return monad;
        }));
    }
    
    public final Result<DATA> filter(Predicate<? super DATA> theCondition) {
        return mapValue((value, exception) -> {
            if (value != null) {
                val isPass = theCondition.test(value);
                if (!isPass)
                    return Result.ofNull();
            }
            return this;
        });
    }
    
    public final Result<DATA> peek(Consumer<? super DATA> theConsumer) {
        return mapValue((value, exception) -> {
            if (value != null)
                theConsumer.accept(value);
            return this;
        });
    }
    
    public final Result<DATA> forValue(Consumer<? super DATA> theConsumer) {
        mapData(throwException(), (value, exception) -> {
            if (value != null)
                theConsumer.accept(value);
            return null;
        });
        return this;
    }
    
    // == Status and check
    public ResultStatus getStatus() {
        return mapData(returnNull(), (value, exception) -> {
            return ResultStatus.getStatus(value, exception);
        });
    }
    
    // == Validation ==
    // == TODO - Validate then accumulate.
    public final Result<DATA> validateNotNull() {
        return mapData(returnValueException(), (value, exception) -> {
            if ((value == null) && (exception == null))
                return newException(new ValidationException(new NullPointerException()));
            return this;
        });
    }
    
    public final Result<DATA> validateNotNull(String message) {
        return mapData(returnValueException(), (value, exception) -> {
            if ((value == null) && (exception == null))
                return newException(new ValidationException(message));
            return this;
        });
    }
    
    public final Result<DATA> validateUnavailable() {
        return mapData(returnValueException(), (value, exception) -> {
            if (exception instanceof ResultNotAvailableException)
                return newException(new ValidationException(exception));
            return this;
        });
    }
    
    public final Result<DATA> validateNotReady() {
        return mapData(returnValueException(), (value, exception) -> {
            if ((exception instanceof ResultNotAvailableException) || (exception instanceof ResultNotReadyException))
                return newException(new ValidationException(exception));
            return this;
        });
    }
    
    public final Result<DATA> validateResultCancelled() {
        return mapData(returnValueException(), (value, exception) -> {
            if (exception instanceof ResultCancelledException)
                return newException(new ValidationException(exception));
            return this;
        });
    }
    
    public final Result<DATA> validateResultNotExist() {
        return mapData(returnValueException(), (value, exception) -> {
            if (exception instanceof ResultNotExistException)
                return newException(new ValidationException(exception));
            return this;
        });
    }
    
    public final Result<DATA> validateNoMoreResult() {
        return mapData(returnValueException(), (value, exception) -> {
            if (exception instanceof NoMoreResultException)
                return newException(new ValidationException(exception));
            return this;
        });
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final Result<DATA> validate(String stringFormat, Predicate<? super DATA> validChecker) {
        return mapData(returnValueException(), (value, exception) -> {
            if (value == null)
                return this;
            try {
                if (validChecker.test(value))
                    return this;
                val validationException = SimpleValidator.exceptionFor(stringFormat).apply(value, (Predicate) validChecker);
                return (Result<DATA>) newException(validationException);
            } catch (Exception cause) {
                val validationException = SimpleValidator.exceptionFor(stringFormat, cause).apply(value, (Predicate) validChecker);
                return (Result<DATA>) newException(validationException);
            }
        });
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final <T> Result<DATA> validate(String stringFormat, Func1<? super DATA, T> mapper, Predicate<? super T> validChecker) {
        return mapData(returnValueException(), (value, exception) -> {
            if (value == null)
                return this;
            try {
                val target = mapper.applyUnsafe(value);
                if (validChecker.test(target))
                    return this;
                val validationException = SimpleValidator.exceptionFor(stringFormat).apply(value, (Predicate) validChecker);
                return (Result<DATA>) newException(validationException);
            } catch (Exception cause) {
                val validationException = SimpleValidator.exceptionFor(stringFormat, cause).apply(value, (Predicate) validChecker);
                return (Result<DATA>) newException(validationException);
            }
        });
    }
    
    public final Result<DATA> validate(Validator<DATA> validator) {
        return mapData(returnValueException(), (value, exception) -> {
            if (value == null)
                return this;
            return validator.validate(value);
        });
    }
    
    @SafeVarargs
    public final Result<Tuple2<DATA, FuncList<ValidationException>>> validate(Validator<? super DATA>... validators) {
        return validate(asList(validators));
    }
    
    @SuppressWarnings("unchecked")
    public final Result<Tuple2<DATA, FuncList<ValidationException>>> validate(List<Validator<? super DATA>> validators) {
        return (Result<Tuple2<DATA, FuncList<ValidationException>>>) mapData(e -> newException(new ResultNotAvailableException()), (value, exception) -> {
            if (exception != null)
                return (Result<Tuple2<DATA, FuncList<ValidationException>>>) this;
            val exceptions = FuncList.from(validators).map(validator -> validator.validate(value)).filter(result -> !result.isValue()).map(result -> result.getException()).map(error -> ValidationException.of(error));
            return Result.valueOf(Tuple.of(value, exceptions));
        });
    }
    
    public final Result<DATA> ensureNotNull() {
        return mapData(returnValueException(), (value, exception) -> {
            if (value != null)
                return this;
            if (exception != null)
                return this;
            return newException(new NullPointerException());
        });
    }
    
    // Alias of whenNotPresentUse
    public final Result<DATA> otherwise(DATA elseValue) {
        return whenAbsentUse(elseValue);
    }
    
    // Alias of whenNotPresentGet
    public final Result<DATA> otherwiseGet(Supplier<? extends DATA> elseSupplier) {
        return whenAbsentGet(elseSupplier);
    }
    
    public final DATA orNull() {
        return orElse(null);
    }
    
    public final DATA orElse(DATA elseValue) {
        return mapData(__ -> elseValue, (value, exception) -> {
            if (value != null)
                return value;
            return elseValue;
        });
    }
    
    public final DATA orElseGet(Supplier<? extends DATA> elseSupplier) {
        return orGet(elseSupplier);
    }
    
    public final DATA orGet(Supplier<? extends DATA> elseSupplier) {
        return mapData(__ -> null, (value, exception) -> {
            if (value != null)
                return value;
            return elseSupplier.get();
        });
    }
    
    public final DATA orApply(Func1<Exception, ? extends DATA> elseMapper) {
        return mapData(__ -> null, (value, exception) -> {
            if (value != null)
                return value;
            return elseMapper.apply(exception);
        });
    }
    
    public final DATA orThrow() throws Exception {
        return mapData(throwException(), (value, exception) -> {
            if (exception == null)
                return value;
            throw exception;
        });
    }
    
    public final DATA orThrowRuntimeException() {
        return mapData(throwRuntimeException(), (value, exception) -> {
            if (exception == null)
                return value;
            throw exception;
        });
    }
    
    public final <EXCEPTION extends Exception> DATA orThrow(Func1<Exception, EXCEPTION> toException) throws EXCEPTION {
        return mapData(e -> {
            throw toException.applyUnsafe(e);
        }, (value, exception) -> {
            if (exception == null)
                return value;
            throw exception;
        });
    }
    
    public final <RUNTIMEEXCEPTION extends RuntimeException> DATA orThrowRuntimeException(Function<Exception, RUNTIMEEXCEPTION> toRuntimeException) {
        return mapData(e -> {
            val exception = toRuntimeException.apply(e);
            if (exception instanceof RuntimeException)
                throw (RuntimeException) exception;
            throw new RuntimeException(exception);
        }, (value, exception) -> {
            if (exception == null)
                return value;
            throw exception;
        });
    }
    
    public final Result<DATA> printException() {
        return printException(System.err);
    }
    
    public final Result<DATA> printException(PrintStream printStream) {
        return mapValue((value, exception) -> {
            exception.printStackTrace(printStream);
            return this;
        });
    }
    
    public final Result<DATA> printException(PrintWriter printWriter) {
        return mapValue((value, exception) -> {
            exception.printStackTrace(printWriter);
            return this;
        });
    }
    
    @Override
    public final int hashCode() {
        return Objects.hash(__valueData());
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public final boolean equals(Object obj) {
        if (!(obj instanceof Result))
            return false;
        return Objects.equals(__valueData(), ((Result) obj).__valueData());
    }
    
    @Override
    public final String toString() {
        @SuppressWarnings("rawtypes")
        val clss = (Class) this.getClass();
        val clssName = ((clss == Value.class) || (clss == DerivedResult.class)) ? "Result" : clss.getSimpleName();
        return clssName + mapData(e -> ":{ Exception: " + e + " }", (value, exception) -> {
            val msg = ((exception != null) && (exception.getMessage() != null)) ? ": " + exception.getMessage() : "";
            if (exception == null)
                return ":{ Value: " + value + " }";
            else if (exception instanceof NoMoreResultException)
                return ":{ NoMoreResult" + msg + " }";
            else if (exception instanceof ResultCancelledException)
                return ":{ Cancelled" + msg + " }";
            else if (exception instanceof ResultNotReadyException)
                return ":{ NotReady" + msg + " }";
            else if (exception instanceof ResultNotExistException)
                return ":{ NotExist" + msg + " }";
            else if (exception instanceof ResultNotAvailableException)
                return ":{ NotAvailable" + msg + " }";
            else if (exception instanceof ValidationException)
                return ":{ Invalid" + msg + " }";
            else
                return ":{ Exception: " + exception + " }";
        });
    }
    
    // == Internal ==
    public final static class ExceptionHolder {
        
        private final Exception exception;
        
        ExceptionHolder(Exception exception) {
            this.exception = requireNonNull(exception);
        }
        
        public final Exception getException() {
            return exception;
        }
        
        @Override
        public String toString() {
            return "ExceptionHolder [exception=" + exception + "]";
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((exception == null) ? 0 : exception.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ExceptionHolder other = (ExceptionHolder) obj;
            if (exception == null) {
                if (other.exception != null)
                    return false;
            } else if (!exception.equals(other.exception))
                return false;
            return true;
        }
    }
}
