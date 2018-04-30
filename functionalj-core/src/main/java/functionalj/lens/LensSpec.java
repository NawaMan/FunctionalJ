package functionalj.lens;

import java.util.function.BiFunction;
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
    private final boolean               isNullSafe;  // May regret this later .. but WTH.
    
    public LensSpec(Function<HOST, DATA> read, WriteLens<HOST, DATA> write) {
        this(read, write, true);
    }
    
    public LensSpec(Function<HOST, DATA> read, WriteLens<HOST, DATA> write, boolean isNullSafe) {
        this.read       = read;
        this.write      = write;
        this.isNullSafe = isNullSafe;
    }
    
    public boolean isNullSafe() {
        return isNullSafe;
    }
    
    public LensSpec<HOST, DATA> withNullSafety(boolean isNullSafe) {
        return (isNullSafe == this.isNullSafe) ? this : new LensSpec<>(read, write, isNullSafe);
    }
    
    public LensSpec<HOST, DATA> toNullSafe() {
        return isNullSafe ? this : new LensSpec<>(read, write, true);
    }
    
    public LensSpec<HOST, DATA> toNullUnsafe() {
        return isNullSafe ? new LensSpec<>(read, write, false) : this;
    }
    
    public static <DATA> LensSpec<DATA, DATA> of(Class<DATA> dataClass) {
        return new LensSpec<DATA, DATA>(selfRead(), selfWrite());
    }
    
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply);
    }
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write, boolean isNullSafe) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply, isNullSafe);
    }
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(
            ObjectLens<HOST, DATA>              dataLens,
            Function<DATA, SUB>                 readSub,
            BiFunction<DATA, SUB, DATA>         writeSub,
            Func1<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val lensSpec = dataLens.lensSpec();
        
        val readValue    = lensSpec.getRead();
        val writeValue   = lensSpec.getWrite();
        val isNullSafe   = lensSpec.isNullSafe();
        val hostSubRead  = createSubRead(readValue, readSub,              isNullSafe);
        val hostSubWrite = createSubWrite(readValue, writeValue, writeSub, isNullSafe);
        val hostSubSpec  = LensSpec.of(hostSubRead, hostSubWrite).withNullSafety(lensSpec.isNullSafe());
        return subLensCreator.apply(hostSubSpec);
    }
    
    public static <DATA, SUB, HOST> Function<HOST, SUB> createSubRead(
            Function<HOST, DATA> readValue,
            Function<DATA, SUB>  readSub, 
            boolean              isNullSafe) {
        return host ->{
            val value = readValue.apply(host);
            if (isNullSafe && (value == null))
                return null;
            
            val subValue = readSub.apply(value);
            return subValue;
        };
    }
    
    public static <HOST, DATA, SUB> BiFunction<HOST, SUB, HOST> createSubWrite(
            Function<HOST, DATA>        readValue,
            WriteLens<HOST, DATA>       writeValue,
            BiFunction<DATA, SUB, DATA> writeSub,
            boolean                     isNullSafe) {
        return (host, newSubValue)->{
            val oldValue = readValue.apply(host);
            if (isNullSafe && (oldValue == null))
                return null;
            
            val newValue = writeSub.apply(oldValue, newSubValue);
            val newHost  = writeValue.apply(host, newValue);
            return newHost;
        };
    }
    
}
