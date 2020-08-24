package functionalj.streamable.doublestreamable;

import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.DoubleStreamable;
import functionalj.streamable.AsStreamable;

public interface AsDoubleStreamable extends AsStreamable<Double> {
    
    public default DoubleStreamable doubleStreamable() {
        return ()->doubleStream();
    }
    
    public DoubleStreamPlus doubleStream();
    
}
