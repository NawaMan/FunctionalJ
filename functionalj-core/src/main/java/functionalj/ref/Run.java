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
package functionalj.ref;

import java.util.List;

import functionalj.environments.AsyncRunner;
import functionalj.function.Func;
import functionalj.list.FuncList;
import functionalj.promise.Promise;
import lombok.val;


public class Run {
    
    // TODO - Don't like this duplicate but let's fix that later.
    
    @SafeVarargs
    public static SyncRunInstance With(Substitution<?> ... substitutions) {
        return new SyncRunInstance(FuncList.of(substitutions));
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
        val currentSubstitutions = Substitution.getCurrentSubstitutions().excludeIn(FuncList.of(excludedSubstitutions));
        return With(currentSubstitutions);
    }
    public static SyncRunInstance WithAll() {
        val currentSubstitutions = Substitution.getCurrentSubstitutions();
        return With(currentSubstitutions);
    }
    
    public static final SyncRunInstance Synchronously = new SyncRunInstance();
    public static final SyncRunInstance synchronously = Synchronously;
    public static final SyncRunInstance OnSameThread  = Synchronously;
    public static final SyncRunInstance onSameThread  = Synchronously;
    
    public static final AsyncRunInstance Asynchronously  = new AsyncRunInstance();
    public static final AsyncRunInstance asynchronously  = Asynchronously;
    public static final AsyncRunInstance OnAnotherThread = Asynchronously;
    public static final AsyncRunInstance onAnotherThread = Asynchronously;
    
    
    public static <E extends Exception> Promise<Object> async(RunBody<E> action) throws E {
        return AsyncRunner.run(()->{
            Ref.runWith(FuncList.empty(), action);
        });
    }
    public static <V, E extends Exception> Promise<V> async(ComputeBody<V, E> action) throws E {
        return AsyncRunner.run(()->{
            return Ref.runWith(FuncList.empty(), action);
        });
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
        
        public AsyncRunInstance asynchronously() {
            if (this instanceof AsyncRunInstance)
                return (AsyncRunInstance)this;
            
            return new AsyncRunInstance(substitutions);
        }
        public AsyncRunInstance onAnotherThread() {
            return asynchronously();
        }
        
        public R with(Substitution<?> ... newSubstitutions) {
            val substitutions = Func.listOf(newSubstitutions);
            return with(substitutions);
        }
        public abstract R with(List<Substitution<?>> newSubstitutions);
        
        public R and(Substitution<?> ... newSubstitutions) {
            return and(FuncList.of(newSubstitutions));
        }
        public R and(List<Substitution<?>> newSubstitutions) {
            return with(newSubstitutions);
        }
        
    }
    
    public static class SyncRunInstance extends RunInstance<SyncRunInstance> {
        
        SyncRunInstance() {
            super();
        }
        SyncRunInstance(List<Substitution<?>> substitutions) {
            super(substitutions);
        }
        
        public SyncRunInstance with(List<Substitution<?>> newSubstitutions) {
            val substitutions = this.substitutions().appendAll(newSubstitutions);
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
    }
    public static class AsyncRunInstance extends RunInstance<AsyncRunInstance> {
        
        AsyncRunInstance() {
            super();
        }
        AsyncRunInstance(List<Substitution<?>> substitutions) {
            super(substitutions);
        }
        
        public AsyncRunInstance with(List<Substitution<?>> newSubstitutions) {
            val substitutions = this.substitutions().appendAll(newSubstitutions);
            return new AsyncRunInstance(substitutions);
        }
        
        public <E extends Exception> Promise<Object> run(RunBody<E> action) throws E {
            val substitutions = substitutions();
            return AsyncRunner.run(()->{
                Ref.runWith(substitutions, action);
            });
        }
        public <V, E extends Exception> Promise<V> run(ComputeBody<V, E> action) throws E {
            val substitutions = substitutions();
            return AsyncRunner.run(()->{
                return Ref.runWith(substitutions, action);
            });
        }
    }
    
}
