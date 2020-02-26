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
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.list.intlist.ImmutableIntList;
import functionalj.list.intlist.IntFuncList;
import functionalj.list.intlist.IntFuncListDerivedFromIntStreamable;
import functionalj.pipeable.Pipeable;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.stream.DoubleStreamPlus;
import functionalj.stream.LongStreamPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import functionalj.tuple.IntIntTuple;
import lombok.val;


public interface IntStreamable 
        extends
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
        val length = data.length;
        val ints   = new int[data.length];
        System.arraycopy(data, 0, ints, 0, length);
        return ()->IntStreamPlus.of(ints);
    }
    
    public static IntStreamable steamableOf(int ... data) {
        return of(data);
    }
    
    public static IntStreamable ints(int ... data) {
        return of(data);
    }
    
    public static IntStreamable from(IntSupplier supplier) {
        return ()->IntStreamPlus.generate(supplier);
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
    
    public static IntStreamable concat(IntStream ... streams) {
        return ()->StreamPlus.of(streams).flatMapToInt(s -> s);
    }
    public static IntStreamable combine(IntStream ... streams) {
        return ()->StreamPlus.of(streams).flatMapToInt(s -> s);
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
    
    public static IntStreamable compound(int seed, IntUnaryOperator f) {
        return ()->IntStreamPlus.compound(seed, f);
    }
    
    public static IntStreamable iterate(int seed1, int seed2, IntBinaryOperator f) {
        return ()->IntStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static IntStreamable compound(int seed1, int seed2, IntBinaryOperator f) {
        return ()->IntStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static Streamable<IntIntTuple> zipOf(
            IntStreamable stream1, 
            IntStreamable stream2) {
        return ()->{
            return IntStreamPlus.zipOf(
                    stream1.stream(),
                    stream2.stream());
        };
    }
    
    public static Streamable<IntIntTuple> zipOf(
            IntStreamable stream1, 
            IntStreamable stream2,
            int           defaultValue) {
        return ()->{
            return IntStreamPlus.zipOf(
                    stream1.stream(),
                    stream2.stream(),
                    defaultValue);
        };
    }
    
    public static <T> Streamable<T> zipToObjOf(
            IntStreamable       stream1, 
            IntStreamable       stream2,
            IntIntBiFunction<T> merger,
            int                 defaultValue) {
        return ()->{
            return StreamPlus.zipOf(
                    stream1.stream(),
                    stream2.stream(),
                    merger,
                    defaultValue);
        };
    }
    
    public static IntStreamable from(
            IntStreamable                      source,
            Function<IntStreamable, IntStream> action) {
        return new IntStreamable() {
            @Override
            public IntStreamPlus stream() {
                val targetStream = action.apply(source);
                return IntStreamPlus.from(targetStream);
            }
        };
    }
    
    //== Stream ==
    
    public IntStreamPlus stream();
    
    public default IntStreamable streamable() {
        return this;
    }
    
    
    //== Helper functions ==
    
    public default IntStreamable derive(
                Function<IntStreamable, IntStream> action) {
        return new IntStreamable() {
            @Override
            public IntStreamPlus stream() {
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
    
//    IntStreamable                  source, 
//    Function<IntStream, Stream<D>> action) {
    
//    @Override
//    public default LongStreamable asLongStream() {
//        return LongStreamPlus.from(stream().asLongStream());
//    }
    
//    @Override
//    public default DoubleStreamable asDoubleStream() {
//        return DoubleStreamPlus.from(stream().asDoubleStream());
//    }
    
    public default Streamable<Integer> boxed() {
        return ()->StreamPlus.from(stream().mapToObj(theInteger.boxed()));
    }
    
    public default PrimitiveIterator.OfInt iterator() {
        return stream().iterator();
    }
    
    public default Spliterator.OfInt spliterator() {
        return stream().spliterator();
    }
    
    //== Stream specific ==
    
    public default IntStreamable sequential() {
        return from(this, streamble -> streamble.stream().sequential());
    }
    
    public default IntStreamable parallel() {
        return from(this, streamble -> streamble.stream().parallel());
    }
    
    public default IntStreamable unordered() {
        return from(this, streamble -> streamble.stream().unordered());
    }
    
    public default boolean isParallel() {
        return stream().isParallel();
    }
    
    //== Functionalities ==
    
    public default IntStreamable map(IntUnaryOperator mapper) {
        return from(this, streamble -> streamble.stream().map(mapper));
    }
    
    public default LongStreamPlus mapToLong(IntToLongFunction mapper) {
        return LongStreamPlus.from(stream().mapToLong(mapper));
    }
  
    public default DoubleStreamPlus mapToDouble(IntToDoubleFunction mapper) {
        return DoubleStreamPlus.from(stream().mapToDouble (mapper));
    }
    
    public default <TARGET> Streamable<TARGET> mapToObj(IntFunction<? extends TARGET> mapper) {
        return Streamable.from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .mapToObj(mapper));
    }
    
    public default IntStreamable mapToInt(IntUnaryOperator mapper) {
        return map(mapper);
    }
    
    public default <TARGET> Streamable<TARGET> flatMapToObj(IntFunction<? extends Stream<TARGET>> mapper) {
        return Streamable.from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .flatMapToObj(mapper));
    }
    
    public default IntStreamable flatMapToInt(IntFunction<? extends IntStream> mapper) {
        return flatMap(mapper);
    }
//    
//    public default LongStreamable flatMapToLong(IntFunction<? extends LongStream> mapper) {
//        return stream()
//                .flatMapToLong(mapper);
//    }
//    
//    public default DoubleStreamable flatMapToDouble(IntFunction<? extends DoubleStream> mapper) {
//        return stream()
//                .flatMapToDouble(mapper);
//    }
    
    public default IntStreamable flatMap(IntFunction<? extends IntStream> mapper) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .flatMap(mapper));
    }
    
    public default IntStreamable filter(IntPredicate predicate) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .filter(predicate));
    }
    
    public default IntStreamable peek(IntConsumer action) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .peek(action));
    }
    
    public default IntStreamable limit(long maxSize) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .limit(maxSize));
    }
    
    public default IntStreamable skip(long offset) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .skip(offset));
    }
    
    public default IntStreamable skipWhile(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .skipWhile(condition));
    }
    
    public default IntStreamable skipUntil(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .skipUntil(condition));
    }
    
    public default IntStreamable takeWhile(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .takeWhile(condition));
    }
    
    public default IntStreamable takeUntil(IntPredicate condition) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .takeUntil(condition));
    }
    
    public default IntStreamable distinct() {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .distinct());
    }
    
    //-- Sorted --
    
    public default IntStreamable sorted() {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .sorted());
    }
    
    public default IntStreamable sortedBy(
            IntUnaryOperator mapper) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .sortedBy(mapper));
    }
    
    public default IntStreamable sortedBy(
            IntUnaryOperator       mapper,
            IntBiFunctionPrimitive comparator) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .sortedBy(mapper, comparator));
    }
    
    public default <T extends Comparable<? super T>> IntStreamable sortedByObj(
            IntFunction<T> mapper) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .sortedByObj(mapper));
    }
    
    public default <T> IntStreamable sortedByObj(
            IntFunction<T> mapper, 
            Comparator<T>  comparator) {
        return from(this, 
                streamable -> 
                    streamable
                    .stream()
                    .sortedByObj(mapper, comparator));
    }
    
    //-- Terminate --
    
    public default void forEach(IntConsumer action) {
        if (action == null)
            return;
        
        stream()
        .forEach(action);
    }
    
    public default void forEachOrdered(IntConsumer action) {
        if (action == null)
            return;
        
        stream()
        .forEachOrdered(action);
    }
    
    public default int reduce(int identity, IntBinaryOperator accumulator) {
        return stream()
                .reduce(identity, accumulator);
    }
    
    public default OptionalInt reduce(IntBinaryOperator accumulator) {
        return stream()
                .reduce(accumulator);
    }
    
    public default <R> R reduce(
            Supplier<R>       supplier,
            ObjIntConsumer<R> accumulator,
            BiConsumer<R, R>  combiner) {
        return stream()
                .collect(supplier, accumulator, combiner);
    }
    
    public default OptionalInt min() {
        return stream()
                .min();
    }
    
    public default OptionalInt max() {
        return stream()
                .max();
    }
    
    public default long count() {
        return stream().count();
    }
    
    public default int size() {
        return (int)stream().count();
    }
    
    public default boolean anyMatch(IntPredicate predicate) {
        return stream().anyMatch(predicate);
    }
    
    public default boolean allMatch(IntPredicate predicate) {
        return stream().allMatch(predicate);
    }
    
    public default boolean noneMatch(IntPredicate predicate) {
        return stream().noneMatch(predicate);
    }
    
    public default OptionalInt findFirst() {
        return stream().findFirst();
    }
    
    public default OptionalInt findAny() {
        return stream().findAny();
    }
    
    //== toXXX ==
    
    public default IntStreamable intStreamable() {
        return this;
    }
    
    public default Streamable<Integer> asStreamable() {
        return boxed();
    }
    
    public default int[] toArray() {
        return stream()
                .toArray();
    }
    
    public default int sum() {
        return stream().sum();
    }
    
    public default OptionalDouble average() {
        return stream().average();
    }
    
    public default IntSummaryStatistics summaryStatistics() {
        return stream().summaryStatistics();
    }
    
    public default String toListString() {
        return stream()
                .toListString();
    }
    
    public default IntFuncList toList() {
        return new IntFuncListDerivedFromIntStreamable(this, streamable -> streamable.stream());
    }
    
    public default FuncList<Integer> toBoxedList() {
        return FuncList.from(this.boxed());
    }
    
    public default ImmutableIntList toImmutableList() {
        return stream()
                .toImmutableList();
    }
    
    //== Plus ==
    
    public default String joinToString() {
        return stream()
                .joinToString();
    }
    
    public default String joinToString(String delimiter) {
        return stream()
                .joinToString(delimiter);
    }
    
    //== Pipe ==
    
    public default <T> Pipeable<IntStreamable> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super IntStreamable, T> piper) {
        return piper.apply(this);
    }
    
    //== Spawn ==
    
    public default <T> Streamable<Result<T>> spawn(
            IntFunction<? extends UncompletedAction<T>> mapToAction) {
        return ()->
                stream()
                .spawn(mapToAction);
    }
    
    public default IntStreamable accumulate(IntBiFunctionPrimitive accumulator) {
        return ()->
                stream()
                .accumulate(accumulator);
    }
    
    public default IntStreamable restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
        return ()->
                stream()
                .restate(restater);
    }
    
    
    
    
    
    
    
    
    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    //== Additional functionalities
