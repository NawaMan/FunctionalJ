// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.functions.StrFuncs.whenBlank;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import functionalj.lens.core.AccessUtils;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.result.Result;
import lombok.val;
import nullablej.nullable.Nullable;

public class ObjectLensImpl<HOST, DATA> extends AnyLens.Impl<HOST, DATA> implements ObjectLens<HOST, DATA> {
    
    public ObjectLensImpl(LensSpec<HOST, DATA> spec) {
        this(null, spec);
    }
    
    public ObjectLensImpl(String name, LensSpec<HOST, DATA> spec) {
        super(name, spec);
    }
    
    
    protected <SUB, SUBLENS> SUBLENS createSubLens(
            Function<DATA, SUB>                    readSub,
            WriteLens<DATA, SUB>                   writeSub,
            Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return LensUtils.createSubLens(this, readSub, writeSub, subLensCreator);
    }
    
    protected <SUB, SUBLENS> SUBLENS createSubLens(
            String                                           name,
            Function<DATA, SUB>                              readSub,
            WriteLens<DATA, SUB>                             writeSub,
            BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val thisName = name();
        val lensName = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return LensUtils.createSubLens(this, lensName, readSub, writeSub, subLensCreator);
    }
    
    protected IntegerLens<HOST> createSubLensInt(
            ToIntFunction<DATA>          readSubInt,
            WriteLens.PrimitiveInt<DATA> writeSubInt) {
        return LensUtils.createSubLens(this, readSubInt, writeSubInt);
    }
    
    protected IntegerLens<HOST> createSubLensInt(
            String                       name,
            ToIntFunction<DATA>          readSubInt,
            WriteLens.PrimitiveInt<DATA> writeSubInt) {
        val thisName = name();
        val lensName = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return LensUtils.createSubLens(this, lensName, readSubInt, writeSubInt);
    }
    
    protected LongLens<HOST> createSubLensLong(
            ToLongFunction<DATA>          readSubLong,
            WriteLens.PrimitiveLong<DATA> writeSubLong) {
        return LensUtils.createSubLens(this, readSubLong, writeSubLong);
    }
    
    protected LongLens<HOST> createSubLensLong(
            String                        name,
            ToLongFunction<DATA>          readSubLong,
            WriteLens.PrimitiveLong<DATA> writeSubLong) {
        val thisName = name();
        val lensName = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return LensUtils.createSubLens(this, lensName, readSubLong, writeSubLong);
    }
    
    protected DoubleLens<HOST> createSubLensDouble(
            ToDoubleFunction<DATA>          readSubDouble,
            WriteLens.PrimitiveDouble<DATA> writeSubDouble) {
        return LensUtils.createSubLens(this, readSubDouble, writeSubDouble);
    }
    
    protected DoubleLens<HOST> createSubLensDouble(
            String                          name,
            ToDoubleFunction<DATA>          readSubDouble,
            WriteLens.PrimitiveDouble<DATA> writeSubDouble) {
        val thisName = name();
        val lensName = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return LensUtils.createSubLens(this, lensName, readSubDouble, writeSubDouble);
    }
    
    protected BooleanLens<HOST> createSubLensBoolean(
            Predicate<DATA>                  readSubBoolean,
            WriteLens.PrimitiveBoolean<DATA> writeSubBoolean) {
        return LensUtils.createSubLens(this, readSubBoolean, writeSubBoolean);
    }
    
