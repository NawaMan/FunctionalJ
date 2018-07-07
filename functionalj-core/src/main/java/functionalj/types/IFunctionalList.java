package functionalj.types;

import static functionalj.FunctionalJ.withIndex;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theObject;
import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.val;

public interface IFunctionalList<DATA, SELF extends IFunctionalList<DATA, SELF>> 
        extends Streamable<DATA, SELF> {
    
    public static <T> IFunctionalList<T, ?> empty() {
        return ImmutableList.empty();
    }
    public static <T> IFunctionalList<T, ?> of(Collection<T> data) {
        return ImmutableList.of(data);
    }
    public static <T> IFunctionalList<T, ?> of(T ... data) {
        return ImmutableList.of(data);
    }
    public static <T> IFunctionalList<T, ?> of(Streamable<T, ?> streamable) {
        return ImmutableList.of(streamable);
    }
    public static <T> IFunctionalList<T, ?> of(Stream<T> stream) {
        return new ImmutableList<T>(stream.collect(Collectors.toList()));
    }
    public static <T> IFunctionalList<T, ?> of(ReadOnlyList<T, ?> readOnlyList) {
        return ImmutableList.of(readOnlyList);
    }
    public static <T> IFunctionalList<T, ?> of(IFunctionalList<T, ?> functionalList) {
        return ImmutableList.of(functionalList);
    }
    public static <T> IFunctionalList<T, ?> of(FunctionalList<T> functionalList) {
        return ImmutableList.of(functionalList);
    }
    
    @Override
    public Stream<DATA> stream();
    
    @Override
    public default IFunctionalList<DATA, ?> toIFunctionalList() {
        return this;
    }
    
    @Override
    public default ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of((Streamable<DATA, ?>)this);
    }
    
    @Override
    public default Iterator<DATA> iterator() {
        return stream().iterator();
    }

    @Override
    public default Spliterator<DATA> spliterator() {
        return stream().spliterator();
    }
    
    public DATA get(int index);
    
    public int size();
    
    
    public default DATA first() {
        val valueRef = new AtomicReference<DATA>();
        if (!Helper.hasAt(stream(), 0, valueRef)) {
            throw new IndexOutOfBoundsException("1");
        }
        
        return valueRef.get();
    }
    
    public default SELF rest() {
        return __stream(stream -> stream.skip(1));
    }
    
    public default FunctionalList<Integer> indexesOf(Predicate<? super DATA> check) {
        return this
                .map   (withIndex((data, index)-> check.test(data) ? index : -1))
                .filter(theInteger.thatNotEqualsTo(-1))
                .toFunctionalList();
    }
    
    public default FunctionalList<IntTuple2<DATA>> select(Predicate<? super DATA> check) {
        return this
                .map   (withIndex((data, index)-> check.test(data) ? new IntTuple2<DATA>(index, data) : null))
                .filter(theObject.thatIsNotNull())
                .toFunctionalList();
    }
    
    //== Modified methods ==
    
    public default SELF append(DATA ... values) {
        return __stream(stream -> Stream.concat(stream, Stream.of(values)));
    }

    @SuppressWarnings("unchecked")
    public default SELF appendAll(Collection<? extends DATA> collection) {
        if ((collection == null) || collection.isEmpty())
            return (SELF)this;
        
        return __stream(stream -> Stream.concat(stream, collection.stream()));
    }

    @SuppressWarnings("unchecked")
    public default SELF appendAll(Streamable<? extends DATA, ?> streamable) {
        if (streamable == null)
            return (SELF)this;
        
        return __stream(stream -> Stream.concat(stream, streamable.stream()));
    }
    
    @SuppressWarnings("unchecked")
    public default SELF appendAll(Supplier<Stream<? extends DATA>> supplier) {
        if (supplier == null)
            return (SELF)this;
        
        return __stream(stream -> Stream.concat(stream, supplier.get()));
    }
    
    public default SELF with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        val i = new AtomicInteger();
        return __stream(stream -> stream.map(each -> (i.getAndIncrement() == index) ? value : each));
    }
    
    public default SELF insertAt(int index, DATA ... elements) {
        if ((elements == null) || (elements.length == 0))
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    Stream.of(elements),
                    iStream.get().skip(index + 1)));
        });
    }
    
    public default SELF insertAllAt(int index, Collection<? extends DATA> collection) {
        if ((collection == null)
          || collection.isEmpty())
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    collection.stream(),
                    iStream.get().skip(index + 1)));
        });
    }
    
    public default SELF insertAllAt(int index, Streamable<? extends DATA, ?> streamable) {
        if (streamable == null)
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return (Stream<DATA>)concat(
                    iStream.get().limit(index), concat(
                    streamable.stream(),
                    iStream.get().skip(index + 1)));
        });
    }
    
    public default SELF excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    iStream.get().limit(index), 
                    iStream.get().skip(index + 2));
        });
    }
    
    public default SELF excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(fromIndexInclusive + count));
        });
    }
    
    @SuppressWarnings("unchecked")
    public default SELF excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive
                                            + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return (SELF)this;
        
        return streamFrom((Supplier<Stream<DATA>> iStream)->{
            return concat(
                    stream().limit(fromIndexInclusive), 
                    stream().skip(toIndexExclusive + 1));
        });
    }
    
    public default <KEY> FunctionalMap<KEY, FunctionalList<DATA>> groupingBy(Function<? super DATA, ? extends KEY> classifier) {
        val theMap = new HashMap<KEY, FunctionalList<DATA>>();
        stream()
            .collect(Collectors.groupingBy(classifier))
            .forEach((key,list)->theMap.put(key, ImmutableList.of(list)));
        return ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY> FunctionalMap<KEY, DATA> toMap(Function<? super DATA, ? extends KEY> keyMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, data -> data));
        return (FunctionalMap<KEY, DATA>) ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FunctionalMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>   keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper));
        return (FunctionalMap<KEY, VALUE>) ImmutableMap.of(theMap);
    }
    
    @SuppressWarnings("unchecked")
    public default <KEY, VALUE> FunctionalMap<KEY, VALUE> toMap(
                Function<? super DATA, ? extends KEY>   keyMapper,
                Function<? super DATA, ? extends VALUE> valueMapper,
                BinaryOperator<VALUE> mergeFunction) {
        val theMap = stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
        return (FunctionalMap<KEY, VALUE>) ImmutableMap.of(theMap);
    }
    
    // toMap(String,Function) ...
    
}
