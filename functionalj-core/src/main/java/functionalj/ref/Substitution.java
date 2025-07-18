// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Objects.requireNonNull;
import java.util.List;
import functionalj.function.Func0;
import functionalj.function.Traced;
import functionalj.list.FuncList;
import functionalj.supportive.CallerId;
import lombok.val;

// TODO - Add a wrapper for various function so the call to it will use the substitution.
public abstract class Substitution<DATA> {
    
    public static enum Scope {
        localThread,
        allThread
    }
    
    public static <D> Substitution<D> of(Ref<D> ref, D value) {
        return new Substitution.Value<D>(
                CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")",
                ref,
                Scope.allThread, value);
    }
    
    public static <D> Substitution<D> of(String name, Ref<D> ref, D value) {
        return new Substitution.Value<D>(
                (name != null) 
                    ? name 
                    : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")"),
                ref, 
                Scope.allThread, 
                value);
    }
    
    public static <D> Substitution<D> from(Ref<D> ref, Func0<D> supplier) {
        return new Substitution.Supplier<D>(
                CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")",
                ref,
                Scope.allThread,
                supplier);
    }
    
    public static <D> Substitution<D> from(String name, Ref<D> ref, Func0<D> supplier) {
        return new Substitution.Supplier<D>(
                (name != null) 
                    ? name 
                    : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")"),
                ref, 
                Scope.allThread, 
                supplier
                );
    }
    
    public static final FuncList<Substitution<?>> currentSubstitutions() {
    	return getCurrentSubstitutions();
    }
    
    public static final FuncList<Substitution<?>> allThreadSubstitutions() {
    	return getCurrentSubstitutions()
    			.exclude(Substitution::isThreadLocal);
    }
    
    public static final FuncList<Substitution<?>> getCurrentSubstitutions() {
        return Ref.getSubstitutions();
    }
    
    public static final FuncList<Substitution<?>> getCurrentSubstitutionOf(Ref<?>... refs) {
        val refList = FuncList.of((Ref<?>[]) refs);
        return getCurrentSubstitutionOf((List<Ref<?>>) refList);
    }
    
    public static FuncList<Substitution<?>> getCurrentSubstitutionOf(List<Ref<?>> refs) {
        return Ref.getSubstitutions().filter(Substitution::ref, refs::contains).toFuncList();
    }
    
    private final Ref<DATA> ref;
    
    private final Scope scope;
    
    private final String toString;
    
    protected Substitution(String toString, Ref<DATA> ref, Scope scope) {
        this.toString = (toString != null) ? toString : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")");
        this.ref      = requireNonNull(ref);
        this.scope    = scope;
    }
    
    public final Ref<DATA> ref() {
        return ref;
    }
    
    public final boolean isThreadLocal() {
        return scope == Scope.localThread;
    }
    
    abstract Substitution<DATA> newSubstitution(Ref<DATA> ref, Scope scope);
    
    public Substitution<DATA> withinThisThread() {
        return withinThisThread(Scope.localThread);
    }
    
    public Substitution<DATA> withinThisThread(Scope scope) {
        if (this.scope == scope)
            return this;
        return newSubstitution(ref, scope);
    }
    
    public abstract Func0<DATA> supplier();
    
    @Override
    public String toString() {
        return toString;
    }
    
    // == Sub classes ==
    
    public static class Value<DATA> extends Substitution<DATA> {
        
        private final DATA value;
        
        public Value(Ref<DATA> ref, Scope scope, DATA value) {
            super(CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")",
                  ref,
                  scope);
            this.value = value;
        }
        
        public Value(String name, Ref<DATA> ref, Scope scope, DATA value) {
            super((name != null) 
                    ? name 
                    : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")"), 
                  ref,
                  scope);
            this.value = value;
        }
        
        Substitution<DATA> newSubstitution(Ref<DATA> ref, Scope scope) {
            return new Substitution.Value<DATA>((CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")"), ref, scope, value);
        }
        
        Substitution<DATA> newSubstitution(String name, Ref<DATA> ref, Scope scope) {
            return new Substitution.Value<DATA>((name != null) ? name : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")"), ref, scope, value);
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
        
        public Supplier(Ref<DATA> ref, Scope scope, Func0<DATA> supplier) {
            super(CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")", 
                  ref, 
                  scope);
            this.supplier = (supplier != null) ? supplier : () -> null;
        }
        
        public Supplier(String name, Ref<DATA> ref, Scope scope, Func0<DATA> supplier) {
            super((name != null) 
                    ? name 
                    : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")"), 
                  ref, 
                  scope);
            this.supplier = (supplier != null) ? supplier : () -> null;
        }
        
        Substitution<DATA> newSubstitution(Ref<DATA> ref, Scope scope) {
            return new Substitution.Supplier<DATA>(CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")", ref, scope, supplier);
        }
        
        Substitution<DATA> newSubstitution(String name, Ref<DATA> ref, Scope scope) {
            return new Substitution.Supplier<DATA>((name != null) ? name : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + ref + ")"), ref, scope, supplier);
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
