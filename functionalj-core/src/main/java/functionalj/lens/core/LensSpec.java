package functionalj.lens.core;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

@SuppressWarnings("javadoc")
public class LensSpec<HOST, DATA> 
            implements Function<HOST, DATA> {
    
    public static <DATA> Function<DATA, DATA>  selfRead()  { return self->self;                }
    public static <DATA> WriteLens<DATA, DATA> selfWrite() { return (self,newValue)->newValue; }
    
    private final Function<HOST, DATA>  read;
    private final WriteLens<HOST, DATA> write;
    private final BooleanSupplier       isNullSafe;  // May regret this later .. but WTH.
    
    public Function<HOST, DATA> getRead() {
        return read;
    }
    public WriteLens<HOST, DATA> getWrite() {
        return write;
    }
    public BooleanSupplier getIsNullSafe() {
        return isNullSafe;
    }
    
    
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
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read) {
        return of(read::apply, (WriteLens<HOST,DATA>)null);
    }
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, WriteLens<HOST, DATA> write) {
        if (write == null)
            return new LensSpec<HOST, DATA>(read::apply, null);
        
        return new LensSpec<HOST, DATA>(read::apply, write::apply);
    }
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, WriteLens<HOST, DATA> write, boolean isNullSafe) {
        return new LensSpec<HOST, DATA>(read::apply, write::apply, booleanSupplierOf(isNullSafe));
    }
    public static <HOST, DATA> LensSpec<HOST, DATA> of(Function<HOST, DATA> read, WriteLens<HOST, DATA> write, BooleanSupplier isNullSafe) {
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
    
    public DATA apply(HOST host) {
        return read.apply(host);
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
    
    public <SUB> LensSpec<HOST, SUB> then(LensSpec<DATA, SUB> sub) {
        return LensSpec.of(
                LensUtils.createSubRead (read,        sub.read,  isNullSafe),
                LensUtils.createSubWrite(read, write, sub.write, isNullSafe),
                isNullSafe);
    }
    
}
