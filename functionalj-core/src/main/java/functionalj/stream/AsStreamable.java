package functionalj.stream;

public interface AsStreamable<DATA> {
    
    public default Streamable<DATA> streamable() {
        return ()->stream();
    }
    
    public StreamPlus<DATA> stream();
    
}
