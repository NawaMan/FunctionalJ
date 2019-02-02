// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.lens.lenses;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import functionalj.lens.core.AccessUtils;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.result.Result;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
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
            FuncListLens<HOST, SUB, SUBLENS> createSubFuncListLens(
                Function<DATA, FuncList<SUB>>                   readSub,
                WriteLens<DATA, FuncList<SUB>>                  writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, FuncList<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, FuncList<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = LensUtils.createLensSpecParameterized(subRead, subWrite, subLensCreator);
        val listLens   = FuncListLens.of(spec);
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
            ResultLens<HOST, SUB, SUBLENS> createSubResultLens(
                Function<DATA,  Result<SUB>>         readSub,
                WriteLens<DATA, Result<SUB>>         writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Result<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Result<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
        val lens = (ResultLens<HOST, SUB, SUBLENS>)()->spec;
        return lens;
    }
    
    protected <SUB, SUBACCESSS extends AnyAccess<HOST, SUB>> 
            ResultAccess<HOST, SUB, SUBACCESSS> createSubResultAccess(
                Function<DATA,  Result<SUB>>              readSub,
                Function<Function<HOST, SUB>, SUBACCESSS> subCreator) {
        val readThis = this.lensSpec().getRead();
        val subRead  = (Function<HOST, Result<SUB>>) LensUtils.createSubRead(readThis, readSub, this.lensSpec().getIsNullSafe());
        return AccessUtils.createResultAccess(subRead, subCreator);
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
        FuncMapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubFuncMapLens(
                Function<DATA,  FuncMap<KEY, VALUE>>           readSub,
                WriteLens<DATA, FuncMap<KEY, VALUE>>           writeSub,
                Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, FuncMap<KEY, VALUE>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, FuncMap<KEY, VALUE>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        return FuncMapLens.of(subRead, subWrite, keyLensCreator, valueLensCreator);
    }
    
}
