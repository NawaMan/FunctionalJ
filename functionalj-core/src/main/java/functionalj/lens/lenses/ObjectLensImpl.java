package functionalj.lens.lenses;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.types.FunctionalList;
import functionalj.types.FunctionalMap;
import functionalj.types.MayBe;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

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
        return LensUtils.createSubLens(this, readSub, writeSub, subLensCreator);
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ListLens<HOST, SUB, SUBLENS> createSubListLens(
                Function<DATA, List<SUB>>                   readSub,
                WriteLens<DATA, List<SUB>>                  writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, List<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, List<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = LensUtils.createLensSpecParameterized(subRead, subWrite, subLensCreator);
        val listLens   = ListLens.of(spec);
        return listLens;
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            FunctionalListLens<HOST, SUB, SUBLENS> createSubFunctionalListLens(
                Function<DATA, FunctionalList<SUB>>                   readSub,
                WriteLens<DATA, FunctionalList<SUB>>                  writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, FunctionalList<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, FunctionalList<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = LensUtils.createLensSpecParameterized(subRead, subWrite, subLensCreator);
        val listLens   = FunctionalListLens.of(spec);
        return listLens;
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            NullableLens<HOST, SUB, SUBLENS> createSubNullableLens(
                Function<DATA,  Nullable<SUB>>         readSub,
                WriteLens<DATA, Nullable<SUB>>         writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Nullable<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Nullable<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
        val lens = (NullableLens<HOST, SUB, SUBLENS>)()->spec;
        return lens;
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            OptionalLens<HOST, SUB, SUBLENS> createSubOptionalLens(
                Function<DATA,  Optional<SUB>>         readSub,
                WriteLens<DATA, Optional<SUB>>         writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Optional<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Optional<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
        val lens = (OptionalLens<HOST, SUB, SUBLENS>)()->spec;
        return lens;
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            MayBeLens<HOST, SUB, SUBLENS> createSubMayBeLens(
                Function<DATA,  MayBe<SUB>>            readSub,
                WriteLens<DATA, MayBe<SUB>>            writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, MayBe<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, MayBe<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
        val lens = (MayBeLens<HOST, SUB, SUBLENS>)()->spec;
        return lens;
    }
        
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubMapLens(
                Function<DATA,  Map<KEY, VALUE>>           readSub,
                WriteLens<DATA, Map<KEY, VALUE>>           writeSub,
                Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Map<KEY, VALUE>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Map<KEY, VALUE>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        return MapLens.of(subRead, subWrite, keyLensCreator, valueLensCreator);
    }   
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        FunctionalMapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubFunctionalMapLens(
                Function<DATA,  FunctionalMap<KEY, VALUE>>           readSub,
                WriteLens<DATA, FunctionalMap<KEY, VALUE>>           writeSub,
                Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, FunctionalMap<KEY, VALUE>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, FunctionalMap<KEY, VALUE>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        return FunctionalMapLens.of(subRead, subWrite, keyLensCreator, valueLensCreator);
    }
    
}
