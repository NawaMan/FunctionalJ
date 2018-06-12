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

public interface ICanStream<DATA, SELF extends ICanStream<DATA, SELF>> extends Iterable<DATA> {
    
    public Stream<DATA> stream();
    
    public <TARGET, TARGET_SELF extends ICanStream<TARGET, TARGET_SELF>> 
            TARGET_SELF stream(Function<Stream<DATA>, Stream<TARGET>> action);
    
    public SELF streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);
    
    
    public default List<DATA> toList() {
        return stream().collect(Collectors.toList());
    }
    
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    // Streamable, Supllier<Stream>
    public default SELF append(DATA ... values) {
        return stream(stream -> Stream.concat(stream, Stream.of(values)));
    }

    public default SELF appendAll(Collection<? extends DATA> c) {
        if ((c == null) || c.isEmpty())
            return (SELF)this;
        
        return stream(stream -> Stream.concat(stream, c.stream()));
    }

    public default SELF appendAll(ICanStream<? extends DATA, ?> c) {
        if (c == null)
            return (SELF)this;
        
        return stream(stream -> Stream.concat(stream, c.stream()));
    }
    
    public default SELF appendAll(Supplier<Stream<? extends DATA>> s) {
        if (s == null)
            return (SELF)this;
        
        return stream(stream -> Stream.concat(stream, s.get()));
    }
    
    public default SELF filter(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return stream(stream -> stream.filter(predicate));
    }
    
    public default SELF exclude(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return stream(stream -> stream.filter(data -> !predicate.test(data)));
    }
    
    public default SELF filter(DATA o) {
        return stream(stream -> stream.filter(each -> Objects.equals(o, each)));
    }
    
    public default SELF exclude(DATA o) {
        return stream(stream -> stream.filter(each -> !Objects.equals(o, each)));
    }
    
    public default SELF filter(Collection<? super DATA> collection) {
        if (collection == null)
            return (SELF)this;
        
        return stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default SELF exclude(Collection<? super DATA> collection) {
        return stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default <TARGET, TARGET_SELF extends ICanStream<TARGET, TARGET_SELF>> TARGET_SELF map(Function<? super DATA, ? extends TARGET> mapper) {
        return stream(stream -> stream.map(mapper));
    }

    public default <TARGET, TARGET_SELF extends ICanStream<TARGET, TARGET_SELF>> TARGET_SELF flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return stream(stream -> stream.flatMap(mapper));
    }

//    // Selective map
    
    public default SELF distinct() {
        return stream(stream -> stream.distinct());
    }
    
    public default SELF sorted() {
        return stream(stream -> stream.sorted());
    }
    
    public default SELF sorted(Comparator<? super DATA> comparator) {
        if (comparator == null)
            return sorted();
        
        return stream(stream -> stream.sorted(comparator));
    }
    
    public default SELF peek(Consumer<? super DATA> action) {
        if (action == null)
            return (SELF)this;
        
        return stream(stream -> stream.peek(action));
    }
    
    public default SELF limit(Long maxSize) {
        if ((maxSize == null) || (maxSize.longValue() < 0))
            return (SELF)this;
        
        return stream(stream -> stream.limit(maxSize));
    }
    
    public default SELF skip(Long startAt){
        if ((startAt == null) || (startAt.longValue() < 0))
            return (SELF)this;
        
        return stream(stream -> stream.skip(startAt));
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
    
        public static <T> boolean hasFirst(Stream<T> stream) {
            return hasFirst(stream, null);
        }
    
        public static <T> boolean hasFirst(Stream<T> stream, AtomicReference<T> resultValue) {
            // Note: It is done this way to avoid interpreting 'null' as no-value
            
            val dummy = new Object();
            val ref = new AtomicReference<Object>(dummy);
            stream
            .peek(value -> ref.set(value))
            .findFirst()
            .orElse(null);
            
            @SuppressWarnings("unchecked")
            val value = (T)ref.get();
            val found = (dummy != value);
            
            if (found && (resultValue != null)) {
                resultValue.set(value);
            }
            
            return found;
        }
        
    }
}
