// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.lens.lenses;

import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.WriteLens;
import lombok.val;

public class PrimitiveLensSpecs {
    
    public static class IntegerLensSpecPrimitive<HOST> extends LensSpec<HOST, Integer> {
        
        private final ToIntFunction<HOST> readInt;
        
        public IntegerLensSpecPrimitive(ToIntFunction<HOST> readInt, WriteLens.PrimitiveInt<HOST> writeInt) {
            super(host -> readInt.applyAsInt(host), writeInt);
            this.readInt = readInt;
        }
        
        public ToIntFunction<HOST> getReadInt() {
            return readInt;
        }
        
        public WriteLens.PrimitiveInt<HOST> getWriteInt() {
            return (WriteLens.PrimitiveInt<HOST>) getWrite();
        }
        
        public int applyAsInt(HOST value) {
            return readInt.applyAsInt(value);
        }
        
        public HOST applyWithInt(HOST host, int newValue) {
            val writePrimitive = (WriteLens.PrimitiveInt<HOST>) getWrite();
            return writePrimitive.applyWithInt(host, newValue);
        }
    }
    
    public static class LongLensSpecPrimitive<HOST> extends LensSpec<HOST, Long> {
        
        private final ToLongFunction<HOST> readLong;
        
        public LongLensSpecPrimitive(ToLongFunction<HOST> readLong, WriteLens.PrimitiveLong<HOST> writeLong) {
            super(host -> readLong.applyAsLong(host), writeLong);
            this.readLong = readLong;
        }
        
        public ToLongFunction<HOST> getReadLong() {
            return readLong;
        }
        
        public WriteLens.PrimitiveLong<HOST> getWriteLong() {
            return (WriteLens.PrimitiveLong<HOST>) getWrite();
        }
        
        public long applyAsLong(HOST value) {
            return readLong.applyAsLong(value);
        }
        
        public HOST applyWithLong(HOST host, long newValue) {
            val writePrimitive = (WriteLens.PrimitiveLong<HOST>) getWrite();
            return writePrimitive.applyWithLong(host, newValue);
        }
    }
    
    public static class DoubleLensSpecPrimitive<HOST> extends LensSpec<HOST, Double> {
        
        private final ToDoubleFunction<HOST> readDouble;
        
        public DoubleLensSpecPrimitive(ToDoubleFunction<HOST> readDouble, WriteLens.PrimitiveDouble<HOST> writeDouble) {
            super(host -> readDouble.applyAsDouble(host), writeDouble);
            this.readDouble = readDouble;
        }
        
        public ToDoubleFunction<HOST> getReadDouble() {
            return readDouble;
        }
        
        public WriteLens.PrimitiveDouble<HOST> getWriteDouble() {
            return (WriteLens.PrimitiveDouble<HOST>) getWrite();
        }
        
        public double applyAsDouble(HOST value) {
            return readDouble.applyAsDouble(value);
        }
        
        public HOST applyWithDouble(HOST host, double newValue) {
            val writePrimitive = (WriteLens.PrimitiveDouble<HOST>) getWrite();
            return writePrimitive.applyWithDouble(host, newValue);
        }
    }
    
    public static class BooleanLensSpecPrimitive<HOST> extends LensSpec<HOST, Boolean> {
        
        private final Predicate<HOST> readBoolean;
        
        public BooleanLensSpecPrimitive(Predicate<HOST> readBoolean, WriteLens.PrimitiveBoolean<HOST> writeBoolean) {
            super(host -> readBoolean.test(host), writeBoolean);
            this.readBoolean = readBoolean;
        }
        
        public Predicate<HOST> getReadBoolean() {
            return readBoolean;
        }
        
        public WriteLens.PrimitiveBoolean<HOST> getWriteBoolean() {
            return (WriteLens.PrimitiveBoolean<HOST>) getWrite();
        }
        
        public boolean test(HOST value) {
            return readBoolean.test(value);
        }
        
        public HOST applyWithBoolean(HOST host, boolean newValue) {
            val writePrimitive = (WriteLens.PrimitiveBoolean<HOST>) getWrite();
            return writePrimitive.applyWithBoolean(host, newValue);
        }
    }
}
