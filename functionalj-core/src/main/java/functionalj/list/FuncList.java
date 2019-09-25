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
import static functionalj.lens.Access.$I;
import static java.util.function.Function.identity;

import java.lang.reflect.Array;
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
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlusHelper;
import functionalj.stream.Streamable;
import functionalj.tuple.IntTuple2;
import functionalj.tuple.Tuple;
import lombok.val;

@SuppressWarnings("javadoc")
public interface FuncList<DATA>
        extends 
            ReadOnlyList<DATA>, 
            Streamable<DATA>, 
            Pipeable<FuncList<DATA>>, 
            Predicate<DATA>,
            FuncListWithMapFirst<DATA>,
            FuncListWithMapThen<DATA>,
            FuncListWithMapTuple<DATA>,
            FuncListWithMapToMap<DATA>,
            FuncListWithFillNull<DATA>,
            FuncListWithCombine<DATA>,
            FuncListAdditionalOperations<DATA> {
    
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
        if (!StreamPlusHelper.hasAt(stream(), 0, valueRef))
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
        val found = StreamPlusHelper.hasAt(this.stream(), index, ref);
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
    public default Object[] toArray() {
        return stream().toArray();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public default <T> T[] toArray(T[] a) {
        int count = size();
        if (a.length != count) {
            a = (T[])Array.newInstance(a.getClass().getComponentType(), count);
        }
        val array = a;
        forEachWithIndex((index, element) -> array[index] = (T)element);
        return array;
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
    
    // -- Filter --
    
    public default FuncList<DATA> filterNonNull() {
        return deriveWith(stream -> stream.filter(Objects::nonNull));
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
    
    // TODO Join
    
    // -- Plus w/ Self --
    // ============================================================================
    
    public default FuncList<DATA> collapse(Predicate<DATA> conditionToCollapse, Func2<DATA, DATA, DATA> concatFunc) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream()).collapse(conditionToCollapse, concatFunc);
        });
    }
    
    public default <T> Streamable<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapper) {
        return deriveWith(stream -> {
            return StreamPlus.from(stream()).spawn(mapper);
        });
    }
}
