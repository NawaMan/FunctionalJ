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
package functionalj.list.longlist;

import java.util.Collection;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.BooleanSupplier;
import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import functionalj.list.FuncList;
import functionalj.list.FuncList.Mode;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.longstream.GrowOnlyLongArray;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;

// TODO - Override methods in FuncListWithMapGroup to make it faster
// TODO - Override methods in FuncListWithMapWithIndex to make it faster
public class ImmutableLongFuncList implements LongFuncList {
    
    private static final LongBinaryOperator zeroForEquals = (long i1, long i2) -> i1 == i2 ? 0 : 1;
    
    private static final LongPredicate toZero = (long i) -> i == 0;
    
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    
    private static ImmutableLongFuncList emptyList = new ImmutableLongFuncList(EMPTY_LONG_ARRAY, 0, Mode.lazy);
    
    /**
     * @return an empty list
     */
    public static ImmutableLongFuncList empty() {
        return emptyList;
    }
    
    /**
     * @return an empty list
     */
    public static ImmutableLongFuncList emptyLongList() {
        return emptyList;
    }
    
    /**
     * @return the list containing the given elements
     */
    public static ImmutableLongFuncList of(long... source) {
        if ((source == null) || source.length == 0)
            return emptyList;
        val newArray = source.clone();
        return new ImmutableLongFuncList(newArray, newArray.length, Mode.lazy);
    }
    
    /**
     * @return the list containing the given elements
     */
    public static ImmutableLongFuncList listOf(long... source) {
        if ((source == null) || source.length == 0)
            return emptyList;
        val newArray = source.clone();
        return new ImmutableLongFuncList(newArray, newArray.length, Mode.lazy);
    }
    
    /**
     * Create a FuncList from the given array.
     */
    public static ImmutableLongFuncList from(long[] datas) {
        return from(Mode.lazy, datas);
    }
    
