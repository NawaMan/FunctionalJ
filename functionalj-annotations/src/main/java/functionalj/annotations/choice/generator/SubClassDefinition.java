package functionalj.annotations.choice.generator;

import static functionalj.annotations.choice.generator.Utils.toTitleCase;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import functionalj.annotations.choice.generator.model.Case;
import lombok.AllArgsConstructor;
import lombok.val;


@AllArgsConstructor
public class SubClassDefinition implements Lines {
    public final TargetClass targetClass;
    public final Case      choice;
    
    @Override
    public List<String> lines() {
        val name     = choice.name;
        if (!choice.isParameterized()) {
            return asList(
                    format("public static final class %1$s%2$s extends %3$s {",    name, targetClass.genericDef(), targetClass.typeWithGenerics()),
                    format("    private static final %1$s instance = new %1$s();", name),
                    format("    private %1$s() {}",                                name),
                    format("}")
            );
        }
        
        val paramDefs  = choice.mapJoinParams(p -> p.type.typeWithGenerics() + " " + p.name, ", ");
        val paramCalls = choice.mapJoinParams(p ->                                   p.name, ", ");
        val fieldAccss = targetClass.spec.publicFields ? "public" : "private";
        return asList(
                asList(               format("public static final class %1$s%2$s extends %3$s {", name, targetClass.genericDef(), targetClass.typeWithGenerics())),
                choice.mapParams(p -> format("    %1$s %2$s %3$s;",                               fieldAccss, p.type.typeWithGenerics(), p.name)),
                asList(               format("    private %1$s(%2$s) {",                          name, paramDefs)),
                choice.mapParams(p -> format("        this.%1$s = %1$s;",                         p.name)),
                asList(               format("    }")),
                choice.mapParams(p -> format("    public %1$s %2$s() { return %2$s; }",       p.type.typeWithGenerics(), p.name)),
                choice.mapParams(p -> format("    public %1$s with%2$s(%3$s %4$s) { return new %5$s(%6$s); }",
                                                      name + targetClass.generics(), toTitleCase(p.name), p.type.typeWithGenerics(), p.name, name + targetClass.generics(), paramCalls)),
                asList(               format("}"))
            ).stream()
            .flatMap(List::stream)
            .collect(toList());
    }
}