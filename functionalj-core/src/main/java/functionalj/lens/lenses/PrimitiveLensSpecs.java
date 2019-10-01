package functionalj.lens.lenses;

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
            return (WriteLens.PrimitiveInt<HOST>)getWrite();
        }
        
        public int applyAsInt(HOST value) {
            return readInt.applyAsInt(value);
        }
        public HOST applyWithInt(HOST host, int newValue) {
            val writePrimitive = (WriteLens.PrimitiveInt<HOST>)getWrite();
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
            return (WriteLens.PrimitiveLong<HOST>)getWrite();
        }
        
        public long applyAsLong(HOST value) {
            return readLong.applyAsLong(value);
        }
        public HOST applyWithLong(HOST host, long newValue) {
            val writePrimitive = (WriteLens.PrimitiveLong<HOST>)getWrite();
            return writePrimitive.applyWithLong(host, newValue);
        }
    }
    
}
