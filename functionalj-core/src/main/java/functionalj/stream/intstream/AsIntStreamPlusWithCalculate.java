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

import static functionalj.stream.intstream.collect.IntCollected.collectedOf;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import functionalj.function.aggregator.IntAggregation;
import functionalj.function.aggregator.IntAggregationToInt;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface AsIntStreamPlusWithCalculate {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public IntStreamPlus intStreamPlus();
    
    public void forEach(IntConsumer action);
    
    // TODO - Optimize this so the concurrent one can has benefit from the Java implementation
    // Still not sure how to do that.
    /**
     * Perform the calculation using the data of this stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <RESULT> RESULT calculate(IntAggregation<RESULT> aggregation) {
        val collector = aggregation.intCollectorPlus();
        Supplier supplier = collector.supplier();
        ObjIntConsumer accumulator = collector.intAccumulator();
        BinaryOperator combiner = collector.combiner();
        Function finisher = collector.finisher();
        val streamPlus = intStreamPlus();
        val accumulated = streamPlus.intStream().collect(supplier, accumulator, (a, b) -> combiner.apply(a, b));
        val value = finisher.apply(accumulated);
        return (RESULT) value;
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default int calculate(IntAggregationToInt aggregation) {
        val collector = aggregation.intCollectorToIntPlus();
        Supplier supplier = collector.supplier();
        ObjIntConsumer accumulator = collector.intAccumulator();
        BinaryOperator combiner = collector.combiner();
        ToIntFunction finisher = collector.finisherToInt();
        val streamPlus = intStreamPlus();
        val accumulated = streamPlus.intStream().collect(supplier, accumulator, (a, b) -> combiner.apply(a, b));
        val value = finisher.applyAsInt(accumulated);
        return value;
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2> Tuple2<RESULT1, RESULT2> calculate(IntAggregation<RESULT1> collector1, IntAggregation<RESULT2> collector2) {
        // TODO - Created combined collectors
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
        });
        return Tuple.of(collected1.finish(), collected2.finish());
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3> Tuple3<RESULT1, RESULT2, RESULT3> calculate(IntAggregation<RESULT1> collector1, IntAggregation<RESULT2> collector2, IntAggregation<RESULT3> collector3) {
        // TODO - Created combined collectors
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
        });
        return Tuple.of(collected1.finish(), collected2.finish(), collected3.finish());
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3, RESULT4> Tuple4<RESULT1, RESULT2, RESULT3, RESULT4> calculate(IntAggregation<RESULT1> collector1, IntAggregation<RESULT2> collector2, IntAggregation<RESULT3> collector3, IntAggregation<RESULT4> collector4) {
        // TODO - Created combined collectors
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        val collected4 = collectedOf(collector4);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
        });
        return Tuple.of(collected1.finish(), collected2.finish(), collected3.finish(), collected4.finish());
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5> Tuple5<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5> calculate(IntAggregation<RESULT1> collector1, IntAggregation<RESULT2> collector2, IntAggregation<RESULT3> collector3, IntAggregation<RESULT4> collector4, IntAggregation<RESULT5> collector5) {
        // TODO - Created combined collectors
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        val collected4 = collectedOf(collector4);
        val collected5 = collectedOf(collector5);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
        });
        return Tuple.of(collected1.finish(), collected2.finish(), collected3.finish(), collected4.finish(), collected5.finish());
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6> Tuple6<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6> calculate(IntAggregation<RESULT1> collector1, IntAggregation<RESULT2> collector2, IntAggregation<RESULT3> collector3, IntAggregation<RESULT4> collector4, IntAggregation<RESULT5> collector5, IntAggregation<RESULT6> collector6) {
        // TODO - Created combined collectors
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        val collected4 = collectedOf(collector4);
        val collected5 = collectedOf(collector5);
        val collected6 = collectedOf(collector6);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
        });
        return Tuple.of(collected1.finish(), collected2.finish(), collected3.finish(), collected4.finish(), collected5.finish(), collected6.finish());
    }
}
