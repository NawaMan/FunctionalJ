package functionalj.lens;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.val;

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
            Function<DATA, SUB>                    readSub,
            BiFunction<DATA, SUB, DATA>            writeSub,
            Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return Lenses.createSubLens(this, readSub, writeSub, subLensCreator);
    }
    
    public <LIST extends List<SUB>, SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ListLens<HOST, LIST, SUB, SUBLENS> createSubListLens(
                Function<DATA, LIST>                   readSub,
                BiFunction<DATA, LIST, DATA>           writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, LIST>)        Lenses.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (BiFunction<HOST, LIST, HOST>)Lenses.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = Lenses.createLensSpecParameterized(subRead, subWrite, subLensCreator);
        val listLens   = ListLens.of(spec);
        return listLens;
    }
}
