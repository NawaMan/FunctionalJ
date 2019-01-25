package functionalj.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.result.Result;
import lombok.val;

public class ArrayBackedIteratorPlus<DATA> implements IteratorPlus<DATA> {
    
    private final DATA[] array;
    private final int    start;
    private final int    end;
    private final Iterator<DATA> iterator;
    
    private AtomicInteger current = new AtomicInteger();
    
    @SafeVarargs
    public static <DATA> ArrayBackedIteratorPlus<DATA> of(DATA ... array) {
        DATA[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIteratorPlus<DATA>(copiedArray);
    }
    public static <DATA> ArrayBackedIteratorPlus<DATA> from(DATA[] array) {
        DATA[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIteratorPlus<DATA>(copiedArray);
    }
    public static <DATA> ArrayBackedIteratorPlus<DATA> from(DATA[] array, int start, int length) {
        DATA[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIteratorPlus<DATA>(copiedArray, start, length);
    }
    
    ArrayBackedIteratorPlus(DATA[] array, int start, int length) {
        this.array = array;
        this.start = Math.max(0, Math.min(array.length - 1, start));
        this.end   = Math.max(0, Math.min(array.length    , start + length));
        this.iterator = new Iterator<DATA>() {
            @Override
            public boolean hasNext() {
                return current.incrementAndGet() < ArrayBackedIteratorPlus.this.end;
            }
            @Override
            public DATA next() {
                int index = current.get();
                if (index >= array.length)
                    throw new NoSuchElementException();
                if (index < 0)
                    throw new NoSuchElementException();
                
                return array[index];
            }
        };
        this.current.set(this.start - 1);
    }
    ArrayBackedIteratorPlus(DATA[] array) {
        this(array, 0, array.length);
    }
    
    public IteratorPlus<DATA> newIterator() {
        return new ArrayBackedIteratorPlus<>(array, start, start + end);
    }
    
    public int getStart() {
        return start;
    }
    
    public int getLength() {
        return end - start;
    }
    
    @Override
    public Iterator<DATA> asIterator() {
        return iterator;
    }
    
    public StreamPlus<DATA> stream() {
        return new ArrayBackedStream<DATA>(this);
    }
    
    public Result<IteratorPlus<DATA>> pullNext(int count) {
        val oldIndex = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        int newIndex = current.get();
        if ((newIndex >= end) && (count != 0))
            return Result.ofNoMore();
        
        return Result.valueOf(new ArrayBackedIteratorPlus<DATA>(array, oldIndex, oldIndex + count));
    }
    
    public <TARGET> Result<TARGET> mapNext(int count, Func1<StreamPlus<DATA>, TARGET> mapper) {
        val old = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        if ((current.get() >= end) && (count != 0))
            return Result.ofNoMore();
        
        val stream = new ArrayBackedIteratorPlus<DATA>(array, old, old + count).stream();
        val value = mapper.apply(stream);
        return Result.valueOf(value);
    }
    
    public Streamable<DATA> streamable() {
        return (Streamable<DATA>)()->{
            val iterable = (Iterable<DATA>)()->newIterator();
            return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
        };
    }
    
    public DATA[] toArray() {
        DATA[] copiedArray = Arrays.copyOfRange(array, start, end);
        return copiedArray;
    }
    
    public <A> A[] toArray(IntFunction<A[]> generator) {
        int length = end - start;
        A[] newArray = generator.apply(length);
        System.arraycopy(array, start, newArray, 0, length);
        return newArray;
    }
    
}
