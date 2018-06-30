package functionalj.lens.core;

import java.util.function.Function;

import functionalj.lens.lenses.AnyLens;
import lombok.val;

public interface LensSpecParameterized2<HOST, TYPE, SUB1, SUB2,
                                         SUBLENS1 extends AnyLens<HOST, SUB1>,
                                         SUBLENS2 extends AnyLens<HOST, SUB2>>
            extends AccessParameterized2<HOST, TYPE, SUB1, SUB2, SUBLENS1, SUBLENS2> {
    
    public LensSpec<HOST, TYPE> getSpec();
    public SUBLENS1             createSubLens1(LensSpec<HOST, SUB1> subSpec);
    public SUBLENS2             createSubLens2(LensSpec<HOST, SUB2> subSpec);
    
    @Override
    public default TYPE apply(HOST host) {
        return getSpec().getRead().apply(host);
    }
    
    @Override
    public default SUBLENS1 createSubAccess1(Function<TYPE, SUB1> accessToSub) {
        val read = getSpec().getRead().andThen(accessToSub);
        val spec = LensSpec.of(read);
        return createSubLens1(spec);
    }

    @Override
    public default SUBLENS2 createSubAccess2(Function<TYPE, SUB2> accessToSub) {
        val read = getSpec().getRead().andThen(accessToSub);
        val spec = LensSpec.of(read);
        return createSubLens2(spec);
    }

    @Override
    public default SUBLENS1 createSubAccessFromHost1(Function<HOST, SUB1> accessToParameter) {
        return createSubLens1(LensSpec.of(accessToParameter));
    }

    @Override
    public default SUBLENS2 createSubAccessFromHost2(Function<HOST, SUB2> accessToParameter) {
        return createSubLens2(LensSpec.of(accessToParameter));
    }
    
}