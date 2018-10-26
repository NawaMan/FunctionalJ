package functionalj.ref;

import functionalj.result.Result;
import lombok.val;
import nawaman.defaultj.api.IProvideDefault;

public class RefTo<DATA> extends Ref<DATA> {
    
    public static final Ref<IProvideDefault> defaultProvider
            = Ref.of(IProvideDefault.class)
            .defaultFrom(IProvideDefault.defaultProvider()::get);
    
    private final int hashCode;
    
    RefTo(Class<DATA> dataClass) {
        super(dataClass);
        hashCode = dataClass.hashCode();
    }
    
    @Override
    protected Result<DATA> findResult() {
        val result = Result.from(()->{
            val provider = defaultProvider.elseGet(IProvideDefault.defaultProvider()::get).get();
            val dataType = getDataType();
            val value    = provider.get(dataType);
            return (DATA)value;
        });
        return result;
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
