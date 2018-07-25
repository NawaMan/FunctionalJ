package functionalj.lens.lenses;

import java.util.function.Predicate;

import functionalj.lens.core.LensSpec;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface BooleanLens<HOST>
        extends
            BooleanAccess<HOST>,
            AnyLens<HOST, Boolean> , Predicate<HOST> {
    
    public static <HOST> BooleanLens<HOST> of(LensSpec<HOST, Boolean> spec) {
        return () -> spec;
    }
    
    public default boolean test(HOST host) {
        return Boolean.TRUE.equals(this.apply(host));
    }
    
    @Override
    default Boolean applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }
    
}
