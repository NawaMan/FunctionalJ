package functionalj.list.intlist;

import static functionalj.lens.Access.$I;
import static functionalj.lens.Access.theInteger;

import java.util.Collection;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.list.FuncListDerived;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.AsStreamable;
import functionalj.stream.IntIterable;
import functionalj.stream.IntIteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import functionalj.stream.intstream.AsIntStreamable;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamPlusHelper;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.intstream.IntStreamableAdditionalTerminalOperators;
import functionalj.stream.intstream.IntStreamableWithCalculate;
import lombok.val;


//TODO - Use this for byte, short and char

public interface IntFuncList
        extends 
            AsIntStreamable,
            IntStreamable, 
            IntIterable,
            IntFuncListWithMapFirst,
            IntFuncListWithMapThen,
            IntFuncListWithMapTuple,
            IntFuncListWithMapToMap,
//          IntFuncListWithSplit,
            IntFuncListWithSegment,
            IntFuncListWithCombine,
            IntStreamableWithCalculate,
//            IntFuncListAdditionalOperations,
            IntStreamableAdditionalTerminalOperators {
    
    public static ImmutableIntFuncList empty() {
        return ImmutableIntFuncList.empty();
    }
    
    public static ImmutableIntFuncList emptyList() {
        return ImmutableIntFuncList.empty();
    }
    
    public static ImmutableIntFuncList emptyIntList() {
        return ImmutableIntFuncList.empty();
    }
    
    public static ImmutableIntFuncList of(int ... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    public static ImmutableIntFuncList AllOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    public static ImmutableIntFuncList ints(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    public static ImmutableIntFuncList intList(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    // TODO - Function to create FuncList from function of Array
    
    public static ImmutableIntFuncList from(int[] datas) {
        return ImmutableIntFuncList.of(datas);
    }
    
    public static ImmutableIntFuncList from(Collection<Integer> data, int valueForNull) {
        IntStream intStream = StreamPlus.from(data.stream())
                .fillNull((Integer)valueForNull)
                .mapToInt($I);
        return ImmutableIntFuncList.from(intStream);
    }
    
    @SafeVarargs
    public static ImmutableIntFuncList ListOf(int ... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    @SafeVarargs
    public static ImmutableIntFuncList listOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    public static IntFuncList from(IntStreamable streamable) {
        return new IntFuncListDerivedFromIntStreamable(streamable);
    }
    
    public static IntFuncList from(IntStream stream) {
        return ImmutableIntFuncList.from(stream);
    }
    
    public static IntFuncList zeroes(int count) {
        return IntFuncList.from(IntStreamable.zeroes(count));
    }
    
    public static IntFuncList ones(int count) {
        return IntFuncList.from(IntStreamable.ones(count));
    }
    
    public static IntFuncList naturalNumbers(int count) {
        return IntFuncList.from(IntStreamable.naturalNumbers().limit(count));
    }
    
    public static IntFuncList wholeNumbers(int count) {
        return IntFuncList.from(IntStreamable.wholeNumbers().limit(count));
    }
    
    public static IntFuncList range(int startInclusive, int endExclusive) {
        return IntFuncList.from(IntStreamable.range(startInclusive, endExclusive));
    }
    
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
    
    // == Stream ==
    
    public IntStreamable intStreamable();
    
    public default Streamable<Integer> streamable() {
        return boxed();
    }
    
    public default IntStreamPlus intStream() {
        return intStreamable().intStream();
    }
    
    public default StreamPlus<Integer> stream() {
        return intStream().boxed();
    }
    
    //== Pipeable ==
    
    public default Pipeable<IntFuncList> pipeable() {
        return Pipeable.of(this);
    }
    
    //== Derive ==
    
    public default IntFuncList derive(IntStreamable streamable) {
        val list = new IntFuncListDerivedFromIntStreamable(streamable);
        val isLazy = isLazy();
        return isLazy ? list : list.toImmutableList();
    }
    
    public default <TARGET> FuncList<TARGET> deriveToList(Streamable<TARGET> streamable) {
        return FuncListDerived.from(streamable);
    }
    
    public default boolean isLazy() {
        return true;
    }
    
    public default boolean isEager() {
        return false;
    }
    
    public IntFuncList lazy();
    
    public IntFuncList eager();
    
    public default ImmutableIntFuncList freeze() {
        return toImmutableList();
    }
    
    public default List<Integer> toJavaList() {
        return FuncList.from(this.boxed());
    }
    
    public default ImmutableIntFuncList toImmutableList() {
        return ImmutableIntFuncList.from(this);
    }
    
    public default int[] toArray() {
        return intStream().toArray();
    }
    
    public default String toListString() {
        return intStream()
                .toListString();
    }
    
    @Override
    public default IntIteratorPlus iterator() {
        return intStream().iterator();
    }
    
    public default FuncList<Integer> boxed() {
        return FuncListDerived.from(()->StreamPlus.from(intStream().mapToObj(theInteger.boxed())));
    }
    
    public default FuncList<Integer> toBoxedList() {
        return FuncList.from(this.boxed());
    }
    
    
    public default boolean isEmpty() {
        return ! iterator().hasNext();
    }
    
    public default boolean contains(int o) {
        return intStream().anyMatch(i -> i == o);
    }
    
    public default boolean containsAllOf(int ... c) {
        return IntStreamPlus
                .of(c)
                .allMatch(each -> intStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(int ... c) {
        return IntStreamPlus
                .of(c)
                .anyMatch(each -> intStream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsAllOf(Collection<Integer> c) {
        return c.stream()
                .allMatch(each -> intStream()
                                    .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(Collection<Integer> c) {
        return c.stream()
                .anyMatch(each -> intStream()
                        .anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default int get(int index) {
        val ref   = new int[1][];
        val found = IntStreamPlusHelper.hasAt(this.intStream(), index, ref);
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
        return derive(() -> {
            return intStream()
                    .mapWithIndex((index, data) -> check.test(data) ? index : -1)
                    .filter(i -> i != -1)
                    ;
        });
    }
    
    public default IntFuncList indexesOf(int value) {
        return derive(() -> {
            return intStream()
                    .mapWithIndex((index, data) -> (data == value) ? index : -1)
                    .filter(i -> i != -1);
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
        return skip(fromIndexInclusive)
                .limit(length);
    }
    
    // -- List specific --
    
    public default OptionalInt first() {
        return intStream()
                .limit(1)
                .findFirst()
                ;
    }
    
    public default IntFuncList first(int count) {
        val streamable = limit(count).intStreamable();
        return derive(streamable);
    }
    
    public default OptionalInt last() {
        return last(1)
                .findFirst()
                ;
    }
    
    public default IntFuncList last(int count) {
        val size   = this.size();
        val offset = Math.max(0, size - count);
        return skip(offset);
    }
    
    
    public default IntFuncList rest() {
        return skip(1);
    }
    
    // Note - Eager
    // We can make it not eager but it will be slow.
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
        return new ImmutableIntFuncList(array, isLazy());
    }
    
    // Note - Eager
    // We can make it not eager but it will be slow.
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
        return new ImmutableIntFuncList(array, isLazy());
    }
////    
////    public default FuncList<IntIntTuple> query(IntPredicate check) {
////        return deriveToList(streamble -> {
////            return streamble
////                    .mapToObjWithIndex((index, data) -> check.test(data) ? IntIntTuple.of(index, data) : null)
////                    .filterNonNull()
////                    .stream();
////        });
////    }
    
    //== Min/Max ==
    
    public default <D extends Comparable<D>> OptionalInt minIndexOf(
            IntPredicate   filter,
            IntUnaryOperator mapper) {
        return intStream()
                .mapWithIndex()
                .map   (t -> t.map2ToInt(mapper))
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.applyAsInt(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty())
                ;
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexOf(
            IntPredicate   filter,
            IntFunction<D> mapper) {
        return intStream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
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
        return intStream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .minBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    public default <D extends Comparable<D>> OptionalInt maxIndexBy(
            IntPredicate   filter,
            IntFunction<D> mapper) {
        return intStream()
                .mapWithIndex()
                .filter(t -> filter.test(t._2))
                .maxBy (t -> mapper.apply(t._2))
                .map   (t -> OptionalInt.of(t._1))
                .orElse(OptionalInt.empty());
    }
    
    // == Modified methods ==
    
    public default IntFuncList append(int value) {
        return derive(() -> IntStreamPlus.concat(intStream(), IntStreamPlus.of(value)));
    }
    
    public default IntFuncList appendAll(int ... values) {
        return derive(() -> IntStreamPlus.concat(intStream(), IntStreamPlus.of(values)));
    }
    public default IntFuncList appendAll(IntStreamable values) {
        return (values == null) 
                ? this 
                : derive(() -> IntStreamPlus.concat(intStream(), IntStreamPlus.from(values.intStream())));
    }
    
    public default IntFuncList appendAll(Supplier<IntStream> supplier) {
        return (supplier == null) 
                ? this 
                : derive(() -> IntStreamPlus.concat(intStream(), IntStreamPlus.from(supplier.get())));
    }
    
    public default IntFuncList prepend(int value) {
        return derive(() -> IntStreamPlus.concat(IntStreamPlus.of(value), intStream()));
    }
    
    public default IntFuncList prependAll(int ... values) {
        return derive(() -> IntStreamPlus.concat(IntStreamPlus.of(values), intStream()));
    }
    
    public default IntFuncList prependAll(IntStreamable prefixStreamable) {
        return (prefixStreamable == null) 
                ? this 
                : derive(() -> IntStreamPlus.concat(prefixStreamable.intStream(), intStream()));
    }
    
    public default IntFuncList prependAll(Supplier<IntStream> supplier) {
        return (supplier == null) 
                ? this 
                : derive(() -> IntStreamPlus.concat(IntStreamPlus.from(supplier.get()), intStream()));
    }
    
    public default IntFuncList with(int index, int value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return derive(() -> {
            val i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? value : each)
                    .intStream(); 
        });
    }
    
    public default IntFuncList with(int index, IntUnaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        return derive(() -> {
            val i = new AtomicInteger();
            return map(each -> (i.getAndIncrement() == index) ? mapper.applyAsInt(each) : each)
                    .intStream(); 
        });
    }
    
    public default IntFuncList insertAt(int index, int ... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        
        return derive(() -> {
                    return IntStreamPlus.concat(
                            intStream().limit(index),
                            IntStreamPlus.of(elements), 
                            intStream().skip(index));
                });
    }
    
    public default IntFuncList insertAllAt(int index, IntStreamable elements) {
        if ((elements == null) || (elements.size() == 0))
            return this;
        
        return derive(() -> {
                    return IntStreamPlus.concat(
                            intStream().limit(index),
                            elements.intStream(), 
                            intStream().skip(index));
                });
    }
    
    public default IntFuncList excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        return derive(() -> {
            return IntStreamPlus.concat(
                    intStream().limit(index), 
                    intStream().skip(index + 1));
        });
    }
    
    public default IntFuncList excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        return derive(() -> {
            return IntStreamPlus.concat(
                    intStream().limit(fromIndexInclusive), 
                    intStream().skip(fromIndexInclusive + count));
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
        
        return derive(() -> {
            return IntStreamPlus.concat(
                    intStream().limit(fromIndexInclusive), 
                    intStream().skip(toIndexExclusive + 1));
        });
    }
    
    public default IntFuncList map(IntUnaryOperator mapper) {
        return derive(() -> {
            return intStream().map(mapper);
        });
    }
    
    public default IntFuncList flatMap(
            IntFunction<? extends AsIntStreamable> mapper) {
        return derive(() -> {
            return intStream().flatMap(item -> mapper.apply(item).intStream());
        });
    }
    
    public default IntFuncList filter(IntPredicate predicate) {
        return derive(() -> {
            return intStream().filter(predicate);
        });
    }
   
    public default IntFuncList peek(IntConsumer action) {
        return derive(() -> {
            return intStream().peek(action);
        });
    }
    
    // == Functionalities ==
//  public default LongStreamPlus mapToLong(IntToLongFunction mapper) {
//      return LongStreamPlus.from(stream().mapToLong(mapper));
//  }
//
//  public default DoubleStreamPlus mapToDouble(IntToDoubleFunction mapper) {
//      return DoubleStreamPlus.from(stream().mapToDouble (mapper));
//  }
    
    public default <TARGET> FuncList<TARGET> mapToObj(IntFunction<? extends TARGET> mapper) {
        return deriveToList(intStreamable().mapToObj(mapper));
    }
    
    public default IntFuncList mapToInt(IntUnaryOperator mapper) {
        return map(mapper);
    }
    
    public default <TARGET> FuncList<TARGET> flatMapToObj(IntFunction<? extends AsStreamable<TARGET>> mapper) {
        return deriveToList(intStreamable().flatMapToObj(mapper));
    }
    
    public default IntFuncList flatMapToInt(IntFunction<? extends AsIntStreamable> mapper) {
        return flatMap(mapper);
    }
    
//  public default LongStreamable flatMapToLong(IntFunction<? extends LongStream> mapper) {
//      return stream()
//              .flatMapToLong(mapper);
//  }
//  
//  public default DoubleStreamable flatMapToDouble(IntFunction<? extends DoubleStream> mapper) {
//      return stream()
//              .flatMapToDouble(mapper);
//  }
    
    public default IntFuncList filter(IntUnaryOperator mapper, IntPredicate predicate) {
        return derive(intStreamable().filter(mapper, predicate));
    }
    
    public default <T> IntFuncList filterAsObject(IntFunction<? extends T> mapper,
            Predicate<? super T> theCondition) {
        return derive(intStreamable().filterAsObject(mapper, theCondition));
    }
    
    public default <T> IntFuncList filterAsObject(Function<Integer, ? extends T> mapper,
            Predicate<? super T> theCondition) {
        return derive(intStreamable().filterAsObject(mapper, theCondition));
    }
    
    // -- Terminate --
    
    public default void forEach(IntConsumer action) {
        if (action == null)
            return;
        
        intStream().forEach(action);
    }
    
    public default void forEachOrdered(IntConsumer action) {
        if (action == null)
            return;
        
        intStream().forEachOrdered(action);
    }
    
    public default int reduce(int identity, IntBinaryOperator accumulator) {
        return intStream().reduce(identity, accumulator);
    }
    
    public default OptionalInt reduce(IntBinaryOperator accumulator) {
        return intStream().reduce(accumulator);
    }
    
    public default <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return intStream().collect(supplier, accumulator, combiner);
    }
    
    public default OptionalInt min() {
        return intStream().min();
    }
    
    public default OptionalInt max() {
        return intStream().max();
    }
    
    public default long count() {
        return intStream().count();
    }
    
    public default int size() {
        return (int) intStream().count();
    }
    
    public default boolean anyMatch(IntPredicate predicate) {
        return intStream().anyMatch(predicate);
    }
    
    public default boolean allMatch(IntPredicate predicate) {
        return intStream().allMatch(predicate);
    }
    
    public default boolean noneMatch(IntPredicate predicate) {
        return intStream().noneMatch(predicate);
    }
    
    public default OptionalInt findFirst() {
        return intStream().findFirst();
    }
    
    public default OptionalInt findAny() {
        return intStream().findAny();
    }
    
    public default int sum() {
        return intStream().sum();
    }
    
    public default OptionalDouble average() {
        return intStream().average();
    }
    
    public default IntSummaryStatistics summaryStatistics() {
        return intStream().summaryStatistics();
    }
    
    // == Plus ==
    
    public default String joinToString() {
        return intStream().joinToString();
    }
    
    public default String joinToString(String delimiter) {
        return intStream().joinToString(delimiter);
    }
    
    // -- Limit/Skip --
    
    public default IntFuncList limit(long maxSize) {
        return derive(() -> intStream().limit(maxSize));
    }
    
    public default IntFuncList skip(long n) {
        return derive(() -> intStream().skip(n));
    }
    
    public default IntFuncList skipWhile(IntPredicate condition) {
        return derive(() -> intStream().skipWhile(condition));
    }
    
    public default IntFuncList skipUntil(IntPredicate condition) {
        return derive(() -> intStream().skipUntil(condition));
    }
    
    public default IntFuncList takeWhile(IntPredicate condition) {
        return derive(() -> intStream().takeWhile(condition));
    }
    
    public default IntFuncList takeUntil(IntPredicate condition) {
        return derive(() -> intStream().takeUntil(condition));
    }
    
    public default IntFuncList distinct() {
        return derive(() -> intStream().distinct());
    }
    
    public default IntFuncList sorted() {
        return derive(() -> intStream().sorted());
    }
    
    public default IntFuncList limit(Long maxSize) {
        if (maxSize == null)
            return this;
        
        return derive(() -> intStream().limit(maxSize));
    }
    
    public default IntFuncList skip(Long startAt) {
        if (startAt == null)
            return this;
        
        return derive(() -> intStream().skip(startAt));
    }
    
    // -- Sorted --
    
    public default IntFuncList sortedBy(
            IntUnaryOperator mapper) {
        return derive(() -> intStream().sortedBy(mapper));
    }
    
    public default IntFuncList sortedBy(
            IntUnaryOperator       mapper,
            IntBiFunctionPrimitive comparator) {
        return derive(() -> intStream().sortedBy(mapper, comparator));
    }
    
    public default <T extends Comparable<? super T>> IntFuncList sortedByObj(IntFunction<T> mapper) {
        return derive(() -> intStream().sortedAs(mapper));
    }
    
    public default <T> IntFuncList sortedByObj(IntFunction<T> mapper, Comparator<T> comparator) {
        return derive(() -> intStream().sortedAs(mapper, comparator));
    }
    
    //== Spawn ==
    
    public default <T> FuncList<Result<T>> spawn(
            IntFunction<? extends UncompletedAction<T>> mapToAction) {
        return deriveToList(() -> {
            return intStream().spawn(mapToAction);
        });
    }
    
    public default IntFuncList accumulate(IntBiFunctionPrimitive accumulator) {
        return derive(() -> intStream().accumulate(accumulator));
    }
    
    public default IntFuncList restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
        return derive(() -> intStream().restate(restater));
    }
    
    // -- Filter --
    
    public default IntFuncList exclude(int value) {
        return derive(() -> intStream().filter(data -> !Objects.equals(value, data)));
    }
    
    public default IntFuncList excludeAll(int ... datas) {
        if ((datas == null) || (datas.length == 0))
            return this;
        
        return derive(() -> {
            val dataList = IntFuncList.of(datas);
            return intStream().filter(data -> !dataList.contains(data));
        });
    }
    
    @Override
    default IntFuncList exclude(IntPredicate predicate) {
        return derive(() -> {
            return intStream().filter(data -> !predicate.test(data));
        });
    }
//    
//    @Override
//    default IntFuncList mapOnly(IntPredicate checker, IntUnaryOperator mapper) {
//        return IntFuncListAdditionalOperations.super.mapOnly(checker, mapper);
//    }
//    
//    @Override
//    default IntFuncList mapIf(IntPredicate checker, IntUnaryOperator mapper, IntUnaryOperator elseMapper) {
//        return derive(() -> {
//            return intStream().mapIf(checker, mapper, elseMapper);
//        });
//    }
//    
//    @Override
//    default <T> FuncList<T> mapToObjIf(IntPredicate checker, IntFunction<T> mapper, IntFunction<T> elseMapper) {
//        return IntFuncListAdditionalOperations.super.mapToObjIf(checker, mapper, elseMapper);
//    }
//
//    @Override
//    default IntFuncList peek(IntPredicate selector, IntConsumer theConsumer) {
//        return IntFuncListAdditionalOperations.super.peek(selector, theConsumer);
//    }
//
//    @Override
//    default IntFuncList collapseAfter(IntPredicate conditionToCollapseNext, IntBinaryOperator concatFunc) {
//        // TODO Auto-generated method stub
//        return IntFuncListWithSegment.super.collapseAfter(conditionToCollapseNext, concatFunc);
//    }
//
//    @Override
//    default FuncList<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize) {
//        // TODO Auto-generated method stub
//        return IntFuncListWithSegment.super.segmentSize(segmentSize);
//    }
//
//    @Override
//    default FuncList<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize, IncompletedSegment incompletedSegment) {
//        // TODO Auto-generated method stub
//        return IntFuncListWithSegment.super.segmentSize(segmentSize, incompletedSegment);
//    }
//
//    @Override
//    default FuncList<IntStreamPlus> segmentSize(IntUnaryOperator segmentSize, boolean includeTail) {
//        // TODO Auto-generated method stub
//        return IntFuncListWithSegment.super.segmentSize(segmentSize, includeTail);
//    }
//
//    @Override
//    default IntFuncList collapseSize(IntUnaryOperator segmentSize, IntBinaryOperator concatFunc) {
//        // TODO Auto-generated method stub
//        return IntFuncListWithSegment.super.collapseSize(segmentSize, concatFunc);
//    }
//
//    @Override
//    default IntFuncList collapseSize(IntUnaryOperator segmentSize, IntBinaryOperator concatFunc, IncompletedSegment incompletedSegment) {
//        // TODO Auto-generated method stub
//        return IntFuncListWithSegment.super.collapseSize(segmentSize, concatFunc, incompletedSegment);
//    }
//
//    @Override
//    default IntFuncList collapseSize(IntUnaryOperator segmentSize, IntBinaryOperator concatFunc, boolean includeTail) {
//        // TODO Auto-generated method stub
//        return IntFuncListWithSegment.super.collapseSize(segmentSize, concatFunc, includeTail);
//    }
    
}
