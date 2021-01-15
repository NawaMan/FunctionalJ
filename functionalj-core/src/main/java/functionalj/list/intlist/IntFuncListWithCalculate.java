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
package functionalj.list.intlist;

import static functionalj.list.intlist.AsIntFuncListHelper.funcListOf;
import static functionalj.stream.Collected.CollectedInt.collectedOf;

import functionalj.stream.intstream.IntStreamProcessor;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface IntFuncListWithCalculate extends AsIntFuncList {
    
    /** Perform the calculation using the data of this funcList */
    public default <A, T> T calculate(
            IntStreamProcessor<T> processor) {
        val funcList  = funcListOf(this);
        val collected = collectedOf(funcList, processor);
        funcList
        .forEach(each -> {
            collected.accumulate(each);
        });
        val value = collected.finish();
        return value;
    }
    
    /** Perform the calculation using the data of this funcList */
    public default <T1, T2> Tuple2<T1, T2> calculate(
            IntStreamProcessor<T1> processor1,
            IntStreamProcessor<T2> processor2) {
        val funcList   = funcListOf(this);
        val collected1 = collectedOf(funcList, processor1);
        val collected2 = collectedOf(funcList, processor2);
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
            IntStreamProcessor<T1> processor1,
            IntStreamProcessor<T2> processor2,
            IntStreamProcessor<T3> processor3) {
        val funcList   = funcListOf(this);
        val collected1 = collectedOf(funcList, processor1);
        val collected2 = collectedOf(funcList, processor2);
        val collected3 = collectedOf(funcList, processor3);
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
            IntStreamProcessor<T1> processor1,
            IntStreamProcessor<T2> processor2,
            IntStreamProcessor<T3> processor3,
            IntStreamProcessor<T4> processor4) {
        val funcList   = funcListOf(this);
        val collected1 = collectedOf(funcList, processor1);
        val collected2 = collectedOf(funcList, processor2);
        val collected3 = collectedOf(funcList, processor3);
        val collected4 = collectedOf(funcList, processor4);
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
            IntStreamProcessor<T1> processor1,
            IntStreamProcessor<T2> processor2,
            IntStreamProcessor<T3> processor3,
            IntStreamProcessor<T4> processor4,
            IntStreamProcessor<T5> processor5) {
        val funcList   = funcListOf(this);
        val collected1 = collectedOf(funcList, processor1);
        val collected2 = collectedOf(funcList, processor2);
        val collected3 = collectedOf(funcList, processor3);
        val collected4 = collectedOf(funcList, processor4);
        val collected5 = collectedOf(funcList, processor5);
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
            IntStreamProcessor<T1> processor1,
            IntStreamProcessor<T2> processor2,
            IntStreamProcessor<T3> processor3,
            IntStreamProcessor<T4> processor4,
            IntStreamProcessor<T5> processor5,
            IntStreamProcessor<T6> processor6) {
        val funcList   = funcListOf(this);
        val collected1 = collectedOf(funcList, processor1);
        val collected2 = collectedOf(funcList, processor2);
        val collected3 = collectedOf(funcList, processor3);
        val collected4 = collectedOf(funcList, processor4);
        val collected5 = collectedOf(funcList, processor5);
        val collected6 = collectedOf(funcList, processor6);
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
