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
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.functions.StrFuncs;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

@FunctionalInterface
public interface LongStreamPlus extends LongStream {
    
    public static LongStreamPlus of(long ... longs) {
        return LongStreamPlus.from(LongStream.of(longs));
    }
    
    public static LongStreamPlus from(LongStream longStream) {
        return ()->longStream;
    }
    
    public static LongStreamPlus concat(LongStreamPlus ... streams) {
        return LongStreamPlus.from(StreamPlus.of(streams).flatMap(s -> s.asStream()).mapToLong(i -> i));
    }
    
    public static LongStreamPlus empty() {
        return LongStreamPlus.from(LongStream.empty());
    }
    
    public static LongStreamPlus infinite() {
        return LongStreamPlus.from(LongStream.iterate(0, i -> i + 1));
    }
    
    public static LongStreamPlus range(int startInclusive, int endExclusive) {
        return LongStreamPlus.from(LongStream.range(startInclusive, endExclusive));
    }
    
    public static LongStreamPlus rangeClosed(int startInclusive, int endInclusive) {
        return LongStreamPlus.from(LongStream.rangeClosed(startInclusive, endInclusive));
    }
    
    public static LongStreamPlus generate(LongSupplier s) {
        return LongStreamPlus.from(LongStream.generate(s));
    }
    
    public static LongStreamPlus iterate(long seed, LongUnaryOperator f) {
        return LongStreamPlus.from(LongStream.iterate(seed, f));
    }
    
    public static LongStreamPlus compound(long seed, LongUnaryOperator f) {
        return LongStreamPlus.from(LongStream.iterate(seed, f));
    }
    
