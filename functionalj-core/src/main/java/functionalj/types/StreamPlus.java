package functionalj.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import lombok.val;



public interface StreamPlus<DATA, SELF extends StreamPlus<DATA, SELF>> 
        extends Iterable<DATA>, Stream<DATA> {
    
    public static <D> StreamPlus<D, ?> of(Stream<D> stream) {
        if (stream instanceof StreamPlus)
            return (StreamPlus)stream;
        
        return new Impl<>(stream);
    }
    
    public static <D> StreamPlus<D, ?> of(D ... data) {
        return StreamPlus.of(Stream.of(data));
    }
    
    
    public Stream<DATA> stream();
    
    public <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __of(Stream<TARGET> targetStream);
    
    
    // Simple implementation.
    
    public static class Impl<DATA> implements StreamPlus<DATA, Impl<DATA>> {
        private final Stream<DATA> sourceStream;
        
        public Impl(Stream<DATA> sourceStream) {
            this.sourceStream = sourceStream;
        }
    
        @Override
        public Stream<DATA> stream() {
            return sourceStream;
        }
    
        @SuppressWarnings("unchecked")
        @Override
        public <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> TARGET_SELF __of(Stream<TARGET> targetStream) {
            return (TARGET_SELF)new Impl<TARGET>(targetStream);
        }
    
    }
    
    // -- Service methods --
    
    public default <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return this.__of(action.apply(this.stream()));
    }
    
    
    public default <TARGET> StreamPlus<TARGET, ?> stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return new Impl<TARGET>(action.apply(this.stream()));
    }
    
    public default <TARGET> StreamPlus<TARGET, ?> map(Function<? super DATA, ? extends TARGET> mapper) {
        return new Impl<TARGET>(this.stream().map(mapper));
    }
    
    public default <TARGET> StreamPlus<TARGET, ?> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return stream(stream -> stream.flatMap(mapper));
    }
    
    public default  <TARGET, TARGET_SELF extends StreamPlus<TARGET, ?>> 
            TARGET_SELF __map(Function<? super DATA, ? extends TARGET> mapper) {
        return __stream(stream -> stream.map(mapper));
    }
    
    public default <TARGET, TARGET_SELF extends StreamPlus<TARGET, ?>> 
            TARGET_SELF __flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return __stream(stream -> stream.flatMap(mapper));
    }
    
    // TODO - Add map, peek, forEach with Index and make sure that the index does not got mixed up when 
    //          reused (for child of this).
    
    // TODO - Think about making this Tuple concern
    
    public default <T1, T2, TARGET_SELF extends StreamPlus<Tuple2<T1, T2>, ?>> 
            TARGET_SELF map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return __map(each -> new ImmutableTuple2<T1, T2>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each)));
    }
    
    public default <T1, T2, T3, TARGET_SELF extends StreamPlus<Tuple3<T1, T2, T3>, ?>> 
            TARGET_SELF map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return __map(each -> new ImmutableTuple3<T1, T2, T3>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each)));
    }
    
    public default <T1, T2, T3, T4, TARGET_SELF extends StreamPlus<Tuple4<T1, T2, T3, T4>, ?>> 
            TARGET_SELF map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return __map(each -> new ImmutableTuple4<T1, T2, T3, T4>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each), 
                                    mapper4.apply(each)));
    }
    
    public default <T1, T2, T3, T4, T5, TARGET_SELF extends StreamPlus<Tuple5<T1, T2, T3, T4, T5>, ?>> 
            TARGET_SELF map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return __map(each -> new ImmutableTuple5<T1, T2, T3, T4, T5>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each), 
                                    mapper4.apply(each), 
                                    mapper5.apply(each)));
    }
    
    public default <T1, T2, T3, T4, T5, T6, TARGET_SELF extends StreamPlus<Tuple6<T1, T2, T3, T4, T5, T6>, ?>> 
            TARGET_SELF map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return __map(each -> new ImmutableTuple6<T1, T2, T3, T4, T5, T6>(
                                    mapper1.apply(each), 
                                    mapper2.apply(each), 
                                    mapper3.apply(each), 
                                    mapper4.apply(each), 
                                    mapper5.apply(each), 
                                    mapper6.apply(each)));
    }
    
    @Override
    public default IntStream mapToInt(ToIntFunction<? super DATA> mapper) {
        return stream().mapToInt(mapper);
    }

    @Override
    public default LongStream mapToLong(ToLongFunction<? super DATA> mapper) {
        return stream().mapToLong(mapper);
    }

    @Override
    public default DoubleStream mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return stream().mapToDouble(mapper);
    }

    @Override
    public default IntStream flatMapToInt(Function<? super DATA, ? extends IntStream> mapper) {
        return stream().flatMapToInt(mapper);
    }

    @Override
    public default LongStream flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
        return stream().flatMapToLong(mapper);
    }

    @Override
    public default DoubleStream flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
        return stream().flatMapToDouble(mapper);
    }

    @Override
    public default Stream<DATA> limit(long maxSize) {
        return stream().limit(maxSize);
    }

    @Override
    public default Stream<DATA> skip(long n) {
        return stream().skip(n);
    }

    @Override
    public default Object[] toArray() {
        return stream().toArray();
    }

    @Override
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream().toArray(generator);
    }

    @Override
    public default boolean isParallel() {
        return stream().isParallel();
    }

    @Override
    public default Stream<DATA> sequential() {
        return stream().sequential();
    }

    @Override
    public default Stream<DATA> parallel() {
        return stream().parallel();
    } 

    @Override
    public default Stream<DATA> unordered() {
        return stream().unordered();
    }
    
    @Override
    public default Stream<DATA> onClose(Runnable closeHandler) {
        return stream().onClose(closeHandler);
    }

    @Override
    public default void close() {
        stream().close();
    }
    
    //== More functionality ==
    
    public default List<DATA> toList() {
        return stream().collect(Collectors.toList());
    }
    
    public default IFunctionalList<DATA, ?> toIFunctionalList() {
        return toImmutableList();
    }
    
    public default FunctionalList<DATA> toFunctionalList() {
        return toImmutableList();
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(stream());
    }

    public default ArrayList<DATA> toMutableList() {
        return new ArrayList<DATA>(toList());
    }

    public default Set<DATA> toSet() {
        return new HashSet<DATA>(stream().collect(Collectors.toSet()));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return __map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9,
            KEY key10, Function<? super DATA, ? extends VALUE> mapper10) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data),
                key10, mapper10.apply(data)));
    }
    
    // TODO - toMapBuilder
    
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    public default SELF filter(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(predicate));
    }
    
    public default SELF exclude(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(data -> !predicate.test(data)));
    }
    
    public default SELF filter(DATA o) {
        return __stream(stream -> stream.filter(each -> Objects.equals(o, each)));
    }
    
    public default SELF onlyNonNull() {
        return __stream(stream -> stream.filter(Objects::nonNull));
    }
    
    public default SELF exclude(DATA o) {
        return __stream(stream -> stream.filter(each -> !Objects.equals(o, each)));
    }
    
    public default SELF filter(Collection<? super DATA> collection) {
        if (collection == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default SELF exclude(Collection<? super DATA> collection) {
        return __stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default SELF selectiveMap(Predicate<? super DATA> filter, Function<DATA, DATA> mapper) {
        return __stream(stream -> stream.map(each -> {
            return filter.test(each)
                    ? mapper.apply(each)
                    : each;
        }));
    }
    
    public default SELF distinct() {
        return __stream(stream -> stream.distinct());
    }
    
    public default SELF sorted() {
        return __stream(stream -> stream.sorted());
    }
    
    public default SELF sorted(Comparator<? super DATA> comparator) {
        if (comparator == null)
            return sorted();
        
        return __stream(stream -> stream.sorted(comparator));
    }
    
    public default SELF peek(Consumer<? super DATA> action) {
        if (action == null)
            return (SELF)this;
        
        return __stream(stream -> stream.peek(action));
    }
    
    public default SELF limit(Long maxSize) {
        if ((maxSize == null) || (maxSize.longValue() < 0))
            return (SELF)this;
        
        return __stream(stream -> stream.limit(maxSize));
    }
    
    public default SELF skip(Long startAt){
        if ((startAt == null) || (startAt.longValue() < 0))
            return (SELF)this;
        
        return __stream(stream -> stream.skip(startAt));
    }
    
    public default void forEach(Consumer<? super DATA> action) {
        if (action == null)
            return;
        
        stream().forEach(action);
    }
    
    public default void forEachOrdered(Consumer<? super DATA> action) {
        if (action == null)
            return;
        
        stream().forEachOrdered(action);
    }
    
    public default DATA reduce(DATA identity, BinaryOperator<DATA> accumulator) {
        return stream().reduce(identity, accumulator);
    }
    
    public default Optional<DATA> reduce(BinaryOperator<DATA> accumulator) {
        return stream().reduce(accumulator);
    }
    
    public default <U> U reduce(
                    U                              identity,
                    BiFunction<U, ? super DATA, U> accumulator,
                    BinaryOperator<U>              combiner) {
        return stream().reduce(identity, accumulator, combiner);
    }
    
    public default <R> R collect(
                    Supplier<R>                 supplier,
                    BiConsumer<R, ? super DATA> accumulator,
                    BiConsumer<R, R>            combiner) {
        return stream().collect(supplier, accumulator, combiner);
    }
    
    public default <R, A> R collect(Collector<? super DATA, A, R> collector) {
        return stream().collect(collector);
    }
    
    public default Optional<DATA> min(Comparator<? super DATA> comparator) {
        return stream().min(comparator);
    }
    
    public default Optional<DATA> max(Comparator<? super DATA> comparator) {
        return stream().max(comparator);
    }
    
    public default long count() {
        return stream().count();
    }
    
    public default boolean anyMatch(Predicate<? super DATA> predicate) {
        return stream().anyMatch(predicate);
    }
    
    public default boolean allMatch(Predicate<? super DATA> predicate) {
        return stream().allMatch(predicate);
    }
    
    public default boolean noneMatch(Predicate<? super DATA> predicate) {
        return stream().noneMatch(predicate);
    }
    
    public default Optional<DATA> findFirst() {
        return stream().findFirst();
    }
    
    public default Optional<DATA> findAny() {
        return stream().findAny();
    }
    
    // TODO segment
    
    public static class Helper {

        private static final Object dummy = new Object();
        
        public static <T> boolean hasAt(Stream<T> stream, long index) {
            return hasAt(stream, index, null);
        }
    
        public static <T> boolean hasAt(Stream<T> stream, long index, AtomicReference<T> resultValue) {
            // Note: It is done this way to avoid interpreting 'null' as no-value
            
            val ref = new AtomicReference<Object>(dummy);
            stream
                .skip(index)
                .peek(value -> ref.set(value))
                .findFirst()
                .orElse(null);
            
            @SuppressWarnings("unchecked")
            val value = (T)ref.get();
            val found = (dummy != value);
            
            if (resultValue != null) {
                resultValue.set(found ? value : null);
            }
            
            return found;
        }
        
    }
    
}
