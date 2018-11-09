package functionalj.stream;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.result.Result;
import lombok.val;

@FunctionalInterface
public interface IteratorPlus<DATA> extends Iterator<DATA> {
    
    public static <D> IteratorPlus<D> of(Iterator<D> iterator) {
        if (iterator instanceof IteratorPlus)
            return (IteratorPlus<D>)iterator;
        
        return (IteratorPlus<D>)(()->iterator);
    }
    public static <D> IteratorPlus<D> from(Stream<D> stream) {
        return of(stream.iterator());
    }
    
    public Iterator<DATA> asIterator();
    
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.of(iterator());
    }
    
    @Override
    public default boolean hasNext() {
        return asIterator().hasNext();
    }
    
    @Override
    public default DATA next() {
        return asIterator().next();
    }
    
    public default StreamPlus<DATA> stream() {
        val iterable = (Iterable<DATA>)()->this;
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    public default Result<DATA> pullNext() {
        if (hasNext())
             return Result.of(next());
        else return Result.ofNoMore();
    }
    
    @SuppressWarnings("unchecked")
    public default Result<IteratorPlus<DATA>> pullNext(int count) {
        Object[] array = stream().limit(count).toArray();
        if ((array.length == 0) && count != 0)
            return Result.ofNoMore();
        
        val iterator = (ArrayBackedIteratorPlus<DATA>)new ArrayBackedIteratorPlus<Object>(array);
        return Result.value(iterator);
    }
    
    public default IteratorPlus<DATA> useNext(FuncUnit1<DATA> usage) {
        if (hasNext()) {
            val next = next();
            usage.accept(next);
        }
        
        return this;
    }
    
    public default IteratorPlus<DATA> useNext(int count, FuncUnit1<StreamPlus<DATA>> usage) {
        Object[] array = stream().limit(count).toArray();
        if ((array.length != 0) || count == 0) {
            @SuppressWarnings("unchecked")
            val iterator = (ArrayBackedIteratorPlus<DATA>)new ArrayBackedIteratorPlus<Object>(array);
            val stream   = iterator.stream();
            usage.accept(stream);
        }
        
        return this;
    }
    
    public default <TARGET> Result<TARGET> mapNext(Func1<DATA, TARGET> mapper) {
        if (hasNext()) {
            val next  = next();
            val value = mapper.apply(next);
            return Result.of(value);
        } else {
            return Result.ofNoMore();
        }
    }
    
    public default <TARGET> Result<TARGET> mapNext(int count, Func1<StreamPlus<DATA>, TARGET> mapper) {
        val array = stream().limit(count).toArray();
        if ((array.length == 0) && (count != 0))
            return Result.ofNoMore();
        
        @SuppressWarnings("unchecked")
        val input  = (IteratorPlus<DATA>)ArrayBackedIteratorPlus.from(array);
        val stream = input.stream();
        val value = mapper.apply(stream);
        return Result.of(value);
    }
    
}
