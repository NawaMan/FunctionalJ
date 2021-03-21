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
package functionalj.list.intlist;

import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BooleanSupplier;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import functionalj.list.FuncList;
import functionalj.list.FuncList.Mode;
import functionalj.stream.intstream.GrowOnlyIntArray;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;


//TODO - Override methods in FuncListWithMapGroup to make it faster
//TODO - Override methods in FuncListWithMapWithIndex to make it faster


public class ImmutableIntFuncList implements IntFuncList {
    
    private static final IntBinaryOperator zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate      toZero        = (int i)          -> i  == 0;
    
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    
    private static ImmutableIntFuncList emptyList = new ImmutableIntFuncList(EMPTY_INT_ARRAY, 0, Mode.lazy);
    
    /** @return an empty list */
    public static ImmutableIntFuncList empty() {
        return emptyList;
    }
    
    /** @return an empty list */
    public static ImmutableIntFuncList emptyIntList() {
        return emptyList;
    }
    
    /** @return the list containing the given elements */
    public static ImmutableIntFuncList of(int ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;
       
        val newArray = source.clone();
        return new ImmutableIntFuncList(newArray, newArray.length, Mode.lazy);
    }
    
    /** @return the list containing the given elements */
    public static ImmutableIntFuncList listOf(int ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;
       
        val newArray = source.clone();
        return new ImmutableIntFuncList(newArray, newArray.length, Mode.lazy);
    }
    
    public static ImmutableIntFuncList from(int[] data) {
        return from(Mode.lazy, data);
    }
    
