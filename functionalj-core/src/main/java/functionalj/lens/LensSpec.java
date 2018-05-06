package functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import functionalj.functions.Func1;
import lombok.Getter;
import lombok.val;

@Getter
public class LensSpec<HOST, DATA> {
    
    public static <DATA> Function<DATA, DATA>  selfRead()  { return self->self;        }
    public static <DATA> WriteLens<DATA, DATA> selfWrite() { return (host,self)->self; }
    
    private final Function<HOST, DATA>  read;
    private final WriteLens<HOST, DATA> write;
    private final BooleanSupplier       isNullSafe;  // May regret this later .. but WTH.
    
    public static final BooleanSupplier SUPPLY_TRUE = new BooleanSupplier() {
        @Override public boolean getAsBoolean() { return true; }
        @Override public String  toString()     { return "BooleanSupplier(true)"; }
    };
    public static final BooleanSupplier SUPPLY_FALSE = new BooleanSupplier() {
        @Override public boolean getAsBoolean() { return false; }
        @Override public String  toString()     { return "BooleanSupplier(false)"; }
    };
    public static final BooleanSupplier booleanSupplierOf(boolean bool) {
        return bool ? SUPPLY_TRUE : SUPPLY_FALSE;
    }
    
    public static <DATA> LensSpec<DATA, DATA> of(Class<DATA> dataClass) {
        return new LensSpec<DATA, DATA>(selfRead(), selfWrite());
    }
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write) {
        if (write == null)
            return new LensSpec<HOST, DATA>(read::apply, null);
        
        return new LensSpec<HOST, DATA>(read::apply, write::apply);
    }
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write, boolean isNullSafe) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply, booleanSupplierOf(isNullSafe));
    }
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write, BooleanSupplier isNullSafe) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply, isNullSafe);
    }
    
    public LensSpec(Function<HOST, DATA> read, WriteLens<HOST, DATA> write) {
        this(read, write, SUPPLY_TRUE);
    }
    
    public LensSpec(Function<HOST, DATA> read, WriteLens<HOST, DATA> write, BooleanSupplier isNullSafe) {
        this.read       = read;
        this.write      = write;
        this.isNullSafe = isNullSafe;
    }
    
    public boolean isNullSafe() {
        return isNullSafe.getAsBoolean();
    }
    
    public LensSpec<HOST, DATA> withNullSafety(boolean isNullSafe) {
        return (isNullSafe && SUPPLY_TRUE.equals(this.isNullSafe)) ? this : new LensSpec<>(read, write, isNullSafe ? SUPPLY_TRUE : SUPPLY_FALSE);
    }
    
    public LensSpec<HOST, DATA> toNullSafe() {
        return SUPPLY_TRUE.equals(this.isNullSafe) ? this : new LensSpec<>(read, write, SUPPLY_TRUE);
    }
    
    public LensSpec<HOST, DATA> toNullUnsafe() {
        return SUPPLY_FALSE.equals(this.isNullSafe) ? this : new LensSpec<>(read, write, SUPPLY_FALSE);
    }
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(
            ObjectLens<HOST, DATA>              dataLens,
            Function<DATA, SUB>                 readSub,
            BiFunction<DATA, SUB, DATA>         writeSub,
            Func1<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val lensSpec    = dataLens.lensSpec();
        val hostSubSpec = lensSpec.then(LensSpec.of(readSub, writeSub, lensSpec.isNullSafe));
        return subLensCreator.apply(hostSubSpec);
    }
    
    public <SUB> LensSpec<HOST, SUB> then(LensSpec<DATA, SUB> sub) {
        return LensSpec.of(
                createSubRead (read,        sub.read,  isNullSafe),
                createSubWrite(read, write, sub.write, isNullSafe),
                isNullSafe);
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
                return null;
            
            val newValue = writeSub.apply(oldValue, newSubValue);
            val newHost  = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
}
