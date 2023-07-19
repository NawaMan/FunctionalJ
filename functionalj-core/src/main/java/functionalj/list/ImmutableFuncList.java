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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import functionalj.function.ObjectObjectToIntegerFunction;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;

// TODO - Override methods in FuncListWithMapGroup to make it faster
// TODO - Override methods in FuncListWithMapWithIndex to make it faster
public final class ImmutableFuncList<DATA> implements FuncList<DATA> {
    
    private static final ObjectObjectToIntegerFunction<Object, Object> zeroForEquals = (Object i1, Object i2) -> Objects.equals(i1, i2) ? 0 : 1;
    
    private static final Predicate<Integer> toZero = (Integer i) -> i == 0;
    
    private final static ImmutableFuncList<?> EMPTY = new ImmutableFuncList<>(Collections.emptyList(), 0);
    
    /**
     * @return an empty list
     */
    @SuppressWarnings("unchecked")
    public static final <T> ImmutableFuncList<T> empty() {
        return (ImmutableFuncList<T>) EMPTY;
    }
    
    /**
     * @return the list containing the given elements
     */
    @SafeVarargs
    public static <T> ImmutableFuncList<T> of(T... data) {
        val list = new ArrayList<T>(data.length);
        for (val each : data) list.add(each);
        return new ImmutableFuncList<>(list, list.size());
    }
    
    /**
     * @return the list containing the given elements
     */
    @SafeVarargs
    public static <T> ImmutableFuncList<T> listOf(T... data) {
        val list = new ArrayList<T>(data.length);
        for (val each : data) list.add(each);
        return new ImmutableFuncList<T>(list, list.size());
    }
    
    /**
     * Create a FuncList from the given array.
     */
    public static <TARGET> ImmutableFuncList<TARGET> from(TARGET[] datas) {
        return from(Mode.lazy, datas);
    }
    
    /**
     * Create a FuncList from the given array.
     */
    public static <TARGET> ImmutableFuncList<TARGET> from(Mode mode, TARGET[] datas) {
        return ImmutableFuncList.of(datas);
    }
    
    /**
     * @return the list containing the given elements
     */
    public static <T> FuncList<T> from(Mode mode, AsFuncList<T> funcList) {
        if (funcList == null)
            return ImmutableFuncList.empty();
        FuncList<T> list = funcList.asFuncList();
        return new ImmutableFuncList<T>(list, list.size(), mode);
    }
    
    /**
     * @return the list containing the element from the given stream
     */
    public static <T> ImmutableFuncList<T> from(Stream<T> stream) {
        val list = stream.collect(Collectors.toList());
        return new ImmutableFuncList<T>(list, list.size());
    }
    
    /**
     * @return the list containing the element from the given stream
     */
    static <T> FuncList<T> from(Mode mode, Stream<T> stream) {
        val list = stream.collect(Collectors.toList());
        return new ImmutableFuncList<T>(list, list.size(), mode);
    }
    
    /**
     * @return the list containing the element from the given list.
     */
    public static <T> ImmutableFuncList<T> from(ReadOnlyList<T> readOnlyList) {
        if (readOnlyList instanceof ImmutableFuncList)
            return (ImmutableFuncList<T>) readOnlyList;
        if (readOnlyList == null)
            return ImmutableFuncList.empty();
        val list = readOnlyList.toJavaList();
        return new ImmutableFuncList<T>(list, list.size());
    }
    
    /**
     * @return the list containing the element from the given collections.
     */
    public static <T> ImmutableFuncList<T> from(Collection<T> collection) {
        if (collection instanceof ImmutableFuncList)
            return (ImmutableFuncList<T>) collection;
        if (collection == null)
            return ImmutableFuncList.empty();
        if (collection instanceof FuncList) {
            val funcList = (FuncList<T>) collection;
            return new ImmutableFuncList<T>(funcList.toJavaList(), funcList.size(), funcList.mode());
        }
        if (collection instanceof List) {
            val list = (List<T>) collection;
            return new ImmutableFuncList<T>(list, list.size(), Mode.lazy);
        }
        val list = (List<T>) collection.stream().collect(Collectors.toList());
        return new ImmutableFuncList<T>(list, list.size(), Mode.lazy);
    }
    
    // -- Data --
    private final List<DATA> data;
    
    private final FuncList.Mode mode;
    
    private final int size;
    
    private volatile String toStringCache = null;
    
    private volatile Integer hashcodeCache = null;
    
    // -- Constructors --
    ImmutableFuncList(Collection<DATA> data, int size) {
        this(data, size, Mode.lazy);
    }
    
    ImmutableFuncList(Collection<DATA> data, int size, Mode mode) {
        if (data == null) {
            this.data = Collections.emptyList();
        } else if (data instanceof ImmutableFuncList) {
            this.data = ((ImmutableFuncList<DATA>) data).data;
        } else {
            val list = new ArrayList<DATA>();
            data.forEach(list::add);
            this.data = list;
        }
        this.size = (size != -1) ? size : this.data.size();
        this.mode = mode;
    }
    
    @Override
    public StreamPlus<DATA> stream() {
        if (size == -1) {
            return StreamPlus.from(data.stream());
        } else {
            return StreamPlus.from(data.stream().limit(size));
        }
    }
    
    @Override
    public Mode mode() {
        return mode;
    }
    
