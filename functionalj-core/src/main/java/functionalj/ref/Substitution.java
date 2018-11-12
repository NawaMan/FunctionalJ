package functionalj.ref;

import static java.util.Objects.requireNonNull;

import java.util.List;

import functionalj.function.Func0;
import functionalj.list.FuncList;
import lombok.val;

public abstract class Substitution<DATA> {
    
    public static <D> Substitution<D> of(Ref<D> ref, D value) {
        return new Substitution.Value<D>(ref, false, value);
    }
    public static <D> Substitution<D> from(Ref<D> ref, Func0<D> supplier) {
        return new Substitution.Supplier<D>(ref, false, supplier);
    }
    
    public static final FuncList<Substitution<?>> getCurrentSubstitutions() {
        return Ref.getSubstitutions();
    }
    
    public static final FuncList<Substitution<?>> getCurrentSubstitutionOf(Ref<?> ... refs) {
        val refList = FuncList.of((Ref<?>[])refs);
        return getCurrentSubstitutionOf((List<Ref<?>>)refList);
    }
    
    public static FuncList<Substitution<?>> getCurrentSubstitutionOf(List<Ref<?>> refs) {
        return Ref
                .getSubstitutions()
                .filter(Substitution::ref, refs::contains);
    }
    
    
    private final Ref<DATA> ref;
    private final boolean   isThreadLocal;
    
    protected Substitution(Ref<DATA> ref, boolean isThreadLocal) {
        this.ref = requireNonNull(ref);
        this.isThreadLocal = isThreadLocal;
    }
    
    public final Ref<DATA> ref() {
        return ref;
    }
    
    public final boolean isThreadLocal() {
        return isThreadLocal;
    }
    
    abstract Substitution<DATA> newSubstitution(Ref<DATA> ref, boolean isThreadLocal);
    
    public Substitution<DATA> withinThisThread() {
        return withinThisThread(true);
    }
    public Substitution<DATA> withinThisThread(boolean threadLocal) {
        if (threadLocal == isThreadLocal)
            return this;
        return newSubstitution(ref, threadLocal);
    }
    
    public abstract Func0<DATA> supplier();
    
    //== Sub classes ==
    
    public static class Value<DATA> extends Substitution<DATA> {
        
        private final DATA value;
        
        public Value(Ref<DATA> ref, boolean isThreadLocal, DATA value) {
            super(ref, isThreadLocal);
            this.value = value;
        }
        
        Substitution<DATA> newSubstitution(Ref<DATA> ref, boolean isThreadLocal) {
            return new Substitution.Value<DATA>(ref, isThreadLocal, value);
        }
        
        public final Func0<DATA> supplier() {
            return () -> value;
        }
        
        @Override
        public String toString() {
            return "Value [value=" + value + ", ref()=" + ref() + "]";
        }
        
    }
    
    public static class Supplier<DATA> extends Substitution<DATA> {
        
        private final Func0<DATA> supplier;
        
        public Supplier(Ref<DATA> ref, boolean isThreadLocal, Func0<DATA> supplier) {
            super(ref, isThreadLocal);
            this.supplier = (supplier != null) ? supplier : ()->null;
        }
        
        Substitution<DATA> newSubstitution(Ref<DATA> ref, boolean isThreadLocal) {
            return new Substitution.Supplier<DATA>(ref, isThreadLocal, supplier);
        }
        
        public final Func0<DATA> supplier() {
            return supplier;
        }
        
        @Override
        public String toString() {
            return "Supplier [ref()=" + ref() + "]";
        }
        
    }
    
}