////    
////    public default Streamable<IntStreamPlus> segment(
////                    int count) {
////        return with(
////                this, 
////                stream 
////                    -> IntStreamPlus
////                    .from(stream)
////                    .segment(count));
////    }
////    public default Streamable<IntStreamPlus> segment(
////                    int     count, 
////                    boolean includeTail) {
////        return with(
////                this, 
////                stream 
////                    -> IntStreamPlus
////                    .from(stream)
////                    .segment(count, includeTail));
////    }
////    public default Streamable<IntStreamPlus> segment(
////                    IntPredicate startCondition) {
////        return with(
////                this, 
////                stream 
////                    -> IntStreamPlus
////                    .from(stream)
////                    .segment(startCondition));
////    }
////    public default Streamable<IntStreamPlus> segment(
////                    IntPredicate startCondition, 
////                    boolean      includeTail) {
////        return with(
////                this, 
////                stream 
////                    -> IntStreamPlus
////                    .from(stream)
////                    .segment(startCondition, includeTail));
////    }
////    public default Streamable<IntStreamPlus> segment(
////                    IntPredicate startCondition, 
////                    IntPredicate endCondition) {
////        return with(
////                this, 
////                stream 
////                    -> IntStreamPlus
////                    .from(stream)
////                    .segment(startCondition, endCondition));
////    }
////    public default Streamable<IntStreamPlus> segment(
////                    IntPredicate startCondition, 
////                    IntPredicate endCondition, 
////                    boolean      includeTail) {
////        return with(
////                this, 
////                stream 
////                    -> IntStreamPlus
////                    .from(stream)
////                    .segment(startCondition, endCondition, includeTail));
////    }
//    
//    public default List<Integer> toList() {
//        return boxed()
//                .toJavaList();
//    }
//    
//    public default FuncList<Integer> toFuncList() {
//        return boxed()
//                .toImmutableList();
//    }
//    public default List<Integer> toMutableList() {
//        return boxed()
//                .toMutableList();
//    }
//    
//    public default ArrayList<Integer> toArrayList() {
//        return boxed()
//                .toArrayList();
//    }
//    
//    public default Set<Integer> toSet() {
//        return boxed()
//                .toSet();
//    }
//    
//    //== Plus ==
//    
//    public default String joining() {
//        return stream()
//                .mapToObj(StrFuncs::toStr)
//                .collect(Collectors.joining());
//    }
//    public default String joining(String delimiter) {
//        return stream()
//                .mapToObj(StrFuncs::toStr)
//                .collect(Collectors.joining(delimiter));
//    }
//    
//    //== Calculate ==
//    
//    public default <T> T calculate(
//            IntStreamProcessor<T> processor) {
//        val streamable = streamable();
//        val collected  = Collected.ofInt(processor, streamable);
//        forEach(each -> {
//            collected.accumulate(each);
//        });
//        val value = collected.finish();
//        return value;
//    }
//    
//    public default <T1, T2> Tuple2<T1, T2> calculate(
//            IntStreamProcessor<T1> processor1, 
//            IntStreamProcessor<T2> processor2) {
//        val streamable = streamable();
//        val collected1 = Collected.ofInt(processor1, streamable);
//        val collected2 = Collected.ofInt(processor2, streamable);
//        forEach(each -> {
//            collected1.accumulate(each);
//            collected2.accumulate(each);
//        });
//        val value1 = collected1.finish();
//        val value2 = collected2.finish();
//        return Tuple.of(value1, value2);
//    }
//    
//    public default <T1, T2, T3> Tuple3<T1, T2, T3> calculate(
//            IntStreamProcessor<T1> processor1, 
//            IntStreamProcessor<T2> processor2, 
//            IntStreamProcessor<T3> processor3) {
//        val streamable = streamable();
//        val collected1 = Collected.ofInt(processor1, streamable);
//        val collected2 = Collected.ofInt(processor2, streamable);
//        val collected3 = Collected.ofInt(processor3, streamable);
//        forEach(each -> {
//            collected1.accumulate(each);
//            collected2.accumulate(each);
//            collected3.accumulate(each);
//        });
//        val value1 = collected1.finish();
//        val value2 = collected2.finish();
//        val value3 = collected3.finish();
//        return Tuple.of(value1, value2, value3);
//    }
//    
//    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> calculate(
//            IntStreamProcessor<T1> processor1, 
//            IntStreamProcessor<T2> processor2, 
//            IntStreamProcessor<T3> processor3, 
//            IntStreamProcessor<T4> processor4) {
//        val streamable = streamable();
//        val collected1 = Collected.ofInt(processor1, streamable);
//        val collected2 = Collected.ofInt(processor2, streamable);
//        val collected3 = Collected.ofInt(processor3, streamable);
//        val collected4 = Collected.ofInt(processor4, streamable);
//        forEach(each -> {
//            collected1.accumulate(each);
//            collected2.accumulate(each);
//            collected3.accumulate(each);
//            collected4.accumulate(each);
//        });
//        val value1 = collected1.finish();
//        val value2 = collected2.finish();
//        val value3 = collected3.finish();
//        val value4 = collected4.finish();
//        return Tuple.of(value1, value2, value3, value4);
//    }
//    
//    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> calculate(
//            IntStreamProcessor<T1> processor1, 
//            IntStreamProcessor<T2> processor2, 
//            IntStreamProcessor<T3> processor3, 
//            IntStreamProcessor<T4> processor4, 
//            IntStreamProcessor<T5> processor5) {
//        val streamable = streamable();
//        val collected1 = Collected.ofInt(processor1, streamable);
//        val collected2 = Collected.ofInt(processor2, streamable);
//        val collected3 = Collected.ofInt(processor3, streamable);
//        val collected4 = Collected.ofInt(processor4, streamable);
//        val collected5 = Collected.ofInt(processor5, streamable);
//        forEach(each -> {
//            collected1.accumulate(each);
//            collected2.accumulate(each);
//            collected3.accumulate(each);
//            collected4.accumulate(each);
//            collected5.accumulate(each);
//        });
//        val value1 = collected1.finish();
//        val value2 = collected2.finish();
//        val value3 = collected3.finish();
//        val value4 = collected4.finish();
//        val value5 = collected5.finish();
//        return Tuple.of(value1, value2, value3, value4, value5);
//    }
//    
//    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> calculate(
//            IntStreamProcessor<T1> processor1, 
//            IntStreamProcessor<T2> processor2, 
//            IntStreamProcessor<T3> processor3, 
//            IntStreamProcessor<T4> processor4, 
//            IntStreamProcessor<T5> processor5, 
//            IntStreamProcessor<T6> processor6) {
//        val streamable = streamable();
//        val collected1 = Collected.ofInt(processor1, streamable);
//        val collected2 = Collected.ofInt(processor2, streamable);
//        val collected3 = Collected.ofInt(processor3, streamable);
//        val collected4 = Collected.ofInt(processor4, streamable);
//        val collected5 = Collected.ofInt(processor5, streamable);
//        val collected6 = Collected.ofInt(processor6, streamable);
//        forEach(each -> {
//            collected1.accumulate(each);
//            collected2.accumulate(each);
//            collected3.accumulate(each);
//            collected4.accumulate(each);
//            collected5.accumulate(each);
//            collected6.accumulate(each);
//        });
//        val value1 = collected1.finish();
//        val value2 = collected2.finish();
//        val value3 = collected3.finish();
//        val value4 = collected4.finish();
//        val value5 = collected5.finish();
//        val value6 = collected6.finish();
//        return Tuple.of(value1, value2, value3, value4, value5, value6);
//    }
//    
////    
////    public default <R, A> R collect(
////            Collector<? super DATA, A, R> collector) {
////        return stream().collect(collector);
////    }
////    
////    public default <R> R collect(
////            Supplier<R>                 supplier,
////            BiConsumer<R, ? super DATA> accumulator,
////            BiConsumer<R, R>            combiner) {
////        return stream().collect(supplier, accumulator, combiner);
////    }
////    
////    //== toXXX ===
////    
////    public default <T> T[] toArray(T[] a) {
////        return StreamPlus.of(stream()).toJavaList().toArray(a);
////    }
////    
////    public default <A> A[] toArray(IntFunction<A[]> generator) {
////        return stream().toArray(generator);
////    }
////    
////    public default byte[] toByteArray(Func1<DATA, Byte> toByte) {
////        val byteArray = new ByteArrayOutputStream();
////        stream().forEach(d -> byteArray.write(toByte.apply(d)));
////        return byteArray.toByteArray();
////    }
////    
////    public default int[] toIntArray(ToIntFunction<DATA> toInt) {
////        return mapToInt(toInt).toArray();
////    }
////    
////    public default long[] toLongArray(ToLongFunction<DATA> toLong) {
////        return mapToLong(toLong).toArray();
////    }
////    
////    public default double[] toDoubleArray(ToDoubleFunction<DATA> toDouble) {
////        return mapToDouble(toDouble).toArray();
////    }
////    //== Plus ==
////    
////    public default String joinToString() {
////        return map(StrFuncs::toStr)
////                .collect(Collectors.joining());
////    }
////    public default String joinToString(String delimiter) {
////        return map(StrFuncs::toStr)
////                .collect(Collectors.joining(delimiter));
////    }
////    
////    //== Spawn ==
////    
////    
////    public default <T> Streamable<Result<T>> spawn(Func1<DATA, ? extends UncompletedAction<T>> mapper) {
////        return deriveWith(stream -> {
////            return stream().spawn(mapper);
////        });
////    }
////    
////    //== accumulate + restate ==
////    
////    public default Streamable<DATA> accumulate(BiFunction<? super DATA, ? super DATA, ? extends DATA> accumulator) {
////        return deriveWith(stream -> {
////            val iterator = StreamPlus.from(stream).iterator();
////            if (!iterator.hasNext())
////                return StreamPlus.empty();
////            
////            val prev = new AtomicReference<DATA>(iterator.next());
////            return StreamPlus.concat(
////                        StreamPlus.of(prev.get()),
////                        iterator.stream().map(n -> {
////                            val next = accumulator.apply(n, prev.get());
////                            prev.set(next);
////                            return next;
////                        })
////                    );
////        });
////    }
////    
////    public default Streamable<DATA> restate(BiFunction<? super DATA, Streamable<DATA>, Streamable<DATA>> restater) {
////        val func = (UnaryOperator<Tuple2<DATA, Streamable<DATA>>>)((Tuple2<DATA, Streamable<DATA>> pair) -> {
////            val stream   = pair._2();
////            val iterator = stream.iterator();
////            if (!iterator.hasNext())
////                return null;
////            
////            val head = iterator.next();
////            val tail =restater.apply(head, ()->iterator.stream());
////            return Tuple2.of(head, tail);
////        });
////        val seed = Tuple2.of((DATA)null, this);
////        val endStream = (Streamable<DATA>)(()->StreamPlus.iterate(seed, func).takeUntil(t -> t == null).skip(1).map(t -> t._1()));
////        return endStream;
////    }
////    
//////    
//////    public default <T extends Comparable<? super T>> FuncList<Tuple2<DATA, Double>> toPercentilesOf(Function<? super DATA, T> mapper) {
//////        FuncList<Tuple2<Integer, DATA>> list 
//////                = mapWithIndex(Tuple2::of)
//////                .sortedBy(tuple -> mapper.apply(tuple._2()))
//////                .toImmutableList();
//////        return Helper.toPercentilesOf(size() - 1, list);
//////    }
//////    
//////    public default <T> FuncList<Tuple2<DATA, Double>> toPercentilesOf(Function<? super DATA, T> mapper, Comparator<T> comparator) {
//////        FuncList<Tuple2<Integer, DATA>> list 
//////                = mapWithIndex(Tuple2::of)
//////                .sortedBy(tuple -> mapper.apply(tuple._2()), comparator)
//////                .toImmutableList();
//////        return Helper.toPercentilesOf(size() - 1, list);
//////    }
////    
}
