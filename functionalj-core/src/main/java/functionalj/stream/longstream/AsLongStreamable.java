package functionalj.stream.longstream;

import functionalj.stream.AsStreamable;

public interface AsLongStreamable extends AsStreamable<Long> {
    
    public default LongStreamable longStreamable() {
        return ()->longStream();
    }
    
    public LongStreamPlus longStream();
    
}
