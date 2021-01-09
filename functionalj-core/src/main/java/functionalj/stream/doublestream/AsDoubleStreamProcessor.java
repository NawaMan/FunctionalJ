package functionalj.stream.doublestream;

public interface AsDoubleStreamProcessor<TARGET> {
    
    public DoubleStreamProcessor<TARGET> asDoubleStreamProcessor();
    
}
