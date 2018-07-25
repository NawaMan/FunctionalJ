package functionalj.pipeable;

import java.util.function.Supplier;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface WhenNull<INPUT, OUTPUT> extends NullSafeOperator<INPUT, OUTPUT> {

    public OUTPUT applyUnsafe(INPUT input) throws Exception;
    
    
    public static <I> WhenNull<I, I> defaultTo(I defaultValue) {
        return input -> {
            return (input != null) ? input : defaultValue;
        };
    }
    public static <I> WhenNull<I, I> defaultFrom(Supplier<I> defaultSupplier) {
        return input -> {
            return (input != null) ? input : defaultSupplier.get();
        };
    }
    public static <I> WhenNull<I, I> throwNullPointerException() {
        return input -> {
            if (input == null)
                throw new NullPointerException();
            
            return input;
        };
    }
    public static <I> WhenNull<I, I> throwNullPointerException(String message) {
        return input -> {
            if (input == null)
                throw new NullPointerException(message);
            
            return input;
        };
    }
    
}
