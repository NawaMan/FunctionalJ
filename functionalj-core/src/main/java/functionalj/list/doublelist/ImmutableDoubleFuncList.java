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
package functionalj.list.doublelist;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.BooleanSupplier;
import java.util.function.DoublePredicate;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import functionalj.function.DoubleDoubleToDoubleFunctionPrimitive;
import functionalj.list.FuncList;
import functionalj.list.FuncList.Mode;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.doublestream.GrowOnlyDoubleArray;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;

// TODO - Override methods in FuncListWithMapGroup to make it faster
// TODO - Override methods in FuncListWithMapWithIndex to make it faster
public class ImmutableDoubleFuncList implements DoubleFuncList {
    
    private static final DoubleDoubleToDoubleFunctionPrimitive zeroForEquals = (double i1, double i2) -> i1 == i2 ? 0 : 1;
    
    private static final DoublePredicate toZero = (double i) -> i == 0;
    
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    
    private static ImmutableDoubleFuncList emptyList = new ImmutableDoubleFuncList(new double[0], 0, Mode.lazy);
    
    /**
     * @return an empty list
     */
    public static ImmutableDoubleFuncList empty() {
        return emptyList;
    }
    
    /**
     * @return an empty list
     */
    public static ImmutableDoubleFuncList emptyDoubleList() {
        return emptyList;
    }
    
    /**
     * @return the list containing the given elements
     */
    public static ImmutableDoubleFuncList of(double... source) {
        if ((source == null) || source.length == 0)
            return emptyList;
        val newArray = source.clone();
        return new ImmutableDoubleFuncList(newArray, newArray.length, Mode.lazy);
    }
    
    /**
     * @return the list containing the given elements
     */
    public static ImmutableDoubleFuncList listOf(double... source) {
        if ((source == null) || source.length == 0)
            return emptyList;
        val newArray = source.clone();
        return new ImmutableDoubleFuncList(newArray, newArray.length, Mode.lazy);
    }
    
    /**
     * Create a FuncList from the given array.
     */
    public static ImmutableDoubleFuncList from(double[] data) {
        return from(Mode.lazy, data);
    }
    
