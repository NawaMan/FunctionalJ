package functionalj.types.choice.generator.model;

import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import lombok.val;

public class MethodParam {
    public final String name;
    public final Type   type;
    
    public MethodParam(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                type.toCode()
        );
        return "new " + this.getClass().getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
    @Override
    public String toString() {
        return "MethodParam [name=" + name + ", type=" + type + "]";
    }
    
}