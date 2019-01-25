package functionalj.result;

import static functionalj.annotations.choice.ChoiceTypes.Match;
import static nawaman.nullablej.nullable.Nullable.nullable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import functionalj.annotations.choice.Self1;
import functionalj.validator.Validator;
import lombok.val;

public class Specs {
    
//    @functionalj.annotations.Choice
    public static interface ValidationSpec<D> {
        void ToBoolean  (Function<D, Boolean>             checker, String messageTemplate);
        void ToMessage  (Function<D, String>              errorMsg);
        void ToException(Function<D, ValidationException> errorChecker);
        
        // TODO - BUG!!! ... the method has to return something can't be void. ... fix this when can.
        default boolean ensureValid(Self1<D> self, D data) {
            val validationException = validate(self, data);
            if (validationException != null)
                throw validationException;
            
            return true;
        }
        default ValidationException validate(Self1<D> self, D data) {
            @SuppressWarnings("unchecked")
            val validation          = (Validation<D>)self.asMe();
            val validationException = Match(validation)
                    .toBoolean  (v -> $inner.checkToBoolean  (v, data))
                    .toMessage  (v -> $inner.checkToMessage  (v, data))
                    .toException(v -> $inner.checkToException(v, data));
            return validationException;
        }
        default Validator<D> toValidator(Self1<D> self) {
            val ref = new AtomicReference<ValidationException>();
            return Validator.of(data -> {
                        ref.set(validate(self, data));
                        return (ref.get() == null);
                    },
                    (__, ___) -> ref.get());
        }
        
        static class $inner {
            
            static <D> ValidationException checkToBoolean(Validation.ToBoolean<D> validating, D data) {
                return Result
                        .of  (()    -> validating.checker().apply(data))
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
                        .of(()     -> validating.errorMsg().apply(data))
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
