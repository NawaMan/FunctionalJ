package functionalj.lens.lenses;

import java.util.function.Function;

import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.types.MayBe;
import lombok.val;

@FunctionalInterface
public interface MayBeLens<HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
                    extends 
                        ObjectLens<HOST, MayBe<TYPE>>,
                        MayBeAccess<HOST, TYPE, SUBLENS> {
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        MayBeLens<HOST, TYPE, SUBLENS> of(
            LensSpec<HOST, MayBe<TYPE>>             nullableLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val read  = nullableLensSpec.getRead();
        val write = nullableLensSpec.getWrite();
        val spec  = LensUtils.createLensSpecParameterized(read, write, subCreator);
        val nullableLens = (MayBeLens<HOST, TYPE, SUBLENS>)()->spec;
        return nullableLens;
    }
    
    public LensSpecParameterized<HOST, MayBe<TYPE>, TYPE, SUBLENS> lensSpecWithSub();

    @Override
    default AccessParameterized<HOST, MayBe<TYPE>, TYPE, SUBLENS> accessWithSub() {
        return lensSpecWithSub();
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<MayBe<TYPE>, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, MayBe<TYPE>> lensSpec() {
        return lensSpecWithSub().getSpec();
    }
    
    @Override
    public default MayBe<TYPE> apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    public default SUBLENS get() {
        WriteLens<HOST, TYPE> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, MayBe.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(MayBe::get), write);
        return lensSpecWithSub().createSubLens(subSpec);
    }
    
}
