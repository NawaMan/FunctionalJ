package nawaman.functionalj.annotations.processor.generator.model;

public enum Modifiability {
    FINAL, MODIFIABLE;
    
    public String toString() {
        return (this == MODIFIABLE)
                ? null
                : name().toLowerCase();
    }
    
}