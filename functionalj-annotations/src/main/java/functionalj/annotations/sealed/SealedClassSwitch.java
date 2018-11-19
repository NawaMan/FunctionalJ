package functionalj.annotations.sealed;

import java.util.function.Function;
import java.util.function.Supplier;

import lombok.val;

public abstract class SealedClassSwitch<D, T> {
    protected final D                      $value;
    protected final Function<? super D, T> $action;
    protected SealedClassSwitch(D theValue, Function<? super D, T> theAction) {
        this.$value  = theValue;
        this.$action = theAction;
    }
    
    public T orElse(T elseValue) {
        return ($action != null)
                ? $action.apply($value)
                : elseValue;
    }
    
    public T orGet(Supplier<T> valueSupplier) {
        return ($action != null)
                ? $action.apply($value)
                : valueSupplier.get();
    }
    public T orGet(Function<? super D, T> valueMapper) {
        @SuppressWarnings("unchecked")
        val newAction = (Function<? super D, T>)(($action != null) ? $action : valueMapper);
        return newAction.apply($value);
    }
    public T orElseGet(Supplier<T> valueSupplier) {
        return orGet(valueSupplier);
    }
    public T orElseGet(Function<? super D, T> valueMapper) {
        return orGet(valueMapper);
    }
    
    public static class SealedClassSwitchData<D, T> {
        protected final D              value;
        protected final Function<D, T> action;
        
        public SealedClassSwitchData(D value) {
            this(value, null);
        }
        public SealedClassSwitchData(D value, Function<D, T> action) {
            this.value  = value;
            this.action = action;
        }
        public D                         value()                           { return value; }
        public Function<D, T>            action()                          { return action; }
        public SealedClassSwitchData<D, T> withValue(D value)                { return new SealedClassSwitchData<>(value, action); }
        public SealedClassSwitchData<D, T> withAction(Function<D, T> action) { return new SealedClassSwitchData<>(value, action); }
    }
}
