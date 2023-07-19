package functionalj.lens;

import functionalj.lens.lenses.IntegerToIntegerAccessPrimitive;

public class TheInteger implements IntegerToIntegerAccessPrimitive {
    
    public static final TheInteger theInteger = new TheInteger();
    
    public int sum(int i1, int i2) {
        return i1 + i2;
    }
    
    public int product(int i1, int i2) {
        return i1 * i2;
    }
    
    public int subtract(int i1, int i2) {
        return i1 - i2;
    }
    
    public int diff(int i1, int i2) {
        return i2 - i1;
    }
    
    public int ratio(int i1, int i2) {
        return i1 / i2;
    }
    
    @Override
    public int applyIntToInt(int host) {
        return host;
    }
}
