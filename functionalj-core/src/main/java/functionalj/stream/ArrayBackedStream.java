package functionalj.stream;

import java.util.function.IntFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.val;

// This class along with ArrayBackedIteratorPlus helps improve performance when do pullNext, useNext and mapNext 
//   with multiple value to run faster.
public class ArrayBackedStream<DATA> implements StreamPlus<DATA> {
    
    private final ArrayBackedIteratorPlus<DATA> iterator;
    private final StreamPlus<DATA>              stream;
    
    @SafeVarargs
    public static <DATA> ArrayBackedStream<DATA> of(DATA ... array) {
        val iterator = ArrayBackedIteratorPlus.of(array);
        val stream   = new ArrayBackedStream<DATA>(iterator);
        return stream;
    }
    public static <DATA> ArrayBackedStream<DATA> from(DATA[] array) {
        val iterator = ArrayBackedIteratorPlus.of(array);
        val stream   = new ArrayBackedStream<DATA>(iterator);
        return stream;
    }
    public static <DATA> ArrayBackedStream<DATA> from(DATA[] array, int start, int length) {
        @SuppressWarnings("unchecked")
        val iterator = (ArrayBackedIteratorPlus<DATA>)ArrayBackedIteratorPlus.of(array, start, length);
        val stream   = new ArrayBackedStream<DATA>(iterator);
        return stream;
    }
    
    ArrayBackedStream(ArrayBackedIteratorPlus<DATA> iterator) {
        this.iterator = iterator;
        
        val iterable = (Iterable<DATA>)()->iterator;
        this.stream  = StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    public Stream<DATA> stream() {
        return stream;
    }
    
    public IteratorPlus<DATA> iterator() {
        return iterator;
    }
    @Override
    public DATA[] toArray() {
        return iterator.toArray();
    }
    
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return iterator.toArray(generator);
    }
    
}
