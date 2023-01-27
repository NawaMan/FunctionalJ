package functionalj.types.struct.generator;

import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import functionalj.types.Type;
import lombok.Value;
import lombok.val;

@Value
public class Parameter {
    
    private final String name;
    private final Type   type;
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                type.toCode()
        );
        return "new functionalj.types.struct.generator.Parameter("
                + params.stream().map(String::valueOf).collect(joining(", "))
                + ")";
    }
}
