package functionalj.functions;

import java.util.function.Supplier;

import functionalj.types.result.ImmutableResult;
import functionalj.types.result.Result;

/**
 * Function of zeroth parameter - a supplier.
 * 
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func0<OUTPUT> extends Supplier<OUTPUT> {

    public OUTPUT applyUnsafe() throws Exception;
    
    public default OUTPUT apply() {
        return get();
    }
    
    public default Result<OUTPUT> applySafely() {
        return ImmutableResult.from(this);
    }
    
    public default Func0<Result<OUTPUT>> safely() {
        return Func.from(this::applySafely);
    }
    
    public default OUTPUT getUnsafe() throws Exception {
        return applyUnsafe();
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
    
    public default Result<OUTPUT> getSafely() {
        return ImmutableResult.from(this);
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
