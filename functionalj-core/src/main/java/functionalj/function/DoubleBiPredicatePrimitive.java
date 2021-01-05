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
package functionalj.function;

import java.util.function.BiPredicate;


@FunctionalInterface
public interface DoubleBiPredicatePrimitive extends Func2<Double, Double, Boolean>, BiPredicate<Double, Double> {
    
    public boolean testDoubleDouble(double i1, double i2);
    
    @Override
    public default boolean test(Double i1, Double i2) {
        return testDoubleDouble(i1, i2);
    }
    
    public default BiPredicate<Double, Double> toPredicate() {
        return (value1, value2) -> test(value1, value2);
    }
    
    @Override
    public default Boolean applyUnsafe(Double input1, Double input2) throws Exception {
        return test(input1, input2);
    }
    
    public static DoubleBiPredicatePrimitive doubleBiPredicate(Func2<Double, Double, Boolean> predicate) {
        if (predicate instanceof DoubleBiPredicatePrimitive)
            return ((DoubleBiPredicatePrimitive)predicate);
        
        return predicate::apply;
    }
    
    public static DoubleBiPredicatePrimitive doublePredicate(BiPredicate<Double, Double> predicate) {
        if (predicate instanceof DoubleBiPredicatePrimitive)
            return ((DoubleBiPredicatePrimitive)predicate);
        
        return predicate::test;
    }
    
}
