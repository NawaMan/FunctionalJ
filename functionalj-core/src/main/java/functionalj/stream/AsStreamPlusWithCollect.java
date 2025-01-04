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

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import functionalj.function.aggregator.Aggregation;
import functionalj.function.aggregator.AggregationToDouble;
import functionalj.function.aggregator.AggregationToInt;
import functionalj.function.aggregator.AggregationToLong;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsStreamPlusWithCollect<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    @Eager
    @Terminal
    public default <RESULT> RESULT collect(Supplier<RESULT> supplier, BiConsumer<RESULT, ? super DATA> accumulator, BiConsumer<RESULT, RESULT> combiner) {
        val streamPlus = streamPlus();
        return streamPlus.collect(supplier, accumulator, combiner);
    }
    
    @Eager
    @Terminal
    public default <RESULT, ACCUMULATOR> RESULT collect(Collector<? super DATA, ACCUMULATOR, RESULT> collector) {
        val streamPlus = streamPlus();
        return streamPlus.collect(collector);
    }
    
    @Eager
    @Terminal
    public default <RESULT> RESULT aggregate(Aggregation<? super DATA, RESULT> aggregation) {
        val collector = aggregation.collectorPlus();
        val streamPlus = streamPlus();
        return streamPlus.collect(collector);
    }
    
    @Eager
    @Terminal
    public default int aggregateToInt(AggregationToInt<? super DATA> aggregation) {
        val collector = aggregation.collectorPlus();
        val streamPlus = streamPlus();
        return streamPlus.collect(collector);
    }
    
    @Eager
    @Terminal
    public default long aggregateToLong(AggregationToLong<? super DATA> aggregation) {
        val collector = aggregation.collectorPlus();
        val streamPlus = streamPlus();
        return streamPlus.collect(collector);
    }
    
    @Eager
    @Terminal
    public default double aggregateToDouble(AggregationToDouble<? super DATA> aggregation) {
        val collector = aggregation.collectorPlus();
        val streamPlus = streamPlus();
        return streamPlus.collect(collector);
    }
}
