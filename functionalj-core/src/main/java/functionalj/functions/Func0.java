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
    
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     **/
    public static <OUTPUT> Func0<OUTPUT> from(Supplier<OUTPUT> supplier) {
        return ()->supplier.get();
    }
    
    /**
     * Get the value from the supplier that might be null.
     * 
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param supplier  the suppler.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <O, F extends O> O getOrElse(Supplier<O> supplier, F fallback) {
        if (supplier == null)
            return fallback;
        
        return supplier.get();
    }
    
}
