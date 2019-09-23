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
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
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
public interface DoubleStreamPlus extends DoubleStream {
    
    public static DoubleStreamPlus of(double ... doubles) {
        return DoubleStreamPlus.from(DoubleStream.of(doubles));
    }
    
    public static DoubleStreamPlus from(DoubleStream doubleStream) {
        return ()->doubleStream;
    }
    
    public static DoubleStreamPlus concat(DoubleStreamPlus ... streams) {
        return DoubleStreamPlus.from(StreamPlus.of(streams).flatMap(s -> s.asStream()).mapToDouble(i -> i));
    }
    
    public static DoubleStreamPlus empty() {
        return DoubleStreamPlus.from(DoubleStream.empty());
    }
    
    public static DoubleStreamPlus infinite() {
        return DoubleStreamPlus.from(DoubleStream.iterate(0, i -> i + 1));
    }
    
    public static DoubleStreamPlus generate(DoubleSupplier s) {
        return DoubleStreamPlus.from(DoubleStream.generate(s));
    }
    
    public static DoubleStreamPlus iterate(double seed, DoubleUnaryOperator f) {
        return DoubleStreamPlus.from(DoubleStream.iterate(seed, f));
    }
    
    public static DoubleStreamPlus compound(double seed, DoubleUnaryOperator f) {
        return DoubleStreamPlus.from(DoubleStream.iterate(seed, f));
    }
    
    public static DoubleStreamPlus iterate(double seed1, double seed2, DoubleBinaryOperator f) {
        val counter = new AtomicInteger(0);
        val int1    = new AtomicReference<Double>(seed1);
        val int2    = new AtomicReference<Double>(seed2);
        return DoubleStreamPlus.generate(()->{
            if (counter.getAndIncrement() == 0)
                return seed1;
            if (counter.getAndIncrement() == 2)
                return seed2;
            
            double i2 = int2.get();
            double i1 = int1.getAndSet(i2);
            double i  = f.applyAsDouble(i1, i2);
            int2.set(i);
            return i;
        });
    }
    
    //== Stream ==
    
    public DoubleStream stream();
    
    public default <TARGET> TARGET terminate(Func1<DoubleStream, TARGET> action) {
        val stream = stream();
        try {
            val result = action.apply(stream);
            return result;
        } finally {
            stream.close();
        }
    }
    
    public default void terminate(FuncUnit1<DoubleStream> action) {
        val stream = stream();
        try {
            action.accept(stream);
        } finally {
            stream.close();
        }
    }
    
    @Override
    public default DoubleStreamPlus filter(DoublePredicate predicate) {
        return DoubleStreamPlus.from(stream().filter(predicate));
    }
    
    @Override
    public default DoubleStreamPlus map(DoubleUnaryOperator mapper) {
        return DoubleStreamPlus.from(stream().map(mapper));
    }
    
