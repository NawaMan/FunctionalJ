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
package functionalj.stream.longstream;

import java.util.function.LongConsumer;

import functionalj.function.aggregator.LongAggregation;
import functionalj.stream.longstream.collect.LongCollected;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;


public interface AsLongStreamPlusWithCalculate {
    
    public void forEach(LongConsumer action);
    
    
    // TODO - Optimize this so the concurrent one can has benefit from the Java implementation
    //        Still not sure how to do that.
    
    /** Perform the calculation using the data of this stream */
    public default <RESULT> RESULT calculate(
            LongAggregation<RESULT> collector) {
        val collected = LongCollected.of(collector);
        forEach(each -> {
            collected.accumulate(each);
        });
        val value = collected.finish();
        return value;
    }
    
    /** Perform the calculation using the data of this stream */
    public default <RESULT1, RESULT2>
                        Tuple2<RESULT1, RESULT2> 
                        calculate(
                                LongAggregation<RESULT1> collector1,
                                LongAggregation<RESULT2> collector2) {
        val collected1 = LongCollected.of(collector1);
        val collected2 = LongCollected.of(collector2);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish()
        );
    }
    
    /** Perform the calculation using the data of this stream */
    public default <RESULT1, RESULT2, RESULT3>
                        Tuple3<RESULT1, RESULT2, RESULT3> 
                        calculate(
                                LongAggregation<RESULT1> collector1,
                                LongAggregation<RESULT2> collector2,
                                LongAggregation<RESULT3> collector3) {
        val collected1 = LongCollected.of(collector1);
        val collected2 = LongCollected.of(collector2);
        val collected3 = LongCollected.of(collector3);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish()
        );
    }
    
    /** Perform the calculation using the data of this stream */
    public default <RESULT1, RESULT2, RESULT3, RESULT4>
                        Tuple4<RESULT1, RESULT2, RESULT3, RESULT4> 
                        calculate(
                                LongAggregation<RESULT1> collector1,
                                LongAggregation<RESULT2> collector2,
                                LongAggregation<RESULT3> collector3,
                                LongAggregation<RESULT4> collector4) {
        val collected1 = LongCollected.of(collector1);
        val collected2 = LongCollected.of(collector2);
        val collected3 = LongCollected.of(collector3);
        val collected4 = LongCollected.of(collector4);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish(),
            collected4.finish()
        );
    }
    
    /** Perform the calculation using the data of this stream */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5>
                        Tuple5<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5> 
                        calculate(
                                LongAggregation<RESULT1> collector1,
                                LongAggregation<RESULT2> collector2,
                                LongAggregation<RESULT3> collector3,
                                LongAggregation<RESULT4> collector4,
                                LongAggregation<RESULT5> collector5) {
        val collected1 = LongCollected.of(collector1);
        val collected2 = LongCollected.of(collector2);
        val collected3 = LongCollected.of(collector3);
        val collected4 = LongCollected.of(collector4);
        val collected5 = LongCollected.of(collector5);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish(),
            collected4.finish(),
            collected5.finish()
        );
    }
    
    /** Perform the calculation using the data of this stream */
    public default <RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6>
                        Tuple6<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6> 
                        calculate(
                                LongAggregation<RESULT1> collector1,
                                LongAggregation<RESULT2> collector2,
                                LongAggregation<RESULT3> collector3,
                                LongAggregation<RESULT4> collector4,
                                LongAggregation<RESULT5> collector5,
                                LongAggregation<RESULT6> collector6) {
        val collected1 = LongCollected.of(collector1);
        val collected2 = LongCollected.of(collector2);
        val collected3 = LongCollected.of(collector3);
        val collected4 = LongCollected.of(collector4);
        val collected5 = LongCollected.of(collector5);
        val collected6 = LongCollected.of(collector6);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
        });
        return Tuple.of(
            collected1.finish(),
            collected2.finish(),
            collected3.finish(),
            collected4.finish(),
            collected5.finish(),
            collected6.finish()
        );
    }
    
}
