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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.functions.StrFuncs;
import functionalj.lens.lenses.AnyLens;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompleteAction;
import functionalj.result.Result;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
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
public interface Streamable<DATA> extends StreamableWithGet<DATA> {
    
    @SafeVarargs
    public static <D> Streamable<D> of(D ... data) {
        return ()->StreamPlus.from(Stream.of(data));
    }
    
    public static <D> Streamable<D> from(Collection<D> collection) {
        return ()->StreamPlus.from(collection.stream());
    }
    public static <D> Streamable<D> from(Func0<Stream<D>> supplier) {
        return ()->StreamPlus.from(supplier.get());
    }
    
    @SafeVarargs
    public static <D> Streamable<D> cycle(D ... data) {
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
    
    public static <D> Streamable<D> empty() {
        return ()->StreamPlus.empty();
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
    public static <D> Streamable<D> generateBy(Supplier<Supplier<D>> supplier) {
        return ()->StreamPlus.generate(supplier.get());
    }
    
    public static <D> Streamable<D> iterate(D seed, UnaryOperator<D> f) {
        return ()->StreamPlus.iterate(seed, f);
    }
    
    public static <D> Streamable<D> compound(D seed, UnaryOperator<D> f) {
        return ()->StreamPlus.compound(seed, f);
    }
    
    public static <D> Streamable<D> iterate(D seed1, D seed2, BinaryOperator<D> f) {
        return ()->{
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
        };
    }
    
    public static <D, T> Streamable<T> with(Streamable<D> source, Function<Stream<D>, Stream<T>> action) {
        return new Streamable<T>() {
            @Override
            public StreamPlus<T> stream() {
                val sourceStream = source.stream();
                val targetStream = action.apply(sourceStream);
                return StreamPlus.from(targetStream);
            }
        };
    }
    public static <D, T> Streamable<T> from(Streamable<D> source, Function<Streamable<D>, Stream<T>> action) {
        return new Streamable<T>() {
            @Override
            public StreamPlus<T> stream() {
                val targetStream = action.apply(source);
                return StreamPlus.from(targetStream);
            }
        };
    }
    
    public StreamPlus<DATA> stream();
    
    public default <TARGET> Streamable<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
        return Streamable.with(this, action);
    }
    
    public default <TARGET> Streamable<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action) {
        return Streamable.from(this, action);
    }
    
    public default <T> Pipeable<Streamable<DATA>> pipable() {
        return Pipeable.of(this);
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
    
    public default Streamable<DATA> sorted() {
        return deriveWith(stream -> {
            return stream.sorted();
        });
    }
    
    public default Streamable<DATA> sorted(Comparator<? super DATA> comparator) {
        return deriveWith(stream -> {
            return (comparator == null)
                    ? stream.sorted()
                    : stream.sorted(comparator);
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
    
    //-- Sorted --
    
    public default <T extends Comparable<? super T>> Streamable<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> Streamable<DATA> sortedBy(Function<? super DATA, T> mapper, Comparator<T> comparator) {
        return deriveWith(stream -> {
            return stream.sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return Objects.compare(vA,  vB, comparator);
                });
        });
    }
    
    // -- fillNull --
    
    public default <VALUE> Streamable<DATA> fillNull(AnyLens<DATA, VALUE> lens, VALUE replacement) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(lens, replacement));
    }
    
