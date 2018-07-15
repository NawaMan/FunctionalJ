package functionalj.functions;

import java.util.function.Supplier;

import functionalj.types.ImmutableResult;
import functionalj.types.Result;

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
    
}
