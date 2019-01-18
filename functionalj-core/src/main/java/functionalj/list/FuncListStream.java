package functionalj.list;

import static functionalj.stream.ZipWithOption.AllowUnpaired;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;

// TODO - Add Integer length here to help with a few operations.
@SuppressWarnings("javadoc")
public class FuncListStream<SOURCE, DATA> 
                implements FuncList<DATA> {
    
    @SuppressWarnings("rawtypes")
    private static final Function noAction = Function.identity();
    
    private final Object                                 source;
    private final Function<Stream<SOURCE>, Stream<DATA>> action;
    
    public static <DATA> FuncListStream<DATA, DATA> from(FuncList<DATA> funcList) {
        return new FuncListStream<>(funcList);
    }
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListStream<DATA, DATA> from(Supplier<Stream<DATA>> supplier) {
        return new FuncListStream<>(supplier, noAction);
    }
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListStream<DATA, DATA> from(Streamable<DATA> streamable) {
        return new FuncListStream<>(streamable, noAction);
    }
    
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListStream<DATA, DATA> from(Collection<DATA> streamable) {
        return new FuncListStream<>(streamable, noAction);
    }
    
    public FuncListStream(Iterable<SOURCE> collection, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = collection;
    }
    public FuncListStream(Supplier<Stream<SOURCE>> streamSupplier, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamSupplier;
    }
    public FuncListStream(Streamable<SOURCE> streamable, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamable;
    }
    public FuncListStream(ReadOnlyList<SOURCE> readOnlyList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = readOnlyList;
    }
    public FuncListStream(FuncList<SOURCE> abstractFuncList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = abstractFuncList;
    }
    @SuppressWarnings("unchecked")
    public FuncListStream(FuncList<DATA> abstractFuncList) {
        this.action = s -> (Stream<DATA>)s;
        this.source = abstractFuncList;
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
    public StreamPlus<DATA> stream() {
        Stream<SOURCE> theStream = getSourceStream();
        Stream<DATA>   newStream = action.apply(theStream);
        return StreamPlus.from(newStream);
    }
    
    public boolean isLazy() {
        return true;
    }
    
    public boolean isEager() {
        return false;
    }
    
    public FuncList<DATA> lazy() {
        return this;
    }
    public FuncList<DATA> eager() {
        return new ImmutableList<DATA>(this, false);
    }
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return new ImmutableList<DATA>(this);
    }
    
    @Override
    public int hashCode() {
        return Helper.hashCode(this);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean equals(Object o) {
        if ((o instanceof Collection))
            return false;
        
        return zipWith(((Collection)o).stream(), AllowUnpaired, Objects::equals)
                .findFirst(Boolean.TRUE::equals)
                .isPresent();
    }
    
    @Override
    public String toString() {
        return Helper.toString(this);
    }
    
}
