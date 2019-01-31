package functionalj.types.choice.generator.model;

import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import lombok.val;

public class CaseParam {
    public final String  name;
    public final Type    type;
    public final boolean isNotNull;
    
    public CaseParam(String name, Type type, boolean isNotNull) {
        this.name = name;
        this.type = type;
        this.isNotNull = isNotNull;
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                type.toCode(),
                "" + isNotNull
        );
        return "new " + this.getClass().getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
    @Override
    public String toString() {
        return "CaseParam [name=" + name + ", type=" + type + ", isNotNull=" + isNotNull + "]";
    }
    
}