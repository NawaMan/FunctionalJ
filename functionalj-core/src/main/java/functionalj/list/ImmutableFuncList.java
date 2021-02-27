// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.Collections.unmodifiableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
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
    private static final Predicate<Integer>                    toZero        = (Integer i)            -> i  == 0;
    
    private final static ImmutableFuncList<?> EMPTY = new ImmutableFuncList<>(Collections.emptyList());
    
    /** @return an empty list */
    @SuppressWarnings("unchecked")
    public static final <T> ImmutableFuncList<T> empty() {
        return (ImmutableFuncList<T>)EMPTY;
    }
    
    /** @return the list containing the given elements */
    @SafeVarargs
    public static <T> ImmutableFuncList<T> of(T ... data) {
        return new ImmutableFuncList<>(Arrays.asList(data));
    }
    
    /** @return the list containing the given elements */
    @SafeVarargs
    public static <T> ImmutableFuncList<T> listOf(T ... data) {
        return new ImmutableFuncList<T>(Arrays.asList(data));
    }
    
    /** @return the list containing the given elements */
    public static <T> ImmutableFuncList<T> from(boolean isLazy, AsFuncList<T> funcList) {
        if (funcList == null)
            return ImmutableFuncList.empty();
        
        return new ImmutableFuncList<T>(funcList.asFuncList(), isLazy);
    }
    
    /** @return the list containing the element from the given stream */
    public static <T> ImmutableFuncList<T> from(Stream<T> stream) {
        return new ImmutableFuncList<T>(stream.collect(Collectors.toList()));
    }
    
    /** @return the list containing the element from the given stream */
    static <T> ImmutableFuncList<T> from(boolean isLazy, Stream<T> stream) {
        return new ImmutableFuncList<T>(stream.collect(Collectors.toList()), isLazy);
    }
    
    /** @return the list containing the element from the given list. */
    public static <T> ImmutableFuncList<T> from(ReadOnlyList<T> readOnlyList) {
        if (readOnlyList instanceof ImmutableFuncList)
            return (ImmutableFuncList<T>)readOnlyList;
        if (readOnlyList == null)
            return ImmutableFuncList.empty();
        
        return new ImmutableFuncList<T>(readOnlyList.toJavaList());
    }
    
    /** @return the list containing the element from the given collections. */
    public static <T> ImmutableFuncList<T> from(Collection<T> collection) {
        if (collection instanceof ImmutableFuncList)
            return (ImmutableFuncList<T>)collection;
        if (collection == null)
            return ImmutableFuncList.empty();
        if (collection instanceof FuncList) {
            val funcList = (FuncList<T>)collection;
            return new ImmutableFuncList<T>(funcList.toJavaList(), funcList.isLazy());
        }
        if (collection instanceof List) {
            val list = (List<T>)collection;
            return new ImmutableFuncList<T>(list, true);
        }
        
        val list = (List<T>)collection.stream().collect(Collectors.toList());
        return new ImmutableFuncList<T>(list, true);
    }
    
    //-- Data --
    
    private final List<DATA> data;
    private final boolean    isLazy;
    
    private volatile String  toStringCache = null;
    private volatile Integer hashcodeCache = null;
    
    //-- Constructors --
    
    ImmutableFuncList(Collection<DATA> data) {
        this(data, true);
    }
    
    ImmutableFuncList(Collection<DATA> data, boolean isLazy) {
        this.isLazy = isLazy;
        if (data == null) {
            this.data = Collections.emptyList();
        } else if (data instanceof ImmutableFuncList) {
            this.data = ((ImmutableFuncList<DATA>)data).data;
        } else {
            val list = new ArrayList<DATA>();
            data.forEach(list::add);
            this.data = unmodifiableList(list);
        }
    }
    
    @Override
    public StreamPlus<DATA> stream() {
        return StreamPlus.from(data.stream());
    }
    
    @Override
    public boolean isLazy() {
        return isLazy;
    }
    
    @Override
    public boolean isEager() {
        return !isLazy;
    }
    
    @Override
    public FuncList<DATA> lazy() {
        if (isLazy)
            return this;
        
        return new ImmutableFuncList<DATA>(data, true);
    }
    
    @Override
    public FuncList<DATA> eager() {
        if (!isLazy)
            return this;
        
        return new ImmutableFuncList<DATA>(data, false);
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
            
            toStringCache = data.toString();
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
            
            hashcodeCache
                    = mapToInt(value -> (value != null) ? Objects.hash(value) : 0)
                    .reduce(43, (hash, each) -> hash*43 + each);
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
        
        val anotherList = (FuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return FuncList.zipOf(this, anotherList, (BiFunction)zeroForEquals)
                .allMatch(toZero);
    }
    
    // -- Short cut --
    
    @Override
    public int size() {
        return data.size();
    }
    
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET> TARGET[] toArray(TARGET[] seed) {
        int count = size();
        if (seed.length != count) {
            seed = (TARGET[])Array.newInstance(seed.getClass().getComponentType(), count);
        }
        return data.toArray(seed);
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
    
    @Sequential
    @Terminal
    @Override
    public Result<DATA> firstResult() {
        return this.data.isEmpty()
                ? Result.ofNotExist()
                : Result.ofValue(this.data.get(0));
    }
    
    @Sequential
    @Terminal
    @Override
    public Result<DATA> lastResult() {
        int size = this.data.size();
        return (size == 0)
                ? Result.ofNotExist()
                : Result.ofValue(this.data.get(size - 1));
    }
    
}
