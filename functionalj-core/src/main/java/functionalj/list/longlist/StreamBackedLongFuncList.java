package functionalj.list.longlist;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import functionalj.list.FuncList.Mode;
import functionalj.stream.longstream.GrowOnlyLongArray;
import functionalj.stream.longstream.LongStreamPlus;
import lombok.NonNull;
import lombok.val;

public class StreamBackedLongFuncList implements LongFuncList {
    
    private static final LongBinaryOperator zeroForEquals = (long i1, long i2) -> i1 == i2 ? 0 : 1;
    private static final LongPredicate      notZero       = (long i)           -> i  != 0;

    private final Mode               mode;
    private final GrowOnlyLongArray  cache = new GrowOnlyLongArray();
    private final Spliterator.OfLong spliterator;

    StreamBackedLongFuncList(@NonNull LongStream stream, @NonNull Mode mode) {
        this.spliterator = stream.spliterator();
        this.mode = mode;
        
        if (mode.isEager()) {
            size();
        }
    }
    
    public StreamBackedLongFuncList(@NonNull LongStream stream) {
        this(stream, Mode.cache);
    }
    
    public Mode mode() {
        return mode;
    }
    
    @Override
    public LongFuncList toLazy() {
        if (mode.isLazy()) {
            return this;
        }
        return new StreamBackedLongFuncList(longStreamPlus(), Mode.lazy);
    }
    
    @Override
    public LongFuncList toEager() {
        // Just materialize all value.
        int size = size();
        return new ImmutableLongFuncList(cache, size, Mode.eager);
    }
    
    @Override
    public LongFuncList toCache() {
        if (mode.isCache()) {
            return this;
        }
        return new StreamBackedLongFuncList(longStreamPlus(), Mode.cache);
    }
    
    @Override
    public LongStreamPlus longStream() {
        val indexRef       = new AtomicInteger(0);
        val valueConsumer  = (LongConsumer)((long v) -> cache.add(v));
        val newSpliterator = new Spliterators.AbstractLongSpliterator(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(LongConsumer consumer) {
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
            
            private boolean fromCache(LongConsumer consumer, int index) {
                if (index >= cache.length())
                    return false;
                
                long value = cache.get(index);
                consumer.accept(value);
                return true;
            }
        };
        val newStream = StreamSupport.longStream(newSpliterator, false);
        return LongStreamPlus.from(newStream);
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(reduce(43, (hash, each) -> hash*43 + each));
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsLongFuncList))
            return false;
        
        val anotherList = (LongFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return !LongFuncList.zipOf(this, anotherList.asLongFuncList(), zeroForEquals)
                .anyMatch(notZero);
    }
    
    @Override
    public String toString() {
        return asLongFuncList().toListString();
    }
    
}
