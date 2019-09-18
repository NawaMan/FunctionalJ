package functionalj.stream;

import static functionalj.tuple.Tuple.tuple2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.FuncUnit1;
import functionalj.list.FuncList;
import functionalj.list.ImmutableList;
import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface StreamPlusAdditionalTerminalOperators<DATA> {
    
    public Stream<DATA> stream();
    
    
    public <TARGET> StreamPlus<TARGET> deriveWith(
            Function<Stream<DATA>, Stream<TARGET>> action);
    
    
    public StreamPlus<DATA> filter(Predicate<? super DATA> predicate);
    
    public <T> StreamPlus<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition);
    
    public <TARGET> TARGET terminate(Func1<Stream<DATA>, TARGET> action);
    
    public void terminate(FuncUnit1<Stream<DATA>> action);
    
    
    //-- Functionalities --
    
    public default void forEachWithIndex(BiConsumer<? super Integer, ? super DATA> action) {
        terminate(stream -> {
            if (action == null)
                return;
            
            val index = new AtomicInteger();
            stream
            .forEach(each -> action.accept(index.getAndIncrement(), each));
        });
    }
    
    //-- Sorted --
    
    public default <T extends Comparable<? super T>> StreamPlus<DATA> sortedBy(Function<? super DATA, T> mapper) {
        return deriveWith(stream -> {
            return stream
                    .sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return vA.compareTo(vB);
                    });
        });
    }
    
    public default <T> StreamPlus<DATA> sortedBy(Function<? super DATA, T> mapper, Comparator<T> comparator) {
        return deriveWith(stream -> {
            return stream
                    .sorted((a, b) -> {
                        T vA = mapper.apply(a);
                        T vB = mapper.apply(b);
                        return Objects.compare(vA,  vB, comparator);
                    });
        });
    }
    
    //-- groupingBy --
    
    // Eager
    public default <KEY> FuncMap<KEY, FuncList<DATA>> groupingBy(
            Function<? super DATA, ? extends KEY> classifier) {
        return terminate(stream -> {
            val theMap = new HashMap<KEY, FuncList<DATA>>();
            stream
                .collect(Collectors.groupingBy(classifier))
                .forEach((key,list)->theMap.put(key, ImmutableList.from(list)));
            return ImmutableMap.from(theMap);
        });
    }
    
    // Eager
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY>   classifier,
            Function<? super FuncList<DATA>, VALUE> aggregate) {
        return terminate(stream -> {
            val theMap = new HashMap<KEY, VALUE>();
            stream
                .collect(Collectors.groupingBy(classifier))
                .forEach((key,list) -> {
                    val valueList      = ImmutableList.from(list);
                    val aggregateValue = aggregate.apply(valueList);
                    theMap.put(key, aggregateValue);
                });
            return ImmutableMap.from(theMap);
        });
    }
    
    // Eager
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, ? extends KEY> classifier,
            StreamProcessor<? super DATA, VALUE>  processor) {
        Function<? super FuncList<DATA>, VALUE> aggregate = (FuncList<DATA> list) -> {
            return (VALUE)processor.process((StreamPlus)list.stream());
        };
        return groupingBy(classifier, aggregate);
    }
    
    // Eager
    public default <KEY, TARGET> FuncMap<KEY, TARGET> groupingBy(
            Function<? super DATA, ? extends KEY> classifier,
            Supplier<StreamElementProcessor<? super DATA, TARGET>> processorSupplier) {
        val theMap   = new ConcurrentHashMap<KEY, Tuple2<StreamElementProcessor<? super DATA, TARGET>, LongAdder>>();
        val supplier = Objects.requireNonNull(processorSupplier);
        forEachWithIndex((index, each)->{
            val key  = classifier.apply(each);
            val pair = theMap.computeIfAbsent(key, __-> tuple2(supplier.get(), new LongAdder()));
            
            val processor = pair._1();
            processor.processElement(index, each);
            
            val counter = pair._2();
            counter.increment();
        });
        
        val mapBuilder = FuncMap.<KEY, TARGET>newBuilder();
        theMap
            .forEach((key, pair) -> {
                val processor = pair._1();
                val counter   = pair._2();
                val count     = counter.longValue();
                val target    = processor.processComplete(count);
                mapBuilder.with(key, target);
            });
        val map = mapBuilder.build();
        return map;
    }
    
    //-- min-max --
    
    public default <D extends Comparable<D>> Optional<DATA> minBy(
            Func1<DATA, D> mapper) {
        return terminate(stream -> {
            return stream
                    .min((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
        });
    }
    
    public default <D extends Comparable<D>> Optional<DATA> maxBy(
            Func1<DATA, D> mapper) {
        return terminate(stream -> {
            return stream
                    .max((a,b)->mapper.apply(a).compareTo(mapper.apply(b)));
        });
    }
    
    public default <D> Optional<DATA> minBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate(stream -> {
            return stream
                    .min((a,b)->comparator.compare(mapper.apply(a), mapper.apply(b)));
        });
    }
    
    public default <D> Optional<DATA> maxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate(stream -> {
            return stream
                    .max((a,b)->comparator.compare(mapper.apply(a), mapper.apply(b)));
        });
    }
    
    @SuppressWarnings("unchecked")
    public default Tuple2<Optional<DATA>, Optional<DATA>> minMax(
            Comparator<? super DATA> comparator) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
            stream
                .sorted(comparator)
                .forEach(each -> {
                    minRef.compareAndSet(StreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            return Tuple2.of(
                    StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                    StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <D extends Comparable<D>> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D> mapper) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
            StreamPlus.from(stream)
                .sortedBy(mapper)
                .forEach(each -> {
                    minRef.compareAndSet(StreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            return tuple2(
                    StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                    StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <D> Tuple2<Optional<DATA>, Optional<DATA>> minMaxBy(
            Func1<DATA, D>        mapper, 
            Comparator<? super D> comparator) {
        return terminate(stream -> {
            val minRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
            val maxRef = new AtomicReference<Object>(StreamPlusHelper.dummy);
            StreamPlus.from(stream)
                .sortedBy(mapper, (i1, i2)->comparator.compare(i1, i2))
                .forEach(each -> {
                    minRef.compareAndSet(StreamPlusHelper.dummy, each);
                    maxRef.set(each);
                });
            val min = minRef.get();
            val max = maxRef.get();
            return tuple2(
                    StreamPlusHelper.dummy.equals(min) ? Optional.empty() : Optional.ofNullable((DATA)min),
                    StreamPlusHelper.dummy.equals(max) ? Optional.empty() : Optional.ofNullable((DATA)max));
        });
    }
    
    //-- Find --
    
    public default Optional<DATA> findFirst(Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream
                    .filter(predicate)
                    .findFirst();
        });
    }
    
    public default Optional<DATA> findAny(Predicate<? super DATA> predicate) {
        return terminate(stream -> {
            return stream
                    .filter(predicate)
                    .findAny();
        });
    }
    
    public default <T> Optional<DATA> findFirst(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(mapper, theCondition)
                .findFirst();
    }
    
    public default <T>  Optional<DATA> findAny(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return filter(mapper, theCondition)
                .findAny();
    }
    
    //-- toMap --
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, ? extends KEY> keyMapper) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, data -> data));
            return (FuncMap<KEY, DATA>)ImmutableMap.from(theMap);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, ? extends KEY>  keyMapper,
            Function<? super DATA, ? extends VALUE> valueMapper) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, valueMapper));
            return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FuncMap<KEY, VALUE> toMap(
            Function<? super DATA, ? extends KEY>   keyMapper,
            Function<? super DATA, ? extends VALUE> valueMapper,
            BinaryOperator<VALUE>                   mergeFunction) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
            return (FuncMap<KEY, VALUE>) ImmutableMap.from(theMap);
        });
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY> FuncMap<KEY, DATA> toMap(
            Function<? super DATA, ? extends KEY> keyMapper,
            BinaryOperator<DATA>                  mergeFunction) {
        return terminate(stream -> {
            val theMap = stream.collect(Collectors.toMap(keyMapper, value -> value, mergeFunction));
            return (FuncMap<KEY, DATA>) ImmutableMap.from(theMap);
        });
    }
}
