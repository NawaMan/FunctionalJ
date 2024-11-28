package functionalj.ref;

class Utils {
    
    static String name(Class<?> clzz) {
        // Check if the class is a common class (String, Integer, etc.)
        if (clzz.isPrimitive() || clzz == String.class || clzz == Integer.class || clzz == Double.class
                || clzz == Boolean.class || clzz == Character.class || clzz == Byte.class
                || clzz == Short.class || clzz == Long.class || clzz == Float.class) {
            return clzz.getSimpleName();
        } else {
            return clzz.getCanonicalName();
        }
    }
    
}
