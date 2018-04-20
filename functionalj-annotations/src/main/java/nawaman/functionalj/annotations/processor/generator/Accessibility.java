package nawaman.functionalj.annotations.processor.generator;

public enum Accessibility {
    PUBLIC, PRIVATE, PROTECTED, PACKAGE;
    
    public String toString() {
        return (this == PACKAGE)
                ? null
                : name().toLowerCase();
    }
}