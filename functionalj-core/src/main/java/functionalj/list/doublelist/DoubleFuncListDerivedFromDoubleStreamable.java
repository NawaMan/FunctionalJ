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

import static functionalj.streamable.doublestreamable.DoubleStreamable.zipOf;

import java.util.Objects;
import java.util.function.DoublePredicate;

import functionalj.function.DoubleBiFunctionPrimitive;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.streamable.doublestreamable.AsDoubleStreamable;
import functionalj.streamable.doublestreamable.DoubleStreamable;
import lombok.val;


class DoubleFuncListDerivedFromDoubleStreamable
                implements DoubleFuncList {
    
    private static final DoubleBiFunctionPrimitive zeroForEquals = (double i1, double i2) -> i1 == i2 ? 0 : 1;
    private static final DoublePredicate           toZero        = (double i)          -> i  == 0;
    
    private AsDoubleStreamable source;
    
    public DoubleFuncListDerivedFromDoubleStreamable(AsDoubleStreamable souce) {
        this.source = Objects.requireNonNull(souce);
    }
    
    @Override
    public DoubleStreamable doubleStreamable() {
        return source.doubleStreamable();
    }
    
    @Override
    public DoubleStreamPlus doubleStreamPlus() {
        return doubleStreamable().doubleStreamPlus();
    }
    
    @Override
    public DoubleFuncList lazy() {
        return this;
    }
    
    @Override
    public DoubleFuncList eager() {
        val data = this.toArray();
        return new ImmutableDoubleFuncList(data, false);
    }
    
    public String toString() {
        return doubleStreamable().toListString();
    }
    
    public int hashCode() {
        return mapToInt(Double::hashCode).reduce(43, (hash, each) -> hash*43 + each);
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof DoubleFuncList))
            return false;
        
        if (hashCode() != o.hashCode())
            return false;
        
        val anotherList = (DoubleFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return zipOf(this.doubleStreamable(), anotherList.doubleStreamable(), zeroForEquals)
                .allMatch(toZero);
    }
    
}
