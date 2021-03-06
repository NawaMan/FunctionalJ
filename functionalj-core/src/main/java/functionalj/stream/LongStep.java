package functionalj.stream;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongUnaryOperator;

import functionalj.function.Func1;
import lombok.val;

public class LongStep implements Streamable<Long>, LongUnaryOperator {
    
    private final long size;
    private final long start;
    
    public static class Size {
        public final long size;
        Size(long size) {
            if (size <= 0) {
                throw new IllegalArgumentException("Step size cannot be zero or negative: " + size);
            }
            
            this.size = size;
        }
    }
    
    public static class From {
        public final long from;
        From(long from) {
            this.from = from;
        }
    }
    
    public static LongStep step(long size) {
        return new LongStep(size, 0);
    }
    
    public static LongStep step(Size size) {
        return new LongStep(size.size, 0);
    }
    
    public static LongStep step(Size size, From from) {
        return new LongStep(size.size, from.from);
    }
    
    public static LongStep step(long size, From from) {
        return new LongStep(size, from.from);
    }
    
    public static LongStep of(long size) {
        return new LongStep(size, 0);
    }
    
    public static LongStep of(Size size) {
        return new LongStep(size.size, 0);
    }
    
    public static LongStep of(Size size, From from) {
        return new LongStep(size.size, from.from);
    }
    
    public static LongStep of(long size, From from) {
        return new LongStep(size, from.from);
    }
    
    public static Size size(long size) {
        return new Size(size);
    }
    
    public static From startAt(long start) {
        return new From(start);
    }
    
    public static From from(long start) {
        return new From(start);
    }
    
    private LongStep(long size, long start) {
        if (size <= 0) {
            throw new IllegalArgumentException("Step size cannot be zero or negative: " + size);
        }
        
        this.size = size;
        this.start = start;
    }
    
    public LongStreamPlus longStream() {
        val num = new AtomicLong(start);
        return LongStreamPlus.generate(()->num.getAndUpdate(i -> i + size));
    }
    
    @Override
    public StreamPlus<Long> stream() {
        return longStream().boxed();
    }
    
    @Override
    public long applyAsLong(long operand) {
        return start + (Math.round(1.0 * (operand - start) / size) * size);
    }
    
    public Func1<Long, Long> function() {
        return i -> applyAsLong(i);
    }
    
}
