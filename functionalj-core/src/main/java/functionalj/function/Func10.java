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

import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple10;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * Function of ten parameters.
 *
 * @param <INPUT1>   the first input data type.
 * @param <INPUT2>   the second input data type.
 * @param <INPUT3>   the third input data type.
 * @param <INPUT4>   the forth input data type.
 * @param <INPUT5>   the fifth input data type.
 * @param <INPUT6>   the sixth input data type.
 * @param <INPUT7>   the seventh input data type.
 * @param <INPUT8>   the eighth input data type.
 * @param <INPUT9>   the ninth input data type.
 * @param <INPUT10>  the tenth input data type.
 * @param <OUTPUT>   the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> {
    
    /**
     * Wraps a given Func10 instance, providing a method reference or lambda expression.
     *
     * @param <I1>  the type of the first input parameter of the function
     * @param <I2>  the type of the second input parameter of the function
     * @param <I3>  the type of the third input parameter of the function
     * @param <I4>  the type of the fourth input parameter of the function
     * @param <I5>  the type of the fifth input parameter of the function
     * @param <I6>  the type of the sixth input parameter of the function
     * @param <I7>  the type of the seventh input parameter of the function
     * @param <I8>  the type of the eighth input parameter of the function
     * @param <I9>  the type of the ninth input parameter of the function
     * @param <I10> the type of the tenth input parameter of the function
     * @param <O>   the type of the output of the function
     * @param func  the Func10 instance to wrap
     * @return a new Func10 instance that delegates to the provided func
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> of(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func) {
        return func;
    }
    
    /**
     * Creates a Func10 instance from an existing Func10.
     *
     * @param <I1>  the type of the first input parameter of the function
     * @param <I2>  the type of the second input parameter of the function
     * @param <I3>  the type of the third input parameter of the function
     * @param <I4>  the type of the fourth input parameter of the function
     * @param <I5>  the type of the fifth input parameter of the function
     * @param <I6>  the type of the sixth input parameter of the function
     * @param <I7>  the type of the seventh input parameter of the function
     * @param <I8>  the type of the eighth input parameter of the function
     * @param <I9>  the type of the ninth input parameter of the function
     * @param <I10> the type of the tenth input parameter of the function
     * @param <O>   the type of the output of the function
     * @param func  the existing Func10 instance
     * @return a new Func10 instance that behaves identically to the provided func
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> from(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func) {
        return func;
    }
    
    /**
     * Represents a function that takes ten input parameters and produces an output.
     * This is a functional interface whose functional method is {@link #applyUnsafe}.
     * 
     * @param <INPUT1>  the type of the first input parameter
     * @param <INPUT2>  the type of the second input parameter
     * @param <INPUT3>  the type of the third input parameter
     * @param <INPUT4>  the type of the fourth input parameter
     * @param <INPUT5>  the type of the fifth input parameter
     * @param <INPUT6>  the type of the sixth input parameter
     * @param <INPUT7>  the type of the seventh input parameter
     * @param <INPUT8>  the type of the eighth input parameter
     * @param <INPUT9>  the type of the ninth input parameter
     * @param <INPUT10> the type of the tenth input parameter
     * @param <OUTPUT>  the type of the output produced by this function
     * @return the result of applying this function to the input parameters
     * @throws Exception if the function execution encounters an error
     */
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, INPUT7 input7, INPUT8 input8, INPUT9 input9, INPUT10 input10) throws Exception;
    
    
    //== Apply ==
    
    /**
     * Applies this function to the given input values.
     *
     * @param  input1   the first input.
     * @param  input2   the second input.
     * @param  input3   the third input.
     * @param  input4   the forth input.
     * @param  input5   the fifth input.
     * @param  input6   the sixth input..
     * @param  input7   the seventh input data type.
     * @param  input8   the eighth input data type.
     * @param  input9   the ninth input data type.
     * @param  input10  the tenth input data type.
     * @return          the function result.
     */
    public default OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, INPUT7 input7, INPUT8 input8, INPUT9 input9, INPUT10 input10) {
        try {
            return applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default OUTPUT applyTo(Tuple10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10> input) {
        val _1  = input._1();
        val _2  = input._2();
        val _3  = input._3();
        val _4  = input._4();
        val _5  = input._5();
        val _6  = input._6();
        val _7  = input._7();
        val _8  = input._8();
        val _9  = input._9();
        val _10 = input._10();
        return apply(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    /**
     * Applies this function partially, taking the first input parameter and returning a function that takes the remaining parameters.
     * @param  input1  the first input parameter.
     * @return         a {@code Func9} function that takes the remaining nine parameters and produces an output.
     */
    public default Func9<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> applyTo(INPUT1 input1) {
        return (input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            return apply(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
        };
    }
    
    /**
     * Applies this function to ten optional input parameters, returning an {@code Optional} of the output.
     * If any input is empty, the function short-circuits and returns {@code Optional.empty()}.
     * 
     * @param input1   optional first input parameter.
     * @param input2   optional second input parameter.
     * @param input3   optional third input parameter.
     * @param input4   optional fourth input parameter.
     * @param input5   optional fifth input parameter.
     * @param input6   optional sixth input parameter.
     * @param input7   optional seventh input parameter.
     * @param input8   optional eighth input parameter.
     * @param input9   optional ninth input parameter.
     * @param input10  optional tenth input parameter.
     * @return         an {@code Optional<OUTPUT>} containing the result, if all inputs are present; otherwise, {@code Optional.empty()}.
     */
    public default Optional<OUTPUT> applyTo(
                                        Optional<INPUT1>  input1, 
                                        Optional<INPUT2>  input2, 
                                        Optional<INPUT3>  input3, 
                                        Optional<INPUT4>  input4, 
                                        Optional<INPUT5>  input5, 
                                        Optional<INPUT6>  input6, 
                                        Optional<INPUT7>  input7, 
                                        Optional<INPUT8>  input8, 
                                        Optional<INPUT9>  input9, 
                                        Optional<INPUT10> input10) {
        return input1.flatMap(i1 -> {
            return input2.flatMap(i2 -> {
                return input3.flatMap(i3 -> {
                    return input4.flatMap(i4 -> {
                        return input5.flatMap(i5 -> {
                            return input6.flatMap(i6 -> {
                                return input7.flatMap(i7 -> {
                                    return input8.flatMap(i8 -> {
                                        return input9.flatMap(i9 -> {
                                            return input10.map(i10 -> {
                                                return Func10.this.apply(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    }
    
    /**
     * Applies this function to ten {@link Nullable} input parameters, returning a {@code Nullable} of the output.
     * If any input is null, the function short-circuits and returns {@code Nullable.empty()}.
     * 
     * @param input1   nullable first input parameter.
     * @param input2   nullable second input parameter.
     * @param input3   nullable third input parameter.
     * @param input4   nullable fourth input parameter.
     * @param input5   nullable fifth input parameter.
     * @param input6   nullable sixth input parameter.
     * @param input7   nullable seventh input parameter.
     * @param input8   nullable eighth input parameter.
     * @param input9   nullable ninth input parameter.
     * @param input10  nullable tenth input parameter.
     * @return         a {@code Nullable<OUTPUT>} containing the result, if all inputs are non-null; otherwise, {@code Nullable.empty()}.
     */
    public default Nullable<OUTPUT> applyTo(
                                        Nullable<INPUT1>  input1,
                                        Nullable<INPUT2>  input2,
                                        Nullable<INPUT3>  input3,
                                        Nullable<INPUT4>  input4,
                                        Nullable<INPUT5>  input5,
                                        Nullable<INPUT6>  input6,
                                        Nullable<INPUT7>  input7,
                                        Nullable<INPUT8>  input8,
                                        Nullable<INPUT9>  input9,
                                        Nullable<INPUT10> input10) {
        return input1.flatMap(i1 -> {
            return input2.flatMap(i2 -> {
                return input3.flatMap(i3 -> {
                    return input4.flatMap(i4 -> {
                        return input5.flatMap(i5 -> {
                            return input6.flatMap(i6 -> {
                                return input7.flatMap(i7 -> {
                                    return input8.flatMap(i8 -> {
                                        return input9.flatMap(i9 -> {
                                            return input10.map(i10 -> {
                                                return Func10.this.apply(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    }
    
    /**
     * Applies this function to ten {@code HasPromise} instances, returning a {@code Promise} of the output.
     * This method facilitates the process of waiting for all provided promises to be fulfilled and then applying this function to their results.
     * 
     * @param input1   the first promise.
     * @param input2   the second promise.
     * @param input3   the third promise.
     * @param input4   the fourth promise.
     * @param input5   the fifth promise.
     * @param input6   the sixth promise.
     * @param input7   the seventh promise.
     * @param input8   the eighth promise.
     * @param input9   the ninth promise.
     * @param input10  the tenth promise.
     * @return         a {@code Promise<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Promise<OUTPUT> applyTo(
                                    HasPromise<INPUT1>  input1, 
                                    HasPromise<INPUT2>  input2, 
                                    HasPromise<INPUT3>  input3, 
                                    HasPromise<INPUT4>  input4, 
                                    HasPromise<INPUT5>  input5, 
                                    HasPromise<INPUT6>  input6, 
                                    HasPromise<INPUT7>  input7, 
                                    HasPromise<INPUT8>  input8, 
                                    HasPromise<INPUT9>  input9, 
                                    HasPromise<INPUT10> input10) {
        return Promise.from(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10, this);
    }
    
    /**
     * Applies this function to ten {@code Func0} instances, returning a {@code Func0} that produces the output.
     * This method allows for lazy evaluation of the function, only invoking the input functions and applying this function 
     *      when the returned {@code Func0} is invoked.
     * 
     * @param input1   the first {@code Func0} providing {@code INPUT1}.
     * @param input2   the second {@code Func0} providing {@code INPUT2}.
     * @param input3   the third {@code Func0} providing {@code INPUT3}.
     * @param input4   the fourth {@code Func0} providing {@code INPUT4}.
     * @param input5   the fifth {@code Func0} providing {@code INPUT5}.
     * @param input6   the sixth {@code Func0} providing {@code INPUT6}.
     * @param input7   the seventh {@code Func0} providing {@code INPUT7}.
     * @param input8   the eighth {@code Func0} providing {@code INPUT8}.
     * @param input9   the ninth {@code Func0} providing {@code INPUT9}.
     * @param input10  the tenth {@code Func0} providing {@code INPUT10}.
     * @return         a {@code Func0<OUTPUT>} that, when invoked, returns the result of applying this function to the values provided by the input functions.
     */
    public default Func0<OUTPUT> applyTo(
                                    Func0<INPUT1> input1,
                                    Func0<INPUT2> input2,
                                    Func0<INPUT3> input3,
                                    Func0<INPUT4> input4,
                                    Func0<INPUT5> input5,
                                    Func0<INPUT6> input6,
                                    Func0<INPUT7> input7,
                                    Func0<INPUT8> input8,
                                    Func0<INPUT9> input9,
                                    Func0<INPUT10> input10) {
        return () -> {
            val value1  = input1.get();
            val value2  = input2.get();
            val value3  = input3.get();
            val value4  = input4.get();
            val value5  = input5.get();
            val value6  = input6.get();
            val value7  = input7.get();
            val value8  = input8.get();
            val value9  = input9.get();
            val value10 = input10.get();
            val output  = apply(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10);
            return output;
        };
    }
    
    /**
     * Applies this function safely to ten input parameters, returning a {@code Result<OUTPUT>}.
     * This method wraps the function application in a try-catch block, capturing any exceptions that occur during execution.
     * 
     * @param input1   the first input parameter.
     * @param input2   the second input parameter.
     * @param input3   the third input parameter.
     * @param input4   the fourth input parameter.
     * @param input5   the fifth input parameter.
     * @param input6   the sixth input parameter.
     * @param input7   the seventh input parameter.
     * @param input8   the eighth input parameter.
     * @param input9   the ninth input parameter.
     * @param input10  the tenth input parameter.
     * @return         a {@code Result<OUTPUT>} containing the result if successful, or an exception if an error occurs during function application.
     */
    public default Result<OUTPUT> applySafely(
                    INPUT1  input1,
                    INPUT2  input2,
                    INPUT3  input3,
                    INPUT4  input4,
                    INPUT5  input5,
                    INPUT6  input6,
                    INPUT7  input7,
                    INPUT8  input8,
                    INPUT9  input9,
                    INPUT10 input10) {
        try {
            val output = applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
            return Result.valueOf(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
    }
    
    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  <TARGET>  the target result value.
     * @param  after     the function to be run after this function.
     * @return           the composed function.
     */
    public default <TARGET> Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, TARGET> then(Func1<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            val output = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default <TARGET> Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, TARGET> map(Func1<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            val output = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> ifException(FuncUnit1<Exception> exceptionHandler) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> ifExceptionThenPrint() {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentGet(Func0<OUTPUT> defaultSupplier) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentApply(Func2<Tuple10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10), null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10), e);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentUse(FuncUnit1<Exception> exceptionHandler, OUTPUT defaultValue) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentGet(FuncUnit1<Exception> exceptionHandler, Func0<OUTPUT> defaultSupplier) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentApply(FuncUnit1<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> whenAbsentApply(FuncUnit1<Exception> exceptionHandler, Func2<Tuple10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10), null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10), e);
            }
        };
    }
    
    /**
     * Applies this function to the given arguments, returning a default value if the function result is null.
     * This method safely applies the function to the ten given arguments and returns the default value if the result is null.
     *
     * @param input1        the first input parameter
     * @param input2        the second input parameter
     * @param input3        the third input parameter
     * @param input4        the fourth input parameter
     * @param input5        the fifth input parameter
     * @param input6        the sixth input parameter
     * @param input7        the seventh input parameter
     * @param input8        the eighth input parameter
     * @param input9        the ninth input parameter
     * @param input10       the tenth input parameter
     * @param defaultValue  the default value to return if the function result is null
     * @return              the result of the function or the default value if the result is null
     */
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, INPUT7 input7, INPUT8 input8, INPUT9 input9, INPUT10 input10, OUTPUT defaultValue) {
        return applySafely(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10).orElse(defaultValue);
    }
    
    /**
     * Applies a function safely with the given inputs. If the application of the function results in a null value,
     * the supplied default value is returned instead.
     *
     * @param input1           the first input parameter
     * @param input2           the second input parameter
     * @param input3           the third input parameter
     * @param input4           the fourth input parameter
     * @param input5           the fifth input parameter
     * @param input6           the sixth input parameter
     * @param input7           the seventh input parameter
     * @param input8           the eighth input parameter
     * @param input9           the ninth input parameter
     * @param input10          the tenth input parameter
     * @param defaultSupplier  the supplier function to provide a default output value
     * @return                 the result of applying the function to the input parameters or the default value if the application results in null
     */
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, INPUT7 input7, INPUT8 input8, INPUT9 input9, INPUT10 input10, Func0<OUTPUT> defaultSupplier) {
        return applySafely(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10).orGet(defaultSupplier);
    }
    
    /**
     * Wraps this function in a safe wrapper that returns a Result object encapsulating the outcome.
     * The resulting function handles any exceptions during the application of this function, 
     *      encapsulating the result or exception within a Result object.
     *
     * @return a function that takes ten parameters and returns a Result object containing 
     *              either the function's output of type OUTPUT or any exception thrown
     */
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    /**
     * Converts this function into one that returns an Optional of the output type.
     * This method ensures that any exceptions thrown during the function's execution result in an empty Optional, 
     * rather than propagating the exception.
     *
     * @return  a function that takes ten parameters and returns an Optional containing the output of type OUTPUT, or an empty Optional if an exception occurs
     */
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, Optional<OUTPUT>> optionally() {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10));
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, Promise<OUTPUT>> async() {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, DeferAction<OUTPUT>> defer() {
        return (input1, input2, input3, input4, input5, input6, input7, input8, input9, input10) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    public default Func10<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, HasPromise<INPUT4>, HasPromise<INPUT5>, HasPromise<INPUT6>, HasPromise<INPUT7>, HasPromise<INPUT8>, HasPromise<INPUT9>, HasPromise<INPUT10>, Promise<OUTPUT>> forPromise() {
        return (promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9, promise10) -> {
            return Promise.from(promise1, promise2, promise3, promise4, promise5, promise6, promise7, promise8, promise9, promise10, this);
        };
    }
    
    /**
     * Converts this function to accept a single Tuple10 parameter, allowing for grouped input parameters.
     * This method facilitates the use of a single tuple to pass all the necessary inputs to the function.
     *
     * @return a function that takes a Tuple10 containing ten parameters and returns the output of type OUTPUT
     */
    public default Func1<Tuple10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10>, OUTPUT> wholly() {
        return t -> {
            val _1  = t._1();
            val _2  = t._2();
            val _3  = t._3();
            val _4  = t._4();
            val _5  = t._5();
            val _6  = t._6();
            val _7  = t._7();
            val _8  = t._8();
            val _9  = t._9();
            val _10 = t._10();
            return this.applyUnsafe(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
        };
    }
    
    /**
     * Flip the parameter order.
     *
     * @return  the Func6 with parameter in a flipped order.
     */
    public default Func10<INPUT10, INPUT9, INPUT8, INPUT7, INPUT6, INPUT5, INPUT4, INPUT3, INPUT2, INPUT1, OUTPUT> flip() {
        return (i10, i9, i8, i7, i6, i5, i4, i3, i2, i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    //== Elevate ==
    
    /**
     * Transforms this ten-parameter function into a function that returns a single-parameter function.
     * This method elevates the first input parameter, allowing the other nine parameters to be preset, and the first parameter to be applied later.
     *
     * @return a function that takes nine parameters and returns a single-parameter function of type INPUT1, which in turn returns an OUTPUT
     */
    public default Func9<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, Func1<INPUT1, OUTPUT>> elevate() {
        return (i2, i3, i4, i5, i6, i7, i8, i9, i10) -> (i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Creates a single-parameter function by pre-setting the other nine parameters of this ten-parameter function.
     * The resulting function takes the first parameter and applies it along with the pre-set values.
     *
     * @param i2   the second input parameter
     * @param i3   the third input parameter
     * @param i4   the fourth input parameter
     * @param i5   the fifth input parameter
     * @param i6   the sixth input parameter
     * @param i7   the seventh input parameter
     * @param i8   the eighth input parameter
     * @param i9   the ninth input parameter
     * @param i10  the tenth input parameter
     * @return     a function that takes a single parameter of type INPUT1 and returns an OUTPUT
     */
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6, INPUT7 i7, INPUT8 i8, INPUT9 i9, INPUT10 i10) {
        return (i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    //== Split ==
    
    /**
     * Splits this ten-parameter function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining nine parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining nine parameters, to produce an OUTPUT
     */
    public default Func1<INPUT1, Func9<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>> split() {
        return split1();
    }
    
    /**
     * Splits this ten-parameter function into a two-level function composition.
     * The first level takes the first input parameter, returning a function that takes the remaining nine parameters to produce the output.
     *
     * @return a function that takes a single parameter of type INPUT1 and returns a function that takes the remaining nine parameters, to produce an OUTPUT
     */
    public default Func1<INPUT1, Func9<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>> split1() {
        return (i1) -> (i2, i3, i4, i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Splits this ten-parameter function into a two-stage function.
     * The first stage takes the first two input parameters, returning a function that accepts the remaining eight parameters to produce the output.
     *
     * @return a function that takes two parameters of types INPUT1 and INPUT2, and returns a function that takes the remaining eight parameters, to produce an OUTPUT
     */
    public default Func2<INPUT1, INPUT2, Func8<INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>> split2() {
        return (i1, i2) -> (i3, i4, i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Divides this ten-parameter function into a two-level function.
     * The first level takes the first three input parameters, and returns a function that requires the remaining seven parameters to produce the output.
     *
     * @return a function that takes three parameters of types INPUT1, INPUT2, and INPUT3, and returns a function that accepts the remaining seven parameters, to yield an OUTPUT
     */
    public default Func3<INPUT1, INPUT2, INPUT3, Func7<INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>> split3() {
        return (i1, i2, i3) -> (i4, i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Segments this ten-parameter function into a two-tiered function structure.
     * The initial tier takes the first four input parameters and returns a function that requires the final six parameters to generate the output.
     *
     * @return a function that accepts four parameters of types INPUT1, INPUT2, INPUT3, and INPUT4, and returns a function that takes the last six parameters, to produce an OUTPUT
     */
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Func6<INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>> split4() {
        return (i1, i2, i3, i4) -> (i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Splits this ten-parameter function into a dual-layer function composition.
     * The first layer accepts the first five input parameters, yielding a function that requires the remaining five parameters to compute the output.
     *
     * @return a function that takes five parameters of types INPUT1, INPUT2, INPUT3, INPUT4, and INPUT5, and returns a function that operates on the final five parameters, to generate an OUTPUT
     */
    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, Func5<INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>> split5() {
        return (i1, i2, i3, i4, i5) -> (i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Divides this ten-parameter function into two stages.
     * The initial stage handles the first six input parameters, leading to a function that demands the last four parameters for producing the output.
     *
     * @return a function that receives six parameters of types INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, and INPUT6, and returns a function that processes the remaining four parameters, to create an OUTPUT
     */
    public default Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Func4<INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>> split6() {
        return (i1, i2, i3, i4, i5, i6) -> (i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Segments this ten-parameter function into a two-layer composition.
     * The first layer captures the first seven input parameters, providing a function that requires the last three parameters to produce the output.
     *
     * @return a function that takes seven parameters of types INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, and INPUT7, and returns a function that operates on the final three parameters, to yield an OUTPUT
     */
    public default Func7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, Func3<INPUT8, INPUT9, INPUT10, OUTPUT>> split7() {
        return (i1, i2, i3, i4, i5, i6, i7) -> (i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Breaks down this ten-parameter function into a hierarchical two-tier function.
     * The first tier handles the first eight input parameters, setting up a function that takes the last two parameters to generate the output.
     *
     * @return a function that accepts eight parameters of types INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, and INPUT8, and returns a function that processes the remaining two parameters, to produce an OUTPUT
     */
    public default Func8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, Func2<INPUT9, INPUT10, OUTPUT>> split8() {
        return (i1, i2, i3, i4, i5, i6, i7, i8) -> (i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Converts this ten-parameter function into a two-stage function.
     * The first stage takes the first nine input parameters and returns a function that accepts the last parameter to complete the operation and produce the output.
     *
     * @return a function that takes nine parameters of types INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, and INPUT9, and returns a single-parameter function of type INPUT10, which then returns an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, Func1<INPUT10, OUTPUT>> split9() {
        return (i1, i2, i3, i4, i5, i6, i7, i8, i9) -> (i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    // == Partially apply functions ==
    
    /**
     * Reduces this function by fixing the first parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the first parameter
     * @return    a function that takes the rest of the parameters, excluding the first, and returns an OUTPUT
     */
    public default Func9<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> apply1(INPUT1 i1) {
        return (i2, i3, i4, i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Reduces this function by fixing the second parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i2  the value to fix for the second parameter
     * @return    a function that takes the rest of the parameters, excluding the second, and returns an OUTPUT
     */
    public default Func9<INPUT1, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> apply2(INPUT2 i2) {
        return (i1, i3, i4, i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Reduces this function by fixing the third parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i3  the value to fix for the third parameter
     * @return    a function that takes the rest of the parameters, excluding the third, and returns an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> apply3(INPUT3 i3) {
        return (i1, i2, i4, i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Reduces this function by fixing the fourth parameter, resulting in a nine-parameter function.
     * The fixed value is used for the fourth input in subsequent calls.
     *
     * @param i4  the value to fix for the fourth parameter
     * @return    a function that takes the rest of the parameters, excluding the forth, and returns an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> apply4(INPUT4 i4) {
        return (i1, i2, i3, i5, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Modifies this function by setting a constant value for the fifth parameter, creating a nine-parameter function.
     * The fixed fifth input is used in all subsequent function calls.
     *
     * @param i5  the value to fix for the fifth parameter
     * @return    a function that takes rest of the parameters, excluding the fifth, and produces an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> apply5(INPUT5 i5) {
        return (i1, i2, i3, i4, i6, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Adjusts this function by permanently assigning a value to the sixth parameter, resulting in a nine-parameter function.
     * This fixed value for the sixth input is consistently used in future function calls.
     *
     * @param i6  the constant value for the sixth parameter
     * @return    a function that takes rest of the parameters, omitting the sixth, and returns an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> apply6(INPUT6 i6) {
        return (i1, i2, i3, i4, i5, i7, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Alters this function by fixing the seventh parameter's value, transforming it into a nine-parameter function.
     * The established value for the seventh input is utilized in all subsequent executions of the function.
     *
     * @param i7  the predetermined value for the seventh parameter
     * @return    a function that takes rest of the parameters, excluding the seventh, and yields an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT8, INPUT9, INPUT10, OUTPUT> apply7(INPUT7 i7) {
        return (i1, i2, i3, i4, i5, i6, i8, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Alters this function by fixing the eighth parameter's value, transforming it into a nine-parameter function.
     * The established value for the eighth input is utilized in all subsequent executions of the function.
     *
     * @param i8  the predetermined value for the eighth parameter
     * @return    a function that takes rest of the parameters, excluding the eighth, and yields an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT9, INPUT10, OUTPUT> apply8(INPUT8 i8) {
        return (i1, i2, i3, i4, i5, i6, i7, i9, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Alters this function by fixing the ninth parameter's value, transforming it into a nine-parameter function.
     * The established value for the ninth input is utilized in all subsequent executions of the function.
     *
     * @param i9  the predetermined value for the ninth parameter
     * @return    a function that takes rest of the parameters, excluding the ninth, and yields an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT10, OUTPUT> apply9(INPUT9 i9) {
        return (i1, i2, i3, i4, i5, i6, i7, i8, i10) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
    /**
     * Alters this function by fixing the tenth parameter's value, transforming it into a nine-parameter function.
     * The established value for the tenth input is utilized in all subsequent executions of the function.
     *
     * @param i10  the predetermined value for the tenth parameter
     * @return     a function that takes rest of the parameters, excluding the tenth, and yields an OUTPUT
     */
    public default Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> apply10(INPUT10 i10) {
        return (i1, i2, i3, i4, i5, i6, i7, i8, i9) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10);
    }
    
//    // == Partially apply functions -- mix ==
//    
}
