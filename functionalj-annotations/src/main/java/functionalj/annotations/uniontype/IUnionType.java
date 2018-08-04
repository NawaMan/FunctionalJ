package functionalj.annotations.uniontype;

public abstract class IUnionType<S> {
    
    public abstract S __switch();
    
    public static <S> S Switch(IUnionType<S> union) {
        return union.__switch();
    }
    
}
