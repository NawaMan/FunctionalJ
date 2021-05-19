package functionalj.function.aggregator;

import functionalj.stream.doublestream.DoubleAggregator;

public interface AsDoubleAggregator<TARGET> {
    
    public DoubleAggregator<TARGET> asDoubleAggregator();
    
}
