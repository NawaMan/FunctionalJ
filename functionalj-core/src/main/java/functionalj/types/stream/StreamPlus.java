package functionalj.types.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
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
import java.util.stream.StreamSupport;

import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.functions.StrFuncs;
import functionalj.types.list.FuncList;
import functionalj.types.list.ImmutableList;
import functionalj.types.map.FuncMap;
import functionalj.types.map.ImmutableMap;
import functionalj.types.tuple.Tuple;
import functionalj.types.tuple.Tuple2;
import functionalj.types.tuple.Tuple3;
import functionalj.types.tuple.Tuple4;
import functionalj.types.tuple.Tuple5;
import functionalj.types.tuple.Tuple6;
import lombok.val;


@SuppressWarnings("javadoc")
@FunctionalInterface
public interface StreamPlus<DATA> 
        extends Iterable<DATA>, Stream<DATA> {
    
    // TODO segment
    // TODO - toMapBuilder
    // TODO - zipWith
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D> StreamPlus<D> from(Stream<D> stream) {
        return (stream instanceof StreamPlus)
                ? (StreamPlus)stream
                : (StreamPlus)(()->stream);
    }
    
    @SafeVarargs
    public static <D> StreamPlus<D> of(D ... data) {
        return StreamPlus.from(Stream.of(data));
    }
    
    //== Stream ==
    
    public Stream<DATA> stream();
    
    public default <TARGET> StreamPlus<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
        return StreamPlus.from(
                action.apply(
                        this.stream()));
    }
    
    //== Functionalities ==
    
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
    
    public default void forEach(BiConsumer<? super Integer, ? super DATA> action) {
        if (action == null)
            return;
        
        val index = new AtomicInteger();
        stream().forEach(each ->
                    action.accept(index.getAndIncrement(), each));
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
    public default boolean isParallel() {
        return stream().isParallel();
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
    
    public default FuncList<DATA> toFuncList() {
        return toImmutableList();
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.from(stream());
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
    
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> groupingBy(Function<? super DATA, ? extends KEY> classifier) {
        val theMap = new HashMap<KEY, FuncList<DATA>>();
        stream()
            .collect(Collectors.groupingBy(classifier))
            .forEach((key,list)->theMap.put(key, ImmutableList.from(list)));
        return ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(Function<? super DATA, ? extends KEY> keyMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, data -> data));
        return (FuncMap<KEY, DATA>)ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
                Function<? super DATA, ?  extends KEY>  keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper));
        return (FuncMap<KEY, VALUE>) ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>   keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper,
                BinaryOperator<VALUE> mergeFunction) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
        return (FuncMap<KEY, VALUE>) ImmutableMap.of(theMap);
    }
    
    //== Plus ==
    
    public default String joining() {
        return stream()
                .map(StrFuncs::toStr)
                .collect(Collectors.joining());
    }
    public default String joining(String delimiter) {
        return stream()
                .map(StrFuncs::toStr)
                .collect(Collectors.joining(delimiter));
    }
    
    //-- Split --
    
    public default Tuple2<FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate) {
        val temp = this.mapTuple(
                it -> predicate.test(it) ? 0 : 1,
                it -> it
        ).toFuncList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        return Tuple.of(
                list1,
                list2
        );
    }
    
    public default Tuple3<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3
        );
    }
    
    public default Tuple4<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4
        );
    }
    
    public default Tuple5<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    :                       4,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5
        );
    }
    
    public default Tuple6<FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4,
            Predicate<? super DATA> predicate5) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    : predicate5.test(it) ? 4
                    :                       5,
                it -> it
        ).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).map(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).map(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).map(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).map(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).map(it -> it._2());
        val list6 = temp.filter(it -> it._1() == 5).map(it -> it._2());
        return Tuple.of(
                list1,
                list2,
                list3,
                list4,
                list5,
                list6
        );
    }
    
    //++ Plus w/ Self ++
    
    @Override
    public default StreamPlus<DATA> sequential() {
        return deriveWith(stream -> { 
            return stream.sequential();
        });
    }
    
    @Override
    public default StreamPlus<DATA> parallel() {
        return deriveWith(stream -> { 
            return stream.parallel();
        });
    } 
    
    @Override
    public default StreamPlus<DATA> unordered() {
        return deriveWith(stream -> { 
            return stream.unordered();
        });
    }
    
    @Override
    public default StreamPlus<DATA> onClose(Runnable closeHandler) {
        return deriveWith(stream -> { 
            return stream.onClose(closeHandler);
        });
    }
    
    public default <TARGET> StreamPlus<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return stream.map(mapper);
        });
    }
    
    public default <TARGET> StreamPlus<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return deriveWith(stream -> {
            return stream.flatMap(mapper);
        });
    }
    
    public default StreamPlus<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(predicate);
        });
    }
    public default StreamPlus<DATA> peek(Consumer<? super DATA> action) {
        return deriveWith(stream -> {
            return (action == null)
                    ? stream
                    : stream.peek(action);
        });
    }
    
    //-- Limit/Skip --
    
    @Override
    public default StreamPlus<DATA> limit(long maxSize) {
        return deriveWith(stream -> {
            return stream.limit(maxSize);
        });
    }
    
    @Override
    public default StreamPlus<DATA> skip(long n) {
        return deriveWith(stream -> {
            return stream.skip(n);
        });
    }
    
    public default StreamPlus<DATA> skipWhile(Predicate<? super DATA> condition) {
        val isStillTrue = new AtomicBoolean(true);
        return deriveWith(stream -> {
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                if (!condition.test(e))
                    isStillTrue.set(false);
                return !isStillTrue.get();
            });
        });
    }
    
    public default StreamPlus<DATA> skipUntil(Predicate<? super DATA> condition) {
        val isStillTrue = new AtomicBoolean(true);
        return deriveWith(stream -> {
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                if (condition.test(e))
                    isStillTrue.set(false);
                return !isStillTrue.get();
            });
        });
    }
    
    public default StreamPlus<DATA> takeWhile(Predicate<? super DATA> condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        return deriveWith(stream -> {
            val splitr = stream.spliterator();
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<DATA>(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(final Consumer<? super DATA> consumer) {
                    if (stillGoing) {
                        final boolean hadNext = splitr.tryAdvance(elem -> {
                            if (condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        });
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            }, false);
        });
    }
    
    public default StreamPlus<DATA> takeUntil(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            val splitr = stream.spliterator();
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<DATA>(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(final Consumer<? super DATA> consumer) {
                    if (stillGoing) {
                        final boolean hadNext = splitr.tryAdvance(elem -> {
                            if (!condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        });
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            }, false);
        });
    }
    
    @Override
    public default StreamPlus<DATA> distinct() {
        return deriveWith(stream -> {
            return stream.distinct();
        });
    }
    
    @Override
    public default StreamPlus<DATA> sorted() {
        return deriveWith(stream -> {
            return stream.sorted();
        });
    }
    
    @Override
    public default StreamPlus<DATA> sorted(Comparator<? super DATA> comparator) {
        return deriveWith(stream -> {
            return (comparator == null)
                    ? stream.sorted()
                    : stream.sorted(comparator);
        });
    }
    
    public default StreamPlus<DATA> limit(Long maxSize) {
        return deriveWith(stream -> {
            return ((maxSize == null) || (maxSize.longValue() < 0))
                    ? stream
                    : stream.limit(maxSize);
        });
    }
    
    public default StreamPlus<DATA> skip(Long startAt) {
        return deriveWith(stream -> {
            return ((startAt == null) || (startAt.longValue() < 0))
                    ? stream
                    : stream.skip(startAt);
        });
    }
    
    //-- Sorted --
    
    public default <T extends Comparable<? super T>> StreamPlus<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> StreamPlus<DATA> sortedBy(Function<? super DATA, T> mapper, Comparator<T> comparator) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return Objects.compare(vA,  vB, comparator);
                });
        });
    }
    
    //--map with condition --
    
    public default StreamPlus<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker.test(d) ? mapper.apply(d) : elseMapper.apply(d);
        });
    }
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>  checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>  checker2, Function<? super DATA, T> mapper2, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Function<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Function<? super DATA, T> mapper3, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Function<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Function<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Function<? super DATA, T> mapper4, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Function<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Function<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Function<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Function<? super DATA, T> mapper5, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Function<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Function<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Function<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Function<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Function<? super DATA, T> mapper6, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Function<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Function<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Function<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Function<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Function<? super DATA, T> mapper6, 
            Predicate<? super DATA>   checker7, Function<? super DATA, T> mapper7, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : checker7.test(d) ? mapper7.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Function<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Function<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Function<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Function<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Function<? super DATA, T> mapper6, 
            Predicate<? super DATA>   checker7, Function<? super DATA, T> mapper7, 
            Predicate<? super DATA>   checker8, Function<? super DATA, T> mapper8, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : checker7.test(d) ? mapper7.apply(d)
                 : checker8.test(d) ? mapper8.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    
    public default <T> StreamPlus<T> mapIf(
            Predicate<? super DATA>   checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>   checker2, Function<? super DATA, T> mapper2, 
            Predicate<? super DATA>   checker3, Function<? super DATA, T> mapper3, 
            Predicate<? super DATA>   checker4, Function<? super DATA, T> mapper4, 
            Predicate<? super DATA>   checker5, Function<? super DATA, T> mapper5, 
            Predicate<? super DATA>   checker6, Function<? super DATA, T> mapper6, 
            Predicate<? super DATA>   checker7, Function<? super DATA, T> mapper7, 
            Predicate<? super DATA>   checker8, Function<? super DATA, T> mapper8, 
            Predicate<? super DATA>   checker9, Function<? super DATA, T> mapper9, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : checker7.test(d) ? mapper7.apply(d)
                 : checker8.test(d) ? mapper8.apply(d)
                 : checker9.test(d) ? mapper9.apply(d)
                                    : elseMapper.apply(d);
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
    
    //== Map to tuple. ==
    // ++ Generated with: GeneratorFunctorMapToTupleToObject ++
    
    public default <T1, T2> 
        StreamPlus<Tuple2<T1, T2>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return mapThan(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> 
        StreamPlus<Tuple3<T1, T2, T3>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return mapThan(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> 
        StreamPlus<Tuple4<T1, T2, T3, T4>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return mapThan(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        StreamPlus<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return mapThan(mapper1, mapper2, mapper3, mapper4, mapper5,
                   (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    public default <T1, T2, T3, T4, T5, T6> 
        StreamPlus<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return mapThan(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    //-- Map and combine --
    
    public default <T1, T2, T> 
        StreamPlus<T> mapThan(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                BiFunction<T1, T2, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v  = function.apply(v1, v2);
            return v;
        });
    }
    public default <T1, T2, T3, T> 
        StreamPlus<T> mapThan(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Func3<T1, T2, T3, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v  = function.apply(v1, v2, v3);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T> 
        StreamPlus<T> mapThan(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Func4<T1, T2, T3, T4, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v  = function.apply(v1, v2, v3, v4);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T5, T> 
        StreamPlus<T> mapThan(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Func5<T1, T2, T3, T4, T5, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v  = function.apply(v1, v2, v3, v4, v5);
            return v;
        });
    }
    public default <T1, T2, T3, T4, T5, T6, T> 
        StreamPlus<T> mapThan(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6,
                Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v  = function.apply(v1, v2, v3, v4, v5, v6);
            return v;
        });
    }
    
    // -- Generated with: GeneratorFunctorMapToTupleToObject --
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9) {
        return map(data -> ImmutableMap.of(
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
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
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
        return map(data -> ImmutableMap.of(
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
    
    //-- Filter --
    
    public default StreamPlus<DATA> filterNonNull() {
        return deriveWith(stream -> stream.filter(Objects::nonNull));
    }
    
    public default StreamPlus<DATA> filterIn(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return (collection == null)
                ? Stream.empty()
                : stream.filter(data -> collection.contains(data));
        });
    }
    
    public default StreamPlus<DATA> exclude(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(data -> !predicate.test(data));
        });
    }
    
    public default StreamPlus<DATA> exclude(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return (collection == null)
                ? stream
                : stream.filter(data -> !collection.contains(data));
        });
    }
    
    public default <T> StreamPlus<DATA> filter(Class<T> clzz) {
        return filter(clzz::isInstance);
    }
    
    public default <T> StreamPlus<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return filter(value -> {
            if (!clzz.isInstance(value))
                return false;
            
            val target = clzz.cast(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> StreamPlus<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }

    public default StreamPlus<DATA> filterWithIndex(BiFunction<? super Integer, ? super DATA, Boolean> predicate) {
        val index = new AtomicInteger();
        return filter(each -> {
                    return (predicate != null) 
                            && predicate.apply(index.getAndIncrement(), each);
        });
    }
    
    //-- Peek --
    
    public default <T extends DATA> StreamPlus<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return peek(value -> {
            if (!clzz.isInstance(value))
                return;
            
            val target = clzz.cast(value);
            theConsumer.accept(target);
        });
    }
    public default StreamPlus<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return peek(value -> {
            if (!selector.test(value))
                return;
            
            theConsumer.accept(value);
        });
    }
    public default <T> StreamPlus<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            theConsumer.accept(target);
        });
    }
    
    public default <T> StreamPlus<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
    //-- FlatMap --
    
    public default StreamPlus<DATA> flatMapOnly(Predicate<? super DATA> checker, Function<? super DATA, Stream<DATA>> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : StreamPlus.of(d));
    }
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Stream<T>> mapper, 
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Stream<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Stream<T>> mapper2,
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Stream<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Stream<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Stream<T>> mapper3,
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Stream<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Stream<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Stream<T>> mapper3,
            Predicate<? super DATA> checker4, Function<? super DATA, Stream<T>> mapper4,
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Stream<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Stream<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Stream<T>> mapper3,
            Predicate<? super DATA> checker4, Function<? super DATA, Stream<T>> mapper4,
            Predicate<? super DATA> checker5, Function<? super DATA, Stream<T>> mapper5,
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker5.test(d) ? mapper5.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Stream<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Stream<T>> mapper2,
            Predicate<? super DATA> checker3, Function<? super DATA, Stream<T>> mapper3,
            Predicate<? super DATA> checker4, Function<? super DATA, Stream<T>> mapper4,
            Predicate<? super DATA> checker5, Function<? super DATA, Stream<T>> mapper5,
            Predicate<? super DATA> checker6, Function<? super DATA, Stream<T>> mapper6,
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : checker3.test(d) ? mapper3.apply(d)
                 : checker4.test(d) ? mapper4.apply(d)
                 : checker6.test(d) ? mapper6.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    //-- Plus w/ Self --
    
    public static class Helper {
        
        private static final Object dummy = new Object();
        
        public static <T> boolean hasAt(Stream<T> stream, long index) {
            return hasAt(stream, index, null);
        }
        
        public static <T> boolean hasAt(Stream<T> stream, long index, AtomicReference<T> StreamPlusValue) {
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
            
            if (StreamPlusValue != null) {
                StreamPlusValue.set(found ? value : null);
            }
            
            return found;
        }
        
    }
    
}
