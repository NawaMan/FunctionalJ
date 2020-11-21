package functionalj.stream;

import functionalj.OnClosable;

import java.util.Iterator;
import java.util.stream.Stream;

public class StreamBackedIteratorPlus<DATA> extends OnClosable<IteratorPlus<DATA>> implements IteratorPlus<DATA> {
    
    private final Stream<DATA> stream;
    private final Iterator<DATA> iterator;
    
    public StreamBackedIteratorPlus(Stream<DATA> stream) {
        this.stream = stream;
        this.iterator = stream.iterator();
    }
    
    @Override
    public Iterator<DATA> asIterator() {
        return iterator;
    }
    
    public void close() {
        stream.close();
        if (iterator instanceof IteratorPlus) {
            ((IteratorPlus<DATA>) iterator).close();
        }
        super.close();
    }
    
    public IteratorPlus<DATA> onClose(Runnable closeHandler) {
        this.stream.onClose(closeHandler);
        super.onClose(closeHandler);
        return this;
    }
    
}