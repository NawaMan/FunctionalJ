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

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import functionalj.function.Func1;
import functionalj.list.intlist.IntFuncList;


public class IntStep implements IntUnaryOperator, IntFunction<Integer>, Function<Integer, Integer>, IntFuncList {
    
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
        
        public IntStep step(int size) {
            return new IntStep(size, from);
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
        return intStreamPlus();
    }
    
    @Override
    public IntStreamPlus intStreamPlus() {
        return IntStreamPlus.wholeNumbers().map(i -> i *size + start);
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
    
    @Override
    public IntFuncList lazy() {
        return IntFuncList.from(() -> intStreamPlus());
    }
    
    /** Please don't call. This will blow up. */
    @Override
    public IntFuncList eager() {
        throw new UnsupportedOperationException(
                "Infinite double step cannot be made an eager list: " 
                    + intStreamPlus().limit(5).join(", ") + "...");
    }
    
}
