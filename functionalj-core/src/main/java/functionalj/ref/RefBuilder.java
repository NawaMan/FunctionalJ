package functionalj.ref;

import static functionalj.ref.Else.ElseDefault;
import static functionalj.ref.Else.ElseGet;
import static functionalj.ref.Else.ElseUse;

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
    
    public RefBuilder<DATA> whenAbsentUse(DATA defaultValue) {
        return new RefBuilder<DATA>(dataClass, ElseUse(defaultValue));
    }
    public RefBuilder<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
        return new RefBuilder<DATA>(dataClass, ElseGet(defaultSupplier));
    }
    public RefBuilder<DATA> whenAbsentUseDefault() {
        return new RefBuilder<DATA>(dataClass, ElseDefault(dataClass));
    }
    
    public Ref<DATA> defaultToNull() {
        @SuppressWarnings("unchecked")
        val result = (Result<DATA>)Result.ofNull();
        val ref    = new RefOf.FromResult<>(dataClass, result, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultTo(DATA value) {
        val result = Result.of(value);
        val ref    = new RefOf.FromResult<>(dataClass, result, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultFrom(Func0<DATA> supplier) {
        val ref = new RefOf.FromSupplier<DATA>(dataClass, supplier, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultToResult(Result<DATA> result) {
        val ref = new RefOf.FromResult<DATA>(dataClass, result, elseSupplier);
        return ref;
    }
    public Ref<DATA> defaultToDefault() {
        return new RefBuilder<DATA>(dataClass, ElseDefault(dataClass))
                .defaultFrom(ElseDefault(dataClass));
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
    public Ref<DATA> dictateToResult(Result<DATA> result) {
        val ref = defaultToResult(result);
        return ref.dictate();
    }
    
}
