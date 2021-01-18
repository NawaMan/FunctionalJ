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
package functionalj.stream.doublestream;

import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import functionalj.function.Func1;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.stream.intstream.IntStreamPlus;


public class DoubleStep implements DoubleUnaryOperator, DoubleFunction<Double>, Function<Double, Double>, DoubleFuncList {
    
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
        
        public DoubleStep step(int size) {
            return new DoubleStep(size, from);
        }
        
        public DoubleStep step(long size) {
            return new DoubleStep(size, from);
        }
        
        public DoubleStep step(double size) {
            return new DoubleStep(size, from);
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
    
    public static DoubleStep step(int size, From from) {
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
        return doubleStreamPlus();
    }
    
    @Override
    public DoubleStreamPlus doubleStreamPlus() {
        return IntStreamPlus.wholeNumbers().mapToDouble(i -> i *size + start);
    }
    
    @Override
    public double applyAsDouble(double operand) {
        return start + (int)(Math.round(1.0 * (operand - start) / size) * size);
    }
    
    @Override
    public Double apply(double operand) {
        return applyAsDouble(operand);
    }
    
    @Override
    public Double apply(Double operand) {
        return applyAsDouble(operand);
    }
    
    public Func1<Double, Double> function() {
        return i -> applyAsDouble(i);
    }
    
    @Override
    public DoubleFuncList lazy() {
        return DoubleFuncList.from(() -> doubleStreamPlus());
    }
    
    /** Please don't call. This will blow up. */
    @Override
    public DoubleFuncList eager() {
        throw new UnsupportedOperationException(
                "Infinite double step cannot be made an eager list: " 
                    + doubleStreamPlus().limit(5).join(", ") + "...");
    }
    
}
