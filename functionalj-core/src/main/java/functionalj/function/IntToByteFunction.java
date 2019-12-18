package functionalj.function;

public interface IntToByteFunction extends ToByteFunction<Integer> {
    
    public default byte apply(Integer i) {
        int intValue = i.intValue();
        byte byteValue = applyAsByte(intValue);
        return byteValue;
    }
    
    public byte applyAsByte(int i);
    
}
