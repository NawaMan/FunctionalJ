package functionalj.ref;

public class DefaultValue {
    
    public static <D> D of(Class<D> clzz) {
        return RefTo.defaultProvider.value().get(clzz);
    }
    
}
