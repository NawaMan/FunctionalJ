package functionalj.lens.lenses;

import static functionalj.lens.core.LensUtils.createLensSpecParameterized;

import java.util.Optional;
import java.util.function.Function;

import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.WriteLens;
import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface OptionalLens<HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
                    extends 
                        ObjectLens<HOST, Optional<TYPE>>,
                        OptionalAccess<HOST, TYPE, SUBLENS> {
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        OptionalLens<HOST, TYPE, SUBLENS> of(
            LensSpec<HOST, Optional<TYPE>> optionalLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val read  = optionalLensSpec.getRead();
        val write = optionalLensSpec.getWrite();
        val spec  = createLensSpecParameterized(read, write, subCreator);
        val optionalLens = (OptionalLens<HOST, TYPE, SUBLENS>)()->spec;
        return optionalLens;
    }
    
    
    public LensSpecParameterized<HOST, Optional<TYPE>, TYPE, SUBLENS> lensSpecWithSub();

    @Override
    default AccessParameterized<HOST, Optional<TYPE>, TYPE, SUBLENS> accessWithSub() {
        return lensSpecWithSub();
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<Optional<TYPE>, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, Optional<TYPE>> lensSpec() {
        return lensSpecWithSub().getSpec();
    }
    
    @Override
    public default Optional<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }
    
    public default SUBLENS get() {
        WriteLens<HOST, TYPE> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, Optional.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(Optional::get), write);
        return lensSpecWithSub().createSubLens(subSpec);
    }
    
}
