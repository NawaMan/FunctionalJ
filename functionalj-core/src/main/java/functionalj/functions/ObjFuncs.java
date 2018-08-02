package functionalj.functions;

import java.util.Objects;

@SuppressWarnings("javadoc")
public class ObjFuncs {
    
    @SuppressWarnings("unlikely-arg-type")
    public static <I1, I2> Func2<I1, I2, Boolean> areEqual() {
        return (i1, i2) -> Objects.equals(i1, i2);
    }
    
    public static <I> Func1<I, Boolean> equalsTo(I i1) {
        
        return i -> Objects.equals(i1, i);
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> lessThan(I i1) {
        return i -> i.compareTo(i1) < 0;
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> greaterThan(I i1) {
        return i -> i.compareTo(i1) > 0;
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> lessThanOrEqualsTo(I i1) {
        return i -> i.compareTo(i1) <= 0;
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> greaterThanOrEqualsTo(I i1) {
        return i -> i.compareTo(i1) >= 0;
    }
    
}
