package functionalj.lens;

@FunctionalInterface
public interface WriteLens<HOST, DATA> {
    
    public HOST apply(HOST host, DATA newValue);
    
    public default HOST applyTo(HOST host, DATA newValue) {
        return apply(host, newValue);
    }
    
}
