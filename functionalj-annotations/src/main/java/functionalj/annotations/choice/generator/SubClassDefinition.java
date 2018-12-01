package functionalj.annotations.choice.generator;

import static functionalj.annotations.choice.generator.Utils.toTitleCase;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import functionalj.annotations.choice.generator.model.Case;
import functionalj.annotations.choice.generator.model.CaseParam;
import lombok.AllArgsConstructor;
import lombok.val;


@AllArgsConstructor
public class SubClassDefinition implements Lines {
    public final TargetClass targetClass;
    public final Case        choice;
    
    @Override
    public List<String> lines() {
        val name = choice.name;
        val lens = new CaseLens(targetClass.spec, choice);
        val lensInstance = lens.generateTheLensField().toDefinition(targetClass.type.pckg).lines().findFirst().get();
        if (!choice.isParameterized()) {
            return asList(
                    asList(format("public static final class %1$s%2$s extends %3$s {",    name, targetClass.genericDef(), targetClass.typeWithGenerics())),
                    asList(format("    " + lensInstance)),
                    asList(format("    private static final %1$s instance = new %1$s();", name)),
                    asList(format("    private %1$s() {}",                                name)),
                    lens.build().stream().map(l -> "    " + l).collect(Collectors.toList()),
                    asList(format("}"))
            ).stream()
            .flatMap(List::stream)
            .collect(toList());
        }
        
        val paramDefs  = choice.mapJoinParams(p -> p.type.typeWithGenerics() + " " + p.name, ", ");
        val paramCalls = choice.mapJoinParams(p ->                                   p.name, ", ");
        val fieldAccss = targetClass.spec.publicFields ? "public" : "private";
        return asList(
                asList(               format("public static final class %1$s%2$s extends %3$s {", name, targetClass.genericDef(), targetClass.typeWithGenerics())),
                asList(               format("    " + lensInstance)),
                choice.mapParams(p -> format("    %1$s %2$s %3$s;",                               fieldAccss, p.type.typeWithGenerics(), p.name)),
                asList(               format("    private %1$s(%2$s) {",                          name, paramDefs)),
                choice.mapParams(this::fieldAssignment),
                asList(               format("    }")),
                choice.mapParams(p -> format("    public %1$s %2$s() { return %2$s; }",           p.type.typeWithGenerics(), p.name)),
                choice.mapParams(p -> format("    public %1$s with%2$s(%3$s %4$s) { return new %5$s(%6$s); }",
                                                      name + targetClass.generics(), toTitleCase(p.name), p.type.typeWithGenerics(), p.name, name + targetClass.generics(), paramCalls)),
                lens.build().stream().map(l -> "    " + l).collect(Collectors.toList()),
                asList(               format("}"))
            ).stream()
            .flatMap(List::stream)
            .collect(toList());
    }
    
    private String fieldAssignment(CaseParam p) {
        if (p.type.isPrimitive())
             return format("        this.%1$s = %1$s;",                 p.name);
        else return format("        this.%1$s = $utils.notNull(%1$s);", p.name);
    }
}