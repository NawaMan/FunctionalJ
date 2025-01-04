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
package functionalj.function;

import java.util.function.LongPredicate;
import java.util.function.Predicate;

import functionalj.lens.lenses.BooleanAccessPrimitive;

@FunctionalInterface
public interface LongPredicatePrimitive extends LongPredicate, Func1<Long, Boolean> {
    
    public boolean test(long value);
    
    public default Predicate<Long> toPredicate() {
        return this::test;
    }
    
    public default BooleanAccessPrimitive<Long> toAccess() {
        return host -> test(host.longValue());
    }
    
    @Override
    public default Boolean applyUnsafe(Long input) throws Exception {
        return test(input);
    }
    
    public static LongPredicatePrimitive longPredicate(LongPredicate predicate) {
        if (predicate instanceof LongPredicatePrimitive)
            return ((LongPredicatePrimitive) predicate);
        return predicate::test;
    }
    
    public static LongPredicatePrimitive longPredicate(Func1<Long, Boolean> predicate) {
        if (predicate instanceof LongPredicatePrimitive)
            return ((LongPredicatePrimitive) predicate);
        return predicate::apply;
    }
    
    public static LongPredicatePrimitive longPredicate(Predicate<Long> predicate) {
        if (predicate instanceof LongPredicatePrimitive)
            return ((LongPredicatePrimitive) predicate);
        return predicate::test;
    }
}
