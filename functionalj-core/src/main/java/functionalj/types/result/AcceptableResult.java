package functionalj.types.result;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import lombok.val;

public abstract class AcceptableResult<DATA> extends Result<DATA> {
    
    protected AcceptableResult(Exception exception) {
        super(null, (exception == null) ? new UnacceptableForUnknownReasonException() : exception);
    }
    protected AcceptableResult(DATA value, Predicate<DATA> checker) {
        this(value, checker, null, new AtomicReference<Exception>());
    }
    protected AcceptableResult(
            DATA                                                             value, 
            Predicate<DATA>                                                  checker, 
            BiFunction<? super DATA, ? super Exception, ? extends Exception> unacceptableException) {
        this(value, checker, unacceptableException, new AtomicReference<Exception>());
    }
    private AcceptableResult(
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
