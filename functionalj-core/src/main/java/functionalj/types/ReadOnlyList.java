package functionalj.types;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.util.Collections.unmodifiableList;

public class ReadOnlyList<DATA> implements List<DATA> {
    
    private final List<DATA> data;
    
    public static <DATA> ReadOnlyList of(DATA ... data) {
        return new ReadOnlyList<>(asList(data));
    }
    
    public static <DATA> ReadOnlyList of(List<DATA> data) {
        if (data instanceof ReadOnlyList)
            return (ReadOnlyList)data;
        
        return new ReadOnlyList<>(data);
    }
    
    public ReadOnlyList(List<DATA> data) {
        this.data = (data instanceof ReadOnlyList) ? data : unmodifiableList(new ArrayList<>(data));
    }
    
    // TODO - Add with and append.

    @Override
    public final int size() {
        return data.size();
    }

    @Override
    public final boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public final boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public final Iterator<DATA> iterator() {
        // TODO - Block remove().
        return data.iterator();
    }

    @Override
    public final Object[] toArray() {
        // TODO - Check if leak can happen here for both cases of constructor inputs.
        return data.toArray();
    }

    @Override
    public final <T> T[] toArray(T[] a) {
        // TODO - Check if leak can happen here for both cases of constructor inputs.
        return data.toArray(a);
    }

    @Override
    public final boolean add(DATA e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public final boolean addAll(Collection<? extends DATA> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean addAll(int index, Collection<? extends DATA> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final DATA get(int index) {
        return data.get(index);
    }

    @Override
    public final DATA set(int index, DATA element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void add(int index, DATA element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final DATA remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public final int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public final ListIterator<DATA> listIterator() {
        // TODO - Block remove set add.
        return data.listIterator();
    }

    @Override
    public final ListIterator<DATA> listIterator(int index) {
        // TODO - Block remove set add.
        return data.listIterator(index);
    }

    @Override
    public final List<DATA> subList(int fromIndex, int toIndex) {
        // TODO - Make it also ReadOnlyList
        return data.subList(fromIndex, toIndex);
    }
    
}
