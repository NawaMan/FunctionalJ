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

import java.util.Arrays;
import java.util.Collection;
import java.util.OptionalDouble;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import functionalj.function.DoubleDoubleToDoubleFunctionPrimitive;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;


//TODO - Override methods in FuncListWithMapGroup to make it faster
//TODO - Override methods in FuncListWithMapWithIndex to make it faster


public class ImmutableDoubleFuncList implements DoubleFuncList {
    
    private static final DoubleDoubleToDoubleFunctionPrimitive zeroForEquals = (double i1, double i2) -> i1 == i2 ? 0 : 1;
    private static final DoublePredicate           toZero        = (double i)             -> i  == 0;
    
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    
    private static ImmutableDoubleFuncList emptyList = new ImmutableDoubleFuncList(new double[0], true);
    
    /** @return an empty list */
    public static ImmutableDoubleFuncList empty() {
        return emptyList;
    }
    
    /** @return an empty list */
    public static ImmutableDoubleFuncList emptyDoubleList() {
        return emptyList;
    }
    
    /** @return the list containing the given elements */
    public static ImmutableDoubleFuncList of(double ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;
       
        val newArray = source.clone();
        return new ImmutableDoubleFuncList(newArray, true);
    }
    
    /** @return the list containing the given elements */
    public static ImmutableDoubleFuncList listOf(double ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;
       
        val newArray = source.clone();
        return new ImmutableDoubleFuncList(newArray, true);
    }
    
    public static ImmutableDoubleFuncList from(boolean isLazy, double[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;
        
        return new ImmutableDoubleFuncList(data.clone(), isLazy);
    }
    
    /** @return the list containing the given elements */
    public static ImmutableDoubleFuncList from(boolean isLazy, AsDoubleFuncList asFuncList) {
        if (asFuncList == null)
            return emptyList;
        
        if (asFuncList instanceof ImmutableDoubleFuncList)
            if (isLazy == asFuncList.asDoubleFuncList().isLazy())
                return (ImmutableDoubleFuncList)asFuncList;
        
        val data = asFuncList.asDoubleFuncList().toArray();
        return new ImmutableDoubleFuncList(data, isLazy);
    }
    
    /** @return the list containing the element from the given stream */
    public static ImmutableDoubleFuncList from(DoubleStream source) {
        if ((source == null))
            return emptyList;
        
        return new ImmutableDoubleFuncList(source.toArray(), true);
    }
    
    /** @return the list containing the element from the given stream */
    public static ImmutableDoubleFuncList from(boolean isLazy, DoubleStream source) {
        if ((source == null))
            return emptyList;
        
        return new ImmutableDoubleFuncList(source.toArray(), isLazy);
    }
    
    /** @return the list containing the element from the given list. */
    public static ImmutableDoubleFuncList from(DoubleFuncList funcList) {
        if ((funcList == null))
            return emptyList;
        
        if (funcList instanceof ImmutableDoubleFuncList)
            return (ImmutableDoubleFuncList)funcList;
        
        val isLazy = funcList.asDoubleFuncList().isLazy();
        return ImmutableDoubleFuncList.from(isLazy, funcList);
    }
    
    /** @return the list containing the element from the given collections. */
    public static ImmutableDoubleFuncList from(Collection<Integer> collection, double valueForNull) {
        val ints     = new double[collection.size()];
        val iterator = collection.iterator();
        for (int i = 0; (i < ints.length) && iterator.hasNext(); i++) {
            Integer integer = iterator.next();
            ints[i] = (integer != null) ? integer.intValue() : valueForNull;
        }
        return new ImmutableDoubleFuncList(ints, true);
    }
    
    //-- Data --
    
    private final double[] data;
    private final boolean  isLazy;
    
    private volatile String  toStringCache = null;
    private volatile Integer hashcodeCache = null;
    
    //-- Constructors --
    
    ImmutableDoubleFuncList(double[] data) {
        this(data, true);
    }
    
    ImmutableDoubleFuncList(double[] data, boolean isLazy) {
        this.data = (data == null) ? EMPTY_DOUBLE_ARRAY : data;
        this.isLazy = isLazy;
    }
    
    @Override
    public DoubleStreamPlus doubleStream() {
        return DoubleStreamPlus.from(Arrays.stream(data));
//        return IntStreamPlus.infinite().limit(size).map(i -> data.get(i));
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
    public DoubleFuncList lazy() {
        if (isLazy)
            return this;
        
        return new ImmutableDoubleFuncList(this.data, true);
    }
    
    @Override
    public DoubleFuncList eager() {
        if (!isLazy)
            return this;
        
        return new ImmutableDoubleFuncList(this.data, false);
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
            
            toStringCache = toListString();
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
            
            hashcodeCache = mapToInt(Double::hashCode).reduce(43, (hash, each) -> hash*43 + each);
            return hashcodeCache;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DoubleFuncList))
            return false;
        
        if (hashCode() != o.hashCode())
            return false;
        
        val anotherList = (DoubleFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return DoubleFuncList.zipOf(this, anotherList, zeroForEquals)
                .allMatch(toZero);
    }
    
    // -- Short cut --
    
    @Override
    public int size() {
        return data.length;
    }
    
    @Override
    public boolean isEmpty() {
        return (data.length == 0);
    }
    
    @Override
    public double[] toArray() {
        return data.clone();
    }
    
    @Override
    public double get(int index) {
        return data[index];
    }
    
    @Override
    public int indexOf(double value) {
        for (int i = 0; i < data.length; i++) {
            if (value == data[i])
                return i;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(double value) {
        for (int i = data.length; i-->0;) {
            if (value == data[i])
                return i;
        }
        return -1;
    }
    
    @Sequential
    @Terminal
    @Override
    public OptionalDouble firstResult() {
        return (this.data.length == 0)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.data[0]);
    }
    
    @Sequential
    @Terminal
    @Override
    public OptionalDouble lastResult() {
        int size = this.data.length;
        return (size == 0)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.data[size - 1]);
    }
    
}
