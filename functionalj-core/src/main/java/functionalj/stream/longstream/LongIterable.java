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
package functionalj.stream.longstream;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;
import lombok.val;

@FunctionalInterface
public interface LongIterable {
    
    public LongIteratorPlus iterator();
    
    public default LongIterable __data() throws Exception {
        return this;
    }
    
    default void forEach(LongConsumer action) {
        Objects.requireNonNull(action);
        val iterator = iterator();
        while (iterator.hasNext()) {
            val i = iterator.nextLong();
            action.accept(i);
        }
    }
    
    default Spliterator.OfLong spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}
