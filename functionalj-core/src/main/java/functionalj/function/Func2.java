// ============================================================================
// Copyright(c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
    
    public static <I1, I2, O> Func2<I1, I2, O> of(Func2<I1, I2, O> func) {
        return func;
    }
    public static <I1, I2, O> Func2<I1, I2, O> func2(Func2<I1, I2, O> func) {
        return func;
    }
    
    public static <I1, I2, O> Func2<I1, I2, O> from(BiFunction<I1, I2, O> func) {
        return func::apply;
    }
    
    public OUTPUT applyUnsafe(INPUT1 input1, INPUT2 input2) throws Exception;
    
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
        return apply(input._1(), input._2());
    }
    public default Func1<INPUT2, OUTPUT> applyTo(INPUT1 input1) {
        return input2 -> apply(input1, input2);
    }
    public default Result<OUTPUT> applyTo(Result<INPUT1> input1, Result<INPUT2> input2) {
        return Result.ofResults(input1, input2, this);
    }
    public default Optional<OUTPUT> applyTo(Optional<INPUT1> input1, Optional<INPUT2> input2) {
        return input1.flatMap(i1 -> {
            return input2.map(i2 -> {
                return Func2.this.apply(i1, i2);
            });
        });
    }
    public default Nullable<OUTPUT> applyTo(Nullable<INPUT1> input1, Nullable<INPUT2> input2) {
        return input1.flatMap(i1 -> {
            return input2.map(i2 -> {
                return Func2.this.apply(i1, i2);
            });
        });
    }
    public default Promise<OUTPUT> applyTo(HasPromise<INPUT1> input1, HasPromise<INPUT2> input2) {
        return Promise.from(input1, input2, this);
    }
    public default Task<OUTPUT> applyTo(Task<INPUT1> input1, Task<INPUT2> input2) {
        return Task.from(input1, input2, this);
    }
    public default StreamPlus<OUTPUT> applyTo(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2) {
        return input1.combineWith(input2, this);
    }
    public default StreamPlus<OUTPUT> applyTo(StreamPlus<INPUT1> input1, StreamPlus<INPUT2> input2, ZipWithOption option) {
        return input1.combineWith(input2, option, this);
    }
    public default FuncList<OUTPUT> applyTo(FuncList<INPUT1> input1, FuncList<INPUT2> input2) {
        return input1.combineWith(input2.stream(), this);
    }
    public default FuncList<OUTPUT> applyTo(FuncList<INPUT1> input1, FuncList<INPUT2> input2, ZipWithOption option) {
        return input1.combineWith(input2.stream(), option, this);
    }
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2) {
        return input1.zipWith(input2, this);
    }
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(FuncMap<KEY, INPUT1> input1, FuncMap<KEY, INPUT2> input2, ZipWithOption option) {
        return input1.zipWith(input2, option, this);
    }
    public default Func0<OUTPUT> applyTo(Supplier<INPUT1> input1, Supplier<INPUT2> input2) {
        return ()->apply(input1.get(), input2.get());
    }
    public default <SOURCE> Func1<SOURCE, OUTPUT> applyTo(Func1<SOURCE, INPUT1> input1, Func1<SOURCE, INPUT2> input2) {
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
            TARGET target = (output != null)
                          ? Func.applyUnsafe(after, output)
                          : null;
            return target;
        };
    }
    
    public default Func2<INPUT1, INPUT2, OUTPUT> ifException(Consumer<Exception> exceptionHandler) {
        return (input1, input2)->{
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
        return (input1, input2)->{
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
        return (input1, input2)->{
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
        return (input1, input2)->{
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
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : defaultValue;
                return returnValue;
            } catch (Exception e) {
                return defaultValue;
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                return defaultSupplier.get();
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(e);
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func3<INPUT1, INPUT2, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(input1, input2, null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(input1, input2, e);
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Func2<Tuple2<INPUT1, INPUT2>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(Tuple.of(input1, input2), null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(Tuple.of(input1, input2), e);
            }
        };
    }
    
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : defaultValue;
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return defaultValue;
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return defaultSupplier.get();
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(e);
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func3<INPUT1, INPUT2, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(input1, input2, null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(input1, input2, e);
            }
        };
    }
    public default Func2<INPUT1, INPUT2, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func2<Tuple2<INPUT1, INPUT2>, Exception, OUTPUT> exceptionMapper) {
        return (input1, input2)->{
            try {
                val outputValue = this.applyUnsafe(input1, input2);
                val returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(Tuple.of(input1, input2), null);
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
            val supplier = (Func0<OUTPUT>)()->{
                return this.applyUnsafe(input1, input2);
            };
            return DeferAction.from(supplier)
                    .start().getPromise();
        };
    }
    
    public default Func2<HasPromise<INPUT1>, HasPromise<INPUT2>, Promise<OUTPUT>> defer() {
        return (promise1, promise2) -> {
            return Promise.from(promise1, promise2, this);
        };
    }
    
    public default FuncUnit2<INPUT1, INPUT2> ignoreResult() {
        return FuncUnit2.of((input1, input2)->applyUnsafe(input1, input2));
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
        return (i1) -> this.applyTo(i1, i2);
    }
    public default Func1<HasPromise<INPUT1>, Promise<OUTPUT>> elevateWith(HasPromise<INPUT2> i2) {
        return (i1) -> this.applyTo(i1, i2);
    }
    
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> split() {
        return split1();
    }
    public default Func1<INPUT1, Func1<INPUT2, OUTPUT>> split1() {
        return (i1) -> (i2) -> this.applyUnsafe(i1, i2);
    }
    
    
    //== Partially apply functions ==
    
    @SuppressWarnings("javadoc")
    public default Func0<OUTPUT> bind(INPUT1 i1, INPUT2 i2) {
        return () -> this.applyUnsafe(i1, i2);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> bind1(INPUT1 i1) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> bind2(INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    
    @SuppressWarnings("javadoc")
    public default Func1<INPUT1, OUTPUT> bind(Absent a1, INPUT2 i2) {
        return i1 -> this.applyUnsafe(i1, i2);
    }
    @SuppressWarnings("javadoc")
    public default Func1<INPUT2, OUTPUT> bind(INPUT1 i1, Absent a2) {
        return i2 -> this.applyUnsafe(i1, i2);
    }
    
}