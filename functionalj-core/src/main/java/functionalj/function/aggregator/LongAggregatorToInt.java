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
package functionalj.function.aggregator;

import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.longstream.LongStreamProcessor;
import functionalj.stream.longstream.collect.LongCollectorToIntPlus;
import lombok.val;

@FunctionalInterface
public interface LongAggregatorToInt extends LongStreamProcessor<Integer> {
    
    public static <A> LongAggregatorToInt from(LongCollectorToIntPlus<A> collector) {
        return () -> collector;
    }
    
    public LongCollectorToIntPlus<?> collectorToInt();
    
    
    public default Integer process(LongStreamPlus stream) {
        val collector = collectorToInt();
        return ((LongStreamProcessor<Integer>)collector).process(stream);
    }
    
    public default LongAccumulatorToInt newLongAccumulatorToInt() {
        val collector = collectorToInt();
        return new LongAccumulatorToInt(collector);
    }
    
}