package functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;

public interface LensSpecParameterized<HOST, TYPE, SUB, SUBLENS extends AnyAccess<HOST, SUB>>
            extends AccessParameterized<HOST, TYPE, SUB, SUBLENS> {
    
    public LensSpec<HOST, TYPE> getSpec();
    public SUBLENS              createSubLens(LensSpec<HOST, SUB> subSpec);
    
    @Override
    public default SUBLENS createSubAccess(Function<TYPE, SUB> accessToSub) {
        val read = getSpec().getRead().andThen(accessToSub);
        val spec = LensSpec.of(read, null);
        return createSubLens(spec);
    }
    @Override
    public default TYPE apply(HOST host) {
        return getSpec().getRead().apply(host);
    }
    
    public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>> 
        LensSpecParameterized<HOST, TYPE, SUB, SUBLENS> createLensSpecParameterized(
            Function<HOST, TYPE> read,
            BiFunction<HOST, TYPE, HOST> write,
            Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val spec = new LensSpecParameterized<HOST, TYPE, SUB, SUBLENS>() {
            @Override
            public LensSpec<HOST, TYPE> getSpec() {
                return LensSpec.of(read, write);
            }
            @Override
            public SUBLENS createSubLens(LensSpec<HOST, SUB> subSpec) {
                return subCreator.apply(subSpec);
            }
        };
        return spec;
    }
}