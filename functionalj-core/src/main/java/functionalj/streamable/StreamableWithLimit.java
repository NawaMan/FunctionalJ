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
package functionalj.streamable;

import static functionalj.streamable.Streamable.deriveFrom;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface StreamableWithLimit<DATA> extends AsStreamable<DATA> {
    
    /** Limit the size of the stream to the given size. */
    public default Streamable<DATA> limit(Long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /** Skip to the given offset position. */
    public default Streamable<DATA> skip(Long startAt) {
        return deriveFrom(this, stream -> stream.skip(startAt));
    }
    
    /** Skip any value while the condition is true. */
    public default Streamable<DATA> skipWhile(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.skipWhile(condition));
    }
    
    /** Skip any value until the condition is true. */
    public default Streamable<DATA> skipUntil(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.skipUntil(condition));
    }
    
    /** Accept any value while the condition is true. */
    public default Streamable<DATA> takeWhile(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.takeWhile(condition));
    }
    
    /** Accept any value while the condition is true. */
    public default Streamable<DATA> takeWhile(BiPredicate<? super DATA, ? super DATA> condition) {
        return deriveFrom(this, stream -> stream.takeWhile(condition));
    }
    
    /** Accept any value until the condition is true. */
    public default Streamable<DATA> takeUntil(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.takeUntil(condition));
    }
    
    /** Accept any value until the condition is true. */
    public default Streamable<DATA> takeUntil(BiPredicate<? super DATA, ? super DATA> condition) {
        return deriveFrom(this, stream -> stream.takeUntil(condition));
    }
    
    /** Accept any value until the condition is false - include the item that the condition is false. */
    public default Streamable<DATA> dropAfter(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.dropAfter(condition));
    }
    
    /** Accept any value until the condition is false - include the item that the condition is false. */
    public default Streamable<DATA> dropAfter(BiPredicate<? super DATA, ? super DATA> condition) {
        return deriveFrom(this, stream -> stream.dropAfter(condition));
    }

}
