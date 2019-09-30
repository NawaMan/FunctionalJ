package functionalj.lens.lenses;

import java.util.function.ToIntFunction;

import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import lombok.val;

public class PrimitiveLensSpecs {
    
    public static class IntegerLensSpecPrimitive<HOST> extends LensSpec<HOST, Integer> {
        private final ToIntFunction<HOST> readInt;

        public IntegerLensSpecPrimitive(ToIntFunction<HOST> readInt, WriteLens.PrimitveInt<HOST> writeInt) {
            super(host -> readInt.applyAsInt(host), writeInt);
            this.readInt = readInt;
        }
        
        public ToIntFunction<HOST> getReadInt() {
            return readInt;
        }
        
        public WriteLens.PrimitveInt<HOST> getWriteInt() {
            return (WriteLens.PrimitveInt<HOST>)getWrite();
        }
        
        public int applyAsInt(HOST value) {
            return readInt.applyAsInt(value);
        }
        public HOST applyWithInt(HOST host, int newValue) {
            val writePrimitive = (WriteLens.PrimitveInt<HOST>)getWrite();
            return writePrimitive.applyWithInt(host, newValue);
        }
    }
    
}
