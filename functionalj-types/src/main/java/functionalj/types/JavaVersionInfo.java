package functionalj.types;

public class JavaVersionInfo {
    
    private final int sourceVersion;
    private final int targetVersion;
    
    public JavaVersionInfo(int sourceVersion, int targetVersion) {
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
    }
    
    public int sourceVersion() {
        return sourceVersion;
    }
    
    public int targetVersion() {
        return targetVersion;
    }
    
    public int minVersion() {
        return Math.min(targetVersion, sourceVersion);
    }
    
    @Override
    public String toString() {
        return "Source Version: " + sourceVersion + ", Target Version: " + targetVersion;
    }
    
    public String toCode() {
        return "new functionalj.types.JavaVersionInfo(" + sourceVersion + ", " + targetVersion + ")";
    }
    
}