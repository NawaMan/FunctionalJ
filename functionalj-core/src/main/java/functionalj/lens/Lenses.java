package functionalj.lens;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import functionalj.types.FunctionalList;
import functionalj.types.MayBe;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class Lenses {
    
    public static final AnyLens<Object, Object> theObject  = AnyLens    .of(LensSpec.of(Object.class));
    public static final BooleanLens<Boolean>    theBoolean = BooleanLens.of(LensSpec.of(Boolean.class));
    public static final StringLens<String>      theString  = StringLens .of(LensSpec.of(String.class));
    public static final IntegerLens<Integer>    theInteger = IntegerLens.of(LensSpec.of(Integer.class));
    
    public static final BooleanLens<Boolean>    $B = theBoolean;
    public static final StringLens<String>      $S = theString;
    public static final IntegerLens<Integer>    $I = theInteger;
    
    
    public static <T> AnyLens<T, T> theItem() {
        return AnyLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T> ObjectLens<T, T> theObject() {
        return ObjectLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T extends Comparable<T>> ComparableLens<T, T> theComparable() {
        return ComparableLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <H> LensCreator<H, String, StringAccess<H>, StringLens<H>> ofString() {
        return (LensCreator<H, String, StringAccess<H>, StringLens<H>>)(LensCreator)__internal__.lensCreatorString;
    }
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(
            ObjectLens<HOST, DATA>                 dataLens,
            Function<DATA, SUB>                    readSub,
            WriteLens<DATA, SUB>                   writeSub,
            Function<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val lensSpec    = dataLens.lensSpec();
        val hostSubSpec = lensSpec.then(LensSpec.of(readSub, writeSub, lensSpec.isNullSafe()));
        return subLensCreator.apply(hostSubSpec);
    }
    
    public static <DATA, SUB, HOST> Function<HOST, SUB> createSubRead(
            Function<HOST, DATA> readValue,
            Function<DATA, SUB>  readSub, 
            BooleanSupplier      isNullSafe) {
        return host ->{
            val value = readValue.apply(host);
            if (isNullSafe.getAsBoolean() && (value == null))
                return null;
            
            val subValue = readSub.apply(value);
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
            val oldValue = readValue.apply(host);
            if (isNullSafe.getAsBoolean() && (oldValue == null))
                return host;
            
            val newValue = writeSub.apply(oldValue, newSubValue);
            val newHost  = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
    //== Parameterized ==
    
    public static <HOST, TYPE, SUB, SUBLENS extends Lens<HOST, SUB>> 
        LensSpecParameterized<HOST, TYPE, SUB, SUBLENS> createLensSpecParameterized(
            Function<HOST, TYPE>                   read,
            WriteLens<HOST, TYPE>                  write,
            Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val spec = new LensSpecParameterized<HOST, TYPE, SUB, SUBLENS>() {
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
    
    public static <HOST, TYPE, SUBLENS extends Lens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            Function<HOST, Nullable<TYPE>>          read,
            WriteLens<HOST, Nullable<TYPE>>         write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        val lens = (NullableLens<HOST, TYPE, SUBLENS>)()->spec;
        return lens;
    }
    
    public static <HOST, TYPE, SUBLENS extends Lens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            LensSpec<HOST, Nullable<TYPE>> nullableLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val lens = createNullableLens(nullableLensSpec.getRead(), nullableLensSpec.getWrite(), subCreator);
        return lens;
    }
    
    //== Optional ==
    
    public static <HOST, TYPE, SUBLENS extends Lens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> 
        createOptionalLens(
            Function<HOST, Optional<TYPE>>          read,
            WriteLens<HOST, Optional<TYPE>>         write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        val lens = (OptionalLens<HOST, TYPE, SUBLENS>)()->spec;
        return lens;
    }
    
    public static <HOST, TYPE, SUBLENS extends Lens<HOST, TYPE>> OptionalLens<HOST, TYPE, SUBLENS> 
        createOptionalLens(
            LensSpec<HOST, Optional<TYPE>> spec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val lens = createOptionalLens(spec.getRead(), spec.getWrite(), subCreator);
        return lens;
    }
    
    //== MayBe ==
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> MayBeLens<HOST, TYPE, SUBLENS> 
        createMayBeLens(
            Function<HOST, MayBe<TYPE>>          read,
            WriteLens<HOST, MayBe<TYPE>>         write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        val lens = (MayBeLens<HOST, TYPE, SUBLENS>)()->spec;
        return lens;
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> MayBeLens<HOST, TYPE, SUBLENS> 
        createMayBeLens(
            LensSpec<HOST, MayBe<TYPE>> spec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val lens = createMayBeLens(spec.getRead(), spec.getWrite(), subCreator);
        return lens;
    }
    
    //== List ==
    
    public static <TYPE, SUBACCESS extends AnyAccess<List<TYPE>, TYPE>, SUBLENS extends Lens<List<TYPE>, TYPE>> ListLens<List<TYPE>, TYPE, SUBLENS> 
        theList(LensCreator<List<TYPE>, TYPE, SUBACCESS, SUBLENS> subCreator) {
        LensSpecParameterized<List<TYPE>, List<TYPE>, TYPE, SUBLENS> spec
                = Lenses.createLensSpecParameterized(l -> l, (l, n)->n, subCreator::newLenes);
        ListLens<List<TYPE>, TYPE, SUBLENS> listLens = ListLens.of(spec);
        return listLens;
    }
    
    public static <HOST, TYPE, SUBLENS extends Lens<HOST, TYPE>> ListLens<HOST, TYPE, SUBLENS> 
        createListLens(
            Function<HOST, List<TYPE>>              read,
            WriteLens<HOST, List<TYPE>>             write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = Lenses.createLensSpecParameterized(read, write, subCreator);
        val listLens = ListLens.of(spec);
        return listLens;
    }
    
    public static <HOST, TYPE, TYPELENS extends Lens<HOST, TYPE>> ListLens<HOST, TYPE, TYPELENS>
            createSubListLens(
                LensSpec<HOST, List<TYPE>>                              spec,
                LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> specParameterized,
                Function<HOST, List<TYPE>>                              read) {
        val newSpec = new LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS>() {
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

    public static <HOST, KEY, VALUE, KEYLENS extends Lens<HOST,KEY>, VALUELENS extends Lens<HOST,VALUE>>
            MapLens<HOST, KEY, VALUE, KEYLENS, VALUELENS> of(
                    Function<HOST,  Map<KEY, VALUE>>           read,
                    WriteLens<HOST, Map<KEY, VALUE>>           write,
                    Function<LensSpec<HOST, KEY>,   KEYLENS>   keyLensCreator,
                    Function<LensSpec<HOST, VALUE>, VALUELENS> valueLensCreator) {
        return MapLens.of(read, write, keyLensCreator, valueLensCreator);
    }
    
    public static <KEYLENS extends Lens<HOST, KEY>, HOST, VALUELENS extends Lens<HOST, VALUE>, KEY, VALUE>
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
    
    //== FunctionalList ==
    
    public static <HOST, TYPE, SUBLENS extends Lens<HOST, TYPE>> FunctionalListLens<HOST, TYPE, SUBLENS> 
        createFunctionalListLens(
            Function<HOST,  FunctionalList<TYPE>>   read,
            WriteLens<HOST, FunctionalList<TYPE>>   write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = Lenses.createLensSpecParameterized(read, write, subCreator);
        val listLens = FunctionalListLens.of(spec);
        return listLens;
    }
    
    public static <HOST, TYPE, TYPELENS extends Lens<HOST, TYPE>> FunctionalListLens<HOST, TYPE, TYPELENS>
            createSubFunctionalListLens(
                LensSpec<HOST, FunctionalList<TYPE>>                              spec,
                LensSpecParameterized<HOST, FunctionalList<TYPE>, TYPE, TYPELENS> specParameterized,
                Function<HOST, FunctionalList<TYPE>>                              read) {
        val newSpec = new LensSpecParameterized<HOST, FunctionalList<TYPE>, TYPE, TYPELENS>() {
            @Override
            public LensSpec<HOST, FunctionalList<TYPE>> getSpec() {
                return new LensSpec<>(read, spec.getWrite(), spec.getIsNullSafe());
            }
            @Override
            public TYPELENS createSubLens(LensSpec<HOST, TYPE> subSpec) {
                return specParameterized.createSubLens(subSpec);
            }
        };
        return () -> newSpec;
    }

    public static final class __internal__ {
        private static final LensCreator<Object, String, StringAccess<Object>, StringLens<Object>> lensCreatorString
        = new LensCreator<Object, String, StringAccess<Object>, StringLens<Object>>() {
            @Override
            public StringLens<Object> newLenes(LensSpec<Object, String> spec) {
                return StringLens.of(spec);
            }
            
            @Override
            public StringAccess<Object> newAccess(Function<Object, String> accessToValue) {
                return accessToValue::apply;
            }
        };
    }
}
