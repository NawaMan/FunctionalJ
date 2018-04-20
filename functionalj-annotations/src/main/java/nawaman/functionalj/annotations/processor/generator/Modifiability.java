package nawaman.functionalj.annotations.processor.generator;

public enum Modifiability {
    FINAL, MODIFIABLE;
    
    public String toString() {
        return (this == MODIFIABLE)
                ? null
                : name().toLowerCase();
    }
    
}