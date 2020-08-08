package functionalj.stream.doublestream;

import functionalj.streamable.AsStreamable;

public interface AsDoubleStreamable extends AsStreamable<Double> {
    
    public default DoubleStreamable doubleStreamable() {
        return ()->doubleStream();
    }
    
    public DoubleStreamPlus doubleStream();
    
}
