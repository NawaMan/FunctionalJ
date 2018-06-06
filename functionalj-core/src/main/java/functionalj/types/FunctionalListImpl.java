package functionalj.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;

import lombok.val;

public class FunctionalListImpl<DATA> implements FunctionalList<DATA> {
    
    private final List<DATA> data;
    
    public FunctionalListImpl(Collection<DATA> data) {
        if (data == null) {
            this.data = Collections.emptyList();
        } else if (data instanceof ReadOnlyList) {
            this.data = (ReadOnlyList)data;
        } else {
            val list = new ArrayList<DATA>(data.size());
            list.addAll(data);
            this.data = unmodifiableList(list);
        }
    }
    
    @Override
    public Stream<DATA> stream() {
        return data.stream();
    }
    @Override
    public Stream<DATA> get() {
        return stream();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public boolean contains(Predicate<DATA> predicate) {
        return data.stream().anyMatch(predicate);
    }

    @Override
    public Iterator<DATA> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <TARGET> TARGET[] toArray(TARGET[] seed) {
        return data.toArray(seed);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public DATA get(int index) {
        return data.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public ListIterator<DATA> listIterator() {
        return data.listIterator();
    }

    @Override
    public ListIterator<DATA> listIterator(int index) {
        return data.listIterator();
    }

    @Override
    public List<DATA> asList() {
        return data;
    }

    @Override
    public FunctionalList<DATA> subList(int fromIndex, int toIndex) {
        if ((fromIndex < 0) || (fromIndex >= data.size()))
            throw new ArrayIndexOutOfBoundsException(fromIndex);
        if ((toIndex < 0) || (toIndex >= data.size()))
            throw new ArrayIndexOutOfBoundsException(toIndex);
        
        if ((fromIndex == 0) && (toIndex == data.size()))
            return this;
        
        Function<Stream<DATA>, Stream<DATA>> theAction  = stream -> stream.skip(fromIndex).limit(toIndex - fromIndex);
        FunctionalListStream<DATA, DATA>     listStream = new FunctionalListStream<DATA, DATA>(this.data, theAction);
        return listStream;
    }
    
    @Override
    public String toString() {
        return this.data.toString();
    }
    
}
