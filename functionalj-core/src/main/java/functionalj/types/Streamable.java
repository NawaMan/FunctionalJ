package functionalj.types;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.val;

public interface Streamable<DATA, SELF extends Streamable<DATA, SELF>> extends Iterable<DATA> {
    
    public Stream<DATA> stream();
    
    public SELF streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);
    
    
    public <TARGET> Streamable<TARGET, ?> stream(Function<Stream<DATA>, Stream<TARGET>> action);
    
    public default <TARGET> Streamable<TARGET, ?> map(Function<? super DATA, ? extends TARGET> mapper) {
        return stream(stream -> stream.map(mapper));
    }
    
    public default <TARGET> Streamable<TARGET, ?> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return stream(stream -> stream.flatMap(mapper));
    }
    
    public <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __stream(Function<Stream<DATA>, Stream<TARGET>> action);
    
    public default  <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __map(Function<? super DATA, ? extends TARGET> mapper) {
        return __stream(stream -> stream.map(mapper));
    }
    
    public default <TARGET, TARGET_SELF extends Streamable<TARGET, ?>> 
            TARGET_SELF __flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return __stream(stream -> stream.flatMap(mapper));
    }
    
    
    
    public default List<DATA> toList() {
        return stream().collect(Collectors.toList());
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(stream());
    }
    
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    public default SELF filter(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(predicate));
    }
    
    public default SELF exclude(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(data -> !predicate.test(data)));
    }
    
    public default SELF filter(DATA o) {
        return __stream(stream -> stream.filter(each -> Objects.equals(o, each)));
    }
    
    public default SELF exclude(DATA o) {
        return __stream(stream -> stream.filter(each -> !Objects.equals(o, each)));
    }
    
    public default SELF filter(Collection<? super DATA> collection) {
        if (collection == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default SELF exclude(Collection<? super DATA> collection) {
        return __stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default SELF selectiveMap(Predicate<DATA> filter, Function<DATA, DATA> mapper) {
        return __stream(stream -> stream.map(each -> {
            return filter.test(each)
                    ? mapper.apply(each)
                    : each;
        }));
    }
    
    public default SELF distinct() {
        return __stream(stream -> stream.distinct());
    }
    
    public default SELF sorted() {
        return __stream(stream -> stream.sorted());
    }
    
    public default SELF sorted(Comparator<? super DATA> comparator) {
        if (comparator == null)
            return sorted();
        
        return __stream(stream -> stream.sorted(comparator));
    }
    
    public default SELF peek(Consumer<? super DATA> action) {
        if (action == null)
            return (SELF)this;
        
        return __stream(stream -> stream.peek(action));
    }
    
    public default SELF limit(Long maxSize) {
        if ((maxSize == null) || (maxSize.longValue() < 0))
            return (SELF)this;
        
        return __stream(stream -> stream.limit(maxSize));
    }
    
    public default SELF skip(Long startAt){
        if ((startAt == null) || (startAt.longValue() < 0))
            return (SELF)this;
        
        return __stream(stream -> stream.skip(startAt));
    }
    
    public default void forEach(Consumer<? super DATA> action) {
        if (action == null)
            return;
        
        stream().forEach(action);
    }
    
    public default void forEachOrdered(Consumer<? super DATA> action) {
        if (action == null)
            return;
        
        stream().forEachOrdered(action);
    }
    
    public default DATA reduce(DATA identity, BinaryOperator<DATA> accumulator) {
        return stream().reduce(identity, accumulator);
    }
    
    public default Optional<DATA> reduce(BinaryOperator<DATA> accumulator) {
        return stream().reduce(accumulator);
    }
    
    public default <U> U reduce(
                    U                              identity,
                    BiFunction<U, ? super DATA, U> accumulator,
                    BinaryOperator<U>              combiner) {
        return stream().reduce(identity, accumulator, combiner);
    }
    
    public default <R> R collect(
                    Supplier<R>                 supplier,
                    BiConsumer<R, ? super DATA> accumulator,
                    BiConsumer<R, R>            combiner) {
        return stream().collect(supplier, accumulator, combiner);
    }
    
    public default <R, A> R collect(Collector<? super DATA, A, R> collector) {
        return stream().collect(collector);
    }
    
    public default Optional<DATA> min(Comparator<? super DATA> comparator) {
        return stream().min(comparator);
    }
    
    public default Optional<DATA> max(Comparator<? super DATA> comparator) {
        return stream().max(comparator);
    }
    
    public default long count() {
        return stream().count();
    }
    
    public default boolean anyMatch(Predicate<? super DATA> predicate) {
        return stream().anyMatch(predicate);
    }
    
    public default boolean allMatch(Predicate<? super DATA> predicate) {
        return stream().allMatch(predicate);
    }
    
    public default boolean noneMatch(Predicate<? super DATA> predicate) {
        return stream().noneMatch(predicate);
    }
    
    public default Optional<DATA> findFirst() {
        return stream().findFirst();
    }
    
    public default Optional<DATA> findAny() {
        return stream().findAny();
    }
    
    public static class Helper {

        private static final Object dummy = new Object();
        
        public static <T> boolean hasAt(Stream<T> stream, long index) {
            return hasAt(stream, index, null);
        }
    
        public static <T> boolean hasAt(Stream<T> stream, long index, AtomicReference<T> resultValue) {
            // Note: It is done this way to avoid interpreting 'null' as no-value
            
            val ref = new AtomicReference<Object>(dummy);
            stream
                .skip(index)
                .peek(value -> ref.set(value))
                .findFirst()
                .orElse(null);
            
            @SuppressWarnings("unchecked")
            val value = (T)ref.get();
            val found = (dummy != value);
            
            if (resultValue != null) {
                resultValue.set(found ? value : null);
            }
            
            return found;
        }
        
    }
}
