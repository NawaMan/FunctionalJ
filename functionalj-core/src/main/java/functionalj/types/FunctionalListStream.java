package functionalj.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import lombok.val;

public class FunctionalListStream<SOURCE, DATA> implements FunctionalList<DATA> {
    
    private final Object                                 source;
    private final Function<Stream<SOURCE>, Stream<DATA>> action;
    private volatile List<DATA> target = null;
    
    public FunctionalListStream(Collection<SOURCE> collection, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = collection;
    }
    public FunctionalListStream(Supplier<Stream<SOURCE>> streamSupplier, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamSupplier;
    }
    
    private synchronized void materialize() {
        if (target != null)
            return;
        
        val oldStraam = getSourceStream();
        val newStream = action.apply(oldStraam);
        target = newStream.collect(toList());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Stream<SOURCE> getSourceStream() {
        if (source == null)
            return Stream.empty();
        if (source instanceof Supplier)
            return (Stream<SOURCE>)((Supplier)source).get();
        if (source instanceof Collection)
            return ((Collection)source).stream();
        throw new IllegalStateException();
    }
    
    @Override
    public Stream<DATA> stream() {
        if (target != null)
            return target.stream();
        
        Stream<SOURCE> theStream = getSourceStream();
        Stream<DATA>   newStream = action.apply(theStream);
        return newStream;
    }

    @Override
    public Stream<DATA> get() {
        return stream();
    }

    @Override
    public int size() {
        materialize();
        return target.size();
    }

    @Override
    public boolean isEmpty() {
        materialize();
        return target.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return stream()
                .anyMatch(each -> Objects.equals(each, o));
    }
    
    @Override
    public boolean contains(Predicate<DATA> predicate) {
        return stream()
                .anyMatch(predicate);
    }

    @Override
    public Iterator<DATA> iterator() {
        return stream()
                .iterator();
    }

    @Override
    public Object[] toArray() {
        return stream()
                .toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        materialize();
        return target.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream()
                .allMatch(each -> stream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }

    @Override
    public DATA get(int index) {
        materialize();
        return target.get(index);
    }

    @Override
    public int indexOf(Object o) {
        materialize();
        return target.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        materialize();
        return target.lastIndexOf(o);
    }

    @Override
    public ListIterator<DATA> listIterator() {
        materialize();
        return target.listIterator();
    }

    @Override
    public ListIterator<DATA> listIterator(int index) {
        materialize();
        return target.listIterator(index);
    }

    @Override
    public FunctionalList<DATA> subList(int fromIndex, int toIndex) {
        if ((fromIndex < 0) || (fromIndex >= this.size()))
            throw new ArrayIndexOutOfBoundsException(fromIndex);
        if ((toIndex < 0) || (toIndex >= this.size()))
            throw new ArrayIndexOutOfBoundsException(toIndex);
        
        if ((fromIndex == 0) && (toIndex == this.size()))
            return this;
        
        Function<Stream<DATA>, Stream<DATA>> theAction  = stream -> stream.skip(fromIndex).limit(toIndex - fromIndex);
        FunctionalListStream<DATA, DATA>     listStream = new FunctionalListStream<DATA, DATA>(this, theAction);
        return listStream;
    }

    @Override
    public List<DATA> asList() {
        return stream().collect(toList());
    }
    
    @Override
    public String toString() {
        return "[" + stream().map(String::valueOf).collect(joining(", ")) + "]";
    }
    
}
