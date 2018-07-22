package functionalj.types.result;

import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func4;
import lombok.val;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(Exception cause) {
        super(cause);
    }
    public ValidationException(String message, Exception cause) {
        super(message, cause);
    }
    
    ValidationException() {
    }
    
    @Override
    public String toString() {
        val msg      = this.getMessage();
        val cause    = getCause();
        val causeMsg = ((msg != null) || (cause == null)) ? "" : ": " + cause.toString();
        return super.toString() + causeMsg;
    }
    
    
    
    public static ValidationException of(Exception e) {
        if (e instanceof ValidationException)
            return (ValidationException)e;
        return new ValidationException(e);
    }
    
    public static <D, T> 
            Func4<
                ? super D, 
                ? super T, 
                ? super Function<? super D, T>, 
                ? super Predicate<? super T>, 
                ? extends ValidationException> forString(String message) {
        return (d, t, m, p) -> new ValidationException(message);
    }
    public static <D, T> 
            Func4<
                ? super D, 
                ? super T, 
                ? super Function<? super D, T>, 
                ? super Predicate<? super T>, 
                ? extends ValidationException> forTemplate(String template) {
        return (d, t, m, p) -> new ValidationException(String.format(template, d, t, m, p));
    }
    
}
