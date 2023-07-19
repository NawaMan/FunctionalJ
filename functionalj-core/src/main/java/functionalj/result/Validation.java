// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import functionalj.function.Absent;
import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.ResultAccess;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
import functionalj.result.Specs.ValidationSpec;
import lombok.val;
import nullablej.utils.reflection.UProxy;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class Validation<D extends Object> implements Pipeable<Validation<D>> {
    
    public static final <D extends Object> ToBoolean<D> ToBoolean(Function<D, java.lang.Boolean> checker, String messageTemplate) {
        return new ToBoolean<D>(checker, messageTemplate);
    }
    
    public static final <D extends Object> ToMessage<D> ToMessage(Function<D, java.lang.String> errorMsg) {
        return new ToMessage<D>(errorMsg);
    }
    
    public static final <D extends Object> ToException<D> ToException(Function<D, functionalj.result.ValidationException> errorChecker) {
        return new ToException<D>(errorChecker);
    }
    
    private final ValidationSpec<D> __spec = UProxy.createDefaultProxy(ValidationSpec.class);
    
    public static final ValidationLens<Validation> theValidation = new ValidationLens<>(LensSpec.of(Validation.class));
    
    public static class ValidationLens<HOST> extends ObjectLensImpl<HOST, Validation> {
        
        public final BooleanAccessPrimitive<Validation> isToBoolean = Validation::isToBoolean;
        
        public final BooleanAccessPrimitive<Validation> isToMessage = Validation::isToMessage;
        
        public final BooleanAccessPrimitive<Validation> isToException = Validation::isToException;
        
        public final ResultAccess<HOST, ToBoolean, ToBoolean.ToBooleanLens<HOST>> asToBoolean = createSubResultLens(Validation::asToBoolean, null, spec -> new ToBoolean.ToBooleanLens(spec));
        
        public final ResultAccess<HOST, ToMessage, ToMessage.ToMessageLens<HOST>> asToMessage = createSubResultLens(Validation::asToMessage, null, spec -> new ToMessage.ToMessageLens(spec));
        
        public final ResultAccess<HOST, ToException, ToException.ToExceptionLens<HOST>> asToException = createSubResultLens(Validation::asToException, null, spec -> new ToException.ToExceptionLens(spec));
        
        public ValidationLens(LensSpec<HOST, Validation> spec) {
            super(spec);
        }
    }
    
    private Validation() {
    }
    
    public Validation<D> __data() throws Exception {
        return this;
    }
    
    public Result<Validation<D>> toResult() {
        return Result.valueOf(this);
    }
    
    public static final class ToBoolean<D extends Object> extends Validation<D> {
        
        public static final ToBooleanLens<ToBoolean> theToBoolean = new ToBooleanLens<>(LensSpec.of(ToBoolean.class));
        
        private Function<D, java.lang.Boolean> checker;
        
        private String messageTemplate;
        
        private ToBoolean(Function<D, java.lang.Boolean> checker, String messageTemplate) {
            this.checker = $utils.notNull(checker);
            this.messageTemplate = $utils.notNull(messageTemplate);
        }
        
        public Function<D, java.lang.Boolean> checker() {
            return checker;
        }
        
        public String messageTemplate() {
            return messageTemplate;
        }
        
        public ToBoolean<D> withChecker(Function<D, java.lang.Boolean> checker) {
            return new ToBoolean<D>(checker, messageTemplate);
        }
        
        public ToBoolean<D> withMessageTemplate(String messageTemplate) {
            return new ToBoolean<D>(checker, messageTemplate);
        }
        
        public static class ToBooleanLens<HOST> extends ObjectLensImpl<HOST, Validation.ToBoolean> {
        
            public final ObjectLens<HOST, Object> checker = (ObjectLens) createSubLens(Validation.ToBoolean::checker, Validation.ToBoolean::withChecker, ObjectLens::of);
        
            public final StringLens<HOST> messageTemplate = (StringLens) createSubLens(Validation.ToBoolean::messageTemplate, Validation.ToBoolean::withMessageTemplate, StringLens::of);
        
            public ToBooleanLens(LensSpec<HOST, Validation.ToBoolean> spec) {
                super(spec);
            }
        }
    }
    
    public static final class ToMessage<D extends Object> extends Validation<D> {
        
        public static final ToMessageLens<ToMessage> theToMessage = new ToMessageLens<>(LensSpec.of(ToMessage.class));
        
        private Function<D, java.lang.String> errorMsg;
        
        private ToMessage(Function<D, java.lang.String> errorMsg) {
            this.errorMsg = $utils.notNull(errorMsg);
        }
        
        public Function<D, java.lang.String> errorMsg() {
            return errorMsg;
        }
        
        public ToMessage<D> withErrorMsg(Function<D, java.lang.String> errorMsg) {
            return new ToMessage<D>(errorMsg);
        }
        
        public static class ToMessageLens<HOST> extends ObjectLensImpl<HOST, Validation.ToMessage> {
        
            public final ObjectLens<HOST, Object> errorMsg = (ObjectLens) createSubLens(Validation.ToMessage::errorMsg, Validation.ToMessage::withErrorMsg, ObjectLens::of);
        
            public ToMessageLens(LensSpec<HOST, Validation.ToMessage> spec) {
                super(spec);
            }
        }
    }
    
    public static final class ToException<D extends Object> extends Validation<D> {
        
        public static final ToExceptionLens<ToException> theToException = new ToExceptionLens<>(LensSpec.of(ToException.class));
        
        private Function<D, functionalj.result.ValidationException> errorChecker;
        
        private ToException(Function<D, functionalj.result.ValidationException> errorChecker) {
            this.errorChecker = $utils.notNull(errorChecker);
        }
        
        public Function<D, functionalj.result.ValidationException> errorChecker() {
            return errorChecker;
        }
        
        public ToException<D> withErrorChecker(Function<D, functionalj.result.ValidationException> errorChecker) {
            return new ToException<D>(errorChecker);
        }
        
        public static class ToExceptionLens<HOST> extends ObjectLensImpl<HOST, Validation.ToException> {
        
            public final ObjectLens<HOST, Object> errorChecker = (ObjectLens) createSubLens(Validation.ToException::errorChecker, Validation.ToException::withErrorChecker, ObjectLens::of);
        
            public ToExceptionLens(LensSpec<HOST, Validation.ToException> spec) {
                super(spec);
            }
        }
    }
    
    private final ValidationFirstSwitch<D> __switch = new ValidationFirstSwitch<D>(this);
    
    public ValidationFirstSwitch<D> match() {
        return __switch;
    }
    
    private volatile String toString = null;
    
    @Override
    public String toString() {
        if (toString != null)
            return toString;
        synchronized (this) {
            if (toString != null)
                return toString;
            toString = match().toBoolean(toBoolean -> "ToBoolean(" + String.format("%1$s,%2$s", toBoolean.checker, toBoolean.messageTemplate) + ")").toMessage(toMessage -> "ToMessage(" + String.format("%1$s", toMessage.errorMsg) + ")").toException(toException -> "ToException(" + String.format("%1$s", toException.errorChecker) + ")");
            return toString;
        }
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Validation))
            return false;
        if (this == obj)
            return true;
        String objToString = obj.toString();
        String thisToString = this.toString();
        return thisToString.equals(objToString);
    }
    
    public boolean ensureValid(D data) {
        return __spec.ensureValid(this, data);
    }
    
    public functionalj.result.ValidationException validate(D data) {
        return __spec.validate(this, data);
    }
    
    public functionalj.validator.Validator<D> toValidator() {
        return __spec.toValidator(this);
    }
    
    public boolean isToBoolean() {
        return this instanceof ToBoolean;
    }
    
    public Result<ToBoolean<D>> asToBoolean() {
        return Result.valueOf(this).filter(ToBoolean.class).map(ToBoolean.class::cast);
    }
    
    public Validation<D> ifToBoolean(Consumer<ToBoolean<D>> action) {
        if (isToBoolean())
            action.accept((ToBoolean<D>) this);
        return this;
    }
    
    public Validation<D> ifToBoolean(Runnable action) {
        if (isToBoolean())
            action.run();
        return this;
    }
    
    public boolean isToMessage() {
        return this instanceof ToMessage;
    }
    
    public Result<ToMessage<D>> asToMessage() {
        return Result.valueOf(this).filter(ToMessage.class).map(ToMessage.class::cast);
    }
    
    public Validation<D> ifToMessage(Consumer<ToMessage<D>> action) {
        if (isToMessage())
            action.accept((ToMessage<D>) this);
        return this;
    }
    
    public Validation<D> ifToMessage(Runnable action) {
        if (isToMessage())
            action.run();
        return this;
    }
    
    public boolean isToException() {
        return this instanceof ToException;
    }
    
    public Result<ToException<D>> asToException() {
        return Result.valueOf(this).filter(ToException.class).map(ToException.class::cast);
    }
    
    public Validation<D> ifToException(Consumer<ToException<D>> action) {
        if (isToException())
            action.accept((ToException<D>) this);
        return this;
    }
    
    public Validation<D> ifToException(Runnable action) {
        if (isToException())
            action.run();
        return this;
    }
    
    public static class ValidationFirstSwitch<D extends Object> {
        
        private Validation<D> $value;
        
        private ValidationFirstSwitch(Validation<D> theValue) {
            this.$value = theValue;
        }
        
        public <TARGET> ValidationFirstSwitchTyped<TARGET, D> toA(Class<TARGET> clzz) {
            return new ValidationFirstSwitchTyped<TARGET, D>($value);
        }
        
        public <TARGET> ValidationSwitchToMessageToException<TARGET, D> toBoolean(Function<? super ToBoolean<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> $action = nullValue();
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : ($value instanceof ToBoolean) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToBoolean<D>) d)) : oldAction;
            return new ValidationSwitchToMessageToException<TARGET, D>($value, newAction);
        }
        
        private <T> T nullValue() {
            return (T) null;
        }
        
        public <TARGET> ValidationSwitchToMessageToException<TARGET, D> toBoolean(Supplier<TARGET> theSupplier) {
            return toBoolean(d -> theSupplier.get());
        }
        
        public <TARGET> ValidationSwitchToMessageToException<TARGET, D> toBoolean(TARGET theValue) {
            return toBoolean(d -> theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, Function<? super ToBoolean<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> $action = nullValue();
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : (($value instanceof ToBoolean) && check.test((ToBoolean<D>) $value)) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToBoolean<D>) d)) : oldAction;
            return new ValidationSwitchToBooleanToMessageToException<TARGET, D>($value, newAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, Supplier<TARGET> theSupplier) {
            return toBoolean(check, d -> theSupplier.get());
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, TARGET theValue) {
            return toBoolean(check, d -> theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public <TARGET> ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
    }
    
    public static class ValidationFirstSwitchTyped<TARGET, D extends Object> {
        
        private Validation<D> $value;
        
        private ValidationFirstSwitchTyped(Validation<D> theValue) {
            this.$value = theValue;
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toBoolean(Function<? super ToBoolean<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> $action = nullValue();
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : ($value instanceof ToBoolean) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToBoolean<D>) d)) : oldAction;
            return new ValidationSwitchToMessageToException<TARGET, D>($value, newAction);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toBoolean(Supplier<TARGET> theSupplier) {
            return toBoolean(d -> theSupplier.get());
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toBoolean(TARGET theValue) {
            return toBoolean(d -> theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, Function<? super ToBoolean<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> $action = nullValue();
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : (($value instanceof ToBoolean) && check.test((ToBoolean<D>) $value)) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToBoolean<D>) d)) : oldAction;
            return new ValidationSwitchToBooleanToMessageToException<TARGET, D>($value, newAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, Supplier<TARGET> theSupplier) {
            return toBoolean(check, d -> theSupplier.get());
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, TARGET theValue) {
            return toBoolean(check, d -> theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
        
        private <T> T nullValue() {
            return (T) null;
        }
    }
    
    public static class ValidationSwitchToBooleanToMessageToException<TARGET, D extends Object> extends ChoiceTypeSwitch<Validation<D>, TARGET> {
        
        private ValidationSwitchToBooleanToMessageToException(Validation<D> theValue, Function<Validation<D>, TARGET> theAction) {
            super(theValue, theAction);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toBoolean(Function<? super ToBoolean<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : ($value instanceof ToBoolean) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToBoolean<D>) d)) : oldAction;
            return new ValidationSwitchToMessageToException<TARGET, D>($value, newAction);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toBoolean(Supplier<TARGET> theSupplier) {
            return toBoolean(d -> theSupplier.get());
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toBoolean(TARGET theValue) {
            return toBoolean(d -> theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, Function<? super ToBoolean<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : (($value instanceof ToBoolean) && check.test((ToBoolean<D>) $value)) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToBoolean<D>) d)) : oldAction;
            return new ValidationSwitchToBooleanToMessageToException<TARGET, D>($value, newAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, Supplier<TARGET> theSupplier) {
            return toBoolean(check, d -> theSupplier.get());
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBoolean(Predicate<ToBoolean<D>> check, TARGET theValue) {
            return toBoolean(check, d -> theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Absent messageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Absent messageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, String aMessageTemplate, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && $utils.checkEquals(aMessageTemplate, toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Absent checker, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Function aChecker, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> $utils.checkEquals(aChecker, toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, Function<ToBoolean<D>, TARGET> theAction) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theAction);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, Supplier<TARGET> theSupplier) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theSupplier);
        }
        
        public ValidationSwitchToBooleanToMessageToException<TARGET, D> toBooleanOf(Predicate<Function> checkerCheck, Predicate<String> messageTemplateCheck, TARGET theValue) {
            return toBoolean(toBoolean -> checkerCheck.test(toBoolean.checker) && messageTemplateCheck.test(toBoolean.messageTemplate), theValue);
        }
    }
    
    public static class ValidationSwitchToMessageToException<TARGET, D extends Object> extends ChoiceTypeSwitch<Validation<D>, TARGET> {
        
        private ValidationSwitchToMessageToException(Validation<D> theValue, Function<Validation<D>, TARGET> theAction) {
            super(theValue, theAction);
        }
        
        public ValidationSwitchToException<TARGET, D> toMessage(Function<? super ToMessage<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : ($value instanceof ToMessage) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToMessage<D>) d)) : oldAction;
            return new ValidationSwitchToException<TARGET, D>($value, newAction);
        }
        
        public ValidationSwitchToException<TARGET, D> toMessage(Supplier<TARGET> theSupplier) {
            return toMessage(d -> theSupplier.get());
        }
        
        public ValidationSwitchToException<TARGET, D> toMessage(TARGET theValue) {
            return toMessage(d -> theValue);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessage(Predicate<ToMessage<D>> check, Function<? super ToMessage<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : (($value instanceof ToMessage) && check.test((ToMessage<D>) $value)) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToMessage<D>) d)) : oldAction;
            return new ValidationSwitchToMessageToException<TARGET, D>($value, newAction);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessage(Predicate<ToMessage<D>> check, Supplier<TARGET> theSupplier) {
            return toMessage(check, d -> theSupplier.get());
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessage(Predicate<ToMessage<D>> check, TARGET theValue) {
            return toMessage(check, d -> theValue);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessageOf(Function aErrorMsg, Function<ToMessage<D>, TARGET> theAction) {
            return toMessage(toMessage -> $utils.checkEquals(aErrorMsg, toMessage.errorMsg), theAction);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessageOf(Function aErrorMsg, Supplier<TARGET> theSupplier) {
            return toMessage(toMessage -> $utils.checkEquals(aErrorMsg, toMessage.errorMsg), theSupplier);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessageOf(Function aErrorMsg, TARGET theValue) {
            return toMessage(toMessage -> $utils.checkEquals(aErrorMsg, toMessage.errorMsg), theValue);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessageOf(Predicate<Function> errorMsgCheck, Function<ToMessage<D>, TARGET> theAction) {
            return toMessage(toMessage -> errorMsgCheck.test(toMessage.errorMsg), theAction);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessageOf(Predicate<Function> errorMsgCheck, Supplier<TARGET> theSupplier) {
            return toMessage(toMessage -> errorMsgCheck.test(toMessage.errorMsg), theSupplier);
        }
        
        public ValidationSwitchToMessageToException<TARGET, D> toMessageOf(Predicate<Function> errorMsgCheck, TARGET theValue) {
            return toMessage(toMessage -> errorMsgCheck.test(toMessage.errorMsg), theValue);
        }
    }
    
    public static class ValidationSwitchToException<TARGET, D extends Object> extends ChoiceTypeSwitch<Validation<D>, TARGET> {
        
        private ValidationSwitchToException(Validation<D> theValue, Function<Validation<D>, TARGET> theAction) {
            super(theValue, theAction);
        }
        
        public TARGET toException(Function<? super ToException<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : ($value instanceof ToException) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToException<D>) d)) : oldAction;
            return newAction.apply($value);
        }
        
        public TARGET toException(Supplier<TARGET> theSupplier) {
            return toException(d -> theSupplier.get());
        }
        
        public TARGET toException(TARGET theValue) {
            return toException(d -> theValue);
        }
        
        public ValidationSwitchToException<TARGET, D> toException(Predicate<ToException<D>> check, Function<? super ToException<D>, TARGET> theAction) {
            Function<Validation<D>, TARGET> oldAction = (Function<Validation<D>, TARGET>) $action;
            Function<Validation<D>, TARGET> newAction = ($action != null) ? oldAction : (($value instanceof ToException) && check.test((ToException<D>) $value)) ? (Function<Validation<D>, TARGET>) (d -> theAction.apply((ToException<D>) d)) : oldAction;
            return new ValidationSwitchToException<TARGET, D>($value, newAction);
        }
        
        public ValidationSwitchToException<TARGET, D> toException(Predicate<ToException<D>> check, Supplier<TARGET> theSupplier) {
            return toException(check, d -> theSupplier.get());
        }
        
        public ValidationSwitchToException<TARGET, D> toException(Predicate<ToException<D>> check, TARGET theValue) {
            return toException(check, d -> theValue);
        }
        
        public ValidationSwitchToException<TARGET, D> toExceptionOf(Function aErrorChecker, Function<ToException<D>, TARGET> theAction) {
            return toException(toException -> $utils.checkEquals(aErrorChecker, toException.errorChecker), theAction);
        }
        
        public ValidationSwitchToException<TARGET, D> toExceptionOf(Function aErrorChecker, Supplier<TARGET> theSupplier) {
            return toException(toException -> $utils.checkEquals(aErrorChecker, toException.errorChecker), theSupplier);
        }
        
        public ValidationSwitchToException<TARGET, D> toExceptionOf(Function aErrorChecker, TARGET theValue) {
            return toException(toException -> $utils.checkEquals(aErrorChecker, toException.errorChecker), theValue);
        }
        
        public ValidationSwitchToException<TARGET, D> toExceptionOf(Predicate<Function> errorCheckerCheck, Function<ToException<D>, TARGET> theAction) {
            return toException(toException -> errorCheckerCheck.test(toException.errorChecker), theAction);
        }
        
        public ValidationSwitchToException<TARGET, D> toExceptionOf(Predicate<Function> errorCheckerCheck, Supplier<TARGET> theSupplier) {
            return toException(toException -> errorCheckerCheck.test(toException.errorChecker), theSupplier);
        }
        
        public ValidationSwitchToException<TARGET, D> toExceptionOf(Predicate<Function> errorCheckerCheck, TARGET theValue) {
            return toException(toException -> errorCheckerCheck.test(toException.errorChecker), theValue);
        }
    }
    
    // == Duplicate ==
    public static class $utils {
        
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
        
        public static boolean checkEquals(byte a, byte b) {
            return a == b;
        }
        
        public static boolean checkEquals(short a, short b) {
            return a == b;
        }
        
        public static boolean checkEquals(int a, int b) {
            return a == b;
        }
        
        public static boolean checkEquals(long a, long b) {
            return a == b;
        }
        
        public static boolean checkEquals(float a, float b) {
            return a == b;
        }
        
        public static boolean checkEquals(double a, double b) {
            return a == b;
        }
        
        public static boolean checkEquals(boolean a, boolean b) {
            return a == b;
        }
        
        public static boolean checkEquals(Object a, Object b) {
            return ((a == null) && (b == null)) || Objects.equals(a, b);
        }
    }
    
    public static abstract class ChoiceTypeSwitch<D, T> {
        
        protected final D $value;
        
        protected final Function<? super D, ? extends T> $action;
        
        protected ChoiceTypeSwitch(D theValue, Function<? super D, ? extends T> theAction) {
            this.$value = theValue;
            this.$action = theAction;
        }
        
        public T orElse(T elseValue) {
            return ($action != null) ? $action.apply($value) : elseValue;
        }
        
        public T orGet(Supplier<T> valueSupplier) {
            return ($action != null) ? $action.apply($value) : valueSupplier.get();
        }
        
        public T orGet(Function<? super D, T> valueMapper) {
            val newAction = (Function<? super D, T>) (($action != null) ? $action : valueMapper);
            return newAction.apply($value);
        }
        
        public T orElseGet(Supplier<T> valueSupplier) {
            return orGet(valueSupplier);
        }
        
        public T orElseGet(Function<? super D, T> valueMapper) {
            return orGet(valueMapper);
        }
        
        public static class ChoiceTypeSwitchData<D, T> {
        
            protected final D value;
        
            protected final Function<D, T> action;
        
            public ChoiceTypeSwitchData(D value) {
                this(value, null);
            }
        
            public ChoiceTypeSwitchData(D value, Function<D, T> action) {
                this.value = value;
                this.action = action;
            }
        
            public D value() {
                return value;
            }
        
            public Function<D, T> action() {
                return action;
            }
        
            public ChoiceTypeSwitchData<D, T> withValue(D value) {
                return new ChoiceTypeSwitchData<>(value, action);
            }
        
            public ChoiceTypeSwitchData<D, T> withAction(Function<D, T> action) {
                return new ChoiceTypeSwitchData<>(value, action);
            }
        }
    }
}
