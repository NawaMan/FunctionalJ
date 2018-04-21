package nawaman.functionalj.annotations.processor.generator.model;

import java.util.stream.Stream;

import lombok.Value;
import lombok.experimental.Wither;
import nawaman.functionalj.annotations.processor.generator.IRequireTypes;
import nawaman.functionalj.annotations.processor.generator.Type;

@Value
@Wither
public class GenParam implements IRequireTypes {
    private String name;
    private Type   type;
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.of(type);
    }
    public String toDefinition() {
        return type.simpleNameWithGeneric() + " " + name;
    }
}