package functionalj.types.list;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.types.stream.Streamable;

// TODO - create unit tests to make sure all ReadOnlyList, FunctionalList types behave consistently.
// TODO - Add Integer length here to help with a few operations.
@SuppressWarnings("javadoc")
public class FunctionalListStream<SOURCE, DATA> 
                implements FunctionalList<DATA> {
    
    @SuppressWarnings("rawtypes")
    private static final Function noAction = Function.identity();
    
    private final Object                                 source;
    private final Function<Stream<SOURCE>, Stream<DATA>> action;
    
    public static <DATA> FunctionalListStream<DATA, DATA> from(FunctionalList<DATA> functionalList) {
        return new FunctionalListStream<>(functionalList);
    }
    @SuppressWarnings("unchecked")
    public static <DATA> FunctionalListStream<DATA, DATA> from(Supplier<Stream<DATA>> supplier) {
        return new FunctionalListStream<>(supplier, noAction);
    }
    @SuppressWarnings("unchecked")
    public static <DATA> FunctionalListStream<DATA, DATA> from(Streamable<DATA> streamable) {
        return new FunctionalListStream<>(streamable, noAction);
    }
    
    public FunctionalListStream(Collection<SOURCE> collection, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = collection;
    }
    public FunctionalListStream(Supplier<Stream<SOURCE>> streamSupplier, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamSupplier;
    }
    public FunctionalListStream(Streamable<SOURCE> streamable, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamable;
    }
    public FunctionalListStream(ReadOnlyList<SOURCE> readOnlyList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = readOnlyList;
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
    public Stream<DATA> stream() {
        Stream<SOURCE> theStream = getSourceStream();
        Stream<DATA>   newStream = action.apply(theStream);
        return newStream;
    }
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return new ImmutableList<DATA>(this);
    }
    
    // TODO - equals, hashCode
    
    @Override
    public String toString() {
        return "[" + joining(", ") + "]";
    }
    
}
