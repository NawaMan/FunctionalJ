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


import java.util.Objects;
import java.util.function.IntPredicate;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;


class IntFuncListDerivedFromIntFuncList
                implements IntFuncList {
    
    private static final IntBiFunctionPrimitive zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate           toZero        = (int i)          -> i  == 0;
    
    private AsIntFuncList source;
    
    public IntFuncListDerivedFromIntFuncList(AsIntFuncList souce) {
        this.source = Objects.requireNonNull(souce);
    }
    
    @Override
    public IntFuncList asIntFuncList() {
        return source.asIntFuncList();
    }
    
    @Override
    public IntStreamPlus intStreamPlus() {
        return intFuncList().intStreamPlus();
    }

    @Override
    public IntStreamPlus intStream() {
        return intStreamPlus();
    }
    
    @Override
    public IntFuncList lazy() {
        return this;
    }
    
    @Override
    public IntFuncList eager() {
        val data = this.toArray();
        return new ImmutableIntFuncList(data, false);
    }
    
    public String toString() {
        return intFuncList().toListString();
    }
    
    public int hashCode() {
        return reduce(43, (hash, each) -> hash*43 + each);
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
