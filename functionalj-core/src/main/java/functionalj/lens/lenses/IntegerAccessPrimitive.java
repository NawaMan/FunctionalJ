package functionalj.lens.lenses;


@FunctionalInterface
public interface IntegerAccessPrimitive<HOST> extends IntegerAccess<HOST> {
    
    public default Integer applyUnsafe(HOST host) throws Exception {
        return applyAsInt(host);
    }
    
}