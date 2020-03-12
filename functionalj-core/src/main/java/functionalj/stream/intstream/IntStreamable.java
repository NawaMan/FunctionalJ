package functionalj.stream.intstream;

import static functionalj.lens.Access.theInteger;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.list.intlist.IntFuncList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.AsStreamable;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import functionalj.stream.longstream.LongStreamable;
import functionalj.tuple.IntIntTuple;
import lombok.val;


public interface IntStreamable 
        extends
            AsIntStreamable,
            IntStreamableWithMapFirst,
            IntStreamableWithMapThen,
            IntStreamableWithMapTuple,
            IntStreamableWithMapToMap,
//            StreamPlusWithSplit,
            IntStreamableWithSegment,
            IntStreamableWithCombine,
            IntStreamableWithCalculate,
            IntStreamableAddtionalOperators,
            IntStreamableAdditionalTerminalOperators {
    
    public static IntStreamable empty() {
        return ()->IntStreamPlus.empty();
    }
    
    public static IntStreamable emptyIntStreamable() {
        return ()->IntStreamPlus.empty();
    }
    
    public static IntStreamable of(int ... data) {
        if (data == null) {
            return empty();
        }
        
        val length = data.length;
        val ints   = new int[length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->IntStreamPlus.of(ints);
    }
    
    public static IntStreamable steamableOf(int ... data) {
        return of(data);
    }
    
    public static IntStreamable ints(int ... data) {
        return of(data);
    }
    
    public static IntStreamable zeroes() {
        return IntStreamable.generate(()->0);
    }
    
    public static IntStreamable zeroes(int count) {
        return IntStreamable.generate(()->0).limit(count);
    }
    
    public static IntStreamable ones() {
        return IntStreamable.generate(()->1);
    }
    
    public static IntStreamable ones(int count) {
        return IntStreamable.generate(()->1).limit(count);
    }
    
    public static IntStreamable repeat(int ... data) {
        val length = data.length;
        val ints   = new int[data.length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->{
            val flatten 
                    = Stream
                    .generate    (() -> IntStreamPlus.of(ints))
                    .flatMapToInt(s -> s);
            return IntStreamPlus.from(flatten);
        };
    }
    
    public static IntStreamable cycle(int ... data) {
        return IntStreamable.repeat(data);
    }
    
    public static IntStreamable loop(int time) {
        return ()->IntStreamPlus.range(0, time);
    }
    
    public static IntStreamable loop() {
        return ()->IntStreamPlus.range(0, Integer.MAX_VALUE);
    }
    
    public static IntStreamable infinite() {
        return ()->IntStreamPlus.range(0, Integer.MAX_VALUE);
    }
    
    public static IntStreamable infiniteInt() {
        return infinite();
    }
    
    public static IntStreamable naturalNumbers() {
        return ()->IntStreamPlus.naturalNumbers();
    }
    
    public static IntStreamable wholeNumbers() {
        return ()->IntStreamPlus.wholeNumbers();
    }
    
    public static IntStreamable naturalNumbers(int count) {
        return naturalNumbers().limit(count);
    }
    
    public static IntStreamable wholeNumbers(int count) {
        return wholeNumbers().limit(count);
    }
    
    public static IntStreamable range(int startInclusive, int endExclusive) {
        return ()->IntStreamPlus.range(startInclusive, endExclusive);
    }
    
    public static IntStreamable generate(IntSupplier intSupplier) {
        return ()->IntStreamPlus.generate(intSupplier);
    }
    
    public static IntStreamable generateWith(Supplier<IntStream> streamSupplier) {
        return ()->StreamPlus.of(streamSupplier).flatMapToInt(s -> s.get());
    }
    
    public static IntStreamable iterate(int seed, IntUnaryOperator f) {
        return ()->IntStreamPlus.iterate(seed, f);
    }
    
    public static IntStreamable iterate(int seed1, int seed2, IntBinaryOperator f) {
        return ()->IntStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static IntStreamable concat(IntStreamable ... streamables) {
        return ()->StreamPlus.of(streamables).flatMapToInt(s -> s.intStream());
    }
    
    public static IntStreamable compound(int seed, IntUnaryOperator f) {
        return ()->IntStreamPlus.compound(seed, f);
    }
    
    public static IntStreamable compound(int seed1, int seed2, IntBinaryOperator f) {
        return ()->IntStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static Streamable<IntIntTuple> zipOf(
            IntStreamable streamable1, 
            IntStreamable streamable2) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream());
        };
    }
    public static StreamPlus<IntIntTuple> zipOf(
            IntStreamable streamable1, 
            IntStreamable streamable2,
            int           defaultValue) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    defaultValue);
        };
    }
    public static StreamPlus<IntIntTuple> zipOf(
            IntStreamable streamable1, int defaultValue1,
            IntStreamable streamable2, int defaultValue2) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(), defaultValue1,
                    streamable2.intStream(), defaultValue2);
        };
    }
    
    public static IntStreamable zipOf(
            IntStreamable          streamable1, 
            IntStreamable          streamable2,
            IntBiFunctionPrimitive merger) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    merger);
        };
    }
    public static IntStreamPlus zipOf(
            IntStreamable          streamable1, 
            IntStreamable          streamable2,
            int                    defaultValue,
            IntBiFunctionPrimitive merger) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(),
                    streamable2.intStream(),
                    defaultValue,
                    merger);
        };
    }
    public static IntStreamPlus zipOf(
            IntStreamable streamable1, int defaultValue1,
            IntStreamable streamable2, int defaultValue2,
            IntBiFunctionPrimitive merger) {
        return ()->{
            return IntStreamPlus.zipOf(
                    streamable1.intStream(), defaultValue1,
                    streamable2.intStream(), defaultValue2,
                    merger);
        };
    }
    
    public static IntStreamable from(
            IntStreamable                      source,
            Function<IntStreamable, IntStream> action) {
        return new IntStreamable() {
            @Override
            public IntStreamPlus intStream() {
                val targetStream = action.apply(source);
                return IntStreamPlus.from(targetStream);
            }
        };
    }
    
    //== Stream ==
    
    public default IntStreamable intStreamable() {
        return this;
    }
    
    public default Streamable<Integer> streamable() {
        return boxed();
    }
    
    public IntStreamPlus intStream();
    
    public default StreamPlus<Integer> stream() {
        return intStream().boxed();
    }
    
    //== Pipeable ==
    
    public default Pipeable<? extends IntStreamable> pipeable() {
        return Pipeable.of(this);
    }
    
    //== Helper functions ==
    
    public default IntStreamable derive(
                Function<IntStreamable, IntStream> action) {
        return new IntStreamable() {
            @Override
            public IntStreamPlus intStream() {
                val targetStream = action.apply(IntStreamable.this);
                return IntStreamPlus.from(targetStream);
            }
        };
    }
    
    public default <TARGET> Streamable<TARGET> deriveToStreamable(
                IntStreamable                           source, 
                Function<IntStreamable, Stream<TARGET>> action) {
        return new Streamable<TARGET>() {
            @Override
            public StreamPlus<TARGET> stream() {
                val targetStream = action.apply(source);
                return StreamPlus.from(targetStream);
            }
        };
    }
    
    public default LongStreamable asLongStreamable() {
        return LongStreamable.from(stream().mapToLong(i -> (long)i));
    }
    
