// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.ref.ComputeBody;
import functionalj.result.Result;


/**
 * Function of zeroth parameter - a supplier.
 * 
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func0<OUTPUT> extends Supplier<OUTPUT>, ComputeBody<OUTPUT, RuntimeException> {
    
    public static <T> Func0<T> of(Func0<T> func0) {
        return func0;
    }
    
    public static <T> Func0<T> from(Supplier<T> supplier) {
        if (supplier instanceof Func0)
            return (Func0<T>)supplier;
        
        return supplier::get;
    }
    public static <T> Func0<T> from(IntFunction<T> generatorFunction) {
        return Func0.from(0, generatorFunction);
    }
    public static <T> Func0<T> from(int start, IntFunction<T> generatorFunction) {
        var counter = new AtomicInteger(start);
        return ()-> generatorFunction.apply(counter.getAndIncrement());
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
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
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
    
    public default Func0<OUTPUT> memoize() {
        return Func0.from(Func.lazy(this));
    }
    
    public default <TARGET> Func0<TARGET> then(Func1<OUTPUT, TARGET> mapper) {
        return ()->{
            var output = this.applyUnsafe();
            var target = Func.applyUnsafe(mapper, output);
            return target;
        };
    }
    public default <TARGET> Func0<TARGET> map(Func1<OUTPUT, TARGET> mapper) {
        return ()->{
            var output = this.applyUnsafe();
            var target = (output != null) 
                       ? Func.applyUnsafe(mapper, output)
                       : null;
            return target;
        };
    }
    
    public default Func0<OUTPUT> ifException(Consumer<Exception> exceptionHandler) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return null;
            }
        };
    }
    public default Func0<OUTPUT> ifExceptionThenPrint() {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }
    public default Func0<OUTPUT> ifExceptionThenPrint(PrintStream printStream) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printStream);
                return null;
            }
        };
    }
    public default Func0<OUTPUT> ifExceptionThenPrint(PrintWriter printWriter) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                return outputValue;
            } catch (Exception e) {
                e.printStackTrace(printWriter);
                return null;
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                var returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : defaultValue;
                return returnValue;
            } catch (Exception e) {
                return defaultValue;
            }
        };
    }
    public default Func0<OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                var returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : defaultSupplier.get();
                return returnValue;
            } catch (Exception e) {
                return defaultSupplier.get();
            }
        };
    }
    public default Func0<OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                var returnValue 
                        = (outputValue != null)
                        ? outputValue
                        : exceptionMapper.apply(null);
                return returnValue;
            } catch (Exception e) {
                return exceptionMapper.apply(e);
            }
        };
    }
    
    public default Func0<OUTPUT> whenAbsentUse(Consumer<Exception> exceptionHandler, OUTPUT defaultValue) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                var returnValue 
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
    public default Func0<OUTPUT> whenAbsentGet(Consumer<Exception> exceptionHandler, Supplier<OUTPUT> defaultSupplier) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                var returnValue 
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
    public default Func0<OUTPUT> whenAbsentApply(Consumer<Exception> exceptionHandler, Func1<Exception, OUTPUT> exceptionMapper) {
        return ()->{
            try {
                var outputValue = this.applyUnsafe();
                var returnValue 
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
    
    public default FuncUnit0 ignoreResult() {
        return FuncUnit0.of(()->applyUnsafe());
    }
    
}
