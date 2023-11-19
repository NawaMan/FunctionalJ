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

import functionalj.functions.ThrowFuncs;
import functionalj.tuple.Tuple10;
import lombok.val;

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
    
//    public default Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> applyTo(INPUT1 input1) {
//        return (input2, input3, input4, input5, input6) -> apply(input1, input2, input3, input4, input5, input6);
//    }
//    
//    public default Result<OUTPUT> applyTo(Result<INPUT1> input1, Result<INPUT2> input2, Result<INPUT3> input3, Result<INPUT4> input4, Result<INPUT5> input5, Result<INPUT6> input6) {
//        return Result.ofResults(input1, input2, input3, input4, input5, input6, this);
//    }
//    
//    public default Optional<OUTPUT> applyTo(Optional<INPUT1> input1, Optional<INPUT2> input2, Optional<INPUT3> input3, Optional<INPUT4> input4, Optional<INPUT5> input5, Optional<INPUT6> input6) {
//        return input1.flatMap(i1 -> {
//            return input2.flatMap(i2 -> {
//                return input3.flatMap(i3 -> {
//                    return input4.flatMap(i4 -> {
//                        return input5.flatMap(i5 -> {
//                            return input6.map(i6 -> {
//                                return Func10.this.apply(i1, i2, i3, i4, i5, i6);
//                            });
//                        });
//                    });
//                });
//            });
//        });
//    }
//    
//    public default Nullable<OUTPUT> applyTo(Nullable<INPUT1> input1, Nullable<INPUT2> input2, Nullable<INPUT3> input3, Nullable<INPUT4> input4, Nullable<INPUT5> input5, Nullable<INPUT6> input6) {
//        return input1.flatMap(i1 -> {
//            return input2.flatMap(i2 -> {
//                return input3.flatMap(i3 -> {
//                    return input4.flatMap(i4 -> {
//                        return input5.flatMap(i5 -> {
//                            return input6.map(i6 -> {
//                                return Func10.this.apply(i1, i2, i3, i4, i5, i6);
//                            });
//                        });
//                    });
//                });
//            });
//        });
//    }
//    
//    public default Promise<OUTPUT> applyTo(HasPromise<INPUT1> input1, HasPromise<INPUT2> input2, HasPromise<INPUT3> input3, HasPromise<INPUT4> input4, HasPromise<INPUT5> input5, HasPromise<INPUT6> input6) {
//        return Promise.from(input1, input2, input3, input4, input5, input6, this);
//    }
//    
//    public default Func0<OUTPUT> applyTo(Supplier<INPUT1> input1, Supplier<INPUT2> input2, Supplier<INPUT3> input3, Supplier<INPUT4> input4, Supplier<INPUT5> input5, Supplier<INPUT6> input6) {
//        return () -> apply(input1.get(), input2.get(), input3.get(), input4.get(), input5.get(), input6.get());
//    }
//    
//    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6) {
//        try {
//            val output = applyUnsafe(input1, input2, input3, input4, input5, input6);
//            return Result.valueOf(output);
//        } catch (Exception exception) {
//            return Result.ofException(exception);
//        }
//    }
//    
//    /**
//     * Compose this function to the given function.
//     * NOTE: Too bad the name 'compose' is already been taken :-(
//     *
//     * @param  <TARGET>  the target result value.
//     * @param  after     the function to be run after this function.
//     * @return           the composed function.
//     */
//    public default <TARGET> Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, TARGET> then(Func1<? super OUTPUT, ? extends TARGET> after) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            OUTPUT output = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//            TARGET target = Func.applyUnsafe(after, output);
//            return target;
//        };
//    }
//    
//    public default <TARGET> Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, TARGET> map(Func1<? super OUTPUT, ? extends TARGET> after) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            OUTPUT output = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//            TARGET target = (output != null) ? Func.applyUnsafe(after, output) : null;
//            return target;
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> ifException(Consumer<Exception> exceptionHandler) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                return outputValue;
//            } catch (Exception e) {
//                exceptionHandler.accept(e);
//                return null;
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> ifExceptionThenPrint() {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                return outputValue;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                return outputValue;
//            } catch (Exception e) {
//                e.printStackTrace(printStream);
//                return null;
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                return outputValue;
//            } catch (Exception e) {
//                e.printStackTrace(printWriter);
//                return null;
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : defaultValue;
//                return returnValue;
//            } catch (Exception e) {
//                return defaultValue;
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
//                return returnValue;
//            } catch (Exception e) {
//                return defaultSupplier.get();
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
//                return returnValue;
//            } catch (Exception e) {
//                return exceptionMapper.apply(e);
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentApply(Func2<Tuple6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6>, Exception, OUTPUT> exceptionMapper) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6), null);
//                return returnValue;
//            } catch (Exception e) {
//                return exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6), e);
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : defaultValue;
//                return returnValue;
//            } catch (Exception e) {
//                exceptionHandler.accept(e);
//                return defaultValue;
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
//                return returnValue;
//            } catch (Exception e) {
//                exceptionHandler.accept(e);
//                return defaultSupplier.get();
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
//                return returnValue;
//            } catch (Exception e) {
//                exceptionHandler.accept(e);
//                return exceptionMapper.apply(e);
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func2<Tuple6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6>, Exception, OUTPUT> exceptionMapper) {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                val outputValue = this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6), null);
//                return returnValue;
//            } catch (Exception e) {
//                exceptionHandler.accept(e);
//                return exceptionMapper.apply(Tuple.of(input1, input2, input3, input4, input5, input6), e);
//            }
//        };
//    }
//    
//    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, OUTPUT defaultValue) {
//        return applySafely(input1, input2, input3, input4, input5, input6).orElse(defaultValue);
//    }
//    
//    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, INPUT5 input5, INPUT6 input6, Supplier<OUTPUT> defaultSupplier) {
//        return applySafely(input1, input2, input3, input4, input5, input6).orGet(defaultSupplier);
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Result<OUTPUT>> safely() {
//        return Func.of(this::applySafely);
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Optional<OUTPUT>> optionally() {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            try {
//                return Optional.ofNullable(this.applyUnsafe(input1, input2, input3, input4, input5, input6));
//            } catch (Exception e) {
//                return Optional.empty();
//            }
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Promise<OUTPUT>> async() {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            val supplier = (Func0<OUTPUT>) () -> {
//                return this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//            };
//            return DeferAction.from(supplier).start().getPromise();
//        };
//    }
//    
//    public default Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, DeferAction<OUTPUT>> defer() {
//        return (input1, input2, input3, input4, input5, input6) -> {
//            val supplier = (Func0<OUTPUT>) () -> {
//                return this.applyUnsafe(input1, input2, input3, input4, input5, input6);
//            };
//            return DeferAction.from(supplier);
//        };
//    }
//    
//    public default Func10<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, HasPromise<INPUT4>, HasPromise<INPUT5>, HasPromise<INPUT6>, Promise<OUTPUT>> forPromise() {
//        return (promise1, promise2, promise3, promise4, promise5, promise6) -> {
//            return Promise.from(promise1, promise2, promise3, promise4, promise5, promise6, this);
//        };
//    }
//    
//    public default Func1<Tuple6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6>, OUTPUT> wholly() {
//        return t -> this.applyUnsafe(t._1(), t._2(), t._3(), t._4(), t._5(), t._6());
//    }
//    
//    /**
//     * Flip the parameter order.
//     *
//     * @return  the Func6 with parameter in a flipped order.
//     */
//    public default Func10<INPUT6, INPUT5, INPUT4, INPUT3, INPUT2, INPUT1, OUTPUT> flip() {
//        return (i6, i5, i4, i3, i2, i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, Func1<INPUT1, OUTPUT>> elevate() {
//        return (i2, i3, i4, i5, i6) -> (i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
//        return (i1) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func1<INPUT1, Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT>> split() {
//        return split1();
//    }
//    
//    public default Func1<INPUT1, Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT>> split1() {
//        return (i1) -> (i2, i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func2<INPUT1, INPUT2, Func4<INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT>> split2() {
//        return (i1, i2) -> (i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func3<INPUT1, INPUT2, INPUT3, Func3<INPUT4, INPUT5, INPUT6, OUTPUT>> split3() {
//        return (i1, i2, i3) -> (i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Func2<INPUT5, INPUT6, OUTPUT>> split4() {
//        return (i1, i2, i3, i4) -> (i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, Func1<INPUT6, OUTPUT>> split5() {
//        return (i1, i2, i3, i4, i5) -> (i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    // == Partially apply functions ==
//    
//    public default Func0<OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4, INPUT5 i5, INPUT6 i6) {
//        return () -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> bind1(INPUT1 i1) {
//        return (i2, i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT1, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> bind2(INPUT2 i2) {
//        return (i1, i3, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT1, INPUT2, INPUT4, INPUT5, INPUT6, OUTPUT> bind3(INPUT3 i3) {
//        return (i1, i2, i4, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT1, INPUT2, INPUT3, INPUT5, INPUT6, OUTPUT> bind4(INPUT4 i4) {
//        return (i1, i2, i3, i5, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT6, OUTPUT> bind5(INPUT5 i5) {
//        return (i1, i2, i3, i4, i6) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    public default Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> bind6(INPUT6 i6) {
//        return (i1, i2, i3, i4, i5) -> this.applyUnsafe(i1, i2, i3, i4, i5, i6);
//    }
//    
//    // == Partially apply functions -- mix ==
//    
}
