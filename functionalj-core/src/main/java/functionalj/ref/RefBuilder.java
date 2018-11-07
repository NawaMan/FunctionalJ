package functionalj.ref;

import functionalj.function.Func0;
import functionalj.result.Result;
import lombok.val;

public class RefBuilder<DATA> {
    
    private final Class<DATA> dataClass;
    
    public RefBuilder(Class<DATA> dataClass) {
        this.dataClass = dataClass;
    }
    
    public Ref<DATA> defaultToNull() {
        @SuppressWarnings("unchecked")
        val result = (Result<DATA>)Result.ofNull();
        val ref    = new RefOf.FromResult<>(dataClass, result);
        return ref;
    }
    public Ref<DATA> defaultTo(DATA value) {
        val result = Result.of(value);
        val ref    = new RefOf.FromResult<>(dataClass, result);
        return ref;
    }
    public Ref<DATA> defaultFrom(Func0<DATA> supplier) {
        val ref = new RefOf.FromSupplier<DATA>(dataClass, supplier);
        return ref;
    }
    @SuppressWarnings("unchecked")
    public Ref<DATA> defaultToResult(Result<DATA> result) {
        val res = (result != null) ? result : (Result<DATA>)Result.ofNotExist();
        val ref = new RefOf.FromResult<DATA>(dataClass, res);
        return ref;
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