    public default <T> Pipeable<DoubleStreamPlus> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super DoubleStreamPlus, T> piper) {
        return piper.apply(this);
    }
    
    public default <U> StreamPlus<U> mapBy(DoubleFunction<? extends U> mapper) {
        return StreamPlus.from(stream().mapToObj(mapper));
    }
    
    public default StreamPlus<Double> asStream() {
        val stream = StreamPlus.from(stream()
                .mapToObj(i -> Double.valueOf(i)));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default <U> StreamPlus<U> mapToObj(DoubleFunction<? extends U> mapper) {
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
    public default IntStreamPlus mapToInt(DoubleToIntFunction mapper) {
        return IntStreamPlus.from(stream().mapToInt(mapper));
    }
    
    @Override
    public default LongStreamPlus mapToLong(DoubleToLongFunction mapper) {
        return LongStreamPlus.from(stream().mapToLong(mapper));
    }
    
    @Override
    public default DoubleStreamPlus flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return DoubleStreamPlus.from(stream().flatMap(mapper));
    }
    
    public default DoubleStreamPlus flatMap(Function<Double, Stream<Double>> mapper) {
        return DoubleStreamPlus.from(stream()
                .mapToObj   (i -> Double.valueOf(i))
                .flatMap    (i -> mapper.apply(i))
                .mapToDouble(i -> i.doubleValue()));
    }
    
    @Override
    public default DoubleStreamPlus distinct() {
        return DoubleStreamPlus.from(stream().distinct());
    }
    
    @Override
    public default DoubleStreamPlus sorted() {
        return DoubleStreamPlus.from(stream().sorted());
    }
    
    @Override
    public default DoubleStreamPlus peek(DoubleConsumer action) {
        return DoubleStreamPlus.from(stream().peek(action));
    }
    
    @Override
    public default DoubleStreamPlus limit(long maxSize) {
        return DoubleStreamPlus.from(stream().limit(maxSize));
    }
    
    @Override
    public default DoubleStreamPlus skip(long n) {
        return DoubleStreamPlus.from(stream().skip(n));
    }
    
    @Override
    public default void forEach(DoubleConsumer action) {
        terminate(stream-> {
            stream
            .forEach(action);
        });
    }
    
    @Override
    public default void forEachOrdered(DoubleConsumer action) {
        terminate(stream-> {
            stream
            .forEachOrdered(action);
        });
    }
    
    @Override
    public default double[] toArray() {
        return terminate(stream-> {
            return stream
                    .toArray();
        });
    }
    
    @Override
    public default double reduce(double identity, DoubleBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(identity, op);
        });
    }
    
    @Override
    public default OptionalDouble reduce(DoubleBinaryOperator op) {
        return terminate(stream-> {
            return stream
                    .reduce(op);
        });
    }
    
    @Override
    public default <R> R collect(Supplier<R> supplier,
                  ObjDoubleConsumer<R> accumulator,
                  BiConsumer<R, R> combiner) {
        return terminate(stream-> {
            return stream
                    .collect(supplier, accumulator, combiner);
        });
    }
    
    @Override
    public default double sum() {
        return terminate(stream-> {
            return stream
                    .sum();
        });
    }
    
    @Override
    public default OptionalDouble min() {
        return terminate(stream-> {
            return stream
                    .min();
        });
    }
    
    @Override
    public default OptionalDouble max() {
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
    public default DoubleSummaryStatistics summaryStatistics() {
        return terminate(stream-> {
            return stream
                    .summaryStatistics();
        });
    }
    
    @Override
    public default boolean anyMatch(DoublePredicate predicate) {
        return terminate(stream-> {
            return stream
                    .anyMatch(predicate);
        });
    }
    
    @Override
    public default boolean allMatch(DoublePredicate predicate) {
        return terminate(stream-> {
            return stream
                    .allMatch(predicate);
        });
    }
    
    @Override
    public default boolean noneMatch(DoublePredicate predicate) {
        return terminate(stream-> {
            return stream
                    .noneMatch(predicate);
        });
    }
    
    @Override
    public default OptionalDouble findFirst() {
        return terminate(stream-> {
            return stream
                    .findFirst();
        });
    }
    
    @Override
    public default OptionalDouble findAny() {
        return terminate(stream-> {
            return stream
                    .findAny();
        });
    }
    
    @Override
    public default StreamPlus<Double> boxed() {
        return StreamPlus.from(stream().boxed());
    }
    
    @Override
    public default DoubleStreamPlus sequential() {
        return DoubleStreamPlus.from(stream().sequential());
    }
    
    @Override
    public default DoubleStreamPlus parallel() {
        return DoubleStreamPlus.from(stream().sequential());
    }
    
    @Override
    public default PrimitiveIterator.OfDouble iterator() {
        // TODO - Make sure close is handled properly.
        return stream().iterator();
    }
    
    @Override
    public default Spliterator.OfDouble spliterator() {
        return stream().spliterator();
    }
    
    @Override
    public default boolean isParallel() {
        return stream().isParallel();
    }
    
    @Override
    public default DoubleStreamPlus unordered() {
        return DoubleStreamPlus.from(stream().unordered());
    }
    
    @Override
    public default DoubleStreamPlus onClose(Runnable closeHandler) {
        return DoubleStreamPlus.from(stream().onClose(closeHandler));
    }
    
    @Override
    public default void close() {
        stream().close();
    }
    
    //== Additional functionalities
    
    public default StreamPlus<DoubleStreamPlus> segment(int count) {
        return segment(count, true);
    }
    public default StreamPlus<DoubleStreamPlus> segment(int count, boolean includeTail) {
//        if (count <= 1)
//            return this;
        
        val index = new AtomicInteger(0);
        return segment(data -> (index.getAndIncrement() % count) == 0, includeTail);
    }
    public default StreamPlus<DoubleStreamPlus> segment(DoublePredicate startCondition) {
        return segment(startCondition, true);
    }
    public default StreamPlus<DoubleStreamPlus> segment(DoublePredicate startCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<Double>());
        val adding = new AtomicBoolean(false);
        
        StreamPlus<DoubleStreamPlus> mainStream = StreamPlus.from(
                mapToObj(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                        val retList = list.getAndUpdate(l -> new ArrayList<Double>());
                        list.get().add(i);
                        
                        if (retList.isEmpty())
                            return null;
                        
                        return DoubleStreamPlus.from(retList.stream().mapToDouble(Double::doubleValue));
                    }
                    if (adding.get()) list.get().add(i);
                    return null;
                }))
                .filterNonNull();
        ;
        val mainSupplier = (Supplier<StreamPlus<DoubleStreamPlus>>)()->mainStream;
        if (!includeTail)
            return mainStream;
        
        val tailSupplier = (Supplier<StreamPlus<DoubleStreamPlus>>)()->{
            return StreamPlus.of(
                    DoubleStreamPlus.from(
                            list.get()
                            .stream()
                            .mapToDouble(Double::doubleValue)));
        };
        val resultStream = StreamPlus.of(
            mainSupplier,
            tailSupplier
        )
        .flatMap(Supplier::get);
        return resultStream;
    }
    
    public default StreamPlus<DoubleStreamPlus> segment(DoublePredicate startCondition, DoublePredicate endCondition) {
        return segment(startCondition, endCondition, true);
    }
    
    public default StreamPlus<DoubleStreamPlus> segment(DoublePredicate startCondition, DoublePredicate endCondition, boolean includeTail) {
        val list = new AtomicReference<>(new ArrayList<Double>());
        val adding = new AtomicBoolean(false);
        
        StreamPlus<DoubleStreamPlus> stream = StreamPlus.from(
                mapToObj(i ->{
                    if (startCondition.test(i)) {
                        adding.set(true);
                    }
                    if (includeTail && adding.get()) list.get().add(i);
                    if (endCondition.test(i)) {
                        adding.set(false);
                        val retList = list.getAndUpdate(l -> new ArrayList<Double>());
                        return DoubleStreamPlus.from(retList.stream().mapToDouble(Double::doubleValue));
                    }
                    
                    if (!includeTail && adding.get()) list.get().add(i);
                    return null;
                }))
            .filterNonNull();
        return stream;
    }
    
    public default List<Double> toList() {
        return asStream().toJavaList();
    }
    
    public default FuncList<Double> toFuncList() {
        return asStream().toImmutableList();
    }
    
    public default ImmutableList<Double> toImmutableList() {
        return asStream().toImmutableList();
    }
    
    public default List<Double> toMutableList() {
        return asStream().toMutableList();
    }
    
    public default ArrayList<Double> toArrayList() {
        return asStream().toArrayList();
    }
    
    public default Set<Double> toSet() {
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
    
    //== Calculate ==
    
    public default <A, T> T calculate(
            DoubleCollectorPlus<A, T> processor) {
        val collected = Collected.ofDouble(processor);
        forEach(each -> {
            collected.accumulate(each);
        });
        val value = collected.finish();
        return value;
    }
    
    public default <A1, A2, T1, T2> Tuple2<T1, T2> calculate(
            DoubleCollectorPlus<A1, T1> processor1, 
            DoubleCollectorPlus<A2, T2> processor2) {
        val collected1 = Collected.ofDouble(processor1);
        val collected2 = Collected.ofDouble(processor2);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        return Tuple.of(value1, value2);
    }
    
    public default <A1, A2, A3, T1, T2, T3> Tuple3<T1, T2, T3> calculate(
            DoubleCollectorPlus<A1, T1> processor1, 
            DoubleCollectorPlus<A2, T2> processor2, 
            DoubleCollectorPlus<A3, T3> processor3) {
        val collected1 = Collected.ofDouble(processor1);
        val collected2 = Collected.ofDouble(processor2);
        val collected3 = Collected.ofDouble(processor3);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        return Tuple.of(value1, value2, value3);
    }
    
    public default <A1, A2, A3, A4, T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> calculate(
            DoubleCollectorPlus<A1, T1> processor1, 
            DoubleCollectorPlus<A2, T2> processor2, 
            DoubleCollectorPlus<A3, T3> processor3, 
            DoubleCollectorPlus<A4, T4> processor4) {
        val collected1 = Collected.ofDouble(processor1);
        val collected2 = Collected.ofDouble(processor2);
        val collected3 = Collected.ofDouble(processor3);
        val collected4 = Collected.ofDouble(processor4);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        val value4 = collected4.finish();
        return Tuple.of(value1, value2, value3, value4);
    }
    
    public default <A1, A2, A3, A4, A5, T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> calculate(
            DoubleCollectorPlus<A1, T1> processor1, 
            DoubleCollectorPlus<A2, T2> processor2, 
            DoubleCollectorPlus<A3, T3> processor3, 
            DoubleCollectorPlus<A4, T4> processor4, 
            DoubleCollectorPlus<A5, T5> processor5) {
        val collected1 = Collected.ofDouble(processor1);
        val collected2 = Collected.ofDouble(processor2);
        val collected3 = Collected.ofDouble(processor3);
        val collected4 = Collected.ofDouble(processor4);
        val collected5 = Collected.ofDouble(processor5);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        val value4 = collected4.finish();
        val value5 = collected5.finish();
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    public default <A1, A2, A3, A4, A5, A6, T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> calculate(
            DoubleCollectorPlus<A1, T1> processor1, 
            DoubleCollectorPlus<A2, T2> processor2, 
            DoubleCollectorPlus<A3, T3> processor3, 
            DoubleCollectorPlus<A4, T4> processor4, 
            DoubleCollectorPlus<A5, T5> processor5, 
            DoubleCollectorPlus<A6, T6> processor6) {
        val collected1 = Collected.ofDouble(processor1);
        val collected2 = Collected.ofDouble(processor2);
        val collected3 = Collected.ofDouble(processor3);
        val collected4 = Collected.ofDouble(processor4);
        val collected5 = Collected.ofDouble(processor5);
        val collected6 = Collected.ofDouble(processor6);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        val value4 = collected4.finish();
        val value5 = collected5.finish();
        val value6 = collected6.finish();
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
}