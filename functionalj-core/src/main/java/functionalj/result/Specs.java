// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.result;

import static nullablej.nullable.Nullable.nullable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import functionalj.validator.Validator;


public class Specs {
    
//    @functionalj.types.Choice
    public static interface ValidationSpec<D> {
        void ToBoolean  (Function<D, Boolean>             checker, String messageTemplate);
        void ToMessage  (Function<D, String>              errorMsg);
        void ToException(Function<D, ValidationException> errorChecker);
        
        // TODO - BUG!!! ... the method has to return something can't be void. ... fix this when can.
        default boolean ensureValid(Validation<D> self, D data) {
            var validationException = validate(self, data);
            if (validationException != null)
                throw validationException;
            
            return true;
        }
        default ValidationException validate(Validation<D> validation, D data) {
            var validationException = validation.match()
                    .toBoolean  (v -> $inner.checkToBoolean  (v, data))
                    .toMessage  (v -> $inner.checkToMessage  (v, data))
                    .toException(v -> $inner.checkToException(v, data));
            return validationException;
        }
        default Validator<D> toValidator(Validation<D> self) {
            var ref = new AtomicReference<ValidationException>();
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
                var exception = validating.errorChecker().apply(data);
                return exception;
            }
        }
    }
}
