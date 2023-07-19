// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import functionalj.list.FuncList.Mode;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class IntFuncListDerived implements IntFuncList {
    
    private static final IntBinaryOperator zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    
    private static final IntPredicate notZero = (int i) -> i != 0;
    
    // -- Data --
    private final Object source;
    
    private final Function<IntStream, IntStream> action;
    
    // -- Constructors --
    IntFuncListDerived(AsIntFuncList source, Function<IntStream, IntStream> action) {
        this.source = Objects.requireNonNull(source);
        this.action = Objects.requireNonNull(action);
    }
    
    IntFuncListDerived(Supplier<IntStream> streams) {
        this.action = stream -> stream;
        this.source = streams;
    }
    
    IntFuncListDerived(Supplier<IntStream> streams, Function<IntStream, IntStream> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streams;
    }
    
    // -- Source Stream --
    @SuppressWarnings("unchecked")
    private IntStream getSourceStream() {
        if (source == null)
            return IntStream.empty();
        if (source instanceof IntFuncList)
            return (IntStream) ((IntFuncList) source).intStream();
        if (source instanceof Supplier)
            return ((Supplier<IntStream>) source).get();
        throw new IllegalStateException();
    }
    
    @Override
    public IntStreamPlus intStream() {
        IntStream theStream = getSourceStream();
        IntStream newStream = action.apply(theStream);
        return IntStreamPlus.from(newStream);
    }
    
    /**
     * Check if this list is a lazy list.
     */
    public Mode mode() {
        return Mode.lazy;
    }
    
    @Override
    public IntFuncList toLazy() {
        return this;
    }
    
    @Override
    public IntFuncList toEager() {
        val data = this.toArray();
        return new ImmutableIntFuncList(data, data.length, Mode.eager);
    }
    
    @Override
    public IntFuncList toCache() {
        return IntFuncList.from(intStream());
    }
    
    /**
     * Returns an immutable list containing the data of this list. Maintaining the mode.
     */
    @Override
    public ImmutableIntFuncList toImmutableList() {
        return ImmutableIntFuncList.from(this);
    }
    
    @Override
    public int hashCode() {
        return reduce(43, (hash, each) -> hash * 43 + each);
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsIntFuncList))
            return false;
        val anotherList = (IntFuncList) o;
        if (size() != anotherList.size())
            return false;
        return !IntFuncList.zipOf(this, anotherList.asIntFuncList(), zeroForEquals).anyMatch(notZero);
    }
    
    @Override
    public String toString() {
        return asIntFuncList().toListString();
    }
}
