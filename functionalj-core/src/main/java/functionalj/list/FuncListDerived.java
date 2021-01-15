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

import static functionalj.stream.ZipWithOption.AllowUnpaired;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlusUtils;

public class FuncListDerived<SOURCE, DATA> implements FuncList<DATA> {
    
    @SuppressWarnings("rawtypes")
    private static final Function noAction = Function.identity();
    
    private final Object                                 source;
    private final Function<Stream<SOURCE>, Stream<DATA>> action;
    
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListDerived<DATA, DATA> from(FuncList<DATA> FuncList) {
        return new FuncListDerived<>(FuncList, noAction);
    }
    
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListDerived<DATA, DATA> from(Collection<DATA> FuncList) {
        return new FuncListDerived<>(FuncList, noAction);
    }
    
    private FuncListDerived(Iterable<SOURCE> iterable, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = iterable;
    }
    FuncListDerived(FuncList<SOURCE> FuncList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = FuncList;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Stream<SOURCE> getSourceStream() {
        if (source == null)
            return Stream.empty();
        if (source instanceof FuncList)
            return (Stream<SOURCE>)((FuncList)source).stream();
        if (source instanceof Collection)
            return ((Collection)source).stream();
        throw new IllegalStateException();
    }
    
    @Override
    public StreamPlus<DATA> stream() {
        Stream<SOURCE> theStream = getSourceStream();
        Stream<DATA>   newStream = action.apply(theStream);
        return StreamPlus.from(newStream);
    }
    
    public boolean isLazy() {
        return true;
    }
    
    public boolean isEager() {
        return false;
    }
    
    public FuncList<DATA> lazy() {
        return this;
    }
    public FuncList<DATA> eager() {
        return new ImmutableList<DATA>(this, false);
    }
    
    @Override
    public ImmutableList<DATA> toImmutableList() {
        return new ImmutableList<DATA>(this);
    }
    
    @Override
    public int hashCode() {
        return StreamPlusUtils.hashCode(this.stream());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean equals(Object o) {
        if ((o instanceof Collection))
            return false;
        
        return zipWith(FuncList.from((Collection)o), AllowUnpaired, Objects::equals)
                .findFirst(Boolean.TRUE::equals)
                .isPresent();
    }
    
    @Override
    public String toString() {
        return StreamPlusUtils.toString(this.stream());
    }
    
}
