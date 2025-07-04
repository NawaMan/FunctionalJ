// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import functionalj.exception.Throwables;
import functionalj.promise.DeferAction;
import functionalj.promise.Promise;
import functionalj.ref.ComputeBody;
import functionalj.result.AsResult;
import functionalj.result.Result;
import lombok.val;

/**
 * Function of zeroth parameter - a supplier.
 *
 * @param <OUTPUT>  the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func0<OUTPUT> extends Supplier<OUTPUT>, ComputeBody<OUTPUT, RuntimeException>, AsResult<OUTPUT> {
    
    public static <T> Func0<T> supplyNull() {
        return () -> null;
    }
    
    public static <T> Func0<T> of(Func0<T> func0) {
        return func0;
    }
    
    public static <T> Func0<T> from(Supplier<T> supplier) {
        if (supplier instanceof Func0)
            return (Func0<T>) supplier;
        return supplier::get;
    }
    
    public static <T> Func0<T> from(IntFunction<T> generatorFunction) {
        return Func0.from(0, generatorFunction);
    }
    
    public static <T> Func0<T> from(int start, IntFunction<T> generatorFunction) {
        val counter = new AtomicInteger(start);
        return () -> generatorFunction.apply(counter.getAndIncrement());
    }
    
    public OUTPUT applyUnsafe() throws Exception;
    
    public default OUTPUT getUnsafe() throws Exception {
        return applyUnsafe();
    }
    
    public default OUTPUT get() {
        try {
            return applyUnsafe();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw Throwables.exceptionTransformer.value().apply(e);
        }
    }
    
    public default OUTPUT compute() throws RuntimeException {
        return apply();
    }
    
    public default OUTPUT apply() {
        return get();
    }
    
    public default Result<OUTPUT> applySafely() {
        return Result.of(this);
    }
    
    public default Result<OUTPUT> getSafely() {
        return Result.of(this);
    }
    
    public default Result<OUTPUT> asResult() {
        return Result.of(this);
    }
    
    public default Promise<OUTPUT> getPromise() {
        return getResult().getPromise();
    }
    
    public default Func0<OUTPUT> memoize() {
        return Func0.from(Func.lazy(this));
    }
    
    public default <TARGET> Func0<TARGET> then(Func1<OUTPUT, TARGET> mapper) {
        return () -> {
            val output = this.applyUnsafe();
            val target = Func.applyUnsafe(mapper, output);
            return target;
        };
    }
    
    public default <TARGET> Func0<TARGET> map(Func1<OUTPUT, TARGET> mapper) {
        return () -> {
            val output = this.applyUnsafe();
            val target = (output != null) ? Func.applyUnsafe(mapper, output) : null;
            return target;
        };
    }
    
    public default Func0<OUTPUT> ifException(Consumer<? super Exception> exceptionHandler) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return null;
            }
        };
    }
    
    public default Func0<OUTPUT> ifExceptionThenPrint() {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }
    
    public default Func0<OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printStream);
                return null;
            }
        };
    }
    
    public default Func0<OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printWriter);
                return null;
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                val returnValue = (outputValue != null) ? outputValue : defaultValue;
                return returnValue;
            } catch (Exception e) {
                return defaultValue;
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentGet(Supplier<? extends OUTPUT> defaultSupplier) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                return defaultSupplier.get();
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply((Exception)null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(e);
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                val returnValue = (outputValue != null) ? outputValue : defaultValue;
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return defaultValue;
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                val returnValue = (outputValue != null) ? outputValue : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return defaultSupplier.get();
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return () -> {
            try {
                val outputValue = this.applyUnsafe();
                val returnValue = (outputValue != null) ? outputValue : exceptionMapper.apply((Exception)null);
                return returnValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return exceptionMapper.apply(e);
            }
        };
    }
    
    public default OUTPUT orElse(OUTPUT defaultValue) {
        return getSafely().orElse(defaultValue);
    }
    
    public default OUTPUT orGet(Supplier<OUTPUT> defaultSupplier) {
        return getSafely().orGet(defaultSupplier);
    }
    
    public default Func0<Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default Func0<Optional<OUTPUT>> optionally() {
        return () -> {
            try {
                return Optional.ofNullable(this.applyUnsafe());
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
    
    public default DeferAction<OUTPUT> async() {
        return defer();
    }
    
    public default DeferAction<OUTPUT> defer() {
        return DeferAction.from(this);
    }
    
    public default Func0<Promise<OUTPUT>> forPromise() {
        return () -> defer().start().getPromise();
    }
    
    public default FuncUnit0 ignoreResult() {
        return FuncUnit0.of(() -> applyUnsafe());
    }
}
