package functionalj.types.result;

import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.val;

public class Valid<DATA extends Validatable<DATA, ?>> extends ImmutableResult<DATA> {
    
    public static <D extends Validatable<D, ?>> Valid<D> valueOf(D value) {
        try {
            return new Valid<D>(value);
            
        } catch (ValidationException e) {
            return new Valid<D>(null, e);
            
        } catch (Exception e) {
            return new Valid<D>(null, new ValidationException(e));
        }
    }
    
    // TODO Find the way to make this extensible .... to represent multiple state of object.
    
    Valid(DATA value, Exception exception) {
        super(null, exception);
    }
    
    protected Valid(DATA value) {
        super(((Supplier<DATA>)()->{
                    if (value == null)
                        return null;
                    
                    try {
                        val checker = createChecker(value);
                        if (checker == null)
                            throw new ValidationException("Checker cannot be null.");
                        
                        if (!checker.test(value))
                            throw new ValidationException("The value failed to check: " + value);
                        
                        return value;
                    } catch (ValidationException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new ValidationException(e);
                    }
                }).get(),
                (value == null)
                    ? new ValidationException(new NullPointerException())
                    : null);
    }
    
    private static <DATA extends Validatable<DATA, ?>> 
    Predicate<DATA> createChecker(Validatable<DATA, ?> checkable) {
        try {
            // TODO - Use Annotation to specify what method to use.
            return (Predicate<DATA>) checkable.getValidatorClass().newInstance();
            
        } catch (InstantiationException | IllegalAccessException e) {
            val canonicalName = checkable.getValidatorClass().getCanonicalName();
            throw new ValidationException("Fail creating checker: " + canonicalName);
        }
    }
    
}
