package functionalj.stream.intstream;

import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

public class StreamBackedIntIteratorPlus implements IntIteratorPlus {
    
    private final IntStream stream;
    private final PrimitiveIterator.OfInt iterator;
    
    public StreamBackedIntIteratorPlus(IntStream stream) {
        this.stream = stream;
        this.iterator = stream.iterator();
    }
    
    @Override
    public PrimitiveIterator.OfInt asIterator() {
        return iterator;
    }
    
    public void close() {
        stream.close();
    }
    
    public IntIteratorPlus onClose(Runnable closeHandler) {
        this.stream.onClose(closeHandler);
        return this;
    }
    
}
