package functionalj.lens;

import static functionalj.lens.Lenses.createLensSpecParameterized;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;

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
        val write = optionalLensSpec.getWrite().toBiFunction();
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
    public default Optional<TYPE> apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    public default SUBLENS get() {
        BiFunction<HOST, TYPE, HOST> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, Optional.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(Optional::get), write );
        return lensSpecWithSub().createSubLens(subSpec);
    }
    
}
