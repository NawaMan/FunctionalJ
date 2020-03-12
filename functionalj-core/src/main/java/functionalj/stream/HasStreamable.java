package functionalj.stream;

public interface HasStreamable<DATA> {
    
    public default Streamable<DATA> streamable() {
        return ()->stream();
    }
    
    public StreamPlus<DATA> stream();
    
}
