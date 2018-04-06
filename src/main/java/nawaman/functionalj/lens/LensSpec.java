package nawaman.functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.Getter;
import nawaman.functionalj.functions.Func1;

@Getter
public class LensSpec<HOST, DATA> {
    
    // TODO - write lens generic types may be violated when done with reflection.
    
    public static <DATA> Function<DATA, DATA>         selfRead()  { return self->self;        }
    public static <DATA> BiFunction<DATA, DATA, DATA> selfWrite() { return (host,self)->self; }
    
    private final Function<HOST, DATA>   read;
    private final BiFunction<HOST, DATA, HOST> write;
    
    public LensSpec(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write) {
        this.read = read;
        this.write = write;
    }
    
    public static <DATA> LensSpec<DATA, DATA> of(Class<DATA> dataClass) {
        return new LensSpec<DATA, DATA>(selfRead(), selfWrite());
    }
    
    public static <HOST, DATA>LensSpec<HOST, DATA> of(Function<HOST, DATA> read, BiFunction<HOST, DATA, HOST> write) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply);
    }
    
}
