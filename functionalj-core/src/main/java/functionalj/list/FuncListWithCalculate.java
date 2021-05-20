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
package functionalj.list;

import static functionalj.stream.collect.Collected.collectedOf;

import functionalj.function.aggregator.Aggregation;
import functionalj.stream.AsStreamPlus;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;


public interface FuncListWithCalculate<DATA> {
    
    public FuncList<DATA> asFuncList();
    
    
    /** Perform the calculation using the data of this funcList */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public default <A, T> T calculate(
            Aggregation<? super DATA, T> aggregation) {
        val funcList = asFuncList();
        return (T) aggregation.apply((AsStreamPlus)funcList);
    }
    
    /** Perform the calculation using the data of this funcList */
    public default <T1, T2> Tuple2<T1, T2> calculate(
            Aggregation<DATA, T1> aggregation1,
            Aggregation<DATA, T2> aggregation2) {
        // TODO - Check if the processor is not Collector ... then there is no need to accumulate ... just call finish.
        val funcList   = asFuncList();
        val collected1 = collectedOf(funcList, aggregation1.collector());
        val collected2 = collectedOf(funcList, aggregation2.collector());
        funcList
        .forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        return Tuple.of(value1, value2);
    }
    
    /** Perform the calculation using the data of this funcList */
    public default <T1, T2, T3> Tuple3<T1, T2, T3> calculate(
            Aggregation<DATA, T1> aggregation1,
            Aggregation<DATA, T2> aggregation2,
            Aggregation<DATA, T3> aggregation3) {
        // TODO - Check if the processor is not Collector ... then there is no need to accumulate ... just call finish.
        val funcList   = asFuncList();
        val collected1 = collectedOf(funcList, aggregation1.collector());
        val collected2 = collectedOf(funcList, aggregation2.collector());
        val collected3 = collectedOf(funcList, aggregation3.collector());
        funcList
        .forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        return Tuple.of(value1, value2, value3);
    }
    
    /** Perform the calculation using the data of this funcList */
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> calculate(
            Aggregation<DATA, T1> aggregation1,
            Aggregation<DATA, T2> aggregation2,
            Aggregation<DATA, T3> aggregation3,
            Aggregation<DATA, T4> aggregation4) {
        // TODO - Check if the processor is not Collector ... then there is no need to accumulate ... just call finish.
        val funcList   = asFuncList();
        val collected1 = collectedOf(funcList, aggregation1.collector());
        val collected2 = collectedOf(funcList, aggregation2.collector());
        val collected3 = collectedOf(funcList, aggregation3.collector());
        val collected4 = collectedOf(funcList, aggregation4.collector());
        funcList
        .forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        val value4 = collected4.finish();
        return Tuple.of(value1, value2, value3, value4);
    }
    
    /** Perform the calculation using the data of this funcList */
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> calculate(
            Aggregation<DATA, T1> aggregation1,
            Aggregation<DATA, T2> aggregation2,
            Aggregation<DATA, T3> aggregation3,
            Aggregation<DATA, T4> aggregation4,
            Aggregation<DATA, T5> aggregation5) {
        // TODO - Check if the processor is not Collector ... then there is no need to accumulate ... just call finish.
        val funcList   = asFuncList();
        val collected1 = collectedOf(funcList, aggregation1.collector());
        val collected2 = collectedOf(funcList, aggregation2.collector());
        val collected3 = collectedOf(funcList, aggregation3.collector());
        val collected4 = collectedOf(funcList, aggregation4.collector());
        val collected5 = collectedOf(funcList, aggregation5.collector());
        funcList
        .forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        val value4 = collected4.finish();
        val value5 = collected5.finish();
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    /** Perform the calculation using the data of this funcList */
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> calculate(
            Aggregation<DATA, T1> aggregation1,
            Aggregation<DATA, T2> aggregation2,
            Aggregation<DATA, T3> aggregation3,
            Aggregation<DATA, T4> aggregation4,
            Aggregation<DATA, T5> aggregation5,
            Aggregation<DATA, T6> aggregation6) {
        // TODO - Check if the processor is not Collector ... then there is no need to accumulate ... just call finish.
        val funcList   = asFuncList();
        val collected1 = collectedOf(funcList, aggregation1.collector());
        val collected2 = collectedOf(funcList, aggregation2.collector());
        val collected3 = collectedOf(funcList, aggregation3.collector());
        val collected4 = collectedOf(funcList, aggregation4.collector());
        val collected5 = collectedOf(funcList, aggregation5.collector());
        val collected6 = collectedOf(funcList, aggregation6.collector());
        funcList
        .forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
            collected3.accumulate(each);
            collected4.accumulate(each);
            collected5.accumulate(each);
            collected6.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        val value3 = collected3.finish();
        val value4 = collected4.finish();
        val value5 = collected5.finish();
        val value6 = collected6.finish();
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
}
