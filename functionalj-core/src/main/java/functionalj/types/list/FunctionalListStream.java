package functionalj.types.list;

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

import functionalj.types.stream.Streamable;
import lombok.val;

// TODO - create unit tests to make sure all ReadOnlyList, FunctionalList types behave consistently.
// TODO - Add Integer length here to help with a few operations.
public class FunctionalListStream<SOURCE, DATA> 
                extends FunctionalList<DATA> {

    private final Function<Stream<DATA>, Stream<DATA>> noAction = Function.identity();
    
    private final Object                                 source;
    private final Function<Stream<SOURCE>, Stream<DATA>> action;
    private volatile List<DATA> target = null;
    
    public static <DATA> FunctionalListStream<DATA, DATA> of(FunctionalList<DATA> abstractFunctionalList) {
        return new FunctionalListStream<>(abstractFunctionalList);
    }
    public static <DATA> FunctionalListStream<DATA, DATA> of(Supplier<Stream<DATA>> abstractFunctionalList) {
        return new FunctionalListStream<>(abstractFunctionalList, s->s);
    }
    
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
    public FunctionalListStream(IFunctionalList<SOURCE, ?> abstractFunctionalList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = abstractFunctionalList;
    }
    public FunctionalListStream(FunctionalList<SOURCE> abstractFunctionalList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = abstractFunctionalList;
    }
    @SuppressWarnings("unchecked")
    public FunctionalListStream(FunctionalList<DATA> abstractFunctionalList) {
        this.action = s -> (Stream<DATA>)s;
        this.source = abstractFunctionalList;
    }
    
    public final synchronized void materialize() {
        if (target != null)
            return;
        
        synchronized (this) {
            if (target != null)
                return;
            
            val oldStraam = getSourceStream();
            val newStream = action.apply(oldStraam);
            target = newStream.collect(Collectors.toList());
        }
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
    public FunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier) {
        return new FunctionalListStream<>(()->{
                    return supplier.apply(()->{
                        return FunctionalListStream.this.stream();
                    });
                },
                noAction);
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
    public FunctionalList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return (FunctionalList<DATA>)new ImmutableList<DATA>(Collections.emptyList());
        
        materialize();
        if ((fromIndexInclusive == 0) && (toIndexExclusive == target.size()))
            return this;
        
        return __stream(stream -> stream.skip(fromIndexInclusive).limit(toIndexExclusive - fromIndexInclusive));
    }
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return new ImmutableList<DATA>(this);
    }
    @Override
    public List<DATA> toList() {
        return this;
    }
    
    @Override
    public int size() {
        if (target != null)
            return target.size();
        
        return (int)stream().count();
    }

    @Override
    public boolean isEmpty() {
        if (target != null)
            return target.isEmpty();
        
        return stream().count() == 0L;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (target != null)
            return target.toArray(a);
        
        return toList().toArray(a);
    }

    @Override
    public DATA get(int index) {
        if (target != null)
            return target.get(index);
        
        return stream()
                .skip(index)
                .findFirst()
                .orElse(null);
    }

    @Override
    public int indexOf(Object o) {
        if (target != null)
            return target.indexOf(o);
        
        // TODO - for now.
        materialize();
        return target.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (target != null)
            return target.indexOf(o);
        
        // TODO - for now.
        materialize();
        return target.lastIndexOf(o);
    }

    @Override
    public ListIterator<DATA> listIterator() {
        if (target != null)
            return target.listIterator();
        
        // TODO - for now.
        materialize();
        return target.listIterator();
    }

    @Override
    public ListIterator<DATA> listIterator(int index) {
        if (target != null)
            return target.listIterator(index);
        
        // TODO - for now.
        materialize();
        return target.listIterator(index);
    }
    
    @Override
    public String toString() {
        return "[" + stream().map(String::valueOf).collect(joining(", ")) + "]";
    }
    
}
