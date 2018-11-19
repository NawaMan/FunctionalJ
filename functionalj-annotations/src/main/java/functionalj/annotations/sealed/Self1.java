package functionalj.annotations.sealed;

@SuppressWarnings("javadoc")
public interface Self1<T> {
    
    @SuppressWarnings("unchecked")
    public default <TARGET> TARGET asMe() {
        return (TARGET)this;
    }
    
    public static <TARGET, T> TARGET getAsMe(Self1<T> self) {
        return self == null ? null : self.asMe();
    }
    
    @SuppressWarnings("unchecked")
    public static <TARGET, T> Self1<T> of(TARGET t) {
        return (Self1<T>)t;
    }
    
}
