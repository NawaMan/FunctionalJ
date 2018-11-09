package functionalj.function;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import functionalj.functions.ThrowFuncs;
import functionalj.promise.DeferAction;
import functionalj.promise.Promise;
import functionalj.ref.ComputeBody;
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
public interface Func0<OUTPUT> extends Supplier<OUTPUT>, ComputeBody<OUTPUT, RuntimeException> {
    
    public static <T> Func0<T> of(Func0<T> func0) {
        return func0;
    }
    
    public static <T> Func0<T> from(Supplier<T> supplier) {
        return supplier::get;
    }
    public static <T> Func0<T> from(IntFunction<T> generatorFunction) {
        return from(0, generatorFunction);
    }
    public static <T> Func0<T> from(int start, IntFunction<T> generatorFunction) {
        val counter = new AtomicInteger(start);
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
            throw ThrowFuncs.exceptionTranformer.value().apply(e);
        }
    }
    
    public default OUTPUT compute() throws RuntimeException {
        return apply();
    }
    
    public default OUTPUT apply() {
        return get();
    }
    
    public default Result<OUTPUT> applySafely() {
        return Result.from(this);
    }
    
    public default Result<OUTPUT> getSafely() {
        return Result.from(this);
    }
    
    public default Func0<OUTPUT> memoize() {
        return Func0.from(Func.lazy(this));
    }
    
    public default <TARGET> Func0<TARGET> then(Func1<OUTPUT, TARGET> mapper) {
        return ()->{
            val output = this.applyUnsafe();
            val target = Func.applyUnsafe(mapper, output);
            return target;
        };
    }
    
    public default Func0<OUTPUT> whenAbsentUse(OUTPUT defaultValue) {
        return ()->{
            val result = applySafely();
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func0<OUTPUT> whenAbsentGet(Supplier<OUTPUT> defaultSupplier) {
        return ()->{
            val result = applySafely();
            val value  = result.orElseGet(defaultSupplier);
            return value;
        };
    }
    public default Func0<OUTPUT> whenAbsentApply(Func1<Exception, OUTPUT> exceptionMapper) {
        return ()->{
            val result = applySafely();
            val value  = result.orApply(exceptionMapper);
            return value;
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
    
    public default Promise<OUTPUT> async() {
        return defer();
    }
    public default Promise<OUTPUT> defer() {
        return DeferAction.from(this).build().getPromise();
    }
    
    public default FuncUnit0 ignoreResult() {
        return FuncUnit0.of(()->applyUnsafe());
    }
    
}