    @Override
    public FuncList<DATA> toLazy() {
        if (mode().isLazy())
            return this;
        // Do this to not duplicate the data
        return new ImmutableFuncList<DATA>(data, size, Mode.lazy);
    }
    
    @Override
    public FuncList<DATA> toEager() {
        if (mode().isEager())
            return this;
        // Do this to not duplicate the data
        return new ImmutableFuncList<DATA>(data, size, Mode.eager);
    }
    
    @Override
    public FuncList<DATA> toCache() {
        if (mode().isCache())
            return this;
        // Do this to not duplicate the data
        return new ImmutableFuncList<DATA>(data, size, Mode.cache);
    }
    
    @Override
    public ImmutableFuncList<DATA> toImmutableList() {
        return this;
    }
    
    @Override
    public String toString() {
        if (toStringCache != null)
            return toStringCache;
        synchronized (this) {
            if (toStringCache != null)
                return toStringCache;
            toStringCache = StreamPlus.from(data.stream().limit(size)).toListString();
            return toStringCache;
        }
    }
    
    @Override
    public int hashCode() {
        if (hashcodeCache != null)
            return hashcodeCache;
        synchronized (this) {
            if (hashcodeCache != null)
                return hashcodeCache;
            hashcodeCache = mapToInt(value -> (value != null) ? Objects.hash(value) : 0).limit(size).reduce(43, (hash, each) -> hash * 43 + each);
            return hashcodeCache;
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FuncList))
            return false;
        if (hashCode() != o.hashCode())
            return false;
        val anotherList = (FuncList) o;
        if (size() != anotherList.size())
            return false;
        return FuncList.zipOf(this, anotherList, (BiFunction) zeroForEquals).allMatch(toZero);
    }
    
    // -- Short cut --
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET> TARGET[] toArray(TARGET[] seed) {
        if (seed.length != size) {
            seed = (TARGET[]) Array.newInstance(seed.getClass().getComponentType(), size);
        }
        val seedArray = seed;
        val streamPlus = streamPlus();
        streamPlus.limit(Math.min(size, seed.length - 0)).forEachWithIndex((index, element) -> {
            seedArray[index] = (TARGET) element;
        });
        return seed;
    }
    
    @Override
    public DATA get(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data.get(index);
    }
    
    @Override
    public int indexOf(Object o) {
        int index = data.indexOf(o);
        if (index >= size) {
            return -1;
        }
        return index;
    }
    
    @Override
    public int lastIndexOf(Object o) {
        // TODO - Improve this efficiency
        return data.subList(0, size).lastIndexOf(o);
    }
    
    @Override
    public ListIterator<DATA> listIterator() {
        // TODO - Improve this efficiency
        return data.subList(0, size).listIterator();
    }
    
    @Override
    public ListIterator<DATA> listIterator(int index) {
        // TODO - Improve this efficiency
        return data.subList(0, size).listIterator();
    }
    
    @Sequential
    @Terminal
    @Override
    public Result<DATA> firstResult() {
        return (size == 0) ? Result.ofNotExist() : Result.ofValue(this.data.get(0));
    }
    
    @Sequential
    @Terminal
    @Override
    public Result<DATA> lastResult() {
        return (size == 0) ? Result.ofNotExist() : Result.ofValue(this.data.get(size - 1));
    }
    
    // -- Append
    /**
     * Add the given value to the end of the list.
     * This method is for convenient. It is not really efficient if used to add a lot of data.
     */
    public FuncList<DATA> append(DATA value) {
        if (this == EMPTY) {
            List<DATA> list = new ArrayList<DATA>();
            list.add(value);
            return new ImmutableFuncList<DATA>(list, 1, mode());
        }
        return syncIf(() -> (data instanceof ArrayList) && (size == data.size()), () -> {
            data.add(value);
            return new ImmutableFuncList<>(data, data.size(), mode());
        }, () -> {
            return FuncList.super.append(value);
        });
    }
    
    /**
     * Add the given values to the end of the list.
     */
    @SuppressWarnings("unchecked")
    public FuncList<DATA> appendAll(DATA... values) {
        // if (this == EMPTY) {
        // val list = new ArrayList<DATA>(values.length);
        // for (DATA value : values) {
        // list.add(value);
        // }
        // return new ImmutableFuncList<DATA>(list, list.size(), isLazy);
        // }
        return syncIf(() -> (data instanceof ArrayList) && (size == data.size()), () -> {
            for (DATA value : values) {
                data.add(value);
            }
            return new ImmutableFuncList<>(data, data.size(), mode());
        }, () -> {
            return FuncList.super.appendAll(values);
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public FuncList<DATA> appendAll(Collection<? extends DATA> collection) {
        // if (this == EMPTY) {
        // val list = new ArrayList<DATA>(collection.size());
        // list.addAll(collection);
        // return new ImmutableFuncList<DATA>(list, list.size(), isLazy);
        // }
        return syncIf(() -> (data instanceof ArrayList) && (size == data.size()), () -> {
            data.addAll(collection);
            return new ImmutableFuncList<>(data, data.size(), mode());
        }, () -> {
            return FuncList.super.appendAll(collection);
        });
    }
    
    private FuncList<DATA> syncIf(BooleanSupplier condition, Supplier<FuncList<DATA>> matchAction, Supplier<FuncList<DATA>> elseAction) {
        synchronized (this) {
            if (condition.getAsBoolean()) {
                return matchAction.get();
            }
        }
        return elseAction.get();
    }
}
