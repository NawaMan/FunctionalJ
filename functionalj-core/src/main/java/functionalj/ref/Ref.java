// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.ref.Substitution.Scope.allThread;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Traced;
import functionalj.list.FuncList;
import functionalj.result.Result;
import functionalj.supportive.CallerId;
import lombok.val;

public abstract class Ref<DATA> {
    
    private static final ThreadLocal<Entry> refEntry = ThreadLocal.withInitial(() -> new Entry(null, null));
    
    public static <D> Ref<D> to(String name, Class<D> dataClass) {
        return new RefTo<D>(name, dataClass);
    }
    
    public static <D> Ref<D> to(Class<D> dataClass) {
        val location = CallerId.instance.trace(Traced::extractLocationString) + ":" + "Ref<" + Utils.name(dataClass) + ">";
        return new RefTo<D>(location, dataClass);
    }
    
    public static <D> RefBuilder<D> of(String name, Class<D> dataClass) {
        return new RefBuilder<D>(name, dataClass);
    }
    
    public static <D> RefBuilder<D> of(Class<D> dataClass) {
        val location = CallerId.instance.trace(Traced::extractLocationString) + ":" + "Ref<" + Utils.name(dataClass) + ">";
        return new RefBuilder<D>(location, dataClass);
    }
    
    @SuppressWarnings("unchecked")
    public static <D> Ref<D> ofValue(String name, D value) {
        val dataClass = (Class<D>) value.getClass();
        val result    = Result.valueOf(value);
        val ref       = new RefOf.FromResult<D>(name, dataClass, result, null);
        return ref;
    }
    
    @SuppressWarnings("unchecked")
    public static <D> Ref<D> ofValue(D value) {
        val dataClass = (Class<D>) value.getClass();
        val location  = CallerId.instance.trace(Traced::extractLocationString) + ":" + "Ref<" + Utils.name(dataClass) + ">";
        val result    = Result.valueOf(value);
        val ref       = new RefOf.FromResult<D>(location, dataClass, result, null);
        return ref;
    }
    
    @SuppressWarnings("unchecked")
    public static <D> Ref<D> dictactedTo(D value) {
        val dataClass = (Class<D>) value.getClass();
        val location  = CallerId.instance.trace(Traced::extractLocationString) + ":" + "Ref<" + Utils.name(dataClass) + ">";
        val result    = Result.valueOf(value);
        val ref       = new RefOf.FromResult<D>(location, dataClass, result, null);
        return ref.dictate();
    }
    
    final String toString;
    
    final Class<DATA> dataClass;
    
    final Supplier<DATA> whenAbsentSupplier;
    
    Ref(String toString, Class<DATA> dataClass, Supplier<DATA> whenAbsentSupplier) {
        this.toString  = toString;
        this.dataClass = requireNonNull(dataClass);
        this.whenAbsentSupplier = whenAbsentSupplier;
    }
    
    abstract Result<DATA> findResult();
    
    Result<DATA> findOverrideResult() {
        val entry = refEntry.get();
        val supplier = entry.findSupplier(this);
        if (supplier != null) {
            val result = Result.of(supplier);
            return result;
        }
        return null;
    }
    
    public DATA get() {
        return getResult().get();
    }
    
    public final Class<DATA> getDataType() {
        return dataClass;
    }
    
    final Supplier<DATA> getElseSupplier() {
        return whenAbsentSupplier;
    }
    
    public final Result<DATA> getResult() {
        val override = findOverrideResult();
        if (override != null) {
            if (override.isPresent() || (whenAbsentSupplier == null))
                return override;
            if (!override.isPresent() && (whenAbsentSupplier != null)) {
                val elseValue = whenAbsentSupplier.get();
                if (elseValue != null)
                    return Result.valueOf(elseValue);
                else
                    return Result.ofNotExist();
            }
        }
        val result = findResult();
        if (result != null) {
            if (result.isPresent() || (whenAbsentSupplier == null))
                return result;
            if (!result.isPresent() && (whenAbsentSupplier != null)) {
                val elseValue = Result.from(whenAbsentSupplier);
                if (elseValue.isPresent())
                    return elseValue;
                else
                    return Result.ofNotExist();
            }
        }
        if (whenAbsentSupplier == null)
            return Result.ofNotExist();
        val elseValue = whenAbsentSupplier.get();
        if (elseValue == null)
            return Result.ofNotExist();
        return Result.valueOf(elseValue);
    }
    
    public final Result<DATA> asResult() {
        return getResult();
    }
    
    public final Optional<DATA> asOptional() {
        return getResult().toOptional();
    }
    
