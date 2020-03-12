package functionalj.stream.longstream;

import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;

public interface LongStreamable {
    
    //== Constructor ==
    
    public static LongStreamable from(LongStreamPlus mapToLong) {
        // TODO Auto-generated method stub
        return null;
    }
    
    //== Core ==
    
    public default LongStreamable longStreamable() {
        return this;
    }
    
    public default Streamable<Long> streamable() {
        return boxed();
    }
    
    public LongStreamPlus longStream();
    
    public default StreamPlus<Long> stream() {
        return longStream().boxed();
    }
    
    public default Streamable<Long> boxed() {
        return ()->StreamPlus.from(longStream().boxed());
    }
    
}
