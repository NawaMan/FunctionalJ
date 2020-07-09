// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.IntIntBiFunction;
import functionalj.function.LongLongBiFunction;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.longstream.LongStreamable;
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

class S implements Streamable<String> {

    @Override
    public StreamPlus<String> stream() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

@FunctionalInterface
public interface Streamable<DATA> 
        extends
            AsStreamable<DATA>,
            StreamableWithMapFirst<DATA>,
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
    
    /** Throw a no more element exception. This is used for generator. */
    public static <D> D noMoreElement() throws NoMoreResultException {
        return StreamPlus.noMoreElement();
    }
    
    //== Constructor ==
    
    /** Returns an empty Streamable. */
    public static <D> Streamable<D> empty() {
        return ()->StreamPlus.empty();
    }
    
    /** Returns an empty Streamable. */
    public static <D> Streamable<D> emptyStreamable() {
        return ()->StreamPlus.empty();
    }
    
    /** Create a Streamable from the given data. */
    @SafeVarargs
    public static <D> Streamable<D> of(D ... data) {
        return ()->StreamPlus.from(Stream.of(data));
    }
    
    /** Create a Streamable from the given data. */
    @SafeVarargs
    public static <D> Streamable<D> steamableOf(D ... data) {
        return ()->StreamPlus.from(Stream.of(data));
    }
    
    /** Create a Streamable from the given collection. */
    public static <D> Streamable<D> from(Collection<D> collection) {
        return ()->StreamPlus.from(collection.stream());
    }
    
    /** Create a Streamable from the given supplier of stream. */
    public static <D> Streamable<D> from(Func0<Stream<D>> supplier) {
        return ()->StreamPlus.from(supplier.get());
    }
    
