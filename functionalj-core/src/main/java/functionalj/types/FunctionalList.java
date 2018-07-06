package functionalj.types;

import static functionalj.FunctionalJ.withIndex;
import static functionalj.lens.Lenses.theInteger;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
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
    
    public <T1, T2> FunctionalList<Tuple2<T1, T2>> map(
            Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2) {
        return new FunctionalListStream<>(this, stream -> 
                stream.map(each -> 
                        new ImmutableTuple2<T1, T2>(
                            mapper1.apply(each), 
                            mapper2.apply(each))));
    }
    
    public <T1, T2, T3> FunctionalList<Tuple3<T1, T2, T3>> map(
            Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2,
            Function<? super DATA, ? extends T3> mapper3) {
        return new FunctionalListStream<>(this, stream -> 
                stream.map(each -> 
                        new ImmutableTuple3<T1, T2, T3>(
                            mapper1.apply(each), 
                            mapper2.apply(each), 
                            mapper3.apply(each))));
    }
    
    public <T1, T2, T3, T4> FunctionalList<Tuple4<T1, T2, T3, T4>> map(
            Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2,
            Function<? super DATA, ? extends T3> mapper3,
            Function<? super DATA, ? extends T4> mapper4) {
        return new FunctionalListStream<>(this, stream -> 
                stream.map(each -> 
                        new ImmutableTuple4<T1, T2, T3, T4>(
                            mapper1.apply(each), 
                            mapper2.apply(each), 
                            mapper3.apply(each), 
                            mapper4.apply(each))));
    }
    
    public <T1, T2, T3, T4, T5> FunctionalList<Tuple5<T1, T2, T3, T4, T5>> map(
            Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2,
            Function<? super DATA, ? extends T3> mapper3,
            Function<? super DATA, ? extends T4> mapper4,
            Function<? super DATA, ? extends T5> mapper5) {
        return new FunctionalListStream<>(this, stream -> 
                stream.map(each -> 
                        new ImmutableTuple5<T1, T2, T3, T4, T5>(
                            mapper1.apply(each), 
                            mapper2.apply(each), 
                            mapper3.apply(each), 
                            mapper4.apply(each), 
                            mapper5.apply(each))));
    }
    
    public <T1, T2, T3, T4, T5, T6> FunctionalList<Tuple6<T1, T2, T3, T4, T5, T6>> map(
            Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2,
            Function<? super DATA, ? extends T3> mapper3,
            Function<? super DATA, ? extends T4> mapper4,
            Function<? super DATA, ? extends T5> mapper5,
            Function<? super DATA, ? extends T6> mapper6) {
        return new FunctionalListStream<>(this, stream -> 
                stream.map(each -> 
                        new ImmutableTuple6<T1, T2, T3, T4, T5, T6>(
                            mapper1.apply(each), 
                            mapper2.apply(each), 
                            mapper3.apply(each), 
                            mapper4.apply(each), 
                            mapper5.apply(each), 
                            mapper6.apply(each))));
    }
    
    // TODO - Extends this to 10.
    // Umm Why are they here ... and not in Streamable?
    
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
        return this
                .map(withIndex((data, index)-> check.test(data) ? index : -1))
                .filter(theInteger.thatNotEqualsTo(-1));
    }
    
}
