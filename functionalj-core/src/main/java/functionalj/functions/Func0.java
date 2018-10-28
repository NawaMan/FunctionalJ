package functionalj.functions;

import java.util.function.Supplier;

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
public interface Func0<OUTPUT> extends Supplier<OUTPUT>, ComputeBody<OUTPUT, RuntimeException>, FuncUnit0 {
    
    public static <T> Func0<T> of(Func0<T> func0) {
        return func0;
    }
    
    public static <T> Func0<T> from(Supplier<T> supplier) {
        return supplier::get;
    }
    
    public OUTPUT applyUnsafe() throws Exception;
    
    public default OUTPUT compute() throws RuntimeException {
        return apply();
    }
    
    public default OUTPUT apply() {
        return get();
    }
    
    public default Result<OUTPUT> applySafely() {
        return Result.from(this);
    }
    
    public default Func0<Result<OUTPUT>> safely() {
        return Func.of(this::applySafely);
    }
    
    public default OUTPUT getUnsafe() throws Exception {
        return applyUnsafe();
    }
    
    public default void runUnsafe() throws Exception {
        getUnsafe();
    }
    
    @Override
    public default void run() throws RuntimeException {
        FuncUnit0.super.run();
    }
    
    public default OUTPUT value() {
        return get();
    }
    public default OUTPUT get() {
        try {
            return applyUnsafe();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionInvocationException(e);
        }
    }
    
    public default Func0<OUTPUT> elseUse(OUTPUT defaultValue) {
        return ()->{
            val result = applySafely();
            val value  = result.orElse(defaultValue);
            return value;
        };
    }
    public default Func0<OUTPUT> elseGet(Supplier<OUTPUT> defaultSupplier) {
        return ()->{
            val result = applySafely();
            val value  = result.orElseGet(defaultSupplier);
            return value;
        };
    }
    
    public default OUTPUT orElse(OUTPUT defaultValue) {
        return getSafely().orElse(defaultValue);
    }
    
    public default OUTPUT orGet(Supplier<OUTPUT> defaultSupplier) {
        return getSafely().orGet(defaultSupplier);
    }
    
    public default OUTPUT orElseGet(Supplier<OUTPUT> defaultSupplier) {
        return orGet(defaultSupplier);
    }
    
    public default <TARGET> Func0<TARGET> mapTo(Func1<OUTPUT, TARGET> mapper) {
        return ()->{
            val output = this.applyUnsafe();
            val target = mapper.apply(output);
            return target;
        };
    }
    
    public default Result<OUTPUT> getSafely() {
        return Result.from(this);
    }
    
    public default Func0<OUTPUT> memoize() {
        return Func0.from(Func.lazy(this));
    }
    
    public default Func1<?, OUTPUT> toFunc1() {
        return __ -> getUnsafe();
    }
    public default Func2<?, ?, OUTPUT> toFunc2() {
        return (__1, __2) -> getUnsafe();
    }
    public default Func3<?, ?, ?, OUTPUT> toFunc3() {
        return (__1, __2, __3) -> getUnsafe();
    }
    public default Func4<?, ?, ?, ?, OUTPUT> toFunc4() {
        return (__1, __2, __3, __4) -> getUnsafe();
    }
    public default Func5<?, ?, ?, ?, ?, OUTPUT> toFunc5() {
        return (__1, __2, __3, __4, __5) -> getUnsafe();
    }
    public default Func6<?, ?, ?, ?, ?, ?, OUTPUT> toFunc6() {
        return (__1, __2, __3, __4, __5, __6) -> getUnsafe();
    }
    
}
