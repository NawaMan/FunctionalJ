// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import functionalj.functions.ThrowFuncs;
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
    
    public static <I1, I2, I3, I4, O> Func4<I1, I2, I3, I4, O> of(Func4<I1, I2, I3, I4, O> func) {
        return func;
    }
    
    public static <I1, I2, I3, I4, O> Func4<I1, I2, I3, I4, O> func4(Func4<I1, I2, I3, I4, O> func) {
        return func;
    }
    
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) throws Exception;
    
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
            return applyUnsafe(input1, input2, input3, input4);
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
    public default OUTPUT applyTo(Tuple4<INPUT1, INPUT2, INPUT3, INPUT4> input) {
        return apply(input._1(), input._2(), input._3(), input._4());
    }
    
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> applyTo(INPUT1 input1) {
        return (input2, input3, input4) -> apply(input1, input2, input3, input4);
    }
    
    public default Result<OUTPUT> applyTo(Result<INPUT1> input1, Result<INPUT2> input2, Result<INPUT3> input3, Result<INPUT4> input4) {
        return Result.ofResults(input1, input2, input3, input4, this);
    }
    
    public default Optional<OUTPUT> applyTo(Optional<INPUT1> input1, Optional<INPUT2> input2, Optional<INPUT3> input3, Optional<INPUT4> input4) {
        return input1.flatMap(i1 -> {
            return input2.flatMap(i2 -> {
                return input3.flatMap(i3 -> {
                    return input4.map(i4 -> {
                        return Func4.this.apply(i1, i2, i3, i4);
                    });
                });
            });
        });
    }
    
    public default Nullable<OUTPUT> applyTo(Nullable<INPUT1> input1, Nullable<INPUT2> input2, Nullable<INPUT3> input3, Nullable<INPUT4> input4) {
        return input1.flatMap(i1 -> {
            return input2.flatMap(i2 -> {
                return input3.flatMap(i3 -> {
                    return input4.map(i4 -> {
                        return Func4.this.apply(i1, i2, i3, i4);
                    });
                });
            });
        });
    }
    
    public default Promise<OUTPUT> applyTo(HasPromise<INPUT1> input1, HasPromise<INPUT2> input2, HasPromise<INPUT3> input3, HasPromise<INPUT4> input4) {
        return Promise.from(input1, input2, input3, input4, this);
    }
    
    public default Task<OUTPUT> applyTo(Task<INPUT1> input1, Task<INPUT2> input2, Task<INPUT3> input3, Task<INPUT4> input4) {
        return Task.from(input1, input2, input3, input4, this);
    }
    
    public default Func0<OUTPUT> applyTo(Supplier<INPUT1> input1, Supplier<INPUT2> input2, Supplier<INPUT3> input3, Supplier<INPUT4> input4) {
        return () -> apply(input1.get(), input2.get(), input3.get(), input4.get());
    }
    
    public default <SOURCE> Func1<SOURCE, OUTPUT> applyTo(Func1<SOURCE, INPUT1> input1, Func1<SOURCE, INPUT2> input2, Func1<SOURCE, INPUT3> input3, Func1<SOURCE, INPUT4> input4) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            val i3 = input3.apply(source);
            val i4 = input4.apply(source);
            return apply(i1, i2, i3, i4);
        };
    }
    
    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4) {
        try {
            val output = applyUnsafe(input1, input2, input3, input4);
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
    public default <TARGET> Func4<INPUT1, INPUT2, INPUT3, INPUT4, TARGET> then(Function<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2, input3, input4) -> {
            OUTPUT output = this.applyUnsafe(input1, input2, input3, input4);
            TARGET result = Func.applyUnsafe(after, output);
            return result;
        };
    }
    
    public default <TARGET> Func4<INPUT1, INPUT2, INPUT3, INPUT4, TARGET> map(Function<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2, input3, input4) -> {
            OUTPUT output = this.applyUnsafe(input1, input2, input3, input4);
            TARGET result = (output != null) ? Func.applyUnsafe(after, output) : null;
            return result;
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifException(Consumer<Exception> exceptionHandler) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                return outputValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return null;
            }
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifExceptionThenPrint() {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printStream);
                return null;
            }
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printWriter);
                return null;
            }
        };
    }
    
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
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
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
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(e);
            }
        };
    }
    
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
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
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
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
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
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(e);
            }
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func5<INPUT1, INPUT2, INPUT3, INPUT4, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2, input3, input4) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2, input3, input4);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(input1, input2, input3, input4, null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(input1, input2, input3, input4, e);
            }
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func2<Tuple4<INPUT1, INPUT2, INPUT3, INPUT4>, Exception, OUTPUT> exceptionMapper) {
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
    
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, OUTPUT defaultValue) {
        return applySafely(input1, input2, input3, input4).orElse(defaultValue);
    }
    
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, INPUT3 input3, INPUT4 input4, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input1, input2, input3, input4).orGet(defaultSupplier);
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, Optional<OUTPUT>> optionally() {
        return (input1, input2, input3, input4) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input1, input2, input3, input4));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, HasPromise<OUTPUT>> async() {
        return (input1, input2, input3, input4) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2, input3, input4);
            };
            return DeferAction.from(supplier).start().getPromise();
        };
    }
    
    public default Func4<INPUT1, INPUT2, INPUT3, INPUT4, DeferAction<OUTPUT>> defer() {
        return (input1, input2, input3, input4) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2, input3, input4);
            };
            return DeferAction.from(supplier);
        };
    }
    
    public default Func4<HasPromise<INPUT1>, HasPromise<INPUT2>, HasPromise<INPUT3>, HasPromise<INPUT4>, Promise<OUTPUT>> forPromise() {
        return (promise1, promise2, promise3, promise4) -> {
            return Promise.from(promise1, promise2, promise3, promise4, this);
        };
    }
    
    public default Func1<Tuple4<INPUT1, INPUT2, INPUT3, INPUT4>, OUTPUT> wholly() {
        return t -> this.applyUnsafe(t._1(), t._2(), t._3(), t._4());
    }
    
    /**
     * Flip the parameter order.
     *
     * @return  the Func4 with parameter in a flipped order.
     */
    public default Func4<INPUT4, INPUT3, INPUT2, INPUT1, OUTPUT> flip() {
        return (i4, i3, i2, i1) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT2, INPUT3, INPUT4, Func1<INPUT1, OUTPUT>> elevate() {
        return (i2, i3, i4) -> (i1) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return (i1) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func1<INPUT1, Func3<INPUT2, INPUT3, INPUT4, OUTPUT>> split() {
        return split1();
    }
    
    public default Func1<INPUT1, Func3<INPUT2, INPUT3, INPUT4, OUTPUT>> split1() {
        return (i1) -> (i2, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func2<INPUT1, INPUT2, Func2<INPUT3, INPUT4, OUTPUT>> split2() {
        return (i1, i2) -> (i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT1, INPUT2, INPUT3, Func1<INPUT4, OUTPUT>> split3() {
        return (i1, i2, i3) -> (i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    // == Partially apply functions ==
    public default Func0<OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return () -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> bind1(INPUT1 i1) {
        return (i2, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> bind2(INPUT2 i2) {
        return (i1, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> bind3(INPUT3 i3) {
        return (i1, i2, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> bind4(INPUT4 i4) {
        return (i1, i2, i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func1<INPUT1, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, INPUT4 i4) {
        return i1 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func1<INPUT2, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return i2 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func1<INPUT3, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return i3 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func1<INPUT4, OUTPUT> bind(INPUT1 i1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return i4 -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func2<INPUT1, INPUT2, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, INPUT4 i4) {
        return (i1, i2) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func2<INPUT1, INPUT3, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, INPUT4 i4) {
        return (i1, i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func2<INPUT1, INPUT4, OUTPUT> bind(Absent a1, INPUT2 i2, INPUT3 i3, Absent a4) {
        return (i1, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func2<INPUT2, INPUT3, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, INPUT4 i4) {
        return (i2, i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func2<INPUT2, INPUT4, OUTPUT> bind(INPUT1 i1, Absent a2, INPUT3 i3, Absent a4) {
        return (i2, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func2<INPUT3, INPUT4, OUTPUT> bind(INPUT1 i1, INPUT2 i2, Absent a3, Absent a4) {
        return (i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT1, INPUT2, INPUT3, OUTPUT> bind(Absent a1, Absent a2, Absent a3, INPUT4 i4) {
        return (i1, i2, i3) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT1, INPUT2, INPUT4, OUTPUT> bind(Absent a1, Absent a2, INPUT3 i3, Absent a4) {
        return (i1, i2, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT1, INPUT3, INPUT4, OUTPUT> bind(Absent a1, INPUT2 i2, Absent a3, Absent a4) {
        return (i1, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
    
    public default Func3<INPUT2, INPUT3, INPUT4, OUTPUT> bind(INPUT1 i1, Absent a2, Absent a3, Absent a4) {
        return (i2, i3, i4) -> this.applyUnsafe(i1, i2, i3, i4);
    }
}
