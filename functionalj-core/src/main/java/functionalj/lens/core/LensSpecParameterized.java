package functionalj.lens.core;

import java.util.function.Function;

import functionalj.lens.lenses.AnyLens;
import lombok.val;

@SuppressWarnings("javadoc")
public interface LensSpecParameterized<HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>>
            extends AccessParameterized<HOST, TYPE, SUB, SUBLENS> {
    
    public LensSpec<HOST, TYPE> getSpec();
    public SUBLENS              createSubLens(LensSpec<HOST, SUB> subSpec);
    
    @Override
    public default TYPE applyUnsafe(HOST host) throws Exception {
        return getSpec().getRead().apply(host);
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<TYPE, SUB> accessToSub) {
        val read = getSpec().getRead().andThen(accessToSub);
        val spec = LensSpec.of(read);
        return createSubLens(spec);
    }
    
    @Override
    public default SUBLENS createSubAccessFromHost(Function<HOST, SUB> accessToParameter) {
        return createSubLens(LensSpec.of(accessToParameter));
    }
}