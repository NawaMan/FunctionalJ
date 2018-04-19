package nawaman.functionalj.annotations.processor.generator;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class Getter {
    
    private String name;
    private Type type;

    public Getter(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}