package functionalj.stream.doublestream;

import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;

public interface DoubleStreamable {
    
    //== Constructor ==
    
    public static DoubleStreamable from(DoubleStreamPlus mapToDouble) {
        // TODO Auto-generated method stub
        return null;
    }
    
    //== Core ==
    
    public default DoubleStreamable doubleStreamable() {
        return this;
    }
    
    public default Streamable<Double> streamable() {
        return boxed();
    }
    
    public DoubleStreamPlus doubleStream();
    
    public default StreamPlus<Double> stream() {
        return doubleStream().boxed();
    }
    
    public default Streamable<Double> boxed() {
        return ()->StreamPlus.from(doubleStream().boxed());
    }
    
}
