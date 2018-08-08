package functionalj.annotations.uniontype;

public class UnionTypes {
    
    private UnionTypes() {}
    
    public static <S> S Switch(AbstractUnionType<S> union) {
        return union.__switch();
    }
    
}
