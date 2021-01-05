// ============================================================================
// Copyright(c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.task.Task;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * Function of one parameter.
 *
 * @param <INPUT>   the input data type.
 * @param <OUTPUT>  the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func1<INPUT, OUTPUT> extends Function<INPUT, OUTPUT> {

    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, OUTPUT>
            Func1<INPUT, OUTPUT> of(Func1<INPUT, OUTPUT> function) {
        return function;
    }
    public static <INPUT, OUTPUT>
            Func1<INPUT, OUTPUT> func1(Func1<INPUT, OUTPUT> function) {
        return function;
    }

    public static <I1, O> Func1<I1, O> from(Function<I1, O> func) {
        return func::apply;
    }
    public static <O, I, I1, I2> Func1<I, O> from(
            Func2<I1, I2, O> func,
            Func1<I, I1> input1,
            Func1<I, I2> input2) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            return func.apply(i1, i2);
        };
    }
    public static <O, I, I1, I2, I3> Func1<I, O> from(
            Func3<I1, I2, I3, O> func,
            Func1<I, I1> input1,
            Func1<I, I2> input2,
            Func1<I, I3> input3) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            val i3 = input3.apply(source);
            return func.apply(i1, i2, i3);
        };
    }
    public static <O, I, I1, I2, I3, I4> Func1<I, O> from(
            Func4<I1, I2, I3, I4, O> func,
            Func1<I, I1> input1,
            Func1<I, I2> input2,
            Func1<I, I3> input3,
            Func1<I, I4> input4) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            val i3 = input3.apply(source);
            val i4 = input4.apply(source);
            return func.apply(i1, i2, i3, i4);
        };
    }
    public static <O, I, I1, I2, I3, I4, I5> Func1<I, O> from(
            Func5<I1, I2, I3, I4, I5, O> func,
            Func1<I, I1> input1,
            Func1<I, I2> input2,
            Func1<I, I3> input3,
            Func1<I, I4> input4,
            Func1<I, I5> input5) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            val i3 = input3.apply(source);
            val i4 = input4.apply(source);
            val i5 = input5.apply(source);
            return func.apply(i1, i2, i3, i4, i5);
        };
    }
    public static <O, I, I1, I2, I3, I4, I5, I6> Func1<I, O> from(
            Func6<I1, I2, I3, I4, I5, I6, O> func,
            Func1<I, I1> input1,
            Func1<I, I2> input2,
            Func1<I, I3> input3,
            Func1<I, I4> input4,
            Func1<I, I5> input5,
            Func1<I, I6> input6) {
        return source -> {
            val i1 = input1.apply(source);
            val i2 = input2.apply(source);
            val i3 = input3.apply(source);
            val i4 = input4.apply(source);
            val i5 = input5.apply(source);
            val i6 = input6.apply(source);
            return func.apply(i1, i2, i3, i4, i5, i6);
        };
    }

    public OUTPUT applyUnsafe(INPUT input) throws Exception;


    /**
     * Applies this function to the given input value.
     *
     * @param input  the input function.
     * @return the function result.
     */
    public default OUTPUT apply(INPUT input) {
        try {
            return applyUnsafe(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }

    public default OUTPUT applyToNull() {
        return apply((INPUT)null);
    }
    public default Result<OUTPUT> applyTo(Result<INPUT> input) {
        return input.map(this);
    }
    public default Optional<OUTPUT> applyTo(Optional<INPUT> input) {
        return input.map(this);
    }
    public default Nullable<OUTPUT> applyTo(Nullable<INPUT> input) {
        return input.map(this);
    }
    public default Promise<OUTPUT> applyTo(HasPromise<INPUT> input) {
        return input.getPromise().map(this);
    }
    public default Task<OUTPUT> applyTo(Task<INPUT> input) {
        return input.map(this);
    }
    public default StreamPlus<OUTPUT> applyTo(Stream<INPUT> input) {
        return StreamPlus.from(input).map(this);
    }
    public default FuncList<OUTPUT> applyTo(List<INPUT> input) {
        return FuncList.from(input).map(this);
    }
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(Map<KEY, INPUT> input) {
        return FuncMap.from(input).map(this);
    }
    public default FuncList<OUTPUT> applyTo(FuncList<INPUT> input) {
        return FuncList.from(input).map(this);
    }
    public default <KEY> FuncMap<KEY, OUTPUT> applyTo(FuncMap<KEY, INPUT> input) {
        return FuncMap.from(input).map(this);
    }
    public default Func0<OUTPUT> applyTo(Supplier<INPUT> input) {
        return ()->apply(input.get());
    }
    public default <T> Func1<T, OUTPUT> applyTo(Function<T, INPUT> input) {
        return t -> apply(input.apply(t));
    }

    public default Result<OUTPUT> applySafely(INPUT input) {
        try {
            val output = applyUnsafe(input);
            return Result.valueOf(output);
        } catch (Exception exception) {
            return Result.ofException(exception);
        }
    }

    public default Func1<INPUT, OUTPUT> memoize() {
        return Func.cacheFor(this);
    }

    /**
     * Compose this function to the given function.
     * NOTE: Too bad the name 'compose' is already been taken :-(
     *
     * @param  <TARGET>  the target result value.
     * @param  after     the function to be run after this function.
     * @return           the composed function.
     */
    public default <TARGET> Func1<INPUT, TARGET> then(Function<? super OUTPUT, ? extends TARGET> after) {
        return input -> {
            OUTPUT output = this.applyUnsafe(input);
            TARGET target = Func.applyUnsafe(after, output);
            return target;
        };
    }
    public default <TARGET> Func1<INPUT, TARGET> map(Function<? super OUTPUT, ? extends TARGET> after) {
        return input -> {
            OUTPUT output = this.applyUnsafe(input);
            TARGET target = (output != null)
                          ? Func.applyUnsafe(after, output)
                          : null;
            return target;
        };
    }

    public default Func1<INPUT, OUTPUT> ifException(Consumer<Exception> exceptionHandler) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
                return outputValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return null;
            }
        };
    }
    public default Func1<INPUT, OUTPUT> ifExceptionThenPrint() {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }
    public default Func1<INPUT, OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printStream);
                return null;
            }
        };
    }
    public default Func1<INPUT, OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printWriter);
                return null;
            }
        };
    }

    public default Func1<INPUT, OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
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
    public default Func1<INPUT, OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
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
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
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
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Func2<INPUT, Exception, OUTPUT> exceptionMapper) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(input, null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(input, e);
            }
        };
    }

    public default Func1<INPUT, OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
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
    public default Func1<INPUT, OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
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
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
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
    public default Func1<INPUT, OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func2<INPUT, Exception, OUTPUT> exceptionMapper) {
        return (input)->{
            try {
                val outputValue = this.applyUnsafe(input);
                val returnValue
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(input, null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(input, e);
            }
        };
    }

    public default OUTPUT orElse(INPUT input, OUTPUT defaultValue) {
        return applySafely(input).orElse(defaultValue);
    }

    public default OUTPUT orGet(INPUT input, Supplier<OUTPUT> defaultSupplier) {
        return applySafely(input).orGet(defaultSupplier);
    }

    public default Func1<INPUT, Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }

    public default Func1<INPUT, Optional<OUTPUT>> optionally() {
        return (input) -> {
            try {
                return Optional.ofNullable(this.applyUnsafe(input));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }

    public default Func1<INPUT, Promise<OUTPUT>> async() {
        return input -> {
            val supplier = (Func0<OUTPUT>)()->{
                return this.applyUnsafe(input);
            };
            return DeferAction.from(supplier)
                    .start().getPromise();
        };
    }
    public default Func1<HasPromise<INPUT>, Promise<OUTPUT>> defer() {
        return input -> input.getPromise().map(this);
    }

    public default FuncUnit1<INPUT> ignoreResult() {
        return FuncUnit1.of((input1)->applyUnsafe(input1));
    }

    public default Predicate<INPUT> toPredicate() {
        return toPredicate(Boolean.TRUE::equals);
    }
    public default Predicate<INPUT> toPredicate(Func1<OUTPUT, Boolean> converter) {
        val converted = this.then(converter);
        return Func.toPredicate(converted);
    }

    /**
     * Create a bind function (a supplier) of the this function.
     *
     * @param   input  the input value.
     * @return         the Supplier.
     */
    public default Func0<OUTPUT> bind(INPUT input) {
        return () -> {
            return this.applyUnsafe(input);
        };
    }

}
