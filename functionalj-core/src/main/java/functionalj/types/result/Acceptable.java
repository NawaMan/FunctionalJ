package functionalj.types.result;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import lombok.val;

@SuppressWarnings("javadoc")
public abstract class Acceptable<DATA> extends Result<DATA> {
    
    protected Acceptable(Exception exception) {
        super(null, (exception == null) ? new UnacceptableForUnknownReasonException() : exception);
    }
    protected Acceptable(DATA value, Predicate<DATA> checker) {
        this(value, checker, null, new AtomicReference<Exception>());
    }
    protected Acceptable(
            DATA                                                             value, 
            Predicate<DATA>                                                  checker, 
            BiFunction<? super DATA, ? super Exception, ? extends Exception> unacceptableException) {
        this(value, checker, unacceptableException, new AtomicReference<Exception>());
    }
    private Acceptable(
            DATA                                                             value, 
            Predicate<DATA>                                                  checker, 
            BiFunction<? super DATA, ? super Exception, ? extends Exception> unacceptableExceptionSupplier, 
            AtomicReference<Exception>                                       unacceptableExceptionReference) {
        super(prepareValue(value, checker, unacceptableExceptionSupplier, unacceptableExceptionReference),
              unacceptableExceptionReference.get());
    }
    
    private static <D> D prepareValue(
                    D            value, 
                    Predicate<D> checker,
                    BiFunction<? super D, ? super Exception, ? extends Exception> unacceptableExceptionSupplier,
                    AtomicReference<Exception>                                    unacceptableExceptionReference) {
        try {
            if ((value == null) && !(checker instanceof NullSafePredicate)) {
                if (!(checker instanceof NullSafePredicate))
                    throw new NullPointerException();
                
                return null;
            }
            
            if (checker.test(value))
                return value;
            
            val exception
                    = (unacceptableExceptionSupplier != null)
                    ? unacceptableExceptionSupplier.apply(value, null)
                    : null;
            unacceptableExceptionReference.set(exception);
            return null;
        } catch (Exception e) {
            val exception
                = (unacceptableExceptionSupplier != null)
                ? unacceptableExceptionSupplier.apply(value, e)
                : e;
            unacceptableExceptionReference.set(exception);
            return null;
        }
    }
    
}
