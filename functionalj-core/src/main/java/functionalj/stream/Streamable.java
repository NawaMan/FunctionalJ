// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.stream;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
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
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.IntIntBiFunction;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import functionalj.tuple.Tuple2;
import lombok.val;

class Helper {
    static <D> FuncList<FuncList<D>> segmentByPercentiles(FuncList<D> list, FuncList<Double> percentiles) {
        val size    = list.size();
        val indexes = percentiles.sorted().map(d -> (int)Math.round(d*size/100)).toArrayList();
        if (indexes.get(indexes.size() - 1) != size) {
            indexes.add(size);
        }
        val lists   = new ArrayList<List<D>>();
        for (int i = 0; i < indexes.size(); i++) {
            lists.add(new ArrayList<D>());
        }
        int idx = 0;
        for (int i = 0; i < size; i++) {
            if (i >= indexes.get(idx)) {
                idx++;
            }
            val l = lists.get(idx);
            val element = list.get(i);
            l.add(element);
        }
        return FuncList.from(
                lists
                .stream()
                .map(each -> (FuncList<D>)StreamPlus.from(each.stream()).toImmutableList()));
    }
//    
//    static <D> FuncList<Tuple2<D, Double>> toPercentilesOf(int size, FuncList<Tuple2<Integer, D>> list) {
//        val sorted
//                = list
//                .mapWithIndex((index, tuper) -> Tuple.of(tuper._1(), tuper._2(), index*100.0/size))
//                .sortedBy(tuple -> tuple._1());
//        FuncList<Tuple2<D, Double>> results = sorted
//                .map(tuple -> Tuple.of(tuple._2(), tuple._3()));
//        return results;
//    }
}

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface Streamable<DATA> 
        extends
            StreamableWithMapCase<DATA>,
            StreamableWithMapThen<DATA>,
            StreamableWithMapTuple<DATA>,
            StreamableWithMapToMap<DATA>,
            StreamableWithSplit<DATA>,
            StreamableWithFillNull<DATA>,
            StreamableWithSegment<DATA>,
            StreamableWithCombine<DATA>,
            StreamableWithCalculate<DATA>,
            StreamableAddtionalOperators<DATA>,
            StreamableAdditionalTerminalOperators<DATA> {
    
    public static <D> Streamable<D> empty() {
        return ()->StreamPlus.empty();
    }
    
    public static <D> Streamable<D> emptyStreamable() {
        return ()->StreamPlus.empty();
    }
    
    @SafeVarargs
    public static <D> Streamable<D> of(D ... data) {
        return ()->StreamPlus.from(Stream.of(data));
    }
    
    @SafeVarargs
    public static <D> Streamable<D> steamableOf(D ... data) {
        return ()->StreamPlus.from(Stream.of(data));
    }
    
    public static <D> Streamable<D> from(Collection<D> collection) {
        return ()->StreamPlus.from(collection.stream());
    }
    
    public static <D> Streamable<D> from(Func0<Stream<D>> supplier) {
        return ()->StreamPlus.from(supplier.get());
    }
    
    public static <D> StreamPlus<D> fromInts(IntStreamable source, Function<IntStreamable, Stream<D>> action) {
        return ()->action.apply(source);
    }
    
    @SafeVarargs
    public static <D> Streamable<D> repeat(D ... data) {
        return ()->StreamPlus.repeat(data);
    }
    
    public static <D> StreamPlus<D> repeat(FuncList<D> data) {
        return ()->StreamPlus.repeat(data);
    }
    
    @SafeVarargs
    public static <D> Streamable<D> cycle(D ... data) {
        return ()->StreamPlus.cycle(data);
    }
    
    public static <D> StreamPlus<D> cycle(FuncList<D> data) {
        return ()->StreamPlus.cycle(data);
    }
    
    public static Streamable<Integer> loop(int time) {
        return ()->StreamPlus.loop(time);
    }
    
    public static Streamable<Integer> loop() {
        return ()->StreamPlus.loop();
    }
    
    public static Streamable<Integer> infiniteInt() {
        return ()->StreamPlus.infiniteInt();
    }
    
    public static Streamable<Integer> range(int startInclusive, int endExclusive) {
        return ()->StreamPlus.range(startInclusive, endExclusive);
    }
    
    // Because people know this.
    @SafeVarargs
    public static <D> Streamable<D> concat(Streamable<D> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    // To avoid name conflict with String.concat
    @SafeVarargs
    public static <D> Streamable<D> combine(Streamable<D> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    public static <D> Streamable<D> generate(Supplier<Supplier<D>> supplier) {
        return ()->StreamPlus.generate(supplier.get());
    }
    
    public static <D> Streamable<D> generateWith(Supplier<Supplier<D>> supplier) {
        return ()->StreamPlus.generate(supplier.get());
    }
    
    public static <D> Streamable<D> iterate(D seed, UnaryOperator<D> f) {
        return ()->StreamPlus.iterate(seed, f);
    }
    
    public static <D> Streamable<D> compound(D seed, UnaryOperator<D> f) {
        return ()->StreamPlus.compound(seed, f);
    }
    
    public static <D> Streamable<D> iterate(D seed1, D seed2, BinaryOperator<D> f) {
        return ()->StreamPlus.iterate(seed1, seed2, f);
    }
    
    public static <D> Streamable<D> compound(D seed1, D seed2, BinaryOperator<D> f) {
        return ()->StreamPlus.compound(seed1, seed2, f);
    }
    
    public static <T1, T2> Streamable<Tuple2<T1, T2>> zipOf(
            Streamable<T1> streamable1, 
            Streamable<T2> streamable2) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(),
                    streamable2.stream());
        };
    }
    
    public static <T1, T2, T> Streamable<T> zipOf(
            Streamable<T1>   streamable1, 
            Streamable<T2>   streamable2,
            Func2<T1, T2, T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(),
                    streamable2.stream(),
                    merger);
        };
    }
    
    public static <T1, T2, T> Streamable<T> zipOf(
            IntStreamable       streamable1, 
            IntStreamable       streamable2,
            IntIntBiFunction<T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(),
                    streamable2.stream(),
                    merger);
        };
    }
    
    public static <T1, T2, T> Streamable<T> zipOf(
            IntStreamable       streamable1, 
            IntStreamable       streamable2,
            int                 defaultValue,
            IntIntBiFunction<T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(),
                    streamable2.stream(),
                    defaultValue,
                    merger);
        };
    }
    
    public static <T1, T2, T> Streamable<T> zipOf(
            IntStreamable       streamable1, 
            int                 defaultValue1,
            IntStreamable       streamable2,
            int                 defaultValue2,
            IntIntBiFunction<T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(), defaultValue1,
                    streamable2.stream(), defaultValue2,
                    merger);
        };
    }
    
    public static <D, T> Streamable<T> with(
            Streamable<D>                  source, 
            Function<Stream<D>, Stream<T>> action) {
        return new Streamable<T>() {
            @Override
            public StreamPlus<T> stream() {
                val sourceStream = source.stream();
                val targetStream = action.apply(sourceStream);
                return StreamPlus.from(targetStream);
            }
        };
    }
    public static <D, T> Streamable<T> from(
            Streamable<D>                      source, 
            Function<Streamable<D>, Stream<T>> action) {
        return new Streamable<T>() {
            @Override
            public StreamPlus<T> stream() {
                val targetStream = action.apply(source);
                return StreamPlus.from(targetStream);
            }
        };
    }
    public static <T> Streamable<T> from(
            IntStreamable                      source, 
            Function<IntStreamable, Stream<T>> action) {
        return new Streamable<T>() {
            @Override
            public StreamPlus<T> stream() {
                val targetStream = action.apply(source);
                return StreamPlus.from(targetStream);
            }
        };
    }
    
    //== Stream ==
    
    public StreamPlus<DATA> stream();
    
    
    //== Helper functions ==
    
    public default <TARGET> Streamable<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
        return Streamable.with(this, action);
    }
    
    public default <TARGET> Streamable<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action) {
        return Streamable.from(this, action);
    }
    
    //== Stream specific ==
    
    public default Streamable<DATA> sequential() {
        return deriveWith(stream -> { 
            return stream.sequential();
        });
    }
    
    public default Streamable<DATA> parallel() {
        return deriveWith(stream -> { 
            return stream.parallel();
        });
    } 
    
    public default Streamable<DATA> unordered() {
        return deriveWith(stream -> { 
            return stream.unordered();
        });
    }
    
    //== Functionalities ==
    
    public default IntStreamPlus mapToInt(ToIntFunction<? super DATA> mapper) {
        return IntStreamPlus.from(stream().mapToInt(mapper));
    }
    
    public default LongStreamPlus mapToLong(ToLongFunction<? super DATA> mapper) {
        return stream()
                .mapToLong(mapper);
    }
    
    public default DoubleStreamPlus mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return stream()
                .mapToDouble(mapper);
    }
    
    public default IntStreamPlus flatMapToInt(Function<? super DATA, ? extends IntStream> mapper) {
        return IntStreamPlus.from(stream().flatMapToInt(mapper));
    }
    
    public default LongStreamPlus flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
        return stream()
                .flatMapToLong(mapper);
    }
    
    public default DoubleStreamPlus flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
        return stream()
                .flatMapToDouble(mapper);
    }
    
    public default <TARGET> Streamable<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return stream.map(mapper);
        });
    }
    
    public default <TARGET> Streamable<TARGET> flatMap(Function<? super DATA, ? extends Streamable<? extends TARGET>> mapper) {
        return deriveWith(stream -> {
            return stream.flatMap(e -> mapper.apply(e).stream());
        });
    }
    
    public default Streamable<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(predicate);
        });
    }
    
    public default Streamable<DATA> peek(Consumer<? super DATA> action) {
        return deriveWith(stream -> {
            return (action == null)
                    ? stream
                    : stream.peek(action);
        });
    }
    
    //-- Limit/Skip --
    
    public default Streamable<DATA> limit(long maxSize) {
        return deriveWith(stream -> {
            return stream.limit(maxSize);
        });
    }
    
    public default Streamable<DATA> skip(long n) {
        return deriveWith(stream -> {
            return stream.skip(n);
        });
    }
    
    public default Streamable<DATA> limit(Long maxSize) {
        return deriveWith(stream -> {
            return ((maxSize == null) || (maxSize.longValue() < 0))
                    ? stream
                    : stream.limit(maxSize);
        });
    }
    
    public default Streamable<DATA> skip(Long startAt) {
        return deriveWith(stream -> {
            return ((startAt == null) || (startAt.longValue() < 0))
                    ? stream
                    : stream.skip(startAt);
        });
    }
    
    public default Streamable<DATA> skipWhile(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).skipWhile(condition);
        });
    }
    
    public default Streamable<DATA> skipUntil(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).skipUntil(condition);
        });
    }
    
    public default Streamable<DATA> takeWhile(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).takeWhile(condition);
        });
    }
    
    public default Streamable<DATA> takeUntil(Predicate<? super DATA> condition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).takeUntil(condition);
        });
    }
    
    public default Streamable<DATA> distinct() {
        return deriveWith(stream -> {
            return stream.distinct();
        });
    }
    
    //-- Sorted --
    
    public default Streamable<DATA> sorted() {
        return deriveWith(stream -> {
            return stream.sorted();
        });
    }
    
    public default Streamable<DATA> sorted(
            Comparator<? super DATA> comparator) {
        return deriveWith(stream -> {
            return (comparator == null)
                    ? stream.sorted()
                    : stream.sorted(comparator);
        });
    }
    
    public default <T extends Comparable<? super T>> Streamable<DATA> sortedBy(
            Function<? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> Streamable<DATA> sortedBy(
            Function<? super DATA, T> mapper, 
            Comparator<T>             comparator) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return Objects.compare(vA,  vB, comparator);
                });
        });
    }
    
    //-- Terminate --
    
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
    
    public default <R, A> R collect(
            Collector<? super DATA, A, R> collector) {
        return stream().collect(collector);
    }
    
    public default <R> R collect(
            Supplier<R>                 supplier,
            BiConsumer<R, ? super DATA> accumulator,
            BiConsumer<R, R>            combiner) {
        return stream().collect(supplier, accumulator, combiner);
    }
    
    public default Optional<DATA> min(
            Comparator<? super DATA> comparator) {
        return stream().min(comparator);
    }
    
    public default Optional<DATA> max(
            Comparator<? super DATA> comparator) {
        return stream().max(comparator);
    }
    
    public default long count() {
        return stream().count();
    }
    
    public default int size() {
        return (int)stream().count();
    }
    
    public default boolean anyMatch(
            Predicate<? super DATA> predicate) {
        return stream().anyMatch(predicate);
    }
    
    public default boolean allMatch(
            Predicate<? super DATA> predicate) {
        return stream().allMatch(predicate);
    }
    
    public default boolean noneMatch(
            Predicate<? super DATA> predicate) {
        return stream().noneMatch(predicate);
    }
    
    public default Optional<DATA> findFirst() {
        return stream().findFirst();
    }
    
    public default Optional<DATA> findAny() {
        return stream().findAny();
    }
    
    //== toXXX ===
    
    public default Object[] toArray() {
        return stream().toArray();
    }
    
    public default <T> T[] toArray(T[] a) {
        return StreamPlus.of(stream()).toJavaList().toArray(a);
    }
    
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream().toArray(generator);
    }
    
    public default List<DATA> toJavaList() {
        return stream().collect(Collectors.toList());
    }
    
    public default byte[] toByteArray(Func1<DATA, Byte> toByte) {
        val byteArray = new ByteArrayOutputStream();
        stream().forEach(d -> byteArray.write(toByte.apply(d)));
        return byteArray.toByteArray();
    }
    
    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
        return mapToInt(toInt).toArray();
    }
    
    public default long[] toLongArray(ToLongFunction<DATA> toLong) {
        return mapToLong(toLong).toArray();
    }
    
    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
        return mapToDouble(toDouble).toArray();
    }
    
    public default FuncList<DATA> toList() {
        return toImmutableList();
    }
    
    public default FuncList<DATA> toLazyList() {
        return FuncList.from(this);
    }
    
    public default String toListString() {
        return "[" + map(String::valueOf).collect(Collectors.joining(", ")) + "]";
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.from(stream());
    }
    
    public default List<DATA> toMutableList() {
        return toArrayList();
    }
    
    public default ArrayList<DATA> toArrayList() {
        return new ArrayList<DATA>(toJavaList());
    }
    
    public default Set<DATA> toSet() {
        return new HashSet<DATA>(stream().collect(Collectors.toSet()));
    }
    
    //-- Iterator --
    
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    //== Plus ==
    
    public default String joinToString() {
        return map(StrFuncs::toStr)
                .collect(Collectors.joining());
    }
    public default String joinToString(String delimiter) {
        return map(StrFuncs::toStr)
                .collect(Collectors.joining(delimiter));
    }
    
    //++ Plus w/ Self ++
    
    public default <T> Pipeable<Streamable<DATA>> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super Streamable<DATA>, T> piper) {
        return piper.apply(this);
    }
    
    //== Spawn ==
    
    
    public default <T> Streamable<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapper) {
        return deriveWith(stream -> {
            return stream().spawn(mapper);
        });
    }
    
    //== accumulate + restate ==
    
    public default Streamable<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
        return deriveWith(stream -> {
            val iterator = StreamPlus.from(stream).iterator();
            if (!iterator.hasNext())
                return StreamPlus.empty();
            
            val prev = new AtomicReference<DATA>(iterator.next());
            return StreamPlus.concat(
                        StreamPlus.of(prev.get()),
                        iterator.stream().map(n -> {
                            val next = accumulator.apply(n, prev.get());
                            prev.set(next);
                            return next;
                        })
                    );
        });
    }
    
    public default Streamable<DATA> restate(BiFunction<? super DATA, Streamable<DATA>, Streamable<DATA>> restater) {
        val func = (UnaryOperator<Tuple2<DATA, Streamable<DATA>>>)((Tuple2<DATA, Streamable<DATA>> pair) -> {
            val stream   = pair._2();
            val iterator = stream.iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = iterator.next();
            val tail =restater.apply(head, ()->iterator.stream());
            return Tuple2.of(head, tail);
        });
        val seed = Tuple2.of((DATA)null, this);
        val endStream = (Streamable<DATA>)(()->StreamPlus.iterate(seed, func).takeUntil(t -> t == null).skip(1).map(t -> t._1()));
        return endStream;
    }
    
//    
//    public default <T extends Comparable<? super T>> FuncList<Tuple2<DATA, Double>> toPercentilesOf(Function<? super DATA, T> mapper) {
//        FuncList<Tuple2<Integer, DATA>> list 
//                = mapWithIndex(Tuple2::of)
//                .sortedBy(tuple -> mapper.apply(tuple._2()))
//                .toImmutableList();
//        return Helper.toPercentilesOf(size() - 1, list);
//    }
//    
//    public default <T> FuncList<Tuple2<DATA, Double>> toPercentilesOf(Function<? super DATA, T> mapper, Comparator<T> comparator) {
//        FuncList<Tuple2<Integer, DATA>> list 
//                = mapWithIndex(Tuple2::of)
//                .sortedBy(tuple -> mapper.apply(tuple._2()), comparator)
//                .toImmutableList();
//        return Helper.toPercentilesOf(size() - 1, list);
//    }
    
}
