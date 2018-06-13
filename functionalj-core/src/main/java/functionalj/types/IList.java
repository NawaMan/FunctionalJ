package functionalj.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.val;

public interface IList<DATA, SELF extends IList<DATA, SELF>> extends List<DATA>, ICanStream<DATA, SELF> {

    public static <T> IList<T, ?> empty() {
        return ImmutableList.empty();
    }
    
    public static <T> IList<T, ?> of(Collection<T> data) {
        return ImmutableList.of(data);
    }
    
    public static <T> IList<T, ?> of(T ... data) {
        return ImmutableList.of(data);
    }
    
    public static <T> IList<T, ?> of(ICanStream<T, ?> icanStream) {
        return ImmutableList.of(icanStream);
    }
    
    public static <T> IList<T, ?> of(FunctionalList<T> functionalList) {
        return ImmutableList.of(functionalList);
    }
    
    public static <T> IList<T, ?> of(IList<T, ?> iList) {
        return ImmutableList.of(iList);
    }
    
    public static <T> IList<T, ?> listOf(T ... data) {
        return ImmutableList.of(data);
    }
    
    
    @Override
    public Stream<DATA> stream();
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(this);
    }
    
    
    @Override
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    //== Access list ==
    
    public default int size() {
        return (int)stream().count();
    }

    public default boolean isEmpty() {
        return Helper.hasAt(stream(), 0);
    }

    public default boolean contains(Object o) {
        return Helper.hasAt(stream().filter(each -> Objects.equals(each, o)), 0);
    }
    
    public default boolean contains(Predicate<DATA> predicate) {
        return Helper.hasAt(stream().filter(predicate), 0);
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
    
    public default DATA get(int index) {
        val ref   = new AtomicReference<DATA>();
        val found = ICanStream.Helper.hasAt(this.stream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        
        return ref.get();
    }
    
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
