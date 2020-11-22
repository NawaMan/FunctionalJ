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
package functionalj.stream;

import java.util.function.Consumer;
import java.util.stream.Collector;

import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;


public interface StreamPlusWithCalculate<DATA> {

    public void forEach(Consumer<? super DATA> action);
    
    // TODO - Optimize this so the concurrent one can has benefit from the Java implementation
    //        Still not sure how to do that.
    
    /** Perform the calculation using the data of this stream */
    public default <RESULT, ACCUMULATED> RESULT calculate(
            Collector<? super DATA, ACCUMULATED, RESULT> collector) {
        var collected = new Collected.ByCollector<>(collector);
        forEach(each -> {
            collected.accumulate(each);
        });
        var value = collected.finish();
        return value;
    }
    
    /** Perform the calculation using the data of this stream */
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2>
                        Tuple2<RESULT1, RESULT2> 
                        calculate(
                            Collector<? super DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<? super DATA, ACCUMULATED2, RESULT2> collector2) {
        var collected1 = new Collected.ByCollector<>(collector1);
        var collected2 = new Collected.ByCollector<>(collector2);
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
                            Collector<? super DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<? super DATA, ACCUMULATED2, RESULT2> collector2,
                            Collector<? super DATA, ACCUMULATED3, RESULT3> collector3) {
        var collected1 = new Collected.ByCollector<>(collector1);
        var collected2 = new Collected.ByCollector<>(collector2);
        var collected3 = new Collected.ByCollector<>(collector3);
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
                            Collector<? super DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<? super DATA, ACCUMULATED2, RESULT2> collector2,
                            Collector<? super DATA, ACCUMULATED3, RESULT3> collector3,
                            Collector<? super DATA, ACCUMULATED4, RESULT4> collector4) {
        var collected1 = new Collected.ByCollector<>(collector1);
        var collected2 = new Collected.ByCollector<>(collector2);
        var collected3 = new Collected.ByCollector<>(collector3);
        var collected4 = new Collected.ByCollector<>(collector4);
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
                            Collector<DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<DATA, ACCUMULATED2, RESULT2> collector2,
                            Collector<DATA, ACCUMULATED3, RESULT3> collector3,
                            Collector<DATA, ACCUMULATED4, RESULT4> collector4,
                            Collector<DATA, ACCUMULATED5, RESULT5> collector5) {
        var collected1 = new Collected.ByCollector<>(collector1);
        var collected2 = new Collected.ByCollector<>(collector2);
        var collected3 = new Collected.ByCollector<>(collector3);
        var collected4 = new Collected.ByCollector<>(collector4);
        var collected5 = new Collected.ByCollector<>(collector5);
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
                            Collector<DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<DATA, ACCUMULATED2, RESULT2> collector2,
                            Collector<DATA, ACCUMULATED3, RESULT3> collector3,
                            Collector<DATA, ACCUMULATED4, RESULT4> collector4,
                            Collector<DATA, ACCUMULATED5, RESULT5> collector5,
                            Collector<DATA, ACCUMULATED6, RESULT6> collector6) {
        var collected1 = new Collected.ByCollector<>(collector1);
        var collected2 = new Collected.ByCollector<>(collector2);
        var collected3 = new Collected.ByCollector<>(collector3);
        var collected4 = new Collected.ByCollector<>(collector4);
        var collected5 = new Collected.ByCollector<>(collector5);
        var collected6 = new Collected.ByCollector<>(collector6);
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
