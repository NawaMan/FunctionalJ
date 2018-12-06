package functionalj.lens.lenses;

import static functionalj.lens.core.LensUtils.createLensSpecParameterized;

import java.util.function.Function;

import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.WriteLens;
import functionalj.result.Result;
import lombok.val;

@FunctionalInterface
public interface ResultLens<HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        extends 
            ObjectLens<HOST, Result<TYPE>>,
            ResultAccess<HOST, TYPE, SUBLENS> {
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        ResultLens<HOST, TYPE, SUBLENS> of(
            LensSpec<HOST, Result<TYPE>> resultLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val read  = resultLensSpec.getRead();
        val write = resultLensSpec.getWrite();
        val spec  = createLensSpecParameterized(read, write, subCreator);
        val nullableLens = (ResultLens<HOST, TYPE, SUBLENS>)()->spec;
        return nullableLens;
    }
    
    
    public LensSpecParameterized<HOST, Result<TYPE>, TYPE, SUBLENS> lensSpecWithSub();
    
    @Override
    default AccessParameterized<HOST, Result<TYPE>, TYPE, SUBLENS> accessWithSub() {
        return lensSpecWithSub();
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<Result<TYPE>, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, Result<TYPE>> lensSpec() {
        return lensSpecWithSub().getSpec();
    }
    
    @Override
    public default Result<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }
    
    public default SUBLENS get() {
        WriteLens<HOST, TYPE> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, Result.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(Result::get), write);
        return lensSpecWithSub().createSubLens(subSpec);
    }
    
    public default SUBLENS value() {
        WriteLens<HOST, TYPE> write = (HOST host, TYPE newValue)->{
            return lensSpec().getWrite().apply(host, Result.of(newValue));
        };
        LensSpec<HOST, TYPE> subSpec = LensSpec.of(lensSpec().getRead().andThen(Result::get), write);
        return lensSpecWithSub().createSubLens(subSpec);
    }

}
