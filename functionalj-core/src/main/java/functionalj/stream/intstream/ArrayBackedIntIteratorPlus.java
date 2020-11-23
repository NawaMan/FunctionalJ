package functionalj.stream.intstream;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.result.AutoCloseableResult;
import functionalj.result.Result;
import functionalj.streamable.intstreamable.IntStreamable;


public class ArrayBackedIntIteratorPlus implements IntIteratorPlus, PrimitiveIterator.OfInt {
    
    private final int[] array;
    private final int   start;
    private final int   end;
    private final PrimitiveIterator.OfInt iterator;
    
    private AtomicInteger current = new AtomicInteger();
    
    private volatile Runnable closeHandler = null;
    
    @SafeVarargs
    public static ArrayBackedIntIteratorPlus of(int ... array) {
        int[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIntIteratorPlus(copiedArray);
    }
    public static ArrayBackedIntIteratorPlus from(int[] array) {
        int[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIntIteratorPlus(copiedArray);
    }
    public static ArrayBackedIntIteratorPlus from(int[] array, int start, int length) {
        int[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedIntIteratorPlus(copiedArray, start, length);
    }
    
    ArrayBackedIntIteratorPlus(int[] array, int start, int length) {
        this.array = array;
        this.start = Math.max(0, Math.min(array.length - 1, start));
        this.end   = Math.max(0, Math.min(array.length    , start + length));
        this.iterator = createIterator(array);
        this.current.set(this.start - 1);
    }
    
    ArrayBackedIntIteratorPlus(int[] array) {
        this(array, 0, array.length);
    }
    
    private PrimitiveIterator.OfInt createIterator(int[] array) {
        return new PrimitiveIterator.OfInt() {
            
            @Override
            public boolean hasNext() {
                return current.incrementAndGet() < ArrayBackedIntIteratorPlus.this.end;
            }
            
            @Override
            public int nextInt() {
                int index = current.get();
                if (index >= array.length)
                    throw new NoSuchElementException();
                if (index < 0)
                    throw new NoSuchElementException();
                
                return array[index];
            }
        };
    }
    
    public IntIteratorPlus newIterator() {
        return new ArrayBackedIntIteratorPlus(array, start, start + end);
    }
    
    public int getStart() {
        return start;
    }
    
    public int getLength() {
        return end - start;
    }
    
    public void close() {
        if (this.closeHandler != null) {
            this.closeHandler.run();
        }
    }
    
    public IntIteratorPlus onClose(Runnable closeHandler) {
        if (closeHandler != null) {
            synchronized (this) {
                if (this.closeHandler == null) {
                    this.closeHandler = closeHandler;
                } else {
                    val thisCloseHandler = this.closeHandler;
                    this.closeHandler = new Runnable() {
                        @Override
                        public void run() {
                            thisCloseHandler.run();
                            closeHandler.run();
                        }
                    };
                }
            }
        }
        return this;
    }
    
    @Override
    public PrimitiveIterator.OfInt asIterator() {
        return iterator;
    }
    
    public IntStreamPlus stream() {
        return new ArrayBackedIntStreamPlus(this);
    }
    
    public AutoCloseableResult<IntIteratorPlus> pullNext(int count) {
        val oldIndex = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        int newIndex = current.get();
        if ((newIndex >= end) && (count != 0))
            return AutoCloseableResult.from(Result.ofNoMore());
        
        return AutoCloseableResult.valueOf(new ArrayBackedIntIteratorPlus(array, oldIndex, oldIndex + count));
    }
    
    public <TARGET> Result<TARGET> mapNext(int count, Func1<IntStreamPlus, TARGET> mapper) {
        val old = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        if ((current.get() >= end) && (count != 0))
            return Result.ofNoMore();
        
        try (var iterator = new ArrayBackedIntIteratorPlus(array, old, old + count)){
            val stream = iterator.stream();
            val value = mapper.apply(stream);
            return Result.valueOf(value);
        }
    }
    
    public IntStreamable streamable() {
        return (IntStreamable)()->{
            val iterable = (IntIterable)()->newIterator();
            return IntStreamPlus.from(StreamSupport.intStream(iterable.spliterator(), false));
        };
    }
    
    public int[] toArray() {
        int[] copiedArray = Arrays.copyOfRange(array, start, end);
        return copiedArray;
    }
    
    public <A> A[] toArray(IntFunction<A[]> generator) {
        int length = end - start;
        A[] newArray = generator.apply(length);
        System.arraycopy(array, start, newArray, 0, length);
        return newArray;
    }
    
}
