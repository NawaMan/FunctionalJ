// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.function;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.task.Task;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * Function of one parameter.
 *
 * @param <INPUT>   the input data type.
 * @param <OUTPUT>  the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func1<INPUT, OUTPUT> extends Function<INPUT, OUTPUT> {
    
    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> of(Func1<INPUT, OUTPUT> function) {
        return function;
    }
    
    /**
     * Creates and returns a {@code Func1} instance using the provided function.
     *
     * @param function  the function to be wrapped in {@code Func1}
     * @return          a {@code Func1} instance that encapsulates the provided function
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> func1(Func1<INPUT, OUTPUT> function) {
        return function;
    }
    
    /**
     * Converts a standard {@link Function} into a {@code Func1} instance.
     *
     * @param func  the function to be converted
     * @return      a {@code Func1} instance representing the given function
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <I1, O> Func1<I1, O> from(Function<I1, O> func) {
        return (func instanceof Func1) ? (Func1)func : func::apply;
    }
    
    
    //== Apply ==
    
    /**
     * Applies this function to the given input, potentially throwing an exception.
     * This method provides a way to apply the function where exceptions are expected to be handled by the caller.
     *
     * @param input  the input to the function
     * @return       the function result
     * @throws Exception if an error occurs during function execution
     */
    public OUTPUT applyUnsafe(INPUT input) throws Exception;
    
    
    /**
     * Applies this function safely to two input parameters, returning a {@code Result<OUTPUT>}.
     * This method wraps the function application in a try-catch block, capturing any exceptions that occur during execution.
     * 
     * @param input  the input parameter.
     * @return       a {@code Result<OUTPUT>} containing the result if successful, or an exception if an error occurs during function application.
     */
    public default Result<OUTPUT> applySafely(
                    INPUT input) {
        try {
            val output = applyUnsafe(input);
            return Result.valueOf(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
    }
    
    /**
     * Applies this function to the given input value.
     *
     * @param input  the input function.
     * @return       the function result.
     */
    public default OUTPUT apply(INPUT input) {
        try {
            val output = applyUnsafe(input);
            return output;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    /**
     * Functorial application -- applies this function to one optional input parameter, returning an {@code Optional} of the output.
     * If any input is empty, the function short-circuits and returns {@code Optional.empty()}.
     * 
     * @param input  optional first input parameter.
     * @return       an {@code Optional<OUTPUT>} containing the result, if all inputs are present; otherwise, {@code Optional.empty()}.
     */
    public default Optional<OUTPUT> applyTo(Optional<INPUT> input) {
        return input.map(this);
    }
    
    /**
     * Functorial application -- applies this function to one {@link Nullable} input parameter, returning a {@code Nullable} of the output.
     * If any input is null, the function short-circuits and returns {@code Nullable.empty()}.
     * 
     * @param input  nullable first input parameter.
     * @return       a {@code Nullable<OUTPUT>} containing the result, if all inputs are non-null; otherwise, {@code Nullable.empty()}.
     */
    public default Nullable<OUTPUT> applyTo(Nullable<INPUT> input) {
        return input.map(this);
    }
    
    /**
     * Functorial application -- applies this function to one {@code Result} instance, returning a {@code Result} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input  the first promise.
     * @return       a {@code Result<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Result<OUTPUT> applyTo(Result<INPUT> input) {
        return input.map(this);
    }
    
    /**
     * Functorial application -- applies this function to one {@code HasPromise} instance, returning a {@code Promise} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input  the first promise.
     * @return       a {@code Promise<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Promise<OUTPUT> applyTo(HasPromise<INPUT> input) {
        return input.getPromise().map(this);
    }
    
    /**
     * Functorial application -- applies this function to one {@code Task} instance, returning a {@code Task} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input  the first task.
     * @return       a {@code Task<OUTPUT>} that will be fulfilled with the result of applying this function.
     */
    public default Task<OUTPUT> applyTo(Task<INPUT> input) {
        return input.map(this);
    }
    
    /**
     * Functorial application -- applies this function to each element of the given input stream, resulting in a stream of output elements.
     * This method facilitates the application of the function to multiple inputs in a streaming context.
     *
     * @param input  the stream of input elements
     * @return       a {@code StreamPlus} of output elements resulting from applying this function to each input element
     */
    public default StreamPlus<OUTPUT> applyTo(Stream<INPUT> input) {
        return StreamPlus.from(input).map(this);
    }
    
    /**
     * Functorial application -- applies this function to each element of the given input list, resulting in a list of output elements.
     * This method facilitates the application of the function to a list of inputs, transforming each element according to this function.
     *
     * @param input  the list of input elements
     * @return       a {@code FuncList} of output elements resulting from applying this function to each element of the input list
     */
    public default FuncList<OUTPUT> applyTo(List<INPUT> input) {
        return FuncList.from(input).map(this);
    }
    
    /**
     * Functorial application -- applies this function to the values of the given input map, resulting in a map with the same keys and transformed values.
     * This method allows for the transformation of map values according to this function, while retaining the original key associations.
     *
     * @param input  the map with keys and input values
     * @return       a {@code FuncMap} with the same keys and output values resulting from applying this function to each input value
     */
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(Map<KEY, INPUT> input) {
        return FuncMap.from(input).map(this);
    }
    
    /**
     * Functorial application -- applies this function to each element of the given {@code FuncList} input, resulting in a {@code FuncList} of output elements.
     * This method streamlines the process of applying the function to all elements in a {@code FuncList}.
     *
     * @param input  the {@code FuncList} of input elements
     * @return       a {@code FuncList} of output elements resulting from applying this function to each element of the input {@code FuncList}
     */
    public default FuncList<OUTPUT> applyTo(FuncList<INPUT> input) {
        return input.map(this);
    }
    
    /**
     * Functorial application -- applies this function to the values of the given {@code FuncMap} input, resulting in a {@code FuncMap} with the same keys and transformed values.
     * This method enables the transformation of map values using this function, maintaining the original key-value associations.
     *
     * @param input  the {@code FuncMap} with keys and input values
     * @return       a {@code FuncMap} with the same keys and output values resulting from applying this function to each input value
     */
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(FuncMap<KEY, INPUT> input) {
        return input.map(this);
    }
    
    /**
     * Functorial application -- applies the function to a combination of a supplier for the first input and the remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and produces a {@code Func0} that, when invoked, supplies the first input and applies the function to all three inputs.
     *
     * @param supplier  the {@code Func0} supplier for the first input.
     * @return           a {@code Func1} function that takes the remaining nine inputs and returns a {@code Func0} producing the output.
     */
    public default Func0<OUTPUT> applyTo(Func0<INPUT> supplier) {
        return () -> {
            val value  = supplier.get();
            val output = apply(value);
            return output;
        };
    }
    
    /**
     * Functorial application -- a transforms the first input using a given function and applies the original function to the transformed input and remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and a function that transforms an additional input into the first input type, then applies the original function to all inputs.
     *
     * @param function  the {@code Func1} function to transform an additional input into the first input type.
     * @return          a {@code Func1} function that takes the remaining inputs and a function to transform an additional input, then returns a {@code Func1} producing the output.
     */
    public default <T> Func1<T, OUTPUT> applyTo(Func1<T, INPUT> function) {
        return input -> {
            val value  = function.apply(input);
            val output = apply(value);
            return output;
        };
    }
    
    /**
     * Memoizes this function, caching its results for previously encountered inputs.
     * This method enhances performance for functions with expensive computations and frequent re-evaluations with the same inputs.
     *
     * @return a memoized version of this {@code Func1} that caches its results
     */
    public default Func1<INPUT, OUTPUT> memoize() {
        return Func.cacheFor(this);
    }
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  <TARGET>  the target result value.
     * @param  after     the function to be run after this function.
     * @return           the composed function.
     */
    public default <TARGET> Func1<INPUT, TARGET> then(Function<? super OUTPUT, ? extends TARGET> after) {
        return input -> {
            val output = this.applyUnsafe(input);
            val target = Func.applyUnsafe(after, output);
            return target;
        };
    }
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  <TARGET>  the target result value.
     * @param  after     the function to be run after this function.
     * @return           the composed function.
     */
    public default <TARGET> Func1<INPUT, TARGET> map(Function<? super OUTPUT, ? extends TARGET> after) {
        return input -> {
            val output = this.applyUnsafe(input);
            val target = (output != null) ? Func.applyUnsafe(after, output) : null;
            return target;
        };
    }
    
    /**
     * Applies this function to the given arguments, handling any exceptions using the specified exception handler.
     * If an exception is thrown during the function execution, the exception handler is invoked with the caught exception.
     *
     * @param exceptionHandler  the functional interface to handle exceptions thrown by this function
     * @return                  a new function that applies this function to the given arguments and uses the provided exception handler in case of exceptions
     */
    public default Func1<INPUT, OUTPUT> ifException(Consumer<Exception> exceptionHandler) {
        return (input) -> {
            try {
                val output = this.applyUnsafe(input);
                return output;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return null;
            }
        };
    }
    
    /**
     * Applies this function to the given arguments and prints the stack trace of any exception that occurs during execution.
     * If an exception is thrown, it's caught and its stack trace is printed, and the function returns null.
     *
     * @return  a new function that applies this function to the given arguments and prints the stack trace in case of exceptions
     */
    public default Func1<INPUT, OUTPUT> ifExceptionThenPrint() {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, printing the stack trace of any exceptions to the specified print stream.
     * If an exception occurs during function execution, its stack trace is printed to the given print stream and the function returns null.
     *
     * @param printStream  the print stream where exception stack traces are printed
     * @return             a new function that applies this function to the given arguments, printing any exception stack traces to the specified print stream
     */
    public default Func1<INPUT, OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printStream);
                return null;
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, printing the stack trace of any exceptions to the specified print writer.
     * If an exception occurs during function execution, its stack trace is printed to the given print writer, and the function returns null.
     *
     * @param printWriter  the print writer where exception stack traces are printed
     * @return             a new function that applies this function to the given arguments, printing any exception stack traces to the specified print writer
     */
    public default Func1<INPUT, OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printWriter);
                return null;
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a supplier to provide a default value if the result is null or an exception occurs.
     * The function attempts to apply the given arguments, invoking the default supplier for a value if the result is null or if an exception is caught.
     *
     * @param defaultValue  the default value when the function result is null or an exception occurs
     * @return              a new function that applies this function to the given arguments, using the default supplier's value when the result is null or an exception occurs
     */
    public default Func1<INPUT, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : defaultValue;
                return returnValue;
            } catch (Exception e) {
                return defaultValue;
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a supplier to provide a default value if the result is null or an exception occurs.
     * The function attempts to apply the given arguments, invoking the default supplier for a value if the result is null or if an exception is caught.
     *
     * @param defaultSupplier  the supplier that provides a default value when the function result is null or an exception occurs
     * @return                 a new function that applies this function to the given arguments, using the default supplier's value when the result is null or an exception occurs
     */
    public default Func1<INPUT, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                return defaultSupplier.get();
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a function to map either the input tuple and an exception, or the input tuple and null, to an output value.
     * If the function result is null or an exception is caught, the exception mapper is applied to the tuple of inputs and the exception (or null if the result is just absent) to provide a return value.
     *
     * @param exceptionMapper  the function to map a tuple of inputs and an exception (or null if the result is absent) to an output value
     * @return                 a new function that applies this function to the given arguments, using the exception mapper to provide a return value in case of null result or exceptions
     */
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply((Exception)null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a function to map either the input tuple and an exception, or the input tuple and null, to an output value.
     * If the function result is null or an exception is caught, the exception mapper is applied to the tuple of inputs and the exception (or null if the result is just absent) to provide a return value.
     *
     * @param exceptionMapper  the function to map a tuple of inputs and an exception (or null if the result is absent) to an output value
     * @return                 a new function that applies this function to the given arguments, using the exception mapper to provide a return value in case of null result or exceptions
     */
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Func2<INPUT, Exception, OUTPUT> exceptionMapper) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(input, null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(input, e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a specified handler for exceptions and returning a default value if the result is null or an exception occurs.
     * If an exception is caught during the function execution, the exception handler is invoked and the default value is returned. The default value is also returned if the function result is null.
     *
     * @param exceptionHandler  the functional interface to handle exceptions thrown by this function
     * @param defaultValue      the default value to return if the function result is null or an exception is thrown
     * @return                  a new function that applies this function to the given arguments, using the exception handler and returning the default value in case of null result or exceptions
     */
    public default Func1<INPUT, OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : defaultValue;
                return returnValue;
            } catch (Exception e) {
                return defaultValue;
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a specified handler for exceptions and a supplier for a default value if the result is null or an exception occurs.
     * If an exception is caught during function execution, the exception handler is invoked and a value from the default supplier is returned. The default supplier's value is also used if the function result is null.
     *
     * @param exceptionHandler  the functional interface to handle exceptions thrown by this function
     * @param defaultSupplier   the supplier that provides a default value when the function result is null or an exception occurs
     * @return                  a new function that applies this function to the given arguments, using the exception handler and the default supplier in case of null result or exceptions
     */
    public default Func1<INPUT, OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                return defaultSupplier.get();
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a specified handler for exceptions and a mapper to provide a value in case an exception occurs or the result is null.
     * If an exception is caught during function execution, the exception handler is invoked, and then the exception mapper is used to provide a return value. The exception mapper is also applied if the function result is null.
     *
     * @param exceptionHandler  the functional interface to handle exceptions thrown by this function
     * @param exceptionMapper   the function to map an exception to an output value, or provide a value when the result is null
     * @return                  a new function that applies this function to the given arguments, using the exception handler and the exception mapper in case of null result or exceptions
     */
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply((Exception)null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a specified handler for exceptions and a mapper that takes a tuple of inputs and an exception to provide a value.
     * If an exception is caught during function execution, the exception handler is invoked, and then the exception mapper is used to provide a return value using both the tuple of inputs and the exception. The exception mapper is also applied with null as the exception if the function result is null.
     *
     * @param exceptionHandler  the functional interface to handle exceptions thrown by this function
     * @param exceptionMapper   the function to map a tuple of inputs and an exception (or null if result is absent) to an output value
     * @return                  a new function that applies this function to the given arguments, using the exception handler and the exception mapper in case of null result or exceptions
     */
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func2<INPUT, Exception, OUTPUT> exceptionMapper) {
        return (input) -> {
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(input, null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(input, e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, returning a default value if the function result is null.
     * This method safely applies the function to the two given arguments and returns the default value if the result is null.
     *
     * @param input         the input parameter
     * @param defaultValue  the default value to return if the function result is null
     * @return              the result of the function or the default value if the result is null
     */
    public default OUTPUT orElse(INPUT input, OUTPUT defaultValue) {
        return applySafely(input).orElse(defaultValue);
    }
    
    /**
     * Applies a function safely with the given inputs. If the application of the function results in a null value,
     * the supplied default value is returned instead.
     *
     * @param input            the input parameter
     * @param defaultSupplier  the supplier function to provide a default output value
     * @return                 the result of applying the function to the input parameters or the default value if the application results in null
     */
    public default OUTPUT orGet(INPUT input, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input).orGet(defaultSupplier);
    }
    
    //== Convert == 
    
    /**
     * Wraps this function in a safe wrapper that returns a Result object encapsulating the outcome.
     * The resulting function handles any exceptions during the application of this function, 
     *      encapsulating the result or exception within a Result object.
     *
     * @return a function that takes two parameters and returns a Result object containing 
     *              either the function's output of type OUTPUT or any exception thrown
     */
    public default Func1<INPUT, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    /**
     * Converts this function into one that returns an Optional of the output type.
     * This method ensures that any exceptions thrown during the function's execution result in an empty Optional, 
     * rather than propagating the exception.
     *
     * @return  a function that takes two parameters and returns an Optional containing the output of type OUTPUT, or an empty Optional if an exception occurs
     */
    public default Func1<INPUT, Optional<OUTPUT>> optionally() {
        return (input) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    /**
     * Converts this function into an asynchronous version returning a Promise of the output type.
     * The function execution is deferred and managed in an asynchronous manner, with the result encapsulated in a Promise.
     *
     * @return a function that takes two parameters and returns a Promise containing the output of type OUTPUT
     */
    public default Func1<INPUT, Promise<OUTPUT>> async() {
        return input -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input);
            };
            return DeferAction.from(supplier).start().getPromise();
        };
    }
    
    /**
     * Transforms this function into a deferred execution, returning a DeferAction that can be used to manage the execution.
     * The actual execution of the function is deferred until the DeferAction is explicitly started.
     *
     * @return a function that takes two parameters and returns a DeferAction encapsulating the deferred execution of this function, producing an output of type OUTPUT
     */
    public default Func1<INPUT, DeferAction<OUTPUT>> defer() {
        return input -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input);
            };
            return DeferAction.from(supplier);
        };
    }
    
    //== forXXX() -- lift ==
    
    /**
     * Lift this function to works with {@link Optional}.
     *
     * @return a function that takes two {@link Optional} and return {@link Optional}.
     */
    public default Func1<Optional<INPUT>, Optional<OUTPUT>> forOptional() {
        return (input) -> {
            return input.map(value -> {
                val output = apply(value);
                return output;
            });
        };
    }
    
    /**
     * Lift this function to works with {@link Nullable}.
     *
     * @return a function that takes two {@link Nullable} and return {@link Nullable}.
     */
    public default Func1<Nullable<INPUT>, Nullable<OUTPUT>> forNullable() {
        return (input) -> {
            return input.map(value -> {
                return apply(value);
            });
        };
    }
    
    /**
     * Lift this function to works with {@link Result}.
     *
     * @return a function that takes two {@link Result} and return {@link Result}.
     */
    public default Func1<Result<INPUT>, Result<OUTPUT>> forResult() {
        return (input) -> {
            val output = input.map(this);
            return output;
        };
    }
    
    /**
     * Lift this function to works with {@link HasPromise}.
     *
     * @return a function that takes two {@link HasPromise} and return {@link Promise}.
     */
    public default Func1<HasPromise<INPUT>, Promise<OUTPUT>> forPromise() {
        return promise -> {
            return promise.getPromise().map(this);
        };
    }
    
    /**
     * Lift this function to works with {@link Task}.
     *
     * @return a function that takes two {@link Task} and return {@link Task}.
     */
    public default Func1<Task<INPUT>, Task<OUTPUT>> forTask() {
        return (input) -> {
            val output = input.map(this);
            return output;
        };
    }
    
    /**
     * Lift this function to works with {@link Func0}.
     *
     * @return a function that takes two {@link Func0} and return {@link Func0}.
     */
    public default Func1<Func0<INPUT>, Func0<OUTPUT>> forFunc0() {
        return (input) -> {
            return () -> {
                val value  = input.applyUnsafe();
                val output = applyUnsafe(value);
                return output;
            };
        };
    }
    
    /**
     * Lift this function to works with {@link Func1} from SOURCE.
     *
     *@param <SOURCE>  the source type.
     * @return a function that takes two {@link Func1} and return {@link Func1}.
     */
    public default <SOURCE> Func1<Func1<SOURCE, INPUT>, Func1<SOURCE, OUTPUT>> forFunc1() {
        return (input) -> {
            return source -> {
                val value  = input.apply(source);
                val output = apply(value);
                return output;
            };
        };
    }
    
    /**
     * Ignore the result.
     * 
     * @return a {@link FuncUnit2} from this function.
     **/
    public default FuncUnit1<INPUT> ignoreResult() {
        return FuncUnit1.of((input1) -> applyUnsafe(input1));
    }
    
    /**
     * Converts this function to a {@link Predicate}, where the function's output is compared against {@code true}.
     * This method is useful for transforming a function that returns a boolean value into a predicate.
     *
     * @return a {@code Predicate} that evaluates to true if this function's output is true
     */
    public default Predicate<INPUT> toPredicate() {
        return toPredicate(Boolean.TRUE::equals);
    }
    
    /**
     * Converts this function into a {@link Predicate} using the provided converter to map the function's output to a boolean value.
     * This method facilitates the creation of a predicate based on the transformed output of this function.
     *
     * @param converter  the function that converts this function's output to a boolean value
     * @return           a {@code Predicate} that evaluates to true or false based on the converted output of this function
     */
    public default Predicate<INPUT> toPredicate(Func1<OUTPUT, Boolean> converter) {
        val converted = this.then(converter);
        return Func.toPredicate(converted);
    }
    
    
    // == Partially apply functions -- mix ==
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param input  the second input
     * @return       the new function.
     */
    public default Func0<OUTPUT> curry(INPUT input) {
        return () -> {
            return this.applyUnsafe(input);
        };
    }
    
}