    public static ImmutableLongFuncList from(Mode mode, long[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;
        val array = data.clone();
        return new ImmutableLongFuncList(array, array.length, mode);
    }
    
    /**
     * @return the list containing the given elements
     */
    public static ImmutableLongFuncList from(Mode mode, AsLongFuncList asFuncList) {
        if (asFuncList == null)
            return emptyList;
        if (asFuncList instanceof ImmutableLongFuncList)
            if (asFuncList.asLongFuncList().isLazy())
                return (ImmutableLongFuncList) asFuncList;
        val data = asFuncList.asLongFuncList().toArray();
        return new ImmutableLongFuncList(data, data.length, mode);
    }
    
    /**
     * @return the list containing the element from the given stream
     */
    public static ImmutableLongFuncList from(LongStream source) {
        if ((source == null))
            return emptyList;
        long[] array = source.toArray();
        return new ImmutableLongFuncList(array, array.length, Mode.lazy);
    }
    
    /**
     * @return the list containing the element from the given stream
     */
    public static ImmutableLongFuncList from(Mode mode, LongStream source) {
        if ((source == null))
            return emptyList;
        long[] array = source.toArray();
        return new ImmutableLongFuncList(array, array.length, mode);
    }
    
    /**
     * @return the list containing the element from the given list.
     */
    public static ImmutableLongFuncList from(AsLongFuncList funcList) {
        if (funcList == null)
            return emptyList;
        if (funcList instanceof ImmutableLongFuncList)
            return (ImmutableLongFuncList) funcList;
        val mode = funcList.asLongFuncList().mode();
        return ImmutableLongFuncList.from(mode, funcList);
    }
    
    /**
     * @return the list containing the element from the given collections.
     */
    public static ImmutableLongFuncList from(Collection<Long> collection, long valueForNull) {
        val longs = new long[collection.size()];
        val iterator = collection.iterator();
        for (int i = 0; (i < longs.length) && iterator.hasNext(); i++) {
            Long next = iterator.next();
            longs[i] = (next != null) ? next.longValue() : valueForNull;
        }
        return new ImmutableLongFuncList(longs, longs.length, Mode.lazy);
    }
    
    // -- Data --
    private final GrowOnlyLongArray data;
    
    private final FuncList.Mode mode;
    
    private final int size;
    
    private volatile String toStringCache = null;
    
    private volatile Integer hashcodeCache = null;
    
    // -- Constructors --
    ImmutableLongFuncList(long[] data, int size) {
        this(data, size, Mode.lazy);
    }
    
    ImmutableLongFuncList(long[] data, int size, Mode mode) {
        this.data = new GrowOnlyLongArray((data == null) ? EMPTY_LONG_ARRAY : data);
        this.mode = mode;
        this.size = size;
    }
    
    ImmutableLongFuncList(GrowOnlyLongArray data, int size, Mode mode) {
        this.data = data;
        this.mode = mode;
        this.size = size;
    }
    
    ImmutableLongFuncList(LongFuncList list, int size, Mode mode) {
        this.mode = mode;
        this.size = size;
        this.data = (list instanceof ImmutableLongFuncList) ? ((ImmutableLongFuncList) list).data : new GrowOnlyLongArray(list.toArray());
    }
    
    @Override
    public LongStreamPlus longStream() {
        if (size == -1) {
            return LongStreamPlus.from(data.stream());
        } else {
            return IntStreamPlus.infinite().limit(size).mapToLong(i -> data.get(i));
        }
    }
    
    @Override
    public Mode mode() {
        return mode;
    }
    
    @Override
    public LongFuncList toLazy() {
        if (mode().isLazy())
            return this;
        // Do this to not duplicate the data
        return new ImmutableLongFuncList(data, size, Mode.lazy);
    }
    
    @Override
    public LongFuncList toEager() {
        if (mode().isEager())
            return this;
        // Do this to not duplicate the data
        return new ImmutableLongFuncList(data, size, Mode.eager);
    }
    
    @Override
    public LongFuncList toCache() {
        if (mode().isCache())
            return this;
        // Do this to not duplicate the data
        return new ImmutableLongFuncList(data, size, Mode.cache);
    }
    
    @Override
    public ImmutableLongFuncList toImmutableList() {
        return this;
    }
    
    @Override
    public String toString() {
        if (toStringCache != null)
            return toStringCache;
        synchronized (this) {
            if (toStringCache != null)
                return toStringCache;
            toStringCache = LongStreamPlus.from(data.stream().limit(size)).toListString();
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
            hashcodeCache = Long.hashCode(limit(size).reduce(43, (hash, each) -> hash * 43 + each));
            return hashcodeCache;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LongFuncList))
            return false;
        if (hashCode() != o.hashCode())
            return false;
        val anotherList = (LongFuncList) o;
        if (size() != anotherList.size())
            return false;
        return LongFuncList.zipOf(this, anotherList, zeroForEquals).allMatch(toZero);
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
    public long[] toArray() {
        return data.stream().toArray();
    }
    
    @Override
    public long get(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data.get(index);
    }
    
    @Override
    public int indexOf(long value) {
        for (int i = 0; i < size; i++) {
            if (value == data.get(i))
                return i;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(long value) {
        for (int i = size; i-- > 0; ) {
            if (value == data.get(i))
                return i;
        }
        return -1;
    }
    
    @Sequential
    @Terminal
    @Override
    public OptionalLong first() {
        return (this.size == 0) ? OptionalLong.empty() : OptionalLong.of(this.data.get(0));
    }
    
    @Sequential
    @Terminal
    @Override
    public OptionalLong last() {
        return (size == 0) ? OptionalLong.empty() : OptionalLong.of(this.data.get(size - 1));
    }
    
    // -- Append
    /**
     * Add the given value to the end of the list.
     * This method is for convenient. It is not really efficient if used to add a lot of data.
     */
    public LongFuncList append(long value) {
        if (this == emptyList) {
            GrowOnlyLongArray list = new GrowOnlyLongArray();
            list.add(value);
            return new ImmutableLongFuncList(list, 1, mode);
        }
        return syncIf(() -> (size == data.length()), () -> {
            data.add(value);
            return new ImmutableLongFuncList(data, data.length(), mode);
        }, () -> {
            return LongFuncList.super.append(value);
        });
    }
    
    /**
     * Add the given values to the end of the list.
     */
    public LongFuncList appendAll(long... values) {
        if (this == emptyList) {
            GrowOnlyLongArray list = new GrowOnlyLongArray();
            for (long value : values) {
                list.add(value);
            }
            return new ImmutableLongFuncList(list, list.length(), mode);
        }
        return syncIf(() -> (size == data.length()), () -> {
            for (long value : values) {
                data.add(value);
            }
            return new ImmutableLongFuncList(data, data.length(), mode);
        }, () -> {
            return LongFuncList.super.appendAll(values);
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public LongFuncList appendAll(GrowOnlyLongArray array) {
        if (this == emptyList) {
            GrowOnlyLongArray list = new GrowOnlyLongArray();
            array.stream().forEach(data::add);
            return new ImmutableLongFuncList(list, list.length(), mode);
        }
        return syncIf(() -> (size == data.length()), () -> {
            array.stream().forEach(data::add);
            return new ImmutableLongFuncList(data, data.length(), mode);
        }, () -> {
            return LongFuncList.super.appendAll(array.toArray());
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public LongFuncList appendAll(List<Long> longs, long fallbackValue) {
        if (this == emptyList) {
            GrowOnlyLongArray list = new GrowOnlyLongArray();
            longs.stream().mapToLong(l -> (l == null) ? fallbackValue : l.longValue()).forEach(data::add);
            return new ImmutableLongFuncList(list, list.length(), mode);
        }
        return syncIf(() -> (size == data.length()), () -> {
            longs.stream().mapToLong(l -> (l == null) ? fallbackValue : l.longValue()).forEach(data::add);
            return new ImmutableLongFuncList(data, data.length(), mode);
        }, () -> {
            GrowOnlyLongArray list = new GrowOnlyLongArray();
            longs.stream().mapToLong(l -> (l == null) ? fallbackValue : l.longValue()).forEach(data::add);
            LongFuncList funcList = new ImmutableLongFuncList(list, list.length(), mode);
            return LongFuncList.super.appendAll(funcList);
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public LongFuncList appendAll(LongFuncList longs) {
        if (this == emptyList) {
            GrowOnlyLongArray list = new GrowOnlyLongArray();
            longs.forEach(data::add);
            return new ImmutableLongFuncList(list, list.length(), mode);
        }
        return syncIf(() -> (size == data.length()), () -> {
            longs.forEach(data::add);
            return new ImmutableLongFuncList(data, data.length(), mode);
        }, () -> {
            return LongFuncList.super.appendAll(longs);
        });
    }
    
    private LongFuncList syncIf(BooleanSupplier condition, Supplier<LongFuncList> matchAction, Supplier<LongFuncList> elseAction) {
        synchronized (this) {
            if (condition.getAsBoolean()) {
                return matchAction.get();
            }
        }
        return elseAction.get();
    }
}
