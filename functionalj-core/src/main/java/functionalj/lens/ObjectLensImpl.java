package functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

import functionalj.functions.Func1;

public class ObjectLensImpl<HOST, DATA> implements ObjectLens<HOST, DATA> {
    
    private LensSpec<HOST, DATA> spec;
    public ObjectLensImpl(LensSpec<HOST, DATA> spec) {
        this.spec = spec;
    }
    
    @Override
    public LensSpec<HOST, DATA> lensSpec() {
        return spec;
    }
    
    protected <SUB, SUBLENS> SUBLENS createSubLens(
            Function<DATA, SUB>                 readSub,
            BiFunction<DATA, SUB, DATA>         writeSub,
            Func1<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return LensSpec.createSubLens(this, readSub, writeSub, subLensCreator);
    }
}
