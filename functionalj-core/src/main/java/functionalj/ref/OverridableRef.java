package functionalj.ref;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import functionalj.functions.Func0;
import functionalj.list.FuncList;
import functionalj.result.Result;
import lombok.val;

public class OverridableRef<DATA> extends RefOf<DATA> {
    
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
    
    private static final ThreadLocal<Entry> refEntry = ThreadLocal.withInitial(()->new Entry(null, null));
    
    private final Ref<DATA> defaultRef;
    
    public OverridableRef(Ref<DATA> defaultRef) {
        super(defaultRef.getDataType());
        this.defaultRef = defaultRef;
    }
    
    @Override
    protected final Result<DATA> findResult() {
        val entry    = refEntry.get();
        val supplier = entry.findSupplier(this);
        if (supplier != null) {
            val result = Result.from(supplier);
            return result;
        }
        if (defaultRef != null) {
            val result = defaultRef.getResult();
            return result;
        }
        
        return Result.ofNotAvailable();
    }
    
    // TODO - This is need to easily implement cross thread override.
//    public FuncList<Substitution<?>> extractAsSubstitutions(
//            List<Ref<?>> refs) {
//        
//    }
    // TODO - Add priority.
    
    static final <V, E extends Exception> 
            V runWith(List<Substitution<?>> substitutions, ComputeBody<V, E> action) throws E {
        val map = refEntry.get();
        try {
            if (substitutions != null) {
                Entry current = map;
                for (val substitution : substitutions) {
                    if (substitution == null)
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
