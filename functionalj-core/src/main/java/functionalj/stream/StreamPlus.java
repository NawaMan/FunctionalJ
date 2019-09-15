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

import static functionalj.function.Func.themAll;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
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
import functionalj.functions.StrFuncs;
import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.tuple.Tuple2;
import lombok.val;

// TODO - Intersect

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface StreamPlus<DATA> 
        extends 
            Iterable<DATA>, 
            Stream<DATA>, 
            StreamPlusWithCalculate<DATA>,
            StreamPlusWithMapToMap<DATA>,
            StreamPlusWithMapFirst<DATA>,
            StreamPlusWithMapTuple<DATA>,
            StreamPlusWithMapThen<DATA>,
            StreamPlusWithSplit<DATA>,
            StreamPlusWithFillNull<DATA>,
            StreamPlusWithSegment<DATA>,
            StreamPlusWithZip<DATA>,
            StreamPlusAddtionalOperators<DATA>,
            StreamPlusAdditionalTerminalOperators<DATA>
        {
    
    public static <D> D noMoreElement() throws NoMoreResultException {
        ThrowFuncs.doThrowFrom(()->new NoMoreResultException());
        return (D)null;
    }
    
    public static <D> StreamPlus<D> empty() {
        return StreamPlus
                .from(Stream.empty());
    }
    
    @SafeVarargs
    public static <D> StreamPlus<D> of(D ... data) {
        return ArrayBackedStream
                .from(data);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D> StreamPlus<D> from(Stream<D> stream) {
        if (stream == null)
            return StreamPlus.empty();
        
        return (stream instanceof StreamPlus)
                ? (StreamPlus)stream
                : (StreamPlus)(()->stream);
    }
    
    public static <D> StreamPlus<D> from(Iterator<D> iterator) {
        return IteratorPlus.of(iterator).stream();
    }
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
    
    @SafeVarargs
    public static <D> StreamPlus<D> cycle(D ... data) {
        val size = data.length;
        return StreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .mapToObj(i -> data[i % size]));
    }
    public static <D> StreamPlus<D> cycle(FuncList<D> data) {
        val size = data.size();
        return StreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .mapToObj(i -> data.get(i % size)));
    }
    
    public static StreamPlus<Integer> loop(int time) {
        return StreamPlus
                .infiniteInt()
                .limit(time);
    }
    public static StreamPlus<Integer> loop() {
        return StreamPlus
                .infiniteInt();
    }
    
    public static StreamPlus<Integer> infiniteInt() {
        return IntStreamPlus
                .from(
                    IntStream
                    .iterate(0, i -> i + 1))
                    .mapToObj(i -> i);
    }
    public static StreamPlus<Integer> range(int startInclusive, int endExclusive) {
        return IntStreamPlus
                .range(startInclusive, endExclusive)
                .mapToObj(i -> i);
    }
    
    // Because people know this.
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Stream<D> ... streams) {
        return StreamPlus
                .of     (streams)
                .flatMap(themAll());
    }
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Supplier<Stream<D>> ... streams) {
        return StreamPlus
                .of     (streams)
                .map    (Supplier::get)
                .flatMap(themAll());
    }
    public static <D> StreamPlus<D> generate(Supplier<D> supplier) {
        return StreamPlus
                .from(Stream.generate(supplier));
    }
    public static <D> StreamPlus<D> generateBy(Supplier<D> supplier) {
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
    
    public static <D> StreamPlus<D> iterate(D seed, UnaryOperator<D> f) {
        return StreamPlus
                .from(Stream.iterate(seed, f));
    }
    
    public static <D> StreamPlus<D> compound(D seed, UnaryOperator<D> f) {
        return iterate(seed, f);
    }
    
    public static <D> StreamPlus<D> iterate(D seed1, D seed2, BinaryOperator<D> f) {
        AtomicInteger      counter = new AtomicInteger(0);
        AtomicReference<D> d1      = new AtomicReference<D>(seed1);
        AtomicReference<D> d2      = new AtomicReference<D>(seed2);
        return StreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            D i2 = d2.get();
            D i1 = d1.getAndSet(i2);
            D i  = f.apply(i1, i2);
            d2.set(i);
            return i;
        });
    }
    
    public static <T1, T2> StreamPlus<Tuple2<T1, T2>> zipOf(
            StreamPlus<T1> stream1, 
            StreamPlus<T2> stream2) {
        return stream1
                .zipWith(stream2, ZipWithOption.RequireBoth);
    }
    
    public static <T1, T2, T> StreamPlus<T> zipOf(
            StreamPlus<T1> stream1, 
            StreamPlus<T2> stream2, 
            Func2<T1, T2, T> merger) {
        return stream1
                .zipWith(stream2, ZipWithOption.RequireBoth, merger);
    }
    
    //== Stream ==
    
    public Stream<DATA> stream();
    
    
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
    
    public default <TARGET> StreamPlus<TARGET> deriveWith(
            Function<Stream<DATA>, Stream<TARGET>> action) {
        return StreamPlus.from(
                action.apply(
                        this.stream()));
    }
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> concatWith(
            Stream<DATA> ... tails) {
        return concat(
                StreamPlus.of(this), 
                StreamPlus.of(tails)
               )
               .flatMap(themAll());
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
    
    @Override
    public default Optional<DATA> findFirst() {
        return terminate(stream -> {
            return stream
                    .findFirst();
        });
    }
    
    @Override
    public default Optional<DATA> findAny() {
        return terminate(stream -> {
            return stream
                    .findAny();
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
    
    //== toXXX ===
    
    @Override
    public default Object[] toArray() {
        return terminate(stream -> {
            return stream
                    .toArray();
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
    
    public default byte[] toByteArray(Func1<DATA, Byte> toByte) {
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
    
    public default IteratorPlus<DATA> iterator() {
        // TODO - Make sure close is handled properly.
        return IteratorPlus.from(stream());
    }
    
    public default Spliterator<DATA> spliterator() {
        // TODO - Make sure close is handled properly.
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
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
    public default StreamPlus<DATA> onClose(Runnable closeHandler) {
        return deriveWith(stream -> { 
            return stream
                    .onClose(closeHandler);
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
    
    public default <T> Pipeable<? extends StreamPlus<DATA>> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipeTo(Function<? super StreamPlus<DATA>, T> piper) {
        return piper.apply(this);
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
    public default <T> StreamPlus<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
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
            return stream
                    .distinct();
        });
    }
    
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
    
    //-- Spawn --
    
    /**
     * Map each element to a uncompleted action, run them and collect which ever finish first.
     * The result stream will not be the same order with the original one 
     *   -- as stated, the order will be the order of completion.
     * If the result StreamPlus is closed (which is done everytime a terminal operation is done),
     *   the unfinished actions will be canceled.
     */
    public default <T> StreamPlus<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapToAction) {
        val results = new ArrayList<DeferAction<T>>();
        val index   = new AtomicInteger(0);
        
        val actions 
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
        
        val stream 
            = StreamPlus
            .from(results.stream().map(action -> action.getResult()))
            ;
        stream
            .onClose(()->actions.forEach(action -> action.abort("Stream closed!")));
        
        return stream;
    }
    
    // -- accumulate --
    
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
        val iterator = this.iterator();
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
    public default StreamPlus<DATA> restate(BiFunction<? super DATA, StreamPlus<DATA>, StreamPlus<DATA>> restater) {
        val func = (UnaryOperator<Tuple2<DATA, StreamPlus<DATA>>>)((Tuple2<DATA, StreamPlus<DATA>> pair) -> {
            val stream   = pair._2();
            val iterator = stream.iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = iterator.next();
            val tail = restater.apply(head, iterator.stream());
            return Tuple2.of(head, tail);
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
