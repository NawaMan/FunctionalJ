package functionalj.annotations.uniontype;


@SuppressWarnings("javadoc")
public interface Self2<T1, T2> {
    
    @SuppressWarnings("unchecked")
    public default <TARGET> TARGET asMe() {
        return (TARGET)this;
    }
    
    public static <TARGET, T1, T2> TARGET getAsMe(Self2<T1, T2> self) {
        return self == null ? null : self.asMe();
    }
    
    @SuppressWarnings("unchecked")
    public static <TARGET, T> Self1<T> of(TARGET t) {
        return (Self1<T>)t;
    }
    
}
