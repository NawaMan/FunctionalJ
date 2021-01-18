package functionalj.stream.doublestream;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.result.AutoCloseableResult;
import functionalj.result.Result;
import lombok.val;


public class ArrayBackedDoubleIteratorPlus implements DoubleIteratorPlus, PrimitiveIterator.OfDouble {
    
    private final double[] array;
    private final int      start;
    private final int      end;
    private final PrimitiveIterator.OfDouble iterator;
    
    private AtomicInteger current = new AtomicInteger();
    
    private volatile Runnable closeHandler = null;
    
    @SafeVarargs
    public static ArrayBackedDoubleIteratorPlus of(double ... array) {
        double[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedDoubleIteratorPlus(copiedArray);
    }
    public static ArrayBackedDoubleIteratorPlus from(double[] array) {
        double[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedDoubleIteratorPlus(copiedArray);
    }
    public static ArrayBackedDoubleIteratorPlus from(double[] array, int start, int length) {
        double[] copiedArray = Arrays.copyOf(array, array.length);
        return new ArrayBackedDoubleIteratorPlus(copiedArray, start, length);
    }
    
    ArrayBackedDoubleIteratorPlus(double[] array, int start, int length) {
        this.array = array;
        this.start = Math.max(0, Math.min(array.length - 1, start));
        this.end   = Math.max(0, Math.min(array.length    , start + length));
        this.iterator = createIterator(array);
        this.current.set(this.start - 1);
    }
    
    ArrayBackedDoubleIteratorPlus(double[] array) {
        this(array, 0, array.length);
    }
    
    private PrimitiveIterator.OfDouble createIterator(double[] array) {
        return new PrimitiveIterator.OfDouble() {
            
            @Override
            public boolean hasNext() {
                return current.incrementAndGet() < ArrayBackedDoubleIteratorPlus.this.end;
            }
            
            @Override
            public double nextDouble() {
                int index = current.get();
                if (index >= array.length)
                    throw new NoSuchElementException();
                if (index < 0)
                    throw new NoSuchElementException();
                
                return array[index];
            }
        };
    }
    
    public DoubleIteratorPlus newIterator() {
        return new ArrayBackedDoubleIteratorPlus(array, start, start + end);
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
    
    public DoubleIteratorPlus onClose(Runnable closeHandler) {
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
    public PrimitiveIterator.OfDouble asIterator() {
        return iterator;
    }
    
    public DoubleStreamPlus stream() {
        return new ArrayBackedDoubleStreamPlus(this);
    }
    
    public AutoCloseableResult<DoubleIteratorPlus> pullNext(int count) {
        val oldIndex = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        int newIndex = current.get();
        if ((newIndex >= end) && (count != 0))
            return AutoCloseableResult.from(Result.ofNoMore());
        
        return AutoCloseableResult.valueOf(new ArrayBackedDoubleIteratorPlus(array, oldIndex, oldIndex + count));
    }
    
    public <TARGET> Result<TARGET> mapNext(int count, Func1<DoubleStreamPlus, TARGET> mapper) {
        val old = current.getAndAccumulate(count, (o, n) -> o + n) + 1;
        if ((current.get() >= end) && (count != 0))
            return Result.ofNoMore();
        
        try (val iterator = new ArrayBackedDoubleIteratorPlus(array, old, old + count)){
            val stream = iterator.stream();
            val value = mapper.apply(stream);
            return Result.valueOf(value);
        }
    }
    
    public DoubleFuncList FuncList() {
        return DoubleFuncList.from(()->{
            val iterable = (DoubleIterable)()->newIterator();
            return DoubleStreamPlus.from(StreamSupport.doubleStream(iterable.spliterator(), false));
        });
    }
    
    public double[] toArray() {
        double[] copiedArray = Arrays.copyOfRange(array, start, end);
        return copiedArray;
    }
    
    public <A> A[] toArray(IntFunction<A[]> generator) {
        int length = end - start;
        A[] newArray = generator.apply(length);
        System.arraycopy(array, start, newArray, 0, length);
        return newArray;
    }
    
}
