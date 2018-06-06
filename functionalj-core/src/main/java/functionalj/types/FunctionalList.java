package functionalj.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import lombok.val;

public interface FunctionalList<DATA> extends Supplier<Stream<DATA>> {
    
    public static <T> FunctionalList<T> of(Collection<T> data) {
        return new FunctionalListImpl<>(data);
    }
    
    public static <T> FunctionalList<T> of(T ... data) {
        return new FunctionalListImpl<>(Arrays.asList(data));
    }
    
    public static <T> FunctionalList<T> listOf(T ... data) {
        return new FunctionalListImpl<>(Arrays.asList(data));
    }

    public int size();

    public boolean isEmpty();

    public boolean contains(Object o);
    
    public boolean contains(Predicate<DATA> predicate);

    public Iterator<DATA> iterator();

    public Object[] toArray();

    public <T> T[] toArray(T[] a);
    
    public boolean containsAll(Collection<?> c);
    
    public DATA get(int index);
    
    public int indexOf(Object o) ;

    public int lastIndexOf(Object o);

    public ListIterator<DATA> listIterator();

    public ListIterator<DATA> listIterator(int index);

    public FunctionalList<DATA> subList(int fromIndex, int toIndex);
    
    public List<DATA> asList();
    
    public Stream<DATA> stream();
    
    // ==
    
    public default FunctionalList<DATA> set(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + " vs " + size());
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        val i = new AtomicInteger();
        return stream(stream -> stream.map(each -> (i.getAndIncrement() == index) ? value : each));
    }
    
    public default <TARGET> FunctionalList<TARGET> stream(Function<Stream<DATA>, Stream<TARGET>> theAction) {
        return new FunctionalListStream<>(this, theAction);
    }
    
    public default DATA first() {
        return stream().findFirst().get();
    }
    
    public default FunctionalList<DATA> rest() {
        return stream(stream -> stream.skip(1));
    }
    
    public default FunctionalList<DATA> append(DATA value) {
        return stream(stream -> Stream.concat(stream, Stream.of(value)));
    }
    
    public default FunctionalList<DATA> filter(Predicate<? super DATA> predicate) {
        return stream(stream -> stream.filter(predicate));
    }
    
    public default <TARGET> FunctionalList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper) {
        return stream(stream -> stream.map(mapper));
    }

    public default <TARGET> FunctionalList<TARGET> flatMap(Function<? super DATA, ? extends Stream<? extends TARGET>> mapper) {
        Function<Stream<DATA>, Stream<TARGET>> theAction  = stream -> stream.flatMap(mapper);
        FunctionalListStream<DATA, TARGET>     listStream = new FunctionalListStream<>(this, theAction);
        return listStream;
    }

//    // Selective map
////    public <TARGET> FunctionalList<TARGET> map(Function<? super DATA, ? extends TARGET> mapper);
    
    public default FunctionalList<DATA> distinct() {
        return stream(stream -> stream.distinct());
    }
    
    public default FunctionalList<DATA> sorted() {
        return stream(stream -> stream.sorted());
    }
    
    public default FunctionalList<DATA> sorted(Comparator<? super DATA> comparator) {
        return stream(stream -> stream.sorted(comparator));
    }
    
    public default FunctionalList<DATA> peek(Consumer<? super DATA> action) {
        return stream(stream -> stream.peek(action));
    }
    
    public default FunctionalList<DATA> limit(long maxSize) {
        return stream(stream -> stream.limit(maxSize));
    }
    
    public default FunctionalList<DATA> skip(long n){
        return stream(stream -> stream.skip(n));
    }
    
    public default void forEach(Consumer<? super DATA> action) {
        stream().forEach(action);
    }
    
    public default void forEachOrdered(Consumer<? super DATA> action) {
        stream().forEach(action);
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
    
}
