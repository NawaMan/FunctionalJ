package functionalj.lens;

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.Func1;
import functionalj.kinds.Monad;
import functionalj.types.MayBe;

@FunctionalInterface
public interface MayBeAccess<HOST, TYPE> extends Func1<HOST, MayBe<TYPE>> {
    
    public default <TARGET> MayBeAccess<HOST, TARGET> map(Func1<TYPE, TARGET> mapper) {
        return host->(MayBe<TARGET>)this.apply(host).map(mapper);
    }
    
    public default <TARGET> MayBeAccess<HOST, TARGET> flatMap(Func1<TYPE, MayBe<TARGET>> mapper) {
        return host->(MayBe<TARGET>)(((MayBe<TYPE>)
                        this.apply(host))
                        .flatMap((Func1<TYPE,Monad<MayBe<?>,TARGET>>)(Func1)mapper));
    }
    
    public default ObjectAccess<HOST, TYPE> get() {
        return host -> this.apply(host).get();
    }
    
    public default ObjectAccess<HOST, TYPE> or(TYPE fallbackValue) {
        return host -> this.apply(host).or(fallbackValue);
    }
    
    public default ObjectAccess<HOST, TYPE> orGet(Supplier<TYPE> orSupplier) {
        return host -> this.apply(host).orGet(orSupplier);
    }
    
    public default ObjectAccess<HOST, TYPE> orThrow() {
        return host -> this.apply(host).orThrow();
    }
    
    public default BooleanAccess<HOST> isNull() {
        return host -> this.apply(host).isPresent();
    }
    
    public default BooleanAccess<HOST> isNotNull() {
        return host -> !this.apply(host).isPresent();
    }
    
}
