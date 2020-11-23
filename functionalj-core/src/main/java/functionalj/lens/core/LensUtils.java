// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.BooleanLens;
import functionalj.lens.lenses.DoubleLens;
import functionalj.lens.lenses.FuncListLens;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ListLens;
import functionalj.lens.lenses.LongLens;
import functionalj.lens.lenses.MapLens;
import functionalj.lens.lenses.NullableLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.OptionalLens;
import functionalj.lens.lenses.ResultLens;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.result.Result;
import nullablej.nullable.Nullable;

public class LensUtils {

    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(
            ObjectLens<HOST, DATA>                 dataLens,
            Function<DATA, SUB>                    readSub,
            WriteLens<DATA, SUB>                   writeSub,
            Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        var lensSpec    = dataLens.lensSpec();
        var hostSubSpec = lensSpec.then(LensSpec.of(readSub, writeSub, lensSpec.isNullSafe()));
        return subLensCreator.apply(hostSubSpec);
    }
    
    public static <HOST, DATA> IntegerLens<HOST> createSubLens(
            ObjectLens<HOST, DATA>       dataLens,
            ToIntFunction<DATA>          readSubInt,
            WriteLens.PrimitiveInt<DATA> writeSubInt) {
        var lensSpec    = dataLens.lensSpec();
        var hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubInt, writeSubInt));
        return (IntegerLens<HOST>)()->hostSubSpec;
    }
    
    public static <HOST, DATA> LongLens<HOST> createSubLens(
            ObjectLens<HOST, DATA>        dataLens,
            ToLongFunction<DATA>          readSubLong,
            WriteLens.PrimitiveLong<DATA> writeSubLong) {
        var lensSpec    = dataLens.lensSpec();
        var hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubLong, writeSubLong));
        return (LongLens<HOST>)()->hostSubSpec;
    }
    
    public static <HOST, DATA> DoubleLens<HOST> createSubLens(
            ObjectLens<HOST, DATA>           dataLens,
            ToDoubleFunction<DATA>           readSubDouble,
            WriteLens.PrimitiveDouble<DATA> writeSubDouble) {
        var lensSpec    = dataLens.lensSpec();
        var hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubDouble, writeSubDouble));
        return (DoubleLens<HOST>)()->hostSubSpec;
    }
    
    public static <HOST, DATA> BooleanLens<HOST> createSubLens(
            ObjectLens<HOST, DATA>           dataLens,
            Predicate<DATA>                  readSubBoolean,
            WriteLens.PrimitiveBoolean<DATA> writeSubBoolean) {
        var lensSpec    = dataLens.lensSpec();
        var hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubBoolean, writeSubBoolean));
        return (BooleanLens<HOST>)()->hostSubSpec;
    }
    
    public static <DATA, SUB, HOST> Function<HOST, SUB> createSubRead(
            Function<HOST, DATA> readValue,
            Function<DATA, SUB>  readSub, 
            BooleanSupplier      isNullSafe) {
        return host ->{
            var value = readValue.apply(host);
            if (isNullSafe.getAsBoolean() && (value == null))
                return null;
            
            var subValue = readSub.apply(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA, SUB> WriteLens<HOST, SUB> createSubWrite(
            Function<HOST, DATA>         readValue,
            WriteLens<HOST, DATA>        writeValue,
            BiFunction<DATA, SUB, DATA>  writeSub,
            BooleanSupplier              isNullSafe) {
        return createSubWrite(readValue, writeValue, WriteLens.of(writeSub), isNullSafe);
    }
    
    public static <HOST, DATA, SUB> WriteLens<HOST, SUB> createSubWrite(
            Function<HOST, DATA>  readValue,
            WriteLens<HOST, DATA> writeValue,
            WriteLens<DATA, SUB>  writeSub,
            BooleanSupplier       isNullSafe) {
        return (host, newSubValue)->{
            return performWrite(readValue, writeValue, writeSub, isNullSafe, host, newSubValue);
        };
    }
    
    //== Primitive ==
    
    //-- Int --
    
    public static <HOST, DATA> ToIntFunction<HOST> createSubReadInt(
            Function<HOST, DATA> readValue,
            ToIntFunction<DATA>  readSub) {
        return host ->{
            var value    = readValue.apply(host);
            var subValue = readSub.applyAsInt(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveInt<HOST> createSubWriteInt(
            Function<HOST, DATA>         readValue,
            WriteLens<HOST, DATA>        writeValue,
            WriteLens.PrimitiveInt<DATA> writeSub) {
        return (host, newSubValue)->{
            var oldValue = readValue.apply(host);
            var newValue = writeSub.apply(oldValue, newSubValue);
            var newHost  = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    //-- Long --
    
    public static <HOST, DATA> ToLongFunction<HOST> createSubReadLong(
            Function<HOST, DATA> readValue,
            ToLongFunction<DATA> readSub) {
        return host ->{
            var value    = readValue.apply(host);
            var subValue = readSub.applyAsLong(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveLong<HOST> createSubWriteLong(
            Function<HOST, DATA>          readValue,
            WriteLens<HOST, DATA>         writeValue,
            WriteLens.PrimitiveLong<DATA> writeSub) {
        return (host, newSubValue)->{
            var oldValue = readValue.apply(host);
            var newValue = writeSub.apply(oldValue, newSubValue);
            var newHost  = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    //-- Double --
    
    public static <HOST, DATA> ToDoubleFunction<HOST> createSubReadDouble(
            Function<HOST, DATA>   readValue,
            ToDoubleFunction<DATA> readSub) {
        return host ->{
            var value    = readValue.apply(host);
            var subValue = readSub.applyAsDouble(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveDouble<HOST> createSubWriteDouble(
            Function<HOST, DATA>            readValue,
            WriteLens<HOST, DATA>           writeValue,
            WriteLens.PrimitiveDouble<DATA> writeSub) {
        return (host, newSubValue)->{
            var oldValue = readValue.apply(host);
            var newValue = writeSub.apply(oldValue, newSubValue);
            var newHost  = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    //-- Boolean --
    
    public static <HOST, DATA> Predicate<HOST> createSubReadBoolean(
            Function<HOST, DATA> readValue,
            Predicate<DATA>      readSub) {
        return host ->{
            var value    = readValue.apply(host);
            var subValue = readSub.test(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveBoolean<HOST> createSubWriteBoolean(
            Function<HOST, DATA>             readValue,
            WriteLens<HOST, DATA>            writeValue,
            WriteLens.PrimitiveBoolean<DATA> writeSub) {
        return (host, newSubValue)->{
            var oldValue = readValue.apply(host);
            var newValue = writeSub.apply(oldValue, newSubValue);
            var newHost  = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    private static <DATA, HOST, SUB> HOST performWrite(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue,
            WriteLens<DATA, SUB> writeSub, BooleanSupplier isNullSafe, HOST host, SUB newSubValue) {
        var oldValue = readValue.apply(host);
        if (isNullSafe.getAsBoolean() && (oldValue == null))
            return host;
        
        var newValue = writeSub.apply(oldValue, newSubValue);
        var newHost  = writeValue.apply(host, newValue);
        return newHost;
    }
    
    //== Parameterized ==
    
    public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>> 
        LensSpecParameterized<HOST, TYPE, SUB, SUBLENS> createLensSpecParameterized(
            Function<HOST, TYPE>                   read,
            WriteLens<HOST, TYPE>                  write,
            Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        var spec = new LensSpecParameterized<HOST, TYPE, SUB, SUBLENS>() {
            @Override
            public LensSpec<HOST, TYPE> getSpec() {
                return LensSpec.of(read, write);
            }
            @Override
            public SUBLENS createSubLens(LensSpec<HOST, SUB> subSpec) {
                return subCreator.apply(subSpec);
            }
        };
        return spec;
    }
    
    //== Nullable ==
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            Function<HOST, Nullable<TYPE>>          read,
            WriteLens<HOST, Nullable<TYPE>>         write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var spec = createLensSpecParameterized(read, write, subCreator);
        var lens = (NullableLens<HOST, TYPE, SUBLENS>)()->spec;
        return lens;
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            LensSpec<HOST, Nullable<TYPE>> nullableLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var lens = createNullableLens(nullableLensSpec.getRead(), nullableLensSpec.getWrite(), subCreator);
        return lens;
    }
    
    //== Result ==
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ResultLens<HOST, TYPE, SUBLENS> 
        createResultLens(
            Function<HOST, Result<TYPE>>          read,
            WriteLens<HOST, Result<TYPE>>         write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var spec = createLensSpecParameterized(read, write, subCreator);
        var lens = (ResultLens<HOST, TYPE, SUBLENS>)()->spec;
        return lens;
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ResultLens<HOST, TYPE, SUBLENS> 
        createResultLens(
            LensSpec<HOST, Result<TYPE>> resultLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var lens = createResultLens(resultLensSpec.getRead(), resultLensSpec.getWrite(), subCreator);
        return lens;
    }
    
    //== Optional ==
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> 
        createOptionalLens(
            Function<HOST, Optional<TYPE>>          read,
            WriteLens<HOST, Optional<TYPE>>         write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var spec = createLensSpecParameterized(read, write, subCreator);
        var lens = (OptionalLens<HOST, TYPE, SUBLENS>)()->spec;
        return lens;
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> 
        createOptionalLens(
            LensSpec<HOST, Optional<TYPE>> spec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var lens = createOptionalLens(spec.getRead(), spec.getWrite(), subCreator);
        return lens;
    }
    
    //== List ==
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, SUBLENS> 
        createListLens(
            Function<HOST, List<TYPE>>              read,
            WriteLens<HOST, List<TYPE>>             write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var spec = createLensSpecParameterized(read, write, subCreator);
        var listLens = ListLens.of(spec);
        return listLens;
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, TYPELENS>
            createSubListLens(
                LensSpec<HOST, List<TYPE>>                              spec,
                LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> specParameterized,
                Function<HOST, List<TYPE>>                              read) {
        var newSpec = new LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS>() {
            @Override
            public LensSpec<HOST, List<TYPE>> getSpec() {
                return new LensSpec<>(read, spec.getWrite(), spec.getIsNullSafe());
            }
            @Override
            public TYPELENS createSubLens(LensSpec<HOST, TYPE> subSpec) {
                return specParameterized.createSubAccessFromHost(subSpec.getRead());
            }
        };
        return () -> newSpec;
    }
    
    //== Map ==
    
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST,KEY>, VALUELENS extends AnyLens<HOST,VALUE>>
            MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(
                    Function<HOST,  Map<KEY, VALUE>>           read,
                    WriteLens<HOST, Map<KEY, VALUE>>           write,
                    Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                    Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return MapLens.of(read, write, keyLensCreator, valueLensCreator);
    }
    
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE>
            LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createMapLensSpec(
                    Function<HOST,  Map<KEY, VALUE>>           read,
                    WriteLens<HOST, Map<KEY, VALUE>>           write,
                    Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                    Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return new LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS>() {

            @Override
            public LensSpec<HOST, Map<KEY, VALUE>> getSpec() {
                return LensSpec.of(read, write);
            }

            @Override
            public KEYLENS createSubLens1(
                    LensSpec<HOST, KEY> subSpec) {
                return keyLensCreator.apply(subSpec);
            }

            @Override
            public VALUELENS createSubLens2(
                    LensSpec<HOST, VALUE> subSpec) {
                return valueLensCreator.apply(subSpec);
            }
        };
    }
    
    //== FuncList ==
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> FuncListLens<HOST, TYPE, SUBLENS> 
        createFuncListLens(
            Function<HOST,  FuncList<TYPE>>   read,
            WriteLens<HOST, FuncList<TYPE>>   write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        var spec = createLensSpecParameterized(read, write, subCreator);
        var listLens = FuncListLens.of(spec);
        return listLens;
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> FuncListLens<HOST, TYPE, TYPELENS>
            createSubFuncListLens(
                LensSpec<HOST, FuncList<TYPE>>                              spec,
                LensSpecParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> specParameterized,
                Function<HOST, FuncList<TYPE>>                              read) {
        var newSpec = new LensSpecParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS>() {
            @Override
            public LensSpec<HOST, FuncList<TYPE>> getSpec() {
                return new LensSpec<>(read, spec.getWrite(), spec.getIsNullSafe());
            }
            @Override
            public TYPELENS createSubLens(LensSpec<HOST, TYPE> subSpec) {
                return specParameterized.createSubLens(subSpec);
            }
        };
        return () -> newSpec;
    }
    
    // == FuncMap ==
    
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE>
            LensSpecParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createFuncMapLensSpec(
                    Function<HOST,  FuncMap<KEY, VALUE>>       read,
                    WriteLens<HOST, FuncMap<KEY, VALUE>>       write,
                    Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                    Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return new LensSpecParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS>() {
            
            @Override
            public LensSpec<HOST, FuncMap<KEY, VALUE>> getSpec() {
                return LensSpec.of(read, write);
            }
            
            @Override
            public KEYLENS createSubLens1(
                    LensSpec<HOST, KEY> subSpec) {
                return keyLensCreator.apply(subSpec);
            }
            
            @Override
            public VALUELENS createSubLens2(
                    LensSpec<HOST, VALUE> subSpec) {
                return valueLensCreator.apply(subSpec);
            }
        };
    }
    
}
