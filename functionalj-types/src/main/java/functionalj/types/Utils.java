package functionalj.types;

public class Utils {
    
    public static String blankToNull(String sourceSpec) {
        return ((sourceSpec == null) || sourceSpec.trim().isEmpty()) ? null : sourceSpec;
    }
    
    public static String emptyToNull(String sourceSpec) {
        return ((sourceSpec == null) || sourceSpec.isEmpty()) ? null : sourceSpec;
    }
}
