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
    public default OUTPUT apply(Tuple2<INPUT1, INPUT2> input) {
        return apply(input._1(), input._2());
    }
    
    /**
     * Applies this function partially, taking the first input parameter and returning a function that takes the remaining parameters.
     * @param  input1  the first input parameter.
     * @return         a {@code Func2} function that takes the remaining nine parameters and produces an output.
     */
    public default Func1<INPUT2, OUTPUT> apply(INPUT1 input1) {
        return input2 -> apply(input1, input2);
    }
    
    /**
     * Applies this function to ten optional input parameters, returning an {@code Optional} of the output.
     * If any input is empty, the function short-circuits and returns {@code Optional.empty()}.
     * 
     * @param input1  optional first input parameter.
     * @param input2  optional second input parameter.
     * @param input3  optional third input parameter.
     * @return        an {@code Optional<OUTPUT>} containing the result, if all inputs are present; otherwise, {@code Optional.empty()}.
     */
    public default Optional<OUTPUT> apply(
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
     * @param input3  nullable third input parameter.
     * @return        a {@code Nullable<OUTPUT>} containing the result, if all inputs are non-null; otherwise, {@code Nullable.empty()}.
     */
    public default Nullable<OUTPUT> apply(
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
     * @param input3  the third promise.
     * @return        a {@code Result<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Result<OUTPUT> apply(
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
     * @param input3  the third promise.
     * @return        a {@code Promise<OUTPUT>} that will be fulfilled with the result of applying this function to the results of the promises.
     */
    public default Promise<OUTPUT> apply(
                                    HasPromise<INPUT1> input1,
                                    HasPromise<INPUT2> input2) {
        return Promise.from(input1, input2, this);
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
    public default Func0<OUTPUT> apply(
                                    Func0<INPUT1> input1,
                                    Func0<INPUT2> input2) {
        return () -> {
            val value1  = input1.get();
            val value2  = input2.get();
            val output  = apply(value1, value2);
            return output;
        };
    }
    
    public default Task<OUTPUT> apply(Task<INPUT1> input1, Task<INPUT2> input2) {
        return Task.from(input1, input2, this);
    }
    
    public default StreamPlus<OUTPUT> apply(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    
    public default StreamPlus<OUTPUT> apply(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2, ZipWithOption option) {
        return input1.zipWith(input2, option, this);
    }
    
    public default FuncList<OUTPUT> apply(FuncList<INPUT1> input1, FuncList<INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    
    public default FuncList<OUTPUT> apply(FuncList<INPUT1> input1, FuncList<INPUT2> input2, ZipWithOption option) {
        return input1.zipWith(input2, option, this);
    }
    
    public default <KEY> FuncMap<KEY, OUTPUT> apply(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    
    public default <KEY> FuncMap<KEY, OUTPUT> apply(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2, ZipWithOption option) {
        return input1.zipWith(input2, option, this);
    }
    
    public default Func0<OUTPUT> apply(Supplier<INPUT1> input1, Supplier<INPUT2> input2) {
        return () -> apply(input1.get(), input2.get());
    }
    
    public default <SOURCE> Func1<SOURCE, OUTPUT> apply(Func1<SOURCE, INPUT1> input1, Func1<SOURCE, INPUT2> input2) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            return apply(i1, i2);
        };
    }
    
    public default Result<OUTPUT> applySafely(INPUT1 input1, INPUT2 input2) {
        try {
            val output = applyUnsafe(input1, input2);
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
    public default <TARGET> Func2<INPUT1, INPUT2, TARGET> then(Function<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2) -> {
            OUTPUT output = this.applyUnsafe(input1, input2);
            TARGET target = Func.applyUnsafe(after, output);
            return target;
        };
    }
    
    public default <TARGET> Func2<INPUT1, INPUT2, TARGET> map(Function<? super OUTPUT, ? extends TARGET> after) {
        return (input1, input2) -> {
            OUTPUT output = this.applyUnsafe(input1, input2);
            TARGET target = (output != null) ? Func.applyUnsafe(after, output) : null;
            return target;
        };
    }
    
    public default Func2<INPUT1, INPUT2, OUTPUT> ifException(Consumer<Exception> exceptionHandler) {
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
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
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
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(e);
            }
        };
    }
    
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
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
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
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
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
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(e);
            }
        };
    }
    
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
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func2<Tuple2<INPUT1, INPUT2>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2) -> {
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply(Tuple.of(input1, input2), null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(Tuple.of(input1, input2), e);
            }
        };
    }
    
    public default OUTPUT orElse(INPUT1 input1, INPUT2 input2, OUTPUT defaultValue) {
        return applySafely(input1, input2).orElse(defaultValue);
    }
    
    public default OUTPUT orGet(INPUT1 input1, INPUT2 input2, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input1, input2).orGet(defaultSupplier);
    }
    
    public default Func2<INPUT1, INPUT2, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default Func2<INPUT1, INPUT2, Optional<OUTPUT>> optionally() {
        return (input1, input2) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input1, input2));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    public default Func2<INPUT1, INPUT2, Promise<OUTPUT>> async() {
        return (input1, input2) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2);
            };
            return DeferAction.from(supplier).start().getPromise();
        };
    }
    
    public default Func2<INPUT1, INPUT2, DeferAction<OUTPUT>> defer() {
        return (input1, input2) -> {
            val supplier = (Func0<OUTPUT>) () -> {
                return this.applyUnsafe(input1, input2);
            };
            return DeferAction.from(supplier);
        };
    }
    
    public default Func2<HasPromise<INPUT1>, HasPromise<INPUT2>, Promise<OUTPUT>> forPromise() {
        return (promise1, promise2) -> {
            return Promise.from(promise1, promise2, this);
        };
    }
    
    public default FuncUnit2<INPUT1, INPUT2> ignoreResult() {
        return FuncUnit2.of((input1, input2) -> applyUnsafe(input1, input2));
    }
    
    public default Func1<Tuple2<INPUT1, INPUT2>, OUTPUT> wholly() {
        return t -> this.applyUnsafe(t._1(), t._2());
    }
    
    public default BiPredicate<INPUT1, INPUT2> toPredicate() {
        return (i1, i2) -> Boolean.TRUE.equals(apply(i1, i2));
    }
    
    public default BiPredicate<INPUT1, INPUT2> toPredicate(Func1<OUTPUT, Boolean> toPredicate) {
        return (i1, i2) -> toPredicate.apply((apply(i1, i2)));
    }
    
    /**
     * Flip the parameter order.
     *
     * @return  the Func2 with parameter in a flipped order.
     */
    public default Func2<INPUT2, INPUT1, OUTPUT> flip() {
        return (i2, i1) -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT2, Func1<INPUT1, OUTPUT>> elevate() {
        return (i2) -> (i1) -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT1, OUTPUT> elevateWith(INPUT2 i2) {
        return (i1) -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<Result<INPUT1>, Result<OUTPUT>> elevateWith(Result<INPUT2> i2) {
        return (i1) -> this.apply(i1, i2);
    }
    
    public default Func1<HasPromise<INPUT1>, Promise<OUTPUT>> elevateWith(HasPromise<INPUT2> i2) {
        return (i1) -> this.apply(i1, i2);
    }
    
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> split() {
        return split1();
    }
    
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> split1() {
        return (i1) -> (i2) -> this.applyUnsafe(i1, i2);
    }
    
    // == Partially apply functions ==
    
    public default Func0<OUTPUT> bind(INPUT1 i1, INPUT2 i2) {
        return () -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT2, OUTPUT> bind1(INPUT1 i1) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT1, OUTPUT> bind2(INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT1, OUTPUT> bind(Absent a1, INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    
    public default Func1<INPUT2, OUTPUT> bind(INPUT1 i1, Absent a2) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
}
