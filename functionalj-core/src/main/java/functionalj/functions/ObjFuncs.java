// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.functions;

import java.util.Objects;
import functionalj.function.Func1;
import functionalj.function.Func2;

public class ObjFuncs {
    
    @SuppressWarnings("unlikely-arg-type")
    public static <I1, I2> Func2<I1, I2, Boolean> areEqual() {
        return (i1, i2) -> Objects.equals(i1, i2);
    }
    
    @SuppressWarnings("unlikely-arg-type")
    public static <I1, I2> Func2<I1, I2, Boolean> notEqual() {
        return (i1, i2) -> !Objects.equals(i1, i2);
    }
    
    public static <I> Func1<I, Boolean> equalsTo(I i1) {
        return i -> Objects.equals(i1, i);
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> lessThan(I i1) {
        return i -> i.compareTo(i1) < 0;
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> greaterThan(I i1) {
        return i -> i.compareTo(i1) > 0;
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> lessThanOrEqualsTo(I i1) {
        return i -> i.compareTo(i1) <= 0;
    }
    
    public static <I extends Comparable<I>> Func1<I, Boolean> greaterThanOrEqualsTo(I i1) {
        return i -> i.compareTo(i1) >= 0;
    }
}
