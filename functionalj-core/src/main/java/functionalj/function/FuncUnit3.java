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

import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.task.Task;
import functionalj.tuple.Tuple3;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * Defines a functional interface for a method that takes three input parameters and performs an operation,
 * potentially throwing an Exception.
 * 
 * This interface represents a function that accepts three arguments and returns no result.
 *
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface FuncUnit3<INPUT1, INPUT2, INPUT3> {
    
    /**
     * Wraps a given {@link FuncUnit3} instance, providing a method reference or lambda expression.
     *
     * @param <INPUT1>  the type of the first input parameter of the function
     * @param <INPUT2>  the type of the second input parameter of the function
     * @param <INPUT3>  the type of the third input parameter of the function
     * @param func      the {@link FuncUnit3} instance to wrap
     * @return a new {@link FuncUnit3} instance that delegates to the provided func
     */
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> of(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer;
    }
    
    /**
     * Creates a {@link FuncUnit3} instance from an existing {@link FuncUnit3}.
     * 
     * @param <INPUT1>  the type of the first input parameter of the function
     * @param <INPUT2>  the type of the second input parameter of the function
     * @param <INPUT3>  the type of the third input parameter of the function
     * @param func      the existing {@link FuncUnit3} instance
     * @return a new {@link FuncUnit3} instance that behaves identically to the provided func
     */
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> funcUnit3(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer;
    }
    
    /**
     * Wraps a given {@link FuncUnit3} instance, providing a method reference or lambda expression.
     *
     * @param <INPUT1>  the type of the first input parameter of the function
     * @param <INPUT2>  the type of the second input parameter of the function
     * @param <INPUT3>  the type of the third input parameter of the function
     * @return a new {@link FuncUnit3} instance that delegates to the provided func
     */
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> from(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer::accept;
    }
    
    
    /**
     * Performs an operation on the given inputs, potentially throwing an exception and return no result.
     * 
     * @param <INPUT1>  the type of the first input parameter
     * @param <INPUT2>  the type of the second input parameter
     * @param <INPUT3>  the type of the third input parameter
     * @return          the result of applying this function to the input parameters
     * @throws Exception if the function execution encounters an error
     */  
    public void acceptUnsafe(
                    INPUT1 input1,
                    INPUT2 input2,
                    INPUT3 input3)
                        throws Exception;
    
    
    /**
     * Represents a function that takes three input parameters and produces no output.
     * This is a functional interface whose functional method is {@link #acceptUnsafe}.
     * This function ignore any exception that might be thrown.
     * 
     * @param <INPUT1>  the type of the first input parameter
     * @param <INPUT2>  the type of the second input parameter
     * @param <INPUT3>  the type of the third input parameter
     * @return          the result of applying this function to the input parameters
     * @throws Exception if the function execution encounters an error
     */
    public default void acceptCarelessly(
            INPUT1 input1,
            INPUT2 input2,
            INPUT3 input3) {
        try {
            acceptUnsafe(input1, input2, input3);
        } catch (Exception e) {
        }
    }
    
    /**
     * Applies this function safely to three input parameters, returning a {@code Result<OUTPUT>}.
     * This method wraps the function application in a try-catch block, capturing any exceptions that occur during execution.
     * 
     * @param input1  the first input parameter.
     * @param input2  the second input parameter.
     * @param input3  the third input parameter.
     * @return        a {@code Result<OUTPUT>} containing the result if successful, or an exception if an error occurs during function application.
     */
    public default Result<Void> acceptSafely(
            INPUT1 input1,
            INPUT2 input2,
            INPUT3 input3) {
        try {
            acceptUnsafe(input1, input2, input3);
            return Result.ofNull();
        } catch (Exception e) {
            return Result.ofException(e);
        }
    }
    
    
    //== Accept ==
    
    /**
     * Accept the given input values.
     * If an exception is thrown, the exception will be handled by {@link ThrowFuncs#exceptionTransformer}.
     *
     * @param input1  the first input.
     * @param input2  the second input.
     * @param input3  the third input.
     * @return        the function result.
     */
    public default void accept(
            INPUT1 input1,
            INPUT2 input2,
            INPUT3 input3) {
        try {
            acceptUnsafe(input1, input2, input3);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    /**
     * Accept the given all input values as {@link Tuple3}.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default void acceptTo(Tuple3<INPUT1, INPUT2, INPUT3> input) {
        val _1  = input._1();
        val _2  = input._2();
        val _3  = input._3();
        accept(_1, _2, _3);
    }
    
    /**
     * Accept the given input values wrapped with {@link Result}.
     * 
     * @param input1  the first result.
     * @param input2  the second result.
     * @param input3  the third result.
     * @return        a {@code Result<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Result<OUTPUT> acceptTo(
            Result<INPUT1> input1,
            Result<INPUT2> input2,
            Result<INPUT3> input3) {
        return input1.flatMap(value1 -> {
            return input2.flatMap(value2 -> {
                return input3.map(value3 -> {
                    accept(value1, value2, value3);
                    return (OUTPUT)null;
                });
            });
        });
    }
    
    /**
     * Accept the given input values wrapped with {@link Promise}.
     * 
     * @param input1  the first promise.
     * @param input2  the second promise.
     * @param input3  the third promise.
     * @return        a {@code Promise<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Promise<OUTPUT> acceptTo(
            HasPromise<INPUT1> input1,
            HasPromise<INPUT2> input2,
            HasPromise<INPUT3> input3) {
        val output = Promise.from(input1, input2, input3, this.thenReturn((OUTPUT)null));
        return output;
    }
    
    /**
     * Accept the given input values wrapped with {@link Task}.
     * 
     * @param input1  the first task.
     * @param input2  the second task.
     * @param input3  the third task.
     * @return        a {@code Task<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Task<OUTPUT> acceptTo(
            Task<INPUT1> input1,
            Task<INPUT2> input2,
            Task<INPUT3> input3) {
        val output = Task.from(input1, input2, input3, this.thenReturn((OUTPUT)null));
        return output;
    }
    
    /**
     * Accept the given input values that were returned from {@link Func0}.
     * 
     * @param input1  the first {@code Func0} providing {@code INPUT1}.
     * @param input2  the second {@code Func0} providing {@code INPUT2}.
     * @param input3  the third {@code Func0} providing {@code INPUT3}.
     * @return        a {@code Func0<OUTPUT>} with the result of <code>null</code>.
     */
    public default <OUTPUT> Func0<OUTPUT> acceptTo(
            Func0<INPUT1>  input1,
            Func0<INPUT2>  input2,
            Func0<INPUT3>  input3) {
        return () -> {
            val value1 = input1.get();
            val value2 = input2.get();
            val value3 = input3.get();
            accept(value1, value2, value3);
            return null;
        };
    }
    
    /**
     * Transforms a function taking multiple input parameters into a function that takes a single source parameter. 
     * Each input function extracts a corresponding value from the source, and these values are then applied to this function.
     * 
     * @param input1  function to extract the first input value from the source
     * @param input2  function to extract the second input value from the source
     * @param input3  function to extract the third input value from the source
     * @return a function that takes a single source parameter and returns an output by applying this function to the extracted input values
     */
    public default <SOURCE, OUTPUT> Func1<SOURCE,OUTPUT> acceptTo(
            Func1<SOURCE,INPUT1> input1,
            Func1<SOURCE,INPUT2> input2,
            Func1<SOURCE,INPUT3> input3) {
        return source -> {
            val value1 = input1.applyUnsafe(source);
            val value2 = input2.applyUnsafe(source);
            val value3 = input3.applyUnsafe(source);
            accept(value1, value2, value3);
            return null;
        };
    }
    
    //== Single ==
    
    /**
     * Accept the first parameter and return {@link FuncUnit2} that take the rest of the parameters.
     * 
     * @param  input1  the first input parameter.
     * @return         a {@code FuncUnit2} function that takes the remaining parameters and produces an output.
     */
    public default FuncUnit2<INPUT2, INPUT3> accept(INPUT1 input1) {
        return (input2, input3) -> {
            accept(input1, input2, input3);
        };
    }
    
    /**
     * Applies the function to a combination of an {@code Optional} of the first input and remaining inputs, returning a {@code FuncUnit2} function.
     * The resulting function takes the remaining inputs and produces an {@code Optional} of the output. If the first input is empty, the function returns an empty {@code Optional}.
     *
     * @param optional1  the {@code Optional} of the first input.
     * @return           a {@code FuncUnit2} function that takes the remaining inputs and returns an {@code Optional} of the output.
     */
    public default FuncUnit2<INPUT2, INPUT3> acceptWith(Optional<INPUT1> optional1) {
        return (input2, input3) -> {
            if (optional1.isPresent()) {
                val value1 = optional1.get();
                accept(value1, input2, input3);
            }
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Nullable} of the first input and remaining inputs, returning a {@code FuncUnit2} function.
     * The resulting function takes the remaining inputs and produces a {@code Nullable} of the output. If the first input is null, the function returns a null output.
     *
     * @param nullable1  the {@code Nullable} of the first input.
     * @return           a {@code FuncUnit2} function that takes the remaining inputs and returns a {@code Nullable} of the output.
     */
    public default FuncUnit2<INPUT2, INPUT3> acceptWith(Nullable<INPUT1> nullable1) {
        return (input2, input3) -> {
            if (nullable1.isPresent()) {
                val value1 = nullable1.get();
                accept(value1, input2, input3);
            }
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Result} of the first input and remaining inputs, returning a {@code FuncUnit2} function.
     * The resulting function takes the remaining inputs and produces a {@code Result} of the output. If the first input is an unsuccessful result, the function propagates this result.
     *
     * @param result1  the {@code Result} of the first input.
     * @return         a {@code FuncUnit2} function that takes the next remaining inputs and returns a {@code Result} of the output.
     */
    public default FuncUnit2<INPUT2, INPUT3> acceptWith(Result<INPUT1> result1) {
        return (input2, input3) -> {
            if (result1.isPresent()) {
                val value1 = result1.get();
                accept(value1, input2, input3);
            }
        };
    }
    
    /**
     * Applies the function to a combination of a promise from {@code HasPromise} of the first input and the remaining inputs, returning a {@code FuncUnit2} function.
     * The resulting function takes the remaining inputs and produces a {@code Promise} of the output. It retrieves the promise of the first input from the given {@code HasPromise} object.
     *
     * @param hasPromise1  the {@code HasPromise} containing the promise of the first input.
     * @return             a {@code FuncUnit2} function that takes the remaining inputs and returns a {@code Promise} of the output.
     */
    public default FuncUnit2<INPUT2, INPUT3> acceptWith(HasPromise<INPUT1> hasPromise1) {
        return (input2, input3) -> {
            val value1 = hasPromise1.getResult().get();
            accept(value1, input2, input3);
        };
    }
    
    /**
     * Applies the function to a combination of a supplier for the first input and the remaining inputs, returning a {@code FuncUnit2} function.
     * The resulting function takes the remaining inputs and produces a {@code Func0} that, when invoked, supplies the first input and applies the function to all three inputs.
     *
     * @param supplier1  the {@code Func0} supplier for the first input.
     * @return           a {@code FuncUnit2} function that takes the remaining inputs and returns a {@code Func0} producing the output.
     */
    public default FuncUnit2<INPUT2, INPUT3> acceptWith(Func0<INPUT1> supplier1) {
        return (input2, input3) -> {
            val input1 = supplier1.get();
            accept(input1, input2, input3);
        };
    }
    
    /**
     * Transforms the first input using a given function and applies the original function to the transformed input and remaining inputs, returning a {@code Func2} function.
     * The resulting function takes the remaining inputs and a function that transforms an additional input into the first input type, then applies the original function to all inputs.
     *
     * @param function1  the {@code Func1} function to transform an additional input into the first input type.
     * @return           a {@code Func2} function that takes the remaining inputs and a function to transform an additional input, then returns a {@code Func1} producing the output.
     */
    public default <INPUT> Func2<INPUT2, INPUT3, FuncUnit1<INPUT>> applyWith(Func1<INPUT, INPUT1> function1) {
        return (input2, input3) -> {
            return input -> {
                val input1 = function1.apply(input);
                accept(input1, input2, input3);
            };
        };
    }
    
    //== Compose ==
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  <TARGET>  the target result value.
     * @param  after     the function to be run after this function.
     * @return           the composed function.
     */
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> then(FuncUnit0 after) {
        requireNonNull(after);
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
            after.runUnsafe();
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
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> then(FuncUnit3<? super INPUT1, ? super INPUT2, ? super INPUT3> after) {
        requireNonNull(after);
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
            after.acceptUnsafe(input1, input2, input3);
        };
    }
    
    /**
     * Convert this {@link FuncUnit3} to {@link Func3} by having it return null.
     * 
     * @return  the {@link Func3}.
     */
    public default <T> Func3<INPUT1, INPUT2, INPUT3, T> thenReturnNull() {
        return thenReturn((T)null);
    }
    
    /**
     * Convert this {@link FuncUnit3} to {@link Func3} by having it return the given value.
     * 
     * @return  the {@link Func3}.
     */
    public default <T> Func3<INPUT1, INPUT2, INPUT3, T> thenReturn(T value) {
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
            return value;
        };
    }
    
    /**
     * Convert this {@link FuncUnit3} to {@link Func3} by having it return the value from the supplier.
     * 
     * @return  the {@link Func3}.
     */
    public default <T> Func3<INPUT1, INPUT2, INPUT3, T> thenGet(Func0<T> supplier) {
        requireNonNull(supplier);
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
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
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> ifException(FuncUnit1<Exception> exceptionHandler) {
        return (input1, input2, input3) -> {
            try {
                this.acceptUnsafe(input1, input2, input3);
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
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> ifExceptionThenPrint() {
        return (input1, input2, input3) -> {
            try {
                this.acceptUnsafe(input1, input2, input3);
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
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> ifExceptionThenPrint(PrintStream printStream) {
        return (input1, input2, input3) -> {
            try {
                this.acceptUnsafe(input1, input2, input3);
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
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input1, input2, input3) -> {
            try {
                this.acceptUnsafe(input1, input2, input3);
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
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> ignoreNullInput() {
        return (input1, input2, input3) -> {
            if ((input1 != null) && (input2 != null) && (input3 != null))
                acceptUnsafe(input1, input2, input3);
        };
    }
    
    //== Convert == 
    
    /**
     * Converts this function to accept a single {@link Tuple3} parameter, allowing for grouped input parameters.
     * This method facilitates the use of a single tuple to pass all the necessary inputs to the function.
     *
     * @return a function that takes a {@link Tuple3} containing three parameters and returns the output of type OUTPUT
     */
    public default FuncUnit1<Tuple3<INPUT1, INPUT2, INPUT3>> wholly() {
        return tuple -> {
            val _1  = tuple._1();
            val _2  = tuple._2();
            val _3  = tuple._3();
            acceptUnsafe(_1, _2, _3);
        };
    }
    
    /**
     * Converts this function to absorb any exception that might be thrown.
     * 
     * @return  the converted function.
     */
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> carelessly() {
        return this::acceptCarelessly;
    }
    
    /**
     * Converts this function into an asynchronous version returning a Promise of the output type.
     * The function execution is deferred and managed in an asynchronous manner, with the result encapsulated in a Promise.
     *
     * @return a function that takes three parameters and returns a Promise containing the output of type OUTPUT
     */
    public default Func3<INPUT1, INPUT2, INPUT3, Promise<Object>> async() {
        return this.thenReturnNull().async();
    }
    
    /**
     * Transforms this function into a deferred execution, returning a DeferAction that can be used to manage the execution.
     * The actual execution of the function is deferred until the DeferAction is explicitly started.
     *
     * @return a function that takes three parameters and returns a DeferAction encapsulating the deferred execution of this function, producing an output of type OUTPUT
     */
    public default Func3<INPUT1, INPUT2, INPUT3, DeferAction<Object>> defer() {
        return this.thenReturnNull().defer();
    }
    
    //== forXXX() -- lift ==
    
    /**
     * Lift this function to works with {@link Optional}.
     *
     * @return a function that takes two {@link Optional} and return {@link Optional}.
     */
    public default FuncUnit3<Optional<INPUT1>, Optional<INPUT2>, Optional<INPUT3>> forOptional() {
        return (input1, input2, input3) -> {
            input1.flatMap(value1 -> {
                input2.flatMap(value2 -> {
                    input3.map(value3 -> {
                        accept(value1, value2, value3);
                        return null;
                    });
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
    public default <OUTPUT> Func3<Result<INPUT1>, Result<INPUT2>, Result<INPUT3>, Result<OUTPUT>> forResult() {
        return (input1, input2, input3) -> {
            val result = Result.ofResults(input1, input2, input3, this.thenReturnNull());
            return (Result<OUTPUT>)result;
        };
    }
    
    /**
     * Adapts this function to work with promises, returning a function that takes promises as input and returns a promise.
     * This method allows the function to be applied to inputs that are encapsulated within Promise objects, combining them into a single output Promise.
     *
     * @return a function that takes three HasPromise parameters, each containing promises, and returns a Promise of type OUTPUT
     */
    public default Func3<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, Promise<Object>> forPromise() {
        return (promise1, promise2, promise3) -> {
            val func0 = this.thenReturnNull();
            return Promise.from(promise1, promise2, promise3, func0);
        };
    }
    
    /**
     * Lift this function to works with {@link Task}.
     *
     * @return a function that takes two {@link Task} and return {@link Task}.
     */
    public default <OUTPUT> Func3<Task<INPUT1>, Task<INPUT2>, Task<INPUT3>, Task<OUTPUT>> forTask() {
        return (input1, input2, input3) -> {
            val returnNull = this.thenReturn((OUTPUT)null);
            return Task.from(input1, input2, input3, returnNull);
        };
    }
    
    /**
     * Lift this function to works with {@link Func0}.
     *
     * @return a function that takes two {@link Func0} and return {@link Func0}.
     */
    public default <OUTPUT> Func3<Func0<INPUT1>, Func0<INPUT2>, Func0<INPUT3>, Func0<OUTPUT>> forFunc0() {
        return (input1, input2, input3) -> {
            return () -> {
                val value1 = input1.applyUnsafe();
                val value2 = input2.applyUnsafe();
                val value3 = input3.applyUnsafe();
                acceptUnsafe(value1, value2, value3);
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
    public default <SOURCE, OUTPUT> Func3<Func1<SOURCE, INPUT1>, Func1<SOURCE, INPUT2>, Func1<SOURCE, INPUT3>, Func1<SOURCE, OUTPUT>> forFunc1() {
        return (input1, input2, input3) -> {
            return source -> {
                val value1 = input1.applyUnsafe(source);
                val value2 = input2.applyUnsafe(source);
                val value3 = input3.applyUnsafe(source);
                acceptUnsafe(value1, value2, value3);
                return (OUTPUT)null;
            };
        };
    }
    
    /**
     * Ignore the result.
     * 
     * @return a {@link FuncUnit3} from this function.
     **/
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> ignoreResult() {
        return (input1, input2, input3) -> {
            acceptUnsafe(input1, input2, input3);
        };
    }
    
    /**
     * Flip the parameter order.
     *
     * @return  the {@link FuncUnit3} with parameter in a flipped order.
     */
    public default FuncUnit3<INPUT3, INPUT2, INPUT1> flip() {
        return (i3, i2, i1) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    //== Elevate ==
    
    /**
     * Transforms this function into a function that returns a single-parameter function.
     * This method elevates the first input parameter, allowing the other parameters to be preset, and the first parameter to be applied later.
     *
     * @return a function that takes parameters and returns a single-parameter function of type INPUT1
     */
    public default Func2<INPUT2, INPUT3, FuncUnit1<INPUT1>> elevate() {
        return (i2, i3) -> (i1) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Creates a single-parameter function by pre-setting the other parameters of this function.
     * The resulting function takes the first parameter and applies it along with the pre-set values.
     *
     * @param i2  the second input parameter
     * @param i3  the third input parameter
     * @return    a function that takes a single parameter of type INPUT1
     */
    public default FuncUnit1<INPUT1> elevateWith(INPUT2 i2, INPUT3 i3) {
        return (i1) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    //== Split ==
    
    /**
     * Splits this function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining parameters
     */
    public default Func1<INPUT1, FuncUnit2<INPUT2, INPUT3>> split() {
        return split1();
    }
    
    /**
     * Splits this function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining parameters
     */
    public default Func1<INPUT1, FuncUnit2<INPUT2, INPUT3>> split1() {
        return (i1) -> (i2, i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Splits this function into a two-stage function.
     * The first stage takes the first two input parameters, returning a function that accepts the remaining parameters to produce the output.
     *
     * @return a function that takes two parameters of types INPUT1 and INPUT2, and returns a function that takes the remaining eight parameters
     */
    public default Func2<INPUT1, INPUT2, FuncUnit1<INPUT3>> split2() {
        return (i1, i2) -> (i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    
    // == Partially apply functions ==
    
    /**
     * Reduces this function by fixing the first parameter, resulting in a two-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the first parameter
     * @return    a function that takes the rest of the parameters, excluding the first.
     */
    public default FuncUnit2<INPUT2, INPUT3> apply1(INPUT1 i1) {
        return (i2, i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Reduces this function by fixing the second parameter, resulting in a two-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the second parameter
     * @return    a function that takes the rest of the parameters, excluding the second.
     */
    public default FuncUnit2<INPUT1, INPUT3> apply2(INPUT2 i2) {
        return (i1, i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Reduces this function by fixing the third parameter, resulting in a two-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i3  the value to fix for the third parameter
     * @return    a function that takes the rest of the parameters, excluding the third.
     */
    public default FuncUnit2<INPUT1, INPUT2> apply3(INPUT3 i3) {
        return (i1, i2) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    
    // == Partially apply functions -- mix ==
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param a2  the placeholder for the second input
     * @param a3  the placeholder for the third input
     * @return  the new function.
     **/
    public default FuncUnit3<INPUT1, INPUT2, INPUT3> curry(Absent a1, Absent a2, Absent a3) {
        return (i1, i2, i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @param a3  the placeholder for the third input
     * @return  the new function.
     **/
    public default FuncUnit2<INPUT2, INPUT3> curry(INPUT1 i1, Absent a2, Absent a3) {
        return (i2, i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @param a3  the placeholder for the third input
     * @return  the new function.
     **/
    public default FuncUnit2<INPUT1, INPUT3> curry(Absent a1, INPUT2 i2, Absent a3) {
        return (i1, i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param i2  the second input
     * @param a3  the placeholder for the third input
     * @return  the new function.
     **/
    public default FuncUnit1<INPUT3> curry(INPUT1 i1, INPUT2 i2, Absent a3) {
        return (i3) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param a2  the placeholder for the second input
     * @param i3  the third input
     * @return  the new function.
     **/
    public default FuncUnit2<INPUT1, INPUT2> curry(Absent a1, Absent a2, INPUT3 i3) {
        return (i1, i2) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @param i3  the third input
     * @return  the new function.
     **/
    public default FuncUnit1<INPUT2> curry(INPUT1 i1, Absent a2, INPUT3 i3) {
        return (i2) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @param i3  the third input
     * @return  the new function.
     **/
    public default FuncUnit1<INPUT1> curry(Absent a1, INPUT2 i2, INPUT3 i3) {
        return (i1) -> {
            this.acceptUnsafe(i1, i2, i3);
        };
    }
    
}
