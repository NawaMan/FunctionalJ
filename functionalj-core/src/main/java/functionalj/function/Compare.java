package functionalj.function;

public interface Compare {
    
    public static Integer compareOrNull(Integer a, Integer b) {
        if ((a == null) || (b == null))
            return null;
        
        return Double.compare(a, b);
    }
    
    public static Integer compareOrNull(Double a, Double b) {
        if ((a == null) || (b == null))
            return null;
        
        return Double.compare(a, b);
    }
    
    public static int comparePrimitive(int a, int b) {
        return Double.compare(a, b);
    }
    
    public static int comparePrimitive(double a, double b) {
        return Double.compare(a, b);
    }
    
}
