package nawaman.functionalj.functions;

import java.util.Objects;

public class ObjectFunctions {
    
    public static <I1, I2> Func2<I1, I2, Boolean> objEquals() {
        return (i1, i2) -> Objects.equals(i1, i2);
    }
    public static <I> Func1<I, Boolean> objEqualsTo(I i1) {
        return i -> Objects.equals(i1, i);
    }
    
}