    public final Func0<DATA> valueSupplier() {
        return () -> {
            val value = value();
            return value;
        };
    }
    
    public final DATA value() {
        val result = getResult();
        val value = result.value();
        return value;
    }
    
    public final DATA orElse(DATA elseValue) {
        return getResult().orElse(elseValue);
    }
    
    public final DATA orGet(Supplier<DATA> elseValue) {
        return getResult().orElseGet(elseValue);
    }
    
    public final DATA orElseGet(Supplier<DATA> elseValue) {
        return getResult().orElseGet(elseValue);
    }
    
    public final <TARGET> Func0<TARGET> then(Func1<DATA, TARGET> mapper) {
        return this.valueSupplier().then(mapper);
    }
    
    // Map to ...
    public final <TARGET> Ref<TARGET> map(Class<TARGET> targetClass, Func1<DATA, TARGET> mapper) {
        return Ref.of(targetClass).defaultFrom(() -> {
            val result = getResult();
            val target = result.map(mapper);
            return target.get();
        });
    }
    
    // -- Overriding --
    // TODO - These methods should not be in DictatedRef ... but I don't know how to gracefully takecare of this.
    
    public final Substitution<DATA> butWith(DATA value) {
        return new Substitution.Value<DATA>(
                CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + this + ")", 
                this, 
                allThread, 
                value);
    }
    
    public final Substitution<DATA> butWith(String name, DATA value) {
        return new Substitution.Value<DATA>(
                (name != null)
                    ? name
                    : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + this + ")"), 
                this, 
                allThread, 
                value);
    }
    
    public final Substitution<DATA> butFrom(Func0<DATA> supplier) {
        return new Substitution.Supplier<DATA>(
                CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + this + ")", 
                this, 
                allThread, 
                supplier);
    }
    
    public final Substitution<DATA> butFrom(String name, Func0<DATA> supplier) {
        return new Substitution.Supplier<DATA>(
                (name != null) 
                    ? name 
                    : (CallerId.instance.trace(Traced::extractLocationString) + ":" + "Substitution(" + this + ")"), 
                this, 
                allThread, 
                supplier);
    }
    
    abstract Ref<DATA> whenAbsent(Func0<DATA> whenAbsent);
    
    // These else method has no effect once the Ref become DictatedRef
    // They also has no effect for RefTo.
    public Ref<DATA> whenAbsentUse(DATA defaultValue) {
        return whenAbsent(WhenAbsent.Use(defaultValue));
    }
    
    public Ref<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
        return whenAbsent(WhenAbsent.Get(defaultSupplier));
    }
    
    public Ref<DATA> whenAbsentReferTo(Ref<DATA> sourceRef) {
        return whenAbsent(WhenAbsent.Get(sourceRef::get));
    }
    
    public Ref<DATA> whenAbsentUseDefault() {
        return whenAbsentUseDefaultOrGet(null);
    }
    
    public Ref<DATA> whenAbsentUseDefaultOrGet(Supplier<DATA> manualDefault) {
        Func0<DATA> useDefault = WhenAbsent.UseDefault(getDataType());
        if (manualDefault != null)
            useDefault = useDefault.whenAbsentGet(manualDefault);
        return whenAbsent(useDefault);
    }
    
    public DictatedRef<DATA> dictate() {
        return new DictatedRef<DATA>(toString, this);
    }
    
    public RetainedRef.Builder<DATA> retained() {
        return new RetainedRef.Builder<DATA>(this, true);
    }
    
    @Override
    public String toString() {
        return toString;
    }
    
    // == Overriability ==
    @SuppressWarnings("rawtypes")
    private static class Entry {
        
        private final Entry parent;
        
        private final Substitution substitution;
        
        Entry(Entry parent, Substitution substitution) {
            this.parent = parent;
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
    
    static final <V, E extends Exception> V runWith(List<Substitution<?>> substitutions, ComputeBody<V, E> action) throws E {
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
    
    static final <V, E extends Exception> void runWith(List<Substitution<?>> substitutions, RunBody<E> action) throws E {
        val map = refEntry.get();
        try {
            if (substitutions != null) {
                Entry currentEntry = map;
                for (val substitution : substitutions) {
                    if (substitution == null)
                        continue;
                    if (substitution.ref() instanceof DictatedRef)
                        continue;
                    val newEntry = new Entry(currentEntry, substitution);
                    refEntry.set(newEntry);
                    currentEntry = newEntry;
                }
            }
            action.run();
        } finally {
            refEntry.set(map);
        }
    }
    
    public static final FuncList<Ref<?>> getCurrentRefs() {
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
