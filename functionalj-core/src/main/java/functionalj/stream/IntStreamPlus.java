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
import java.util.concurrent.atomic.AtomicLong;
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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit1;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

@FunctionalInterface
public interface IntStreamPlus extends IntStream {
    
    public static IntStreamPlus of(int ... ints) {
        return IntStreamPlus.from(IntStream.of(ints));
    }
    
    public static IntStreamPlus from(IntStream intStream) {
        return ()->intStream;
    }
    
    public static IntStreamPlus concat(IntStreamPlus ... streams) {
        return StreamPlus.of(streams).flatMap(s -> s.asStream()).mapToInt(i -> i);
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
    
    public static IntStreamPlus compound(int seed, IntUnaryOperator f) {
        return IntStreamPlus.from(IntStream.iterate(seed, f));
    }
    
    public static IntStreamPlus iterate(int seed1, int seed2, IntBinaryOperator f) {
        val counter = new AtomicInteger(0);
        val int1    = new AtomicInteger(seed1);
        val int2    = new AtomicInteger(seed2);
        return IntStreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            int i2 = int2.get();
            int i1 = int1.getAndSet(i2);
            int i  = f.applyAsInt(i1, i2);
            int2.set(i);
            return i;
        });
    }
    
    //== Stream ==
    
    public IntStream stream();
    
    public default <TARGET> TARGET terminate(Func1<IntStream, TARGET> action) {
        val stream = stream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(FuncUnit1<IntStream> action) {
        val stream = stream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
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
        val stream = StreamPlus.from(stream()
                .mapToObj(i -> Integer.valueOf(i)));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default <U> StreamPlus<U> mapToObj(IntFunction<? extends U> mapper) {
        StreamPlus<U> stream = StreamPlus.from(stream().mapToObj(mapper));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    public default <TARGET> StreamPlus<TARGET> mapToObj(Supplier<? extends TARGET> supplier) {
        StreamPlus<TARGET> stream = StreamPlus.from(stream().mapToObj(e -> supplier.get()));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default LongStreamPlus mapToLong(IntToLongFunction mapper) {
        return LongStreamPlus.from(stream().mapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus mapToDouble(IntToDoubleFunction mapper) {
        return DoubleStreamPlus.from(stream().mapToDouble (mapper));
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
        terminate(stream-> {
            stream
            .forEach(action);
        });
    }
    
    @Override
    public default void forEachOrdered(IntConsumer action) {
        terminate(stream-> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Override
    public default int[] toArray() {
        return terminate(stream-> {
            return stream
                    .toArray();
        });
    }
    
    @Override
    public default int reduce(int identity, IntBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(identity, op);
        });
    }
    
    @Override
    public default OptionalInt reduce(IntBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(op);
        });
    }
    
    @Override
    public default <R> R collect(Supplier<R> supplier,
                  ObjIntConsumer<R> accumulator,
                  BiConsumer<R, R> combiner) {
        return terminate(stream-> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Override
    public default int sum() {
        return terminate(stream-> {
            return stream
                    .sum();
        });
    }
    
    @Override
    public default OptionalInt min() {
        return terminate(stream-> {
            return stream
                    .min();
        });
    }
    
    @Override
    public default OptionalInt max() {
        return terminate(stream-> {
            return stream
                    .max();
        });
    }
    
    @Override
    public default long count() {
        return terminate(stream-> {
            return stream
                    .count();
        });
    }
    
    @Override
    public default OptionalDouble average() {
        return terminate(stream-> {
            return stream
                    .average();
        });
    }
    
    @Override
    public default IntSummaryStatistics summaryStatistics() {
        return terminate(stream-> {
            return stream
                    .summaryStatistics();
        });
    }
    
    @Override
    public default boolean anyMatch(IntPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Override
    public default boolean allMatch(IntPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Override
    public default boolean noneMatch(IntPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Override
    public default OptionalInt findFirst() {
        return terminate(stream-> {
            return stream
                    .findFirst();
        });
    }
    
    @Override
    public default OptionalInt findAny() {
        return terminate(stream-> {
            return stream
                    .findAny();
        });
    }
    
    @Override
    public default LongStreamPlus asLongStream() {
        return LongStreamPlus.from(stream().asLongStream());
    }
    
    @Override
    public default DoubleStreamPlus asDoubleStream() {
        return DoubleStreamPlus.from(stream().asDoubleStream());
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
        // TODO - Make sure close is handled properly.
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
    
    // TODO - percentile (multiple percentile value) and percentileRange (invert of percentile)
    // TODO - segmentAt, segmentAtPercentiles
    
    public default List<Integer> toList() {
        return asStream().toJavaList();
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
        return terminate(stream -> {
            return stream()
                .mapToObj(StrFuncs::toStr)
                .collect(Collectors.joining());
        });
    }
    public default String joining(String delimiter) {
        return terminate(stream -> {
            return stream()
                .mapToObj(StrFuncs::toStr)
                .collect(Collectors.joining(delimiter));
        });
    }
    
    //== Get ==
    
    public default <T> T get(IntStreamElementProcessor<T> processor) {
        val counter = new AtomicLong(0);
        val iterator = iterator();
        while (iterator.hasNext()) {
            val each  = iterator.nextInt();
            val index = counter.getAndIncrement();
            processor.processElement(index, each);
        }
        val count = counter.get();
        return processor.processComplete(count);
    }
    
    public default <T1, T2> Tuple2<T1, T2> get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2) {
        return get(processor1, processor2, Tuple2::of);
    }
    
    public default <T, T1, T2> T get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2,
                Func2<T1, T2, T>          combiner) {
        val counter = new AtomicLong(0);
        val iterator = iterator();
        while (iterator.hasNext()) {
            val each  = iterator.nextInt();
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        return combiner.apply(value1, value2);
    }
    
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3) {
        return get(processor1, processor2, processor3, Tuple3::of);
    }
    
    public default <T1, T2, T3, T> T get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3,
                Func3<T1, T2, T3, T>   combiner) {
        val counter = new AtomicLong(0);
        val iterator = iterator();
        while (iterator.hasNext()) {
            val each  = iterator.nextInt();
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        return combiner.apply(value1, value2, value3);
    }
    
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3, 
                IntStreamElementProcessor<T4> processor4) {
        return get(processor1, processor2, processor3, processor4, Tuple4::of);
    }
    
    public default <T1, T2, T3, T4, T> T get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3,
                IntStreamElementProcessor<T4> processor4,
                Func4<T1, T2, T3, T4, T>  combiner) {
        val counter = new AtomicLong(0);
        val iterator = iterator();
        while (iterator.hasNext()) {
            val each  = iterator.nextInt();
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        return combiner.apply(value1, value2, value3, value4);
    }
    
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3, 
                IntStreamElementProcessor<T4> processor4, 
                IntStreamElementProcessor<T5> processor5) {
        return get(processor1, processor2, processor3, processor4, processor5, Tuple5::of);
    }
    
    public default <T1, T2, T3, T4, T5, T> T get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3,
                IntStreamElementProcessor<T4> processor4,
                IntStreamElementProcessor<T5> processor5,
                Func5<T1, T2, T3, T4, T5, T>  combiner) {
        val counter = new AtomicLong(0);
        val iterator = iterator();
        while (iterator.hasNext()) {
            val each  = iterator.nextInt();
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return combiner.apply(value1, value2, value3, value4, value5);
    }
    
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3, 
                IntStreamElementProcessor<T4> processor4, 
                IntStreamElementProcessor<T5> processor5, 
                IntStreamElementProcessor<T6> processor6) {
        return get(processor1, processor2, processor3, processor4, processor5, processor6, Tuple6::of);
    }
    
    public default <T1, T2, T3, T4, T5, T6, T> T get(
                IntStreamElementProcessor<T1> processor1, 
                IntStreamElementProcessor<T2> processor2, 
                IntStreamElementProcessor<T3> processor3,
                IntStreamElementProcessor<T4> processor4,
                IntStreamElementProcessor<T5> processor5,
                IntStreamElementProcessor<T6> processor6,
                Func6<T1, T2, T3, T4, T5, T6, T>  combiner) {
        val counter = new AtomicLong(0);
        val iterator = iterator();
        while (iterator.hasNext()) {
            val each  = iterator.nextInt();
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return combiner.apply(value1, value2, value3, value4, value5, value6);
    }
}