    /** Create a Streamable from the given IntStreamable. */
    public static <D> Streamable<D> fromInts(IntStreamable source, Function<IntStreamable, Stream<D>> action) {
        return ()->StreamPlus.from(action.apply(source));
    }

    
    /** Create a Streamable that is the repeat of the given array of data. */
    @SuppressWarnings("unchecked")
    public static <D> Streamable<D> repeat(D ... data) {
        return cycle(data);
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static <D> Streamable<D> repeat(FuncList<D> data) {
        return cycle(data);
    }
    
    /** Create a Streamable that is the repeat of the given array of data. */
    @SafeVarargs
    public static <D> Streamable<D> cycle(D ... data) {
        val size = data.length;
        return () ->
                StreamPlus.from(
                        IntStream
                        .iterate(0, i -> i + 1)
                        .mapToObj(i -> data[i % size]));
    }
    
    /** Create a Streamable that is the repeat of the given list of data. */
    public static <D> Streamable<D> cycle(FuncList<D> data) {
        val size = data.size();
        return () ->
                StreamPlus.from(
                        IntStream
                        .iterate(0, i -> i + 1)
                        .mapToObj(i -> data.get(i % size)));
    }
    
    /** Create a Streamable that for an infinite loop - the value is boolean true */
    public static Streamable<Boolean> loop() {
        return ()-> {
            return StreamPlus.from(Stream.generate(() -> Boolean.TRUE));
        };
    }
    
    /** Create a Streamable that for a loop with the number of time given - the value is the index of the loop. */
    public static Streamable<Boolean> loop(int time) {
        return Streamable.loop().limit(time);
    }
    
    /** Create a Streamable that for an infinite loop - the value is the index of the loop. */
    public static Streamable<Integer> infiniteInt() {
        return IntStreamable
                .wholeNumbers()
                .boxed();
    }
    
    /** Create a Streamable that for a loop from the start value inclusively to the end value exclusively. */
    public static Streamable<Integer> range(int startInclusive, int endExclusive) {
        return IntStreamable
                .range(startInclusive, endExclusive)
                .boxed();
    }
    
    /** Concatenate all the given streams. */
    @SafeVarargs
    public static <D> Streamable<D> concat(Streamable<D> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    /** Concatenate all the given streams. */
    // To avoid name conflict with String.concat
    @SafeVarargs
    public static <D> Streamable<D> combine(Streamable<D> ... streams) {
        return ()->StreamPlus.of(streams).flatMap(s -> s.stream());
    }
    
    /**
     * Create a StreamPlus from the supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <D> Streamable<D> generate(Func0<Func0<D>> supplier) {
        return ()->StreamPlus.generate(supplier.get());
    }
    
    /**
     * Create a StreamPlus from the supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <D> Streamable<D> generateWith(Func0<Func0<D>> supplier) {
        return ()->StreamPlus.generate(supplier.get());
    }
    
    /**
     * Create a StreamPlus by apply the function to the seed over and over.
     * 
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 <- seed, 
     *      2 <- (1*2), 
     *      4 <- ((1*2)*2), 
     *      8 <- (((1*2)*2)*2), 
     *      16 <- ((((1*2)*2)*2)*2)
     *      ...
     * 
     * Note: this is an alias of compound()
     **/
    public static <D> Streamable<D> iterate(D seed, UnaryOperator<D> f) {
        return ()->StreamPlus.iterate(seed, f);
    }
    
    /**
     * Create a StreamPlus by apply the function to the seed over and over.
     * 
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 <- seed, 
     *      2 <- (1*2), 
     *      4 <- ((1*2)*2), 
     *      8 <- (((1*2)*2)*2), 
     *      16 <- ((((1*2)*2)*2)*2)
     *      ...
     *      
     * Note: this is an alias of iterate()
     **/
    public static <D> Streamable<D> compound(D seed, UnaryOperator<D> f) {
        return ()->StreamPlus.compound(seed, f);
    }
    
    /**
     * Create a StreamPlus by apply the function to the seeds over and over.
     * 
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 <- seed1, 
     *      1 <- seed2, 
     *      2 <- (1+1), 
     *      3 <- (1+2), 
     *      5 <- (2+3), 
     *      8 <- (5+8)
     *      ...
     * 
     * Note: this is an alias of compound()
     **/
    public static <D> Streamable<D> iterate(D seed1, D seed2, BinaryOperator<D> f) {
        return ()->StreamPlus.iterate(seed1, seed2, f);
    }
    
    /**
     * Create a StreamPlus by apply the function to the seeds over and over.
     * 
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 <- seed1, 
     *      1 <- seed2, 
     *      2 <- (1+1), 
     *      3 <- (1+2), 
     *      5 <- (2+3), 
     *      8 <- (5+8)
     *      ...
     * 
     * Note: this is an alias of iterate()
     **/
    public static <D> Streamable<D> compound(D seed1, D seed2, BinaryOperator<D> f) {
        return ()->StreamPlus.compound(seed1, seed2, f);
    }
    
    /**
     * Create a StreamPlus by combining elements together into a StreamPlus of tuples.
     * Only elements with pair will be combined. If this is not desirable, use stream1.zip(stream2).
     * 
     * For example:
     *     stream1 = [A, B, C, D, E]
     *     stream2 = [1, 2, 3, 4]
     *     
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     **/
    public static <T1, T2> Streamable<Tuple2<T1, T2>> zipOf(
            Streamable<T1> streamable1, 
            Streamable<T2> streamable2) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.stream(),
                    streamable2.stream());
        };
    }
    
    /**
     * Create a StreamPlus by combining elements together using the merger function and collected into the result stream.
     * Only elements with pair will be combined. If this is not desirable, use stream1.zip(stream2).
     * 
     * For example:
     *     stream1 = [A, B, C, D, E]
     *     stream2 = [1, 2, 3, 4]
     *     merger  = a + "+" + b 
     *     
     * The result stream = ["A+1", "B+2", "C+3", "D+4"].
     **/
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
                    streamable1.intStream(),
                    streamable2.intStream(),
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
                    streamable1.intStream(),
                    streamable2.intStream(),
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
                    streamable1.intStream(), defaultValue1,
                    streamable2.intStream(), defaultValue2,
                    merger);
        };
    }
    
    public static <T1, T2, T> Streamable<T> zipOf(
            LongStreamable       streamable1, 
            LongStreamable       streamable2,
            LongLongBiFunction<T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream(),
                    merger);
        };
    }
    
    public static <T1, T2, T> Streamable<T> zipOf(
            LongStreamable       streamable1, 
            LongStreamable       streamable2,
            int                 defaultValue,
            LongLongBiFunction<T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream(),
                    defaultValue,
                    merger);
        };
    }
    
    public static <T1, T2, T> Streamable<T> zipOf(
            LongStreamable       streamable1, 
            long                 defaultValue1,
            LongStreamable       streamable2,
            long                 defaultValue2,
            LongLongBiFunction<T> merger) {
        return ()->{
            return StreamPlus.zipOf(
                    streamable1.longStream(), defaultValue1,
                    streamable2.longStream(), defaultValue2,
                    merger);
        };
    }
    
    //== Core ==
    
    public default Streamable<DATA> streamable() {
        return this;
    }
    
    public StreamPlus<DATA> stream();
    
    
    //== Helper functions ==
    // TODO - Move this out to a helper class
    
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
    
    public default <TARGET> Streamable<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
        return Streamable.with(this, action);
    }
    
    public default <TARGET> Streamable<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action) {
        return Streamable.from(this, action);
    }
    
    public default <TARGET> Streamable<TARGET> derive(Func1<Streamable<DATA>, Streamable<TARGET>> action) {
        return action.apply(this);
    }
    
    public default IntStreamable deriveToInt(Func1<Streamable<DATA>, IntStreamable> action) {
        return action.apply(this);
    }
    
    public default <TARGET> Streamable<TARGET> deriveToObj(Func1<Streamable<DATA>, Streamable<TARGET>> action) {
        return action.apply(this);
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
    
    //-- Iterator --
    
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    //== Functionalities ==
    
    //-- Map --
    
    public default <TARGET> Streamable<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return stream.map(mapper);
        });
    }
    
    public default IntStreamable mapToInt(ToIntFunction<? super DATA> mapper) {
        return () -> IntStreamPlus.from(stream().mapToInt(mapper));
    }
    
    public default LongStreamable mapToLong(ToLongFunction<? super DATA> mapper) {
        return () -> stream().mapToLong(mapper);
    }