//    @Override
//    public default DoubleStreamable asDoubleStream() {
//        return DoubleStreamPlus.from(stream().asDoubleStream());
//    }
    
    public default Streamable<Integer> boxed() {
        return ()->StreamPlus.from(intStream().mapToObj(theInteger.boxed()));
    }
    
    public default PrimitiveIterator.OfInt iterator() {
        return intStream().iterator();
    }
    
    public default Spliterator.OfInt spliterator() {
        return intStream().spliterator();
    }
    
    //== Stream specific ==
    
    public default IntStreamable sequential() {
        return from(this, streamble -> streamble.intStream().sequential());
    }
    
    public default IntStreamable parallel() {
        return from(this, streamble -> streamble.intStream().parallel());
    }
    
    public default IntStreamable unordered() {
        return from(this, streamble -> streamble.intStream().unordered());
    }
    
    public default boolean isParallel() {
        return intStream().isParallel();
    }
    
    //== Functionalities ==
    
    public default IntStreamable map(IntUnaryOperator mapper) {
        return from(this, streamble -> streamble.intStream().map(mapper));
    }
    
//    public default LongStreamPlus mapToLong(IntToLongFunction mapper) {
//        return LongStreamPlus.from(stream().mapToLong(mapper));
//    }
//  
//    public default DoubleStreamPlus mapToDouble(IntToDoubleFunction mapper) {
//        return DoubleStreamPlus.from(stream().mapToDouble (mapper));
//    }
    
    public default <TARGET> Streamable<TARGET> mapToObj(IntFunction<? extends TARGET> mapper) {
        return Streamable.from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .mapToObj(mapper));
    }
    
    public default IntStreamable mapToInt(IntUnaryOperator mapper) {
        return map(mapper);
    }
    
    public default <TARGET> Streamable<TARGET> flatMapToObj(IntFunction<? extends AsStreamable<TARGET>> mapper) {
        return Streamable.from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .flatMapToObj(each -> mapper.apply(each).stream()));
    }
    
    public default IntStreamable flatMapToInt(IntFunction<? extends AsIntStreamable> mapper) {
        return flatMap(mapper);
    }
    
