package functionalj.stream.intstream;

@FunctionalInterface
public interface AsIntStreamProcessor<TARGET> {
    
    IntStreamProcessor<TARGET> asIntStreamProcessor();
    
}
