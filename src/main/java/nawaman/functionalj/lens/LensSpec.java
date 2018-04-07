package nawaman.functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.Getter;
import lombok.val;
import nawaman.functionalj.functions.Func1;

@Getter
public class LensSpec<HOST, DATA> {
    
    // TODO - 'write' lens generic types may be violated when done with reflection - HOST and HOST.
    
    public static <DATA> Function<DATA, DATA>         selfRead()  { return self->self;        }
    public static <DATA> BiFunction<DATA, DATA, DATA> selfWrite() { return (host,self)->self; }
    
    private final Function<HOST, DATA>         read;
    private final BiFunction<HOST, DATA, HOST> write;
    private final boolean                      isNullSafe;  // May regret this later .. but WTH.
    
    public LensSpec(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write) {
        this(read, write, true);
    }
    
    public LensSpec(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write, boolean isNullSafe) {
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
    
    public static <HOST, DATA>LensSpec<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply);
    }
    
    public static <HOST, DATA, SUB, SUBLENS> SUBLENS createSubLens(
            ObjectLens<HOST, DATA>              dataLens,
            Function<DATA, SUB>                 readSub,
            BiFunction<DATA, SUB, DATA>         writeSub,
            Func1<LensSpec<HOST, SUB>, SUBLENS> subLensCreator) {
        val lensSpec = dataLens.lensSpec();
        
        Function<HOST, SUB> hostSubRead = host ->{
            val read  = lensSpec.getRead();
            val value = read.apply(host);
            if (lensSpec.isNullSafe() && (value == null))
                return null;
            
            val subValue = readSub.apply(value);
            return subValue;
        };
        BiFunction<HOST, SUB, HOST> hostSubWrite = (host, newSubValue)->{
            val readValue  = lensSpec.getRead();
            val oldValue   = readValue.apply(host);
            if (lensSpec.isNullSafe() && (oldValue == null))
                return null;
            
            val newValue   = writeSub.apply(oldValue, newSubValue);
            val writeValue = lensSpec.getWrite();
            val newHost    = writeValue.apply(host, newValue);
            return newHost;
        };
        
        val hostSubSpec = LensSpec.of(hostSubRead, hostSubWrite).withNullSafety(lensSpec.isNullSafe());
        return subLensCreator.apply(hostSubSpec);
    }
    
}
