package nawaman.functionalj.annotations.processor.generator;

public enum Scope {
    STATIC, INSTANCE;
    
    public String toString() {
        return (this == INSTANCE)
                ? null
                : name().toLowerCase();
    }
    
}