    protected BooleanLens<HOST> createSubLensBoolean(
            String                           name,
            Predicate<DATA>                  readSubBoolean,
            WriteLens.PrimitiveBoolean<DATA> writeSubBoolean) {
        val thisName = name();
        val lensName = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return LensUtils.createSubLens(this, lensName, readSubBoolean, writeSubBoolean);
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ListLens<HOST, SUB, SUBLENS> createSubListLens(
                String                                           name,
                Function<DATA, List<SUB>>                        readSub,
                WriteLens<DATA, List<SUB>>                       writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, List<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, List<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = LensUtils.createLensSpecParameterized(subRead, subWrite, subLensCreator);
        val thisName   = name();
        val lensName   = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        val listLens   = ListLens.of(lensName, spec);
        return listLens;
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ListLens<HOST, SUB, SUBLENS> createSubListLens(
                Function<DATA, List<SUB>>                        readSub,
                WriteLens<DATA, List<SUB>>                       writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return createSubListLens(null, readSub, writeSub, subLensCreator);
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ListLens<HOST, SUB, SUBLENS> createSubListLens(
                Function<DATA, List<SUB>>              readSub,
                WriteLens<DATA, List<SUB>>             writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return createSubListLens(null, readSub, writeSub, (name, spec) -> subLensCreator.apply(spec));
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected <SUB> ListLens<HOST, Object, ObjectLens<HOST, Object>> createSubListLens(
                String                     name,
                Function<DATA, List<SUB>>  readSub,
                WriteLens<DATA, List<SUB>> writeSub) {
        Function<DATA, List<Object>>  readObject  = (Function)readSub;
        WriteLens<DATA, List<Object>> writeObject = (WriteLens)writeSub;
        return createSubListLens(name, readObject, writeObject, ObjectLens::of);
    }
    protected <SUB> ListLens<HOST, Object, ObjectLens<HOST, Object>> createSubListLens(
                Function<DATA, List<SUB>>  readSub,
                WriteLens<DATA, List<SUB>> writeSub) {
        return createSubListLens(null, readSub, writeSub);
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            FuncListLens<HOST, SUB, SUBLENS> createSubFuncListLens(
                Function<DATA, FuncList<SUB>>          readSub,
                WriteLens<DATA, FuncList<SUB>>         writeSub,
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
            FuncListLens<HOST, SUB, SUBLENS> createSubFuncListLens(
                String                                 name,
                Function<DATA, FuncList<SUB>>          readSub,
                WriteLens<DATA, FuncList<SUB>>         writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, FuncList<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, FuncList<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = LensUtils.createLensSpecParameterized(subRead, subWrite, subLensCreator);
        val thisName   = name();
        val lensName   = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        val listLens   = FuncListLens.of(lensName, spec);
        return listLens;
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            NullableLens<HOST, SUB, SUBLENS> createSubNullableLens(
                String                                           name,
                Function<DATA,  Nullable<SUB>>                   readSub,
                WriteLens<DATA, Nullable<SUB>>                   writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis  = this.lensSpec().getRead();
        val writeThis = this.lensSpec().getWrite();
        val subRead   = (Function<HOST, Nullable<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite  = (WriteLens<HOST, Nullable<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec      = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
        val thisName  = name();
        val lensName  = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        val lens      = NullableLens.of(lensName, spec);
        return lens;
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            NullableLens<HOST, SUB, SUBLENS> createSubNullableLens(
                Function<DATA,  Nullable<SUB>>                   readSub,
                WriteLens<DATA, Nullable<SUB>>                   writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return createSubNullableLens(null, readSub, writeSub, subCreator);
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            NullableLens<HOST, SUB, SUBLENS> createSubNullableLens(
                Function<DATA,  Nullable<SUB>>         readSub,
                WriteLens<DATA, Nullable<SUB>>         writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return createSubNullableLens(null, readSub, writeSub, (__,spec)->subCreator.apply(spec));
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ResultLens<HOST, SUB, SUBLENS> createSubResultLens(
                String                                           name,
                Function<DATA,  Result<SUB>>                     readSub,
                WriteLens<DATA, Result<SUB>>                     writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Result<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Result<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
        val thisName   = name();
        val lensName   = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return ResultLens.of(lensName, spec);
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ResultLens<HOST, SUB, SUBLENS> createSubResultLens(
                Function<DATA,  Result<SUB>>           readSub,
                WriteLens<DATA, Result<SUB>>           writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return createSubResultLens(null, readSub, writeSub, subCreator);
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            ResultLens<HOST, SUB, SUBLENS> createSubResultLens(
                Function<DATA,  Result<SUB>>           readSub,
                WriteLens<DATA, Result<SUB>>           writeSub,
                Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return createSubResultLens(null, readSub, writeSub, (__,spec)->subCreator.apply(spec));
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
                String                                           name,
                Function<DATA,  Optional<SUB>>                   readSub,
                WriteLens<DATA, Optional<SUB>>                   writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Optional<SUB>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Optional<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val spec       = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
        val thisName   = name();
        val lensName   = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return OptionalLens.of(lensName, spec);
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            OptionalLens<HOST, SUB, SUBLENS> createSubOptionalLens(
                Function<DATA,  Optional<SUB>>                   readSub,
                WriteLens<DATA, Optional<SUB>>                   writeSub,
                BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return createSubOptionalLens(null, readSub, writeSub, subCreator);
    }
    
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            OptionalLens<HOST, SUB, SUBLENS> createSubOptionalLens(
                String                                                   name,
                LensSpecParameterized<HOST, Optional<SUB>, SUB, SUBLENS> spec) {
        return OptionalLens.of(name, spec);
    }
    protected <SUB, SUBLENS extends AnyLens<HOST, SUB>> 
            OptionalLens<HOST, SUB, SUBLENS> createSubOptionalLens(
                LensSpecParameterized<HOST, Optional<SUB>, SUB, SUBLENS> spec) {
        return OptionalLens.of(null, spec);
    }
    
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubMapLens(
                String                                     name,
                Function<DATA,  Map<KEY, VALUE>>           readSub,
                WriteLens<DATA, Map<KEY, VALUE>>           writeSub,
                BiFunction<String, LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, Map<KEY, VALUE>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, Map<KEY, VALUE>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val thisName   = name();
        val lensName   = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return MapLens.of(lensName, subRead, subWrite, keyLensCreator, valueLensCreator);
    }
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubMapLens(
                Function<DATA,  Map<KEY, VALUE>>                     readSub,
                WriteLens<DATA, Map<KEY, VALUE>>                     writeSub,
                BiFunction<String, LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createSubMapLens(null, readSub, writeSub, keyLensCreator, valueLensCreator);
    }
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubMapLens(
                Function<DATA,  Map<KEY, VALUE>>           readSub,
                WriteLens<DATA, Map<KEY, VALUE>>           writeSub,
                Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createSubMapLens(
                        null, 
                        readSub, writeSub, 
                        (__,spec)->keyLensCreator.apply(spec), 
                        (__,spec)->valueLensCreator.apply(spec));
    }
    
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        FuncMapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubFuncMapLens(
                String                                               name,
                Function<DATA,  FuncMap<KEY, VALUE>>                 readSub,
                WriteLens<DATA, FuncMap<KEY, VALUE>>                 writeSub,
                BiFunction<String, LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        val readThis   = this.lensSpec().getRead();
        val writeThis  = this.lensSpec().getWrite();
        val subRead    = (Function<HOST, FuncMap<KEY, VALUE>>) LensUtils.createSubRead(readThis,  readSub,             this.lensSpec().getIsNullSafe());
        val subWrite   = (WriteLens<HOST, FuncMap<KEY, VALUE>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, this.lensSpec().getIsNullSafe());
        val thisName   = name();
        val lensName   = whenBlank(Stream.of(thisName, name).filter(Objects::nonNull).collect(joining(".")), (String)null);
        return FuncMapLens.of(lensName, subRead, subWrite, keyLensCreator, valueLensCreator);
    }
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        FuncMapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubFuncMapLens(
                Function<DATA,  FuncMap<KEY, VALUE>>                 readSub,
                WriteLens<DATA, FuncMap<KEY, VALUE>>                 writeSub,
                BiFunction<String, LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createSubFuncMapLens(null, readSub, writeSub, keyLensCreator, valueLensCreator);
    }
    protected <KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
        FuncMapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> createSubFuncMapLens(
                Function<DATA,  FuncMap<KEY, VALUE>>       readSub,
                WriteLens<DATA, FuncMap<KEY, VALUE>>       writeSub,
                Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createSubFuncMapLens(
                        readSub, 
                        writeSub, 
                        (__,spec)->keyLensCreator.apply(spec), 
                        (__,spec)->valueLensCreator.apply(spec));
    }
    
}
