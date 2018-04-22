package functionalj.functions;

import java.util.function.Supplier;

/**
 * Function of zeroth parameter - a supplier.
 * 
 * @param <OUTPUT>  the output data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface Func0<OUTPUT> extends Supplier<OUTPUT> {
    
    /**
     * Constructs a Func0 from function or lambda.
     * 
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     **/
    public static <OUTPUT> Func0<OUTPUT> of(Func0<OUTPUT> supplier) {
        return supplier;
    }
    
}
