package functionalj.stream;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.Func1;
import lombok.val;

public class DoubleStep implements Streamable<Double>, DoubleUnaryOperator {
    
    private final double size;
    private final double start;
    
    public static class Size {
        public final double size;
        Size(double size) {
            if (size <= 0) {
                throw new IllegalArgumentException("Step size cannot be zero or negative: " + size);
            }
            
            this.size = size;
        }
    }
    
    public static class From {
        public final double from;
        From(double from) {
            this.from = from;
        }
    }
    
    public static DoubleStep step(double size) {
        return new DoubleStep(size, 0);
    }
    
    public static DoubleStep step(Size size) {
        return new DoubleStep(size.size, 0);
    }
    
    public static DoubleStep step(Size size, From from) {
        return new DoubleStep(size.size, from.from);
    }
    
    public static DoubleStep step(double size, From from) {
        return new DoubleStep(size, from.from);
    }
    
    public static DoubleStep of(double size) {
        return new DoubleStep(size, 0);
    }
    
    public static DoubleStep of(Size size) {
        return new DoubleStep(size.size, 0);
    }
    
    public static DoubleStep of(Size size, From from) {
        return new DoubleStep(size.size, from.from);
    }
    
    public static DoubleStep of(double size, From from) {
        return new DoubleStep(size, from.from);
    }
    
    public static Size size(double size) {
        return new Size(size);
    }
    
    public static From startAt(double start) {
        return new From(start);
    }
    
    public static From from(double start) {
        return new From(start);
    }
    
    private DoubleStep(double size, double start) {
        if (size <= 0) {
            throw new IllegalArgumentException("Step size cannot be zero or negative: " + size);
        }
        
        this.size = size;
        this.start = start;
    }
    
    public DoubleStreamPlus doubleStream() {
        val num = new AtomicReference<Double>(start);
        return DoubleStreamPlus.generate(()->num.getAndUpdate(i -> i + size));
    }
    
    @Override
    public StreamPlus<Double> stream() {
        return doubleStream().boxed();
    }
    
    @Override
    public double applyAsDouble(double operand) {
        return start + (Math.round(1.0 * (operand - start) / size) * size);
    }
    
    public Func1<Double, Double> function() {
        return i -> applyAsDouble(i);
    }
    
}
