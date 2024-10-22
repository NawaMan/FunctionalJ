package functionalj.types;

public class JavaVersionInfo {
    
    private final int sourceVersion;
    private final int targetVersion;
    
    public JavaVersionInfo(int sourceVersion, int targetVersion) {
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
    }
    
    public int getSourceVersion() {
        return sourceVersion;
    }
    
    public int getTargetVersion() {
        return targetVersion;
    }
    
    @Override
    public String toString() {
        return "Source Version: " + sourceVersion + ", Target Version: " + targetVersion;
    }
    
    public String toCode() {
        return "new functionalj.types.JavaVersionInfo(" + sourceVersion + ", " + targetVersion + ")";
    }
    
}