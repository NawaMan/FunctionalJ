package nawaman.functionalj;

public class FunctionalStrings {
    
    public static String stringOf(Object obj) {
        return (obj == null) ? null : String.valueOf(obj);
    }
    
}
