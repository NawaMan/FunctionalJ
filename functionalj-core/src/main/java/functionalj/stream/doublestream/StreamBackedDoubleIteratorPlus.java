package functionalj.stream.doublestream;

import java.util.PrimitiveIterator;
import java.util.stream.DoubleStream;

public class StreamBackedDoubleIteratorPlus implements DoubleIteratorPlus {
    
    private final DoubleStream stream;
    private final PrimitiveIterator.OfDouble iterator;
    
    public StreamBackedDoubleIteratorPlus(DoubleStream stream) {
        this.stream = stream;
        this.iterator = stream.iterator();
    }
    
    @Override
    public PrimitiveIterator.OfDouble asIterator() {
        return iterator;
    }
    
    public void close() {
        stream.close();
    }
    
    public DoubleIteratorPlus onClose(Runnable closeHandler) {
        this.stream.onClose(closeHandler);
        return this;
    }
    
}
