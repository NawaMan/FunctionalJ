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
package functionalj.stream.longstream;

import static functionalj.function.Func.itself;
import static functionalj.lens.Access.theLong;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.LongBiFunctionPrimitive;
import functionalj.stream.AsStreamable;
import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;
import functionalj.stream.intstream.IntStreamable;
import functionalj.tuple.LongLongTuple;
import lombok.val;

public interface LongStreamable 
        extends
            AsLongStreamable,
//            IntStreamableWithMapFirst,
//            IntStreamableWithMapThen,
//            IntStreamableWithMapTuple,
//            IntStreamableWithMapToMap,
//            //StreamPlusWithSplit,
//            IntStreamableWithSegment,
            LongStreamableWithCombine//,
//            IntStreamableWithCalculate,
//            IntStreamableAddtionalOperators,
//            IntStreamableAdditionalTerminalOperators 
            {
    
    public static LongStreamable empty() {
        return ()->LongStreamPlus.empty();
    }
    
    public static LongStreamable emptyLongStreamable() {
        return ()->LongStreamPlus.empty();
    }
    
    public static LongStreamable of(long ... data) {
        if (data == null) {
            return empty();
        }
        
        val length = data.length;
        val longs   = new long[length];
        System.arraycopy(data, 0, longs, 0, length);
        return ()->LongStreamPlus.of(longs);
    }
    
    public static LongStreamable steamableOf(long ... data) {
        return of(data);
    }
    
    public static LongStreamable longs(long ... data) {
        return of(data);
    }
    
    public static LongStreamable zeroes() {
        return LongStreamable.generate(()->0L);
    }
    
    public static LongStreamable zeroes(int count) {
        return LongStreamable.generate(()->0).limit(count);
    }
    
    public static LongStreamable ones() {
        return LongStreamable.generate(()->1);
    }
    
    public static LongStreamable ones(int count) {
        return LongStreamable.generate(()->1).limit(count);
    }
    
    public static LongStreamable repeat(long ... data) {
        val length = data.length;
        val longs   = new long[data.length];
        System.arraycopy(data, 0, longs, 0, length);
        return ()->{
            val flatten 
                    = Stream
                    .generate    (() -> LongStreamPlus.of(longs))
                    .flatMapToLong(s -> s);
            return LongStreamPlus.from(flatten);
        };
    }
    
    public static LongStreamable cycle(long ... data) {
        return LongStreamable.repeat(data);
    }
    
    public static LongStreamable loop(long time) {
        return ()->LongStreamPlus.range(0, time);
    }
    
    public static LongStreamable loop() {
        return ()->LongStreamPlus.range(0, Long.MAX_VALUE);
    }
    
    public static LongStreamable infinite() {
        return ()->LongStreamPlus.range(0, Integer.MAX_VALUE);
    }
    
    public static LongStreamable infiniteInt() {
        return infinite();
    }
    
    public static LongStreamable naturalNumbers() {
        return ()->LongStreamPlus.naturalNumbers();
    }
    
    public static LongStreamable wholeNumbers() {
        return ()->LongStreamPlus.wholeNumbers();
    }
    
    public static LongStreamable naturalNumbers(long count) {
        return naturalNumbers().limit(count);
    }
    
    public static LongStreamable wholeNumbers(long count) {
        return wholeNumbers().limit(count);
    }
    
    public static LongStreamable range(long startInclusive, long endExclusive) {
        return ()->LongStreamPlus.range(startInclusive, endExclusive);
    }
    
    public static LongStreamable generate(LongSupplier intSupplier) {
        return ()->LongStreamPlus.generate(intSupplier);
    }
    
    public static LongStreamable generateWith(Supplier<LongStream> streamSupplier) {
        return ()->StreamPlus.of(streamSupplier).flatMapToLong(s -> s.get());
    }
    
    public static LongStreamable iterate(long seed, LongUnaryOperator f) {
        return ()->LongStreamPlus.iterate(seed, f);
    }
    
    public static LongStreamable iterate(long seed1, long seed2, LongBinaryOperator f) {
        return ()->LongStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static LongStreamable concat(LongStreamable ... streamables) {
        return ()->StreamPlus.of(streamables).flatMapToLong(s -> s.longStream());
    }
    
    public static LongStreamable compound(long seed, LongUnaryOperator f) {
        return ()->LongStreamPlus.compound(seed, f);
    }
    
    public static LongStreamable compound(long seed1, long seed2, LongBinaryOperator f) {
        return ()->LongStreamPlus.iterate(seed1, seed2, f);
    }
    
    public static Streamable<LongLongTuple> zipOf(
            LongStreamable streamable1, 
            LongStreamable streamable2) {
        return ()->{
            return LongStreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream());
        };
    }
    public static StreamPlus<LongLongTuple> zipOf(
            LongStreamable streamable1, 
            LongStreamable streamable2,
            long           defaultValue) {
        return ()->{
            return LongStreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream(),
                    defaultValue);
        };
    }
    public static StreamPlus<LongLongTuple> zipOf(
            LongStreamable streamable1, long defaultValue1,
            LongStreamable streamable2, long defaultValue2) {
        return ()->{
            return LongStreamPlus.zipOf(
                    streamable1.longStream(), defaultValue1,
                    streamable2.longStream(), defaultValue2);
        };
    }
    
    public static LongStreamable zipOf(
            LongStreamable          streamable1, 
            LongStreamable          streamable2,
            LongBiFunctionPrimitive merger) {
        return ()->{
            return LongStreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream(),
                    merger);
        };
    }
    public static LongStreamable zipOf(
            LongStreamable          streamable1, 
            LongStreamable          streamable2,
            long                    defaultValue,
            LongBiFunctionPrimitive merger) {
        return ()->{
            return LongStreamPlus.zipOf(
                    streamable1.longStream(),
                    streamable2.longStream(),
                    defaultValue,
                    merger);
        };
    }
    public static LongStreamable zipOf(
            LongStreamable streamable1, long defaultValue1,
            LongStreamable streamable2, long defaultValue2,
            LongBiFunctionPrimitive merger) {
        return ()->{
            return LongStreamPlus.zipOf(
                    streamable1.longStream(), defaultValue1,
                    streamable2.longStream(), defaultValue2,
                    merger);
        };
    }
    
    public static LongStreamable from(LongStreamable mapToLong) {
        return (mapToLong == null) ? empty() : mapToLong;
    }
    
    public static LongStreamable from(
            LongStreamable                       source,
            Function<LongStreamable, LongStream> action) {
        return new LongStreamable() {
            @Override
            public LongStreamPlus longStream() {
                val targetStream = action.apply(source);
                return LongStreamPlus.from(targetStream);
            }
        };
    }
    
    //== Stream ==
    
    public default LongStreamable longStreamable() {
        return this;
    }
    
    public default Streamable<Long> streamable() {
        return boxed();
    }
    
    public LongStreamPlus longStream();
    
    public default StreamPlus<Long> stream() {
        return longStream().boxed();
    }
    
