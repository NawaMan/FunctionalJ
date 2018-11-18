package functionalj.annotations.uniontype.generator.model;

import static functionalj.annotations.uniontype.generator.Utils.toListCode;
import static functionalj.annotations.uniontype.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;

@Value
@AllArgsConstructor
public class SourceSpec {
    public final String        targetName;
    public final Type          sourceType;
    public final String        specObjName;
    public final boolean       publicFields;
    public final List<Generic> generics;
    public final List<Choice>  choices;
    public final List<Method>  methods;
    public SourceSpec(String targetName, Type sourceType, List<Choice> choices) {
        this(targetName, sourceType, null, false, new ArrayList<Generic>(), choices, new ArrayList<>());
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(targetName),
                sourceType.toCode(),
                toStringLiteral(specObjName),
                "" + publicFields,
                toListCode     (generics, Generic::toCode),
                toListCode     (choices,  Choice::toCode),
                toListCode     (methods,  Method::toCode)
        );
        return "new functionalj.annotations.uniontype.generator.model.SourceSpec(" 
                + params.stream().collect(joining(", "))
                + ")";
    }
    
}
