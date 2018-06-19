package functionalj.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FunctionalList<DATA> 
                    implements 
                        ReadOnlyList<DATA, FunctionalList<DATA>>, 
                        IFunctionalList<DATA, FunctionalList<DATA>> {
    
    public static <T> FunctionalList<T> empty() {
        return ImmutableList.empty();
    }
    public static <T> FunctionalList<T> of(Collection<T> data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> of(T ... data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> of(Streamable<T, ?> streamable) {
        return ImmutableList.of(streamable);
    }
    public static <T> FunctionalList<T> of(Stream<T> stream) {
        return new ImmutableList<T>(stream.collect(Collectors.toList()));
    }
    public static <T> FunctionalList<T> of(ReadOnlyList<T, ?> readOnlyList) {
        return ImmutableList.of(readOnlyList);
    }
    public static <T> FunctionalList<T> of(IFunctionalList<T, ?> functionalList) {
        return ImmutableList.of(functionalList);
    }
    public static <T> FunctionalList<T> of(FunctionalList<T> functionalList) {
        return ImmutableList.of(functionalList);
    }

    @Override
    public abstract FunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);

    @SuppressWarnings("unchecked")
    @Override
    public <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return (TARGET_SELF)stream(action);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __map(Function<? super DATA, ? extends TARGET> mapper) {
        return (TARGET_SELF)map(mapper);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return (TARGET_SELF)flatMap(mapper);
    }
    
    @Override
    public <TARGET> FunctionalList<TARGET> stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return new FunctionalListStream<>(this, action);
    }
    @Override
    public <TARGET> FunctionalList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return new FunctionalListStream<>(this, stream -> stream.map(mapper));
    }
    @Override
    public <TARGET> FunctionalList<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return new FunctionalListStream<>(this, stream -> stream.flatMap(mapper));
    }
    
    @Override
    public abstract Stream<DATA> stream();
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(this);
    }
    @Override
    public List<DATA> toList() {
        return this;
    }
    
    @Override
    public Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    @Override
    public Spliterator<DATA> spliterator() {
        return stream().spliterator();
    }

    @Override
    public int size() {
        return (int)stream().count();
    }

    @Override
    public abstract DATA get(int index);
    
    
}
