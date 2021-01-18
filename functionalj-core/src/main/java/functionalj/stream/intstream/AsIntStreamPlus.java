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
package functionalj.stream.intstream;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;


class AsIntStreamPlusHelper {
    
    /** @return  the stream plus instance of this object. */
    public static IntStreamPlus streamFrom(AsIntStreamPlus streamPlus) {
        return streamPlus.intStreamPlus();
    }
    
}

/**
 * Classes implementing this interface can provider a StreamPlus instance of itself.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsIntStreamPlus
                    extends
                        AsIntStreamPlusWithConversion,
                        AsIntStreamPlusWithCollect,
                        AsIntStreamPlusWithForEach,
                        AsIntStreamPlusWithGroupingBy,
                        AsIntStreamPlusWithMatch,
                        AsIntStreamPlusWithReduce,
                        AsIntStreamPlusWithStatistic {
    
    /** @return  the stream plus instance of this object. */
    public IntStreamPlus intStreamPlus();
    
    /** @return  return the stream underneath the stream plus. */
    public default IntStream intStream() {
        return intStreamPlus();
    }
    
    /** Iterate all element through the action */
    @Eager
    @Terminal
    public default void forEach(IntConsumer action) {
        intStreamPlus().forEach(action);
    }
    
}
