package functionalj.stream.longstream;

import functionalj.streamable.AsStreamable;

public interface AsLongStreamable extends AsStreamable<Long> {
    
    public default LongStreamable longStreamable() {
        return ()->longStream();
    }
    
    public LongStreamPlus longStream();
    
}
