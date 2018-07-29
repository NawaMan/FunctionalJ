package functionalj.types.stream;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.types.map.FunctionalMap;
import functionalj.types.map.ImmutableMap;
import functionalj.types.tuple.Tuple2;
import functionalj.types.tuple.Tuple3;
import functionalj.types.tuple.Tuple4;
import functionalj.types.tuple.Tuple5;
import functionalj.types.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public interface Streamable<DATA> 
        extends StreamPlus<DATA> {
    
    @Override
    public default void close() {
        // Close has no meaning as new stream will be created for next use.
    }
    
    @Override
    public Stream<DATA> stream();
    
    @Override
    public <TARGET> Streamable<TARGET> stream(Function<Stream<DATA>, Stream<TARGET>> action);
    
    @Override
    public default <TARGET> Streamable<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return stream(stream -> stream.map(mapper));
    }
    
    @Override
    public default <TARGET> Streamable<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return stream(stream -> stream.flatMap(mapper));
    }
    
    public default Streamable<DATA> filter(Predicate<? super DATA> predicate) {
        return stream(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(predicate);
        });
    }
    
    public default Streamable<DATA> peek(Consumer<? super DATA> action) {
        return stream(stream -> {
            return (action == null)
                    ? stream
                    : stream.peek(action);
        });
    }
    
    //++ Plus w/ Self ++
    
    //-- Limit/Skip --
    
    @Override
    public default Streamable<DATA> limit(long maxSize) {
        return stream(stream -> {
            return stream.limit(maxSize);
        });
    }
    
    @Override
    public default Streamable<DATA> skip(long n) {
        return stream(stream -> {
            return stream.skip(n);
        });
    }
    
    @Override
    public default Streamable<DATA> distinct() {
        return stream(stream -> {
            return stream.distinct();
        });
    }
    
    @Override
    public default Streamable<DATA> sorted() {
        return stream(stream -> {
            return stream.sorted();
        });
    }
    
    @Override
    public default Streamable<DATA> sorted(Comparator<? super DATA> comparator) {
        return stream(stream -> {
            return (comparator == null)
                    ? stream.sorted()
                    : stream.sorted(comparator);
        });
    }
    
    public default Streamable<DATA> limit(Long maxSize) {
        return stream(stream -> {
            return ((maxSize == null) || (maxSize.longValue() < 0))
                    ? stream
                    : stream.limit(maxSize);
        });
    }
    
    public default Streamable<DATA> skip(Long startAt) {
        return stream(stream -> {
            return ((startAt == null) || (startAt.longValue() < 0))
                    ? stream
                    : stream.skip(startAt);
        });
    }
    
    //-- Sorted --
    
    public default <T extends Comparable<? super T>> Streamable<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return stream(stream -> {
            return stream.sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> Streamable<DATA> sortedBy(Function<? super DATA, T> mapper, Comparator<T> comparator) {
        return stream(stream -> {
            return stream.sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return Objects.compare(vA,  vB, comparator);
                });
        });
    }
    
    //--map with condition --
    
    public default Streamable<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    public default <T> Streamable<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker.test(d) ? mapper.apply(d) : elseMapper.apply(d);
        });
    }
    public default <T> Streamable<T> mapIf(
            Predicate<? super DATA>  checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>  checker2, Function<? super DATA, T> mapper2, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> Streamable<T> mapIf(
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
    public default <T> Streamable<T> mapIf(
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
    public default <T> Streamable<T> mapIf(
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
    public default <T> Streamable<T> mapIf(
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
    
    public default <T> Streamable<T> mapIf(
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
    
    public default <T> Streamable<T> mapIf(
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
    
    public default <T> Streamable<T> mapIf(
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
    
    public default <T> Streamable<T> mapWithIndex(BiFunction<? super Integer, ? super DATA, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(index.getAndIncrement(), each));
    }
    
    public default <T1, T> Streamable<T> mapWithIndex(
                Function<? super DATA, ? extends T1>       mapper1,
                BiFunction<? super Integer, ? super T1, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each)));
    }
    
    public default <T1, T2, T> Streamable<T> mapWithIndex(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Func3<? super Integer, ? super T1, ? super T2, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each),
                                mapper2.apply(each)));
    }
    
    public default <T1, T2, T3, T> Streamable<T> mapWithIndex(
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
    
    public default <T1, T2, T3, T4, T> Streamable<T> mapWithIndex(
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
    
    public default <T1, T2, T3, T4, T5, T> Streamable<T> mapWithIndex(
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
        Streamable<Tuple2<T1, T2>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return mapCombine(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> 
        Streamable<Tuple3<T1, T2, T3>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return mapCombine(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> 
        Streamable<Tuple4<T1, T2, T3, T4>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return mapCombine(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        Streamable<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return mapCombine(mapper1, mapper2, mapper3, mapper4, mapper5,
                   (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    public default <T1, T2, T3, T4, T5, T6> 
        Streamable<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return mapCombine(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    //-- Map and combine --
    
    public default <T1, T2, T> 
        Streamable<T> mapCombine(
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
        Streamable<T> mapCombine(
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
        Streamable<T> mapCombine(
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
        Streamable<T> mapCombine(
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
        Streamable<T> mapCombine(
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
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FunctionalMap<KEY, VALUE>> mapToMap(
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
    
    public default Streamable<DATA> filterNonNull() {
        return stream(stream -> stream.filter(Objects::nonNull));
    }
    
    public default Streamable<DATA> filterIn(Collection<? super DATA> collection) {
        return stream(stream -> {
            return (collection == null)
                ? Stream.empty()
                : stream.filter(data -> collection.contains(data));
        });
    }
    
    public default Streamable<DATA> exclude(Predicate<? super DATA> predicate) {
        return stream(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(data -> !predicate.test(data));
        });
    }
    
    public default Streamable<DATA> exclude(Collection<? super DATA> collection) {
        return stream(stream -> {
            return (collection == null)
                ? stream
                : stream.filter(data -> !collection.contains(data));
        });
    }
    
    public default <T> Streamable<DATA> filter(Class<T> clzz) {
        return filter(clzz::isInstance);
    }
    
    public default <T> Streamable<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return filter(value -> {
            if (!clzz.isInstance(value))
                return false;
            
            val target = clzz.cast(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> Streamable<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    //-- Peek --
    
    public default <T extends DATA> Streamable<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return peek(value -> {
            if (!clzz.isInstance(value))
                return;
            
            val target = clzz.cast(value);
            theConsumer.accept(target);
        });
    }
    public default Streamable<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return peek(value -> {
            if (!selector.test(value))
                return;
            
            theConsumer.accept(value);
        });
    }
    public default <T> Streamable<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            theConsumer.accept(target);
        });
    }
    
    public default <T> Streamable<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
    //-- FlatMap --
    
    public default <T> Streamable<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Stream<T>> mapper, 
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> Streamable<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Stream<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Stream<T>> mapper2,
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> Streamable<T> flatMapIf(
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
    
    public default <T> Streamable<T> flatMapIf(
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
    
    public default <T> Streamable<T> flatMapIf(
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
    
    public default <T> Streamable<T> flatMapIf(
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
    
    
    
    
    
    
    
    
    
    //-- Check if we really need --
    
    public Streamable<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);
    
}
