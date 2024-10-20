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
package functionalj.function;

import static java.util.Objects.requireNonNull;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Consumer;
import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.task.Task;
import lombok.val;

/**
 * Defines a functional interface for a method that takes one input parameters and performs an operation,
 * potentially throwing an Exception.
 * 
 * This interface represents a function that accepts three arguments and returns no result.
 *
 * @param <INPUT1>  the first input data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public interface FuncUnit1<INPUT> extends Consumer<INPUT> {
    
    /**
     * {@link FuncUnit1} that do nothing.
     */
    public static <D> FuncUnit1<D> doNothing() {
        return (item) -> {
        };
    }
    
    /**
     * Wraps a given {@link FuncUnit1} instance, providing a method reference or lambda expression.
     *
     * @param <INPUT>   the type of the first input parameter of the function
     * @param consumer  the {@link FuncUnit2} instance to wrap
     * @return          a new {@link FuncUnit1} instance that delegates to the provided func
     */
    public static <INPUT> FuncUnit1<INPUT> of(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    
    /**
     * Wraps a given {@link FuncUnit1} instance, providing a method reference or lambda expression.
     *
     * @param <INPUT>   the type of the first input parameter of the function
     * @param consumer  the {@link FuncUnit2} instance to wrap
     * @return          a new {@link FuncUnit1} instance that delegates to the provided func
     */
    public static <INPUT> FuncUnit1<INPUT> funcUnit1(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    
    /**
     * Wraps a given {@link FuncUnit1} instance, providing a method reference or lambda expression.
     *
     * @param <INPUT>   the type of the first input parameter of the function
     * @param consumer  the {@link FuncUnit2} instance to wrap
     * @return          a new {@link FuncUnit1} instance that delegates to the provided func
     */
    public static <INPUT> FuncUnit1<INPUT> from(Consumer<INPUT> consumer) {
        return consumer::accept;
    }
    
    
    /**
     * Performs an operation on the given inputs, potentially throwing an exception and return no result.
     * 
     * @throws Exception if the function execution encounters an error
     */
    public void acceptUnsafe(INPUT input) throws Exception;
    
    
    /**
     * Represents a function that takes an input parameter and produces no output.
     * This is a functional interface whose functional method is {@link #acceptUnsafe}.
     * This function ignore any exception that might be thrown.
     * 
     * @throws Exception if the function execution encounters an error
     */
    public default void acceptCarelessly(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (Exception e) {
        }
    }
    
    /**
     * Applies this function safely to the input parameter, returning a {@code Result<OUTPUT>}.
     * This method wraps the function application in a try-catch block, capturing any exceptions that occur during execution.
     * 
     * @param input  the input parameter.
     * @return       a {@code Result<OUTPUT>} containing the result if successful, or an exception if an error occurs during function application.
     */
    public default Result<Void> acceptSafely(INPUT input) {
        try {
            acceptUnsafe(input);
            return Result.ofNull();
        } catch (Exception e) {
            return Result.ofException(e);
        }
    }
    
    
    //== Accept ==
    
    /**
     * Represents a function that takes the input parameter and produces no output.
     * This is a functional interface whose functional method is {@link #acceptUnsafe}.
     * This function ignore any exception that might be thrown.
     * 
     * @throws Exception if the function execution encounters an error
     */
    public default void accept(INPUT input) {
        try {
            acceptUnsafe(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    /**
     * Accept the given input values wrapped with {@link Result}.
     * 
     * @param input  the result.
     * @return       a {@code Result<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Result<OUTPUT> acceptTo(Result<INPUT> input1) {
        return input1.map(value1 -> {
            accept(value1);
            return (OUTPUT)null;
        });
    }
    
    /**
     * Accept the given input values wrapped with {@link Promise}.
     * 
     * @param input  the promise.
     * @return       a {@code Promise<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Promise<OUTPUT> acceptTo(HasPromise<INPUT> input) {
        return input.getPromise().map(value -> {
            accept(value);
            return (OUTPUT)null;
        });
    }
    
    /**
     * Accept the given input values wrapped with {@link Task}.
     * 
     * @param input  the first task.
     * @return       a {@code Task<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Task<OUTPUT> acceptTo(Task<INPUT> input) {
        return input.map(value -> {
            accept(value);
            return (OUTPUT)null;
        });
    }
    
    /**
     * Accept the given input values that were returned from {@link Func0}.
     * 
     * @param input  the first {@code Func0} providing {@code INPUT1}.
     * @return       a {@code Func0<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Func0<OUTPUT> acceptTo(Func0<INPUT> input) {
        return () -> {
            val value = input.get();
            accept(value);
            return null;
        };
    }
    
    /**
     * Transforms a function taking multiple input parameters into a function that takes a single source parameter. 
     * Each input function extracts a corresponding value from the source, and these values are then applied to this function.
     * 
     * @param input  function to extract the first input value from the source
     * @return       a function that takes a single source parameter and returns an output by applying this function to the extracted input values
     */
    public default <SOURCE, OUTPUT> Func1<SOURCE,OUTPUT> acceptTo(Func1<SOURCE, INPUT> input) {
        return source -> {
            val value = input.applyUnsafe(source);
            accept(value);
            return null;
        };
    }
    
    
    //== Compose ==
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  after     the function to be run after this function.
     * @return           the composed function.
     */
    public default FuncUnit1<INPUT> then(FuncUnit0 after) {
        requireNonNull(after);
        return (input) -> {
            acceptUnsafe(input);
            after.runUnsafe();
        };
    }
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  after     the function to be run after this function.
     * @return           the composed function.
     */
    public default FuncUnit1<INPUT> then(FuncUnit1<? super INPUT> after) {
        requireNonNull(after);
        return (input) -> {
            acceptUnsafe(input);
            after.acceptUnsafe(input);
        };
    }
    
    /**
     * Convert this {@link FuncUnit1} to {@link Func1} by having it return null.
     * 
     * @return  the {@link Func1}.
     */
    public default <T> Func1<INPUT, T> thenReturnNull() {
        return thenReturn(null);
    }
    
    /**
     * Convert this {@link FuncUnit1} to {@link Func1} by having it return the given value.
     * 
     * @return  the {@link Func1}.
     */
    public default <T> Func1<INPUT, T> thenReturn(T value) {
        return (input) -> {
            acceptUnsafe(input);
            return value;
        };
    }
    
    /**
     * Convert this {@link FuncUnit1} to {@link Func1} by having it return the value from the supplier.
     * 
     * @return  the {@link Func1}.
     */
    public default <T> Func1<INPUT, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return (input) -> {
            acceptUnsafe(input);
            val value = supplier.applyUnsafe();
            return value;
        };
    }
    
    /**
     * Applies this function to the given arguments, handling any exceptions using the specified exception handler.
     * If an exception is thrown during the function execution, the exception handler is invoked with the caught exception.
     *
     * @param exceptionHandler  the functional interface to handle exceptions thrown by this function
     * @return                  a new function that applies this function to the given arguments and uses the provided exception handler in case of exceptions
     */
    public default FuncUnit1<INPUT> ifException(FuncUnit1<Exception> exceptionHandler) {
        return (input) -> {
            try {
                this.acceptUnsafe(input);
            } catch (Exception e) {
                exceptionHandler.accept(e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments and prints the stack trace of any exception that occurs during execution.
     * If an exception is thrown, it's caught and its stack trace is printed, and the function returns null.
     *
     * @return  a new function that applies this function to the given arguments and prints the stack trace in case of exceptions
     */
    public default FuncUnit1<INPUT> ifExceptionThenPrint() {
        return (input) -> {
            try {
                this.acceptUnsafe(input);
            } catch (Exception e) {
                e.printStackTrace();
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
    public default FuncUnit1<INPUT> ifExceptionThenPrint(PrintStream printStream) {
        return (input) -> {
            try {
                this.acceptUnsafe(input);
            } catch (Exception e) {
                e.printStackTrace(printStream);
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
    public default FuncUnit1<INPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input) -> {
            try {
                this.acceptUnsafe(input);
            } catch (Exception e) {
                e.printStackTrace(printWriter);
            }
        };
    }
    
    /**
     * Convert this function to check if any the input is <code>null</code>, if so skip the execution.
     * 
     * @return  the converted function.
     */
    public default FuncUnit1<INPUT> ignoreNullInput() {
        return (input) -> {
            if ((input != null))
                acceptUnsafe(input);
        };
    }
    
    //== Convert ==
    
    /**
     * Converts this function to absorb any exception that might be thrown.
     * 
     * @return  the converted function.
     */
    public default FuncUnit1<INPUT> carelessly() {
        return this::acceptCarelessly;
    }
    
    /**
     * Converts this function into an asynchronous version returning a Promise of the output type.
     * The function execution is deferred and managed in an asynchronous manner, with the result encapsulated in a Promise.
     *
     * @return a function that takes three parameters and returns a Promise containing the output of type OUTPUT
     */
    public default Func1<INPUT, Promise<Object>> async() {
        return this.thenReturnNull().async();
    }
    
    /**
     * Transforms this function into a deferred execution, returning a DeferAction that can be used to manage the execution.
     * The actual execution of the function is deferred until the DeferAction is explicitly started.
     *
     * @return a function that takes three parameters and returns a DeferAction encapsulating the deferred execution of this function, producing an output of type OUTPUT
     */
    public default Func1<INPUT, DeferAction<Object>> defer() {
        return this.thenReturnNull().defer();
    }
    
    //== forXXX() -- lift ==
    
    /**
     * Lift this function to works with {@link Optional}.
     *
     * @return a function that takes two {@link Optional} and return {@link Optional}.
     */
    public default FuncUnit1<Optional<INPUT>> forOptional() {
        return input -> input.map(value -> {
            accept(value);
            return null;
        });
    }
    
    /**
     * Lift this function to works with {@link Result}.
     *
     * @return a function that takes two {@link Result} and return {@link Result}.
     */
    public default <OUTPUT> Func1<Result<INPUT>, Result<OUTPUT>> forResult() {
        return (input) -> {
            try {
                val value = input.get();
                acceptUnsafe(value);
                return Result.ofNull();
            } catch (Exception e) {
                return Result.ofException(e);
            }
        };
    }
    
    /**
     * Adapts this function to work with promises, returning a function that takes promises as input and returns a promise.
     * This method allows the function to be applied to inputs that are encapsulated within Promise objects, combining them into a single output Promise.
     *
     * @return a function that takes three HasPromise parameters, each containing promises, and returns a Promise of type OUTPUT
     */
    public default Func1<HasPromise<INPUT>, Promise<Object>> forPromise() {
        return (promise) -> {
            val func = this.thenReturnNull();
            val result = promise.getPromise().map(func);
            return result;
        };
    }
    
    /**
     * Lift this function to works with {@link Task}.
     *
     * @return a function that takes two {@link Task} and return {@link Task}.
     */
    public default <OUTPUT> Func1<Task<INPUT>, Task<OUTPUT>> forTask() {
        return (input) -> {
            val returnNull = this.thenReturn((OUTPUT)null);
            return input.map(returnNull);
        };
    }
    
    /**
     * Lift this function to works with {@link Func0}.
     *
     * @return a function that takes two {@link Func0} and return {@link Func0}.
     */
    public default <OUTPUT> Func1<Func0<INPUT>, Func0<OUTPUT>> forFunc0() {
        return (input) -> {
            return () -> {
                val value = input.applyUnsafe();
                acceptUnsafe(value);
                return (OUTPUT)null;
            };
        };
    }
    
    /**
     * Lift this function to works with {@link Func1} from SOURCE.
     *
     *@param <SOURCE>  the source type.
     * @return a function that takes two {@link Func1} and return {@link Func1}.
     */
    public default <SOURCE, OUTPUT> Func1<Func1<SOURCE, INPUT>, Func1<SOURCE, OUTPUT>> forFunc1() {
        return (input) -> {
            return source -> {
                val value = input.applyUnsafe(source);
                acceptUnsafe(value);
                return (OUTPUT)null;
            };
        };
    }
    
    /**
     * Ignore the result.
     * 
     * @return a {@link FuncUnit1} from this function.
     **/
    public default FuncUnit1<INPUT> ignoreResult() {
        return (input) -> {
            acceptUnsafe(input);
        };
    }
    
    // == Partially apply functions ==
    
    /**
     * Reduces this function by fixing the first parameter, resulting in a zero-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param input  the value to fix for the first parameter
     * @return       a function that takes the rest of the parameters, excluding the first.
     */
    public default FuncUnit0 apply(INPUT input) {
        return () -> {
            this.acceptUnsafe(input);
        };
    }
    
}
