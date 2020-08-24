package functionalj.streamable.longstreamable;

import functionalj.stream.longstream.AsLongStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamable;

public interface AsLongStreamable extends AsLongStreamPlus {
    
    public default LongStreamable longStreamable() {
        return ()->longStream();
    }
    
    public LongStreamPlus longStream();
    
}
