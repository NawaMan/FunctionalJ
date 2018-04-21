package nawaman.functionalj.annotations.processor.generator;

import java.util.stream.Stream;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class GenParam implements IRequireTypes {
    private String name;
    private Type   type;
    
    @Override
    public Stream<Type> getRequiredTypes() {
        return Stream.of(type);
    }
    public String toDefinition() {
        return type.simpleNameWithGeneric() + " " + name;
    }
}