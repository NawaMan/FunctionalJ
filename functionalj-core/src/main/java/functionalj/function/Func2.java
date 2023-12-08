// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import functionalj.task.Task;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * Function of two parameters.
 *
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <OUTPUT>  the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func2<INPUT1, INPUT2, OUTPUT> extends BiFunction<INPUT1, INPUT2, OUTPUT> {
    
    /**
     * Wraps a given {@link Func2} instance, providing a method reference or lambda expression.
     *
     * @param <I1>  the type of the first input parameter of the function
     * @param <I2>  the type of the second input parameter of the function
     * @param <O>   the type of the output of the function
     * @param func  the {@link Func2} instance to wrap
     * @return a new {@link Func2} instance that delegates to the provided func
     */
    public static <I1, I2, O> Func2<I1, I2, O> of(Func2<I1, I2, O> func) {
        return func;
    }
    
    /**
     * Creates a {@link Func2} instance from an existing {@link Func3}.
     * 
     * @param <I1>  the type of the first input parameter of the function
     * @param <I2>  the type of the second input parameter of the function
     * @param <O>   the type of the output of the function
     * @param func  the existing {@link Func2} instance
     * @return a new {@link Func2} instance that behaves identically to the provided func
     */
    public static <I1, I2, O> Func2<I1, I2, O> func2(Func2<I1, I2, O> func) {
        return func;
    }
    
    /**
     * Wraps a BiFunction into a {@link Func2}, providing compatibility with the Func2 interface.
     * If the given function is already an instance of Func2, it is cast and returned directly. 
     * Otherwise, it is wrapped in a new {@link Func2} that delegates to its apply method.
     * 
     * @param <I1>  the first input data type
     * @param <I2>  the second input data type
     * @param <O>   the output data type
     * @param func  the BiFunction to be wrapped into a Func2
     * @return      a Func2 instance that represents the given BiFunction
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <I1, I2, O> Func2<I1, I2, O> from(BiFunction<I1, I2, O> func) {
        return (func instanceof Func2)? (Func2)func : func::apply;
    }
    
    
    /**
     * Applies this function to the given arguments, potentially throwing an exception.
     * 
     * @param input1the first input argument
     * @param input2  the second input argument
     * @return        the function result
     * @throws Exception if the function execution encounters an error
     */
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;

    /**
     * Applies this function safely to ten input parameters, returning a {@code Result<OUTPUT>}.
     * This method wraps the function application in a try-catch block, capturing any exceptions that occur during execution.
     * 
     * @param input1  the first input parameter.
     * @param input2  the second input parameter.
     * @return        a {@code Result<OUTPUT>} containing the result if successful, or an exception if an error occurs during function application.
     */
    public default Result<OUTPUT> applySafely(
                    INPUT1  input1,
                    INPUT2  input2) {
        try {
            val output = applyUnsafe(input1, input2);
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
     * @return         the function result.
     */
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2) {
        try {
            return applyUnsafe(input1, input2);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input the tuple input.
     * @return       the function result.
     */
    public default OUTPUT applyTo(Tuple2<INPUT1, INPUT2> input) {
        val _1  = input._1();
        val _2  = input._2();
        return apply(_1, _2);
    }
    
    /**
     * Applies this function to ten optional input parameters, returning an {@code Optional} of the output.
     * If any input is empty, the function short-circuits and returns {@code Optional.empty()}.
     * 
     * @param input1  optional first input parameter.
     * @param input2  optional second input parameter.
     * @return        an {@code Optional<OUTPUT>} containing the result, if all inputs are present; otherwise, {@code Optional.empty()}.
     */
    public default Optional<OUTPUT> applyTo(
                                        Optional<INPUT1> input1,
                                        Optional<INPUT2> input2) {
        return input1.flatMap(i1 -> {
            return input2.map(i2 -> {
                return Func2.this.apply(i1, i2);
            });
        });
    }
    
    /**
     * Applies this function to ten {@link Nullable} input parameters, returning a {@code Nullable} of the output.
     * If any input is null, the function short-circuits and returns {@code Nullable.empty()}.
     * 
     * @param input1  nullable first input parameter.
     * @param input2  nullable second input parameter.
     * @return        a {@code Nullable<OUTPUT>} containing the result, if all inputs are non-null; otherwise, {@code Nullable.empty()}.
     */
    public default Nullable<OUTPUT> applyTo(
                                        Nullable<INPUT1> input1,
                                        Nullable<INPUT2> input2) {
        return input1.flatMap(i1 -> {
            return input2.map(i2 -> {
                return Func2.this.apply(i1, i2);
            });
        });
    }
    
    /**
     * Applies this function to ten {@code Result} instances, returning a {@code Result} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input1  the first promise.
     * @param input2  the second promise.
     * @return        a {@code Result<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Result<OUTPUT> applyTo(
                                    Result<INPUT1> input1,
                                    Result<INPUT2> input2) {
        return Result.ofResults(input1, input2, this);
    }
    
    /**
     * Applies this function to ten {@code HasPromise} instances, returning a {@code Promise} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input1  the first promise.
     * @param input2  the second promise.
     * @return        a {@code Promise<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Promise<OUTPUT> applyTo(
                                    HasPromise<INPUT1> input1,
                                    HasPromise<INPUT2> input2) {
        return Promise.from(input1, input2, this);
    }
    
    /**
     * Applies this function to ten {@code Task} instances, returning a {@code Task} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input1  the first task.
     * @param input2  the second task.
     * @return        a {@code Task<OUTPUT>} that will be fulfilled with the result of applying this function.
     */
    public default Task<OUTPUT> applyTo(
                                    Task<INPUT1> input1,
                                    Task<INPUT2> input2) {
        return Task.from(input1, input2, this);
    }
    
    /**
     * Applies this function to ten {@code Func0} instances, returning a {@code Func0} that produces the output.
     * This method allows for lazy evaluation of the function, only invoking the input functions and applying this function 
     *      when the returned {@code Func0} is invoked.
     * 
     * @param input1  the first {@code Func0} providing {@code INPUT1}.
     * @param input2  the second {@code Func0} providing {@code INPUT2}.
     * @return        a {@code Func0<OUTPUT>} that, when invoked, returns the result of applying this function to the values provided by the input functions.
     */
    public default Func0<OUTPUT> applyTo(
                                    Func0<INPUT1> input1,
                                    Func0<INPUT2> input2) {
        return () -> {
            val value1  = input1.get();
            val value2  = input2.get();
            val output  = apply(value1, value2);
            return output;
        };
    }
    
    //== Single ==
    
    /**
     * Applies this function partially, taking the first input parameter and returning a function that takes the remaining parameters.
     * 
     * @param  input1  the first input parameter.
     * @return         a {@code Func1} function that takes the remaining parameters and produces an output.
     */
    public default Func1<INPUT2, OUTPUT> applyTo(INPUT1 input1) {
        return (input2) -> {
            return apply(input1, input2);
        };
    }
    
    /**
     * Applies the function to a combination of an {@code Optional} of the first input and remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and produces an {@code Optional} of the output. If the first input is empty, the function returns an empty {@code Optional}.
     *
     * @param optional1  the {@code Optional} of the first input.
     * @return           a {@code Func1} function that takes the remaining inputs and returns an {@code Optional} of the output.
     */
    public default Func1<INPUT2, Optional<OUTPUT>> applyTo(Optional<INPUT1> optional1) {
        return (input2) -> {
            return optional1.map(input1 -> {
                return apply(input1, input2);
            });
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Nullable} of the first input and remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and produces a {@code Nullable} of the output. If the first input is null, the function returns a null output.
     *
     * @param nullable1  the {@code Nullable} of the first input.
     * @return           a {@code Func1} function that takes the remaining inputs and returns a {@code Nullable} of the output.
     */
    public default Func1<INPUT2, Nullable<OUTPUT>> applyTo(Nullable<INPUT1> nullable1) {
        return (input2) -> {
            return nullable1.map(input1 -> {
                return apply(input1, input2);
            });
        };
    }
    
    /**
     * Applies the function to a combination of a {@code Result} of the first input and remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and produces a {@code Result} of the output. If the first input is an unsuccessful result, the function propagates this result.
     *
     * @param result1  the {@code Result} of the first input.
     * @return         a {@code Func1} function that takes the next remaining inputs and returns a {@code Result} of the output.
     */
    public default Func1<INPUT2, Result<OUTPUT>> applyTo(Result<INPUT1> result1) {
        return (input2) -> {
            return result1.map(input1 -> {
                return apply(input1, input2);
            });
        };
    }
    
    /**
     * Applies the function to a combination of a promise from {@code HasPromise} of the first input and the remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and produces a {@code Promise} of the output. It retrieves the promise of the first input from the given {@code HasPromise} object.
     *
     * @param hasPromise1  the {@code HasPromise} containing the promise of the first input.
     * @return             a {@code Func1} function that takes the remaining nine inputs and returns a {@code Promise} of the output.
     */
    public default Func1<INPUT2, Promise<OUTPUT>> applyTo(HasPromise<INPUT1> hasPromise1) {
        return (input2) -> {
            return hasPromise1.getPromise().map(input1 -> {
                return apply(input1, input2);
            });
        };
    }
    
    /**
     * Applies the function to a combination of a supplier for the first input and the remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and produces a {@code Func0} that, when invoked, supplies the first input and applies the function to all three inputs.
     *
     * @param supplier1  the {@code Func0} supplier for the first input.
     * @return           a {@code Func1} function that takes the remaining nine inputs and returns a {@code Func0} producing the output.
     */
    public default Func1<INPUT2, Func0<OUTPUT>> applyTo(Func0<INPUT1> supplier1) {
        return (input2) -> {
            return () -> {
                val input1 = supplier1.get();
                return apply(input1, input2);
            };
        };
    }
    
    /**
     * Transforms the first input using a given function and applies the original function to the transformed input and remaining inputs, returning a {@code Func1} function.
     * The resulting function takes the remaining inputs and a function that transforms an additional input into the first input type, then applies the original function to all inputs.
     *
     * @param function1  the {@code Func1} function to transform an additional input into the first input type.
     * @return           a {@code Func1} function that takes the remaining inputs and a function to transform an additional input, then returns a {@code Func1} producing the output.
     */
    public default <INPUT> Func1<INPUT2, Func1<INPUT, OUTPUT>> applyTo(Func1<INPUT, INPUT1> function1) {
        return (input2) -> {
            return input -> {
                val input1 = function1.apply(input);
                return apply(input1, input2);
            };
        };
    }
    
    //== Compose ==
    
    /**
     * Applies this function to elements from two {@link StreamPlus} instances, pairing elements by their position in the streams.
     *
     * @param input1  the first stream of input elements
     * @param input2  the second stream of input elements
     * @return        a {@link StreamPlus} of output elements resulting from applying this function to pairs of elements from input1 and input2
     */
    public default StreamPlus<OUTPUT> apply(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    
    /**
     * Applies this function to elements from two {@link StreamPlus} instances based on the specified {@link ZipWithOption}, pairing elements by their position in the streams.
     *
     * @param input1  the first stream of input elements
     * @param input2  the second stream of input elements
     * @param option  the {@link ZipWithOption} determining how elements from the two streams are paired
     * @return        a {@link StreamPlus} of output elements resulting from applying this function to pairs of elements from input1 and input2 as per the specified option
     */
    public default StreamPlus<OUTPUT> apply(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2, ZipWithOption option) {
        return input1.zipWith(input2, option, this);
    }
    
    /**
     * Applies this function to elements from two {@link FuncList} instances, pairing elements by their position in the lists.
     *
     * @param input1  the first list of input elements
     * @param input2  the second list of input elements
     * @return        a {@link FuncList} of output elements resulting from applying this function to pairs of elements from input1 and input2
     */
    public default FuncList<OUTPUT> apply(FuncList<INPUT1> input1, FuncList<INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    
    /**
     * Applies this function to elements from two {@link FuncList} instances based on the specified {@link ZipWithOption}, pairing elements by their position in the lists.
     *
     * @param input1  the first list of input elements
     * @param input2  the second list of input elements
     * @param option  the {@link ZipWithOption} determining how elements from the two lists are paired
     * @return        a {@link FuncList} of output elements resulting from applying this function to pairs of elements from input1 and input2 as per the specified option
     */
    public default FuncList<OUTPUT> apply(FuncList<INPUT1> input1, FuncList<INPUT2> input2, ZipWithOption option) {
        return input1.zipWith(input2, option, this);
    }
    
    /**
     * Applies this function to pairs of values associated with the same keys in two {@link FuncMap} instances.
     *
     * @param <KEY>   the type of keys in the input maps
     * @param input1  the first map of key-value pairs, with values of type INPUT1
     * @param input2  the second map of key-value pairs, with values of type INPUT2
     * @return        a {@link FuncMap} of key-output pairs, where each output is the result of applying this function to corresponding values from input1 and input2
     */
    public default <KEY> FuncMap<KEY, OUTPUT> apply(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    
    /**
     * Applies this function to pairs of values associated with the same keys in two {@link FuncMap} instances, based on the specified {@link ZipWithOption}.
     *
     * @param <KEY>   the type of keys in the input maps
     * @param input1  the first map of key-value pairs, with values of type INPUT1
     * @param input2  the second map of key-value pairs, with values of type INPUT2
     * @param option  the {@link ZipWithOption} determining how pairs of values are chosen from the two maps
     * @return        a {@link FuncMap} of key-output pairs, where each output is the result of applying this function to corresponding values from input1 and input2 as per the specified option
     */
    public default <KEY> FuncMap<KEY, OUTPUT> apply(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2, ZipWithOption option) {
        return input1.zipWith(input2, option, this);
    }
    
    /**
     * Creates a {@link Func0} that applies this function to the results of two {@link Supplier} instances.
     *
     * @param input1  the supplier of the first input value
     * @param input2  the supplier of the second input value
     * @return        a {@link Func0} that, when invoked, applies this function to the values supplied by input1 and input2
     */
    public default Func0<OUTPUT> apply(Supplier<INPUT1> input1, Supplier<INPUT2> input2) {
        return () -> apply(input1.get(), input2.get());
    }
    
    /**
     * Composes this function with two other functions, each mapping a source value to respective inputs of this function.
     *
     * @param <SOURCE>  the type of the source value for the input functions
     * @param input1    the function that maps the source to the first input of this function
     * @param input2    the function that maps the source to the second input of this function
     * @return          a {@link Func1} that, when applied to a source value, computes the respective inputs using input1 and input2 and then applies this function to those inputs
     */
    public default <SOURCE> Func1<SOURCE, OUTPUT> apply(Func1<SOURCE, INPUT1> input1, Func1<SOURCE, INPUT2> input2) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            return apply(i1, i2);
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
    public default <TARGET> Func2<INPUT1, INPUT2, TARGET> then(Function<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2) -> {
            OUTPUT output = this.applyUnsafe(input1, input2);
            TARGET target = Func.applyUnsafe(after, output);
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
    public default <TARGET> Func2<INPUT1, INPUT2, TARGET> map(Function<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2) -> {
            OUTPUT output = this.applyUnsafe(input1, input2);
            TARGET target = (output != null) ? Func.applyUnsafe(after, output) : null;
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
    public default Func2<INPUT1, INPUT2, OUTPUT> ifException(FuncUnit1<Exception> exceptionHandler) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                return outputValue;
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
    public default Func2<INPUT1, INPUT2, OUTPUT> ifExceptionThenPrint() {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                return outputValue;
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentGet(Func0<OUTPUT> defaultSupplier) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func2<Tuple2<INPUT1, INPUT2>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2), null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(Tuple.of(input1, input2), e);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func3<INPUT1, INPUT2, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(input1, input2, null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(input1, input2, e);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentUse(FuncUnit1<Exception> exceptionHandler, OUTPUT defaultValue) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentGet(FuncUnit1<Exception> exceptionHandler, Func0<OUTPUT> defaultSupplier) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(FuncUnit1<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
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
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func3<INPUT1, INPUT2, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(input1, input2, null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(input1, input2, e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, returning a default value if the function result is null.
     * This method safely applies the function to the ten given arguments and returns the default value if the result is null.
     *
     * @param input1        the first input parameter
     * @param input2        the second input parameter
     * @param defaultValue  the default value to return if the function result is null
     * @return              the result of the function or the default value if the result is null
     */
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, OUTPUT defaultValue) {
        return applySafely(input1, input2).orElse(defaultValue);
    }
    
    /**
     * Applies a function safely with the given inputs. If the application of the function results in a null value,
     * the supplied default value is returned instead.
     *
     * @param input1           the first input parameter
     * @param input2           the second input parameter
     * @param defaultSupplier  the supplier function to provide a default output value
     * @return                 the result of applying the function to the input parameters or the default value if the application results in null
     */
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input1, input2).orGet(defaultSupplier);
    }
    
    //== Convert == 
    
    /**
     * Wraps this function in a safe wrapper that returns a Result object encapsulating the outcome.
     * The resulting function handles any exceptions during the application of this function, 
     *      encapsulating the result or exception within a Result object.
     *
     * @return a function that takes ten parameters and returns a Result object containing 
     *              either the function's output of type OUTPUT or any exception thrown
     */
    public default Func2<INPUT1, INPUT2, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    /**
     * Converts this function into one that returns an Optional of the output type.
     * This method ensures that any exceptions thrown during the function's execution result in an empty Optional, 
     * rather than propagating the exception.
     *
     * @return  a function that takes ten parameters and returns an Optional containing the output of type OUTPUT, or an empty Optional if an exception occurs
     */
    public default Func2<INPUT1, INPUT2, Optional<OUTPUT>> optionally() {
        return (input1, input2) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input1, input2));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    /**
     * Converts this function into an asynchronous version returning a Promise of the output type.
     * The function execution is deferred and managed in an asynchronous manner, with the result encapsulated in a Promise.
     *
     * @return a function that takes ten parameters and returns a Promise containing the output of type OUTPUT
     */
    public default Func2<INPUT1, INPUT2, Promise<OUTPUT>> async() {
        return (input1, input2) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2);
            };
            return DeferAction.from(supplier).start().getPromise();
        };
    }
    
    /**
     * Transforms this function into a deferred execution, returning a DeferAction that can be used to manage the execution.
     * The actual execution of the function is deferred until the DeferAction is explicitly started.
     *
     * @return a function that takes ten parameters and returns a DeferAction encapsulating the deferred execution of this function, producing an output of type OUTPUT
     */
    public default Func2<INPUT1, INPUT2, DeferAction<OUTPUT>> defer() {
        return (input1, input2) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2);
            };
            return DeferAction.from(supplier);
        };
    }
    
    /**
     * Adapts this function to work with promises, returning a function that takes promises as input and returns a promise.
     * This method allows the function to be applied to inputs that are encapsulated within Promise objects, combining them into a single output Promise.
     *
     * @return a function that takes ten HasPromise parameters, each containing promises, and returns a Promise of type OUTPUT
     */
    public default Func2<HasPromise<INPUT1>, HasPromise<INPUT2>, Promise<OUTPUT>> forPromise() {
        return (promise1, promise2) -> {
            return Promise.from(promise1, promise2, this);
        };
    }
    
    /**
     * Converts this function to accept a single {@link Tuple2} parameter, allowing for grouped input parameters.
     * This method facilitates the use of a single tuple to pass all the necessary inputs to the function.
     *
     * @return a function that takes a {@link Tuple2} containing ten parameters and returns the output of type OUTPUT
     */
    public default Func1<Tuple2<INPUT1, INPUT2>, OUTPUT> wholly() {
        return t -> {
            val _1  = t._1();
            val _2  = t._2();
            return this.applyUnsafe(_1, _2);
        };
    }
    
    // TODO - Add this back
//    public default FuncUnit2<INPUT1, INPUT2> ignoreResult() {
//        return FuncUnit2.of((input1, input2) -> applyUnsafe(input1, input2));
//    }
    
    /**
     * Converts this function into a {@link BiPredicate} that evaluates the function and returns true if the result is {@link Boolean#TRUE}.
     *
     * @param i1  the first input parameter of the function
     * @param i2  the second input parameter of the function
     * @return    a {@link BiPredicate} that returns true if the function applied to the input parameters equals {@link Boolean#TRUE}
     */
    public default BiPredicate<INPUT1, INPUT2> toPredicate() {
        return (i1, i2) -> Boolean.TRUE.equals(apply(i1, i2));
    }
    
    /**
     * Converts this function into a {@link BiPredicate} using the provided function to determine the predicate outcome.
     *
     * @param toPredicate  the function to convert the output of this function to a {@link Boolean} value
     * @return             a {@link BiPredicate} that returns the result of applying the toPredicate function to the output of this function
     */
    public default BiPredicate<INPUT1, INPUT2> toPredicate(Func1<OUTPUT, Boolean> toPredicate) {
        return (i1, i2) -> toPredicate.apply((apply(i1, i2)));
    }
    
    /**
     * Flip the parameter order.
     *
     * @return  the {@link Func2} with parameter in a flipped order.
     */
    public default Func2<INPUT2, INPUT1, OUTPUT> flip() {
        return (i2, i1) -> this.applyUnsafe(i1, i2);
    }
    
    //== Elevate ==
    
    /**
     * Transforms this function into a function that returns a single-parameter function.
     * This method elevates the first input parameter, allowing the other nine parameters to be preset, and the first parameter to be applied later.
     *
     * @return a function that takes nine parameters and returns a single-parameter function of type INPUT1, which in turn returns an OUTPUT
     */
    public default Func1<INPUT2, Func1<INPUT1, OUTPUT>> elevate() {
        return (i2) -> (i1) -> this.applyUnsafe(i1, i2);
    }
    
    /**
     * Creates a single-parameter function by pre-setting the other nine parameters of this function.
     * The resulting function takes the first parameter and applies it along with the pre-set values.
     *
     * @param i2  the second input parameter
     * @return    a function that takes a single parameter of type INPUT1 and returns an OUTPUT
     */
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2) {
        return (i1) -> this.applyUnsafe(i1, i2);
    }
    
    /**
     * Elevates this function to operate on {@link Result} objects, returning a {@link Result} of the output.
     *
     * @param i2 the second input parameter wrapped in a {@link Result}
     * @return a {@link Func1} that takes a {@link Result} of the first input and returns a {@link Result} of the output
     */
    public default Func1<Result<INPUT1>, Result<OUTPUT>> elevateWith(Result<INPUT2> i2) {
        return (i1) -> this.applyTo(i1, i2);
    }
    
    /**
     * Elevates this function to operate on {@link HasPromise} objects, returning a {@link Promise} of the output.
     *
     * @param i2 the second input parameter wrapped in a {@link HasPromise}
     * @return a {@link Func1} that takes a {@link HasPromise} of the first input and returns a {@link Promise} of the output
     */
    public default Func1<HasPromise<INPUT1>, Promise<OUTPUT>> elevateWith(HasPromise<INPUT2> i2) {
        return (i1) -> this.applyTo(i1, i2);
    }
    
    //== Split ==
    
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> split() {
        return split1();
    }
    
    /**
     * Splits this function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining nine parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining nine parameters, to produce an OUTPUT
     */
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> split1() {
        return (i1) -> (i2) -> this.applyUnsafe(i1, i2);
    }
    
    
    // == Partially apply functions ==
    
    /**
     * Reduces this function by fixing the first parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the first parameter
     * @return    a function that takes the rest of the parameters, excluding the first, and returns an OUTPUT
     */
    public default Func1<INPUT2, OUTPUT> apply1(INPUT1 i1) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
    
    /**
     * Reduces this function by fixing the second parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the second parameter
     * @return    a function that takes the rest of the parameters, excluding the second, and returns an OUTPUT
     */
    public default Func1<INPUT1, OUTPUT> apply2(INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    
    
    // == Partially apply functions -- mix ==
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param a1  the placeholder for the first input
     * @param i2  the second input
     * @return    the new function.
     **/
    public default Func1<INPUT1, OUTPUT> curry(Absent a1, INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    
    
    /**
     * Partially apply some inputs while leave some absent, then, returns a function that takes the absent inputs.
     * 
     * @param i1  the first input
     * @param a2  the placeholder for the second input
     * @return    the new function.
     **/
    public default Func1<INPUT2, OUTPUT> curry(INPUT1 i1, Absent a2) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
    
}
