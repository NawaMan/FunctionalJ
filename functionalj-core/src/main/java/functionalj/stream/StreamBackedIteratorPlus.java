package functionalj.stream;

import java.util.Iterator;
import java.util.stream.Stream;

public class StreamBackedIteratorPlus<DATA> implements IteratorPlus<DATA> {

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
    }

    public IteratorPlus<DATA> onClose(Runnable closeHandler) {
        this.stream.onClose(closeHandler);
        return this;
    }
}
