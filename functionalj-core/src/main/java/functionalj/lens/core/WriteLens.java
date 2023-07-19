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
package functionalj.lens.core;

import java.util.function.BiFunction;

@FunctionalInterface
public interface WriteLens<HOST, DATA> {
    
    public static <HOST, DATA> WriteLens<HOST, DATA> of(BiFunction<HOST, DATA, HOST> biFunction) {
        return (host, newValue) -> {
            return biFunction.apply(host, newValue);
        };
    }
    
    public HOST apply(HOST host, DATA newValue);
    
    public default HOST applyTo(HOST host, DATA newValue) {
        return apply(host, newValue);
    }
    
    public default BiFunction<HOST, DATA, HOST> toBiFunction() {
        return this::apply;
    }
    
    public static interface PrimitiveInt<HOST> extends WriteLens<HOST, Integer> {
    
        public HOST applyWithInt(HOST host, int newValue);
    
        public default HOST apply(HOST host, Integer newValue) {
            return applyWithInt(host, newValue);
        }
    }
    
    public static interface PrimitiveLong<HOST> extends WriteLens<HOST, Long> {
    
        public HOST applyWithLong(HOST host, long newValue);
    
        public default HOST apply(HOST host, Long newValue) {
            return applyWithLong(host, newValue);
        }
    }
    
    public static interface PrimitiveDouble<HOST> extends WriteLens<HOST, Double> {
    
        public HOST applyWithDouble(HOST host, double newValue);
    
        public default HOST apply(HOST host, Double newValue) {
            return applyWithDouble(host, newValue);
        }
    }
    
    public static interface PrimitiveBoolean<HOST> extends WriteLens<HOST, Boolean> {
    
        public HOST applyWithBoolean(HOST host, boolean newValue);
    
        public default HOST apply(HOST host, Boolean newValue) {
            return applyWithBoolean(host, newValue);
        }
    }
}