//    //== Pipeable ==
//    
//    public default Pipeable<? extends IntStreamable> pipeable() {
//        return Pipeable.of(this);
//    }
//    
//    //== Helper functions ==
//    
//    public default IntStreamable derive(
//                Function<IntStreamable, IntStream> action) {
//        return new IntStreamable() {
//            @Override
//            public IntStreamPlus intStream() {
//                val targetStream = action.apply(IntStreamable.this);
//                return IntStreamPlus.from(targetStream);
//            }
//        };
//    }
//    
//    public default <TARGET> Streamable<TARGET> deriveToStreamable(
//                IntStreamable                           source, 
//                Function<IntStreamable, Stream<TARGET>> action) {
//        return new Streamable<TARGET>() {
//            @Override
//            public StreamPlus<TARGET> stream() {
//                val targetStream = action.apply(source);
//                return StreamPlus.from(targetStream);
//            }
//        };
//    }
    
    public default LongStreamable asLongStreamable() {
        return LongStreamable.from(()->stream().mapToLong(i -> (long)i));
    }
    
//    @Override
//    public default DoubleStreamable asDoubleStream() {
//        return DoubleStreamPlus.from(stream().asDoubleStream());
//    }
    
    public default Streamable<Long> boxed() {
        return ()->StreamPlus.from(longStream().mapToObj(theLong.boxed()));
    }
    
    public default PrimitiveIterator.OfLong iterator() {
        return longStream().iterator();
    }
    
    public default Spliterator.OfLong spliterator() {
        return longStream().spliterator();
    }
    
    //== Stream specific ==
    
    public default LongStreamable sequential() {
        return LongStreamable.from(this, streamble -> streamble.longStream().sequential());
    }
    
    public default LongStreamable parallel() {
        return LongStreamable.from(this, streamble -> streamble.longStream().parallel());
    }
    
    public default LongStreamable unordered() {
        return LongStreamable.from(this, streamble -> streamble.longStream().unordered());
    }
    
    public default boolean isParallel() {
        return longStream().isParallel();
    }
    
    //== Functionalities ==
    
    public default LongStreamable map(LongUnaryOperator mapper) {
        return LongStreamable.from(this, streamble -> streamble.longStream().map(mapper));
    }
    
    public default LongStreamable mapToLong(LongUnaryOperator mapper) {
        return map(mapper);
    }
    
