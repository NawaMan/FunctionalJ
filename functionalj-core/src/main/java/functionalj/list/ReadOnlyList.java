// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import functionalj.stream.IteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlusHelper;
import lombok.val;

@FunctionalInterface
public interface ReadOnlyList<DATA> extends List<DATA> {
    
    public static <T> ReadOnlyList<T> empty() {
        return ImmutableFuncList.empty();
    }
    
    public static <T> ReadOnlyList<T> of(Collection<T> data) {
        if (data instanceof ReadOnlyList) {
            return (ReadOnlyList<T>) data;
        }
        return ImmutableFuncList.from(data);
    }
    
    @SafeVarargs
    public static <T> ReadOnlyList<T> of(T... data) {
        return ImmutableFuncList.of(data);
    }
    
    @Override
    public StreamPlus<DATA> stream();
    
    public default ImmutableFuncList<DATA> toImmutableList() {
        return ImmutableFuncList.from(this);
    }
    
    public default List<DATA> toJavaList() {
        return this;
    }
    
    @Override
    public default IteratorPlus<DATA> iterator() {
        return IteratorPlus.from(stream());
    }
    
    // == Access list ==
    @Override
    public default int size() {
        return (int) stream().count();
    }
    
    @Override
    public default boolean isEmpty() {
        return !StreamPlusHelper.hasAt(stream(), 0);
    }
    
    @Override
    public default boolean contains(Object o) {
        return StreamPlusHelper.hasAt(stream().filter(each -> Objects.equals(each, o)), 0);
    }
    
    @Override
    public default boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(each -> stream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    @Override
    public default Object[] toArray() {
        return stream().toArray();
    }
    
    @Override
    public default <T> T[] toArray(T[] a) {
        return StreamPlus.of(stream()).toJavaList().toArray(a);
    }
    
    public default <A> A[] toArray(IntFunction<A[]> generator) {
        return stream().toArray(generator);
    }
    
    @Override
    public default DATA get(int index) {
        val ref = new AtomicReference<DATA>();
        val found = StreamPlusHelper.hasAt(this.stream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        return ref.get();
    }
    
    @Override
    public default int indexOf(Object o) {
        return stream().toJavaList().indexOf(o);
    }
    
    @Override
    public default int lastIndexOf(Object o) {
        return stream().toJavaList().lastIndexOf(o);
    }
    
    @Override
    public default ListIterator<DATA> listIterator() {
        return stream().toJavaList().listIterator();
    }
    
    @Override
    public default ListIterator<DATA> listIterator(int index) {
        return stream().toJavaList().listIterator(index);
    }
    
    @Override
    public default ReadOnlyList<DATA> subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        val subList = stream().skip(fromIndexInclusive).limit(length).collect(Collectors.toList());
        return (ReadOnlyList<DATA>) (() -> StreamPlus.from(subList.stream()));
    }
    
    @Override
    public default Spliterator<DATA> spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    @Override
    public default void forEach(Consumer<? super DATA> action) {
        if (action == null)
            return;
        stream().forEach(action);
    }
    
    // == Mutable methods are not supported.
    @Override
    public default DATA set(int index, DATA element) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default boolean add(DATA e) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default boolean remove(Object o) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default boolean addAll(Collection<? extends DATA> c) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default boolean addAll(int index, Collection<? extends DATA> c) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default boolean removeAll(Collection<?> c) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default boolean retainAll(Collection<?> c) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default void clear() {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default void add(int index, DATA element) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default DATA remove(int index) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default void replaceAll(UnaryOperator<DATA> operator) {
        throw new ReadOnlyListException(this);
    }
    
    @Override
    public default void sort(Comparator<? super DATA> c) {
        throw new ReadOnlyListException(this);
    }
}
