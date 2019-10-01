package functionalj.lens.lenses;

@FunctionalInterface
public interface DoubleAccessPrimitive<HOST> extends DoubleAccess<HOST> {
    
    public default Double applyUnsafe(HOST host) throws Exception {
        return applyAsDouble(host);
    }
    
}