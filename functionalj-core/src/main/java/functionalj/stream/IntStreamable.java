package functionalj.stream;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.val;


public interface IntStreamable {

    
    public static IntStreamable empty() {
        return ()->IntStreamPlus.empty();
    }
    
    public static IntStreamable emptyStreamable() {
        return ()->IntStreamPlus.empty();
    }
    
    public static IntStreamable of(int ... data) {
        val length = data.length;
        val ints   = new int[data.length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->IntStream.of(ints);
    }
    
    public static IntStreamable steamableOf(int ... data) {
        return of(data);
    }
    
    public static IntStreamable ints(int ... data) {
        return of(data);
    }
    
    public static IntStreamable from(IntSupplier supplier) {
        return ()->IntStreamPlus.generate(supplier);
    }
    
    public static IntStreamable repeat(int ... data) {
        val length = data.length;
        val ints   = new int[data.length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->Stream
                    .generate    (() -> IntStream.of(ints))
                    .flatMapToInt(s -> s);
    }
    
    public static IntStreamable cycle(int ... data) {
        return IntStreamable.repeat(data);
    }
    
    public static IntStreamable loop(int time) {
        return ()->IntStreamPlus.range(0, time);
    }
    
    public static IntStreamable loop() {
        return ()->IntStreamPlus.range(0, Integer.MAX_VALUE);
    }
    
    public static IntStreamable infiniteInt() {
        return ()->IntStreamPlus.range(0, Integer.MAX_VALUE);
    }
    
    public static IntStreamable range(int startInclusive, int endExclusive) {
        return ()->IntStreamPlus.range(startInclusive, endExclusive);
    }
    
    public static IntStreamable concat(IntStream ... streams) {
        return ()->StreamPlus.of(streams).flatMapToInt(s -> s);
    }
    public static IntStreamable combine(IntStream ... streams) {
        return ()->StreamPlus.of(streams).flatMapToInt(s -> s);
    }
    
    public static IntStreamable generate(Supplier<IntStream> streamSupplier) {
        return ()->StreamPlus.of(streamSupplier).flatMapToInt(s -> s.get());
    }
    public static IntStreamable generateWith(Supplier<IntStream> streamSupplier) {
        return ()->StreamPlus.of(streamSupplier).flatMapToInt(s -> s.get());
    }
    
    public static IntStreamable iterate(int seed, IntUnaryOperator f) {
        return ()->IntStreamPlus.iterate(seed, f);
    }
    
    public static IntStreamable compound(int seed, IntUnaryOperator f) {
        return ()->IntStreamPlus.compound(seed, f);
    }
    
    public static IntStreamable iterate(int seed1, int seed2, IntBinaryOperator f) {
        return ()->IntStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static IntStreamable compound(int seed1, int seed2, IntBinaryOperator f) {
        return ()->IntStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static <D> IntStreamable with(
            Streamable<D>                  source, 
            Function<Stream<D>, IntStream> action) {
        return new IntStreamable() {
            @Override
            public IntStream stream() {
                val sourceStream = source.stream();
                val targetStream = action.apply(sourceStream);
                return targetStream;
            }
        };
    }
    public static <D> Streamable<D> with(
            IntStreamable                  source, 
            Function<IntStream, Stream<D>> action) {
        return new Streamable<D>() {
            @Override
            public StreamPlus<D> stream() {
                val sourceStream = source.stream();
                val targetStream = action.apply(sourceStream);
                return StreamPlus.from(targetStream);
            }
        };
    }
    public static IntStreamable from(
            IntStreamable                  source,
            Function<IntStream, IntStream> action) {
        return new IntStreamable() {
            @Override
            public IntStream stream() {
                val sourceStream = source.stream();
                val targetStream = action.apply(sourceStream);
                return targetStream;
            }
        };
    }
    
    //== Stream ==
    
    public IntStream stream();
    
//    
//    //== Helper functions ==
//    
//    public default <TARGET> Streamable<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
//        return Streamable.with(this, action);
//    }
//    
//    public default <TARGET> Streamable<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action) {
//        return Streamable.from(this, action);
//    }
    
    //== Stream specific ==
    
    public default IntStreamable sequential() {
        return IntStreamable.from(this, IntStream::sequential);
    }
    
    public default IntStreamable parallel() {
        return IntStreamable.from(this, IntStream::parallel);
    } 
    
    public default IntStreamable unordered() {
        return IntStreamable.from(this, IntStream::unordered);
    }
    
//    //== Functionalities ==
//    
//    public default IntStreamPlus mapToInt(ToIntFunction<? super DATA> mapper) {
//        return IntStreamPlus.from(stream().mapToInt(mapper));
//    }
//    
//    public default LongStreamPlus mapToLong(ToLongFunction<? super DATA> mapper) {
//        return stream()
//                .mapToLong(mapper);
//    }
//    
//    public default DoubleStreamPlus mapToDouble(ToDoubleFunction<? super DATA> mapper) {
//        return stream()
//                .mapToDouble(mapper);
//    }
//    
//    public default IntStreamPlus flatMapToInt(Function<? super DATA, ? extends IntStream> mapper) {
//        return IntStreamPlus.from(stream().flatMapToInt(mapper));
//    }
//    
//    public default LongStreamPlus flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
//        return stream()
//                .flatMapToLong(mapper);
//    }
//    
//    public default DoubleStreamPlus flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
//        return stream()
//                .flatMapToDouble(mapper);
//    }
//    
//    public default <TARGET> Streamable<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
//        return deriveWith(stream -> {
//            return stream.map(mapper);
//        });
//    }
//    
//    public default <TARGET> Streamable<TARGET> flatMap(Function<? super DATA, ? extends Streamable<? extends TARGET>> mapper) {
//        return deriveWith(stream -> {
//            return stream.flatMap(e -> mapper.apply(e).stream());
//        });
//    }
//    
//    public default Streamable<DATA> filter(Predicate<? super DATA> predicate) {
//        return deriveWith(stream -> {
//            return (predicate == null)
//                ? stream
//                : stream.filter(predicate);
//        });
//    }
//    
//    public default Streamable<DATA> peek(Consumer<? super DATA> action) {
//        return deriveWith(stream -> {
//            return (action == null)
//                    ? stream
//                    : stream.peek(action);
//        });
//    }
//    
//    //-- Limit/Skip --
//    
//    public default Streamable<DATA> limit(long maxSize) {
//        return deriveWith(stream -> {
//            return stream.limit(maxSize);
//        });
//    }
//    
//    public default Streamable<DATA> skip(long n) {
//        return deriveWith(stream -> {
//            return stream.skip(n);
//        });
//    }
//    
//    public default Streamable<DATA> limit(Long maxSize) {
//        return deriveWith(stream -> {
//            return ((maxSize == null) || (maxSize.longValue() < 0))
//                    ? stream
//                    : stream.limit(maxSize);
//        });
//    }
//    
//    public default Streamable<DATA> skip(Long startAt) {
//        return deriveWith(stream -> {
//            return ((startAt == null) || (startAt.longValue() < 0))
//                    ? stream
//                    : stream.skip(startAt);
//        });
//    }
//    
//    public default Streamable<DATA> skipWhile(Predicate<? super DATA> condition) {
//        return deriveWith(stream -> {
//            return StreamPlus.from(stream).skipWhile(condition);
//        });
//    }
//    
//    public default Streamable<DATA> skipUntil(Predicate<? super DATA> condition) {
//        return deriveWith(stream -> {
//            return StreamPlus.from(stream).skipUntil(condition);
//        });
//    }
//    
//    public default Streamable<DATA> takeWhile(Predicate<? super DATA> condition) {
//        return deriveWith(stream -> {
//            return StreamPlus.from(stream).takeWhile(condition);
//        });
//    }
//    
//    public default Streamable<DATA> takeUntil(Predicate<? super DATA> condition) {
//        return deriveWith(stream -> {
//            return StreamPlus.from(stream).takeUntil(condition);
//        });
//    }
//    
//    public default Streamable<DATA> distinct() {
//        return deriveWith(stream -> {
//            return stream.distinct();
//        });
//    }
//    
//    //-- Sorted --
//    
//    public default Streamable<DATA> sorted() {
//        return deriveWith(stream -> {
//            return stream.sorted();
//        });
//    }
//    
//    public default Streamable<DATA> sorted(
//            Comparator<? super DATA> comparator) {
//        return deriveWith(stream -> {
//            return (comparator == null)
//                    ? stream.sorted()
//                    : stream.sorted(comparator);
//        });
//    }
//    
//    public default <T extends Comparable<? super T>> Streamable<DATA> sortedBy(
//            Function<? super DATA, T> mapper) {
//        return deriveWith(stream -> {
//            return stream.sorted((a, b) -> {
//                        T vA = mapper.apply(a);
//                        T vB = mapper.apply(b);
//                        return vA.compareTo(vB);
//                    });
//        });
//    }
//    
//    public default <T> Streamable<DATA> sortedBy(
//            Function<? super DATA, T> mapper, 
//            Comparator<T>             comparator) {
//        return deriveWith(stream -> {
//            return stream.sorted((a, b) -> {
//                    T vA = mapper.apply(a);
//                    T vB = mapper.apply(b);
//                    return Objects.compare(vA,  vB, comparator);
//                });
//        });
//    }
//    
//    //-- Terminate --
//    
//    public default void forEach(Consumer<? super DATA> action) {
//        if (action == null)
//            return;
//        
//        stream().forEach(action);
//    }
//    
//    public default void forEachOrdered(Consumer<? super DATA> action) {
//        if (action == null)
//            return;
//        
//        stream().forEachOrdered(action);
//    }
//    
//    public default DATA reduce(DATA identity, BinaryOperator<DATA> accumulator) {
//        return stream().reduce(identity, accumulator);
//    }
//    
//    public default Optional<DATA> reduce(BinaryOperator<DATA> accumulator) {
//        return stream().reduce(accumulator);
//    }
//    
//    public default <U> U reduce(
//                    U                              identity,
//                    BiFunction<U, ? super DATA, U> accumulator,
//                    BinaryOperator<U>              combiner) {
//        return stream().reduce(identity, accumulator, combiner);
//    }
//    
//    public default <R, A> R collect(
//            Collector<? super DATA, A, R> collector) {
//        return stream().collect(collector);
//    }
//    
//    public default <R> R collect(
//            Supplier<R>                 supplier,
//            BiConsumer<R, ? super DATA> accumulator,
//            BiConsumer<R, R>            combiner) {
//        return stream().collect(supplier, accumulator, combiner);
//    }
//    
//    public default Optional<DATA> min(
//            Comparator<? super DATA> comparator) {
//        return stream().min(comparator);
//    }
//    
//    public default Optional<DATA> max(
//            Comparator<? super DATA> comparator) {
//        return stream().max(comparator);
//    }
//    
//    public default long count() {
//        return stream().count();
//    }
//    
//    public default int size() {
//        return (int)stream().count();
//    }
//    
//    public default boolean anyMatch(
//            Predicate<? super DATA> predicate) {
//        return stream().anyMatch(predicate);
//    }
//    
//    public default boolean allMatch(
//            Predicate<? super DATA> predicate) {
//        return stream().allMatch(predicate);
//    }
//    
//    public default boolean noneMatch(
//            Predicate<? super DATA> predicate) {
//        return stream().noneMatch(predicate);
//    }
//    
//    public default Optional<DATA> findFirst() {
//        return stream().findFirst();
//    }
//    
//    public default Optional<DATA> findAny() {
//        return stream().findAny();
//    }
//    
//    //== toXXX ===
//    
//    public default Object[] toArray() {
//        return stream().toArray();
//    }
//    
//    public default <T> T[] toArray(T[] a) {
//        return StreamPlus.of(stream()).toJavaList().toArray(a);
//    }
//    
//    public default <A> A[] toArray(IntFunction<A[]> generator) {
//        return stream().toArray(generator);
//    }
//    
//    public default List<DATA> toJavaList() {
//        return stream().collect(Collectors.toList());
//    }
//    
//    public default byte[] toByteArray(Func1<DATA, Byte> toByte) {
//        val byteArray = new ByteArrayOutputStream();
//        stream().forEach(d -> byteArray.write(toByte.apply(d)));
//        return byteArray.toByteArray();
//    }
//    
//    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
//        return mapToInt(toInt).toArray();
//    }
//    
//    public default long[] toLongArray(ToLongFunction<DATA> toLong) {
//        return mapToLong(toLong).toArray();
//    }
//    
//    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
//        return mapToDouble(toDouble).toArray();
//    }
//    
//    public default FuncList<DATA> toList() {
//        return toImmutableList();
//    }
//    
//    public default FuncList<DATA> toLazyList() {
//        return FuncList.from(this);
//    }
//    
//    public default String toListString() {
//        return "[" + map(String::valueOf).collect(Collectors.joining(", ")) + "]";
//    }
//    
//    public default ImmutableList<DATA> toImmutableList() {
//        return ImmutableList.from(stream());
//    }
//    
//    public default List<DATA> toMutableList() {
//        return toArrayList();
//    }
//    
//    public default ArrayList<DATA> toArrayList() {
//        return new ArrayList<DATA>(toJavaList());
//    }
//    
//    public default Set<DATA> toSet() {
//        return new HashSet<DATA>(stream().collect(Collectors.toSet()));
//    }
//    
//    //-- Iterator --
//    
//    public default IteratorPlus<DATA> iterator() {
//        return IteratorPlus.from(stream());
//    }
//    
//    public default Spliterator<DATA> spliterator() {
//        return Spliterators.spliteratorUnknownSize(iterator(), 0);
//    }
//    
//    //== Plus ==
//    
//    public default String joinToString() {
//        return map(StrFuncs::toStr)
//                .collect(Collectors.joining());
//    }
//    public default String joinToString(String delimiter) {
//        return map(StrFuncs::toStr)
//                .collect(Collectors.joining(delimiter));
//    }
//    
//    //++ Plus w/ Self ++
//    
//    public default <T> Pipeable<Streamable<DATA>> pipable() {
//        return Pipeable.of(this);
//    }
//    
//    public default <T> T pipe(Function<? super Streamable<DATA>, T> piper) {
//        return piper.apply(this);
//    }
//    
//    //== Spawn ==
//    
//    
//    public default <T> Streamable<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapper) {
//        return deriveWith(stream -> {
//            return stream().spawn(mapper);
//        });
//    }
//    
//    //== accumulate + restate ==
//    
//    public default Streamable<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
//        return deriveWith(stream -> {
//            val iterator = StreamPlus.from(stream).iterator();
//            if (!iterator.hasNext())
//                return StreamPlus.empty();
//            
//            val prev = new AtomicReference<DATA>(iterator.next());
//            return StreamPlus.concat(
//                        StreamPlus.of(prev.get()),
//                        iterator.stream().map(n -> {
//                            val next = accumulator.apply(n, prev.get());
//                            prev.set(next);
//                            return next;
//                        })
//                    );
//        });
//    }
//    
//    public default Streamable<DATA> restate(BiFunction<? super DATA, Streamable<DATA>, Streamable<DATA>> restater) {
//        val func = (UnaryOperator<Tuple2<DATA, Streamable<DATA>>>)((Tuple2<DATA, Streamable<DATA>> pair) -> {
//            val stream   = pair._2();
//            val iterator = stream.iterator();
//            if (!iterator.hasNext())
//                return null;
//            
//            val head = iterator.next();
//            val tail =restater.apply(head, ()->iterator.stream());
//            return Tuple2.of(head, tail);
//        });
//        val seed = Tuple2.of((DATA)null, this);
//        val endStream = (Streamable<DATA>)(()->StreamPlus.iterate(seed, func).takeUntil(t -> t == null).skip(1).map(t -> t._1()));
//        return endStream;
//    }
//    
////    
////    public default <T extends Comparable<? super T>> FuncList<Tuple2<DATA, Double>> toPercentilesOf(Function<? super DATA, T> mapper) {
////        FuncList<Tuple2<Integer, DATA>> list 
////                = mapWithIndex(Tuple2::of)
////                .sortedBy(tuple -> mapper.apply(tuple._2()))
////                .toImmutableList();
////        return Helper.toPercentilesOf(size() - 1, list);
////    }
////    
////    public default <T> FuncList<Tuple2<DATA, Double>> toPercentilesOf(Function<? super DATA, T> mapper, Comparator<T> comparator) {
////        FuncList<Tuple2<Integer, DATA>> list 
////                = mapWithIndex(Tuple2::of)
////                .sortedBy(tuple -> mapper.apply(tuple._2()), comparator)
////                .toImmutableList();
////        return Helper.toPercentilesOf(size() - 1, list);
////    }
//    
}
