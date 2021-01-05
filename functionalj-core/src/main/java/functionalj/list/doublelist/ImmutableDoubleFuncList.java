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

import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import lombok.val;


public class ImmutableDoubleFuncList implements DoubleFuncList {
    
    private static final DoubleBiFunctionPrimitive zeroForEquals = (double i1, double i2) -> i1 == i2 ? 0 : 1;
    private static final DoublePredicate           toZero        = (double i)             -> i  == 0;
    
    private final double[]   data;
    private final boolean isLazy;
    
    private volatile String  toStringCache = null;
    private volatile Integer hashcodeCache = null;
    
    private static ImmutableDoubleFuncList emptyList = new ImmutableDoubleFuncList(new double[0], true);
    
    public static ImmutableDoubleFuncList empty() {
        return emptyList;
    }
    
    public static ImmutableDoubleFuncList of(double ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;
       
        val newArray = source.clone();
        return new ImmutableDoubleFuncList(newArray, true);
    }
    
    public static ImmutableDoubleFuncList from(DoubleFuncList list) {
        if (list == null)
            return emptyList;
        if (list instanceof ImmutableDoubleFuncList)
            return (ImmutableDoubleFuncList)list;
        
        val data   = list.toArray();
        val isLazy = list.isLazy();
        return new ImmutableDoubleFuncList(data, isLazy);
    }
    
    public static ImmutableDoubleFuncList from(double[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;
        
        return new ImmutableDoubleFuncList(data.clone(), true);
    }
    
    public static ImmutableDoubleFuncList from(DoubleStream source) {
        if ((source == null))
            return emptyList;
        
        return new ImmutableDoubleFuncList(source.toArray(), true);
    }
    
    public static ImmutableDoubleFuncList from(DoubleStreamable source) {
        if ((source == null))
            return emptyList;
        
        if (source instanceof ImmutableDoubleFuncList)
            return (ImmutableDoubleFuncList)source;
        
        return new ImmutableDoubleFuncList(source, true);
    }
    
    ImmutableDoubleFuncList(double[] data, boolean isLazy) {
        this.data = Objects.requireNonNull(data);
        this.isLazy = isLazy;
    }
    
    ImmutableDoubleFuncList(DoubleStreamable source, boolean isLazy) {
        this(source.toArray(), isLazy);
    }
    
    @Override
    public DoubleStreamable doubleStreamable() {
        return ()->doubleStreamPlus();
    }
    
    @Override
    public DoubleStreamPlus doubleStreamPlus() {
        return DoubleStreamPlus.of(data);
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
    public boolean isLazy() {
        return isLazy;
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
            
            hashcodeCache = mapToInt(Double::hashCode).reduce(43, (hash, each) -> hash*43 + each);
            return hashcodeCache;
        }
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof DoubleFuncList))
            return false;
        
        if (hashCode() != o.hashCode())
            return false;
        
        val anotherList = (DoubleFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return DoubleStreamable.zipOf(this.doubleStreamable(), anotherList.doubleStreamable(), zeroForEquals)
                .allMatch(toZero);
    }
    
}
