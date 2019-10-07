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

import static functionalj.function.Func.f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
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
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.lens.lenses.IntBiFunctionPrimitive;
import functionalj.lens.lenses.IntObjBiFunction;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.pipeable.Pipeable;
import functionalj.promise.DeferAction;
import functionalj.promise.UncompletedAction;
import functionalj.result.Result;
import functionalj.tuple.IntTuple2;
import lombok.val;

@FunctionalInterface
public interface IntStreamPlus 
        extends 
            IntStream {
    
    public static IntStreamPlus empty() {
        return IntStreamPlus.from(IntStream.empty());
    }
    
    public static IntStreamPlus emptyIntStream() {
        return IntStreamPlus.from(IntStream.empty());
    }
    
    public static IntStreamPlus of(int ... ints) {
        return IntStreamPlus.from(IntStream.of(Arrays.copyOf(ints, ints.length)));
    }
    
    public static IntStreamPlus from(IntStream intStream) {
        return ()->intStream;
    }
    
    public static IntStreamPlus repeat(int ... data) {
        return cycle(data);
    }
    
    public static IntStreamPlus cycle(int ... data) {
        val ints = Arrays.copyOf(data, data.length);
        val size = ints.length;
        return IntStreamPlus.from(
                IntStream
                .iterate(0, i -> i + 1)
                .map(i -> data[i % size]));
    }
    
    public static IntStreamPlus loop(int time) {
        return IntStreamPlus
                .infinite()
                .limit(time);
    }
    
    public static IntStreamPlus loop() {
        return IntStreamPlus
                .infinite();
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
    
    public static IntStreamPlus concat(IntStreamPlus ... streams) {
        return StreamPlus.of(streams).flatMapToInt(s -> s.stream()).mapToInt(i -> i);
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
    
    public static IntStreamPlus compound(int seed1, int seed2, IntBinaryOperator f) {
        return IntStreamPlus.from(IntStreamPlus.iterate(seed1, seed2, f));
    }
    
    // TODO - Zip?
    
    //== Stream ==
    
    public IntStream stream();
    
    //== Helper functions ==
    
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
    
    public default IntStreamPlus sequential(Func1<IntStreamPlus, IntStreamPlus> action) {
        val stream = stream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            val orgIntStreamPlus = IntStreamPlus.from(stream);
            val newIntStreamPlus = action.apply(orgIntStreamPlus);
            return newIntStreamPlus;
        }
        
        val orgIntStreamPlus = IntStreamPlus.from(stream.sequential());
        val newIntStreamPlus = action.apply(orgIntStreamPlus);
        if (newIntStreamPlus.isParallel())
            return newIntStreamPlus;
        
        return newIntStreamPlus.parallel();
    }
    
    public default <TARGET> StreamPlus<TARGET> sequentialToObj(Func1<IntStreamPlus, StreamPlus<TARGET>> action) {
        val stream = stream();
        
        val isParallel = stream.isParallel();
        if (!isParallel) {
            return action.apply(IntStreamPlus.from(stream));
        }
        
        val resultStream = action.apply(IntStreamPlus.from(stream.sequential()));
        if (resultStream.isParallel())
            return resultStream;
        
        return resultStream.parallel();
    }
    
    public default IntStreamPlus deriveWith(
            Function<IntStreamPlus, IntStreamPlus> action) {
        return IntStreamPlus.from(
                action.apply(this));
    }
    
    public default <TARGET> StreamPlus<TARGET> deriveToObjWith(
            Function<IntStreamPlus, Stream<TARGET>> action) {
        return StreamPlus.from(
                action.apply(this));
    }
    
    //== Stream sepecific ==
    
    @Override
    public default IntStreamPlus sequential() {
        return IntStreamPlus.from(stream().sequential());
    }
    
    @Override
    public default IntStreamPlus parallel() {
        return IntStreamPlus.from(stream().sequential());
    }
    
    @Override
    public default IntStreamPlus unordered() {
        return IntStreamPlus.from(stream().unordered());
    }
    
    public default boolean isParallel() {
        return stream()
                .isParallel();
    }
    
    // TODO - Think about terminate
    @Override
    public default PrimitiveIterator.OfInt iterator() {
        return stream().iterator();
    }
    
    @Override
    public default Spliterator.OfInt spliterator() {
        return stream().spliterator();
    }
    
    @Override
    public default IntStreamPlus onClose(Runnable closeHandler) {
        return IntStreamPlus
                .from(stream().onClose(closeHandler));
    }
    
    @Override
    public default void close() {
        stream()
        .close();
    }
    
    //== Functionalities ==
    
    public default IntStreamPlus mapToInt(IntUnaryOperator mapper) {
        return IntStreamPlus.from(stream().map(mapper));
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
    public default IntStreamPlus map(IntUnaryOperator mapper) {
        return IntStreamPlus.from(stream().map(mapper));
    }
    
    @Override
    public default IntStreamPlus flatMap(IntFunction<? extends IntStream> mapper) {
        return IntStreamPlus.from(stream().flatMap(mapper));
    }
    
    @Override
    public default IntStreamPlus filter(IntPredicate predicate) {
        return IntStreamPlus.from(stream().filter(predicate));
    }
    
    public default IntStreamPlus filter(IntUnaryOperator mapper, IntPredicate predicate) {
        return IntStreamPlus.from(stream().filter(i -> {
            val v = mapper.applyAsInt(i);
            val b = predicate.test(v);
            return b;
        }));
    }
    
    @Override
    public default IntStreamPlus peek(IntConsumer action) {
        return IntStreamPlus.from(stream().peek(action));
    }
    
    //-- Limit/Skip --
    
    @Override
    public default IntStreamPlus limit(long maxSize) {
        return IntStreamPlus.from(stream().limit(maxSize));
    }
    
    @Override
    public default IntStreamPlus skip(long offset) {
        return IntStreamPlus.from(stream().skip(offset));
    }
    
    public default IntStreamPlus skipWhile(IntPredicate condition) {
        return sequential(stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream
                    .filter(e -> {
                        if (!isStillTrue.get())
                            return true;
                        
                        if (!condition.test(e))
                            isStillTrue.set(false);
                        
                        return !isStillTrue.get();
                    });
        });
    }
    
    public default IntStreamPlus skipUntil(IntPredicate condition) {
        return sequential(stream -> {
            val isStillTrue = new AtomicBoolean(true);
            return stream.filter(e -> {
                if (!isStillTrue.get())
                    return true;
                if (condition.test(e))
                    isStillTrue.set(false);
                return !isStillTrue.get();
            });
        });
    }
    
    public default IntStreamPlus takeWhile(IntPredicate condition) {
        // https://stackoverflow.com/questions/32290278/picking-elements-of-a-list-until-condition-is-met-with-java-8-lambdas
        return sequential(stream -> {
            val splitr = stream.spliterator();
            return IntStreamPlus.from(
                    StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
                        boolean stillGoing = true;
                        @Override
                        public boolean tryAdvance(IntConsumer consumer) {
                            if (stillGoing) {
                                final boolean hadNext = splitr.tryAdvance((int elem) -> {
                                    if (condition.test(elem)) {
                                        consumer.accept(elem);
                                    } else {
                                        stillGoing = false;
                                    }
                                });
                                return hadNext && stillGoing;
                            }
                            return false;
                        }
                    }, false)
                );
        });
    }
    
    public default IntStreamPlus takeUntil(IntPredicate condition) {
        return sequential(stream -> {
            val splitr = stream.spliterator();
            val resultStream = StreamSupport.intStream(new Spliterators.AbstractIntSpliterator(splitr.estimateSize(), 0) {
                boolean stillGoing = true;
                
                @Override
                public boolean tryAdvance(IntConsumer consumer) {
                    if (stillGoing) {
                        final boolean hadNext = splitr.tryAdvance((int elem) -> {
                            if (!condition.test(elem)) {
                                consumer.accept(elem);
                            } else {
                                stillGoing = false;
                            }
                        });
                        return hadNext && stillGoing;
                    }
                    return false;
                }
            }, false);
            return IntStreamPlus.from(resultStream);
        });
    }
    
    @Override
    public default IntStreamPlus distinct() {
        return IntStreamPlus.from(stream().distinct());
    }
    
    @Override
    public default IntStreamPlus sorted() {
        return IntStreamPlus.from(stream().sorted());
    }
    
    public default <T extends Comparable<? super T>> IntStreamPlus sortedBy(
            IntFunction<T> mapper) {
        return IntStreamPlus.from(
                stream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .sorted  ((a,b) -> a._2.compareTo(b._2))
                .mapToInt(t     -> t._1));
    }
    
    public default <T> IntStreamPlus sortedBy(
            IntFunction<T> mapper, 
            Comparator<T>  comparator) {
        return IntStreamPlus.from(
                stream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .sorted  ((a,b) -> comparator.compare(a._2, b._2))
                .mapToInt(t     -> t._1));
    }
    
    //-- Terminate --
    
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
    
    public default <T extends Comparable<T>> OptionalInt minBy(IntFunction<T> mapper) {
        Optional<Integer> optional = stream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .min     ((a,b) -> a._2.compareTo(b._2))
                .map     (t -> t._1);
        return optional.isPresent()
                ? OptionalInt.empty()
                : OptionalInt.of(optional.get());
    }
    
    public default <T extends Comparable<T>> OptionalInt maxBy(IntFunction<T> mapper) {
        Optional<Integer> optional = stream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .max     ((a,b) -> a._2.compareTo(b._2))
                .map     (t -> t._1);
        return optional.isPresent()
                ? OptionalInt.empty()
                : OptionalInt.of(optional.get());
    }
    
    public default <T> OptionalInt minBy(IntFunction<T> mapper, Comparator<T>  comparator) {
        Optional<Integer> optional = stream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .min     ((a,b) -> comparator.compare(a._2, b._2))
                .map     (t -> t._1);
        return optional.isPresent()
                ? OptionalInt.empty()
                : OptionalInt.of(optional.get());
    }
    
    public default <T> OptionalInt maxBy(IntFunction<T> mapper, Comparator<T>  comparator) {
        Optional<Integer> optional = stream()
                .mapToObj(i     -> new IntTuple2<>(i, mapper.apply(i)))
                .max     ((a,b) -> comparator.compare(a._2, b._2))
                .map     (t -> t._1);
        return optional.isPresent()
                ? OptionalInt.empty()
                : OptionalInt.of(optional.get());
    }
    
    @Override
    public default long count() {
        return terminate(stream-> {
            return stream
                    .count();
        });
    }
    
    public default int size() {
        return terminate(stream-> {
            return (int)stream
                    .count();
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
    
    //== toXXX ==
    
    public default StreamPlus<Integer> asStream() {
        val stream = StreamPlus.from(stream()
                .mapToObj(i -> Integer.valueOf(i)));
        stream.onClose(()->{ close(); });
        return stream;
    }
    
    @Override
    public default int[] toArray() {
        return terminate(stream-> {
            return stream
                    .toArray();
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
    
    public default String toListString() {
        val strValue 
            = mapToObj(String::valueOf)
            .collect(Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
//    public default ImmutableList<DATA> toImmutableList() {
//        return terminate(stream -> {
//            return ImmutableList.from(this);
//        });
//    }
    
    //-- Iterator --
    
    /** DO NOT USE THIS METHOD OR YOUR STREAM WILL NOT BE CLOSED. */
    public default PrimitiveIterator.OfInt __iterator() {
        return IntIteratorPlus.from(this);
    }
    
    /**
     * Use iterator of this stream without terminating the stream.
     */
    public default IntStreamPlus useIterator(Func1<IntIteratorPlus, IntStreamPlus> action) {
        return sequential(stream -> {
            IntStreamPlus result = null;
            try {
                val iterator = IntIteratorPlus.from(stream).iterator();
                result = action.apply(iterator);
                return result;
            } finally {
                if (result == null) {
                    f(()->close())
                    .runCarelessly();
                } else {
                    result
                    .onClose(()->{
                        f(()->close())
                        .runCarelessly();
                    });
                }
            }
        });
    }
    
    //== Plus ==
    
    public default String joinToString() {
        val strValue 
        = mapToObj(String::valueOf)
        .collect(Collectors.joining());
    return "[" + strValue + "]";
    }
    public default String joinToString(String delimiter) {
        val strValue 
        = mapToObj(String::valueOf)
        .collect(Collectors.joining(delimiter));
    return "[" + strValue + "]";
    }
    
    //== Pipe ==
    
    public default <T> Pipeable<IntStreamPlus> pipable() {
        return Pipeable.of(this);
    }
    
    public default <T> T pipe(Function<? super IntStreamPlus, T> piper) {
        return piper.apply(this);
    }
    
    //== Spawn ==
    
    public default <T> StreamPlus<Result<T>> spawn(IntFunction<? extends UncompletedAction<T>> mapToAction) {
        return sequentialToObj(stream -> {
            val results = new ArrayList<DeferAction<T>>();
            val index   = new AtomicInteger(0);
            
            val actions 
                = stream()
                .mapToObj(mapToAction)
                .peek    (action -> results.add(DeferAction.<T>createNew()))
                .peek    (action -> action
                    .getPromise()
                    .onComplete(result -> {
                        val thisIndex  = index.getAndIncrement();
                        val thisAction = results.get(thisIndex);
                        if (result.isValue())
                             thisAction.complete(result.value());
                        else thisAction.fail    (result.exception());
                    })
                )
                .peek(action -> action.start())
                .collect(Collectors.toList())
                ;
            
            val resultStream 
                = StreamPlus
                .from(results.stream().map(action -> action.getResult()))
                ;
            resultStream
                .onClose(()->actions.forEach(action -> action.abort("Stream closed!")));
            
            return resultStream;
        });
    }
    
    public default IntStreamPlus accumulate(IntBiFunctionPrimitive accumulator) {
        return useIterator(iterator -> {
            if (!iterator.hasNext())
                return IntStreamPlus.empty();
            
            val prev = new int[] { iterator.nextInt() };
            return IntStreamPlus
                    .concat(
                        IntStreamPlus.of(prev[0]),
                        iterator.stream().map(n -> {
                            val next = accumulator.applyAsIntAndInt(n, prev[0]);
                            prev[0] = next;
                            return next;
                        })
                    );
        });
    }
    
    public default IntStreamPlus restate(IntObjBiFunction<IntStreamPlus, IntStreamPlus> restater) {
        val func = (UnaryOperator<IntTuple2<IntStreamPlus>>)((IntTuple2<IntStreamPlus> pair) -> {
            val stream = pair._2();
            if (stream == null)
                return null;
            
            val iterator = stream.__iterator();
            if (!iterator.hasNext())
                return null;
            
            val head = new int[] { iterator.nextInt() };
            val tail = IntObjBiFunction.apply(restater, head[0], IntIteratorPlus.from(iterator).stream());
            if (tail == null)
                return null;
            
            return IntTuple2.<IntStreamPlus>of(head[0], tail);
        });
        val seed = IntTuple2.<IntStreamPlus>of(0, this);
        IntStreamPlus endStream 
            = StreamPlus.iterate(seed, func)
            .takeUntil(t -> t == null)
            .skip(1)
            .mapToInt(t -> t._1());
        return endStream;
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
//    public default <T> IntStreamPlus filterBy(IntFunction<T> mapper, Predicate<T> predicate) {
//        return IntStreamPlus.from(stream().filter(i -> {
//            val v = mapper.apply(i);
//            val b = predicate.test(v);
//            return b;
//        }));
//    }
//    
//    public default <T> IntStreamPlus peekBy(IntFunction<T> mapper, Consumer<T> action) {
//        return IntStreamPlus.from(stream().peek(i->{
//            val v = mapper.apply(i);
//            action.accept(v);
//        }));
//    }
//    
//    public default <T> IntStreamPlus filterBy(IntFunction<T> mapper, BiPredicate<Integer, T> predicate) {
//        return IntStreamPlus.from(stream().filter(i -> {
//            val v = mapper.apply(i);
//            val b = IntObjBiPredicate.test(predicate, i, v);
//            return b;
//        }));
//    }
//    
//    public default <T> IntStreamPlus peekBy(IntFunction<T> mapper, BiConsumer<Integer, T> action) {
//        return IntStreamPlus.from(stream().peek(i->{
//            val v = mapper.apply(i);
//            IntObjBiConsumer.accept(action, i, v);
//        }));
//    }
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
//    
//    public default StreamPlus<IntStreamPlus> segment(int count) {
//        return segment(count, true);
//    }
//    public default StreamPlus<IntStreamPlus> segment(int count, boolean includeTail) {
////        if (count <= 1)
////            return this;
//        
//        val index = new AtomicInteger(0);
//        return segment(data -> (index.getAndIncrement() % count) == 0, includeTail);
//    }
//    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition) {
//        return segment(startCondition, true);
//    }
//    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, boolean includeTail) {
//        val list = new AtomicReference<>(new ArrayList<Integer>());
//        val adding = new AtomicBoolean(false);
//        
//        StreamPlus<IntStreamPlus> mainStream = StreamPlus.from(
//                mapToObj(i ->{
//                    if (startCondition.test(i)) {
//                        adding.set(true);
//                        val retList = list.getAndUpdate(l -> new ArrayList<Integer>());
//                        list.get().add(i);
//                        
//                        if (retList.isEmpty())
//                            return null;
//                        
//                        return IntStreamPlus.from(retList.stream().mapToInt(Integer::intValue));
//                    }
//                    if (adding.get()) list.get().add(i);
//                    return null;
//                }))
//                .filterNonNull();
//        ;
//        val mainSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->mainStream;
//        if (!includeTail)
//            return mainStream;
//        
//        val tailSupplier = (Supplier<StreamPlus<IntStreamPlus>>)()->{
//            return StreamPlus.of(
//                    IntStreamPlus.from(
//                            list.get()
//                            .stream()
//                            .mapToInt(Integer::intValue)));
//        };
//        val resultStream = StreamPlus.of(
//            mainSupplier,
//            tailSupplier
//        )
//        .flatMap(Supplier::get);
//        return resultStream;
//    }
//    
//    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition) {
//        return segment(startCondition, endCondition, true);
//    }
//    
//    public default StreamPlus<IntStreamPlus> segment(IntPredicate startCondition, IntPredicate endCondition, boolean includeTail) {
//        val list = new AtomicReference<>(new ArrayList<Integer>());
//        val adding = new AtomicBoolean(false);
//        
//        StreamPlus<IntStreamPlus> stream = StreamPlus.from(
//                mapToObj(i ->{
//                    if (startCondition.test(i)) {
//                        adding.set(true);
//                    }
//                    if (includeTail && adding.get()) list.get().add(i);
//                    if (endCondition.test(i)) {
//                        adding.set(false);
//                        val retList = list.getAndUpdate(l -> new ArrayList<Integer>());
//                        return IntStreamPlus.from(retList.stream().mapToInt(Integer::intValue));
//                    }
//                    
//                    if (!includeTail && adding.get()) list.get().add(i);
//                    return null;
//                }))
//            .filterNonNull();
//        return stream;
//    }
//    
//    // TODO - percentile (multiple percentile value) and percentileRange (invert of percentile)
//    // TODO - segmentAt, segmentAtPercentiles
    
    public default List<Integer> toList() {
        // TODO - WE may want to create IntFuncList
        return boxed().toJavaList();
    }
    
    public default FuncList<Integer> toFuncList() {
        // TODO - WE may want to create IntFuncList
        return boxed().toImmutableList();
    }
    
    public default ImmutableList<Integer> toImmutableList() {
        return boxed().toImmutableList();
    }
    
//    public default List<Integer> toMutableList() {
//        return asStream().toMutableList();
//    }
//    
//    public default ArrayList<Integer> toArrayList() {
//        return asStream().toArrayList();
//    }
//    
//    public default Set<Integer> toSet() {
//        return asStream().toSet();
//    }
//    
//    //== Plus ==
//    
//    public default String joining() {
//        return terminate(stream -> {
//            return stream()
//                .mapToObj(StrFuncs::toStr)
//                .collect(Collectors.joining());
//        });
//    }
//    public default String joining(String delimiter) {
//        return terminate(stream -> {
//            return stream()
//                .mapToObj(StrFuncs::toStr)
//                .collect(Collectors.joining(delimiter));
//        });
//    }
//    
//    //== Calculate ==
//    
//    public default <A, T> T calculate(
//            IntCollectorPlus<A, T> processor) {
//        val collected = Collected.ofInt(processor);
//        forEach(each -> {
//            collected.accumulate(each);
//        });
//        val value = collected.finish();
//        return value;
//    }
//    
//    public default <A1, A2, T1, T2> Tuple2<T1, T2> calculate(
//            IntCollectorPlus<A1, T1> processor1, 
//            IntCollectorPlus<A2, T2> processor2) {
//        val collected1 = Collected.ofInt(processor1);
//        val collected2 = Collected.ofInt(processor2);
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
//            IntCollectorPlus<A1, T1> processor1, 
//            IntCollectorPlus<A2, T2> processor2, 
//            IntCollectorPlus<A3, T3> processor3) {
//        val collected1 = Collected.ofInt(processor1);
//        val collected2 = Collected.ofInt(processor2);
//        val collected3 = Collected.ofInt(processor3);
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
//            IntCollectorPlus<A1, T1> processor1, 
//            IntCollectorPlus<A2, T2> processor2, 
//            IntCollectorPlus<A3, T3> processor3, 
//            IntCollectorPlus<A4, T4> processor4) {
//        val collected1 = Collected.ofInt(processor1);
//        val collected2 = Collected.ofInt(processor2);
//        val collected3 = Collected.ofInt(processor3);
//        val collected4 = Collected.ofInt(processor4);
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
//            IntCollectorPlus<A1, T1> processor1, 
//            IntCollectorPlus<A2, T2> processor2, 
//            IntCollectorPlus<A3, T3> processor3, 
//            IntCollectorPlus<A4, T4> processor4, 
//            IntCollectorPlus<A5, T5> processor5) {
//        val collected1 = Collected.ofInt(processor1);
//        val collected2 = Collected.ofInt(processor2);
//        val collected3 = Collected.ofInt(processor3);
//        val collected4 = Collected.ofInt(processor4);
//        val collected5 = Collected.ofInt(processor5);
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
//            IntCollectorPlus<A1, T1> processor1, 
//            IntCollectorPlus<A2, T2> processor2, 
//            IntCollectorPlus<A3, T3> processor3, 
//            IntCollectorPlus<A4, T4> processor4, 
//            IntCollectorPlus<A5, T5> processor5, 
//            IntCollectorPlus<A6, T6> processor6) {
//        val collected1 = Collected.ofInt(processor1);
//        val collected2 = Collected.ofInt(processor2);
//        val collected3 = Collected.ofInt(processor3);
//        val collected4 = Collected.ofInt(processor4);
//        val collected5 = Collected.ofInt(processor5);
//        val collected6 = Collected.ofInt(processor6);
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
