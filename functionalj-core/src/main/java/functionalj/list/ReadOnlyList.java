package functionalj.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface ReadOnlyList<DATA> 
                    extends List<DATA>, Streamable<DATA> {
    
    public static <T> ReadOnlyList<T> empty() {
        return ImmutableList.empty();
    }
    
    public static <T> ReadOnlyList<T> of(Collection<T> data) {
        return ImmutableList.from(data);
    }
    @SafeVarargs
	public static <T> ReadOnlyList<T> of(T ... data) {
        return ImmutableList.of(data);
    }
    public static <T> ReadOnlyList<T> of(Streamable<T> streamable) {
        return ImmutableList.from(streamable);
    }
    public static <T> ReadOnlyList<T> of(ReadOnlyList<T> readOnlyList) {
        return readOnlyList;
    }
    
    @Override
    public StreamPlus<DATA> stream();
    
    @Override
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.from(this);
    }
    @Override
    public default List<DATA> toJavaList() {
        return this;
    }
    
    @Override
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    //== Access list ==
    
    @Override
    public default int size() {
        return (int)stream().count();
    }
    
    @Override
    public default boolean isEmpty() {
        return !Helper.hasAt(stream(), 0);
    }
    
    @Override
    public default boolean contains(Object o) {
        return Helper.hasAt(stream().filter(each -> Objects.equals(each, o)), 0);
    }
    
    @Override
    public default boolean containsAll(Collection<?> c) {
        return c.stream()
                .allMatch(each -> stream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    @Override
    public default Object[] toArray() {
        return stream().toArray();
    }
    
    @Override
    public default <T> T[] toArray(T[] a) {
        return StreamPlus.of(stream()).toJavaList().toArray(a);
    }
    
    @Override
    public default DATA get(int index) {
        val ref   = new AtomicReference<DATA>();
        val found = Helper.hasAt(this.stream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        
        return ref.get();
    }
    
    @Override
    public default int indexOf(Object o) {
        return StreamPlus.from(stream()).toJavaList().indexOf(o);
    }
    
    @Override
    public default int lastIndexOf(Object o) {
        return StreamPlus.from(stream()).toJavaList().lastIndexOf(o);
    }
    
    @Override
    public default ListIterator<DATA> listIterator() {
        return StreamPlus.from(stream()).toJavaList().listIterator();
    }
    
    @Override
    public default ListIterator<DATA> listIterator(int index) {
        return StreamPlus.from(stream()).toJavaList().listIterator(index);
    }
    
    @Override
    public default ReadOnlyList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        val subList = stream()
                .skip(fromIndexInclusive).limit(length)
                .collect(Collectors.toList());
        return (ReadOnlyList<DATA>)(()->StreamPlus.from(subList.stream()));
    }
    
    @Override
    public default Spliterator<DATA> spliterator() {
        return Streamable.super.spliterator();
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
    
    @Override
    public default void replaceAll(UnaryOperator<DATA> operator) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public default void sort(Comparator<? super DATA> c) {
        throw new UnsupportedOperationException();
    }
    
}
