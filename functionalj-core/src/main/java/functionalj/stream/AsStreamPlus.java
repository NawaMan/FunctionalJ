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
package functionalj.stream;

import java.util.function.Consumer;
import java.util.stream.Stream;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

class AsStreamPlusHelper {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public static <D> StreamPlus<D> streamFrom(AsStreamPlus<D> streamPlus) {
        return streamPlus.streamPlus();
    }
}

/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @param <DATA> the data type of the stream plus.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsStreamPlus<DATA> 
        extends
            AsStreamPlusWithCalculate<DATA>,
            AsStreamPlusWithCollect<DATA>,
            AsStreamPlusWithConversion<DATA>,
            AsStreamPlusWithForEach<DATA>,
            AsStreamPlusWithGroupingBy<DATA>,
            AsStreamPlusWithMatch<DATA>,
            AsStreamPlusWithReduce<DATA>,
            AsStreamPlusWithStatistic<DATA> {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public StreamPlus<DATA> streamPlus();
    
    /**
     * @return  return the stream underneath the stream plus.
     */
    public default Stream<DATA> stream() {
        return streamPlus();
    }
    
    /**
     * Iterate all element through the action
     */
    @Eager
    @Terminal
    public default void forEach(Consumer<? super DATA> action) {
        val streamPlus = streamPlus();
        streamPlus.forEach(action);
    }
}
