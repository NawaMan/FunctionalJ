package functionalj.result;

import static functionalj.annotations.choice.ChoiceTypes.Switch;
import static java.util.Objects.requireNonNull;
import static nawaman.nullablej.nullable.Nullable.nullable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import functionalj.annotations.Choice;
import functionalj.annotations.choice.Self1;
import lombok.val;

@SuppressWarnings("javadoc")
public abstract class Acceptable<DATA> extends ImmutableResult<DATA> {
    
    protected Acceptable(Exception exception) {
        super(null, (exception == null) ? new UnacceptableForUnknownReasonException() : exception);
    }
    protected Acceptable(Validation<DATA> validating, DATA value) {
        this(validating, value, new AtomicReference<Exception>());
    }
    private Acceptable(Validation<DATA> validating, DATA value, AtomicReference<Exception> exceptionRef) {
        super(prepareValue(validating, value, exceptionRef), exceptionRef);
    }
    
    private static <D> D prepareValue(Validation<D> validating, D value, AtomicReference<Exception> exceptionRef) {
        try {
            requireNonNull(validating);
            validating.ensureValid(value);
            return value;
        } catch (ValidationException e) {
            exceptionRef.set(e);
        } catch (Exception e) {
            exceptionRef.set(new ValidationException(e));
        }
        return null;
    }
    
    //== AUX class ==
    
    @Choice
    public static interface ValidationSpec<D> {
        void ToBoolean  (Function<D, Boolean>             checker, String messageTemplate);
        void ToMessage  (Function<D, String>              errorMsg);
        void ToException(Function<D, ValidationException> errorChecker);
        
        // TODO - BUG!!! ... the method has to return something can't be void. ... fix this when can.
        default boolean ensureValid(Self1<D> self, D data) {
            @SuppressWarnings("unchecked")
            val validation          = (Validation<D>)self.asMe();
            val validationException = Switch(validation)
                    .toBoolean  (v -> $inner.checkToBoolean(v, data))
                    .toMessage  (v -> $inner.checkToMessage(v, data))
                    .toException(v -> $inner.checkToException(v, data));
            
            if (validationException != null)
                throw validationException;
            
            return true;
        }
        
        static class $inner {
            
            static <D> ValidationException checkToBoolean(Validation.ToBoolean<D> validating, D data) {
                return Result
                        .from  (()    -> validating.checker().apply(data))
                        .filter(valid -> !valid)
                        .map   (__    -> new ValidationException(getErrorMessage(validating, data)))
                        .get   ();
            }
            private static <D> String getErrorMessage(Validation.ToBoolean<D> validating, D data) {
                return nullable(validating.messageTemplate())
                        .map   (template -> String.format(template, data))
                        .get   ();
            }
            static <D> ValidationException checkToMessage(Validation.ToMessage<D> validating, D data) {
                return Result
                        .from(()     -> validating.errorMsg().apply(data))
                        .map (errMsg -> new ValidationException(errMsg))
                        .get ();
            }
            static <D> ValidationException checkToException(Validation.ToException<D> validating, D data) {
                val exception = validating.errorChecker().apply(data);
                return exception;
            }
        }
    }
    
}
