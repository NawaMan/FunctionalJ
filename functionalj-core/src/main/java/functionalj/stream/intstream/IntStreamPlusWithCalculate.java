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
package functionalj.stream.intstream;

import java.util.function.IntConsumer;

import functionalj.stream.collect.Collected;
import functionalj.stream.intstream.collect.IntCollectorPlus;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;


public interface IntStreamPlusWithCalculate {

     public void forEach(IntConsumer action);
     
     
     // TODO - Optimize this so the concurrent one can has benefit from the Java implementation
     //        Still not sure how to do that.
     
     /** Perform the calculation using the data of this stream */
    public default <RESULT, ACCUMULATED> RESULT calculate(
            IntCollectorPlus<ACCUMULATED, RESULT> collector) {
        val collected = new Collected.ByCollectedInt<>(collector);
        forEach(each -> {
            collected.accumulate(each);
        });
        val value = collected.finish();
        return value;
    }
    
    /** Perform the calculation using the data of this stream */
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2>
                        Tuple2<RESULT1, RESULT2> 
                        calculate(
                            IntCollectorPlus<ACCUMULATED1, RESULT1> collector1,
                            IntCollectorPlus<ACCUMULATED2, RESULT2> collector2) {
        val collected1 = new Collected.ByCollectedInt<>(collector1);
        val collected2 = new Collected.ByCollectedInt<>(collector2);
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
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2, 
                    ACCUMULATED3, RESULT3>
                        Tuple3<RESULT1, RESULT2, RESULT3> 
                        calculate(
                            IntCollectorPlus<ACCUMULATED1, RESULT1> collector1,
                            IntCollectorPlus<ACCUMULATED2, RESULT2> collector2,
                            IntCollectorPlus<ACCUMULATED3, RESULT3> collector3) {
        val collected1 = new Collected.ByCollectedInt<>(collector1);
        val collected2 = new Collected.ByCollectedInt<>(collector2);
        val collected3 = new Collected.ByCollectedInt<>(collector3);
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
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2, 
                    ACCUMULATED3, RESULT3, 
                    ACCUMULATED4, RESULT4>
                        Tuple4<RESULT1, RESULT2, RESULT3, RESULT4> 
                        calculate(
                            IntCollectorPlus<ACCUMULATED1, RESULT1> collector1,
                            IntCollectorPlus<ACCUMULATED2, RESULT2> collector2,
                            IntCollectorPlus<ACCUMULATED3, RESULT3> collector3,
                            IntCollectorPlus<ACCUMULATED4, RESULT4> collector4) {
        val collected1 = new Collected.ByCollectedInt<>(collector1);
        val collected2 = new Collected.ByCollectedInt<>(collector2);
        val collected3 = new Collected.ByCollectedInt<>(collector3);
        val collected4 = new Collected.ByCollectedInt<>(collector4);
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
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2, 
                    ACCUMULATED3, RESULT3, 
                    ACCUMULATED4, RESULT4, 
                    ACCUMULATED5, RESULT5>
                        Tuple5<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5> 
                        calculate(
                            IntCollectorPlus<ACCUMULATED1, RESULT1> collector1,
                            IntCollectorPlus<ACCUMULATED2, RESULT2> collector2,
                            IntCollectorPlus<ACCUMULATED3, RESULT3> collector3,
                            IntCollectorPlus<ACCUMULATED4, RESULT4> collector4,
                            IntCollectorPlus<ACCUMULATED5, RESULT5> collector5) {
        val collected1 = new Collected.ByCollectedInt<>(collector1);
        val collected2 = new Collected.ByCollectedInt<>(collector2);
        val collected3 = new Collected.ByCollectedInt<>(collector3);
        val collected4 = new Collected.ByCollectedInt<>(collector4);
        val collected5 = new Collected.ByCollectedInt<>(collector5);
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
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2, 
                    ACCUMULATED3, RESULT3, 
                    ACCUMULATED4, RESULT4, 
                    ACCUMULATED5, RESULT5, 
                    ACCUMULATED6, RESULT6>
                        Tuple6<RESULT1, RESULT2, RESULT3, RESULT4, RESULT5, RESULT6> 
                        calculate(
                            IntCollectorPlus<ACCUMULATED1, RESULT1> collector1,
                            IntCollectorPlus<ACCUMULATED2, RESULT2> collector2,
                            IntCollectorPlus<ACCUMULATED3, RESULT3> collector3,
                            IntCollectorPlus<ACCUMULATED4, RESULT4> collector4,
                            IntCollectorPlus<ACCUMULATED5, RESULT5> collector5,
                            IntCollectorPlus<ACCUMULATED6, RESULT6> collector6) {
        val collected1 = new Collected.ByCollectedInt<>(collector1);
        val collected2 = new Collected.ByCollectedInt<>(collector2);
        val collected3 = new Collected.ByCollectedInt<>(collector3);
        val collected4 = new Collected.ByCollectedInt<>(collector4);
        val collected5 = new Collected.ByCollectedInt<>(collector5);
        val collected6 = new Collected.ByCollectedInt<>(collector6);
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
