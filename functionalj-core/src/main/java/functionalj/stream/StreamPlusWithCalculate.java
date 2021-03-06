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
    
    
    //== Calculate ==
    
    // TODO - Optimize this so the concurrent one can has benefit from the Java implementation
    
    public default <RESULT, ACCUMULATED> RESULT calculate(
            Collector<DATA, ACCUMULATED, RESULT> collector) {
        val collected = new Collected.ByCollector<>(collector);
        forEach(each -> {
            collected.accumulate(each);
        });
        val value = collected.finish();
        return value;
    }
    
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2>
                        Tuple2<RESULT1, RESULT2> 
                        calculate(
                            Collector<DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<DATA, ACCUMULATED2, RESULT2> collector2) {
        val collected1 = new Collected.ByCollector<>(collector1);
        val collected2 = new Collected.ByCollector<>(collector2);
        forEach(each -> {
            collected1.accumulate(each);
            collected2.accumulate(each);
        });
        return Tuple.of(
                collected1.finish(),
                collected2.finish()
            );
    }
    
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2, 
                    ACCUMULATED3, RESULT3>
                        Tuple3<RESULT1, RESULT2, RESULT3> 
                        calculate(
                            Collector<DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<DATA, ACCUMULATED2, RESULT2> collector2,
                            Collector<DATA, ACCUMULATED3, RESULT3> collector3) {
        val collected1 = new Collected.ByCollector<>(collector1);
        val collected2 = new Collected.ByCollector<>(collector2);
        val collected3 = new Collected.ByCollector<>(collector3);
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
    
    public default <ACCUMULATED1, RESULT1, 
                    ACCUMULATED2, RESULT2, 
                    ACCUMULATED3, RESULT3, 
                    ACCUMULATED4, RESULT4>
                        Tuple4<RESULT1, RESULT2, RESULT3, RESULT4> 
                        calculate(
                            Collector<DATA, ACCUMULATED1, RESULT1> collector1,
                            Collector<DATA, ACCUMULATED2, RESULT2> collector2,
                            Collector<DATA, ACCUMULATED3, RESULT3> collector3,
                            Collector<DATA, ACCUMULATED4, RESULT4> collector4) {
        val collected1 = new Collected.ByCollector<>(collector1);
        val collected2 = new Collected.ByCollector<>(collector2);
        val collected3 = new Collected.ByCollector<>(collector3);
        val collected4 = new Collected.ByCollector<>(collector4);
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
        val collected1 = new Collected.ByCollector<>(collector1);
        val collected2 = new Collected.ByCollector<>(collector2);
        val collected3 = new Collected.ByCollector<>(collector3);
        val collected4 = new Collected.ByCollector<>(collector4);
        val collected5 = new Collected.ByCollector<>(collector5);
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
        val collected1 = new Collected.ByCollector<>(collector1);
        val collected2 = new Collected.ByCollector<>(collector2);
        val collected3 = new Collected.ByCollector<>(collector3);
        val collected4 = new Collected.ByCollector<>(collector4);
        val collected5 = new Collected.ByCollector<>(collector5);
        val collected6 = new Collected.ByCollector<>(collector6);
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