//    public default LongStreamable flatMapToLong(IntFunction<? extends LongStream> mapper) {
//        return stream()
//                .flatMapToLong(mapper);
//    }
//    
//    public default DoubleStreamable flatMapToDouble(IntFunction<? extends DoubleStream> mapper) {
//        return stream()
//                .flatMapToDouble(mapper);
//    }
    
    public default IntStreamable flatMap(IntFunction<? extends AsIntStreamable> mapper) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .flatMap(each -> mapper.apply(each).intStream()));
    }
    
    public default IntStreamable filter(IntPredicate predicate) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .filter(predicate));
    }
    
    // TODO - Rename all filter with map to filter
    public default IntStreamable filter(
            IntUnaryOperator mapper, 
            IntPredicate     predicate) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .filter(mapper, predicate));
    }
    
    public default <T> IntStreamable filterAsObject(
            IntFunction<? extends T> mapper,
            Predicate<? super T>     theCondition) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .filterAsObject(mapper, theCondition));
    }
    
    public default <T> IntStreamable filterAsObject(
            Function<Integer, ? extends T> mapper,
            Predicate<? super T>     theCondition) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .filterAsObject(mapper, theCondition));
    }
    
    public default IntStreamable peek(IntConsumer action) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .peek(action));
    }
    
    public default IntStreamable limit(long maxSize) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .limit(maxSize));
    }
    
    public default IntStreamable skip(long offset) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .skip(offset));
    }
    
    public default IntStreamable skipWhile(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .skipWhile(condition));
    }
    
    public default IntStreamable skipUntil(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .skipUntil(condition));
    }
    
    public default IntStreamable takeWhile(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .takeWhile(condition));
    }
    
    public default IntStreamable takeUntil(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .takeUntil(condition));
    }
    
    public default IntStreamable distinct() {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .distinct());
    }
    
    //-- Sorted --
    
    public default IntStreamable sorted() {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .sorted());
    }
    
    public default IntStreamable sortedBy(
            IntUnaryOperator mapper) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .sortedBy(mapper));
    }
    
    public default IntStreamable sortedBy(
            IntUnaryOperator       mapper,
            IntBiFunctionPrimitive comparator) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .sortedBy(mapper, comparator));
    }
    
    public default <T extends Comparable<? super T>> IntStreamable sortedByObj(
            IntFunction<T> mapper) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .sortedByObj(mapper));
    }
    
    public default <T> IntStreamable sortedByObj(
            IntFunction<T> mapper, 
            Comparator<T>  comparator) {
        return from(this, 
                streamable -> 
                    streamable
                    .intStream()
                    .sortedByObj(mapper, comparator));
    }
    
    //-- Terminate --
    
    public default void forEach(IntConsumer action) {
        if (action == null)
            return;
        
        intStream()
        .forEach(action);
    }
    
    public default void forEachOrdered(IntConsumer action) {
        if (action == null)
            return;
        
        intStream()
        .forEachOrdered(action);
    }
    
    public default int reduce(int identity, IntBinaryOperator accumulator) {
        return intStream()
                .reduce(identity, accumulator);
    }
    
    public default OptionalInt reduce(IntBinaryOperator accumulator) {
        return intStream()
                .reduce(accumulator);
    }
    
    public default <R> R collect(
            Supplier<R>       supplier,
            ObjIntConsumer<R> accumulator,
            BiConsumer<R, R>  combiner) {
        return intStream()
                .collect(supplier, accumulator, combiner);
    }
    
    public default OptionalInt min() {
        return intStream()
                .min();
    }
    
    public default OptionalInt max() {
        return intStream()
                .max();
    }
    
    public default long count() {
        return intStream().count();
    }
    
    public default int size() {
        return (int)intStream().count();
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
    
    //== toXXX ==
    
    public default int[] toArray() {
        return intStream()
                .toArray();
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
    
    public default String toListString() {
        return intStream()
                .toListString();
    }
    
    public default IntFuncList toList() {
        return IntFuncList.from(this);
    }
    
    public default FuncList<Integer> toBoxedList() {
        return FuncList.from(this.boxed());
    }
    
    public default ImmutableIntFuncList toImmutableList() {
        return intStream()
                .toImmutableList();
    }
    
    //== Plus ==
    
    public default String joinToString() {
        return intStream()
                .joinToString();
    }
    
    public default String joinToString(String delimiter) {
        return intStream()
                .joinToString(delimiter);
    }
    
    //== Pipe ==
    
    public default <T> Pipeable<? extends IntStreamable> pipable() {
        return Pipeable.of(this);
    }
    
    //== Spawn ==
    
    public default <T> Streamable<Result<T>> spawn(
            IntFunction<? extends UncompletedAction<T>> mapToAction) {
        return ()->
                intStream()
                .spawn(mapToAction);
    }
    
    public default IntStreamable accumulate(IntBiFunctionPrimitive accumulator) {
        return ()->
                intStream()
                .accumulate(accumulator);
    }
    
    public default IntStreamable restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
        return ()->
                intStream()
                .restate(restater);
    }
}