    public static LongStreamPlus iterate(long seed1, long seed2, LongBinaryOperator f) {
        val counter = new AtomicLong(0);
        val int1    = new AtomicLong(seed1);
        val int2    = new AtomicLong(seed2);
        return LongStreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            long i2 = int2.get();
            long i1 = int1.getAndSet(i2);
            long i  = f.applyAsLong(i1, i2);
            int2.set(i);
            return i;
        });
    }
    
    //== Stream ==
    
    public LongStream stream();
    
    public default <TARGET> TARGET terminate(Func1<LongStream, TARGET> action) {
        val stream = stream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(FuncUnit1<LongStream> action) {
        val stream = stream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
    @Override
    public default LongStreamPlus filter(LongPredicate predicate) {
        return LongStreamPlus.from(stream().filter(predicate));
    }
    
    @Override
    public default LongStreamPlus map(LongUnaryOperator mapper) {
        return LongStreamPlus.from(stream().map(mapper));
    }
    
    public default <T> Pipeable<LongStreamPlus> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super LongStreamPlus, T> piper) {
        return piper.apply(this);
    }
    
    public default <U> StreamPlus<U> mapBy(LongFunction<? extends U> mapper) {
        return StreamPlus.from(stream().mapToObj(mapper));
    }
    
    public default StreamPlus<Long> asStream() {
        val stream = StreamPlus.from(stream()
                .mapToObj(i -> Long.valueOf(i)));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default <U> StreamPlus<U> mapToObj(LongFunction<? extends U> mapper) {
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
    public default IntStreamPlus mapToInt(LongToIntFunction mapper) {
        return IntStreamPlus.from(stream().mapToInt(mapper));
    }
    
    @Override
    public default DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        return stream().mapToDouble (mapper);
    }
    
    @Override
    public default LongStreamPlus flatMap(LongFunction<? extends LongStream> mapper) {
        return LongStreamPlus.from(stream().flatMap(mapper));
    }
    
    public default LongStreamPlus flatMap(Function<Long, Stream<Long>> mapper) {
        return LongStreamPlus.from(stream()
                .mapToObj (i -> Long.valueOf(i))
                .flatMap  (i -> mapper.apply(i))
                .mapToLong(i -> i.longValue()));
    }
    
    @Override
    public default LongStreamPlus distinct() {
        return LongStreamPlus.from(stream().distinct());
    }
    
    @Override
    public default LongStreamPlus sorted() {
        return LongStreamPlus.from(stream().sorted());
    }
    
    @Override
    public default LongStreamPlus peek(LongConsumer action) {
        return LongStreamPlus.from(stream().peek(action));
    }
    
    @Override
    public default LongStreamPlus limit(long maxSize) {
        return LongStreamPlus.from(stream().limit(maxSize));
    }
    
    @Override
    public default LongStreamPlus skip(long n) {
        return LongStreamPlus.from(stream().skip(n));
    }
    
    @Override
    public default void forEach(LongConsumer action) {
        terminate(stream-> {
            stream
            .forEach(action);
        });
    }
    
    @Override
    public default void forEachOrdered(LongConsumer action) {
        terminate(stream-> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Override
    public default long[] toArray() {
        return terminate(stream-> {
            return stream
                    .toArray();
        });
    }
    
    @Override
    public default long reduce(long identity, LongBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(identity, op);
        });
    }
    
    @Override
    public default OptionalLong reduce(LongBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(op);
        });
    }
    
    @Override
    public default <R> R collect(Supplier<R> supplier,
                  ObjLongConsumer<R> accumulator,
                  BiConsumer<R, R> combiner) {
        return terminate(stream-> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Override
    public default long sum() {
        return terminate(stream-> {
            return stream
                    .sum();
        });
    }
    
    @Override
    public default OptionalLong min() {
        return terminate(stream-> {
            return stream
                    .min();
        });
    }
    
    @Override
    public default OptionalLong max() {
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
    public default LongSummaryStatistics summaryStatistics() {
        return terminate(stream-> {
            return stream
                    .summaryStatistics();
        });
    }
    
    @Override
    public default boolean anyMatch(LongPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Override
    public default boolean allMatch(LongPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Override
    public default boolean noneMatch(LongPredicate predicate) {
        return terminate(stream-> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Override
    public default OptionalLong findFirst() {
        return terminate(stream-> {
            return stream
                    .findFirst();
        });
    }
    
    @Override
    public default OptionalLong findAny() {
        return terminate(stream-> {
            return stream
                    .findAny();
        });
    }
    
    @Override
    public default DoubleStreamPlus asDoubleStream() {
        return DoubleStreamPlus.from(stream().asDoubleStream());
    }
    
    @Override
    public default StreamPlus<Long> boxed() {
        return StreamPlus.from(stream().boxed());
    }
    
    @Override
    public default LongStreamPlus sequential() {
        return LongStreamPlus.from(stream().sequential());
    }
    
    @Override
    public default LongStreamPlus parallel() {
        return LongStreamPlus.from(stream().sequential());
    }
    
    @Override
    public default PrimitiveIterator.OfLong iterator() {
        // TODO - Make sure close is handled properly.
        return stream().iterator();
    }
    
    @Override
    public default Spliterator.OfLong spliterator() {
        return stream().spliterator();
    }
    
    @Override
    public default boolean isParallel() {
        return stream().isParallel();
    }
    
    @Override
    public default LongStreamPlus unordered() {
        return LongStreamPlus.from(stream().unordered());
    }
    
    @Override
    public default LongStreamPlus onClose(Runnable closeHandler) {
        return LongStreamPlus.from(stream().onClose(closeHandler));
    }
    
    @Override
    public default void close() {
        stream().close();
    }
    
    //== Additional functionalities
    
    public default StreamPlus<LongStreamPlus> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<LongStreamPlus> segment(int count, boolean includeTail) {
//        if (count <= 1)
//            return this;
        
        val index = new AtomicLong(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeTail);
    }
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<Long>());
        val adding = new AtomicBoolean(false);
        
        StreamPlus<LongStreamPlus> mainStream = StreamPlus.from(
                mapToObj(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                        val retList = list.getAndUpdate(l -> new ArrayList<Long>());
                        list.get().add(i);
                        
                        if (retList.isEmpty())
                            return null;
                        
                        return LongStreamPlus.from(retList.stream().mapToLong(Long::longValue));
                    }
                    if (adding.get()) list.get().add(i);
                    return null;
                }))
                .filterNonNull();
        ;
        val mainSupplier = (Supplier<StreamPlus<LongStreamPlus>>)()->mainStream;
        if (!includeTail)
            return mainStream;
        
        val tailSupplier = (Supplier<StreamPlus<LongStreamPlus>>)()->{
            return StreamPlus.of(
                    LongStreamPlus.from(
                            list.get()
                            .stream()
                            .mapToLong(Long::longValue)));
        };
        val resultStream = StreamPlus.of(
            mainSupplier,
            tailSupplier
        )
        .flatMap(Supplier::get);
        return resultStream;
    }
    
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, LongPredicate endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<LongStreamPlus> segment(LongPredicate startCondition, LongPredicate endCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<Long>());
        val adding = new AtomicBoolean(false);
        
        StreamPlus<LongStreamPlus> stream = StreamPlus.from(
                mapToObj(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                    }
                    if (includeTail && adding.get()) list.get().add(i);
                    if (endCondition.test(i)) {
                        adding.set(false);
                        val retList = list.getAndUpdate(l -> new ArrayList<Long>());
                        return LongStreamPlus.from(retList.stream().mapToLong(Long::longValue));
                    }
                    
                    if (!includeTail && adding.get()) list.get().add(i);
                    return null;
                }))
            .filterNonNull();
        return stream;
    }
    
    public default List<Long> toList() {
        return asStream().toJavaList();
    }
    
    public default FuncList<Long> toFuncList() {
        return asStream().toImmutableList();
    }
    
    public default ImmutableList<Long> toImmutableList() {
        return asStream().toImmutableList();
    }
    
    public default List<Long> toMutableList() {
        return asStream().toMutableList();
    }
    
    public default ArrayList<Long> toArrayList() {
        return asStream().toArrayList();
    }
    
    public default Set<Long> toSet() {
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
//    
//    //== Calculate ==
//    
//    public default <A, T> T calculate(
//            LongCollectorPlus<A, T> processor) {
//        val collected = Collected.ofLong(processor);
//        forEach(each -> {
//            collected.accumulate(each);
//        });
//        val value = collected.finish();
//        return value;
//    }
//    
//    public default <A1, A2, T1, T2> Tuple2<T1, T2> calculate(
//            LongCollectorPlus<A1, T1> processor1, 
//            LongCollectorPlus<A2, T2> processor2) {
//        val collected1 = Collected.ofLong(processor1);
//        val collected2 = Collected.ofLong(processor2);
//        forEach(each -> {
//            collected1.accumulate(each);
//            collected2.accumulate(each);
//        });
//        val value1 = collected1.finish();
//        val value2 = collected2.finish();
//        return Tuple.of(value1, value2);
//    }
//    
//    public default <A1, A2, A3, T1, T2, T3> Tuple3<T1, T2, T3> calculate(
//            LongCollectorPlus<A1, T1> processor1, 
//            LongCollectorPlus<A2, T2> processor2, 
//            LongCollectorPlus<A3, T3> processor3) {
//        val collected1 = Collected.ofLong(processor1);
//        val collected2 = Collected.ofLong(processor2);
//        val collected3 = Collected.ofLong(processor3);
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
//    public default <A1, A2, A3, A4, T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> calculate(
//            LongCollectorPlus<A1, T1> processor1, 
//            LongCollectorPlus<A2, T2> processor2, 
//            LongCollectorPlus<A3, T3> processor3, 
//            LongCollectorPlus<A4, T4> processor4) {
//        val collected1 = Collected.ofLong(processor1);
//        val collected2 = Collected.ofLong(processor2);
//        val collected3 = Collected.ofLong(processor3);
//        val collected4 = Collected.ofLong(processor4);
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
//    public default <A1, A2, A3, A4, A5, T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> calculate(
//            LongCollectorPlus<A1, T1> processor1, 
//            LongCollectorPlus<A2, T2> processor2, 
//            LongCollectorPlus<A3, T3> processor3, 
//            LongCollectorPlus<A4, T4> processor4, 
//            LongCollectorPlus<A5, T5> processor5) {
//        val collected1 = Collected.ofLong(processor1);
//        val collected2 = Collected.ofLong(processor2);
//        val collected3 = Collected.ofLong(processor3);
//        val collected4 = Collected.ofLong(processor4);
//        val collected5 = Collected.ofLong(processor5);
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
//    public default <A1, A2, A3, A4, A5, A6, T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> calculate(
//            LongCollectorPlus<A1, T1> processor1, 
//            LongCollectorPlus<A2, T2> processor2, 
//            LongCollectorPlus<A3, T3> processor3, 
//            LongCollectorPlus<A4, T4> processor4, 
//            LongCollectorPlus<A5, T5> processor5, 
//            LongCollectorPlus<A6, T6> processor6) {
//        val collected1 = Collected.ofLong(processor1);
//        val collected2 = Collected.ofLong(processor2);
//        val collected3 = Collected.ofLong(processor3);
//        val collected4 = Collected.ofLong(processor4);
//        val collected5 = Collected.ofLong(processor5);
//        val collected6 = Collected.ofLong(processor6);
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
}
