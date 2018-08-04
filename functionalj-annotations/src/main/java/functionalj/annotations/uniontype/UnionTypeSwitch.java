package functionalj.annotations.uniontype;

import java.util.Objects;
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
    
    protected boolean checkEquals(byte a, byte b) {
        return a == b;
    }
    protected boolean checkEquals(short a, short b) {
        return a == b;
    }
    protected boolean checkEquals(int a, int b) {
        return a == b;
    }
    protected boolean checkEquals(long a, long b) {
        return a == b;
    }
    protected boolean checkEquals(float a, float b) {
        return a == b;
    }
    protected boolean checkEquals(double a, double b) {
        return a == b;
    }
    protected boolean checkEquals(boolean a, boolean b) {
        return a == b;
    }
    protected boolean checkEquals(Object a, Object b) {
        return ((a == null) && (b == null)) || Objects.equals(a, b);
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
