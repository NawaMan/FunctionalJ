package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static nawaman.functionalj.functions.StringFunctions.toStr;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;

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
//    private List<Type>    additionalTypes;    // TODO - Take care of this.
    
    public ILines toDefinition() {
        val def = asList(accessibility, modifiability, scope, type.getSimpleName(), name).stream()
                .map(toStr())
                .filter(Objects::nonNull)
                .collect(joining(" "));
        val value = (defaultValue != null) ? " = " + defaultValue : "";
        return ()->Stream.of(def + value + ";");
    }
}