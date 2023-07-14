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
package functionalj.stream.doublestream;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.DoubleFunction;
import functionalj.stream.markers.Eager;
import lombok.val;

public interface DoubleStreamPlusWithSort {

    public DoubleStreamPlus doubleStreamPlus();

    /**
     * Sort the values by the mapped value.
     */
    @Eager
    public default <T extends Comparable<? super T>> DoubleStreamPlus sortedBy(DoubleFunction<T> mapper) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.sorted((a, b) -> {
            T vA = mapper.apply(a);
            T vB = mapper.apply(b);
            return vA.compareTo(vB);
        });
    }

    /**
     * Sort the values by the mapped value using the comparator.
     */
    @Eager
    public default <T> DoubleStreamPlus sortedBy(DoubleFunction<T> mapper, Comparator<T> comparator) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.sorted((a, b) -> {
            T vA = mapper.apply(a);
            T vB = mapper.apply(b);
            return Objects.compare(vA, vB, comparator);
        });
    }
    // TODO - sortedAsIntBy, sortedAsLongBy, sortedAsDoubleBy
}
