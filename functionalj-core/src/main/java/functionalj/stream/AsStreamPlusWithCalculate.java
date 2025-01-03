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

import static functionalj.stream.collect.Collected.collectedOf;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.aggregator.Aggregation;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple10;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import functionalj.tuple.Tuple7;
import functionalj.tuple.Tuple8;
import functionalj.tuple.Tuple9;
import lombok.val;

public interface AsStreamPlusWithCalculate<DATA> {
    
    /**
     * @return  the stream plus instance of this object.
     */
    public StreamPlus<DATA> streamPlus();
    
    public void forEach(Consumer<? super DATA> action);
    
    // TODO - Optimize this so the concurrent one can has benefit from the Java implementation
    // Still not sure how to do that properly.
    /**
     * Perform the calculation using the data of this stream
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public default <RESULT> RESULT calculate(Aggregation<? super DATA, RESULT> aggregation) {
        val collector = aggregation.collectorPlus();
        Supplier supplier = collector.supplier();
        BiConsumer accumulator = collector.accumulator();
        BinaryOperator combiner = collector.combiner();
        Function finisher = collector.finisher();
        val streamPlus = streamPlus();
        val accumulated = streamPlus.stream().collect(supplier, accumulator, (a, b) -> combiner.apply(a, b));
        val value = finisher.apply(accumulated);
        return (RESULT) value;
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2> Tuple2<RESULT1, RESULT2> calculate(Aggregation<? super DATA, RESULT1> collector1, Aggregation<? super DATA, RESULT2> collector2) {
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
    public default <RESULT1, RESULT2, RESULT3> Tuple3<RESULT1, RESULT2, RESULT3> calculate(Aggregation<? super DATA, RESULT1> collector1, Aggregation<? super DATA, RESULT2> collector2, Aggregation<? super DATA, RESULT3> collector3) {
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
    public default <RESULT1, RESULT2, RESULT3, RESULT4> Tuple4<RESULT1, RESULT2, RESULT3, RESULT4> calculate(Aggregation<? super DATA, RESULT1> collector1, Aggregation<? super DATA, RESULT2> collector2, Aggregation<? super DATA, RESULT3> collector3, Aggregation<? super DATA, RESULT4> collector4) {
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
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5> Tuple5<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5> calculate(Aggregation<DATA, RESULT1> collector1, Aggregation<DATA, RESULT2> collector2, Aggregation<DATA, RESULT3> collector3, Aggregation<DATA, RESULT4> collector4, Aggregation<DATA, RESULT5> collector5) {
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
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6> Tuple6<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6> calculate(Aggregation<DATA, RESULT1> collector1, Aggregation<DATA, RESULT2> collector2, Aggregation<DATA, RESULT3> collector3, Aggregation<DATA, RESULT4> collector4, Aggregation<DATA, RESULT5> collector5, Aggregation<DATA, RESULT6> collector6) {
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
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7> Tuple7<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7> calculate(
                Aggregation<DATA, RESULT1> collector1,
                Aggregation<DATA, RESULT2> collector2,
                Aggregation<DATA, RESULT3> collector3,
                Aggregation<DATA, RESULT4> collector4,
                Aggregation<DATA, RESULT5> collector5,
                Aggregation<DATA, RESULT6> collector6,
                Aggregation<DATA, RESULT7> collector7) {
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        val collected4 = collectedOf(collector4);
        val collected5 = collectedOf(collector5);
        val collected6 = collectedOf(collector6);
        val collected7 = collectedOf(collector7);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
            collected7.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish(),
            collected4.finish(),
            collected5.finish(),
            collected6.finish(),
            collected7.finish()
        );
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7, RESULT8> Tuple8<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7, RESULT8> calculate(
                Aggregation<DATA, RESULT1> collector1,
                Aggregation<DATA, RESULT2> collector2,
                Aggregation<DATA, RESULT3> collector3,
                Aggregation<DATA, RESULT4> collector4,
                Aggregation<DATA, RESULT5> collector5,
                Aggregation<DATA, RESULT6> collector6,
                Aggregation<DATA, RESULT7> collector7,
                Aggregation<DATA, RESULT8> collector8) {
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        val collected4 = collectedOf(collector4);
        val collected5 = collectedOf(collector5);
        val collected6 = collectedOf(collector6);
        val collected7 = collectedOf(collector7);
        val collected8 = collectedOf(collector8);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
            collected7.accumulate(each);
            collected8.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish(),
            collected4.finish(),
            collected5.finish(),
            collected6.finish(),
            collected7.finish(),
            collected8.finish()
        );
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7, RESULT8, RESULT9> Tuple9<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7, RESULT8, RESULT9> calculate(
                Aggregation<DATA, RESULT1> collector1,
                Aggregation<DATA, RESULT2> collector2,
                Aggregation<DATA, RESULT3> collector3,
                Aggregation<DATA, RESULT4> collector4,
                Aggregation<DATA, RESULT5> collector5,
                Aggregation<DATA, RESULT6> collector6,
                Aggregation<DATA, RESULT7> collector7,
                Aggregation<DATA, RESULT8> collector8,
                Aggregation<DATA, RESULT9> collector9) {
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        val collected4 = collectedOf(collector4);
        val collected5 = collectedOf(collector5);
        val collected6 = collectedOf(collector6);
        val collected7 = collectedOf(collector7);
        val collected8 = collectedOf(collector8);
        val collected9 = collectedOf(collector9);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
            collected7.accumulate(each);
            collected8.accumulate(each);
            collected9.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish(),
            collected4.finish(),
            collected5.finish(),
            collected6.finish(),
            collected7.finish(),
            collected8.finish(),
            collected9.finish()
        );
    }
    
    /**
     * Perform the calculation using the data of this stream
     */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7, RESULT8, RESULT9, RESULT10> Tuple10<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6, RESULT7, RESULT8, RESULT9, RESULT10> calculate(
                Aggregation<DATA, RESULT1> collector1,
                Aggregation<DATA, RESULT2> collector2,
                Aggregation<DATA, RESULT3> collector3,
                Aggregation<DATA, RESULT4> collector4,
                Aggregation<DATA, RESULT5> collector5,
                Aggregation<DATA, RESULT6> collector6,
                Aggregation<DATA, RESULT7> collector7,
                Aggregation<DATA, RESULT8> collector8,
                Aggregation<DATA, RESULT9> collector9,
                Aggregation<DATA, RESULT10> collector10) {
        val collected1 = collectedOf(collector1);
        val collected2 = collectedOf(collector2);
        val collected3 = collectedOf(collector3);
        val collected4 = collectedOf(collector4);
        val collected5 = collectedOf(collector5);
        val collected6 = collectedOf(collector6);
        val collected7 = collectedOf(collector7);
        val collected8 = collectedOf(collector8);
        val collected9 = collectedOf(collector9);
        val collected10 = collectedOf(collector10);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
            collected7.accumulate(each);
            collected8.accumulate(each);
            collected9.accumulate(each);
            collected10.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish(),
            collected4.finish(),
            collected5.finish(),
            collected6.finish(),
            collected7.finish(),
            collected8.finish(),
            collected9.finish(),
            collected10.finish()
        );
    }
    
}
