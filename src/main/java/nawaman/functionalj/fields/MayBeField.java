package nawaman.functionalj.fields;

import java.util.function.Function;
import java.util.function.Supplier;

import nawaman.functionalj.functions.Func1;
import nawaman.functionalj.kinds.Monad;
import nawaman.functionalj.types.MayBe;

@FunctionalInterface
public interface MayBeField<HOST, TYPE> extends Function<HOST, MayBe<TYPE>> {
    
    public default <TARGET> MayBeField<HOST, TARGET> map(Func1<TYPE, TARGET> mapper) {
        return host->(MayBe<TARGET>)this.apply(host).map(mapper);
    }
    
    public default <TARGET> MayBeField<HOST, TARGET> flatMap(Func1<TYPE, MayBe<TARGET>> mapper) {
        return host->(MayBe<TARGET>)(((MayBe<TYPE>)
                        this.apply(host))
                        .flatMap((Func1<TYPE,Monad<MayBe<?>,TARGET>>)(Func1)mapper));
    }
    
    public default ObjectField<HOST, TYPE> get() {
        return host -> this.apply(host).get();
    }
    
    public default ObjectField<HOST, TYPE> or(TYPE fallbackValue) {
        return host -> this.apply(host).or(fallbackValue);
    }
    
    public default ObjectField<HOST, TYPE> orGet(Supplier<TYPE> orSupplier) {
        return host -> this.apply(host).orGet(orSupplier);
    }
    
    public default ObjectField<HOST, TYPE> orThrow() {
        return host -> this.apply(host).orThrow();
    }
    
    public default BooleanField<HOST> isNull() {
        return host -> this.apply(host).isPresent();
    }
    
    public default BooleanField<HOST> isNotNull() {
        return host -> !this.apply(host).isPresent();
    }
    
}
