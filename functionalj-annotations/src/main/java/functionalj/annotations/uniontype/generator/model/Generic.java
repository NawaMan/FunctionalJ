package functionalj.annotations.uniontype.generator.model;

import static functionalj.annotations.uniontype.generator.Utils.toListCode;
import static functionalj.annotations.uniontype.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import lombok.Value;
import lombok.val;

@Value
public class Generic {
    public final String name;
    public final String withBound;
    public final List<Type> boundTypes;
    public Generic(String name) {
        this(name, name, null);
    }
    public Generic(String name, String withBound, List<Type> boundTypes) {
        this.name = name;
        this.withBound = (withBound == null) ? name : withBound;
        this.boundTypes = boundTypes;
    }
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                toStringLiteral(withBound),
                toListCode     (boundTypes, Type::toCode)
        );
        return "new functionalj.annotations.uniontype.generator.model.Generic("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
}
