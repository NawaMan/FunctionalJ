package functionalj.all.matchcase;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.val;

public class OptionalSwitchCase {
    
    // TODO - Find a way to DRY this and also make one for Nullable.
    
    public static class OptionalSwitch<SOURCE> {
        
        private final Optional<SOURCE> $value;
        
        public OptionalSwitch(Optional<SOURCE> source) {
            this.$value = source;
        }
        public <TARGET> OptionalSwitchTyped<SOURCE, TARGET> toA(Class<TARGET> clzz) {
            return new OptionalSwitchTyped<SOURCE, TARGET>($value, clzz);
        }
        
        public <TARGET> OptionalSwitchWithoutPresent<SOURCE, TARGET> empty(Supplier<? extends TARGET> theAction) {
            Function<SOURCE, TARGET> $action = null;
            Function<SOURCE, TARGET> oldAction = (Function<SOURCE, TARGET>)$action;
            Function<SOURCE, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (!$value.isPresent())
                    ? (Function<SOURCE, TARGET>)(d -> theAction.get())
                    : oldAction;
            
            return new OptionalSwitchWithoutPresent<SOURCE, TARGET>($value, newAction);
        }
        public <TARGET> OptionalSwitchWithoutPresent<SOURCE, TARGET> empty(TARGET theValue) {
            return empty(()->theValue);
        }
        
        public <TARGET> OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(Function<? super SOURCE, ? extends TARGET> theAction) {
            Function<SOURCE, TARGET> $action = null;
            Function<SOURCE, TARGET> oldAction = (Function<SOURCE, TARGET>)$action;
            Function<SOURCE, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value.isPresent())
                    ? (Function<SOURCE, TARGET>)(o -> theAction.apply(o))
                    : oldAction;
            
            return new OptionalSwitchWithoutEmpty<SOURCE, TARGET>($value, newAction);
        }
        public <TARGET> OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(Supplier<? extends TARGET> theSupplier) {
            return present(d->theSupplier.get());
        }
        public <TARGET> OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(TARGET theValue) {
            return present(()->theValue);
        }
        
