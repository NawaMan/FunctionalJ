package functionalj.list;

import static functionalj.lens.Access.$I;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.functions.Func3;
import functionalj.functions.Func4;
import functionalj.functions.Func5;
import functionalj.functions.Func6;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import functionalj.tuple.IntTuple2;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public interface FuncList<DATA> 
                    extends 
                        ReadOnlyList<DATA>, 
                        Streamable<DATA>,
                        Pipeable<FuncList<DATA>> {
    
    public static <T> FuncList<T> empty() {
        return ImmutableList.empty();
    }
    @SafeVarargs
    public static <T> FuncList<T> of(T ... data) {
        return ImmutableList.of(data);
    }
    @SafeVarargs
    public static <T> FuncList<T> AllOf(T ... data) {
        return ImmutableList.of(data);
    }
    public static <T> FuncList<T> from(Collection<T> data) {
        return ImmutableList.from(data);
    }
    public static <T> FuncList<T> from(List<T> data) {
        return ImmutableList.from(data);
    }
    @SafeVarargs
    public static <T> FuncList<T> listOf(T ... data) {
    	return ImmutableList.of(data);
    }
    public static <T> FuncList<T> from(Streamable<T> streamable) {
        return ImmutableList.from(streamable);
    }
    public static <T> FuncList<T> from(Stream<T> stream) {
        return new ImmutableList<T>(stream.collect(Collectors.toList()));
    }
    public static <T> FuncList<T> from(ReadOnlyList<T> readOnlyList) {
        return ImmutableList.from(readOnlyList);
    }
    public static <T> FuncList<T> from(FuncList<T> funcList) {
        return ImmutableList.from(funcList);
    }
    
    //== Override ==
    
    @Override
    public default <TARGET> FuncList<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
        return new FuncListStream<DATA, TARGET>(this, action);
    }
    
    @Override
    public default <TARGET> FuncList<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action) {
        return FuncListStream.from((Supplier<Stream<TARGET>>)()->{
            return action.apply(FuncList.this);
        });
    }
    
    @Override
    public default FuncList<DATA> __data() throws Exception {
        return this;
    }
    
    @Override
    public default List<DATA> toList() {
        return this;
    }
    @Override
    public default FuncList<DATA> toFuncList() {
        return this;
    }
    
    //-- List specific --
    
    public default FuncList<Integer> indexesOf(Predicate<? super DATA> check) {
        return this
                .mapWithIndex((index, data)-> check.test(data) ? index : -1)
                .filter($I.thatNotEqualsTo(-1))
                .toImmutableList();
    }
    
    @Override
    public default int indexOf(Object o) {
        return indexesOf(each -> Objects.equals(o, each))
                .findFirst()
                .orElse(-1);
    }
    
    public default Optional<DATA> first() {
        val valueRef = new AtomicReference<DATA>();
        if (!Helper.hasAt(stream(), 0, valueRef))
            return Optional.empty();
        
        return Optional.ofNullable(valueRef.get());
    }
    
    public default FuncList<DATA> rest() {
        return deriveWith(stream -> stream.skip(1));
    }
    
    public default FuncList<IntTuple2<DATA>> select(Predicate<? super DATA> check) {
        return this
                .mapWithIndex((index, data)-> check.test(data) ? new IntTuple2<DATA>(index, data) : null)
                .filterNonNull();
    }
    
    //== Modified methods ==
    
    @Override
    public default <T> T[] toArray(T[] a) {
        return toList().toArray(a);
    }
    
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> append(DATA ... values) {
        return deriveWith(stream -> 
                Stream.concat(stream, Stream.of(values)));
    }
    
    public default FuncList<DATA> appendAll(Collection<? extends DATA> collection) {
        return ((collection == null) || collection.isEmpty())
                ? this
                : deriveWith(stream -> Stream.concat(stream, collection.stream()));
    }
    
    public default FuncList<DATA> appendAll(Streamable<? extends DATA> streamable) {
        return (streamable == null)
                ? this
                : deriveWith(stream -> Stream.concat(stream, streamable.stream()));
    }
    
    public default FuncList<DATA> appendAll(Supplier<Stream<? extends DATA>> supplier) {
        return (supplier == null)
                ? this
                : deriveWith(stream -> Stream.concat(stream, supplier.get()));
    }
    
    public default FuncList<DATA> with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        val i = new AtomicInteger();
        return deriveWith(stream -> stream.map(each -> (i.getAndIncrement() == index) ? value : each));
    }
    
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> insertAt(int index, DATA ... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        return FuncListStream.from(deriveFrom((Streamable<DATA> streamable)->{
            return Stream.concat(
                    streamable.stream().limit(index), Stream.concat(
                    Stream.of(elements),
                    streamable.stream().skip(index + 1)));
        }));
    }
    
    public default FuncList<DATA> insertAllAt(int index, Collection<? extends DATA> collection) {
        if ((collection == null)
          || collection.isEmpty())
            return this;
        
        return FuncListStream.from(deriveFrom((Streamable<DATA> streamable)->{
            return (Stream<DATA>)Stream.concat(
                    streamable.stream().limit(index), Stream.concat(
                    collection.stream(),
                    streamable.stream().skip(index + 1)));
        }));
    }
    
    public default FuncList<DATA> insertAllAt(int index, Streamable<? extends DATA> theStreamable) {
        if (theStreamable == null)
            return this;
        
        return FuncListStream.from(deriveFrom((Streamable<DATA> streamable)->{
            return Stream.concat(
                    streamable.stream().limit(index), Stream.concat(
                    theStreamable.stream(),
                    streamable.stream().skip(index + 1)));
        }));
    }
    
    public default FuncList<DATA> excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        return FuncListStream.from(deriveFrom((Streamable<DATA> streamable)->{
            return Stream.concat(
                    streamable.stream().limit(index), 
                    streamable.stream().skip(index + 2));
        }));
    }
    
    public default FuncList<DATA> excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        return FuncListStream.from(deriveFrom((Streamable<DATA> streamable)->{
            return Stream.concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(fromIndexInclusive + count));
        }));
    }
    
    public default FuncList<DATA> excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive
                                            + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
        
        return FuncListStream.from(deriveFrom((Streamable<DATA> streamable)->{
            return Stream.concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(toIndexExclusive + 1));
        }));
    }
    
    @Override
    public default FuncList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return new FuncListStream<>(this, stream -> stream.skip(fromIndexInclusive).limit(length));
    }
    
    //==================================================================================================================
    // NOTE: The following part of the code was copied from StreamPlus
    //       We will write a program to do the copy and replace ...
    //         in the mean time, change this in StreamPlus.
    //++ Plus w/ Self ++
    
    @Override
    public default FuncList<DATA> sequential() {
        return deriveWith(stream -> { 
            return stream.sequential();
        });
    }
    
    @Override
    public default FuncList<DATA> parallel() {
        return deriveWith(stream -> { 
            return stream.parallel();
        });
    } 
    
    @Override
    public default FuncList<DATA> unordered() {
        return deriveWith(stream -> { 
            return stream.unordered();
        });
    }
    
    @Override
    public default FuncList<DATA> onClose(Runnable closeHandler) {
        return deriveWith(stream -> { 
            return stream.onClose(closeHandler);
        });
    }
    
    public default <TARGET> FuncList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return stream.map(mapper);
        });
    }
    
    public default <TARGET> FuncList<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return deriveWith(stream -> {
            return stream.flatMap(mapper);
        });
    }
    
    public default FuncList<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(predicate);
        });
    }
    public default FuncList<DATA> peek(Consumer<? super DATA> action) {
        return deriveWith(stream -> {
            return (action == null)
                    ? stream
                    : stream.peek(action);
        });
    }
    
    //-- Limit/Skip --
    
    @Override
    public default FuncList<DATA> limit(long maxSize) {
        return deriveWith(stream -> {
            return stream.limit(maxSize);
        });
    }
    
    @Override
    public default FuncList<DATA> skip(long n) {
        return deriveWith(stream -> {
            return stream.skip(n);
        });
    }
    
    public default FuncList<DATA> skipWhile(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).skipWhile(condition);
        });
    }
    
    public default FuncList<DATA> skipUntil(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).skipUntil(condition);
        });
    }
    
    public default FuncList<DATA> takeWhile(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).takeWhile(condition);
        });
    }
    
    public default FuncList<DATA> takeUntil(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).takeUntil(condition);
        });
    }
    
    @Override
    public default FuncList<DATA> distinct() {
        return deriveWith(stream -> {
            return stream.distinct();
        });
    }
    
    @Override
    public default FuncList<DATA> sorted() {
        return deriveWith(stream -> {
            return stream.sorted();
        });
    }
    
    @Override
    public default FuncList<DATA> sorted(Comparator<? super DATA> comparator) {
        return deriveWith(stream -> {
            return (comparator == null)
                    ? stream.sorted()
                    : stream.sorted(comparator);
        });
    }
    
    public default FuncList<DATA> limit(Long maxSize) {
        return deriveWith(stream -> {
            return ((maxSize == null) || (maxSize.longValue() < 0))
                    ? stream
                    : stream.limit(maxSize);
        });
    }
    
    public default FuncList<DATA> skip(Long startAt) {
        return deriveWith(stream -> {
            return ((startAt == null) || (startAt.longValue() < 0))
                    ? stream
                    : stream.skip(startAt);
        });
    }
    
    //-- Sorted --
    
    public default <T extends Comparable<? super T>> FuncList<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> FuncList<DATA> sortedBy(Function<? super DATA, T> mapper, Comparator<T> comparator) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return Objects.compare(vA,  vB, comparator);
                });
        });
    }
    
    //--map with condition --
    
    public default FuncList<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    public default <T> FuncList<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker.test(d) ? mapper.apply(d) : elseMapper.apply(d);
        });
    }
    public default <T> FuncList<T> mapIf(
            Predicate<? super DATA>  checker1, Function<? super DATA, T> mapper1, 
            Predicate<? super DATA>  checker2, Function<? super DATA, T> mapper2, 
            Function<? super DATA, T> elseMapper) {
        return map(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                                    : elseMapper.apply(d);
        });
    }
    public default <T> FuncList<T> mapIf(
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
    public default <T> FuncList<T> mapIf(
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
    public default <T> FuncList<T> mapIf(
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
    public default <T> FuncList<T> mapIf(
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
    
    public default <T> FuncList<T> mapIf(
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
    
    public default <T> FuncList<T> mapIf(
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
    
    public default <T> FuncList<T> mapIf(
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
    
    public default <T> FuncList<T> mapWithIndex(BiFunction<? super Integer, ? super DATA, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(index.getAndIncrement(), each));
    }
    
    public default <T1, T> FuncList<T> mapWithIndex(
                Function<? super DATA, ? extends T1>       mapper1,
                BiFunction<? super Integer, ? super T1, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each)));
    }
    
    public default <T1, T2, T> FuncList<T> mapWithIndex(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Func3<? super Integer, ? super T1, ? super T2, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(
                                index.getAndIncrement(),
                                mapper1.apply(each),
                                mapper2.apply(each)));
    }
    
    public default <T1, T2, T3, T> FuncList<T> mapWithIndex(
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
    
    public default <T1, T2, T3, T4, T> FuncList<T> mapWithIndex(
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
    
    public default <T1, T2, T3, T4, T5, T> FuncList<T> mapWithIndex(
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
        FuncList<Tuple2<T1, T2>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return mapThen(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> 
        FuncList<Tuple3<T1, T2, T3>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> 
        FuncList<Tuple4<T1, T2, T3, T4>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        FuncList<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5,
                   (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    public default <T1, T2, T3, T4, T5, T6> 
        FuncList<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5,
                Function<? super DATA, ? extends T6> mapper6) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    //-- Map and combine --
    
    public default <T1, T2, T> 
        FuncList<T> mapThen(
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
        FuncList<T> mapThen(
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
        FuncList<T> mapThen(
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
        FuncList<T> mapThen(
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
        FuncList<T> mapThen(
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default FuncList<DATA> filterNonNull() {
        return deriveWith(stream -> stream.filter(Objects::nonNull));
    }
    
    public default FuncList<DATA> filterIn(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return (collection == null)
                ? Stream.empty()
                : stream.filter(data -> collection.contains(data));
        });
    }
    
    public default FuncList<DATA> exclude(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(data -> !predicate.test(data));
        });
    }
    
    public default FuncList<DATA> exclude(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return (collection == null)
                ? stream
                : stream.filter(data -> !collection.contains(data));
        });
    }
    
    public default <T> FuncList<DATA> filter(Class<T> clzz) {
        return filter(clzz::isInstance);
    }
    
    public default <T> FuncList<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return filter(value -> {
            if (!clzz.isInstance(value))
                return false;
            
            val target = clzz.cast(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    public default <T> FuncList<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }

    public default FuncList<DATA> filterWithIndex(BiFunction<? super Integer, ? super DATA, Boolean> predicate) {
        val index = new AtomicInteger();
        return filter(each -> {
                    return (predicate != null) 
                            && predicate.apply(index.getAndIncrement(), each);
        });
    }
    
    //-- Peek --
    
    public default <T extends DATA> FuncList<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return peek(value -> {
            if (!clzz.isInstance(value))
                return;
            
            val target = clzz.cast(value);
            theConsumer.accept(target);
        });
    }
    public default FuncList<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return peek(value -> {
            if (!selector.test(value))
                return;
            
            theConsumer.accept(value);
        });
    }
    public default <T> FuncList<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            theConsumer.accept(target);
        });
    }
    
    public default <T> FuncList<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
    //-- FlatMap --
    
    public default <T> FuncList<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Stream<T>> mapper, 
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    public default <T> FuncList<T> flatMapIf(
            Predicate<? super DATA> checker1, Function<? super DATA, Stream<T>> mapper1, 
            Predicate<? super DATA> checker2, Function<? super DATA, Stream<T>> mapper2,
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> {
            return checker1.test(d) ? mapper1.apply(d)
                 : checker2.test(d) ? mapper2.apply(d)
                 : elseMapper.apply(d);
        });
    }
    
    public default <T> FuncList<T> flatMapIf(
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
    
    public default <T> FuncList<T> flatMapIf(
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
    
    public default <T> FuncList<T> flatMapIf(
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
    
    public default <T> FuncList<T> flatMapIf(
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
    //==================================================================================================================
    
}
