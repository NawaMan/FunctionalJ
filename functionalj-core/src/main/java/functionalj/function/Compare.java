package functionalj.function;

public interface Compare {
    
    public static Integer compareOrNull(Integer a, Integer b) {
        if ((a == null) || (b == null))
            return null;
        return Integer.compare(a, b);
    }
    
    public static Integer compareOrNull(Long a, Long b) {
        if ((a == null) || (b == null))
            return null;
        return Long.compare(a, b);
    }
    
    public static Integer compareOrNull(Double a, Double b) {
        if ((a == null) || (b == null))
            return null;
        return Double.compare(a, b);
    }
    
    public static int comparePrimitive(int a, int b) {
        return Integer.compare(a, b);
    }
    
    public static int comparePrimitive(long a, long b) {
        return Long.compare(a, b);
    }
    
    public static int comparePrimitive(double a, double b) {
        return Double.compare(a, b);
    }
}
