package functionalj.stream;

public enum ZipWithOption {
    
    RequireBoth,
    AllowUnpaired;
    
    public boolean isRequireBoth() {
        return this == ZipWithOption.RequireBoth;
    }
    
}
