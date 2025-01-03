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
package functionalj.list.longlist;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import functionalj.list.FuncList.Mode;
import functionalj.stream.longstream.LongStreamPlus;
import lombok.val;

public class LongFuncListDerived implements LongFuncList {
    
    private static final LongBinaryOperator zeroForEquals = (long i1, long i2) -> i1 == i2 ? 0 : 1;
    
    private static final LongPredicate notZero = (long i) -> i != 0;
    
    // -- Data --
    private final Object source;
    
    private final Function<LongStream, LongStream> action;
    
    // -- Constructors --
    LongFuncListDerived(AsLongFuncList source, Function<LongStream, LongStream> action) {
        this.source = Objects.requireNonNull(source);
        this.action = Objects.requireNonNull(action);
    }
    
    LongFuncListDerived(Supplier<LongStream> streams) {
        this.action = stream -> stream;
        this.source = streams;
    }
    
    LongFuncListDerived(Supplier<LongStream> streams, Function<LongStream, LongStream> action) {
        this.action = Objects.requireNonNull(action);
        this.source = streams;
    }
    
    // -- Source Stream --
    @SuppressWarnings("unchecked")
    private LongStream getSourceStream() {
        if (source == null)
            return LongStream.empty();
        if (source instanceof LongFuncList)
            return (LongStream) ((LongFuncList) source).longStream();
        if (source instanceof Supplier)
            return ((Supplier<LongStream>) source).get();
        throw new IllegalStateException();
    }
    
    @Override
    public LongStreamPlus longStream() {
        LongStream theStream = getSourceStream();
        LongStream newStream = action.apply(theStream);
        return LongStreamPlus.from(newStream);
    }
    
    /**
     * Check if this list is a lazy list.
     */
    public Mode mode() {
        return Mode.lazy;
    }
    
    @Override
    public LongFuncList toLazy() {
        return this;
    }
    
    @Override
    public LongFuncList toEager() {
        val data = this.toArray();
        return new ImmutableLongFuncList(data, data.length, Mode.eager);
    }
    
    @Override
    public LongFuncList toCache() {
        return LongFuncList.from(longStream());
    }
    
    @Override
    public ImmutableLongFuncList toImmutableList() {
        return ImmutableLongFuncList.from(this);
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(reduce(43, (hash, each) -> hash * 43 + each));
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsLongFuncList))
            return false;
        val anotherList = (LongFuncList) o;
        if (size() != anotherList.size())
            return false;
        return !LongFuncList.zipOf(this, anotherList.asLongFuncList(), zeroForEquals).anyMatch(notZero);
    }
    
    @Override
    public String toString() {
        return asLongFuncList().toListString();
    }
}
