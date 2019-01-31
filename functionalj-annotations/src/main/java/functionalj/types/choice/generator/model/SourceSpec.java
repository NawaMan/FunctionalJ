package functionalj.annotations.choice.generator.model;

import static functionalj.annotations.choice.generator.Utils.toListCode;
import static functionalj.annotations.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
    public final List<Case>    choices;
    public final List<Method>  methods;
    public final List<String>  localTypeWithLens;
    public SourceSpec(String targetName, Type sourceType, List<Case> choices) {
        this(targetName, sourceType, null, false, new ArrayList<Generic>(), choices, new ArrayList<>(), new ArrayList<>());
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(targetName),
                sourceType.toCode(),
                toStringLiteral(specObjName),
                "" + publicFields,
                toListCode     (generics, Generic::toCode),
                toListCode     (choices,  Case::toCode),
                toListCode     (methods,  Method::toCode),
                toListCode     (localTypeWithLens.stream().map(name -> toStringLiteral(name)).collect(toList()), Function.identity())
        );
        
        return "new " + this.getClass().getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
}
