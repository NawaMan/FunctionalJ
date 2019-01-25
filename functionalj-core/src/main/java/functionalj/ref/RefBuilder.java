package functionalj.ref;

import static functionalj.ref.WhenAbsent.UseDefault;

import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.result.Result;
import lombok.val;

public class RefBuilder<DATA> {
    
    private final Class<DATA>    dataClass;
    private final Supplier<DATA> elseSupplier;
    
    RefBuilder(Class<DATA> dataClass) {
        this(dataClass, null);
    }
    RefBuilder(Class<DATA> dataClass, Supplier<DATA> elseSupplier) {
        this.dataClass    = dataClass;
        this.elseSupplier = elseSupplier;
    }
    
    public Ref<DATA> orTypeDefault() {
        return orTypeDefaultOrGet(null);
    }
    public Ref<DATA> orTypeDefaultOrGet(Supplier<DATA> manualDefault) {
        return defaultToTypeDefault()
                .whenAbsentUseDefaultOrGet(manualDefault);
    }
    
    public RefBuilder<DATA> whenAbsentUse(DATA defaultValue) {
        return new RefBuilder<DATA>(dataClass, WhenAbsent.Use(defaultValue));
    }
    public RefBuilder<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
        return new RefBuilder<DATA>(dataClass, WhenAbsent.Get(defaultSupplier));
    }
    public RefBuilder<DATA> whenAbsentUseTypeDefault() {
        return new RefBuilder<DATA>(dataClass, WhenAbsent.UseDefault(dataClass));
    }
    
    public Ref<DATA> defaultToNull() {
        @SuppressWarnings("unchecked")
        val result = (Result<DATA>)Result.ofNull();
        val ref    = new RefOf.FromResult<>(dataClass, result, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultTo(DATA value) {
        val result = Result.valueOf(value);
        val ref    = new RefOf.FromResult<>(dataClass, result, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultFrom(Ref<DATA> anotherRef) {
        val ref = new RefOf.FromRef<DATA>(dataClass, anotherRef, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultFrom(Func0<DATA> supplier) {
        val ref = new RefOf.FromSupplier<DATA>(dataClass, supplier, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultToTypeDefault() {
        return new RefBuilder<DATA>(dataClass, UseDefault(dataClass))
                .defaultFrom(UseDefault(dataClass));
    }
    
    
    public Ref<DATA> dictateToNull() {
        val ref = defaultToNull();
        return ref.dictate();
    }
    public Ref<DATA> dictateTo(DATA value) {
        val ref = defaultTo(value);
        return ref.dictate();
    }
    public Ref<DATA> dictateFrom(Func0<DATA> supplier) {
        val ref = defaultFrom(supplier);
        return ref.dictate();
    }
    
}
