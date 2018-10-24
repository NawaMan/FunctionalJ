package functionalj.ref;

import static java.util.Objects.requireNonNull;

import functionalj.functions.Func0;
import functionalj.functions.Func1;
import functionalj.result.AsResult;
import functionalj.result.Result;
import lombok.val;

public abstract class Ref<DATA> implements Func0<DATA>, AsResult<DATA> {
    
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
    
    private final Class<DATA> dataClass;
    
    Ref(Class<DATA> dataClass) {
        this.dataClass = requireNonNull(dataClass);
    }
    
    protected abstract Result<DATA> findResult();
    
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
    
    public final Substitution<DATA> butWith(DATA value) {
        return new Substitution.Value<DATA>(this, value);
    }
    
    public final Substitution<DATA> butFrom(Func0<DATA> supplier) {
        return new Substitution.Supplier<DATA>(this, supplier);
    }
    
    public OverridableRef<DATA> overridable() {
        return new OverridableRef<DATA>(this);
    }
    
    public RetainedRef.Builder<DATA> retained() {
        return new RetainedRef.Builder<DATA>(this);
    }
    
}
