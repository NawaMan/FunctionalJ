package functionalj.types.result;

public class Invalid {
    
    public static <D extends Validatable<D, ?>> Valid<D> valueOf(String errorMsg) {
        return new Valid<D>((D)null, new ValidationException(errorMsg));
    }
    public static <D extends Validatable<D, ?>> Valid<D> valueOf(Exception error) {
        return new Valid<D>((D)null, new ValidationException(error));
    }
    
}
