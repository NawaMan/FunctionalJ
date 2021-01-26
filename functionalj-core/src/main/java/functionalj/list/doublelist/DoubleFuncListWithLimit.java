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


import static functionalj.list.doublelist.DoubleFuncList.deriveToDouble;

import java.util.function.DoublePredicate;

import functionalj.function.DoubleBiPredicatePrimitive;

public interface DoubleFuncListWithLimit extends AsDoubleFuncList {
    
    /** Limit the size of the stream to the given size. */
    public default DoubleFuncList limit(Long maxSize) {
        return deriveToDouble(this, stream -> stream.limit(maxSize));
    }
    
    /** Skip to the given offset position. */
    public default DoubleFuncList skip(Long startAt) {
        return deriveToDouble(this, stream -> stream.skip(startAt));
    }
    
    /** Skip any value while the condition is true. */
    public default DoubleFuncList skipWhile(DoublePredicate condition) {
        return deriveToDouble(this, stream -> stream.skipWhile(condition));
    }
    
    /** Skip any value until the condition is true. */
    public default DoubleFuncList skipUntil(DoublePredicate condition) {
        return deriveToDouble(this, stream -> stream.skipUntil(condition));
    }
    
    /** Skip any value until the condition is true. */
    public default DoubleFuncList takeWhile(DoubleBiPredicatePrimitive condition) {
        return deriveToDouble(this, stream -> stream.takeWhile(condition));
    }
    
    /** Accept any value while the condition is true. */
    public default DoubleFuncList takeWhile(DoublePredicate condition) {
        return deriveToDouble(this, stream -> stream.takeWhile(condition));
    }
    
    /** Accept any value until the condition is true. */
    public default DoubleFuncList takeUntil(DoublePredicate condition) {
        return deriveToDouble(this, stream -> stream.takeUntil(condition));
    }
    
    /** Accept any value until the condition is true. */
    public default DoubleFuncList takeUntil(DoubleBiPredicatePrimitive condition) {
        return deriveToDouble(this, stream -> stream.takeUntil(condition));
    }
    
    /** Accept any value while the condition is true. */
    public default DoubleFuncList dropAfter(DoublePredicate condition) {
        return deriveToDouble(this, stream -> stream.dropAfter(condition));
    }
    
    /** Accept any value while the condition is true. */
    public default DoubleFuncList dropAfter(DoubleBiPredicatePrimitive condition) {
        return deriveToDouble(this, stream -> stream.dropAfter(condition));
    }
    
}
