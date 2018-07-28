package functionalj.types.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
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

import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.functions.StringFunctions;
import functionalj.types.list.FunctionalList;
import functionalj.types.list.ImmutableList;
import functionalj.types.tuple.Tuple2;
import lombok.val;


@SuppressWarnings("javadoc")
@FunctionalInterface
public interface StreamPlus<DATA> 
        extends Iterable<DATA>, Stream<DATA> {
    
    // TODO takeUntil
    // TODO takeWhile
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D> StreamPlus<D> ofStream(Stream<D> stream) {
        return (stream instanceof StreamPlus)
                ? (StreamPlus)stream
                : (StreamPlus)(()->stream);
    }
    
    @SafeVarargs
    public static <D> StreamPlus<D> of(D ... data) {
        return StreamPlus.ofStream(Stream.of(data));
    }
    
    public Stream<DATA> stream();
    
    //== Stream ==
    
    public default <TARGET> StreamPlus<TARGET> stream(Function<Stream<DATA>, Stream<TARGET>> action) {
        return StreamPlus.ofStream(
                action.apply(
                        this.stream()));
    }
    
    public default <TARGET> StreamPlus<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return stream(stream -> {
            return stream.map(mapper);
        });
    }
    
    public default <TARGET> StreamPlus<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return stream(stream -> {
            return stream.flatMap(mapper);
        });
    }
    
    public default StreamPlus<DATA> filter(Predicate<? super DATA> predicate) {
        return stream(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(predicate);
        });
    }
    public default StreamPlus<DATA> peek(Consumer<? super DATA> action) {
        return stream(stream -> {
            return (action == null)
                    ? stream
                    : stream.peek(action);
        });
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
    
    @Override
    public default StreamPlus<DATA> limit(long maxSize) {
        return stream(stream -> {
            return stream.limit(maxSize);
        });
    }
    
    @Override
    public default StreamPlus<DATA> skip(long n) {
        return stream(stream -> {
            return stream.skip(n);
        });
    }
    
    @Override
    public default StreamPlus<DATA> distinct() {
        return stream(stream -> {
            return stream.distinct();
        });
    }
    
    @Override
    public default StreamPlus<DATA> sorted() {
        return stream(stream -> {
            return stream.sorted();
        });
    }
    
    @Override
    public default StreamPlus<DATA> sorted(Comparator<? super DATA> comparator) {
        return stream(stream -> {
            return (comparator == null)
                    ? stream.sorted()
                    : stream.sorted(comparator);
        });
    }
    
    @Override
    public default boolean isParallel() {
        return stream().isParallel();
    }
    
    @Override
    public default StreamPlus<DATA> sequential() {
        return stream(stream -> { 
            return stream.sequential();
        });
    }
    
    @Override
    public default StreamPlus<DATA> parallel() {
        return stream(stream -> { 
            return stream.parallel();
        });
    } 
    
    @Override
    public default StreamPlus<DATA> unordered() {
        return stream(stream -> { 
            return stream.unordered();
        });
    }
    
    @Override
    public default StreamPlus<DATA> onClose(Runnable closeHandler) {
        return stream(stream -> { 
            return stream.onClose(closeHandler);
        });
    }
    
    @Override
    public default void close() {
        stream().close();
    }
    
    //== toXXX ===
    
    @Override
    public default Object[] toArray() {
        return stream().toArray();
    }
    
    @Override
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream().toArray(generator);
    }
    
    public default List<DATA> toList() {
        return stream().collect(Collectors.toList());
    }
    
    public default FunctionalList<DATA> toFunctionalList() {
        return toImmutableList();
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(stream());
    }
    
    public default List<DATA> toMutableList() {
        return toArrayList();
    }
    
    public default ArrayList<DATA> toArrayList() {
        return new ArrayList<DATA>(toList());
    }
    
    public default Set<DATA> toSet() {
        return new HashSet<DATA>(stream().collect(Collectors.toSet()));
    }
    // TODO - toMapBuilder
    
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    //== Plus ==
    
    public default String joining() {
        return stream()
                .map(StringFunctions::stringOf)
                .collect(Collectors.joining());
    }
    public default String joining(String delimiter) {
        return stream()
                .map(StringFunctions::stringOf)
                .collect(Collectors.joining(delimiter));
    }
    
    //++ Plus w/ Self ++
    
    //-- Limit/Skip --
    
    public default StreamPlus<DATA> limit(Long maxSize) {
        return stream(stream -> {
            return ((maxSize == null) || (maxSize.longValue() < 0))
                    ? stream
                    : stream.limit(maxSize);
        });
    }
    
    public default StreamPlus<DATA> skip(Long startAt) {
        return stream(stream -> {
            return ((startAt == null) || (startAt.longValue() < 0))
                    ? stream
                    : stream.skip(startAt);
        });
    }
    
    //-- Sorted --
    
    public default <T extends Comparable<? super T>> StreamPlus<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return stream(stream -> {
            return stream.sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> StreamPlus<DATA> sortedBy(Function<? super DATA, T> mapper, Comparator<T> comparator) {
        return stream(stream -> {
            return stream.sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return Objects.compare(vA,  vB, comparator);
                });
        });
    }
    
    //-- mapWithIndex --
    
    public default <T> StreamPlus<T> mapWithIndex(BiFunction<? super Integer, ? super DATA, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(index.getAndIncrement(), each));
    }
    
    public default <T1, T> StreamPlus<T> mapWithIndex(
                Function<? super DATA, ? extends T1>       mapper1,
                BiFunction<? super Integer, ? super T1, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each)));
    }
    
    public default <T1, T2, T> StreamPlus<T> mapWithIndex(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Func3<? super Integer, ? super T1, ? super T2, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each),
                                mapper2.apply(each)));
    }
    
    public default <T1, T2, T3, T> StreamPlus<T> mapWithIndex(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Func4<? super Integer, ? super T1, ? super T2, ? super T3, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each),
                                mapper2.apply(each),
                                mapper3.apply(each)));
    }
    
    public default <T1, T2, T3, T4, T> StreamPlus<T> mapWithIndex(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Func5<? super Integer, ? super T1, ? super T2, ? super T3, ? super T4, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each),
                                mapper2.apply(each),
                                mapper3.apply(each),
                                mapper4.apply(each)));
    }
    
    public default <T1, T2, T3, T4, T5, T> StreamPlus<T> mapWithIndex(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Func6<? super Integer, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each),
                                mapper2.apply(each),
                                mapper3.apply(each),
                                mapper4.apply(each),
                                mapper5.apply(each)));
    }
    
    //-- Plus w/ Self --
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public default <T1, T2> StreamPlus<Tuple2<T1, T2>> map(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
//        return map(mapper1, mapper2, (t1, t2) -> Tuple.of(t1, t2));
        return null;
    }
    
    
    //== More functionality ==
    
    
    public default StreamPlus<DATA> exclude(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return this;
        
        return stream(stream -> stream.filter(data -> !predicate.test(data)));
    }
    
    public default StreamPlus<DATA> onlyNonNull() {
        return stream(stream -> stream.filter(Objects::nonNull));
    }
    
    public default StreamPlus<DATA> filter(Collection<? super DATA> collection) {
        if (collection == null)
            return this;
        
        return stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default StreamPlus<DATA> exclude(Collection<? super DATA> collection) {
        return stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default StreamPlus<DATA> selectiveMap(Predicate<? super DATA> filter, Function<DATA, DATA> mapper) {
        return stream(stream -> stream.map(each -> {
            return filter.test(each)
                    ? mapper.apply(each)
                    : each;
        }));
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
