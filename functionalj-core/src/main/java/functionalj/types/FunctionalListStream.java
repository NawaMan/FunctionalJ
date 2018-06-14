package functionalj.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import lombok.val;

// TODO - create unit tests to make sure all ReadOnlyList, FunctionalList types behave consistently.
public class FunctionalListStream<SOURCE, DATA> 
                extends AbstractFunctionalList<DATA> {
    
    private final Function<Stream<DATA>, Stream<DATA>> noAction = Function.identity();
    
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
    public FunctionalListStream(Streamable<SOURCE, ?> streamable, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamable;
    }
    public FunctionalListStream(ReadOnlyList<SOURCE, ?> readOnlyList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = readOnlyList;
    }
    public FunctionalListStream(FunctionalList<SOURCE, ?> abstractFunctionalList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = abstractFunctionalList;
    }
    public FunctionalListStream(AbstractFunctionalList<SOURCE> abstractFunctionalList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = abstractFunctionalList;
    }
    
    public final synchronized void materialize() {
        if (target != null)
            return;
        
        val oldStraam = getSourceStream();
        val newStream = action.apply(oldStraam);
        target = newStream.collect(Collectors.toList());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Stream<SOURCE> getSourceStream() {
        if (source == null)
            return Stream.empty();
        if (source instanceof Supplier)
            return (Stream<SOURCE>)((Supplier)source).get();
        if (source instanceof Streamable)
            return (Stream<SOURCE>)((Streamable)source).stream();
        if (source instanceof Collection)
            return ((Collection)source).stream();
        throw new IllegalStateException();
    }
    
    @Override
    public AbstractFunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier) {
        return new FunctionalListStream<>(()->{
                    return supplier.apply(()->{
                        return FunctionalListStream.this.stream();
                    });
                },
                noAction);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET, TARGET_SELF extends Streamable<TARGET, TARGET_SELF>> 
            TARGET_SELF stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return (TARGET_SELF)new FunctionalListStream<DATA, TARGET>(this, action);
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
    public AbstractFunctionalList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return (AbstractFunctionalList<DATA>)new ImmutableList<DATA>(Collections.emptyList());
        
        materialize();
        if ((fromIndexInclusive == 0) && (toIndexExclusive == target.size()))
            return this;
        
        return stream(stream -> stream.skip(fromIndexInclusive).limit(toIndexExclusive - fromIndexInclusive));
    }
    
    public ImmutableList<DATA> toImmutableList() {
        materialize();
        return new ImmutableList<DATA>(target);
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
    public <T> T[] toArray(T[] a) {
        materialize();
        return target.toArray(a);
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
    public String toString() {
        return "[" + stream().map(String::valueOf).collect(joining(", ")) + "]";
    }
    
}
