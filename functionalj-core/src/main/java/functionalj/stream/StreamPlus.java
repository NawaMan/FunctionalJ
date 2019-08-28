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

import static functionalj.function.Func.f;
import static functionalj.function.Func.themAll;
import static functionalj.functions.ObjFuncs.notEqual;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static java.lang.Boolean.TRUE;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit1;
import functionalj.functions.StrFuncs;
import functionalj.functions.ThrowFuncs;
import functionalj.lens.core.WriteLens;
import functionalj.lens.lenses.AnyLens;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompleteAction;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

class StreamPlusMapAddOnHelper {
    
    @SafeVarargs
    public static final <D, T> StreamPlus<T> mapFirst(
            StreamPlus<D>              stream,
            Function<? super D, T> ... mappers) {
        return stream.map(f(d -> {
            Exception exception = null;
            boolean hasNull = false;
            for(val mapper : mappers) {
                try {
                    val res = mapper.apply(d);
                    if (res == null)
                         hasNull = true;
                    else return (T)res;
                } catch (Exception e) {
                    if (exception == null)
                        exception = e;
                }
            }
            if (hasNull)
                return (T)null;
            
            throw exception;
        }));
    }
}

// TODO - Intersect
// TODO - Sorted Spread

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface StreamPlus<DATA> 
        extends Iterable<DATA>, Stream<DATA> {
    
    public static <D> D noMoreElement() throws NoMoreResultException {
        ThrowFuncs.doThrowFrom(()->new NoMoreResultException());
        return (D)null;
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
    public static <D> StreamPlus<D> of(D ... data) {
        return ArrayBackedStream.from(data);
    }
    
    @SafeVarargs
    public static <D> StreamPlus<D> cycle(D ... data) {
        return StreamPlus.from(IntStream.iterate(0, i -> i + 1).mapToObj(i -> data[i % data.length]));
    }
    
    public static StreamPlus<Integer> loop(int time) {
        return StreamPlus.infiniteInt().limit(time);
    }
    public static StreamPlus<Integer> loop() {
        return StreamPlus.infiniteInt();
    }
    
    public static StreamPlus<Integer> infiniteInt() {
        return IntStreamPlus.from(IntStream.iterate(0, i -> i + 1)).mapToObj(i -> i);
    }
    public static StreamPlus<Integer> range(int startInclusive, int endExclusive) {
        return IntStreamPlus.range(startInclusive, endExclusive).mapToObj(i -> i);
    }
    
    public static <D> StreamPlus<D> empty() {
        return StreamPlus.from(Stream.empty());
    }
    
    // Because people know this.
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Stream<D> ... streams) {
        return StreamPlus.of(streams).flatMap(Func.themAll());
    }
    @SafeVarargs
    public static <D> StreamPlus<D> concat(Supplier<Stream<D>> ... streams) {
        return StreamPlus.of(streams).map(Supplier::get).flatMap(Func.themAll());
    }
    public static <D> StreamPlus<D> generate(Supplier<D> supplier) {
        return StreamPlus.from(Stream.generate(supplier));
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
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    public static <D> StreamPlus<D> iterate(D seed, UnaryOperator<D> f) {
        return StreamPlus.from(Stream.iterate(seed, f));
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
    
    //== Stream ==
    
    public Stream<DATA> stream();
    
    public default <TARGET> TARGET terminate(Func1<Stream<DATA>, TARGET> action) {
        val stream = stream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(FuncUnit1<Stream<DATA>> action) {
        val stream = stream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
        return StreamPlus.from(
                action.apply(
                        this.stream()));
    }
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> concatWith(Stream<DATA> ... tails) {
        return concat(
                StreamPlus.of(this), 
                StreamPlus.of(tails)
               )
               .flatMap(themAll());
    }
    
    //== Functionalities ==
    
    @Override
    public default IntStreamPlus mapToInt(ToIntFunction<? super DATA> mapper) {
        val intStreamPlus = IntStreamPlus.from(stream().mapToInt(mapper));
        intStreamPlus.onClose(()->{
            close();
        });
        return intStreamPlus;
    }
    
    @Override
    public default LongStreamPlus mapToLong(ToLongFunction<? super DATA> mapper) {
        return LongStreamPlus.from(stream().mapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return DoubleStreamPlus.from(stream().mapToDouble(mapper));
    }
    
    @Override
    public default IntStreamPlus flatMapToInt(Function<? super DATA, ? extends IntStream> mapper) {
        return IntStreamPlus.from(stream().flatMapToInt(mapper));
    }
    
    @Override
    public default LongStreamPlus flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
        return LongStreamPlus.from(stream().flatMapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
        return DoubleStreamPlus.from(stream().flatMapToDouble(mapper));
    }
    
    public default void forEach(Consumer<? super DATA> action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            stream
            .forEach(action);
        });
    }
    
    public default void forEachWithIndex(BiConsumer<? super Integer, ? super DATA> action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            val index = new AtomicInteger();
            stream
            .forEach(each -> action.accept(index.getAndIncrement(), each));
        });
    }
    
    public default void forEachOrdered(Consumer<? super DATA> action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            stream
            .forEachOrdered(action);
        });
    }
    
    public default DATA reduce(DATA identity, BinaryOperator<DATA> accumulator) {
        return terminate(stream -> {
            return stream.reduce(identity, accumulator);
        });
    }
    
    public default Optional<DATA> reduce(BinaryOperator<DATA> accumulator) {
        return terminate(stream -> {
            return stream.reduce(accumulator);
        });
    }
    
    public default <U> U reduce(
                    U                              identity,
                    BiFunction<U, ? super DATA, U> accumulator,
                    BinaryOperator<U>              combiner) {
        return terminate(stream -> {
            return stream.reduce(identity, accumulator, combiner);
        });
    }
    
    public default <R> R collect(
                    Supplier<R>                 supplier,
                    BiConsumer<R, ? super DATA> accumulator,
                    BiConsumer<R, R>            combiner) {
        return terminate(stream -> {
            return stream.collect(supplier, accumulator, combiner);
        });
    }
    
    public default <R, A> R collect(Collector<? super DATA, A, R> collector) {
        return terminate(stream -> {
            return stream.collect(collector);
        });
    }
    
    public default FuncMap<DATA, Integer> histogram() {
        return histogram(Func.itself());
    }
    
    public default <D> FuncMap<D, Integer> histogram(Func1<DATA, D> mapper) {
        return groupingBy(mapper)
                .mapValue(FuncList::size)
                .sortedByValue((a, b)->Integer.compare(b, a));
    }
    
    public default Optional<Map.Entry<DATA, Integer>> mostFrequence() {
        return histogram()
                .entries()
                .findFirst();
    }
    
    public default <T> StreamPlus<Result<T>> spawn(Func1<DATA, ? extends UncompleteAction<T>> mapToAction) {
        val results = new ArrayList<DeferAction<T>>();
        val index   = new AtomicInteger(0);
        
        val actions 
            = stream()
            .map (mapToAction)
            .peek(action -> results.add(DeferAction.<T>createNew()))
            .peek(action -> action.getPromise().onComplete(result -> {
                val thisIndex  = index.getAndIncrement();
                val thisAction = results.get(thisIndex);
                if (result.isValue())
                     thisAction.complete(result.value());
                else thisAction.fail    (result.exception());
            }))
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
    
    public default Optional<DATA> min(Comparator<? super DATA> comparator) {
        return terminate(stream -> {
            return stream.min(comparator);
        });
    }
    
    public default Optional<DATA> max(Comparator<? super DATA> comparator) {
        return terminate(stream -> {
            return stream.max(comparator);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default Tuple2<Optional<DATA>, Optional<DATA>> minMax(Comparator<? super DATA> comparator) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(Helper.dummy);
            val maxRef = new AtomicReference<Object>(Helper.dummy);
            stream
                .sorted(comparator)
                .forEach(each -> {
                    minRef.compareAndSet(Helper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            return Tuple2.of(
                    Helper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                    Helper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
        });
    }
    
    public default <D extends Comparable<D>> Optional<DATA> minBy(Func1<DATA, D> mapper) {
        return terminate(stream -> {
            return stream.min((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
        });
    }
    
    public default <D extends Comparable<D>> Optional<DATA> maxBy(Func1<DATA, D> mapper) {
        return terminate(stream -> {
            return stream.max((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
        });
    }
    
    public default <D> Optional<DATA> minBy(Func1<DATA, D> mapper, Comparator<? super D> comparator) {
        return terminate(stream -> {
            return stream.min((a,b)->comparator.compare(mapper.apply(a), mapper.apply(b)));
        });
    }
    
    public default <D> Optional<DATA> maxBy(Func1<DATA, D> mapper, Comparator<? super D> comparator) {
        return terminate(stream -> {
            return stream.max((a,b)->comparator.compare(mapper.apply(a), mapper.apply(b)));
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <D extends Comparable<D>> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(Func1<DATA, D> mapper) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(Helper.dummy);
            val maxRef = new AtomicReference<Object>(Helper.dummy);
            StreamPlus.from(stream)
                .sortedBy(mapper)
                .forEach(each -> {
                    minRef.compareAndSet(Helper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            return Tuple2.of(
                    Helper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                    Helper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <D> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(Helper.dummy);
            val maxRef = new AtomicReference<Object>(Helper.dummy);
            StreamPlus.from(stream)
                .sortedBy(mapper, (i1, i2)->comparator.compare(i1, i2))
                .forEach(each -> {
                    minRef.compareAndSet(Helper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            return Tuple2.of(
                    Helper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                    Helper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
        });
    }
    
    public default Optional<BigDecimal> sumToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        return map(toBigDecimal)
                .reduce(BigDecimal::add);
    }
    
    public default Optional<BigDecimal> minToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        return map(toBigDecimal)
                .reduce((a, b) -> a.compareTo(b) <= 0 ? a : b);
    }
    
    public default Optional<BigDecimal> maxToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        return map(toBigDecimal)
                .reduce((a, b) -> a.compareTo(b) <= 0 ? b : a);
    }
    
    public default Optional<BigDecimal> averageToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        return map(each -> Tuple.of(1, toBigDecimal.apply(each)))
               .reduce((a, b)->Tuple.of(a._1 + b._1, a._2.add(b._2)))
               .map(t -> t._2.divide(new BigDecimal(t._1)));
    }
    
    public default Optional<BigDecimal> sum(Function<? super DATA, BigDecimal> toBigDecimal) {
        return sumToBigDecimal(toBigDecimal);
    }
    
    public default Optional<BigDecimal> min(Function<? super DATA, BigDecimal> toBigDecimal) {
        return minToBigDecimal(toBigDecimal);
    }
    
    public default Optional<BigDecimal> max(Function<? super DATA, BigDecimal> toBigDecimal) {
        return maxToBigDecimal(toBigDecimal);
    }
    
    public default Optional<BigDecimal> average(Function<? super DATA, BigDecimal> toBigDecimal) {
        return averageToBigDecimal(toBigDecimal);
    }
    
    public default long count() {
        return terminate(stream -> {
            return stream.count();
        });
    }
    
    public default int size() {
        return terminate(stream -> {
            return (int)stream.count();
        });
    }
    
    public default boolean anyMatch(Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream.anyMatch(predicate);
        });
    }
    
    public default boolean allMatch(Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream.allMatch(predicate);
        });
    }
    
    public default boolean noneMatch(Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream.noneMatch(predicate);
        });
    }
    
    public default Optional<DATA> findFirst(Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream.filter(predicate).findFirst();
        });
    }
    
    public default Optional<DATA> findAny(Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream.filter(predicate).findAny();
        });
    }
    
    public default <T> Optional<DATA> findFirst(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(mapper, theCondition).findFirst();
    }
    
    public default <T>  Optional<DATA> findAny(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(mapper, theCondition).findAny();
    }
    
    public default Optional<DATA> findFirst() {
        return terminate(stream -> {
            return stream.findFirst();
        });
    }
    
    public default Optional<DATA> findAny() {
        return terminate(stream -> {
            return stream.findAny();
        });
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
        return terminate(stream -> {
            return stream.toArray();
        });
    }
    
    @Override
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return terminate(stream -> {
            return stream.toArray(generator);
        });
    }
    
    public default List<DATA> toJavaList() {
        return terminate(stream -> {
            return stream.collect(Collectors.toList());
        });
    }
    
    public default byte[] toByteArray(Func1<DATA, Byte> toByte) {
        return terminate(stream -> {
            val byteArray = new ByteArrayOutputStream();
            stream.forEach(d -> byteArray.write(toByte.apply(d)));
            return byteArray.toByteArray();
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
    
    public default String toListString() {
        return "[" + map(String::valueOf).collect(Collectors.joining(", ")) + "]";
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.from(this);
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
    
    public default IteratorPlus<DATA> iterator() {
        // TODO - Make sure close is handled properly.
        return IteratorPlus.from(stream());
    }
    
    public default Spliterator<DATA> spliterator() {
        // TODO - Make sure close is handled properly.
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> groupingBy(
            Function<? super DATA, ? extends KEY> classifier) {
        return terminate(stream -> {
            val theMap = new HashMap<KEY, FuncList<DATA>>();
            stream
                .collect(Collectors.groupingBy(classifier))
                .forEach((key,list)->theMap.put(key, ImmutableList.from(list)));
            return ImmutableMap.from(theMap);
        });
    }
    
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY>   classifier,
            Function<? super FuncList<DATA>, VALUE> aggregate) {
        return terminate(stream -> {
            val theMap = new HashMap<KEY, VALUE>();
            stream
                .collect(Collectors.groupingBy(classifier))
                .forEach((key,list) -> {
                    val valueList      = ImmutableList.from(list);
                    val aggregateValue = aggregate.apply(valueList);
                    theMap.put(key, aggregateValue);
                });
            return ImmutableMap.from(theMap);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(Function<? super DATA, ? extends KEY> keyMapper) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, data -> data));
            return (FuncMap<KEY, DATA>)ImmutableMap.from(theMap);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>  keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, valueMapper));
            return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>   keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper,
                BinaryOperator<VALUE>                   mergeFunction) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
            return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(
                Function<? super DATA, ? extends KEY> keyMapper,
                BinaryOperator<DATA>                  mergeFunction) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, value -> value, mergeFunction));
            return (FuncMap<KEY, DATA>) ImmutableMap.from(theMap);
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
    
    //-- Split --
    
    public default Tuple2<FuncList<DATA>, FuncList<DATA>> split(
            Predicate<? super DATA> predicate) {
        val temp = this.mapTuple(
                it -> predicate.test(it) ? 0 : 1,
                it -> it
        ).toList();
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
    
    //-- SplitToMap --
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate,
            KEY key2) {
        val temp = this.mapTuple(
                it -> predicate.test(it) ? 0 : 1,
                it -> it
        ).toList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
        ).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
        ).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3, 
                key4, list4);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    :                       4,
                it -> it
        ).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).map(it -> it._2()) : FuncList.<DATA>empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3, 
                key4, list4, 
                key5, list5);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5, Predicate<? super DATA> predicate5,
            KEY key6) {
        val temp = this.mapTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    : predicate5.test(it) ? 4
                    :                       5,
                it -> it
        ).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).map(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).map(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).map(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).map(it -> it._2()) : FuncList.<DATA>empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).map(it -> it._2()) : FuncList.<DATA>empty();
        val list6 = (key6 != null) ? temp.filter(it -> it._1() == 5).map(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, list1, 
                key2, list2, 
                key3, list3, 
                key4, list4, 
                key5, list5,
                key6, list6);
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
    
    public default <T> Pipeable<? extends StreamPlus<DATA>> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super StreamPlus<DATA>, T> piper) {
        return piper.apply(this);
    }
    
    public default <TARGET> StreamPlus<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return stream.map(mapper);
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
    
    // -- fillNull --
    
    public default <VALUE> StreamPlus<DATA> fillNull(AnyLens<DATA, VALUE> lens, VALUE replacement) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacement);
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            VALUE                    replacement) {
        return deriveWith(stream -> {
            return (Stream<DATA>)stream.map(orgElmt -> {
                val value   = get.apply(orgElmt);
                if (value == null) {
                    val newElmt = set.apply(orgElmt, replacement);
                    return (DATA)newElmt;
                }
                return orgElmt;
            });
        });
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Supplier<VALUE>      replacementSupplier) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacementSupplier);
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Supplier<VALUE>          replacementSupplier) {
        return deriveWith(stream -> {
            return (Stream<DATA>)stream.map(orgElmt -> {
                val value   = get.apply(orgElmt);
                if (value == null) {
                    val replacement = replacementSupplier.get();
                    val newElmt     = set.apply(orgElmt, replacement);
                    return (DATA)newElmt;
                }
                return orgElmt;
            });
        });
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Func1<DATA, VALUE>   replacementSupplier) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacementSupplier);
    }
    
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Func1<DATA, VALUE>       replacementFunction) {
        return deriveWith(stream -> {
            return (Stream<DATA>)stream.map(orgElmt -> {
                val value   = get.apply(orgElmt);
                if (value == null) {
                    val replacement = replacementFunction.apply(orgElmt);
                    val newElmt     = set.apply(orgElmt, replacement);
                    return (DATA)newElmt;
                }
                return orgElmt;
            });
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
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2) {
        return StreamPlusMapAddOnHelper.mapFirst(this, mapper1, mapper2);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        return StreamPlusMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4) {
        return StreamPlusMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3, mapper4);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5) {
        return StreamPlusMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        return StreamPlusMapAddOnHelper.mapFirst(this, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
    
    //-- mapWithIndex --
    
    public default StreamPlus<Tuple2<Integer, DATA>> mapWithIndex() {
        val index = new AtomicInteger();
        return map(each -> Tuple2.of(index.getAndIncrement(), each));
    }
    
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
    
    //-- mapWithPrev --
    
    public default <TARGET> StreamPlus<TARGET> mapWithPrev(BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        val prev = new AtomicReference<Result<DATA>>(Result.ofNotExist());
        return map(element -> {
            val newValue = mapper.apply(prev.get(), element);
            prev.set(Result.valueOf(element));
            return newValue;
        });
    }
    
    // -- accumulate --
    
    public default StreamPlus<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
        val iterator = this.iterator();
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
    }
    
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
        val endStream = StreamPlus.iterate(seed, func).takeUntil(t -> t == null).skip(1).map(t -> t._1());
        return endStream;
    }
    
    //== Map to tuple. ==
    // ++ Generated with: GeneratorFunctorMapToTupleToObject ++
    
    public default <T1, T2> 
        StreamPlus<Tuple2<T1, T2>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return mapThen(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> 
        StreamPlus<Tuple3<T1, T2, T3>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> 
        StreamPlus<Tuple4<T1, T2, T3, T4>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        StreamPlus<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5,
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
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    //-- Map and combine --
    
    public default <T1, T2, T> 
        StreamPlus<T> mapThen(
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
        StreamPlus<T> mapThen(
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
        StreamPlus<T> mapThen(
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
        StreamPlus<T> mapThen(
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
        StreamPlus<T> mapThen(
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
    
    public default StreamPlus<DATA> excludeIn(Collection<? super DATA> collection) {
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
    
    public default StreamPlus<DATA> flatMapOnly(Predicate<? super DATA> checker, Function<? super DATA, ? extends Stream<DATA>> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : StreamPlus.of(d));
    }
    public default <T> StreamPlus<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Stream<T>> mapper, 
            Function<? super DATA, Stream<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    
    //-- segment --
    
    public default StreamPlus<StreamPlus<DATA>> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<StreamPlus<DATA>> segment(int count, boolean includeTail) {
        val index = new AtomicInteger(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeTail);
    }
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<DATA>());
        val adding = new AtomicBoolean(false);
        
        val streamOrNull = (Function<DATA, StreamPlus<DATA>>)((DATA data) ->{
            if (startCondition.test(data)) {
                adding.set(true);
                val retList = list.getAndUpdate(l -> new ArrayList<DATA>());
                list.get().add(data);
                return retList.isEmpty()
                        ? null
                        : StreamPlus.from(retList.stream());
            }
            if (adding.get()) list.get().add(data);
            return null;
        });
        val mainStream = StreamPlus.from(map(streamOrNull)).filterNonNull();
        if (!includeTail)
            return mainStream;
        
        val mainSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->mainStream;
        val tailSupplier = (Supplier<StreamPlus<StreamPlus<DATA>>>)()->{
            return StreamPlus.of(
                    StreamPlus.from(
                            list.get()
                            .stream()));
        };
        val resultStream
                = StreamPlus.of(mainSupplier, tailSupplier)
                .flatMap(Supplier::get);
        return resultStream;
    }
    
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<DATA>());
        val adding = new AtomicBoolean(false);
        
        return StreamPlus.from(
                map(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                    }
                    if (includeTail && adding.get()) list.get().add(i);
                    if (endCondition.test(i)) {
                        adding.set(false);
                        val retList = list.getAndUpdate(l -> new ArrayList<DATA>());
                        return StreamPlus.from(retList.stream());
                    }
                    
                    if (!includeTail && adding.get()) list.get().add(i);
                    return null;
                }))
            .filterNonNull();
    }
    
    //-- Zip --
    
    public default <B, TARGET> StreamPlus<TARGET> combine(Stream<B> anotherStream, Func2<DATA, B, TARGET> combinator) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth)
                .map(combinator::applyTo);
    }
    public default <B, TARGET> StreamPlus<TARGET> combine(Stream<B> anotherStream, ZipWithOption option, Func2<DATA, B, TARGET> combinator) {
        return zipWith(anotherStream, option)
                .map(combinator::applyTo);
    }
    
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth);
    }
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <B> StreamPlus<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream, ZipWithOption option) {
        val iteratorA = this.iterator();
        val iteratorB = anotherStream.iterator();
        val iterable = new Iterable<Tuple2<DATA,B>>() {
            @Override
            public Iterator<Tuple2<DATA, B>> iterator() {
                return new Iterator<Tuple2<DATA,B>>() {
                    private boolean hasNextA;
                    private boolean hasNextB;
                    
                    public boolean hasNext() {
                        hasNextA = iteratorA.hasNext();
                        hasNextB = iteratorB.hasNext();
                        return (option == ZipWithOption.RequireBoth)
                                ? (hasNextA && hasNextB)
                                : (hasNextA || hasNextB);
                    }
                    public Tuple2<DATA,B> next() {
                        val nextA = hasNextA ? iteratorA.next() : null;
                        val nextB = hasNextB ? iteratorB.next() : null;
                        return Tuple2.of(nextA, nextB);
                    }
                };
            }
            
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    public default StreamPlus<DATA> choose(Stream<DATA> anotherStream, Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return zipWith(anotherStream, AllowUnpaired)
                .map(t -> {
                    val _1 = t._1();
                    val _2 = t._2();
                    if ((_1 != null) && _2 == null)
                        return _1;
                    if ((_1 == null) && _2 != null)
                        return _2;
                    if ((_1 == null) && _2 == null)
                        return null;
                    val which = selectThisNotAnother.applyTo(t);
                    return which ? _1 : _2;
                })
                .filterNonNull();
    }
    public default StreamPlus<DATA> merge(Stream<DATA> anotherStream) {
        val iteratorA = this.iterator();
        val iteratorB = anotherStream.iterator();
        val iterable = new Iterable<DATA>() {
            @Override
            public Iterator<DATA> iterator() {
                return new Iterator<DATA>() {
                    private boolean isA = true;
                    
                    public boolean hasNext() {
                        if (isA) {
                            if (iteratorA.hasNext()) return true;
                            isA = false;
                            if (iteratorB.hasNext()) return true;
                            return false;
                        }
                        
                        if (iteratorB.hasNext()) return true;
                        isA = true;
                        if (iteratorA.hasNext()) return true;
                        return false;
                    }
                    public DATA next() {
                        val next = isA ? iteratorA.next() : iteratorB.next();
                        isA = !isA;
                        return next;
                    }
                };
            }
            
        };
        return StreamPlus.from(StreamSupport.stream(iterable.spliterator(), false));
    }
    
    // TODO - collapse(IntFunction<DATA> numbersToCollapse, Func2<DATA, DATA, DATA> concatFunc)
    
    @SuppressWarnings("unchecked")
    public default StreamPlus<DATA> collapse(Predicate<DATA> conditionToCollapse, Func2<DATA, DATA, DATA> concatFunc) {
        return terminate(stream -> {
            val iterator = stream.iterator();
            
            DATA first = null;
            try {
                first = iterator.next();
            } catch (NoSuchElementException e) {
                return StreamPlus.empty();
            }
            
            val prev = new AtomicReference<Object>(first);
            return StreamPlus.generateBy(()->{
                if (prev.get() == Helper.dummy)
                    throw new NoMoreResultException();
                
                while(true) {
                    DATA next;
                    try {
                        next = iterator.next();
                    } catch (NoSuchElementException e) {
                        val yield = prev.get();
                        prev.set(Helper.dummy);
                        return (DATA)yield;
                    }
                    if (conditionToCollapse.test(next)) {
                        prev.set(concatFunc.apply((DATA)prev.get(), next));
                    } else {
                        val yield = prev.get();
                        prev.set(next);
                        return (DATA)yield;
                    }
                }
            });
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
        
        public static <T> boolean equals(Stream<T> stream1, Stream<T> stream2) {
            return !from    (stream1)
                    .combine(from(stream2), AllowUnpaired, notEqual())
                    .filter (TRUE::equals)
                    .findAny()
                    .isPresent();
        }
        
        public static <T> int hashCode(Stream<T> stream) {
            return stream
                    .mapToInt(e -> (e == null) ? 0 : e.hashCode())
                    .reduce(1, (h, eh) -> 31*h + eh);
        }
        
        public static <T> String toString(Stream<T> stream) {
            return "[" + StreamPlus.from(stream).joinToString(", ") + "]";
        }
        
    }
    
}
