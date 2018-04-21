package nawaman.functionalj.annotations.processor.generator.model;

public enum Accessibility {
    PUBLIC, PRIVATE, PROTECTED, PACKAGE;
    
    public String toString() {
        return (this == PACKAGE)
                ? null
                : name().toLowerCase();
    }
}