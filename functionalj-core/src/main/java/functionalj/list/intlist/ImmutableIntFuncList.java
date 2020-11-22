// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.intstreamable.IntStreamable;
import lombok.val;

public class ImmutableIntFuncList implements IntFuncList {

    private static final IntBiFunctionPrimitive zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate           toZero        = (int i)          -> i  == 0;

    private final int[]   data;
    private final boolean isLazy;

    private volatile String  toStringCache = null;
    private volatile Integer hashcodeCache = null;

    private static ImmutableIntFuncList emptyList = new ImmutableIntFuncList(new int[0], true);

    public static ImmutableIntFuncList empty() {
        return emptyList;
    }

    public static ImmutableIntFuncList of(int ... source) {
       if ((source == null) || source.length == 0)
            return emptyList;

        var newArray = source.clone();
        return new ImmutableIntFuncList(newArray, true);
    }

    public static ImmutableIntFuncList from(IntFuncList list) {
        if (list == null)
            return emptyList;
        if (list instanceof ImmutableIntFuncList)
            return (ImmutableIntFuncList)list;

        var data = list.toArray();
        var isLazy = list.isLazy();
        return new ImmutableIntFuncList(data, isLazy);
    }

    public static ImmutableIntFuncList from(int[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;

        return new ImmutableIntFuncList(data.clone(), true);
    }

    public static ImmutableIntFuncList from(IntStream source) {
        if ((source == null))
            return emptyList;

        return new ImmutableIntFuncList(source.toArray(), true);
    }

    //public static ImmutableIntFuncList from(IntStreamable source) {
    //    if ((source == null))
    //        return emptyList;

    //    if (source instanceof ImmutableIntFuncList)
    //        return (ImmutableIntFuncList)source;

    //    return new ImmutableIntFuncList(source, true);
    //}

    ImmutableIntFuncList(int[] data, boolean isLazy) {
        this.data = Objects.requireNonNull(data);
        this.isLazy = isLazy;
    }

    ImmutableIntFuncList(IntStreamable source, boolean isLazy) {
        this(source.toArray(), isLazy);
    }

    @Override
    public IntStreamable intStreamable() {
        return ()->intStreamPlus();
    }

     @Override
     public IntStreamPlus intStreamPlus() {
         return IntStreamPlus.of(data);
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

            hashcodeCache = reduce(43, (hash, each) -> hash*43 + each);
            return hashcodeCache;
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof IntFuncList))
            return false;

        if (hashCode() != o.hashCode())
            return false;

        var anotherList = (IntFuncList)o;
        if (size() != anotherList.size())
            return false;

        return IntStreamable.zipOf(this.intStreamable(), anotherList.intStreamable(), zeroForEquals)
                .allMatch(toZero);
    }

}
