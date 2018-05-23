package functionalj.lens;

import java.util.List;
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
            WriteLens<DATA, SUB>                   writeSub,
            Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return Lenses.createSubLens(this, readSub, writeSub, subLensCreator);
    }
    
    public <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ListLens<HOST, SUB, SUBLENS> createSubListLens(
                Function<DATA, List<SUB>>                   readSub,
                WriteLens<DATA, List<SUB>>                  writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, List<SUB>>) Lenses.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, List<SUB>>)Lenses.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = Lenses.createLensSpecParameterized(subRead, subWrite, subLensCreator);
        val listLens   = ListLens.of(spec);
        return listLens;
    }
}
