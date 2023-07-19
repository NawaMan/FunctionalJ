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

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import functionalj.function.Func;
import functionalj.function.aggregator.DoubleAggregation;
import functionalj.function.aggregator.DoubleAggregationToDouble;
import functionalj.function.aggregator.DoubleAggregationToInt;
import functionalj.function.aggregator.DoubleAggregationToLong;
import functionalj.stream.doublestream.collect.DoubleCollectorPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;

public interface AsDoubleStreamPlusWithCollect {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /**
     * Performs a mutable reduction operation on the elements of this stream. A mutable reduction is one in which the reduced value is
     * a mutable result container, such as an {@code ArrayList}, and elements are incorporated by updating the state of the result rather
     * than by replacing the result.
     */
    @Eager
    @Terminal
    public default <RESULT> RESULT collect(Supplier<RESULT> supplier, ObjDoubleConsumer<RESULT> accumulator, BiConsumer<RESULT, RESULT> combiner) {
        val streamPlus = doubleStreamPlus();
        return streamPlus.collect(supplier, accumulator, combiner);
    }
    
    @Eager
    @Terminal
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <ACCUMULATOR, RESULT> RESULT collect(DoubleCollectorPlus<ACCUMULATOR, RESULT> collector) {
        val supplier = (Supplier) collector.supplier();
        val combiner = Func.f((ACCUMULATOR r1, ACCUMULATOR r2) -> {
            BinaryOperator simpleCombiner = collector.combiner();
            simpleCombiner.apply(r1, r2);
        });
        ObjDoubleConsumer<ACCUMULATOR> accumulator = (ACCUMULATOR r, double v) -> {
            // This is ridiculous but work. Sorry.
            Object objectR = (Object) r;
            ACCUMULATOR resultR = (ACCUMULATOR) objectR;
            BiConsumer simpleAccumulator = collector.accumulator();
            simpleAccumulator.accept(resultR, v);
        };
        val finisher = collector.finisher();
        val streamPlus = doubleStreamPlus();
        val accumulated = streamPlus.collect(supplier, accumulator, combiner);
        val result = finisher.apply((ACCUMULATOR) accumulated);
        return result;
    }
    
    @Eager
    @Terminal
    public default <RESULT> RESULT aggregate(DoubleAggregation<RESULT> aggregation) {
        val collector = aggregation.doubleCollectorPlus();
        return collect(collector);
    }
    
    @Eager
    @Terminal
    public default int aggregateToInt(DoubleAggregationToInt aggregation) {
        val collector = aggregation.doubleCollectorPlus();
        return collect(collector);
    }
    
    @Eager
    @Terminal
    public default long aggregateToLong(DoubleAggregationToLong aggregation) {
        val collector = aggregation.doubleCollectorPlus();
        return collect(collector);
    }
    
    @Eager
    @Terminal
    public default double aggregateToDouble(DoubleAggregationToDouble aggregation) {
        val collector = aggregation.doubleCollectorPlus();
        return collect(collector);
    }
}
