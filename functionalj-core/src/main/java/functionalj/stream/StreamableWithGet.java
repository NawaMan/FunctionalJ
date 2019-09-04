package functionalj.stream;

import java.util.concurrent.atomic.AtomicLong;

import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface StreamableWithGet<DATA> {

    public StreamPlus<DATA>   stream();
    public IteratorPlus<DATA> iterator();
    
    //-- 1 --
    
    @SuppressWarnings("unchecked")
    public default <T> T get(StreamElementProcessor<DATA, T> processor) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor.processElement(index, each);
        }
        val count = counter.get();
        return processor.processComplete(count);
    }
    
    public default <T> T get(StreamProcessor<DATA, T> processor) {
        val stream = this.stream();
        val target = processor.process(stream);
        return target;
    }
    
    //-- 2 --
    
    @SuppressWarnings("unchecked")
    public default <T1, T2> Tuple2<T1, T2> get(
                StreamElementProcessor<DATA, T1> processor1, 
                StreamElementProcessor<DATA, T2> processor2) {
        val counter = new AtomicLong(0);
        for (val each : ((Iterable< DATA>)this)) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        return Tuple.of(value1, value2);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2> Tuple2<T1, T2> get(
                StreamElementProcessor<DATA, T1> processor1, 
                StreamProcessor<DATA, T2>        processor2) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        return Tuple.of(value1, value2);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2> Tuple2<T1, T2> get(
            StreamProcessor<DATA, T1>        processor1,
            StreamElementProcessor<DATA, T2> processor2) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        return Tuple.of(value1, value2);
    }
    
    public default <T1, T2> Tuple2<T1, T2> get(
            StreamProcessor<DATA, T1> processor1,
            StreamProcessor<DATA, T2> processor2) {
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        return Tuple.of(value1, value2);
    }
    
    //-- 3 --
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        return Tuple.of(value1, value2, value3);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        return Tuple.of(value1, value2, value3);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        return Tuple.of(value1, value2, value3);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        return Tuple.of(value1, value2, value3);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        return Tuple.of(value1, value2, value3);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        return Tuple.of(value1, value2, value3);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        return Tuple.of(value1, value2, value3);
    }
    
    public default <T1, T2, T3> Tuple3<T1, T2, T3> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3> processor3) {
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        return Tuple.of(value1, value2, value3);
    }
    
    //-- 4 --
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
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
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4> processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    public default <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4> processor4) {
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        return Tuple.of(value1, value2, value3, value4);
    }
    
    //-- 5 --
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
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
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
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
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5> processor5) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    public default <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5> processor5) {
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5);
    }
    
    //-- 6 --
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
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
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor4.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor5.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5> processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor4.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamElementProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor6.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.processComplete(count);
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
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
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor4.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamElementProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6> processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor5.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.processComplete(count);
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
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
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());;
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>       processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamElementProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor4.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.processComplete(count);
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1>        processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamElementProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor3.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.processComplete(count);
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamElementProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor2.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.process(stream());
        val value2 = processor2.processComplete(count);
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    @SuppressWarnings("unchecked")
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamElementProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2>        processor2, 
            StreamProcessor<DATA, T3>        processor3, 
            StreamProcessor<DATA, T4>        processor4, 
            StreamProcessor<DATA, T5>        processor5, 
            StreamProcessor<DATA, T6>        processor6) {
        val counter = new AtomicLong(0);
        for (val each : (Iterable<DATA>)this) {
            val index = counter.getAndIncrement();
            processor1.processElement(index, each);
        }
        val count = counter.get();
        val value1 = processor1.processComplete(count);
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
    public default <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> get(
            StreamProcessor<DATA, T1> processor1, 
            StreamProcessor<DATA, T2> processor2, 
            StreamProcessor<DATA, T3> processor3, 
            StreamProcessor<DATA, T4> processor4, 
            StreamProcessor<DATA, T5> processor5, 
            StreamProcessor<DATA, T6> processor6) {
        val value1 = processor1.process(stream());
        val value2 = processor2.process(stream());
        val value3 = processor3.process(stream());
        val value4 = processor4.process(stream());
        val value5 = processor5.process(stream());
        val value6 = processor6.process(stream());
        return Tuple.of(value1, value2, value3, value4, value5, value6);
    }
    
}