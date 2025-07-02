package functionalj.functions;

import static functionalj.exception.Throwables.exceptionTransformer;

import java.util.function.Supplier;

import functionalj.exception.Throwables;
import lombok.val;

public interface ThrowFuncs {
    
	/**
	 * Throw the a throwable from the supplier without creating an flow-interruption to the compiler.
	 * 
	 * @param <T>       the throwable type.
	 * @param supplier  the throwable supplier.
	 * @return          nothing.
	 * @throws T        the throwable.
	 */
    public static <T extends Throwable> T throwFrom(Supplier<T> supplier) throws T {
        throw supplier.get();
    }
    
    /** Log a throwable for when throwing it is not preferred. **/
    public static void logUnthrowable(Throwable throwable) {
        Throwables.noThrowLogger.value().accept(throwable);
    }
    
    /** Throw the given throwable as a {@link RuntimeException} */
    public static void throwAsRuntimeException(Throwable throwable) {
        val runtimeException = exceptionTransformer.value().apply(throwable);
        throw runtimeException;
    }
    
}
