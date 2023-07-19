package functionalj.lens;

import functionalj.lens.lenses.LongToLongAccessPrimitive;

public class TheLong implements LongToLongAccessPrimitive {
    
    public static final TheLong theLong = new TheLong();
    
    public long sum(long i1, long i2) {
        return i1 + i2;
    }
    
    public long product(long i1, long i2) {
        return i1 * i2;
    }
    
    public long subtract(long i1, long i2) {
        return i1 - i2;
    }
    
    public long diff(long i1, long i2) {
        return i2 - i1;
    }
    
    public long ratio(long i1, long i2) {
        return i1 / i2;
    }
    
    @Override
    public long applyLongToLong(long host) {
        return host;
    }
}
