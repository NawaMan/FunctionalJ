package functionalj.list.intlist;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import functionalj.stream.intstream.GrowOnlyIntArray;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class StreamBackedIntFuncList implements IntFuncList {
    
    private static final IntBinaryOperator zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate      notZero       = (int i)          -> i  != 0;
    
    private final GrowOnlyIntArray  cache = new GrowOnlyIntArray();
    private final Spliterator.OfInt spliterator;
    
    public StreamBackedIntFuncList(IntStream stream) {
        this.spliterator = stream.spliterator();
    }
    
    @Override
    public IntFuncList lazy() {
        return this;
    }
    
    @Override
    public IntFuncList eager() {
        // Just materialize all value.
        int size = size();
        return new ImmutableIntFuncList(cache.toArray(), size);
    }
    
    @Override
    public IntStreamPlus intStream() {
        val indexRef       = new AtomicInteger(0);
        val valueConsumer  = (IntConsumer)((int v) -> cache.add(v));
        val newSpliterator = new Spliterators.AbstractIntSpliterator(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                int index = indexRef.getAndIncrement();
                
                if (fromCache(consumer, index))
                    return true;
                
                boolean hadNext = false;
                synchronized (this) {
                    if (index >= cache.length()) {
                        hadNext = spliterator.tryAdvance(valueConsumer);
                    }
                }
                if (fromCache(consumer, index))
                    return true;
                
                return hadNext;
            }
            
            private boolean fromCache(IntConsumer consumer, int index) {
                if (index >= cache.length())
                    return false;
                
                int value = cache.get(index);
                consumer.accept(value);
                return true;
            }
        };
        val newStream = StreamSupport.intStream(newSpliterator, false);
        return IntStreamPlus.from(newStream);
    }
    
    @Override
    public int hashCode() {
        return reduce(43, (hash, each) -> hash*43 + each);
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsIntFuncList))
            return false;
        
        val anotherList = (IntFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return !IntFuncList.zipOf(this, anotherList.asIntFuncList(), zeroForEquals)
                .anyMatch(notZero);
    }
    
    @Override
    public String toString() {
        return asIntFuncList().toListString();
    }
    
}