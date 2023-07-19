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
package functionalj.lens.core;

import static functionalj.functions.StrFuncs.joinNonNull;
import static functionalj.functions.StrFuncs.whenBlank;
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
import lombok.val;
import nullablej.nullable.Nullable;

public class LensUtils {
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(ObjectLens<HOST, DATA> dataLens, String name, Function<DATA, SUB> readSub, WriteLens<DATA, SUB> writeSub, BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val lensSpec = dataLens.lensSpec();
        val hostSubSpec = lensSpec.then(LensSpec.of(readSub, writeSub, lensSpec.isNullSafe()));
        return subLensCreator.apply(name, hostSubSpec);
    }
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(ObjectLens<HOST, DATA> dataLens, Function<DATA, SUB> readSub, WriteLens<DATA, SUB> writeSub, BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return createSubLens(dataLens, null, readSub, writeSub, subLensCreator);
    }
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(ObjectLens<HOST, DATA> dataLens, Function<DATA, SUB> readSub, WriteLens<DATA, SUB> writeSub, Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        return createSubLens(dataLens, null, readSub, writeSub, (__, spec) -> subLensCreator.apply(spec));
    }
    
    public static <HOST, DATA> IntegerLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, String name, ToIntFunction<DATA> readSubInt, WriteLens.PrimitiveInt<DATA> writeSubInt) {
        val lensSpec = dataLens.lensSpec();
        val hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubInt, writeSubInt));
        return new IntegerLens.Impl<>(name, hostSubSpec);
    }
    
    public static <HOST, DATA> IntegerLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, ToIntFunction<DATA> readSubInt, WriteLens.PrimitiveInt<DATA> writeSubInt) {
        return createSubLens(dataLens, null, readSubInt, writeSubInt);
    }
    
    public static <HOST, DATA> LongLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, String name, ToLongFunction<DATA> readSubLong, WriteLens.PrimitiveLong<DATA> writeSubLong) {
        val lensSpec = dataLens.lensSpec();
        val hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubLong, writeSubLong));
        return new LongLens.Impl<>(name, hostSubSpec);
    }
    
    public static <HOST, DATA> LongLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, ToLongFunction<DATA> readSubLong, WriteLens.PrimitiveLong<DATA> writeSubLong) {
        return createSubLens(dataLens, null, readSubLong, writeSubLong);
    }
    
    public static <HOST, DATA> DoubleLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, String name, ToDoubleFunction<DATA> readSubDouble, WriteLens.PrimitiveDouble<DATA> writeSubDouble) {
        val lensSpec = dataLens.lensSpec();
        val hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubDouble, writeSubDouble));
        return new DoubleLens.Impl<>(name, hostSubSpec);
    }
    
    public static <HOST, DATA> DoubleLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, ToDoubleFunction<DATA> readSubDouble, WriteLens.PrimitiveDouble<DATA> writeSubDouble) {
        return createSubLens(dataLens, null, readSubDouble, writeSubDouble);
    }
    
    public static <HOST, DATA> BooleanLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, String name, Predicate<DATA> readSubBoolean, WriteLens.PrimitiveBoolean<DATA> writeSubBoolean) {
        val lensSpec = dataLens.lensSpec();
        val hostSubSpec = lensSpec.thenPrimitive(LensSpec.ofPrimitive(readSubBoolean, writeSubBoolean));
        return new BooleanLens.Impl<>(name, hostSubSpec);
    }
    
    public static <HOST, DATA> BooleanLens<HOST> createSubLens(ObjectLens<HOST, DATA> dataLens, Predicate<DATA> readSubBoolean, WriteLens.PrimitiveBoolean<DATA> writeSubBoolean) {
        return createSubLens(dataLens, null, readSubBoolean, writeSubBoolean);
    }
    
    public static <DATA, SUB, HOST> Function<HOST, SUB> createSubRead(Function<HOST, DATA> readValue, Function<DATA, SUB> readSub, BooleanSupplier isNullSafe) {
        return host -> {
            val value = readValue.apply(host);
            if (isNullSafe.getAsBoolean() && (value == null))
                return null;
            val subValue = readSub.apply(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA, SUB> WriteLens<HOST, SUB> createSubWrite(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue, BiFunction<DATA, SUB, DATA> writeSub, BooleanSupplier isNullSafe) {
        return createSubWrite(readValue, writeValue, WriteLens.of(writeSub), isNullSafe);
    }
    
    public static <HOST, DATA, SUB> WriteLens<HOST, SUB> createSubWrite(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue, WriteLens<DATA, SUB> writeSub, BooleanSupplier isNullSafe) {
        return (host, newSubValue) -> {
            return performWrite(readValue, writeValue, writeSub, isNullSafe, host, newSubValue);
        };
    }
    
    // == Primitive ==
    // -- Int --
    public static <HOST, DATA> ToIntFunction<HOST> createSubReadInt(Function<HOST, DATA> readValue, ToIntFunction<DATA> readSub) {
        return host -> {
            val value = readValue.apply(host);
            val subValue = readSub.applyAsInt(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveInt<HOST> createSubWriteInt(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue, WriteLens.PrimitiveInt<DATA> writeSub) {
        return (host, newSubValue) -> {
            val oldValue = readValue.apply(host);
            val newValue = writeSub.apply(oldValue, newSubValue);
            val newHost = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    // -- Long --
    public static <HOST, DATA> ToLongFunction<HOST> createSubReadLong(Function<HOST, DATA> readValue, ToLongFunction<DATA> readSub) {
        return host -> {
            val value = readValue.apply(host);
            val subValue = readSub.applyAsLong(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveLong<HOST> createSubWriteLong(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue, WriteLens.PrimitiveLong<DATA> writeSub) {
        return (host, newSubValue) -> {
            val oldValue = readValue.apply(host);
            val newValue = writeSub.apply(oldValue, newSubValue);
            val newHost = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    // -- Double --
    public static <HOST, DATA> ToDoubleFunction<HOST> createSubReadDouble(Function<HOST, DATA> readValue, ToDoubleFunction<DATA> readSub) {
        return host -> {
            val value = readValue.apply(host);
            val subValue = readSub.applyAsDouble(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveDouble<HOST> createSubWriteDouble(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue, WriteLens.PrimitiveDouble<DATA> writeSub) {
        return (host, newSubValue) -> {
            val oldValue = readValue.apply(host);
            val newValue = writeSub.apply(oldValue, newSubValue);
            val newHost = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    // -- Boolean --
    public static <HOST, DATA> Predicate<HOST> createSubReadBoolean(Function<HOST, DATA> readValue, Predicate<DATA> readSub) {
        return host -> {
            val value = readValue.apply(host);
            val subValue = readSub.test(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA> WriteLens.PrimitiveBoolean<HOST> createSubWriteBoolean(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue, WriteLens.PrimitiveBoolean<DATA> writeSub) {
        return (host, newSubValue) -> {
            val oldValue = readValue.apply(host);
            val newValue = writeSub.apply(oldValue, newSubValue);
            val newHost = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    private static <DATA, HOST, SUB> HOST performWrite(Function<HOST, DATA> readValue, WriteLens<HOST, DATA> writeValue, WriteLens<DATA, SUB> writeSub, BooleanSupplier isNullSafe, HOST host, SUB newSubValue) {
        val oldValue = readValue.apply(host);
        if (isNullSafe.getAsBoolean() && (oldValue == null))
            return host;
        val newValue = writeSub.apply(oldValue, newSubValue);
        val newHost = writeValue.apply(host, newValue);
        return newHost;
    }
    
    // == Parameterized ==
    public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>> LensSpecParameterized<HOST, TYPE, SUB, SUBLENS> createLensSpecParameterized(String name, Function<HOST, TYPE> read, WriteLens<HOST, TYPE> write, BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return new LensSpecParameterized<HOST, TYPE, SUB, SUBLENS>() {
    
            @Override
            public LensSpec<HOST, TYPE> getSpec() {
                return LensSpec.of(read, write);
            }
    
            @Override
            public SUBLENS createSubLens(String subName, LensSpec<HOST, SUB> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return subCreator.apply(lensName, subSpec);
            }
        };
    }
    
    public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>> LensSpecParameterized<HOST, TYPE, SUB, SUBLENS> createLensSpecParameterized(Function<HOST, TYPE> read, WriteLens<HOST, TYPE> write, BiFunction<String, LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return createLensSpecParameterized(null, read, write, subCreator);
    }
    
    public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>> LensSpecParameterized<HOST, TYPE, SUB, SUBLENS> createLensSpecParameterized(Function<HOST, TYPE> read, WriteLens<HOST, TYPE> write, Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        return createLensSpecParameterized(null, read, write, (name, spec) -> subCreator.apply(spec));
    }
    
    // == Nullable ==
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> createNullableLens(String name, Function<HOST, Nullable<TYPE>> read, WriteLens<HOST, Nullable<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        return new NullableLens.Impl<>(name, spec);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> createNullableLens(Function<HOST, Nullable<TYPE>> read, WriteLens<HOST, Nullable<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createNullableLens(null, read, write, subCreator);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> createNullableLens(Function<HOST, Nullable<TYPE>> read, WriteLens<HOST, Nullable<TYPE>> write, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createNullableLens(null, read, write, (name, spec) -> subCreator.apply(spec));
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> createNullableLens(LensSpec<HOST, Nullable<TYPE>> nullableLensSpec, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createNullableLens(nullableLensSpec.getRead(), nullableLensSpec.getWrite(), subCreator);
    }
    
    // == Result ==
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ResultLens<HOST, TYPE, SUBLENS> createResultLens(String name, Function<HOST, Result<TYPE>> read, WriteLens<HOST, Result<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        return new ResultLens.Impl<>(name, spec);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ResultLens<HOST, TYPE, SUBLENS> createResultLens(Function<HOST, Result<TYPE>> read, WriteLens<HOST, Result<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createResultLens(null, read, write, subCreator);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ResultLens<HOST, TYPE, SUBLENS> createResultLens(Function<HOST, Result<TYPE>> read, WriteLens<HOST, Result<TYPE>> write, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createResultLens(null, read, write, (__, spec) -> subCreator.apply(spec));
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ResultLens<HOST, TYPE, SUBLENS> createResultLens(LensSpec<HOST, Result<TYPE>> resultLensSpec, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val lens = createResultLens(resultLensSpec.getRead(), resultLensSpec.getWrite(), subCreator);
        return lens;
    }
    
    // == Optional ==
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> createOptionalLens(String name, Function<HOST, Optional<TYPE>> read, WriteLens<HOST, Optional<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        return new OptionalLens.Impl<>(name, spec);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> createOptionalLens(Function<HOST, Optional<TYPE>> read, WriteLens<HOST, Optional<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createOptionalLens(null, read, write, subCreator);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> createOptionalLens(Function<HOST, Optional<TYPE>> read, WriteLens<HOST, Optional<TYPE>> write, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createOptionalLens(null, read, write, (__, spec) -> subCreator.apply(spec));
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> createOptionalLens(LensSpec<HOST, Optional<TYPE>> spec, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val lens = createOptionalLens(spec.getRead(), spec.getWrite(), subCreator);
        return lens;
    }
    
    // == List ==
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, SUBLENS> createListLens(String name, Function<HOST, List<TYPE>> read, WriteLens<HOST, List<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(name, read, write, subCreator);
        val listLens = ListLens.of(name, spec);
        return listLens;
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, SUBLENS> createListLens(Function<HOST, List<TYPE>> read, WriteLens<HOST, List<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createListLens(null, read, write, subCreator);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, SUBLENS> createListLens(Function<HOST, List<TYPE>> read, WriteLens<HOST, List<TYPE>> write, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createListLens(null, read, write, (__, spec) -> subCreator.apply(spec));
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, TYPELENS> createSubListLens(String name, LensSpec<HOST, List<TYPE>> spec, LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> specParameterized, Function<HOST, List<TYPE>> read) {
        val newSpec = new LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS>() {
    
            @Override
            public LensSpec<HOST, List<TYPE>> getSpec() {
                return new LensSpec<>(read, spec.getWrite(), spec.getIsNullSafe());
            }
    
            @Override
            public TYPELENS createSubLens(String subName, LensSpec<HOST, TYPE> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return specParameterized.createSubLens(lensName, subSpec);
            }
        };
        return new ListLens.Impl<>(null, newSpec);
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, TYPELENS> createSubListLens(LensSpec<HOST, List<TYPE>> spec, LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> specParameterized, Function<HOST, List<TYPE>> read) {
        return createSubListLens(null, spec, specParameterized, read);
    }
    
    // == Map ==
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST, KEY>, VALUELENS extends AnyLens<HOST, VALUE>> MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(String name, Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return MapLens.of(name, read, write, keyLensCreator, valueLensCreator);
    }
    
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST, KEY>, VALUELENS extends AnyLens<HOST, VALUE>> MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return of(null, read, write, keyLensCreator, valueLensCreator);
    }
    
    public static <HOST, KEY, VALUE, KEYLENS extends AnyLens<HOST, KEY>, VALUELENS extends AnyLens<HOST, VALUE>> MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, Function<LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return of(null, read, write, (__, spec) -> keyLensCreator.apply(spec), (__, spec) -> valueLensCreator.apply(spec));
    }
    
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE> LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createMapLensSpec(String name, Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return new LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS>() {
    
            @Override
            public LensSpec<HOST, Map<KEY, VALUE>> getSpec() {
                return LensSpec.of(read, write);
            }
    
            @Override
            public KEYLENS createSubLens1(String subName, LensSpec<HOST, KEY> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return keyLensCreator.apply(lensName, subSpec);
            }
    
            @Override
            public VALUELENS createSubLens2(String subName, LensSpec<HOST, VALUE> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return valueLensCreator.apply(lensName, subSpec);
            }
        };
    }
    
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE> LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createMapLensSpec(Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createMapLensSpec(null, read, write, keyLensCreator, valueLensCreator);
    }
    
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE> LensSpecParameterized2<HOST, Map<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createMapLensSpec(Function<HOST, Map<KEY, VALUE>> read, WriteLens<HOST, Map<KEY, VALUE>> write, Function<LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createMapLensSpec(read, write, (__, spec) -> keyLensCreator.apply(spec), (__, spec) -> valueLensCreator.apply(spec));
    }
    
    // == FuncList ==
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> FuncListLens<HOST, TYPE, SUBLENS> createFuncListLens(String name, Function<HOST, FuncList<TYPE>> read, WriteLens<HOST, FuncList<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(name, read, write, subCreator);
        return FuncListLens.of(name, spec);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> FuncListLens<HOST, TYPE, SUBLENS> createFuncListLens(Function<HOST, FuncList<TYPE>> read, WriteLens<HOST, FuncList<TYPE>> write, BiFunction<String, LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createFuncListLens(null, read, write, subCreator);
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> FuncListLens<HOST, TYPE, SUBLENS> createFuncListLens(Function<HOST, FuncList<TYPE>> read, WriteLens<HOST, FuncList<TYPE>> write, Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createFuncListLens(null, read, write, (__, spec) -> subCreator.apply(spec));
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> FuncListLens<HOST, TYPE, TYPELENS> createSubFuncListLens(String name, LensSpec<HOST, FuncList<TYPE>> spec, LensSpecParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> specParameterized, Function<HOST, FuncList<TYPE>> read) {
        val newSpec = new LensSpecParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS>() {
    
            @Override
            public LensSpec<HOST, FuncList<TYPE>> getSpec() {
                return new LensSpec<>(read, spec.getWrite(), spec.getIsNullSafe());
            }
    
            @Override
            public TYPELENS createSubLens(String subName, LensSpec<HOST, TYPE> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return specParameterized.createSubLens(lensName, subSpec);
            }
        };
        return FuncListLens.of(null, newSpec);
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> FuncListLens<HOST, TYPE, TYPELENS> createSubFuncListLens(LensSpec<HOST, FuncList<TYPE>> spec, LensSpecParameterized<HOST, FuncList<TYPE>, TYPE, TYPELENS> specParameterized, Function<HOST, FuncList<TYPE>> read) {
        return createSubFuncListLens(null, spec, specParameterized, read);
    }
    
    // == FuncMap ==
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE> LensSpecParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createFuncMapLensSpec(String name, Function<HOST, FuncMap<KEY, VALUE>> read, WriteLens<HOST, FuncMap<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return new LensSpecParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS>() {
    
            @Override
            public LensSpec<HOST, FuncMap<KEY, VALUE>> getSpec() {
                return LensSpec.of(read, write);
            }
    
            @Override
            public KEYLENS createSubLens1(String subName, LensSpec<HOST, KEY> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return keyLensCreator.apply(lensName, subSpec);
            }
    
            @Override
            public VALUELENS createSubLens2(String subName, LensSpec<HOST, VALUE> subSpec) {
                val lensName = whenBlank(joinNonNull(".", name, subName), (String) null);
                return valueLensCreator.apply(lensName, subSpec);
            }
        };
    }
    
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE> LensSpecParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createFuncMapLensSpec(Function<HOST, FuncMap<KEY, VALUE>> read, WriteLens<HOST, FuncMap<KEY, VALUE>> write, BiFunction<String, LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, BiFunction<String, LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createFuncMapLensSpec(null, read, write, keyLensCreator, valueLensCreator);
    }
    
    public static <KEYLENS extends AnyLens<HOST, KEY>, HOST, VALUELENS extends AnyLens<HOST, VALUE>, KEY, VALUE> LensSpecParameterized2<HOST, FuncMap<KEY, VALUE>, KEY, VALUE, KEYLENS, VALUELENS> createFuncMapLensSpec(Function<HOST, FuncMap<KEY, VALUE>> read, WriteLens<HOST, FuncMap<KEY, VALUE>> write, Function<LensSpec<HOST, KEY>, KEYLENS> keyLensCreator, Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return createFuncMapLensSpec(null, read, write, (__, spec) -> keyLensCreator.apply(spec), (__, spec) -> valueLensCreator.apply(spec));
    }
}
