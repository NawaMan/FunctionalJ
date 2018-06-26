package functionalj.lens;

import java.util.function.Function;

import lombok.val;

public interface LensSpecParameterized<HOST, TYPE, SUB, SUBLENS extends Lens<HOST, SUB>>
            extends AccessParameterized<HOST, TYPE, SUB, SUBLENS> {
    
    public LensSpec<HOST, TYPE> getSpec();
    public SUBLENS              createSubLens(LensSpec<HOST, SUB> subSpec);
    
    @Override
    public default TYPE apply(HOST host) {
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