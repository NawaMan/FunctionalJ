package functionalj.lens;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface NullableLens<HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
                    extends 
                        ObjectLens<HOST, Nullable<TYPE>>,
                        NullableAccess<HOST, TYPE, SUBLENS> {
    
    public LensSpecParameterized<HOST, Nullable<TYPE>, TYPE, SUBLENS> lensSpecWithSub();

    @Override
    default AccessParameterized<HOST, Nullable<TYPE>, TYPE, SUBLENS> accessWithSub() {
        return lensSpecWithSub();
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<Nullable<TYPE>, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, Nullable<TYPE>> lensSpec() {
        return lensSpecWithSub().getSpec();
    }
    
    @Override
    public default Nullable<TYPE> apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            Function<HOST, Nullable<TYPE>>          read,
            BiFunction<HOST, Nullable<TYPE>, HOST>  write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = LensSpecParameterized.createLensSpecParameterized(read, write, subCreator);
        val nullableLens = (NullableLens<HOST, TYPE, SUBLENS>)()->spec;
        return nullableLens;
    }
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            LensSpec<HOST, Nullable<TYPE>> nullableLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createNullableLens(nullableLensSpec.getRead(), nullableLensSpec.getWrite().toBiFunction(), subCreator);
    }
    
    public default SUBLENS get() {
        BiFunction<HOST, TYPE, HOST> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, Nullable.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(Nullable::get), write );
        return lensSpecWithSub().createSubLens(subSpec);
    }
    
}
