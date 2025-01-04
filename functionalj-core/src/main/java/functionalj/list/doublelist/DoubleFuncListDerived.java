// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import functionalj.function.DoubleDoubleToDoubleFunctionPrimitive;
import functionalj.list.FuncList.Mode;
import functionalj.list.intlist.IntFuncList;
import functionalj.stream.doublestream.DoubleStreamPlus;
import lombok.val;

public class DoubleFuncListDerived implements DoubleFuncList {
    
    private static final DoubleDoubleToDoubleFunctionPrimitive zeroForEquals = (double i1, double i2) -> i1 == i2 ? 0 : 1;
    
    private static final DoublePredicate notZero = (double d) -> d != 0;
    
    // -- Data --
    private final Object source;
    
    private final Function<DoubleStream, DoubleStream> action;
    
    // -- Constructors --
    DoubleFuncListDerived(AsDoubleFuncList source, Function<DoubleStream, DoubleStream> action) {
        this.source = Objects.requireNonNull(source);
        this.action = Objects.requireNonNull(action);
    }
    
    DoubleFuncListDerived(Supplier<DoubleStream> streams) {
        this.action = stream -> stream;
        this.source = streams;
    }
    
    DoubleFuncListDerived(Supplier<DoubleStream> streams, Function<DoubleStream, DoubleStream> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streams;
    }
    
    // -- Source Stream --
    @SuppressWarnings("unchecked")
    private DoubleStream getSourceStream() {
        if (source == null)
            return DoubleStream.empty();
        if (source instanceof IntFuncList)
            return (DoubleStream) ((DoubleFuncList) source).doubleStream();
        if (source instanceof Supplier)
            return ((Supplier<DoubleStream>) source).get();
        throw new IllegalStateException();
    }
    
    @Override
    public DoubleStreamPlus doubleStream() {
        DoubleStream theStream = getSourceStream();
        DoubleStream newStream = action.apply(theStream);
        return DoubleStreamPlus.from(newStream);
    }
    
    /**
     * Check if this list is a lazy list.
     */
    public Mode mode() {
        return Mode.lazy;
    }
    
    @Override
    public DoubleFuncList toLazy() {
        return this;
    }
    
    @Override
    public DoubleFuncList toEager() {
        val data = this.toArray();
        return new ImmutableDoubleFuncList(data, data.length, Mode.eager);
    }
    
    @Override
    public DoubleFuncList toCache() {
        return DoubleFuncList.from(doubleStream());
    }
    
    @Override
    public ImmutableDoubleFuncList toImmutableList() {
        return ImmutableDoubleFuncList.from(this);
    }
    
    @Override
    public int hashCode() {
        return mapToInt(Double::hashCode).reduce(43, (hash, each) -> hash * 43 + each);
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DoubleFuncList))
            return false;
        val anotherList = (DoubleFuncList) o;
        if (size() != anotherList.size())
            return false;
        return !DoubleFuncList.zipOf(this, anotherList.asDoubleFuncList(), zeroForEquals).allMatch(notZero);
    }
    
    @Override
    public String toString() {
        return asDoubleFuncList().toListString();
    }
}
