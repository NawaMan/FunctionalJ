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
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;


public class ImmutableIntFuncList implements IntFuncList {
    
    private static final IntBiFunctionPrimitive zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate           toZero        = (int i)          -> i  == 0;
    
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    
    private static ImmutableIntFuncList emptyList = new ImmutableIntFuncList(EMPTY_INT_ARRAY, true);
    
    public static ImmutableIntFuncList empty() {
        return emptyList;
    }
    
    public static ImmutableIntFuncList emptyIntList() {
        return emptyList;
    }
    
    public static ImmutableIntFuncList of(int ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;
       
        val newArray = source.clone();
        return new ImmutableIntFuncList(newArray, true);
    }
    
    public static ImmutableIntFuncList listOf(int ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;
       
        val newArray = source.clone();
        return new ImmutableIntFuncList(newArray, true);
    }
    
    public static ImmutableIntFuncList from(int[] data) {
        return from(true, data);
    }
    
    public static ImmutableIntFuncList from(boolean isLazy, int[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;
        
        return new ImmutableIntFuncList(data.clone(), isLazy);
    }
    
    public static ImmutableIntFuncList from(Collection<Integer> collection, int valueForNull) {
        val ints     = new int[collection.size()];
        val iterator = collection.iterator();
        for (int i = 0; (i < ints.length) && iterator.hasNext(); i++) {
            Integer integer = iterator.next();
            ints[i] = (integer != null) ? integer.intValue() : valueForNull;
        }
        return new ImmutableIntFuncList(ints);
    }
    
    public static ImmutableIntFuncList from(AsIntFuncList FuncList) {
        if (FuncList == null)
            return ImmutableIntFuncList.empty();
        
        return new ImmutableIntFuncList(FuncList.toArray());
    }
    
    public static ImmutableIntFuncList from(boolean isLazy, AsIntFuncList FuncList) {
        if (FuncList == null)
            return ImmutableIntFuncList.empty();
        
        return new ImmutableIntFuncList(FuncList.toArray(), isLazy);
    }
    
    public static ImmutableIntFuncList from(IntStream source) {
        if ((source == null))
            return emptyList;
        
        return new ImmutableIntFuncList(source.toArray());
    }
    
    public static ImmutableIntFuncList from(IntFuncList funcList) {
        if (funcList instanceof ImmutableIntFuncList)
            return (ImmutableIntFuncList)funcList;
        if (funcList == null)
            return ImmutableIntFuncList.empty();
        
        return new ImmutableIntFuncList(funcList.toArray(), funcList.isLazy());
    }
    
    private final int[]   data;
    private final boolean isLazy;
    
    private volatile String  toStringCache = null;
    private volatile Integer hashcodeCache = null;

    ImmutableIntFuncList(int[] data) {
        this(data, true);
    }
    
    ImmutableIntFuncList(int[] data, boolean isLazy) {
        this.data = (data == null) ? EMPTY_INT_ARRAY : data;
        this.isLazy = isLazy;
    }
    
    @Override
    public IntFuncList intFuncList() {
        return ()->intStreamPlus();
    }
    
    @Override
    public IntStreamPlus intStreamPlus() {
        return IntStreamPlus.of(data);
    }
    
    @Override
    public IntStreamPlus intStream() {
        return intStreamPlus();
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
    public IntFuncList lazy() {
        if (isLazy)
            return this;
        
        return new ImmutableIntFuncList(this.data, true);
    }
    
    @Override
    public IntFuncList eager() {
        if (!isLazy)
            return this;
        
        return new ImmutableIntFuncList(this.data, false);
    }
    
    @Override
    public ImmutableIntFuncList toImmutableList() {
        return this;
    }
    
    @Override
    public int size() {
        return data.length;
    }
    
    @Override
    public boolean isEmpty() {
        return (data.length == 0);
    }
    
    @Override
    public int[] toArray() {
        return data.clone();
    }
    
    @Override
    public int get(int index) {
        return data[index];
    }
    
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
    
    public int hashCode() {
        if (hashcodeCache != null)
            return hashcodeCache;
        
        synchronized (this) {
            if (hashcodeCache != null)
                return hashcodeCache;
            
            hashcodeCache = reduce(43, (hash, each) -> hash*43 + each);
            return hashcodeCache;
        }
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof IntFuncList))
            return false;
        
        if (hashCode() != o.hashCode())
            return false;
        
        val anotherList = (IntFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return IntFuncList.zipOf(this.intFuncList(), anotherList.intFuncList(), zeroForEquals)
                .allMatch(toZero);
    }
    
}