    public static ImmutableIntFuncList from(Mode mode, int[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;
        
        val array = data.clone();
        return new ImmutableIntFuncList(array, array.length, mode);
    }
    
    /** @return the list containing the given elements */
    public static ImmutableIntFuncList from(Mode mode, AsIntFuncList funcList) {
        if (funcList == null)
            return emptyList;
        
        if (funcList instanceof ImmutableIntFuncList)
            if (funcList.asIntFuncList().isLazy())
                return (ImmutableIntFuncList)funcList;
        
        int[] data = funcList.toArray();
        return new ImmutableIntFuncList(data, data.length, mode);
    }
    
    /** @return the list containing the element from the given stream */
    public static ImmutableIntFuncList from(IntStream source) {
        if ((source == null))
            return emptyList;
        
        int[] array = source.toArray();
        return new ImmutableIntFuncList(array, array.length, Mode.lazy);
    }
    
    /** @return the list containing the element from the given stream */
    public static ImmutableIntFuncList from(Mode mode, IntStream source) {
        if ((source == null))
            return emptyList;
        
        int[] array = source.toArray();
        return new ImmutableIntFuncList(array, array.length, mode);
    }
    
    /** @return the list containing the element from the given list. */
    public static ImmutableIntFuncList from(AsIntFuncList funcList) {
        if (funcList == null)
            return emptyList;
        
        if (funcList instanceof ImmutableIntFuncList)
            return (ImmutableIntFuncList)funcList;
        
        val mode = funcList.asIntFuncList().mode();
        return ImmutableIntFuncList.from(mode, funcList);
    }
    
    /** @return the list containing the element from the given collections. */
    public static ImmutableIntFuncList from(Collection<Integer> collection, int valueForNull) {
        val ints     = new int[collection.size()];
        val iterator = collection.iterator();
        for (int i = 0; (i < ints.length) && iterator.hasNext(); i++) {
            Integer integer = iterator.next();
            ints[i] = (integer != null) ? integer.intValue() : valueForNull;
        }
        return new ImmutableIntFuncList(ints, ints.length, Mode.lazy);
    }
    
    //-- Data --
    
    private final GrowOnlyIntArray data;
    private final FuncList.Mode    mode;
    private final int              size;
    
    private volatile String  toStringCache = null;
    private volatile Integer hashcodeCache = null;
    
    //-- Constructors --
    
    ImmutableIntFuncList(int[] data, int size) {
        this(data, size, Mode.lazy);
    }
    
    ImmutableIntFuncList(int[] data, int size, Mode mode) {
        this.data = new GrowOnlyIntArray((data == null) ? EMPTY_INT_ARRAY : data);
        this.mode = mode;
        this.size = size;
    }
    
    ImmutableIntFuncList(GrowOnlyIntArray data, int size, Mode mode) {
        this.data = data;
        this.mode = mode;
        this.size = size;
    }
    
    ImmutableIntFuncList(IntFuncList list, int size, Mode mode) {
        this.mode = mode;
        this.size = size;
        this.data = (list instanceof ImmutableIntFuncList) ? ((ImmutableIntFuncList)list).data : new GrowOnlyIntArray(list.toArray());
    }
    
    @Override
    public IntStreamPlus intStream() {
        if (size ==-1) {
            return IntStreamPlus.from(data.stream());
        } else {
            return IntStreamPlus.infinite().limit(size).map(i -> data.get(i));
        }
    }
    
    @Override
    public Mode mode() {
        return mode;
    }
    
    @Override
    public IntFuncList toLazy() {
        if (mode().isLazy())
            return this;
        
        // Do this to not duplicate the data
        return new ImmutableIntFuncList(data, size, Mode.lazy);
    }
    
    @Override
    public IntFuncList toEager() {
        if (mode().isEager())
            return this;
        
        // Do this to not duplicate the data
        return new ImmutableIntFuncList(data, size, Mode.eager);
    }
    
    @Override
    public IntFuncList toCache() {
        if (mode().isCache())
            return this;
        
        // Do this to not duplicate the data
        return new ImmutableIntFuncList(data, size, Mode.cache);
    }
    
    @Override
    public ImmutableIntFuncList toImmutableList() {
        return this;
    }
    
    @Override
    public String toString() {
        if (toStringCache != null)
            return toStringCache;
        
        synchronized (this) {
            if (toStringCache != null)
                return toStringCache;
            
            toStringCache = IntStreamPlus.from(data.stream().limit(size)).toListString();
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
            
            hashcodeCache = limit(size).reduce(43, (hash, each) -> hash*43 + each);
            return hashcodeCache;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntFuncList))
            return false;
        
        if (hashCode() != o.hashCode())
            return false;
        
        val anotherList = (IntFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return IntFuncList.zipOf(this, anotherList, zeroForEquals)
                .allMatch(toZero);
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
    
    @Override
    public int[] toArray() {
        return data.stream().toArray();
    }
    
    @Override
    public int get(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data.get(index);
    }
    
    @Override
    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (value == data.get(i))
                return i;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(int value) {
        for (int i = size; i-->0;) {
            if (value == data.get(i))
                return i;
        }
        return -1;
    }
    
    @Sequential
    @Terminal
    @Override
    public OptionalInt firstResult() {
        return (this.size == 0)
                ? OptionalInt.empty()
                : OptionalInt.of(this.data.get(0));
    }
    
    @Sequential
    @Terminal
    @Override
    public OptionalInt lastResult() {
        return (size == 0)
                ? OptionalInt.empty()
                : OptionalInt.of(this.data.get(size - 1));
    }
    
    //-- Append
    
    /**
     * Add the given value to the end of the list.
     * This method is for convenient. It is not really efficient if used to add a lot of data.
     **/
    public IntFuncList append(int value) {
        if (this == emptyList) {
            GrowOnlyIntArray list = new GrowOnlyIntArray();
            list.add(value);
            return new ImmutableIntFuncList(list, 1, mode);
        }
        
        return syncIf(
                () ->(size == data.length()), 
                ()-> {
                    data.add(value);
                    return new ImmutableIntFuncList(data, data.length(), mode);
                },
                () -> {
                    return IntFuncList.super.append(value);
                });
    }
    
    /** Add the given values to the end of the list. */
    public IntFuncList appendAll(int ... values) {
        if (this == emptyList) {
            GrowOnlyIntArray list = new GrowOnlyIntArray();
            for (int value : values) {
                list.add(value);
            }
            return new ImmutableIntFuncList(list, list.length(), mode);
        }
        
        return syncIf(
                () ->(size == data.length()), 
                ()-> {
                    for (int value : values) {
                        data.add(value);
                    }
                    return new ImmutableIntFuncList(data, data.length(), mode);
                },
                () -> {
                    return IntFuncList.super.appendAll(values);
                });
    }
    
    /** Add the given value in the collection to the end of the list. */
    public IntFuncList appendAll(GrowOnlyIntArray array) {
        if (this == emptyList) {
            GrowOnlyIntArray list = new GrowOnlyIntArray();
            array.stream().forEach(data::add);
            return new ImmutableIntFuncList(list, list.length(), mode);
        }
        return syncIf(
                () ->(size == data.length()), 
                ()-> {
                    array.stream().forEach(data::add);
                    return new ImmutableIntFuncList(data, data.length(), mode);
                },
                () -> {
                    return IntFuncList.super.appendAll(array.toArray());
                });
    }
    
    /** Add the given value in the collection to the end of the list. */
    public IntFuncList appendAll(List<Integer> ints, int fallbackValue) {
        if (this == emptyList) {
            GrowOnlyIntArray list = new GrowOnlyIntArray();
            ints.stream()
                .mapToInt(i -> (i == null) ? fallbackValue : i.intValue())
                .forEach(data::add);
            return new ImmutableIntFuncList(list, list.length(), mode);
        }
        return syncIf(
                () ->(size == data.length()), 
                ()-> {
                    ints.stream()
                        .mapToInt(i -> (i == null) ? fallbackValue : i.intValue())
                        .forEach(data::add);
                    return new ImmutableIntFuncList(data, data.length(), mode);
                },
                () -> {
                    GrowOnlyIntArray list = new GrowOnlyIntArray();
                    ints.stream()
                        .mapToInt(i -> (i == null) ? fallbackValue : i.intValue())
                        .forEach(data::add);
                    IntFuncList funcList = new ImmutableIntFuncList(list, list.length(), mode);
                    return IntFuncList.super.appendAll(funcList);
                });
    }
    
    /** Add the given value in the collection to the end of the list. */
    public IntFuncList appendAll(IntFuncList ints) {
        if (this == emptyList) {
            GrowOnlyIntArray list = new GrowOnlyIntArray();
            ints.forEach(data::add);
            return new ImmutableIntFuncList(list, list.length(), mode);
        }
        return syncIf(
                () ->(size == data.length()), 
                ()-> {
                    ints.forEach(data::add);
                    return new ImmutableIntFuncList(data, data.length(), mode);
                },
                () -> {
                    return IntFuncList.super.appendAll(ints);
                });
    }
    
    private IntFuncList syncIf(
            BooleanSupplier       condition,
            Supplier<IntFuncList> matchAction,
            Supplier<IntFuncList> elseAction) {
        synchronized (this) {
            if (condition.getAsBoolean()) {
                return matchAction.get();
            }
        }
        return elseAction.get();
    }
    
}
