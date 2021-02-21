package functionalj.stream.longstream;

import java.util.PrimitiveIterator;
import java.util.stream.LongStream;

public class StreamBackedLongIteratorPlus implements LongIteratorPlus {
    
    private final LongStream stream;
    private final PrimitiveIterator.OfLong iterator;
    
    public StreamBackedLongIteratorPlus(LongStream stream) {
        this.stream = stream;
        this.iterator = stream.iterator();
    }
    
    @Override
    public PrimitiveIterator.OfLong asIterator() {
        return iterator;
    }
    
    public void close() {
        stream.close();
    }
    
    public LongIteratorPlus onClose(Runnable closeHandler) {
        this.stream.onClose(closeHandler);
        return this;
    }
    
}
