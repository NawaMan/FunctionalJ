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
package functionalj.list.longlist;

import static functionalj.list.longlist.LongFuncList.deriveFrom;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

public interface LongFuncListWithPeek extends AsLongFuncList {
    
    /**
     * Peek only the value that is selected with selector.
     */
    public default LongFuncList peekBy(LongPredicate selector, LongConsumer theConsumer) {
        return LongFuncList.from(() -> longStreamPlus().peekBy(selector, theConsumer));
    }
    
    // TODO - peekByInt, peekByLong, peekByDouble, peekByObj
    // TODO - peekAsInt, peekAsLong, peekAsDouble, peekAsObj
    /**
     * Peek the mapped value using the mapper.
     */
    public default <T> LongFuncList peekAs(LongFunction<T> mapper, Consumer<? super T> theConsumer) {
        return deriveFrom(this, stream -> stream.peekAs(mapper, theConsumer));
    }
    
    /**
     * Peek only the mapped value using the mapper.
     */
    public default <T> LongFuncList peekBy(LongFunction<T> mapper, Predicate<? super T> selector, LongConsumer theConsumer) {
        return deriveFrom(this, stream -> stream.peekBy(mapper, selector, theConsumer));
    }
    
    /**
     * Peek only the mapped value using the mapper that is selected by the selector.
     */
    public default <T> LongFuncList peekAs(LongFunction<T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return deriveFrom(this, stream -> stream.peekAs(mapper, selector, theConsumer));
    }
}
