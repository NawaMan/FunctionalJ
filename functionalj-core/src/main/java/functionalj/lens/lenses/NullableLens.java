package functionalj.lens.lenses;

import static functionalj.lens.core.LensUtils.createLensSpecParameterized;

import java.util.function.Function;

import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.WriteLens;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface NullableLens<HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
                    extends 
                        ObjectLens<HOST, Nullable<TYPE>>,
                        NullableAccess<HOST, TYPE, SUBLENS> {
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        NullableLens<HOST, TYPE, SUBLENS> of(
            LensSpec<HOST, Nullable<TYPE>> nullableLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val read  = nullableLensSpec.getRead();
        val write = nullableLensSpec.getWrite();
        val spec  = createLensSpecParameterized(read, write, subCreator);
        val nullableLens = (NullableLens<HOST, TYPE, SUBLENS>)()->spec;
        return nullableLens;
    }
    
    
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
    
    public default SUBLENS get() {
        WriteLens<HOST, TYPE> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, Nullable.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(Nullable::get), write);
        return lensSpecWithSub().createSubLens(subSpec);
    }
    
}
