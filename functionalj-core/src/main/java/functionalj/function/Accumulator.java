package functionalj.function;

import functionalj.lens.lenses.DoubleToIntegerAccessBoxed;
import functionalj.lens.lenses.IntegerAccessBoxed;
import functionalj.lens.lenses.IntegerToIntegerAccessBoxed;
import functionalj.lens.lenses.LongToIntegerAccessBoxed;
import functionalj.stream.Collected;
import functionalj.stream.CollectorPlus;
import functionalj.stream.doublestream.DoubleCollectorPlus;
import functionalj.stream.intstream.IntCollectorPlus;
import functionalj.stream.longstream.LongCollectorPlus;
import lombok.val;

public class Accumulator {
    
    public static <DATA, ACCUMULATED> IntegerAccessBoxed<DATA> ofInt(CollectorPlus<DATA, ACCUMULATED, Integer> collector) {
        val collected = new Collected.ByCollector<>(collector);
        return data -> {
            collected.accumulate(data);
            return collected.finish();
        };
    }
    
    public static <ACCUMULATED> IntegerToIntegerAccessBoxed ofInt(IntCollectorPlus<ACCUMULATED, Integer> collector) {
        val collected = new Collected.ByCollector<>(collector);
        return data -> {
            collected.accumulate(data);
            return collected.finish();
        };
    }
    
    public static <ACCUMULATED> LongToIntegerAccessBoxed ofInt(LongCollectorPlus<ACCUMULATED, Integer> collector) {
        val collected = new Collected.ByCollector<>(collector);
        return data -> {
            collected.accumulate(data);
            return collected.finish();
        };
    }
    
    public static <ACCUMULATED> DoubleToIntegerAccessBoxed ofInt(DoubleCollectorPlus<ACCUMULATED, Integer> collector) {
        val collected = new Collected.ByCollector<>(collector);
        return data -> {
            collected.accumulate(data);
            return collected.finish();
        };
    }
    
    // TODO - Add Long and Double
    
}