    public default <VALUE> Streamable<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            VALUE                    replacement) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(get, set, replacement));
    }
    
    public default <VALUE> Streamable<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Supplier<VALUE>      replacementSupplier) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(lens, replacementSupplier));
    }
    
    public default <VALUE> Streamable<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Supplier<VALUE>          replacementSupplier) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(get, set, replacementSupplier));
    }
    
    public default <VALUE> Streamable<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Func1<DATA, VALUE>   replacementFunction) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(lens, replacementFunction));
    }
    
    public default <VALUE> Streamable<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Func1<DATA, VALUE>       replacementFunction) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(get, set, replacementFunction));
    }
    
    //--map with condition --
    
    public default Streamable<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
    
    public default <T> Streamable<T> mapIf(
            Predicate<? super DATA>   checker, 
            Function<? super DATA, T> mapper, 
            Function<? super DATA, T> elseMapper) {
        return deriveWith(stream -> StreamPlus.from(stream).mapIf(checker, mapper, elseMapper));
    }
    
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2));
    }
    
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3));
    }
    
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3, mapper4));
    }
    
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    public default <T> Streamable<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
    
    //-- mapWithIndex --
    
    public default Streamable<Tuple2<Integer, DATA>> mapWithIndex() {
        val index = new AtomicInteger();
        return map(each -> Tuple2.of(index.getAndIncrement(), each));
    }
    
    public default <T> Streamable<T> mapWithIndex(BiFunction<? super Integer, ? super DATA, T> mapper) {
        return deriveWith(stream -> {
            val index = new AtomicInteger();
            return stream.map(each -> mapper.apply(index.getAndIncrement(), each));
        });
    }
    
    public default <T1, T> Streamable<T> mapWithIndex(
                Function<? super DATA, ? extends T1>       mapper1,
                BiFunction<? super Integer, ? super T1, T> mapper) {
        return deriveWith(stream -> {
            val index = new AtomicInteger();
            return stream.map(each -> mapper.apply(
                                    index.getAndIncrement(),
                                    mapper1.apply(each)));
        });
    }
    
    //-- mapWithPrev --
    
    public default <TARGET> Streamable<TARGET> mapWithPrev(BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            val prev = new AtomicReference<Result<DATA>>(Result.ofNotExist());
            return map(element -> {
                val newValue = mapper.apply(prev.get(), element);
                prev.set(Result.valueOf(element));
                return newValue;
            })
            .stream();
        });
    }
    
    // -- accumulate --
    
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
    
    //== Map to tuple. ==
    // ++ Generated with: GeneratorFunctorMapToTupleToObject ++
    
    public default <T1, T2> 
        Streamable<Tuple2<T1, T2>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2) {
        return mapThen(mapper1, mapper2,
                   (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> 
        Streamable<Tuple3<T1, T2, T3>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3,
                   (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> 
        Streamable<Tuple4<T1, T2, T3, T4>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4,
                   (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> 
        Streamable<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
                Function<? super DATA, ? extends T1> mapper1,
                Function<? super DATA, ? extends T2> mapper2,
                Function<? super DATA, ? extends T3> mapper3,
                Function<? super DATA, ? extends T4> mapper4,
                Function<? super DATA, ? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5,
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
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                   (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    //-- Map and combine --
    
    public default <T1, T2, T> 
        Streamable<T> mapThen(
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
        Streamable<T> mapThen(
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
        Streamable<T> mapThen(
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
        Streamable<T> mapThen(
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
        Streamable<T> mapThen(
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
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
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
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
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
        return deriveWith(stream -> stream.filter(Objects::nonNull));
    }
    
    public default Streamable<DATA> filterIn(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return (collection == null)
                ? Stream.empty()
                : stream.filter(data -> collection.contains(data));
        });
    }
    
    public default Streamable<DATA> exclude(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null)
                ? stream
                : stream.filter(data -> !predicate.test(data));
        });
    }
    
    public default Streamable<DATA> excludeIn(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
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

    public default Streamable<DATA> filterWithIndex(BiFunction<? super Integer, ? super DATA, Boolean> predicate) {
        val index = new AtomicInteger();
        return filter(each -> {
                    return (predicate != null) 
                            && predicate.apply(index.getAndIncrement(), each);
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
    
    public default Streamable<DATA> flatMapOnly(Predicate<? super DATA> checker, Function<? super DATA, ? extends Streamable<DATA>> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) :()-> StreamPlus.of(d));
    }
    public default <T> Streamable<T> flatMapIf(
            Predicate<? super DATA> checker, 
            Function<? super DATA, Streamable<T>> mapper, 
            Function<? super DATA, Streamable<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    //-- segment --
    
    public default Streamable<StreamPlus<DATA>> segment(int count) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).segment(count);
        });
    }
    public default Streamable<StreamPlus<DATA>> segment(int count, boolean includeTail) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).segment(count, includeTail);
        });
    }
    public default Streamable<StreamPlus<DATA>> segment(Predicate<DATA> startCondition) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).segment(startCondition);
        });
    }
    public default Streamable<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, boolean includeTail) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).segment(startCondition, includeTail);
        });
    }
    
    public default Streamable<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).segment(startCondition, endCondition);
        });
    }
    
    public default Streamable<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition, boolean includeLast) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).segment(startCondition, endCondition, includeLast);
        });
    }
    
    public default <T> Streamable<FuncList<DATA>> segmentByPercentiles(int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(percentileList);
    }
    
    public default <T> Streamable<FuncList<DATA>> segmentByPercentiles(double ... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(percentileList);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(FuncList<Double> percentiles) {
        val list = sorted().toImmutableList();
        return Helper.segmentByPercentiles(list, percentiles);
    }
    
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, Comparator<T> comparator, int ... percentiles) {
        val percentileList = IntStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, double ... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(mapper, percentileList);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, Comparator<T> comparator, double ... percentiles) {
        val percentileList = DoubleStreamPlus.of(percentiles).mapToObj(Double::valueOf).toImmutableList();
        return segmentByPercentiles(mapper, comparator, percentileList);
    }
    
    public default <T extends Comparable<? super T>> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, FuncList<Double> percentiles) {
        val list = sortedBy(mapper).toImmutableList();
        return Helper.segmentByPercentiles(list, percentiles);
    }
    
    public default <T> FuncList<FuncList<DATA>> segmentByPercentiles(Function<? super DATA, T> mapper, Comparator<T> comparator, FuncList<Double> percentiles) {
        val list = sortedBy(mapper, comparator).toImmutableList();
        return Helper.segmentByPercentiles(list, percentiles);
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
    
    //-- Zip --
    
    public default <B, TARGET> Streamable<TARGET> combine(Stream<B> anotherStream, Func2<DATA, B, TARGET> combinator) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).combine(anotherStream, combinator);
        });
    }
    public default <B, TARGET> Streamable<TARGET> combine(Stream<B> anotherStream, ZipWithOption option, Func2<DATA, B, TARGET> combinator) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).combine(anotherStream, option, combinator);
        });
    }
    
    public default <B> Streamable<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).zipWith(anotherStream);
        });
    }
    public default <B> Streamable<Tuple2<DATA,B>> zipWith(Stream<B> anotherStream, ZipWithOption option) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).zipWith(anotherStream, option);
        });
    }
    
    public default Streamable<DATA> choose(Stream<DATA> anotherStream, Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).choose(anotherStream, selectThisNotAnother);
        });
    }
    public default Streamable<DATA> merge(Stream<DATA> anotherStream) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream).merge(anotherStream);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default Streamable<DATA> concatWith(Streamable<DATA> ... tails) {
        return deriveWith(stream -> {
            return StreamPlus
                    .concat(StreamPlus.of(stream), StreamPlus.of(tails).map(Streamable::stream))
                    .flatMap(themAll());
        });
    }
    
    //-- Plus w/ Self --
    //============================================================================
    
    //== Functionalities ==
    
    public default IntStreamPlus mapToInt(ToIntFunction<? super DATA> mapper) {
        return IntStreamPlus.from(stream().mapToInt(mapper));
    }
    
    public default LongStream mapToLong(ToLongFunction<? super DATA> mapper) {
        return stream().mapToLong(mapper);
    }
    
    public default DoubleStream mapToDouble(ToDoubleFunction<? super DATA> mapper) {
        return stream().mapToDouble(mapper);
    }
    
    public default IntStream flatMapToInt(Function<? super DATA, ? extends IntStream> mapper) {
        return IntStreamPlus.from(stream().flatMapToInt(mapper));
    }
    
    public default LongStream flatMapToLong(Function<? super DATA, ? extends LongStream> mapper) {
        return stream().flatMapToLong(mapper);
    }
    
    public default DoubleStream flatMapToDouble(Function<? super DATA, ? extends DoubleStream> mapper) {
        return stream().flatMapToDouble(mapper);
    }
    
    public default void forEach(Consumer<? super DATA> action) {
        if (action == null)
            return;
        
        stream().forEach(action);
    }
    
    public default void forEachWithIndex(BiConsumer<? super Integer, ? super DATA> action) {
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
    
    public default <D extends Comparable<D>> Optional<DATA> minBy(Func1<DATA, D> mapper) {
        return stream().min((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
    }
    
    public default <D extends Comparable<D>> Optional<DATA> maxBy(Func1<DATA, D> mapper) {
        return stream().max((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
    }
    
    public default Optional<BigDecimal> sumToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        return map(toBigDecimal).reduce(BigDecimal::add);
    }
    
    public default Optional<BigDecimal> minToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        return map(toBigDecimal).reduce((a, b) -> a.compareTo(b) <= 0 ? a : b);
    }
    
    public default Optional<BigDecimal> maxToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        return map(toBigDecimal).reduce((a, b) -> a.compareTo(b) <= 0 ? b : a);
    }
    
    public default Optional<BigDecimal> averageToBigDecimal(Function<? super DATA, BigDecimal> toBigDecimal) {
        val countSum = map(each -> Tuple.of(1, toBigDecimal.apply(each)))
        .reduce((a, b)->Tuple.of(a._1 + b._1, a._2.add(b._2)));
        return countSum.map(t -> t._2.divide(new BigDecimal(t._1)));
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
        return stream().count();
    }
    
    public default int size() {
        return (int)stream().count();
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
    
    public default Optional<DATA> findFirst(Predicate<? super DATA> predicate) {
        return stream().filter(predicate).findFirst();
    }
    
    public default Optional<DATA> findAny(Predicate<? super DATA> predicate) {
        return stream().filter(predicate).findAny();
    }
    
    public default <T> Optional<DATA> findFirst(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(mapper, theCondition).findFirst();
    }
    
    public default <T>  Optional<DATA> findAny(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(mapper, theCondition).findAny();
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
    
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    public default <KEY> FuncMap<KEY, FuncList<DATA>> groupingBy(Function<? super DATA, ? extends KEY> classifier) {
        val theMap = new HashMap<KEY, FuncList<DATA>>();
        stream()
            .collect(Collectors.groupingBy(classifier))
            .forEach((key,list)->theMap.put(key, ImmutableList.from(list)));
        return ImmutableMap.from(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(Function<? super DATA, ? extends KEY> keyMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, data -> data));
        return (FuncMap<KEY, DATA>)ImmutableMap.from(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>  keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper));
        return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>   keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper,
                BinaryOperator<VALUE> mergeFunction) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
        return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
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
    
    public default <T> T pipe(Function<? super Streamable<DATA>, T> piper) {
        return piper.apply(this);
    }
    
    public default Streamable<DATA> collapse(Predicate<DATA> conditionToCollapse, Func2<DATA, DATA, DATA> concatFunc) {
        return deriveWith(stream -> { 
            return StreamPlus.from(stream()).collapse(conditionToCollapse, concatFunc);
        });
    }
    
    public default <T> Streamable<Result<T>> spawn(Func1<DATA, ? extends UncompleteAction<T>> mapper) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream()).spawn(mapper);
        });
    }
    
}
