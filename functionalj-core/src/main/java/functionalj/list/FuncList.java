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
package functionalj.list;
   
import static functionalj.function.Func.alwaysTrue;
import static functionalj.function.Func.themAll;
import static functionalj.lens.Access.$I;
import static java.util.function.Function.identity;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.lens.lenses.AnyLens;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompleteAction;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlus.Helper;
import functionalj.stream.Streamable;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.IntTuple2;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public interface FuncList<DATA>
        extends ReadOnlyList<DATA>, Streamable<DATA>, Pipeable<FuncList<DATA>>, Predicate<DATA> {
    
    public static <T> ImmutableList<T> empty() {
        return ImmutableList.empty();
    }
    
    public static <T> ImmutableList<T> emptyList() {
        return ImmutableList.empty();
    }
    
    public static <T> ImmutableList<T> empty(Class<T> elementClass) {
        return ImmutableList.empty();
    }
    
    public static <T> ImmutableList<T> emptyList(Class<T> elementClass) {
        return ImmutableList.empty();
    }
    
    @SafeVarargs
    public static <T> ImmutableList<T> of(T... data) {
        return ImmutableList.of(data);
    }
    
    @SafeVarargs
    public static <T> ImmutableList<T> AllOf(T... data) {
        return ImmutableList.of(data);
    }
    
    // TODO - Function to create FuncList from function of Array
    
    public static <T> ImmutableList<T> from(T[] datas) {
        return ImmutableList.from(datas);
    }
    
    public static <T> ImmutableList<T> from(Collection<T> data) {
        return ImmutableList.from(data);
    }
    
    public static <T> ImmutableList<T> from(List<T> data) {
        return ImmutableList.from(data);
    }
    
    @SafeVarargs
    public static <T> ImmutableList<T> ListOf(T... data) {
        return ImmutableList.of(data);
    }
    
    @SafeVarargs
    public static <T> ImmutableList<T> listOf(T... data) {
        return ImmutableList.of(data);
    }
    
    public static <T> FuncList<T> from(Streamable<T> streamable) {
        return new FuncListDerived<T, T>(streamable, identity());
    }
    
    public static <T> ImmutableList<T> from(Stream<T> stream) {
        return new ImmutableList<T>(stream.collect(Collectors.toList()));
    }
    
    public static <T> ImmutableList<T> from(ReadOnlyList<T> readOnlyList) {
        return ImmutableList.from(readOnlyList);
    }
    
    public static <T> ImmutableList<T> from(FuncList<T> funcList) {
        return ImmutableList.from(funcList);
    }
    
    public static <T> FuncListBuilder<T> newFuncList() {
        return new FuncListBuilder<T>();
    }
    
    public static <T> FuncListBuilder<T> newList() {
        return new FuncListBuilder<T>();
    }
    
    public static <T> FuncListBuilder<T> newBuilder() {
        return new FuncListBuilder<T>();
    }
    
    // == Override ==
    
    @Override
    public default <TARGET> FuncList<TARGET> deriveWith(Function<Stream<DATA>, Stream<TARGET>> action) {
        val list = new FuncListDerived<DATA, TARGET>(this, action);
        val isLazy = isLazy();
        return isLazy ? list : new ImmutableList<>(list, false);
    }
    
    @Override
    public default <TARGET> FuncList<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action) {
        return FuncListDerived.from((Supplier<Stream<TARGET>>) () -> {
            return action.apply(FuncList.this);
        });
    }
    
    @Override
    public default FuncList<DATA> __data() throws Exception {
        return this;
    }
    
    @Override
    public default boolean test(DATA data) {
        return contains(data);
    }
    
    public default boolean isLazy() {
        return true;
    }
    
    public default boolean isEager() {
        return false;
    }
    
    public FuncList<DATA> lazy();
    
    public FuncList<DATA> eager();
    
    @Override
    public default List<DATA> toJavaList() {
        return this;
    }
    
    public default FuncList<DATA> toList() {
        return this;
    }
    
    public default ImmutableList<DATA> freeze() {
        return toImmutableList();
    }
    
    // -- List specific --
    
    public default FuncList<Integer> indexesOf(Predicate<? super DATA> check) {
        return this.mapWithIndex((index, data) -> check.test(data) ? index : -1).filter($I.thatNotEqualsTo(-1))
                .toImmutableList();
    }
    
    @Override
    public default int indexOf(Object o) {
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    public default Optional<DATA> first() {
        val valueRef = new AtomicReference<DATA>();
        if (!Helper.hasAt(stream(), 0, valueRef))
            return Optional.empty();
    
        return Optional.ofNullable(valueRef.get());
    }
    
    public default FuncList<DATA> first(int count) {
        val size  = size();
        val index = Math.max(0, size - count);
        return skip(index);
    }
    
    public default Optional<DATA> last() {
        val size = this.size();
        if (size <= 0)
            return Optional.empty();
    
        return Optional.ofNullable(get(size - 1));
    }
    
    public default FuncList<DATA> last(int count) {
        return limit(count);
    }
    
    public default Optional<DATA> at(int index) {
        val ref = new AtomicReference<DATA>();
        val found = Helper.hasAt(this.stream(), index, ref);
        if (!found)
            Optional.empty();
    
        return Optional.ofNullable(ref.get());
    }
    
    public default FuncList<DATA> rest() {
        return deriveWith(stream -> stream.skip(1));
    }
    
    // Note - Eager
    public default FuncList<DATA> reverse() {
        val temp = this.toMutableList();
        Collections.reverse(temp);
    
        val list = FuncList.from(temp);
        return isLazy() ? list.lazy() : list.eager();
    }
    
    // Note - Eager
    public default FuncList<DATA> shuffle() {
        val temp = this.toMutableList();
        Collections.shuffle(temp);
    
        val list = FuncList.from(temp);
        return isLazy() ? list.lazy() : list.eager();
    }
    
    public default FuncList<IntTuple2<DATA>> query(Predicate<? super DATA> check) {
        return this.mapWithIndex((index, data) -> check.test(data) ? new IntTuple2<DATA>(index, data) : null)
                .filterNonNull();
    }
    
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Func1<DATA, D> mapper) {
        return minIndexBy(alwaysTrue(), mapper);
    }
    
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Func1<DATA, D> mapper) {
        return maxIndexBy(alwaysTrue(), mapper);
    }
    
    public default <D extends Comparable<D>> Optional<Integer> minIndexBy(Predicate<DATA> filter,
            Func1<DATA, D> mapper) {
        return stream().mapWithIndex(Tuple::of).filter(t -> filter.test(t._2)).minBy(t -> mapper.apply(t._2))
                .map(t -> t._1);
    }
    
    public default <D extends Comparable<D>> Optional<Integer> maxIndexBy(Predicate<DATA> filter,
            Func1<DATA, D> mapper) {
        return stream().mapWithIndex(Tuple::of).filter(t -> filter.test(t._2)).maxBy(t -> mapper.apply(t._2))
                .map(t -> t._1);
    }
    
    // == Modified methods ==
    
    @Override
    public default <T> T[] toArray(T[] a) {
        return toJavaList().toArray(a);
    }
    
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream().toArray(generator);
    }
    
    public default FuncList<DATA> append(DATA value) {
        return deriveWith(stream -> Stream.concat(stream, Stream.of(value)));
    }
    
    public default FuncList<DATA> appendAll(DATA[] values) {
        return deriveWith(stream -> Stream.concat(stream, Stream.of(values)));
    }
    
    public default FuncList<DATA> appendAll(Collection<? extends DATA> collection) {
        return ((collection == null) || collection.isEmpty()) ? this
                : deriveWith(stream -> Stream.concat(stream, collection.stream()));
    }
    
    public default FuncList<DATA> appendAll(Supplier<Stream<? extends DATA>> supplier) {
        return (supplier == null) ? this : deriveWith(stream -> Stream.concat(stream, supplier.get()));
    }
    
    public default FuncList<DATA> prepend(DATA value) {
        return deriveWith(stream -> Stream.concat(Stream.of(value), stream));
    }
    
    public default FuncList<DATA> prependAll(DATA[] values) {
        return deriveWith(stream -> Stream.concat(Stream.of(values), stream));
    }
    
    public default FuncList<DATA> prependAll(Collection<? extends DATA> collection) {
        return ((collection == null) || collection.isEmpty()) ? this
                : deriveWith(stream -> Stream.concat(collection.stream(), stream));
    }
    
    public default FuncList<DATA> prependAll(Streamable<? extends DATA> streamable) {
        return (streamable == null) ? this : deriveWith(stream -> Stream.concat(streamable.stream(), stream));
    }
    
    public default FuncList<DATA> prependAll(Supplier<Stream<? extends DATA>> supplier) {
        return (supplier == null) ? this : deriveWith(stream -> Stream.concat(supplier.get(), stream));
    }
    
    public default FuncList<DATA> with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
    
        val i = new AtomicInteger();
        return deriveWith(stream -> stream.map(each -> (i.getAndIncrement() == index) ? value : each));
    }
    
    public default FuncList<DATA> with(int index, Function<DATA, DATA> mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
    
        val i = new AtomicInteger();
        return deriveWith(stream -> stream.map(each -> (i.getAndIncrement() == index) ? mapper.apply(each) : each));
    }
    
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> insertAt(int index, DATA... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
    
        return FuncListDerived.from(deriveFrom((Streamable<DATA> streamable) -> {
            return Stream.concat(streamable.stream().limit(index),
                    Stream.concat(Stream.of(elements), streamable.stream().skip(index + 1)));
        }));
    }
    
    public default FuncList<DATA> insertAllAt(int index, Collection<? extends DATA> collection) {
        if ((collection == null) || collection.isEmpty())
            return this;
    
        return FuncListDerived.from(deriveFrom((Streamable<DATA> streamable) -> {
            return (Stream<DATA>) Stream.concat(streamable.stream().limit(index),
                    Stream.concat(collection.stream(), streamable.stream().skip(index + 1)));
        }));
    }
    
    public default FuncList<DATA> insertAllAt(int index, Streamable<? extends DATA> theStreamable) {
        if (theStreamable == null)
            return this;
    
        return FuncListDerived.from(deriveFrom((Streamable<DATA> streamable) -> {
            return Stream.concat(streamable.stream().limit(index),
                    Stream.concat(theStreamable.stream(), streamable.stream().skip(index + 1)));
        }));
    }
    
    public default FuncList<DATA> excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
    
        return FuncListDerived.from(deriveFrom((Streamable<DATA> streamable) -> {
            return Stream.concat(streamable.stream().limit(index), streamable.stream().skip(index + 2));
        }));
    }
    
    public default FuncList<DATA> excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
    
        return FuncListDerived.from(deriveFrom((Streamable<DATA> streamable) -> {
            return Stream.concat(stream().limit(fromIndexInclusive), stream().skip(fromIndexInclusive + count));
        }));
    }
    
    public default FuncList<DATA> excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException(
                    "fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
    
        return FuncListDerived.from(deriveFrom((Streamable<DATA> streamable) -> {
            return Stream.concat(stream().limit(fromIndexInclusive), stream().skip(toIndexExclusive + 1));
        }));
    }
    
    @Override
    public default FuncList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return new FuncListDerived<>(this, stream -> stream.skip(fromIndexInclusive).limit(length));
    }
    
    // ============================================================================
    // NOTE: The following part of the code was copied from StreamPlus
    // We will write a program to do the copy and replace ...
    // in the mean time, change this in StreamPlus.
    // ++ Plus w/ Self ++
    
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
    
    public default <TARGET> FuncList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            return stream.map(mapper);
        });
    }
    
    public default <TARGET> FuncList<TARGET> flatMap(
            Function<? super DATA, ? extends Streamable<? extends TARGET>> mapper) {
        return deriveWith(stream -> {
            return stream.flatMap(e -> mapper.apply(e).stream());
        });
    }
    
    public default FuncList<DATA> filter(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null) ? stream : stream.filter(predicate);
        });
    }
   
    public default FuncList<DATA> peek(Consumer<? super DATA> action) {
        return deriveWith(stream -> {
            return (action == null) ? stream : stream.peek(action);
        });
    }
    
    // -- Limit/Skip --
    
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
            return (comparator == null) ? stream.sorted() : stream.sorted(comparator);
        });
    }
    
    public default FuncList<DATA> limit(Long maxSize) {
        return deriveWith(stream -> {
            return ((maxSize == null) || (maxSize.longValue() < 0)) ? stream : stream.limit(maxSize);
        });
    }
    
    public default FuncList<DATA> skip(Long startAt) {
        return deriveWith(stream -> {
            return ((startAt == null) || (startAt.longValue() < 0)) ? stream : stream.skip(startAt);
        });
    }
    
    // -- Sorted --
    
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
                return Objects.compare(vA, vB, comparator);
            });
        });
    }
    
    // -- fillNull --
    
    public default <VALUE> FuncList<DATA> fillNull(AnyLens<DATA, VALUE> lens, VALUE replacement) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(lens, replacement));
    }
    
    public default <VALUE> FuncList<DATA> fillNull(Func1<DATA, VALUE> get, Func2<DATA, VALUE, DATA> set,
            VALUE replacement) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(get, set, replacement));
    }
    
    public default <VALUE> FuncList<DATA> fillNull(AnyLens<DATA, VALUE> lens, Supplier<VALUE> replacementSupplier) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(lens, replacementSupplier));
    }
    
    public default <VALUE> FuncList<DATA> fillNull(Func1<DATA, VALUE> get, Func2<DATA, VALUE, DATA> set,
            Supplier<VALUE> replacementSupplier) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(get, set, replacementSupplier));
    }
    
    public default <VALUE> FuncList<DATA> fillNull(AnyLens<DATA, VALUE> lens, Func1<DATA, VALUE> replacementFunction) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(lens, replacementFunction));
    }
    
    public default <VALUE> FuncList<DATA> fillNull(Func1<DATA, VALUE> get, Func2<DATA, VALUE, DATA> set,
            Func1<DATA, VALUE> replacementFunction) {
        return deriveWith(stream -> StreamPlus.from(stream).fillNull(get, set, replacementFunction));
    }
    
    // --map with condition --
    
    public default FuncList<DATA> mapOnly(Predicate<? super DATA> checker, Function<? super DATA, DATA> mapper) {
        return map(d -> checker.test(d) ? mapper.apply(d) : d);
    }
   
    public default <T> FuncList<T> mapIf(Predicate<? super DATA> checker, Function<? super DATA, T> mapper,
            Function<? super DATA, T> elseMapper) {
        return deriveWith(stream -> StreamPlus.from(stream).mapIf(checker, mapper, elseMapper));
    }
    
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2));
    }
    
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3));
    }
    
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3, mapper4));
    }
    
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5) {
        return deriveWith(stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        return deriveWith(
                stream -> StreamPlus.from(stream).mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
    
    // -- mapWithIndex --
    
    public default FuncList<Tuple2<Integer, DATA>> mapWithIndex() {
        val index = new AtomicInteger();
        return map(each -> Tuple2.of(index.getAndIncrement(), each));
    }
    
    public default <T> FuncList<T> mapWithIndex(BiFunction<? super Integer, ? super DATA, T> mapper) {
        val index = new AtomicInteger();
        return map(each -> mapper.apply(index.getAndIncrement(), each));
    }
    
    public default <T1, T> FuncList<T> mapWithIndex(Function<? super DATA, ? extends T1> mapper1,
            BiFunction<? super Integer, ? super T1, T> mapper) {
        return deriveWith(stream -> {
            val index = new AtomicInteger();
            return stream.map(each -> mapper.apply(index.getAndIncrement(), mapper1.apply(each)));
        });
    }
    
    // -- mapWithPrev --
    
    public default <TARGET> FuncList<TARGET> mapWithPrev(
            BiFunction<? super Result<DATA>, ? super DATA, ? extends TARGET> mapper) {
        return deriveWith(stream -> {
            val prev = new AtomicReference<Result<DATA>>(Result.ofNotExist());
            return stream.map(element -> {
                val newValue = mapper.apply(prev.get(), element);
                prev.set(Result.valueOf(element));
                return newValue;
            });
        });
    }
    
    // -- accumulate --
    
    public default FuncList<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
        return deriveWith(stream -> {
            val iterator = StreamPlus.from(stream).iterator();
            if (!iterator.hasNext())
                return StreamPlus.empty();
    
            val prev = new AtomicReference<DATA>(iterator.next());
            return StreamPlus.concat(StreamPlus.of(prev.get()), iterator.stream().map(n -> {
                val next = accumulator.apply(n, prev.get());
                prev.set(next);
                return next;
            }));
        });
    }
    
    // == Map to tuple. ==
    // ++ Generated with: GeneratorFunctorMapToTupleToObject ++
    
    public default <T1, T2> FuncList<Tuple2<T1, T2>> mapTuple(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2) {
        return mapThen(mapper1, mapper2, (v1, v2) -> Tuple2.of(v1, v2));
    }
    
    public default <T1, T2, T3> FuncList<Tuple3<T1, T2, T3>> mapTuple(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3) {
        return mapThen(mapper1, mapper2, mapper3, (v1, v2, v3) -> Tuple3.of(v1, v2, v3));
    }
    
    public default <T1, T2, T3, T4> FuncList<Tuple4<T1, T2, T3, T4>> mapTuple(
            Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2,
            Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, (v1, v2, v3, v4) -> Tuple4.of(v1, v2, v3, v4));
    }
    
    public default <T1, T2, T3, T4, T5> FuncList<Tuple5<T1, T2, T3, T4, T5>> mapTuple(
            Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2,
            Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4,
            Function<? super DATA, ? extends T5> mapper5) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5,
                (v1, v2, v3, v4, v5) -> Tuple5.of(v1, v2, v3, v4, v5));
    }
    
    public default <T1, T2, T3, T4, T5, T6> FuncList<Tuple6<T1, T2, T3, T4, T5, T6>> mapTuple(
            Function<? super DATA, ? extends T1> mapper1, Function<? super DATA, ? extends T2> mapper2,
            Function<? super DATA, ? extends T3> mapper3, Function<? super DATA, ? extends T4> mapper4,
            Function<? super DATA, ? extends T5> mapper5, Function<? super DATA, ? extends T6> mapper6) {
        return mapThen(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6,
                (v1, v2, v3, v4, v5, v6) -> Tuple6.of(v1, v2, v3, v4, v5, v6));
    }
    
    // -- Map and combine --
    
    public default <T1, T2, T> FuncList<T> mapThen(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2, BiFunction<T1, T2, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v = function.apply(v1, v2);
            return v;
        });
    }
    
    public default <T1, T2, T3, T> FuncList<T> mapThen(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3,
            Func3<T1, T2, T3, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v = function.apply(v1, v2, v3);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T> FuncList<T> mapThen(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3,
            Function<? super DATA, ? extends T4> mapper4, Func4<T1, T2, T3, T4, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v = function.apply(v1, v2, v3, v4);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T> FuncList<T> mapThen(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3,
            Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5,
            Func5<T1, T2, T3, T4, T5, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5);
            return v;
        });
    }
    
    public default <T1, T2, T3, T4, T5, T6, T> FuncList<T> mapThen(Function<? super DATA, ? extends T1> mapper1,
            Function<? super DATA, ? extends T2> mapper2, Function<? super DATA, ? extends T3> mapper3,
            Function<? super DATA, ? extends T4> mapper4, Function<? super DATA, ? extends T5> mapper5,
            Function<? super DATA, ? extends T6> mapper6, Func6<T1, T2, T3, T4, T5, T6, T> function) {
        return map(each -> {
            val v1 = mapper1.apply(each);
            val v2 = mapper2.apply(each);
            val v3 = mapper3.apply(each);
            val v4 = mapper4.apply(each);
            val v5 = mapper5.apply(each);
            val v6 = mapper6.apply(each);
            val v = function.apply(v1, v2, v3, v4, v5, v6);
            return v;
        });
    }
    
    // -- Generated with: GeneratorFunctorMapToTupleToObject --
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key,
            Function<? super DATA, ? extends VALUE> mapper) {
        return map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2,
            Function<? super DATA, ? extends VALUE> mapper2) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4,
            Function<? super DATA, ? extends VALUE> mapper4) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data), key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4,
            Function<? super DATA, ? extends VALUE> mapper4, KEY key5,
            Function<? super DATA, ? extends VALUE> mapper5) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4,
            Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4,
            Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7,
            Function<? super DATA, ? extends VALUE> mapper7) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data),
                key7, mapper7.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4,
            Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7,
            Function<? super DATA, ? extends VALUE> mapper7, KEY key8,
            Function<? super DATA, ? extends VALUE> mapper8) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data),
                key7, mapper7.apply(data), key8, mapper8.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4,
            Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7,
            Function<? super DATA, ? extends VALUE> mapper7, KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data),
                key7, mapper7.apply(data), key8, mapper8.apply(data), key9, mapper9.apply(data)));
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(KEY key1,
            Function<? super DATA, ? extends VALUE> mapper1, KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3, KEY key4,
            Function<? super DATA, ? extends VALUE> mapper4, KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6, KEY key7,
            Function<? super DATA, ? extends VALUE> mapper7, KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9, KEY key10,
            Function<? super DATA, ? extends VALUE> mapper10) {
        return map(data -> ImmutableMap.of(key1, mapper1.apply(data), key2, mapper2.apply(data), key3,
                mapper3.apply(data), key4, mapper4.apply(data), key5, mapper5.apply(data), key6, mapper6.apply(data),
                key7, mapper7.apply(data), key8, mapper8.apply(data), key9, mapper9.apply(data), key10,
                mapper10.apply(data)));
    }
    
    // -- Filter --
    
    public default FuncList<DATA> filterNonNull() {
        return deriveWith(stream -> stream.filter(Objects::nonNull));
    }
    
    public default FuncList<DATA> filterIn(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return (collection == null) ? Stream.empty() : stream.filter(data -> collection.contains(data));
        });
    }
    
    public default FuncList<DATA> exclude(Predicate<? super DATA> predicate) {
        return deriveWith(stream -> {
            return (predicate == null) ? stream : stream.filter(data -> !predicate.test(data));
        });
    }
    
    public default FuncList<DATA> excludeIn(Collection<? super DATA> collection) {
        return deriveWith(stream -> {
            return (collection == null) ? stream : stream.filter(data -> !collection.contains(data));
        });
    }
    
    public default FuncList<DATA> exclude(DATA value) {
        return deriveWith(stream -> {
            return stream.filter(data -> !Objects.equals(value, data));
        });
    }
    
    public default FuncList<DATA> excludeAll(DATA[] datas) {
        val dataList = FuncList.of(datas);
        return deriveWith(stream -> {
            return (datas == null) ? stream : stream.filter(data -> !dataList.contains(data));
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
            return (predicate != null) && predicate.apply(index.getAndIncrement(), each);
        });
    }
    
    // -- Peek --
    
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
    
    public default <T> FuncList<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector,
            Consumer<? super T> theConsumer) {
        return peek(value -> {
            val target = mapper.apply(value);
            if (selector.test(target))
                theConsumer.accept(target);
        });
    }
    
    // -- FlatMap --
    
    public default FuncList<DATA> flatMapOnly(Predicate<? super DATA> checker,
            Function<? super DATA, ? extends Streamable<DATA>> mapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : () -> StreamPlus.of(d));
    }
    
    public default <T> FuncList<T> flatMapIf(Predicate<? super DATA> checker,
            Function<? super DATA, Streamable<T>> mapper, Function<? super DATA, Streamable<T>> elseMapper) {
        return flatMap(d -> checker.test(d) ? mapper.apply(d) : elseMapper.apply(d));
    }
    
    // -- segment --
    
    public default FuncList<StreamPlus<DATA>> segment(int count) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).segment(count);
        });
    }
    
    public default FuncList<StreamPlus<DATA>> segment(int count, boolean includeTail) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).segment(count, includeTail);
        });
    }
    
    public default FuncList<StreamPlus<DATA>> segment(Predicate<DATA> startCondition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).segment(startCondition);
        });
    }
    
    public default FuncList<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, boolean includeTail) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).segment(startCondition);
        });
    }
    
    public default FuncList<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).segment(startCondition, endCondition);
        });
    }
    
    public default FuncList<StreamPlus<DATA>> segment(Predicate<DATA> startCondition, Predicate<DATA> endCondition,
            boolean includeLast) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).segment(startCondition, endCondition, includeLast);
        });
    }
    
    // -- Zip --
    
    public default <B, TARGET> FuncList<TARGET> combine(Stream<B> anotherStream, Func2<DATA, B, TARGET> combinator) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).combine(anotherStream, combinator);
        });
    }
    
    public default <B, TARGET> FuncList<TARGET> combine(Stream<B> anotherStream, ZipWithOption option,
            Func2<DATA, B, TARGET> combinator) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).combine(anotherStream, option, combinator);
        });
    }
    
    public default <B> FuncList<Tuple2<DATA, B>> zipWith(Stream<B> anotherStream) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).zipWith(anotherStream);
        });
    }
    
    public default <B> FuncList<Tuple2<DATA, B>> zipWith(Stream<B> anotherStream, ZipWithOption option) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).zipWith(anotherStream, option);
        });
    }
    
    public default FuncList<DATA> choose(Stream<DATA> anotherStream, Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).choose(anotherStream, selectThisNotAnother);
        });
    }
    
    public default FuncList<DATA> merge(Stream<DATA> anotherStream) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream).merge(anotherStream);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default FuncList<DATA> concatWith(FuncList<DATA> ... tails) {
        return deriveWith(stream -> {
            return StreamPlus
                    .concat(StreamPlus.of(stream), StreamPlus.of(tails).map(Streamable::stream))
                    .flatMap(themAll());
        });
    }
    
    // -- Plus w/ Self --
    // ============================================================================
    
    public default FuncList<DATA> collapse(Predicate<DATA> conditionToCollapse, Func2<DATA, DATA, DATA> concatFunc) {
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