//    public default DoubleStreamable mapToDouble(IntToDoubleFunction mapper) {
//        return DoubleStreamPlus.from(stream().mapToDouble (mapper));
//    }
//    
    public default <TARGET> Streamable<TARGET> mapToObj(LongFunction<? extends TARGET> mapper) {
        return (Streamable<TARGET>)()->longStream().mapToObj(mapper);
    }
    
    public default IntStreamable mapToInt(LongToIntFunction mapper) {
        return ()->longStream().mapToInt(mapper);
    }
    
    public default <TARGET> Streamable<TARGET> flatMapToObj(LongFunction<? extends AsStreamable<TARGET>> mapper) {
        return (Streamable<TARGET>)()->longStream()
                    .flatMapToObj(each -> mapper.apply(each).stream());
    }
    
    public default IntStreamable flatMapToInt(
            LongFunction<? extends IntStream> mapper) {
//        return mapToObj(mapper).flatMapToInt(itself());
        return null;
    }
    
    public default LongStreamable flatMapToLong(
            LongFunction<? extends LongStream> mapper) {
//        return mapToObj(mapper).flatMapToLong(itself());
        return null;
    }
//    
//    public default DoubleStreamable flatMapToDouble(
//            LongFunction<? extends DoubleStream> mapper) {
//        return mapToObj(mapper).flatMapToDouble(itself());
//    }
    
//    public default DoubleStreamable flatMapToDouble(IntFunction<? extends DoubleStream> mapper) {
//        return stream()
//                .flatMapToDouble(mapper);
//    }
    
    public default LongStreamable flatMap(LongFunction<? extends AsLongStreamable> mapper) {
        return (LongStreamable)()->longStream().flatMap(each -> mapper.apply(each).longStream());
    }
    
//    public default IntStreamable filter(IntPredicate predicate) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .filter(predicate));
//    }
//    
//    // TODO - Rename all filter with map to filter
//    public default IntStreamable filter(
//            IntUnaryOperator mapper, 
//            IntPredicate     predicate) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .filter(mapper, predicate));
//    }
//    
//    public default <T> IntStreamable filterAsObject(
//            IntFunction<? extends T> mapper,
//            Predicate<? super T>     theCondition) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .filterAsObject(mapper, theCondition));
//    }
//    
//    public default <T> IntStreamable filterAsObject(
//            Function<Integer, ? extends T> mapper,
//            Predicate<? super T>     theCondition) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .filterAsObject(mapper, theCondition));
//    }
//    
//    public default IntStreamable peek(IntConsumer action) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .peek(action));
//    }
    
    public default LongStreamable limit(long maxSize) {
        return LongStreamable.from(this, 
                streamable -> 
                    streamable
                    .longStream()
                    .limit(maxSize));
    }
    
    public default LongStreamable skip(long offset) {
        return LongStreamable.from(this, 
                streamable -> 
                    streamable
                    .longStream()
                    .skip(offset));
    }
    
