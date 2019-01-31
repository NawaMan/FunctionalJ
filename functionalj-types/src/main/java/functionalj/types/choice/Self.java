package functionalj.types.choice;

@SuppressWarnings("javadoc")
public interface Self {

    @SuppressWarnings("unchecked")
    public default <TARGET> TARGET asMe() {
        return (TARGET)this;
    }
    
    public static <TARGET> TARGET getAsMe(Self self) {
        return self == null ? null : self.asMe();
    }
    
    public static <TARGET> Self of(TARGET t) {
        return (Self)t;
    }
}
