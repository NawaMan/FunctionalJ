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

import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamProcessor;
import functionalj.stream.intstream.collect.IntCollectorToDoublePlus;
import lombok.val;

@FunctionalInterface
public interface IntAggregatorToDouble extends IntStreamProcessor<Double> {
    
    public static <A> IntAggregatorToDouble from(IntCollectorToDoublePlus<A> collector) {
        return () -> collector;
    }
    
    public IntCollectorToDoublePlus<?> collectorToDouble();
    
    
    public default Double process(IntStreamPlus stream) {
        val collector = collectorToDouble();
        return ((IntStreamProcessor<Double>)collector).process(stream);
    }
    
    public default IntAccumulatorToDouble newIntAccumulatorToDouble() {
        val collector = collectorToDouble();
        return new IntAccumulatorToDouble(collector);
    }
    
}
