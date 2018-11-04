package functionalj.stream;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Set;
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
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.list.FuncList;
import functionalj.list.ImmutableList;

@FunctionalInterface
public interface IntStreamPlus extends IntStream {

    public static IntStreamPlus of(int ... ints) {
        return IntStreamPlus.from(IntStream.of(ints));
    }
    
    public static IntStreamPlus from(IntStream intStream) {
        return ()->intStream;
    }
    
    public static IntStreamPlus empty() {
        return IntStreamPlus.from(IntStream.empty());
    }
    
    public static IntStreamPlus infinite() {
        return IntStreamPlus.from(IntStream.iterate(0, i -> i + 1));
    }
    
    public static IntStreamPlus range(int startInclusive, int endExclusive) {
        return IntStreamPlus.from(IntStream.range(startInclusive, endExclusive));
    }
    
    public static IntStreamPlus rangeClosed(int startInclusive, int endInclusive) {
        return IntStreamPlus.from(IntStream.rangeClosed(startInclusive, endInclusive));
    }
    
    public static IntStreamPlus generate(IntSupplier s) {
        return IntStreamPlus.from(IntStream.generate(s));
    }
    
    public static IntStreamPlus iterate(int seed, IntUnaryOperator f) {
        return IntStreamPlus.from(IntStream.iterate(seed, f));
    }
    
    //== Stream ==
    
    public IntStream stream();
    
    @Override
    public default IntStreamPlus filter(IntPredicate predicate) {
        return IntStreamPlus.from(stream().filter(predicate));
    }
    
    @Override
    public default IntStreamPlus map(IntUnaryOperator mapper) {
        return IntStreamPlus.from(stream().map(mapper));
    }
    
    public default <U> StreamPlus<U> mapBy(IntFunction<? extends U> mapper) {
        return StreamPlus.from(stream().mapToObj(mapper));
    }
    
    public default StreamPlus<Integer> asStream() {
        return StreamPlus.from(stream()
                .mapToObj(i -> Integer.valueOf(i)));
    }
    
    @Override
    public default <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        return StreamPlus.from(stream().mapToObj(mapper));
    }
    
    @Override
    public default LongStream mapToLong(IntToLongFunction mapper) {
        return stream().mapToLong(mapper);
    }
    
    @Override
    public default DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return stream().mapToDouble (mapper);
    }
    
    @Override
    public default IntStreamPlus flatMap(IntFunction<? extends IntStream> mapper) {
        return IntStreamPlus.from(stream().flatMap(mapper));
    }
    
    public default IntStreamPlus flatMap(Function<Integer, Stream<Integer>> mapper) {
        return IntStreamPlus.from(stream()
                .mapToObj(i -> Integer.valueOf(i))
                .flatMap (i -> mapper.apply(i))
                .mapToInt(i -> i.intValue()));
    }
    
    @Override
    public default IntStreamPlus distinct() {
        return IntStreamPlus.from(stream().distinct());
    }
    
    @Override
    public default IntStreamPlus sorted() {
        return IntStreamPlus.from(stream().sorted());
    }
    
    @Override
    public default IntStream peek(IntConsumer action) {
        return IntStreamPlus.from(stream().peek(action));
    }
    
    @Override
    public default IntStream limit(long maxSize) {
        return IntStreamPlus.from(stream().limit(maxSize));
    }
    
    @Override
    public default IntStream skip(long n) {
        return IntStreamPlus.from(stream().skip(n));
    }
    
    @Override
    public default void forEach(IntConsumer action) {
        stream().forEach(action);
    }
    
    @Override
    public default void forEachOrdered(IntConsumer action) {
        stream().forEach(action);
    }
    
    @Override
    public default int[] toArray() {
        return stream().toArray();
    }
    
    @Override
    public default int reduce(int identity, IntBinaryOperator op) {
        return stream().reduce(identity, op);
    }
    
    @Override
    public default OptionalInt reduce(IntBinaryOperator op) {
        return stream().reduce(op);
    }
    
    @Override
    public default <R> R collect(Supplier<R> supplier,
                  ObjIntConsumer<R> accumulator,
                  BiConsumer<R, R> combiner) {
        return stream().collect(supplier, accumulator, combiner);
    }
    
    @Override
    public default int sum() {
        return stream().sum();
    }
    
    @Override
    public default OptionalInt min() {
        return stream().min();
    }
    
    @Override
    public default OptionalInt max() {
        return stream().min();
    }
    
    @Override
    public default long count() {
        return stream().count();
    }
    
    @Override
    public default OptionalDouble average() {
        return stream().average();
    }
    
    @Override
    public default IntSummaryStatistics summaryStatistics() {
        return stream().summaryStatistics();
    }
    
    @Override
    public default boolean anyMatch(IntPredicate predicate) {
        return stream().anyMatch(predicate);
    }
    
    @Override
    public default boolean allMatch(IntPredicate predicate) {
        return stream().allMatch(predicate);
    }
    
    @Override
    public default boolean noneMatch(IntPredicate predicate) {
        return stream().noneMatch(predicate);
    }
    
    @Override
    public default OptionalInt findFirst() {
        return stream().findFirst();
    }
    
    @Override
    public default OptionalInt findAny() {
        return stream().findAny();
    }
    
    @Override
    public default LongStream asLongStream() {
        return stream().asLongStream();
    }
    
    @Override
    public default DoubleStream asDoubleStream() {
        return stream().asDoubleStream();
    }
    
    @Override
    public default StreamPlus<Integer> boxed() {
        return StreamPlus.from(stream().boxed());
    }
    
    @Override
    public default IntStream sequential() {
        return stream().sequential();
    }
    
    @Override
    public default IntStream parallel() {
        return stream().sequential();
    }
    
    @Override
    public default PrimitiveIterator.OfInt iterator() {
        return stream().iterator();
    }
    
    @Override
    public default Spliterator.OfInt spliterator() {
        return stream().spliterator();
    }
    
    @Override
    public default boolean isParallel() {
        return stream().isParallel();
    }
    
    @Override
    public default IntStream unordered() {
        return stream().unordered();
    }
    
    @Override
    public default IntStream onClose(Runnable closeHandler) {
        return stream().onClose(closeHandler);
    }
    
    @Override
    public default void close() {
        stream().close();
    }
    
    //== Additional functionality
    
    public default List<Integer> toList() {
        return asStream().toList();
    }
    
    public default FuncList<Integer> toFuncList() {
        return asStream().toImmutableList();
    }
    
    public default ImmutableList<Integer> toImmutableList() {
        return asStream().toImmutableList();
    }
    
    public default List<Integer> toMutableList() {
        return asStream().toMutableList();
    }
    
    public default ArrayList<Integer> toArrayList() {
        return asStream().toArrayList();
    }
    
    public default Set<Integer> toSet() {
        return asStream().toSet();
    }
    
}
