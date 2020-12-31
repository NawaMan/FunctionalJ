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
package functionalj.function;

import java.util.function.BiPredicate;


@FunctionalInterface
public interface IntBiPredicatePrimitive extends Func2<Integer, Integer, Boolean>, BiPredicate<Integer, Integer> {
    
    public boolean testIntInt(int i1, int i2);
    
    @Override
    public default boolean test(Integer i1, Integer i2) {
        return testIntInt(i1, i2);
    }
    
    public default BiPredicate<Integer, Integer> toPredicate() {
        return (value1, value2) -> test(value1, value2);
    }
    
    @Override
    public default Boolean applyUnsafe(Integer input1, Integer input2) throws Exception {
        return test(input1, input2);
    }
    
    public static IntBiPredicatePrimitive intBiPredicate(Func2<Integer, Integer, Boolean> predicate) {
        if (predicate instanceof IntBiPredicatePrimitive)
            return ((IntBiPredicatePrimitive)predicate);
        
        return predicate::apply;
    }
    
    public static IntBiPredicatePrimitive intPredicate(BiPredicate<Integer, Integer> predicate) {
        if (predicate instanceof IntPredicatePrimitive)
            return ((IntBiPredicatePrimitive)predicate);
        
        return predicate::test;
    }
    
}
