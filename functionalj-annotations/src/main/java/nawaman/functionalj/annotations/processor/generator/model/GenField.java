package nawaman.functionalj.annotations.processor.generator.model;

import static java.util.Arrays.asList;
import static nawaman.functionalj.annotations.processor.generator.ILines.oneLineOf;
import static nawaman.functionalj.functions.StringFunctions.toStr;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;
import nawaman.functionalj.annotations.processor.generator.ILines;
import nawaman.functionalj.annotations.processor.generator.Type;

@Value
@Wither
@EqualsAndHashCode(callSuper=false)
public class GenField implements GenElement {
    private Accessibility accessibility;
    private Modifiability modifiability;
    private Scope         scope;
    private String        name;
    private Type          type;
    private String        defaultValue;
    
    public ILines toDefinition() {
        val def = oneLineOf(
                    accessibility, 
                    scope, 
                    modifiability, 
                    type.simpleNameWithGeneric(), 
                    name
                );
        
        val value = (defaultValue != null) ? " = " + defaultValue : "";
        return ()->Stream.of(def + value + ";");
    }
}