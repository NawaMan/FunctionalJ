package functionalj.ref;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Supplier;

import functionalj.result.Result;
import lombok.val;
import nawaman.defaultj.api.IProvideDefault;

public class RefTo<DATA> extends Ref<DATA> {
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Default {
        
    }
    
    public static final Ref<IProvideDefault> defaultProvider
            = Ref.of      (IProvideDefault.class)
            .whenAbsentGet(IProvideDefault.defaultProvider()::get)
            .defaultFrom  (IProvideDefault.defaultProvider()::get);
    
    private final int hashCode;
    
    RefTo(Class<DATA> dataClass) {
        super(dataClass, null);
        hashCode = dataClass.hashCode();
    }
    
    @Override
    protected Result<DATA> findResult() {
        val result = Result.from(()->{
            val provider = defaultProvider.value();
            val dataType = getDataType();
            val value    = provider.get(dataType);
            return (DATA)value;
        });
        return result;
    }
    
    public Ref<DATA> whenAbsentUse(DATA defaultValue) {
        // No effect
        return this;
    }
    public Ref<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
        // No effect
        return this;
    }
    public Ref<DATA> whenAbsentUseDefault() {
        // No effect
        return this;
    }
    
    public final int hashCode() {
        return hashCode;
    }
    
    public final boolean equals(Object another) {
        if (this == another)
            return true;
        if (another == null)
            return false;
        if (!(another instanceof RefOf))
            return false;
        
        @SuppressWarnings("unchecked")
        val anotherRef = (RefOf<DATA>)another;
        if (!anotherRef.getDataType().equals(this.getDataType()))
            return false;
        
        return this.hashCode() == another.hashCode();
    }
    
}