    public static ImmutableDoubleFuncList from(Mode mode, double[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;
        val array = data.clone();
        return new ImmutableDoubleFuncList(array, array.length, mode);
    }
    
    /**
     * @return the list containing the given elements
     */
    public static ImmutableDoubleFuncList from(Mode mode, AsDoubleFuncList asFuncList) {
        if (asFuncList == null)
            return emptyList;
        if (asFuncList instanceof ImmutableDoubleFuncList)
            if (asFuncList.asDoubleFuncList().isLazy())
                return (ImmutableDoubleFuncList) asFuncList;
        val data = asFuncList.asDoubleFuncList().toArray();
        return new ImmutableDoubleFuncList(data, data.length, mode);
    }
    
    /**
     * @return the list containing the element from the given stream
     */
    public static ImmutableDoubleFuncList from(DoubleStream source) {
        if ((source == null))
            return emptyList;
        double[] array = source.toArray();
        return new ImmutableDoubleFuncList(array, array.length, Mode.lazy);
    }
    
    /**
     * @return the list containing the element from the given stream
     */
    public static ImmutableDoubleFuncList from(Mode mode, DoubleStream source) {
        if ((source == null))
            return emptyList;
        double[] array = source.toArray();
        return new ImmutableDoubleFuncList(array, array.length, mode);
    }
    
    /**
     * @return the list containing the element from the given list.
     */
    public static ImmutableDoubleFuncList from(AsDoubleFuncList funcList) {
        if ((funcList == null))
            return emptyList;
        if (funcList instanceof ImmutableDoubleFuncList)
            return (ImmutableDoubleFuncList) funcList;
        val mode = funcList.asDoubleFuncList().mode();
        return ImmutableDoubleFuncList.from(mode, funcList);
    }
    
    /**
     * @return the list containing the element from the given collections.
     */
    public static ImmutableDoubleFuncList from(Collection<Double> collection, double valueForNull) {
        val doubles = new double[collection.size()];
        val iterator = collection.iterator();
        for (int i = 0; (i < doubles.length) && iterator.hasNext(); i++) {
            Double value = iterator.next();
            doubles[i] = (value != null) ? value.doubleValue() : valueForNull;
        }
        return new ImmutableDoubleFuncList(doubles, doubles.length, Mode.lazy);
    }
    
    // -- Data --
    private final GrowOnlyDoubleArray data;
    
    private final FuncList.Mode mode;
    
    private final int size;
    
    private volatile String toStringCache = null;
    
    private volatile Integer hashcodeCache = null;
    
    // -- Constructors --
    ImmutableDoubleFuncList(double[] data, int size) {
        this(data, size, Mode.lazy);
    }
    
    ImmutableDoubleFuncList(double[] data, int size, Mode mode) {
        this.data = new GrowOnlyDoubleArray((data == null) ? EMPTY_DOUBLE_ARRAY : data);
        this.mode = mode;
        this.size = size;
    }
    
    ImmutableDoubleFuncList(GrowOnlyDoubleArray data, int size, Mode mode) {
        this.data = data;
        this.mode = mode;
        this.size = size;
    }
    
    ImmutableDoubleFuncList(DoubleFuncList list, int size, Mode mode) {
        this.mode = mode;
        this.size = size;
        this.data = (list instanceof ImmutableDoubleFuncList) ? ((ImmutableDoubleFuncList) list).data : new GrowOnlyDoubleArray(list.toArray());
    }
    
    @Override
    public DoubleStreamPlus doubleStream() {
        if (size == -1) {
            return DoubleStreamPlus.from(data.stream());
        } else {
            return IntStreamPlus.infinite().limit(size).mapToDouble(i -> data.get(i));
        }
    }
    
    @Override
    public Mode mode() {
        return mode;
    }
    
    @Override
    public DoubleFuncList toLazy() {
        if (mode().isLazy())
            return this;
        // Do this to not duplicate the data
        return new ImmutableDoubleFuncList(data, size, Mode.lazy);
    }
    
    @Override
    public DoubleFuncList toEager() {
        if (mode().isEager())
            return this;
        // Do this to not duplicate the data
        return new ImmutableDoubleFuncList(data, size, Mode.eager);
    }
    
    @Override
    public DoubleFuncList toCache() {
        if (mode().isCache())
            return this;
        // Do this to not duplicate the data
        return new ImmutableDoubleFuncList(data, size, Mode.cache);
    }
    
    @Override
    public ImmutableDoubleFuncList toImmutableList() {
        return this;
    }
    
    @Override
    public String toString() {
        if (toStringCache != null)
            return toStringCache;
        synchronized (this) {
            if (toStringCache != null)
                return toStringCache;
            toStringCache = DoubleStreamPlus.from(data.stream().limit(size)).toListString();
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
            hashcodeCache = mapToInt(Double::hashCode).reduce(43, (hash, each) -> hash * 43 + each);
            return hashcodeCache;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DoubleFuncList))
            return false;
        if (hashCode() != o.hashCode())
            return false;
        val anotherList = (DoubleFuncList) o;
        if (size() != anotherList.size())
            return false;
        return DoubleFuncList.zipOf(this, anotherList, zeroForEquals).allMatch(toZero);
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
    public double[] toArray() {
        return data.stream().toArray();
    }
    
    @Override
    public double get(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return data.get(index);
    }
    
    @Override
    public int indexOf(double value) {
        for (int i = 0; i < size; i++) {
            if (value == data.get(i))
                return i;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(double value) {
        for (int i = size; i-- > 0; ) {
            if (value == data.get(i))
                return i;
        }
        return -1;
    }
    
    @Sequential
    @Terminal
    public OptionalDouble first() {
        return (this.size == 0) ? OptionalDouble.empty() : OptionalDouble.of(this.data.get(0));
    }
    
    @Sequential
    @Terminal
    public OptionalDouble last() {
        return (size == 0) ? OptionalDouble.empty() : OptionalDouble.of(this.data.get(size - 1));
    }
    
    // -- Append
    /**
     * Add the given value to the end of the list.
     * This method is for convenient. It is not really efficient if used to add a lot of data.
     */
    public DoubleFuncList append(double value) {
        if (this == emptyList) {
            GrowOnlyDoubleArray list = new GrowOnlyDoubleArray();
            list.add(value);
            return new ImmutableDoubleFuncList(list, 1, Mode.lazy);
        }
        return syncIf(() -> (size == data.length()), () -> {
            data.add(value);
            return new ImmutableDoubleFuncList(data, data.length(), Mode.lazy);
        }, () -> {
            return DoubleFuncList.super.append(value);
        });
    }
    
    /**
     * Add the given values to the end of the list.
     */
    public DoubleFuncList appendAll(double... values) {
        if (this == emptyList) {
            GrowOnlyDoubleArray list = new GrowOnlyDoubleArray();
            for (double value : values) {
                list.add(value);
            }
            return new ImmutableDoubleFuncList(list, list.length(), Mode.lazy);
        }
        return syncIf(() -> (size == data.length()), () -> {
            for (double value : values) {
                data.add(value);
            }
            return new ImmutableDoubleFuncList(data, data.length(), Mode.lazy);
        }, () -> {
            return DoubleFuncList.super.appendAll(values);
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public DoubleFuncList appendAll(GrowOnlyDoubleArray array) {
        if (this == emptyList) {
            GrowOnlyDoubleArray list = new GrowOnlyDoubleArray();
            array.stream().forEach(data::add);
            return new ImmutableDoubleFuncList(list, list.length(), Mode.lazy);
        }
        return syncIf(() -> (size == data.length()), () -> {
            array.stream().forEach(data::add);
            return new ImmutableDoubleFuncList(data, data.length(), Mode.lazy);
        }, () -> {
            return DoubleFuncList.super.appendAll(array.toArray());
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public DoubleFuncList appendAll(List<Double> doubles, double fallbackValue) {
        if (this == emptyList) {
            GrowOnlyDoubleArray list = new GrowOnlyDoubleArray();
            doubles.stream().mapToDouble(d -> (d == null) ? fallbackValue : d.doubleValue()).forEach(data::add);
            return new ImmutableDoubleFuncList(list, list.length(), Mode.lazy);
        }
        return syncIf(() -> (size == data.length()), () -> {
            doubles.stream().mapToDouble(d -> (d == null) ? fallbackValue : d.doubleValue()).forEach(data::add);
            return new ImmutableDoubleFuncList(data, data.length(), Mode.lazy);
        }, () -> {
            GrowOnlyDoubleArray list = new GrowOnlyDoubleArray();
            doubles.stream().mapToDouble(d -> (d == null) ? fallbackValue : d.doubleValue()).forEach(data::add);
            DoubleFuncList funcList = new ImmutableDoubleFuncList(list, list.length(), Mode.lazy);
            return DoubleFuncList.super.appendAll(funcList);
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public DoubleFuncList appendAll(DoubleFuncList doubles) {
        if (this == emptyList) {
            GrowOnlyDoubleArray list = new GrowOnlyDoubleArray();
            doubles.forEach(data::add);
            return new ImmutableDoubleFuncList(list, list.length(), Mode.lazy);
        }
        return syncIf(() -> (size == data.length()), () -> {
            doubles.forEach(data::add);
            return new ImmutableDoubleFuncList(data, data.length(), Mode.lazy);
        }, () -> {
            return DoubleFuncList.super.appendAll(doubles);
        });
    }
    
    private DoubleFuncList syncIf(BooleanSupplier condition, Supplier<DoubleFuncList> matchAction, Supplier<DoubleFuncList> elseAction) {
        synchronized (this) {
            if (condition.getAsBoolean()) {
                return matchAction.get();
            }
        }
        return elseAction.get();
    }
}