        public <TARGET> OptionalSwitchTyped<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, Function<? super SOURCE, ? extends TARGET> theAction) {
            return new OptionalSwitchTyped<SOURCE, TARGET>($value, (Class<TARGET>)null)
                    .present(condition, theAction);
        }
        public <TARGET> OptionalSwitchTyped<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, Supplier<? extends TARGET> theSupplier) {
            return present(condition, d->theSupplier.get());
        }
        public <TARGET> OptionalSwitchTyped<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, TARGET theValue) {
            return present(condition, ()->theValue);
        }
        
    }
    
    public static class OptionalSwitchTyped<SOURCE, TARGET> {
        
        final Optional<SOURCE> $value;
        
        public OptionalSwitchTyped(Optional<SOURCE> source, Class<TARGET> clzz) {
            this.$value = source;
        }
        
        public OptionalSwitchWithoutPresent<SOURCE, TARGET> empty(Supplier<? extends TARGET> theAction) {
            Function<SOURCE, TARGET> $action = null;
            Function<SOURCE, TARGET> oldAction = (Function<SOURCE, TARGET>)$action;
            Function<SOURCE, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value.isPresent())
                    ? (Function<SOURCE, TARGET>)(d -> theAction.get())
                    : oldAction;
            
            return new OptionalSwitchWithoutPresent<SOURCE, TARGET>($value, newAction);
        }
        public OptionalSwitchWithoutPresent<SOURCE, TARGET> none(TARGET theValue) {
            return empty(()->theValue);
        }
        
        public OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(Function<? super SOURCE, ? extends TARGET> theAction) {
            Function<SOURCE, TARGET> $action = null;
            Function<SOURCE, TARGET> oldAction = (Function<SOURCE, TARGET>)$action;
            Function<SOURCE, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value.isPresent())
                    ? (Function<SOURCE, TARGET>)(o -> theAction.apply(o))
                    : oldAction;
            
            return new OptionalSwitchWithoutEmpty<SOURCE, TARGET>($value, newAction);
        }
        public OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(Supplier<? extends TARGET> theSupplier) {
            return present(d->theSupplier.get());
        }
        public OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(TARGET theValue) {
            return present(()->theValue);
        }
        
        public OptionalSwitchTyped<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, Function<? super SOURCE, ? extends TARGET> theAction) {
            Function<SOURCE, TARGET> $action = null;
            Function<SOURCE, TARGET> oldAction = (Function<SOURCE, TARGET>)$action;
            Function<SOURCE, TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value.isPresent() && condition.test($value.get()))
                    ? (Function<SOURCE, TARGET>)(o -> theAction.apply(o))
                    : oldAction;
                    
            return new OptionalSwitchTypedWithAction<SOURCE, TARGET>($value, (Class<TARGET>)null, newAction);
        }
        public OptionalSwitchTyped<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, Supplier<? extends TARGET> theSupplier) {
            return present(condition, d->theSupplier.get());
        }
        public OptionalSwitchTyped<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, TARGET theValue) {
            return present(condition, ()->theValue);
        }
    }
    
    public static class OptionalSwitchTypedWithAction<SOURCE, TARGET> extends OptionalSwitchTyped<SOURCE, TARGET> {
        protected final Function<? super SOURCE, ? extends TARGET> $action;
        public OptionalSwitchTypedWithAction(Optional<SOURCE> source, Class<TARGET> clzz, Function<? super SOURCE, ? extends TARGET> theAction) {
            super(source, clzz);
            this.$action = theAction;
        }
        
        public OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(Function<? super SOURCE, ? extends TARGET> theAction) {
            Function<? super SOURCE, ? extends TARGET> oldAction = (Function<? super SOURCE, ? extends TARGET>)$action;
            Function<? super SOURCE, ? extends TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value.isPresent())
                    ? (Function<SOURCE, TARGET>)(o -> theAction.apply(o))
                    : oldAction;
            
            return new OptionalSwitchWithoutEmpty<SOURCE, TARGET>($value, newAction);
        }
        public OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(Supplier<? extends TARGET> theSupplier) {
            return present(d->theSupplier.get());
        }
        public OptionalSwitchWithoutEmpty<SOURCE, TARGET> present(TARGET theValue) {
            return present(()->theValue);
        }
    }
    
    public static abstract class OptionalSwitchAlmost<SOURCE, TARGET> {
        protected final Optional<SOURCE>                           $value;
        protected final Function<? super SOURCE, ? extends TARGET> $action;
        protected OptionalSwitchAlmost(Optional<SOURCE> theValue, Function<? super SOURCE, ? extends TARGET> theAction) {
            this.$value  = theValue;
            this.$action = theAction;
        }
        
        public TARGET orElse(TARGET elseValue) {
            return (($action != null) && $value.isPresent())
                    ? $action.apply($value.get())
                    : elseValue;
        }
        
        public TARGET orGet(Supplier<? extends TARGET> valueSupplier) {
            if (($action == null) || !$value.isPresent())
                return valueSupplier.get();
            
            val value    = $value.get();
            val newValue = $action.apply(value);
            return newValue;
        }
        public TARGET orElseGet(Supplier<TARGET> valueSupplier) {
            return orGet(valueSupplier);
        }
    }
    
    public static class OptionalSwitchWithoutEmpty<SOURCE, TARGET> extends OptionalSwitchAlmost<SOURCE, TARGET> {
        
        public OptionalSwitchWithoutEmpty(Optional<SOURCE> theValue, Function<? super SOURCE, ? extends TARGET> theAction) {
            super(theValue, theAction);
        }
        
        public TARGET empty(Supplier<? extends TARGET> theAction) {
            Function<? super SOURCE, ? extends TARGET> oldAction = (Function<? super SOURCE, ? extends TARGET>)$action;
            Function<? super SOURCE, ? extends TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    (!$value.isPresent())
                    ? (Function<? super SOURCE, ? extends TARGET>)(d -> theAction.get())
                    : oldAction;
            
            return newAction.apply($value.orElse(null));
        }
        public TARGET empty(TARGET theValue) {
            return empty(()->theValue);
        }
        
    }
    public static class OptionalSwitchWithoutPresent<SOURCE, TARGET> extends OptionalSwitchAlmost<SOURCE, TARGET> {
        
        public OptionalSwitchWithoutPresent(Optional<SOURCE> theValue, Function<? super SOURCE, ? extends TARGET> theAction) {
            super(theValue, theAction);
        }
        
        public TARGET present(Function<? super SOURCE, ? extends TARGET> theAction) {
            Function<? super SOURCE, ? extends TARGET> oldAction = (Function<? super SOURCE, ? extends TARGET>)$action;
            Function<? super SOURCE, ? extends TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value.isPresent())
                    ? (Function<? super SOURCE, ? extends TARGET>)(o -> theAction.apply(o))
                    : oldAction;
                    
            return newAction.apply($value.orElse(null));
        }
        public TARGET present(Supplier<? extends TARGET> theSupplier) {
            return present(d->theSupplier.get());
        }
        public TARGET present(TARGET theValue) {
            return present(()->theValue);
        }
        
        public OptionalSwitchWithoutPresent<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, Function<? super SOURCE, ? extends TARGET> theAction) {
            Function<? super SOURCE, ? extends TARGET> oldAction = (Function<? super SOURCE, ? extends TARGET>)$action;
            Function<? super SOURCE, ? extends TARGET> newAction =
                ($action != null)
                ? oldAction : 
                    ($value.isPresent() && condition.test($value.get()))
                    ? (Function<? super SOURCE, ? extends TARGET>)(o -> theAction.apply(o))
                    : oldAction;
                    
            return new OptionalSwitchWithoutPresent<SOURCE, TARGET>($value, newAction);
        }
        public OptionalSwitchWithoutPresent<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, Supplier<? extends TARGET> theSupplier) {
            return present(condition, d->theSupplier.get());
        }
        public OptionalSwitchWithoutPresent<SOURCE, TARGET> present(Predicate<? super SOURCE> condition, TARGET theValue) {
            return present(condition, ()->theValue);
        }
        
    }
    
}
