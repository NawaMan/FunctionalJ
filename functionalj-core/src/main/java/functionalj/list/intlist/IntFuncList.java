package functionalj.list.intlist;

import static functionalj.lens.Access.$I;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.list.FuncListDerived;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamPlusHelper;
import functionalj.stream.intstream.IntStreamable;
import functionalj.tuple.IntIntTuple;
import lombok.val;

public interface IntFuncList
        extends 
//            ReadOnlyList<DATA>, 
            IntStreamable, 
            Pipeable<IntFuncList>
//            ,
//            FuncListWithMapFirst<DATA>,
//            FuncListWithMapThen<DATA>,
//            FuncListWithMapTuple<DATA>,
//            FuncListWithMapToMap<DATA>,
//            FuncListWithFillNull<DATA>,
//            FuncListWithCombine<DATA>,
//            FuncListAdditionalOperations<DATA>
                {
    
    public static ImmutableIntList empty() {
        return ImmutableIntList.empty();
    }
    
    public static ImmutableIntList emptyList() {
        return ImmutableIntList.empty();
    }
    
    public static ImmutableIntList emptyIntList() {
        return ImmutableIntList.empty();
    }
    
    public static ImmutableIntList of(int ... data) {
        return ImmutableIntList.of(data);
    }
    
    public static ImmutableIntList AllOf(int... data) {
        return ImmutableIntList.of(data);
    }
    
    public static ImmutableIntList ints(int... data) {
        return ImmutableIntList.of(data);
    }
    
    public static ImmutableIntList intList(int... data) {
        return ImmutableIntList.of(data);
    }
    
    // TODO - Function to create FuncList from function of Array
    
    public static ImmutableIntList from(int[] datas) {
        return ImmutableIntList.of(datas);
    }
    
    public static ImmutableIntList from(Collection<Integer> data, int valueForNull) {
        IntStream intStream = StreamPlus.from(data.stream())
                .fillNull((Integer)valueForNull)
                .mapToInt($I);
        return ImmutableIntList.from(intStream);
    }
    
    @SafeVarargs
    public static ImmutableIntList ListOf(int ... data) {
        return ImmutableIntList.of(data);
    }
    
    @SafeVarargs
    public static ImmutableIntList listOf(int... data) {
        return ImmutableIntList.of(data);
    }
    
    public static IntFuncList from(IntStreamable streamable) {
        return new IntFunctListDerivedFromIntStreamable(streamable, i -> i.stream());
    }
    
    public static IntFuncList from(IntStream stream) {
        return ImmutableIntList.from(stream);
    }
//    
//    public static <T> FuncListBuilder<T> newFuncList() {
//        return new FuncListBuilder<T>();
//    }
//    
//    public static <T> FuncListBuilder<T> newList() {
//        return new FuncListBuilder<T>();
//    }
//    
//    public static <T> FuncListBuilder<T> newBuilder() {
//        return new FuncListBuilder<T>();
//    }
    
    // == Override ==
    
    public default IntFuncList derive(Function<IntStreamable, IntStream> action) {
        val list = new IntFunctListDerivedFromIntStreamable((IntStreamable)()->this.stream(), action);
        val isLazy = isLazy();
        return isLazy ? list : new ImmutableIntList(list, false);
    }
    
    public default <TARGET> FuncList<TARGET> deriveToList(Function<IntStreamable, Stream<TARGET>> action) {
        return FuncListDerived.from(()->action.apply(this));
    }
    
    @Override
    public default IntFuncList __data() throws Exception {
        return this;
    }
    
    public default boolean isEmpty() {
        return size() == 0;
    }
    
    public default boolean contains(int o) {
        return stream().anyMatch(i -> i == o);
    }
    
    public default boolean containsAllOf(int ... c) {
        return IntStreamPlus
                .of(c)
                .allMatch(each -> stream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(int ... c) {
        return IntStreamPlus
                .of(c)
                .anyMatch(each -> stream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsAllOf(Collection<Integer> c) {
        return c.stream()
                .allMatch(each -> stream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(Collection<Integer> c) {
        return c.stream()
                .anyMatch(each -> stream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default IntStreamPlus parallelStream() {
        return stream().parallel();
    }
    
    public default int get(int index) {
        val ref   = new int[1][];
        val found = IntStreamPlusHelper.hasAt(this.stream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        
        return ref[0][0];
    }
    
    public default OptionalInt at(int index){
        return skip(index)
                .limit(1)
                .findFirst()
                ;
    }
    
    public default IntFuncList indexesOf(IntPredicate check) {
        return derive(streamable -> {
            return streamable.mapWithIndex((index, data) -> check.test(data) ? index : -1)
                    .filter(i -> i != -1)
                    .stream();
        });
    }
    
    public default IntFuncList indexesOf(int value) {
        return derive(streamable -> {
            return streamable.mapWithIndex((index, data) -> (data == value) ? index : -1)
                    .filter(i -> i != -1)
                    .stream();
        });
    }
    
    public default int indexOf(int o) {
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    public default int lastIndexOf(int o){
        return indexesOf(each -> Objects.equals(o, each)).last().orElse(-1);
    }
    
    public default IntFuncList subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return derive(streamable -> {
            return streamable.stream().skip(fromIndexInclusive).limit(length);
        });
    }
    
    public default boolean isLazy() {
        return true;
    }
    
    public default boolean isEager() {
        return false;
    }
    
    public IntFuncList lazy();
    
    public IntFuncList eager();
    
    public default ImmutableIntList freeze() {
        return toImmutableList();
    }
    
    public default List<Integer> toJavaList() {
        return FuncList.from(this.boxed());
    }
    
    public default ImmutableIntList toImmutableList() {
        return ImmutableIntList.from(this);
    }
    
    @Override
    public default int[] toArray() {
        return stream().toArray();
    }
    
    // -- List specific --
    
    public default OptionalInt first() {
        return stream()
                .limit(1)
                .findFirst()
                ;
    }
    
    public default IntFuncList first(int count) {
        return derive(streamable -> {
            return streamable
                    .limit(count)
                    .stream()
                    ;
        });
    }
    
    public default OptionalInt last() {
        return last(1)
                .findFirst()
                ;
    }
    
    public default IntFuncList last(int count) {
        val size   = this.size();
        val offset = Math.max(0, size - count);
        return derive(streamble -> {
            return streamble
                    .skip(offset)
                    .stream()
                    ;
        });
    }
    
    
    public default IntFuncList rest() {
        return derive(streamble 
                -> streamble
                .stream()
                .skip(1));
    }
    
    // Note - Eager
    public default IntFuncList reverse() {
        val length = size();
        if (length <= 1)
            return this;
        
        val array = toArray();
        val mid = length / 2;
        for (int i = 0; i < mid; i++) {
            val j = length - i - 1;
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableIntList(array, isLazy());
    }
    
//    // Note - Eager
    public default IntFuncList shuffle() {
        val length = size();
        if (length <= 1)
            return this;
        
        val array = toArray();
        val rand  = new Random();
        for (int i = 0; i < length; i++) {
            val j = rand.nextInt(length);
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableIntList(array, isLazy());
    }
    
    public default FuncList<IntIntTuple> query(IntPredicate check) {
        // TODO - Implement this.
//        return this.mapToObjWithIndex((index, data) -> check.test(data) ? IntIntTuple.of(index, data) : null)
//                .filterNonNull();
        return null;
    }
    
    //== Min/Max ==
//    
//    public default <D extends Comparable<D>> OptionalInt minIndexOf(
//            IntPredicate   filter,
//            IntUnaryOperator mapper) {
//        return stream()
//                .mapWithIndex(mapper) ()
//                .filter(t -> filter.test(t._2))
//                .minBy (t -> mapper.apply(t._2))
//                .map   (t -> OptionalInt.of(t._1))
//                .orElse(OptionalInt.empty());
//    }
//    
//    public default <D extends Comparable<D>> OptionalInt maxIndexOf(
//            IntPredicate   filter,
//            IntFunction<D> mapper) {
//        return stream()
//                .mapWithIndex()
//                .filter(t -> filter.test(t._2))
//                .maxBy (t -> mapper.apply(t._2))
//                .map   (t -> OptionalInt.of(t._1))
//                .orElse(OptionalInt.empty());
//    }
    
    public default <D extends Comparable<D>> OptionalInt minIndex() {
        return minIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndex() {
        return maxIndexBy(i -> true, i -> i);
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexBy(IntFunction<D> mapper) {
        return minIndexBy(i -> true, mapper);
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(IntFunction<D> mapper) {
        return maxIndexBy(i -> true, mapper);
    }
    
    public default <D extends Comparable<D>> OptionalInt minIndexBy(
            IntPredicate   filter,
            IntFunction<D> mapper) {
        return stream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            IntPredicate   filter,
            IntFunction<D> mapper) {
        return stream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    // == Modified methods ==
    
    public default IntFuncList append(int value) {
        return derive(streamable -> IntStreamPlus.concat(streamable.stream(), IntStreamPlus.of(value)));
    }
    
    public default IntFuncList appendAll(int ... values) {
        return derive(streamable -> IntStreamPlus.concat(streamable.stream(), IntStreamPlus.of(values)));
    }
    public default IntFuncList appendAll(IntStreamable values) {
        return (values == null) 
                ? this 
                : derive(streamable -> IntStreamPlus.concat(streamable.stream(), IntStreamPlus.from(values.stream())));
    }
    
    public default IntFuncList appendAll(Supplier<IntStream> supplier) {
        return (supplier == null) 
                ? this 
                : derive(streamable -> IntStreamPlus.concat(streamable.stream(), IntStreamPlus.from(supplier.get())));
    }
    
    public default IntFuncList prepend(int value) {
        return derive(streamable -> IntStreamPlus.concat(IntStreamPlus.of(value), streamable.stream()));
    }
    
    public default IntFuncList prependAll(int ... values) {
        return derive(streamable -> IntStreamPlus.concat(IntStreamPlus.of(values), streamable.stream()));
    }
    
    public default IntFuncList prependAll(IntStreamable prefixStreamable) {
        return (prefixStreamable == null) 
                ? this 
                : derive(streamable -> IntStreamPlus.concat(prefixStreamable.stream(), streamable.stream()));
    }
    
    public default IntFuncList prependAll(Supplier<IntStream> supplier) {
        return (supplier == null) 
                ? this 
                : derive(streamable -> IntStreamPlus.concat(IntStreamPlus.from(supplier.get()), streamable.stream()));
    }
    
    public default IntFuncList with(int index, int value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return derive(streamable -> {
            val i = new AtomicInteger();
            return streamable
                    .map(each -> (i.getAndIncrement() == index) ? value : each)
                    .stream(); 
        });
    }
    
    public default IntFuncList with(int index, IntUnaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return derive(streamable -> {
            val i = new AtomicInteger();
            return streamable
                    .map(each -> (i.getAndIncrement() == index) ? mapper.applyAsInt(each) : each)
                    .stream(); 
        });
    }
    
    public default IntFuncList insertAt(int index, int ... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        return derive((IntStreamable streamable) -> {
                    return IntStreamPlus.concat(
                            streamable.stream().limit(index),
                            IntStreamPlus.of(elements), 
                            streamable.stream().skip(index));
                });
    }
    
    public default IntFuncList insertAllAt(int index, IntStreamable elements) {
        if ((elements == null) || (elements.size() == 0))
            return this;
        
        return derive((IntStreamable streamable) -> {
                    return IntStreamPlus.concat(
                            streamable.stream().limit(index),
                            elements.stream(), 
                            streamable.stream().skip(index));
                });
    }
    
    public default IntFuncList excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        return derive((IntStreamable streamable) -> {
            return IntStreamPlus.concat(
                    streamable.stream().limit(index), 
                    streamable.stream().skip(index + 1));
        });
    }
    
    public default IntFuncList excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        return derive((IntStreamable streamable) -> {
            return IntStreamPlus.concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(fromIndexInclusive + count));
        });
    }
    
    public default IntFuncList excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException(
                    "fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
        
        return derive((IntStreamable streamable) -> {
            return IntStreamPlus.concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(toIndexExclusive + 1));
        });
    }
    
    // ============================================================================
    // NOTE: The following part of the code was copied from StreamPlus
    // We will write a program to do the copy and replace ...
    // in the mean time, change this in StreamPlus.
    // ++ Plus w/ Self ++
    
    @Override
    public default IntFuncList sequential() {
        return derive(streamable -> {
            return streamable.stream().sequential();
        });
    }
    
    @Override
    public default IntFuncList parallel() {
        return derive(streamable -> {
            return streamable.stream().parallel();
        });
    }
    
    @Override
    public default IntFuncList unordered() {
        return derive(streamable -> {
            return streamable.stream().unordered();
        });
    }
    
    public default IntFuncList map(IntUnaryOperator mapper) {
        return derive(streamable -> {
            return streamable.stream().map(mapper);
        });
    }
    
    public default IntFuncList flatMap(
            IntFunction<? extends IntStream> mapper) {
        return derive(streamable -> {
            return streamable.stream().flatMap(e -> mapper.apply(e));
        });
    }
    
    public default IntFuncList filter(IntPredicate predicate) {
        return derive(streamable -> {
            return (predicate == null) 
                    ? streamable.stream() 
                    : streamable.stream().filter(predicate);
        });
    }
   
    public default IntFuncList peek(IntConsumer action) {
        return derive(streamable -> {
            return (action == null) 
                    ? streamable.stream()
                    : streamable.stream().peek(action);
        });
    }
//    
//    // -- Limit/Skip --
    
    @Override
    public default IntFuncList limit(long maxSize) {
        return derive(streamable -> {
            return streamable.stream().limit(maxSize);
        });
    }
    
    @Override
    public default IntFuncList skip(long n) {
        return derive(streamable -> {
            return streamable.stream().skip(n);
        });
    }
    
    public default IntFuncList skipWhile(IntPredicate condition) {
        return derive(streamable -> {
            return streamable.stream().skipWhile(condition);
        });
    }
    
    public default IntFuncList skipUntil(IntPredicate condition) {
        return derive(streamable -> {
            return streamable.stream().skipUntil(condition);
        });
    }
    
    public default IntFuncList takeWhile(IntPredicate condition) {
        return derive(streamable -> {
            return streamable.stream().takeWhile(condition);
        });
    }
    
    public default IntFuncList takeUntil(IntPredicate condition) {
        return derive(streamable -> {
            return streamable.stream().takeUntil(condition);
        });
    }
    
    @Override
    public default IntFuncList distinct() {
        return derive(streamable -> {
            return streamable.stream().distinct();
        });
    }
    
    @Override
    public default IntFuncList sorted() {
        return derive(streamable -> {
            return streamable.stream().sorted();
        });
    }
    
    public default IntFuncList limit(Long maxSize) {
        if (maxSize == null)
            return this;
        
        return derive(streamable -> {
            return streamable.stream().limit(maxSize);
        });
    }
    
    public default IntFuncList skip(Long startAt) {
        if (startAt == null)
            return this;
        
        return derive(streamable -> {
            return streamable.stream().skip(startAt);
        });
    }
    
    // -- Sorted --
    
    public default IntFuncList sortedBy(
            IntUnaryOperator mapper) {
        return derive(streamable -> {
            return streamable
                    .stream()
                    .sortedBy(mapper);
        });
    }
    
    public default IntFuncList sortedBy(
            IntUnaryOperator       mapper,
            IntBiFunctionPrimitive comparator) {
        return derive(streamable -> {
            return streamable
                    .stream()
                    .sortedBy(mapper, comparator);
        });
    }
    
    public default <T extends Comparable<? super T>> IntFuncList sortedByObj(IntFunction<T> mapper) {
        return derive(streamable -> {
            return streamable
                    .stream()
                    .sortedByObj(mapper);
        });
    }
    
    public default <T> IntFuncList sortedByObj(IntFunction<T> mapper, Comparator<T> comparator) {
        return derive(streamable -> {
            return streamable
                    .stream()
                    .sortedByObj(mapper, comparator);
        });
    }
    
    // -- accumulate --
    
    public default <T> FuncList<Result<T>> spawn(
            IntFunction<? extends UncompletedAction<T>> mapToAction) {
        return deriveToList(streamable -> {
            return streamable.spawn(mapToAction).stream();
        });
    }
    
    public default IntFuncList accumulate(IntBiFunctionPrimitive accumulator) {
        return derive(streamable -> {
            return streamable.stream()
                    .accumulate(accumulator)
                    .stream();
        });
    }
    
    public default IntStreamable restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
        return derive(streamable -> {
            return streamable.stream()
                    .restate(restater)
                    .stream();
        });
    }
    
    // -- Filter --
    
    public default IntFuncList exclude(int value) {
        return derive(streamable -> {
            return streamable.stream().filter(data -> !Objects.equals(value, data));
        });
    }
    
    public default IntFuncList excludeAll(int ... datas) {
        if ((datas == null) || (datas.length == 0))
            return this;
        
        return derive(streamable -> {
            val dataList = IntFuncList.of(datas);
            return streamable.stream().filter(data -> !dataList.contains(data));
        });
    }
    
    // TODO Join
    
    // -- Plus w/ Self --
    // ============================================================================
    
    public default IntFuncList collapse(IntPredicate conditionToCollapse, IntBinaryOperator concatFunc) {
        return derive(streamable -> {
            return stream()
                    .collapse(conditionToCollapse, concatFunc);
        });
    }
    
}
