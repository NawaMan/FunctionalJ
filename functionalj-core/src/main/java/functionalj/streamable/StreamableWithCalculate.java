// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.streamable;

import functionalj.stream.AsStreamable;
import functionalj.stream.Collected;
import functionalj.stream.StreamProcessor;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface StreamableWithCalculate<DATA> 
        extends AsStreamable<DATA> {
    
    /** Perform the calculation using the data of this streamable */
    public default <A, T> T calculate(
            StreamProcessor<? extends DATA, T> processor) {
        val streamble = Streamable.from(()->stream());
        val collected = Collected.of(streamble, processor);
        streamble
        .forEach(each -> {
            collected.accumulate(each);
        });
        val value = collected.finish();
        return value;
    }
    
    /** Perform the calculation using the data of this streamable */
    public default <T1, T2> Tuple2<T1, T2> calculate(
            StreamProcessor<DATA, T1> processor1,
            StreamProcessor<DATA, T2> processor2) {
        val streamble  = Streamable.from(()->stream());
        val collected1 = Collected.of(streamble, processor1);
        val collected2 = Collected.of(streamble, processor2);
        streamble
        .forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
        });
        val value1 = collected1.finish();
        val value2 = collected2.finish();
        return Tuple.of(value1, value2);
    }
    
    /** Perform the calculation using the data of this streamable */
    public default <T1, T2, T3> Tuple3<T1, T2, T3> calculate(
            StreamProcessor<DATA, T1> processor1,
            StreamProcessor<DATA, T2> processor2,
            StreamProcessor<DATA, T3> processor3) {
        val streamble  = Streamable.from(()->stream());
        val collected1 = Collected.of(streamble, processor1);
        val collected2 = Collected.of(streamble, processor2);
        val collected3 = Collected.of(streamble, processor3);
        streamble
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
    
    /** Perform the calculation using the data of this streamable */
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> calculate(
            StreamProcessor<DATA, T1> processor1,
            StreamProcessor<DATA, T2> processor2,
            StreamProcessor<DATA, T3> processor3,
            StreamProcessor<DATA, T4> processor4) {
        val streamble  = Streamable.from(()->stream());
        val collected1 = Collected.of(streamble, processor1);
        val collected2 = Collected.of(streamble, processor2);
        val collected3 = Collected.of(streamble, processor3);
        val collected4 = Collected.of(streamble, processor4);
        streamble
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
    
    /** Perform the calculation using the data of this streamable */
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> calculate(
            StreamProcessor<DATA, T1> processor1,
            StreamProcessor<DATA, T2> processor2,
            StreamProcessor<DATA, T3> processor3,
            StreamProcessor<DATA, T4> processor4,
            StreamProcessor<DATA, T5> processor5) {
        val streamble  = Streamable.from(()->stream());
        val collected1 = Collected.of(streamble, processor1);
        val collected2 = Collected.of(streamble, processor2);
        val collected3 = Collected.of(streamble, processor3);
        val collected4 = Collected.of(streamble, processor4);
        val collected5 = Collected.of(streamble, processor5);
        streamble
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
    
    /** Perform the calculation using the data of this streamable */
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> calculate(
            StreamProcessor<DATA, T1> processor1,
            StreamProcessor<DATA, T2> processor2,
            StreamProcessor<DATA, T3> processor3,
            StreamProcessor<DATA, T4> processor4,
            StreamProcessor<DATA, T5> processor5,
            StreamProcessor<DATA, T6> processor6) {
        val streamble  = Streamable.from(()->stream());
        val collected1 = Collected.of(streamble, processor1);
        val collected2 = Collected.of(streamble, processor2);
        val collected3 = Collected.of(streamble, processor3);
        val collected4 = Collected.of(streamble, processor4);
        val collected5 = Collected.of(streamble, processor5);
        val collected6 = Collected.of(streamble, processor6);
        streamble
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
