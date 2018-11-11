package functionalj.ref;

import java.util.Collection;
import java.util.List;

import functionalj.environments.AsyncRunner;
import functionalj.list.FuncList;
import functionalj.promise.Promise;
import lombok.val;

public class Run {
    
    // TODO - Don't like this duplicate but let's fix that later.
    
    @SafeVarargs
    public static SyncRunInstance With(Substitution<?> ... allSubstitutions) {
        val substitutions = new SyncRunInstance().substitutions().appendAll(allSubstitutions);
        return new SyncRunInstance(substitutions);
    }
    @SafeVarargs
    public static SyncRunInstance with(Substitution<?> ... substitutions) {
        return With(substitutions);
    }
    public static SyncRunInstance With(List<Substitution<?>> substitutions) {
        return new SyncRunInstance(substitutions);
    }
    public static SyncRunInstance with(List<Substitution<?>> substitutions) {
        return With(substitutions);
    }
    public static SyncRunInstance withAllExcept(Substitution<?> ... excludedSubstitutions) {
        val currentSubstitutions = Run.getCurrentSubstitutions().excludeIn(FuncList.of(excludedSubstitutions));
        return With(currentSubstitutions);
    }
    public static SyncRunInstance WithAll() {
        val currentSubstitutions = Run.getCurrentSubstitutions();
        return With(currentSubstitutions);
    }
    
    public static final FuncList<Ref<?>> getCurrentRefs() {
        return Ref.getRefs();
    }
    
    public static final FuncList<Substitution<?>> getCurrentSubstitutions() {
        return Ref.getSubstitutions();
    }
    
    public static final FuncList<Substitution<?>> getCurrentSubstitutions(Ref<?> ... refs) {
        val refList = FuncList.of((Ref<?>[])refs);
        return getCurrentSubstitutions((List<Ref<?>>)refList);
    }
    
    public static FuncList<Substitution<?>> getCurrentSubstitutions(List<Ref<?>> refs) {
        return Ref
                .getSubstitutions()
                .filter(Substitution::ref, refs::contains);
    }
    
    public static abstract class RunInstance<R extends RunInstance<?>> {
    
        private final FuncList<Substitution<?>> substitutions;
        
        RunInstance() {
            this.substitutions = FuncList.empty();
        }
        RunInstance(List<Substitution<?>> substitutions) {
            this.substitutions = FuncList.from(substitutions);
        }
        
        public FuncList<Substitution<?>> substitutions() {
            return this.substitutions;
        }
        
        public SyncRunInstance synchronously() {
            if (this instanceof SyncRunInstance)
                return (SyncRunInstance)this;
            
            return new SyncRunInstance(substitutions);
        }
        public SyncRunInstance onSameThread() {
            return synchronously();
        }
        
        public abstract R with(Substitution<?> ... newSubstitutions);
        public abstract R with(List<Substitution<?>> newSubstitutions);
        
        public R and(Substitution<?> ... newSubstitutions) {
            return with(newSubstitutions);
        }
        public R and(List<Substitution<?>> newSubstitutions) {
            return with(newSubstitutions);
        }
        
        public abstract R from(Ref<?> ... newRefs);
        public abstract R from(List<Ref<?>> newRefs);
        
    }
    
    public static class SyncRunInstance extends RunInstance<SyncRunInstance> {
        
        SyncRunInstance() {
            super();
        }
        SyncRunInstance(List<Substitution<?>> substitutions) {
            super(substitutions);
        }
        
        public SyncRunInstance with(Substitution<?> ... newSubstitutions) {
            val substitutions = this.substitutions().appendAll(newSubstitutions);
            return new SyncRunInstance(substitutions);
        }
        public SyncRunInstance with(List<Substitution<?>> newSubstitutions) {
            val substitutions = this.substitutions().appendAll(newSubstitutions);
            return new SyncRunInstance(substitutions);
        }
        
        public SyncRunInstance from(Ref<?> ... newRefs) {
            // TODO - Find out why this is ambiguous.
            val newSubstitutions = (Collection<Substitution<?>>)Run.getCurrentSubstitutions(newRefs);
            val substitutions    = this.substitutions().appendAll(newSubstitutions);
            return new SyncRunInstance(substitutions);
        }
        public SyncRunInstance from(List<Ref<?>> newRefs) {
            // TODO - Find out why this is ambiguous.
            val newSubstitutions = (Collection<Substitution<?>>)Run.getCurrentSubstitutions(newRefs);
            val substitutions    = this.substitutions().appendAll(newSubstitutions);
            return new SyncRunInstance(substitutions);
        }
        
        public <E extends Exception> void run(RunBody<E> action) throws E {
            val substitutions = substitutions();
            Ref.runWith(substitutions, action);
        }
        public <V, E extends Exception> V run(ComputeBody<V, E> action) throws E {
            val substitutions = substitutions();
            return Ref.runWith(substitutions, action);
        }
        
        public <E extends Exception> Promise<Object> runAsync(RunBody<E> action) throws E {
            val substitutions = substitutions();
            return AsyncRunner.run(()->{
                Run.with(substitutions).run(action);
            });
        }
        public <V, E extends Exception> Promise<V> runAsync(ComputeBody<V, E> action) throws E {
            val substitutions = substitutions();
            return AsyncRunner.run(()->{
                return Run.with(substitutions).run(action);
            });
        }
    }
    
}
