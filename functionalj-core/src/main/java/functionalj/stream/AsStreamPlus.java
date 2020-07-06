package functionalj.stream;

import java.util.stream.Stream;

public interface AsStreamPlus<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    public default Stream<DATA> stream() {
        return streamPlus().stream();
    }
    
}
