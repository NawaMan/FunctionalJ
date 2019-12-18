package functionalj.lens;

import functionalj.lens.lenses.DoubleToDoubleAccessPrimitive;

public class TheDouble implements DoubleToDoubleAccessPrimitive {
    
    public static final TheDouble theDouble = new TheDouble();
    
    public double sum(double i1, double i2) {
        return i1 + i2;
    }
    
    public double product(double i1, double i2) {
        return i1 * i2;
    }
    
    public double subtract(double i1, double i2) {
        return i1 - i2;
    }
    
    public double diff(double i1, double i2) {
        return i2 - i1;
    }
    
    public double ratio(double i1, double i2) {
        return i1 / i2;
    }
    
    @Override
    public double applyDoubleToDouble(double host) {
        return host;
    }
    
}
