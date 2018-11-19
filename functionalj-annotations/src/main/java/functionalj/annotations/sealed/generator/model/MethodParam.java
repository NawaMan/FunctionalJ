package functionalj.annotations.sealed.generator.model;

import static functionalj.annotations.sealed.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.val;

@ToString
@AllArgsConstructor
public class MethodParam {
    public final String name;
    public final Type   type;
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                type.toCode()
        );
        return "new functionalj.annotations.sealed.generator.model.MethodParam(" 
                + params.stream().collect(joining(", "))
                + ")";
    }
}