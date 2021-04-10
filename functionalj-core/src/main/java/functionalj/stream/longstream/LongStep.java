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
package functionalj.stream.longstream;

import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

import functionalj.function.Func1;
import functionalj.list.longlist.LongFuncList;
import lombok.val;


public class LongStep implements LongUnaryOperator, LongFunction<Long>, Function<Long, Long>, LongFuncList {
    
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
        
        public LongStep step(int size) {
            return new LongStep(size, from);
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
    
    public static LongStep ofSize(long size) {
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
    
    public static From StartFrom(long start) {
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
    
    public LongStep startFrom(long start) {
        return new LongStep(size, start);
    }
    
    public LongStreamPlus to(long end) {
        val sizePositive     = size > 0;
        val distancePositive = (end - start) > 0;
        val sameDirection    = sizePositive == distancePositive;
        val longStreamPlus
                = sameDirection 
                ? LongStreamPlus.wholeNumbers().map(i ->  i * size + start)
                : LongStreamPlus.wholeNumbers().map(i -> -i * size + start);
        if (distancePositive) {
            return longStreamPlus.takeUntil(i -> i > end);
        } else {
            return longStreamPlus.takeUntil(i -> i < end);
        }
    }
    
    public LongStreamPlus longStream() {
        return longStreamPlus();
    }
    
    @Override
    public LongStreamPlus longStreamPlus() {
        return LongStreamPlus.wholeNumbers().map(i -> i *size + start);
    }
    
    @Override
    public long applyAsLong(long operand) {
        return start + (long)(Math.round(1.0 * (operand - start) / size) * size);
    }
    
    @Override
    public Long apply(long operand) {
        return applyAsLong(operand);
    }
    
    @Override
    public Long apply(Long operand) {
        return applyAsLong(operand);
    }
    
    public Func1<Long, Long> function() {
        return i -> applyAsLong(i);
    }
    
    @Override
    public LongFuncList toLazy() {
        return LongFuncList.from(() -> longStreamPlus());
    }
    
    /** Please don't call. This will blow up. */
    @Override
    public LongFuncList toEager() {
        throw new UnsupportedOperationException(
                "Infinite double step cannot be made an eager list: " 
                    + longStreamPlus().limit(5).join(", ") + "...");
    }
    
    /** Please don't call. This will blow up. */
    @Override
    public LongFuncList toCache() {
        throw new UnsupportedOperationException(
                "Infinite double step cannot be made a cache list: " 
                    + longStreamPlus().limit(5).join(", ") + "...");
    }
    
}
