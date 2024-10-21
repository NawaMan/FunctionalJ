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
import java.util.function.BiConsumer;
import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.task.Task;
import functionalj.tuple.Tuple2;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * Defines a functional interface for a method that takes two input parameters and performs an operation,
 * potentially throwing an Exception.
 * 
 * This interface represents a function that accepts three arguments and returns no result.
 *
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface FuncUnit2<INPUT1, INPUT2> extends BiConsumer<INPUT1, INPUT2> {
    
    /**
     * Wraps a given {@link FuncUnit2} instance, providing a method reference or lambda expression.
     *
     * @param <INPUT1>  the type of the first input parameter of the function
     * @param <INPUT2>  the type of the second input parameter of the function
     * @param consumer  the {@link FuncUnit2} instance to wrap
     * @return a new {@link FuncUnit2} instance that delegates to the provided func
     */
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> of(FuncUnit2<INPUT1, INPUT2> consumer) {
        return consumer;
    }
    
    /**
     * Creates a {@link FuncUnit2} instance from an existing {@link FuncUnit2}.
     * 
     * @param <INPUT1>  the type of the first input parameter of the function
     * @param <INPUT2>  the type of the second input parameter of the function
     * @param consumer  the existing {@link FuncUnit2} instance
     * @return a new {@link FuncUnit2} instance that behaves identically to the provided func
     */
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> funcUnit2(FuncUnit2<INPUT1, INPUT2> consumer) {
        return consumer;
    }
    
    /**
     * Creates a {@link FuncUnit2} instance from an existing {@link FuncUnit2}.
     * 
     * @param <INPUT1>  the type of the first input parameter of the function
     * @param <INPUT2>  the type of the second input parameter of the function
     * @param consumer  the existing {@link FuncUnit2} instance
     * @return a new {@link FuncUnit2} instance that behaves identically to the provided func
     */
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> from(BiConsumer<INPUT1, INPUT2> consumer) {
        return consumer::accept;
    }
    
    
    /**
     * Performs an operation on the given inputs, potentially throwing an exception and return no result.
     * 
     * @throws Exception if the function execution encounters an error
     */
    public void acceptUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;
    
    
    /**
     * Represents a function that takes two input parameters and produces no output.
     * This is a functional interface whose functional method is {@link #acceptUnsafe}.
     * This function ignore any exception that might be thrown.
     */
    public default void acceptCarelessly(
            INPUT1 input1,
            INPUT2 input2) {
        try {
            acceptUnsafe(input1, input2);
        } catch (Exception e) {
        }
    }
    
    /**
     * Applies this function safely to two input parameters, returning a {@code Result<OUTPUT>}.
     * This method wraps the function application in a try-catch block, capturing any exceptions that occur during execution.
     * 
     * @param input1  the first input parameter.
     * @param input2  the second input parameter.
     * @return        a {@code Result<OUTPUT>} containing the result if successful, or an exception if an error occurs during function application.
     */
    public default Result<Void> acceptSafely(
            INPUT1 input1,
            INPUT2 input2) {
        try {
            acceptUnsafe(input1, input2);
            return Result.ofNull();
        } catch (Exception e) {
            return Result.ofException(e);
        }
    }
    
    
    //== Accept ==
    
    /**
     * Represents a function that takes two input parameters and produces no output.
     * This is a functional interface whose functional method is {@link #acceptUnsafe}.
     * This function ignore any exception that might be thrown.
     */
    public default void accept(
            INPUT1 input1,
            INPUT2 input2) {
        try {
            acceptUnsafe(input1, input2);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    /**
     * Accept the given all input values as {@link Tuple2}.
     *
     * @param  input the tuple input.
     */
    public default void acceptTo(Tuple2<INPUT1, INPUT2> input) {
        val _1  = input._1();
        val _2  = input._2();
        accept(_1, _2);
    }
    
    /**
     * Accept the given input values wrapped with {@link Result}.
     * 
     * @param input1  the first result.
     * @param input2  the second result.
     * @return        a {@code Result<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Result<OUTPUT> acceptTo(
            Result<INPUT1> input1,
            Result<INPUT2> input2) {
        return input1.flatMap(value1 -> {
            return input2.map(value2 -> {
                accept(value1, value2);
                return (OUTPUT)null;
            });
        });
    }
    
    /**
     * Accept the given input values wrapped with {@link Promise}.
     * 
     * @param input1  the first promise.
     * @param input2  the second promise.
     * @return        a {@code Promise<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Promise<OUTPUT> acceptTo(
            HasPromise<INPUT1> input1,
            HasPromise<INPUT2> input2) {
        val output = Promise.from(input1, input2, this.thenReturn((OUTPUT)null));
        return output;
    }
    
    /**
     * Accept the given input values wrapped with {@link Task}.
     * 
     * @param input1  the first task.
     * @param input2  the second task.
     * @return        a {@code Task<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Task<OUTPUT> acceptTo(
            Task<INPUT1> input1,
            Task<INPUT2> input2) {
        val output = Task.from(input1, input2, this.thenReturn((OUTPUT)null));
        return output;
    }
    
    /**
     * Accept the given input values that were returned from {@link Func0}.
     * 
     * @param input1  the first {@code Func0} providing {@code INPUT1}.
     * @param input2  the second {@code Func0} providing {@code INPUT2}.
     * @return        a {@code Func0<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Func0<OUTPUT> acceptTo(
            Func0<INPUT1>  input1,
            Func0<INPUT2>  input2) {
        return () -> {
            val value1 = input1.get();
            val value2 = input2.get();
            accept(value1, value2);
            return null;
        };
    }
    
    /**
     * Transforms a function taking multiple input parameters into a function that takes a single source parameter. 
     * Each input function extracts a corresponding value from the source, and these values are then applied to this function.
     * 
     * @param input1  function to extract the first input value from the source
     * @param input2  function to extract the second input value from the source
     * @return a function that takes a single source parameter and returns an output by applying this function to the extracted input values
     */
    public default <SOURCE, OUTPUT> Func1<SOURCE,OUTPUT> acceptTo(
            Func1<SOURCE,INPUT1> input1,
            Func1<SOURCE,INPUT2> input2) {
        return source -> {
            val value1 = input1.applyUnsafe(source);
            val value2 = input2.applyUnsafe(source);
            accept(value1, value2);
            return null;
        };
    }
    
    //== Single ==
    
    /**
     * Accept the first parameter and return {@link FuncUnit1} that take the rest of the parameters.
     * 
     * @param  input1  the first input parameter.
     * @return         a {@code FuncUnit1} function that takes the remaining parameters and produces an output.
     */
    public default FuncUnit1<INPUT2> accept(INPUT1 input1) {
        return (input2) -> {
            accept(input1, input2);
        };
    }
    
    /**
     * Applies the function to a combination of an {@code Optional} of the first input and remaining inputs, returning a {@code FuncUnit2} function.
     * The resulting function takes the remaining inputs and produces an {@code Optional} of the output. If the first input is empty, the function returns an empty {@code Optional}.
     *
     * @param optional1  the {@code Optional} of the first input.
     * @return           a {@code FuncUnit1} function that takes the remaining inputs and returns an {@code Optional} of the output.
     */
    public default FuncUnit1<INPUT2> acceptWith(Optional<INPUT1> optional1) {
        return (input2) -> {
            if (optional1.isPresent()) {
                val value1 = optional1.get();
                accept(value1, input2);
            }
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Nullable} of the first input and remaining inputs, returning a {@code FuncUnit1} function.
     * The resulting function takes the remaining inputs and produces a {@code Nullable} of the output. If the first input is null, the function returns a null output.
     *
     * @param nullable1  the {@code Nullable} of the first input.
     * @return           a {@code FuncUnit1} function that takes the remaining inputs and returns a {@code Nullable} of the output.
     */
    public default FuncUnit1<INPUT2> acceptWith(Nullable<INPUT1> nullable1) {
        return (input2) -> {
            if (nullable1.isPresent()) {
                val value1 = nullable1.get();
                accept(value1, input2);
            }
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Result} of the first input and remaining inputs, returning a {@code FuncUnit1} function.
     * The resulting function takes the remaining inputs and produces a {@code Result} of the output. If the first input is an unsuccessful result, the function propagates this result.
     *
     * @param result1  the {@code Result} of the first input.
     * @return         a {@code FuncUnit1} function that takes the next remaining inputs and returns a {@code Result} of the output.
     */
    public default FuncUnit1<INPUT2> acceptWith(Result<INPUT1> result1) {
        return (input2) -> {
            if (result1.isPresent()) {
                val value1 = result1.get();
                accept(value1, input2);
            }
        };
    }
    
    /**
     * Applies the function to a combination of a promise from {@code HasPromise} of the first input and the remaining inputs, returning a {@code FuncUnit1} function.
     * The resulting function takes the remaining inputs and produces a {@code Promise} of the output. It retrieves the promise of the first input from the given {@code HasPromise} object.
     *
     * @param hasPromise1  the {@code HasPromise} containing the promise of the first input.
     * @return             a {@code FuncUnit1} function that takes the remaining inputs and returns a {@code Promise} of the output.
     */
    public default FuncUnit1<INPUT2> acceptWith(HasPromise<INPUT1> hasPromise1) {
        return (input2) -> {
            val value1 = hasPromise1.getResult().get();
            accept(value1, input2);
        };
    }
    
    /**
     * Applies the function to a combination of a supplier for the first input and the remaining inputs, returning a {@code FuncUnit1} function.
     * The resulting function takes the remaining inputs and produces a {@code Func0} that, when invoked, supplies the first input and applies the function to all three inputs.
     *
     * @param supplier1  the {@code Func0} supplier for the first input.
     * @return           a {@code FuncUnit1} function that takes the remaining inputs and returns a {@code Func0} producing the output.
     */
    public default FuncUnit1<INPUT2> acceptWith(Func0<INPUT1> supplier1) {
        return (input2) -> {
            val input1 = supplier1.get();
            accept(input1, input2);
        };
    }
    
    /**
     * Transforms the first input using a given function and applies the original function to the transformed input and remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and a function that transforms an additional input into the first input type, then applies the original function to all inputs.
     *
     * @param function1  the {@code Func1} function to transform an additional input into the first input type.
     * @return           a {@code Func1} function that takes the remaining inputs and a function to transform an additional input, then returns a {@code Func1} producing the output.
     */
    public default <INPUT> Func1<INPUT2, FuncUnit1<INPUT>> applyWith(Func1<INPUT, INPUT1> function1) {
        return (input2) -> {
            return input -> {
                val input1 = function1.apply(input);
                accept(input1, input2);
            };
        };
    }
    
    //== Compose ==
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  after  the function to be run after this function.
     * @return        the composed function.
     */
    public default FuncUnit2<INPUT1, INPUT2> then(FuncUnit0 after) {
        requireNonNull(after);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            after.runUnsafe();
        };
    }
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  after  the function to be run after this function.
     * @return        the composed function.
     */
    public default FuncUnit2<INPUT1, INPUT2> then(FuncUnit2<? super INPUT1, ? super INPUT2> after) {
        requireNonNull(after);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            after.acceptUnsafe(input1, input2);
        };
    }
    
    /**
     * Convert this {@link FuncUnit1} to {@link Func1} by having it return null.
     * 
     * @return  the {@link Func1}.
     */
    public default <T> Func2<INPUT1, INPUT2, T> thenReturnNull() {
        return thenReturn(null);
    }
    
    /**
     * Convert this {@link FuncUnit2} to {@link Func2} by having it return the given value.
     * 
     * @return  the {@link Func2}.
     */
    public default <T> Func2<INPUT1, INPUT2, T> thenReturn(T value) {
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
            return value;
        };
    }
    
    /**
     * Convert this {@link FuncUnit2} to {@link Func2} by having it return the value from the supplier.
     * 
     * @return  the {@link Func2}.
     */
    public default <T> Func2<INPUT1, INPUT2, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
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
    public default FuncUnit2<INPUT1, INPUT2> ifException(FuncUnit1<Exception> exceptionHandler) {
        return (input1, input2) -> {
            try {
                this.acceptUnsafe(input1, input2);
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
    public default FuncUnit2<INPUT1, INPUT2> ifExceptionThenPrint() {
        return (input1, input2) -> {
            try {
                this.acceptUnsafe(input1, input2);
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
    public default FuncUnit2<INPUT1, INPUT2> ifExceptionThenPrint(PrintStream printStream) {
        return (input1, input2) -> {
            try {
                this.acceptUnsafe(input1, input2);
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
    public default FuncUnit2<INPUT1, INPUT2> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input1, input2) -> {
            try {
                this.acceptUnsafe(input1, input2);
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
    public default FuncUnit2<INPUT1, INPUT2> ignoreNullInput() {
        return (input1, input2) -> {
            if ((input1 != null) && (input2 != null))
                acceptUnsafe(input1, input2);
        };
    }
    
    //== Convert == 
    
    /**
     * Converts this function to accept a single {@link Tuple2} parameter, allowing for grouped input parameters.
     * This method facilitates the use of a single tuple to pass all the necessary inputs to the function.
     *
     * @return a function that takes a {@link Tuple2} containing three parameters and returns the output of type OUTPUT
     */
    public default FuncUnit1<Tuple2<INPUT1, INPUT2>> wholly() {
        return tuple -> {
            val _1 = tuple._1();
            val _2 = tuple._2();
            acceptUnsafe(_1, _2);
        };
    }
    
    /**
     * Converts this function to absorb any exception that might be thrown.
     * 
     * @return  the converted function.
     */
    public default FuncUnit2<INPUT1, INPUT2> carelessly() {
        return this::acceptCarelessly;
    }
    
    /**
     * Converts this function into an asynchronous version returning a Promise of the output type.
     * The function execution is deferred and managed in an asynchronous manner, with the result encapsulated in a Promise.
     *
     * @return a function that takes three parameters and returns a Promise containing the output of type OUTPUT
     */
    public default Func2<INPUT1, INPUT2, Promise<Object>> async() {
        return this.thenReturnNull().async();
    }
    
    /**
     * Transforms this function into a deferred execution, returning a DeferAction that can be used to manage the execution.
     * The actual execution of the function is deferred until the DeferAction is explicitly started.
     *
     * @return a function that takes three parameters and returns a DeferAction encapsulating the deferred execution of this function, producing an output of type OUTPUT
     */
    public default Func2<INPUT1, INPUT2, DeferAction<Object>> defer() {
        return this.thenReturnNull().defer();
    }
    
    //== forXXX() -- lift ==
    
    /**
     * Lift this function to works with {@link Optional}.
     *
     * @return a function that takes two {@link Optional} and return {@link Optional}.
     */
    public default FuncUnit2<Optional<INPUT1>, Optional<INPUT2>> forOptional() {
        return (input1, input2) -> {
            input1.flatMap(value1 -> {
                input2.map(value2 -> {
                    accept(value1, value2);
                    return null;
                });
                return null;
            });
        };
    }
    
    /**
     * Lift this function to works with {@link Result}.
     *
     * @return a function that takes two {@link Result} and return {@link Result}.
     */
    @SuppressWarnings("unchecked")
    public default <OUTPUT> Func2<Result<INPUT1>, Result<INPUT2>, Result<OUTPUT>> forResult() {
        return (input1, input2) -> {
            val result = Result.ofResults(input1, input2, this.thenReturnNull());
            return (Result<OUTPUT>)result;
        };
    }
    
    /**
     * Adapts this function to work with promises, returning a function that takes promises as input and returns a promise.
     * This method allows the function to be applied to inputs that are encapsulated within Promise objects, combining them into a single output Promise.
     *
     * @return a function that takes three HasPromise parameters, each containing promises, and returns a Promise of type OUTPUT
     */
    public default Func2<HasPromise<INPUT1>, HasPromise<INPUT2>, Promise<Object>> forPromise() {
        return (promise1, promise2) -> {
            val func0 = this.thenReturnNull();
            return Promise.from(promise1, promise2, func0);
        };
    }
    
    /**
     * Lift this function to works with {@link Task}.
     *
     * @return a function that takes two {@link Task} and return {@link Task}.
     */
    public default <OUTPUT> Func2<Task<INPUT1>, Task<INPUT2>, Task<OUTPUT>> forTask() {
        return (input1, input2) -> {
            val returnNull = this.thenReturn((OUTPUT)null);
            return Task.from(input1, input2, returnNull);
        };
    }
    
    /**
     * Lift this function to works with {@link Func0}.
     *
     * @return a function that takes two {@link Func0} and return {@link Func0}.
     */
    public default <OUTPUT> Func2<Func0<INPUT1>, Func0<INPUT2>, Func0<OUTPUT>> forFunc0() {
        return (input1, input2) -> {
            return () -> {
                val value1 = input1.applyUnsafe();
                val value2 = input2.applyUnsafe();
                acceptUnsafe(value1, value2);
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
    public default <SOURCE, OUTPUT> Func2<Func1<SOURCE, INPUT1>, Func1<SOURCE, INPUT2>, Func1<SOURCE, OUTPUT>> forFunc1() {
        return (input1, input2) -> {
            return source -> {
                val value1 = input1.applyUnsafe(source);
                val value2 = input2.applyUnsafe(source);
                acceptUnsafe(value1, value2);
                return (OUTPUT)null;
            };
        };
    }
    
    /**
     * Ignore the result.
     * 
     * @return a {@link FuncUnit2} from this function.
     **/
    public default FuncUnit2<INPUT1, INPUT2> ignoreResult() {
        return (input1, input2) -> {
            acceptUnsafe(input1, input2);
        };
    }
    
    /**
     * Flip the parameter order.
     *
     * @return  the {@link FuncUnit2} with parameter in a flipped order.
     */
    public default FuncUnit2<INPUT2, INPUT1> flip() {
        return (i2, i1) -> {
            this.acceptUnsafe(i1, i2);
        };
    }
    
    //== Elevate ==
    
    /**
     * Transforms this function into a function that returns a single-parameter function.
     * This method elevates the first input parameter, allowing the other parameters to be preset, and the first parameter to be applied later.
     *
     * @return a function that takes one parameter and returns a single-parameter function of type INPUT1
     */
    public default FuncUnit1<INPUT1> elevateWith(INPUT2 input2) {
        return (input1) -> {
            acceptUnsafe(input1, input2);
        };
    }
    
    //== Split ==
    
    /**
     * Splits this function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining parameters
     */
    public default Func1<INPUT1, FuncUnit1<INPUT2>> split() {
        return split1();
    }
    
    /**
     * Splits this function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining parameters
     */
    public default Func1<INPUT1, FuncUnit1<INPUT2>> split1() {
        return (i1) -> (i2) -> {
            this.acceptUnsafe(i1, i2);
        };
    }
    
    
    // == Partially apply functions ==
    
    /**
     * Reduces this function by fixing the first parameter, resulting in a one-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i1  the value to fix for the first parameter
     * @return    a function that takes the rest of the parameters, excluding the first.
     */
    public default FuncUnit1<INPUT2> apply1(INPUT1 i1) {
        return (i2) -> {
            this.acceptUnsafe(i1, i2);
        };
    }
    
    /**
     * Reduces this function by fixing the second parameter, resulting in a one-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the second parameter
     * @return    a function that takes the rest of the parameters, excluding the second.
     */
    public default FuncUnit1<INPUT1> apply2(INPUT2 i2) {
        return (i1) -> {
            this.acceptUnsafe(i1, i2);
        };
    }
    
    
    // == Partially apply functions -- mix ==
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param a2  the placeholder for the second input
     * @return  the new function.
     **/
    public default FuncUnit2<INPUT1, INPUT2> curry(Absent a1, Absent a2) {
        return (i1, i2) -> {
            this.acceptUnsafe(i1, i2);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @return  the new function.
     **/
    public default FuncUnit1<INPUT2> curry(INPUT1 i1, Absent a2) {
        return (i2) -> {
            this.acceptUnsafe(i1, i2);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @return  the new function.
     **/
    public default FuncUnit1<INPUT1> curry(Absent a1, INPUT2 i2) {
        return (i1) -> {
            this.acceptUnsafe(i1, i2);
        };
    }
    
}
