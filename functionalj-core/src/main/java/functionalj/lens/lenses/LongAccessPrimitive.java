package functionalj.lens.lenses;

@FunctionalInterface
public interface LongAccessPrimitive<HOST> extends LongAccess<HOST> {
    
    public default Long applyUnsafe(HOST host) throws Exception {
        return applyAsLong(host);
    }
    
}