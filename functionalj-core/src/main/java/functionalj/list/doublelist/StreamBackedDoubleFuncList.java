package functionalj.list.doublelist;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.GrowOnlyDoubleArray;
import lombok.val;

public class StreamBackedDoubleFuncList implements DoubleFuncList {
    
    private static final DoubleBinaryOperator zeroForEquals = (double i1, double i2) -> i1 == i2 ? 0 : 1;
    private static final DoublePredicate      notZero       = (double i)           -> i  != 0;
    
    private final GrowOnlyDoubleArray  cache = new GrowOnlyDoubleArray();
    private final Spliterator.OfDouble spliterator;
    
    public StreamBackedDoubleFuncList(DoubleStream stream) {
        this.spliterator = stream.spliterator();
    }
    
    @Override
    public DoubleFuncList lazy() {
        return this;
    }
    
    @Override
    public DoubleFuncList eager() {
        // Just materialize all value.
        int size = size();
        return new ImmutableDoubleFuncList(cache.toArray(), size);
    }
    
    @Override
    public DoubleStreamPlus doubleStream() {
        val indexRef       = new AtomicInteger(0);
        val valueConsumer  = (DoubleConsumer)((double v) -> cache.add(v));
        val newSpliterator = new Spliterators.AbstractDoubleSpliterator(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
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
            
            private boolean fromCache(DoubleConsumer consumer, int index) {
                if (index >= cache.length())
                    return false;
                
                double value = cache.get(index);
                consumer.accept(value);
                return true;
            }
        };
        val newStream = StreamSupport.doubleStream(newSpliterator, false);
        return DoubleStreamPlus.from(newStream);
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(reduce(43, (hash, each) -> hash*43 + each));
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsDoubleFuncList))
            return false;
        
        val anotherList = (DoubleFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return !DoubleFuncList.zipOf(this, anotherList.asDoubleFuncList(), zeroForEquals)
                .anyMatch(notZero);
    }
    
    @Override
    public String toString() {
        return asDoubleFuncList().toListString();
    }
    
}
