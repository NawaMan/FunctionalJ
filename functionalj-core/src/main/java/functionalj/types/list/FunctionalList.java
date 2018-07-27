package functionalj.types.list;

import static functionalj.FunctionalJ.withIndex;
import static functionalj.lens.Access.theInteger;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.functions.FuncUnit;
import functionalj.types.stream.StreamPlus;
import functionalj.types.stream.Streamable;

@SuppressWarnings("javadoc")
public abstract class FunctionalList<DATA> 
                    implements 
                        FunctionalListMapAddOn<DATA>,
                        FunctionalListPeekAddOn<DATA>,
                        FunctionalListFlatMapAddOn<DATA>,
                        FunctionalListFilterAddOn<DATA>,
                        ReadOnlyList<DATA, FunctionalList<DATA>>, 
                        IFunctionalList<DATA, FunctionalList<DATA>> {
    
    @SuppressWarnings("unchecked")
	@Override
    public <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> TARGET_SELF __of(Stream<TARGET> targetStream) {
        return (TARGET_SELF)FunctionalList.ofStream(targetStream);
    }
    
    public static <T> FunctionalList<T> empty() {
        return ImmutableList.empty();
    }
    public static <T> FunctionalList<T> of(Collection<T> data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> ofCollection(Collection<T> data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> ofList(List<T> data) {
        return ImmutableList.of(data);
    }
    @SafeVarargs
	public static <T> FunctionalList<T> of(T ... data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T> of(Streamable<T, ?> streamable) {
        return ImmutableList.of(streamable);
    }
    public static <T> FunctionalList<T> ofStream(Stream<T> stream) {
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
    
    public FunctionalList<DATA> asFunctionalList() {
        return this;
    }
    
    @Override
    public abstract FunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);
    
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET, TARGET_SELF extends StreamPlus<TARGET, ?>> 
            TARGET_SELF __map(Function<? super DATA, ? extends TARGET> mapper) {
        return (TARGET_SELF)map(mapper);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET, TARGET_SELF extends StreamPlus<TARGET, ?>> 
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
    public FunctionalList<DATA> peek(FuncUnit<? super DATA> consumer) {
        return new FunctionalListStream<>(this, stream -> stream.peek(consumer));
    }
    
    @Override

    public <TARGET> FunctionalList<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return new FunctionalListStream<>(this, stream -> stream.flatMap(mapper));
    }
    
    public FunctionalList<DATA> flatMapOnly(Predicate<? super DATA> checker, Function<? super DATA, FunctionalList<DATA>> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : Stream.of(d));
    }
    
    @Override
    public abstract Stream<DATA> stream();
    
    @Override
    public List<DATA> toList() {
        return this;
    }
    
    @Override
    public IFunctionalList<DATA, ?> toIFunctionalList() {
        return this;
    }
    
    @Override
    public FunctionalList<DATA> toFunctionalList() {
        return this;
    }
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(this);
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
    
    public FunctionalList<Integer> indexesOf(Predicate<? super DATA> check) {
        // TODO - This might be wrong the second time.
        return this
                .map(withIndex((data, index)-> check.test(data) ? index : -1))
                .filter(theInteger.thatNotEqualsTo(-1));
    }
    
}
