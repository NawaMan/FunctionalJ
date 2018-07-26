package functionalj.types.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
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

import functionalj.types.list.FunctionalList;
import functionalj.types.list.IFunctionalList;
import functionalj.types.list.ImmutableList;
import functionalj.types.map.FunctionalMap;
import functionalj.types.map.ImmutableMap;
import functionalj.types.tuple.Tuple;
import functionalj.types.tuple.Tuple2;
import functionalj.types.tuple.Tuple3;
import functionalj.types.tuple.Tuple4;
import functionalj.types.tuple.Tuple5;
import functionalj.types.tuple.Tuple6;
import lombok.val;

// TODO - Double check if any of the methods can be removed as StreamPlus already have them.
// TODO - Lots of this can goes on StreamPlus

@SuppressWarnings("javadoc")
public interface Streamable<DATA, SELF extends Streamable<DATA, SELF>> 
        extends StreamPlus<DATA, SELF> {
    
    public Stream<DATA> stream();
    
    public SELF streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);
    
    
    public <TARGET> Streamable<TARGET, ?> stream(Function<Stream<DATA>, Stream<TARGET>> action);
    
    @Override
    public default void close() {
        // Unclosable.
    }
    
    public default <TARGET> Streamable<TARGET, ?> map(Function<? super DATA, ? extends TARGET> mapper) {
        return stream(stream -> stream.map(mapper));
    }
    
    public default <TARGET> Streamable<TARGET, ?> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return stream(stream -> stream.flatMap(mapper));
    }
    
    public default  <TARGET, TARGET_SELF extends StreamPlus<TARGET, ?>> 
            TARGET_SELF __map(Function<? super DATA, ? extends TARGET> mapper) {
        return __stream(stream -> stream.map(mapper));
    }
    
    public default <TARGET, TARGET_SELF extends StreamPlus<TARGET, ?>> 
            TARGET_SELF __flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        return __stream(stream -> stream.flatMap(mapper));
    }
    
    public default List<DATA> toList() {
        return stream().collect(Collectors.toList());
    }
    
    public default IFunctionalList<DATA, ?> toIFunctionalList() {
        return toImmutableList();
    }
    
    public default FunctionalList<DATA> toFunctionalList() {
        return toImmutableList();
    }
    
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(stream());
    }

    public default ArrayList<DATA> toMutableList() {
        return new ArrayList<DATA>(toList());
    }

    public default Set<DATA> toSet() {
        return new HashSet<DATA>(stream().collect(Collectors.toSet()));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key, Function<? super DATA, ? extends VALUE> mapper) {
        return __map(data -> ImmutableMap.of(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data)));
    }
    
    public default <KEY, VALUE> FunctionalList<FunctionalMap<KEY, VALUE>> toMap(
            KEY key1, Function<? super DATA, ? extends VALUE> mapper1,
            KEY key2, Function<? super DATA, ? extends VALUE> mapper2,
            KEY key3, Function<? super DATA, ? extends VALUE> mapper3,
            KEY key4, Function<? super DATA, ? extends VALUE> mapper4,
            KEY key5, Function<? super DATA, ? extends VALUE> mapper5,
            KEY key6, Function<? super DATA, ? extends VALUE> mapper6,
            KEY key7, Function<? super DATA, ? extends VALUE> mapper7,
            KEY key8, Function<? super DATA, ? extends VALUE> mapper8,
            KEY key9, Function<? super DATA, ? extends VALUE> mapper9,
            KEY key10, Function<? super DATA, ? extends VALUE> mapper10) {
        return __map(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data),
                key10, mapper10.apply(data)));
    }
    
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    public default Spliterator<DATA> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
    
    @SuppressWarnings("unchecked")
	public default SELF filter(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(predicate));
    }

    @SuppressWarnings("unchecked")
    public default SELF filter(BiFunction<? super Integer, ? super DATA, Boolean> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        val index = new AtomicInteger();
        return __stream(stream -> stream.filter(each -> predicate.apply(index.getAndIncrement(), each)));
    }
    
    @SuppressWarnings("unchecked")
	public default SELF exclude(Predicate<? super DATA> predicate) {
        if (predicate == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(data -> !predicate.test(data)));
    }
    
    public default SELF filter(DATA o) {
        return __stream(stream -> stream.filter(each -> Objects.equals(o, each)));
    }
    
    public default SELF onlyNonNull() {
        return __stream(stream -> stream.filter(Objects::nonNull));
    }
    
    public default SELF exclude(DATA o) {
        return __stream(stream -> stream.filter(each -> !Objects.equals(o, each)));
    }
    
    @SuppressWarnings("unchecked")
	public default SELF filter(Collection<? super DATA> collection) {
        if (collection == null)
            return (SELF)this;
        
        return __stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default SELF exclude(Collection<? super DATA> collection) {
        return __stream(stream -> stream.filter(data -> !collection.contains(data)));
    }
    
    public default SELF selectiveMap(Predicate<? super DATA> filter, Function<DATA, DATA> mapper) {
        return __stream(stream -> stream.map(each -> {
            return filter.test(each)
                    ? mapper.apply(each)
                    : each;
        }));
    }
    
    // TODO - Find a better ways to do it
    
    public default Tuple2<SELF, SELF> split(
            Predicate<? super DATA> predicate) {
        return Tuple.of(
                this.filter(predicate),
                this.exclude(predicate)
        );
    }
    
    public default Tuple3<SELF, SELF, SELF> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2) {
        return Tuple.of(
                this.filter(predicate1),
                this.exclude(predicate1).filter(predicate2),
                this.exclude(predicate1).exclude(predicate2)
        );
    }

    public default Tuple4<SELF, SELF, SELF, SELF> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3) {
        return Tuple.of(
                this.filter(predicate1),
                this.exclude(predicate1).filter(predicate2),
                this.exclude(predicate1).exclude(predicate2).filter(predicate3),
                this.exclude(predicate1).exclude(predicate2).exclude(predicate3)
        );
    }

    public default Tuple5<SELF, SELF, SELF, SELF, SELF> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4) {
        return Tuple.of(
                this.filter(predicate1),
                this.exclude(predicate1).filter(predicate2),
                this.exclude(predicate1).exclude(predicate2).filter(predicate3),
                this.exclude(predicate1).exclude(predicate2).exclude(predicate3).filter(predicate4),
                this.exclude(predicate1).exclude(predicate2).exclude(predicate3).exclude(predicate4)
        );
    }
    
    public default Tuple6<SELF, SELF, SELF, SELF, SELF, SELF> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4,
            Predicate<? super DATA> predicate5) {
        return Tuple.of(
                this.filter(predicate1),
                this.exclude(predicate1).filter(predicate2),
                this.exclude(predicate1).exclude(predicate2).filter(predicate3),
                this.exclude(predicate1).exclude(predicate2).exclude(predicate3).filter(predicate4),
                this.exclude(predicate1).exclude(predicate2).exclude(predicate3).exclude(predicate4).filter(predicate5),
                this.exclude(predicate1).exclude(predicate2).exclude(predicate3).exclude(predicate4).exclude(predicate5)                
        );
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
    
    @SuppressWarnings("unchecked")
	public default SELF peek(Consumer<? super DATA> action) {
        if (action == null)
            return (SELF)this;
        
        return __stream(stream -> stream.peek(action));
    }
    
    @SuppressWarnings("unchecked")
	public default SELF limit(Long maxSize) {
        if ((maxSize == null) || (maxSize.longValue() < 0))
            return (SELF)this;
        
        return __stream(stream -> stream.limit(maxSize));
    }
    
    @SuppressWarnings("unchecked")
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
    public default void forEach(BiConsumer<? super Integer, ? super DATA> action) {
        if (action == null)
            return;
        val index = new AtomicInteger();
        stream().forEach(each -> action.accept(index.getAndIncrement(), each));
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
    
    // TODO segment
    
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
