// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.intstream;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import functionalj.function.IntBiConsumer;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsIntStreamPlusWithForEach {
    
    public IntStreamPlus intStreamPlus();
    
    @Eager
    @Terminal
    @Sequential
    public default void forEachOrdered(IntConsumer action) {
        intStreamPlus().forEachOrdered(action);
    }
    
    /**
     * For each with the index.
     */
    @Eager
    @Terminal
    public default void forEachWithIndex(IntBiConsumer action) {
        val streamPlus = intStreamPlus();
        val index = new AtomicInteger();
        streamPlus.forEach(each -> {
            val currentIndex = index.getAndIncrement();
            action.accept(currentIndex, each);
        });
    }
    
    /**
     * Populate the array with the population in the stream from 0 to length or until run out of elements.
     */
    @Terminal
    public default void populateArray(int[] array) {
        val streamPlus = intStreamPlus();
        streamPlus.limit(array.length).forEachWithIndex((index, element) -> {
            array[index] = element;
        });
    }
    
    /**
     * Populate the array with the population in the stream from offset to length or until run out of elements.
     */
    @Terminal
    public default void populateArray(int[] array, int offset) {
        val streamPlus = intStreamPlus();
        streamPlus.limit(array.length - offset).forEachWithIndex((index, element) -> {
            array[offset + index] = element;
        });
    }
    
    /**
     * Populate the array with the population in the stream from offset to length or until run out of elements.
     */
    @Terminal
    public default void populateArray(int[] array, int offset, int length) {
        val streamPlus = intStreamPlus();
        streamPlus.limit(Math.min(length, array.length - offset)).forEachWithIndex((index, element) -> {
            array[offset + index] = element;
        });
    }
}
