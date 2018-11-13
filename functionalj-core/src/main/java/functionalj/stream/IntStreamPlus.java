package functionalj.stream;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import lombok.val;

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
    
    public default <T> Pipeable<IntStreamPlus> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super IntStreamPlus, T> piper) {
        return piper.apply(this);
    }
    
    public default <U> StreamPlus<U> mapBy(IntFunction<? extends U> mapper) {
        return StreamPlus.from(stream().mapToObj(mapper));
    }
    
    public default StreamPlus<Integer> asStream() {
        return StreamPlus.from(stream()
                .mapToObj(i -> Integer.valueOf(i)));
    }
    
    @Override
    public default <U> StreamPlus<U> mapToObj(IntFunction<? extends U> mapper) {
        return StreamPlus.from(stream().mapToObj(mapper));
    }
    
    public default <TARGET> StreamPlus<TARGET> mapToObj(Supplier<? extends TARGET> supplier) {
        return StreamPlus.from(stream().mapToObj(e -> supplier.get()));
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
    public default IntStreamPlus peek(IntConsumer action) {
        return IntStreamPlus.from(stream().peek(action));
    }
    
    @Override
    public default IntStreamPlus limit(long maxSize) {
        return IntStreamPlus.from(stream().limit(maxSize));
    }
    
    @Override
    public default IntStreamPlus skip(long n) {
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
    public default IntStreamPlus sequential() {
        return IntStreamPlus.from(stream().sequential());
    }
    
    @Override
    public default IntStreamPlus parallel() {
        return IntStreamPlus.from(stream().sequential());
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
    public default IntStreamPlus unordered() {
        return IntStreamPlus.from(stream().unordered());
    }
    
    @Override
    public default IntStreamPlus onClose(Runnable closeHandler) {
        return IntStreamPlus.from(stream().onClose(closeHandler));
    }
    
    @Override
    public default void close() {
        stream().close();
    }
    
    //== Additional functionalities
    
    public default StreamPlus<IntStreamPlus> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<IntStreamPlus> segment(int count, boolean includeTail) {
//        if (count <= 1)
//            return this;
        
        val index = new AtomicInteger(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeTail);
    }
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<Integer>());
        val adding = new AtomicBoolean(false);
        
        StreamPlus<IntStreamPlus> mainStream = StreamPlus.from(
                mapToObj(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                        val retList = list.getAndUpdate(l -> new ArrayList<Integer>());
                        list.get().add(i);
                        
                        if (retList.isEmpty())
                            return null;
                        
                        return IntStreamPlus.from(retList.stream().mapToInt(Integer::intValue));
                    }
                    if (adding.get()) list.get().add(i);
                    return null;
                }))
                .filterNonNull();
        ;
        val mainSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->mainStream;
        if (!includeTail)
            return mainStream;
        
        val tailSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->{
            return StreamPlus.of(
                    IntStreamPlus.from(
                            list.get()
                            .stream()
                            .mapToInt(Integer::intValue)));
        };
        val resultStream = StreamPlus.of(
            mainSupplier,
            tailSupplier
        )
        .flatMap(Supplier::get);
        return resultStream;
    }
    
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<Integer>());
        val adding = new AtomicBoolean(false);
        
        StreamPlus<IntStreamPlus> stream = StreamPlus.from(
                mapToObj(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                    }
                    if (includeTail && adding.get()) list.get().add(i);
                    if (endCondition.test(i)) {
                        adding.set(false);
                        val retList = list.getAndUpdate(l -> new ArrayList<Integer>());
                        return IntStreamPlus.from(retList.stream().mapToInt(Integer::intValue));
                    }
                    
                    if (!includeTail && adding.get()) list.get().add(i);
                    return null;
                }))
            .filterNonNull();
        return stream;
    }
    
    // No Zip - as it seems to give no additional benefit than just changing it to Stream and zip it there.
    
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
    
    //== Plus ==
    
    public default String joining() {
        return stream()
                .mapToObj(StrFuncs::toStr)
                .collect(Collectors.joining());
    }
    public default String joining(String delimiter) {
        return stream()
                .mapToObj(StrFuncs::toStr)
                .collect(Collectors.joining(delimiter));
    }
    
}
