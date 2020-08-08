package functionalj.stream;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import functionalj.function.Func1;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.streamable.intstreamable.IntStreamable;
import lombok.val;

public class IntStep implements IntUnaryOperator, IntFunction<Integer>, Function<Integer, Integer>, IntStreamable {

    private final int size;
    private final int start;

    public static class Size {
        public final int size;
        Size(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("Step size cannot be zero or negative: " + size);
            }

            this.size = size;
        }
    }

    public static class From {
        public final int from;
        From(int from) {
            this.from = from;
        }
    }

    public static IntStep step(int size) {
        return new IntStep(size, 0);
    }

    public static IntStep step(Size size) {
        return new IntStep(size.size, 0);
    }

    public static IntStep step(Size size, From from) {
        return new IntStep(size.size, from.from);
    }

    public static IntStep step(int size, From from) {
        return new IntStep(size, from.from);
    }

    public static IntStep of(int size) {
        return new IntStep(size, 0);
    }

    public static IntStep of(Size size) {
        return new IntStep(size.size, 0);
    }

    public static IntStep of(Size size, From from) {
        return new IntStep(size.size, from.from);
    }

    public static IntStep of(int size, From from) {
        return new IntStep(size, from.from);
    }

    public static Size size(int size) {
        return new Size(size);
    }

    public static From startAt(int start) {
        return new From(start);
    }

    public static From from(int start) {
        return new From(start);
    }

    private IntStep(int size, int start) {
        if (size <= 0) {
            throw new IllegalArgumentException("Step size cannot be zero or negative: " + size);
        }

        this.size = size;
        this.start = start;
    }

    public IntStreamPlus intStream() {
        val num = new AtomicInteger(start);
        return IntStreamPlus.generate(()->num.getAndUpdate(i -> i + size));
    }

    @Override
    public int applyAsInt(int operand) {
        return start + (int)(Math.round(1.0 * (operand - start) / size) * size);
    }

    @Override
    public Integer apply(int operand) {
        return applyAsInt(operand);
    }

    @Override
    public Integer apply(Integer operand) {
        return applyAsInt(operand);
    }

    public Func1<Integer, Integer> function() {
        return i -> applyAsInt(i);
    }

}
