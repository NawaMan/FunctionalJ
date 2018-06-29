package functionalj;

public class StringFuncs {
    
    public static String stringOf(Object obj) {
        return (obj == null) ? null : String.valueOf(obj);
    }
    
}
