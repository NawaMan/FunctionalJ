// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import java.util.Optional;

import functionalj.exception.Throwables;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.task.Task;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple4;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * Function of four parameters.
 *
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 * @param <INPUT4>  the third input data type.
 * @param <OUTPUT>  the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> {
    
    /**
     * Wraps a given {@link Func4} instance, providing a method reference or lambda expression.
     *
     * @param <I1>  the type of the first input parameter of the function
     * @param <I2>  the type of the second input parameter of the function
     * @param <I3>  the type of the third input parameter of the function
     * @param <I4>  the type of the fourth input parameter of the function
     * @param <O>   the type of the output of the function
     * @param func  the {@link Func4} instance to wrap
     * @return a new {@link Func4} instance that delegates to the provided func
     */
    public static <I1, I2, I3, I4, O> Func4<I1, I2, I3, I4, O> of(Func4<I1, I2, I3, I4, O> func) {
        return func;
    }
    
    /**
     * Creates a {@link Func4} instance from an existing {@link Func4}.
     *
     * @param <I1>  the type of the first input parameter of the function
     * @param <I2>  the type of the second input parameter of the function
     * @param <I3>  the type of the third input parameter of the function
     * @param <I4>  the type of the fourth input parameter of the function
     * @param <O>   the type of the output of the function
     * @param func  the existing {@link Func4} instance
     * @return a new {@link Func4} instance that behaves identically to the provided func
     */
    public static <I1, I2, I3, I4, O> Func4<I1, I2, I3, I4, O> from(Func4<I1, I2, I3, I4, O> func) {
        return func;
    }
    
    /**
     * Represents a function that takes four input parameters and produces an output.
     * This is a functional interface whose functional method is {@link #applyUnsafe}.
     * 
     * @return the result of applying this function to the input parameters
     * @throws Exception if the function execution encounters an error
     */
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) throws Exception;
    
    /**
     * Applies this function safely to four input parameters, returning a {@code Result<OUTPUT>}.
     * This method wraps the function application in a try-catch block, capturing any exceptions that occur during execution.
     * 
     * @param input1  the first input parameter.
     * @param input2  the second input parameter.
     * @param input3  the third input parameter.
     * @param input4  the fourth input parameter.
     * @return        a {@code Result<OUTPUT>} containing the result if successful, or an exception if an error occurs during function application.
     */
    public default Result<OUTPUT> applySafely(
                    INPUT1  input1,
                    INPUT2  input2,
                    INPUT3  input3,
                    INPUT4  input4) {
        try {
            val output = applyUnsafe(input1, input2, input3, input4);
            return Result.valueOf(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
    }
    
    
    //== Apply ==
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input1  the first input.
     * @param  input2  the second input.
     * @param  input3  the third input.
     * @param  input4  the forth input.
     * @return         the function result.
     */
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) {
        try {
            val output = applyUnsafe(input1, input2, input3, input4);
            return output;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw Throwables.exceptionTransformer.value().apply(e);
        }
    }
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT applyTo(Tuple4<INPUT1, INPUT2, INPUT3, INPUT4> input) {
        val _1  = input._1();
        val _2  = input._2();
        val _3  = input._3();
        val _4  = input._4();
        val output = apply(_1, _2, _3, _4);
        return output;
    }
    
    /**
     * Applies this function to four optional input parameters, returning an {@code Optional} of the output.
     * If any input is empty, the function short-circuits and returns {@code Optional.empty()}.
     * 
     * @param input1  optional first input parameter.
     * @param input2  optional second input parameter.
     * @param input3  optional third input parameter.
     * @param input4  optional fourth input parameter.
     * @return        an {@code Optional<OUTPUT>} containing the result, if all inputs are present; otherwise, {@code Optional.empty()}.
     */
    public default Optional<OUTPUT> applyTo(
                                        Optional<INPUT1> input1,
                                        Optional<INPUT2> input2,
                                        Optional<INPUT3> input3,
                                        Optional<INPUT4> input4) {
        return input1.flatMap(i1 -> {
            return input2.flatMap(i2 -> {
                return input3.flatMap(i3 -> {
                    return input4.map(i4 -> {
                        val output = Func4.this.apply(i1, i2, i3, i4);
                        return output;
                    });
                });
            });
        });
    }
    
    /**
     * Applies this function to four {@link Nullable} input parameters, returning a {@code Nullable} of the output.
     * If any input is null, the function short-circuits and returns {@code Nullable.empty()}.
     * 
     * @param input1  nullable first input parameter.
     * @param input2  nullable second input parameter.
     * @param input3  nullable third input parameter.
     * @param input4  nullable fourth input parameter.
     * @return        a {@code Nullable<OUTPUT>} containing the result, if all inputs are non-null; otherwise, {@code Nullable.empty()}.
     */
    public default Nullable<OUTPUT> applyTo(
                                        Nullable<INPUT1> input1,
                                        Nullable<INPUT2> input2,
                                        Nullable<INPUT3> input3,
                                        Nullable<INPUT4> input4) {
        return input1.flatMap(i1 -> {
            return input2.flatMap(i2 -> {
                return input3.flatMap(i3 -> {
                    return input4.map(i4 -> {
                        val output = Func4.this.apply(i1, i2, i3, i4);
                        return output;
                    });
                });
            });
        });
    }
    
    /**
     * Applies this function to four {@code Result} instances, returning a {@code Result} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input1  the first promise.
     * @param input2  the second promise.
     * @param input3  the third promise.
     * @param input4  the fourth promise.
     * @return        a {@code Result<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Result<OUTPUT> applyTo(
                                    Result<INPUT1> input1,
                                    Result<INPUT2> input2,
                                    Result<INPUT3> input3,
                                    Result<INPUT4> input4) {
        val output = Result.ofResults(input1, input2, input3, input4, this);
        return output;
    }
    
    /**
     * Applies this function to four {@code HasPromise} instances, returning a {@code Promise} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input1  the first promise.
     * @param input2  the second promise.
     * @param input3  the third promise.
     * @param input4  the fourth promise.
     * @return        a {@code Promise<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Promise<OUTPUT> applyTo(
                                    HasPromise<INPUT1> input1,
                                    HasPromise<INPUT2> input2,
                                    HasPromise<INPUT3> input3,
                                    HasPromise<INPUT4> input4) {
        val output = Promise.from(input1, input2, input3, input4, this);
        return output;
    }
    
    /**
     * Applies this function to four {@code Task} instances, returning a {@code Task} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input1  the first task.
     * @param input2  the second task.
     * @param input3  the third task.
     * @param input4  the fourth task.
     * @return        a {@code Task<OUTPUT>} that will be fulfilled with the result of applying this function.
     */
    public default Task<OUTPUT> applyTo(
                                    Task<INPUT1>  input1,
                                    Task<INPUT2>  input2,
                                    Task<INPUT3>  input3,
                                    Task<INPUT4>  input4) {
        val output = Task.from(input1, input2, input3, input4, this);
        return output;
    }
    
    /**
     * Applies this function to four {@code Func0} instances, returning a {@code Func0} that produces the output.
     * This method allows for lazy evaluation of the function, only invoking the input functions and applying this function 
     *      when the returned {@code Func0} is invoked.
     * 
     * @param input1  the first {@code Func0} providing {@code INPUT1}.
     * @param input2  the second {@code Func0} providing {@code INPUT2}.
     * @param input3  the third {@code Func0} providing {@code INPUT3}.
     * @param input4  the fourth {@code Func0} providing {@code INPUT4}.
     * @return        a {@code Func0<OUTPUT>} that, when invoked, returns the result of applying this function to the values provided by the input functions.
     */
    public default Func0<OUTPUT> applyTo(
                                    Func0<INPUT1> input1,
                                    Func0<INPUT2> input2,
                                    Func0<INPUT3> input3,
                                    Func0<INPUT4> input4) {
        return () -> {
            val value1  = input1.get();
            val value2  = input2.get();
            val value3  = input3.get();
            val value4  = input4.get();
            val output  = apply(value1, value2, value3, value4);
            return output;
        };
    }
    
    /**
     * Transforms a function taking multiple input parameters into a function that takes a single source parameter. 
     * Each input function extracts a corresponding value from the source, and these values are then applied to this function.
     *
     * @param input1  function to extract the first input value from the source
     * @param input2  function to extract the second input value from the source
     * @param input3  function to extract the third input value from the source
     * @param input4  function to extract the fourth input value from the source
     * @return a function that takes a single source parameter and returns an output by applying this function to the extracted input values
     */
    public default <SOURCE> Func1<SOURCE,OUTPUT> applyTo(
                    Func1<SOURCE,INPUT1> input1,
                    Func1<SOURCE,INPUT2> input2,
                    Func1<SOURCE,INPUT3> input3,
                    Func1<SOURCE,INPUT4> input4) {
        return source -> {
            val value1 = input1.applyUnsafe(source);
            val value2 = input2.applyUnsafe(source);
            val value3 = input3.applyUnsafe(source);
            val value4 = input4.applyUnsafe(source);
            val output = apply(value1, value2, value3, value4);
            return output;
        };
    }
    
    //== Single ==
    
    /**
     * Applies this function partially, taking the first input parameter and returning a function that takes the remaining parameters.
     * 
     * @param  input1  the first input parameter.
     * @return         a {@code Func3} function that takes the remaining parameters and produces an output.
     */
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> apply(INPUT1 input1) {
        return (input2, input3, input4) -> {
            val output = apply(input1, input2, input3, input4);
            return output;
        };
    }
    
    /**
     * Applies the function to a combination of an {@code Optional} of the first input and remaining inputs, returning a {@code Func3} function.
     * The resulting function takes the remaining inputs and produces an {@code Optional} of the output. If the first input is empty, the function returns an empty {@code Optional}.
     *
     * @param optional1  the {@code Optional} of the first input.
     * @return           a {@code Func3} function that takes the remaining inputs and returns an {@code Optional} of the output.
     */
    public default Func3<INPUT2, INPUT3, INPUT4, Optional<OUTPUT>> applyWith(Optional<INPUT1> optional1) {
        return (input2, input3, input4) -> {
            return optional1.map(input1 -> {
                val output = apply(input1, input2, input3, input4);
                return output;
            });
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Nullable} of the first input and remaining inputs, returning a {@code Func3} function.
     * The resulting function takes the remaining inputs and produces a {@code Nullable} of the output. If the first input is null, the function returns a null output.
     *
     * @param nullable1  the {@code Nullable} of the first input.
     * @return           a {@code Func3} function that takes the remaining inputs and returns a {@code Nullable} of the output.
     */
    public default Func3<INPUT2, INPUT3, INPUT4, Nullable<OUTPUT>> applyWith(Nullable<INPUT1> nullable1) {
        return (input2, input3, input4) -> {
            return nullable1.map(input1 -> {
                val output = apply(input1, input2, input3, input4);
                return output;
            });
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Result} of the first input and remaining inputs, returning a {@code Func3} function.
     * The resulting function takes the remaining inputs and produces a {@code Result} of the output. If the first input is an unsuccessful result, the function propagates this result.
     *
     * @param result1  the {@code Result} of the first input.
     * @return         a {@code Func3} function that takes the next remaining inputs and returns a {@code Result} of the output.
     */
    public default Func3<INPUT2, INPUT3, INPUT4, Result<OUTPUT>> applyWith(Result<INPUT1> result1) {
        return (input2, input3, input4) -> {
            return result1.map(input1 -> {
                val output = apply(input1, input2, input3, input4);
                return output;
            });
        };
    }
    
    /**
     * Applies the function to a combination of a promise from {@code HasPromise} of the first input and the remaining inputs, returning a {@code Func3} function.
     * The resulting function takes the remaining inputs and produces a {@code Promise} of the output. It retrieves the promise of the first input from the given {@code HasPromise} object.
     *
     * @param hasPromise1  the {@code HasPromise} containing the promise of the first input.
     * @return             a {@code Func3} function that takes the remaining nine inputs and returns a {@code Promise} of the output.
     */
    public default Func3<INPUT2, INPUT3, INPUT4, Promise<OUTPUT>> applyWith(HasPromise<INPUT1> hasPromise1) {
        return (input2, input3, input4) -> {
            return hasPromise1.getPromise().map(input1 -> {
                val output = apply(input1, input2, input3, input4);
                return output;
            });
        };
    }
    
    /**
     * Applies the function to a combination of a supplier for the first input and the remaining inputs, returning a {@code Func3} function.
     * The resulting function takes the remaining inputs and produces a {@code Func0} that, when invoked, supplies the first input and applies the function to all four inputs.
     *
     * @param supplier1  the {@code Func0} supplier for the first input.
     * @return           a {@code Func3} function that takes the remaining nine inputs and returns a {@code Func0} producing the output.
     */
    public default Func3<INPUT2, INPUT3, INPUT4, Func0<OUTPUT>> applyWith(Func0<INPUT1> supplier1) {
        return (input2, input3, input4) -> {
            return () -> {
                val input1 = supplier1.get();
                val output = apply(input1, input2, input3, input4);
                return output;
            };
        };
    }
    
    /**
     * Transforms the first input using a given function and applies the original function to the transformed input and remaining inputs, returning a {@code Func3} function.
     * The resulting function takes the remaining inputs and a function that transforms an additional input into the first input type, then applies the original function to all inputs.
     *
     * @param function1  the {@code Func1} function to transform an additional input into the first input type.
     * @return           a {@code Func3} function that takes the remaining inputs and a function to transform an additional input, then returns a {@code Func1} producing the output.
     */
    public default <INPUT> Func3<INPUT2, INPUT3, INPUT4, Func1<INPUT, OUTPUT>> applyWith(Func1<INPUT, INPUT1> function1) {
        return (input2, input3, input4) -> {
            return input -> {
                val input1 = function1.apply(input);
                val output = apply(input1, input2, input3, input4);
                return output;
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
    public default <TARGET> Func4<INPUT1, INPUT2, INPUT3, INPUT4, TARGET> then(Func1<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2, input3, input4) -> {
            val output = this.applyUnsafe(input1, input2, input3, input4);
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
    public default <TARGET> Func4<INPUT1, INPUT2, INPUT3, INPUT4, TARGET> map(Func1<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2, input3, input4) -> {
            val output = this.applyUnsafe(input1, input2, input3, input4);
            val target = Func.applyUnsafe(after, output);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifException(FuncUnit1<Exception> exceptionHandler) {
        return (input1, input2, input3, input4) -> {
            try {
                val output = this.applyUnsafe(input1, input2, input3, input4);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifExceptionThenPrint() {
        return (input1, input2, input3, input4) -> {
            try {
                val output = this.applyUnsafe(input1, input2, input3, input4);
                return output;
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return (input1, input2, input3, input4) -> {
            try {
                val output = this.applyUnsafe(input1, input2, input3, input4);
                return output;
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input1, input2, input3, input4) -> {
            try {
                val output = this.applyUnsafe(input1, input2, input3, input4);
                return output;
            } catch (Exception e) {
                e.printStackTrace(printWriter);
                return null;
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, returning a default value if the result is null or an exception occurs.
     * The function attempts to apply the given arguments, returning the default value if the result is null or if an exception is caught.
     *
     * @param defaultValue  the default value to return if the function result is null or an exception is thrown
     * @return              a new function that applies this function to the given arguments, returning the default value when the result is null or an exception occurs
     */
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentGet(Func0<OUTPUT> defaultSupplier) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                return defaultSupplier.get();
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, using a function to map any caught exception to an output value.
     * If the function result is null or an exception is caught, the exception mapper is applied to the exception (or null if the result is just absent) to provide a return value.
     *
     * @param exceptionMapper  the function to map an exception (or null if the result is absent) to an output value
     * @return                 a new function that applies this function to the given arguments, using the exception mapper to provide a return value in case of null result or exceptions
     */
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Func2<Tuple4<INPUT1, INPUT2, INPUT3, INPUT4>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2, input3, input4), null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(Tuple.of(input1, input2, input3, input4), e);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Func5<INPUT1, INPUT2, INPUT3, INPUT4, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(input1, input2, input3, input4, null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(input1, input2, input3, input4, e);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentUse(FuncUnit1<Exception> exceptionHandler, OUTPUT defaultValue) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : defaultValue;
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentGet(FuncUnit1<Exception> exceptionHandler, Func0<OUTPUT> defaultSupplier) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(FuncUnit1<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply((Exception)null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
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
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(FuncUnit1<Exception> exceptionHandler, Func2<Tuple4<INPUT1, INPUT2, INPUT3, INPUT4>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2, input3, input4), null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(Tuple.of(input1, input2, input3, input4), e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, returning a default value if the function result is null.
     * This method safely applies the function to the four given arguments and returns the default value if the result is null.
     *
     * @param input1        the first input parameter
     * @param input2        the second input parameter
     * @param input3        the third input parameter
     * @param input4        the fourth input parameter
     * @param defaultValue  the default value to return if the function result is null
     * @return              the result of the function or the default value if the result is null
     */
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, OUTPUT defaultValue) {
        val result = applySafely(input1, input2, input3, input4);
        val output = result.orElse(defaultValue);
        return output;
    }
    
    /**
     * Applies a function safely with the given inputs. If the application of the function results in a null value,
     * the supplied default value is returned instead.
     *
     * @param input1           the first input parameter
     * @param input2           the second input parameter
     * @param input3           the third input parameter
     * @param input4           the fourth input parameter
     * @param defaultSupplier  the supplier function to provide a default output value
     * @return                 the result of applying the function to the input parameters or the default value if the application results in null
     */
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, Func0<OUTPUT> defaultSupplier) {
        val result = applySafely(input1, input2, input3, input4);
        val output = result.orGet(defaultSupplier);
        return output;
    }
    
    //== Convert == 
    
    /**
     * Wraps this function in a safe wrapper that returns a Result object encapsulating the outcome.
     * The resulting function handles any exceptions during the application of this function, 
     *      encapsulating the result or exception within a Result object.
     *
     * @return a function that takes four parameters and returns a Result object containing 
     *              either the function's output of type OUTPUT or any exception thrown
     */
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    /**
     * Converts this function into one that returns an Optional of the output type.
     * This method ensures that any exceptions thrown during the function's execution result in an empty Optional, 
     * rather than propagating the exception.
     *
     * @return  a function that takes four parameters and returns an Optional containing the output of type OUTPUT, or an empty Optional if an exception occurs
     */
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Optional<OUTPUT>> optionally() {
        return (input1, input2, input3, input4) -> {
            try {
                val output = this.applyUnsafe(input1, input2, input3, input4);
                return Optional.ofNullable(output);
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    /**
     * Converts this function into an asynchronous version returning a Promise of the output type.
     * The function execution is deferred and managed in an asynchronous manner, with the result encapsulated in a Promise.
     *
     * @return a function that takes four parameters and returns a Promise containing the output of type OUTPUT
     */
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Promise<OUTPUT>> async() {
        return (input1, input2, input3, input4) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                val output = this.applyUnsafe(input1, input2, input3, input4);
                return output;
            };
            return DeferAction.from(supplier).start().getPromise();
        };
    }
    
    /**
     * Transforms this function into a deferred execution, returning a DeferAction that can be used to manage the execution.
     * The actual execution of the function is deferred until the DeferAction is explicitly started.
     *
     * @return a function that takes four parameters and returns a DeferAction encapsulating the deferred execution of this function, producing an output of type OUTPUT
     */
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, DeferAction<OUTPUT>> defer() {
        return (input1, input2, input3, input4) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                val output = this.applyUnsafe(input1, input2, input3, input4);
                return output;
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
    public default Func4<Optional<INPUT1>, Optional<INPUT2>, Optional<INPUT3>, Optional<INPUT4>, Optional<OUTPUT>> forOptional() {
        return (input1, input2, input3, input4) -> {
            return input1.flatMap(value1 -> {
                return input2.flatMap(value2 -> {
                    return input3.flatMap(value3 -> {
                        return input4.map(value4 -> {
                            val output = apply(value1, value2, value3, value4);
                            return output;
                        });
                    });
                });
            });
        };
    }
    
    /**
     * Lift this function to works with {@link Nullable}.
     *
     * @return a function that takes two {@link Nullable} and return {@link Nullable}.
     */
    public default Func4<Nullable<INPUT1>, Nullable<INPUT2>, Nullable<INPUT3>, Nullable<INPUT4>, Nullable<OUTPUT>> forNullable() {
        return (input1, input2, input3, input4) -> {
            return input1.flatMap(value1 -> {
                return input2.flatMap(value2 -> {
                    return input3.flatMap(value3 -> {
                        return input4.map(value4 -> {
                            val output = apply(value1, value2, value3, value4);
                            return output;
                        });
                    });
                });
            });
        };
    }
    
    /**
     * Lift this function to works with {@link Result}.
     *
     * @return a function that takes two {@link Result} and return {@link Result}.
     */
    public default Func4<Result<INPUT1>, Result<INPUT2>, Result<INPUT3>, Result<INPUT4>, Result<OUTPUT>> forResult() {
        return (input1, input2, input3, input4) -> {
            val result = Result.ofResults(input1, input2, input3, input4, this);
            return result;
        };
    }
    
    /**
     * Adapts this function to work with promises, returning a function that takes promises as input and returns a promise.
     * This method allows the function to be applied to inputs that are encapsulated within Promise objects, combining them into a single output Promise.
     *
     * @return a function that takes four HasPromise parameters, each containing promises, and returns a Promise of type OUTPUT
     */
    public default Func4<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, HasPromise<INPUT4>, Promise<OUTPUT>> forPromise() {
        return (promise1, promise2, promise3, promise4) -> {
            return Promise.from(promise1, promise2, promise3, promise4, this);
        };
    }
    
    /**
     * Lift this function to works with {@link Task}.
     *
     * @return a function that takes two {@link Task} and return {@link Task}.
     */
    public default Func4<Task<INPUT1>, Task<INPUT2>, Task<INPUT3>, Task<INPUT4>, Task<OUTPUT>> forTask() {
        return (input1, input2, input3, input4) -> {
            return Task.from(input1, input2, input3, input4, this);
        };
    }
    
    /**
     * Lift this function to works with {@link Func0}.
     *
     * @return a function that takes two {@link Func0} and return {@link Func0}.
     */
    public default Func4<Func0<INPUT1>, Func0<INPUT2>, Func0<INPUT3>, Func0<INPUT4>, Func0<OUTPUT>> forFunc0() {
        return (input1, input2, input3, input4) -> {
            return () -> {
                val value1 = input1.applyUnsafe();
                val value2 = input2.applyUnsafe();
                val value3 = input3.applyUnsafe();
                val value4 = input4.applyUnsafe();
                val output = applyUnsafe(value1, value2, value3, value4);
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
    public default <SOURCE> Func4<Func1<SOURCE, INPUT1>, Func1<SOURCE, INPUT2>, Func1<SOURCE, INPUT3>, Func1<SOURCE, INPUT4>, Func1<SOURCE, OUTPUT>> forFunc1() {
        return (input1, input2, input3, input4) -> {
            return source -> {
                val value1 = input1.applyUnsafe(source);
                val value2 = input2.applyUnsafe(source);
                val value3 = input3.applyUnsafe(source);
                val value4 = input4.applyUnsafe(source);
                val output = applyUnsafe(value1, value2, value3, value4);
                return output;
            };
        };
    }
    
    /**
     * Ignore the result.
     * 
     * @return a {@link FuncUnit4} from this function.
     **/
    public default FuncUnit4<INPUT1, INPUT2, INPUT3, INPUT4> ignoreResult() {
        return (input1, input2, input3, input4) -> {
            applyUnsafe(input1, input2, input3, input4);
        };
    }
    
    /**
     * Converts this function to accept a single {@link Tuple4} parameter, allowing for grouped input parameters.
     * This method facilitates the use of a single tuple to pass all the necessary inputs to the function.
     *
     * @return a function that takes a {@link Tuple4} containing four parameters and returns the output of type OUTPUT
     */
    public default Func1<Tuple4<INPUT1, INPUT2, INPUT3, INPUT4>, OUTPUT> wholly() {
        return t -> {
            val _1  = t._1();
            val _2  = t._2();
            val _3  = t._3();
            val _4  = t._4();
            val output = this.applyUnsafe(_1, _2, _3, _4);
            return output;
        };
    }
    
    /**
     * Flip the parameter order.
     *
     * @return  the {@link Func4} with parameter in a flipped order.
     */
    public default Func4<INPUT4, INPUT3, INPUT2, INPUT1, OUTPUT> flip() {
        return (i4, i3, i2, i1) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    //== Elevate ==
    
    /**
     * Transforms this function into a function that returns a single-parameter function.
     * This method elevates the first input parameter, allowing the other nine parameters to be preset, and the first parameter to be applied later.
     *
     * @return a function that takes nine parameters and returns a single-parameter function of type INPUT1, which in turn returns an OUTPUT
     */
    public default Func3<INPUT2, INPUT3, INPUT4, Func1<INPUT1, OUTPUT>> elevate() {
        return (i2, i3, i4) -> (i1) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Creates a single-parameter function by pre-setting the other nine parameters of this function.
     * The resulting function takes the first parameter and applies it along with the pre-set values.
     *
     * @param i2  the second input parameter
     * @param i3  the third input parameter
     * @param i4  the fourth input parameter
     * @return    a function that takes a single parameter of type INPUT1 and returns an OUTPUT
     */
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return (i1) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    //== Split ==
    
    /**
     * Splits this function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining nine parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining nine parameters, to produce an OUTPUT
     */
    public default Func1<INPUT1, Func3<INPUT2, INPUT3, INPUT4, OUTPUT>> split() {
        return split1();
    }
    
    /**
     * Splits this function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining nine parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining nine parameters, to produce an OUTPUT
     */
    public default Func1<INPUT1, Func3<INPUT2, INPUT3, INPUT4, OUTPUT>> split1() {
        return (i1) -> (i2, i3, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Splits this function into a two-stage function.
     * The first stage takes the first two input parameters, returning a function that accepts the remaining eight parameters to produce the output.
     *
     * @return a function that takes two parameters of types INPUT1 and INPUT2, and returns a function that takes the remaining eight parameters, to produce an OUTPUT
     */
    public default Func2<INPUT1, INPUT2, Func2<INPUT3, INPUT4, OUTPUT>> split2() {
        return (i1, i2) -> (i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    /**
     * Divides this function into a two-level function.
     * The first level takes the first three input parameters, and returns a function that requires the remaining seven parameters to produce the output.
     *
     * @return a function that takes three parameters of types INPUT1, INPUT2, and INPUT3, and returns a function that accepts the remaining seven parameters, to yield an OUTPUT
     */
    public default Func3<INPUT1, INPUT2, INPUT3, Func1<INPUT4, OUTPUT>> split3() {
        return (i1, i2, i3) -> (i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    
    // == Partially apply functions ==
    
    /**
     * Reduces this function by fixing the first parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     * 
     * @param i1  the value to fix for the first parameter
     * @return    a function that takes the rest of the parameters, excluding the first, and returns an OUTPUT
     */
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> apply1(INPUT1 i1) {
        return (i2, i3, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Reduces this function by fixing the second parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the second parameter
     * @return    a function that takes the rest of the parameters, excluding the second, and returns an OUTPUT
     */
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> apply2(INPUT2 i2) {
        return (i1, i3, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Reduces this function by fixing the third parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i3  the value to fix for the third parameter
     * @return    a function that takes the rest of the parameters, excluding the third, and returns an OUTPUT
     */
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> apply3(INPUT3 i3) {
        return (i1, i2, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Reduces this function by fixing the fourth parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i4  the value to fix for the fourth parameter
     * @return    a function that takes the rest of the parameters, excluding the forth, and returns an OUTPUT
     */
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> apply4(INPUT4 i4) {
        return (i1, i2, i3) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    
    // == Partially apply functions -- mix ==
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param a2  the placeholder for the second input
     * @param a3  the placeholder for the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> curry(Absent a1, Absent a2, Absent a3, Absent a4) {
        return (i1, i2, i3, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @param a3  the placeholder for the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> curry(INPUT1 i1, Absent a2, Absent a3, Absent a4) {
        return (i2, i3, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @param a3  the placeholder for the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> curry(Absent a1, INPUT2 i2, Absent a3, Absent a4) {
        return (i1, i3, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param i2  the second input
     * @param a3  the placeholder for the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func2<INPUT3, INPUT4, OUTPUT> curry(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4) {
        return (i3, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param a2  the placeholder for the second input
     * @param i3  the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> curry(Absent a1, Absent a2, INPUT3 i3, Absent a4) {
        return (i1, i2, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @param i3  the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func2<INPUT2, INPUT4, OUTPUT> curry(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4) {
        return (i2, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @param i3  the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func2<INPUT1, INPUT4, OUTPUT> curry(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return (i1, i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param i2  the second input
     * @param i3  the third input
     * @param a4  the placeholder for the forth input
     * @return    the new function.
     **/
    public default Func1<INPUT4, OUTPUT> curry(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return (i4) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param a2  the placeholder for the second input
     * @param a3  the placeholder for the third input
     * @param i4  the forth input
     * @return    the new function.
     **/
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> curry(Absent a1, Absent a2, Absent a3, INPUT4 i4) {
        return (i1, i2, i3) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @param a3  the placeholder for the third input
     * @param i4  the forth input
     * @return    the new function.
     **/
    public default Func2<INPUT2, INPUT3, OUTPUT> curry(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4) {
        return (i2, i3) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @param a3  the placeholder for the third input
     * @param i4  the forth input
     * @return    the new function.
     **/
    public default Func2<INPUT1, INPUT3, OUTPUT> curry(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return (i1, i3) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param i2  the second input
     * @param a3  the placeholder for the third input
     * @param i4  the forth input
     * @return    the new function.
     **/
    public default Func1<INPUT3, OUTPUT> curry(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return (i3) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param a2  the placeholder for the second input
     * @param i3  the third input
     * @param i4  the forth input
     * @return    the new function.
     **/
    public default Func2<INPUT1, INPUT2, OUTPUT> curry(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return (i1, i2) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @param i3  the third input
     * @param i4  the forth input
     * @return    the new function.
     **/
    public default Func1<INPUT2, OUTPUT> curry(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return (i2) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @param i3  the third input
     * @param i4  the forth input
     * @return    the new function.
     **/
    public default Func1<INPUT1, OUTPUT> curry(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return (i1) -> {
            val output = this.applyUnsafe(i1, i2, i3, i4);
            return output;
        };
    }
    
}
