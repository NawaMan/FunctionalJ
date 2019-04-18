// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.stream.StreamPlus;
import functionalj.stream.Streamable;

// TODO - Add Integer length here to help with a few operations.
@SuppressWarnings("javadoc")
public class FuncListDerived<SOURCE, DATA> 
                implements FuncList<DATA> {
    
    @SuppressWarnings("rawtypes")
    private static final Function noAction = Function.identity();
    
    private final Object                                 source;
    private final Function<Stream<SOURCE>, Stream<DATA>> action;
    
    public static <DATA> FuncListDerived<DATA, DATA> from(FuncList<DATA> funcList) {
        return new FuncListDerived<>(funcList);
    }
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListDerived<DATA, DATA> from(Supplier<Stream<DATA>> supplier) {
        return new FuncListDerived<>(supplier, noAction);
    }
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListDerived<DATA, DATA> from(Streamable<DATA> streamable) {
        return new FuncListDerived<>(streamable, noAction);
    }
    
    @SuppressWarnings("unchecked")
    public static <DATA> FuncListDerived<DATA, DATA> from(Collection<DATA> streamable) {
        return new FuncListDerived<>(streamable, noAction);
    }
    
    public FuncListDerived(Iterable<SOURCE> collection, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = collection;
    }
    public FuncListDerived(Supplier<Stream<SOURCE>> streamSupplier, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamSupplier;
    }
    public FuncListDerived(Streamable<SOURCE> streamable, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streamable;
    }
    public FuncListDerived(ReadOnlyList<SOURCE> readOnlyList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = readOnlyList;
    }
    public FuncListDerived(FuncList<SOURCE> abstractFuncList, Function<Stream<SOURCE>, Stream<DATA>> action) {
        this.action = Objects.requireNonNull(action);
        this.source = abstractFuncList;
    }
    @SuppressWarnings("unchecked")
    public FuncListDerived(FuncList<DATA> abstractFuncList) {
        this.action = s -> (Stream<DATA>)s;
        this.source = abstractFuncList;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Stream<SOURCE> getSourceStream() {
        if (source == null)
            return Stream.empty();
        if (source instanceof Supplier)
            return (Stream<SOURCE>)((Supplier)source).get();
        if (source instanceof Streamable)
            return (Stream<SOURCE>)((Streamable)source).stream();
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
        return Helper.hashCode(this);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean equals(Object o) {
        if ((o instanceof Collection))
            return false;
        
        return combine(((Collection)o).stream(), AllowUnpaired, Objects::equals)
                .findFirst(Boolean.TRUE::equals)
                .isPresent();
    }
    
    @Override
    public String toString() {
        return Helper.toString(this);
    }
    
}
