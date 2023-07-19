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
package functionalj.list.longlist;

import static functionalj.list.longlist.LongFuncList.deriveToLong;
import java.util.function.LongPredicate;
import functionalj.function.LongBiPredicatePrimitive;

public interface LongFuncListWithLimit extends AsLongFuncList {
    
    /**
     * Limit the size of the stream to the given size.
     */
    public default LongFuncList limit(Long maxSize) {
        return deriveToLong(this, stream -> stream.limit(maxSize));
    }
    
    /**
     * Skip to the given offset position.
     */
    public default LongFuncList skip(Long startAt) {
        return deriveToLong(this, stream -> stream.skip(startAt));
    }
    
    /**
     * Skip any value while the condition is true.
     */
    public default LongFuncList skipWhile(LongPredicate condition) {
        return deriveToLong(this, stream -> stream.skipWhile(condition));
    }
    
    /**
     * Skip any value while the condition is true.
     */
    public default LongFuncList skipWhile(LongBiPredicatePrimitive condition) {
        return deriveToLong(this, stream -> stream.skipWhile(condition));
    }
    
    /**
     * Skip any value until the condition is true.
     */
    public default LongFuncList skipUntil(LongPredicate condition) {
        return deriveToLong(this, stream -> stream.skipUntil(condition));
    }
    
    /**
     * Skip any value until the condition is true.
     */
    public default LongFuncList skipUntil(LongBiPredicatePrimitive condition) {
        return deriveToLong(this, stream -> stream.skipUntil(condition));
    }
    
    /**
     * Accept any value while the condition is true.
     */
    public default LongFuncList acceptWhile(LongPredicate condition) {
        return deriveToLong(this, stream -> stream.acceptWhile(condition));
    }
    
    /**
     * Accept any value while the condition is true.
     */
    public default LongFuncList acceptWhile(LongBiPredicatePrimitive condition) {
        return deriveToLong(this, stream -> stream.acceptWhile(condition));
    }
    
    /**
     * Accept any value until the condition is true.
     */
    public default LongFuncList acceptUntil(LongPredicate condition) {
        return deriveToLong(this, stream -> stream.acceptUntil(condition));
    }
    
    /**
     * Accept any value until the condition is true.
     */
    public default LongFuncList acceptUntil(LongBiPredicatePrimitive condition) {
        return deriveToLong(this, stream -> stream.acceptUntil(condition));
    }
    
    /**
     * Accept any value while the condition is true.
     */
    public default LongFuncList dropAfter(LongPredicate condition) {
        return deriveToLong(this, stream -> stream.dropAfter(condition));
    }
    
    /**
     * Accept any value while the condition is true.
     */
    public default LongFuncList dropAfter(LongBiPredicatePrimitive condition) {
        return deriveToLong(this, stream -> stream.dropAfter(condition));
    }
}
