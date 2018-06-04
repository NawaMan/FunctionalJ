package functionalj.lens;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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
        return Lenses.createSubLens(this, readSub, writeSub, subLensCreator);
    }
    
    protected <SUB, SUBLENS extends Lens<HOST, SUB>> 
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
    
    protected <SUB, SUBLENS extends Lens<HOST, SUB>> 
            NullableLens<HOST, SUB, SUBLENS> createSubNullableLens(
                Function<DATA,  Nullable<SUB>>         readSub,
                WriteLens<DATA, Nullable<SUB>>         writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Nullable<SUB>>) Lenses.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Nullable<SUB>>)Lenses.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec = Lenses.createLensSpecParameterized(subRead, subWrite, subCreator);
        val lens = (NullableLens<HOST, SUB, SUBLENS>)()->spec;
        return lens;
    }
    
    protected <SUB, SUBLENS extends Lens<HOST, SUB>> 
            OptionalLens<HOST, SUB, SUBLENS> createSubOptionalLens(
                Function<DATA,  Optional<SUB>>         readSub,
                WriteLens<DATA, Optional<SUB>>         writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Optional<SUB>>) Lenses.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Optional<SUB>>)Lenses.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec = Lenses.createLensSpecParameterized(subRead, subWrite, subCreator);
        val lens = (OptionalLens<HOST, SUB, SUBLENS>)()->spec;
        return lens;
    }
    
    protected <SUB, SUBLENS extends Lens<HOST, SUB>> 
            MayBeLens<HOST, SUB, SUBLENS> createSubMayBeLens(
                Function<DATA,  MayBe<SUB>>            readSub,
                WriteLens<DATA, MayBe<SUB>>            writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, MayBe<SUB>>) Lenses.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, MayBe<SUB>>)Lenses.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec = Lenses.createLensSpecParameterized(subRead, subWrite, subCreator);
        val lens = (MayBeLens<HOST, SUB, SUBLENS>)()->spec;
        return lens;
    }
        
    protected <KEY, VALUE, KEYLENS extends Lens<HOST,KEY>, VALUELENS extends Lens<HOST,VALUE>>
        MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(
                Function<DATA,  Map<KEY, VALUE>>           readSub,
                WriteLens<DATA, Map<KEY, VALUE>>           writeSub,
                Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Map<KEY, VALUE>>) Lenses.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Map<KEY, VALUE>>)Lenses.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        return MapLens.of(subRead, subWrite, keyLensCreator, valueLensCreator);
    }
}
