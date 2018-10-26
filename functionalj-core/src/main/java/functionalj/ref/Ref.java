package functionalj.ref;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.list.FuncList;
import functionalj.result.AsResult;
import functionalj.result.Result;
import lombok.val;

public abstract class Ref<DATA> implements Func0<DATA>, AsResult<DATA> {
    
    private static final ThreadLocal<Entry> refEntry = ThreadLocal.withInitial(()->new Entry(null, null));
    
    public static <D> Ref<D> to(Class<D> dataClass) {
        return new RefTo<D>(dataClass);
    }
    
    public static <D> RefBuilder<D> of(Class<D> dataClass) {
        return new RefBuilder<D>(dataClass);
    }
    
    public static <D> Ref<D> ofValue(D value) {
        @SuppressWarnings("unchecked")
        val dataClass = (Class<D>)value.getClass();
        val result    = Result.of(value);
        val ref       = new RefOf.FromResult<D>(dataClass, result);
        return ref;
    }
    
    public static <D> Ref<D> dictactedTo(D value) {
        val ref = ofValue(value);
        return ref.dictate();
    }
    
    private final Class<DATA> dataClass;
    
    Ref(Class<DATA> dataClass) {
        this.dataClass = requireNonNull(dataClass);
    }
    
    abstract Result<DATA> findResult();
    
    Result<DATA> findOverrideResult() {
        val entry    = refEntry.get();
        val supplier = entry.findSupplier(this);
        if (supplier != null) {
            val result = Result.from(supplier);
            return result;
        }
        
        return null;
    }
    
    @Override
    public final DATA applyUnsafe() throws Exception {
        return getResult().get();
    }
    
    @Override
    public DATA get() {
        return getResult().get();
    }
    
    public final Class<DATA> getDataType() {
        return dataClass;
    }
    
    public final Result<DATA> getResult() {
        val override = findOverrideResult();
        if (override != null)
            return override;
        
        val result = findResult();
        if (result == null)
            return Result.ofNotAvailable();
        
        return result;
    }
    
    @Override
    public final Result<DATA> asResult() {
        return getResult();
    }
    
    public final Func0<DATA> valueSupplier() {
        return ()->{
            val value = value();
            return value;
        };
    }
    
    public final DATA value() {
        val result = getResult();
        val value  = result.value();
        return value;
    }
    public final DATA orElse(DATA elseValue) {
        return getResult().orElse(elseValue);
    }
    public final <TARGET> Func0<TARGET> mapTo(Func1<DATA, TARGET> mapper) {
        return this
                .valueSupplier()
                .mapTo(mapper);
    }
    
    public final <TARGET> Ref<TARGET> map(Class<TARGET> targetClass, Func1<DATA, TARGET> mapper) {
        return Ref.of(targetClass).defaultFrom(()->{
            val result = asResult();
            val target = result.map(mapper);
            return target.get();
        });
    }
    
    // TODO - These methods should not be for DictatedRef ... but I don't know how to gracefully takecare of this.
    public final Substitution<DATA> butWith(DATA value) {
        return new Substitution.Value<DATA>(this, value);
    }
    public final Substitution<DATA> butFrom(Func0<DATA> supplier) {
        return new Substitution.Supplier<DATA>(this, supplier);
    }
    
    public DictatedRef<DATA> dictate() {
        return new DictatedRef<DATA>(this);
    }
    
    public RetainedRef.Builder<DATA> retained() {
        return new RetainedRef.Builder<DATA>(this);
    }
    
    //== Overriability ==
    
    @SuppressWarnings("rawtypes")
    private static class Entry {
        
        private final Entry        parent;
        private final Substitution substitution;
        
        Entry(Entry parent, Substitution substitution) {
            this.parent       = parent;
            this.substitution = substitution;
        }
        
        @SuppressWarnings("unchecked")
        public <D> Func0<D> findSupplier(Ref<D> ref) {
            if (ref == null)
                return null;
            
            if (substitution != null) {
                if (ref.equals(substitution.ref())) {
                    return substitution.supplier();
                }
            }
            
            if (parent == null)
                return null;
            
            return parent.findSupplier(ref);
        }
        
        @Override
        public String toString() {
            return "Entry [parent=" + parent + ", substitution=" + substitution + "]";
        }
        
    }
    
    static final <V, E extends Exception> 
            V runWith(List<Substitution<?>> substitutions, ComputeBody<V, E> action) throws E {
        val map = refEntry.get();
        try {
            if (substitutions != null) {
                Entry current = map;
                for (val substitution : substitutions) {
                    if (substitution == null)
                        continue;
                    if (substitution.ref() instanceof DictatedRef)
                        continue;
                    
                    current = new Entry(current, substitution);
                }
                refEntry.set(current);
            }
            
            return action.compute();
        } finally {
            refEntry.set(map);
        }
    }
    
    static final <V, E extends Exception> 
            void runWith(List<Substitution<?>> substitutions, RunBody<E> action) throws E {
        val map = refEntry.get();
        try {
            if (substitutions != null) {
                for (val substitution : substitutions) {
                    if (substitution == null)
                        continue;
                    if (substitution.ref() instanceof DictatedRef)
                        continue;
                    
                    val newEntry = new Entry(map, substitution);
                    refEntry.set(newEntry);
                }
            }
            
            action.run();
        } finally {
            refEntry.set(map);
        }
    }
    
    static final FuncList<Ref<?>> getRefs() {
        val set = new HashSet<Ref<?>>();
        Entry entry = refEntry.get();
        while ((entry != null) && (entry.substitution != null)) {
            @SuppressWarnings("rawtypes")
            val ref = entry.substitution.ref();
            set.add(ref);
            
            entry = entry.parent;
        }
        return FuncList.from(set);
    }
    
    static final FuncList<Substitution<?>> getSubstitutions() {
        val map = new HashMap<Ref<?>, Substitution<?>>();
        Entry entry = refEntry.get();
        while ((entry != null) && (entry.substitution != null)) {
            @SuppressWarnings("rawtypes")
            val ref = entry.substitution.ref();
            map.putIfAbsent(ref, entry.substitution);
            
            entry = entry.parent;
        }
        return FuncList.from(map.values());
    }
}
