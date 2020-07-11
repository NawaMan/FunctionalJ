package functionalj.stream;

public interface AsStreamable<DATA> {
    
    public default Streamable<DATA> streamable() {
        return (Streamable<DATA>)(()->stream());
    }
    
    public StreamPlus<DATA> stream();
    
}
