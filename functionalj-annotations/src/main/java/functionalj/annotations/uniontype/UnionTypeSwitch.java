package functionalj.annotations.uniontype;

import java.util.function.Function;
import java.util.function.Supplier;

import lombok.val;

public abstract class UnionTypeSwitch<D, T> {
    protected final D                      value;
    protected final Function<? super D, T> action;
    protected UnionTypeSwitch(D value, Function<? super D, T> action) {
        this.value  = value;
        this.action = action;
    }
    
    public T orElse(T elseValue) {
        return (action != null)
                ? action.apply(value)
                : elseValue;
    }
    
    public T orElseGet(Supplier<T> valueSupplier) {
        return (action != null)
                ? action.apply(value)
                : valueSupplier.get();
    }
    public T orElseGet(Function<? super D, T> valueMapper) {
        @SuppressWarnings("unchecked")
        val newAction = (Function<? super D, T>)((action != null) ? action : valueMapper);
        return newAction.apply(value);
    }
    
    
    public static class Data<D, T> {
        protected final D              value;
        protected final Function<D, T> action;
        
        public Data(D value) {
            this(value, null);
        }
        public Data(D value, Function<D, T> action) {
            this.value  = value;
            this.action = action;
        }
        public D              value()  { return value; }
        public Function<D, T> action() { return action; }
        public Data<D, T>     withValue(D value)                { return new Data<>(value, action); }
        public Data<D, T>     withAction(Function<D, T> action) { return new Data<>(value, action); }
    }
}
