package nawaman.functionalj.annotations.processor.generator;

public enum Scope {
    STATIC, INSTANCE, NONE;
    
    public String toString() {
        return ((this == INSTANCE) || (this == NONE))
                ? null
                : name().toLowerCase();
    }
    
}