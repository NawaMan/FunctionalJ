package functionalj.types;

import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.val;

public interface FunctionalList<DATA, SELF extends FunctionalList<DATA, SELF>> 
        extends Streamable<DATA, SELF> {
    
    public static <T> FunctionalList<T, ?> empty() {
        return ImmutableList.empty();
    }
    public static <T> FunctionalList<T, ?> of(Collection<T> data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T, ?> of(T ... data) {
        return ImmutableList.of(data);
    }
    public static <T> FunctionalList<T, ?> of(Streamable<T, ?> streamable) {
        return ImmutableList.of(streamable);
    }
    public static <T> FunctionalList<T, ?> of(ReadOnlyList<T, ?> readOnlyList) {
        return ImmutableList.of(readOnlyList);
    }
    public static <T> FunctionalList<T, ?> of(FunctionalList<T, ?> functionalList) {
        return ImmutableList.of(functionalList);
    }
    public static <T> FunctionalList<T, ?> of(AbstractFunctionalList<T> functionalList) {
        return ImmutableList.of(functionalList);
    }
    
    @Override
    public Stream<DATA> stream();

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
        return stream(stream -> stream.skip(1));
    }
    
    //== Modified methods ==
    
    public default SELF append(DATA ... values) {
        return stream(stream -> Stream.concat(stream, Stream.of(values)));
    }

    public default SELF appendAll(Collection<? extends DATA> c) {
        if ((c == null) || c.isEmpty())
            return (SELF)this;
        
        return stream(stream -> Stream.concat(stream, c.stream()));
    }

    public default SELF appendAll(Streamable<? extends DATA, ?> c) {
        if (c == null)
            return (SELF)this;
        
        return stream(stream -> Stream.concat(stream, c.stream()));
    }
    
    public default SELF appendAll(Supplier<Stream<? extends DATA>> s) {
        if (s == null)
            return (SELF)this;
        
        return stream(stream -> Stream.concat(stream, s.get()));
    }
    
    public default SELF with(int index, DATA value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        
        val i = new AtomicInteger();
        return stream(stream -> stream.map(each -> (i.getAndIncrement() == index) ? value : each));
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
    
}
