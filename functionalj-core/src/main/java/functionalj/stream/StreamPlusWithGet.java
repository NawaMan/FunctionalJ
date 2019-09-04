package functionalj.stream;

import java.util.concurrent.atomic.AtomicLong;

import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface StreamPlusWithGet<DATA> {

    public IteratorPlus<DATA> iterator();
    
    //== Get ==
    
    public default <T> T get(StreamElementProcessor<DATA, T> processor) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)(()->iterator())) {
            val index = counter.getAndIncrement();
            processor.processElement(index, each);
        }
        val count = counter.get();
        return processor.processComplete(count);
    }
    
    public default <T1, T2> Tuple2<T1, T2> get(
                StreamElementProcessor<DATA, T1> processor1, 
                StreamElementProcessor<DATA, T2> processor2) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)(()->iterator())) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        return Tuple.of(value1, value2);
    }
    
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
                StreamElementProcessor<DATA, T1> processor1, 
                StreamElementProcessor<DATA, T2> processor2, 
                StreamElementProcessor<DATA, T3> processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)(()->iterator())) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        return Tuple.of(value1, value2, value3);
    }
    
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
                StreamElementProcessor<DATA, T1> processor1, 
                StreamElementProcessor<DATA, T2> processor2, 
                StreamElementProcessor<DATA, T3> processor3, 
                StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)(()->iterator())) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
                StreamElementProcessor<DATA, T1> processor1, 
                StreamElementProcessor<DATA, T2> processor2, 
                StreamElementProcessor<DATA, T3> processor3, 
                StreamElementProcessor<DATA, T4> processor4, 
                StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)(()->iterator())) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
                StreamElementProcessor<DATA, T1> processor1, 
                StreamElementProcessor<DATA, T2> processor2, 
                StreamElementProcessor<DATA, T3> processor3, 
                StreamElementProcessor<DATA, T4> processor4, 
                StreamElementProcessor<DATA, T5> processor5, 
                StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)(()->iterator())) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }

}
