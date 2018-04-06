package nawaman.functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;
import nawaman.functionalj.functions.Func1;

public class ObjectLensImpl<HOST, DATA> implements ObjectLens<HOST, DATA> {
    
    private LensSpec<HOST, DATA> spec;
    public ObjectLensImpl(LensSpec<HOST, DATA> spec) {
        this.spec = spec;
    }
    
    @Override
    public LensSpec<HOST, DATA> lensSpec() {
        return spec;
    }
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(
            ObjectLens<HOST, DATA>              dataLens,
            Function<DATA, SUB>                 readSub,
            BiFunction<DATA, SUB, DATA>         writeSub,
            Func1<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val spec = dataLens.lensSpec();
        Function<HOST, SUB>         hostSubRead  = spec.getRead().andThen(readSub);
        BiFunction<HOST, SUB, HOST> hostSubWrite = (host, newColor)->{
            val newCar  = writeSub.apply(spec.getRead().apply(host), newColor);
            val newHost = spec.getWrite().apply(host, newCar);
            return newHost;
        };
        val hostSubSpec = LensSpec.of(hostSubRead, hostSubWrite);
        return subLensCreator.apply(hostSubSpec);
    }
    
    protected <SUB, SUBLENS> SUBLENS createSubLens(
            Function<DATA, SUB>                 readSub,
            BiFunction<DATA, SUB, DATA>         writeSub,
            Func1<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return createSubLens(this, readSub, writeSub, subLensCreator);
    }
}
