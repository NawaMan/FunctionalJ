package functionalj.annotations.uniontype.generator;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.List;

import functionalj.annotations.uniontype.generator.model.Choice;
import lombok.AllArgsConstructor;
import lombok.val;


@AllArgsConstructor
public class SubClassConstructor implements Lines {
    public final TargetClass targetClass;
    public final Choice      choice;
    
    @Override
    public List<String> lines() {
        val sourceName = targetClass.spec.sourceType.name;
        val name       = choice.name;
        if (!choice.isParameterized()) {
            return asList(
                format("public static final %1$s%2$s %3$s() {", (targetClass.genericDef().isEmpty() ? "" : targetClass.genericDef() + " "), targetClass.typeWithGenerics(), name),
                format("    return %3$s.instance;", targetClass.genericDef(), targetClass.typeWithGenerics(), name),
                format("}")
            );
        }
        
        val validateName = choice.validationMethod;
        val isV = (validateName != null);
        val paramDefs  = choice.mapJoinParams(p -> p.type.name + " " + p.name, ", ");
        val paramCalls = choice.mapJoinParams(p ->                     p.name, ", ");
        return asList(
                format      ("public static final %1$s%2$s %3$s(%4$s) {", (targetClass.genericDef().isEmpty() ? "" : targetClass.genericDef() + " "), targetClass.typeWithGenerics(), name, paramDefs),
                isV ? format("    %1$s.%2$s(%3$s);",                       sourceName, validateName, paramCalls) : null,
                format      ("    return new %1$s%2$s(%3$s);",             name, targetClass.generics(), paramCalls),
                format      ("}")
        );
    }
}