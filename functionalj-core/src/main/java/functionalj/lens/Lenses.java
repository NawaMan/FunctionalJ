package functionalj.lens;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class Lenses {
    
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

    public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>> 
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
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            Function<HOST, Nullable<TYPE>>          read,
            WriteLens<HOST, Nullable<TYPE>>         write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        val nullableLens = (NullableLens<HOST, TYPE, SUBLENS>)()->spec;
        return nullableLens;
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            LensSpec<HOST, Nullable<TYPE>> nullableLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createNullableLens(nullableLensSpec.getRead(), nullableLensSpec.getWrite(), subCreator);
    }
    
    //== List ==
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, TYPE, SUBLENS> 
        createListLens(
            Function<HOST, List<TYPE>>                    read,
            WriteLens<HOST, List<TYPE>>                   write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = Lenses.createLensSpecParameterized(read, write, subCreator);
        val listLens = ListLens.of(spec);
        return listLens;
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
    
    //== Map ==

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

}
