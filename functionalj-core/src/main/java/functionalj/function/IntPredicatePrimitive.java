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

import java.util.function.IntPredicate;
import java.util.function.Predicate;

@FunctionalInterface
public interface IntPredicatePrimitive extends IntPredicate, Func1<Integer, Boolean> {
    
    public boolean test(int value);
    
    public default Predicate<Integer> toPredicate() {
        return this::test;
    }
    
    @Override
    public default Boolean applyUnsafe(Integer input) throws Exception {
        return test(input);
    }
    
    public static IntPredicatePrimitive intPredicate(IntPredicate predicate) {
        if (predicate instanceof IntPredicatePrimitive)
            return ((IntPredicatePrimitive)predicate);
        
        return predicate::test;
    }
    
    public static IntPredicatePrimitive intPredicate(Func1<Integer, Boolean> predicate) {
        if (predicate instanceof IntPredicatePrimitive)
            return ((IntPredicatePrimitive)predicate);
        
        return predicate::apply;
    }
    
    public static IntPredicatePrimitive intPredicate(Predicate<Integer> predicate) {
        if (predicate instanceof IntPredicatePrimitive)
            return ((IntPredicatePrimitive)predicate);
        
        return predicate::test;
    }
    
}
