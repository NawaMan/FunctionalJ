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

import static functionalj.function.Func.f;
import static functionalj.function.Func.themAll;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
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
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit1;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.function.ToByteFunction;
import functionalj.functions.StrFuncs;
import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.tuple.Tuple2;
import lombok.val;

// TODO - Intersect

@FunctionalInterface
public interface StreamPlus<DATA> 
        extends 
            Stream<DATA>,
            StreamPlusWithMapCase<DATA>,
            StreamPlusWithMapThen<DATA>,
            StreamPlusWithMapTuple<DATA>,
            StreamPlusWithMapToMap<DATA>,
            StreamPlusWithSplit<DATA>,
            StreamPlusWithFillNull<DATA>,
            StreamPlusWithSegment<DATA>,
            StreamPlusWithCombine<DATA>,
            StreamPlusWithCalculate<DATA>,
            StreamPlusAddtionalOperators<DATA>,
            StreamPlusAdditionalTerminalOperators<DATA>
        {
    
    /**
     * Throw a no more element exception. This is used for generater.
     * This is done in the way that it can be overridden.
     **/
    public static <D> D noMoreElement() throws NoMoreResultException {
        ThrowFuncs.doThrowFrom(()->new NoMoreResultException());
        return (D)null;
    }
    
    /**
     * Returns an empty StreamPlus.
     */
    public static <D> StreamPlus<D> empty() {
        return StreamPlus
                .from(Stream.empty());
    }

    /**
     * Returns an empty StreamPlus.
     */
    public static <D> StreamPlus<D> emptyStream() {
        return StreamPlus
                .from(Stream.empty());
    }
    
    /** Create a StreamPlus from the given data. */
    @SafeVarargs
    public static <D> StreamPlus<D> of(D ... data) {
        return ArrayBackedStreamPlus
                .from(data);
    }
    
    /** Create a StreamPlus from the given data */
    @SafeVarargs
    public static <D> StreamPlus<D> steamOf(D ... data) {
        return of(data);
    }
    
    /** Create a StreamPlus from the given stream. */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D> StreamPlus<D> from(Stream<D> stream) {
        if (stream == null)
            return StreamPlus.empty();
        
        return (stream instanceof StreamPlus)
                ? (StreamPlus)stream
                : (StreamPlus)(()->stream);
    }
    
    /** Create a StreamPlus from the given iterator. */
    public static <D> StreamPlus<D> from(Iterator<D> iterator) {
        return IteratorPlus.from(iterator).stream();
    }
    
    /** Create a StreamPlus from the given enumeration. */
    public static <D> StreamPlus<D> from(Enumeration<D> enumeration) {
        Iterable<D> iterable = new Iterable<D>() {
            public Iterator<D> iterator() {
                return new Iterator<D>() {
                    private D next;
                    @Override
                    public boolean hasNext() {
                        try {
                            next = enumeration.nextElement();
                            return true;
                        } catch (NoSuchElementException e) {
                            return false;
                        }
                    }
                    @Override
                    public D next() {
                        return next;
                    }
                };
            }
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    /** Create a StreamPlus that is the repeat of the given array of data. */
    @SuppressWarnings("unchecked")
    public static <D> StreamPlus<D> repeat(D ... data) {
        return cycle(data);
    }
    
    /** Create a StreamPlus that is the repeat of the given list of data. */
    public static <D> StreamPlus<D> repeat(FuncList<D> data) {
        return cycle(data);
    }
    
    /** Create a StreamPlus that is the repeat of the given array of data. */
    @SafeVarargs
    public static <D> StreamPlus<D> cycle(D ... data) {
        val size = data.length;
        return StreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .mapToObj(i -> data[i % size]));
    }
    
    /** Create a StreamPlus that is the repeat of the given list of data. */
    public static <D> StreamPlus<D> cycle(FuncList<D> data) {
        val size = data.size();
        return StreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .mapToObj(i -> data.get(i % size)));
    }
    
    /** Create a StreamPlus that for a loop with the number of time given - the value is the index of the loop. */
    public static StreamPlus<Integer> loop(int time) {
        return StreamPlus
                .infiniteInt()
                .limit(time);
    }
    
    /** Create a StreamPlus that for an infinite loop - the value is the index of the loop. */
    public static StreamPlus<Integer> loop() {
        return StreamPlus
                .infiniteInt();
    }
    
    /** Create a StreamPlus that for an infinite loop - the value is the index of the loop. */
    public static StreamPlus<Integer> infiniteInt() {
        return IntStreamPlus
                .from(
                    IntStream
                    .iterate(0, i -> i + 1))
                    .mapToObj(i -> i);
    }
    
    /** Create a StreamPlus that for a loop from the start value inclusively to the end value exclusively. */
    public static StreamPlus<Integer> range(int startInclusive, int endExclusive) {
        return IntStreamPlus
                .range(startInclusive, endExclusive)
                .mapToObj(i -> i);
    }
    
    /** Concatenate all the given streams. */
    // Because people know this.
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Stream<D> ... streams) {
        return StreamPlus
                .of     (streams)
                .flatMap(themAll());
    }
    
    /** Concatenate all streams supplied by the given supplied. */
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Supplier<Stream<D>> ... streams) {
        return StreamPlus
                .of     (streams)
                .map    (Supplier::get)
                .flatMap(themAll());
    }
    
    /**
     * Create a StreamPlus from the supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <D> StreamPlus<D> generate(Supplier<D> supplier) {
        return StreamPlus
                .from(Stream.generate(supplier));
    }
    
    /**
     * Create a StreamPlus from the supplier. 
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     **/
    public static <D> StreamPlus<D> generateWith(Supplier<D> supplier) {
        Iterable<D> iterable = new Iterable<D>() {
            public Iterator<D> iterator() {
                return new Iterator<D>() {
                    private D next;
                    @Override
                    public boolean hasNext() {
                        try {
                            next = supplier.get();
                            return true;
                        } catch (NoMoreResultException e) {
                            return false;
                        }
                    }
                    @Override
                    public D next() {
                        return next;
                    }
                };
            }
        };
        return StreamPlus
                .from(StreamSupport.stream(iterable.spliterator(), false));
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
    public static <D> StreamPlus<D> iterate(D seed, UnaryOperator<D> f) {
        return StreamPlus
                .from(Stream.iterate(seed, f));
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
    public static <D> StreamPlus<D> compound(D seed, UnaryOperator<D> f) {
        return iterate(seed, f);
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
    public static <D> StreamPlus<D> iterate(D seed1, D seed2, BinaryOperator<D> f) {
        AtomicInteger      counter = new AtomicInteger(0);
        AtomicReference<D> d1      = new AtomicReference<D>(seed1);
        AtomicReference<D> d2      = new AtomicReference<D>(seed2);
        return StreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2) // Because, 1 is the second time of the first check.
                return seed2;
            
            D i2 = d2.get();
            D i1 = d1.getAndSet(i2);
            D i  = f.apply(i1, i2);
            d2.set(i);
            return i;
        });
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
    public static <D> StreamPlus<D> compound(D seed1, D seed2, BinaryOperator<D> f) {
        return iterate(seed1, seed2, f);
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
    public static <T1, T2> StreamPlus<Tuple2<T1, T2>> zipOf(
            Stream<T1> stream1, 
            Stream<T2> stream2) {
        return StreamPlus.from(stream1)
                .zipWith(stream2, ZipWithOption.RequireBoth);
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
    public static <T1, T2, T> StreamPlus<T> zipOf(
            Stream<T1> stream1, 
            Stream<T2> stream2, 
            Func2<T1, T2, T> merger) {
        return StreamPlus.from(stream1)
                .zipWith(stream2, ZipWithOption.RequireBoth, merger);
    }
    
    public static <TARGET> StreamPlus<TARGET> zipOf(
            IntStream stream1, 
            IntStream stream2, 
            IntIntBiFunction<TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipToObjWith(stream2, merger);
    }
    
    public static <TARGET> StreamPlus<TARGET> zipOf(
            IntStream                stream1, 
            IntStream                stream2, 
            int                      defaultValue,
            IntIntBiFunction<TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue, merger);
    }
    
    public static <TARGET> StreamPlus<TARGET> zipOf(
            IntStream                stream1, 
            int                      defaultValue1, 
            IntStream                stream2,
            int                      defaultValue2,
            IntIntBiFunction<TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            IntStream                         stream1, 
            Stream<ANOTHER>                   stream2, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            IntStream                         stream1, 
            Stream<ANOTHER>                   stream2, 
            ZipWithOption                     option, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return IntStreamPlus.from(stream1)
                .zipWith(stream2, option, merger);
    }
    
    public static <TARGET> StreamPlus<TARGET> zipOf(
            LongStream stream1, 
            LongStream stream2, 
            LongLongBiFunction<TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipToObjWith(stream2, merger);
    }
    
    public static <TARGET> StreamPlus<TARGET> zipOf(
            LongStream                 stream1, 
            LongStream                 stream2, 
            long                       defaultValue,
            LongLongBiFunction<TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue, merger);
    }
    
    public static <TARGET> StreamPlus<TARGET> zipOf(
            LongStream                stream1, 
            long                      defaultValue1, 
            LongStream                stream2,
            long                      defaultValue2,
            LongLongBiFunction<TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipToObjWith(stream2, defaultValue1, defaultValue2, merger);
    }
    
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            LongStream                         stream1, 
            Stream<ANOTHER>                    stream2, 
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, merger);
    }
    
    public static <ANOTHER, TARGET> StreamPlus<TARGET> zipOf(
            LongStream                         stream1, 
            Stream<ANOTHER>                    stream2, 
            ZipWithOption                      option, 
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return LongStreamPlus.from(stream1)
                .zipWith(stream2, option, merger);
    }
    
    //== Stream ==
    
    public Stream<DATA> stream();
    
    //== Helper functions ==
    
    public default <TARGET> TARGET terminate(
            Func1<Stream<DATA>, TARGET> action) {
        val stream = stream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(
            FuncUnit1<Stream<DATA>> action) {
        val stream = stream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
    public default <TARGET> StreamPlus<TARGET> sequential(Func1<StreamPlus<DATA>, StreamPlus<TARGET>> action) {
        return deriveWith(stream -> {
            val isParallel = stream.isParallel();
            if (!isParallel) {
                return action.apply(StreamPlus.from(stream));
            }
            
            val resultStream = action.apply(StreamPlus.from(stream.sequential()));
            if (resultStream.isParallel())
                return resultStream;
            
            return resultStream.parallel();
        });
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveWith(
            Function<Stream<DATA>, Stream<TARGET>> action) {
        return StreamPlus.from(
                action.apply(
                        this.stream()));
    }
    
    //== Stream sepecific ==
    
    @Override
    public default StreamPlus<DATA> sequential() {
        return deriveWith(stream -> { 
            return stream
                    .sequential();
        });
    }
    
    @Override
    public default StreamPlus<DATA> parallel() {
        return deriveWith(stream -> { 
            return stream
                    .parallel();
        });
    } 
    
    @Override
    public default StreamPlus<DATA> unordered() {
        return deriveWith(stream -> { 
            return stream
                    .unordered();
        });
    }
    
    @Override
    public default boolean isParallel() {
        return stream()
                .isParallel();
    }
    
    @Override
    public default void close() {
        stream()
        .close();
    }
    
    @Override
    public default StreamPlus<DATA> onClose(Runnable closeHandler) {
        return deriveWith(stream -> { 
            return stream
                    .onClose(closeHandler);
        });
    }
    
    //== Functionalities ==
    
    @Override
    public default IntStreamPlus mapToInt(
            ToIntFunction<? super DATA> mapper) {
        val intStreamPlus = IntStreamPlus.from(stream().mapToInt(mapper));
        intStreamPlus.onClose(()->{
            close();
        });
        return intStreamPlus;
    }
    
    @Override
    public default LongStreamPlus mapToLong(
            ToLongFunction<? super DATA> mapper) {
        return LongStreamPlus
                .from(
                    stream()
                    .mapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(
            ToDoubleFunction<? super DATA> mapper) {
        return DoubleStreamPlus
                .from(
                    stream()
                    .mapToDouble(mapper));
    }
    
    @Override
    public default IntStreamPlus flatMapToInt(
            Function<? super DATA, ? extends IntStream> mapper) {
        return IntStreamPlus
                .from(
                    stream()
                    .flatMapToInt(mapper));
    }
    
    @Override
    public default LongStreamPlus flatMapToLong(
            Function<? super DATA, ? extends LongStream> mapper) {
        return LongStreamPlus
                .from(
                    stream()
                    .flatMapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus flatMapToDouble(
            Function<? super DATA, ? extends DoubleStream> mapper) {
        return DoubleStreamPlus
                .from(
                    stream()
                    .flatMapToDouble(mapper));
    }
    
    @Override
    public default <TARGET> StreamPlus<TARGET> map(
            Function<? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return stream
                    .map(mapper);
        });
    }
    
    @Override
    public default <TARGET> StreamPlus<TARGET> flatMap(
            Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return deriveWith(stream -> {
            return stream
                    .flatMap(mapper);
        });
    }
    
    @Override
    public default StreamPlus<DATA> filter(
            Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(predicate);
        });
    }
    
    @Override
    public default <T> StreamPlus<DATA> filter(
            Function<? super DATA, T> mapper, 
            Predicate<? super T>      theCondition) {
        return filter(value -> {
            val target = mapper.apply(value);
            val isPass = theCondition.test(target);
            return isPass;
        });
    }
    
    @Override
    public default StreamPlus<DATA> peek(
            Consumer<? super DATA> action) {
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
            return stream
                    .limit(maxSize);
        });
    }
    
    @Override
    public default StreamPlus<DATA> skip(long n) {
        return deriveWith(stream -> {
            return stream
                    .skip(n);
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
    
    public default StreamPlus<DATA> skipWhile(Predicate<? super DATA> condition) {
        return sequential(stream -> {
            val isStillTrue = new AtomicBoolean(true);
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
        return sequential(stream -> {
            val isStillTrue = new AtomicBoolean(true);
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
        return sequential(stream -> {
            val splitr = stream.spliterator();
            return StreamPlus.from(
                    StreamSupport.stream(new Spliterators.AbstractSpliterator<DATA>(splitr.estimateSize(), 0) {
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
                    }, false)
                );
        });
    }
    
    public default StreamPlus<DATA> takeUntil(Predicate<? super DATA> condition) {
        return sequential(stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.stream(new Spliterators.AbstractSpliterator<DATA>(splitr.estimateSize(), 0) {
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
            return StreamPlus.from(resultStream);
        });
    }
    
    @Override
    public default StreamPlus<DATA> distinct() {
        return deriveWith(stream -> {
            return stream
                    .distinct();
        });
    }
    
    //-- Sorted --
    
    @Override
    public default StreamPlus<DATA> sorted() {
        return deriveWith(stream -> {
            return stream
                    .sorted();
        });
    }
    
    @Override
    public default StreamPlus<DATA> sorted(
            Comparator<? super DATA> comparator) {
        return deriveWith(stream -> {
            return (comparator == null)
                    ? stream.sorted()
                    : stream.sorted(comparator);
        });
    }
    
    public default <T extends Comparable<? super T>> StreamPlus<DATA> sortedBy(
            Function<? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> StreamPlus<DATA> sortedBy(
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
    
    @Override
    public default void forEach(
            Consumer<? super DATA> action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            stream
            .forEach(action);
        });
    }
    
    @Override
    public default void forEachOrdered(
            Consumer<? super DATA> action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            stream
            .forEachOrdered(action);
        });
    }
    
    @Override
    public default DATA reduce(
            DATA identity, 
            BinaryOperator<DATA> accumulator) {
        return terminate(stream -> {
            return stream
                    .reduce(identity, accumulator);
        });
    }
    
    @Override
    public default Optional<DATA> reduce(
            BinaryOperator<DATA> accumulator) {
        return terminate(stream -> {
            return stream
                    .reduce(accumulator);
        });
    }
    
    @Override
    public default <U> U reduce(
            U                              identity,
            BiFunction<U, ? super DATA, U> accumulator,
            BinaryOperator<U>              combiner) {
        return terminate(stream -> {
            return stream
                    .reduce(identity, accumulator, combiner);
        });
    }
    
    @Override
    public default <R> R collect(
            Supplier<R>                 supplier,
            BiConsumer<R, ? super DATA> accumulator,
            BiConsumer<R, R>            combiner) {
        return terminate(stream -> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Override
    public default <R, A> R collect(
            Collector<? super DATA, A, R> collector) {
        return terminate(stream -> {
            return stream
                    .collect(collector);
        });
    }
    
    @Override
    public default Optional<DATA> min(
            Comparator<? super DATA> comparator) {
        return terminate(stream -> {
            return stream
                    .min(comparator);
        });
    }
    
    @Override
    public default Optional<DATA> max(
            Comparator<? super DATA> comparator) {
        return terminate(stream -> {
            return stream
                    .max(comparator);
        });
    }
    
    @Override
    public default <D extends Comparable<D>> Optional<DATA> minBy(Func1<DATA, D> mapper) {
        return min((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
    }
    
    @Override
    public default <D extends Comparable<D>> Optional<DATA> maxBy(Func1<DATA, D> mapper) {
        return max((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
    }
    
    @Override
    public default long count() {
        return terminate(stream -> {
            return stream
                    .count();
        });
    }
    
    public default int size() {
        return terminate(stream -> {
            return (int)stream
                    .count();
        });
    }
    
    @Override
    public default boolean anyMatch(
            Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Override
    public default boolean allMatch(
            Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Override
    public default boolean noneMatch(
            Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    public default Optional<DATA> findFirst() {
        return stream().findFirst();
    }
    
    public default Optional<DATA> findAny() {
        return stream().findAny();
    }
    
    //== toXXX ===
    
    @Override
    public default Object[] toArray() {
        return terminate(stream -> {
            return stream
                    .toArray();
        });
    }
    
    public default <T> T[] toArray(T[] a) {
        return terminate(stream -> {
            T[] array 
                    = toJavaList()
                    .toArray(a);
            return array;
        });
    }
    
    @Override
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return terminate(stream -> {
            return stream
                    .toArray(generator);
        });
    }
    
    public default List<DATA> toJavaList() {
        return terminate(stream -> {
            return stream
                    .collect(Collectors.toList());
        });
    }
    
    public default byte[] toByteArray(ToByteFunction<DATA> toByte) {
        return terminate(stream -> {
            val byteArray = new ByteArrayOutputStream();
            stream.forEach(d -> byteArray.write(toByte.apply(d)));
            return byteArray
                    .toByteArray();
        });
    }
    
    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
        return mapToInt(toInt)
                .toArray();
    }
    
    public default long[] toLongArray(ToLongFunction<DATA> toLong) {
        return mapToLong(toLong)
                .toArray();
    }
    
    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
        return mapToDouble(toDouble)
                .toArray();
    }
    
    public default FuncList<DATA> toList() {
        return toImmutableList();
    }
    
    public default FuncList<DATA> toFuncList() {
        return toImmutableList();
    }
    
    public default String toListString() {
        return "[" + map(String::valueOf).collect(Collectors.joining(", ")) + "]";
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return terminate(stream -> {
            return ImmutableList.from(this);
        });
    }
    
    public default List<DATA> toMutableList() {
        return toArrayList();
    }
    
    public default ArrayList<DATA> toArrayList() {
        return new ArrayList<DATA>(toJavaList());
    }
    
    public default Set<DATA> toSet() {
        return new HashSet<DATA>(this.collect(Collectors.toSet()));
    }
    
    //-- Iterator --
    @Override
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    @Override
    public default Spliterator<DATA> spliterator() {
        return terminate(s -> {
            val iterator = iterator();
            return Spliterators.spliteratorUnknownSize(iterator, 0);
        });
    }
    
    /**
     * Use iterator of this stream without terminating the stream.
     */
    public default <T> StreamPlus<T> useIterator(Func1<IteratorPlus<DATA>, StreamPlus<T>> action) {
        return sequential(stream -> {
            StreamPlus<T> result = null;
            try {
                val iterator = StreamPlus.from(stream).iterator();
                result = action.apply(iterator);
                return result;
            } finally {
                if (result == null) {
                    f(()->close())
                    .runCarelessly();
                } else {
                    result
                    .onClose(()->{
                        f(()->close())
                        .runCarelessly();
                    });
                }
            }
        });
    }
    
    //== Plus ==
    
    public default String joinToString() {
        return terminate(stream -> {
            return stream
                    .map(StrFuncs::toStr)
                    .collect(Collectors.joining());
        });
    }
    public default String joinToString(String delimiter) {
        return terminate(stream -> {
            return stream
                    .map(StrFuncs::toStr)
                    .collect(Collectors.joining(delimiter));
        });
    }
    
    //== Pipe ==
    
    public default <T> Pipeable<? extends StreamPlus<DATA>> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipeTo(Function<? super StreamPlus<DATA>, T> piper) {
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
    public default <T> StreamPlus<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapToAction) {
        return sequential(stream -> {
            val results = new ArrayList<DeferAction<T>>();
            val index   = new AtomicInteger(0);
            
            List<? extends UncompletedAction<T>> actions 
                = stream()
                .map (mapToAction)
                .peek(action -> results.add(DeferAction.<T>createNew()))
                .peek(action -> action
                    .getPromise()
                    .onComplete(result -> {
                        val thisIndex  = index.getAndIncrement();
                        val thisAction = results.get(thisIndex);
                        if (result.isValue())
                             thisAction.complete(result.value());
                        else thisAction.fail    (result.exception());
                    })
                )
                .peek(action -> action.start())
                .collect(Collectors.toList())
                ;
            
            val resultStream 
                = StreamPlus
                .from(results.stream().map(action -> action.getResult()))
                ;
            resultStream
                .onClose(()->actions.forEach(action -> action.abort("Stream closed!")));
            
            return resultStream;
        });
    }
    
    //== accumulate + restate ==
    
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
    public default StreamPlus<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
        return useIterator(iterator -> {
            if (!iterator.hasNext())
                return StreamPlus.empty();
            
            val prev = new AtomicReference<DATA>(iterator.next());
            return StreamPlus
                    .concat(
                        StreamPlus.of(prev.get()),
                        iterator.stream().map(n -> {
                            val next = accumulator.apply(n, prev.get());
                            prev.set(next);
                            return next;
                        })
                    );
        });
    }
    
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
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> restate(BiFunction<? super DATA, StreamPlus<DATA>, StreamPlus<DATA>> restater) {
        val func = (UnaryOperator<Tuple2<DATA, StreamPlus<DATA>>>)((Tuple2<DATA, StreamPlus<DATA>> pair) -> {
            val stream = pair._2();
            if (stream == null)
                return null;
            
            Object[] head     = new Object[] { null };
            val      iterator = stream.iterator();
            if (!iterator.hasNext())
                return null;
            
            head[0]  = iterator.next();
            val tail = restater.apply((DATA)head[0], iterator.stream());
            if (tail == null)
                return null;
            
            return Tuple2.of((DATA)head[0], tail);
        });
        val seed = Tuple2.of((DATA)null, this);
        val endStream 
            = iterate(seed, func)
            .takeUntil(t -> t == null)
            .skip(1)
            .map(t -> t._1());
        return endStream;
    }
    
}
