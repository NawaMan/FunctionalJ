package functionalj.types;

import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.val;

public interface IList<DATA, SELF extends IList<DATA, SELF>> extends List<DATA>, ICanStream<DATA, SELF> {
    
    @Override
    public <TARGET, TARGET_SELF extends ICanStream<TARGET, TARGET_SELF>> 
            TARGET_SELF stream(Function<Stream<DATA>, Stream<TARGET>> action);
    
    
    @Override
    public Stream<DATA> stream();
    
    public ImmutableList<DATA> toImmutableList();
    
    
    @Override
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    //== Access list ==
    
    public default int size() {
        return (int)stream().count();
    }

    public default boolean isEmpty() {
        return Helper.hasFirst(stream());
    }

    public default boolean contains(Object o) {
        return Helper.hasFirst(stream().filter(each -> Objects.equals(each, o)));
    }
    
    public default boolean contains(Predicate<DATA> predicate) {
        return Helper.hasFirst(stream().filter(predicate));
    }
    
    public default boolean containsAll(Collection<?> c) {
        return c.stream()
                .allMatch(each -> stream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default Object[] toArray() {
        return stream().toArray();
    }

    public <T> T[] toArray(T[] a);
    
    public DATA get(int index);
    
    public int indexOf(Object o) ;

    public int lastIndexOf(Object o);

    public ListIterator<DATA> listIterator();

    public ListIterator<DATA> listIterator(int index);

    @Override
    public SELF subList(int fromIndexInclusive, int toIndexExclusive);
    
    @Override
    public default Spliterator<DATA> spliterator() {
        return ICanStream.super.spliterator();
    }
    
    public default DATA first() {
        val valueRef = new AtomicReference<DATA>();
        if (!Helper.hasFirst(stream(), valueRef)) {
            throw new IndexOutOfBoundsException("1");
        }
        
        return valueRef.get();
    }
    
    public default SELF rest() {
        return stream(stream -> stream.skip(1));
    }
    
    //== Modified methods ==
    
    public default SELF with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        val i = new AtomicInteger();
        return stream(stream -> stream.map(each -> (i.getAndIncrement() == index) ? value : each));
    }
    
    public default SELF insertAt(int index, DATA ... elements) {
        if ((elements == null) || (elements.length == 0))
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    Stream.of(elements),
                    iStream.get().skip(index + 1)));
        });
    }
    
    public default SELF insertAllAt(int index, Collection<? extends DATA> collection) {
        if ((collection == null)
          || collection.isEmpty())
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    collection.stream(),
                    iStream.get().skip(index + 1)));
        });
    }
    
    public default SELF insertAllAt(int index, ICanStream<? extends DATA, ?> iCanStream) {
        if (iCanStream == null)
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    iCanStream.stream(),
                    iStream.get().skip(index + 1)));
        });
    }
    
    public default SELF excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    iStream.get().limit(index), 
                    iStream.get().skip(index + 2));
        });
    }
    
    public default SELF excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(fromIndexInclusive + count));
        });
    }
    
    public default SELF excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive
                                            + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(toIndexExclusive + 1));
        });
    }
    
    //== Mutable methods are not supported.

    @Override
    public default DATA set(int index, DATA element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default boolean add(DATA e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default boolean addAll(Collection<? extends DATA> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default boolean addAll(int index, Collection<? extends DATA> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public default void add(int index, DATA element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default DATA remove(int index) {
        throw new UnsupportedOperationException();
    }
    
}
