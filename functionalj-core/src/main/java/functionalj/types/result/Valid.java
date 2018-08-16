package functionalj.types.result;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import lombok.val;

@SuppressWarnings("javadoc")
public final class Valid<DATA extends Validatable<DATA, ?>> extends Acceptable<DATA> {

    public static <D extends Validatable<D, ?>> Valid<D> valueOf(D data) {
        return new Valid<D>(data);
    }
    
    
    @SuppressWarnings("rawtypes")
    private static final NullSafePredicate FALSE = v -> false;
    @SuppressWarnings("unchecked")
    private static final <D> NullSafePredicate<D> alwaysFalse() { return (NullSafePredicate<D>)FALSE; }
    
    
    Valid(DATA value, String exceptionMsg) {
        super((exceptionMsg != null) ? null                                : value,
              (exceptionMsg != null) ? alwaysFalse()                       : createChecker(value),
              (exceptionMsg != null) ? toValidationException(exceptionMsg) : prepareUnacceptableException());
    }
    Valid(DATA value, Exception exception) {
        super((exception != null) ? null                             : value,
              (exception != null) ? alwaysFalse()                    : createChecker(value),
              (exception != null) ? toValidationException(exception) : prepareUnacceptableException());
    }
    Valid(DATA value) {
        super(value, createChecker(value), prepareUnacceptableException());
    }
    
    private static <D extends Validatable<D, ?>> BiFunction<? super D, ? super Exception, ? extends Exception> toValidationException(String errMsg) {
        return (v, c) -> {
            return new ValidationException(errMsg);
        };
    }
    private static <D extends Validatable<D, ?>> BiFunction<? super D, ? super Exception, ? extends Exception> toValidationException(Exception exception) {
        return (v, c) -> {
            if (exception instanceof ValidationException)
                return (ValidationException)exception;
            
            return new ValidationException(exception);
        };
    }
    
    @SuppressWarnings("unchecked")
    private static <D extends Validatable<D, ?>> NullSafePredicate<D> createChecker(Validatable<D, ?> checkable) {
        return value -> {
            if (value == null)
                throw new ValidationException(new NullPointerException());
            
            try {
                try {
                    val validatorClass = value.getValidatorClass();
                    val predicate      = (Predicate<D>)validatorClass.newInstance();
                    return predicate.test(value);
                    
                } catch (InstantiationException | IllegalAccessException e) {
                    val canonicalName = checkable.getValidatorClass().getCanonicalName();
                    throw new ValidationException("Fail creating checker: " + canonicalName);
                }
            } catch (ValidationException e) {
                throw e;
            } catch (Exception e) {
                throw new ValidationException(e);
            }
        };
    }
    
    private static <D extends Validatable<D, ?>> BiFunction<? super D, ? super Exception, ? extends Exception> prepareUnacceptableException() {
        return (v, cause) -> {
            
            val template = (v != null) ? v.getValidationErrorTemplateByValue() : null;
            if ((v == null) || (template == null)) {
                if (cause instanceof ValidationException)
                    return (ValidationException)cause;
                if (cause != null)
                    return new ValidationException(cause);
                
                return new ValidationException(new NullPointerException());
            }
            
            val msg = String.format(template, v);
            return new ValidationException(msg, cause);
        };
    }
    
}
