package functionalj.lens;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class Lenses {
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(
            ObjectLens<HOST, DATA>                 dataLens,
            Function<DATA, SUB>                    readSub,
            BiFunction<DATA, SUB, DATA>            writeSub,
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
    
    public static <HOST, DATA, SUB> BiFunction<HOST, SUB, HOST> createSubWrite(
            Function<HOST, DATA>         readValue,
            WriteLens<HOST, DATA>        writeValue,
            BiFunction<DATA, SUB, DATA>  writeSub,
            BooleanSupplier              isNullSafe) {
        return createSubWrite(readValue, writeValue, WriteLens.of(writeSub), isNullSafe);
    }
    
    public static <HOST, DATA, SUB> BiFunction<HOST, SUB, HOST> createSubWrite(
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
            BiFunction<HOST, TYPE, HOST>           write,
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
            BiFunction<HOST, Nullable<TYPE>, HOST>  write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = createLensSpecParameterized(read, write, subCreator);
        val nullableLens = (NullableLens<HOST, TYPE, SUBLENS>)()->spec;
        return nullableLens;
    }
    
    public static <HOST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> NullableLens<HOST, TYPE, SUBLENS> 
        createNullableLens(
            LensSpec<HOST, Nullable<TYPE>> nullableLensSpec,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return createNullableLens(nullableLensSpec.getRead(), nullableLensSpec.getWrite().toBiFunction(), subCreator);
    }
    
    //== List ==
    
    public static <HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, LIST, TYPE, SUBLENS> 
        createListLens(
            Function<HOST, LIST>                    read,
            BiFunction<HOST, LIST, HOST>            write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = Lenses.createLensSpecParameterized(read, write, subCreator);
        val listLens = (ListLens<HOST, LIST, TYPE, SUBLENS>)()->spec;
        return listLens;
    }

}
