package functionalj.list;

import static functionalj.stream.ZipWithOption.AllowUnpaired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlusUtils;
import lombok.val;

public class StreamBackedFuncList<DATA> implements FuncList<DATA> {
    
    private final List<DATA> cache = new ArrayList<DATA>();
    private final Spliterator<DATA> spliterator;
    
    public StreamBackedFuncList(Stream<DATA> stream) {
        this.spliterator = stream.spliterator();
    }
    
    @Override
    public FuncList<DATA> lazy() {
        return this;
    }
    
    @Override
    public FuncList<DATA> eager() {
        // Just materialize all value.
        int size = size();
        return new ImmutableFuncList<DATA>(cache, size);
    }
    
    @Override
    public StreamPlus<DATA> stream() {
        val indexRef       = new AtomicInteger(0);
        val valueConsumer  = (Consumer<DATA>)((DATA v) -> cache.add(v));
        val newSpliterator = new Spliterators.AbstractSpliterator<DATA>(Long.MAX_VALUE, 0) {
            @Override
            public boolean tryAdvance(Consumer<? super DATA> consumer) {
                int index = indexRef.getAndIncrement();
                
                if (fromCache(consumer, index))
                    return true;
                
                boolean hadNext = false;
                synchronized (this) {
                    if (index >= cache.size()) {
                        hadNext = spliterator.tryAdvance(valueConsumer);
                    }
                }
                if (fromCache(consumer, index))
                    return true;
                
                return hadNext;
            }
            
            private boolean fromCache(Consumer<? super DATA> consumer, int index) {
                if (index >= cache.size())
                    return false;
                
                DATA value = cache.get(index);
                consumer.accept(value);
                return true;
            }
        };
        val newStream = StreamSupport.stream(newSpliterator, false);
        return StreamPlus.from(newStream);
    }
    
    @Override
    public int hashCode() {
        return StreamPlusUtils.hashCode(this.stream());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Collection))
            return false;
        
        val anotherList = FuncList.from((Collection)o);
        return !zipWith(anotherList, AllowUnpaired, Objects::equals)
                .findFirst(Boolean.FALSE::equals)
                .isPresent();
    }
    
    @Override
    public String toString() {
        return asFuncList().toListString();
    }
    
}