//    
//    public default DoubleStreamable mapToDouble(ToDoubleFunction<? super DATA> mapper) {
//        return () -> stream().mapToDouble(mapper);
//    }
    
    public default <T> Streamable<T> mapToObj(Function<? super DATA, ? extends T> mapper) {
        return map(mapper);
    }
    
    //-- FlatMap --
    
    public default <TARGET> Streamable<TARGET> flatMap(Function<? super DATA, ? extends Streamable<? extends TARGET>> mapper) {
        return deriveWith(stream -> {
            return stream.flatMap(e -> ((Streamable<? extends TARGET>)mapper.apply(e)).stream());
        });
    }
    
    public default IntStreamable flatMapToInt(Function<? super DATA, ? extends IntStream> mapper) {
        return ()->IntStreamPlus.from(stream().flatMapToInt(mapper));
    }
    
    public default LongStreamable flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
        return () -> stream().flatMapToLong(mapper);
    }
//    
//    public default DoubleStreamable flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
//        return () -> stream().flatMapToDouble(mapper);
//    }
    
    public default <T> Streamable<T> flatMapToObj(Function<? super DATA, ? extends Streamable<? extends T>> mapper) {
        return flatMap(mapper);
    }
    
    //-- Filter --
    
    public default Streamable<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(predicate);
        });
    }
    
    @Override
    public default <T> Streamable<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default Streamable<DATA> filterAsInt(
            ToIntFunction<? super DATA> mapper, 
            IntPredicate                predicate) {
        return filter(value -> {
            val target = mapper.applyAsInt(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default Streamable<DATA> filterAsLong(
            ToLongFunction<? super DATA> mapper, 
            LongPredicate                predicate) {
        return filter(value -> {
            val target = mapper.applyAsLong(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default Streamable<DATA> filterAsDouble(
            ToDoubleFunction<? super DATA> mapper, 
            DoublePredicate                predicate) {
        return filter(value -> {
            val target = mapper.applyAsDouble(value);
            val isPass = predicate.test(target);
            return isPass;
        });
    }
    
    public default <T> Streamable<DATA> filterAsObject(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      predicate) {
        return filter(mapper, predicate);
    }
    
    //-- Peek --
    
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
//    
//    public default long[] toLongArray(ToLongFunction<DATA> toLong) {
//        return mapToLong(toLong).toArray();
//    }
//    
//    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
//        return mapToDouble(toDouble).toArray();
//    }
    
    public default FuncList<DATA> toList() {
        return toImmutableList();
    }
    
    public default FuncList<DATA> toFuncList() {
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
    
    /**
     * Map each element to a uncompleted action, run them and collect which ever finish first.
     * The result stream will not be the same order with the original one 
     *   -- as stated, the order will be the order of completion.
     * If the result StreamPlus is closed (which is done everytime a terminal operation is done),
     *   the unfinished actions will be canceled.
     */
    public default <T> Streamable<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .spawn(mapper);
        });
    }
    
    //== accumulate ==
    
    /**
     * Accumulate the previous to the next element.
     * 
     * For example:
     *      inputs = [i1, i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *      and ~ is a accumulate function
     * 
     * From this we get
     *      acc0  = head of inputs => i1
     *      rest0 = tail of inputs => [i2, i3, i4, i5, i6, i7, i8, i9, i10]
     * 
     * The outputs are:
     *     output0 = acc0 with acc1 = acc0 ~ rest0 and rest1 = rest of rest0
     *     output1 = acc1 with acc2 = acc1 ~ rest1 and rest2 = rest of rest1
     *     output2 = acc2 with acc3 = acc2 ~ rest2 and rest3 = rest of rest2
     *     ...
     */
    public default Streamable<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from(stream)
                    .accumulate(accumulator);
        });
    }
    
    //== restate ==
    
    /**
     * Use each of the element to recreate the stream by applying each element to the rest of the stream and repeat.
     * 
     * For example:
     *      inputs = [i1, i2, i3, i4, i5, i6, i7, i8, i9, i10]
     *      and ~ is a restate function
     * 
     * From this we get
     *      head0 = head of inputs = i1
     *      rest0 = tail of inputs = [i2, i3, i4, i5, i6, i7, i8, i9, i10]
     * 
     * The outputs are:
     *     output0 = head0 with rest1 = head0 ~ rest0 and head1 = head of rest0
     *     output1 = head1 with rest2 = head1 ~ rest1 and head2 = head of rest2
     *     output2 = head2 with rest3 = head2 ~ rest2 and head3 = head of rest3
     *     ...
     **/
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
    
}