//    public default IntStreamable skipWhile(IntPredicate condition) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .skipWhile(condition));
//    }
//    
//    public default IntStreamable skipUntil(IntPredicate condition) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .skipUntil(condition));
//    }
//    
//    public default IntStreamable takeWhile(IntPredicate condition) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .takeWhile(condition));
//    }
//    
//    public default IntStreamable takeUntil(IntPredicate condition) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .takeUntil(condition));
//    }
//    
//    public default IntStreamable distinct() {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .distinct());
//    }
//    
//    //-- Sorted --
//    
//    public default IntStreamable sorted() {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .sorted());
//    }
//    
//    public default IntStreamable sortedBy(
//            IntUnaryOperator mapper) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .sortedBy(mapper));
//    }
//    
//    public default IntStreamable sortedBy(
//            IntUnaryOperator       mapper,
//            IntBiFunctionPrimitive comparator) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .sortedBy(mapper, comparator));
//    }
//    
//    public default <T extends Comparable<? super T>> IntStreamable sortedByObj(
//            IntFunction<T> mapper) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .sortedByObj(mapper));
//    }
//    
//    public default <T> IntStreamable sortedByObj(
//            IntFunction<T> mapper, 
//            Comparator<T>  comparator) {
//        return from(this, 
//                streamable -> 
//                    streamable
//                    .intStream()
//                    .sortedByObj(mapper, comparator));
//    }
//    
//    //-- Terminate --
//    
//    public default void forEach(IntConsumer action) {
//        if (action == null)
//            return;
//        
//        intStream()
//        .forEach(action);
//    }
//    
//    public default void forEachOrdered(IntConsumer action) {
//        if (action == null)
//            return;
//        
//        intStream()
//        .forEachOrdered(action);
//    }
//    
//    public default int reduce(int identity, IntBinaryOperator accumulator) {
//        return intStream()
//                .reduce(identity, accumulator);
//    }
//    
//    public default OptionalInt reduce(IntBinaryOperator accumulator) {
//        return intStream()
//                .reduce(accumulator);
//    }
//    
//    public default <R> R collect(
//            Supplier<R>       supplier,
//            ObjIntConsumer<R> accumulator,
//            BiConsumer<R, R>  combiner) {
//        return intStream()
//                .collect(supplier, accumulator, combiner);
//    }
//    
//    public default OptionalInt min() {
//        return intStream()
//                .min();
//    }
//    
//    public default OptionalInt max() {
//        return intStream()
//                .max();
//    }
//    
//    public default long count() {
//        return intStream().count();
//    }
//    
//    public default int size() {
//        return (int)intStream().count();
//    }
//    
//    public default boolean anyMatch(IntPredicate predicate) {
//        return intStream().anyMatch(predicate);
//    }
//    
//    public default boolean allMatch(IntPredicate predicate) {
//        return intStream().allMatch(predicate);
//    }
//    
//    public default boolean noneMatch(IntPredicate predicate) {
//        return intStream().noneMatch(predicate);
//    }
//    
//    public default OptionalInt findFirst() {
//        return intStream().findFirst();
//    }
//    
//    public default OptionalInt findAny() {
//        return intStream().findAny();
//    }
//    
//    //== toXXX ==
//    
//    public default int[] toArray() {
//        return intStream()
//                .toArray();
//    }
//    
//    public default int sum() {
//        return intStream().sum();
//    }
//    
//    public default OptionalDouble average() {
//        return intStream().average();
//    }
//    
//    public default IntSummaryStatistics summaryStatistics() {
//        return intStream().summaryStatistics();
//    }
    
    public default String toListString() {
        return longStream()
                .toListString();
    }
    
//    public default IntFuncList toList() {
//        return IntFuncList.from(this);
//    }
//    
//    public default FuncList<Integer> toBoxedList() {
//        return FuncList.from(this.boxed());
//    }
//    
//    public default ImmutableIntFuncList toImmutableList() {
//        return intStream()
//                .toImmutableList();
//    }
//    
//    //== Plus ==
//    
//    public default String joinToString() {
//        return intStream()
//                .joinToString();
//    }
//    
//    public default String joinToString(String delimiter) {
//        return intStream()
//                .joinToString(delimiter);
//    }
//    
//    //== Pipe ==
//    
//    public default <T> Pipeable<? extends IntStreamable> pipable() {
//        return Pipeable.of(this);
//    }
//    
//    //== Spawn ==
//    
//    public default <T> Streamable<Result<T>> spawn(
//            IntFunction<? extends UncompletedAction<T>> mapToAction) {
//        return ()->
//                intStream()
//                .spawn(mapToAction);
//    }
//    
//    public default IntStreamable accumulate(IntBiFunctionPrimitive accumulator) {
//        return ()->
//                intStream()
//                .accumulate(accumulator);
//    }
//    
//    public default IntStreamable restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
//        return ()->
//                intStream()
//                .restate(restater);
//    }
//    
}
