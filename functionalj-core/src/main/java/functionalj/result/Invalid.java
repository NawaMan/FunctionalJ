package functionalj.result;

@SuppressWarnings("javadoc")
public class Invalid {
    
    public static <D extends Validatable<D, ?>> Valid<D> valueOf(String errorMsg) {
        return new Valid<D>((D)null, errorMsg);
    }
    public static <D extends Validatable<D, ?>> Valid<D> valueOf(Exception error) {
        return new Valid<D>((D)null, error);
    }
    
}
