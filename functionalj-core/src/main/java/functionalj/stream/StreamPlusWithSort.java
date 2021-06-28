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
package functionalj.stream;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

import functionalj.stream.markers.Eager;
import lombok.val;


public interface StreamPlusWithSort<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /** Sort the values by the mapped value. */
    @Eager
    public default <T extends Comparable<? super T>> StreamPlus<DATA> sortedBy(Function<? super DATA, T> mapper) {
        val streamPlus = streamPlus();
        return streamPlus
                .sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return vA.compareTo(vB);
                });
    }
    
    /** Sort the values by the mapped value using the comparator. */
    @Eager
    public default <T> StreamPlus<DATA> sortedBy(
            Function<? super DATA, T> mapper, 
            Comparator<T>             comparator) {
        val streamPlus = streamPlus();
        return streamPlus
                .sorted((a, b) -> {
                    T vA = mapper.apply(a);
                    T vB = mapper.apply(b);
                    return Objects.compare(vA, vB, comparator);
                });
    }
    
}
