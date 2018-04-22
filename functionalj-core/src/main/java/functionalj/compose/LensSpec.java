package functionalj.compose;

import java.util.function.BiFunction;
import java.util.function.Function;

public class LensSpec<TYPE, SUB> {

    private final BiFunction<TYPE, SUB, TYPE> read;
    private final Function<TYPE, SUB>         change;
    
    public LensSpec(BiFunction<TYPE, SUB, TYPE> setter, Function<TYPE, SUB> getter) {
        this.read   = setter;
        this.change = getter;
    }
    public Function<TYPE, SUB> read() {
        return change;
    }
    public BiFunction<TYPE, SUB, TYPE> change() {
        return read;
    }
    
}
